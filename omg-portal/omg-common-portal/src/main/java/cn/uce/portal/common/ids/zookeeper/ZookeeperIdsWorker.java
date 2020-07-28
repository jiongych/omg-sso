package cn.uce.portal.common.ids.zookeeper;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException.ConnectionLossException;
import org.apache.zookeeper.KeeperException.SessionExpiredException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooKeeper.States;
import org.apache.zookeeper.data.Stat;
import org.springframework.util.Assert;

import cn.uce.portal.common.ids.IdsWorker;


public class ZookeeperIdsWorker implements IdsWorker {

	private Lock waitLock = new ReentrantLock();

	private static final String DEFAULT_NODE = "/uSecquence";

	private final ZooKeeper zooKeeper;

	private static ZookeeperIdsWorker idsWorker;

	private ZookeeperIdsWorker(ZooKeeper zooKeeper) {
		Assert.notNull(zooKeeper, "zooKeeper must not be null.");
		this.zooKeeper = zooKeeper;
	}

	public String nextId() {
		return nextId(DEFAULT_NODE);
	}

	private <T> T retryUntilConnected(Callable<T> callable) {
		while (true) {
			if (zooKeeper.getState() == States.CLOSED) {
				throw new IllegalStateException("zooKeeper already closed!");
			}
			try {
				return callable.call();
			} catch (ConnectionLossException e) {
				Thread.yield();
				retry();
			} catch (SessionExpiredException e) {
				Thread.yield();
				retry();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	private void retry() {
		try {
			waitLock.lockInterruptibly();
		} catch (InterruptedException e) {
			e.printStackTrace();
			return;
		}
		Condition waitCondition = waitLock.newCondition();
		boolean stillWaiting = true;
		Date timeout = new Date(System.currentTimeMillis() + 500l);
		try {
			while (zooKeeper.getState() != States.CONNECTED) {
				if (!stillWaiting) {
					return;
				}
				stillWaiting = waitCondition.awaitUntil(timeout);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			waitLock.unlock();
		}
	}

	public String nextId(final String sequence) {
		int nextId = retryUntilConnected(new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				Stat stat = zooKeeper.exists(sequence, false);
				if (null == stat) {
					zooKeeper.create(sequence, new byte[0],
							Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
				}
				stat = zooKeeper.setData(sequence, new byte[0], -1);
				return stat.getVersion();
			}
		});
		return nextId + "";
	}

	public static void main(String[] args) throws IOException,
			InterruptedException {
		ZookeeperIdsWorker worker = getWorker(new ZooKeeper(
				"localhost:2181,localhost:2182,localhost:2183", 1000,
				new Watcher() {
					public void process(WatchedEvent event) {

					}
				}));
		// 测试期间 Shutdown ZooKeeper实例, 然后重启
		for (int i = 0; i < 100; i++) {
			System.out.println(worker.nextId());
			if (i % 20 == 5) {
				Thread.sleep(5000l);
			}
		}
	}

	public static ZookeeperIdsWorker getWorker(ZooKeeper zooKeeper) {
		if (idsWorker == null) {
			synchronized (ZookeeperIdsWorker.class) {
				if (idsWorker == null) {
					idsWorker = new ZookeeperIdsWorker(zooKeeper);
				}
			}
		}
		if (zooKeeper.getState() == States.CLOSED
				|| zooKeeper.getState() == States.AUTH_FAILED) {
			throw new IllegalArgumentException("zooKeeper is unuseful.");
		}
		return idsWorker;
	}
}

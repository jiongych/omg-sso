package cn.uce.portal.common.ids.snowflake;

import java.security.SecureRandom;

import cn.uce.portal.common.ids.IdsWorker;


/**
 * 生成19位有序ID, 自增步长不稳定
 * @author zhuhy
 */
public class SnowFlakeIdsWorker implements IdsWorker{
	
	private static SnowFlakeIdsWorker idsWorker;

    private final long twepoch = 142004160000000L;

    private final long workerIdBits = 8L;

    private final long datacenterIdBits = 8L;

    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);
    
    private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);

    private final long sequenceBits = 12L;

    private final long workerIdShift = sequenceBits;

    private final long datacenterIdShift = sequenceBits + workerIdBits;

    private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    private long workerId;

    private long datacenterId;

    private long sequence = 0L;

    private long lastTimestamp = -1L;

    public SnowFlakeIdsWorker(long workerId, long datacenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    public synchronized String nextId() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                    String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        }
        else {
            sequence = 0L;
        }
        lastTimestamp = timestamp;
        return String.valueOf((((timestamp - twepoch) << timestampLeftShift) //
                | (datacenterId << datacenterIdShift) //
                | (workerId << workerIdShift) //
                | sequence)).substring(1);
    }

    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    protected long timeGen() {
        return System.currentTimeMillis();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            System.out.println(getWorker().nextId());
        }
        System.out.println(getWorker().nextId().length());
    }

	@Override
	public String nextId(String sequence) {
		throw new UnsupportedOperationException("Unsupport operation nextId(String sequence) for SnowFlakeIdsWorker.");
	}
	
	public static IdsWorker getWorker() {
		if(idsWorker == null){
			synchronized (SnowFlakeIdsWorker.class) {
				if(idsWorker == null) {
					SecureRandom sr = new SecureRandom();
					idsWorker = new SnowFlakeIdsWorker(sr.nextInt(256), sr.nextInt(256));
				}
			}
		}
		return idsWorker;
	}
}

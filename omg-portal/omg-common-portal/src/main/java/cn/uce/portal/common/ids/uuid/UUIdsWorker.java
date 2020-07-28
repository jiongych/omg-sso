package cn.uce.portal.common.ids.uuid;

import java.util.UUID;

import cn.uce.portal.common.ids.IdsWorker;
import cn.uce.portal.common.ids.snowflake.SnowFlakeIdsWorker;


public class UUIdsWorker implements IdsWorker{
	
	private static UUIdsWorker idsWorker;
	
	@Override
	public String nextId() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	@Override
	public String nextId(String sequence) {
		throw new UnsupportedOperationException("Unsupport operation nextId(String sequence) for UUIdsWorker.");
	}

	public static IdsWorker getWorker() {
		if(idsWorker == null){
			synchronized (SnowFlakeIdsWorker.class) {
				if(idsWorker == null) {
					idsWorker = new UUIdsWorker();
				}
			}
		}
		return idsWorker;
	}

}

package cn.uce.portal.common.ids;

public interface IdsWorker {
	
	String nextId();
	
	String nextId(String sequence);
	
}

package cn.uce.omg.portal.vo;

import java.io.Serializable;

public class PortalSimpleZTreeVo implements Serializable{

	private static final long serialVersionUID = 1L;
	//id
	private String i;
	//text
	private String t;
	//pid
	private String p;
	//checked
	private boolean c;
	
	private boolean nocheck;
	
	public boolean isNocheck() {
		return nocheck;
	}
	public void setNocheck(boolean nocheck) {
		this.nocheck = nocheck;
	}
	public String getI() {
		return i;
	}
	public void setI(String i) {
		this.i = i;
	}
	
	public String getT() {
		return t;
	}
	public void setT(String t) {
		this.t = t;
	}
	public String getP() {
		return p;
	}
	public void setP(String p) {
		this.p = p;
	}
	public boolean getC() {
		return c;
	}
	public void setC(boolean c) {
		this.c = c;
	}
}

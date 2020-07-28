package cn.uce.omg.sso.constant;

public enum UserAccountType {
	Portal(1),Yinhe(2);
	int value;
	
	private UserAccountType(int value){
		this.value = value;
	}
	
	public int value() {
		return this.value;
	}
}

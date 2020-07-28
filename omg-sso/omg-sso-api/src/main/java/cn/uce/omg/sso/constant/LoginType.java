package cn.uce.omg.sso.constant;

public enum LoginType {

	Password(1), Captcha(2), SweepCode(3);
	int value;
	
	private LoginType(int value) {
		this.value = value;
	}
	
	public int value() {
		return this.value;
	}
}

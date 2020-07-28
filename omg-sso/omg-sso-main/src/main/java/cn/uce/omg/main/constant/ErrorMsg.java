package cn.uce.omg.main.constant;
/**
 * 错误信息
 * @author crj
 *
 */
public enum ErrorMsg {
	EXCEPTION(-1,"系统异常，请联系管理员"),
    SUCCESS(0,"请求成功"),
    EMPCODE_NULL(1,"员工编号不能为空"),
    EXPIRED(2,"用户登录失效"),
    FILE_EXISTS(3,"暂无公告消息");

    private int code;
    private String message;

    public int getCode(){
        return code;
    }
    public String getMessage(){
        return message;
    }

    ErrorMsg(int code, String message){
        this.code = code;
        this.message = message;
    }
}
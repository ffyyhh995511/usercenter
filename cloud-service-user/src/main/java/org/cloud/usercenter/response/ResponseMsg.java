package org.cloud.usercenter.response;
public enum ResponseMsg {
	STATUS001(001,"接口请求成功"),
	STATUS002(002,"接口请求失败"),
	STATUS003(003,"参数有误"),
	STATUS004(004,"权限不足"),
	STATUS005(005,"系统内部异常");
	
	ResponseMsg(){
		
	}
	
	ResponseMsg(int code,String msg){
		this.code = code;
		this.msg = msg;
	}
	
	private int code;
	
	private String msg;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
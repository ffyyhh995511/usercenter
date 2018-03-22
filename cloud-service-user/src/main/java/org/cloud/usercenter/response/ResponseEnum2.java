package org.cloud.usercenter.response;
/**
 * 
 * @Description:
 * 代号枚举
 * 2xx表示通用请求成功
 * 4xx表示客户端异常（参数校验不通过、权限不足等）
 * 5xx表示服务端异常
 * @author:fangyunhe
 * @time:2018年3月21日 上午11:28:23
 * @version 1.0
 */
public enum ResponseEnum2 {
	STATUS200(200,"接口请求成功"),
	STATUS400(400,"接口请求失败"),
	STATUS500(500,"服务异常请稍后重试");
	
	ResponseEnum2(){
		
	}
	
	ResponseEnum2(int code,String msg){
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
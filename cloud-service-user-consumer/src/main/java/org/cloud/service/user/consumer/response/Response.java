package org.cloud.service.user.consumer.response;


import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @author fangyunhe
 *
 * 2017年9月13日 下午2:34:21
 */
@Data
public class Response<T> implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer code;      
	
    private String msg;
    
    private T data;

    public Response(T data, Integer code, String msg){
        this.data = data;
        this.code = code;
        this.msg = msg;
    }
    
    /**
     * 返回成功结果
     * @param <T>
     * @return
     */
    public static <T> Response<T> successResponse() {
        return new Response<T>(null, ResponseEnum.STATUS001.getCode(), ResponseEnum.STATUS001.getMsg());
    }
    
    public static <T> Response<T> successResponse(T data) {
        return new Response<T>(data, ResponseEnum.STATUS001.getCode(), ResponseEnum.STATUS001.getMsg());
    }
    
    public static <T> Response<T> successResponse(String msg) {
        return new Response<T>(null, ResponseEnum.STATUS001.getCode(), msg);
    }
    
    public static <T> Response<T> successResponse(T data,String msg) {
        return new Response<T>(data, ResponseEnum.STATUS001.getCode(), msg);
    }
    
    /**
     * 返回失败结果
     * @param <T>
     * @return
     */
    public static <T> Response<T> failedResponse() {
        return new Response<T>(null, ResponseEnum.STATUS002.getCode(), ResponseEnum.STATUS002.getMsg());
    }
    
    public static <T> Response<T> failedResponse(T data) {
        return new Response<T>(data, ResponseEnum.STATUS002.getCode(), ResponseEnum.STATUS002.getMsg());
    }
    
    public static <T> Response<T> failedResponse(String msg) {
        return new Response<T>(null, ResponseEnum.STATUS002.getCode(), msg);
    }
    
    /**
     * 返回参数校验失败结果
     * @param data
     * @return
     */
    public static <T> Response<T> paramsCheckFailResponse() {
        return new Response<T>(null, ResponseEnum.STATUS003.getCode(), ResponseEnum.STATUS003.getMsg());
    }
    
    public static <T> Response<T> paramsCheckFailResponse(T data) {
        return new Response<T>(data, ResponseEnum.STATUS003.getCode(), ResponseEnum.STATUS003.getMsg());
    }
    
    public static <T> Response<T> paramsCheckFailResponse(String msg) {
        return new Response<T>(null, ResponseEnum.STATUS003.getCode(), msg);
    }
    
    /**
     * 返回权限失败结果
     * @param <T>
     * @return
     */
    public static <T> Response<T> authResponse() {
        return new Response<T>(null, ResponseEnum.STATUS004.getCode(), ResponseEnum.STATUS004.getMsg());
    }
    
    public static <T> Response<T> authResponse(T data) {
        return new Response<T>(data, ResponseEnum.STATUS004.getCode(), ResponseEnum.STATUS004.getMsg());
    }
    
    public static <T> Response<T> authResponse(String msg) {
        return new Response<T>(null, ResponseEnum.STATUS004.getCode(), msg);
    }
    
    public static <T> Response<T> authResponse(T data,String msg) {
        return new Response<T>(data, ResponseEnum.STATUS004.getCode(), msg);
    }
    
    /**
     * 内部错误
     * @return
     */
    public static <T> Response<T> interiorErrorResponse() {
        return new Response<T>(null, ResponseEnum.STATUS005.getCode(), ResponseEnum.STATUS005.getMsg());
    }
}

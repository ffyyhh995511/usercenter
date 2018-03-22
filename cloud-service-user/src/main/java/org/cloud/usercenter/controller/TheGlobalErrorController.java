package org.cloud.usercenter.controller;

import org.cloud.usercenter.entity.BusinessType;
import org.cloud.usercenter.exception.GException;
import org.cloud.usercenter.response.Response;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * 模拟全局异常报错，然后全局拦截，并在aop打印错误参数
 * @Description:demo相关
 * @author:fangyunhe
 * @time:2018年3月21日 上午11:15:19
 * @version 1.0	
 */
@Slf4j
@RestController
@RequestMapping(value="/demo")
public class TheGlobalErrorController {
	
	@RequestMapping(value="/id",method=RequestMethod.POST)
	public Object add(){
		return Response.failedResponse("test");
	}
	
	@RequestMapping(value="/id",method=RequestMethod.GET)
	public Object selectById(String id,String name){
		return Response.failedResponse("test");
	}
	
	@RequestMapping(value="/error",method=RequestMethod.GET)
	public Object selectByError(String id,String name,BusinessType businessType) throws GException{
		throw new GException(500);
	}
	
	@RequestMapping(value="/error1",method=RequestMethod.GET)
	public Object selectByError1(String id,String name,BusinessType businessType) throws GException{
		throw new GException(400);
	}
	
	@RequestMapping(value="/error2",method=RequestMethod.GET)
	public Object selectByError2() throws GException{
		throw new NullPointerException();
	}
	
	@RequestMapping(value="/error3",method=RequestMethod.GET)
	public Object selectByError3() throws GException{
		throw new IndexOutOfBoundsException();
	}
	
	@RequestMapping(value="/id",method=RequestMethod.DELETE)
	public Object deleteById(){
		return Response.failedResponse("test");
	}
	
	@RequestMapping(value="/id",method=RequestMethod.PATCH)
	public Object updateById(){
		return Response.failedResponse("test");
	}
}

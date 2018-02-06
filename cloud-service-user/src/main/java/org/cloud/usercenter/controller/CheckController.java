/**
 * 
 */
package org.cloud.usercenter.controller;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.cloud.usercenter.response.Response;
import org.cloud.usercenter.service.TokenService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * @author:fangyunhe
 * @time:2018年1月8日 下午2:54:31
 */
@Slf4j
@RestController
@RequestMapping(value="/check")
public class CheckController {
	
	@Resource
	TokenService tokenService;
	
	/**
	 * 校验token是否合法
	 * web端token(cookie)相关
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/token",method=RequestMethod.GET)
	public Object token(String token){
		if(StringUtils.isBlank(token)) {
			return Response.paramsCheckFailResponse("参数不合法");
		}
		try {
			Object result = tokenService.checkToken(token);
			return result;
		} catch (Exception e) {
			log.error("令牌检查失败",e);
		}
		return Response.failedResponse("令牌检查失败");
	}
	
	/**
	 * app端票据校验
	 * @param ticket
	 * @return
	 */
	@RequestMapping(value="/ticket",method=RequestMethod.GET)
	public Object ticket(String ticket){
		if(StringUtils.isBlank(ticket)) {
			return Response.paramsCheckFailResponse("参数不合法");
		}
		try {
			Object result = tokenService.ticketToken(ticket);
			return result;
		} catch (Exception e) {
			log.error("票据检查失败",e);
		}
		return Response.failedResponse("票据检查失败");
	}
}

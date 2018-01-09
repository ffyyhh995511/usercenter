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
 * web版token(cookie)相关
 * @author:fangyunhe
 * @time:2018年1月8日 下午2:54:31
 */
@Slf4j
@RestController
@RequestMapping(value="/web/token")
public class WebTokenController {
	
	@Resource
	TokenService tokenService;
	
	/**
	 * web端校验token是否合法
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/check",method=RequestMethod.GET)
	public Object check(String token){
		if(StringUtils.isBlank(token)) {
			return Response.paramsCheckFailResponse("参数不合法");
		}
		try {
			Object result = tokenService.checkWebToken(token);
			return result;
		} catch (Exception e) {
			log.error("令牌检查失败",e);
		}
		return Response.failedResponse("令牌检查失败");
	}
}

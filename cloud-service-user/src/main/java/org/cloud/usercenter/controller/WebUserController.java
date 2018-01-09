/**
 * 
 */
package org.cloud.usercenter.controller;


import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.cloud.usercenter.dto.AuthorizationDto;
import org.cloud.usercenter.entity.User;
import org.cloud.usercenter.response.Response;
import org.cloud.usercenter.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * web版
 * @author:fangyunhe
 * @time:2018年1月4日 下午5:08:41
 */

@Slf4j
@RestController
@RequestMapping(value="web/user")
public class WebUserController {
	
	@Resource
	private UserService userService;
	
	/**
	 * 账号密码方式注册username、password
	 * @return
	 */
	@RequestMapping(value="/up/register",method=RequestMethod.POST)
	public Object upRegister(User user){
		if(user == null || !StringUtils.isNoneBlank(user.getUsername(),user.getPassword())) {
			return Response.paramsCheckFailResponse("账号密码不能为空");
		}
		try {
			Response<User> result = userService.saveUsernameAndPassword(user);
			return result;
		} catch (Exception e) {
			log.error("注册失败",e);
		}
		return Response.failedResponse("注册失败");
	}
	
	/**
	 * 账号密码方式登录
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/up/login",method=RequestMethod.POST)
	public Object upLogin(User user){
		if(user == null || !StringUtils.isNoneBlank(user.getUsername(),user.getPassword())) {
			return Response.paramsCheckFailResponse("账号密码不能为空");
		}
		try {
			Response<AuthorizationDto> result = userService.loginUsernameAndPassword(user);
			return result;
		} catch (Exception e) {
			log.error("登录失败",e);
		}
		return Response.failedResponse("登录失败");
	}
	
}

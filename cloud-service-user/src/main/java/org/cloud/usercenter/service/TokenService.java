/**
 * 
 */
package org.cloud.usercenter.service;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.cloud.usercenter.common.ConmonConstant;
import org.cloud.usercenter.entity.User;
import org.cloud.usercenter.mapper.UserMapper;
import org.cloud.usercenter.response.Response;
import org.cloud.usercenter.util.RandomCodeUtil;
import org.cloud.usercenter.util.RedisUtil;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description:TODO
 * @author:fangyunhe
 * @time:2018年1月8日 下午3:16:09
 */
@Slf4j
@Service
public class TokenService {
	
	private String intervalSymbol = "_";
	
	@Resource
	RsaService rsaService;
	
	@Resource
	UserMapper userMapper;

	/**
	 * web版检查每次通讯的token(cookie)
	 * @param token
	 * @throws Exception 
	 */
	public Object checkWebToken(String token) throws Exception {
		String plainToken = rsaService.decryptionByPrivateKey(token);
		String[] items = plainToken.split(intervalSymbol);
		String uid = items[0];
		String redisToken = RedisUtil.get(ConmonConstant.WEB_USER_TOEKEN + uid);
		if(StringUtils.isBlank(redisToken)) {
			return Response.failedResponse("请重新登录");
		}
		if(!token.equals(redisToken)) {
			return Response.failedResponse("令牌校验不通过");
		}
		long ttl = RedisUtil.ttl(ConmonConstant.WEB_USER_TOEKEN + uid);
		//如果过期时间小于一天，自动把token刷新到最新七天的过期时间
		if(ttl < 3600 * 24) {
			log.info("用户uid:{} web_token服务器重新续长",uid);
			long tokenExpire = System.currentTimeMillis() + 24 * 3600 * 7 * 1000L;
			RedisUtil.expireAt(ConmonConstant.WEB_USER_TOEKEN + uid, tokenExpire / 1000);
			User user = new User();
			user.setUid(Long.parseLong(uid));
			user.setTokenExpire(tokenExpire);
			userMapper.updateByPrimaryKeySelective(user);
		}
		return Response.successResponse("合法令牌");
	}
	
}

/**
 * 
 */
package org.cloud.usercenter.service;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.cloud.usercenter.common.ConmonConstant;
import org.cloud.usercenter.entity.User;
import org.cloud.usercenter.mapper.UserMapper;
import org.cloud.usercenter.response.Response;
import org.cloud.usercenter.util.AESUtils;
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
	public Object checkToken(String token) throws Exception {
//		String plainToken = rsaService.decryptionByPrivateKey(token);
		String plainToken = AESUtils.decrypt(token, ConmonConstant.AES_KEY);
		String[] items = plainToken.split(intervalSymbol);
		String uid = items[0];
		String redisToken = RedisUtil.get(ConmonConstant.USER_TOEKEN + uid);
		if(StringUtils.isBlank(redisToken)) {
			return Response.failedResponse("token已过期,请重新登录(客户端可以用refresh重新获取token,避免再次登录操作)");
		}
		if(!token.equals(redisToken)) {
			return Response.failedResponse("令牌校验不通过");
		}
		long ttl = RedisUtil.ttl(ConmonConstant.USER_TOEKEN + uid);
		//如果过期时间小于一天，自动把token刷新到最新七天的过期时间
		if(ttl < 3600 * 24) {
			log.info("用户uid:{} web_token服务器重新续长",uid);
			long tokenExpire = System.currentTimeMillis() + 24 * 3600 * 7 * 1000L;
			RedisUtil.expireAt(ConmonConstant.USER_TOEKEN + uid, tokenExpire / 1000);
			User user = new User();
			user.setUid(Long.parseLong(uid));
			user.setTokenExpire(tokenExpire);
			userMapper.updateByPrimaryKeySelective(user);
			return Response.successResponse("合法令牌,服务器已为token重新续长7天");
		}
		return Response.successResponse("合法令牌");
	}

	/**
	 * ticket = rsa(token_时间戳_四位随机数组成) 生成
	 * ticket为一次性使用，半个小时内不能重复用
	 * @param ticket
	 * @return
	 * @throws Exception 
	 */
	public Object ticketToken(String ticket) throws Exception {
		String value = RedisUtil.get(ticket);
		if(StringUtils.isNotBlank(value)) {
			return Response.failedResponse("票据不可重复使用");
		}
		//存到缓存中去
		RedisUtil.set(ticket,ticket);
		//ticket半小时过期
		RedisUtil.expire(ticket, 1800);
		
		String plainTicket = rsaService.decryptionByPrivateKey(ticket);
		String[] split = plainTicket.split(intervalSymbol);
		String token = split[0];
		Object checkTokenResult = checkToken(token);
		return checkTokenResult;
	}
	
}

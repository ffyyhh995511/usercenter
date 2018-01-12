/**
 * 
 */
package org.cloud.usercenter.service;

import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.cloud.usercenter.common.ConmonConstant;
import org.cloud.usercenter.dto.AuthorizationDto;
import org.cloud.usercenter.entity.SnowflakeIdConfig;
import org.cloud.usercenter.entity.User;
import org.cloud.usercenter.mapper.SnowflakeIdConfigMapper;
import org.cloud.usercenter.mapper.UserMapper;
import org.cloud.usercenter.response.Response;
import org.cloud.usercenter.util.ComputerInfoUtil;
import org.cloud.usercenter.util.MD5Util;
import org.cloud.usercenter.util.RandomCodeUtil;
import org.cloud.usercenter.util.RedisUtil;
import org.cloud.usercenter.util.ServerMacUtil;
import org.cloud.usercenter.util.SnowflakeIdGenerator;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description:TODO
 * @author:fangyunhe
 * @time:2018年1月4日 下午5:49:41
 */
@Slf4j
@Service
public class UserService {

	private String mac;

	private SnowflakeIdGenerator snowflakeIdGenerator;

	@Resource
	SnowflakeIdConfigMapper snowflakeIdConfigMapper;

	@Resource
	UserMapper userMapper;

	@Resource
	RsaService rsaService;

	@PostConstruct
	private void init() throws Exception {
		mac = ComputerInfoUtil.getMacAddress();
		SnowflakeIdConfig sfConfig = snowflakeIdConfigMapper.selectByPrimaryKey(mac);
		snowflakeIdGenerator = new SnowflakeIdGenerator(sfConfig.getWorkerId(), sfConfig.getDatacenterId());
	}

	/**
	 * 账号密码注册
	 * 
	 * @param user
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public Response<User> saveUsernameAndPassword(User user) throws NoSuchAlgorithmException {
		// 判断当前用户名是否已经注册
		User isExistUser = userMapper.selectByUsername(user.getUsername());
		if (isExistUser != null) {
			return Response.failedResponse("用户名已被注册");
		}
		// 注册、生成用户相关数据
		long nextId = snowflakeIdGenerator.nextId();
		String sourcePassword = user.getPassword();
		String encrypPassword = MD5Util.encryptionUpperCase(sourcePassword);
		user.setUid(nextId);
		user.setCreateTime(new Date());
		user.setPassword(encrypPassword);
		userMapper.insert(user);
		return Response.successResponse("注册成功");
	}

	/**
	 * 账密方式登录
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public Response<AuthorizationDto> loginUsernameAndPassword(User user) throws Exception {
		String password = user.getPassword();
		String encryptPassword = MD5Util.encryptionUpperCase(password);
		User isExistUser = userMapper.selectByUsernameAndPassword(user.getUsername(), encryptPassword);
		if (isExistUser == null) {
			return Response.failedResponse("账号密码错误");
		}
		// 登录成功、生成token
		AuthorizationDto auth = createAuth(isExistUser);
		// 更新数据库
		isExistUser.setLastLoginTime(new Date());
		isExistUser.setToken(auth.getToken());
		isExistUser.setTokenExpire(auth.getTokenExpire());
		int res = userMapper.updateByPrimaryKeySelective(isExistUser);
		if (res > 0) {
			String uid = String.valueOf(isExistUser.getUid());
			//token缓存
			RedisUtil.set(ConmonConstant.USER_TOEKEN + uid, auth.getToken());
			RedisUtil.expireAt(ConmonConstant.USER_TOEKEN + uid, auth.getTokenExpire() / 1000);
			//refreshToken缓存
			RedisUtil.set(ConmonConstant.USER_REFRESH_TOEKEN + uid, auth.getToken());
			RedisUtil.expireAt(ConmonConstant.USER_REFRESH_TOEKEN + uid, auth.getResreshTokenExpire() / 1000);
			return Response.successResponse(auth, "登录成功");
		}
		return Response.failedResponse("登录失败");
	}
	
	/**
	 * 生产web版auth对象
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	public AuthorizationDto createWebAuth(User user) throws Exception{
		// 生成token
		String commonTeken = user.getUid() + "_" + user.getUsername() + "_" + System.currentTimeMillis() + "_";
		String plainToken = commonTeken + RandomCodeUtil.getUniqueCode(4);
		String token = rsaService.encryptByPublicKey(plainToken);
		// web版token过期默认7天
		long tokenExpire = System.currentTimeMillis() + 24 * 3600 * 7 * 1000L;
		AuthorizationDto auth = new AuthorizationDto(user.getUid(), user.getUsername(), token, tokenExpire);
		return auth;
	}
	
	/**
	 * 生产app版token
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public AuthorizationDto createAuth(User user) throws Exception{
		String commonTeken = user.getUid() + "_" + user.getUsername() + "_" + System.currentTimeMillis() + "_";
		// 生成token
		String plainToken = commonTeken + RandomCodeUtil.getUniqueCode(4);
		String token = rsaService.encryptByPublicKey(plainToken);
		// 生成refreshToken
		String plainRefreshToken = commonTeken + RandomCodeUtil.getUniqueCode(8);
		String refreshToken = rsaService.encryptByPublicKey(plainRefreshToken);
		// web版token过期默认7天
		long tokenExpire = System.currentTimeMillis() + 24 * 3600 * 7 * 1000L;
		// refreshToken过期默认30天
		long resreshTokenExpire = System.currentTimeMillis() + 24 * 3600 * 30 * 1000L;
		AuthorizationDto auth = new AuthorizationDto(user.getUid(), user.getUsername(), token,refreshToken, tokenExpire, resreshTokenExpire);
		return auth;
	}

}

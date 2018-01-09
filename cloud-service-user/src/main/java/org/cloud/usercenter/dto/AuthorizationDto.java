/**
 * 
 */
package org.cloud.usercenter.dto;

/**
 * @Description:TODO
 * @author:fangyunhe
 * @time:2018年1月5日 下午3:54:44
 */

public class AuthorizationDto {
	private Long uid;
	
	private String username;
	
	/**
	 * 授权令牌
	 */
	private String token;
	/**
	 * 刷新令牌
	 */
	private String refreshToken;
	/**
	 * 令牌过期时间(时间戳)
	 */
	private long tokenExpire;
	/**
	 * 刷新令牌过期时间(时间戳)
	 */
	private long resreshTokenExpire;
	
	public AuthorizationDto() {
		
	}
	
	public AuthorizationDto(Long uid,String username, String token, String refreshToken, long tokenExpire, long resreshTokenExpire) {
		this.uid = uid;
		this.username = username;
		this.token = token;
		this.refreshToken = refreshToken;
		this.tokenExpire = tokenExpire;
		this.resreshTokenExpire = resreshTokenExpire;
	}
	
	public AuthorizationDto(Long uid,String username, String token, long tokenExpire) {
		this.uid = uid;
		this.username = username;
		this.token = token;
		this.tokenExpire = tokenExpire;
	}

	/**
	 * @return the uid
	 */
	public Long getUid() {
		return uid;
	}

	/**
	 * @param uid the uid to set
	 */
	public void setUid(Long uid) {
		this.uid = uid;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the refreshToken
	 */
	public String getRefreshToken() {
		return refreshToken;
	}

	/**
	 * @param refreshToken the refreshToken to set
	 */
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	/**
	 * @return the tokenExpire
	 */
	public long getTokenExpire() {
		return tokenExpire;
	}

	/**
	 * @param tokenExpire the tokenExpire to set
	 */
	public void setTokenExpire(long tokenExpire) {
		this.tokenExpire = tokenExpire;
	}

	/**
	 * @return the resreshTokenExpire
	 */
	public long getResreshTokenExpire() {
		return resreshTokenExpire;
	}

	/**
	 * @param resreshTokenExpire the resreshTokenExpire to set
	 */
	public void setResreshTokenExpire(long resreshTokenExpire) {
		this.resreshTokenExpire = resreshTokenExpire;
	}
	
}

package com.whackode.itrip.pojo.vo;

import com.whackode.itrip.pojo.entity.User;

import java.io.Serializable;

/**
 * 返回前端-Token相关VO
 */
public class TokenVO implements Serializable {
	/**
	 * 用户认证凭据
	 */
	private String token;
	/**
	 * 用户信息
	 */
	private User user;
	/**
	 * 过期时间
	 */
	private long expTime;
	/**
	 * 生成时间
	 */
	private long genTime;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public long getExpTime() {
		return expTime;
	}

	public void setExpTime(long expTime) {
		this.expTime = expTime;
	}

	public long getGenTime() {
		return genTime;
	}

	public void setGenTime(long genTime) {
		this.genTime = genTime;
	}

	public TokenVO(String token, User user, long expTime, long genTime) {
		this.token = token;
		this.user = user;
		this.expTime = expTime;
		this.genTime = genTime;
	}
}

package org.cloud.usercenter.mapper;

import org.apache.ibatis.annotations.Param;
import org.cloud.usercenter.entity.User;

public interface UserMapper {
    int deleteByPrimaryKey(Long uid);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long uid);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

	/**
	 * @param username
	 */
    User selectByUsername(@Param("username")String username);

	/**
	 * @param username
	 * @param encryptPassword
	 */
    User selectByUsernameAndPassword(@Param("username")String username, @Param("password")String encryptPassword);
}
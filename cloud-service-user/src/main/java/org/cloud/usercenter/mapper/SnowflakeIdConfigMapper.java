package org.cloud.usercenter.mapper;

import org.cloud.usercenter.entity.SnowflakeIdConfig;

public interface SnowflakeIdConfigMapper {
    int deleteByPrimaryKey(String mac);

    int insert(SnowflakeIdConfig record);

    int insertSelective(SnowflakeIdConfig record);

    SnowflakeIdConfig selectByPrimaryKey(String mac);

    int updateByPrimaryKeySelective(SnowflakeIdConfig record);

    int updateByPrimaryKey(SnowflakeIdConfig record);
}
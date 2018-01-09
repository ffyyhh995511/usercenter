package org.cloud.usercenter.mapper;

import org.cloud.usercenter.entity.BusinessType;

public interface BusinessTypeMapper {
    int deleteByPrimaryKey(String id);

    int insert(BusinessType record);

    int insertSelective(BusinessType record);

    BusinessType selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BusinessType record);

    int updateByPrimaryKey(BusinessType record);
}
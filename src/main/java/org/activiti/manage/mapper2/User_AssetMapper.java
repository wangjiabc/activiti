package org.activiti.manage.mapper2;

import com.voucher.manage.model.User_Asset;

public interface User_AssetMapper {
    int deleteByPrimaryKey(String openId);

    int insert(User_Asset record);

    int insertSelective(User_Asset record);

    User_Asset selectByPrimaryKey(String openId);

    int updateByPrimaryKeySelective(User_Asset record);

    int updateByPrimaryKey(User_Asset record);
    
    int getCountUser_AssetByOpenId(String openId);
}
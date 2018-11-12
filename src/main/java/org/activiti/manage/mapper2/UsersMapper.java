package org.activiti.manage.mapper2;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.voucher.manage.model.SNSUserInfo;
import com.voucher.manage.model.Users;


public interface UsersMapper {
    int deleteByPrimaryKey(String phone);

    int insert(Users record);

    int insertSelective(Users record);    

    int updateByPrimaryKeySelective(Users record);

    int updateByPrimaryKey(Users record);

    Integer upsubscribeByOpenId(Map<String, Object> paramterMap);
    
    int upAtionFormatter(Map<String, Object> paramterMap);
    
    int selectRepeatUser(@Param(value="name") String name);
    
    int selectRepeatUserByOpenId(@Param(value="openId") String openId);
    
    int insertUsersInfo(Users users);
    
    int updateUsersInfo(Users users);
    
    Users getUserByOnlyOpenId(String openId);
    
    //**新增方法

	List<Users> getAllFullUser(@Param(value="campusId")Integer campusId,@Param(value="limit")Integer limit, @Param(value="offset")Integer offset, @Param(value="sort")String sort, @Param(value="order")String order,@Param(value="search")String search);

	List<Users> getWetchatAllUsers(@Param(value="campusId")Integer campusId,@Param(value="place")Integer place,@Param(value="limit")Integer limit, @Param(value="offset")Integer offset, @Param(value="sort")String sort, @Param(value="order")String order);
	
	List<Users> getUserByPhone(@Param(value="limit")Integer limit, @Param(value="offset")Integer offset, @Param(value="sort")String sort, @Param(value="order")String order);
	
	List<Users> getUserByGuidance();
	
	List<Users> getUserByPlace(@Param(value="place")Integer place);
	
	Integer getOpenId(@Param(value="campusId")Integer campusId,@Param(value="openId")String openId);
	
	Integer insertUserInfo(SNSUserInfo snsUserInfo);
	
	Users getUserByOpenId(@Param(value="campusId")Integer campusId,@Param(value="openId")String openId);
	
	Integer upUserByOpenId(SNSUserInfo snsUserInfo);
	
	Integer getUserCount(@Param(value="campusAdmin")String campusAdmin,@Param(value="campusId")Integer campusId,@Param(value="search")String search);
	
	Integer getUserFullCount(@Param(value="campusId")Integer campusId,@Param(value="search")String search);

	Integer setUserAdmin(@Param(value="phone")String phone, @Param(value="campusId")Integer campusId);

	Integer setUserCommon(@Param(value="phone")String phone,@Param(value="campusId")Integer campusId);

	Integer setUserSuperAdmin(@Param(value="phone")String phone, @Param(value="campusId")Integer campusId);

	int updateUserImage(@Param(value="imageUrl")String imageUrl, @Param(value="phone")String phone);

	String getImageUrl(@Param(value="phone")String phone);

	List<Users> getDeliverAdmin(Map<String, Object> paramMap);

	int setUserToken(@Param(value="phone")String phoneId, @Param(value="token")String token);

	String getUserToken(@Param(value="togetherId")String togetherId);

	int clearOldToken(@Param(value="token")String token);

	String getUserPhone(@Param(value="togetherId")String togetherId);

	List<String> getAllSuperAdminPhone(Map<String, Object> paramterMap);      //获取所有的超级管理员

	String getUserTokenByPhone(Map<String, Object> paramterMap);      //获取用户token

	Integer getCountsByDevice(Map<String, Object> paramMap);   //获取不同设备用户的个数

	List<Users> selectByPhoneAndPassword(Map<String, Object> paramMap);
	
	Users checkLogin(String phone);

	List<String> getUserByType(Map<String, Object> paramMap);
	
	Users getUserByAssetCharter(@Param(value="charter")String charter, @Param(value="idNo")String idNo);
}
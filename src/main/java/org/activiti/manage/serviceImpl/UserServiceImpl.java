package org.activiti.manage.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.activiti.manage.mapper2.AccessMapper;
import org.activiti.manage.mapper2.User_AssetMapper;
import org.activiti.manage.mapper2.UsersMapper;
import org.activiti.manage.service.UserService;

import com.voucher.manage.model.Access;
import com.voucher.manage.model.SNSUserInfo;
import com.voucher.manage.model.User_Asset;
import com.voucher.manage.model.Users;



@Service("userService")
public class UserServiceImpl implements UserService {
	private UsersMapper usersMapper;         //操作用户信息

	private User_AssetMapper user_AssetMapper;
	
	private AccessMapper accessMapper;
	
	@Autowired
	public void setUsersMapper(UsersMapper usersMapper) {
		this.usersMapper = usersMapper;
	}

	@Autowired
	public void setUser_AssetMapper(User_AssetMapper user_AssetMapper) {
		this.user_AssetMapper = user_AssetMapper;
	}
	
	@Autowired
	public void setAccessMapper(AccessMapper accessMapper) {
		this.accessMapper = accessMapper;
	}
	
	public List<Users> getAllFullUser(Integer campusId,Integer limit, Integer offset, String sort,
			String order,String search) {
		return usersMapper.getAllFullUser(campusId,limit,offset,sort,order,search);
	}
	
	public Integer getUserCount(String campusAdmin ,Integer campusId,String search) {
		return usersMapper.getUserCount(campusAdmin,campusId,search);
	}

	public Integer getUserFullCount(Integer campusId,String search) {
		return usersMapper.getUserFullCount(campusId,search);
	}



	@Override
	public Integer getOpenId(Integer campusId, String openId) {
		// TODO Auto-generated method stub
		return usersMapper.getOpenId(campusId, openId);
	}

	@Override
	public Integer insertUser(SNSUserInfo snsUserInfo) {
		// TODO Auto-generated method stub
		return usersMapper.insertUserInfo(snsUserInfo);
	}

	@Override
	public Users getUserInfoById(Integer campusId, String openId) {
		// TODO Auto-generated method stub
		return usersMapper.getUserByOpenId(campusId, openId);
	}

	@Override
	public Integer upUserByOpenId(SNSUserInfo snsUserInfo) {
		// TODO Auto-generated method stub
		return usersMapper.upUserByOpenId(snsUserInfo);
	}

	@Override
	public Integer upsubscribeByOpenId(Map<String, Object> paramterMap) {
		// TODO Auto-generated method stub
		return usersMapper.upsubscribeByOpenId(paramterMap);
	}

	@Override
	public int upAtionFormatter(Map<String, Object> paramterMap) {
		// TODO Auto-generated method stub
		return usersMapper.upAtionFormatter(paramterMap);
	}

	@Override
	public int selectRepeatUser(String name) {
		// TODO Auto-generated method stub
		return usersMapper.selectRepeatUser(name);
	}

	@Override
	public int selectRepeatUserByOpenId(String openId) {
		// TODO Auto-generated method stub
		return usersMapper.selectRepeatUserByOpenId(openId);
	}

	@Override
	public int insertUsersInfo(Users users) {
		// TODO Auto-generated method stub
		return usersMapper.insertUsersInfo(users);
	}

	@Override
	public int updateUsersInfo(Users users) {
		// TODO Auto-generated method stub
		return usersMapper.updateUsersInfo(users);
	}

	@Override
	public Users getUserByOnlyOpenId(String openId) {
		// TODO Auto-generated method stub
		return usersMapper.getUserByOnlyOpenId(openId);
	}

	@Override
	public User_Asset selectUser_AssetByOpenId(String openId) {
		// TODO Auto-generated method stub
		return user_AssetMapper.selectByPrimaryKey(openId);
	}
	
	@Override
	public int insertIntoUser_AssetByOpenId(User_Asset user_Asset) {
		// TODO Auto-generated method stub
		return user_AssetMapper.insert(user_Asset);
	}

	@Override
	public int updateUser_AssetByOpenId(User_Asset user_Asset) {
		// TODO Auto-generated method stub
		return user_AssetMapper.updateByPrimaryKeySelective(user_Asset);
	}

	@Override
	public int getCountUser_AssetByOpenId(String openId) {
		// TODO Auto-generated method stub
		return user_AssetMapper.getCountUser_AssetByOpenId(openId);
	}

	@Override
	public List<Users> getUserByPhone(Integer limit, Integer offset, String sort, String order) {
		// TODO Auto-generated method stub
		return usersMapper.getUserByPhone(limit, offset, sort, order);
	}

	@Override
	public List<Users> getUserByGuidance() {
		// TODO Auto-generated method stub
		return usersMapper.getUserByGuidance();
	}

	@Override
	public List<Users> getUserByPlace(Integer place) {
		// TODO Auto-generated method stub
		return usersMapper.getUserByPlace(place);
	}
	
	@Override
	public List<Users> getWetchatAllUsers(Integer campusId,Integer place,Integer limit,Integer offset, String sort,String order) {
		// TODO Auto-generated method stub
		return usersMapper.getWetchatAllUsers(campusId, place,limit,offset,sort,order);
	}

	@Override
	public int insertAccess(Access access) {
		// TODO Auto-generated method stub
		return accessMapper.insert(access);
	}

	@Override
	public Map selectAllAccess(Integer campusId, Integer limit, Integer offset, String sort, String order,
			String search,String page) {
		// TODO Auto-generated method stub
		
		if(sort!=null&&sort.equals("accessTime")){
			sort="access_time";
		}else{
			sort="access_time";
		}
		
		if(order!=null&&order.equals("asc")){
			order="asc";
		}
		
		if(order!=null&&order.equals("desc")){
			order="desc";
		}
		
		List list=accessMapper.selectAllAccess(campusId, limit, offset, sort, order, search,page);
		
		int total=accessMapper.selectCountAccess(campusId, search,page);
	    
		Map map=new HashMap<>();
		
		map.put("rows", list);
		
		map.put("total", total);
		
		return map;
	}

	@Override
	public Users getUserByAssetCharter(String charter, String idNo) {
		// TODO Auto-generated method stub
		
		return usersMapper.getUserByAssetCharter(charter, idNo);
		
	}

}

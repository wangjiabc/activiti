package org.activiti.manage.mapper2;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.voucher.manage.model.Access;

public interface AccessMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Access record);

    int insertSelective(Access record);

    Access selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Access record);

    int updateByPrimaryKey(Access record);
    
    List<Access> selectAllAccess(@Param(value="campusId")Integer campusId,@Param(value="limit")Integer limit, 
    		@Param(value="offset")Integer offset, @Param(value="sort")String sort,@Param(value="order")String order,
    		@Param(value="search")String search,@Param(value="page")String page);
    
    int selectCountAccess(@Param(value="campusId")Integer campusId,@Param(value="search")String search,@Param(value="page")String page);
}
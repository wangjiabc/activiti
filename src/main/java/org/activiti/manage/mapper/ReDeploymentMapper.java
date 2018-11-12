package org.activiti.manage.mapper;

import java.util.List;

import org.activiti.manage.model.ReDeployment;

public interface ReDeploymentMapper {
    int deleteByPrimaryKey(String id);

    int insert(ReDeployment record);

    int insertSelective(ReDeployment record);

    ReDeployment selectByPrimaryKey(String id);

    List<ReDeployment> selectAll();
    
    int updateByPrimaryKeySelective(ReDeployment record);

    int updateByPrimaryKey(ReDeployment record);
}
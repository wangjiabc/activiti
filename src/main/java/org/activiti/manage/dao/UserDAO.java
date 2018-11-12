package org.activiti.manage.dao;

import java.util.List;

import org.activiti.manage.daoModel.Users;

public interface UserDAO {

    public Integer addUser(Users user);

    public Integer addUser2(Users users);
    
    
    public Integer addAll(Users users); 
    
    public void deleteUser(int id);

    public void updateUser(Users user);

    public String searchUserName(int id);
    
    public Users searchUser(int id);
    
    public List<Users> findAll();

}

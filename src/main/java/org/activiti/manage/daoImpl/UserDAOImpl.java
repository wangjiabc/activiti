package org.activiti.manage.daoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.activiti.manage.dao.UserDAO;
import org.activiti.manage.daoModel.Users;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;



public class UserDAOImpl extends JdbcDaoSupport implements UserDAO {

    public Integer addUser(Users users) {
        String sql = "insert into users values(?,?,?)";
        return this.getJdbcTemplate().update(sql, users.getId(), users.getUsername(),
                users.getPassword());
    }

    @Override
    public Integer addUser2(Users users) {
        String sql = "insert into users2 values(?,?,?)";
        return this.getJdbcTemplate().update(sql, users.getId(), users.getUsername(),
                users.getPassword());
    }
    
    @Override
	public Integer addAll(Users users) {
		// TODO Auto-generated method stub
    	int i=0;
    	i=addUser2(users);
    	i=addUser(users);
		return i;
	}
    
    public void deleteUser(int id) {
        String sql = "delete from users where id=?";
        this.getJdbcTemplate().update(sql, id);

    }

    public void updateUser(Users users) {
        String sql = "update users set username=?,password=? where id=?";
        this.getJdbcTemplate().update(sql, users.getUsername(),
                users.getPassword(), users.getId());
    }

    public String searchUserName(int id) {// 简单查询，按照ID查询，返回字符串
        String sql = "select username from users where id=?";
        // 返回类型为String(String.class)
        return this.getJdbcTemplate().queryForObject(sql, String.class, id);

    }

    public List<Users> findAll() {// 复杂查询返回List集合
        String sql = "select * from users";
        return this.getJdbcTemplate().query(sql, new UserRowMapper());

    }

    public Users searchUser(int id) {
        String sql="select * from users where id=?";
        return this.getJdbcTemplate().queryForObject(sql, new UserRowMapper(), id);
    }

    class UserRowMapper implements RowMapper<Users> {
        //rs为返回结果集，以每行为单位封装着
        public Users mapRow(ResultSet rs, int rowNum) throws SQLException {
    
            Users users = new Users();
            users.setId(rs.getInt("id"));
            users.setUsername(rs.getString("username"));
            users.setPassword(rs.getString("password"));
            return users;
        }

    }

	

}

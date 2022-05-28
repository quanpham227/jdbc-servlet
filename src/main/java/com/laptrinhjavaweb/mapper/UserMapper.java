package com.laptrinhjavaweb.mapper;

import com.laptrinhjavaweb.model.RoleModel;
import com.laptrinhjavaweb.model.UserModel;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserMapper implements RowMapper<UserModel> {

	@Override
	public UserModel mapRow(ResultSet resultSet) {
		try {
			UserModel userModel = new UserModel();
			userModel.setId(resultSet.getLong("id"));
			userModel.setUserName(resultSet.getString("username"));
			userModel.setPassword(resultSet.getString("password"));
			userModel.setFullName(resultSet.getString("fullname"));
			userModel.setStatus(resultSet.getInt("status"));
			try {
				RoleModel role = new RoleModel();
				role.setCode(resultSet.getString("code"));
				role.setName(resultSet.getString("name"));
				userModel.setRole(role);
			}catch(SQLException ex){
				Logger.getLogger(UserMapper.class.getName()).log(Level.SEVERE,null,ex);
			}
			return userModel;
		}catch (SQLException ex){
			return null;
		}
		
	}

}

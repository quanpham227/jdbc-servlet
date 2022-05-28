package com.laptrinhjavaweb.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.laptrinhjavaweb.dao.GenericDAO;
import com.laptrinhjavaweb.mapper.RowMapper;

public class AbstractDAO<T> implements GenericDAO<T> {
	ResourceBundle bundle = ResourceBundle.getBundle("db");
	public Connection getConnection() {
		try {
			Class.forName(bundle.getString("driverName"));
			String url = (bundle.getString("url"));
			String user = (bundle.getString("user"));
			String password = (bundle.getString("password"));
			return DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(AbstractDAO.class.getName()).log(Level.SEVERE,null,ex);
		} catch (SQLException e) {
			Logger.getLogger(AbstractDAO.class.getName()).log(Level.SEVERE,null,e);
		}
		return null;
	}

	

	private void setParameter(PreparedStatement statement, Object... parameters) {
				try {
					for (int i = 0; i < parameters.length; i++) {
						Object parameter = parameters[i];
						int index = i + 1;
						if(parameter instanceof Long) {
							statement.setLong(index, (Long) parameter);
						}else if(parameter instanceof Integer) {
							statement.setInt(index, (Integer) parameter);
						}else if(parameter instanceof String) {
							statement.setString(index, (String) parameter);
						}else if(parameter instanceof Timestamp) {
							statement.setTimestamp(index, (Timestamp) parameter);
						}
					}
					} catch (SQLException e) {
						Logger.getLogger(AbstractDAO.class.getName()).log(Level.SEVERE,null,e);
				}
	}



	@Override
	public <T>List<T> query(String sql, RowMapper<T> rowMapper, Object... parameters) {
		List<T> results = new ArrayList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = getConnection();
			statement = connection.prepareStatement(sql);
			//set parameter
			setParameter(statement,parameters);
			resultSet = statement.executeQuery();
			while(resultSet.next()) {
				results.add(rowMapper.mapRow(resultSet));
			}
		}catch(SQLException ex) {
			Logger.getLogger(AbstractDAO.class.getName()).log(Level.SEVERE,null,ex);
		}finally {
			try {
				if(connection != null) {
					connection.close();
				}else if(statement != null) {
					statement.close();
				}else if(resultSet != null) {
					resultSet.close();
				}
			}catch(SQLException ex) {
				Logger.getLogger(AbstractDAO.class.getName()).log(Level.SEVERE,null,ex);
			}
		}
	
		return results;
	}



	@Override
	public void update(String sql, Object... parameters) {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = getConnection();
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(sql);
			setParameter(statement, parameters);
			statement.executeUpdate();
			connection.commit();
		}catch(SQLException ex) {
			if(connection != null) {
				try {
					connection.rollback();
				} catch (SQLException e) {
					Logger.getLogger(AbstractDAO.class.getName()).log(Level.SEVERE,null,e);
				}
			}
			Logger.getLogger(AbstractDAO.class.getName()).log(Level.SEVERE,null,ex);
			
		}finally {
			try {
				if(connection != null) {
					connection.close();
				}else if(statement != null) {
					statement.close();
				}
			}catch(SQLException ex){
				Logger.getLogger(AbstractDAO.class.getName()).log(Level.SEVERE,null,ex);
			}
		}
	}



	@Override
	public Long insert(String sql, Object... parameters) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Long id = null;
		// thêm mới 1 bài viết thì phải trả về id của bài viết đó  owr trang giao dieejn load laij chi tieets cuar baif vieets ddos
		try {
			connection = getConnection();
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			setParameter(statement, parameters);
			statement.executeUpdate();
			resultSet = statement.getGeneratedKeys();// lấy id tự tăng mới nhất 
			if(resultSet.next()) {
				id = resultSet.getLong(1);
			}
			connection.commit();
		}catch(SQLException ex) {
			if(connection != null) {
				try {
					connection.rollback();
				} catch (SQLException e) {
					Logger.getLogger(AbstractDAO.class.getName()).log(Level.SEVERE,null,e);
				}
			}
			Logger.getLogger(AbstractDAO.class.getName()).log(Level.SEVERE,null,ex);
			
		}finally {
			try {
				if(connection != null) {
					connection.close();
				}else if(statement != null) {
					statement.close();
				}else if(resultSet != null) {
					resultSet.close();
				}
			}catch(SQLException ex){
				Logger.getLogger(AbstractDAO.class.getName()).log(Level.SEVERE,null,ex);
			}
		}
		return id;
	}



	@Override
	public void deleted(String sql, Object... parameters) {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = getConnection();
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(sql);
			setParameter(statement, parameters);
			statement.executeUpdate();
			connection.commit();
		}catch(SQLException ex) {
			if(connection != null) {
				try {
					connection.rollback();
				} catch (SQLException e) {
					Logger.getLogger(AbstractDAO.class.getName()).log(Level.SEVERE,null,e);
				}
			}
			Logger.getLogger(AbstractDAO.class.getName()).log(Level.SEVERE,null,ex);
			
		}finally {
			try {
				if(connection != null) {
					connection.close();
				}else if(statement != null) {
					statement.close();
				}
			}catch(SQLException ex){
				Logger.getLogger(AbstractDAO.class.getName()).log(Level.SEVERE,null,ex);
			}
		}
		
	}



	@Override
	public int count(String sql, Object... parameters) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		int count= 0;
		try {
			
			connection = getConnection();
			statement = connection.prepareStatement(sql);
			//set parameter
			setParameter(statement,parameters);
			resultSet = statement.executeQuery();
			while(resultSet.next()) {
				count = resultSet.getInt(1);
			}
		}catch(SQLException ex) {
			Logger.getLogger(AbstractDAO.class.getName()).log(Level.SEVERE,null,ex);
		}finally {
			try {
				if(connection != null) {
					connection.close();
				}else if(statement != null) {
					statement.close();
				}else if(resultSet != null) {
					resultSet.close();
				}
			}catch(SQLException ex) {
				Logger.getLogger(AbstractDAO.class.getName()).log(Level.SEVERE,null,ex);
			}
		}
		return count;
	}
}

package com.laptrinhjavaweb.dao.impl;

import java.util.List;

import com.laptrinhjavaweb.dao.INewDao;
import com.laptrinhjavaweb.mapper.NewMapper;
import com.laptrinhjavaweb.model.NewModel;
import com.laptrinhjavaweb.paging.Pageble;
import org.apache.commons.lang.StringUtils;

public class NewDao extends AbstractDAO<NewModel> implements INewDao {
	@Override
	public List<NewModel> findByCategoryId(Long categoryId) {
		String sql = "Select * from news where categoryid = ?";
		return query(sql, new NewMapper(), categoryId);
		// trả về 1 list
	}

	@Override
	public Long insert(NewModel newModel) {
		String sql = "insert into news(title,thumbnail,shortdescription, content, categoryid,createddate,createdby) values (?,?,?,?,?,?,?) ";
		return insert(sql, newModel.getTitle(),newModel.getThumbnail(),newModel.getShortDescription(),
				newModel.getContent(), newModel.getCategoryId(),newModel.getCreatedDate(),newModel.getCreateBy());
	}

	@Override
	public void update(NewModel newModel) {
		StringBuilder sql = new StringBuilder("update news set title=?,thumbnail=?,");
		sql.append("shortdescription=?,content=?,categoryid=?,");
		sql.append("createddate=?,createdby=? where id=?");
		update(sql.toString(),newModel.getTitle(),newModel.getThumbnail(),newModel.getShortDescription(),
			newModel.getContent(),newModel.getCategoryId(),newModel.getCreatedDate(),newModel.getCreateBy(),newModel.getId());
	}

	

	@Override
	public NewModel findOne(Long id) {
		String sql = "Select * from news where id = ?";
		List<NewModel> news = query(sql, new NewMapper(), id);
		return news.isEmpty() ? null:news.get(0);
		// hàm trả về singer newModel
	}

	@Override
	public void deleted(long id) {
		String sql = "delete from news where id = ?";
		deleted(sql, id);
		
	}

	@Override
	public List<NewModel> findAll(Pageble pageble) {
		StringBuilder sql = new StringBuilder("Select * from news");
		if(pageble.getSorter() !=null && StringUtils.isNotBlank(pageble.getSorter().getSortName())&& StringUtils.isNotBlank(pageble.getSorter().getSortBy())) {
			sql.append(" ORDER BY "+pageble.getSorter().getSortName()+" "+pageble.getSorter().getSortBy()+"");
		}
		if(pageble.getOffset() != null) {
			sql.append(" limit "+pageble.getOffset()+","+pageble.getLimit()+"");
		}
		return query(sql.toString(), new NewMapper());
		
		
	}

	@Override
	public int getTotalItem() {
		String sql = "select count(*) from news";
		return count(sql);
	}

}
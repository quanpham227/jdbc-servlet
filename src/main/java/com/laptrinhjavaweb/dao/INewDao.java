package com.laptrinhjavaweb.dao;

import java.util.List;

import com.laptrinhjavaweb.model.NewModel;
import com.laptrinhjavaweb.paging.Pageble;

public interface INewDao extends GenericDAO<NewModel>{
	NewModel findOne (Long id);
	List<NewModel> findByCategoryId(Long categoryId);
	Long insert(NewModel newModel);
	void update(NewModel newModel);
	void deleted(long id);
	
	List<NewModel> findAll(Pageble pageble);
	int getTotalItem();
}

package com.laptrinhjavaweb.service;

import java.util.List;

import com.laptrinhjavaweb.model.NewModel;
import com.laptrinhjavaweb.paging.Pageble;

public interface INewService {
	List<NewModel> findByCategoryId(Long categoryId);
	NewModel insert(NewModel newModel);
	NewModel update(NewModel newModel);
	void deleted(long[] ids);
	
	List<NewModel> findAll(Pageble pageble);// quan ly bai viet
	int getTotalItem();

	NewModel findOne(Long id);


}

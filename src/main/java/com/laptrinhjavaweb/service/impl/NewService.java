package com.laptrinhjavaweb.service.impl;

import com.laptrinhjavaweb.dao.ICategoryDAO;
import com.laptrinhjavaweb.dao.INewDao;
import com.laptrinhjavaweb.model.NewModel;
import com.laptrinhjavaweb.model.CategoryModel;
import com.laptrinhjavaweb.paging.Pageble;
import com.laptrinhjavaweb.service.INewService;
import java.sql.Timestamp;
import java.util.List;
import javax.inject.Inject;

public class NewService implements INewService{
	@Inject
	private INewDao newDao;
	@Inject
	private ICategoryDAO categoryDAO;

	@Override
	public List<NewModel> findByCategoryId(Long categoryId) {
		return newDao.findByCategoryId(categoryId);
	}

	@Override
	public NewModel insert(NewModel newModel) {
		newModel.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		CategoryModel categoryModel = categoryDAO.findOneByCode(newModel.getCategoryCode());
		newModel.setCategoryId(categoryModel.getId());
		Long newId = newDao.insert(newModel);
		return newDao.findOne(newId);
	}

	@Override
	public NewModel update(NewModel newModel) {
		NewModel oldNew = newDao.findOne(newModel.getId());
		newModel.setCreatedDate(oldNew.getCreatedDate());
		newModel.setCreateBy(oldNew.getCreateBy());
		newModel.setModifiedDate(new Timestamp(System.currentTimeMillis()));
		CategoryModel categoryModel = categoryDAO.findOneByCode(newModel.getCategoryCode());
		newModel.setCategoryId(categoryModel.getId());
		newDao.update(newModel);
		return newDao.findOne(newModel.getId());
	}

	@Override
	public void deleted(long[] ids) {
		for (long id : ids) {
			newDao.deleted(id);
		}
		
	}

	@Override
	public List<NewModel> findAll(Pageble pageble) {
		return newDao.findAll(pageble);
	}

	@Override
	public int getTotalItem() {
		return newDao.getTotalItem();
	}

	@Override
	public NewModel findOne(Long id) {
		NewModel newModel = newDao.findOne(id);
		CategoryModel categoryModel = categoryDAO.findOne(newModel.getCategoryId());
		newModel.setCategoryCode(categoryModel.getCode());
		return newModel;
	}


}

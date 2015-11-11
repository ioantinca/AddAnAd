package com.ioan.dao;

import java.util.List;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ioan.model.Category;


public interface CategoryDao extends CrudRepository<Category, Long> {
	
	public List<Category> findByName(String name);
	
	public List<Category> findByParentCategory(Category categoryParent);
	

}

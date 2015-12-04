package com.ioan.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.ioan.model.Category;

@RepositoryRestResource
public interface CategoryRepository extends CrudRepository<Category, Long> {
	
	public List<Category> findByName(String name);
	
	public List<Category> findByParent(@Param("parent") Category parentCategory);

}

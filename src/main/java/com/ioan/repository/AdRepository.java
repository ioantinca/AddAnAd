package com.ioan.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ioan.model.Ad;
import com.ioan.model.Category;


public interface AdRepository extends CrudRepository<Ad, Long> {
	
	public List<Ad> findByCategory(Category category);

}

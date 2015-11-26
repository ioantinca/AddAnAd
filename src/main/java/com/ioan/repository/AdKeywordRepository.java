package com.ioan.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.ioan.model.Ad;
import com.ioan.model.AdKeyword;

@RepositoryRestResource
public interface AdKeywordRepository extends CrudRepository<AdKeyword, Long> {

	public List<AdKeyword> findByKeyword(String keyword);
	
	public List<AdKeyword> findByAd(Ad Ad);
	
}

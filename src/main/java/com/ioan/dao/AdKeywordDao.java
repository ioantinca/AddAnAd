package com.ioan.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ioan.model.Ad;
import com.ioan.model.AdKeyword;

public interface AdKeywordDao extends CrudRepository<AdKeyword, Long> {

	public List<AdKeyword> findByKeyword(String keyword);
	
	public List<AdKeyword> findByAd(Ad Ad);
	
}

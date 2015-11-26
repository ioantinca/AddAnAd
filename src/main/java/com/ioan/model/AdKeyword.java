package com.ioan.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="adkeyword")
public class AdKeyword {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String keyword;

	@ManyToOne
	private Ad ad;

	public AdKeyword() {

	}

	public AdKeyword(String keyword, Ad ad) {
		this.keyword = keyword;
		this.ad = ad;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Ad getAd() {
		return ad;
	}

	public void setAd(Ad ad) {
		this.ad = ad;
	}

}

package com.ioan;

import java.util.List;

import javassist.compiler.ast.Keyword;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ioan.model.Ad;
import com.ioan.model.AdKeyword;
import com.ioan.model.Category;
import com.ioan.repository.AdKeywordRepository;
import com.ioan.repository.AdRepository;
import com.ioan.repository.CategoryRepository;
import com.ioan.util.Util;

/**
 * Standalone application with Spring Data JPA, Hibernate and Maven
 * 
 */
public class App {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext.xml");

		addDataToDB(context);

		CategoryRepository categoryDao = context.getBean(CategoryRepository.class);

		AdRepository adDao = context.getBean(AdRepository.class);

		Category categoriesAuto = categoryDao.findByName("Autoturisme").get(0);

		for (Ad ad : adDao.findByCategory(categoriesAuto)) {
			System.out.println(ad.getName() + " " + ad.getDescription() + " "
					+ ad.getPrice());
		}

		AdKeywordRepository adKeywordDao = context.getBean(AdKeywordRepository.class);

		List<AdKeyword> adKeywordsMasina = adKeywordDao.findByKeyword("masina");

		for (AdKeyword adKeyword : adKeywordsMasina) {
			System.out.println(adKeyword.getKeyword() + " " + adKeyword.getId()
					+ " " + adKeyword.getAd().getName());
		}
		

		List<AdKeyword> adKeywords = adKeywordDao.findByAd(adDao.findOne((long) 14));

		for (AdKeyword adKeyword : adKeywordsMasina) {
			System.out.println(adKeyword.getKeyword() + " " + adKeyword.getId()
					+ " " + adKeyword.getAd().getName());
		}
		

		context.close();

	}

	private static void addDataToDB(ClassPathXmlApplicationContext context) {
		Util.addDummyDataToDB(context);
	}

}
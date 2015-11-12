package com.ioan.util;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ioan.model.Ad;
import com.ioan.model.AdKeyword;
import com.ioan.model.Category;
import com.ioan.repository.AdKeywordRepository;
import com.ioan.repository.AdRepository;
import com.ioan.repository.CategoryRepository;

public class Util {

	public static void addDummyDataToDB(ClassPathXmlApplicationContext context) {
		CategoryRepository categoryDao = context.getBean(CategoryRepository.class);

		AdRepository adDao = context.getBean(AdRepository.class);

		AdKeywordRepository adKeywordDao = context.getBean(AdKeywordRepository.class);
		
		Category auto = new Category("Auto");
		categoryDao.save(auto);

		Category autoturisme = new Category("Autoturisme");
		autoturisme.setParentCategory(auto);

		Category autoServicii = new Category("Servicii");
		autoServicii.setParentCategory(auto);

		Category pieseAuto = new Category("Piese auto");
		pieseAuto.setParentCategory(autoServicii);
		Category serviceAuto = new Category("Service-uri");
		serviceAuto.setParentCategory(autoServicii);

		categoryDao.save(autoturisme);
		categoryDao.save(autoServicii);
		categoryDao.save(pieseAuto);
		categoryDao.save(serviceAuto);

		Category imobiliare = new Category("Imobiliare");
		categoryDao.save(imobiliare);

		Category terenuri = new Category("terenuri");
		terenuri.setParentCategory(imobiliare);

		Category apartamente = new Category("apartamente");
		apartamente.setParentCategory(imobiliare);

		Category vanzare = new Category("Vanzare");
		vanzare.setParentCategory(apartamente);

		Category chirie = new Category("Chirie");
		chirie.setParentCategory(apartamente);

		categoryDao.save(terenuri);
		categoryDao.save(apartamente);
		categoryDao.save(vanzare);
		categoryDao.save(chirie);

		Category electronice = new Category("Electronice");
		categoryDao.save(electronice);

		Category telefoane = new Category("Telefoane");
		telefoane.setParentCategory(electronice);

		Category tablete = new Category("Tablete");
		tablete.setParentCategory(electronice);

		categoryDao.save(telefoane);
		categoryDao.save(tablete);
		
		
		Ad mercedes = new Ad ("mercedes", "Vand mercedes", 2000.0);
		Ad audi = new Ad ("audi" , "Vanzare audi" , 1000.0);
		
		mercedes.setCategory(autoturisme);
		audi.setCategory(autoturisme);
		
		adDao.save(mercedes);
		adDao.save(audi);
		
		Ad rulment = new Ad ("rulment", "Vand rulmenti", 100.0);
		rulment.setCategory(pieseAuto);
		adDao.save(rulment);
		
		Ad service = new Ad ("service", "Service Mercedes", 0.0);
		service.setCategory(serviceAuto);
		adDao.save(service);
		
		Ad terenArges = new Ad("teren arges", "Vand 300 de ha in Arges", 1000.0);
		terenArges.setCategory(terenuri);
		adDao.save(terenArges);
		
		Ad terenBrasov = new Ad("teren brasov", "Vand 45 de ha in Brasov", 200.0);
		terenBrasov.setCategory(terenuri);
		adDao.save(terenBrasov);
		
		AdKeyword  adKeywordMercedes = new AdKeyword("mercedes", mercedes);
		adKeywordDao.save(adKeywordMercedes);
		
		AdKeyword  adKeywordMasina = new AdKeyword("masina", mercedes);
		adKeywordDao.save(adKeywordMasina);
		
		
		

	}
}

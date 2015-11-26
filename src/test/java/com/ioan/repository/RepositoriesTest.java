package com.ioan.repository;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.PropertySources;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.ioan.Application;
import com.ioan.model.Ad;
import com.ioan.model.AdKeyword;
import com.ioan.model.Category;
import com.ioan.repository.CategoryRepository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class,
		DbUnitTestExecutionListener.class })
@DatabaseSetup("classpath:testsData.xml")
@TestPropertySource(locations="classpath:test.properties")
public class RepositoriesTest {

	@Autowired
	public CategoryRepository categoryRepository;

	@Autowired
	public AdRepository adRepository;

	@Autowired
	public AdKeywordRepository adKeywordRepository;

	 @Test
	public void search_NoCategoriesFound_ShouldReturnEmptyList() {
		List<Category> categoryEntries = categoryRepository
				.findByName("NOT FOUND");
		assertEquals(categoryEntries.size(), 0);
	}

	 @Test
	public void search_OneCategoryFound_ShouldReturnAListOfOneEntry() {
		List<Category> categoryEntries = categoryRepository
				.findByName("Autoturisme");
		assertThat(categoryEntries.size(), equalTo(1));
		assertThat(
				categoryEntries.get(0),
				allOf(hasProperty("id", is(2L)),
						hasProperty("name", is("Autoturisme"))));
		assertThat(categoryEntries.get(0).getParent(),
				hasProperty("id", is(1L)));
	}

	 @Test
	public void search_CategoryByParent() {
		List<Category> categoryEntries = categoryRepository.findByName("Auto");

		Category parent = categoryEntries.get(0);

		List<Category> childCategories = categoryRepository
				.findByParent(parent);

		assertThat(childCategories.size(), equalTo(2));
		assertThat(
				childCategories,
				contains(
						allOf(hasProperty("id", is(2L)),
								hasProperty("name", is("Autoturisme"))),
						allOf(allOf(hasProperty("id", is(3L)),
								hasProperty("name", is("Servicii"))))));

	}

	 @Test
	public void searchAd_ByCategoryNull() {
		List<Ad> adEntries = adRepository.findByCategory(null);

		assertThat(adEntries.size(), equalTo(0));
	}

	 @Test
	public void searchAd_ByInexistentCategory() {

		Category inexistentCategory = new Category("INEXISTENT");
		inexistentCategory.setId(-1L);

		List<Ad> adEntries = adRepository.findByCategory(inexistentCategory);

		assertThat(adEntries.size(), equalTo(0));
	}

	 @Test
	public void searchAd_ByExistentCategory() {
		Category autoturismeCategory = categoryRepository.findOne((long) 2);

		List<Ad> adEntries = adRepository.findByCategory(autoturismeCategory);

		assertThat(adEntries.size(), equalTo(2));
		assertThat(
				adEntries,
				contains(
						allOf(hasProperty("id", is(1L)),
								hasProperty("name", is("Mercedes")),
								hasProperty("description", is("Vand Mercedes")),
								hasProperty("price", is(1000.0))),
						allOf(hasProperty("id", is(2L)),
								hasProperty("name", is("Audi")),
								hasProperty("description", is("Vand Audi")),
								hasProperty("price", is(1000.0)))));

	}

	 @Test
	public void searchAdKeyword_ByInexistentKeyword() {
		List<AdKeyword> adKeywords = adKeywordRepository
				.findByKeyword("INEXISTENT");

		assertThat(adKeywords.size(), equalTo(0));
	}

	@Test
	public void searchAdKeyowrd_ByExistentKeyWord() {
		List<AdKeyword> adKeywords = adKeywordRepository
				.findByKeyword("masina");

		assertThat(adKeywords.size(), equalTo(2));
		assertThat(
				adKeywords,
				contains(
						allOf(hasProperty("id", is(2L)),
								hasProperty("keyword", is("masina"))),
						allOf(hasProperty("id", is(3L)),
								hasProperty("keyword", is("masina")))));
	}
	
	@Test
	public void searchAdKeyword_ByAd() {
		Ad ad = adRepository.findOne(1L);
		
		List<AdKeyword> adKeywords = adKeywordRepository.findByAd(ad);
		
		assertThat(adKeywords.size(), equalTo(2));
		assertThat(
				adKeywords,
				contains(
						allOf(hasProperty("id", is(1L)),
								hasProperty("keyword", is("mercedes"))),
						allOf(hasProperty("id", is(2L)),
								hasProperty("keyword", is("masina")))));
	
		
	}
}

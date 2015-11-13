package com.ioan.repository;

import java.util.List;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.beans.HasPropertyWithValue;
import org.hamcrest.core.Is;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.ioan.model.Ad;
import com.ioan.model.Category;
import com.ioan.repository.CategoryRepository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class,
		DbUnitTestExecutionListener.class })
@DatabaseSetup("classpath:testsData.xml")
public class RepositoriesTest {

	@Autowired
	public CategoryRepository categoryRepository;

	@Autowired
	public AdRepository adRepository;

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
}

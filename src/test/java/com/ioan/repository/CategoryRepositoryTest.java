package com.ioan.repository;

import java.util.List;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.beans.HasPropertyWithValue;
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
import com.ioan.model.Category;
import com.ioan.repository.CategoryRepository;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class,
		DbUnitTestExecutionListener.class })
@DatabaseSetup("classpath:categoryData.xml")
public class CategoryRepositoryTest {

	@Autowired
	public CategoryRepository categoryRepository;

	@Test
	public void search_NoCategoriesFound_ShouldReturnEmptyList() {
		List<Category> categoryEntries = categoryRepository
				.findByName("NOT FOUND");
		assertEquals(categoryEntries.size(), 0);
	}
	
	public void search_OneCategoryFound_ShouldReturnAListOfOneEntry() {
		List<Category> categoryEntries = categoryRepository
				.findByName("Autoturisme");
		assertEquals(categoryEntries.size(), 1);
//		Category category
	}

}

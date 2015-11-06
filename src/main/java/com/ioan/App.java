package com.ioan;

import java.util.List;
 
import org.springframework.context.support.ClassPathXmlApplicationContext;
 
import com.ioan.dao.CategoryDao;
import com.ioan.model.Category;
 
/**
 * Standalone application with Spring Data JPA, Hibernate and Maven
 * 
 * @author DevCrumb.com
 */
public class App {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "applicationContext.xml");
        CategoryDao dao = context.getBean(CategoryDao.class);
 
        Category auto = new Category("Auto3");
        Category imobiliare = new Category("Imobiliare");
 
        // Add new Category records
        dao.save(auto);
        dao.save(imobiliare);
 
        // Count Person records
        System.out.println("Count Category records: " + dao.count());
 
        // Print all records
        List<Category> categories = (List<Category>) dao.findAll();
        for (Category category : categories) {
            System.out.println(category);
        }
 
        // Find Person by surname
        System.out.println("Find by auto 'Auto': "  + dao.findByName("Auto"));
 
        // Update Person
        imobiliare.setName("Imobiliar");
        dao.save(imobiliare);
 
        System.out.println("Find by id 2: " + dao.findOne(2L));
 
        // Remove record from Person
//        dao.delete(2L);
 
        // And finally count records
        System.out.println("Count records: " + dao.count());
 
        context.close();
    }
}
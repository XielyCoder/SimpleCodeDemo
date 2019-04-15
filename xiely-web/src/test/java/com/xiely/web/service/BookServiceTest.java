package com.xiely.web.service;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;
import java.util.List;

import com.xiely.web.sql.entities.Book;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BookServiceTest
{
    private static BookService bookservice;

    @BeforeClass
    public static void before()
    {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath*:resources/applicationContext.xml");
        bookservice = ctx.getBean(BookService.class);
    }

    @Test
    public void testGetAllBooks()
    {
        List<Book> books = bookservice.getAllBooks();
        for (Book book : books)
        {
            System.out.println(book);
        }
        assertNotNull(books);
    }

    @Test
    public void testAdd()
    {
        Book entity = new Book(0, "Hibernate 第七版", 78.1, new Date());
        try
        {
            assertEquals(1, bookservice.add(entity));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void testDeleteInt()
    {
        assertEquals(1, bookservice.delete(9));
    }

    @Test
    public void testDeleteStringArray()
    {
        String[] ids = {"7", "11", "12"};
        assertEquals(3, bookservice.delete(ids));
    }

    @Test
    public void testUpdate()
    {
        Book entity = new Book(7, "Hibernate 第二版", 79.1, new Date());
        try
        {
            assertEquals(1, bookservice.update(entity));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetBookById()
    {
        assertNotNull(bookservice.getBookById(1));
    }

    @Test
    public void testAddDouble()
    {
        //因为书名相同，添加第二本会失败，用于测试事务
        Book entity1 = new Book(0, "Hibernate 第八版", 78.1, new Date());
        assertEquals(2, bookservice.add(entity1));
    }
}
package com.xiely.web.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.List;

import com.xiely.web.sql.entities.Book;
import com.xiely.web.sql.mapper.BookDAO;


@Service
public class BookService
{
    @Resource
    BookDAO bookdao;

    public List<Book> getAllBooks()
    {
        return bookdao.getAllBooks();
    }

    public Book getBookById(int id)
    {
        return bookdao.getBookById(id);
    }

    public int add(Book entity)
    {
        if (StringUtils.isEmpty(entity.getTitle()))
        {
            return -1;
        }
        return bookdao.add(entity);
    }

    @Transactional
    public int add(List<Book> books)
    {
        for (Book book : books)
        {
            if (bookdao.add(book) != 1)
            {
                return -1;
            }
        }
        return 1;
    }

    public int delete(int id)
    {
        return bookdao.delete(id);
    }

    /**
     * 多删除
     */
    public int delete(String[] ids)
    {
        int rows = 0;
        for (String idStr : ids)
        {
            int id = Integer.parseInt(idStr);
            rows += delete(id);
        }
        return rows;
    }

    public int update(Book entity)
    {
        return bookdao.update(entity);
    }

}
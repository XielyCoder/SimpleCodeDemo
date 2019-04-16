package com.xiely.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.xiely.web.service.BookService;
import com.xiely.web.sql.entities.Book;


@RestController
@RequestMapping("/BookController")
public class BookController
{
    @Autowired
    BookService bookService;

    @ResponseBody
    @GetMapping(value = "/getAllBooks.do", produces = "application/json;charset=UTF-8")
    public List<Book> getAllBooks()
    {
        return bookService.getAllBooks();
    }

    @ResponseBody
    @PostMapping(value = "/add.do", produces = "application/json;charset=UTF-8")
    public int test(Book book)
    {
        return bookService.add(book);
    }
}

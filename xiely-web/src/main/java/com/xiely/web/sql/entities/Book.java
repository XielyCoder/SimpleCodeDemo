package com.xiely.web.sql.entities;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 图书实体
 */
@Data
public class Book implements Serializable
{
    /**
     * 编号
     */
    private int id;
    /**
     * 书名
     */
    private String title;
    /**
     * 价格
     */
    private double price;
    /**
     * 出版日期
     */
    private Date publishDate;

    public Book(int id, String title, double price, Date publishDate)
    {
        this.id = id;
        this.title = title;
        this.price = price;
        this.publishDate = publishDate;
    }

    public Book()
    {
    }
}

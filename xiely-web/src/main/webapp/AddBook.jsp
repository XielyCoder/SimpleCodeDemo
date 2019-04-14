<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="styles/main.css" type="text/css" rel="stylesheet" />
<title>新增图书</title>
</head>
<body>
    <div class="main">
        <h2 class="title"><span>新增图书</span></h2>
        <form action="BookController.do?act=AddBookPost" method="post">
        <fieldset>
            <legend>图书</legend>
            <p>
                <label for="title">图书名称：</label>
                <input type="text" id="title" name="title"  value="${model.title}"/>
            </p>
            <p>
                <label for="title">图书价格：</label>
                <input type="text" id="price" name="price" value="${model.price}"/>
            </p>
            <p>
                <label for="title">出版日期：</label>
                <input type="text" id="publishDate" name="publishDate"  value="${model.publishDate}"/>
            </p>
            <p>
              <input type="submit" value="保存" class="btn">
            </p>
        </fieldset>
        </form>
        <p style="color: red">${message}</p>
        <p>
            <a href="BookController.do?act=ListBook" class="abtn">返回列表</a>
        </p>
    </div>
</body>
</html>
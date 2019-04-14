<%@ page contentType="text/html;charset=utf-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title></title>
    <link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
    <script src="js/bootstrap.min.js"></script>
    <script src="js/jquery-3.3.1.min.js"></script>
    <script type="text/javascript">
        $(function () {
            $.ajax({
                url: "/web/BookController/getAllBooks.do",
                type: "GET",
                dataType: "json",
                success: function (data) {
                    $.each(data, function (index, item) {
                        var tr = $("<tr/>");
                        $("<td/>").html(item["title"]).appendTo(tr);
                        $("<td/>").html(item["price"]).appendTo(tr);
                        $("<td/>").html(item["publishDate"]).appendTo(tr);
                        $("<button id ='d' onclick='del(" + item["Id"] + ")'>").html("删除").appendTo(tr);
                        $("<button id ='u' onclick='update(" + item["Id"] + ")'>").html("更新").appendTo(tr);
                        tr.appendTo("#tab");
                    });
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    alert(XMLHttpRequest + "," + textStatus + "," + errorThrown);
                }
            });

        });

        function add (title, price, publishDate) {
            $.ajax({
                url: "/TsetWeb.asmx/SetUserJson",
                data: "{name:'李六',phone:'13727713819'}",
                contentType: 'application/json',
                dataType: "json",
                type: "post",
                success: function (data) {
                    var a = eval("(" + data.d + ")"); //后台返回是字符串，所以转换为json对象
                    alert(a);//返回1表示成功
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    alert(XMLHttpRequest + "," + textStatus + "," + errorThrown);
                }
            });
        }

        function del(id) {
            $.ajax({
                url: "/TsetWeb.asmx/DelUserJson",
                type: "Post",
                data: { "id": id },
                dataType: "json",
                success: function (data) {
                    var a = eval("(" + data.d + ")"); //后台返回是字符串，所以转换为json对象
                    alert(a);//返回1表示成功
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    alert(XMLHttpRequest + "," + textStatus + "," + errorThrown);
                }
            });
        }

        function updata(id) {
            $.ajax({
                url: "/TsetWeb.asmx/Update",
                type: "Post",
                data: { "id": id, "name": '九九', "phone": '15927713819' },
                dataType: "json",
                success: function (data) {
                    var a = eval("(" + data.d + ")"); //后台返回是字符串，所以转换为json对象
                    alert(a);//返回1表示成功
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    alert(XMLHttpRequest + "," + textStatus + "," + errorThrown);
                }
            });
        }
    </script>
</head>
<body>
    <div>
        <table id="tab">
            <tr>
                <th>产品名称</th>
                <th>产品品牌</th>
				<th>ASIN</th>
                <th>SKU</th>
            </tr>
        </table>
        <button id="add" onclick="add()">add</button>
    </div>

</body>
</html>
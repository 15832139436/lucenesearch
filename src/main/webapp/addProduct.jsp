<%@page pageEncoding="UTF-8" contentType="text/html; UTF-8" isELIgnored="false" %>
<html>
<head >

    <meta http-equiv="content-type" content="text/html;charset=UTF-8" >

</head>
<body>


<h1>添加商品</h1>
<form action="${pageContext.request.contextPath}/product/add" method="post" enctype="multipart/form-data">
    商品名称:<input type="text" name="name"><br/>
    商品价格:<input type="text" name="price"><br/>
    商品描述:<input type="text" name="description"><br/>
    商品图片:<input type="file" name="picture"><br/>
    商品状态:<input type="text" name="status"><br/>
    商品生产日期:<input type="date" name="uptime"><br/>
    商品产地:<input type="text" name="city"><br/>
    <input type="submit" value="提交"><br/>
</form>


</body>
</html>

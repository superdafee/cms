<%--
  Created by IntelliJ IDEA.
  User: dafee
  Date: 14-8-15
  Time: 下午10:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Bootstrap 101 Template</title>

    <!-- Bootstrap -->
    <link href="${ctx}/static/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<h1>你好，世界！</h1>
<form role="form">
    <div class="form-group">
        <label for="exampleInputEmail1">家长姓名</label>
        <input type="email" class="form-control" id="exampleInputEmail1" placeholder="请填写您的称呼">
    </div>
    <div class="form-group">
        <label for="exampleInputPassword1">移动电话</label>
        <input type="password" class="form-control" id="exampleInputPassword1" placeholder="请填写您的手机号码">
    </div>

    <div class="form-group">
        <label for="areaSel">所在区域</label>
        <select class="form-control" id="areaSel">
            <option value="">请选择小学所在区</option>
            <option value="1">裕华区</option>
            <option value="2">长安区</option>
        </select>
    </div>
    <div class="form-group">
        <label for="schoolSel">所在学校</label>
        <select class="form-control" id="schoolSel">
            <option value="">请选择小学所在区</option>
            <option value="1">裕华区</option>
            <option value="2">长安区</option>
        </select>
    </div>
    <div class="form-group">
        <label for="exampleInputFile">File input</label>
        <input type="file" id="exampleInputFile">
        <p class="help-block">Example block-level help text here.</p>
    </div>
    <div class="checkbox">
        <label>
            <input type="checkbox"> Check me out
        </label>
    </div>
    <button type="submit" class="btn btn-default">Submit</button>
</form>
<input type="button" value="back" onclick="history.go(-1)" />
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="js/bootstrap.min.js"></script>
</body>
</html>
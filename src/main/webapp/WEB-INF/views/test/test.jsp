<%--
  Created by IntelliJ IDEA.
  User: dafee
  Date: 14-8-15
  Time: 下午4:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
    <title></title>
    <link href="${ctx}/static/bootstrap/3.2.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
</head>
<body>
    <div style="width: 100%; padding: 5% 5% 0 5%">
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
                    <option value="">请选择学校名称</option>
                    <option value="1">师大附小</option>
                    <option value="2">草场街小学</option>
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
        <a href="list1">mytest</a>
    </div>
</body>
</html>
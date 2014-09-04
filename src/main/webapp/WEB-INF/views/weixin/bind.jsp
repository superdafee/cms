<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <title>账号绑定</title>
    <link href="${ctx}/static/bootstrap/3.2.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
    <script src="${ctx}/static/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
</head>
<body>
<div style="width: 100%; padding: 1% 1% 0 1%">
<div class="panel panel-default">
    <!-- Default panel contents -->
    <div class="panel-heading" style="background-color: #dddddd">填写并保存信息完成您的绑定</div>

    <div class="panel-body">

        <form role="form">
            <%--OPENID:${openid}--%>
            <input type="hidden" value="${openid}" name="openid" />

            <div class="form-group">
                <label for="exampleInputEmail1">手机号码</label>
                <input type="email" class="form-control" id="exampleInputEmail1" placeholder="输入手机号码">
            </div>
            <button type="submit" class="btn btn-default">下一步</button>
        </form>

    </div>

</div>
</div>
<script type="text/javascript">

    function e(e) {
        typeof window.WeixinJSBridge != "undefined" && WeixinJSBridge.invoke("imagePreview", {
            current: e,
            urls: n
        });
    }
    function t() {
        var t = document.getElementById("img-content");
        t = t ? t.getElementsByTagName("img") : [];
        for (var r = 0, i = t.length; r < i; r++) {
            var s = t.item(r), o = s.getAttribute("data-src") || s.getAttribute("src");
            o && (!!s.dataset && !!s.dataset.s, n.push(o), function(t) {
                s.addEventListener ? s.addEventListener("click", function() {
                    e(t);
                }, !1) : s.attachEvent && s.attachEvent("click", function() {
                    e(t);
                }, !1);
            }(o));
        }
    }
    var n = [];
    window.addEventListener ? window.addEventListener("load", t, !1) : window.attachEvent && (window.attachEvent("load", t), window.attachEvent("onload", t));

</script>
</body>
</html>
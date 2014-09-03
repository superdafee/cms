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
                <label for="exampleInputEmail1">所在学校</label>
                <select class="form-control" style="margin-bottom: 2px">
                    <option>河北省</option>
                </select>
                <select class="form-control" style="margin-bottom: 2px">
                    <option>石家庄市</option>
                </select>
                <select class="form-control" name="region" style="margin-bottom: 2px" id="regionSel">
                    <option value="130102">长安区</option>
                    <option value="130103">桥东区</option>
                    <option value="130104">桥西区</option>
                    <option value="130105">新华区</option>
                    <option value="130108">裕华区</option>
                    <option value="13010801">开发区</option>
                    <option value="130127">高邑县</option>
                    <option value="130184">新乐市</option>
                    <option value="130185">鹿泉市</option>
                </select>
                <select class="form-control" name="schoolid" id="schoolSel">
                    <c:forEach items="${schoolList}" var="school">
                        <option value="${school.id}">${school.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label for="exampleInputEmail1">Email address</label>
                <input type="email" class="form-control" id="exampleInputEmail1" placeholder="Enter email">
            </div>
            <div class="form-group">
                <label for="exampleInputPassword1">Password</label>
                <input type="password" class="form-control" id="exampleInputPassword1" placeholder="Password">
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

    $(function(){
        $("#regionSel").change(function(){
            $("#schoolSel").empty();
            $.post("${ctx}/weixin/user/fetchSchool",{region:$("#regionSel").val()},function(data){
                if(data){
                    $(data).each(function(i){
                        $("<option value='"+this.id+"'>"+this.name+"</option>").appendTo("#schoolSel");
                    });
                }
            },"json");
        });
    });

</script>
</body>
</html>
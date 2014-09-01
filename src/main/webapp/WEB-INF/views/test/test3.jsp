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
<div style="width: 100%; padding: 1% 1% 0 1%" id="img-content">
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <%--<div class="panel-heading" style="background-color: #dddddd">《xxx练习册》 第一章——第1页</div>--%>
        <div class="panel-body">
            <div class="media">
                <a class="pull-left" href="#">
                    <img class="media-object" data-src="http://y1.ifengimg.com/7c7ccbd3890051bf/2012/0618/rdn_4fdeed7edca1d.jpg"
                         src="${ctx}/static/test/preview.jpg"
                         alt="..." style="border: 1px #dddddd solid">
                </a>
                <div class="media-body" style="border: 0px #dddddd solid;text-align: center">
                    <%--<h4 class="media-heading">Media heading</h4>--%>
                        <h5 class="media-heading">《语文课课练六年级(下)》</h5>
                        <p>
                            河北教育出版社
                        </p>
                        <p>
                            第一章——第1页
                        </p>
                </div>
            </div>
        </div>

        <div class="list-group">
            <a href="javascript:void(0)" class="list-group-item" style="background-color: #dddddd">
                视频讲解
            </a>
            <a href="#" class="list-group-item">
                <span class="glyphicon glyphicon-film"></span>
                牛吃草
            </a>
            <a href="#" class="list-group-item">
                <span class="glyphicon glyphicon-film"></span>
                路程问题版本一
            </a>
            <a href="#" class="list-group-item">
                <span class="glyphicon glyphicon-film"></span>
                路程问题版本二
            </a>
        </div>

        <%--<!-- Table -->--%>
        <%--<table class="table">--%>
        <%--<th>--%>
        <%--<td>题号</td>--%>
        <%--<td>题目</td>--%>
        <%--<td>操作</td>--%>
        <%--</th>--%>
        <%--<tbody>--%>
        <%--<tr>--%>
        <%--<td>2</td>--%>
        <%--<td>题目</td>--%>
        <%--<td>操作</td>--%>
        <%--</tr>--%>
        <%--<tr>--%>
        <%--<td>7</td>--%>
        <%--<td>题目</td>--%>
        <%--<td>操作</td>--%>
        <%--</tr>--%>
        <%--</tbody>--%>
        <%--</table>--%>
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
            var s = t.item(r), o = s.getAttribute("data-src") ;
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
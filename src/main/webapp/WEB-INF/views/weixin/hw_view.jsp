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
    <title></title>
    <link href="${ctx}/static/bootstrap/3.2.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
</head>
<body>
<div style="width: 100%; padding: 1% 1% 0 1%" id="img-content">
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading" style="background-color: #dddddd"><button type="button" class="btn btn-danger" onclick="history.go(-1)">返回</button>《一课一练》 第一章—第1页</div>
        <div class="panel-body">
            <p style="text-align: center">
                标准解题答案参考
                <img width="100%" data-src="http://y1.ifengimg.com/7c7ccbd3890051bf/2012/0618/rdn_4fdeed7edca1d.jpg" src="${ctx}/static/test/preview1.jpg">
            </p>
        </div>

        <div class="list-group">
            <a href="javascript:void(0)" class="list-group-item" style="background-color: #dddddd">
                重点试题讲解
            </a>
            <c:choose>
                <c:when test="${course == 1}">
                    <a href="${ctx}/weixin/hw/video/1" class="list-group-item">
                        <span class="glyphicon glyphicon-film"></span>
                        榨油问题
                    </a>
                    <a href="${ctx}/weixin/hw/video/2" class="list-group-item">
                        <span class="glyphicon glyphicon-film"></span>
                        两桶水
                    </a>
                    <a href="${ctx}/weixin/hw/video/3" class="list-group-item">
                        <span class="glyphicon glyphicon-film"></span>
                        裁衣服
                    </a>
                </c:when>
                <c:when test="${course == 2}">
                    <a href="${ctx}/weixin/hw/video/4" class="list-group-item">
                        <span class="glyphicon glyphicon-film"></span>
                        修改病句
                    </a>
                    <a href="${ctx}/weixin/hw/video/5" class="list-group-item">
                        <span class="glyphicon glyphicon-film"></span>
                        卜算子++咏梅++（毛泽东）
                    </a>
                    <a href="${ctx}/weixin/hw/video/6" class="list-group-item">
                        <span class="glyphicon glyphicon-film"></span>
                        《哲学家的最后一课》
                    </a>
                </c:when>
                <c:otherwise>
                    <a href="#" class="list-group-item">
                        暂无视频
                    </a>
                </c:otherwise>
            </c:choose>

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
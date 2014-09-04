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
<div style="width: 100%; padding: 1% 1% 0 1%">
<div class="panel panel-default">
    <!-- Default panel contents -->
    <div class="panel-heading" style="background-color: #dddddd">《一课一练》</div>
    <div class="panel-body">
        <div class="media">
            <a class="pull-left" href="#">
                <img class="media-object" src="${ctx}/static/test/cover.png" alt="...">
            </a>
            <div class="media-body">
                <%--<h4 class="media-heading">Media heading</h4>--%>
                    已更新至78页
                    <div class="progress">
                        <div class="progress-bar progress-bar-success progress-bar-striped" role="progressbar" aria-valuenow="78" aria-valuemin="0" aria-valuemax="186" style="width: ${100*78/186}%" >
                            42%
                        </div>
                    </div>
            </div>
        </div>


    </div>

    <ul class="list-group">
        <li class="list-group-item" style="background-color: #dddddd">页码选择 <span style="font-size: 10px">蓝色为已阅览页，如</span> <span class="label label-info">1</span></li>
        <li class="list-group-item">
            <h5>第一章</h5>
            <p>
                <button type="button" class="btn btn-info" style="margin-bottom: 3px; width: 19%" onclick="window.location.href='${ctx}/weixin/hw/view/${course}'">1</button>
                <button type="button" class="btn btn-info" style="margin-bottom: 3px; width: 19%" onclick="window.location.href='${ctx}/weixin/hw/view/${course}'">2</button>
                <button type="button" class="btn btn-default" style="margin-bottom: 3px; width: 19%" onclick="window.location.href='${ctx}/weixin/hw/view/${course}'">3</button>
                <button type="button" class="btn btn-default" style="margin-bottom: 3px; width: 19%" onclick="window.location.href='${ctx}/weixin/hw/view/${course}'">4</button>
                <button type="button" class="btn btn-default" style="margin-bottom: 3px; width: 19%" onclick="window.location.href='${ctx}/weixin/hw/view/${course}'">5</button>
                <button type="button" class="btn btn-default" style="margin-bottom: 3px; width: 19%" onclick="window.location.href='${ctx}/weixin/hw/view/${course}'">6</button>
                <button type="button" class="btn btn-default" style="margin-bottom: 3px; width: 19%" onclick="window.location.href='${ctx}/weixin/hw/view/${course}'">7</button>
            </p>
            <h5>第二章</h5>
            <p>
                <button type="button" class="btn btn-info" style="margin-bottom: 3px; width: 19%" onclick="window.location.href='${ctx}/weixin/hw/view/${course}'">8</button>
                <button type="button" class="btn btn-default" style="margin-bottom: 3px; width: 19%" onclick="window.location.href='${ctx}/weixin/hw/view/${course}'">9</button>
                <button type="button" class="btn btn-default" style="margin-bottom: 3px; width: 19%" onclick="window.location.href='${ctx}/weixin/hw/view/${course}'">10</button>
                <button type="button" class="btn btn-default" style="margin-bottom: 3px; width: 19%" onclick="window.location.href='${ctx}/weixin/hw/view/${course}'">11</button>
                <button type="button" class="btn btn-default" style="margin-bottom: 3px; width: 19%" onclick="window.location.href='${ctx}/weixin/hw/view/${course}'">12</button>
                <button type="button" class="btn btn-default" style="margin-bottom: 3px; width: 19%" onclick="window.location.href='${ctx}/weixin/hw/view/${course}'">13</button>
                <button type="button" class="btn btn-default" style="text-align:center;margin-bottom: 3px; width: 19%" onclick="window.location.href='${ctx}/weixin/hw/view/${course}'">14</button>
            </p>
            <h5>第三章</h5>
            <p>
                <button type="button" class="btn btn-default" style="margin-bottom: 3px; width: 19%" onclick="window.location.href='${ctx}/weixin/hw/view/${course}'">15</button>
                <button type="button" class="btn btn-default" style="margin-bottom: 3px; width: 19%" onclick="window.location.href='${ctx}/weixin/hw/view/${course}'">16</button>
                <button type="button" class="btn btn-default" style="margin-bottom: 3px; width: 19%" onclick="window.location.href='${ctx}/weixin/hw/view/${course}'">17</button>
                <button type="button" class="btn btn-default" style="margin-bottom: 3px; width: 19%" onclick="window.location.href='${ctx}/weixin/hw/view/${course}'">18</button>
                <button type="button" class="btn btn-default" style="margin-bottom: 3px; width: 19%" onclick="window.location.href='${ctx}/weixin/hw/view/${course}'">19</button>
                <button type="button" class="btn btn-default" style="margin-bottom: 3px; width: 19%" onclick="window.location.href='${ctx}/weixin/hw/view/${course}'">20</button>
                <button type="button" class="btn btn-default" style="margin-bottom: 3px; width: 19%" onclick="window.location.href='${ctx}/weixin/hw/view/${course}'">21</button>
            </p>
        </li>
    </ul>

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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: dafee
  Date: 14-8-15
  Time: 下午4:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
    <title>${qname}</title>
    <link href="${ctx}/static/bootstrap/3.2.0/css/bootstrap.min.css" type="text/css" rel="stylesheet"/>
    <script src="${ctx}/static/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
    <script src="${ctx}/static/bootstrap/3.2.0/js/bootstrap.min.js" type="text/javascript"></script>
</head>
<body>
<div style="width: 100%; padding: 1% 1% 0 1%" id="img-content">
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading" style="background-color: #dddddd">
            <button type="button" class="btn btn-danger" onclick="history.go(-1)">返回</button>
        </div>
        <div class="panel-body">
            <p style="font-size: 12pt">
                一个两位小数精确到十分位是5.0,这个数最小是[    ]。
                <div class="radio">
                    <label>
                        <input type="radio" name="optionsRadios" id="optionsRadios1" value="option1" checked>
                        4.99
                    </label>
                </div>
                <div class="radio">
                    <label>
                        <input type="radio" name="optionsRadios" id="optionsRadios2" value="option2">
                        5.1
                    </label>
                </div>
                <div class="radio">
                    <label>
                        <input type="radio" name="optionsRadios" id="optionsRadios3" value="option3">
                        4.94
                    </label>
                </div>
                <div class="radio">
                    <label>
                        <input type="radio" name="optionsRadios" id="optionsRadios4" value="option3">
                        4.95
                    </label>
                </div>
            </p>
            <%--<button type="button" class="btn btn-primary">确定</button>--%>
            <div style="text-align: center">
                <!-- Button trigger modal -->
                <button class="btn btn-success" data-toggle="modal" data-target="#myModal" style="width: 100px">
                    确定
                </button>
            </div>

            <!-- Modal -->
            <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                            <%--<h4 class="modal-title" id="myModalLabel"></h4>--%>
                        </div>
                        <div class="modal-body">
                            <p>
                                <div id="feedback" style="padding: 5px 0px;text-align: center">

                                </div>
                                正确答案为：4.95
                            </p>
                        </div>
                        <div class="modal-footer">
                            <%--<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>--%>
                            <button type="button" class="btn btn-default" data-dismiss="modal" onclick="history.go(-1)">知道啦</button>
                        </div>
                    </div>
                </div>
            </div>
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
            var s = t.item(r), o = s.getAttribute("data-src");
            o && (!!s.dataset && !!s.dataset.s, n.push(o), function (t) {
                s.addEventListener ? s.addEventListener("click", function () {
                    e(t);
                }, !1) : s.attachEvent && s.attachEvent("click", function () {
                    e(t);
                }, !1);
            }(o));
        }
    }
    var n = [];
    window.addEventListener ? window.addEventListener("load", t, !1) : window.attachEvent && (window.attachEvent("load", t), window.attachEvent("onload", t));
    $('#myModal').on('show.bs.modal', function (e) {
        if($("#optionsRadios4").is(":checked")){
            $("#feedback").removeClass();
            $("#feedback").addClass("bg-success");
            $("#feedback").html("<h4><span class='label label-success'>恭喜你，回答正确！</span></h4>");
        }else{
            $("#feedback").removeClass();
            $("#feedback").addClass("bg-danger");
            $("#feedback").html("<h4><span class='label label-danger'>很遗憾，回答错误！</span></h4>");
        }
    })
</script>
</body>
</html>
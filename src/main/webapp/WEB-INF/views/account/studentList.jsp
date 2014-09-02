<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>学生管理</title>
    <script src="${ctx}/static/jquery/jquery.form.js" type="text/javascript"></script>
    <script>
        <!--
        function showModal(id) {
            $("#deleteId").val(id);
            $("#deleteFlg").val("1");
            $('#myModal').modal('show');
        }

        function confirmDelete() {
            if ($("#deleteFlg").val() == "1") {
                window.location.href = "${ctx}/student/delete/" + $("#deleteId").val() + "?page=" + $('#pageNumber').val() + "&sortType=${sortType}&${searchParams}";
            } else if ($("#deleteFlg").val() == "2") {
                window.location.href = "${ctx}/student/deleteBySelected/" + $("#deleteId").val() + "?page=" + $('#pageNumber').val() + "&sortType=${sortType}&${searchParams}";
            }
            $("#deleteFlg").val("");
        }

        function refreshPage() {
            $("#searchForm").submit();
        }

        function checkAndShowModal() {
            var selectedIds=$("input[name='studentId']:checked");
            if($(selectedIds).size()==0){
                alert("请选择需要删除的记录！");
                return;
            } else {
                var deleteIds = "";
                for(i = 0; i < selectedIds.length; i++){
                    if (i==0) {
                        deleteIds = selectedIds[i].value;
                    } else {
                        deleteIds = deleteIds+","+selectedIds[i].value;
                    }
                }
                $("#deleteId").val(deleteIds);
                $("#deleteFlg").val("2");
                $('#myModal').modal('show');
            }
        }

        function openUploadDialog() {
            $('#uploadModal').modal('show');
        }

        $(document).ready(function() {

            $('#uploadModal').modal({
                backdrop: "static",
                keyboard: true,
                show: false
            });

            var options = {
                target:        '#uploadModal',
                beforeSubmit:  showRequest,
                success:       showResponse
            };
            $('#uldv').ajaxForm(options);
        });

        function showRequest(formData, jqForm, options){
            if ($('#batchfd').val()==""){
                alert("请先选择上传Excel文件!");
                return false;
            }

            var filename = $("#batchfd").val();
            if (((/[.]/.exec(filename)) ? /[^.]+$/.exec(filename.toLowerCase()) : '')!="xls") {
                alert("批量导入的学生信息文件类型为Excel 2003格式！");
                return false;
            }

            $('#uploadModal').modal('hide');
            uploading();
            return true;
        }

        function showResponse(responseText, statusText) {
            $("#uploadMsg").html();
            if (responseText == null || responseText == "") {
                $("#uploadSuccessFlg").val("1");
                setInterval("refreshPage()", 1000);
            } else {
                $('#uploadMsg').addClass("alert alert-error");
                var dataArr = responseText.split("！");
                $('#uploadMsg').html("<p>批量导入错误信息：</p>");
                for (var i = 0; i < dataArr.length; i++) {
                    $('#uploadMsg').html($('#uploadMsg').html()+ dataArr[i] + '<br/>');
                }
            }
        }

        function uploading() {
            $('#uploadMsg').html("<div class=\"progress progress-striped active\"><div class=\"bar\" style=\"width: 100%;\"></div></div>");
            $('#rsModal').modal('show');
        }
        //-->
    </script>
</head>

<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
    <form id="searchForm" class="form-search" action="list">
	<div class="row">
        <div style="float: left"><a class="btn" href="${ctx}/student/add">新建学生</a>
            <a class="btn" href="javascript:void(0)" onclick="checkAndShowModal()">批量删除</a>
            <a class="btn" href="javascript:void(0)" onclick="openUploadDialog()">批量导入</a></div>
        <input type="hidden" id="uploadSuccessFlg" name="uploadSuccessFlg" />
        <div style="float: right">

				<label>姓名：</label> <input type="text" name="search_LIKE_realname" class="input-medium" value="${searchName}">
				<button type="submit" class="btn" id="search_btn">Search</button>

	    </div>
	    <%--<tags:sort/>--%>
	</div>
	<br/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th style="text-align: center;"><input type="checkbox" name="all" onclick="toggleCheckBox($(this),'studentId');"/></th><th>姓名</th><th>年级</th><th>班级</th><th>创建时间</th><th>操作</th></tr></thead>
		<tbody>
		<c:forEach items="${students.content}" var="student">
			<tr>
                <td style="text-align: center;width:36px"><input type="checkbox" name="studentId" value="${student.id}"/></td>
				<td>${student.realname}</td>
				<td>${student.grade}年级</td>
                <td>${student.classname}</td>
                <td><fmt:formatDate value="${student.createtime}" pattern="yyyy-MM-dd"/></td>
                <td><a href="${ctx}/student/update/${student.id}">修改</a>&nbsp;|&nbsp;<a href="javascript:void(0)" onclick="showModal(${student.id})">删除</a></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<tags:pagination page="${students}" paginationSize="20"/>
    </form>

    <div class="modal hide fade" id="myModal">
        <div id="main_title"><a class="close" data-dismiss="modal">×</a>管理系统</div>
        <div class="modal-body">
            <p>确认要删除所选记录么？</p>
        </div>
        <div class="modal-footer">
            <input type="button"  class="btn btn-primary" id="deleteBtn" onclick="confirmDelete()" value="删除" />
            <input type="button"  class="btn" data-dismiss="modal" value="关闭" />

            <input type="hidden" id="deleteId"/>
            <input type="hidden" id="deleteFlg"/>
        </div>
    </div>
    <form id="uldv" method="post" enctype="multipart/form-data" action="${ctx}/student/uploadStudent">
        <div class="modal hide fade" id="uploadModal">
            <div id="main_title"><a class="close" data-dismiss="modal">×</a>管理系统</div>
            <div class="modal-body">
                <p>下载批量导入模板：<input class="btn btn-primary" onclick="javascript:location.href='${ctx}/student/downloadTpl'" type="button" value="模版下载"/></p>
                <hr>
                <p>批量导入学生信息：<input type="file" id="batchfd" name="fileData"/></p>
            </div>
            <div class="modal-footer">
                <input class="btn btn-primary" id="submit_btn" type="submit" value="批量导入"/>
                <input type="button"  class="btn" data-dismiss="modal" value="关闭" />
            </div>
        </div>
    </form>
    <div class="modal hide fade" id="rsModal">
        <div id="main_title"><a class="close" onclick="refreshPage()">×</a>批量导入结果</div>
        <div class="modal-body">
            <div id="uploadMsg" ></div>
        </div>
        <div class="modal-footer">
            <input type="button" class="btn_com_b" onclick="refreshPage()" value="关闭"/>
        </div>
    </div>
</body>
</html>

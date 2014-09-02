<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>家长管理</title>
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
                window.location.href = "${ctx}/parent/delete/" + $("#deleteId").val() + "?page=" + $('#pageNumber').val() + "&sortType=${sortType}&${searchParams}";
            } else if ($("#deleteFlg").val() == "2") {
                window.location.href = "${ctx}/parent/deleteBySelected/" + $("#deleteId").val() + "?page=" + $('#pageNumber').val() + "&sortType=${sortType}&${searchParams}";
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

        //-->
    </script>
</head>

<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
    <form id="searchForm" class="form-search" action="list">
	<div class="row">
        <div style="float: left">
            <a class="btn" href="javascript:void(0)" onclick="checkAndShowModal()">批量删除</a></div>
		<div style="float: right">
				<label>姓名：</label> <input type="text" name="search_LIKE_realname" class="input-medium" value="${searchName}">
				<button type="submit" class="btn" id="search_btn">Search</button>
	    </div>
	    <%--<tags:sort/>--%>
	</div>
        <br/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th style="text-align: center;"><input type="checkbox" name="all" onclick="toggleCheckBox($(this),'parentId');"/></th>
            <th>姓名</th><th>手机号码</th><th>其它电话号码</th><th>创建时间</th><th>操作</th></tr></thead>
		<tbody>
		<c:forEach items="${parents.content}" var="parent">
			<tr>
                <td style="text-align: center;width:36px"><input type="checkbox" name="parentId" value="${parent.id}"/></td>
				<td>${parent.realname}</td>
				<td>${parent.mobile}</td>
                <td>${parent.phone}</td>
                <td><fmt:formatDate value="${parent.createtime}" pattern="yyyy-MM-dd"/></td>
                <td><a href="${ctx}/parent/update/${student.id}">修改</a>&nbsp;|&nbsp;<a href="javascript:void(0)" onclick="showModal(${parent.id})">删除</a></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<tags:pagination page="${parents}" paginationSize="20"/>
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

</body>
</html>

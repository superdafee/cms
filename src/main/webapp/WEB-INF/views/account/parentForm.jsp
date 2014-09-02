<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>家长管理</title>
    <script>
        <!--
        $(document).ready(function() {
            //为inputForm注册validate函数
            $("#inputForm").validate();
        });
        //-->
    </script>
</head>

<body>
	<form id="inputForm" action="${ctx}/parent/${action}" method="post" class="form-horizontal">
        <input type="hidden" name="id" id="hidParentId" value="${parent.id}"/>
        <input type="hidden" name="searchParams" value="${searchParams}"/>
        <input type="hidden" name="sortType" value="${sortType}"/>
        <input type="hidden" name="page" value="${page}"/>
		<fieldset>
            <legend><small>家长信息</small></legend>
                <div class="control-group">
                    <label for="parentsName" class="control-label">家长姓名:<span style="color: red;font-size: 16px">&nbsp;&nbsp;*</span></label>
                    <div class="controls">
                        <input type="text" id="parentsName" name="parentsName" class="input-large" value="${parent.realname}" maxlength="20"/>
                    </div>
                </div>
                <div class="control-group">
                    <label for="parentMobile" class="control-label">手机号:</label>
                    <div class="controls" id="mobileDiv">
                        <div>
                            <input type="text" id="parentMobile" name="parentMobile" class="input-large" value="${parent.mobile}"/>
                        </div>
                    </div>
                </div>
                <div class="control-group">
                    <label for="parentPhone" class="control-label">固定电话:</label>
                    <div class="controls" id="phoneDiv">
                        <div>
                            <input type="text" id="parentPhone" name="parentPhone" class="input-large" value="${parent.phone}"/>
                        </div>
                    </div>
                </div>
            <div class="form-actions">
                <input id="submit_btn" class="btn btn-primary" type="submit" value="提交"/>&nbsp;
                <input id="cancel_btn" class="btn" type="button" value="返回" onclick="history.back()"/>
            </div>
		</fieldset>
	</form>

</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>学生管理</title>
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
	<form id="inputForm" action="${ctx}/student/${action}" method="post" class="form-horizontal">
        <input type="hidden" name="id" id="hidStudentId" value="${student.id}"/>
        <input type="hidden" name="searchParams" value="${searchParams}"/>
        <input type="hidden" name="sortType" value="${sortType}"/>
        <input type="hidden" name="page" value="${page}"/>
		<fieldset>
            <legend><small>学生信息</small></legend>
			<div class="control-group">
				<label for="student_realname" class="control-label">姓名:<span style="color: red;font-size: 16px">&nbsp;&nbsp;*</span></label>
				<div class="controls">
					<input type="text" id="student_realname" name="realname"  value="${student.realname}" class="input-large required" maxlength="20"/>
				</div>
			</div>	
			<div class="control-group">
				<label for="student_grade" class="control-label">年级:<span style="color: red;font-size: 16px">&nbsp;&nbsp;*</span></label>
				<div class="controls">
                    <input type="text" id="student_grade" name="grade" class="input-large required" value="${student.grade}"/>
				</div>
			</div>
            <div class="control-group">
                <label for="student_classname" class="control-label">班级:<span style="color: red;font-size: 16px">&nbsp;&nbsp;*</span></label>
                <div class="controls">
                    <input type="text" id="student_classname" name="classname"  value="${student.classname}" class="input-large required" maxlength="20"/>
                </div>
            </div>
            <legend><small>家长信息</small></legend>
            <c:if test="${not empty student.parentList}">
                <input type="hidden" id="parentStudentId" name="parentStudentId" class="input-large" value="${parentStudent.id}"/>
                <div class="control-group">
                    <label for="parentsName1" class="control-label">家长姓名:<span style="color: red;font-size: 16px">&nbsp;&nbsp;*</span></label>
                    <div class="controls">
                        <input type="text" id="parentsName1" name="parentsName" class="input-large" value="${parentStudent.parent.realname}" maxlength="20"/>
                    </div>
                </div>
                <div class="control-group">
                    <label for="relationship1" class="control-label">关系:</label>
                    <div class="controls">
                        <select id="relationship1" name="relation" style="width: 140px">
                            <option value="1" ${parentStudent.relation == 1 ? 'selected':''}>父亲</option>
                            <option value="2" ${parentStudent.relation == 2 ? 'selected':''}>母亲</option>
                            <option value="3" ${parentStudent.relation == 3 ? 'selected':''}>祖父</option>
                            <option value="4" ${parentStudent.relation == 4 ? 'selected':''}>祖母</option>
                            <option value="5" ${parentStudent.relation == 5 ? 'selected':''}>外祖父</option>
                            <option value="6" ${parentStudent.relation == 6 ? 'selected':''}>外祖母</option>
                            <option value="7" ${parentStudent.relation == 7 ? 'selected':''}>其他</option>
                        </select>
                    </div>
                </div>
                <div class="control-group">
                    <label for="parentMobile1" class="control-label">手机号:</label>
                    <div class="controls" id="mobileDiv1">
                        <div>
                            <input type="text" id="parentMobile1" name="parentMobile" class="input-large" value="${parentStudent.parent.mobile}"/>
                        </div>
                    </div>
                </div>
                <div class="control-group">
                    <label for="parentPhone1" class="control-label">固定电话:</label>
                    <div class="controls" id="phoneDiv1">
                        <div>
                            <input type="text" id="parentPhone1" name="parentPhone" class="input-large" value="${parentStudent.parent.phone}"/>
                        </div>
                    </div>
                </div>
            </c:if>
            <c:if test="${empty student.parentList}">
                <div class="control-group">
                    <label for="parentsName" class="control-label">家长姓名:<span style="color: red;font-size: 16px">&nbsp;&nbsp;*</span></label>
                    <div class="controls">
                        <input type="text" id="parentsName" name="parentsName" class="input-large" value="" maxlength="20"/>
                    </div>
                </div>
                <div class="control-group">
                    <label for="relationship" class="control-label">关系:</label>
                    <div class="controls">
                        <select id="relationship" name="relation" style="width: 140px">
                            <option value="1">父亲</option>
                            <option value="2">母亲</option>
                            <option value="3">祖父</option>
                            <option value="4">祖母</option>
                            <option value="5">外祖父</option>
                            <option value="6">外祖母</option>
                            <option value="7">其他</option>
                        </select>
                    </div>
                </div>
                <div class="control-group">
                    <label for="parentMobile" class="control-label">手机号:</label>
                    <div class="controls" id="mobileDiv">
                        <div>
                            <input type="text" id="parentMobile" name="parentMobile" class="input-large" value=""/>
                        </div>
                    </div>
                </div>
                <div class="control-group">
                    <label for="parentPhone" class="control-label">固定电话:</label>
                    <div class="controls" id="phoneDiv">
                        <div>
                            <input type="text" id="parentPhone" name="parentPhone" class="input-large" value=""/>
                        </div>
                    </div>
                </div>
            </c:if>

            <div class="form-actions">
                <input id="submit_btn" class="btn btn-primary" type="submit" value="提交"/>&nbsp;
                <input id="cancel_btn" class="btn" type="button" value="返回" onclick="history.back()"/>
            </div>
		</fieldset>
	</form>

</body>
</html>

<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<head>
<title>任务管理</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css" href="css/box.css">
<link rel="stylesheet" type="text/css" href="css/table.css">
<script language="javascript" type="text/javascript" src="script/helper.js"></script>
<link href="css/ui-lightness/jquery-ui-1.9.2.custom.css"
	rel="stylesheet" />
<script src="js/jquery-1.8.3.js"></script>
<script src="js/jquery-ui-1.9.2.custom.js"></script>
<s:if test="!editTaskView">
<script>
$(function(){
	$("#form").accordion();
});
</script>
</s:if>
<s:else>
<script>
$(function(){
	$("#form").accordion({active:1});
});
</script>
</s:else>
</head>

<body>
<div class="title">
	<h1>任务管理</h1>
</div>
	<s:form id="form" action="task" method="post">
	<h3>当前任务</h3>
	<div>
		<s:hidden name="taskID"></s:hidden>
		<s:if test="showMsg">
			<div class="msg">
				<p>
					<s:property value="msgTitle" />
				</p>
				<ul>
					<s:iterator value="msgItem">
						<li><s:property /></li>
					</s:iterator>
				</ul>
			</div>
		</s:if>
		<table id="mytab" border="1" class="t1">
			<thead>
				<tr>
					<th>名称</th>
					<th>匹配表达式</th>
					<th>创建时间</th>
					<th>等待执行结果</th>
					<th>发布</th>
					<th>编辑</th>
				</tr>
			</thead>
			<s:iterator value="tasks" id="p" status="st">
				<s:url id="del" action="task" method="deleteTask">
					<s:param name="taskID" value="#p.CId"></s:param>
				</s:url>
				<s:url id="edit" action="task" method="editTask">
					<s:param name="taskID" value="#p.CId"></s:param>
				</s:url>
				<s:url id="log" action="task_log">
					<s:param name="taskID" value="#p.CId"></s:param>
				</s:url>

				<s:if test="#p.CId==taskID">
					<tr class="selected">
				</s:if>
				<s:elseif test="#st.Even">
					<tr>
				</s:elseif>
				<s:else>
					<tr class="a1">
				</s:else>
				<td><s:property value="CName" /></td>
				<td><s:property value="CVersionRegex" /></td>
				<td><s:property value="CCreateTime" /></td>
				<td><s:property value="CWaiteResult" /></td>
				<td><span><s:property value="CPublish" /></span> <s:if
						test="CEdit">
						<span>，编辑中。</span>
					</s:if></td>
				<td><s:a href="javascript:deleteConfim('%{del}')">删除</s:a> <s:a href="%{edit}">编辑</s:a> <s:a
						href="%{log}">日志</s:a></td>
				</tr>

			</s:iterator>
			<tr >
			<td colspan="6">
					<s:bean name="org.apache.struts2.util.Counter" id="counter">
			<s:param name="first" value="1" />
			<s:param name="last" value="%{pageCount}" />
			<s:iterator id="pp">
				<s:if test="(#pp-1)==taskPageIndex">
				第<s:property />页
				</s:if>
				<s:else>
				<s:url includeParams="all" id="ppp">
					<s:param name="taskPageIndex" value="%{#pp-1}"></s:param>
				</s:url>
				<s:a href="%{ppp}" >
				第<s:property />页
				</s:a>
				</s:else>
			</s:iterator>
		</s:bean>
			</td>
			</tr>
		</table>
	</div>
		<s:if test="!editTaskView">
			<h3>添加任务</h3>
			<div>
				<div class="line">
					<span class="label">任务名称</span> <input type="text" name="taskName"
						class="txt"></input>
				</div>
				<div class="line">
					<span class="label">版本匹配正则表达式</span> <input
						value="eng\.F5_BXT_01_V01" type="text" name="versionRegex"
						class="txt">

				</div>
				<div class="line">
					<span class="label">设备ID</span> <input type="text" name="deviceId"
						class="txt">

				</div>
				<div class="line">
					<input type="checkbox" name="waitResult" value="true"> <span>等待执行结果</span>
				</div>
				<div class="line">
					<input type="checkbox" name="publish" value="true"> <span>发布</span>
				</div>
				<div class="bottom">
					<s:submit method="taskAdd" value="添加" cssClass="button"></s:submit>
				</div>
				<br />
			</div>
		</s:if>
		<s:else>
			<h3>修改任务</h3>
			<div>
				<div class="line">
					<span class="label">任务名称</span>
					<s:textfield name="taskName" cssClass="txt"></s:textfield>
				</div>
				<div class="line">
					<span class="label">版本匹配正则表达式</span>
					<s:textfield name="versionRegex" cssClass="txt"></s:textfield>
				</div>
				<div class="line">
					<span class="label">设备ID</span>
					<s:textfield name="deviceId" cssClass="txt"></s:textfield>
				</div>
				<div class="line">
					<s:checkbox name="waitResult"></s:checkbox>
					<span>等待执行结果</span>
				</div>
				<div class="line">
					<s:checkbox name="publish"></s:checkbox>
					<span>发布</span>
				</div>
				<div class="line">
					<s:url id="editItem" action="task" method="taskEditItem">
						<s:param name="taskID" value="taskID"></s:param>
					</s:url>
					<s:a href="%{editItem}">修改项目</s:a>
				</div>
				<div class="bottom">
					<s:submit method="taskEditOk" value="确定" cssClass="button"></s:submit>
					<s:submit method="taskEditCancel" value="取消" cssClass="button"></s:submit>
				</div>
			</div>
		</s:else>
	</s:form>

</body>


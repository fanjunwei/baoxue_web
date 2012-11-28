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
</head>

<body>

	<s:form action="task" method="post">
		<s:hidden name="taskID"></s:hidden>
		<div class="title">
			<h1>任务管理</h1>
		</div>
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
					<th>发布</th>
					<th>编辑</th>
			</thead>
			<s:iterator value="tasks" id="p" status="st">
				<s:url id="del" action="task" method="deleteTask">
					<s:param name="taskID" value="#p.CId"></s:param>
				</s:url>
				<s:url id="edit" action="task" method="editTask">
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
				<td><s:property value="CPublish" /></td>
				<td><s:a href="%{del}">删除</s:a> <s:a href="%{edit}">编辑</s:a></td>
				</tr>

			</s:iterator>
		</table>
		<s:if test="!editTaskView">
			<div class="box">
				<div class="box_title">
					<h1>添加任务</h1>
				</div>
				<div class="line">
					<span class="label">任务名称</span> <input type="text" name="taskName"
						class="txt"></input>
				</div>
				<div class="line">
					<span class="label">匹配表达式</span> <input type="text"
						name="versionRegex" class="txt">

				</div>
				<div class="line">
					<s:checkbox name="publish"></s:checkbox>
					<span>发布</span>
				</div>

				<div class="bottom">
					<s:submit method="taskAdd" value="添加" cssClass="button"></s:submit>
				</div>
				<br />

			</div>
			<br />
			<br />
		</s:if>
		<s:else>
			<div class="box">
				<div class="box_title">
					<h1>修改任务</h1>
				</div>
				<div class="line">
					<span class="label">任务名称</span>
					<s:textfield name="taskName" cssClass="txt"></s:textfield>
				</div>
				<div class="line">
					<span class="label">匹配表达式</span>
					<s:textfield name="versionRegex" cssClass="txt"></s:textfield>
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
					<s:submit method="execute" value="取消" cssClass="button"></s:submit>
				</div>
				<br /> <br /> <br />

			</div>
			<br />
			<br />
		</s:else>
	</s:form>

</body>

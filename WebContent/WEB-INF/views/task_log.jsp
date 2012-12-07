<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<head>
<title>任务日志</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css" href="css/box.css">
<link rel="stylesheet" type="text/css" href="css/table.css">
</head>

<body>
	<s:hidden name="taskID"></s:hidden>
	<s:hidden name="taskName"></s:hidden>
	<div class="title">
		<h1>任务日志</h1>
		<h2>
			任务名称:
			<s:property value="taskName" />
		</h2>
	</div>
	<table id="mytab" border="1" class="t1">
		<thead>
			<tr>
				<th>设备ID</th>
				<th>时间</th>
				<th>IP地址</th>
				<th>版本号</th>
		</thead>
		<s:iterator value="taskLogs" id="p" status="st">
			<s:if test="#p.CId==itemID">
				<tr class="selected">
			</s:if>
			<s:elseif test="#st.Even">
				<tr>
			</s:elseif>
			<s:else>
				<tr class="a1">
			</s:else>
			<td><s:property value="CDeviceId" /></td>
			<td><s:property value="CTime" /></td>
			<td><s:property value="CIp" /></td>
			<td><s:property value="CDeviceVersion" /></td>
			</tr>
		</s:iterator>
			<tr >
			<td colspan="4">
					<s:bean name="org.apache.struts2.util.Counter" id="counter">
			<s:param name="first" value="1" />
			<s:param name="last" value="%{pageCount}" />
			<s:iterator id="pp">
				<s:if test="(#pp-1)==taskPageIndex">
				第<s:property />页
				</s:if>
				<s:else>
				<s:url includeParams="all" id="ppp">
					<s:param name="pageIndex" value="%{#pp-1}"></s:param>
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
	<s:a action="task_log" includeParams="get" method="clear">清除</s:a>
	<s:a action="task">返回</s:a>
	<br />
	<br />
	<br />
</body>

<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<head>
<title>应用管理</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css" href="css/box.css">
<link rel="stylesheet" type="text/css" href="css/table.css">
</head>

<body>
	<div class="title">
		<h1>管理应用</h1>
	</div>
	<table id="mytab" border="1" class="t1">
		<thead>
			<tr>
				<th>PackageName</th>
				<th>VersionCode</th>
				<th>VersionName</th>
				<th>替换包名</th>
				<th>上传时间</th>
				<th>发布</th>
				<th>强制更新</th>
				<th>操作</th>
		</thead>
		<s:iterator value="packages" id="p" status="st">
			<s:url id="del" action="manage_package" method="delete">
				<s:param name="id" value="#p.id"></s:param>
			</s:url>
			<s:url id="edit" action="manage_package" method="edit">
				<s:param name="id" value="#p.id"></s:param>
			</s:url>
			<s:if test="#st.Even">
				<tr >
			</s:if>
			<s:else>
				<tr class="a1">
			</s:else>
			<td><s:property value="packageName" /></td>
			<td><s:property value="versionCode" /></td>
			<td><s:property value="versionName" /></td>
			<td><s:property value="oldPackageName" /></td>
			<td><s:property value="uploadTime" /></td>
			<td><s:property value="publish" /></td>
			<td><s:property value="forcesUpdate" /></td>
			<td><s:a href="%{del}">删除</s:a> <s:a href="%{edit}">编辑</s:a></td>
			</tr>

		</s:iterator>
	</table>

	<br />
	<br />
	<br />
	<br />
	<br />
	<s:if test="showMsg">
		<div class="msg">
			<p>
				<s:property value="msgTitle" />
			</p>
		</div>
	</s:if>
</body>

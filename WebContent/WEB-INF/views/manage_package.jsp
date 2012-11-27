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
				<s:param name="id" value="#p.CId"></s:param>
			</s:url>
			<s:url id="edit" action="manage_package" method="edit">
				<s:param name="id" value="#p.CId"></s:param>
			</s:url>
			<s:if test="#st.Even">
				<tr >
			</s:if>
			<s:else>
				<tr class="a1">
			</s:else>
			<td><s:property value="CPackageName" /></td>
			<td><s:property value="CVersionCode" /></td>
			<td><s:property value="CVersionName" /></td>
			<td><s:property value="COldPackageName" /></td>
			<td><s:property value="CUploadTime" /></td>
			<td><s:property value="CPublish" /></td>
			<td><s:property value="CForcesUpdate" /></td>
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
		<s:form action="upload_package!delete">
			<s:hidden name="id"></s:hidden>
			<div class="msg">
				<p>
					<s:property value="msgTitle" />
				</p>
				<ul>
					<s:iterator value="msgItem">
						<li><s:property /></li>
					</s:iterator>
				</ul>
				<s:if test="showDelete">
			
					<input type="submit" class="button" style="background: #f68b44; color:white;" value="删除" />
				
				<br/>
				</s:if>
			</div>
		</s:form>
	</s:if>
</body>

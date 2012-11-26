 <%@ page language="java" pageEncoding="utf-8"%>

<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<head>

<title>上传天气预报</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css" href="css/topmenu.css">

</head>

<body>
	<s:form id="form1" action="uploadWeather" method="post"
		enctype="multipart/form-data">
		<table>
			<tr>
				<td>选择weather.apk</td>
				<td><s:file name="file"></s:file></td>
			</tr>
			<tr>
				<td colspan="2"><s:submit id="submit" value="提交"></s:submit></td>
			</tr>
		</table>
	</s:form>


	<s:label name="msg" cssStyle="color:red"></s:label>
	<s:if test="showFileinfo">
	<table width="0" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>文件大小</td>
			<td><s:property value="uploadSize" /></td>
		</tr>
		<tr>
			<td>版本号</td>
			<td><s:property value="apkVersion" /></td>
		</tr>
	</table>
	</s:if>
</body>
</html>

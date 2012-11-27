<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<head>

<title>欢迎登陆</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

</head>

<body>
	<s:a action="uploadWeather">上传天气应用</s:a>
	<br />
	<s:a action="upload_package">上传应用</s:a>
	<br />
	<s:a action="manage_package">管理应用</s:a>
	<br />
	<s:a action="updata" namespace="/device_service">updata</s:a>
	<br />
	<s:a action="task">任务</s:a>
</body>

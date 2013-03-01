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
<base target='right' />
<body>
	<s:a target="_top" action="login" method="logout">退出</s:a>
	<br />
	<!--  
	<br />
	<s:a target='right' action="uploadWeather">上传天气应用</s:a>
	-->
	<br />
	<s:a target='right' action="task">任务管理</s:a><br/>
	<s:a target='right' action="task_log" method="DoIpLocate">获取IP归属地</s:a>

	<br />
	<br />
	<br /> 测试
	<br />
	<div style="border: 1px solid #ACC3E4;">
		测试获取Task <br />
		<s:form action="task" namespace="/device_service">
	设备ID
		<input type="text" name="deviceId" />
	设备版本
		<input type="text" name="deviceVersion" />
			<input type="submit" value="获取" />
		</s:form>
	</div>
</body>

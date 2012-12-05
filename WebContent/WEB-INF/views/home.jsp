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
<base target='right'/>
<body>
	<s:a target='right' action="uploadWeather">上传天气应用</s:a>

	<br />
	<s:a target='right' action="task">任务管理</s:a>
	
	<br /><br /><br />
	测试
	<br/>
	<s:url id="test_task" action="task" namespace="/device_service">
	<s:param name="deviceId" value="'--ste--'"></s:param>
	</s:url>
	<s:a href="%{test_task}">task</s:a>
</body>

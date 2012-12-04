<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<head>

<title>欢迎登陆暴雪后台</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">


</head>

<s:url action="home_left" id="left"></s:url>

<frameset rows="100,*" border="0">
<frame name="top" src="top.jsp" />
<frameset cols="150,*" border="0">
<frame name="left" src="${left}" />
<frame name="right"  /> 
</frameset>
</frameset>

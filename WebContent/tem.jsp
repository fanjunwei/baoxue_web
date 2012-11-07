 <%@ page language="java" pageEncoding="utf-8"%>

<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<head>

<title>登陆</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css" href="css/topmenu.css">
<sx:head parseContent="true"/>
</head>

<body>

		<s:form id="form1" action="login" method="post">
			<table>
				<tr>
					<td>用户名</td>
					<td><s:textfield name="userName"></s:textfield></td>
				</tr>

				<tr>
					<td>密码</td>
					<td><s:password name="password"></s:password></td>
				</tr>
				<tr>

					<td><img src="<s:url action="imgCode" ></s:url>" /></td>

					<td><s:textfield name="imgCode"></s:textfield></td>
				</tr>
				<tr>

					<td colspan="2"><sx:submit targets="ss" id="submit" value="提交"></sx:submit></td>
				</tr>
			</table>
		</s:form>
		<s:label name="msg" cssStyle="color:red"></s:label>

</body>


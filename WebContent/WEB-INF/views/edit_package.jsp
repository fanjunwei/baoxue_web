<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<head>
<title>修改应用</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css" href="css/box.css">
</head>

<body>

	<s:form id="form1" action="manage_package!update" method="post">
<s:hidden name="editPackage.CId"></s:hidden>
		<div class="box">
			<div class="title">
				<h1>修改应用</h1>
			</div>
			<div class="line">
				<span class="label">包名</span>
				<span class="txt"><s:property value="editPackage.CPackageName" ></s:property></span>
			</div>
			<div class="line">
				<span class="label">原包名</span>
				<s:textfield name="editPackage.COldPackageName" cssClass="txt"></s:textfield>
				<span class="rigth">如无需替换，则保持为空。</span>
			</div>
			
			<div class="line">
				<s:checkbox name="editPackage.CForcesUpdate"></s:checkbox>
				<span>强制更新</span>
			</div>
			<div class="line">
				<s:checkbox name="editPackage.CPublish"></s:checkbox>
				<span>发布</span>
			</div>
			<div class="bottom">
				<s:submit value="确定" cssClass="button"></s:submit>
				<s:a action="manage_package" cssClass="button">取消</s:a>
			</div>
			<br /> <br /> <br />

		</div>
		<br />
		<br />
	</s:form>
</body>


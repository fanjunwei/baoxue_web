<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

  <head>

    
    <title> <s:property value="title" default="设置IP归属地结果"/></title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script src="js/jquery-1.8.3.js"></script>
	<script src="js/jquery-ui-1.9.2.custom.js"></script>
	<script>
	gerPre=function()
	{
		$.get("task_log!DoIpLocateProgress.action",function(result,status)
		{
			if(status == 'success'){
				total=result.total;
				count=result.count;
				success=result.success;
				pre=0;
				if(total!=0 && count!=0)
				{
					pre=count*100/total;
				}
				if(total==count)
				{
					pre=100;
				}
				$("#lblTotal").text(total);
				$("#lblCount").text(count);
				$("#lblPre").text(pre+"%");
				if(success==0)
				{
					setTimeout("gerPre()",100);
				}
				
			}
		});	
	};
$(gerPre);
	</script>
  </head>
  
  <body>
  <table>
  <tr>
  <td>要处理总数：</td>
  <td><label id="lblTotal"></label></td>
  </tr>
  <tr>
	 <td>已完成：</td>
	 <td><label id="lblCount"></label></td>
  </tr>
  <tr>
	 <td>完成比例：</td>
	 <td><label id="lblPre"></label></td>
  </tr>
  </table>

  </body>

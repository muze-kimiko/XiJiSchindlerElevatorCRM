<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>

<html:html>
<head>
<html:base/>
<title><bean:message key="app.title"/></title>
</head>

<frameset id="main" rows="*" cols="0,20%,*" border="0">
	<frame name="hiddenFrame" scrolling="no" noresize="noresize" src="hiddenbar.jsp">
	<frame name="leftFrame" scrolling="auto" src="treeNode.jsp">
	<frameset id="rmain" rows="0,20%,*" cols="0" border="0">
		<frame name="rightupFrame" scrolling="no" noresize="noresize" src="righthiddenbar.jsp">
		<frame name="rightcenterFrame" scrolling="auto" src='<html:rewrite page="/dutysearchAction.do?method=toSearchRecord"/>'>
		<frame name="rightFrame" src="welcome.jsp">
	</frameset>	
</frameset>

<noframes>
<body>
<center>
�Բ�������������֧�ֿ��(Frameset)�������޷��������ҳ��
</center>
</body>
</noframes>
</html:html>

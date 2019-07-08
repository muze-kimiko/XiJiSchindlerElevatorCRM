<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<link rel="stylesheet" type="text/css" href="<html:rewrite forward='formCSS'/>">

<br>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <title>XJSCRM</title>
</head>
<body>
  <html:errors/>
  <html:form action="/lostElevatorReportAction.do?method=toUpdateRecord" enctype="multipart/form-data">
    <%@ include file="addOrModify.jsp" %>
    
    <logic:equal name="lostElevatorReportBean" property="submitType" value="Y">
		<!-- ���ϴ��ĸ��� -->
		<%@ include file="UpLoadFileDisplay.jsp" %>
   	</logic:equal>
   	<logic:notEqual name="lostElevatorReportBean" property="submitType" value="Y">
		<!-- ���ϴ��ĸ��� -->
		<%@ include file="UpLoadedFile.jsp" %>
		<!-- �ϴ��ĸ��� -->
		<%@ include file="UpLoadFile.jsp" %>
	</logic:notEqual>
    
  </html:form>
</body>
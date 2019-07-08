<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>

<link rel="stylesheet" type="text/css" href="<html:rewrite forward='formCSS'/>">
<br>
<html:errors/>
<logic:present name="MaintainProjectInfoBean">
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
  <tr>
    <td class="wordtd">��������:</td>
    <td><bean:write name="MaintainProjectInfoBean" scope="request" property="maintType"/></td>
  </tr>
  <tr>
    <td class="wordtd">ά����Ŀ:</td>
    <td><bean:write name="MaintainProjectInfoBean" scope="request" property="maintItem"/></td>
  </tr>
  <tr>
    <td class="wordtd">ά����������:</td>
    <td><bean:write name="MaintainProjectInfoBean" scope="request" property="maintContents"/></td>
  </tr> 

  <tr>
    <td class="wordtd">��������:</td>
    <td>${MaintainProjectInfoBean.elevatorType=='T'?'ֱ��':'����'}</td>
  </tr> 
   <tr>
    <td class="wordtd">�����:</td>
    <td><bean:write name="MaintainProjectInfoBean" scope="request" property="orderby"/></td>
  </tr> 

  <tr>
    <td class="wordtd"><bean:message key="handoverelevatorcheckitem.enabledFlag"/>:</td>
    <td>
	<logic:match name="MaintainProjectInfoBean" property="enabledFlag" value="Y">
	<bean:message key="pageword.yes"/>
	</logic:match>
	<logic:match name="MaintainProjectInfoBean" property="enabledFlag" value="N">
	<bean:message key="pageword.no"/>
	</logic:match>
	</td>
  </tr>
  <tr>
	<td class="wordtd"><bean:message key="handoverelevatorcheckitem.rem"/>:</td>
    <td><bean:write name="MaintainProjectInfoBean" scope="request" property="rem"/></td>
  </tr>
</table>
</logic:present>
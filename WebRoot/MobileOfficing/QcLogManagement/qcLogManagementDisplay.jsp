<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>

<link rel="stylesheet" type="text/css" href="<html:rewrite forward='formCSS'/>">
<br>
<html:errors/>
<logic:present name="qclogManagementBean">
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
  <tr>
    <td class="wordtd">¼����:</td>
    <td style="width: 35%"><bean:write name="qclogManagementBean" property="operId"/></td>
    <td class="wordtd">¼������:</td>
    <td style="width: 35%"><bean:write name="qclogManagementBean" property="operDate"/></td>
 </tr>
  <tr>
    <td class="wordtd">ά��վ:</td>
    <td style="width: 35%"><bean:write name="qclogManagementBean" property="maintStation"/></td>
    <td class="wordtd">ά����:</td>
    <td style="width: 35%"><bean:write name="qclogManagementBean" property="maintPersonnel"/></td>
 </tr>
  <tr>
    <td class="wordtd">���ݱ��:</td>
    <td style="width: 35%"><bean:write name="qclogManagementBean" property="elevatorNo"/></td>
    <td class="wordtd">�¶�����:</td>
    <td style="width: 35%"><bean:write name="qclogManagementBean" property="ydlh"/></td>
 </tr>
  <tr>
    <td class="wordtd">ά���������Ƿ�Թ�������:</td>
    <td style="width: 35%"><bean:write name="qclogManagementBean" property="isgzfz"/></td>
    <td class="wordtd">ά���������Ƿ������������:</td>
    <td style="width: 35%"><bean:write name="qclogManagementBean" property="iszfwt"/></td>
 </tr>
  <tr>
    <td class="wordtd">�׷���������:</td>
    <td style="width: 35%"><bean:write name="qclogManagementBean" property="jffkwt"/></td>
    <td class="wordtd">Զ�̼������:</td>
    <td style="width: 35%"><bean:write name="qclogManagementBean" property="ycjkwt"/></td>
 </tr>
   <tr>
    <td class="wordtd">��ע:</td>
    <td style="width: 35%" colspan="3"><bean:write name="qclogManagementBean" property="rem"/></td>
 </tr>
 </table>
	
</logic:present>
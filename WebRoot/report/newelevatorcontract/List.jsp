<%@ page contentType="text/html;charset=gb2312" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ page import="java.util.*" %>
<link rel="stylesheet" type="text/css" href="<html:rewrite forward='formCSS'/>">

<html>
<head>
<script language="javascript">
function toExcelRecord(){
    document.serveTableForm.flag.value="Y";
    document.serveTableForm.genReport.value="Y";
	document.serveTableForm.target = "_blank";
	document.serveTableForm.submit();
}
</script>
<title></title>
</head>
<style type="text/css">
	.th{
		font-size: 12px;
		text-align: center;
		vertical-align:top;
		padding-top: 6px;
		padding-right: 5px;
		padding-bottom: 3px;
		padding-left: 5px;
		background-color: #b8c4f4;
	}
</style>
<html:form action="/newelevatorContractReportAction.do?method=toSearchRecord">
<html:hidden property="property(flag)" styleId="flag" value="Y"/>
<html:hidden property="property(genReport)" styleId="genReport" value="Y"/>

</html:form>
<body style="font-size:3px;">

<br>
<br>
<br>


<!--�˱�ԭ����ϸ����-->
<table width="98%"  border="0" cellpadding="0" cellspacing="0" align="center">
  <tr>
    <td align="center" height="50"><div align="center"><font style="font-size:15px" color="blue">
    <bean:write name="dateinfo" property="startdate"/>��<bean:write name="dateinfo" property="enddate"/>������������ͬͳ��</font></div></td>
  </tr>
</table>

<table width="98%" height="58"  border="0" cellpadding="0" class="tb" cellspacing="1" align="center">
<tr>
    <td class="th" nowrap>��� </td>
    <td class="th" nowrap>վ��</td>
    <td class="th" nowrap>�����</td>
    <td class="th" nowrap>��ͬ��</td>
    <td class="th" nowrap>�׷���λ</td>
    <td class="th" nowrap>�ͺ�</td>
    <td class="th" nowrap>��/վ/��</td>
    <td class="th" nowrap>����</td>
    <td class="th" nowrap>�ܼ۸�</td>
    <td class="th" nowrap>��ʼ����</td>
    <td class="th" nowrap>��������</td>
  </tr>
  <%
  int i=0;
  //���浥��
  %>
  <logic:present name="report_list">
  <logic:iterate id="element" name="report_list">
  	<tr style="font-size:12px;border:1px;">
  	<%
  		i++;
  	%>
  		<td nowrap align="center"><%=i%></td>
  		<td nowrap><bean:write name="element" property="storagename"/></td>
  		<td nowrap><bean:write name="element" property="serviceno"/></td>
  		<td nowrap><bean:write name="element" property="contractid"/></td>
  		<td nowrap><bean:write name="element" property="custname"/></td>
  		<td nowrap><bean:write name="element" property="elevatorid"/></td>
  		<td nowrap><bean:write name="element" property="floorstagedoor"/></td>
  		<td nowrap><bean:write name="element" property="price"/></td>
  		<td nowrap><bean:write name="element" property="countsfee"/></td>
  		<td nowrap><bean:write name="element" property="mugstartdate"/></td>
  		<td nowrap><bean:write name="element" property="mugenddate"/></td>
  	</tr>
  </logic:iterate>
  </logic:present>
  <tr>
  	<td colspan="13">
  		<br>
		<div height="100">&nbsp;&nbsp;&nbsp;ͳ�ƣ���¼��<b><%=i%></b>��,�����ܺ�Ϊ��<b><bean:write name="price"/></b>��Ԫ�����ܼ��ܼ�Ϊ��<b><bean:write name="countprice"/></b>��Ԫ��</div>
		<br>
  	</td>
  </tr>
</table>


<br>
<br>
<div align="center">
<input type="button" name="toExcelRecord" value="����Excel" onclick="toExcelRecord()"/>&nbsp;
<input type="button" value="  �ر�  " onclick="javascript:window.close()";>
</div>
<br>
</body>



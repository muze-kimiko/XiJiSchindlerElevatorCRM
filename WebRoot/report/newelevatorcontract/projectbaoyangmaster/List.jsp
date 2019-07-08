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
<html:form action="/ProjectBaoYangMasterAction.do?method=toSearchRecord">
<html:hidden property="property(flag)" styleId="flag" value="Y"/>
<html:hidden property="property(genReport)" styleId="genReport" value="Y"/>
<html:hidden property="property(startdate)" styleId="startdate" value="${dateinfo.startdate}"/>
<html:hidden property="property(enddate)" styleId="startdate" value="${dateinfo.enddate}"/>
<html:hidden property="property(searchflags)" styleId="searchflags" />
<html:hidden property="property(contractid)" styleId="contractid" value="${contractid}"/>
<html:hidden property="property(custname)" styleId="custname" value="${custname}"/>
<html:hidden property="property(serviceno)" styleId="serviceno" value="${serviceno}"/>
<html:hidden property="property(elevatorid)" styleId="elevatorid" value="${elevatorid}"/>
<html:hidden property="property(mugStorages)" styleId="mugStorages" value="${mugStorages}"/>
<html:hidden property="property(tstartdate)" styleId="tstartdate" value="${tstartdate}"/>
<html:hidden property="property(tenddate)" styleId="tenddate" value="${tenddate}"/>
<html:hidden property="property(grcid22)" styleId="grcid22" value="${grcid22}"/>
<html:hidden property="property(storageids)" styleId="storageids"/>
<html:hidden property="property(elevatorTypes)" styleId="elevatorTypes"/>
</html:form>
<body style="font-size:3px;">

<br>
<br>
<br>


<table width="98%"  border="0" cellpadding="0" cellspacing="0" align="center">
  <tr>
    <td align="center" height="50"><div align="center"><font style="font-size:15px" color="blue">���������ܱ��ѯ</font></div></td>
  </tr>
</table>

<table width="98%" height="58"  border="0" cellpadding="0" class="tb" cellspacing="1" align="center">
<tr>
    <td class="th" nowrap>��� </td>
    <td class="th" nowrap>��ͬ��</td>
    <td class="th" nowrap>վ��</td>
    <td class="th" nowrap>�׷���λ</td>    
    <td class="th" nowrap>���ݱ��</td>
    <td class="th" nowrap>��/վ/��</td>
    <td class="th" nowrap>��ʼʱ��</td>
    <td class="th" nowrap>����ʱ��</td>
    <td class="th" nowrap>�˱�ʱ��</td>
    <td class="th" nowrap>��ϵ��</td>
    <td class="th" nowrap>��ϵ�绰</td>
    <td class="th" nowrap>��ϵ��ַ</td>
    <td class="th" nowrap>������ַ</td>
    <td class="th" nowrap>��ͬ״̬</td>
    <td class="th" nowrap>����ά���ֲ�</td>
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
  		<td nowrap align="center"><bean:write name="element" property="contractid"/></td>
  		<td nowrap align="center"><bean:write name="element" property="nodename"/></td>
  		<td nowrap align="center"><bean:write name="element" property="custname"/></td>
  		<td nowrap align="center"><bean:write name="element" property="elevatorid"/></td>
  		<td nowrap align="center"><bean:write name="element" property="floor"/>/<bean:write name="element" property="stage"/>/<bean:write name="element" property="door"/></td>
  	    <td nowrap align="center"><bean:write name="element" property="mugstartdate"/></td>
  	    <td nowrap align="center"><bean:write name="element" property="mugenddate"/></td>
  	    <td nowrap align="center"><bean:write name="element" property="backdate"/></td>
  	    <td nowrap align="center"><bean:write name="element" property="linkman"/></td>
  	    <td nowrap align="center"><bean:write name="element" property="linkphone"/></td>
  	    <td nowrap align="center"><bean:write name="element" property="linkaddress"/></td>
  	    <td nowrap align="center"><bean:write name="element" property="mugaddress"/></td>
  	    <td nowrap align="center"><bean:write name="element" property="searchflag"/></td>  	    
  	    <td nowrap align="center"><bean:write name="element" property="grcname"/></td>
  	    <td nowrap style="diaplay:none;"><bean:write name="element" property="Flag"/></td>	    
  	</tr>
  </logic:iterate>
  </logic:present>
  <tr>
  	<td colspan="17">
  		<br>
		<div height="100">&nbsp;&nbsp;&nbsp;ͳ�ƣ���¼��<b><%=i%></b>��</div>
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
</html>


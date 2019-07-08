<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<link rel="stylesheet" type="text/css" href="<html:rewrite forward='formCSS'/>">

<html>
<head>
<script language="javascript">
function toExcelRecord(){
	document.serveTableForm.target = "_blank";
    document.serveTableForm.genReport.value="Y";
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
<html:form action="/NewSignBusinessSummaryTableReportSearchAction.do?method=toSearchRecord">

<html:hidden property="property(genReport)" styleId="genReport" />
<%--html:hidden property="property(contractid)" value="${conditionmap.contractid }" /--%>
<html:hidden property="property(lotdates)" value="${conditionmap.lotdates }" />
<html:hidden property="property(lotdatee)" value="${conditionmap.lotdatee }" />
<html:hidden property="property(username)" value="${conditionmap.username }" />
<html:hidden property="property(grcid)" value="${conditionmap.grcid }" />
<%--html:hidden property="property(custname)" value="${conditionmap.custname }" /--%>
<%--html:hidden property="property(nodeid)" value="${conditionmap.nodeid }" /--%>
</html:form>

<body style="font-size:3px;">

<br>
<br>
<br>

<table width="98%"  border="0" cellpadding="0" cellspacing="0" align="center">
  <tr>
    <td align="center" height="50">
	    <div align="center">
	    <font style="font-size:15px" color="blue">
		   <b> ��ǩҵ����ܱ�	    
		  	<logic:present name="conditionmap">
			    <logic:equal name="conditionmap" property="timerange" value="Y">
			    	(${conditionmap.lotdates }��${conditionmap.lotdatee })
			    </logic:equal>
		    </logic:present>
		   </b>
		</font>
		</div>
    </td>
  </tr>
</table>

<table width="98%" height="58"  border="0" cellpadding="0" class="tb" cellspacing="1" align="center">
  <tr>
    <td rowspan="2" class="th" nowrap valign="bottom">���</td>
    <td rowspan="2" class="th" nowrap valign="bottom">ҵ��Ա</td>
    <td rowspan="2" class="th" nowrap valign="bottom">����ά���ֲ�</td>  
    <td colspan="2" class="th" nowrap>��ǩ��ά�޺�ͬ</td>
    <td colspan="2" class="th" nowrap>��ǩ�������ͬ</td>
    <td colspan="2" class="th" nowrap>��ǩ����װ��ͬ</td>
    <td colspan="2" class="th" nowrap>��ǩ��������ͬ</td>
  </tr>  
  <tr>
    <td class="th" nowrap>̨��</td>
    <td class="th" nowrap>���</td>
    <td class="th" nowrap>̨��</td>
    <td class="th" nowrap>���</td>
    <td class="th" nowrap>̨��</td>
    <td class="th" nowrap>���</td>
    <td class="th" nowrap>̨��</td>
    <td class="th" nowrap>���</td>
  </tr>
  
<logic:present name="resultList">
<logic:iterate id="element" name="resultList">
  <tr style="font-size:12px;border:1px;">
  		<td nowrap align="center"><bean:write name="element" property="xuhao"/></td>
  		<td nowrap align="center"><bean:write name="element" property="username"/></td>
  		<td nowrap align="center"><bean:write name="element" property="grcname"/></td>
  		<td nowrap align="center"><bean:write name="element" property="wnum"/></td>
  		<td nowrap align="center"><bean:write name="element" property="wamt"/></td>
  		<td nowrap align="center"><bean:write name="element" property="gnum"/></td>
  		<td nowrap align="center"><bean:write name="element" property="gamt"/></td>
  		<td nowrap align="center"><bean:write name="element" property="jnum"/></td>
  		<td nowrap align="center"><bean:write name="element" property="jamt"/></td>
  		<td nowrap align="center"><bean:write name="element" property="bnum"/></td>
  		<td nowrap align="center"><bean:write name="element" property="bamt"/></td>
  </tr>
</logic:iterate>
</logic:present>
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



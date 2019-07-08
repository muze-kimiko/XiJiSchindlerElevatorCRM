<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
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

<html:form action="/CollectionInquiresTableSearchAction.do?method=toSearchRecord">

<html:hidden property="property(genReport)" styleId="genReport" />
<html:hidden property="property(contractid)" value="${conditionmap.contractid }" />
<html:hidden property="property(predates)" value="${conditionmap.predates }" />
<html:hidden property="property(predatee)" value="${conditionmap.predatee }" />
<html:hidden property="property(budgetid)" value="${conditionmap.budgetid }" />
<html:hidden property="property(username)" value="${conditionmap.username }" />
<html:hidden property="property(custname)" value="${conditionmap.custname }" />
<html:hidden property="property(nodeid)" value="${conditionmap.nodeid }" />
<html:hidden property="property(grcid)" value="${conditionmap.grcid }" />
</html:form>

<body style="font-size:3px;">

<br>
<br>
<br>


<table width="98%"  border="0" cellpadding="0" cellspacing="0" align="center">
  <tr>
    <td align="center" height="50">
	    <div align="center"><font style="font-size:15px" color="blue">
		   <b>ά���տ��ѯ��</b>
		</font></div>
    </td>
  </tr>
</table>

<table width="98%" height="58"  border="0" cellpadding="0" class="tb" cellspacing="1" align="center">
  <tr>
    <td class="th" nowrap>���</td>
    <td class="th" nowrap>��ͬ��</td>
    <td class="th" nowrap>�׷���λ</td>
    <td class="th" nowrap>Ӧ������</td>
    <td class="th" nowrap>Ӧ�ս��</td>
    <td class="th" nowrap>�ѿ�Ʊ���</td>
    <td class="th" nowrap>δ��Ʊ���</td>   
    <td class="th" nowrap>�ѿ�Ʊ���տ���</td>   
    <td class="th" nowrap>�ѿ�Ʊ����Ƿ����</td>   
    <td class="th" nowrap>δ��Ʊ����Ƿ����</td>     
    <td class="th" nowrap>�ѿ�Ʊ��Ƿ����</td>
    <td class="th" nowrap>����ά���ֲ�</td>    
  </tr>
<logic:present name="resultList">
<logic:iterate id="element" name="resultList">
  <tr style="font-size:12px;border:1px;">
  		<td nowrap align="center"><bean:write name="element" property="xuhao"/></td>  		
  		<td nowrap align="center"><bean:write name="element" property="contractid"/></td>  		
  		<td nowrap align="center"><bean:write name="element" property="custname"/></td>
  		<td nowrap align="center"><bean:write name="element" property="predate"/></td>		 		
  		<td nowrap align="center"><bean:write name="element" property="premoney"/></td>
  		<td nowrap align="center"><bean:write name="element" property="nowfee"/></td>
  		<td nowrap align="center"><bean:write name="element" property="nonowfee"/></td>
  		<td nowrap align="center"><bean:write name="element" property="billatm"/></td>
  		<td nowrap align="center"><bean:write name="element" property="billnoatm"/></td> 
  		<td nowrap align="center"><bean:write name="element" property="nobillatm"/></td>
  		<td nowrap align="center"><bean:write name="element" property="nobillnoatm"/></td>
  		<td nowrap align="center"><bean:write name="element" property="grcname"/></td>
  </tr>
</logic:iterate>
</logic:present>
<tr>
  	<td colspan="12">
  		<br>
		<div height="100">${sj }</div>
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


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
<html:form action="/invoiceAuditAction.do?method=toUpdateRecord">
<html:hidden property="isreturn"/>
<html:hidden property="id"/>
<html:hidden property="auditStatus"/>
<logic:present name="invoiceAuditBean">
<html:hidden name="invoiceAuditBean" property="jnlNo"/>
<html:hidden name="invoiceAuditBean" property="billNo"/>
<table id="companyA" width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td class="wordtd" width="20%">ί�к�ͬ�ţ�</td>
		<td width="15%">
		<a onclick="simpleOpenWindow('returningMaintainDetailAction','${invoiceAuditBean.entrustContractNo}');" class="link">${invoiceAuditBean.entrustContractNo}</a>
		</td>
		<td class="wordtd" width="15%">ί�е�λ���ƣ�</td>
		<td width="20%"><bean:write name="invoiceAuditBean" property="companyId"/></td>
		<td class="wordtd" width="15%">�����ֲ���</td>
		<td width="10%"><bean:write name="invoiceAuditBean" property="maintDivision"/></td>
	</tr>
</table>
<br/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td class="wordtd"  width="20%">��ͬ�ܶ</td>
		<td width="15%"><bean:write name="contractTotal"/></td>
		<td class="wordtd" width="15%">���շ�Ʊ��</td>
		<td width="20%"><bean:write name="builtReceivables"/></td>
		<td class="wordtd" width="15%">δ�շ�Ʊ��</td>
		<td width="10%"><bean:write name="noBuiltReceivables"/></td>
	</tr>
</table>
<br/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
	<td class="wordtd" width="20%">��Ʊ�ţ�</td>
		<td colspan="5"><bean:write name="invoiceAuditBean" property="invoiceNo"/></td>
	</tr>
	<tr>
		<td class="wordtd" width="20%">��Ʊ���ͣ�</td>
		<td width="15%"><bean:write name="invoiceAuditBean" property="invoiceType"/></td>
		<td class="wordtd" width="15%">��Ʊ��</td>
		<td width="20%"><bean:write name="invoiceAuditBean" property="invoiceMoney"/></td>
		<td class="wordtd" width="15%">��Ʊ���ڣ�</td>
		<td width="10%"><bean:write name="invoiceAuditBean" property="invoiceDate"/></td>
	</tr>
	<tr>
		<td class="wordtd">��ע��</td>
		<td colspan="5"><bean:write name="invoiceAuditBean" property="rem" /></td>
	</tr>
</table>
<br/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td class="wordtd" width="20%">¼���ˣ�</td>
		<td width="30%"><bean:write name="invoiceAuditBean" property="operId"/></td>
		<td class="wordtd" width="20%">¼�����ڣ�</td>
		<td width="30%"><bean:write name="invoiceAuditBean" property="operDate"/></td>
	</tr>
</table>
<br/>
<div style="width: 100%;padding-top: 4;padding-bottom: 4;border-bottom: 0" class="tb">        
   		<b>&nbsp;Ӧ�տ����</b>
</div>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td class="wordtd" width="20%">����ˣ�</td>
		<td width="30%"><bean:write name="invoiceAuditBean" property="auditOperid"/></td>
		<td class="wordtd" width="20%">������ڣ�</td>
		<td width="30%"><bean:write name="invoiceAuditBean" property="auditDate"/></td>
	</tr>
	<tr>
		<td class="wordtd">��������</td>
		<td colspan="3"><logic:match name="isAudit" value="N">
		<html:textarea name="invoiceAuditBean" property="auditRem" cols="100" rows="3"></html:textarea>
	    </logic:match>
	    <logic:match name="isAudit" value="Y">
	    <bean:write name="invoiceAuditBean" property="auditRem"/>
		</logic:match></td>
	</tr>
</table>

  <script type="text/javascript"> 
	
  </script> 
</logic:present>
  </html:form>
</body>
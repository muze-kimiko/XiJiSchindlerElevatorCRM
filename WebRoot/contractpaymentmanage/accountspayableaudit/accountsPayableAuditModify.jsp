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
<html:form action="/accountsPayableAuditAction.do?method=toUpdateRecord">
<html:hidden property="isreturn"/>
<html:hidden property="id"/>
<html:hidden property="auditStatus"/>
<logic:present name="accountsPayableAuditBean">
<html:hidden name="accountsPayableAuditBean" property="jnlNo"/>
<html:hidden name="accountsPayableAuditBean" property="billNo"/>
<table id="companyA" width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td nowrap="nowrap" class="wordtd">ί�к�ͬ�ţ�</td>
		<td width="20%">
		<a onclick="simpleOpenWindow('returningMaintainDetailAction','${accountsPayableAuditBean.entrustContractNo}');" class="link"><bean:write name="accountsPayableAuditBean" property="entrustContractNo"/></a>
		
		</td>
		<td class="wordtd">ί�е�λ���ƣ�</td>
		<td width="20%"><bean:write name="accountsPayableAuditBean" property="companyId"/></td>
		<td nowrap="nowrap" class="wordtd">�����ֲ���</td>
		<td width="20%"><bean:write name="accountsPayableAuditBean" property="maintDivision"/></td>
	</tr>
</table>
<br/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td nowrap="nowrap" class="wordtd">��ͬ�ܶ</td>
		<td width="20%"><bean:write name="contractTotal"/></td>
		<td class="wordtd">�ѽ�Ӧ�տ��</td>
		<td width="20%"><bean:write name="builtReceivables"/></td>
		<td class="wordtd">δ��Ӧ�տ��</td>
		<td width="20%"><bean:write name="noBuiltReceivables"/></td>
	</tr>
</table>
<br/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td class="wordtd">Ӧ�տ����ƣ�</td>
		<td width="20%"><bean:write name="accountsPayableAuditBean" property="recName"/></td>
		<td class="wordtd">Ӧ�տ��</td>
		<td width="20%"><bean:write name="accountsPayableAuditBean" property="preMoney"/></td>
		<td class="wordtd">Ӧ�տ����ڣ�</td>
		<td width="20%"><bean:write name="accountsPayableAuditBean" property="preDate"/></td>
	</tr>
	<tr>
		<td class="wordtd">��ע��</td>
		<td colspan="5"><bean:write name="accountsPayableAuditBean" property="rem"/></td>
	</tr>
</table>
<br/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td nowrap="nowrap" class="wordtd">¼���ˣ�</td>
		<td width="30%"><bean:write name="accountsPayableAuditBean" property="operId"/></td>
		<td nowrap="nowrap" class="wordtd">¼�����ڣ�</td>
		<td width="30%"><bean:write name="accountsPayableAuditBean" property="operDate"/></td>
	</tr>
</table>
<br/>
<div style="width: 100%;padding-top: 4;padding-bottom: 4;border-bottom: 0" class="tb">        
   		<b>&nbsp;Ӧ�տ����</b>
</div>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td nowrap="nowrap" class="wordtd">����ˣ�</td>
		<td width="30%"><bean:write name="accountsPayableAuditBean" property="auditOperid"/></td>
		<td nowrap="nowrap" class="wordtd">������ڣ�</td>
		<td width="30%"><bean:write name="accountsPayableAuditBean" property="auditDate"/>
		<html:hidden name="accountsPayableAuditBean" property="auditDate"/>
		</td>
	</tr>
	<tr>
		<td class="wordtd">��������</td>
		<td colspan="3">
		<logic:match name="isAudit" value="N">
		<html:textarea name="accountsPayableAuditBean" property="auditRem" cols="100" rows="3"></html:textarea>
	    </logic:match>
	    <logic:match name="isAudit" value="Y">
	    <bean:write name="accountsPayableAuditBean" property="auditRem"/>
		</logic:match>
	    </td>
	</tr>
</table>

  <script type="text/javascript"> 
	
  </script> 
</logic:present>
  </html:form>
</body>
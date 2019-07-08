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
<html:form action="/accountsPayableModifyAction.do?method=toUpdateRecord">

<logic:present name="accountsPayableAuditBean">
<html:hidden name="accountsPayableAuditBean" property="jnlNo"/>

<table id="companyA" width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td nowrap="nowrap" class="wordtd">ί�к�ͬ�ţ�</td>
		<td width="20%">
			<bean:write name="accountsPayableAuditBean" property="entrustContractNo"/>
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

		<td class="wordtd">Ӧ�����</td>
		<td width="20%"><html:text name="accountsPayableAuditBean" property="preMoney" styleId="preMoney" styleClass="default_input" onchange="checkthisvalue(this);"/><font color="red">*</font></td>
		<td class="wordtd">Ӧ�������ڣ�</td>
		<td width="25%"><html:text name="accountsPayableAuditBean" property="preDate" styleId="preDate" size="12" styleClass="Wdate" readonly="true" onfocus="WdatePicker({isShowClear:true,readOnly:true})" /><font color="red">*</font></td>
	
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
		</td>
	</tr>
	<tr>
		<td class="wordtd">��������</td>
		<td colspan="3">
	    <bean:write name="accountsPayableAuditBean" property="auditRem"/>
	    </td>
	</tr>
</table>

  <script type="text/javascript"> 
	
  </script> 
</logic:present>
  </html:form>
</body>
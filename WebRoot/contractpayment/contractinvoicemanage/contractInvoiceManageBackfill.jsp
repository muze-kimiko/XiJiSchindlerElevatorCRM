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
  <html:form action="/contractInvoiceManageAction.do?method=toBackfillRecord">
    <logic:present name="contractInvoiceManageBean">
 <html:hidden property="isreturn"/>
<html:hidden property="id"/>
<html:hidden name="contractInvoiceManageBean" property="jnlNo"/>
<html:hidden name="contractInvoiceManageBean" property="billNo"/>
<html:hidden name="contractInvoiceManageBean" property="submitType"/>

<table id="companyA" width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td class="wordtd" width="20%">��ͬ�ţ�</td>
		<td width="30%"><bean:write name="contractInvoiceManageBean" property="contractNo"/></td>
		<td class="wordtd" width="20%">�׷���λ���ƣ�</td>
		<td width="30%"><bean:write name="contractInvoiceManageBean" property="companyId"/></td>
	</tr>
	<tr>
		<td class="wordtd">��ͬ���ͣ�</td>
		<td>
			<logic:match name="contractInvoiceManageBean" property="contractType" value="B">����</logic:match>
			<logic:match name="contractInvoiceManageBean" property="contractType" value="W">ά��</logic:match>
			<logic:match name="contractInvoiceManageBean" property="contractType" value="G">����</logic:match>
		</td>
		<td class="wordtd">�����ֲ���</td>
		<td><bean:write name="contractInvoiceManageBean" property="maintDivision"/></td>
	</tr>
</table>
<br/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td class="wordtd"  width="20%">��ͬ�ܶ</td>
		<td width="15%"><bean:write name="contractBean" property="contractTotal"/></td>
		<td class="wordtd" width="15%">�ѿ�Ʊ�ܶ</td>
		<td width="15%"><bean:write name="contractBean" property="invoiceTotal"/></td>
		<td class="wordtd" width="15%">δ��Ʊ�ܶ</td>
		<td width="15%"><bean:write name="contractBean" property="noInvoiceTotal"/></td>
	</tr>
	<tr>
		<td class="wordtd">Ӧ�տ���ˮ�ţ�</td>
		<td><html:text name="contractBean" property="ARF_JnlNo" readonly="true" styleClass="default_input_noborder" /></td>
		<td class="wordtd">Ӧ�տ����ƣ�</td>
		<td><bean:write name="contractBean" property="recName"/></td>
		<td class="wordtd"></td>
		<td></td>
	</tr>
	<tr>
		<td class="wordtd">Ӧ�տ��</td>
		<td><bean:write name="contractBean" property="preMoney"/></td>
		<td class="wordtd">�ѿ�Ʊ��</td>
		<td><bean:write name="contractBean" property="bilMoney"/></td>
		<td class="wordtd">δ��Ʊ��</td>
		<td><bean:write name="contractBean" property="nobilMoney"/></td>
	</tr>
</table>
<br/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td class="wordtd">��Ʊ�ţ�</td>
		<td colspan="5"><html:text name="contractInvoiceManageBean" property="invoiceNo" styleClass="default_input"></html:text><font color="red">*</font></td>
	</tr>
	<tr>
		<td class="wordtd" width="20%">��Ʊ���ͣ�</td>
		<td width="15%"><bean:write name="contractInvoiceManageBean" property="invoiceType"/></td>
		<td class="wordtd" width="15%">��Ʊ��</td>
		<td width="15%"><html:text name="contractInvoiceManageBean" property="invoiceMoney" styleClass="default_input_noborder" readonly="true"></html:text></td>
		<td class="wordtd" width="15%">��Ʊ���ڣ�</td>
		<td width="15%"><bean:write name="contractInvoiceManageBean" property="invoiceDate"/></td>
	</tr>
	<tr>
		<td class="wordtd">��ע��</td>
		<td colspan="5"><bean:write name="contractInvoiceManageBean" property="rem"/></td>
	</tr>
</table>
<br/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td class="wordtd" width="20%">¼���ˣ�</td>
		<td width="30%"><bean:write name="contractInvoiceManageBean" property="operId"/></td>
		<td class="wordtd" width="20%">¼�����ڣ�</td>
		<td width="30%"><bean:write name="contractInvoiceManageBean" property="operDate"/></td>
	</tr>
</table>
<br/>
<div style="width: 100%;padding-top: 4;padding-bottom: 4;border-bottom: 0" class="tb">        
   		<b>&nbsp;Ӧ�տ����</b>
</div>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td class="wordtd" width="20%">����ˣ�</td>
		<td width="30%"><bean:write name="contractInvoiceManageBean" property="auditOperid"/></td>
		<td class="wordtd" width="20%">������ڣ�</td>
		<td width="30%"><bean:write name="contractInvoiceManageBean" property="auditDate"/></td>
	</tr>
	<tr>
		<td class="wordtd">��������</td>
		<td colspan="3"><bean:write name="contractInvoiceManageBean" property="auditRem"/></td>
	</tr>
</table>

  <script type="text/javascript"> 
	
  </script> 
</logic:present>
  </html:form>
</body>
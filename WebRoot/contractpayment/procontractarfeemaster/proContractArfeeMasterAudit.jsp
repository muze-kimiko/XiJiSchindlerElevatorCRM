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
  <html:form action="/proContractArfeeMasterAction.do?method=toAuditRecord">
   <logic:present name="proContractArfeeMasterBean">
 <html:hidden property="isreturn"/>
<html:hidden property="id"/>
<html:hidden name="proContractArfeeMasterBean" property="jnlNo"/>
<html:hidden name="proContractArfeeMasterBean" property="billNo"/>
<html:hidden name="proContractArfeeMasterBean" property="submitType"/>
<html:hidden name="proContractArfeeMasterBean" property="auditStatus"/>

<table id="companyA" width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td class="wordtd" width="15%">��ͬ�ţ�</td>
		<td width="20%"><bean:write name="proContractArfeeMasterBean" property="contractNo"/></td>
		<td class="wordtd" width="15%">�׷���λ���ƣ�</td>
		<td width="20%"><bean:write name="proContractArfeeMasterBean" property="companyId"/></td>
		<td class="wordtd" width="15%">��ͬ���ͣ�</td>
		<td width="20%">
			<logic:match name="proContractArfeeMasterBean" property="contractType" value="B">����</logic:match>
			<logic:match name="proContractArfeeMasterBean" property="contractType" value="W">ά��</logic:match>
			<logic:match name="proContractArfeeMasterBean" property="contractType" value="G">����</logic:match>
		</td>
	</tr>
	<tr>
		<td class="wordtd">�����ֲ���</td>
		<td><bean:write name="proContractArfeeMasterBean" property="maintDivision"/></td>
		<td class="wordtd">����ά��վ��</td>
		<td><bean:write name="proContractArfeeMasterBean" property="maintStation"/></td>
		<td class="wordtd"></td>
		<td></td>
	</tr>
</table>
<br/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td class="wordtd"  width="15%">��ͬ�ܶ</td>
		<td width="20%"><bean:write name="contractTotal"/></td>
		<td class="wordtd" width="15%">�ѽ�Ӧ�տ��</td>
		<td width="20%"><bean:write name="builtReceivables"/></td>
		<td class="wordtd" width="15%">δ��Ӧ�տ��</td>
		<td width="20%"><bean:write name="noBuiltReceivables"/></td>
	</tr>
</table>
<br/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td class="wordtd" width="15%">ά�����䣺</td>
		<td colspan="5"><bean:write name="proContractArfeeMasterBean" property="maintScope"/></td>
	</tr>
	<tr>
		<td class="wordtd" width="15%">Ӧ�տ����ƣ�</td>
		<td width="20%"><bean:write name="proContractArfeeMasterBean" property="recName"/></td>
		<td class="wordtd" width="15%">Ӧ�տ��</td>
		<td width="20%"><bean:write name="proContractArfeeMasterBean" property="preMoney"/></td>
		<td class="wordtd" width="15%">Ӧ�տ����ڣ�</td>
		<td width="20%"><bean:write name="proContractArfeeMasterBean" property="preDate"/></td>
	</tr>
	<tr>
		<td class="wordtd">��ע��</td>
		<td colspan="5"><bean:write name="proContractArfeeMasterBean" property="rem"/></td>
	</tr>
</table>
<br/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td class="wordtd" width="15%">¼���ˣ�</td>
		<td width="20%"><bean:write name="proContractArfeeMasterBean" property="operId"/></td>
		<td class="wordtd" width="15%">¼�����ڣ�</td>
		<td><bean:write name="proContractArfeeMasterBean" property="operDate"/></td>
	</tr>
</table>

    <br/>
    <div style="width: 100%;padding-top: 4;padding-bottom: 4;border-bottom: 0" class="tb">        
   		<b>&nbsp;Ӧ�տ����</b>
  	</div>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
    	<tr>
    		<td class="wordtd" width="15%">����ˣ�</td>
    		<td width="20%"><bean:write name="auditOpername"/></td>
    		<td class="wordtd" width="15%">������ڣ�</td>
    		<td><bean:write name="auditDate"/></td>
    	</tr>
    	<tr>
    		<td class="wordtd">��������</td>
    		<td colspan="3"><html:textarea property="auditRem" rows="3" cols="100" styleClass="default_textarea"/></td>
    	</tr>
    </table>
</logic:present>
  </html:form>
</body>
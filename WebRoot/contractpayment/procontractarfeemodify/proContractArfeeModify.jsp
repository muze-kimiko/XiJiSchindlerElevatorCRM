<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<link rel="stylesheet" type="text/css" href="<html:rewrite forward='formCSS'/>">

<script language="javascript" src="<html:rewrite page="/common/javascript/openwindow.js"/>"></script>
<br>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <title>XJSCRM</title>
</head>
<body>
  <html:errors/>
  <html:form action="/proContractArfeeModifyAction.do?method=toUpdateRecord">
   <logic:present name="proContractArfeeMasterBean">

<html:hidden name="proContractArfeeMasterBean" property="jnlNo"/>

<table id="companyA" width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td class="wordtd" width="15%">��ͬ�ţ�</td>
		<td width="20%"><bean:write name="proContractArfeeMasterBean" property="contractNo"/></td>
		<td class="wordtd" width="15%">�׷���λ���ƣ�</td>
		<td colspan="3">
			<%-- bean:write name="proContractArfeeMasterBean" property="companyId"/--%>

	      <input type="text" name="companyName" id="companyName" value="${companyName}" readonly="true" size="40"/>
	      <input type="button" value=".." onclick="openWindowAndReturnValue3('searchCustomerAction','toSearchRecord','cusNature=JF','')" class="default_input"/><font color="red">*</font>
	      <html:hidden name="proContractArfeeMasterBean" property="companyId" styleId="companyId"/>

		</td>
	</tr>
	<tr>
		<td class="wordtd" width="15%">��ͬ���ͣ�</td>
		<td width="20%">
			<logic:match name="proContractArfeeMasterBean" property="contractType" value="B">����</logic:match>
			<logic:match name="proContractArfeeMasterBean" property="contractType" value="W">ά��</logic:match>
			<logic:match name="proContractArfeeMasterBean" property="contractType" value="G">����</logic:match>
		</td>
		<td class="wordtd" width="15%">�����ֲ���</td>
		<td width="20%"><bean:write name="proContractArfeeMasterBean" property="maintDivision"/></td>
		<td class="wordtd" width="15%">����ά��վ��</td>
		<td width="20%"><bean:write name="proContractArfeeMasterBean" property="maintStation"/></td>
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
		<td colspan="5"><html:text name="proContractArfeeMasterBean" property="maintScope" styleId="maintScope" styleClass="default_input" size="40"/><font color="red">*</font></td>
	</tr>
	<tr>
		<td class="wordtd" width="15%">Ӧ�տ����ƣ�</td>
		<td width="20%">
			<bean:write  name="proContractArfeeMasterBean" property="recName"/>
			<%-- 
			<html:select name="proContractArfeeMasterBean" property="recName">
				<html:option value="">��ѡ��</html:option>
		    	<html:options collection="receivablesList" property="recId" labelProperty="recName"/>
        	</html:select><font color="red">*</font>
        	--%>
		</td>
		
		<td class="wordtd">Ӧ�տ��</td>
		<td width="20%"><html:text name="proContractArfeeMasterBean" property="preMoney" styleId="preMoney" styleClass="default_input" onchange="checkthisvalue(this);"/><font color="red">*</font></td>
		<td class="wordtd">Ӧ�տ����ڣ�</td>
		<td width="25%"><html:text name="proContractArfeeMasterBean" property="preDate" styleId="preDate" size="12" styleClass="Wdate" readonly="true" onfocus="WdatePicker({isShowClear:true,readOnly:true})" /><font color="red">*</font></td>
	
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
    		<td width="20%"><bean:write name="proContractArfeeMasterBean" property="auditOperid"/></td>
    		<td class="wordtd" width="15%">������ڣ�</td>
    		<td><bean:write name="proContractArfeeMasterBean" property="auditDate"/></td>
    	</tr>
    	<tr>
    		<td class="wordtd">��������</td>
    		<td colspan="3"><bean:write name="proContractArfeeMasterBean" property="auditRem"/></td>
    	</tr>
    </table>
</logic:present>
  </html:form>
</body>
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
<html:form action="/paymentAuditAction.do?method=toUpdateRecord">
<html:hidden property="isreturn"/>
<html:hidden property="id"/>
<%-- <html:hidden property="auditStatus"/> --%>
<logic:present name="paymentAuditBean">
<html:hidden name="paymentAuditBean" property="jnlNo"/>
<html:hidden name="paymentAuditBean" property="billNo"/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td class="wordtd" width="20%">ί�к�ͬ�ţ�</td>
		<td width="15%">
		<a onclick="simpleOpenWindow('returningMaintainDetailAction','${paymentAuditBean.entrustContractNo}');" class="link">${paymentAuditBean.entrustContractNo}</a>
		
		</td>
		<td class="wordtd" width="15%">ί�е�λ���ƣ�</td>
		<td width="20%">
			<bean:write name="paymentAuditBean" property="companyId" />
		</td>
		<td class="wordtd" width="15%">�����ֲ���</td>
		<td width="10%">
			<bean:write name="paymentAuditBean" property="maintDivision" />
		</td>
	</tr>
</table>
<br/>
<%-- <table  width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td class="wordtd" width="20%">��ͬ�ܶ</td>
		<td width="15%"><bean:write name="contractTotal"/></td>
		<td class="wordtd" width="15%">��ͬ�Ѹ����</td>
		<td width="20%"><bean:write name="invoiceReceivables"/></td>
		<td class="wordtd"  width="15%">��ͬδ�����</td>
		<td width="10%"><bean:write name="noInvoiceReceivables"/></td>
	</tr>	
	 <tr>
	 <td class="wordtd">��Ʊ�ţ�</td>
	 <td><bean:write name="invoiceNo"/></td>
	 <td class="wordtd">��Ʊ���ͣ�</td>
	 <td colspan="3"><bean:write name="invoiceType"/></td>
	 </tr>
	<tr>
		<td class="wordtd">��Ʊ�ܶ</td>
		<td ><bean:write name="invoiceMoney"/></td>
		<td class="wordtd">�Ѹ����</td>
		<td ><bean:write name="builtReceivables"/></td>
		<td class="wordtd">δ�����</td>
		<td ><bean:write name="noBuiltReceivables"/></td>
	</tr>
</table> 
<br/>--%>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td class="wordtd" width="20%">ƾ֤�ţ�</td>
		<td width="15%"><bean:write name="paymentAuditBean" property="paragraphNo"/></td>
		<td class="wordtd" width="15%">�����</td>
		<td width="20%"><bean:write name="paymentAuditBean" property="paragraphMoney"/></td>
		<td class="wordtd" width="15%">�������ڣ�</td>
		<td width="10%"><bean:write name="paymentAuditBean" property="paragraphDate"/></td>
	</tr>
	<tr>
		<td class="wordtd">��ע��</td>
		<td colspan="5"><bean:write name="paymentAuditBean" property="rem" /></td>
	</tr>
</table>
<br/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td class="wordtd" width="20%">¼���ˣ�</td>
		<td width="15%">
			<bean:write name="paymentAuditBean" property="operId" />
		</td>
		<td class="wordtd" width="15%">¼�����ڣ�</td>
		<td width="20%"><bean:write name="paymentAuditBean" property="operDate"/></td>
		
		<td class="wordtd" width="15%">&nbsp;</td>
		<td width="10%">&nbsp;</td>
	</tr>
</table> 
<br/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr height="23"><td colspan="6">&nbsp;<b>��������������</b></td></tr>
	<tr>
		<td class="wordtd" width="20%">�������:</td>
		<td width="15%"><bean:write name="paymentAuditBean" property="bydAuditDate"/></td>
		<td class="wordtd" width="15%">����:</td>
		<td width="20%">
		<logic:present name="paymentAuditBean" property="bydAuditEvaluate">
			<logic:match name="paymentAuditBean" property="bydAuditEvaluate" value="WZ">����</logic:match>
			<logic:match name="paymentAuditBean" property="bydAuditEvaluate" value="YB">һ��</logic:match>
			<logic:match name="paymentAuditBean" property="bydAuditEvaluate" value="BWZ">������</logic:match>
		</logic:present>
		</td>
		<td class="wordtd" width="15%">&nbsp;</td>
		<td width="10%">&nbsp;</td>
	</tr>
	<tr>
		<td class="wordtd" width="20%">���:</td>
		<td colspan="5">
			<bean:write name="paymentAuditBean" property="bydAuditRem"/>
		</td>
	</tr>
</table>
<br/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr height="23"><td colspan="6">&nbsp;<b>���лطý�����</b></td></tr>
	<tr>
		<td class="wordtd" width="20%">������ڣ�</td>
		<td width="15%"><bean:write name="paymentAuditBean" property="hfAuditDate"/></td>
		<td class="wordtd" width="15%">������������ĸ������������</td>
		<td width="20%"><bean:write name="paymentAuditBean" property="hfAuditNum"/></td>
		<td class="wordtd" width="15%">��Ǽ�����������ĸ������������</td>
		<td width="10%"><bean:write name="paymentAuditBean" property="hfAuditNum2"/></td>
	</tr>
	<tr>
		<td class="wordtd" width="20%">���:</td>
		<td colspan="5">
			<bean:write name="paymentAuditBean" property="hfAuditRem"/>
		</td>
	</tr>
</table>
<br/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr height="23"><td colspan="6">&nbsp;<b>���߹��������</b></td></tr>
	<tr>
		<td class="wordtd" width="20%">������ڣ�</td>
		<td width="15%"><bean:write name="paymentAuditBean" property="rxAuditDate"/></td>
		<td class="wordtd" width="15%">�������������Ͷ�ߴ�����</td>
		<td width="20%"><bean:write name="paymentAuditBean" property="rxAuditNum"/></td>
		<td class="wordtd" width="15%">��Ǽ������������Ͷ�ߴ�����</td>
		<td width="10%"><bean:write name="paymentAuditBean" property="rxAuditNum2"/></td>
	</tr>
	<tr>
		<td class="wordtd" width="20%">���:</td>
		<td colspan="5">
			<bean:write name="paymentAuditBean" property="rxAuditRem"/>
		</td>
	</tr>
</table>
<br/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr height="23"><td colspan="6">&nbsp;<b>�ɼ��˻�������</b></td></tr>
	<tr>
		<td class="wordtd" width="20%">�������:</td>
		<td width="15%"><bean:write name="paymentAuditBean" property="jjthAuditDate"/></td>
		<td class="wordtd" width="15%">����:</td>
		<td width="20%">
		<logic:present name="paymentAuditBean" property="jjthAuditEvaluate">
			<logic:match name="paymentAuditBean" property="jjthAuditEvaluate" value="THJS">�˻ؼ�ʱ</logic:match>
			<logic:match name="paymentAuditBean" property="jjthAuditEvaluate" value="THYB">һ��</logic:match>
			<logic:match name="paymentAuditBean" property="jjthAuditEvaluate" value="THBJS">�˻ز���ʱ</logic:match>
		</logic:present>
		</td>
		<td class="wordtd" width="15%">&nbsp;</td>
		<td width="10%">&nbsp;</td>
	</tr>
	<tr>
		<td class="wordtd" width="20%">���:</td>
		<td colspan="5">
			<bean:write name="paymentAuditBean" property="jjthAuditRem"/>
		</td>
	</tr>
</table>
<br/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr height="23"><td colspan="6">&nbsp;<b>�ֲ������</b></td></tr>
	<tr>
		<td class="wordtd" width="20%">������ڣ�</td>
		<td width="15%"><bean:write name="paymentAuditBean" property="fbzAuditDate"/></td>
		<td class="wordtd" width="15%">���ۣ�</td>
		<td width="20%">
		<logic:present name="paymentAuditBean" property="fbzAuditEvaluate">
			<logic:equal name="paymentAuditBean" property="fbzAuditEvaluate" value="PH">���</logic:equal>
			<logic:equal name="paymentAuditBean" property="fbzAuditEvaluate" value="PHYB">һ��</logic:equal>
			<logic:match name="paymentAuditBean" property="fbzAuditEvaluate" value="BPH">�����</logic:match>
		</logic:present>
		</td>
		<td class="wordtd" width="15%">�ۿ��</td>
		<td width="10%"><bean:write name="paymentAuditBean" property="debitMoney"/></td>
	</tr>
	<tr>
		<td class="wordtd" width="20%">���:</td>
		<td colspan="5">
			<bean:write name="paymentAuditBean" property="fbzAuditRem"/>
		</td>
	</tr>
</table>
<br/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr height="23"><td colspan="2">&nbsp;<b>�ܲ������</b></td></tr>
	<tr>
		<td class="wordtd" width="20%">������ڣ�</td>
		<td width="75%"><bean:write name="paymentAuditBean" property="zbzAuditDate"/></td>
	</tr>
	<tr>
		<td class="wordtd">���:</td>
		<td>
			<bean:write name="paymentAuditBean" property="zbzAuditRem"/>
		</td>
	</tr>
</table>
<br/>
<%@ include file="/workflow/processApproveMessage.jsp" %>
<%-- <div style="width: 100%;padding-top: 4;padding-bottom: 4;border-bottom: 0" class="tb">        
   		<b>&nbsp;Ӧ�տ����</b>
</div>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td class="wordtd" width="20%">����ˣ�</td>
		<td width="30%"><bean:write name="paymentAuditBean" property="auditOperid"/></td>
		<td class="wordtd" width="20%">������ڣ�</td>
		<td width="30%"><bean:write name="paymentAuditBean" property="auditDate"/></td>
	</tr>
	<tr>
		<td class="wordtd">��������</td>
		<td colspan="3"><logic:match name="isAudit" value="N">
		<html:textarea name="paymentAuditBean" property="auditRem" cols="100" rows="3"></html:textarea>
	    </logic:match>
	    <logic:match name="isAudit" value="Y">
	    <bean:write name="paymentAuditBean" property="auditRem"/>
		</logic:match></td>
	</tr>
</table> --%>

  <script type="text/javascript"> 
	
  </script> 
</logic:present>
  </html:form>
</body>
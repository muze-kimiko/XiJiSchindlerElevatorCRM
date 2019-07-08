<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>

<style>
  .show{display:block;}
  .hide{display:none;}
</style>

<logic:present name="paymentManageBean">
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td class="wordtd" width="20%">ί�к�ͬ�ţ�</td>
		<td width="15%">
		<a onclick="simpleOpenWindow('returningMaintainDetailAction','${paymentManageBean.entrustContractNo}');" class="link">${paymentManageBean.entrustContractNo}</a>
		
		
		</td>
		<td class="wordtd" width="15%">ί�е�λ���ƣ�</td>
		<td width="20%">
			<bean:write name="paymentManageBean" property="companyId" />
		</td>
		<td class="wordtd" width="15%">�����ֲ���</td>
		<td width="10%">
			<bean:write name="paymentManageBean" property="maintDivision" />
		</td>
	</tr>
</table>
<br/>
<table  width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
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
<br/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td class="wordtd" width="20%">ƾ֤�ţ�</td>
		<td width="15%"><bean:write name="paymentManageBean" property="paragraphNo"/></td>
		<td class="wordtd" width="15%">�����</td>
		<td width="20%"><bean:write name="paymentManageBean" property="paragraphMoney"/></td>
		<td class="wordtd" width="15%">�������ڣ�</td>
		<td width="10%"><bean:write name="paymentManageBean" property="paragraphDate"/></td>
	</tr>
	<tr>
		<td class="wordtd">��ע��</td>
		<td colspan="5"><bean:write name="paymentManageBean" property="rem" /></td>
	</tr>
</table>
<br/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td class="wordtd" width="20%">¼���ˣ�</td>
		<td width="15%">
			<bean:write name="paymentManageBean" property="operId" />
		</td>
		<td class="wordtd" width="15%">¼�����ڣ�</td>
		<td width="20%"><html:text name="paymentManageBean" property="operDate" readonly="true" styleClass="default_input_noborder" /></td>
		
		<td class="wordtd" width="15%">&nbsp;</td>
		<td width="10%">&nbsp;</td>
	</tr>
</table> 
<br/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr height="23"><td colspan="6">&nbsp;<b>��������������</b></td></tr>
	<tr>
		<td class="wordtd" width="20%">�������:</td>
		<td width="15%"><bean:write name="paymentManageBean" property="bydAuditDate"/></td>
		<td class="wordtd" width="15%">����:</td>
		<td width="20%">
		<logic:present name="paymentManageBean" property="bydAuditEvaluate">
			<logic:match name="paymentManageBean" property="bydAuditEvaluate" value="WZ">����</logic:match>
			<logic:match name="paymentManageBean" property="bydAuditEvaluate" value="YB">һ��</logic:match>
			<logic:match name="paymentManageBean" property="bydAuditEvaluate" value="BWZ">������</logic:match>
		</logic:present>
		</td>
		<td class="wordtd" width="15%">&nbsp;</td>
		<td width="10%">&nbsp;</td>
	</tr>
	<tr>
		<td class="wordtd" width="20%">���:</td>
		<td colspan="5">
			<bean:write name="paymentManageBean" property="bydAuditRem"/>
		</td>
	</tr>
</table>
<br/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr height="23"><td colspan="6">&nbsp;<b>���лطý�����</b></td></tr>
	<tr>
		<td class="wordtd" width="20%">������ڣ�</td>
		<td width="15%"><bean:write name="paymentManageBean" property="hfAuditDate"/></td>
		<td class="wordtd" width="15%">������������ĸ������������</td>
		<td width="20%"><bean:write name="paymentManageBean" property="hfAuditNum"/></td>
		<td class="wordtd" width="15%">��Ǽ�����������ĸ������������</td>
		<td width="10%"><bean:write name="paymentManageBean" property="hfAuditNum2"/></td>
	</tr>
	<tr>
		<td class="wordtd" width="20%">���:</td>
		<td colspan="5">
			<bean:write name="paymentManageBean" property="hfAuditRem"/>
		</td>
	</tr>
</table>
<br/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr height="23"><td colspan="6">&nbsp;<b>���߹��������</b></td></tr>
	<tr>
		<td class="wordtd" width="20%">������ڣ�</td>
		<td width="15%"><bean:write name="paymentManageBean" property="rxAuditDate"/></td>
		<td class="wordtd" width="15%">�������������Ͷ�ߴ�����</td>
		<td width="20%"><bean:write name="paymentManageBean" property="rxAuditNum"/></td>
		<td class="wordtd" width="15%">��Ǽ������������Ͷ�ߴ�����</td>
		<td width="10%"><bean:write name="paymentManageBean" property="rxAuditNum2"/></td>
	</tr>
	<tr>
		<td class="wordtd" width="20%">���:</td>
		<td colspan="5">
			<bean:write name="paymentManageBean" property="rxAuditRem"/>
		</td>
	</tr>
</table>
<br/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr height="23"><td colspan="6">&nbsp;<b>�ɼ��˻�������</b></td></tr>
	<tr>
		<td class="wordtd" width="20%">�������:</td>
		<td width="15%"><bean:write name="paymentManageBean" property="jjthAuditDate"/></td>
		<td class="wordtd" width="15%">����:</td>
		<td width="20%">
		<logic:present name="paymentManageBean" property="jjthAuditEvaluate">
			<logic:match name="paymentManageBean" property="jjthAuditEvaluate" value="THJS">�˻ؼ�ʱ</logic:match>
			<logic:match name="paymentManageBean" property="jjthAuditEvaluate" value="THYB">һ��</logic:match>
			<logic:match name="paymentManageBean" property="jjthAuditEvaluate" value="THBJS">�˻ز���ʱ</logic:match>
		</logic:present>
		</td>
		<td class="wordtd" width="15%">&nbsp;</td>
		<td width="10%">&nbsp;</td>
	</tr>
	<tr>
		<td class="wordtd" width="20%">���:</td>
		<td colspan="5">
			<bean:write name="paymentManageBean" property="jjthAuditRem"/>
		</td>
	</tr>
</table>
<br/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr height="23"><td colspan="6">&nbsp;<b>�ֲ������</b></td></tr>
	<tr>
		<td class="wordtd" width="20%">������ڣ�</td>
		<td width="15%"><bean:write name="paymentManageBean" property="fbzAuditDate"/></td>
		<td class="wordtd" width="15%">���ۣ�</td>
		<td width="20%">
		<logic:present name="paymentManageBean" property="fbzAuditEvaluate">
			<logic:equal name="paymentManageBean" property="fbzAuditEvaluate" value="PH">���</logic:equal>
			<logic:equal name="paymentManageBean" property="fbzAuditEvaluate" value="PHYB">һ��</logic:equal>
			<logic:match name="paymentManageBean" property="fbzAuditEvaluate" value="BPH">�����</logic:match>
		</logic:present>
		</td>
		<td class="wordtd" width="15%">�ۿ��</td>
		<td width="10%"><bean:write name="paymentManageBean" property="debitMoney"/></td>
	</tr>
	<tr>
		<td class="wordtd" width="20%">���:</td>
		<td colspan="5">
			<bean:write name="paymentManageBean" property="fbzAuditRem"/>
		</td>
	</tr>
</table>
<br/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr height="23"><td colspan="2">&nbsp;<b>�ܲ������</b></td></tr>
	<tr>
		<td class="wordtd" width="30%">������ڣ�</td>
		<td><bean:write name="paymentManageBean" property="zbzAuditDate"/></td>
	</tr>
	<tr>
		<td class="wordtd" width="30%">���:</td>
		<td>
			<bean:write name="paymentManageBean" property="zbzAuditRem"/>
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
		<td width="30%"><bean:write name="paymentManageBean" property="auditOperid"/></td>
		<td class="wordtd" width="20%">������ڣ�</td>
		<td width="30%"><bean:write name="paymentManageBean" property="auditDate"/></td>
	</tr>
	<tr>
		<td class="wordtd">��������</td>
		<td colspan="3"><bean:write name="paymentManageBean" property="auditRem"/></td>
	</tr>
</table> --%>

</logic:present>
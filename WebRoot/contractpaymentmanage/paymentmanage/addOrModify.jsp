<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>

<html:hidden property="isreturn"/>
<html:hidden property="id"/>
<html:hidden name="paymentManageBean" property="jnlNo"/>

<html:hidden name="paymentManageBean" property="billNo"/>
<html:hidden name="paymentManageBean" property="submitType"/>
<%-- <html:hidden name="paymentManageBean" property="auditStatus"/> --%>
<html:hidden property="ctJnlNo" value="${ctjnlNo }"/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td class="wordtd" width="20%">ί�к�ͬ�ţ�</td>
		<td width="15%"><html:hidden name="paymentManageBean" property="entrustContractNo" />
		<a onclick="simpleOpenWindow('returningMaintainDetailAction','${paymentManageBean.entrustContractNo}');" class="link">${paymentManageBean.entrustContractNo}</a>
		
		
		</td>
		<td class="wordtd" width="15%">ί�е�λ���ƣ�</td>
		<td width="20%">
		    <bean:write name="companyName"/>
			<html:hidden name="paymentManageBean" property="companyId" />
		</td>
		<td class="wordtd" width="15%">�����ֲ���</td>
		<td width="10%">
		    <bean:write name="maintDivisionName"/>
			<html:hidden name="paymentManageBean" property="maintDivision" />
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
		<td width="15%"><html:text name="paymentManageBean" property="paragraphNo" readonly="true" styleClass="default_input_noborder"/></td>
		<td class="wordtd" width="15%">�����</td>
		<td width="20%"><html:text name="paymentManageBean" property="paragraphMoney" styleId="money" styleClass="default_input" onkeypress="f_check_number3()" onchange="judgePreMoney(this,'${noBuiltReceivables}')" /><font color="red">*</font></td>
		<td class="wordtd" width="15%">�������ڣ�</td>
		<td width="10%"><html:text name="paymentManageBean" property="paragraphDate" size="12" styleClass="Wdate" readonly="true" onfocus="WdatePicker({isShowClear:true,readOnly:true})" /></td>
	</tr>
	<tr>
		<td class="wordtd">��ע��</td>
		<td colspan="5"><html:textarea name="paymentManageBean" property="rem" rows="3" cols="100" styleClass="default_textarea"/></td>
	</tr>
</table>
<br/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td class="wordtd" width="20%">¼���ˣ�</td>
		<td width="30%">
			<bean:write name="operName"/>
			<html:hidden name="paymentManageBean" property="operId" />
		</td>
		<td class="wordtd" width="20%">¼�����ڣ�</td>
		<td width="30%"><html:text name="paymentManageBean" property="operDate" readonly="true" styleClass="default_input_noborder" /></td>
	</tr>
</table>
<script type="text/javascript"> 
</script>
<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>

<html:hidden property="isreturn"/>
<html:hidden property="id"/>
<html:hidden name="contractInvoiceManageBean" property="jnlNo"/>

<html:hidden name="contractInvoiceManageBean" property="billNo"/>
<html:hidden name="contractInvoiceManageBean" property="submitType"/>
<html:hidden name="contractInvoiceManageBean" property="auditStatus"/>

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td class="wordtd">��ͬ�ţ�</td>
		<td width="20%"><html:text name="contractInvoiceManageBean" property="contractNo" readonly="true" styleClass="default_input_noborder" /></td>
		<td class="wordtd" width="15%">̨����</td>
		<td width="20%">
			<html:text name="contractInvoiceManageBean" size="10" property="r9" styleId="r9" onkeypress="f_check_number3();" />
			<font color="red">*</font>
		</td>
		<td class="wordtd">��ͬ���ͣ�</td>
		<td width="20%">
			<logic:match name="contractInvoiceManageBean" property="contractType" value="B">����</logic:match>
			<logic:match name="contractInvoiceManageBean" property="contractType" value="W">ά��</logic:match>
			<logic:match name="contractInvoiceManageBean" property="contractType" value="G">����</logic:match>
			<html:hidden name="contractInvoiceManageBean" property="contractType" />
		</td>
	</tr>
	<tr>
		<td class="wordtd">�׷���λ���ƣ�</td>
		<td width="20%">
			<bean:write name="companyName" />
			<html:hidden name="contractInvoiceManageBean" property="companyId" />
		</td>
		<td class="wordtd">�����ֲ���</td>
		<td>
			<bean:write name="maintDivisionName"/>
			<html:hidden name="contractInvoiceManageBean" property="maintDivision" />
		</td>
		<td class="wordtd">����ά��վ��</td>
		<td>
			<bean:write name="maintStationName"/>
			<html:hidden name="contractInvoiceManageBean" property="maintStation" />
		</td>
	</tr>
</table>
<br/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td class="wordtd">��ͬ�ܶ</td>
		<td width="20%"><bean:write name="contractBean" property="contractTotal"/></td>
		<td class="wordtd">�ѿ�Ʊ�ܶ</td>
		<td width="20%"><bean:write name="contractBean" property="invoiceTotal"/></td>
		<td class="wordtd">δ��Ʊ�ܶ</td>
		<td width="20%"><bean:write name="contractBean" property="noInvoiceTotal"/></td>
	</tr>
	<tr>
		<td class="wordtd">Ӧ�տ���ˮ�ţ�</td>
		<td><html:text name="contractBean" property="ARF_JnlNo" readonly="true" styleClass="default_input_noborder" /></td>
		<td class="wordtd">Ӧ�տ����ƣ�</td>
		<td><bean:write name="contractBean" property="recName"/></td>
		<td class="wordtd">Ӧ�տ����ڣ�</td>
		<td><bean:write name="contractBean" property="preDate"/></td>
	</tr>
	<tr>
		<td class="wordtd">Ӧ�տ��</td>
		<td><bean:write name="contractBean" property="preMoney"/></td>
		<td class="wordtd">�ѿ�Ʊ��</td>
		<td><bean:write name="contractBean" property="bilMoney"/></td>
		<td class="wordtd">δ��Ʊ��</td>
		<td><bean:write name="contractBean" property="nobilMoney"/></td>
	</tr>
	<tr>
		<td class="wordtd">Ӧ�տע��</td>
		<td colspan="5"><bean:write name="contractBean" property="yskrem"/></td>
	</tr>
</table>
<br/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td class="wordtd">��Ʊ�ţ�</td>
		<td width="20%"><html:text name="contractInvoiceManageBean" property="invoiceNo" styleClass="default_input"/></td>
		<td class="wordtd">��Ʊ���ƣ�</td>
		<td width="20%">
			<bean:write name="contractInvoiceManageBean" property="invoiceName"/>
			<html:hidden name="contractInvoiceManageBean" property="invoiceName"/>
		</td>
		<td class="wordtd">ά�����䣺</td>
		<td width="20%">
			<html:text name="contractInvoiceManageBean" size="22" property="maintScope" styleId="maintScope"/>
		</td>
	</tr>
	<tr>
		<td class="wordtd">��Ʊ���ͣ�</td>
		<td>
			<html:select name="contractInvoiceManageBean" property="invoiceType">
				<html:option value="">��ѡ��</html:option>
		    	<html:options collection="invoiceTypeList" property="inTypeId" labelProperty="inTypeName"/>
        	</html:select><font color="red">*</font>
			<%-- <html:text name="contractInvoiceManageBean" property="invoiceType" styleClass="default_input" /> --%>
		</td>
		<td class="wordtd">��Ʊ��</td>
		<td><html:text name="contractInvoiceManageBean" property="invoiceMoney" styleId="invoiceMoney" styleClass="default_input" onkeypress="f_check_number3();" onkeydown="judgePreMoney(this,'${contractBean.nobilMoney}');" onkeyup="judgePreMoney(this,'${contractBean.nobilMoney}');" /><font color="red">*</font></td>
		<td class="wordtd">��Ʊ���ڣ�</td>
		<td><html:text name="contractInvoiceManageBean" property="invoiceDate" size="12" styleClass="Wdate" onfocus="WdatePicker({isShowClear:true,readOnly:true})" /></td>
	</tr>
	<tr>
		<td class="wordtd">��ע��</td>
		<td colspan="5"><html:textarea name="contractInvoiceManageBean" property="rem" rows="3" cols="100" styleClass="default_textarea"/></td>
	</tr>
</table>
<br/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td class="wordtd">¼���ˣ�</td>
		<td width="20%">
			<bean:write name="operName"/>
			<html:hidden name="contractInvoiceManageBean" property="operId" />
		</td>
		<td class="wordtd">¼�����ڣ�</td>
		<td><html:text name="contractInvoiceManageBean" property="operDate" readonly="true" styleClass="default_input_noborder" /></td>
	</tr>
</table>

<script type="text/javascript"> 
</script>
<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>

<html:hidden property="isreturn"/>
<html:hidden property="id"/>
<html:hidden name="accountsPayableManageBean" property="jnlNo"/>

<html:hidden name="accountsPayableManageBean" property="billNo"/>
<html:hidden name="accountsPayableManageBean" property="submitType"/>
<html:hidden name="accountsPayableManageBean" property="auditStatus"/>

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td class="wordtd" width="20%">ί�к�ͬ�ţ�</td>
		<td width="15%">
		<a onclick="simpleOpenWindow('returningMaintainDetailAction','${accountsPayableManageBean.entrustContractNo}');" class="link"><bean:write name="accountsPayableManageBean" property="entrustContractNo"/></a>
		<html:hidden name="accountsPayableManageBean" property="entrustContractNo"/></td>
		<td class="wordtd" width="15%">ί�е�λ���ƣ�</td>
		<td width="20%">
		    <bean:write name="companyName"/>
			<html:hidden name="accountsPayableManageBean" property="companyId" />
		</td>
		<td class="wordtd" width="15%">�����ֲ���</td>
		<td width="10%">
		    <bean:write name="maintDivisionName"/>
			<html:hidden name="accountsPayableManageBean" property="maintDivision" />
		</td>
	</tr>
</table>
<br/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td class="wordtd"  width="20%">��ͬ�ܶ</td>
		<td width="15%"><bean:write name="contractTotal"/></td>
		<td class="wordtd" width="15%">�ѽ�Ӧ�����</td>
		<td width="20%"><bean:write name="builtReceivables"/></td>
		<td class="wordtd" width="15%">δ��Ӧ�����</td>
		<td width="10%"><bean:write name="noBuiltReceivables"/></td>
	</tr>
</table>
<br/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td class="wordtd" width="20%">Ӧ�������ƣ�</td>
		<td width="15%">
			<html:select name="accountsPayableManageBean" property="recName">
				<html:option value="">��ѡ��</html:option>
		    	<html:options collection="receivablesList" property="recId" labelProperty="recName"/>
        	</html:select><font color="red">*</font>
			<%-- <html:text name="contractInvoiceManageBean" property="invoiceType" styleClass="default_input" /> --%>
		</td>
		<td class="wordtd" width="15%">Ӧ�����</td>
		<td width="20%"><html:text name="accountsPayableManageBean" property="preMoney" styleId="money" styleClass="default_input" onkeydown="judgePreMoney(this,'${noBuiltReceivables}');" onkeyup="judgePreMoney(this,'${noBuiltReceivables}');"  /><font color="red">*</font></td>
		<td class="wordtd" width="15%">Ӧ�������ڣ�</td>
		<td width="10%"><html:text name="accountsPayableManageBean" property="preDate" size="12" styleClass="Wdate" readonly="true" onfocus="WdatePicker({isShowClear:true,readOnly:true})" /></td>
	</tr>
	<tr>
		<td class="wordtd">��ע��</td>
		<td colspan="5"><html:textarea name="accountsPayableManageBean" property="rem" rows="3" cols="100" styleClass="default_textarea"/></td>
	</tr>
</table>
<br/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td class="wordtd" width="20%">¼���ˣ�</td>
		<td width="30%">
			<bean:write name="operName"/>
			<html:hidden name="accountsPayableManageBean" property="operId" />
		</td>
		<td class="wordtd" width="20%">¼�����ڣ�</td>
		<td width="30%"><html:text name="accountsPayableManageBean" property="operDate" readonly="true" styleClass="default_input_noborder" /></td>
	</tr>
</table>
<br/>
<logic:present name="proList">
<div id="caption_0" style="width: 100%;padding-top: 2;padding-bottom: 2;border-bottom: 0" class="tb">
  <b>&nbsp;�ѽ�Ӧ����</b>
</div>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<thead>
		<tr class="wordtd_header">
			<td width="15%">Ӧ������ˮ��</td>
			<td width="15%">Ӧ��������</td>
			<td width="15%">Ӧ������</td>
			<td width="15%">Ӧ��������</td>
			<td width="40%">��ע</td>
		</tr>
	</thead>
	<tfoot>
		<tr height="15"><td colspan="5"></td></tr>
	</tfoot>
	<tbody>
          <logic:iterate id="pro" name="proList" >
          	<tr>
				<td align="center"><bean:write name="pro" property="jnlNo"/></td>
				<td align="center"><bean:write name="pro" property="recName"/></td>
				<td align="center"><bean:write name="pro" property="preMoney"/></td>
				<td align="center"><bean:write name="pro" property="preDate"/></td>
				<td><bean:write name="pro" property="rem"/></td>
			</tr>
          </logic:iterate>
	</tbody>
</table>
</logic:present>
<script type="text/javascript"> 
</script>
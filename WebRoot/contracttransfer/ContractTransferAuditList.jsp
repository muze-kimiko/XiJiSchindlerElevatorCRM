<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/html-table.tld" prefix="table"%>
<link rel="stylesheet" type="text/css" href="<html:rewrite forward='formCSS'/>">
<script language="javascript" defer="defer" src="<html:rewrite forward='DatePickerJS'/>"></script>
<html:errors />
<br>
<html:form action="/ServeTable.do">
<html:hidden property="property(genReport)" styleId="genReport" />
	<table>
		<tr>
			<td>��ˮ�ţ�</td>
			<td><html:text property="property(billNo)" styleClass="default_input" /></td>
			<td>&nbsp;&nbsp;ά����ͬ�ţ�</td>
			<td><html:text property="property(maintContractNo)" styleClass="default_input" /></td>
			<td>&nbsp;&nbsp;���ۺ�ͬ�ţ�</td>
			<td><html:text property="property(salesContractNo)" styleClass="default_input" /></td>
			<td>&nbsp;&nbsp;�׷���λ���ƣ�</td>
			<td><html:text property="property(companyId)" size="30" styleClass="default_input" /></td>
		</tr>
		<tr>
			<td>���ݱ�ţ�</td>
			<td><html:text property="property(elevatorNo)" styleClass="default_input" /></td>
			<td>&nbsp;&nbsp;���״̬��</td>
			<td>
			<html:select property="property(auditStatus)">
	          <html:option value="">ȫ��</html:option>
			  <html:option value="N">δ���</html:option>
			  <html:option value="Y">�����</html:option>
	        </html:select>
	        </td>                   
			<td>&nbsp;&nbsp;<bean:message key="maintContract.maintDivision" />:</td>
			<td>
				<html:select property="property(maintDivision)" styleId="maintdivision" onchange="Evenmore(this,'maintstation')">
				<html:options collection="maintDivisionList" property="grcid" labelProperty="grcname"/>
				</html:select>
			</td>   
			<td>&nbsp;&nbsp;����ά��վ:</td>
			<td>
				<html:select property="property(maintStation)" styleId="maintstation">
				<html:options collection="mainStationList" property="storageid" labelProperty="storagename"/>
				</html:select>
			</td>
		</tr>
	</table>
	<br>
  <table:table id="guiContractTransferAuditList" name="contractTransferAuditList">
    <logic:iterate id="element" name="contractTransferAuditList">
      <table:define id="c_cb">
        <html:radio property="checkBoxList(ids)" styleId="ids" value="${element.billNo}" />
        <input type="hidden" name="auditStatus" value="${element.auditStatus}"/>
      </table:define>
      <table:define id="c_BillNo">
        <a href="<html:rewrite page='/ContractTransferAuditAction.do'/>?method=toPrepareAddRecord&id=<bean:write name="element"  property="billNo"/>&auditStatus=${element.auditStatus}">
          <bean:write name="element" property="billNo" />
        </a>
      </table:define>
      <table:define id="c_CompanyName">
        <bean:write name="element" property="companyId" />
      </table:define>
      <table:define id="c_MaintContractNo">
        <bean:write name="element" property="maintContractNo" />
      </table:define>
      <table:define id="c_SalesContractNo">
        <bean:write name="element" property="salesContractNo" />
      </table:define>
      <table:define id="c_ElevatorNo">
        <bean:write name="element" property="elevatorNo" />
      </table:define>
      <table:define id="c_MaintDivision">
        <bean:write name="element" property="maintDivision" />
      </table:define>
      <table:define id="c_MaintStation">
        <bean:write name="element" property="maintStation" />
      </table:define>
      <table:define id="c_ContractSDate">
        <bean:write name="element" property="contractSdate" />
      </table:define>
      <table:define id="c_ContractEDate">
        <bean:write name="element" property="contractEdate" />
      </table:define>
      <table:define id="c_auditStatus">
        <logic:match name="element" property="auditStatus" value="Y">�����</logic:match>
        <logic:match name="element" property="auditStatus" value="N">δ���</logic:match>
      </table:define>
      <table:define id="c_IsCheck">
      	<logic:notEqual name="element" property="billNo2" value="">
      	<a href="javascript:void(0);" onclick="printMethod('${element.billNo2}')">�鿴</a>
      	</logic:notEqual>
      </table:define>
      <table:tr />
    </logic:iterate>
  </table:table>
</html:form>

<script>
function printMethod(billno){
	window.open('<html:rewrite page="/elevatorTransferCaseRegisterManageAction.do"/>?id='+billno+'&method=toPreparePrintRecord');
}

</script>

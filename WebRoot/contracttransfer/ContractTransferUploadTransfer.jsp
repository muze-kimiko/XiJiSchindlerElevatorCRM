<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/html-table.tld" prefix="table"%>
<link rel="stylesheet" type="text/css" href="<html:rewrite forward='formCSS'/>">
<script language="javascript" defer="defer" src="<html:rewrite forward='DatePickerJS'/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/checkinput.js"/>"></script>
<html:errors />
<br>
<html:form action="/ContractTransferUploadAction.do?method=toTransferRecord">
<html:hidden property="isreturn"/>
<html:hidden property="billNo" value="${billNo}"/>
<html:hidden property="id"/>
<html:hidden property="transfeSubmitType"/>

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td height="23" colspan="9">&nbsp;<b>����Ϣ</td>
	</tr>
	<tr class="wordtd_header">
		<td>��ˮ��</td>
		<td>ά����ͬ��</td>
		<td>���ۺ�ͬ��</td>
		<td>�׷���λ����</td>
		<td>���ݱ��</td>
		<td>��ͬ��ʼ����</td>
		<td>��ͬ��������</td>
		<td>����ά���ֲ�</td>
		<td>����ά��վ</td>
	</tr>
	<logic:iterate id="element" name="masterList">
	<tr>
		<td >${element.billNo}</td>
		<td >${element.maintContractNo}</td>
		<td >${element.salesContractNo}</td> 
		<td >${element.companyId}</td>
		<td >${element.elevatorNo}</td>
		<td >${element.contractSdate}</td>
		<td >${element.contractEdate}</td>
		<td >${element.maintDivision}</td>
		<td >${element.maintStation}</td>
	</tr>
	</logic:iterate>
</table>
<br/>

<table width="100%" class="tb">
<tr>
<td class="wordtd" >ת���ˣ�</td>
<td width="83%">${isTransId }</td>
</tr>
<tr>
<td class="wordtd" >ת�����ڣ�</td>
<td width="83%">${isTransDate }</td>
</tr>
<tr>
<td class="wordtd" >����ά������</td>
<td width="83%">
<html:select property="wbgTransfeId">
	<html:option value="">��ѡ��</html:option>
	<html:options collection="wbgList" property="wbgId" labelProperty="wbgName" />
</html:select><span style="color: red">*</span>
</td>
</tr>
</table>

</html:form>



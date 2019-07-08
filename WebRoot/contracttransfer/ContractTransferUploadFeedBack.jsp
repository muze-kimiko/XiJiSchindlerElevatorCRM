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
<html:form action="/ContractTransferUploadAction.do?method=toFeedBackRecord" enctype="multipart/form-data">
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
<logic:iterate id="elefile" name="fileTypes">
	<tr>
		<td height="23" class="wordtd">&nbsp;�ϴ�${elefile.fileTypeName}</td>
		<td></td>
	</tr>
</logic:iterate>
</table>
<br/>

<table width="100%" border="0" cellpadding="1" cellspacing="0" class="tb">
	<tr>
		<td class="wordtd">�����ˣ�</td>
		<td>${operid}</td>
	</tr>
	<tr>
		<td class="wordtd">�������ڣ�</td>
		<td>${operdate}</td>
	</tr>
	<tr>
		<td class="wordtd">�����������ͣ�</td>
		<td>
		<html:select property="feedbacktypeid"><html:option value="">��ѡ��</html:option><html:options collection="typeList" property="feedbackTypeId" labelProperty="feedbackTypeName"/></html:select><span style="color: red">*</span>
		</td>
	</tr>
	<tr>
		<td class="wordtd">�������ݣ�</td>
		<td>
		<html:textarea property="transferRem" rows="3" cols="62"></html:textarea><span style="color: red">*</span>
		</td>
	</tr>
	<tr>
		<td class="wordtd">����������</td>
		<td>
		<table width="45%" class="tb">
			<tr class="wordtd">
				<td width="5%" align="center">
					<input type="checkbox" onclick="checkTableFileAll(this)">
				</td>
				<td align="left">&nbsp;&nbsp;<input type="button" name="toaddrow" value="+" onclick="AddRow(this)"/>
				&nbsp;<input type="button" name="todelrow" value="-" onclick="deleteFileRow(this)">
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
<br/>
</html:form>



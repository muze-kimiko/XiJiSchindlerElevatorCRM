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
			<td>&nbsp;&nbsp;�ύ��־��</td>
			<td>
			<html:select property="property(transfeSubmitType)">
	          <html:option value="">ȫ��</html:option>
			  <html:option value="N">δ�ύ</html:option>
			  <html:option value="Y">���ύ</html:option>
			  <html:option value="R">����</html:option>
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
  <table:table id="guiContractTransferUploadList" name="contractTransferUploadList">
    <logic:iterate id="element" name="contractTransferUploadList">
      <table:define id="c_cb">
      	<html:checkbox property="checkBoxList(ids)" styleId="ids" value="${element.billNo}"/>
        <input type="hidden" name="transfeSubmitType" value="${element.transfeSubmitType}"/>
        <input type="hidden" name="fileType" value="${element.fileType}"/>
        <input type="hidden" name="fFlag" value="${element.fFlag}"/>
      </table:define>
      <table:define id="c_BillNo">
        <a href="<html:rewrite page='/ContractTransferUploadAction.do'/>?method=toDisplayRecord&id=<bean:write name="element"  property="billNo"/>">
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
      <table:define id="c_TransfeSubmitType">
        <logic:match name="element" property="transfeSubmitType" value="Y">���ύ</logic:match>
        <logic:match name="element" property="transfeSubmitType" value="N">δ�ύ</logic:match>
        <logic:match name="element" property="transfeSubmitType" value="R">����</logic:match>
      </table:define>
      <table:define id="c_IsCheck">
      	<logic:notEqual name="element" property="billNo2" value="">
      	<a href="javascript:void(0);" onclick="printMethod('${element.billNo2}')">�鿴</a>
      	</logic:notEqual>
      </table:define>
       <table:define id="c_OperDate">
        <bean:write name="element" property="operdate" />
      </table:define>
      <table:define id="c_debugfile">
      <table width="100%" border="0">
      	<logic:present name="element" property="fileList">
			<logic:iterate id="files" name="element" property="fileList">
				<logic:equal name="files" property="trflag1" value="Y">
				<tr>
				<td nowrap="nowrap">
				</logic:equal>
				<a style="cursor:hand;text-decoration: underline;color: blue;" onclick="downloadFile('${files.newFileName}','${files.oldFileName}','${files.filePath}','${files.folder }')">${files.oldFileName}</a>
				<logic:equal name="files" property="trflag2" value="Y">
				</td>
				</tr>
				</logic:equal>
	      	</logic:iterate>
		</logic:present>
      </table>
      </table:define>
      <table:tr />
    </logic:iterate>
  </table:table>
</html:form>

<script>
function printMethod(billno){
	window.open('<html:rewrite page="/elevatorTransferCaseRegisterManageAction.do"/>?id='+billno+'&method=toPreparePrintRecord');
}

//���ظ���
function downloadFile(name,oldName,filePath,folder){
	var uri = '<html:rewrite page="/ContractTransferUploadAction.do"/>?method=toDownloadFile';
	var name1=encodeURI(name);
	name1=encodeURI(name1);
	var oldName1=encodeURI(oldName);
	oldName1=encodeURI(oldName1);
	filePath=encodeURI(filePath);
	filePath=encodeURI(filePath);
	    uri +='&filePath='+ filePath;
		uri +='&filesname='+ name1;
		uri +='&folder='+folder;
		uri+='&fileOldName='+oldName1;
	window.location = uri;
	//window.open(url);
}

</script>

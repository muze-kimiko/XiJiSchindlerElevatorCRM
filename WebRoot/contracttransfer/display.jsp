<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td height="23" colspan="6">&nbsp;<b>����Ϣ</td>
	</tr>
	<tr>
		<td class="wordtd">��ˮ�ţ�</td>
		<td width="22%">
			<html:text name="contractTransferMasterBean" property="billNo" readonly="true" styleClass="default_input_noborder"/>
		</td>
		<td class="wordtd">ά����ͬ�ţ�</td>
		<td width="20%">
			<html:text name="contractTransferMasterBean" property="maintContractNo" readonly="true" styleClass="default_input_noborder"/>
		</td>
		<td class="wordtd">���ۺ�ͬ�ţ�</td>
		<td width="20%">
			<html:text name="contractTransferMasterBean" property="salesContractNo" readonly="true" styleClass="default_input_noborder"/>
		</td>  
	</tr>
	<tr>
		<td class="wordtd">�׷���λ���ƣ�</td>
		<td width="20%">
			${contractTransferMasterBean.companyId}
		</td>
		<td class="wordtd">���ݱ�ţ�</td>
		<td width="20%">
			<html:text name="contractTransferMasterBean" property="elevatorNo" readonly="true" styleClass="default_input_noborder"/>
		</td>
		<td class="wordtd">����ά���ֲ���</td>
		<td width="20%">
			<html:text name="contractTransferMasterBean" property="maintDivision" readonly="true" styleClass="default_input_noborder"/>
		</td>  
	</tr>
	<tr>
		<td class="wordtd">��ͬ��ʼ���ڣ�</td>
		<td width="20%">
			<html:text name="contractTransferMasterBean" property="contractSdate" readonly="true" styleClass="default_input_noborder"/>
		</td>
		<td class="wordtd">��ͬ�������ڣ�</td>
		<td width="20%">
			<html:text name="contractTransferMasterBean" property="contractEdate" readonly="true" styleClass="default_input_noborder"/>
		</td>
		<td class="wordtd">����ά��վ��</td>
		<td width="20%">
			<html:text name="contractTransferMasterBean" property="maintStation" readonly="true" styleClass="default_input_noborder"/>
		</td>  
	</tr>
</table>
<br/>
<table width="100%" class="tb">
<logic:iterate id="ele" name="fileTypes">
	<tr>
		<td width="15%" height="23" class="wordtd">&nbsp;�ϴ�${ele.fileTypeName}</td>
		<td>
			<logic:present name="ele" property="fileList">
			<table width="100%" border="0" cellpadding="1" cellspacing="2">
				<logic:iterate id="element2" name="ele" property="fileList">
					<tr>
					<td style="border-right-style:none;border-left-style:none;border-top-style:none;border-bottom-style:none;">
						<a style="cursor:hand;text-decoration: underline;color: blue;" onclick="downloadFile('${element2.newFileName}','${element2.oldFileName}','${element2.filePath}','ContractTransferFileinfo.file.upload.folder')">${element2.oldFileName}</a>
					</td>
					</tr>
				</logic:iterate>
			</table>
			</logic:present>
		</td>
	</tr>
</logic:iterate>
</table>
<br/>

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td height="23" colspan="5">&nbsp;<b>��������</td>
	</tr>
	<tr>
		<td width="10%" class="wordtd_header">������</td>
		<td width="12%" class="wordtd_header">��������</td>
		<td width="10%" class="wordtd_header">������������</td>
		<td width="45%" class="wordtd_header">��������</td>
		<td width="23%" class="wordtd_header">��������</td>
	</tr>
	<logic:iterate id="ele" name="feedbackList">
	<tr>
		<td style="text-align: center;">${ele.operId}</td>
		<td style="text-align: center;">${ele.operDate}</td>
		<td style="text-align: center;">${ele.feedbacktypeid}</td>
		<td style="text-align: center;word-wrap:break-word;"><span style="width: 400px">${ele.transferRem}</span></td>
		<td>
		<table width="100%" border="0" cellpadding="1" cellspacing="2">
		<logic:present name="ele" property="fileList">
			<logic:iterate id="element2" name="ele" property="fileList">
				<tr>
					<td style="border-right-style:none;border-left-style:none;border-top-style:none;border-bottom-style:none;">
						<a style="cursor:hand;text-decoration: underline;color: blue;" onclick="downloadFile('${element2.newFileName}','${element2.oldFileName}','${element2.filePath}','ContractTransferFeedbackFileinfo.file.upload.folder')">${element2.oldFileName}</a>
					</td>
				</tr>
			</logic:iterate>
		</logic:present>
		</table>
		</td>
	</tr>
	</logic:iterate>
</table>

<br/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td height="23" colspan="6">&nbsp;<b>�ɹ���Ϣ</td>
	</tr>
	<tr>
		<td class="wordtd">�ɹ��ˣ�</td>
		<td width="22%">
			<html:text name="contractTransferMasterBean" property="operId" readonly="true" styleClass="default_input_noborder"/>
		</td>
		<td class="wordtd">�ɹ�״̬��</td>
		<td width="20%">
			<logic:match name="contractTransferMasterBean" property="submitType" value="Y">���ύ</logic:match>
	        <logic:match name="contractTransferMasterBean" property="submitType" value="N">δ�ύ</logic:match>
	        <logic:match name="contractTransferMasterBean" property="submitType" value="R">����</logic:match>
		</td>
		<td class="wordtd">�ɹ����ڣ�</td>
		<td width="20%">
			<html:text name="contractTransferMasterBean" property="operDate" readonly="true" styleClass="default_input_noborder"/>
		</td>  
	</tr>
</table>

<br/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td height="23" colspan="6">&nbsp;<b>�ϴ���Ϣ</td>
	</tr>
	<tr>
		<td class="wordtd">�ϴ��ˣ�</td>
		<td width="22%">
			<html:text name="contractTransferMasterBean" property="transfeId" readonly="true" styleClass="default_input_noborder"/>
		</td>
		<td class="wordtd">�ϴ�״̬��</td>
		<td width="20%">
			<logic:match name="contractTransferMasterBean" property="transfeSubmitType" value="Y">���ύ</logic:match>
	        <logic:match name="contractTransferMasterBean" property="transfeSubmitType" value="N">δ�ύ</logic:match>
	        <logic:match name="contractTransferMasterBean" property="transfeSubmitType" value="R">����</logic:match>
		</td>
		<td class="wordtd">�ϴ����ڣ�</td>
		<td width="20%">
			<html:text name="contractTransferMasterBean" property="transfeDate" readonly="true" styleClass="default_input_noborder"/>
		</td>  
	</tr>
	<tr>
		<td class="wordtd">�ϴ����������</td>
		<td width="22%" colspan="5">
			${contractTransferMasterBean.transferRem}
		</td>
	</tr>
</table>

<logic:notEqual name="addflag2" value="Y">
<br/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td height="23" colspan="6">&nbsp;<b>ά��������Ա�����Ϣ</td>
	</tr>
	<tr>
		<td class="wordtd">������Ա����ˣ�</td>
		<td width="22%">
			<html:text name="contractTransferMasterBean" property="auditOperid2" readonly="true" styleClass="default_input_noborder"/>
		</td>
		<td class="wordtd">������Ա���״̬��</td>
		<td width="20%">
			<logic:equal name="contractTransferMasterBean" property="auditStatus2" value="Y">�����</logic:equal>
	        <logic:notEqual name="contractTransferMasterBean" property="auditStatus2" value="Y">δ���</logic:notEqual>
		</td>
		<td class="wordtd">������Ա������ڣ�</td>
		<td width="20%">
			<html:text name="contractTransferMasterBean" property="auditDate2" readonly="true" styleClass="default_input_noborder"/>
		</td>  
	</tr>
	<tr>
		<td class="wordtd">������Ա��������</td>
		<td width="22%" colspan="5">
			${contractTransferMasterBean.auditRem2}
		</td>
	</tr>
</table>
</logic:notEqual>


<logic:notEqual name="addflag" value="Y">
<br/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td height="23" colspan="6">&nbsp;<b>�����Ϣ</td>
	</tr>
	<tr>
		<td class="wordtd">����ˣ�</td>
		<td width="22%">
			<html:text name="contractTransferMasterBean" property="auditOperid" readonly="true" styleClass="default_input_noborder"/>
		</td>
		<td class="wordtd">���״̬��</td>
		<td width="20%">
			<logic:match name="contractTransferMasterBean" property="auditStatus" value="Y">�����</logic:match>
	        <logic:match name="contractTransferMasterBean" property="auditStatus" value="N">δ���</logic:match>
		</td>
		<td class="wordtd">������ڣ�</td>
		<td width="20%">
			<html:text name="contractTransferMasterBean" property="auditDate" readonly="true" styleClass="default_input_noborder"/>
		</td>  
	</tr>
	<tr>
		<td class="wordtd">��������</td>
		<td width="22%" colspan="5">
			${contractTransferMasterBean.auditRem}
		</td>
	</tr>
</table>
</logic:notEqual>

<script>

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
<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"";
%>
<link rel="stylesheet" type="text/css" href="<html:rewrite forward='formCSS'/>">
<br>
<html:errors/>
<html:form action="/elevatorTransferCaseRegisterJbpmAction.do?method=toSaveApprove" enctype="multipart/form-data">
<html:hidden property="id"/>
<html:hidden name="elevatorTransferCaseRegisterBean" property="billno"/>
<html:hidden property="processStatus"/>
<html:hidden property="isreturn"/>
<html:hidden property="tokenId" styleId="tokenId"/>
<html:hidden property="taskId"/>
<html:hidden property="taskName" styleId="taskName"/>
<html:hidden property="tasktype"/>
<a href="" id="avf" target="_blank"></a>
<html:hidden property="status" styleId="status"/>
<html:hidden property="flowname" styleId="flowname"/>
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
   <tr>
   <td nowrap="nowrap" class="wordtd"  >��ͬ���ͣ�</td>
   	<td>
   	<logic:match name="elevatorTransferCaseRegisterBean" property="contractType" value="XS">���ۺ�ͬ</logic:match>
   	<logic:match name="elevatorTransferCaseRegisterBean" property="contractType" value="WG">ά�ĺ�ͬ</logic:match>
   	</td>
   	<td nowrap="nowrap" class="wordtd" >���������</td> 
   	<td>
   		<html:text name="elevatorTransferCaseRegisterBean" property="checkNum" styleId="checkNum" readonly="true" styleClass="default_input_noborder" />
   	</td> 
   	<td nowrap="nowrap" class="wordtd" >&nbsp;</td>
   	<td>&nbsp;</td> 
   	</tr>
   	<tr>
   	<td nowrap="nowrap" class="wordtd" >ʵ�ʳ���ʱ�䣺</td>
   	<td>
   		${elevatorTransferCaseRegisterBean.checkDate }
   	</td>
   	<td nowrap="nowrap" class="wordtd" >�ύ����ʱ�䣺</td>
   	<td><bean:write name="elevatorTransferCaseRegisterBean" property="checkTime" /></td>
   	 <td nowrap="nowrap" class="wordtd" >&nbsp;</td>
   	<td>&nbsp;</td> 
   </tr>
   <tr>
   	<td nowrap="nowrap" class="wordtd">���ݱ�ţ�</td>
   	<td>
   		<html:text name="elevatorTransferCaseRegisterBean" property="elevatorNo" styleId="elevatorNo" readonly="true" styleClass="default_input_noborder" />
   	</td>
   	<td nowrap="nowrap" class="wordtd">��ͬ�ţ�</td>
   	<td><html:text name="elevatorTransferCaseRegisterBean" property="salesContractNo" styleId="salesContractNo" readonly="true" styleClass="default_input_noborder" /></td>
   
   	<td nowrap="nowrap" class="wordtd">��Ŀ���ƣ�</td>
   	<td><html:text name="elevatorTransferCaseRegisterBean" property="projectName" styleId="projectName" readonly="true" styleClass="default_input_noborder" /></td>
   </tr>
   <tr>
   	<td nowrap="nowrap" class="wordtd">��Ŀ��ַ��</td>
   	<td>${elevatorTransferCaseRegisterBean.projectAddress }
   	<%-- <html:text name="elevatorTransferCaseRegisterBean" property="projectAddress" styleId="projectAddress" readonly="true" styleClass="default_input_noborder" size="50" /> --%></td>
   	<td nowrap="nowrap" class="wordtd">�������ͣ�</td>
   	<td><logic:match name="elevatorTransferCaseRegisterBean" property="elevatorType" value="T">ֱ��</logic:match>
   	<logic:match name="elevatorTransferCaseRegisterBean" property="elevatorType" value="F">����</logic:match>
   	<html:hidden name="elevatorTransferCaseRegisterBean" property="elevatorType" styleId="elevatorType"/>
   	</td>
   	<td nowrap="nowrap" class="wordtd">����ͺţ�</td>
   	<td><html:text name="elevatorTransferCaseRegisterBean" property="elevatorParam" styleId="elevatorParam" readonly="true" styleClass="default_input_noborder" /></td>
   </tr>
   <tr>
   	<td class="wordtd">��ͬ���ʣ�</td>
   <td>
   	<html:text name="elevatorTransferCaseRegisterBean" property="salesContractType" styleId="salesContractType" readonly="true" styleClass="default_input_noborder" /></td>
   	<td nowrap="nowrap" class="wordtd">��/վ/�ţ�</td>
   	<td><bean:write name="elevatorTransferCaseRegisterBean" property="floor"/>/<bean:write name="elevatorTransferCaseRegisterBean" property="stage"/>/<bean:write name="elevatorTransferCaseRegisterBean" property="door"/>
   	</td>
   	<td nowrap="nowrap" class="wordtd">�����߶ȣ�</td>
   	<td>
   	 <html:text name="elevatorTransferCaseRegisterBean" property="high" styleId="high" readonly="true" styleClass="default_input_noborder" /></td>
   	 </tr>
   <tr>
   	<td nowrap="nowrap" class="wordtd">���أ�</td>
   	<td><html:text name="elevatorTransferCaseRegisterBean" property="weight" styleId="weight" readonly="true" styleClass="default_input_noborder" /></td>
   	<td nowrap="nowrap" class="wordtd">��ٶȣ�</td>
   	<td><html:text name="elevatorTransferCaseRegisterBean" property="speed" styleId="speed" readonly="true" styleClass="default_input_noborder" /></td>  	
   	<td nowrap="nowrap" class="wordtd">��Ŀʡ�ݣ�</td>
   	<td>${elevatorTransferCaseRegisterBean.projectProvince }</td>
   </tr>   
   <tr>
   		<td nowrap="nowrap"  class="wordtd">�Ƿ�Ѹ�ﰲװ��</td>
   	<td>
   	<logic:match name="elevatorTransferCaseRegisterBean" property="isxjs" value="Y">��</logic:match>
   	<logic:match name="elevatorTransferCaseRegisterBean" property="isxjs" value="N">��</logic:match>
   	</td>
   	
   	<td nowrap="nowrap" class="wordtd">��װ��˾���ƣ�</td>
   	<td>${elevatorTransferCaseRegisterBean.insCompanyName }
   	<%-- <html:text name="elevatorTransferCaseRegisterBean" property="insCompanyName" size="40" readonly="true" styleClass="default_input_noborder"  /> --%></td>
   	<td nowrap="nowrap" class="wordtd">��װ��˾��ϵ�˺͵绰��</td>
   	<td><html:text name="elevatorTransferCaseRegisterBean" property="insLinkPhone" readonly="true" styleClass="default_input_noborder"  /></td>
   	</tr>  
   <tr>
   <td nowrap="nowrap" class="wordtd">�������ţ�</td>
   	<td>
   		<html:text name="elevatorTransferCaseRegisterBean" property="department" readonly="true" styleClass="default_input_noborder" />
   	</td>
   <td nowrap="nowrap" class="wordtd">������Ա���ƣ�</td>
   	<td>
   		<html:text name="elevatorTransferCaseRegisterBean" property="staffName" readonly="true" styleClass="default_input_noborder" />
   	</td>
   	<td nowrap="nowrap" class="wordtd">������Ա��ϵ�绰��</td>
   	<td><html:text name="elevatorTransferCaseRegisterBean" property="staffLinkPhone" readonly="true" styleClass="default_input_noborder" /></td>        
   </tr>
   <tr>
   	<td nowrap="nowrap" class="wordtd">��Ŀ�����������绰��</td>
   	<td><bean:write name="elevatorTransferCaseRegisterBean" property="projectManager"/></td>
   	<td nowrap="nowrap" class="wordtd">������Ա�������绰��</td>
   	<td><bean:write name="elevatorTransferCaseRegisterBean" property="debugPers"/></td>
    <td nowrap="nowrap"  class="wordtd">�Ƿ���ΰ�װ��</td>
   	<td>
   	<logic:equal name="elevatorTransferCaseRegisterBean" property="firstInstallation" value="Y">��</logic:equal>
   	<logic:equal name="elevatorTransferCaseRegisterBean" property="firstInstallation" value="N">��</logic:equal>
   	<html:hidden name="elevatorTransferCaseRegisterBean" property="firstInstallation" styleId="firstInstallation"/>
   	</td>
   </tr>
    <tr>          
   	<td nowrap="nowrap" class="wordtd">��������</td>
   	<td ><html:text name="elevatorTransferCaseRegisterBean" property="factoryCheckResult" readonly="true" styleClass="default_input_noborder" /></td> 
   	<td nowrap="nowrap" class="wordtd">���������</td>
   	<td>
   		<logic:equal name="elevatorTransferCaseRegisterBean" property="checkNum" value="1">
	   		<html:text name="elevatorTransferCaseRegisterBean" property="r2" readonly="true" styleClass="default_input_noborder" />
	   	</logic:equal>
	   	<logic:notEqual name="elevatorTransferCaseRegisterBean" property="checkNum" value="1">
	   		<html:text name="elevatorTransferCaseRegisterBean" property="r2" readonly="true" value="" styleClass="default_input_noborder" />
	   	</logic:notEqual>
   	</td> 
   	<td nowrap="nowrap" class="wordtd">������2��</td>
   	<td ><html:text name="elevatorTransferCaseRegisterBean" property="factoryCheckResult2" readonly="true" styleClass="default_input_noborder" /></td> 
   	
   	</tr>
   	<tr>
   	<td nowrap="nowrap" class="wordtd">����λ�ã�</td>
   	<td colspan="5">${elevatorTransferCaseRegisterBean.elevatorAddress }</td> 
   	</tr>
   	<tr>
   	<td class="wordtd">¼���ˣ�</td>
   	<td><bean:write name="elevatorTransferCaseRegisterBean" property="operId"/></td>
   	<td class="wordtd">¼�����ڣ�</td>
   	<td><bean:write name="elevatorTransferCaseRegisterBean" property="operDate"/></td>
    <td class="wordtd">�������ڣ�</td>
   	<td><bean:write name="elevatorTransferCaseRegisterBean" property="receiveDate"/></td>
   </tr>
</table>
<br/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td nowrap="nowrap" class="wordtd">����Ҫ��</td>
		<td>
			<table id="searchCompany"  style="border:none;" >
               <%int specialno=1; %>
        		<logic:present name="specialRegister">
					<tr>
					  <logic:iterate id="element" name="specialRegister">
						<logic:match name="element" property="isOk" value="Y">
							<td style="border:none;" width="5%">
							<logic:match name="element" property="isOk" value="Y">
							<input type="checkbox" disabled="disabled" checked="checked">
							</logic:match>
                            <logic:match name="element" property="isOk" value="N">
                             <input type="checkbox" disabled="disabled" >                                        
                            </logic:match></td>
							<td style="border:none;">
								<bean:write name="element" property="r1"/>
								<input type="hidden" name="scId" value="${element.scId }"/>
								<input type="hidden" name="scjnlno" value="${element.jnlno }"/>
							</td>
						</logic:match>
							<% if(specialno%4==0){ %></tr><tr><%} %>
                            <%specialno++; %>
					</logic:iterate>
					</tr>
				</logic:present>
        	</table>
		</td>
	</tr>
</table>
<br>
<%int i=1,j=0; %>
<logic:equal name="taskName" value="���첿�����">
<logic:notPresent name="display">
<div id="caption_0" style="width: 100%;padding-top: 2;padding-bottom: 2;border-bottom: 0" class="tb">       
  <b>&nbsp;����������(��ɫΪ©������)</b>
</div>
<table id="party_a" width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<thead>
		<tr class="wordtd_header">
		    <td width="5%">����</td>
			<td width="10%">�������</td>
			<td width="10%">�����Ŀ</td>
			<td width="10%">������</td>
			<td width="35%">��������</td>
			<td width="15%">��ע</td>
			<td width="15%">����</td>
			<td width="20%" nowrap="nowrap">�Ƿ�������</td>
		</tr>
	</thead>
	
   <tfoot>
      <tr height="15"><td colspan="7"><input type="hidden" id="noDeleteLength"></td></tr>
    </tfoot>
    <tbody>
    <logic:present name="hecirList">
    <logic:iterate id="ele" name="hecirList">
      <tr id="tr_<%=i %>">
             <td><input type="hidden" name="deleteBtn"> <input type="button" value="ɾ��" onclick="deleteRow('tr_<%=i %>')"><input type="hidden" name="isDelete" value="N"></td>
			 <td style="color: ${ele.color}" class="${ele.color}">${ele.examTypeName}
			 <input type="hidden" name="examType" value="${ele.examType}">
			 <input type="hidden" name="jnlno" value="${ele.jnlno}">
			 <input type="hidden" name="isRecheck" value="${ele.isRecheck}">
			 <input type="hidden" name="itemgroup" value="${ele.itemgroup}">
			 </td>
			 <td>${ele.checkItem}</td>
	         <td>${ele.issueCoding}</td>
	         <td>${ele.issueContents1}</td>
	         <td>${ele.rem}</td>
	         <td style="valign: top;">${ele.fileListsize}
	           <table width="100%" class="tb">
				<logic:present name="ele" property="fileList">
					<logic:iterate id="element2" name="ele" property="fileList">
						<logic:notEqual name="element2" property="ext1" value="Y">
						<tr>
						<td>
							<a style="cursor:hand;text-decoration: underline;color: blue;" onclick="downloadFile('${element2.newFileName}','${element2.oldFileName}','${element2.filePath}')">${element2.oldFileName}</a>
						</td>
						</tr>
						</logic:notEqual>
					</logic:iterate>
				</logic:present>
				</table>
	         </td>
	         <td align="center">
	         	<logic:equal name="ele" property="isyzg" value="Y">������</logic:equal>
	         	<input type="hidden" name="isyzg" value="${ele.isyzg}"/>
	         </td>
	         <td style="display: none;"><textarea cols="30" rows="2" name="deleteRem"></textarea></td>
	         <td style="display: none;">
	         	<table width="100%" class="tb">
				<tr class="wordtd"><td width="5%" align="center"><input type="checkbox" onclick="checkTableFileAll(this)"></td>
				<td align="left">&nbsp;&nbsp;<input type="button" name="toaddrow" value=" + " onclick="AddRow(this,'Y','${ele.checkItem}','${ele.examType}','${ele.issueCoding}')"/>
				&nbsp;<input type="button" name="todelrow" value=" - " onclick="deleteFileRow(this)">
				</td></tr>
				</table>
	         </td>
	    <%i++;%> 
	    </tr>    
      </logic:iterate>
    </logic:present>
	</tbody>
</table>
<br/>
<logic:present name="hecirDeketeList">
<table id="party_b"  width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<thead>
		<tr class="wordtd_header">
			 <td width="5%">����</td>
			<td width="10%">�������</td>
			<td width="8%">�����Ŀ</td>
			<td width="8%">������</td>
			<td width="15%">��������</td>
			<td width="7%">��ע</td>
			<td width="10%">����</td>
			<td width="10%">�Ƿ�������</td>
			<td width="10%">ɾ��ԭ��<font style="color: red">*</font></td>
			<td width="22%" nowrap="nowrap">ɾ��ԭ�򸽼�</td>
		</tr>
		</thead>
		<tfoot>
      <tr height="15"><td colspan="9"></td></tr>
      </tfoot>
       <tbody>
          <logic:iterate id="eleDelete" name="hecirDeketeList">
            <tr id="tr_<%=i %>">
             <td ><input type="hidden" name="deleteBtn"> <input type="button" value="ɾ��" onclick="returnDeleteRow('tr_<%=i %>')"><input type="hidden" name="isDelete" value="Y"></td>
			 <td  style="color: ${eleDelete.color}" class="${eleDelete.color}">${eleDelete.examTypeName}
			 <input type="hidden" name="examType" value="${eleDelete.examType}">
			 <input type="hidden" name="jnlno" value="${eleDelete.jnlno}">
			 <input type="hidden" name="isRecheck" value="${eleDelete.isRecheck}">
			 <input type="hidden" name="itemgroup" value="${eleDelete.itemgroup}">
			 </td>
			 <td >${eleDelete.checkItem}</td>
	         <td >${eleDelete.issueCoding}</td>
	         <td >${eleDelete.issueContents1}</td>
	         <td >${eleDelete.rem}</td>
	         <td ><table width="100%" class="tb">
				<logic:present name="eleDelete" property="fileList">
					<logic:iterate id="element2" name="eleDelete" property="fileList">
						<logic:notEqual name="element2" property="ext1" value="Y">
						<tr>
						<td>
							<a style="cursor:hand;text-decoration: underline;color: blue;" onclick="downloadFile('${element2.newFileName}','${element2.oldFileName}','${element2.filePath}')">${element2.oldFileName}</a>
						</td></tr>
						</logic:notEqual>
					</logic:iterate>
				</logic:present>
				</table>
				</td>
	         <td align="center">
	         	<logic:equal name="eleDelete" property="isyzg" value="Y">������</logic:equal>
	         	<input type="hidden" name="isyzg" value="${eleDelete.isyzg}"/>
	         </td>
			 <td><textarea cols="30" rows="2" name="deleteRem">${eleDelete.deleteRem}</textarea></td>
			 <td>
	         	<table width="100%" class="tb">
				<tr class="wordtd"><td width="5%" align="center"><input type="checkbox" onclick="checkTableFileAll(this)"></td>
				<td align="left">&nbsp;&nbsp;<input type="button" name="toaddrow" value="+" onclick="AddRow(this,'Y','${eleDelete.checkItem}','${eleDelete.examType}','${eleDelete.issueCoding}')"/>
				&nbsp;<input type="button" name="todelrow" value="-" onclick="deleteFileRow(this)">
				</td></tr>
				<logic:present name="eleDelete" property="fileList">
					<logic:iterate id="element2" name="eleDelete" property="fileList">
						<logic:match name="element2" property="ext1" value="Y">
						<tr>
						<td></td>
						<td>
							<a style="cursor:hand;text-decoration: underline;color: blue;" onclick="downloadFile('${element2.newFileName}','${element2.oldFileName}','${element2.filePath}')">${element2.oldFileName}</a>
				      	<a style="cursor:hand;" onclick="deleteFile(this,'${element2.fileSid}','N')"><img src="../../common/images/toolbar/del_attach.gif" alt="<bean:message key="delete.button.value"/>" /></a>
				      	<%j++; %>
						</td></tr>
						</logic:match>
					</logic:iterate>
				</logic:present>
				</table><br>
	         </td>
			<%i++;%> 
      </tr>
      </logic:iterate>
      </tbody>
</table>
<br/>
</logic:present>
<logic:notPresent name="hecirDeketeList">
<table id="party_b" style="display: none;"  width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<thead>
		<tr class="wordtd_header">
			 <td width="5%">����</td>
			<td width="10%">�������</td>
			<td width="8%">�����Ŀ</td>
			<td width="8%">������</td>
			<td width="15%">��������</td>
			<td width="7%">��ע</td>
			<td width="10%">����</td>
			<td width="10%">�Ƿ�������</td>
			<td width="10%">ɾ��ԭ��<font style="color: red">*</font></td>
			<td width="22%" nowrap="nowrap">ɾ��ԭ�򸽼�</td>
		</tr>
		</thead>
		<tfoot>
      <tr height="15"><td colspan="7"></td></tr>
      </tfoot>
</table>
<br/>
</logic:notPresent>
</logic:notPresent>
<logic:present name="display">
<div id="caption_0" style="width: 100%;padding-top: 2;padding-bottom: 2;border-bottom: 0" class="tb">
  <b>&nbsp;����������(��ɫΪ©������)</b>
</div>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<thead>
		<tr class="wordtd_header">
			<td width="10%">�������</td>
			<td width="10%">�����Ŀ</td>
			<td width="10%">������</td>
			<td width="35%">��������</td>
			<td width="20%">��ע</td>
			<td width="10%">����</td>
			<td width="20%" nowrap="nowrap">�Ƿ�������</td>
		</tr>
	</thead>
	<tfoot>
      <tr height="15"><td colspan="8"></td></tr>
    </tfoot>
    <logic:present name="hecirList">
    <logic:iterate id="ele" name="hecirList">
      <tr> 
			 <td style="color: ${ele.color}">${ele.examTypeName}</td>
			 <td >${ele.checkItem}</td>
	         <td >${ele.issueCoding}</td>
	         <td >${ele.issueContents1}</td>
	         <td >${ele.rem}</td>
	         <td >
	         	  <table width="100%" class="tb">
				<logic:present name="ele" property="fileList">
					<logic:iterate id="element2" name="ele" property="fileList">
						<logic:notEqual name="element2" property="ext1" value="Y">
						<tr>
						<td>
							<a style="cursor:hand;text-decoration: underline;color: blue;" onclick="downloadFile('${element2.newFileName}','${element2.oldFileName}','${element2.filePath}')">${element2.oldFileName}</a>
						</td></tr>
						</logic:notEqual>
					</logic:iterate>
				</logic:present>
				</table>
	         </td>
	         <td align="center">
	         	<logic:equal name="ele" property="isyzg" value="Y">������</logic:equal>
	         	<input type="hidden" name="isyzg" value="${ele.isyzg}"/>
	         </td>
	         <td style="display: none;"><input name="deleteRem" type="text"></td>
      </tr>
      </logic:iterate>
    </logic:present>
</table>
<br> 
<logic:present name="hecirDeketeList">
   <div id="caption_0" style="width: 100%;padding-top: 2;padding-bottom: 2;border-bottom: 0" class="tb">
  <b>&nbsp;���첿��ɾ������</b>
</div>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<thead>
		<tr class="wordtd_header">
			<td width="10%">�������</td>
			<td width="10%">�����Ŀ</td>
			<td width="10%">������</td>
			<td width="20%">��������</td>
			<td width="15%">��ע</td>
			<td width="10%">����</td>
			<td width="10%">�Ƿ�������</td>
			<td width="10%">ɾ��ԭ��</td>
			<td width="10%" nowrap="nowrap">ɾ��ԭ�򸽼�</td>
		</tr>
	</thead>
	<tfoot>
      <tr height="15"><td colspan="6"></td></tr>
    </tfoot>
    <logic:iterate id="eleDelete" name="hecirDeketeList">
      <tr>
			 <td style="color: ${eleDelete.color}">${eleDelete.examTypeName}</td>
			 <td >${eleDelete.checkItem}</td>
	         <td >${eleDelete.issueCoding}</td>
	         <td >${eleDelete.issueContents1}</td>
	         <td >${eleDelete.rem}</td>
	         <td >  <table width="100%" class="tb">
				<logic:present name="eleDelete" property="fileList">
					<logic:iterate id="element2" name="eleDelete" property="fileList">
						<logic:notEqual name="element2" property="ext1" value="Y">
						<tr>
						<td>
							<a style="cursor:hand;text-decoration: underline;color: blue;" onclick="downloadFile('${element2.newFileName}','${element2.oldFileName}','${element2.filePath}')">${element2.oldFileName}</a>
						</td></tr>
						</logic:notEqual>
					</logic:iterate>
				</logic:present>
				</table></td>
	         <td align="center">
	         	<logic:equal name="eleDelete" property="isyzg" value="Y">������</logic:equal>
	         	<input type="hidden" name="isyzg" value="${eleDelete.isyzg}"/>
	         </td>
		<td>${eleDelete.deleteRem}<input type="hidden" name="deleteRem" value="${eleDelete.deleteRem}"></td>
		<td>
			<table width="100%" class="tb">
				<logic:present name="eleDelete" property="fileList">
					<logic:iterate id="element2" name="eleDelete" property="fileList">
						<logic:match name="element2" property="ext1" value="Y">
						<tr>
						<td>
							<a style="cursor:hand;text-decoration: underline;color: blue;" onclick="downloadFile('${element2.newFileName}','${element2.oldFileName}','${element2.filePath}')">${element2.oldFileName}</a>
						</td></tr>
						</logic:match>
					</logic:iterate>
				</logic:present>
				</table>
		</td>
      </tr>
      </logic:iterate>
</table>
<br>
 </logic:present>
</logic:present>
</logic:equal>
<logic:notEqual name="taskName" value="���첿�����">
<div id="caption_0" style="width: 100%;padding-top: 2;padding-bottom: 2;border-bottom: 0" class="tb">
  <b>&nbsp;����������(��ɫΪ©������)</b>
</div>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<thead>
		<tr class="wordtd_header">
			<td width="10%">�������</td>
			<td width="10%">�����Ŀ</td>
			<td width="10%">������</td>
			<td width="35%">��������</td>
			<td width="20%">��ע</td>
			<td width="10%">����</td>
			<td width="20%" nowrap="nowrap">�Ƿ�������</td>
		</tr>
	</thead>
	<tfoot>
      <tr height="15"><td colspan="6"></td></tr>
    </tfoot>
    <logic:present name="hecirList">
    <logic:iterate id="ele" name="hecirList">
      <tr>
			 <td style="color: ${ele.color}">${ele.examTypeName}</td>
			 <td >${ele.checkItem}</td>
	         <td >${ele.issueCoding}</td>
	         <td >${ele.issueContents1}</td>
	         <td >${ele.rem}</td>
	         <td >
	         	  <table width="100%" class="tb">
				<logic:present name="ele" property="fileList">
					<logic:iterate id="element2" name="ele" property="fileList">
						<logic:notEqual name="element2" property="ext1" value="Y">
						<tr>
						<td>
							<a style="cursor:hand;text-decoration: underline;color: blue;" onclick="downloadFile('${element2.newFileName}','${element2.oldFileName}','${element2.filePath}')">${element2.oldFileName}</a>
						</td></tr>
						</logic:notEqual>
					</logic:iterate>
				</logic:present>
				</table>
	         </td>
	         
	         <td align="center">
	         	<logic:equal name="ele" property="isyzg" value="Y">������</logic:equal>
	         	<input type="hidden" name="isyzg" value="${ele.isyzg}"/>
	         </td>
      </tr>
      </logic:iterate>
    </logic:present>
</table>
<br> 
<logic:present name="hecirDeketeList">
   <div id="caption_0" style="width: 100%;padding-top: 2;padding-bottom: 2;border-bottom: 0" class="tb">
  <b>&nbsp;���첿��ɾ������</b>
</div>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<thead>
		<tr class="wordtd_header">
			<td width="10%">�������</td>
			<td width="10%">�����Ŀ</td>
			<td width="10%">������</td>
			<td width="20%">��������</td>
			<td width="15%">��ע</td>
			<td width="10%">����</td>
			<td width="10%">�Ƿ�������</td>
			<td width="10%">ɾ��ԭ��</td>
			<td width="10%" nowrap="nowrap">ɾ��ԭ�򸽼�</td>
		</tr>
	</thead>
	<tfoot>
      <tr height="15"><td colspan="8"></td></tr>
    </tfoot>
    <logic:iterate id="eleDelete" name="hecirDeketeList">
      <tr>
			 <td style="color: ${eleDelete.color}">${eleDelete.examTypeName}</td>
			 <td >${eleDelete.checkItem}</td>
	         <td >${eleDelete.issueCoding}</td>
	         <td >${eleDelete.issueContents1}</td>
	         <td >${eleDelete.rem}</td>
	         <td >  <table width="100%" class="tb">
				<logic:present name="eleDelete" property="fileList">
					<logic:iterate id="element2" name="eleDelete" property="fileList">
						<logic:notEqual name="element2" property="ext1" value="Y">
						<tr>
						<td>
							<a style="cursor:hand;text-decoration: underline;color: blue;" onclick="downloadFile('${element2.newFileName}','${element2.oldFileName}','${element2.filePath}')">${element2.oldFileName}</a>
						</td></tr>
						</logic:notEqual>
					</logic:iterate>
				</logic:present>
				</table></td>
				
	         <td align="center">
	         	<logic:equal name="eleDelete" property="isyzg" value="Y">������</logic:equal>
	         	<input type="hidden" name="isyzg" value="${eleDelete.isyzg}"/>
	         </td>
			<td>${eleDelete.deleteRem}<input type="hidden" name="deleteRem" value="${eleDelete.deleteRem}"></td>
			<td>
			<table width="100%" class="tb">
			<logic:present name="eleDelete" property="fileList">
					<logic:iterate id="element3" name="eleDelete" property="fileList">
						<logic:match name="element3" property="ext1" value="Y">
						<tr>
						<td>
							<a style="cursor:hand;text-decoration: underline;color: blue;" onclick="downloadFile('${element3.newFileName}','${element3.oldFileName}','${element3.filePath}')">${element3.oldFileName}</a>
						</td>
						</tr>
						</logic:match>
					</logic:iterate>
				</logic:present>
				</table>
			</td>
      </tr>
      </logic:iterate>
</table>
<br>
 </logic:present>
</logic:notEqual>
  <table  width="99%" border="0" cellpadding="0" cellspacing="0" class="tb" >
 <tr>
 <td class="wordtd">�ͻ�ǩ��:</td>
    <td class="inputtd">
    <logic:notEmpty name="elevatorTransferCaseRegisterBean" property="customerSignature">
		<img src="<%=basePath%>/elevatorTransferCaseRegisterDisplayAction.do?method=toDownloadFileRecord1&customerSignature=${elevatorTransferCaseRegisterBean.customerSignature}"
			width="${CSwidth}" height="${CSheight}" id="${elevatorTransferCaseRegisterBean.customerSignature}_1"> 
	</logic:notEmpty>
	<logic:notEmpty name="elevatorTransferCaseRegisterBean" property="customerImage">
		<img src="<%=basePath%>/elevatorTransferCaseRegisterDisplayAction.do?method=toDownloadFileRecord1&customerImage=${elevatorTransferCaseRegisterBean.customerImage}"
			width="${CIwidth}" height="${CIheight}" id="${elevatorTransferCaseRegisterBean.customerImage}_2">
	</logic:notEmpty>
    </td>
     </tr>

</table>
 </br>
<logic:notPresent name="display">
		<logic:match name="taskName" value="���첿�����">
		<div id="caption_0" style="width: 100%;padding-top: 2;padding-bottom: 2;border-bottom: 0" class="tb">
		  <b>&nbsp;���첿���ϴ�����</b>
		</div>
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
		<tr>
			<td nowrap="nowrap" class="wordtd" width="10%">��ע:</td>
			<td><html:textarea name="elevatorTransferCaseRegisterBean" property="checkRem" styleId="checkRem" styleClass="default_textarea" cols="90" rows="3"/></td>
		</tr>
		<tr>
			<td nowrap="nowrap" class="wordtd" width="10%">����:</td>
			<td>
			<table width="65%" class="tb">
				<tr class="wordtd"><td width="5%" align="center"><input type="checkbox" onclick="checkTableFileAll(this)"></td>
				<td align="left">&nbsp;&nbsp;<input type="button" name="toaddrow" value="+" onclick="AddRow2(this)"/>
				&nbsp;<input type="button" name="todelrow" value="-" onclick="deleteFileRow(this)">
				</td></tr>
				<logic:present name="wbfilelist">
					<logic:iterate id="wbele" name="wbfilelist">
						<tr>
						<td>&nbsp;</td>
						<td>
							<a style="cursor:hand;text-decoration: underline;color: blue;" onclick="downloadFile('${wbele.newFileName}','${wbele.oldFileName}','${wbele.filePath}')">${wbele.oldFileName}</a>
				      		<a style="cursor:hand;" onclick="deleteFile(this,'${wbele.fileSid}','Y')"><img src="../../common/images/toolbar/del_attach.gif" alt="<bean:message key="delete.button.value"/>" /></a>
				      		<%i++; %>
						</td></tr>
					</logic:iterate>
				</logic:present>
			</table>
			<br/>
			</td>
		</tr>
		</table><br>
		</logic:match>
		<logic:notEqual value="���첿�����" name="taskName">
			<div id="caption_0" style="width: 100%;padding-top: 2;padding-bottom: 2;border-bottom: 0" class="tb">
			  <b>&nbsp;���첿���ϴ�����</b>
			</div>
			<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="tb">
			<tr>
				<td nowrap="nowrap" class="wordtd" width="10%">��ע:</td>
				<td><bean:write name="elevatorTransferCaseRegisterBean" property="checkRem"/></td>
			</tr>
			<tr>
				<td nowrap="nowrap" class="wordtd" width="10%">����:</td>
				<td>
				<table width="45%" class="tb">
					<logic:present name="wbfilelist">
						<logic:iterate id="wbele" name="wbfilelist">
							<tr><td>
								<a style="cursor:hand;text-decoration: underline;color: blue;" onclick="downloadFile('${wbele.newFileName}','${wbele.oldFileName}','${wbele.filePath}')">${wbele.oldFileName}</a>
							</td></tr>
						</logic:iterate>
					</logic:present>
				</table>
				</td>
			</tr>
			</table><br>
		</logic:notEqual>
		
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
			<tr>
			<td nowrap="nowrap" class="wordtd" width="10%">�Ƿ�ۿ�:</td>
			<td>
			<logic:match name="taskName" value="���첿�����">
				<html:select property="isDeductions" styleId="isDeductions" styleClass="default_input">
		          	<html:option value="">��ѡ��</html:option>
		          	<html:option value="Y">ͬ��ۿ�</html:option>
	          		<html:option value="N">ͬ�ⲻ�ۿ�</html:option>
		        </html:select><font color="red">*</font>
		     </logic:match>
		     <logic:notEqual value="���첿�����" name="taskName">
		     	<logic:present name="elevatorTransferCaseRegisterBean"  property="isDeductions">
		     		<logic:match name="elevatorTransferCaseRegisterBean" property="isDeductions" value="Y">ͬ��ۿ�</logic:match>
					<logic:match name="elevatorTransferCaseRegisterBean" property="isDeductions" value="N">ͬ�ⲻ�ۿ�</logic:match>
				</logic:present>
			</logic:notEqual>
			</td>
			</tr>
			<tr>
				<td nowrap="nowrap" class="wordtd" width="10%">�ۿ���(Ԫ):</td>
				<td id="td1">
				<logic:match name="taskName" value="��װ�������">
				<script language="javascript">
				
					window.onload=function(){
						var approveResult=document.getElementById("approveResult");
						isDeductions(approveResult,'${elevatorTransferCaseRegisterBean.isDeductions}');
					}
				</script>
				</logic:match>
				<logic:notEqual value="װ�������" name="taskName">
					<bean:write name="elevatorTransferCaseRegisterBean" property="deductMoney"/>
				</logic:notEqual>
				</td>
			</tr>
			<tr>
				<td nowrap="nowrap" class="wordtd" width="10%">�ۿע:</td>
				<td>
				<logic:match name="taskName" value="ִ�пۿ�">
					<html:textarea property="processResult" styleId="processResult" styleClass="default_textarea" cols="90" rows="2"/>
					<font color="red">*</font>
				</logic:match>
				<logic:notEqual value="ִ�пۿ�" name="taskName">
					<bean:write name="elevatorTransferCaseRegisterBean" property="processResult"/>
				</logic:notEqual>
				</td>
			</tr>
			<tr>
			<td nowrap="nowrap" class="wordtd" width="10%">�رձ�ע:</td>
				<td>
				<logic:match name="taskName" value="�ر�����">
				 <textarea rows="2" cols="90" class="default_textarea" name="colseRem" id="colseRem"></textarea> 
				</logic:match>
				<logic:notEqual value="�ر�����" name="taskName">
					${elevatorTransferCaseRegisterBean.colseRem }
				</logic:notEqual>
				</td>
			</tr>
		</table>
		<br/>
		
<div style="width: 100%;padding-top: 2;padding-bottom: 2;border-bottom: 0" class="tb">
  <b>&nbsp;����</b>
</div>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td nowrap="nowrap" class="wordtd">���������</td>
		<td>
		<logic:notEqual value="��װ�������" name="taskName">
			<html:select property="approveResult" styleId="approveResult" styleClass="default_input">
	          <html:options collection="ResultList" property="tranname" labelProperty="tranname"/>
	        </html:select>
	     </logic:notEqual>
	     <logic:match name="taskName" value="��װ�������">
	     	<html:select property="approveResult" styleId="approveResult" styleClass="default_input" onchange="isDeductions(this,'${elevatorTransferCaseRegisterBean.isDeductions}');">
	          <html:options collection="ResultList" property="tranname" labelProperty="tranname"/>
	        </html:select>
	     </logic:match>
		</td>
	</tr>
	<tr>
		<td nowrap="nowrap" class="wordtd">���������</td>
		<td>
			<html:textarea property="approveRem" styleId="approveRem" styleClass="default_textarea" cols="90" rows="3"/>
		</td>
	</tr>
</table>
</logic:notPresent>
<logic:equal name="display" value="Y">

	<div id="caption_0" style="width: 100%;padding-top: 2;padding-bottom: 2;border-bottom: 0" class="tb">
	  <b>&nbsp;���첿���ϴ�����</b>
	</div>
	<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td nowrap="nowrap" class="wordtd" width="10%">��ע:</td>
		<td><bean:write name="elevatorTransferCaseRegisterBean" property="checkRem"/></td>
	</tr>
	<tr>
		<td nowrap="nowrap" class="wordtd" width="10%">����:</td>
		<td>
			
			<table width="45%" class="tb">
				<logic:present name="wbfilelist">
					<logic:iterate id="wbele" name="wbfilelist">
						<tr><td>
							<a style="cursor:hand;text-decoration: underline;color: blue;" onclick="downloadFile('${wbele.newFileName}','${wbele.oldFileName}','${wbele.filePath}')">${wbele.oldFileName}</a>
						</td></tr>
					</logic:iterate>
				</logic:present>
			</table>
		</td>
	</tr>
	</table><br>

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
<tr>
	<td nowrap="nowrap" class="wordtd">�Ƿ�ۿ�:</td>
	<td>
		<logic:present name="elevatorTransferCaseRegisterBean"  property="isDeductions">
			<logic:match name="elevatorTransferCaseRegisterBean" property="isDeductions" value="Y">ͬ��ۿ�</logic:match>
			<logic:match name="elevatorTransferCaseRegisterBean" property="isDeductions" value="N">ͬ�ⲻ�ۿ�</logic:match>
		</logic:present>
	</td>
	</tr>
	<tr>
	<td nowrap="nowrap" class="wordtd">�ۿ���(Ԫ):</td>
	<td>
		<bean:write name="elevatorTransferCaseRegisterBean" property="deductMoney"/>
	</td>
	</tr>
	<tr>
	<td nowrap="nowrap" class="wordtd">�ۿע:</td>
	<td>
		<bean:write name="elevatorTransferCaseRegisterBean" property="processResult"/>
	</td>
	</tr>
	
<tr>
	<td nowrap="nowrap" class="wordtd">�رձ�ע:</td>
	<td>
		
		${elevatorTransferCaseRegisterBean.colseRem }
	</td>
   </tr>
</table>
<br>
</logic:equal>
<br/>

<logic:present name="etcpList">
	<div id="caption_0" style="width: 100%;padding-top: 2;padding-bottom: 2;border-bottom: 0" class="tb">
	  &nbsp;<b>&nbsp;��������</b>
	</div>	
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
		<tr class="wordtd_header">  <td class="wordtd" nowrap><div align="center">�����</div></td>
      <td class="wordtd" nowrap><div align="center">��������</div></td> 
      <td class="wordtd" nowrap><div align="center">������</div></td>
      <td class="wordtd" nowrap><div align="center">�������</div></td>
      <td class="wordtd" nowrap><div align="center">��������</div></td>
      <td class="wordtd" nowrap><div align="center">�������</div></td>
      </tr>
		<logic:iterate id="element4" name="etcpList" >
			<tr>
			    <td><bean:write name="element4" property="taskId" /></td>
				<td><bean:write name="element4" property="taskName" /></td>
				<td><bean:write name="element4" property="userId" /></td>
				<td><bean:write name="element4" property="approveRem" /></td>
				<td><bean:write name="element4" property="date1"/></td>
				<td><bean:write name="element4" property="approveResult" /></td>
			</tr>
		</logic:iterate>
	</table>
</logic:present>
</html:form>
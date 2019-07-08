<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<link rel="stylesheet" type="text/css" href="<html:rewrite forward='formCSS'/>">
<br>
<html:errors/>
<html:form action="/elevatorTransferCaseRegisterAction.do?method=toDisposeRecord" enctype="multipart/form-data">
<html:hidden property="id"/>
<html:hidden name="elevatorTransferCaseRegisterBean" property="billno"/>
<html:hidden property="processStatus"/>
<html:hidden property="isreturn"/>
<html:hidden property="hecirs" />

  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
  <td nowrap="nowrap" class="wordtd" width="10%">��ͬ���ͣ�</td>
   	<td width="20%">
   		<logic:present name="elevatorTransferCaseRegisterBean" property="contractType">
   			<logic:match name="elevatorTransferCaseRegisterBean" property="contractType" value="XS">���ۺ�ͬ</logic:match>
        	<logic:match name="elevatorTransferCaseRegisterBean" property="contractType" value="WG">ά�ĺ�ͬ</logic:match>
        </logic:present>
   	</td>
   	<td nowrap="nowrap" class="wordtd" width="10%" >����ʱ�䣺</td>
   	<td width="20%">
   		<html:text name="elevatorTransferCaseRegisterBean" property="checkTime" size="23" styleClass="Wdate" onfocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"></html:text>
   	<%-- <bean:write name="elevatorTransferCaseRegisterBean" property="checkTime"/> --%>
   	</td>
   	<td nowrap="nowrap" class="wordtd" width="10%" >���������</td>
   	<td nowrap="nowrap" width="20%" ><html:text name="elevatorTransferCaseRegisterBean" property="checkNum" styleId="checkNum" readonly="true" styleClass="default_input_noborder"/></td>   
   </tr>
   <tr>
   	<td nowrap="nowrap" class="wordtd">���ݱ�ţ�</td>
   	<td><bean:write name="elevatorTransferCaseRegisterBean" property="elevatorNo"/></td>
   	<td nowrap="nowrap" class="wordtd">��ͬ�ţ�</td>
   	<td><bean:write name="elevatorTransferCaseRegisterBean" property="salesContractNo"/></td>
   	<td nowrap="nowrap" class="wordtd">��Ŀ���ƣ�</td>
   	<td><bean:write name="elevatorTransferCaseRegisterBean" property="projectName"/></td>
   </tr>
   <tr>
   	<td nowrap="nowrap" class="wordtd">��Ŀ��ַ��</td>
   	<td><bean:write name="elevatorTransferCaseRegisterBean" property="projectAddress"/></td>
   	<td nowrap="nowrap" class="wordtd">�������ͣ�</td>
   	<td>
   		<logic:match name="elevatorTransferCaseRegisterBean" property="elevatorType" value="T">ֱ��</logic:match>
   		<logic:match name="elevatorTransferCaseRegisterBean" property="elevatorType" value="F">����</logic:match>
   	</td>
   	<td nowrap="nowrap" class="wordtd">����ͺţ�</td>
   	<td><bean:write name="elevatorTransferCaseRegisterBean" property="elevatorParam"/></td>
   </tr>
   <tr>
   	<td class="wordtd">��ͬ���ʣ�</td>
   <td><bean:write name="elevatorTransferCaseRegisterBean" property="salesContractType"/></td>
   	<td nowrap="nowrap" class="wordtd">��/վ/�ţ�</td>
   	<td><bean:write name="elevatorTransferCaseRegisterBean" property="floor"/>/<bean:write name="elevatorTransferCaseRegisterBean" property="stage"/>/<bean:write name="elevatorTransferCaseRegisterBean" property="door"/>
   	</td>
   	<td nowrap="nowrap" class="wordtd">�����߶ȣ�</td>
   	<td><bean:write name="elevatorTransferCaseRegisterBean" property="high"/></td>
   	 </tr>
   <tr>
   	<td nowrap="nowrap" class="wordtd">���أ�</td>
   	<td><bean:write name="elevatorTransferCaseRegisterBean" property="weight"/></td>
   	<td nowrap="nowrap" class="wordtd">��ٶȣ�</td>
   	<td><bean:write name="elevatorTransferCaseRegisterBean" property="speed"/></td>  	
   	<td nowrap="nowrap" class="wordtd">��Ŀʡ�ݣ�</td>
   	<td>${elevatorTransferCaseRegisterBean.projectProvince }</td>
   </tr>   
   <tr>
   		<td nowrap="nowrap"  class="wordtd">�Ƿ�Ѹ�ﰲװ��</td>
   	<td>
   		<logic:present name="elevatorTransferCaseRegisterBean" property="isxjs">
   			<logic:match name="elevatorTransferCaseRegisterBean" property="isxjs" value="Y">��</logic:match>
        	<logic:match name="elevatorTransferCaseRegisterBean" property="isxjs" value="N">��</logic:match>
        </logic:present>
   	</td>
   	
   	<td nowrap="nowrap" class="wordtd">��װ��˾���ƣ�</td>
   	<td><bean:write name="elevatorTransferCaseRegisterBean" property="insCompanyName"/></td>
   	<td nowrap="nowrap" class="wordtd">��װ��˾��ϵ�˺͵绰��</td>
   	<td><bean:write name="elevatorTransferCaseRegisterBean" property="insLinkPhone"/></td>
   	</tr>  
   <tr>
   <td nowrap="nowrap" class="wordtd">�������ţ�</td>
   	<td><bean:write name="elevatorTransferCaseRegisterBean" property="department"/></td>
   <td nowrap="nowrap" class="wordtd">������Ա���ƣ�</td>
   	<td><bean:write name="elevatorTransferCaseRegisterBean" property="staffName"/></td>
   	<td nowrap="nowrap" class="wordtd">������Ա��ϵ�绰��</td>
   	<td><bean:write name="elevatorTransferCaseRegisterBean" property="staffLinkPhone"/></td>
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
   	<td ><html:text name="elevatorTransferCaseRegisterBean" property="factoryCheckResult" readonly="true" styleClass="default_input_noborder"/></td>
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
   	
    <tr>
   	<td nowrap="nowrap" class="wordtd">����λ�ã�</td>
   	<td colspan="5">
   	<textarea rows="2" cols="90" name="elevatorAddress" id="elevatorAddress">${elevatorTransferCaseRegisterBean.elevatorAddress }</textarea><font color="red">*</font>
   	</td> 
   	</tr>
  <tr>
   <td nowrap="nowrap" class="wordtd">¼���ˣ�</td>
   	<td><bean:write name="elevatorTransferCaseRegisterBean" property="operId"/></td>
   	<td nowrap="nowrap" class="wordtd">¼�����ڣ�</td>
   	<td><bean:write name="elevatorTransferCaseRegisterBean" property="operDate"/></td>
   	<td nowrap="nowrap" class="wordtd">�������ڣ�</td>
   	<td><bean:write name="elevatorTransferCaseRegisterBean" property="receiveDate"/></td>
   </tr>
<logic:notEqual value="" name="elevatorTransferCaseRegisterBean" property="bhRem">
	<tr>
		<td nowrap="nowrap" class="wordtd">�������ڣ�</td>
		<td><bean:write name="elevatorTransferCaseRegisterBean" property="bhDate"/></td>
		<td nowrap="nowrap" class="wordtd">���ط��ࣺ</td>
		<td>${elevatorTransferCaseRegisterBean.bhType }</td>
		<td class="wordtd">����ԭ��</td>
		<td><bean:write name="elevatorTransferCaseRegisterBean" property="bhRem" /></td>
		<td></td>
	</tr>
</logic:notEqual>
<logic:notEqual value="" name="elevatorTransferCaseRegisterBean" property="transferId">
		<tr>
			<td nowrap="nowrap" class="wordtd">ת���ˣ�</td>
			<td><bean:write name="elevatorTransferCaseRegisterBean" property="transferId"/></td>
			<td nowrap="nowrap" class="wordtd">ת�����ڣ�</td>
			<td><bean:write name="elevatorTransferCaseRegisterBean" property="transferDate"/></td>
			<td nowrap="nowrap" class="wordtd"></td>
			<td></td>
		</tr>
</logic:notEqual>
</table>
<br/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td nowrap="nowrap" class="wordtd">����Ҫ��</td>
		<td>
			<table id="searchCompany" style="border: 0;margin: 0;" class="tb">
        		<tr>
              <%int specialno=1; %>
        		<logic:present name="specialRegister">
					  <logic:iterate id="element" name="specialRegister">
							<td nowrap="nowrap"  style="border: none;" width="5%">
							<logic:match name="element" property="isOk" value="Y">
							<input type="checkbox" name="isCheck" onclick="isOkCheck()" checked="checked">
							</logic:match>
                            <logic:match name="element" property="isOk" value="N">
                             <input type="checkbox" name="isCheck" onclick="isOkCheck()">                                        
                            </logic:match>
                            </td>
							<td style="border: none;">
								<bean:write name="element" property="r1"/>
								<input type="hidden" name="isOk" value="${element.isOk }">
								<input type="hidden" name="scId" value="${element.scId }"/>
								<input type="hidden" name="scjnlno" value="${element.jnlno }"/>
							</td>
						
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
<% int i=0;int j=0;%>
 <div id="caption_0" style="width: 100%;padding-top: 2;padding-bottom: 2;border-bottom: 0" class="tb">
  &nbsp;<input type="button" value=" + " onclick="addElevators('important','<%=i %>','${elevatorTransferCaseRegisterBean.elevatorType}','');" class="default_input">
  <input type="button" value=" - " onclick="deleteRow('important');isQualified('${elevatorTransferCaseRegisterBean.elevatorType}',${elevatorTransferCaseRegisterBean.checkNum});" class="default_input">
  &nbsp;<input type="button" value=" ���� " onclick="addElevators('important','<%=i %>','${elevatorTransferCaseRegisterBean.elevatorType}','Y');" class="default_input">           
  <b>&nbsp;����������(��ɫΪ©������)</b>
</div>
<table id="important" width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<thead>
		<tr class="wordtd_header">
			<td width="5%"><input type="checkbox" name="cbAll" onclick="checkTableAll('important',this)"/></td>
			<td width="10%">�������</td>
			<td width="8%">�����Ŀ</td>
			<td width="8%">������</td>
			<td width="25%">��������</td>
			<td width="13%">��ע</td>
			<td width="22%">����</td>
			<td width="22%">�Ƿ�������</td>
		</tr>
	</thead>
	<tbody>
		<logic:present name="hecirList">
          <logic:iterate id="element1" name="hecirList" >
          	<tr id="party_1" name="party_1">
          	 <td align="center"><input type="checkbox" onclick="cancelCheckAll(this)"/></td>
          	 <td style="color: ${element1.color}">
	          	 <input type="hidden" name="examType"value="${element1.examType}" />
	          	 <input type="hidden" name="isRecheck" value="${element1.isRecheck}" />
	          	 <input type="hidden" name="itemgroup" value="${element1.itemgroup}" />
          	 	${element1.examTypeName}
          	 </td>
	         <td >${element1.checkItem}<input type="hidden" name="jnlno"value="${element1.jnlno}" /><input type="hidden" name="checkItem"value="${element1.checkItem}" /></td>
	         <td align="center">${element1.issueCoding}<input type="hidden" name="issueCoding"value="${element1.issueCoding}" /></td>
	         <td>${element1.issueContents1}</td>
	         <td>
	         <input type="hidden" name="issueContents" value="${element1.issueContents1}" class="default_input" />
	         <textarea name="rem" cols="20" rows="2">${element1.rem}</textarea>
	         </td>
	         <td>
	         	<table width="100%" class="tb">
				<tr class="wordtd"><td width="5%" align="center"><input type="checkbox" onclick="checkTableFileAll(this)"></td>
				<td align="left">&nbsp;&nbsp;<input type="button" name="toaddrow" value="+" onclick="AddRow(this,'${element1.numno}','${element1.checkItem}','${element1.examType}','${element1.issueCoding}')"/>
				&nbsp;<input type="button" name="todelrow" value="-" onclick="deleteFileRow(this)">
				</td></tr>
				<logic:present name="element1" property="fileList">
					<logic:iterate id="element2" name="element1" property="fileList">
						<tr>
						<td></td>
						<td>
							<a style="cursor:hand;text-decoration: underline;color: blue;" onclick="downloadFile('${element2.newFileName}','${element2.oldFileName}','${element2.filePath}')">${element2.oldFileName}</a>
				      	<a style="cursor:hand;" onclick="deleteFile(this,'${element2.fileSid}','${element2.filePath}')"><img src="../../common/images/toolbar/del_attach.gif" alt="<bean:message key="delete.button.value"/>" /></a>
				      	<%i++; %>
						</td></tr>
					</logic:iterate>
				</logic:present>
				</table><br>
	         </td>
	         <td align="center">
		         <logic:equal name="elevatorTransferCaseRegisterBean" property="firstInstallation" value="Y">
			    	<select name="isyzg" id="isyzg" onchange="isQualified('${elevatorTransferCaseRegisterBean.elevatorType}',${elevatorTransferCaseRegisterBean.checkNum});">
		              	<option value="" <logic:notEqual name="element1" property="isyzg" value="">selected</logic:notEqual> >��ѡ��</option>
		              	<option value="Y" <logic:equal name="element1" property="isyzg" value="Y">selected</logic:equal> >������</option>
	              	</select>
              	</logic:equal>
              	<logic:notEqual name="elevatorTransferCaseRegisterBean" property="firstInstallation" value="Y">
              		<input type="hidden" name="isyzg" value="" />
              	</logic:notEqual>
	         </td>
	         
	        </tr>
          </logic:iterate>
        </logic:present>
	</tbody>
	<input type="hidden" name="file_length" value="<%=i %>" />
</table> 
<div id="caption_1" style="width: 100%;padding-top: 0;padding-bottom: 2;border-top:0 none #ffffff;border-bottom: 1 solid #999999;" class="tb">
</div>
<script type="text/javascript">
window.onload=function(){
	isQualified('${elevatorTransferCaseRegisterBean.elevatorType}',${elevatorTransferCaseRegisterBean.checkNum});
}
</script> 

</html:form>
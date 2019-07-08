<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<link rel="stylesheet" type="text/css" href="<html:rewrite forward='formCSS'/>">
<script language="javascript" defer="defer" src="<html:rewrite forward='DatePickerJS'/>"></script>
<br>
<html:errors/>
<html:form action="/elevatorTransferCaseRegisterJbpmAction.do?method=toSaveApproveModify" enctype="multipart/form-data">
<html:hidden property="id"/>
<html:hidden name="elevatorTransferCaseRegisterBean" property="billno"/>
<html:hidden property="processStatus"/>
<html:hidden property="isreturn"/>
<html:hidden property="hecirs" />
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
   		<html:text property="checkDate" styleId="checkDate" styleClass="Wdate" size="22" onfocus="WdatePicker({readOnly:true,isShowClear:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${elevatorTransferCaseRegisterBean.checkDate }"/>
   		<font color="red">*</font>
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
   	<td><html:text name="elevatorTransferCaseRegisterBean" property="projectProvince" styleId="projectProvince" styleClass="default_input" /><font color='red'>*</font></td>  	
   	
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
   	<td><html:text name="elevatorTransferCaseRegisterBean" property="factoryCheckResult" readonly="true" styleClass="default_input_noborder" /></td> 
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
   	<td colspan="5">
   	<textarea rows="2" cols="90" name="elevatorAddress" id="elevatorAddress">${elevatorTransferCaseRegisterBean.elevatorAddress }</textarea>
   	</td> 
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
	         <td align="center">${element1.checkItem}<input type="hidden" name="jnlno"value="${element1.jnlno}" /><input type="hidden" name="checkItem"value="${element1.checkItem}" /></td>
	         <td align="center">${element1.issueCoding}<input type="hidden" name="issueCoding"value="${element1.issueCoding}" /></td>
	         <td>${element1.issueContents1}</td>
	         <td>
	         <input type="hidden" name="issueContents" value="${element1.issueContents1}" class="default_input" />
	         <textarea name="rem" cols="20" rows="2">${element1.rem}</textarea>
	         </td>
	         <td>
	         	<table width="100%" class="tb">
				<tr class="wordtd"><td width="5%" align="center"><input type="checkbox" onclick="checkTableFileAll(this)"></td>
				<td align="left">&nbsp;&nbsp;<input type="button" name="toaddrow" value="+" onclick="AddRow(this,'N','${element1.checkItem}','${element1.examType}','${element1.issueCoding}')"/>
				&nbsp;<input type="button" name="todelrow" value="-" onclick="deleteFileRow(this)">
				</td></tr>
				<logic:present name="element1" property="fileList">
					<logic:iterate id="element2" name="element1" property="fileList">
						<tr>
						<td></td>
						<td>
							<a style="cursor:hand;text-decoration: underline;color: blue;" onclick="downloadFile('${element2.newFileName}','${element2.oldFileName}','${element2.filePath}')">${element2.oldFileName}</a>
				      		<a style="cursor:hand;" onclick="deleteFile(this,'${element2.fileSid}','N')"><img src="../../common/images/toolbar/del_attach.gif" alt="<bean:message key="delete.button.value"/>" /></a>
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
<br>
<logic:notPresent name="display">
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
			<td nowrap="nowrap" class="wordtd" width="10%">�Ƿ�ۿ�:</td>
			<td>
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
				<logic:notEqual value="װ�������" name="taskName">
					<bean:write name="elevatorTransferCaseRegisterBean" property="deductMoney"/>
				</logic:notEqual>
				</td>
			</tr>
			<tr>
				<td nowrap="nowrap" class="wordtd" width="10%">�ۿע:</td>
				<td>
				<logic:notEqual value="ִ�пۿ�,�ر�����" name="taskName">
					<bean:write name="elevatorTransferCaseRegisterBean" property="processResult"/>
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


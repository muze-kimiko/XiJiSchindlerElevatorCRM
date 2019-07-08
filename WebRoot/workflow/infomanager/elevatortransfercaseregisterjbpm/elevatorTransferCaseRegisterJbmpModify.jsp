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
   <td nowrap="nowrap" class="wordtd"  >合同类型：</td>
   	<td>
   	<logic:match name="elevatorTransferCaseRegisterBean" property="contractType" value="XS">销售合同</logic:match>
   	<logic:match name="elevatorTransferCaseRegisterBean" property="contractType" value="WG">维改合同</logic:match>
   	</td>
   	<td nowrap="nowrap" class="wordtd" >厂检次数：</td> 
   	<td>
   		<html:text name="elevatorTransferCaseRegisterBean" property="checkNum" styleId="checkNum" readonly="true" styleClass="default_input_noborder" />
   	</td> 
   	<td nowrap="nowrap" class="wordtd" >&nbsp;</td>
   	<td>&nbsp;</td> 
   	</tr>
   	<tr>
   	<td nowrap="nowrap" class="wordtd" >实际厂检时间：</td>
   	<td>
   		<html:text property="checkDate" styleId="checkDate" styleClass="Wdate" size="22" onfocus="WdatePicker({readOnly:true,isShowClear:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${elevatorTransferCaseRegisterBean.checkDate }"/>
   		<font color="red">*</font>
   	</td>
   	<td nowrap="nowrap" class="wordtd" >提交厂检时间：</td>
   	<td><bean:write name="elevatorTransferCaseRegisterBean" property="checkTime" /></td>
   	 <td nowrap="nowrap" class="wordtd" >&nbsp;</td>
   	<td>&nbsp;</td> 
   </tr>
   <tr>
   	<td nowrap="nowrap" class="wordtd">电梯编号：</td>
   	<td>
   		<html:text name="elevatorTransferCaseRegisterBean" property="elevatorNo" styleId="elevatorNo" readonly="true" styleClass="default_input_noborder" />
   	</td>
   	<td nowrap="nowrap" class="wordtd">合同号：</td>
   	<td><html:text name="elevatorTransferCaseRegisterBean" property="salesContractNo" styleId="salesContractNo" readonly="true" styleClass="default_input_noborder" /></td>
   
   	<td nowrap="nowrap" class="wordtd">项目名称：</td>
   	<td><html:text name="elevatorTransferCaseRegisterBean" property="projectName" styleId="projectName" readonly="true" styleClass="default_input_noborder" /></td>
   </tr>
   <tr>
   	<td nowrap="nowrap" class="wordtd">项目地址：</td>
   	<td>${elevatorTransferCaseRegisterBean.projectAddress }
   	<%-- <html:text name="elevatorTransferCaseRegisterBean" property="projectAddress" styleId="projectAddress" readonly="true" styleClass="default_input_noborder" size="50" /> --%></td>
   	<td nowrap="nowrap" class="wordtd">电梯类型：</td>
   	<td><logic:match name="elevatorTransferCaseRegisterBean" property="elevatorType" value="T">直梯</logic:match>
   	<logic:match name="elevatorTransferCaseRegisterBean" property="elevatorType" value="F">扶梯</logic:match>
   	<html:hidden name="elevatorTransferCaseRegisterBean" property="elevatorType" styleId="elevatorType"/>
   	</td>
   	<td nowrap="nowrap" class="wordtd">规格型号：</td>
   	<td><html:text name="elevatorTransferCaseRegisterBean" property="elevatorParam" styleId="elevatorParam" readonly="true" styleClass="default_input_noborder" /></td>
   </tr>
   <tr>
   	<td class="wordtd">合同性质：</td>
   <td>
   	<html:text name="elevatorTransferCaseRegisterBean" property="salesContractType" styleId="salesContractType" readonly="true" styleClass="default_input_noborder" /></td>
   	<td nowrap="nowrap" class="wordtd">层/站/门：</td>
   	<td><bean:write name="elevatorTransferCaseRegisterBean" property="floor"/>/<bean:write name="elevatorTransferCaseRegisterBean" property="stage"/>/<bean:write name="elevatorTransferCaseRegisterBean" property="door"/>
   	</td>
   	<td nowrap="nowrap" class="wordtd">提升高度：</td>
   	<td>
   	 <html:text name="elevatorTransferCaseRegisterBean" property="high" styleId="high" readonly="true" styleClass="default_input_noborder" /></td>
   	 </tr>
   <tr>
   	<td nowrap="nowrap" class="wordtd">载重：</td>
   	<td><html:text name="elevatorTransferCaseRegisterBean" property="weight" styleId="weight" readonly="true" styleClass="default_input_noborder" /></td>
   	<td nowrap="nowrap" class="wordtd">额定速度：</td>
   	<td><html:text name="elevatorTransferCaseRegisterBean" property="speed" styleId="speed" readonly="true" styleClass="default_input_noborder" /></td>  	
   	<td nowrap="nowrap" class="wordtd">项目省份：</td>
   	<td><html:text name="elevatorTransferCaseRegisterBean" property="projectProvince" styleId="projectProvince" styleClass="default_input" /><font color='red'>*</font></td>  	
   	
   </tr>   
   <tr>
   		<td nowrap="nowrap"  class="wordtd">是否迅达安装：</td>
   	<td>
   	<logic:match name="elevatorTransferCaseRegisterBean" property="isxjs" value="Y">是</logic:match>
   	<logic:match name="elevatorTransferCaseRegisterBean" property="isxjs" value="N">否</logic:match>
   	</td>
   	
   	<td nowrap="nowrap" class="wordtd">安装公司名称：</td>
   	<td>${elevatorTransferCaseRegisterBean.insCompanyName }
   	<%-- <html:text name="elevatorTransferCaseRegisterBean" property="insCompanyName" size="40" readonly="true" styleClass="default_input_noborder"  /> --%></td>
   	<td nowrap="nowrap" class="wordtd">安装公司联系人和电话：</td>
   	<td><html:text name="elevatorTransferCaseRegisterBean" property="insLinkPhone" readonly="true" styleClass="default_input_noborder"  /></td>
   	</tr>  
   <tr>
   <td nowrap="nowrap" class="wordtd">所属部门：</td>
   	<td>
   		<html:text name="elevatorTransferCaseRegisterBean" property="department" readonly="true" styleClass="default_input_noborder" />
   	</td>
   <td nowrap="nowrap" class="wordtd">厂检人员名称：</td>
   	<td>
   		<html:text name="elevatorTransferCaseRegisterBean" property="staffName" readonly="true" styleClass="default_input_noborder" />
   	</td>
   	<td nowrap="nowrap" class="wordtd">厂检人员联系电话：</td>
   	<td><html:text name="elevatorTransferCaseRegisterBean" property="staffLinkPhone" readonly="true" styleClass="default_input_noborder" /></td>        
   </tr>
   <tr>
   	<td nowrap="nowrap" class="wordtd">项目经理姓名及电话：</td>
   	<td><bean:write name="elevatorTransferCaseRegisterBean" property="projectManager"/></td>
   	<td nowrap="nowrap" class="wordtd">调试人员姓名及电话：</td>
   	<td><bean:write name="elevatorTransferCaseRegisterBean" property="debugPers"/></td>
   	<td nowrap="nowrap"  class="wordtd">是否初次安装：</td>
    <td>
   	<logic:equal name="elevatorTransferCaseRegisterBean" property="firstInstallation" value="Y">是</logic:equal>
   	<logic:equal name="elevatorTransferCaseRegisterBean" property="firstInstallation" value="N">否</logic:equal>
   	<html:hidden name="elevatorTransferCaseRegisterBean" property="firstInstallation" styleId="firstInstallation"/>
   	</td>
   </tr>
    <tr>          
   	<td nowrap="nowrap" class="wordtd">厂检结果：</td>
   	<td><html:text name="elevatorTransferCaseRegisterBean" property="factoryCheckResult" readonly="true" styleClass="default_input_noborder" /></td> 
   	<td nowrap="nowrap" class="wordtd">厂检分数：</td>
   	<td>
   		<logic:equal name="elevatorTransferCaseRegisterBean" property="checkNum" value="1">
	   		<html:text name="elevatorTransferCaseRegisterBean" property="r2" readonly="true" styleClass="default_input_noborder" />
	   	</logic:equal>
	   	<logic:notEqual name="elevatorTransferCaseRegisterBean" property="checkNum" value="1">
	   		<html:text name="elevatorTransferCaseRegisterBean" property="r2" readonly="true" value="" styleClass="default_input_noborder" />
	   	</logic:notEqual>
   	</td> 
   	<td nowrap="nowrap" class="wordtd">厂检结果2：</td>
   	<td ><html:text name="elevatorTransferCaseRegisterBean" property="factoryCheckResult2" readonly="true" styleClass="default_input_noborder" /></td> 
   	
   	</tr>
   	<tr>
   	<td nowrap="nowrap" class="wordtd">电梯位置：</td>
   	<td colspan="5">
   	<textarea rows="2" cols="90" name="elevatorAddress" id="elevatorAddress">${elevatorTransferCaseRegisterBean.elevatorAddress }</textarea>
   	</td> 
   	</tr>
   	
   	<tr>
   	<td class="wordtd">录入人：</td>
   	<td><bean:write name="elevatorTransferCaseRegisterBean" property="operId"/></td>
   	<td class="wordtd">录入日期：</td>
   	<td><bean:write name="elevatorTransferCaseRegisterBean" property="operDate"/></td>
    <td class="wordtd">接收日期：</td>
   	<td><bean:write name="elevatorTransferCaseRegisterBean" property="receiveDate"/></td>
   </tr>
</table>
<br/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td nowrap="nowrap" class="wordtd">特殊要求：</td>
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
  &nbsp;<input type="button" value=" 复制 " onclick="addElevators('important','<%=i %>','${elevatorTransferCaseRegisterBean.elevatorType}','Y');" class="default_input">           
  <b>&nbsp;检查电梯问题(红色为漏检问题)</b>
</div>
<table id="important" width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<thead>
		<tr class="wordtd_header">
			<td width="5%"><input type="checkbox" name="cbAll" onclick="checkTableAll('important',this)"/></td>
			<td width="10%">检查类型</td>
			<td width="8%">检查项目</td>
			<td width="8%">问题编号</td>
			<td width="25%">问题内容</td>
			<td width="13%">备注</td>
			<td width="22%">附件</td>
			<td width="22%">是否已整改</td>
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
		              	<option value="" <logic:notEqual name="element1" property="isyzg" value="">selected</logic:notEqual> >请选择</option>
		              	<option value="Y" <logic:equal name="element1" property="isyzg" value="Y">selected</logic:equal> >已整改</option> 
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
	  <b>&nbsp;厂检部长上传附件</b>
	</div>
	<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td nowrap="nowrap" class="wordtd" width="10%">备注:</td>
		<td><bean:write name="elevatorTransferCaseRegisterBean" property="checkRem"/></td>
	</tr>
	<tr>
		<td nowrap="nowrap" class="wordtd" width="10%">附件:</td>
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
			<td nowrap="nowrap" class="wordtd" width="10%">是否扣款:</td>
			<td>
		     <logic:notEqual value="厂检部长审核" name="taskName">
		     	<logic:present name="elevatorTransferCaseRegisterBean"  property="isDeductions">
		     		<logic:match name="elevatorTransferCaseRegisterBean" property="isDeductions" value="Y">同意扣款</logic:match>
					<logic:match name="elevatorTransferCaseRegisterBean" property="isDeductions" value="N">同意不扣款</logic:match>
				</logic:present>
			</logic:notEqual>
			</td>
			</tr>
			<tr>
				<td nowrap="nowrap" class="wordtd" width="10%">扣款金额(元):</td>
				<td id="td1">
				<logic:notEqual value="装部长审核" name="taskName">
					<bean:write name="elevatorTransferCaseRegisterBean" property="deductMoney"/>
				</logic:notEqual>
				</td>
			</tr>
			<tr>
				<td nowrap="nowrap" class="wordtd" width="10%">扣款备注:</td>
				<td>
				<logic:notEqual value="执行扣款,关闭流程" name="taskName">
					<bean:write name="elevatorTransferCaseRegisterBean" property="processResult"/>
				</logic:notEqual>
				</td>
			</tr>
		</table>
		<br/>
		
<div style="width: 100%;padding-top: 2;padding-bottom: 2;border-bottom: 0" class="tb">
  <b>&nbsp;审批</b>
</div>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td nowrap="nowrap" class="wordtd">审批结果：</td>
		<td>
		<logic:notEqual value="安装部长审核" name="taskName">
			<html:select property="approveResult" styleId="approveResult" styleClass="default_input">
	          <html:options collection="ResultList" property="tranname" labelProperty="tranname"/>
	        </html:select>
	     </logic:notEqual>
		</td>
	</tr>
	<tr>
		<td nowrap="nowrap" class="wordtd">审批意见：</td>
		<td>
			<html:textarea property="approveRem" styleId="approveRem" styleClass="default_textarea" cols="90" rows="3"/>
		</td>
	</tr>
</table>
</logic:notPresent>
<logic:equal name="display" value="Y">
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
<tr>
	<td nowrap="nowrap" class="wordtd">是否扣款:</td>
	<td>
		<logic:present name="elevatorTransferCaseRegisterBean"  property="isDeductions">
			<logic:match name="elevatorTransferCaseRegisterBean" property="isDeductions" value="Y">同意扣款</logic:match>
			<logic:match name="elevatorTransferCaseRegisterBean" property="isDeductions" value="N">同意不扣款</logic:match>
		</logic:present>
	</td>
	</tr>
	<tr>
	<td nowrap="nowrap" class="wordtd">扣款金额(元):</td>
	<td>
		<bean:write name="elevatorTransferCaseRegisterBean" property="deductMoney"/>
	</td>
	</tr>
	<tr>
	<td nowrap="nowrap" class="wordtd">扣款备注:</td>
	<td>
		<bean:write name="elevatorTransferCaseRegisterBean" property="processResult"/>
	</td>
</tr>
<tr>
	<td nowrap="nowrap" class="wordtd">关闭备注:</td>
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
	  &nbsp;<b>&nbsp;审批流程</b>
	</div>	
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
		<tr class="wordtd_header">  <td class="wordtd" nowrap><div align="center">任务号</div></td>
      <td class="wordtd" nowrap><div align="center">任务名称</div></td> 
      <td class="wordtd" nowrap><div align="center">审批人</div></td>
      <td class="wordtd" nowrap><div align="center">审批意见</div></td>
      <td class="wordtd" nowrap><div align="center">审批日期</div></td>
      <td class="wordtd" nowrap><div align="center">审批结果</div></td>
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


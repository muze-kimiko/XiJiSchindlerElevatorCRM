<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<link rel="stylesheet" type="text/css" href="<html:rewrite forward='formCSS'/>">
<br>
<html:errors/>
<html:form action="/elevatorTransferCaseRegisterAction.do?method=toDisposeTurnRecord">
<html:hidden property="id"/>
<html:hidden name="elevatorTransferCaseRegisterBean" property="billno"/>
<html:hidden property="processStatus"/>
<html:hidden property="isreturn"/>
<html:hidden property="hecirs" />

  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
  <tr>
   <td nowrap="nowrap" class="wordtd"  >��ͬ���ͣ�</td>
   	<td>
   	<logic:match name="elevatorTransferCaseRegisterBean" property="contractType" value="XS">���ۺ�ͬ</logic:match>
   	<logic:match name="elevatorTransferCaseRegisterBean" property="contractType" value="WG">ά�ĺ�ͬ</logic:match>
   	</td>
   	<td nowrap="nowrap" class="wordtd" >����ʱ�䣺</td>
   	<td><bean:write name="elevatorTransferCaseRegisterBean" property="checkTime"/></td>
   	<td nowrap="nowrap" class="wordtd" >���������</td>
   	<td nowrap="nowrap" ><html:text style="width: 200px" name="elevatorTransferCaseRegisterBean" property="checkNum" readonly="true" styleClass="default_input_noborder" /></td>   
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
   	<td><html:text name="elevatorTransferCaseRegisterBean" property="projectAddress" styleId="projectAddress" readonly="true" styleClass="default_input_noborder" size="50" /></td>
   	<td nowrap="nowrap" class="wordtd">�������ͣ�</td>
   	<td><logic:match name="elevatorTransferCaseRegisterBean" property="elevatorType" value="T">ֱ��</logic:match>
   	<logic:match name="elevatorTransferCaseRegisterBean" property="elevatorType" value="F">����</logic:match>
   	</td>
   	<td nowrap="nowrap" class="wordtd">����ͺţ�</td>
   	<td><html:text name="elevatorTransferCaseRegisterBean" property="elevatorParam" styleId="elevatorParam" readonly="true" styleClass="default_input_noborder" /></td>
   </tr>
   <tr>
   	<td class="wordtd">��ͬ���ʣ�</td>
   <td>
   	<html:text name="elevatorTransferCaseRegisterBean" property="salesContractType" styleId="salesContractType" readonly="true" styleClass="default_input_noborder" /></td>
   	<td nowrap="nowrap" class="wordtd">��/վ/�ţ�</td>
   	<td><%-- <input type="text" id="fsd" class="default_input_noborder" value="${elevatorTransferCaseRegisterBean.r1}" readonly="true"/> --%>
   	<bean:write name="elevatorTransferCaseRegisterBean" property="floor" />/<bean:write name="elevatorTransferCaseRegisterBean" property="stage" />/<bean:write name="elevatorTransferCaseRegisterBean" property="door" />
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
   	<td><html:text name="elevatorTransferCaseRegisterBean" property="insCompanyName" size="40" styleClass="default_input_noborder"  /></td>
   	<td nowrap="nowrap" class="wordtd">��װ��˾��ϵ�˺͵绰��</td>
   	<td><html:text name="elevatorTransferCaseRegisterBean"  size="25" property="insLinkPhone" styleClass="default_input_noborder"  /></td>
   	</tr>  
   <tr>
   <td nowrap="nowrap" class="wordtd">�������ţ�</td>
   	<td>
   		<html:text name="elevatorTransferCaseRegisterBean" property="department" styleClass="default_input_noborder" />
   	</td>
   <td nowrap="nowrap" class="wordtd">������Ա���ƣ�</td>
   	<td>
   		<input name="username" id="username" readonly="readonly" class="default_input" value="${username}" />
   		<html:hidden name="elevatorTransferCaseRegisterBean" property="staffName" styleId="staffName" />
   		<input type="button" value=".." onclick="openWindowAndReturnValue2('searchStaffAction','')" class="default_input"/>
   		<font color="red">*</font>
   	</td>
   	<td nowrap="nowrap" class="wordtd">������Ա��ϵ�绰��</td>
   	<td><html:text name="elevatorTransferCaseRegisterBean" property="staffLinkPhone" styleId="staffLinkPhone" readonly="true" styleClass="default_input_noborder" /></td>        
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
   	</td>
   </tr>
    <tr>          
   	<td nowrap="nowrap" class="wordtd">��������</td>
   	<td ><html:text name="elevatorTransferCaseRegisterBean" property="factoryCheckResult" readonly="true" styleClass="default_input_noborder"></html:text></td>
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
   	
   <%--  <td nowrap="nowrap" class="wordtd">����Ҫ��</td>
   	<td colspan="3"><table id="searchCompany" style="border: none;" class="tb">
        		<%int searchno=1; %>
        		<logic:present name="specialRegister">
        		<tr>
					  <logic:iterate id="element" name="specialRegister">
							<td style="border: none;" width="5%">
							<logic:match name="element" property="isOk" value="Y">
							<input type="checkbox" disabled="disabled" checked="checked">
							</logic:match>
                            <logic:match name="element" property="isOk" value="N">
                             <input type="checkbox" disabled="disabled" >                                        
                            </logic:match></td>
							<td style="border: none;">
								<bean:write name="element" property="r1"/>
								<input type="hidden" name="scId" value="${element.scId }"/>
								<input type="hidden" name="scjnlno" value="${element.jnlno }"/>
							</td>
						<% if(searchno%2==0){ %></tr><tr><%} %>
						<%searchno++; %>
					</logic:iterate>
					</tr>
				</logic:present>
        	</table>
        	</td> --%> 
  </tr>
  
   	<tr>
   	<td nowrap="nowrap" class="wordtd">����λ�ã�</td>
   	<td colspan="5">${elevatorTransferCaseRegisterBean.elevatorAddress }</td> 
   	</tr>
  <tr>
   <td nowrap="nowrap" class="wordtd">¼���ˣ�</td>
   	<td><bean:write name="elevatorTransferCaseRegisterBean" property="operId"/></td>
   	<td nowrap="nowrap" class="wordtd">¼�����ڣ�</td>
   	<td><bean:write name="elevatorTransferCaseRegisterBean" property="operDate"/></td>
   	<td nowrap="nowrap" class="wordtd">�������ڣ�</td>
   	<td><bean:write name="elevatorTransferCaseRegisterBean" property="receiveDate"/></td>
   </tr>
	<tr>
		<td nowrap="nowrap" class="wordtd">ת���ˣ�</td>
		<td><bean:write name="elevatorTransferCaseRegisterBean" property="transferId"/></td>
		<td nowrap="nowrap" class="wordtd">ת�����ڣ�</td>
		<td><%-- bean:write name="elevatorTransferCaseRegisterBean" property="transferDate"/--%>
			<html:text name="elevatorTransferCaseRegisterBean" property="transferDate" size="23" styleClass="Wdate" onfocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"></html:text>
		</td>
		<td nowrap="nowrap" class="wordtd"></td>
		<td></td>
	</tr>
</table>
<br>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td nowrap="nowrap" class="wordtd">����Ҫ��</td>
		<td>
			<table id="searchCompany" style="border: none;" class="tb">
        		<%int searchno=1; %>
        		<logic:present name="specialRegister">
        		<tr>
					  <logic:iterate id="element" name="specialRegister">
							<td style="border: none;" width="5%">
							<logic:match name="element" property="isOk" value="Y">
							<input type="checkbox" disabled="disabled" checked="checked">
							</logic:match>
                            <logic:match name="element" property="isOk" value="N">
                             <input type="checkbox" disabled="disabled" >                                        
                            </logic:match></td>
							<td style="border: none;">
								<bean:write name="element" property="r1"/>
								<input type="hidden" name="scId" value="${element.scId }"/>
								<input type="hidden" name="scjnlno" value="${element.jnlno }"/>
							</td>
						<% if(searchno%4==0){ %></tr><tr><%} %>
						<%searchno++; %>
					</logic:iterate>
					</tr>
				</logic:present>
        	</table>
		</td>
	</tr>
</table>

<br>
<% int i=0; int j=0;%>
 <div id="caption_0" style="width: 100%;padding-top: 2;padding-bottom: 2;border-bottom: 0" class="tb">
  &nbsp;<b>&nbsp;����������</b>
</div>
<table id="important" width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<thead>
		<tr class="wordtd_header">
			<td width="5%"><input type="checkbox" name="cbAll" onclick="checkTableAll('important',this)"/></td>
			<td width="10%">�������</td>
			<td width="8%">�����Ŀ</td>
			<td width="8%">������</td>
			<td width="35%">��������</td>
			<td width="29%">��ע</td>
			<td width="5%">����</td>
		</tr>
	</thead>
	<tbody>
		<logic:present name="hecirList">
          <logic:iterate id="element1" name="hecirList" >
          	<tr id="party_1" name="party_1">
          	 <td align="center"><input type="checkbox" onclick="cancelCheckAll('important','cbAll')"/></td>
          	 <td>${element1.examTypeName}</td>
	         <td align="center">${element1.checkItem}</td>
	         <td align="center">${element1.issueCoding}</td>
	         <td>${element1.issueContents1}</td>
	         <td>${element1.issueContents}</td>
	         <td align="center">
	         	<%i++;%>
	         	<logic:notEmpty name="element1" property="appendix">
				    <logic:notEqual value="" name="element1" property="appendix">
				    <span>
				      	<a style="cursor:hand;text-decoration: underline;color: blue;" id="mtapp_<%=j %>" onclick="downloadFile('${element1.appendix}')"><bean:message key="qualitycheckmanagement.check"/></a>
				    </span>
				    </logic:notEqual>
				</logic:notEmpty>
				<%j++; %>
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
	/* setDynamicTable("important","important_0");// ���ö�̬��ɾ�б�� */
	
</script> 

</html:form>
<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<link rel="stylesheet" type="text/css" href="<html:rewrite forward='formCSS'/>">
<script language="javascript" src="<html:rewrite forward='pageJS'/>"></script>
<script type="text/javascript" src="<html:rewrite page="/common/javascript/jquery-1.9.1.min.js"/>"></script>
<br>
<html:errors/>
<html:form action="/elevatorTransferCaseRegisterAction.do?method=toDisplayRecord">
<style>
  .show{display:block;}
  .hide{display:none;}

</style>

<style type="text/css">
		*{
			font-family: SongTi_GB2312;
		}
		.divtable table{border-collapse: collapse;border: 1px outset #999999;background-color: #FFFFFF;}
        .divtable table td{font-size: 12px;border: 1px outset #999999;}
</style>
<logic:present name="elevatorTransferCaseRegisterBean">
<html:hidden name="elevatorTransferCaseRegisterBean" property="billno"/>
    <h2 style="text-align: center;">����֪ͨ��</h2>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
   <tr>
   <td nowrap="nowrap" class="wordtd" >��ͬ���ͣ�</td>
   	<td>
   	<logic:match name="elevatorTransferCaseRegisterBean" property="contractType" value="XS">���ۺ�ͬ</logic:match>
   	<logic:match name="elevatorTransferCaseRegisterBean" property="contractType" value="WG">ά�ĺ�ͬ</logic:match>
   	<input type="hidden" id="pdfstatus" value='<bean:write name="elevatorTransferCaseRegisterBean" property="processStatus"/>'>
   	</td>
   	<td nowrap="nowrap" class="wordtd" >����ʱ�䣺</td>
   	<td><bean:write name="elevatorTransferCaseRegisterBean" property="checkTime" /></td>
   	<td nowrap="nowrap" class="wordtd" >���������</td>
   	<td nowrap="nowrap" ><html:text style="width: 200px" name="elevatorTransferCaseRegisterBean" property="checkNum" readonly="true" styleClass="default_input_noborder" /></td>   
   </tr>
   <tr>
   	<td nowrap="nowrap" class="wordtd">���ݱ�ţ�</td>
   	<td>
   		<html:text name="elevatorTransferCaseRegisterBean"  property="elevatorNo" styleId="elevatorNo" readonly="true" styleClass="default_input_noborder" />
   	</td>
   	<td nowrap="nowrap" class="wordtd">��ͬ�ţ�</td>
   	<td><html:text name="elevatorTransferCaseRegisterBean" property="salesContractNo" styleId="salesContractNo" readonly="true" styleClass="default_input_noborder" /></td>
   
   	<td nowrap="nowrap" class="wordtd">��Ŀ���ƣ�</td>
   	<td>
   	${elevatorTransferCaseRegisterBean.projectName }
   	<%-- html:text name="elevatorTransferCaseRegisterBean" property="projectName" styleId="projectName" readonly="true" styleClass="default_input_noborder" /--%>
   	</td>
   </tr>
   <tr>
   	<td nowrap="nowrap" class="wordtd">��Ŀ��ַ��</td>
   	<td>
   	${elevatorTransferCaseRegisterBean.projectAddress }
   	<%-- html:text name="elevatorTransferCaseRegisterBean"  property="projectAddress" styleId="projectAddress" readonly="true" styleClass="default_input_noborder" size="50" /--%>
   	</td>
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
   	<td>${elevatorTransferCaseRegisterBean.r1}
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
   	<td nowrap="nowrap" class="wordtd"></td>
   	<td></td>
   </tr>   
   <tr>
   	<td nowrap="nowrap"  class="wordtd">�Ƿ�Ѹ�ﰲװ��</td>
   	<td>
   	<logic:match name="elevatorTransferCaseRegisterBean" property="isxjs" value="Y">��</logic:match>
   	<logic:match name="elevatorTransferCaseRegisterBean" property="isxjs" value="N">��</logic:match>
   	</td>
   	
   	<td nowrap="nowrap" class="wordtd">��װ��˾���ƣ�</td>
   	<td>
   	${elevatorTransferCaseRegisterBean.insCompanyName }
   	<%-- html:text name="elevatorTransferCaseRegisterBean" property="insCompanyName" readonly="true" size="40" styleClass="default_input_noborder"  /--%>
   	</td>
   	<td nowrap="nowrap" class="wordtd">��װ��˾��ϵ�˺͵绰��</td>
   	<td><html:text name="elevatorTransferCaseRegisterBean"  size="25" property="insLinkPhone" readonly="true" styleClass="default_input_noborder"  /></td>
   	</tr>  
   <tr>
   <td nowrap="nowrap" class="wordtd">�������ţ�</td>
   	<td>
   		<html:text name="elevatorTransferCaseRegisterBean" readonly="true" property="department" styleClass="default_input_noborder" />
   	</td>
   <td nowrap="nowrap" class="wordtd">������Ա���ƣ�</td>
   	<td>
   		<html:text name="elevatorTransferCaseRegisterBean" readonly="true" property="staffName" styleClass="default_input_noborder" />
   	</td>
   	<td nowrap="nowrap" class="wordtd">������Ա��ϵ�绰��</td>
   	<td><html:text name="elevatorTransferCaseRegisterBean" readonly="true" property="staffLinkPhone" readonly="true" styleClass="default_input_noborder" /></td>        
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
   	
   	<%-- <td nowrap="nowrap" class="wordtd">����Ҫ��</td>
   	<td colspan="3">
   	<table id="searchCompany" style="border: none;">
              	<%int i=1; %>
        		<logic:present name="specialRegister">
					 <tr>
					  <logic:iterate id="element" name="specialRegister" >
						
							<td nowrap="nowrap"  style="width: 5%;border: none;">
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
						<% if(i%2==0){ %></tr><tr><%} %>
						<%i++; %>
					</logic:iterate>
					</tr>
				</logic:present>
        	</table></td> --%> 
   	</tr>
</table>
<br>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td nowrap="nowrap" class="wordtd">����Ҫ��</td>
		<td>
			<table id="searchCompany" style="border: none;">
              	<%int i=1; %>
        		<logic:present name="specialRegister">
					 <tr>
					  <logic:iterate id="element" name="specialRegister" >
						
							<td nowrap="nowrap"  style="width: 5%;border: none;">
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
						<% if(i%2==0){ %></tr><tr><%} %>
						<%i++; %>
					</logic:iterate>
					</tr>
				</logic:present>
        	</table>
		</td>
	</tr>
</table>

<br/>
<div id="caption_0" style="width: 100%;padding-top: 2;padding-bottom: 2;border-bottom: 0" class="tb">
  <b>&nbsp;����������</b>
</div>
<table id="party_a" width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<thead>
		<tr class="wordtd_header">
			<td width="15%">�������</td>
			<td width="15%">�����Ŀ</td>
			<td width="10%">������</td>
			<td width="40%">��������</td>
			<td width="20%">��ע</td>
		</tr>
	</thead>
	<tfoot>
      <tr height="15"><td colspan="6"></td></tr>
    </tfoot>
    <logic:present name="hecirList">
    <logic:iterate id="ele" name="hecirList">
      <tr>
			 <td >${ele.examType}</td>
			 <td >${ele.checkItem}</td>
	         <td >${ele.issueCoding}</td>
	         <td >${ele.issueContents1}</td>
	         <td >${ele.issueContents}</td>
      </tr>
      </logic:iterate>
    </logic:present>
</table>
</logic:present>
</html:form>
<script>
	/**
	*ҳ�������ͱ���html�ļ�
	*/
	$(function(){
		var url1 = '<html:rewrite page="/elevatorTransferCaseRegisterManageAction.do"/>';
		 var test=document.getElementsByTagName('html')[0].innerHTML; 
		 var top=$("table.top_navigation").parent().html();	
		 var tabToolBar=$("td.table_outline3").html();
		 	$.ajax({
					url : url1,// Ҫ�����action
				data : {
					method : "createPdfFile",
					test : test,
					top : top,
					tabToolBar : tabToolBar,
				    htmlName : "ElevatorTransferCaseRegister.html"
				},// ����Ĳ����ͷ���
				type : "POST",
				dataType : "text",// ��ʲô��������
				async : "false",// �Ƿ��첽����������첽�������������ȴ�����������ؾ�ִ�������������
				cache : "false",// �Ƿ���л���
				contentType: "application/x-www-form-urlencoded; charset=utf-8", 
				success : function(arr) {

				},
				error: function(){
					
				}
			});
	})
</script>
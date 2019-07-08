<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>

<link rel="stylesheet" type="text/css" href="<html:rewrite forward='formCSS'/>">
<script language="javascript" src="<html:rewrite forward='pageJS'/>"></script>
<script language="javascript" defer="defer" src="<html:rewrite forward='DatePickerJS'/>"></script>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"";
%>

<html:form action="/maintenanceWorkPlanCompletionAction.do?method=toUpdate2Record">
<html:errors/>
<html:hidden property="isreturn"/>
<logic:present name="maintenanceWorkPlanDetailBean">
    <table  width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
    <tr>
      <td nowrap="nowrap" class="wordtd">����վ:</td>
      <td  width="30%">
       <bean:write name="mapBean"  property="storagename"/>
       <html:hidden name="maintenanceWorkPlanDetailBean" property="numno"/>
      </td>          
      <td nowrap="nowrap" class="wordtd">ά����:</td>
      <td  width="30%">
      <bean:write name="mapBean"  property="username"/>
      </td>        
    </tr>  
    <tr>
      <td class="wordtd">��ͬ��:</td>
      <td>
        <a href="<html:rewrite page='/maintContractAction.do'/>?method=toDisplayRecord&typejsp=Yes&id=${mapBean.billno}" target="_blnk"><bean:write name="mapBean"  property="maintContractNo"/></a>
      </td>          
      <td class="wordtd">��ϵ�绰:</td>
      <td>
        <span class="renewal show"><bean:write name="mapBean" property="phone"/></span>      
      </td>
        
    </tr>        
    <tr>
      <td class="wordtd">��Ŀ����:</td>
      <td>
        <bean:write name="mapBean" property="projectName"/>
      </td> 
      <td class="wordtd">��������:</td>
      <td><bean:write name="maintenanceWorkPlanDetailBean" property="maintDate" />
      </td>
    </tr>
    <tr>
      <td class="wordtd">��Ŀ��ַ:</td>
      <td >
        <bean:write name="mapBean" property="maintAddress"/>
      </td> 
      
      <td class="wordtd">������Ա</td>
      <td><bean:write name="mapBean" property="r5name"/></td> 
     </tr>       
  </table>
  <br>
   <table  width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
   <tr>
   <td nowrap="nowrap" class="wordtd">��������:</td>
   <td width="30%"><bean:write name="maintenanceWorkPlanDetailBean" property="receivingTime"/></td>
   <td nowrap="nowrap" class="wordtd">�Ƿ�ת��:</td>
   <td width="30%">
    <logic:notEmpty name="maintenanceWorkPlanDetailBean" property="isTransfer">
   <logic:match name="maintenanceWorkPlanDetailBean" property="isTransfer"  value="Y">��</logic:match>
   <logic:match name="maintenanceWorkPlanDetailBean" property="isTransfer"  value="N">��</logic:match>  
   </logic:notEmpty>
   <logic:empty name="maintenanceWorkPlanDetailBean" property="isTransfer" >
                    ��
       </logic:empty>
   </td>
   </tr>
    <tr>
   <td nowrap="nowrap" class="wordtd">��ת����:</td>
   <td><bean:write name="maintenanceWorkPlanDetailBean" property="receivingPerson" /></td>
   <td nowrap="nowrap" class="wordtd">��ת���˵绰:</td>
   <td><bean:write name="maintenanceWorkPlanDetailBean" property="receivingPhone"/></td>
   </tr>
  </table>
  <br>
  <div style="border:solid #999999 1px;border-bottom:none;padding-top: 4;padding-bottom: 4;background:#ffffff;">        
	   <b>&nbsp;������ϸ</b>
	  </div>
   <table  width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
   <tr>
   <td nowrap="nowrap" class="wordtd">��������:</td>
   <td width="30%"><bean:write name="maintenanceWorkPlanDetailBean" property="singleno"/></td>
   <td nowrap="nowrap" class="wordtd">���ݱ��:</td>
   <td width="30%">
   <a onclick="simpleOpenWindow('elevatorSaleAction','${mapBean.elevatorNo}');" class="link"><bean:write name="mapBean" property="elevatorNo"/></a></td>
   </tr>
   <tr>
   <td class="wordtd">��������:</td>
   <td>
   		<logic:match name="mapBean" property="elevatorType" value="T" >
		ֱ��
       </logic:match>
       <logic:match name="mapBean" property="elevatorType" value="F" >
		����
       </logic:match>
   </td>
   <td  class="wordtd">��������:</td>
   <td >
    <logic:match name="maintenanceWorkPlanDetailBean" property="maintType" value="halfMonth" >
		���±���
       </logic:match>
       <logic:match name="maintenanceWorkPlanDetailBean" property="maintType" value="quarter" >
		���ȱ��� 
       </logic:match>
       <logic:match name="maintenanceWorkPlanDetailBean" property="maintType" value="halfYear" >
		���걣��
       </logic:match>
       <logic:match name="maintenanceWorkPlanDetailBean" property="maintType" value="yearDegree" >
		��ȱ���
       </logic:match>
   </td>
   </tr>
   
   <tr>
   <td class="wordtd">׼����ʱ��(����):</td>
   <td><bean:write name="maintenanceWorkPlanDetailBean"  property="maintDateTime" /></td>
    <td class="wordtd">�ϴα�������:</td>
   <td><bean:write name="mapBean"  property="sMaintEndTime" /></td>
   </tr>
   <tr>
   <td class="wordtd">������ʼʱ��:</td>
   <td><bean:write name="maintenanceWorkPlanDetailBean" property="maintStartTime"/></td>
   <td class="wordtd">������ʼ��ַ:</td>
   <td><bean:write name="maintenanceWorkPlanDetailBean" property="maintStartAddres"/></td>
   </tr>
   <tr>
   <td class="wordtd">������ʼ����(��):</td>
   <td><bean:write name="maintenanceWorkPlanDetailBean" property="startDistance"/></td>
   <td class="wordtd">&nbsp;</td>
   <td>&nbsp;</td>
   </tr>
   <tr>
   <td class="wordtd">��������ʱ��:</td>
   <td><bean:write name="maintenanceWorkPlanDetailBean" property="maintEndTime"/></td>
   <td class="wordtd">����������ַ:</td>
   <td><bean:write name="maintenanceWorkPlanDetailBean" property="maintEndAddres"/></td>
   </tr>
   <tr>
   <td class="wordtd">������������(��):</td>
   <td><bean:write name="maintenanceWorkPlanDetailBean" property="endDistance"/></td>
   <td class="wordtd">&nbsp;</td>
   <td>&nbsp;</td>
   </tr>
   
      <tr>
   <td class="wordtd">��ͣʱ��:</td>
   <td><bean:write name="maintenanceWorkPlanDetailBean" property="stopTime"/></td>
   <td class="wordtd">��ͣ��ַ:</td>
   <td><bean:write name="maintenanceWorkPlanDetailBean" property="stopAddres"/></td>
   </tr>
   <tr>
   <td class="wordtd">��ͣ����(��):</td>
   <td><bean:write name="maintenanceWorkPlanDetailBean" property="stopDistance"/></td>
   <td class="wordtd">&nbsp;</td>
   <td>&nbsp;</td>
   </tr>
   <tr>
   <td class="wordtd">����ʱ��:</td>
   <td><bean:write name="maintenanceWorkPlanDetailBean" property="restartTime"/></td>
   <td class="wordtd">������ַ:</td>
   <td><bean:write name="maintenanceWorkPlanDetailBean" property="restartAddres"/></td>
   </tr>
   <tr>
   <td class="wordtd">��������(��):</td>
   <td><bean:write name="maintenanceWorkPlanDetailBean" property="restartDistance"/></td>
   <td class="wordtd">&nbsp;</td>
   <td>&nbsp;</td>
   </tr>
   
   <tr>
   <td class="wordtd">����ʱ��(����):</td>
   <td>
   		<bean:write name="maintenanceWorkPlanDetailBean" property="usedDuration"/>
   </td>
   <td class="wordtd">����ʱ������:</td>
	<td>
		<bean:write name="maintenanceWorkPlanDetailBean" property="dateScore"/>
	</td>
   </tr>
   <tr>
   <td class="wordtd">��������:</td>
   <td>
   		<bean:write name="maintenanceWorkPlanDetailBean" property="distanceScore"/>
   	</td>
   <td class="wordtd">�����÷�:</td>
	<td>
		<bean:write name="maintenanceWorkPlanDetailBean" property="maintScore"/>
	</td>
   </tr>

	   <tr>
		  <td class="wordtd">������ע</td>
	      <td colspan="3"><bean:write name="mapBean" property="byrem"/></td> 
      </tr>
   </table>
  
   <br/>
	<div style="border:solid #999999 1px;border-bottom:none;padding-top: 4;padding-bottom: 4;background:#ffffff;">        
	   <b>&nbsp;������Ŀ��ϸ</b>
	  </div>
     <table  width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
        <tr>
	        <td class="wordtd_header" nowrap="nowrap" >���</td>
	        <td class="wordtd_header" nowrap="nowrap">ά����Ŀ</td>
	        <td class="wordtd_header" nowrap="nowrap">ά������Ҫ��</td>
	        <td class="wordtd_header" nowrap="nowrap">�������</td>
        </tr>
         <logic:present name="workinfoList">
        <logic:iterate id="ele" name="workinfoList">
        <tr>
        	<td align="center">${ele.orderby }</td>
        	<td>${ele.maintItem }</td>
        	<td>${ele.maintContents }</td>
        	<td align="center">
        		<logic:equal name="ele" property="isMaintain" value="Y">��</logic:equal>
        		<logic:equal name="ele" property="isMaintain" value="N">��</logic:equal>
        	</td>
        </tr>
        </logic:iterate>
        </logic:present>
     </table>
     <br/>
	<table width="100%" border="0" cellpadding="0" cellspacing="0"  class="tb">
      <tr>
        <td class="wordtd">�ͻ�ǩ��:</td>
        <td width="72%" class="inputtd">
        	<logic:notEmpty name="maintenanceWorkPlanDetailBean" property="customerSignature">
				<img src="<%=basePath%>/maintenanceWorkPlanCompletionAction.do?method=toDownloadFileRecord&customerSignature=${maintenanceWorkPlanDetailBean.customerSignature}"
					width="${CSwidth}" height="${CSheight}"  id="${maintenanceWorkPlanDetailBean.customerSignature}_1"> 	
			</logic:notEmpty>
			<logic:notEmpty name="maintenanceWorkPlanDetailBean" property="customerImage">
				<img src="<%=basePath%>/maintenanceWorkPlanCompletionAction.do?method=toDownloadFileRecord&customerImage=${maintenanceWorkPlanDetailBean.customerImage}"
					width="${CIwidth}" height="${CIheight}" id="${maintenanceWorkPlanDetailBean.customerImage}_2">
			</logic:notEmpty>
        </td>
      </tr>
    </table>
  
  <br/>
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
  <tr>
   <td nowrap="nowrap" class="wordtd" width="30%">��ע:</td>
   <td nowrap="nowrap" colspan="3" width="75%">
  	 <bean:write name="maintenanceWorkPlanDetailBean" property="rem"/>
   </td>
   </tr>
   <tr>
   <td nowrap="nowrap" class="wordtd">�����:</td>
   <td nowrap="nowrap" width="30%"><bean:write name="maintenanceWorkPlanDetailBean" property="byAuditOperid"/></td>
   <td nowrap="nowrap" class="wordtd">������ڣ�</td>
   <td nowrap="nowrap" width="30%"><bean:write name="maintenanceWorkPlanDetailBean" property="byAuditDate"/></td>
   </tr>
   </table>
   <br/>
   <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
   <tr>
   <td nowrap="nowrap" class="wordtd">����������:</td>
   <td nowrap="nowrap" colspan="3">
  	<html:textarea name="maintenanceWorkPlanDetailBean" rows="3" cols="120" property="fittingsReplace"></html:textarea>
 </td>
   </tr>
   <tr>
   <td nowrap="nowrap" class="wordtd" >�Ƿ��е���:</td>
   <td nowrap="nowrap" colspan="3"> 
   	<html:radio name="maintenanceWorkPlanDetailBean" styleId="isInvoice" property="isInvoice" value="Y">��</html:radio>
    <html:radio name="maintenanceWorkPlanDetailBean" styleId="isInvoice" property="isInvoice" value="N">��</html:radio>
   </tr>
   <tr>
   <td nowrap="nowrap" class="wordtd">����¼����:</td>
   <td nowrap="nowrap" width="30%"><bean:write name="maintenanceWorkPlanDetailBean" property="djOperId2"/></td>
   <td nowrap="nowrap" class="wordtd">����¼�����ڣ�</td>
   <td nowrap="nowrap" width="30%"><bean:write name="maintenanceWorkPlanDetailBean" property="djOperDate2"/></td>
   </tr>
  </table>
  <br> 

   
</logic:present>
</html:form>
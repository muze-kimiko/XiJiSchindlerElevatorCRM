<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>

<style>
  .show{display:block;}
  .hide{display:none;}
</style>
<logic:present name="maintenanceWorkPlanDetailBean">
    <table  width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
    <tr>
      <td nowrap="nowrap" class="wordtd">����վ:</td>
      <td  width="25%">
       <bean:write name="mapBean"  property="storagename"/>
      </td>          
      <td nowrap="nowrap" class="wordtd">ά����:</td>
      <td  width="30%">
      <bean:write name="mapBean"  property="username"/>
      </td>        
    </tr>  
    <tr>
      <td class="wordtd">��ͬ��:</td>
      <td>
        <%-- <a href="<html:rewrite page='/maintContractAction.do'/>?method=toDisplayRecord&typejsp=Yes&id=${mapBean.billno}" target="_blnk"> --%><bean:write name="mapBean"  property="maintContractNo"/><!-- </a> -->
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
             <td class="wordtd">&nbsp;</td>
      <td>&nbsp;</td> 
     </tr>       
  </table>
  <br>
   <table  width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
   <tr>
   <td nowrap="nowrap" class="wordtd">��������:</td>
   <td width="25%"><bean:write name="maintenanceWorkPlanDetailBean" property="receivingTime"/></td>
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
   <td><bean:write name="maintenanceWorkPlanDetailBean" property="r1" /></td>
   <td nowrap="nowrap" class="wordtd">��ת���˵绰:</td>
   <td><bean:write name="maintenanceWorkPlanDetailBean" property="receivingPhone"/></td>
   </tr>
  </table>
  
  <br>
   <table  width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
   <tr>
   <td nowrap="nowrap" class="wordtd">��������:</td>
   <td  width="25%"><bean:write name="maintenanceWorkPlanDetailBean" property="singleno"/></td>
   <td nowrap="nowrap" class="wordtd">���ݱ��:</td>
   <td  width="30%">
   <a onclick="simpleOpenWindow('elevatorSaleAction','${mapBean.elevatorNo}');" class="link"><bean:write name="mapBean" property="elevatorNo"/></a></td>
   </tr>
   <tr>
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
   <td class="wordtd">׼����ʱ��:</td>
   <td><bean:write name="maintenanceWorkPlanDetailBean"  property="maintDateTime" /></td>
   </tr>
   <tr>
    <td class="wordtd">�ϴα�������:</td>
   <td><bean:write name="mapBean"  property="sMaintEndTime" /></td>
   <td class="wordtd"></td>
   <td></td>
   </tr>
   <tr>
   <td class="wordtd">������ʼʱ��:</td>
   <td><bean:write name="maintenanceWorkPlanDetailBean" property="maintStartTime"/></td>
   <td class="wordtd">��ַ:</td>
   <td><bean:write name="maintenanceWorkPlanDetailBean" property="maintStartAddres"/></td>
   </tr>
   <tr>
   <td class="wordtd">��������ʱ��:</td>
   <td><bean:write name="maintenanceWorkPlanDetailBean" property="maintEndTime"/></td>
   <td class="wordtd">��ַ:</td>
   <td><bean:write name="maintenanceWorkPlanDetailBean" property="maintEndAddres"/></td>
   </tr>
   <tr>
   <% int i=1; %>
   <td class="wordtd">����:</td>
   <td colspan="3">
   <logic:present name="fileSidList">
   <logic:iterate id="ele" name="fileSidList">
   <a style="cursor:hand;text-decoration: underline;color: blue;" onclick="downloadFile('${ele.fileSid}')">����<%=i%></a>&nbsp;&nbsp; <%i++; %>
   </logic:iterate>
   </logic:present>
   </td>   
   </tr>  
   </table>
</logic:present>
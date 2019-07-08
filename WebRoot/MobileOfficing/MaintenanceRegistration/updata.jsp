<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>

<style>
  .show{display:block;}
  .hide{display:none;}
</style>
<logic:present name="maintenanceWorkPlanDetailBean">
     <html:hidden property="btn" styleId="btn" value="${btn1 }"/>
     <html:hidden property="btn2" styleId="btn2" value="${btn2 }"/>
     <html:hidden name="maintenanceWorkPlanDetailBean" property="numno" styleId="numno" value="${maintenanceWorkPlanDetailBean.numno }"/> 
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
       <%--  <a href="<html:rewrite page='/maintContractAction.do'/>?method=toDisplayRecord&typejsp=Yes&id=${mapBean.billno}" target="_blnk"> --%><bean:write name="mapBean"  property="maintContractNo"/><!-- </a> -->
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
   <logic:equal name="maintenanceWorkPlanDetailBean" property="handleStatus" value="0">
   <table  width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
   <tr>
   <td nowrap="nowrap" class="wordtd">��������:</td>
   <td width="25%"><html:text name="maintenanceWorkPlanDetailBean" property="receivingTime" value="${receivingTime }" styleId="receivingTime" styleClass="Wdate" onfocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/></td>
   <td nowrap="nowrap" class="wordtd">�Ƿ�ת��:</td>
   <td width="30%">
    <html:select name="maintenanceWorkPlanDetailBean" property="isTransfer" styleId="isTransfer">
        <html:option value="">��ѡ��</html:option>
        <html:option value="Y">��</html:option>
        <html:option value="N">��</html:option>
    </html:select>
   </td>
   </tr>
    <tr>
   <td nowrap="nowrap" class="wordtd">��ת����:</td>
   <td><input id="username" type="text" readonly="readonly" value="${maintenanceWorkPlanDetailBean.r1 }"><input type="hidden" name="receivingPerson"  id="receivingPerson" value="${maintenanceWorkPlanDetailBean.receivingPerson }">
   <input type="button" value=".." onclick="openWindowAndReturnValue3('searchStaffAction','toSearchRecord3','mainStation=${mapBean.mainStation}','')" class="default_input"/></td>
	
   <td nowrap="nowrap" class="wordtd">��ת���˵绰:</td>
   <td><html:text name="maintenanceWorkPlanDetailBean" readonly="readonly" property="receivingPhone" styleId="receivingPhone" /></td>
   </tr>
   
  </table>
   </logic:equal>
   <logic:notEqual name="maintenanceWorkPlanDetailBean" property="handleStatus" value="0">
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
   <td nowrap="nowrap" class="wordtd">��ת������:</td>
   <td><bean:write name="maintenanceWorkPlanDetailBean" property="transferDate"/></td>
   </tr>
  </table>
  </logic:notEqual>
  <br>
   <table  width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
   <tr>
   <td nowrap="nowrap" class="wordtd">��������:</td>
   <td  width="25%"><bean:write name="maintenanceWorkPlanDetailBean" property="singleno" />
   <html:hidden name="maintenanceWorkPlanDetailBean" property="singleno" styleId="singleno"/> </td>
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
   <td><bean:write name="mapBean"  property="sMaintEndTime"/></td>
   <td class="wordtd"></td>
   <td></td>
   </tr>
   <!-- ���� -->
   <logic:equal name="maintenanceWorkPlanDetailBean" property="handleStatus" value="1">
   <tr>
   <td class="wordtd">������ʼʱ��:</td>
   <td><html:text name="maintenanceWorkPlanDetailBean" property="maintStartTime" styleId="maintStartTime" value="${maintStartTime }"  styleClass="Wdate" onfocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/></td>
   <td class="wordtd">��ַ:</td>
   <td><html:text name="maintenanceWorkPlanDetailBean" property="maintStartAddres" styleId="maintStartAddres" size="40"/></td>
   </tr>
   </logic:equal>
   <!-- ���� -->
   <logic:notEqual name="maintenanceWorkPlanDetailBean" property="handleStatus" value="1">
   <tr>
   <td class="wordtd">������ʼʱ��:</td>
   <td><bean:write name="maintenanceWorkPlanDetailBean" property="maintStartTime"/></td>
   <td class="wordtd">��ַ:</td>
   <td><bean:write name="maintenanceWorkPlanDetailBean" property="maintStartAddres"/></td>
   </tr>
   </logic:notEqual>
   <!-- �깤 -->
   <logic:equal name="maintenanceWorkPlanDetailBean" property="handleStatus" value="2">
   <tr>
   <td class="wordtd">��������ʱ��:</td>
   <td><html:text name="maintenanceWorkPlanDetailBean" property="maintEndTime" styleId="maintEndTime" styleClass="Wdate" value="${maintEndTime }" onfocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/></td>
   <td class="wordtd">��ַ:</td>
   <td><html:text name="maintenanceWorkPlanDetailBean" property="maintEndAddres" styleId="maintEndAddres" size="40"/></td>
   </tr>
   <tr>
   <td class="wordtd">����:</td>
   <td colspan="3">
   <table width="100%" class="tb">
	<tr class="wordtd"><td width="5%" align="center"><input type="checkbox" onclick="checkTableFileAll(this)"></td>
	<td align="left">&nbsp;&nbsp;<input type="button" name="toaddrow" value="+" onclick="AddRow(this)"/>
	&nbsp;<input type="button" name="todelrow" value="-" onclick="deleteFileRow(this)">
	</td></tr>
	</table><br>
   </td>   
   </tr> 
   </logic:equal>
   <!-- �깤 -->
   <logic:notEqual name="maintenanceWorkPlanDetailBean" property="handleStatus" value="2">
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
   </logic:notEqual>
   </table>
</logic:present>
<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/html-table.tld" prefix="table"%>
<%@ page import="com.gzunicorn.common.util.SysConfig" %>
<link rel="stylesheet" type="text/css" href="<html:rewrite forward='formCSS'/>">
<script language="javascript" defer="defer" src="<html:rewrite forward='DatePickerJS'/>"></script>
<html:errors />
<br>
<html:form action="/ServeTable.do">
<html:hidden property="property(genReport)" styleId="genReport" />
  <table>
    <tr>
      <td>      
        &nbsp;&nbsp;
                       ��������:
      </td>
      <td>
        <html:text property="property(singleno)" styleClass="default_input" />
      </td>                 
      <td>
        &nbsp;&nbsp;               
                       ���ݱ��:
      </td>
      <td>
        <html:text property="property(elevatorNo)" styleClass="default_input" />
      </td> 
      
	<td>
         &nbsp;&nbsp;
         	�����ƻ�����:
	</td>
   		<td colspan="3">
   		<html:text property="property(sdate1)" styleClass="Wdate" size="13" onfocus="WdatePicker({isShowClear:true})"/>
		- 
		<html:text property="property(edate1)" styleClass="Wdate" size="13" onfocus="WdatePicker({isShowClear:true})"/>
	</td>
     
	</tr>
	<tr>
	 <td>  
        &nbsp;&nbsp;     
                      ά����:
      </td>
      <td>
        <html:text property="property(maintPersonnel)" styleClass="default_input" />
      </td>
	<td>
        &nbsp;&nbsp;               
                     ����ά����Ա:
      </td>
      <td>
        <html:text property="property(r5)" styleClass="default_input" />
      </td>                
     	<td>
		&nbsp;&nbsp;
		ά��վ:
		</td>
		<td>
		<html:select property="property(assignedMainStation)">
			<logic:equal name="showmt" value="Y">
	          <html:option value="%">ȫ��</html:option>
	        </logic:equal>
	        <logic:present name="mainStationList">
	          <html:options collection="mainStationList" property="storageid" labelProperty="storagename"/>
	        </logic:present>
        </html:select>
		
	</td>
	<td>
        &nbsp;&nbsp;               
                     �Ƿ����:
      </td>
      <td>
        <html:select property="property(auditType)">
	          <html:option value="%">ȫ��</html:option>
			  <html:option value="N">δ���</html:option>
			  <html:option value="Y">�����</html:option>
        </html:select>
      </td> 
	</tr>
	
  </table>
  <br>
   <table:table id="guiMaintenanceWorkPlanCompletion" name="maintenanceWorkPlanCompletionList">
    <logic:iterate id="element" name="maintenanceWorkPlanCompletionList">
      <table:define id="c_cb">
        <html:radio property="checkBoxList(ids)" styleId="ids" value="${element.numno}" />    
		<input type="hidden" name="hsingleno" value="<bean:write name="element" property="singleno" />"/>
		<input type="hidden" name="hmaintEndTime" value="<bean:write name="element" property="maintEndTime" />"/>
		<input type="hidden" name="hauditType" value="<bean:write name="element" property="auditType" />"/>
      </table:define>	
      <table:define id="c_singleno">
      	  <logic:equal name="element" property="isred" value="Y">
      	  	<font color="red">
      	  </logic:equal>
      	  	<bean:write name="element" property="singleno" />
      	  <logic:equal name="element" property="isred" value="Y">
      	  	</font>
      	  </logic:equal>
      </table:define>
      <table:define id="c_elevatorNo">
      
       <logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="nmaintenanceworkplancompletion" value="Y">
     	<a href="<html:rewrite page='/maintenanceWorkPlanCompletionAction.do'/>?method=toDisplayRecord&id=<bean:write name="element"  property="numno"/>">
        <bean:write name="element" property="elevatorNo" />
        </a>
        </logic:equal>
        
        <logic:notEqual name="<%=SysConfig.TOOLBAR_RIGHT%>" property="nmaintenanceworkplancompletion" value="Y">
        <bean:write name="element" property="elevatorNo" />
        </logic:notEqual>
        
      </table:define>
      <table:define id="c_assignedMainStation">
      	  <logic:equal name="element" property="isred" value="Y">
      	  	<font color="red">
      	  </logic:equal>
        <bean:write name="element" property="storagename" />
        <logic:equal name="element" property="isred" value="Y">
      	  	</font>
      	  </logic:equal>
      </table:define>
      <table:define id="c_maintPersonnel">
          <logic:equal name="element" property="isred" value="Y">
      	  	<font color="red">
      	  </logic:equal>
        <bean:write name="element" property="username" />
        <logic:equal name="element" property="isred" value="Y">
      	  	</font>
      	  </logic:equal>
      </table:define>
      <table:define id="c_phone">
      	  <logic:equal name="element" property="isred" value="Y">
      	  	<font color="red">
      	  </logic:equal>
        <bean:write name="element" property="phone" />
        <logic:equal name="element" property="isred" value="Y">
      	  	</font>
      	  </logic:equal>
      </table:define>
      <table:define id="c_projectName">
      	  <logic:equal name="element" property="isred" value="Y">
      	  	<font color="red">
      	  </logic:equal>
        <bean:write name="element" property="projectName" />
        <logic:equal name="element" property="isred" value="Y">
      	  	</font>
      	  </logic:equal>
      </table:define>
      <table:define id="c_maintType">
      	  <logic:equal name="element" property="isred" value="Y">
      	  	<font color="red">
      	  </logic:equal>
       <logic:match name="element" property="maintType" value="halfMonth" >
		���±���
       </logic:match>
       <logic:match name="element" property="maintType" value="quarter" >
		���ȱ��� 
       </logic:match>
       <logic:match name="element" property="maintType" value="halfYear" >
		���걣��
       </logic:match>
       <logic:match name="element" property="maintType" value="yearDegree" >
		��ȱ���
       </logic:match>    
       <logic:equal name="element" property="isred" value="Y">
      	  	</font>
      	  </logic:equal>
      </table:define>
      <table:define id="c_maintDate">
      	  <logic:equal name="element" property="isred" value="Y">
      	  	<font color="red">
      	  </logic:equal>
       <bean:write name="element" property="maintDate" />
       <logic:equal name="element" property="isred" value="Y">
      	  	</font>
      	  </logic:equal>
      </table:define>
      <table:define id="c_receivingTime">
      	  <logic:equal name="element" property="isred" value="Y">
      	  	<font color="red">
      	  </logic:equal>
        <bean:write name="element" property="receivingTime" />
        <logic:equal name="element" property="isred" value="Y">
      	  	</font>
      	  </logic:equal>
      </table:define>
      <table:define id="c_maintStartTime">
      	  <logic:equal name="element" property="isred" value="Y">
      	  	<font color="red">
      	  </logic:equal>
        <bean:write name="element" property="maintStartTime" />
        <logic:equal name="element" property="isred" value="Y">
      	  	</font>
      	  </logic:equal>
      </table:define>
      <table:define id="c_maintEndTime">
      	  <logic:equal name="element" property="isred" value="Y">
      	  	<font color="red">
      	  </logic:equal>
        <bean:write name="element" property="maintEndTime" />
        <logic:equal name="element" property="isred" value="Y">
      	  	</font>
      	  </logic:equal>
      </table:define>
      <table:define id="c_maintScore">
      	  <logic:equal name="element" property="isred" value="Y">
      	  	<font color="red">
      	  </logic:equal>
        <bean:write name="element" property="maintScore" />
        <logic:equal name="element" property="isred" value="Y">
      	  	</font>
      	  </logic:equal>
      </table:define>
      <table:define id="c_byAuditOperid">
      	  <logic:equal name="element" property="isred" value="Y">
      	  	<font color="red">
      	  </logic:equal>
        <logic:match name="element" property="auditType" value="Y" >�����</logic:match> 
        <logic:match name="element" property="auditType" value="N" >δ���</logic:match>
      <logic:equal name="element" property="isred" value="Y">
      	  	</font>
      	  </logic:equal>
      </table:define>
      	  
      	  <table:define id="c_r5">
      	  <logic:equal name="element" property="isred" value="Y">
      	  	<font color="red">
      	  </logic:equal>
        <bean:write name="element" property="r5name" />
        <logic:equal name="element" property="isred" value="Y">
      	  	</font>
      	  </logic:equal>
      </table:define>
      	  
      <table:tr />
    </logic:iterate>
    </table:table>
</html:form>



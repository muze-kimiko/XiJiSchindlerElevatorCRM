<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/html-table.tld" prefix="table"%>
<link rel="stylesheet" type="text/css" href="<html:rewrite forward='formCSS'/>">
<script language="javascript" defer="defer" src="<html:rewrite forward='DatePickerJS'/>"></script>
<html:errors />
<br>
<html:form action="/ServeTable.do">
<html:hidden property="property(genReport)" styleId="genReport" />
  <table border="0">
    <tr>
      <td>     
       &nbsp;&nbsp;    
        ��ˮ��
        :
      </td>
      <td>
        <html:text property="property(billno)" styleClass="default_input" />
      </td>                 
      <td> 
      &nbsp;&nbsp;    
        ���ۺ�ͬ��
        :
      </td>
      <td>
        <html:text property="property(salesContractNo)" styleClass="default_input"/>
      </td> 
    <td>       
     &nbsp;&nbsp;
     ���ݱ��
        :
      </td>
      <td>
         <html:text property="property(elevatorNo)" styleClass="default_input"/>
      </td> 
      
      <td>      
       &nbsp;&nbsp;   
        ������Ա����
        :
      </td>
      <td>
        <html:text property="property(staffName)" styleClass="default_input" />
      </td>
                
    </tr>
    <tr>  
      <td> 
        &nbsp;&nbsp;      
        ��������
        :
      </td>
      <td>
        <html:select property="property(department)">
        	<html:option value="">ȫ��</html:option>
		    <html:options collection="departmentList" property="comid" labelProperty="comfullname"/>
        </html:select>
      </td>    
      <td>
        &nbsp;&nbsp;               
        ��Ŀ����
        :
      </td>
      <td colspan="3">
        <html:text property="property(projectName)" styleClass="default_input" size="56" />
      </td>   
       <td>&nbsp;&nbsp;
       	������:</td>
        <td>
        	<html:select property="property(factoryCheckResult)">
        		<html:option value="">ȫ��</html:option>
        		<html:option value="�ϸ�">�ϸ�</html:option>
        		<html:option value="���ϸ�">���ϸ�</html:option>
        	</html:select>
        </td>
    </tr>
    <tr>
      <td>       
       &nbsp;&nbsp;   
                    �ύ��־
        :
      </td>
      <td>
        <html:select property="property(submitType)">
          <html:option value="">ȫ��</html:option>
		  <html:option value="Y">���ύ</html:option>
		  <html:option value="N">δ�ύ</html:option>
		  <html:option value="Z">ת��</html:option>
		  <html:option value="R">����</html:option>
        </html:select>
      </td>     
   <td>       
     &nbsp;&nbsp;
     ��װ��˾����
        :
      </td>
      <td colspan="3">
         <html:text property="property(insCompanyName)" styleClass="default_input" size="56" />
      </td> 
      <td>&nbsp;&nbsp;
      	����汾:</td>
       <td>
       	<html:select property="property(checkVersion)">
       		<html:option value="">ȫ��</html:option>
       		<html:option value="Y">���°汾</html:option>
       		<html:option value="N">��ʷ�汾</html:option>
       	</html:select>
       </td>
    </tr>
    <tr>
    	
    	<td>
    		&nbsp;&nbsp;
    		�Ƿ�ۿ�:
    	</td>
    	<td>
    		<html:select property="property(isDeductions)">
	       		<html:option value="">ȫ��</html:option>
	       		<html:option value="Y">ͬ��ۿ�</html:option>
	       		<html:option value="N">ͬ�ⲻ�ۿ�</html:option>
       		</html:select>
    	</td>
    	<td>
    		&nbsp;&nbsp;
    		����״̬:
    	</td>
    	<td>
    		<html:select property="property(processStatus)">
	       		<html:option value="">ȫ��</html:option>
	       		<html:option value="0">δ����</html:option>
	       		<html:option value="4">�ѽ���</html:option>
	       		<html:option value="1">�ѵǼ�δ�ύ</html:option>
	       		<html:option value="2">�ѵǼ����ύ</html:option>
	       		<html:option value="3">�����</html:option>
       		</html:select>
    	</td>                      
      <td>                 
     	  &nbsp;&nbsp;����״̬:
      </td>
      <td>
        <html:select property="property(status)">
          <html:option value="">ȫ��</html:option>
		  <html:options collection="processStatusList" property="typeid" labelProperty="typename"/>
        </html:select>
      </td>                       
      <td>                 
     	  &nbsp;&nbsp;�Ƿ�رճ���:
      </td>
      <td>
        <html:select property="property(isClose)">
          	<html:option value="">ȫ��</html:option>
	       	<html:option value="Y">��</html:option>
	       	<html:option value="N">��</html:option>
        </html:select>
      </td>   
    </tr>
    
    
  </table>
  <br>
  <table:table id="guiElevatorTransferCaseRegisterManage" name="elevatorTransferCaseRegisterList">
    <logic:iterate id="element" name="elevatorTransferCaseRegisterList">
      <table:define id="c_cb">
        <bean:define id="billno" name="element" property="billno" />
        <html:radio property="checkBoxList(ids)" styleId="ids" value="${billno}" />
        <html:hidden name="element" property="submitType" />
        <html:hidden name="element" property="isClose" />
        <html:hidden name="element" property="checkVersion" />
        <html:hidden name="element" property="r1" />
        <html:hidden name="element" property="elevatorNo" />
      </table:define>
      <table:define id="c_billno">
        <a href="<html:rewrite page='/elevatorTransferCaseRegisterManageAction.do'/>?method=toDisplayRecord&id=<bean:write name="element"  property="billno"/>">
          <bean:write name="element" property="billno" />
        </a>
      </table:define>
      <table:define id="c_checkTime">
        <bean:write name="element" property="checkTime" />
      </table:define>
      <table:define id="c_checkNum">
        <bean:write name="element" property="checkNum" />
      </table:define>
      <table:define id="c_projectName">
        <bean:write name="element" property="projectName" />
      </table:define>
      <table:define id="c_insCompanyName">
        <bean:write name="element" property="insCompanyName" />
      </table:define>
      <table:define id="c_salesContractNo">
        <bean:write name="element" property="salesContractNo" />
      </table:define>
       <table:define id="c_elevatorNo">
        <bean:write name="element" property="elevatorNo" />
      </table:define>   
      <table:define id="c_staffName">
        <bean:write name="element" property="staffName" />
      </table:define>
      <table:define id="c_department">
        <bean:write name="element" property="department" />
      </table:define>
      <table:define id="c_factoryCheckResult">
        <bean:write name="element" property="factoryCheckResult" />
        <html:hidden name="element" property="factoryCheckResult"/>
      </table:define>    
      <table:define id="c_isDeductions">
	    <logic:present name="element" property="isDeductions">
	      <logic:match name="element" property="isDeductions" value="Y">ͬ��ۿ�</logic:match>
        <logic:match name="element" property="isDeductions" value="N">ͬ�ⲻ�ۿ�</logic:match>
         </logic:present>
      </table:define>
      <table:define id="c_status">
        <bean:write name="element" property="r2" />    
        <html:hidden name="element" property="status"/>
      </table:define>   
      <table:define id="c_processName">
        <bean:write name="element" property="processName" />    
      </table:define>     
      <table:define id="c_submitType">
        <logic:match name="element" property="submitType" value="Y">���ύ</logic:match>
        <logic:match name="element" property="submitType" value="N">δ�ύ</logic:match>
        <logic:match name="element" property="submitType" value="R">����</logic:match>
        <logic:match name="element" property="submitType" value="Z">ת��</logic:match>
      </table:define>
      <table:define id="c_processStatus">
        <html:hidden name="element" property="processStatus"/>
        <logic:match name="element" property="processStatus" value="0">δ����</logic:match>
        <logic:match name="element" property="processStatus" value="4">�ѽ���</logic:match>
        <logic:match name="element" property="processStatus" value="1">�ѵǼ�δ�ύ</logic:match>
        <logic:match name="element" property="processStatus" value="2">�ѵǼ����ύ</logic:match>
        <logic:match name="element" property="processStatus" value="3">�����</logic:match>
      </table:define>    
      <table:define id="c_isClose">
        <logic:equal name="element" property="isClose" value="Y">��</logic:equal>
        <logic:notEqual name="element" property="isClose" value="Y">��</logic:notEqual>
      </table:define>
      <table:tr />
    </logic:iterate>
  </table:table>
</html:form>
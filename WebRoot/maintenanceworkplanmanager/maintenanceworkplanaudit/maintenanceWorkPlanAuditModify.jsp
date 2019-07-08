<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<link rel="stylesheet" type="text/css" href="<html:rewrite forward='formCSS'/>">
<script language="javascript" src="<html:rewrite forward='pageJS'/>"></script>
	<script language="javascript" defer="defer" src="<html:rewrite forward='DatePickerJS'/>"></script>

<html:form action="/maintenanceWorkPlanAuditAction.do?method=toUpdateRecord">
<html:hidden property="isreturn"/>
<html:errors/>
 <logic:present name="maintContractMasterBean">
   <table id="contractInfoTable" width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
    <tr>
      <td height="23" colspan="6">&nbsp;<b>��ͬ����Ϣ</td>
    </tr>
    <tr>
      <td nowrap="nowrap" class="wordtd_a"><bean:message key="maintContract.maintContractNo"/>:</td>
      <td nowrap="nowrap" width="20%">
        <a href="<html:rewrite page='/maintContractAction.do'/>?method=toDisplayRecord&typejsp=Yes&id=<bean:write name="maintContractMasterBean"  property="billNo"/>" target="_blnk"><bean:write name="maintContractMasterBean"  property="maintContractNo"/></a>          
        <html:hidden name="maintContractMasterBean"  property="billNo"/>
      </td>          
      <td nowrap="nowrap" class="wordtd_a"><bean:message key="maintContract.contractNatureOf"/>:</td>
      <td nowrap="nowrap" width="20%">�Ա�</td>
      <td nowrap="nowrap" class="wordtd_a"><bean:message key="maintContract.mainMode"/>:</td>
      <td nowrap="nowrap" width="20%">
        <logic:match name="maintContractMasterBean" property="mainMode" value="FREE">���</logic:match>
        <logic:match name="maintContractMasterBean" property="mainMode" value="PAID">�շ�</logic:match>
      </td>         
    </tr>  
    <tr>
      <td class="wordtd_a"><bean:message key="maintContract.contractPeriod"/>:</td>
      <td>
        <span class="renewal show"><bean:write name="maintContractMasterBean" property="contractPeriod"/></span>  
        <span class="renewal hide"><input type="text" name="contractPeriod" class="default_input_noborder"></span>         
      </td>          
      <td class="wordtd_a"><bean:message key="maintContract.contractSdate"/>:</td>
      <td>
        <span class="renewal show"><bean:write name="maintContractMasterBean" property="contractSdate"/></span>      
      </td>
      <td class="wordtd_a"><bean:message key="maintContract.contractEdate"/>:</td>
      <td>
        <span class="renewal show"><bean:write name="maintContractMasterBean" property="contractEdate"/></span>
      </td>         
    </tr>        
    <tr>
      <td class="wordtd_a"><bean:message key="maintContract.maintDivision"/>:</td>
      <td>
        <bean:write name="maintContractMasterBean" property="maintDivision"/>
      </td>
      <td class="wordtd_a">����ά��վ</td>
      <td>
      	<bean:write name="maintContractMasterBean" property="maintStation"/>
      </td>  
      <td class="wordtd_a"><bean:message key="maintContract.attn"/>:</td>
      <td>
        <bean:write name="maintContractMasterBean" property="attn"/>
      </td>
    </tr>    
    <tr>
      <td class="wordtd_a">������:</td>
      <td>
        ${operidkk}
      </td> 
      <td class="wordtd_a">��������:</td>
      <td>
        ${ operdatekk}
      </td>
      <td class="wordtd_a">&nbsp;</td>
      <td>&nbsp;</td>         
    </tr>
  </table>
 </logic:present>
 <br/>
  <logic:present name="maintContractDetailList">
  <table id="contractInfoTable" width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
    <tr >
      <td height="23" colspan="12">&nbsp;<b>��ͬ��ϸ</td>
    </tr>
    <tr >
    <td class="wordtd_a" style="text-align: center;">���ݱ��</td>
    <td class="wordtd_a" style="text-align: center;" nowrap="nowrap">��������</td>
    <td class="wordtd_a" style="text-align: center;">��/վ/��</td>
    <td class="wordtd_a" style="text-align: center;">�����߶�</td>
    <td class="wordtd_a" style="text-align: center;">�������</td>
    <td class="wordtd_a" style="text-align: center;">���ۺ�ͬ��</td>
    <td class="wordtd_a" style="text-align: center;">��Ŀ����</td>
    <td class="wordtd_a" style="text-align: center;" nowrap="nowrap">ά����ʼʱ��</td>
    <td class="wordtd_a" style="text-align: center;" nowrap="nowrap">ά���ƻ���ʼʱ��</td>
    <td class="wordtd_a" style="text-align: center;" nowrap="nowrap">ά������ʱ��</td>
    <td class="wordtd_a" style="text-align: center;">�´�ά��վ</td>
    <td class="wordtd_a" style="text-align: center;">ά����</td>
    <td class="wordtd_a" style="text-align: center;" nowrap="nowrap">�����߼�</td>
    </tr>
    <logic:iterate id="element" name="maintContractDetailList">
    <tr>
    <td nowrap="nowrap" style="text-align: center;" >
	<input style="width: 110px;" onclick="simpleOpenWindow('elevatorSaleAction',this.value);" class="link noborder_center" readonly="readonly" value="<bean:write name="element" property="elevatorNo" />" name="elevatorNo">	
	<input type="hidden" name="rowid" value="${element.rowid}" >
	<input type="hidden" name="checkflag" value="${element.r2}">
	</td>
	<td nowrap="nowrap" style="text-align: center;">${element.elevatorType=='T'?'ֱ��':'����'}</td>
	<td nowrap="nowrap" style="text-align: center;"><bean:write name="element" property="floor" />/<bean:write name="element" property="stage" />/<bean:write name="element" property="door" /></td>
	<td nowrap="nowrap" style="text-align: center;"><bean:write name="element" property="high" /></td>
	<td nowrap="nowrap" style="text-align: center;"><bean:write name="element" property="annualInspectionDate" /></td>
	
	<td nowrap="nowrap" style="text-align: center;"><bean:write name="element" property="salesContractNo" /></td>
	<td nowrap="nowrap" style="text-align: center;"><bean:write name="element" property="projectName" /></td>
	<td nowrap="nowrap" style="text-align: center;"><bean:write name="element" property="mainSdate"></bean:write> </td>
	<td nowrap="nowrap" style="text-align: center;"><bean:write name="element" property="shippedDate" ></bean:write> </td>
	<td nowrap="nowrap" style="text-align: center;"><bean:write name="element" property="mainEdate"></bean:write></td>
	<td nowrap="nowrap" style="text-align: center;"><bean:write name="element" property="assignedMainStation" /></td>
    <td nowrap="nowrap" style="text-align: center;">
    &nbsp;<a href="<html:rewrite page='/maintenanceWorkPlanAuditAction.do'/>?method=toMaintenanceWorkPlanDisplayRecord&isOpen=Y&id=${element.r1}" target="_blnk"><bean:write name="element" property="maintPersonnel"/></a>&nbsp;         
    <input type="hidden" name="wmpmbillno" value="${element.r1}">
    
    </td>
    <td nowrap="nowrap" style="text-align: center;">
		<logic:equal name="element" property="r3" value="1">�߼�һ</logic:equal>
		<logic:equal name="element" property="r3" value="2">�߼���</logic:equal>
		<logic:equal name="element" property="r3" value="3">�߼���</logic:equal>
		<logic:equal name="element" property="r3" value="4">�߼���</logic:equal>
		<logic:equal name="element" property="r3" value="5">�߼���</logic:equal>
	</td> 
    </tr>
    </logic:iterate>
    </table>
    </logic:present>
    <br/>
    <table id="contractInfoTable" width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
 <tr>
      <td nowrap="nowrap"  class="wordtd_a">�����:</td>
      <td nowrap="nowrap" width="25%" >
        <bean:write name="maintContractMasterBean" property="r1"/>
      </td> 
      <td  nowrap="nowrap" class="wordtd_a" >�������:</td>
      <td  nowrap="nowrap" width="30%">
        <bean:write name="maintContractMasterBean" property="r2"/>
      </td>
      <td  nowrap="nowrap" class="wordtd_a" ></td>
      <td  nowrap="nowrap" width="15%"></td>         
    </tr>
 </table>
</html:form>
 
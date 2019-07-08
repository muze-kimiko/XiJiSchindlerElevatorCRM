<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<link rel="stylesheet" type="text/css" href="<html:rewrite forward='formCSS'/>">
<script language="javascript" src="<html:rewrite forward='pageJS'/>"></script>
	<script language="javascript" defer="defer" src="<html:rewrite forward='DatePickerJS'/>"></script>


<html:form action="/maintenanceWorkPlanCustomizeAction.do?method=toUpdateRecord">
 <html:hidden property="isreturn"/>
 <input type="hidden" name="issaverem" id="issaverem" value="N"/>
<html:errors/>
 <logic:present name="maintContractMasterBean">
   <table id="contractInfoTable" width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
    <tr>
      <td height="23" colspan="6">&nbsp;<b>��ͬ����Ϣ</td>
    </tr>
    <tr>
      <td nowrap="nowrap" class="wordtd_a"><bean:message key="maintContract.maintContractNo"/>:</td>
      <td  class="inputtd" nowrap="nowrap" width="25%">
        <a href="<html:rewrite page='/maintContractAction.do'/>?method=toDisplayRecord&typejsp=Yes&id=<bean:write name="maintContractMasterBean"  property="billNo"/>" target="_blnk"><bean:write name="maintContractMasterBean"  property="maintContractNo"/></a>          
        <html:hidden name="maintContractMasterBean"  property="billNo"/>
      </td>          
      <td nowrap="nowrap" class="wordtd_a"><bean:message key="maintContract.contractNatureOf"/>:</td>
      <td class="inputtd"  nowrap="nowrap" width="30%">�Ա�</td>
      <td nowrap="nowrap" class="wordtd_a"><bean:message key="maintContract.mainMode"/>:</td>
      <td class="inputtd" nowrap="nowrap" width="15%">
        <logic:match name="maintContractMasterBean" property="mainMode" value="FREE">���</logic:match>
        <logic:match name="maintContractMasterBean" property="mainMode" value="PAID">�շ�</logic:match>
      </td>         
    </tr>  
    <tr>
      <td class="wordtd_a"><bean:message key="maintContract.contractPeriod"/>:</td>
      <td class="inputtd" >
        <span class="renewal show"><bean:write name="maintContractMasterBean" property="contractPeriod"/></span>  
        <span class="renewal hide"><input type="text" name="contractPeriod" class="default_input_noborder"></span>         
      </td>          
      <td class="wordtd_a"><bean:message key="maintContract.contractSdate"/>:</td>
      <td class="inputtd" >
        <span class="renewal show"><bean:write name="maintContractMasterBean" property="contractSdate"/></span>      
      </td>
      <td class="wordtd_a"><bean:message key="maintContract.contractEdate"/>:</td>
      <td class="inputtd" >
        <span class="renewal show"><bean:write name="maintContractMasterBean" property="contractEdate"/></span>
      </td>         
    </tr>        
    <tr>
      <td class="wordtd_a"><bean:message key="maintContract.maintDivision"/>:</td>
      <td class="inputtd" >
        <bean:write name="maintContractMasterBean" property="maintDivision"/>
      </td> 
      <td class="wordtd_a">����ά��վ:</td>
      <td>
      	<bean:write name="maintContractMasterBean" property="maintStation"/>
      </td> 
      <td class="wordtd_a"><bean:message key="maintContract.attn"/>:</td>
      <td class="inputtd" >
        <bean:write name="maintContractMasterBean" property="attn"/>
      </td>
    </tr>
    <tr>
      <td class="wordtd_a">���Ʊ�ע:</td>
      <td colspan="5">
      	<html:textarea name="maintContractMasterBean" property="customizeRem" styleId="customizeRem" rows="3" cols="100" styleClass="default_textarea"/> 
      </td>       
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
    <td class="wordtd_a" nowrap="nowrap" style="text-align: center;">ά����ʼʱ��</td>
    <td class="wordtd_a" nowrap="nowrap" style="text-align: center;">ά���ƻ���ʼʱ��</td>
    <td class="wordtd_a" nowrap="nowrap" style="text-align: center;">ά������ʱ��</td>
    <td class="wordtd_a" style="text-align: center;">�´�ά��վ</td>
    <td class="wordtd_a" style="text-align: center;">ά����</td>
    <td class="wordtd_a" style="text-align: center;">�����߼�</td>
    </tr>
    
    <logic:empty name="maintContractDetailsize">
    <tr><td class="inputtd" colspan="12" style="text-align: center;">û�пɶ��Ʊ����ƻ���</td></tr>
    </logic:empty>
    
    <logic:notEmpty name="maintContractDetailsize">
    <logic:iterate id="element" name="maintContractDetailList">
		    <tr>
		    <td nowrap="nowrap" style="text-align: center;">
			<input style="width: 110px;" onclick="simpleOpenWindow('elevatorSaleAction',this.value);" class="link noborder_center" readonly="readonly" value="<bean:write name="element" property="elevatorNo" />" name="elevatorNo">	
			<input type="hidden" name="rowid" value="${element.rowid}" >
			</td>
			<td nowrap="nowrap" style="text-align: center;">${element.elevatorType=='T'?'ֱ��':'����'}</td>
	        <td nowrap="nowrap" style="text-align: center;"><bean:write name="element" property="floor" />/<bean:write name="element" property="stage" />/<bean:write name="element" property="door" /></td>
			<td nowrap="nowrap" style="text-align: center;"><bean:write name="element" property="high" /></td>
			<td nowrap="nowrap" style="text-align: center;"><bean:write name="element" property="annualInspectionDate" /></td>
			
			<td nowrap="nowrap" style="text-align: center;"><bean:write name="element" property="salesContractNo" /></td>
			<td nowrap="nowrap" style="text-align: center;"><bean:write name="element" property="projectName" /></td>
			<td nowrap="nowrap" style="text-align: center;"><bean:write name="element" property="mainSdate" /></td>
			<td nowrap="nowrap" style="text-align: center;"><html:text name="element" property="shippedDate" size="12" styleClass="Wdate" onfocus="WdatePicker({isShowClear:true})"></html:text> </td>
			<td nowrap="nowrap" style="text-align: center;"><html:text name="element" property="mainEdate" size="12" styleClass="Wdate" onfocus="WdatePicker({isShowClear:true})"></html:text></td>
			<td nowrap="nowrap" style="text-align: center;"><bean:write name="element" property="assignedMainStation" /></td>
		    <td nowrap="nowrap" style="text-align: center;">
		    &nbsp;<a href="<html:rewrite page='/maintenanceWorkPlanCustomizeAction.do'/>?method=toMaintenanceWorkPlanDisplayRecord&isOpen=Y&id=${element.r1}" target="_blnk"><bean:write name="element" property="maintPersonnel"/></a>&nbsp;
			<td nowrap="nowrap" style="text-align: center;">
				<select name="maintlogic">
					<option value="1">�߼�һ</option>
					<option value="2">�߼���</option>
					<option value="3">�߼���</option>
					<option value="4">�߼���</option>
					<option value="5">�߼���</option>
				</select>
				<input type="hidden" name="hmaintlogic" value="${element.r2 }"/>
			</td>
		    </td>      
		    </tr>
		    </logic:iterate>
    </logic:notEmpty>    
    </table>

    </logic:present>
</html:form>

<script>
//�����ʼ��Ϊ��һ�η��䱣���߼�
	function setMaintPersonnel(){
		var hismp=document.getElementsByName("hmaintlogic");
		var maintlogic=document.getElementsByName("maintlogic");
		for(var i=0;i<hismp.length;i++){
			var hmpval=hismp[i].value;
			if(hmpval!=""){
				var mpsel=maintlogic[i];
				//ѭ�������������ֵ
				for(var j=0;j<mpsel.length;j++){
		              if(hmpval==mpsel.options[j].value){  
		            	  mpsel.options[j].selected=true;
		            	  break;
		              }  
		          }
			}
		}
	}
	setMaintPersonnel();
</script>	
	
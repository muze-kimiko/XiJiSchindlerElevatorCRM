<%@ page contentType="text/html;charset=GBK" %>
<script language="javascript" defer="defer" src="<html:rewrite forward='DatePickerJS'/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/jquery-1.9.1.min.js"/>"></script>

<logic:present name="lostElevatorReportBean">
<html:hidden name="lostElevatorReportBean" property="jnlno"/>
<html:hidden name="lostElevatorReportBean" property="billNo"/>
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
    <tr>
    <td nowrap="nowrap" height="23" colspan="4">&nbsp;<b>���ݱ��������Ϣ</td>
  </tr>
  <tr>
    <td class="wordtd">�����ֲ�:</td>
    <td width=30%" nowrap="nowrap">
    	${lostElevatorReportBean.maintDivision}
    </td>
    <td class="wordtd">����ά��վ:</td>
    <td nowrap="nowrap" width="30%">
    	${lostElevatorReportBean.maintStation}
    </td>
  </tr>
  <tr>    
    <td class="wordtd">ά����ͬ��:</td>
    <td>
    	
    	<a href="<html:rewrite page='/maintContractAction.do'/>?method=toDisplayRecord&typejsp=Yes&id=<bean:write name="lostElevatorReportBean"  property="billNo"/>" target="_blnk">${lostElevatorReportBean.maintContractNo}</a>
	 </td>
    <td nowrap="nowrap" class="wordtd">��Ŀ����:</td>
    <td>
    	${lostElevatorReportBean.projectName}
    </td>   
  </tr>     
  <tr>
    <td nowrap="nowrap" class="wordtd">̨��:</td>
    <td>
    	${lostElevatorReportBean.eleNum}
    </td>   
    <td nowrap="nowrap" class="wordtd">��������:</td>
    <td>
	    <logic:equal name="lostElevatorReportBean" property="fhRem" value="">
	    	<html:text name="lostElevatorReportBean" property="lostElevatorDate" size="12" styleClass="Wdate" onfocus="WdatePicker({isShowClear:true,readOnly:true})"/>
	    </logic:equal>
    	<logic:notEqual name="lostElevatorReportBean" property="fhRem" value="">
    		${lostElevatorReportBean.lostElevatorDate}
    	</logic:notEqual>
    </td>          
  </tr>
  <tr>      
    <td nowrap="nowrap" class="wordtd">��ͬ���:</td>
    <td>
    	${lostElevatorReportBean.contractNatureOf}
    </td>          
    <td nowrap="nowrap" class="wordtd">ԭ�����:</td>
    <td>
    	<logic:equal name="lostElevatorReportBean" property="fhRem" value="">
	    	<html:select name="lostElevatorReportBean" property="causeAnalysis" styleClass="default_input">
	    		<html:option value="">--��ѡ��--</html:option>
	    		<html:options collection="causeAnalysisList" property="id.pullid" labelProperty="pullname"/>
	    	</html:select>
	    </logic:equal>
	    <logic:notEqual name="lostElevatorReportBean" property="fhRem" value="">
	    	${lostElevatorReportBean.r4}
    	</logic:notEqual>
    </td>   
  </tr>
  <tr>      
    <td class="wordtd">ʹ�õ�λ��ϵ��:</td>
    <td>
    	<logic:equal name="lostElevatorReportBean" property="fhRem" value="">
    		<html:text name="lostElevatorReportBean" property="contacts" size="30" styleClass="default_input"/>
	    </logic:equal>
	    <logic:notEqual name="lostElevatorReportBean" property="fhRem" value="">
	    	${lostElevatorReportBean.contacts}
    	</logic:notEqual>
    </td>          
    <td nowrap="nowrap" class="wordtd">��ϵ�绰:</td>
    <td>
    	<logic:equal name="lostElevatorReportBean" property="fhRem" value="">
	    	<html:text name="lostElevatorReportBean" property="contactPhone" size="30" styleClass="default_input"/>
	    </logic:equal>
	    <logic:notEqual name="lostElevatorReportBean" property="fhRem" value="">
	    	${lostElevatorReportBean.contactPhone}
    	</logic:notEqual>
    </td>   
  </tr>
  <tr>      
    <td class="wordtd">��ע��ϸԭ��:</td>
    <td colspan="3">
    	<logic:equal name="lostElevatorReportBean" property="fhRem" value="">
	    	<html:textarea name="lostElevatorReportBean" property="detailedRem" rows="3" cols="100" styleClass="default_textarea"/>
	    </logic:equal>
	    <logic:notEqual name="lostElevatorReportBean" property="fhRem" value="">
	    	${lostElevatorReportBean.detailedRem}
    	</logic:notEqual>
    </td>          
  </tr>
  <tr>      
    <td nowrap="nowrap" class="wordtd">¼����:</td>
    <td>
    	${lostElevatorReportBean.operId}
    </td>         
    <td nowrap="nowrap" class="wordtd">¼������:</td>
    <td>
    	${lostElevatorReportBean.operDate}
    </td>   
  </tr>
</table>

<br>

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
  <tr>
    <td nowrap="nowrap" height="23" colspan="2">&nbsp;<b>������������Ϣ</b></td>        
  </tr>
  <tr>
    <td width="20%" class="wordtd">������λ����:</td>
    <td nowrap="nowrap" width="75%" class="isred">
    	<logic:equal name="lostElevatorReportBean" property="fhRem" value="">
	      <html:text name="lostElevatorReportBean" property="competeCompany" size="50" styleClass="default_input"/> 
	    </logic:equal>
	    <logic:notEqual name="lostElevatorReportBean" property="fhRem" value="">
	    	${lostElevatorReportBean.competeCompany}
    	</logic:notEqual>
    </td> 
  </tr>
  <tr>
    <td nowrap="nowrap" class="wordtd">�ָ��ƻ�:</td>
    <td nowrap="nowrap" class="isred">
    	<logic:equal name="lostElevatorReportBean" property="fhRem" value="">
	      <html:textarea name="lostElevatorReportBean" property="recoveryPlan" rows="3" cols="100" styleClass="default_textarea"/>
	    </logic:equal>
	    <logic:notEqual name="lostElevatorReportBean" property="fhRem" value="">
	    	${lostElevatorReportBean.recoveryPlan}
    	</logic:notEqual>
    </td>                  
  </tr>
  </table>
  
<!-- ���ϴ��ĸ��� -->
<%@ include file="UpLoadFileDisplay.jsp" %>

  <script language="javascript">
  	/* function isRed2(cause,company,plan){
  		if(cause!="�ͻ�����ά��" && cause!="����ͣ��" && cause!="��������" && company=="" && plan==""){
  			$(".isred").css("background-color","red");
  		}
  	}
  	$(document).ready(function() {
  		isRed2('${lostElevatorReportBean.causeAnalysis}','${lostElevatorReportBean.competeCompany}','${lostElevatorReportBean.recoveryPlan}')
	}) */
  </script>
</logic:present>
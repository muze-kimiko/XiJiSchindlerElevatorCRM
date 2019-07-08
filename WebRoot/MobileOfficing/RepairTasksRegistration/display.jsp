<%@page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<link href="/common/DatePicker/������/skin/WdatePicker.css"  type="text/style+css">
<script language="javascript" defer="defer" src="<html:rewrite forward='DatePickerJS'/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/jquery-1.9.1.min.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/openwindow.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/checkinput.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/dynamictable.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/math.js"/>"></script>
<html:hidden property="isreturn"/>
<html:hidden property="id"/>
<table width="99%" class="tb"  height="25">
		<tr>
			<td  align="center" width="90%"><b>������Ϣ</b></td>
		</tr>
</table>
<table  width="99%" border="0" cellpadding="0" cellspacing="0" class="tb" >
<tr>
    <td class="wordtd_a" width="10%">���޵���:</td>
    <td width="23%">
     ${CalloutMasterList.calloutMasterNo }
     <input type="hidden" name="calloutMasterNo" id="calloutMasterNo" value="${CalloutMasterList.calloutMasterNo }" />
    </td>
    <td class="wordtd_a" width="10%">¼����:</td>
    <td width="23%">
     ${CalloutMasterList.operId }
    </td>
    <td class="wordtd_a" width="10%">����ʱ��:</td>
    <td width="23%">
    ${CalloutMasterList.repairTime }
    </td>
</tr>
<tr>
    <td class="wordtd_a">���޷�ʽ:</td>
    <td >
     ${CalloutMasterList.repairMode }
    </td>
    <td class="wordtd_a">������:</td>
    <td >
     ${CalloutMasterList.repairUser }
    </td>
    <td class="wordtd_a">���޵绰:</td>
    <td >
     ${CalloutMasterList.repairTel }
    </td>
</tr>
<tr>
    <td class="wordtd_a">�������:</td>
    <td >
     ${CalloutMasterList.serviceObjects } 
    </td>
    <td class="wordtd_a">���޵�λ:</td>
    <td >
    ${CalloutMasterList.companyId } 
    </td>
    <td class="wordtd_a">��Ŀ����:</td>
    <td >
    ${CalloutMasterList.projectName }
    </td>
</tr>
<tr>
    <td class="wordtd_a">���ۺ�ͬ��:</td>
    <td >
    ${CalloutMasterList.salesContractNo }
    </td>
    <td class="wordtd_a">���ݱ��:</td>
    <td >
    ${CalloutMasterList.elevatorNo }
    </td>
    <td class="wordtd_a">����ͺ�:</td>
    <td >
    ${CalloutMasterList.elevatorParam }
    </td>
</tr>
<tr>
    <td class="wordtd_a">ά��վ:</td>
    <td >
    ${CalloutMasterList.maintStation }
    </td>
    <td class="wordtd_a">�ɹ�����:</td>
    <td >
    ${CalloutMasterList.assignObject }
    </td>
    <td class="wordtd_a">�绰:</td>
    <td >
    ${CalloutMasterList.phone }
    </td>
</tr>
<tr>
	<td class="wordtd_a">��Ŀ��ַ:</td>
    <td >
    ${CalloutMasterList.projectAddress }
    </td>
    <td class="wordtd_a">�Ƿ�����:</td>
    <td>
     ${CalloutMasterList.isTrap }
    </td>
    <td class="wordtd_a">�Ƿ��Ͱ�������:</td>
    <td>
    	<logic:present name="CalloutMasterList" property="isSendSms2">
    		<logic:match name="CalloutMasterList" property="isSendSms2" value="Y">��</logic:match>
    		<logic:match name="CalloutMasterList" property="isSendSms2" value="N">��</logic:match>
    	</logic:present>
    </td>
 </tr>
 <tr>   
    <td class="wordtd_a">��������:</td>
    <td  colspan="5">
    ${CalloutMasterList.repairDesc }
    </td>
</tr>
</table>
<br>
<table width="99%" class="tb"  height="25">
		<tr>
			<td  align="center" width="90%"><b>��ת��Ҫ��Ϣ</b></td>
		</tr>
</table>
<table  width="99%" border="0" cellpadding="0" cellspacing="0" class="tb" >
<tr>
    <td class="wordtd_a" width="10%">ת��ʱ��:</td>
    <td width="23%">
     ${CalloutProcessList.turnSendTime } 
    </td>
    <td class="wordtd_a" width="10%">��ת����:</td>
    <td width="23%">
     ${CalloutProcessList.turnSendId } 
    </td>
    <td class="wordtd_a" width="10%">��ת���˵绰:</td>
    <td width="23%">
    ${CalloutProcessList.turnSendTel } 
    </td>
</tr>
<tr>
    <td class="wordtd_a" width="10%">�ɹ�����ʱ��:</td>
    <td width="23%">
     ${CalloutProcessList.assignTime }
    </td>
    <td class="wordtd_a" width="10%">�ɹ�������:</td>
    <td width="23%">
     ${CalloutProcessList.assignUser }
    </td>
    <td class="wordtd_a" width="10%">�����˵绰:</td>
    <td width="23%">
     ${CalloutProcessList.assignUserTel }
    </td>
</tr>
</table><br>
<table width="99%" class="tb"  height="25">
		<tr>
			<td  align="center" width="90%"><b>�ֳ�ά����Ϣ</b></td>
		</tr>
</table>
<table  width="99%" border="0" cellpadding="0" cellspacing="0" class="tb" >
<tr>
    <td class="wordtd_a" width="10%">���ֳ�ʱ��:</td>
    <td width="23%">
     ${CalloutProcessList.arriveDateTime }
    </td>
    <td class="wordtd_a" width="10%">����λ��:</td>
    <td width="23%">
    ${CalloutProcessList.arriveLocation }
    </td> 
    <td class="wordtd_a" width="10%">�豸���:</td>
    <td width="23%">
     ${CalloutProcessList.deviceId }
    </td>      
</tr>
<tr>
    <td class="wordtd_a" width="10%">���ϴ���:</td>
    <td width="23%">
      ${CalloutProcessList.faultCode }
    </td>
    <td class="wordtd_a" width="10%">����״̬:</td>
    <td width="23%">
      ${CalloutProcessList.faultStatus }
    </td>
    <td class="wordtd_a" width="10%">��������:</td>
    <td width="23%">
    ${CalloutProcessList.hmtId }
    </td>
</tr>
<tr>
    <td class="wordtd_a" width="10%">�Ƿ�ͣ��:</td>
    <td width="23%" >
     ${CalloutProcessList.isStop }
    </td>
    <td class="wordtd_a" width="10%">ͣ��ʱ��:</td>
    <td width="23%" colspan="3">
     ${CalloutProcessList.stopTime }
    </td>
</tr>
<tr>
	<td class="wordtd_a" width="10%">ͣ�ݱ�ע:</td>
	<td width="23%" colspan="5">
		 ${CalloutProcessList.stopRem}
	</td>
</tr>
</table><br>
<table width="99%" class="tb"  height="25">
		<tr>
			<td  align="center" width="90%"><b>ά���깤��Ϣ</b></td>
		</tr>
</table>
<table  width="99%" border="0" cellpadding="0" cellspacing="0" class="tb" >
<tr>
    <td class="wordtd_a" width="10%">�깤ʱ��:</td>
    <td width="23%">
     ${CalloutProcessList.completeTime }
    </td >
    <td class="wordtd_a" width="10%">�깤λ��:</td>
    <td  width="23%">
    ${CalloutProcessList.fninishLocation }
    </td>
    <td class="wordtd_a">��������:</td>
    <td width="23%"> 
     ${CalloutProcessList.hftId }
    </td>
</tr>
   
<%-- <tr>
    <td class="wordtd_a" width="10%">�»�������ƺ�����:</td>
    <td width="23%">
     ${CalloutProcessList.newFittingName }
    </td>     
    <td class="wordtd_a">�Ƿ��շ�:</td>
    <td >
     ${CalloutProcessList.isToll }
    </td>
    <td class="wordtd_a">�շѽ��:</td>
    <td >
    ${CalloutProcessList.money }
    </td>
</tr> --%>
<tr>
	<%-- <td class="wordtd_a" width="10%">���Ϸ���:</td>
    <td width="23%">
    	${CalloutMasterList.hfcId}
    </td> --%>
    <td class="wordtd_a">ά�޹�������:</td>
    <td colspan="5">
     ${CalloutProcessList.processDesc }
    </td>
</tr>
<tr>
    <td class="wordtd_a">ά�ޱ�ע:</td>
    <td colspan="5">
     ${CalloutProcessList.serviceRem }
    </td>
</tr>
</table><br>

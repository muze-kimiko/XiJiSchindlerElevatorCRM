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
    <td class="wordtd_a" width="10%">�豸���:</td>
    <td width="23%">
     ${CalloutProcessList.deviceId }
    </td>
    <td class="wordtd_a" width="10%">����λ��:</td>
    <td width="23%">
    ${hashmapbean.elevatorLocation }
    </td>
    
</tr>
<tr>
    <td class="wordtd_a" width="10%">���ϴ���:</td>
    <td width="23%">
      ${CalloutProcessList.faultCode }
    </td>
    <td class="wordtd_a" width="10%">����״̬:</td>
    <td width="23%" >
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
     ${CalloutProcessList.IsStop }
    </td>
    <td class="wordtd_a" width="10%">ͣ��ʱ��:</td>
    <td width="23%" colspan="3">
     ${CalloutProcessList.stopTime }
    </td>
</tr>
<tr>
	<td class="wordtd_a" width="10%">ͣ�ݱ�ע:</td>
	<td width="23%" colspan="5">
		${CalloutProcessList.stopRem }
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
    <td class="wordtd_a" width="10%">ά�����ʱ��:</td>
    <td width="23%">
     ${CalloutProcessList.completeTime }
    </td >
    <td class="wordtd_a" width="10%">��������:</td>
    <td width="23%"> 
     ${CalloutProcessList.hftId }
    </td>
     <td class="wordtd_a" width="10%">�깤λ��:</td>
    <td  width="23%">
    ${hashmapbean.elevatorLocation2 }
    </td>
</tr>
<%-- <tr>
    <td class="wordtd_a" width="10%">�»�������ƺ�����:</td>
    <td width="23%">
     ${CalloutProcessList.newFittingName }
    </td>
    <td class="wordtd_a" width="10%">�Ƿ��շ�:</td>
    <td width="23%">
     ${CalloutProcessList.IsToll }
    </td>
    <td class="wordtd_a" width="10%">�շѽ��:</td>
    <td width="23%">
    ${CalloutProcessList.money }
    </td>
</tr> --%>
<tr>
	<td class="wordtd_a" width="10%">���Ϸ���:</td>
    <td width="23%">
    	${CalloutProcessList.hfcId}
    </td>
    <td class="wordtd_a" width="10%">ά�޹�������:</td>
    <td colspan="3">
     ${CalloutProcessList.processDesc }
    </td>
</tr>
<tr>
    <td class="wordtd_a" width="10%">����Ʒ���ͺ�<br/>��������ע:</td>
    <td colspan="5">
     ${CalloutProcessList.serviceRem }
    </td>
</tr>
<tr>
    <td class="wordtd_a" width="10%">�����:</td>
    <td width="23%">
 
    </td>
    <td class="wordtd_a" width="10%">�������:</td>
    <td width="23%">

    </td>
    <td class="wordtd_a" width="10%">�Ƿ��Ͷ���:</td>
    <td width="23%">

    </td>
</tr>
<tr>
<td class="wordtd_a">������:</td>
<td colspan="5"></td>
</tr>
</table><br>
<%-- <table width="99%" class="tb"  height="25">
		<tr>
			<td  align="center" width="90%"><b>�û��ظ���Ϣ</b></td>
		</tr>
</table>
<table  width="99%" border="0" cellpadding="0" cellspacing="0" class="tb" >
<tr>
    <td class="wordtd_a"  width="10%">�û���������:</td>
    <td width="23%">
     ${CalloutSmsList.serviceRating }
    </td>
    <td class="wordtd_a"  width="10%">�������:</td>
    <td width="56%">
     ${CalloutSmsList.otherRem }
    </td>

</tr>
</table><br> --%>
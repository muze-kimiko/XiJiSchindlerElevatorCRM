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
<tr>
    <td class="wordtd_a" width="10%">ά����Ա:</td>
    <td colspan="5">
     ${hashmapbean.r5name }
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
    <td class="wordtd_a" width="10%">ʵ�ʵ���ʱ��:</td>
    <td width="23%">
     ${CalloutProcessList.arriveDate }&nbsp;${CalloutProcessList.arriveTime }
    </td>
    <td class="wordtd_a" width="10%">�ύ����ʱ��:</td>
    <td width="23%">
     ${CalloutProcessList.arriveDateTime }
    </td>
     <td class="wordtd_a" width="10%">&nbsp;</td>
    <td width="23%">&nbsp;</td>
 </tr>
 <tr>
    <td class="wordtd_a" width="10%">����λ��:</td>
    <td width="23%">
    ${hashmapbean.elevatorLocation }
    </td> 
    <td class="wordtd_a" width="10%">�豸���:</td>
    <td width="23%">
    <logic:equal name="typejsp" value="sh">
    <input name="deviceId" id="deviceId" value="${CalloutProcessList.deviceId}"><font style="display: none;" color="red">*</font>
    </logic:equal>
    <logic:notEqual name="typejsp" value="sh">
     ${CalloutProcessList.deviceId }
     </logic:notEqual>
    </td>      
    <td class="wordtd_a" width="10%">&nbsp;</td>
    <td width="23%">&nbsp;</td>
</tr>
<logic:notEqual name="typejsp" value="sh">
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
</logic:notEqual>
<logic:equal name="typejsp" value="sh">
<tr>
    <td class="wordtd_a" width="10%">���ϴ���:</td>
    <td width="23%">
     <textarea rows="2" cols="35" class="default_textarea" name="faultCode" id="faultCode" > ${CalloutProcessList.faultCode }</textarea><font style="display: none;" color="red">*</font>
    </td>
    <td class="wordtd_a" width="10%">����״̬:</td>
    <td width="23%">
     <textarea rows="2" cols="35" class="default_textarea" name="faultStatus" id="faultStatus" > ${CalloutProcessList.faultStatus }</textarea><font style="display: none;" color="red">*</font>
    </td>
    <td class="wordtd_a" width="10%">��������:</td>
    <td width="23%">
    <input type="hidden" name="hmtId" id="hmtId" value="${CalloutProcessList.hmtId }"   >
    <input name="hmtName" id="hmtName" value="${hmtId }" readonly="readonly"><font style="display: none;" color="red">*</font>
    <input type="button" value=".." onclick="openWindowAndReturnValue3('searchCalloutAction','toSearchRecord','','')" class="default_input"/>
   
    </td>
</tr>
<tr>
    <td class="wordtd_a" width="10%">�Ƿ�ͣ��:</td>
    <td width="23%" >
    <html:select name="CalloutProcessList" property="isStop">
    	<html:option value="">��ѡ��</html:option>
    	<html:option value="Y">��</html:option>
    	<html:option value="N">��</html:option>
    </html:select>
     <%-- ${CalloutProcessList.isStop } --%>
    </td>
    <td class="wordtd_a" width="10%">ͣ��ʱ��:</td>
    <td width="23%" colspan="3">
    <html:text name="CalloutProcessList" property="stopTime" styleId="stopTime" styleClass="Wdate" size="21" onfocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
     <%-- ${CalloutProcessList.stopTime } --%>
    </td>
</tr>
<tr>
	<td class="wordtd_a" width="10%">ͣ�ݱ�ע:</td>
	<td width="23%" colspan="5">
		<html:textarea name="CalloutProcessList" property="stopRem" styleId="stopRem" rows="3" cols="60"> ${CalloutProcessList.stopRem}</html:textarea>
	</td>
</tr>
</logic:equal>
</table><br>

<table width="99%" class="tb"  height="25">
	<tr>
		<td  align="center" width="90%"><b>ͣ�ݻط�</b></td>
	</tr>
</table>
<table  width="99%" border="0" cellpadding="0" cellspacing="0" class="tb" >
<tr>
	<td class="wordtd_a" width="10%">ͣ�ݻطñ�ע:</td>
	<td width="23%" colspan="5">
		${CalloutMasterList.stophfRem}
	</td>
</tr>
<tr>
    <td class="wordtd_a" width="10%">ͣ�ݻط���:</td>
    <td width="23%" >
     	${CalloutMasterList.stophfOperid }
    </td>
    <td class="wordtd_a" width="10%">ͣ�ݻط�����:</td>
    <td width="23%">
     	${CalloutMasterList.stophfDate }
    </td>
    <td class="wordtd_a">&nbsp;</td>
    <td  width="23%">&nbsp;</td>
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
    ${hashmapbean.elevatorLocation2 }
    </td>
    <td class="wordtd_a">&nbsp;</td>
    <td width="23%">&nbsp;</td>
</tr>
<tr>
    <td class="wordtd_a">��������:</td>
    <td colspan="5" width="23%"> 
    <logic:equal name="typejsp" value="sh">
    	<input type="hidden" name="hftId" id="hftId" value="${CalloutProcessList.hftId}" >
    	<textarea rows="3" cols="60" name="hftDesc" id="hftDesc" readonly="readonly">${hftId }</textarea>
    	<input type="button" value="��ѡ��" onclick="openWindowAndReturnValue5('searchCalloutAction','toSearchRecord2','','')" class="default_input"/>
    	&nbsp;
    	<input type="button" value="�� ��" onclick="delselect();" class="default_input"/>
    </logic:equal>
    <logic:notEqual name="typejsp" value="sh">
     	${CalloutProcessList.hftId }
     </logic:notEqual>
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
	
    <td class="wordtd_a">ά�޹�������:</td>
    <td colspan="5">
    <logic:equal name="typejsp" value="sh">
    	<textarea rows="3" cols="60" name="processDesc" id="processDesc" value="${CalloutProcessList.processDesc}">${CalloutProcessList.processDesc }</textarea>
    </logic:equal>
    <logic:notEqual name="typejsp" value="sh">
     ${CalloutProcessList.processDesc }
     </logic:notEqual>
    </td>
</tr>
<tr>
    <td class="wordtd_a">����Ʒ���ͺ�<br/>��������ע:</td>
    <td colspan="5">
    <logic:equal name="typejsp" value="sh">
    	<textarea rows="3" cols="60" name="serviceRem" id="serviceRem" value="${CalloutProcessList.serviceRem}">${CalloutProcessList.serviceRem}</textarea>
    </logic:equal>
    <logic:notEqual name="typejsp" value="sh">
     	${CalloutProcessList.serviceRem }
     </logic:notEqual>
    </td>
</tr>
</table>
<br/>
<table width="99%" class="tb"  height="25">
		<tr>
			<td  align="center" width="90%"><b>ά���깤���</b></td>
		</tr>
</table>
<table  width="99%" border="0" cellpadding="0" cellspacing="0" class="tb" >
<logic:equal name="typejsp" value="sh">
<tr>
    <td class="wordtd_a">�����:</td>
    <td  width="23%">
    <input type="text" name="auditOperid" id="auditOperid" value="${CalloutMasterList.auditOperid }" readonly class="default_input_noborder">    
    </td>
    <td class="wordtd_a">�������:</td>
    <td  width="23%">
    <input type="text" name="auditDate" id="auditDate" value="${CalloutMasterList.auditDate }" readonly class="default_input_noborder">
    </td>
    <td class="wordtd_a">�Ƿ��Ͷ���:</td>
    <td  width="23%">
    <input type="radio" name="isSendSms" id="isSendSms" value="Y" <logic:equal name="CalloutMasterList" property="isSendSms" value="Y">checked</logic:equal>>��
    <input type="radio" name="isSendSms" id="isSendSms" value="N" <logic:notEqual name="CalloutMasterList" property="isSendSms" value="Y">checked</logic:notEqual>>��
    </td>
</tr>
<tr>
<td class="wordtd_a" width="10%">�ύ��ȫ�������:</td>
    <td width="23%">
    <logic:equal name="typejsp" value="sh">
	    <input type="radio" name="isSubSM" id="isSubSM" value="Y" <logic:equal name="CalloutMasterList" property="isSubSM" value="Y">checked</logic:equal> >��
    	<input type="radio" name="isSubSM" id="isSubSM" value="N" <logic:notEqual name="CalloutMasterList" property="isSubSM" value="Y">checked</logic:notEqual> >��
    </logic:equal>
    <logic:notEqual name="typejsp" value="sh">
    	${CalloutMasterList.isSubSM }
    </logic:notEqual>
    </td>
	<td class="wordtd_a" width="10%">���Ϸ���:</td>
    <td width="23%">
	    <logic:equal name="typejsp" value="sh">
	    <html:select name="CalloutMasterList" property="hfcId">
	    <%--html:option value="">��ѡ��</html:option--%>
	    <html:options collection="HotlineFaultClassificationList" property="hfcId" labelProperty="hfcName"/>
	    </html:select>
	    </logic:equal>
	     <logic:notEqual name="typejsp" value="sh">
	    ${CalloutMasterList.hfcId }
	    </logic:notEqual>
    </td>
    <td class="wordtd_a">&nbsp;</td>
	<td >&nbsp;</td>
 </td>
</tr>
<tr>
 <td class="wordtd_a">������:</td>
 <td colspan="5"><textarea rows="3" cols="60" name="auditRem" id="auditRem">${CalloutMasterList.auditRem }</textarea>
</tr>

</logic:equal>
<logic:notEqual name="typejsp" value="sh">
<tr>
    <td class="wordtd_a">�����:</td>
    <td  width="23%">
     ${CalloutMasterList.auditOperid } 
    </td>
    <td class="wordtd_a">�������:</td>
    <td  width="23%">
     ${CalloutMasterList.auditDate }
    </td>
    <td class="wordtd_a">�Ƿ��Ͷ���:</td>
    <td  width="23%">
    ${CalloutMasterList.isSendSms }
    </td>
</tr>
<tr>
	<td class="wordtd_a" width="10%">�ύ��ȫ�������:</td>
    <td width="23%">
    	${CalloutMasterList.isSubSM }
    </td>
	<td class="wordtd_a" width="10%">���Ϸ���:</td>
    <td width="23%">
    ${CalloutMasterList.hfcId }
    </td>
    <td class="wordtd_a">&nbsp;</td>
	 <td >&nbsp;</td>
</tr>
<tr>
 <td class="wordtd_a">������:</td>
 <td colspan="5">${CalloutMasterList.auditRem }</td>
</tr>
</logic:notEqual>
</table>

<br/>
<table width="99%" class="tb"  height="25">
		<tr>
			<td  align="center" width="90%"><b>��ȫ�������</b></td>
		</tr>
</table>
<table  width="99%" border="0" cellpadding="0" cellspacing="0" class="tb" >
<tr>
    <td class="wordtd_a">�����:</td>
    <td  width="23%">
     	${CalloutMasterList.smAuditOperid } 
    </td>
    <td class="wordtd_a">�������:</td>
    <td  width="23%">
     	${CalloutMasterList.smAuditDate }
    </td>
    <td class="wordtd_a">&nbsp;</td>
    <td  width="23%">&nbsp;</td>
</tr>
<tr>
	 <td class="wordtd_a">������:</td>
	 <td colspan="5">${CalloutMasterList.smAuditRem }</td>
</tr>
</table><br>





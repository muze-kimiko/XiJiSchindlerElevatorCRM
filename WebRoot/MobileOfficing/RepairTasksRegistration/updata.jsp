<%@page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<link href="/common/DatePicker/������/skin/WdatePicker.css"  type="text/style+css">
<script language="javascript" defer="defer" src="<html:rewrite forward='DatePickerJS'/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/jquery-1.9.1.min.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/openwindow.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/checkinput.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/dynamictable.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/math.js"/>"></script>
<table width="99%" class="tb"  height="25">
		<tr>
			<td  align="center" width="90%"><b>������Ϣ</b></td>
		</tr>
</table>
<table  width="99%" border="0" cellpadding="0" cellspacing="0" class="tb" >
<tr>
    <td class="wordtd_a" width="10%">���޵���:</td>
    <td width="23%">
        <html:hidden property="btn" styleId="btn" value="${btn1 }"/>
        <html:hidden property="btn2" styleId="btn2" value="${btn2 }"/>
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
    ${CalloutMasterList.r1 }
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


<!-- ���պ�ת��  -->
<logic:equal name="CalloutMasterList" property="handleStatus" value="0">
<tr>
    <td class="wordtd_a" width="10%">ת��ʱ��:</td>
    <td width="23%">
     <html:text name="CalloutProcessList" property="turnSendTime" value="${turnSendTime }" styleId="turnSendTime" styleClass="Wdate" size="21" onfocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
    </td>
    <td class="wordtd_a" width="10%">��ת����:</td>
    <td width="23%"> 
     <input type="text" id="username" readonly="readonly" value="${turnName}">
      <html:hidden name="CalloutProcessList" property="turnSendId" styleId="turnSendId"/>
       <input type="button" value=".." onclick="openWindowAndReturnValue3('searchStaffAction','toSearchRecord3','mainStation=${CalloutMasterList.maintStation}&type=jx','')" class="default_input"/>
    </td>
    <td class="wordtd_a" width="10%">��ת���˵绰:</td>
    <td width="23%">
    <input type="text" id="turnSendTel" name="turnSendTel" readonly="readonly"  value="${CalloutProcessList.turnSendTel}">
    </td>
</tr>
<tr>
    <td class="wordtd_a" width="10%">�ɹ�����ʱ��:</td>
    <td width="23%">
     <html:text name="CalloutProcessList" property="assignTime" value="${assignTime }" styleId="assignTime" styleClass="Wdate" size="21" onfocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
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
</logic:equal>

<!-- ���պ�ת��  -->



<logic:notEqual name="CalloutMasterList" property="handleStatus" value="0">
<tr>
    <td class="wordtd_a" width="10%">ת��ʱ��:</td>
    <td width="23%">
     ${CalloutProcessList.turnSendTime } 
    </td>
    <td class="wordtd_a" width="10%">��ת����:</td>
    <td width="23%">
     ${turnName}
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
</logic:notEqual>

</tr>
</table><br>
<table width="99%" class="tb"  height="25">
		<tr>
			<td  align="center" width="90%"><b>�ֳ�ά����Ϣ</b></td>
		</tr>
</table>
<table id="arriveTable"  width="99%" border="0" cellpadding="0" cellspacing="0" class="tb" >


<!-- ���� -->
<logic:equal name="CalloutMasterList" property="handleStatus" value="1">
<tr>
    <td class="wordtd_a" width="10%">���ֳ�ʱ��:</td>
    <td width="23%">
     <html:text name="CalloutProcessList" property="arriveDateTime" value="${arriveDateTime }" styleId="arriveDateTime" styleClass="Wdate" size="21" onfocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/><font style="display: none;" color="red">*</font>
    </td>
    <td class="wordtd_a" width="10%">����λ��:</td>
    <td width="23%">
    <input name="arriveLocation" id="arriveLocation" size="30"><font style="display: none;" color="red">*</font>
    </td> 
    <td class="wordtd_a" width="10%">�豸���:</td>
    <td width="23%">
      <input name="deviceId" id="deviceId"><font style="display: none;" color="red">*</font>
    </td>      
</tr>
<tr>
    <td class="wordtd_a" width="10%">���ϴ���:</td>
    <td width="23%">
      <input name="faultCode" id="faultCode"><font style="display: none;" color="red">*</font>
    </td>
    <td class="wordtd_a" width="10%">����״̬:</td>
    <td width="23%">
    	<textarea rows="2" cols="35" class="default_textarea" name="faultStatus" id="faultStatus" ></textarea><font style="display: none;" color="red">*</font>
    </td>
    <td class="wordtd_a" width="10%">��������:</td>
    <td width="23%">
        <input type="hidden" name="hmtId" id="hmtId" value="${hmtId }"   >
    <input name="hmtName" id="hmtName" value="${hmtName }" readonly="readonly"><font style="display: none;" color="red">*</font>
    <input type="button" value=".." onclick="openWindowAndReturnValue3('searchCalloutAction','toSearchRecord','','')" class="default_input"/>
    </td>
</tr>
</logic:equal>

<!-- ���� -->


<logic:notEqual name="CalloutMasterList" property="handleStatus" value="1">
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
</logic:notEqual>


<!-- ͣ�� -->
<logic:equal name="CalloutMasterList" property="handleStatus" value="2">
<tr>
    <td class="wordtd_a" width="10%">�Ƿ�ͣ��:</td>
    <td width="23%" >
     ${CalloutProcessList.isStop }
     <select name="isStop" id="isStop">
        <option value="">��ѡ��</option>
        <option value="Y">��</option>
        <option value="N">��</option>
     </select>
    </td>
    <td class="wordtd_a" width="10%">ͣ��ʱ��:</td>
    <td width="23%" colspan="3">
     <html:text name="CalloutProcessList" property="stopTime" value="${stopTime }" styleId="stopTime" styleClass="Wdate" size="21" onfocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
    </td>
</tr>
<tr>
	<td class="wordtd_a" width="10%">ͣ�ݱ�ע:</td>
	<td width="23%" colspan="5">
		<html:textarea name="CalloutProcessList" property="stopRem" styleId="stopRem" rows="3" cols="60"></html:textarea>
	</td>
</tr>
</logic:equal>

<!-- ͣ�� -->


<logic:notEqual name="CalloutMasterList" property="handleStatus" value="2">
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
</table><br>
<table width="99%" class="tb"  height="25">
		<tr>
			<td  align="center" width="90%"><b>ά���깤��Ϣ</b></td>
		</tr>
</table>
<table id="completeTable"  width="99%" border="0" cellpadding="0" cellspacing="0" class="tb" >



<!-- �깤 -->
<logic:equal name="iscomplete"  value="Y">
<tr>
    <td class="wordtd_a" width="10%">�깤ʱ��:</td>
    <td width="23%">
     <html:text name="CalloutProcessList" property="completeTime" value="${completeTime }" styleId="completeTime" styleClass="Wdate" size="21" onfocus="WdatePicker({isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/><font style="display: none;" color="red">*</font>
    </td >
    <td class="wordtd_a" width="10%">�깤λ��:</td>
    <td  width="23%">
    <input name="fninishLocation" id="fninishLocation" size="30"><font style="display: none;" color="red">*</font>
    </td>
    <td class="wordtd_a">��������:</td>
    <td width="23%">
    <textarea rows="3" cols="60" name="hftDesc" id="hftDesc" readonly="readonly"></textarea>
    <input name="hftDesc" id="hftDesc" readonly="readonly">
    <input type="button" value=".." onclick="openWindowAndReturnValue4('searchCalloutAction','toSearchRecord2','','')" class="default_input"/>
    </td>
</tr>
   
<%-- <tr>
    <td class="wordtd_a" width="10%">�»�������ƺ�����:</td>
    <td width="23%">
     <textarea rows="2" cols="20" name="newFittingName" id="newFittingName"></textarea>
    </td>     
    <td class="wordtd_a">�Ƿ��շ�:</td>
    <td >
     <html:select name="CalloutProcessList" property="isToll" styleId="isToll">
       <html:option value="">��ѡ��</html:option>
        <html:option value="Y">��</html:option>
         <html:option value="N">��</html:option>
     </html:select>
    </td>
    <td class="wordtd_a">�շѽ��:</td>
    <td >
    <input name="money" id="money" >
    </td>
</tr> --%>
<tr>
	<%-- <td class="wordtd_a" width="10%">���Ϸ���:</td>
    <td width="23%">
    <html:select name="CalloutMasterList" property="hfcId">
    <html:options collection="HotlineFaultClassificationList" property="hfcId" labelProperty="hfcName"/>
    </html:select>
    
    </td> --%>
    <td class="wordtd_a">ά�޹�������:</td>
    <td colspan="5">
      <textarea rows="3" cols="60" name="processDesc" id="processDesc"></textarea>
    </td>
</tr>
<tr>
    <td class="wordtd_a">ά�ޱ�ע:</td>
    <td colspan="5">
       <textarea rows="3" cols="60" name="serviceRem" id="serviceRem"></textarea>
    </td>
</tr>
</logic:equal>

<!-- �깤 -->





<logic:notEqual name="iscomplete"  value="Y">
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
</logic:notEqual>
</table><br>

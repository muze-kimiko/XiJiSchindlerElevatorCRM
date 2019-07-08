<%@page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/html-table.tld" prefix="table"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"";
%>
<link rel="stylesheet" type="text/css" href="<html:rewrite forward='formCSS'/>">
<link href="/XJSCRM/common/css/bb.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="<html:rewrite forward='contractCSS'/>">
<script language="javascript" src="<html:rewrite forward='calendarJS'/>"></script>
<script language="javascript" src="<html:rewrite forward='pageJS'/>"></script>
<script language="javascript" defer="defer" src="<html:rewrite forward='DatePickerJS'/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/jquery-1.9.1.min.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/openwindow.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/checkinput.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/dynamictable.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/math.js"/>"></script>

<html:errors/> 
<html:form action="/hotphoneAction.do?method=tosavestophf" enctype="multipart/form-data">
<br>
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
     <input type="hidden" name="calloutMasterNo" value="${CalloutMasterList.calloutMasterNo }"/>
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
<logic:notEqual name="typejsp" value="sh">
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
</logic:notEqual>
<logic:equal name="typejsp" value="sh">
<tr>
    <td class="wordtd_a" >���޷�ʽ:</td>
    <td >
    <html:select name="CalloutMasterList" property="repairMode">
     <html:options collection="rmList" property="id.pullid" labelProperty="pullname"></html:options>
     </html:select><font color="red">*</font>
    </td>
    <td class="wordtd_a" >������:</td>
    <td >
    <div id="bxp">
     <input type="text" name="repairUser" value="${CalloutMasterList.repairUser }" class="default_input"><font color="red">*</font>
    </div>
    </td>
    <td class="wordtd_a" >���޵绰:</td>
    <td >
    <div id="bxt">
    <input type="text" name="repairTel" value="${CalloutMasterList.repairTel }" ><font color="red">*</font>
    </div>
    </td> 
</tr>
</logic:equal>
<tr>
    <td class="wordtd_a">�������:</td>
    <td >
    <logic:equal name="typejsp" value="sh">
    <html:select name="CalloutMasterList" property="serviceObjects" styleId="serviceObjects">
     <html:options collection="soList" property="id.pullid" labelProperty="pullname"></html:options>
     </html:select>
    </logic:equal>
    <logic:notEqual name="typejsp" value="sh">
     ${CalloutMasterList.serviceObjects } 
     </logic:notEqual>
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
<tr> <td class="wordtd_a">��Ŀ���Ƽ�¥����:</td>
    <td >
    ${CalloutMasterList.projectAddress }
    </td>
    <td class="wordtd_a">�Ƿ�����:</td>
    <td>
    <logic:equal name="typejsp" value="sh">
     <html:radio name="CalloutMasterList" property="isTrap" value="N">������</html:radio>
     <html:radio name="CalloutMasterList" property="isTrap" value="Y" >����</html:radio>
    </logic:equal>
    <logic:notEqual name="typejsp" value="sh">
     ${CalloutMasterList.isTrap }
     </logic:notEqual>
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
    <logic:equal name="typejsp" value="sh">
    <textarea rows="3" cols="60" name="repairDesc" id="repairDesc" >${CalloutMasterList.repairDesc }</textarea>
    </logic:equal>
    <logic:notEqual name="typejsp" value="sh">
    ${CalloutMasterList.repairDesc }
    </logic:notEqual>
    </td>
</tr>
</table>

<%@ include file="calloutStophfDisplay2.jsp" %>

<table width="99%" class="tb"  height="25">
		<tr>
			<td  align="center" width="90%"><b>�ط��û���Ϣ</b></td>
		</tr>
</table>
<table  width="99%" border="0" cellpadding="0" cellspacing="0" class="tb" >
<logic:notEqual name="typejsp" value="ps">
<tr>

    <td class="wordtd_a" width="10%">�ط��û���������:</td>
    <td width="23%">
     ${CalloutMasterList.serviceAppraisal }
    </td>
    <td class="wordtd_a" width="10%">�ط�����������:</td>
    <td width="23%">
    ${CalloutMasterList.fittingSituation }
    </td>
    <td class="wordtd_a">�ط��շ����:</td>
    <td  width="23%">
    ${CalloutMasterList.tollSituation }
    </td>
</tr>
<tr>
    <td class="wordtd_a">�Ƿ�رջ���:</td>
    <td  width="23%">
     ${CalloutMasterList.isColse }
    </td>
 	<td class="wordtd_a">�طñ�ע:</td>
    <td colspan="3">
     ${CalloutMasterList.visitRem }
    </td>
</tr>
</logic:notEqual>
<logic:equal name="typejsp" value="ps">
<tr>

    <td class="wordtd_a" width="10%">�ط��û���������:</td>
    <td width="23%">
     <html:select property="serviceAppraisal">
     <html:option value="1">�ǳ�����</html:option>
     <html:option value="2">����</html:option>
     <html:option value="3">һ��</html:option>
     <html:option value="4">������</html:option>
     <html:option value="5">�ǳ�������</html:option>
     </html:select>
    </td>
    <td class="wordtd_a" width="10%">�ط�����������:</td>
    <td width="23%">
    <input type="radio" name="fittingSituation" id="fittingSituation" value="1" checked>��ʵ
    <input type="radio" name="fittingSituation" id="fittingSituation" value="2">����ʵ
    </td>
    <td class="wordtd_a" width="10%">�ط��շ����:</td>
    <td width="23%">
    <input type="radio" name="tollSituation" id="tollSituation" value="1" checked>��ʵ
    <input type="radio" name="tollSituation" id="tollSituation" value="2">����ʵ
    </td>    
</tr>
<tr>
    <td class="wordtd_a" width="10%">�Ƿ�رջ���:</td>
    <td  width="23%">
     <input type="radio" name="isColse" id="isColse" value="1" checked>�ر�
    <input type="radio" name="isColse" id="isColse" value="2">���ر�
    </td>
	<td class="wordtd_a" width="10%">�طñ�ע:</td>
    <td colspan="3">
     <textarea rows="3" cols="60" name="visitRem" id="visitRem" ></textarea>
    </td>
</tr>
</logic:equal>
</table>

<br/>
<table width="99%" class="tb"  height="25">
		<tr>
			<td  align="center" width="90%"><b>���޶�����Ϣ</b></td>
		</tr>
</table>
<table  width="99%" border="0" cellpadding="0" cellspacing="0" class="tb" >
<tr>
    <td class="wordtd_a" width="10%">�������ŷ��ͺ���:</td>
    <td width="23%">
     ${calloutSmsBean.smsTel} 
    </td>
    <td class="wordtd_a" width="10%">������������:</td>
    <td width="23%">
     ${calloutSmsBean.smsContent} 
    </td>
    <td class="wordtd_a" width="10%">�������ŷ���ʱ��:</td>
    <td width="23%">
    ${calloutSmsBean.smsSendTime} 
    </td>
</tr>
<tr>
    <td class="wordtd_a" width="10%">�طö��ŷ��ͺ���:</td>
    <td width="23%">
     ${calloutSmsBean.smsTel2}
    </td>
    <td class="wordtd_a" width="10%">�طö�������:</td>
    <td width="23%">
     ${calloutSmsBean.smsContent2}
    </td>
    <td class="wordtd_a" width="10%">�طö��ŷ���ʱ��:</td>
    <td width="23%">
     ${calloutSmsBean.smsSendTime2}
    </td>
</tr>
</table>
</br>
<table  width="99%" border="0" cellpadding="0" cellspacing="0" class="tb" >
 <tr>
 <td class="wordtd_a" width="10%">�ͻ�ǩ��:</td>
    <td class="inputtd" >
    <logic:notEmpty name="CalloutProcessList" property="customerSignature">
		<img src="<%=basePath%>/hotphoneAction.do?method=toDownloadFileRecord&customerSignature=${CalloutProcessList.customerSignature}"
			width="${CSwidth}" height="${CSheight}"  id="${CalloutProcessList.customerSignature}_1"> 	
	</logic:notEmpty>
	<logic:notEmpty name="CalloutProcessList" property="customerImage">
		<img src="<%=basePath%>/hotphoneAction.do?method=toDownloadFileRecord&customerImage=${CalloutProcessList.customerImage}"
			width="${CIwidth}" height="${CIheight}" id="${CalloutProcessList.customerImage}_2">
	</logic:notEmpty>
    </td>
  </tr>
</table>

</html:form>

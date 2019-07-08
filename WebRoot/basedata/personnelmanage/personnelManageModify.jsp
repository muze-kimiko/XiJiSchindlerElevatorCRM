<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<link rel="stylesheet" type="text/css" href="<html:rewrite forward='formCSS'/>">
<script language="javascript" src="<html:rewrite forward='validator'/>"></script>
<script language="javascript" defer="defer" src="<html:rewrite forward='DatePickerJS'/>"></script>
<script language="javascript" src="<html:rewrite forward='pageJS'/>"></script>
<link href="/XJSCRM/common/css/bb.css" rel="stylesheet" type="text/css">
<br>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <title>XJSCRM</title>
</head>
<body>
<html:errors/>
<html:form action="/personnelManageAction.do?method=toUpdateRecord" enctype="multipart/form-data">
<html:hidden property="id"/>
<html:hidden property="isreturn"/>
<html:hidden name="personnelManageBean" property="billno"/>
<html:hidden name="personnelManageBean" property="oldygid" value="${personnelManageBean.ygid }"/>

  <table width="100%" height="24" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr class="td_2">
      <td width="2" background="/images/line.gif"></td>
      <td width="100" class="tab-on" id="navcell" onclick="switchCell('0')" >Ա������</td>
      <td width="3" background="/images/line_2.gif"><img src="/images/line_1.gif" width="1" height="25"></td>
        
      <td width="100" class="tab-off" id="navcell" onclick="switchCell('1')">��λ��ʷ</td>  
      <td width="3" background="/images/line_2.gif"><img src="/images/line_1.gif" width="1" height="25"></td>

      <td width="100" class="tab-off" id="navcell" onclick="switchCell('2')">��ѵ����</td>
      <td width="3" background="/images/line_2.gif"><img src="/images/line_1.gif" width="1" height="25"></td>
      
      <td width="100" class="tab-off" id="navcell" onclick="switchCell('3')">֤�鵵��</td>
      <td width="3" background="/images/line_2.gif"><img src="/images/line_1.gif" width="1" height="25"></td>
      
      <td width="100" class="tab-off" id="navcell" onclick="switchCell('4')">������ȡ����</td>
      <td width="60%" background="/images/line_2.gif">&nbsp;</td>

    </tr>
  </table>
  <table id="table1" width="100%" border="0" align="center" cellpadding="0" cellspacing="0">  
   
    <tr id="tb">
      <td width="3" background="/images/line_3.gif" bgcolor="#FFFFFF" style="background-repeat:repeat-y;">&nbsp;</td>
      <td bgcolor="#ffffff">
        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
          <br>
          <tr>
            <td class="wordtd">Ա������:</td>
            <td width="35%"><html:text name="personnelManageBean" property="ygid" styleClass="default_input"/></td>
            <td class="wordtd">Ա������:</td>
            <td><html:text name="personnelManageBean" property="name" styleClass="default_input"/><font color="red">*</font></td>
          </tr>
          <tr>
            <td class="wordtd"><bean:message key="personnelManage.sex"/>:</td>
            <td>  
              <html:radio name="personnelManageBean" property="sex" value="��" />��
              <html:radio name="personnelManageBean" property="sex" value="Ů" />Ů
            </td>
            <td class="wordtd"><bean:message key="personnelManage.idCardNo"/>:</td>
            <td><html:text name="personnelManageBean" property="idCardNo" styleClass="default_input"/><font color="red">*</font></td>
          </tr>  
          <tr>    
              <td class="wordtd"><bean:message key="personnelManage.education"/>:</td>
            <td><html:text name="personnelManageBean" property="education" styleClass="default_input"/><font color="red">*</font></td>
          	<td class="wordtd"><bean:message key="personnelManage.phone"/>:</td>
            <td><html:text name="personnelManageBean" property="phone" styleClass="default_input"/><font color="red">*</font></td>
          </tr>  
          <tr> 
          	<td class="wordtd"><bean:message key="personnelManage.familyAddress"/>:</td>
            <td ><html:text name="personnelManageBean" property="familyAddress" size="55" styleClass="default_input"/><font color="red">*</font></td>
          	<td class="wordtd">��������:</td>
            <td><html:text name="personnelManageBean" property="birthDate" size="17" styleClass="Wdate" onfocus="WdatePicker({isShowClear:true,readOnly:true})"/><font color="red">*</font></td>
          </tr>  
          <tr>
            <td class="wordtd">���õ绰:</td>
            <td><html:text name="personnelManageBean" property="phone2" styleClass="default_input"/><font color="red">*</font></td>
            <td class="wordtd"><bean:message key="personnelManage.contractNo"/>:</td>
            <td><html:text name="personnelManageBean" property="contractNo" styleClass="default_input"/></td>
          </tr>  
          <tr>    
            <td class="wordtd"><bean:message key="personnelManage.startDate"/>:</td>
            <td><html:text name="personnelManageBean" property="startDate" size="17" styleClass="Wdate" onfocus="WdatePicker({isShowClear:true,readOnly:true})"/></td>
          <td class="wordtd"><bean:message key="personnelManage.years"/>:</td>
            <td><html:text name="personnelManageBean" property="years" styleClass="default_input"/></td>
          </tr>  
          <tr>    
            <td class="wordtd"><bean:message key="personnelManage.endDate"/>:</td>
            <td><html:text name="personnelManageBean" property="endDate" size="17" styleClass="Wdate" onfocus="WdatePicker({isShowClear:true,readOnly:true})"/></td>
            <td class="wordtd"><bean:message key="personnelManage.workAddress"/>:</td>
            <td><html:text name="personnelManageBean" property="workAddress" size="55" styleClass="default_input"/></td>
          </tr>  
          <tr>
            <td class="wordtd">�����ֲ�:</td>
            <td><html:text name="personnelManageBean" property="sector" styleClass="default_input"/></td>
            <td class="wordtd">��������:</td>
            <td><html:text name="personnelManageBean" property="maintStation" styleClass="default_input"/></td>
          </tr>  
          <tr>
            <td class="wordtd">����:</td>
            <td><html:text name="personnelManageBean" property="hubie" styleClass="default_input"/><font color="red">*</font></td>
             <td class="wordtd">�Ƿ�פ��:</td>
            <td>
	            <html:select name="personnelManageBean" property="iszhuwai" >
	            	<html:option value="��">��</html:option>
	            	<html:option value="��">��</html:option>
	            	<html:option value="ʵϰ��">ʵϰ��</html:option>
	            	<html:option value="����">����</html:option>
	            </html:select><font color="red">*</font>
	        </td>
         </tr>
          
          <tr>
           	<td class="wordtd">��λ����:</td>
            <td>
            	<html:select name="personnelManageBean" property="jobLeval" >
            		<logic:iterate id="pmjl" name="personnelManageJobLeval">
            		<html:option value="${pmjl.pullname }">${pmjl.pullname }</html:option>
	            	<!--<html:option value="����">����</html:option>
	            	<html:option value="����">����</html:option>
	            	<html:option value="����֧��">����֧��</html:option>
	            	<html:option value="��ְ">��ְ</html:option>
	            	<html:option value="ʵϰ��">ʵϰ��</html:option>
	            	<html:option value="ά����">ά����</html:option>
	            	<html:option value="ά������">ά������</html:option>
	            	<html:option value="ά��վ��">ά��վ��</html:option>
	            	<html:option value="ά�޼���Ա">ά�޼���Ա</html:option>
	            	<html:option value="��Ա">��Ա</html:option>
	            	<html:option value="���ۼ�������">���ۼ�������</html:option>
	            	<html:option value="����">����</html:option>-->
	            	</logic:iterate>
	            </html:select><font color="red">*</font>
            </td>
         	<td class="wordtd">��λ����:</td>
            <td>
	            <html:select name="personnelManageBean" property="level" >
	            	<html:option value="">��ѡ��</html:option>
	            	<logic:iterate id="pml" name="personnelManageLevel">
            		<html:option value="${pml.pullname }">${pml.pullname }</html:option>
	            	<!--<html:option value="">��ѡ��</html:option>
	            	<html:option value="�Ҵ�">�Ҵ�</html:option>
	            	<html:option value="�ƴ�">�ƴ�</html:option>
	            	<html:option value="�̴�">�̴�</html:option>
	            	<html:option value="���">���</html:option>
	            	<html:option value="�ڴ�">�ڴ�</html:option>-->
	            	</logic:iterate>
	            </html:select>
	        </td>
          </tr> 
          <tr>
            <td class="wordtd">������:</td>
            <td><html:text name="personnelManageBean" property="probation" styleClass="default_input"/><font color="red">*</font></td>
            <td class="wordtd">�����ڹ���:</td>
            <td><html:text name="personnelManageBean" property="probationgz" styleClass="default_input" onchange="checkthisvalue(this);"/></td>
          </tr>
          
          <tr>
          <td class="wordtd">���ý�������:</td>
            <td><html:text name="personnelManageBean" property="probationEdate" size="17" styleClass="Wdate" onfocus="WdatePicker({isShowClear:true,readOnly:true})"/></td>
          	
	      <td class="wordtd">��ȡ��ȫñ����:</td>
            <td><html:text name="personnelManageBean" property="r3" size="17" styleClass="Wdate" onfocus="WdatePicker({isShowClear:true,readOnly:true})"/></td>
	            
          </tr> 
          
          <tr>
            <td class="wordtd">�ͱ�Ь:</td>
            <td><html:text name="personnelManageBean" property="r1" styleClass="default_input"/></td>
            <td class="wordtd">��װ��:</td>
            <td><html:text name="personnelManageBean" property="r2" styleClass="default_input"/></td>
          </tr>
          
          <tr>
            <td class="wordtd"><bean:message key="personnelManage.rem"/>:</td>
            <td ><html:textarea name="personnelManageBean" property="rem" rows="3" cols="55" styleClass="default_textarea"/></td>
            
            <td class="wordtd"><bean:message key="personnelManage.enabledFlag"/>:</td>
            <td>  
              <html:radio name="personnelManageBean" property="enabledFlag" value="Y"/><bean:message key="pageword.yes"/>
              <html:radio name="personnelManageBean" property="enabledFlag" value="N"/><bean:message key="pageword.no"/>
            </td>
          </tr>
        </table>
      </td>
    </tr>
      
      <!-- ��λ��ʷ -->
      <tr id="tb" style="display:none;" >
        <td width="3" background="/images/line_3.gif" bgcolor="#FFFFFF" style="background-repeat:repeat-y;" >&nbsp;</td>
        <td bgcolor="#ffffff" >
          <br> 
          <input name="BtnAdd" type="Button" value="�� ��" class="default_input" onclick="addonerows(jobHistory,0)"/> 
          <input name="BtnDel" type="button" value="ɾ ��" class="default_input" onclick="deleteRow(jobHistory,0)"/> 
          <table id="jobHistory" width="100%" border="0" cellpadding="0" cellspacing="0" class="tb" style="margin-top:5">          
            <tr id="titleRow_1" class="td_3_1">
              <td><input type="checkbox" name="ckAll"  onclick="op(this,jobHistory)"/></td>
              <td>�ڸ�ʱ���<font color="red">*</font></td>
              <td>������ַ<font color="red">*</font></td>
              <td>��λ����<font color="red">*</font></td>
              <td>����<font color="red">*</font></td>
              <td>��������<font color="red">*</font></td>
            </tr>    
            <tr id="tr_0">
              <td align="center">
                <input type="checkbox" name="cb1" id="cb1" onclick="opleft(this,jobHistory)"/>
              </td>
              <td align="center">
                <input type="text" name="sdate1" id="sdate1" size="12" class="Wdate" onfocus="WdatePicker({isShowClear:true,readOnly:true})"/>
                -
                <input type="text" name="edate1" id="edate1" size="12" class="Wdate" onfocus="WdatePicker({isShowClear:true,readOnly:true})"/>
              </td>
              <td align="center"><input type="text" name="workAddress1" id="workAddress1" size="55" class="default_input"/></td>
              <td align="center"><input type="text" name="jobTitle1" id="jobTitle1" size="30" class="default_input"/></td>
              <td align="center"><input type="text" name="level1" id="level1" size="10" class="default_input"/></td>
              <td align="center"><input type="text" name="revisionDate1" id="revisionDate1" size="12" class="Wdate" onfocus="WdatePicker({isShowClear:true,readOnly:true})"/></td>
            </tr> 
            <logic:iterate id="element" name="jobHistoryList" >
              <tr id="tr_0">
                <td align="center">
                  <input type="checkbox" name="cb1" id="cb1" onclick="opleft(this,jobHistory)"/>
                </td>
                <td align="center">
                  <input type="text" name="sdate1" id="sdate1" size="12" value="${element.sdate}" class="Wdate" onfocus="WdatePicker({isShowClear:true,readOnly:true})"/>
                  -
                  <input type="text" name="edate1" id="edate1" size="12" value="${element.edate}" class="Wdate" onfocus="WdatePicker({isShowClear:true,readOnly:true})"/>
                </td>
                <td align="center"><input type="text" name="workAddress1" id="workAddress1" value="${element.workAddress}" size="55" class="default_input"/></td>
                <td align="center"><input type="text" name="jobTitle1" id="jobTitle1" value="${element.jobTitle}" size="30" class="default_input"/></td>
                <td align="center"><input type="text" name="level1" id="level1" value="${element.level}" size="10" class="default_input"/></td>
                <td align="center"><input type="text" name="revisionDate1" id="revisionDate1" value="${element.revisionDate}" size="12" class="Wdate" onfocus="WdatePicker({isShowClear:true,readOnly:true})"/></td>
              </tr>
          </logic:iterate>   
          </table>               
        </td>       
      </tr>
      
      <!-- ��ѵ���� -->
      <tr id="tb" style="display:none;">
        <td width="3" background="/images/line_3.gif" bgcolor="#FFFFFF" style="background-repeat:repeat-y;" >&nbsp;</td>
        <td bgcolor="#ffffff">
          <br>     
          <input name="BtnAdd" type="Button" value="�� ��" class="default_input" onclick="addonerows(trainingHistory,1)"/> 
          <input name="BtnDel" type="button" value="ɾ ��" class="default_input" onclick="deleteRow(trainingHistory,1)"/> 
          <table id="trainingHistory" width="100%" border="0" cellpadding="0" cellspacing="0" class="tb" style="margin-top:5">          
            <tr id="titleRow_2" class="td_3_1">
              <td><input type="checkbox" name="ckAll"  onclick="op(this,trainingHistory)"/></td>
              <td>��ѵ����<font color="red">*</font></td>
              <td>��ѵ�γ�<font color="red">*</font></td>
              <td>��ʱ<font color="red">*</font></td>
              <td>���۳ɼ�<font color="red">*</font></td>
              <td>ʵ�ٳɼ�<font color="red">*</font></td>
              <td>���ֳɼ�</td>
              <td>��ѵ����/��ע</td>
              <td>�ܷ�</td>
              <td>�ȼ�</td>
            </tr>
            <tr id="tr_1">
              <td align="center">
                <input type="checkbox" name="cb1" id="cb1" onclick="opleft(this,trainingHistory)"/>
              </td>
              <td align="center">
                <input type="text" name="straDate1" id="straDate1" size="12" class="Wdate" onfocus="WdatePicker({isShowClear:true,readOnly:true})"/>
                -
                <input type="text" name="etraDate1" id="etraDate1" size="12" class="Wdate" onfocus="WdatePicker({isShowClear:true,readOnly:true})"/>
              </td>
              <td align="center">
              		<textarea rows="2" cols="30" name="traCourse1" id="traCourse1" ></textarea> 
              </td>
              <td align="center"><input type="text" name="lesson1" onchange="checkthisvalue(this);" id="lesson1" size="8" class="default_input"/></td>
              <td align="center"><input type="text" name="theoreticalResults1" onchange="checkthisvalue(this);" id="theoreticalResults1" size="8" class="default_input"/></td>
              <td align="center"><input type="text" name="practicalResults1" onchange="checkthisvalue(this);" id="practicalResults1" size="8" class="default_input"/></td>
              <td align="center"><input type="text" name="perforResults1" onchange="checkthisvalue(this);" id="perforResults1" size="8" class="default_input"/></td>            
              <td align="center">
	              <textarea rows="2" cols="20" name="traAssess1" id="traAssess1" ></textarea>
              </td>
              <td align="center"><input type="text" name="totalScore1" onchange="checkthisvalue(this);" id="totalScore1" size="8" class="default_input"/></td>
              <td align="center"><input type="text" name="rating1" id="rating1" size="8" class="default_input"/></td>            
            </tr>            
            <logic:iterate id="element" name="trainingHistoryList" >
              <tr id="tr_1">
                <td align="center">
                  <input type="checkbox" name="cb1" id="cb1" onclick="opleft(this,trainingHistory)"/>
                </td>
                <td align="center">
                  <input type="text" name="straDate1" id="straDate1" value="${element.straDate}" size="12" class="Wdate" onfocus="WdatePicker({isShowClear:true,readOnly:true})"/>
                  -
                  <input type="text" name="etraDate1" id="etraDate1" value="${element.etraDate}" size="12" class="Wdate" onfocus="WdatePicker({isShowClear:true,readOnly:true})"/>
                </td>
                <td align="center">
                	<textarea rows="2" cols="30" name="traCourse1" id="traCourse1" >${element.traCourse}</textarea> 
                </td>
                <td align="center"><input type="text" name="lesson1" id="lesson1" value="${element.lesson}" onchange="checkthisvalue(this);" size="8" class="default_input"/></td>
                <td align="center"><input type="text" name="theoreticalResults1" id="theoreticalResults1" value="${element.theoreticalResults}" onchange="checkthisvalue(this);" size="8" class="default_input"/></td>
                <td align="center"><input type="text" name="practicalResults1" id="practicalResults1" value="${element.practicalResults}" onchange="checkthisvalue(this);" size="8" class="default_input"/></td>
                <td align="center"><input type="text" name="perforResults1" id="perforResults1" value="${element.perforResults}" onchange="checkthisvalue(this);" size="8" class="default_input"/></td>            
                <td align="center">
                	<textarea rows="2" cols="20" name="traAssess1" id="traAssess1" >${element.traAssess}</textarea>
                </td>
                <td align="center"><input type="text" name="totalScore1" id="totalScore1" value="${element.totalScore}" onchange="checkthisvalue(this);" size="8" class="default_input"/></td>
                <td align="center"><input type="text" name="rating1" id="rating1" value="${element.rating}" size="8" class="default_input"/></td>            
              </tr>
            </logic:iterate>
          </table>     
        </td>
      </tr>
      
      <!-- ֤�鵵�� -->
      <tr id="tb" style="display:none;">
        <td width="3" background="/images/line_3.gif" bgcolor="#FFFFFF" style="background-repeat:repeat-y;" >&nbsp;</td>
        <td bgcolor="#ffffff">
          <br>         
          <input name="BtnAdd" type="Button" value="�� ��" class="default_input" onclick="addonerows(certificateExam,2)"/> 
          <input name="BtnDel" type="button" value="ɾ ��" class="default_input" onclick="deleteRow(certificateExam,2)"/>  
          &nbsp;&nbsp;<font color="red">֤��Ų�ѯ</font>&nbsp;
	      <input type="text" id="searchvalNo1" name="searchvalNo1" value="" class="default_input"/>
	      <input type="button"  value="����" onclick="toSearchOperNo1()" class="default_input">
	      &nbsp;&nbsp;<input type="button"  value="��ʾȫ��" onclick="showAll('certificateExam')" class="default_input">
	      
          <table id="certificateExam" width="100%" border="0" cellpadding="0" cellspacing="0" class="tb" style="margin-top:5">          
            <tr id="titleRow_3" class="td_3_1">
              <td><input type="checkbox" name="ckAll"  onclick="op(this,certificateExam)"/></td>
              <td>֤���<font color="red">*</font></td>
              <td>֤������<font color="red">*</font></td>
              <td>֤������<font color="red">*</font></td>
              <td>��ʼ����<font color="red">*</font></td>
              <td>ʧЧ����<font color="red">*</font></td>
              <td>��֤����<font color="red">*</font></td>
              <td nowrap="nowrap">�Ƿ���<font color="red">*</font></td>
              <td nowrap="nowrap">�Ƿ�۷�<font color="red">*</font></td>
              <td>֤��ȥ��</td>
              <td>��ע</td>
            </tr>
            <tr id="tr_2">
              <td align="center">
                <input type="checkbox" name="cb1" id="cb1" onclick="opleft(this,certificateExam)"/>
              </td>
              <td align="center"><input type="text" name="certificateNo1" id="certificateNo1" class="default_input"/></td>
              <td align="center"><input type="text" name="certificateName1" id="certificateName1" class="default_input"/></td>
              <td align="center"><input type="text" name="certificateProperty1" id="certificateProperty1" size="10" class="default_input"/></td>
              <td align="center"><input type="text" name="startDate1" id="startDate1" size="12" class="Wdate" onfocus="WdatePicker({isShowClear:true,readOnly:true})"/></td>
              <td align="center"><input type="text" name="endDate1" id="endDate1" size="12" class="Wdate" onfocus="WdatePicker({isShowClear:true,readOnly:true})"/></td>                    
              
              <td align="center"><input type="text" name="issuingAuthority1" id="issuingAuthority1" size="30" class="default_input"/></td>
              <td align="center"><input type="text" name="isExpense1" id="isExpense1" size="10"  class="default_input"/>
              		<!-- <select name="isExpense1" id="isExpense1" class="default_input" >
		              	<option value="N">��</option>
		              	<option value="Y">��</option>
	              	</select> -->
              </td>
              <td align="center"><input type="text" name="isCharging1" id="isCharging1" size="10"  class="default_input"/>
              		<!-- <select name="isCharging1" id="isCharging1" class="default_input" >
		              	<option value="N">��</option>
		              	<option value="Y">��</option>
	              	</select> -->
              <td align="center">
              		<textarea rows="2" cols="20" name="certificateExt1" id="certificateExt1" ></textarea> 
              </td>
              <td align="center">
					<textarea rows="2" cols="20" name="rem0" id="rem0" ></textarea> 
			  </td>  
            </tr>
            <logic:iterate id="element" name="certificateExamList" >
              <tr id="tr_2">
                <td align="center">
                  <input type="checkbox" name="cb1" id="cb1" onclick="opleft(this,certificateExam)"/>
                </td>
                <td align="center"><input type="text" name="certificateNo1" id="certificateNo1" value="${element.certificateNo}" class="default_input"/></td>
                <td align="center"><input type="text" name="certificateName1" id="certificateName1" value="${element.certificateName}" class="default_input"/></td>
                <td align="center"><input type="text" name="certificateProperty1" id="certificateProperty1" value="${element.certificateProperty}" size="10" class="default_input"/></td>
                <td align="center"><input type="text" name="startDate1" id="startDate1" value="${element.startDate}" size="12" class="Wdate" onfocus="WdatePicker({isShowClear:true,readOnly:true})"/></td>
                <td align="center"><input type="text" name="endDate1" id="endDate1" value="${element.endDate}" size="12" class="Wdate" onfocus="WdatePicker({isShowClear:true,readOnly:true})"/></td>                    
              
	              <td align="center"><input type="text" name="issuingAuthority1" id="issuingAuthority1" size="30" class="default_input" value="${element.issuingAuthority}"/></td>
	              <td align="center">
	              		<input type="text" name="isExpense1" id="isExpense1" value="${element.isExpense}" size="10" class="default_input"/></td>
	              		<!-- <select name="isExpense1" id="isExpense1" class="default_input" >
			              	<option value="N" <logic:equal name="element" property="isExpense" value="N">selected</logic:equal> >��</option>
			              	<option value="Y" <logic:equal name="element" property="isExpense" value="Y">selected</logic:equal> >��</option>
		              	</select> -->
	              </td>
	              <td align="center">
	             		<input type="text" name="isCharging1" id="isCharging1" value="${element.isCharging}" size="10" class="default_input"/></td>
	              		<!--<select name="isCharging1" id="isCharging1" class="default_input" >
			              	<option value="N" <logic:equal name="element" property="isCharging" value="N">selected</logic:equal> >��</option>
			              	<option value="Y" <logic:equal name="element" property="isCharging" value="Y">selected</logic:equal> >��</option>
		              	</select>-->
	              <td align="center">
	              		<textarea rows="2" cols="20" name="certificateExt1" id="certificateExt1" >${element.certificateExt}</textarea> 
	              </td>
	              <td align="center">
						<textarea rows="2" cols="20" name="rem0" id="rem0" >${element.rem}</textarea> 
				  </td>  
              </tr>
            </logic:iterate>            
          </table> 
        </td>
      </tr>   
      
      <!-- ������ȡ���� -->
      <tr id="tb" style="display:none;">
        <td width="3" background="/images/line_3.gif" bgcolor="#FFFFFF" style="background-repeat:repeat-y;" >&nbsp;</td>
        <td bgcolor="#ffffff">
          <br>         
          <input name="BtnAdd" type="Button" value="�� ��" class="default_input" onclick="addonerows(toolReceive,3)"/> 
          <input name="BtnDel" type="button" value="ɾ ��" class="default_input" onclick="deleteRow(toolReceive,3)"/>
          &nbsp;&nbsp;<font color="red">�������˲�ѯ</font>&nbsp;
	      <input type="text" id="searchval" name="searchval" value="" class="default_input"/>
	      <input type="button"  value="����" onclick="toSearchOper()" class="default_input">
	      &nbsp;&nbsp;<input type="button"  value="��ʾȫ��" onclick="showAll('toolReceive')" class="default_input">  
	      
          <table id="toolReceive" width="100%" border="0" cellpadding="0" cellspacing="0" class="tb" style="margin-top:5">          
            <tr id="titleRow_4" class="td_3_1">
              <td><input type="checkbox" name="ckAll"  onclick="op(this,toolReceive)"/></td>
              <td>���߱��</td>
              <td>��������</td>
              <td>�����ͺ�</td>
              <td>����</td>
              <td>������</td>
              <td>��ȡ����</td>
              <td>�Ƿ��շ�</td>
              <td>�Ƿ�����</td>
              <td>��ע</td>
              <td>����</td>
            </tr>
            
            <tr id="tr_3">
              <td align="center">
                <input type="checkbox" name="cb1" id="cb1" onclick="opleft(this,toolReceive)"/>
              </td>
              <td align="center">
	              <input type="hidden" name="numno" id="numno" size="15" class="default_input"/>
	              <input type="text" name="toolId1" id="toolId1" size="15" class="default_input"/>
              </td>
              <td align="center">
              	<textarea rows="2" cols="50" name="toolName1" id="toolName1" ></textarea> 
              </td>
              <td align="center"><input type="text" name="toolParam1" id="toolParam1" class="default_input"/></td>
              <td align="center"><input type="text" name="toolnum1" id="toolnum1" size="6" class="default_input" onchange="checkthisvalue(this);" /></td>
              <td align="center"><input type="text" name="operName1" id="operName1" size="12" class="default_input"/></td>
              <td align="center"><input type="text" name="operDate1" id="operDate1" size="12" class="Wdate" onfocus="WdatePicker({isShowClear:true,readOnly:true})"/></td>                    
              <td align="center">
              	  <select name="isCharge1" id="isCharge1" class="default_input" >
	              	<option value="M">��˾����</option>
	              	<option value="X">�ֳ�����</option>
	              	<option value="Y">���շ�</option>
	              	<option value="N">δ�շ�</option>
	              </select>
              </td>
              <td align="center">
	              <select name="isLiquidation1" id="isLiquidation1" class="default_input" >
	              	<option value="N">��</option>
	              	<option value="Y">��</option>
	              </select>
              </td>
              <td align="center"><textarea rows="2" cols="30" name="rem1" id="rem1" ></textarea>       
              <td align="center"><input type="file" name="dataFile" id="dataFile" /></td>        
            </tr>
            <logic:iterate id="element4" name="ToolReceiveList" indexId="eind">
	            <tr id="tr_3">
	              <td align="center">
	                <input type="checkbox" name="cb1" id="cb1" onclick="opleft(this,toolReceive)"/>
	              </td>
	              <td align="center">
	              	<input type="text" name="toolId1" id="toolId1" size="15" class="default_input" value="${element4.toolId }"/>
	              	<input type="hidden" name="numno" id="numno" size="15" class="default_input" value="${element4.numNo }"/>
	              </td>
	              <td align="center">
	              		<textarea rows="2" cols="50" name="toolName1" id="toolName1" >${element4.toolName }</textarea> 
	              </td>
	              <td align="center"><input type="text" name="toolParam1" id="toolParam1" class="default_input" value="${element4.toolParam }"/></td>
	              <td align="center"><input type="text" name="toolnum1" id="toolnum1" size="6" class="default_input" onchange="checkthisvalue(this);"  value="${element4.toolnum }"/></td>
	              <td align="center"><input type="text" name="operName1" id="operName1" size="12" class="default_input" value="${element4.operName}"/></td>
	              <td align="center"><input type="text" name="operDate1" id="operDate1" size="12" class="Wdate" onfocus="WdatePicker({isShowClear:true,readOnly:true})" value="${element4.operDate }"/></td>                    
	              <td align="center">
	              	  <select name="isCharge1" id="isCharge1" class="default_input">
		              	<option value="M" <logic:equal name="element4" property="isCharge" value="M">selected</logic:equal> >��˾����</option>
		              	<option value="X" <logic:equal name="element4" property="isCharge" value="X">selected</logic:equal> >�ֳ�����</option>
		              	<option value="Y" <logic:equal name="element4" property="isCharge" value="Y">selected</logic:equal> >���շ�</option>
		              	<option value="N" <logic:equal name="element4" property="isCharge" value="N">selected</logic:equal> >δ�շ�</option>
		              </select>
	              </td>
	              <td align="center">
		              <select name="isLiquidation1" id="isLiquidation1" class="default_input" >
		              	<option value="N" <logic:equal name="element4" property="isLiquidation" value="N">selected</logic:equal> >��</option>
		              	<option value="Y" <logic:equal name="element4" property="isLiquidation" value="Y">selected</logic:equal> >��</option>
		              </select>
	              </td>
	              <td align="center"><textarea rows="2" cols="30" name="rem1" id="rem1" > ${element4.rem }</textarea>   
	              <td align="center">
	              	<a href="javascript:downloadFile(${element4.numNo });">${element4.r2 }</a>
	              	<input type="file" name="dataFile" id="dataFile" />
	              </td>  
	            </tr>
            </logic:iterate>
            
          </table> 
        </td>
      </tr>
             
  </table>
</html:form>
<script type="text/javascript">
// ������id="tr_?"���б���Ϊģ��
  var defaultrowobjs = new Array();
  var n = 0;
  while(document.getElementById("tr_"+n)!=null){
    n++;
  }
  for(var i=0;i<n;i++){
    defaultrowobjs[i]=new Array();  
    var row = document.getElementById("tr_"+i); 
    if(row != null){
      for(var j=0;j<row.cells.length;j++){      
        defaultrowobjs[i][j] = row.cells[j].innerHTML;  
      }
    	row.parentNode.deleteRow(row.rowIndex); 
    }
  }
  
  setfilename();//����file��name
</script>
<script>
	//����������������ȡ����
	function toSearchOper(){
		var searchval = document.getElementById("searchval").value;//Ҫ����������
		var tableid = document.getElementById("toolReceive");
		var rowlen = tableid.rows.length;
		
		for(var i = 1;i<rowlen;i++){
			var opername = document.getElementsByName("operName1")[i-1].value;
			if(opername.indexOf(searchval) == -1){
				tableid.rows[i].style.display="none";
			}else{
				tableid.rows[i].style.display="inline";
			}
		}
		
	}
	
	//��ʾȫ��
	function showAll(tableidname){
		var tableid = document.getElementById(tableidname);
		var rowlen = tableid.rows.length;
		
		for(var i = 1;i<rowlen;i++){
			tableid.rows[i].style.display="inline";
		}
	}

	//��������,֤�鵵��
	function toSearchOperNo1(){
		var searchval = document.getElementById("searchvalNo1").value;//Ҫ����������
		var tableid = document.getElementById("certificateExam");
		var rowlen = tableid.rows.length;
		
		for(var i = 1;i<rowlen;i++){
			var opername = document.getElementsByName("certificateNo1")[i-1].value;
			if(opername.indexOf(searchval) == -1){
				tableid.rows[i].style.display="none";
			}else{
				tableid.rows[i].style.display="inline";
			}
		}
		
	}

</script>
</body>





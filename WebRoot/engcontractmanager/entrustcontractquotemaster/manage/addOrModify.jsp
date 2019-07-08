<%@page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<script language="javascript" defer="defer" src="<html:rewrite forward='DatePickerJS'/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/jquery-1.9.1.min.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/openwindow.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/checkinput.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/dynamictable.js"/>"></script>


<html:hidden property="isreturn"/>
<html:hidden property="id"/>
<input type="hidden" name="id1" value="${id}"/>
<html:hidden name="quoteBean" property="billNo"/>
<html:hidden name="quoteBean" property="maintBillNo" />
<html:hidden property="submitType" />

<table id="companyA" width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
  <!-- <tr>
    <td nowrap="nowrap" height="23" colspan="6">&nbsp;<b>�ͻ���Ϣ</td>
  </tr> -->
  <tr>
    <td class="wordtd_a">ί��ά����λ����:</td>
    <td width="25%">
      <html:hidden name="quoteBean" property="companyId" styleId="companyId"/>
      <input type="text" name="companyName" id="companyName" value="${customer.companyName}" readonly="true" size="23"/>
      <input type="button" value=".." onclick="choiceCustomer()" class="default_input"/><font color="red">*</font>
    </td>
    <td class="wordtd_a">���ʼ���:</td>
    <td width="20%">
    	<input type="text" name="qualiLevelWb" id="qualiLevelWb" value="${customer.qualiLevelWb}" readonly="true" class="default_input_noborder"/>
    </td>
    <td class="wordtd_a">��������:</td>
    <td width="20%">
    	<html:text name="quoteBean" property="assLevel" styleClass="default_input"/>
    </td>  
  </tr>               
</table>

<br>

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
  <tr>
    <td  height="23" colspan="6">&nbsp;<b>��ͬ����Ϣ</td>
  </tr>
  <tr>
    <td class="wordtd_a">ά����ͬ��:</td>
    <td width="25%">
      <input type="hidden" name="billNo1" value="${contractBean.billNo}"/>
      <bean:write name="contractBean" property="maintContractNo"/>
      <html:hidden name="quoteBean" property="maintContractNo"/>
    </td>          
    <td class="wordtd_a">��ͬ����:</td>
    <td width="20%">
    	<logic:match name="contractBean" property="contractNatureOf" value="ZB">�Ա�</logic:match>
        <logic:match name="contractBean" property="contractNatureOf" value="PY">ƽ��</logic:match>
        <logic:match name="contractBean" property="contractNatureOf" value="WT">ί��</logic:match>
    </td>
     <td class="wordtd_a">������ʽ:</td>
    <td width="20%">
        <logic:match name="contractBean" property="mainMode" value="FREE">���</logic:match>
        <logic:match name="contractBean" property="mainMode" value="PAID">�շ�</logic:match>
    </td>         
  </tr>  
  <tr>
    <td class="wordtd_a">��ͬ��Ч�ڣ��£�:</td>
    <td>
      <html:text name="quoteBean" property="contractPeriod" readonly="true" styleClass="default_input_noborder"/>          
    </td>  
    <td class="wordtd_a">��ͬ��ʼ����:</td>
    <td>${contractSdate.contractSdate }
     <html:text name="quoteBean" property="contractSdate" styleId="contractSdate" styleClass="Wdate" size="12"   onfocus="WdatePicker({isShowClear:true,readOnly:true})"></html:text>
    </td>
    <td class="wordtd_a">��������:</td>
    <td>
    <html:text name="quoteBean" property="contractEdate" styleId="contractEdate" styleClass="Wdate" size="12"   onfocus="WdatePicker({isShowClear:true,readOnly:true})"></html:text>
    </td>        
  </tr>          
  <tr>
    <td class="wordtd_a">����ά���ֲ�:</td>
    <td>
      <bean:write name="contractBean" property="maintDivision"/>
      <html:hidden name="quoteBean" property="maintDivision"/>
    </td> 
     <td class="wordtd_a">����ά��վ:</td>
    <td >
      <bean:write name="contractBean" property="maintStation"/>
      <html:hidden name="quoteBean" property="maintStation"/>
    </td>
    <td class="wordtd_a">��ͬ����:</td>
    <td>
       <html:select name="quoteBean" property="contractNatureOf">
       <html:option value="">��ѡ��</html:option>
       <html:options collection="contractNatureOfList" property="id.pullid" labelProperty="pullname"/>
      </html:select><font color="red">*</font>  
    </td>  
  </tr>
  <tr>
    <td class="wordtd_a">¼����:</td>
    <td>
      <bean:write name="quoteBean" property="r1"/>
      <html:hidden name="quoteBean" property="operId"/>
    </td> 
     <td class="wordtd_a">¼������:</td>
    <td >
      <bean:write name="quoteBean" property="operDate"/>
      <html:hidden name="quoteBean" property="operDate"/>
    </td>
    <td class="wordtd_a">&nbsp;</td>
    <td>&nbsp;</td>  
  </tr> 
</table>
<br>

<div id="caption_0" style="width: 100%;padding-top: 2;padding-bottom: 2;border-bottom: 0" class="tb">
  &nbsp;<b>&nbsp;��ͬ��ϸ</b>
</div>
<div id="wrap_0" style="overflow-x:scroll">
  <table id="dynamictable_0"  width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
    <thead>
      <tr id="titleRow_0">
        <td nowrap="nowrap" class="wordtd_header">���ݱ��</td>
        <td nowrap="nowrap" class="wordtd_header">��������</td>
        <td nowrap="nowrap" class="wordtd_header">��</td>
        <td nowrap="nowrap" class="wordtd_header">վ</td>
        <td nowrap="nowrap" class="wordtd_header">��</td>
        <td nowrap="nowrap" class="wordtd_header">�����߶�</td>
        <td nowrap="nowrap" class="wordtd_header">����ͺ�</td>
        <td nowrap="nowrap" class="wordtd_header">�������</td>
        <td nowrap="nowrap" class="wordtd_header">���ۺ�ͬ��</td>
        <td nowrap="nowrap" class="wordtd_header">����λ����</td> 
        <td nowrap="nowrap" class="wordtd_header">ʹ�õ�λ����</td>
        <td nowrap="nowrap" class="wordtd_header">ά����ʼ����</td>
        <td nowrap="nowrap" class="wordtd_header">ά����������</td> 
      </tr>
    </thead>
    <tbody>
      <logic:present name="detailList">
        <logic:iterate id="e" name="detailList" >
          <tr>
          	<td nowrap="nowrap" align="center" style="display:none;"><input type="text" name="maintRowid" value="${e.rowid}" readonly="readonly" class="noborder_center"/></td>
            <td nowrap="nowrap" align="center">
            	<a onclick="simpleOpenWindow('elevatorSaleAction','${e.elevatorNo}');" class="link">
            		${e.elevatorNo}
            	</a>
            </td>
            <td nowrap="nowrap" align="center">
            	<logic:match name="e" property="elevatorType" value="T">ֱ��</logic:match>
            	<logic:match name="e" property="elevatorType" value="F">����</logic:match>
            </td>
            <td nowrap="nowrap" align="center">${e.floor}</td>
            <td nowrap="nowrap" align="center">${e.stage}</td>
            <td nowrap="nowrap" align="center">${e.door}</td>
            <td nowrap="nowrap" align="center">${e.high}</td>
            <td nowrap="nowrap" align="center">${e.elevatorParam}</td>
            <td nowrap="nowrap" align="center">${e.annualInspectionDate}</td>
            <td nowrap="nowrap" align="center">${e.salesContractNo}</td>
            <td nowrap="nowrap" align="center">${e.projectName}</td>
            <td nowrap="nowrap" align="center">${e.maintAddress}</td>
            <td nowrap="nowrap" align="center"><html:text name="e" property="mainSdate" styleId="mainSdate" styleClass="Wdate" size="12"   onfocus="WdatePicker({isShowClear:true,readOnly:true})"></html:text></td>
            <td nowrap="nowrap" align="center"><html:text name="e" property="mainEdate" styleId="mainEdate" styleClass="Wdate" size="12"   onfocus="WdatePicker({isShowClear:true,readOnly:true})"></html:text></td>
          </tr>
        </logic:iterate>
      </logic:present>
    </tbody>    
  </table>
</div>
<br>

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
  <tr>
    <td class="wordtd_a">��׼ί�м۸�(Ԫ):</td>
    <td width="25%">
    	<input name="standardPrice" value="${quoteBean.standardPrice}" onkeypress="f_check_number3()" onpropertychange="calculation();" size="10"/>
      <!-- <html:text name="quoteBean" property="standardPrice" styleId="standardPrice" size="10" onkeypress="f_check_number3();" onkeydown="calculation();"/> -->
      <font color="red">*</font>         
    </td>          
    <td class="wordtd_a">ʵ��ί�м۸�(Ԫ):</td>
    <td width="20%">
    	<input name="realPrice" value="${quoteBean.realPrice}" onkeypress="f_check_number3()" onpropertychange="calculation();" size="10"/>
      <!-- <html:text name="quoteBean" property="realPrice" onkeypress="f_check_number3();" onkeydown="calculation();" size="10" /> -->
      <font color="red">*</font>        
    </td> 
    <td class="wordtd_a">�Ӽ���(%):</td>
    <td width="20%">
    	<html:text name="quoteBean" property="markups" readonly="true" styleClass="default_input_noborder"/>
    </td>        
  </tr>
  <tr>
  <td class="wordtd_a">��ע:</td>
  <td colspan="5"><html:textarea property="rem" value="${quoteBean.rem}" rows="3" cols="80" styleClass="default_textarea"/></td>
  </tr>
</table>

<script type="text/javascript">
//initPage();
$("document").ready(function() {
	  setScrollTable("wrap_0","dynamictable_0",11);
	  setContractPeriod();
	  $("#contractSdate,#contractEdate").bind("propertychange",function(){setContractPeriod();}); // �����ͬ��Ч��  
})
/* function initPage(){
	
   
  $("#contractSdate,#contractEdate").bind("propertychange",function(){setContractPeriod();}); // �����ͬ��Ч��  
  
  setTopRowDateInputsPropertychange(dynamictable_0); //��̬����һ�����ڿؼ����propertychange�¼�
} */
</script>
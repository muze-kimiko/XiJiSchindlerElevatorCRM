<%@page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<script language="javascript" defer="defer" src="<html:rewrite forward='DatePickerJS'/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/jquery-1.9.1.min.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/openwindow.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/checkinput.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/dynamictable.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/math.js"/>"></script>

<html:hidden property="isreturn"/>
<html:hidden property="submitType"/>
<html:hidden property="id"/>
<html:hidden name="quoteBean" property="wgBillno" />
<html:hidden name="quoteBean" property="billNo"/>
<table id="companyA" width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
  <tr>
    <td class="wordtd">ί��ά�ĵ�λ����:</td>
    <td width="30%">
      <input type="text" name="companyName" id="companyName" value="${customer.companyName}" readonly="true" size="23"/>
      <input type="button" value=".." onclick="choiceCustomer()" class="default_input"/><font color="red">*</font>
      <html:hidden name="quoteBean" property="companyId" styleId="companyId"/>
    </td>
    <td class="wordtd">���ʼ���:</td>
    <td width="30%">
    	<input type="text" name="qualiLevelWg" id="qualiLevelWg" value="${customer.qualiLevelWg}" readonly="true" class="default_input_noborder"/>
    </td>
  </tr>
</table>
<br>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb" >
  <tr>
    <td height="23" colspan="6">&nbsp;<b>��ͬ����Ϣ</td>
  </tr>
  <tr>
    <td class="wordtd">ά�ĺ�ͬ��:</td>
    <td width="30%">
      <bean:write name="contractBean" property="maintContractNo"/>
      <html:hidden name="quoteBean" property="maintContractNo"/>
    </td> 
    <td nowrap="nowrap" class="wordtd">ҵ�����:</td>
    <td width="30%">
        <logic:match name="contractBean" property="busType" value="G">����</logic:match>
        <logic:match name="contractBean" property="busType" value="W">ά��</logic:match>
    </td>
    </tr>             
  <tr>  
    <td class="wordtd">����ά���ֲ�:</td>
    <td>
      <bean:write name="contractBean" property="maintDivision"/>
      <html:hidden name="quoteBean" property="maintDivision"/>
    </td> 
      <td class="wordtd">����ά��վ:</td>
      <td>
    	<bean:write name="contractBean" property="maintStation"/>
    	<html:hidden name="quoteBean" property="maintStation"/>
   	  </td>
  </tr>
  <tr>
  	<td class="wordtd">¼����:</td>
  	<td>
  		<bean:write name="quoteBean" property="r1"/>
  		<html:hidden name="quoteBean" property="operId"/>
  	</td>
  	<td class="wordtd">¼������:</td>
  	<td>
  		<bean:write name="quoteBean" property="operDate"/>
  		<html:hidden name="quoteBean" property="operDate"/>
  	</td>
  </tr>
</table>
<br>


<div id="caption_0" style="width: 100%;padding-top: 2;padding-bottom: 2;border-bottom: 0" class="tb"><b>&nbsp;ά����Ϣ</b></div>
  <div id="scrollBox" style="overflow:scroll; overflow-y:hidden">
  <table id="dynamictable_0" width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
    <thead>
      <tr id="titleRow_0">
        <td class="wordtd_header">ά���ݱ��</td>
        <td class="wordtd_header" >���ۺ�ͬ��<font color="red">*</font></td>
        <td class="wordtd_header">��Ŀ����<font color="red">*</font></td>
        <td class="wordtd_header" >ά������<font color="red">*</font> </td>
      </tr>     
    </thead>
    <tfoot>
      <tr height="10"><td colspan="4"></td></tr>
    </tfoot>
    <tbody>
      <logic:present name="detailList">
        <logic:iterate id="e" name="detailList" >
          <tr>
            <td nowrap="nowrap" align="center" style="display:none;"><input type="hidden" name="maintRowid" value="${e.rowid}"/></td>         
            <td align="center" >
	            <a onclick="simpleOpenWindow('elevatorSaleAction','${e.elevatorNo}');" class="link">
	            	${e.elevatorNo}
	            </a>
            </td>
            <td align="center" >${e.salesContractNo}</td>
            <td align="center" >${e.projectName}</td>
            <td>${e.contents}</td>
          </tr>
        </logic:iterate>
      </logic:present>      
    </tbody>    
  </table>
</div>
<br>

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb" style="table-layout:fixed;">
  <tr>
    <td class="wordtd" nowrap="nowrap">��׼ί�м۸�(Ԫ):</td>
    <td width="20%">
    	<input name="standardPrice" value="${quoteBean.standardPrice}" onkeypress="f_check_number3()" onpropertychange="calculation();" size="10"/>
    	<font color="red">*</font>
      <!-- <html:text name="quoteBean" property="standardPrice" onkeypress="f_check_number3()"  onchange="checkthisvalue(this);" /> -->
    </td>          
    <td class="wordtd" nowrap="nowrap">ʵ��ί�м۸�(Ԫ):</td>
    <td width="20%">
    	<input name="realPrice" value="${quoteBean.realPrice}" onkeypress="f_check_number3()" onpropertychange="calculation();" size="10"/>
    	<font color="red">*</font>
      <!-- <html:text name="quoteBean" property="realPrice" onkeypress="f_check_number3()"  onchange="checkthisvalue(this);" /> -->        
    </td>        
    <td class="wordtd" nowrap="nowrap">�Ӽ���(%):</td>
    <td width="20%">
    	<html:text name="quoteBean" property="markups" readonly="readonly" styleClass="default_input_noborder"/> 
    </td> 
  </tr>
   <tr>
  <td class="wordtd">��ע:</td>
  <td colspan="5"><html:textarea property="rem" value="${quoteBean.rem}" rows="3" cols="80" styleClass="default_textarea"/></td>
  </tr>
</table>

<script type="text/javascript">
$("document").ready(function(){
	setScrollTable("scrollBox","dynamictable_0",10);
})

/* function setbus(){
  var busType='${ServicingContractMasterList.busType}';
  $("#busType").html(busType=="W"?"ά��":"����");
}
setbus(); */
</script>
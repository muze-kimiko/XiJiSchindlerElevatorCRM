<%@page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<script language="javascript" defer="defer" src="<html:rewrite forward='DatePickerJS'/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/jquery-1.9.1.min.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/openwindow.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/checkinput.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/dynamictable.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/math.js"/>"></script>

<html:hidden property="isreturn"/>
<html:hidden property="id"/>
<html:hidden name="ServicingContractMasterList" property="billno" />
<html:hidden name="ServicingContractMasterList" property="quoteBillNo"/>

<table id="companyA" width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
  <tr>
    <td height="23" colspan="6">&nbsp;<b>�ͻ���Ϣ</td>
  </tr>
  <tr>
    <td class="wordtd">�׷���λ����:</td>
    <td width="20%">
       ${CustomerList.companyName}
      <html:hidden name="ServicingContractMasterList" property="companyId" styleId="companyId"/>
    </td>
    <td class="wordtd">�׷���λ��ַ:</td>
    <td width="20%">${CustomerList.address}</td>
    <td nowrap="nowrap" class="wordtd">�׷�����:</td>
    <td width="20%">${CustomerList.legalPerson}</td>  
  </tr>
  <tr>    
    <td class="wordtd">�׷�ί����:</td>
    <td>${CustomerList.client}</td>
    <td class="wordtd">�׷���ϵ��:</td>
    <td>${CustomerList.contacts}</td>   
    <td class="wordtd">�׷���ϵ�绰:</td>
    <td>${CustomerList.contactPhone}</td>          
  </tr>     
  <tr>
    <td nowrap="nowrap" class="wordtd">�׷�����:</td>
    <td>${CustomerList.fax}</td>   
    <td nowrap="nowrap" class="wordtd">�׷��ʱ�:</td>
    <td>${CustomerList.postCode}</td>          
    <td nowrap="nowrap" class="wordtd">��ַ���绰:</td>
    <td>${CustomerList.accountHolder}</td> 
  </tr>
  <tr>      
    <td class="wordtd">�׷������˺�:</td>
    <td>${CustomerList.account}</td>          
    <td class="wordtd">�׷���������:</td>
    <td>${CustomerList.bank}</td>   
    <td nowrap="nowrap" class="wordtd">��˰��ʶ���:</td>
    <td>${CustomerList.taxId}</td>          
  </tr>                 
</table>
<br>

<table id="companyB" width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
  <tr>
    <td class="wordtd">�ҷ���λ����:</td>
    <td width="20%">
    	${CustomerList2.companyName}
      <html:hidden name="ServicingContractMasterList" property="companyId2" styleId="companyId2" />
      <%-- <input type="text" name="companyName2" id="companyName2" value="${CustomerList2.companyName}" readonly="true" size="23"/>
      <input type="button" value=".." onclick="openWindowAndReturnValue3('searchCustomerAction','toSearchRecord','&cusNature=WT','2')" class="default_input"/><font color="red">*</font> --%>
    </td>
    <td class="wordtd">�ҷ���λ��ַ:</td>
    <td width="20%">
    <font id="address2">${CustomerList2.address}</font>
    </td>
    <td nowrap="nowrap" class="wordtd">�ҷ�����:</td>
    <td width="20%"><input type="text" name="legalPerson" id="legalPerson2" value="${CustomerList2.legalPerson}" readonly="true" class="default_input_noborder"/></td>
  </tr>
  <tr>       
    <td class="wordtd">�ҷ�ί����:</td>
    <td><input type="text" name="client" id="client2" value="${CustomerList2.client}" readonly="true" class="default_input_noborder"/></td>          
    <td class="wordtd">�ҷ���ϵ��:</td>
    <td><input type="text" name="contacts" id="contacts2" value="${CustomerList2.contacts}" readonly="true" class="default_input_noborder"/></td>   
    <td class="wordtd">�ҷ���ϵ�绰:</td>
    <td><input type="text" name="contactPhone" id="contactPhone2" value="${CustomerList2.contactPhone}" readonly="true" class="default_input_noborder"/></td>          
  </tr>     
  <tr>
    <td nowrap="nowrap" class="wordtd">�ҷ�����:</td>
    <td><input type="text" name="fax" id="fax2" value="${CustomerList2.fax}" readonly="true" class="default_input_noborder"/></td>   
    <td nowrap="nowrap" class="wordtd">�ҷ��ʱ�:</td>
    <td><input type="text" name="postCode" id="postCode2" value="${CustomerList2.postCode}" readonly="true" class="default_input_noborder"/></td>          
    <td nowrap="nowrap" class="wordtd">�ҷ�����:</td>
    <td><input type="text" name="accountHolder" id="accountHolder2" value="${CustomerList2.accountHolder}" readonly="true" class="default_input_noborder"/></td>   
  </tr>
  <tr>
    <td class="wordtd">�ҷ������˺�:</td>
    <td><input type="text" name="account" id="account2" value="${CustomerList2.account}" readonly="true" class="default_input_noborder"/></td> 
    <td nowrap="nowrap" class="wordtd">&nbsp;</td>
    <td>&nbsp;</td>   
    <td nowrap="nowrap" class="wordtd">&nbsp;</td>
    <td>&nbsp;</td>                   
  </tr>            
</table>
<br>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb" >
  <tr>
    <td height="23" colspan="6">&nbsp;<b>��ͬ����Ϣ</td>
  </tr>
  <tr>
    <td class="wordtd">ά�ĺ�ͬ��:</td>
    <td width="20%">
      <html:text name="ServicingContractMasterList" property="maintContractNo" readonly="true" styleClass="default_input_noborder"/>
    </td> 
     <td class="wordtd">ί�к�ͬ��:</td>
    <td width="20%">
      ${ServicingContractMasterList.outContractNo }
    </td>            
    <td nowrap="nowrap" class="wordtd">ҵ�����:</td>
    <td id="busType" width="20%">           
    </td>
    </tr>             
    <tr>
    <td nowrap="nowrap" class="wordtd">ǩ������:</td>
    <td width="20%">
      <html:text name="ServicingContractMasterList" property="signingDate" styleId="signingDate" size="15" styleClass="Wdate" onfocus="WdatePicker({isShowClear:true,readOnly:true})"/><font color="red">*</font> 
    </td>         
    <td class="wordtd">����ά���ֲ�:</td>
    <td>
      ${userInfoList.comName }
      <html:hidden name="ServicingContractMasterList" property="maintDivision" />
    </td> 
      <td class="wordtd">����ά��վ:</td>
        <td>
      ${maintStationName}
      <html:hidden name="ServicingContractMasterList" property="maintStation" />
      <!-- <html:select name="ServicingContractMasterList" property="maintStation">
       <html:options collection="maintStationList" property="storageid" labelProperty="storagename"/><font color="red">*</font>
      </html:select> -->
    </td>
</tr>
<tr>   
<td nowrap="nowrap" class="wordtd">������:</td>
    <td>
    ${userInfoList.userName }
      <html:hidden name="ServicingContractMasterList" property="attn" />
    </td>
    
       
  </tr>   
</table>
<br>


<div id="caption_0" style="width: 100%;padding-top: 2;padding-bottom: 2;border-bottom: 0" class="tb"><b>&nbsp;ά����Ϣ</b></div>
  <div id="scrollBox" style="overflow:scroll; overflow-y:hidden">
  <table id="dynamictable_0" width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
    <thead>
      <tr id="titleRow_0">
        <!-- <td class="wordtd_header"><input type="checkbox" onclick="checkTableAll(this)"/></td> -->
        <td class="wordtd_header">ά���ݱ��</td>
        <td class="wordtd_header" >���ۺ�ͬ��</td>
        <td class="wordtd_header">��Ŀ����</td>
        <td class="wordtd_header" >ά������</td>
      </tr>     
    </thead>
    <tfoot>
      <tr height="10"><td colspan="4"></td></tr>
    </tfoot>
    <tbody>
      <logic:present name="ServicingContractDetailList">
        <logic:iterate id="e" name="ServicingContractDetailList" >
          <tr>
            <!-- <td align="center" width="10%"><input type="checkbox" onclick="cancelCheckAll(this)"/></td>            -->
            <td align="center" >${e.elevatorNo}<input type="hidden" name="elevatorNo" id="elevatorNo" value="${e.elevatorNo}" /></td>
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
    <td class="wordtd" nowrap="nowrap">��ͬ�ܶ�(Ԫ):</td>
    <td width="30%">
      ${ServicingContractMasterList.contractTotal }
      <html:hidden name="ServicingContractMasterList" property="contractTotal"/>        
      </td>          
    <td class="wordtd" nowrap="nowrap">��������(Ԫ):</td>
    <td width="30%">
      <html:text name="ServicingContractMasterList" property="otherFee" onkeypress="f_check_number3()"  onchange="checkthisvalue(this);" />        
    </td>        
  </tr>
  <tr>
    <td class="wordtd" nowrap="nowrap">���ʽ:</td>
    <td colspan="3">
     
      <html:textarea name="ServicingContractMasterList"  property="paymentMethod" rows="3" cols="100" styleClass="default_textarea"/> 
    </td> 
  </tr>
  <tr>
    <td class="wordtd">���Ӻ�ͬ����:</td>
    <td colspan="3">      
      <html:textarea name="ServicingContractMasterList"  property="contractTerms" rows="3" cols="100" styleClass="default_textarea"/>       
    </td>                  
  </tr>  
</table>

<script type="text/javascript">
$("document").ready(function(){
	
	setScrollTable("scrollBox","dynamictable_0",10);
	
})

function setbus(){
  var busType='${ServicingContractMasterList.busType}';
  $("#busType").html(busType=="W"?"ά��":"����");
}
setbus();
</script>
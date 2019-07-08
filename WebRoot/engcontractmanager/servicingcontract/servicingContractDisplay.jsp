<%@page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<script language="javascript" defer="defer" src="<html:rewrite forward='DatePickerJS'/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/jquery-1.9.1.min.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/openwindow.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/checkinput.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/dynamictable.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/math.js"/>"></script>
<a id="ss"></a>
<html:hidden property="isreturn"/>
<html:hidden property="id"/>
<html:hidden property="taskSubFlag"/>
<html:hidden property="auditStatus"/>
<html:hidden property="submitType"/>
<html:hidden name="ServicingContractMasterList" property="wgBillno" />
<html:hidden name="ServicingContractMasterList" property="billNo" value="${ServicingContractQuoteMasterList.billNo }"/>
<html:hidden name="ServicingContractMasterList" property="busType" value="${ServicingContractQuoteMasterList.busType }"/>
<input type="hidden" name="details" id="details">
<input type="hidden" name="isSelectAdd" id="isSelectAdd" value="${isSelectAdd}"/>
<div height="23" width="100%" class="tb" style="border-bottom: 0">&nbsp;<b>�ͻ���Ϣ</b></div>
<table id="companyA" width="100%" border="0" cellpadding="0" cellspacing="0" class="tb" >
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

<table id="companyB" width="100%" border="0" cellpadding="0" cellspacing="0" class="tb" >
  <tr>
    <td class="wordtd">�ҷ���λ����:</td>
    <td width="20%">
      ${CustomerList2.companyName}
      <html:hidden name="ServicingContractMasterList" property="companyId2" styleId="companyId2" />
   </td>
    <td class="wordtd">�ҷ���λ��ַ:</td>
    <td width="20%">${CustomerList2.address}</td>
    <td class="wordtd" nowrap="nowrap">�ҷ�����:</td>
    <td width="20%">${CustomerList2.legalPerson}</td>
  </tr>
  <tr>       
    <td class="wordtd">�ҷ�ί����:</td>
    <td>${CustomerList2.client}</td>          
    <td class="wordtd">�ҷ���ϵ��:</td>
    <td>${CustomerList2.contacts}</td>   
    <td class="wordtd">�ҷ���ϵ�绰:</td>
    <td>${CustomerList2.contactPhone}</td>          
  </tr>     
  <tr>
    <td nowrap="nowrap" class="wordtd">�ҷ�����:</td>
    <td>${CustomerList2.fax}</td>   
    <td nowrap="nowrap" class="wordtd">�ҷ��ʱ�:</td>
    <td>${CustomerList2.postCode}</td>          
    <td nowrap="nowrap" class="wordtd">�ҷ�����:</td>
    <td>${CustomerList2.accountHolder}</td>   
  </tr>
  <tr>
    <td class="wordtd">�ҷ������˺�:</td>
    <td>${CustomerList2.account}</td> 
    <td nowrap="nowrap" class="wordtd">&nbsp;</td>
    <td>&nbsp;</td>   
    <td nowrap="nowrap" class="wordtd">&nbsp;</td>
    <td>&nbsp;</td>                   
  </tr>            
</table>
<br>
<div height="23"  class="tb" style="border-bottom: 0" width="100%" >&nbsp;<b>��ͬ����Ϣ</b></div>
<table  border="0"  width="100%" cellpadding="0" cellspacing="0" class="tb" >
  <tr>
    <td class="wordtd_a">ά�ĺ�ͬ��:</td>
    <td width="20%">
    ${ServicingContractMasterList.maintContractNo }
      <html:hidden name="ServicingContractMasterList" property="maintContractNo" />
    </td>          
    <td nowrap="nowrap" class="wordtd">ҵ�����:</td>
    <td width="20%" id="busType"></td>
    <td nowrap="nowrap" class="wordtd">ǩ������:</td>
    <td width="20%">
   ${ServicingContractMasterList.signingDate }
    </td>         
  </tr>               
  <tr>
    <td class="wordtd" >����ά���ֲ�:</td>
    <td>
      ${ServicingContractMasterList.maintDivision }
      <html:hidden name="ServicingContractMasterList" property="maintDivision" />
    </td> 
      <td nowrap="nowrap" class="wordtd">����ά��վ:</td>
      <td>   
         <bean:write name="maintStationName"/>    
      </td>
    <td nowrap="nowrap" class="wordtd" >������:</td>
    <td>
    ${ServicingContractMasterList.attn }
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
        <td class="wordtd_header">ά���ݱ��</td>
        <td class="wordtd_header" >���ۺ�ͬ��<font color="red">*</font></td>
        <td class="wordtd_header">��Ŀ����<font color="red">*</font></td>
        <td class="wordtd_header" >ά������<font color="red">*</font> </td>
      </tr>
    </thead>
    <tfoot>
      <tr height="23"><td colspan="3"></td></tr>
    </tfoot>
    <tbody>
      <logic:present name="ServicingContractDetailList">
        <logic:iterate id="e" name="ServicingContractDetailList" >
          <tr>         
            <td align="center" ><a  onclick="openwindowcb('${e.elevatorNo}');" style="cursor:hand;text-decoration: underline;color: blue;">${e.elevatorNo}</a></td>
            <td align="center" ><input type="text" name="salesContractNo" id="salesContractNo" value="${e.salesContractNo}" readonly="readonly" class="noborder_center"/></td>
            <td align="center" ><input type="text" name="projectName" id="projectName" value="${e.projectName}" readonly="readonly" class="noborder_center"/></td>
            <td align="center" >${e.contents}</td>
          </tr>
        </logic:iterate>
      </logic:present>    
    </tbody>    
  </table>
</div>
<br>

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb" >
  <tr>
   <td class="wordtd">��׼����(Ԫ):</td>
    <td width="20%">
    ${ServicingContractMasterList.basePrice}
      <html:hidden name="ServicingContractMasterList" property="basePrice" />        
    </td>  
    <td class="wordtd">��ͬ�ܶ�(Ԫ):</td>
    <td width="20%">
     ${ServicingContractMasterList.contractTotal}
      <html:hidden name="ServicingContractMasterList" property="contractTotal" />        
    </td>     
      
    <td class="wordtd">��������(Ԫ):</td>
    <td width="20%">
    ${ServicingContractMasterList.otherFee}
      <html:hidden name="ServicingContractMasterList" property="otherFee" />        
    </td>     
  </tr>
  <tr>
    <td class="wordtd">���ʽ:</td>
    <td colspan="5">
    ${ServicingContractMasterList.paymentMethod}
    </td> 
  </tr>
  <tr>
    <td class="wordtd">���Ӻ�ͬ����:</td>
    <td colspan="5">                
    ${ServicingContractMasterList.contractTerms}
    </td>                  
  </tr>  
</table>

 <logic:notEqual name="typejsp" value="display">
 <br>
  <div height="23"  class="tb" style="border-bottom: 0" width="100%">&nbsp;<b>�����Ϣ</b></div>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb" >
  <tr>
    <td class="wordtd" width="10%">�����:</td>
    <td width="20%">
      ${ServicingContractMasterList.auditOperid }
    </td> 
    <td class="wordtd" width="10%">���״̬:</td>
    <td width="20%">
    ${ServicingContractMasterList.auditStatus }
    </td>
    <td class="wordtd" width="10%">���ʱ��:</td>
    <td width="20%">${ServicingContractMasterList.auditDate }
    </td>       
  </tr>
  <logic:notEqual  name="ServicingContractMasterList" property="auditStatus" value="�����">
  <tr>
  <td class="wordtd">������:</td>
  <td colspan="5"><textarea name="auditRem" id="auditRem" cols="40" rows="2">${ServicingContractMasterList.auditRem }</textarea></td>
  </tr>
  </logic:notEqual>
  <logic:equal  name="ServicingContractMasterList" property="auditStatus" value="�����">
  <tr>
  <td class="wordtd" >������:</td>
  <td colspan="5" >${ServicingContractMasterList.auditRem }</td>
  </tr>
  </logic:equal>
  </table>

 <br>
 <div height="25"  class="tb"  style="border-bottom: 0">&nbsp;<b>�´���Ϣ</b></div>
 <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb" >
  <tr>
    <td class="wordtd" width="10%">�����´���:</td>
    <td width="20%">
      ${ServicingContractMasterList.taskUserId }
    </td> 
    <td class="wordtd" width="10%">�����´��־:</td>
    <td width="20%">
    ${ServicingContractMasterList.taskSubFlag }
    </td >
    <td class="wordtd" width="10%">�����´�ʱ��:</td>
    <td width="20%">${ServicingContractMasterList.taskSubDate }
    </td>       
  </tr>
  <logic:equal name="typejsp" value="totask">
  <logic:notEqual  name="ServicingContractMasterList" property="taskSubFlag" value="���´�">
  <tr>
  <td class="wordtd">�´ﱸע:</td>
  <td colspan="5"><textarea name="taskRem" id="taskRem" cols="40" rows="2">${ServicingContractMasterList.taskRem }</textarea></td>
  </tr>
  </logic:notEqual>
  <logic:equal  name="ServicingContractMasterList" property="taskSubFlag" value="���´�">
  <tr>
  <td class="wordtd">�´ﱸע:</td>
  <td colspan="5">${ServicingContractMasterList.taskRem }</td>
  </tr>
  </logic:equal>
  </logic:equal>
  <logic:notEqual name="typejsp" value="totask">
  <tr>
  <td class="wordtd">�´ﱸע:</td>
  <td colspan="5">${ServicingContractMasterList.taskRem }</td>
  </tr>
  </logic:notEqual>
  </table>
  </logic:notEqual>
 <logic:equal name="typejsp" value="display">
 <br>
 <div height="25"  class="tb"  style="border-bottom: 0">&nbsp;<b>�����Ϣ</b></div>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb" >
  <tr>
    <td nowrap="nowrap" class="wordtd">�����:</td>
    <td width="20%">
      ${ServicingContractMasterList.auditOperid }
    </td> 
    <td nowrap="nowrap" class="wordtd">���״̬:</td>
    <td width="20%">
    ${ServicingContractMasterList.auditStatus}
    </td >
    <td nowrap="nowrap" class="wordtd">���ʱ��:</td>
    <td width="20%">${ServicingContractMasterList.auditDate }
    </td>       
  </tr>
  <tr>
  <td nowrap="nowrap" class="wordtd">������:</td>
  <td colspan="5">${ServicingContractMasterList.auditRem }</td>
  </tr>
  </table>
  
 
<br>
<div height="23"  class="tb" style="border-bottom: 0">&nbsp;<b>�´���Ϣ</b></div>
 <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb" >
  <tr>
    <td nowrap="nowrap" class="wordtd">�����´���:</td>
    <td width="20%">
      ${ServicingContractMasterList.taskUserId }
    </td> 
    <td nowrap="nowrap" class="wordtd">�����´��־:</td>
    <td width="20%">
    ${ServicingContractMasterList.taskSubFlag}
    </td>
    <td nowrap="nowrap" class="wordtd">�����´�ʱ��:</td>
    <td width="20%">${ServicingContractMasterList.taskSubDate }
    </td>       
  </tr>
  <tr>
  <td nowrap="nowrap" class="wordtd">�´ﱸע:</td>
  <td colspan="5">${ServicingContractMasterList.taskRem }</td>
  </tr>
  </table>
</logic:equal>
<script type="text/javascript">
$("document").ready(function(){
	setScrollTable("scrollBox","dynamictable_0",10);
})

function setbus(){
  var busType='${ServicingContractMasterList.busType}';
  $("#busType").html(busType=="W"?"ά��":"����");
}
setbus();
function openwindowcb(obj){
window.open('<html:rewrite page="/elevatorSaleAction.do"/>?method=toDisplayRecord&isOpen=Y&id='+obj,'','height=500px, width=1000px,scrollbars=yes, resizable=yes,directories=no');
}

</script>
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

<html:hidden property="id" value="${quoteBean.billNo}"/>
<html:hidden name="quoteBean" property="billNo"/>
<html:hidden property="submitType"/>
<table id="companyA" width="100%" border="0" cellpadding="0" cellspacing="0" class="tb" >
  <tr>
    <td class="wordtd">ί��ά�ĵ�λ����:</td>
    <td width="30%">
    	<logic:present name="customer">
      		<bean:write name="customer" property="companyName"/>
      	</logic:present>
      <html:hidden name="quoteBean" property="companyId" styleId="companyId"/>
    </td>
    <td class="wordtd">���ʼ���:</td>
    <td width="30%">
    <logic:present name="customer">
    	<bean:write name="customer" property="qualiLevelWg"/>
    </logic:present>
    </td>
  </tr>
</table>
<br>
<div height="23"  class="tb" style="border-bottom: 0" width="100%" >&nbsp;<b>��ͬ����Ϣ</b></div>
<table  border="0"  width="100%" cellpadding="0" cellspacing="0" class="tb" >
  <tr>
    <td class="wordtd_a">ά�ĺ�ͬ��:</td>
    <td width="30%">
    	<bean:write name="contractBean" property="maintContractNo"/>
    </td>          
    <td nowrap="nowrap" class="wordtd">ҵ�����:</td>
    <td width="30%">
    	<logic:match name="contractBean" property="busType" value="G">����</logic:match>
        <logic:match name="contractBean" property="busType" value="W">ά��</logic:match>
    </td>
    </tr>               
  <tr>         
    <td class="wordtd" >����ά���ֲ�:</td>
    <td>
      <bean:write name="contractBean" property="maintDivision"/>
    </td> 
      <td nowrap="nowrap" class="wordtd">����ά��վ:</td>
      <td>   
         <bean:write name="contractBean" property="maintStation"/>    
      </td>
  </tr> 
  <tr>
    <td nowrap="nowrap" class="wordtd" >¼����:</td>
    <td>
    	<bean:write name="quoteBean" property="r1"/>
    </td>
    <td nowrap="nowrap" class="wordtd" >¼������:</td>
    <td>
    	<bean:write name="quoteBean" property="operDate"/>
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
        <td class="wordtd_header" >���ۺ�ͬ��</td>
        <td class="wordtd_header">��Ŀ����</td>
        <td class="wordtd_header" >ά������</td>
      </tr>
    </thead>
    <tfoot>
      <tr height="10"><td colspan="4"></td></tr>
    </tfoot>
    <tbody>
      <logic:present name="detailList">
        <logic:iterate id="e" name="detailList" >
          <tr>         
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

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb" >
  <tr>
    <td class="wordtd">��׼ί�м۸�(Ԫ):</td>
    <td width="20%">
    	<bean:write name="quoteBean" property="standardPrice"/>
    </td>         
    <td class="wordtd">ʵ��ί�м۸�(Ԫ):</td>
    	<td width="20%">
    <bean:write name="quoteBean" property="realPrice"/>
    </td>     
    <td class="wordtd">�Ӽ���(%):</td>
    <td width="20%">
    	<bean:write name="quoteBean" property="markups"/>
    </td> 
  </tr>
  <tr>
  <td  class="wordtd_a">��ע:</td>
  <td colspan="5"><bean:write name="quoteBean" property="rem"/></td>
  </tr>
</table>
<br>
 
<script type="text/javascript">
$("document").ready(function(){
	setScrollTable("scrollBox","dynamictable_0",10);
})

/* function setbus(){
  var busType='${ServicingContractMasterList.busType}';
  $("#busType").html(busType=="W"?"ά��":"����");
}
setbus();
function openwindowcb(obj){
window.open('<html:rewrite page="/elevatorSaleAction.do"/>?method=toDisplayRecord&isOpen=Y&id='+obj,'','height=500px, width=1000px,scrollbars=yes, resizable=yes,directories=no');
} */

</script>
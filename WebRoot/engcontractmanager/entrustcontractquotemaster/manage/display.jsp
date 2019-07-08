<%@ page contentType="text/html;charset=GBK" %>
<script language="javascript" src="<html:rewrite page="/common/javascript/jquery-1.9.1.min.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/dynamictable.js"/>"></script>
<style>
  .show{display:block;}
  .hide{display:none;}

</style>
<logic:present name="quoteBean">
<html:hidden name="quoteBean" property="billNo"/>
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
    <tr>
      <td  class="wordtd_a">ί��ά����λ����:</td>
      <td width="20%">
      <logic:present name="customer">
      <bean:write name="customer" property="companyName"/>
      </logic:present>
      </td>
      <td  class="wordtd_a">���ʼ���:</td>
      <td width="20%">
      <logic:present name="customer">
      <bean:write name="customer" property="qualiLevelWb"/>
      </logic:present>
      </td>
      <td  class="wordtd_a">��������:</td>
      <td width="20%"><bean:write name="quoteBean" property="assLevel"/></td>   
    </tr>
  </table>
  <br>
  
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
    <tr>
      <td  height="23" colspan="6">&nbsp;<b>��ͬ����Ϣ</td>
    </tr>
    <tr>
    <td  class="wordtd_a">ά����ͬ��:</td>
    <td width="20%">
      <bean:write name="contractBean" property="maintContractNo"/>
    </td>          
    <td  class="wordtd_a">��ͬ����:</td>
    <td width="20%">
    	<logic:match name="contractBean" property="contractNatureOf" value="ZB">�Ա�</logic:match>
        <logic:match name="contractBean" property="contractNatureOf" value="PY">ƽ��</logic:match>
        <logic:match name="contractBean" property="contractNatureOf" value="WT">ί��</logic:match>
    </td>
     <td  class="wordtd_a">������ʽ:</td>
    <td width="20%">
        <logic:match name="contractBean" property="mainMode" value="FREE">���</logic:match>
        <logic:match name="contractBean" property="mainMode" value="PAID">�շ�</logic:match>
    </td>         
  </tr>  
  <tr>
  <td class="wordtd_a">��ͬ��Ч�ڣ��£�:</td>
    <td>
      <bean:write name="quoteBean" property="contractPeriod"/>          
    </td>
    <td  class="wordtd_a">��ͬ��ʼ����:</td>
    <td>
      <bean:write name="quoteBean" property="contractSdate"/>
    </td>
    <td  class="wordtd_a">��������:</td>
    <td>
      <bean:write name="quoteBean" property="contractEdate"/>
    </td>         
         
  </tr>          
  <tr>
    <td  class="wordtd_a">����ά���ֲ�:</td>
    <td>
      <bean:write name="contractBean" property="maintDivision"/>
    </td> 
     <td  class="wordtd_a">����ά��վ:</td>
    <td >
      <bean:write name="contractBean" property="maintStation"/>
    </td>
    <td  class="wordtd_a">��ͬ����:</td>
    <td>
    <bean:write name="quoteBean" property="r2"/>
    </td>  
  </tr>
  <tr>
    <td  class="wordtd_a">¼����:</td>
    <td>
      <bean:write name="quoteBean" property="r1"/>
    </td> 
     <td  class="wordtd_a">¼������:</td>
    <td >
      <bean:write name="quoteBean" property="operDate"/>
    </td>
    <td  class="wordtd_a"></td>
    <td></td>  
  </tr>
  </table>
  <br>
  
  <div style="width: 100%;padding-top: 4;padding-bottom: 4;border-bottom: 0" class="tb">        
   <b>&nbsp;��ͬ��ϸ</b>
  </div>
  <div id="wrap_0" style="overflow-x:scroll">
    <table id="dynamictable_0" width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
      <thead id="header_0">
        <tr id="titleRow_0">
          <td  class="wordtd_header">���ݱ��</td>
          <td  class="wordtd_header">��������</td>
          <td  class="wordtd_header">��</td>
          <td  class="wordtd_header">վ</td>
          <td  class="wordtd_header">��</td>
          <td  class="wordtd_header">�����߶�</td>
          <td  class="wordtd_header">����ͺ�</td>
          <td  class="wordtd_header">�������</td>
          <td  class="wordtd_header">���ۺ�ͬ��</td>
          <td  class="wordtd_header">����λ����</td>
          <td  class="wordtd_header">ʹ�õ�λ����</td>
          <td  class="wordtd_header">ά����ʼ����</td>
          <td  class="wordtd_header">ά����������</td>
        </tr>
      </thead>
      <tbody>
        <logic:present name="detailList">
          <logic:iterate id="e" name="detailList" >
            <tr>
              <td  align="center">
              	<a onclick="simpleOpenWindow('elevatorSaleAction','${e.elevatorNo}');" class="link">
              		${e.elevatorNo}
              	</a>
              </td>
              <td  align="center">
            	<logic:match name="e" property="elevatorType" value="T">ֱ��</logic:match>
            	<logic:match name="e" property="elevatorType" value="F">����</logic:match>
              </td>
              <td  align="center">${e.floor}</td>
              <td  align="center">${e.stage}</td>
              <td  align="center">${e.door}</td>
              <td  align="center">${e.high}</td>
              <td  align="center">${e.elevatorParam}</td>
              <td  align="center">${e.annualInspectionDate}</td>
              <td  align="center">${e.salesContractNo}</td>
              <td  align="center">${e.projectName}</td>
              <td  align="center">${e.maintAddress}</td>
              <td  align="center">${e.mainSdate }</td>
              <td  align="center">${e.mainEdate }</td>
            </tr>
          </logic:iterate>
        </logic:present>
      </tbody>    
    </table>
  </div>
  <br>
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
  <tr>
    <td  class="wordtd_a">��׼ί�м۸�(Ԫ):</td>
    <td width="20%">
      <bean:write name="quoteBean" property="standardPrice"/>         
    </td>          
    <td  class="wordtd_a">ʵ��ί�м۸�(Ԫ):</td>
    <td width="20%">
      <bean:write name="quoteBean" property="realPrice"/>        
    </td> 
    <td  class="wordtd_a">�Ӽ���(%):</td>
    <td width="20%">
    	<bean:write name="quoteBean" property="markups"/>
    </td>        
  </tr>
  <tr>
  <td  class="wordtd_a">��ע:</td>
  <td colspan="5"><bean:write name="quoteBean" property="rem"/></td>
  </tr>
  </table>
  
  <script type="text/javascript">
//������������Ӧ���
  $("document").ready(function() {
	  
    $("input[name='rownum']").each(function(i,obj){
      obj.value = i+1;
    })

    setScrollTable("wrap_0","dynamictable_0",11);

})
  
  </script> 
</logic:present>
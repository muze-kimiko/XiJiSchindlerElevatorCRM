<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>

<script language="javascript" defer="defer" src="<html:rewrite forward='DatePickerJS'/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/jquery-1.9.1.min.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/openwindow.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/checkinput.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/dynamictable.js"/>"></script>

<style>
  .show{display:block;}
  .hide{display:none;}
</style>

<logic:present name="maintContractBean">
  <html:hidden name="maintContractBean" property="billNo"/>
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
    <tr>
      <td height="23" colspan="6">&nbsp;<b>�ͻ���Ϣ</td>
    </tr>
    <tr>
      <td class="wordtd">�׷���λ����:</td>
      <td width="20%"><bean:write name="companyA" property="companyName"/></td>
      <td class="wordtd">�׷���λ��ַ:</td>
      <td width="20%"><bean:write name="companyA" property="address"/></td>
      <td class="wordtd">�׷�����:</td>
      <td width="20%"><bean:write name="companyA" property="legalPerson"/></td>   
    </tr>
    <tr>
      <td class="wordtd">�׷�ί����:</td>
      <td><bean:write name="companyA" property="client"/></td>          
      <td class="wordtd">�׷���ϵ��:</td>
      <td><bean:write name="companyA" property="contacts"/></td>   
      <td class="wordtd">�׷���ϵ�绰:</td>
      <td><bean:write name="companyA" property="contactPhone"/></td>          
    </tr>     
    <tr>
      <td class="wordtd">�׷�����:</td>
      <td><bean:write name="companyA" property="fax"/></td>   
      <td class="wordtd">�׷��ʱ�:</td>
      <td><bean:write name="companyA" property="postCode"/></td>          
      <td class="wordtd">��ַ���绰:</td>
      <td><bean:write name="companyA" property="accountHolder"/></td>   
    </tr>
    <tr>
      <td class="wordtd">�׷������˺�:</td>
      <td><bean:write name="companyA" property="account"/></td>          
      <td class="wordtd">�׷���������:</td>
      <td><bean:write name="companyA" property="bank"/></td>   
      <td class="wordtd">��˰��ʶ���:</td>
      <td><bean:write name="companyA" property="taxId"/></td>          
    </tr>                 
  </table>
  <br>
  
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
    <tr>
      <td class="wordtd">�ҷ���λ����:</td>
      <td width="20%"><bean:write name="companyB" property="companyName"/></td>
      <td class="wordtd">�ҷ���λ��ַ:</td>
      <td width="20%"><bean:write name="companyB" property="address"/></td>
      <td nowrap="nowrap" class="wordtd">�ҷ�����:</td>
      <td width="20%"><bean:write name="companyB" property="legalPerson"/></td>   
    </tr>
    <tr>
      <td class="wordtd">�ҷ�ί����:</td>
      <td><bean:write name="companyB" property="client"/></td>          
      <td class="wordtd">�ҷ���ϵ��:</td>
      <td><bean:write name="companyB" property="contacts"/></td>   
      <td class="wordtd">�ҷ���ϵ�绰:</td>
      <td><bean:write name="companyB" property="contactPhone"/></td>          
    </tr>     
    <tr>
      <td nowrap="nowrap" class="wordtd">�ҷ�����:</td>
      <td><bean:write name="companyB" property="fax"/></td>   
      <td nowrap="nowrap" class="wordtd">�ҷ��ʱ�:</td>
      <td><bean:write name="companyB" property="postCode"/></td>          
      <td nowrap="nowrap" class="wordtd">�ҷ�����:</td>
      <td><bean:write name="companyB" property="accountHolder"/></td>   
    </tr>
    <tr>
      <td class="wordtd">�ҷ������˺�:</td>
      <td><bean:write name="companyB" property="account"/></td>
      <td class="wordtd">&nbsp;</td>
      <td></td>   
      <td class="wordtd">&nbsp;</td>
      <td></td>            
    </tr>            
  </table>
  <br>
  
  <table id="contractInfoTable" width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
    <tr>
      <td height="23" colspan="6">&nbsp;<b>��ͬ����Ϣ</td>
    </tr>
    <tr>
      <td class="wordtd"><bean:message key="maintContract.maintContractNo"/>:</td>
      <td width="20%">
        <bean:write name="maintContractBean" property="maintContractNo"/>          
      </td>          
      <td nowrap="nowrap" class="wordtd"><bean:message key="maintContract.contractNatureOf"/>:</td>
      <td width="20%">�Ա�</td>
      <td nowrap="nowrap" class="wordtd"><bean:message key="maintContract.mainMode"/>:</td>
      <td width="20%">
        <logic:match name="maintContractBean" property="mainMode" value="FREE">���</logic:match>
        <logic:match name="maintContractBean" property="mainMode" value="PAID">�շ�</logic:match>
      </td>         
    </tr>  
    <tr>
      <td class="wordtd"><bean:message key="maintContract.contractPeriod"/>:</td>
      <td>
        <bean:write name="maintContractBean" property="contractPeriod"/>    
      </td>          
      <td class="wordtd"><bean:message key="maintContract.contractSdate"/>:</td>
      <td>
        <bean:write name="maintContractBean" property="contractSdate"/>
      </td>
      <td class="wordtd"><bean:message key="maintContract.contractEdate"/>:</td>
      <td>
        <bean:write name="maintContractBean" property="contractEdate"/>
      </td>         
    </tr>        
    <tr>
      <td class="wordtd"><bean:message key="maintContract.maintDivision"/>:</td>
      <td>
        <bean:write name="maintContractBean" property="maintDivision"/>
      </td> 
      <td class="wordtd">����ά��վ:</td>
      <td>
        <html:hidden name="maintContractBean" property="maintStation"/>
        <bean:write name="maintStationName"/>
      </td>  
      <td class="wordtd"><bean:message key="maintContract.attn"/>:</td>
      <td>
        <bean:write name="maintContractBean" property="attn"/>
      </td>       
    </tr> 
    <tr>
      <td nowrap="nowrap" class="wordtd">¼����:</td>
      <td>
        <bean:write name="maintContractBean" property="operId"/>
      </td>
      <td nowrap="nowrap" class="wordtd">¼������:</td>
      <td>
        <bean:write name="maintContractBean" property="operDate"/>
      </td>  
      <td class="wordtd">&nbsp;</td>
      <td>&nbsp;</td>       
    </tr>   
  </table>
  <br>
  
  <div style="border:solid #999999 1px;border-bottom:none;padding-top: 4;padding-bottom: 4;background:#ffffff;">        
   <b>&nbsp;��ͬ��ϸ</b>
  </div>
  <div id="scrollBox" style="overflow:scroll;">
    <table id="scrollTable" width="1800px" border="0" cellpadding="0" cellspacing="0" class="tb">
      <thead>
        <tr id="titleRow">
          <td class="wordtd_header" nowrap="nowrap">���</td>          
          <td class="wordtd_header" nowrap="nowrap">�´�ά��վ</td>
          <td class="wordtd_header" nowrap="nowrap">ǩ��ʽ</td>
          <td class="wordtd_header" nowrap="nowrap">���ݱ��</td>
          <td class="wordtd_header" nowrap="nowrap">��������</td>
          <td class="wordtd_header" nowrap="nowrap">��</td>
          <td class="wordtd_header" nowrap="nowrap">վ</td>
          <td class="wordtd_header" nowrap="nowrap">��</td>
          <td class="wordtd_header" nowrap="nowrap">�����߶�</td>
          <td class="wordtd_header" nowrap="nowrap">����ͺ�</td>
          <td class="wordtd_header" nowrap="nowrap">�������</td>
          <td class="wordtd_header" nowrap="nowrap">���ۺ�ͬ��</td>
          <td class="wordtd_header" nowrap="nowrap">����λ����</td>
          <td class="wordtd_header" nowrap="nowrap">ʹ�õ�λ����</td>
          <td class="wordtd_header" nowrap="nowrap">ά����ʼ����</td>
          <td class="wordtd_header" nowrap="nowrap">ά����������</td>
          <td class="wordtd_header" nowrap="nowrap">�ӱ���������<span class="hide" style="color:red;">*</span></td> 
        </tr>
      </thead>
      <tfoot>
        <tr height="15px"><td colspan="17"></td></tr>
      </tfoot>
      <tbody>
        <logic:present name="maintContractDetailList">
          <logic:iterate id="e" name="maintContractDetailList" indexId="i">
            <tr>
              <td align="center">${i+1}
                <html:hidden name="e" property="rowid" />
                <html:hidden name="e" property="elevatorNo" />                
                <html:hidden name="e" property="assignedMainStation" />
                <html:hidden name="e" property="mainEdate" />
              </td>
              <td align="center" width="1%">           
                  <logic:iterate id="ms" name="maintStationList">
                    ${e.assignedMainStation == ms.storageid ? ms.storagename : ''}                
                  </logic:iterate>                                
              </td>
              <td align="center">${e.signWay}</td> 
              <td align="center">
                <a onclick="simpleOpenWindow('elevatorSaleAction','${e.elevatorNo}');" class="link">${e.elevatorNo}</a>                                             
              </td>
              <td align="center">${e.elevatorType}</td>
              <td align="center">${e.floor}</td>
              <td align="center">${e.stage}</td>
              <td align="center">${e.door}</td>
              <td align="center">${e.high}</td>
              <td align="center">${e.elevatorParam}</td>
              <td align="center">${e.annualInspectionDate}</td>
              <td align="center">${e.salesContractNo}</td>
              <td align="center">${e.projectName}</td>
              <td align="center">${e.maintAddress}</td>
              <td align="center">${e.mainSdate}</td>
              <td align="center">${e.mainEdate}</td>
              <td align="center">
                <span class="show">${e.realityEdate}</span>
                <span class="hide">
                  <input type="text" name="realityEdate" value="${e.realityEdate}" class="Wdate" onfocus="pickEndDay('${maintContractBean.contractEdate}')" size="12"/>
                </span>
              </td>
            </tr>
          </logic:iterate>
        </logic:present>
      </tbody>   
    </table>
  </div>
  <br>
  
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
    <tr>
      <td class="wordtd" nowrap="nowrap">��ͬ�ܶ�:</td>
      <td width="30%">
        <bean:write name="maintContractBean" property="contractTotal"/>
      </td>          
      <td class="wordtd" nowrap="nowrap">��������:</td>
      <td width="30%">
        <bean:write name="maintContractBean" property="otherFee"/>
      </td>        
    </tr>
    <tr>
      <td class="wordtd" nowrap="nowrap">���ʽ:</td>
      <td colspan="3">
        <bean:write name="maintContractBean" property="r4"/>
      </td> 
    </tr>
   	<tr>
    <td class="wordtd">
    	���ʽ���뱸ע:
    </td>
    <td colspan="3">
    	<bean:write name="maintContractBean" property="paymentMethodRem"/>
    </td>
    </tr>
    <tr>
      <td class="wordtd" nowrap="nowrap">��ͬ�������������:</td>
      <td colspan="3">     
        <bean:write name="maintContractBean" property="r5"/> 
      </td>                  
    </tr>  
    <tr>
    <td class="wordtd">
    	��ͬ�������������ע:
    </td>
    <td colspan="3">
    	<bean:write  name="maintContractBean" property="contractContentRem"/>
    </td>
    </tr>
    <tr>
      <td class="wordtd" nowrap="nowrap">�ӱ�¼����:</td>
      <td width="30%">
        <bean:write name="username"/>
      </td>          
      <td class="wordtd" nowrap="nowrap">�ӱ�¼������:</td>
      <td width="30%">
        <bean:write name="daytime"/>
      </td>        
    </tr>
	  <tr>
	    <td class="wordtd">��ע:</td>
	    <td colspan="5">
	    	 <span class="show"><bean:write name="delayrem"/></span>
             <span class="hide">
                 <html:textarea property="rem" styleId="rem" rows="3" cols="100" styleClass="default_textarea"/><font color="red">*</font>
            </span>   
	    </td>                  
	  </tr> 
  </table>
 

  <script type="text/javascript"> 
	window.onload = function() {			
		setScrollTable('scrollBox','scrollTable',10); // ���ù������
	}
  </script> 
</logic:present>
<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>

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
      <td nowrap="nowrap" class="wordtd">�׷�����:</td>
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
      <td class="wordtd">�ҷ�����:</td>
      <td><bean:write name="companyB" property="fax"/></td>   
      <td class="wordtd">�ҷ��ʱ�:</td>
      <td><bean:write name="companyB" property="postCode"/></td>          
      <td class="wordtd">�ҷ�����:</td>
      <td><bean:write name="companyB" property="accountHolder"/></td>   
    </tr>
    <tr>
      <td class="wordtd">�ҷ������˺�:</td>
      <td><bean:write name="companyB" property="account"/></td>
      <td class="wordtd"></td>
      <td></td>   
      <td class="wordtd"></td>
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
      <td class="wordtd"><bean:message key="maintContract.contractPeriod"/>���£�:</td>
      <td>
        <span class="renewal show"><bean:write name="maintContractBean" property="contractPeriod"/></span>  
        <span class="renewal hide"><input type="text" name="contractPeriod" class="default_input_noborder"></span>         
      </td>          
      <td class="wordtd"><bean:message key="maintContract.contractSdate"/>:</td>
      <td>
        <span class="renewal show"><bean:write name="maintContractBean" property="contractSdate"/></span>
        <span class="renewal hide"><input type="text" name="contractSdate" class="Wdate" onpropertychange="setContractPeriod();" onfocus="pickStartDay('contractEdate')" size="12"/><font color="red">*</font></span>       
      </td>
      <td class="wordtd"><bean:message key="maintContract.contractEdate"/>:</td>
      <td>
        <span class="renewal show"><bean:write name="maintContractBean" property="contractEdate"/></span>
        <span class="renewal hide"><input type="text" name="contractEdate" class="Wdate" onpropertychange="setContractPeriod();" onfocus="pickEndDay('contractSdate')" size="12"/><font color="red">*</font></span>
      </td>         
    </tr>        
    <tr>
      <td class="wordtd"><bean:message key="maintContract.maintDivision"/>:</td>
      <td>
      	<bean:write name="maintDivisionName" />
      </td>
      <td class="wordtd">����ά��վ:</td>
      <td>
        <bean:write name="maintStationName" />
      </td>   
      <%-- td class="wordtd"><logic:present name="display">��һ�ݺ�ͬ��:</logic:present></td>
      <td>
      	<logic:present name="display">
      		<bean:write name="maintContractBean" property="histContractNo"/>
      	</logic:present>
      </td--%>      
    <td class="wordtd"><bean:message key="maintContract.attn"/>:</td>
    <td>
    	<bean:write name="maintContractBean" property="attn"/>
    </td>       
    </tr>
    <tr>
    <td class="wordtd">������ˮ��:</td>
    <td>${maintContractBean.quoteBillNo}</td> 
    <td class="wordtd">����ǩ��ʽ:</td>
    <td>
    	<logic:equal name="maintContractBean" property="quoteSignWay" value="ZB">��ǩ</logic:equal>
    	<logic:equal name="maintContractBean" property="quoteSignWay" value="XB">��ǩ</logic:equal>
    	<logic:equal name="maintContractBean" property="quoteSignWay" value="BFXB">������ǩ</logic:equal>
	</td> 
    <td class="wordtd">¼������:</td>
    <td>
    	<bean:write name="maintContractBean" property="operDate"/>
    </td>       
  </tr>     
  </table>
  <br>
  
  <div style="border:solid #999999 1px;border-bottom:none;padding-top: 4;padding-bottom: 4;background:#ffffff;">        
   <b>&nbsp;��ͬ��ϸ</b>
  </div>
  <div id="scrollBox" style="overflow:scroll;">
    <table id="scrollTable" width="1650px" border="0" cellpadding="0" cellspacing="0" class="tb">
      <thead>
        <tr id="titleRow">
          <td class="wordtd_header" nowrap="nowrap">���</td>          
          <td class="wordtd_header" nowrap="nowrap">�´�ά��վ<logic:equal name="doType" value="assign"><font color="red">*</font></logic:equal></td>
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
          <td class="wordtd_header" nowrap="nowrap">ά����ʼ����<logic:equal name="doType" value="renewal"><font color="red">*</font></logic:equal></td>
          <td class="wordtd_header" nowrap="nowrap">ά����������<logic:equal name="doType" value="renewal"><font color="red">*</font></logic:equal></td>
          <td class="wordtd_header" nowrap="nowrap">ʵ�ʽ�������</td>
          <td class="wordtd_header" nowrap="nowrap">��������</td>
          <td class="wordtd_header" nowrap="nowrap">�Ƿ�̨����</td>
          <td class="wordtd_header" nowrap="nowrap">�Ƿ�Ϊ������</td>
          <%-- <td class="wordtd_header" nowrap="nowrap">��������</td>
          <td class="wordtd_header" nowrap="nowrap">�ʼ췢֤����</td>
          <td class="wordtd_header" nowrap="nowrap">�ƽ��ͻ�����</td>
          <td class="wordtd_header" nowrap="nowrap">ά��ȷ������</td>  --%>    
        </tr>
      </thead>
      <tfoot>
        <tr height="15px"><td colspan="20"></td></tr>
      </tfoot>
      <tbody>
        <logic:present name="maintContractDetailList">
          <logic:iterate id="e" name="maintContractDetailList" indexId="i">
            <tr>
              <td align="center">${i+1}
                <html:hidden name="e" property="rowid" />
                <html:hidden name="e" property="elevatorNo" />                                
              </td>
              <td align="center" width="1%">
                       
                <logic:notEqual name="doType" value="assign">             
                  <logic:iterate id="ms" name="maintStationList">
                    ${e.assignedMainStation == ms.storageid ? ms.storagename : ''}                
                  </logic:iterate>
                </logic:notEqual> 
                
                <logic:equal name="doType" value="assign">
                
                  <logic:equal name="e" property="assignedSignFlag" value="Y">
                  	<html:hidden name="e" property="assignedMainStation" /> 
                    <logic:iterate id="ms" name="maintStationList">
                      ${e.assignedMainStation == ms.storageid ? ms.storagename : ''}                                        
                    </logic:iterate>
                  </logic:equal>
                 
                  <logic:notEqual name="e" property="assignedSignFlag" value="Y">
                    <html:select name="e" property="assignedMainStation">
                      <html:option value="">��ѡ��</html:option>
                      <html:options collection="maintStationList" property="storageid" labelProperty="storagename" />                                 
                    </html:select>  
                  </logic:notEqual>
                  
                </logic:equal>
                                  
              </td>
              <td align="center">
              <logic:notEqual name="doType" value="renewal">
              	${e.signWay}
              </logic:notEqual>
              <logic:equal name="doType" value="renewal">
              	${e.r1}
              	<input type="hidden" name="" value="${e.signWay}"/>
              </logic:equal>
              </td> 
              <td align="center">
                <a onclick="simpleOpenWindow('elevatorSaleAction','${e.elevatorNo}');" class="link">${e.elevatorNo}</a>                                             
              </td>
              <td align="center">${e.elevatorType}</td>
              <td align="center">${e.floor}</td>
              <td align="center">${e.stage}</td>
              <td align="center">${e.door}</td>
              <td align="center">${e.high}</td>
              <td align="center">${e.elevatorParam}</td>
              <td align="center">
              	<logic:notEqual name="doType" value="aidate">
              		${e.annualInspectionDate}
              	</logic:notEqual>
              	<logic:equal name="doType" value="aidate">
              		<input type="text" name="annualInspectionDate" id="annualInspectionDate" value="${e.annualInspectionDate}" size="12" class="Wdate" onfocus="WdatePicker({readOnly:true})" onpropertychange="setDateChanged(this)" />
              	</logic:equal>
              </td>
              <td align="center">${e.salesContractNo}</td>
              <td align="center">${e.projectName}</td>
              <td align="center">${e.maintAddress}</td>
              <td align="center">
                <span class="renewal show">${e.mainSdate}</span>
                <span class="renewal hide"><input type="text" name="mainSdate" class="Wdate" onfocus="pickStartDay('mainEdate')" size="12"/></span>
              </td>
              <td align="center">
                <span class="renewal show">${e.mainEdate}</span>
                <span class="renewal hide"><input type="text" name="mainEdate" class="Wdate" onfocus="pickEndDay('mainSdate')" size="12"/></span>
              </td>
              <td align="center">${e.realityEdate}</td>
              <td align="center">${e.elevatorNature}</td>
              <td align="center">${e.r4}</td>
              <td align="center">${e.r5}</td>
              <%-- <td align="center">${e.shippedDate}</td>
              <td align="center">${e.issueDate}</td>
              <td align="center">${e.tranCustDate}</td>
              <td align="center">${e.mainConfirmDate}</td> --%>
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
      <td width="20%">
        <bean:write name="maintContractBean" property="contractTotal"/>
      </td>          
      <td class="wordtd" nowrap="nowrap">��������:</td>
      <td width="20%">
        <bean:write name="maintContractBean" property="otherFee"/>
      </td> 
      <td class="wordtd" nowrap="nowrap">&nbsp;</td>
      <td width="20%">&nbsp;</td>        
    </tr>
    <tr>
      <td class="wordtd" nowrap="nowrap">���ʽ:</td>
      <td colspan="5">
        <bean:write name="maintContractBean" property="r4"/>
      </td> 
    </tr>
  <tr>
    <td class="wordtd">���ʽ��ע:</td>
    <td colspan="5">
      	<bean:write name="maintContractBean" property="paymentMethodRem"/>
    </td> 
  </tr>
    <tr>
      <td class="wordtd">��ͬ�������������:</td>
      <td colspan="5">     
        <bean:write name="maintContractBean" property="r5"/> 
      </td>                  
    </tr>  
    <tr>
      <td class="wordtd">��ͬ�������������ע:</td>
      <td colspan="5">     
        <bean:write name="maintContractBean" property="contractContentRem"/> 
      </td>                  
    </tr> 
    <tr>
      <td class="wordtd">��ע:</td>
      <td colspan="5">     
        <bean:write name="maintContractBean" property="rem"/> 
      </td>                  
    </tr> 
  </table>
 
  <br>
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
    <tr>
      <td height="23" colspan="6">&nbsp;<b>�����Ϣ</td>
    </tr>
    <tr>
      <td class="wordtd" nowrap="nowrap">�����:</td>
      <td width="20%">
        ${maintContractBean.auditOperid}
      </td>     
      <td class="wordtd" nowrap="nowrap">���״̬:</td>
      <td width="20%">
        ${maintContractBean.auditStatus == 'Y' ? '�����' : 'δ���'}
      </td>         
      <td class="wordtd" nowrap="nowrap">���ʱ��:</td>
      <td width="20%">
        ${maintContractBean.auditDate}
      </td>        
    </tr>
    <tr>
      <td class="wordtd" nowrap="nowrap">������:</td>
      <td colspan="5">
	          <logic:notPresent name="auditdisplay">
	          	${maintContractBean.auditRem}
	          </logic:notPresent>
              <logic:present name="auditdisplay">
               <html:textarea name="maintContractBean" property="auditRem" rows="3" cols="100" styleClass="default_textarea"/>
             </logic:present>
      </td> 
    </tr> 
  </table>

  <br>
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
    <tr>
      <td height="23" colspan="6">&nbsp;<b>�����´���Ϣ</td>
    </tr>
    <tr>
      <td class="wordtd">�����´���:</td>
      <td width="20%">
        ${maintContractBean.taskUserId}
      </td>     
      <td class="wordtd">�����´��־:</td>
      <td width="20%">
        ${maintContractBean.taskSubFlag == 'Y' ? '���´�' : ''}
        ${maintContractBean.taskSubFlag == 'N' ? 'δ�´�' : ''}
        ${maintContractBean.taskSubFlag == 'R' ? '���˻�' : ''}
      </td>         
      <td class="wordtd">�����´�����:</td>
      <td width="20%">
        ${maintContractBean.taskSubDate}
      </td>        
    </tr>
    <tr>
      <td class="wordtd">�����´ﱸע:</td>
      <td colspan="5">
        <span class="assign show">${maintContractBean.taskRem}</span>
        <span class="assign hide">
          <html:textarea name="maintContractBean" property="taskRem" rows="3" cols="100" styleClass="default_textarea"/>
        </span>
      </td> 
    </tr> 
  </table>

  
  <script type="text/javascript"> 
	$(document).ready(function() {			
		setScrollTable('scrollBox','scrollTable',10); // ���ù������
	})
	//setTopRowDateInputsPropertychange(scrollTable); //��̬����һ�����ڿؼ����propertychange�¼�    
  </script> 
</logic:present>
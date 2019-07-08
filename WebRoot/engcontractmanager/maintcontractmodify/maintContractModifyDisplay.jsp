<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<link rel="stylesheet" type="text/css" href="<html:rewrite forward='formCSS'/>">
<script language="javascript" defer="defer" src="<html:rewrite forward='DatePickerJS'/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/openwindow.js"/>"></script>
<br>
<head>
  <title>XJSCRM</title>
</head>
<body>

  <html:errors/>
  <html:form action="/maintContractModifyAction.do?method=toAuditRecord">

    <html:hidden name="maintContractBean" property="billNo"/> 

<logic:present name="maintContractBean">
  <html:hidden name="maintContractBean" property="billNo"/>
  <input type="hidden" name="isupdate" value="${isupdate }"/>
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
    <tr>
      <td height="23" colspan="6">&nbsp;<b>�ͻ���Ϣ</td>
    </tr>
    <tr>
      <td class="wordtd">�׷���λ����:</td>
      <td width="20%" colspan="5">
      	  <%-- bean:write name="companyA" property="companyName"/--%>
      	  <input type="text" name="companyName" id="companyName" value="${companyA.companyName}" readonly="true" size="50"/>
	      <input type="button" value=".." onclick="openWindowAndReturnValue3('searchCustomerAction','toSearchRecord','cusNature=JF','')" class="default_input"/><font color="red">*</font>
	      <html:hidden name="maintContractBean" property="companyId" styleId="companyId"/>
      
      </td>
      </tr>
      <tr>
      <td class="wordtd">�׷���λ��ַ:</td>
      <td width="20%"><bean:write name="companyA" property="address"/></td>
      <td nowrap="nowrap" class="wordtd">�׷�����:</td>
      <td width="20%"><bean:write name="companyA" property="legalPerson"/></td>  
      <td nowrap="nowrap" class="wordtd">&nbsp;</td>
      <td width="20%">&nbsp;</td>   
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
      	<logic:notEqual name="isupdate" value="Y">
	        <logic:match name="maintContractBean" property="mainMode" value="FREE">���</logic:match>
	        <logic:match name="maintContractBean" property="mainMode" value="PAID">�շ�</logic:match>
	        <html:hidden name="maintContractBean" property="mainMode" write="false"/>
        </logic:notEqual>
        <logic:equal name="isupdate" value="Y">
           	<html:select name="maintContractBean" property="mainMode" styleId="maintContractBean">
	        	<html:option value="PAID">�շ�</html:option>
	        	<html:option value="FREE">���</html:option>
	        </html:select>
        </logic:equal>
      </td>         
    </tr>  
    <tr>
      <td class="wordtd"><bean:message key="maintContract.contractPeriod"/>(��):</td>
      <td>
           <html:text name="maintContractBean" property="contractPeriod" readonly="true" styleClass="default_input_noborder"/>          
    </td>          
      <td class="wordtd"><bean:message key="maintContract.contractSdate"/>:</td>
      <td>
      	<logic:notEqual name="isupdate" value="Y">
            ${maintContractBean.contractSdate }
            <input type="hidden" name="contractSdate" id="contractSdate" value="${maintContractBean.contractSdate }"/>
        </logic:notEqual>
        <logic:equal name="isupdate" value="Y">
      	    <html:text name="maintContractBean" property="contractSdate" styleId="contractSdate" size="12" styleClass="Wdate" onfocus="pickStartDay('contractEdate')" /><font color="red">*</font>  
    	</logic:equal>
      </td>
      <td class="wordtd"><bean:message key="maintContract.contractEdate"/>:</td>
      <td>
      	<logic:notEqual name="isupdate" value="Y">
            ${maintContractBean.contractEdate }
            <input type="hidden" name="contractEdate" id="contractEdate" value="${maintContractBean.contractEdate }"/>
        </logic:notEqual>
        <logic:equal name="isupdate" value="Y">
      	 	<html:text name="maintContractBean" property="contractEdate" styleId="contractEdate" size="12" styleClass="Wdate" onfocus="pickEndDay('contractSdate')" /><font color="red">*</font>   
    	</logic:equal>
      </td>         
    </tr>        
    <tr>
      <td class="wordtd"><bean:message key="maintContract.maintDivision"/>:</td>
      <td>
      	<%--bean:write name="maintDivisionName" /--%>
      	<html:select name="maintContractBean" property="maintDivision"  styleId="maintDivision"  onchange="EvenmoreUP(this,'maintStation')">
          <html:options collection="maintDivisionList" property="grcid" labelProperty="grcname"/>
        </html:select><font color="red">*</font>
      </td>
      <td class="wordtd">����ά��վ:</td>
      <td>
        <%-- bean:write name="maintStationName" /   onchange="setAssignedMainStation(this.value);"--%>
        <html:select name="maintContractBean" property="maintStation"  styleId="maintStation">
        	<html:option value="">��ѡ��</html:option>
          <html:options collection="maintStationList" property="storageid" labelProperty="storagename"/>
        </html:select><font color="red">*</font>
      </td>    
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
  <tr>
  <td nowrap="nowrap" class="wordtd">��ͬ״̬:</td>
	<td colspan="5">
		<html:select name="maintContractBean" property="contractStatus">
       		<html:options collection="contractStatusList" property="id.pullid" labelProperty="pullname"/>
     	</html:select>
     </td>
  </tr>
  </table>
  <br>
  
  <div style="border:solid #999999 1px;border-bottom:none;padding-top: 4;padding-bottom: 4;background:#ffffff;">
  <logic:equal name="isupdate" value="Y">
  &nbsp;&nbsp;<input type="button" value=" - " onclick="deleteRow('scrollTable')" class="default_input">       
  </logic:equal>
   <b>&nbsp;��ͬ��ϸ</b>
  </div>
  <div id="scrollBox" style="overflow:scroll;">
  	<table id="scrollTable" width="1600px" border="0" cellpadding="0" cellspacing="0" class="tb">
      <thead>
        <tr id="titleRow">
          <logic:equal name="isupdate" value="Y">
          	<td class="wordtd_header" ><input type="checkbox" name="cbAll" onclick="checkTableAll('scrollTable',this)"/></td>
          </logic:equal>
          <td class="wordtd_header" nowrap="nowrap">���</td>          
          <td class="wordtd_header" nowrap="nowrap">�´�ά��վ<font color="red">*</font></td>
          <td class="wordtd_header" nowrap="nowrap">ǩ��ʽ<font color="red">*</font></td>
          <td class="wordtd_header" nowrap="nowrap">���ݱ��<font color="red">*</font></td>
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
          <td class="wordtd_header" nowrap="nowrap">ά����������<logic:equal name="isupdate" value="Y"><font color="red">*</font></logic:equal></td>
          <td class="wordtd_header" nowrap="nowrap">�Ƿ��˱�</td>   
          <td class="wordtd_header" nowrap="nowrap">��������</td> 
          <td class="wordtd_header" nowrap="nowrap">�Ƿ��ѻ����պϸ�֤</td>
          <td class="wordtd_header" nowrap="nowrap">�Ƿ�̨����</td>
          <td class="wordtd_header" nowrap="nowrap">�Ƿ�Ϊ������</td>
        </tr>
      </thead>
      <tfoot>
        <tr height="15px"><td colspan="21"></td></tr>
      </tfoot>
      <tbody>
        <logic:present name="maintContractDetailList">
          <logic:iterate id="e" name="maintContractDetailList" indexId="i">
            <tr>
            	<logic:equal name="isupdate" value="Y">
        			<td align="center"><input type="checkbox" onclick="cancelCheckAll('scrollTable','cbAll')"/></td>
        		</logic:equal>
              <td align="center">${i+1}
                <html:hidden name="e" property="rowid" />
                <%-- html:hidden name="e" property="elevatorNo" /--%>                                
              </td>
             <td align="center" width="1%">
                  <select name="assignedMainStation" id="assignedMainStation">
	              		<option value="">��ѡ��</option>
			           	<logic:iterate id="s" name="maintStationList" >
				           <logic:equal name="s" property="storageid" value="${e.assignedMainStation}">
				           		<option value="${s.storageid}" selected="selected">${s.storagename}</option>
				           </logic:equal>
				           <logic:notEqual name="s" property="storageid" value="${e.assignedMainStation}">
				              <option value="${s.storageid}">${s.storagename}</option>
			              </logic:notEqual>
		            	</logic:iterate>
		        	</select>
              </td>
              <td align="center">
              	<logic:notEqual name="isupdate" value="Y">
              		${e.r1}
              	</logic:notEqual>
              	<logic:equal name="isupdate" value="Y">
              		<select name="signWay" property="signWay" >
			           	<logic:iterate id="s" name="signWayList" >
				           <logic:equal name="s" property="id.pullid" value="${e.signWay}">
				           		<option value="${s.id.pullid}" selected="selected">${s.pullname}</option>
				           </logic:equal>
				           <logic:notEqual name="s" property="id.pullid" value="${e.signWay}">
				              <option value="${s.id.pullid}">${s.pullname}</option>
			              </logic:notEqual>
		            	</logic:iterate>
		        	</select>
              	</logic:equal>
              </td> 
              <td align="center">
                <input type="text" name="elevatorNo" value="${e.elevatorNo}" class="default_input"/>
	              <%-- a onclick="simpleOpenWindow('elevatorSaleAction','${e.elevatorNo}');" class="link">${e.elevatorNo}</a--%>                                             
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
              <td align="center">
	              <logic:notEqual name="isupdate" value="Y">
	              		${e.mainEdate}
	              		<input type="hidden" name="mainEdate" value="${e.mainEdate}"/>
	              </logic:notEqual>
	              <logic:equal name="isupdate" value="Y">
	             	<input type="text" name="mainEdate" size="13" class="Wdate" onfocus="WdatePicker({readOnly:true,isShowClear:true});" value="${e.mainEdate }"/>
	              </logic:equal>
              </td>
              <td align="center">
	              <logic:notEqual name="isupdate" value="Y">
	              		<logic:notEqual name="e" property="isSurrender" value="Y">��</logic:notEqual>
	              		<logic:equal name="e" property="isSurrender" value="Y">��</logic:equal>
	              </logic:notEqual>
	              <logic:equal name="isupdate" value="Y">
	              	<select name="isSurrender">
	              		<option value='N' <logic:notEqual name="e" property="isSurrender" value="Y">selected="selected"</logic:notEqual> >��</option>
	              		<option value='Y' <logic:equal name="e" property="isSurrender" value="Y">selected="selected"</logic:equal> >��</option>
	              	</select>
	              	</logic:equal>
              </td>
              <td align="center">
              <logic:notEqual name="isupdate" value="Y">
              	${e.elevatorNature}
              </logic:notEqual>
              <logic:equal name="isupdate" value="Y">
	              <select name="elevatorNature" property="elevatorNature" >
		           	<logic:iterate id="s" name="elevatorNatureList" >
			           <logic:equal name="s" property="id.pullid" value="${e.elevatorNature}">
			           		<option value="${s.id.pullid}" selected="selected">${s.pullname}</option>
			           </logic:equal>
			           <logic:notEqual name="s" property="id.pullid" value="${e.elevatorNature}">
			              <option value="${s.id.pullid}">${s.pullname}</option>
		              </logic:notEqual>
	            	</logic:iterate>
	        	</select>
	        	</logic:equal>
              </td>
              <td align="center">
	              <logic:notEqual name="isupdate" value="Y">
	              		<logic:notEqual name="e" property="isCertificate" value="Y">��</logic:notEqual>
	              		<logic:equal name="e" property="isCertificate" value="Y">��</logic:equal>
	              </logic:notEqual>
	              <logic:equal name="isupdate" value="Y">
	              	<select name="isCertificate">
	              		<option value='N' <logic:notEqual name="e" property="isCertificate" value="Y">selected="selected"</logic:notEqual> >��</option>
	              		<option value='Y' <logic:equal name="e" property="isCertificate" value="Y">selected="selected"</logic:equal> >��</option>
	              	</select>
	              	</logic:equal>
              </td>
              <td align="center">
	            <select name="r4" property="r4" >
		           	<option value="��" <logic:equal name="e" property="r4" value="��">selected="selected"</logic:equal>>��</option>
             		<option value="��" <logic:equal name="e" property="r4" value="��">selected="selected"</logic:equal>>��</option>
		        </select>
	        </td>
	        <td align="center">
	            <select name="r5" property="r5" >
	            	<option value="��" <logic:equal name="e" property="r5" value="��">selected="selected"</logic:equal>>��</option>
		           	<option value="��" <logic:equal name="e" property="r5" value="��">selected="selected"</logic:equal>>��</option>
             		
		        </select>
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
      <td width="20%">
          <html:text name="maintContractBean" property="contractTotal" styleClass="default_input" styleId="contractTotal" onchange="checkthisvalue(this);"/><font color="red">*</font>
      </td>          
      <td class="wordtd" nowrap="nowrap">��������:</td>
      <td width="20%">
          <html:text name="maintContractBean" property="otherFee" styleClass="default_input" styleId="otherFee" onchange="checkthisvalue(this);"/>
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
      		<html:textarea name="maintContractBean" property="rem" rows="3" cols="100" styleClass="default_textarea"/>
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
      <td colspan="5">${maintContractBean.auditRem}</td> 
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
      <td colspan="5">${maintContractBean.taskRem}</td> 
    </tr> 
  </table>

  
  <script type="text/javascript"> 
	$(document).ready(function() {			
		setScrollTable('scrollBox','scrollTable',10); // ���ù������
	})
	
	  $("#contractSdate,#contractEdate").bind("propertychange",function(){setContractPeriod();}); // �����ͬ��Ч��  
  </script> 
</logic:present>
    
    
    
  </html:form>
</body>
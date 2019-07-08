<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>

<html:hidden property="isreturn"/>
<html:hidden property="id"/>
<html:hidden property="isSelectAdd" styleId="isSelectAdd"/>
<html:hidden property="maintContractDetails" styleId="maintContractDetails"/>
<html:hidden name="maintContractBean" property="billNo"/>
<logic:present name="maintContractBean" property="historyBillNo">
	<html:hidden name="maintContractBean" property="historyBillNo" />
	<html:hidden name="maintContractBean" property="histContractNo"/>
	<html:hidden name="maintContractBean" property="histContractStatus"/>
	<html:hidden name="maintContractBean" property="xqType"/>
</logic:present>


<table id="companyA" width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
  <tr>
    <td height="23" colspan="6">&nbsp;<b>�ͻ���Ϣ</td>
  </tr>
  <tr>
    <td class="wordtd">�׷���λ����:</td>
    <td width="20%">
      <input type="text" name="companyName" id="companyName" value="${companyA.companyName}" readonly="true" size="23"/>
      <input type="button" value=".." onclick="openWindowAndReturnValue3('searchCustomerAction','toSearchRecord','cusNature=JF','')" class="default_input"/><font color="red">*</font>
      <html:hidden name="maintContractBean" property="companyId" styleId="companyId"/>
    </td>
    <td class="wordtd">�׷���λ��ַ:</td>
    <td width="20%" id="address">${companyA.address}</td>
    <td nowrap="nowrap" class="wordtd">�׷�����:</td>
    <td width="20%" id="legalPerson">${companyA.legalPerson}</td>  
  </tr>
  <tr>    
    <td class="wordtd">�׷�ί����:</td>
    <td id="client">${companyA.client}</td>
    <td class="wordtd">�׷���ϵ��:</td>
    <td id="contacts">${companyA.contacts}</td>   
    <td class="wordtd">�׷���ϵ�绰:</td>
    <td id="contactPhone">${companyA.contactPhone}</td>          
  </tr>     
  <tr>
    <td class="wordtd">�׷�����:</td>
    <td id="fax">${companyA.fax}</td>   
    <td class="wordtd">�׷��ʱ�:</td>
    <td id="postCode">${companyA.postCode}</td>          
    <td class="wordtd">��ַ���绰:</td>
    <td id="accountHolder">${companyA.accountHolder}</td> 
  </tr>
  <tr>      
    <td class="wordtd">�׷������˺�:</td>
    <td id="account">${companyA.account}</td>          
    <td class="wordtd">�׷���������:</td>
    <td id="bank">${companyA.bank}</td>   
    <td class="wordtd">��˰��ʶ���:</td>
    <td id="taxId">${companyA.taxId}</td>          
  </tr>                 
</table>
<br>

<table id="companyB" width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
  <tr>
    <td class="wordtd">�ҷ���λ����:</td>
    <td width="20%">
    ${companyB.companyName}
      <%-- <input type="text" name="companyName" id="companyName2" value="${companyB.companyName}" readonly="true" size="23"/> --%>
      <!-- <input type="button" value=".." onclick="openWindowAndReturnValue2('searchCustomerAction','2')" class="default_input"/><font color="red">*</font> -->
      <html:hidden name="maintContractBean" property="companyId2" styleId="companyId2" value="${companyB.companyId}" />     
    </td>
    <td class="wordtd">�ҷ���λ��ַ:</td>
    <td width="20%" id="address2">${companyB.address}</td>
    <td nowrap="nowrap" class="wordtd">�ҷ�����:</td>
    <td width="20%" id="legalPerson2">${companyB.legalPerson}</td>
  </tr>
  <tr>       
    <td class="wordtd">�ҷ�ί����:</td>
    <td id="client2">${companyB.client}</td>          
    <td class="wordtd">�ҷ���ϵ��:</td>
    <td id="contacts2">${companyB.contacts}</td>   
    <td class="wordtd">�ҷ���ϵ�绰:</td>
    <td id="contactPhone2">${companyB.contactPhone}</td>          
  </tr>     
  <tr>
    <td class="wordtd">�ҷ�����:</td>
    <td id="fax2">${companyB.fax}</td>   
    <td class="wordtd">�ҷ��ʱ�:</td>
    <td id="postCode2">${companyB.postCode}</td>          
    <td class="wordtd">�ҷ�����:</td>
    <td id="accountHolder2">${companyB.accountHolder}</td>   
  </tr>
  <tr>
    <td class="wordtd">�ҷ������˺�:</td>
    <td id="account2">${companyB.account}</td> 
    <td class="wordtd"></td>
    <td></td>   
    <td class="wordtd"></td>
    <td></td>                   
  </tr>            
</table>
<br>

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
  <tr>
    <td height="23" colspan="6">&nbsp;<b>��ͬ����Ϣ</td>
  </tr>
  <tr>
    <td class="wordtd">ά����ͬ��:</td>
    <td width="20%">
      <html:text name="maintContractBean" property="maintContractNo" readonly="true" styleClass="default_input_noborder"/>
    </td>          
    <td nowrap="nowrap" class="wordtd">��ͬ����:</td>
    <td width="20%">�Ա�</td>
    <td nowrap="nowrap" class="wordtd">������ʽ:</td>
    <td width="20%">
    <logic:present name="isupdate">
    	<logic:equal name="maintContractBean" property="mainMode" value="FREE">���</logic:equal>
    	<logic:equal name="maintContractBean" property="mainMode" value="PAID">�շ�</logic:equal>
    	<html:hidden name="maintContractBean" property="mainMode" />
    </logic:present>
    <logic:notPresent name="isupdate">
	    <logic:notPresent name="isSelectAdd">
	    	���<html:hidden name="maintContractBean" property="mainMode" value="FREE"/>
	    </logic:notPresent>
	    <logic:present name="isSelectAdd">
	    	<logic:match name="isSelectAdd" value="Y">
	    		�շ�<html:hidden name="maintContractBean" property="mainMode" value="PAID"/>
	    	</logic:match>
	    </logic:present>
    </logic:notPresent>
    </td>         
  </tr>  
  <tr>
    <td class="wordtd">��ͬ��Ч�ڣ��£�:</td>
    <td>
      <html:text name="maintContractBean" property="contractPeriod" readonly="true" styleClass="default_input_noborder"/>          
    </td>          
    <td class="wordtd">��ͬ��ʼ����:</td>
    <td>
      <html:text name="maintContractBean" property="contractSdate" styleId="contractSdate" size="12" readonly="true" styleClass="default_input_noborder" />  
    </td>
    <td class="wordtd">��������:</td>
    <td>
      <html:text name="maintContractBean" property="contractEdate" styleId="contractEdate" size="12" readonly="true" styleClass="default_input_noborder" />   
    </td>         
  </tr>          
  <tr>
    <td class="wordtd">����ά���ֲ�:</td>
    <td>
    <logic:equal parameter="isSelectAdd" value="Y">
      <bean:write name="maintDivisionName"/>
      <html:hidden name="maintContractBean" property="maintDivision"/>
      </logic:equal>
      <logic:notEqual parameter="isSelectAdd" value="Y">
      	<html:select name="maintContractBean" property="maintDivision" styleId="maintDivision" onchange="EvenmoreAdd(this,'maintStation')">
        	<html:options collection="maintDivisionList" property="grcid" labelProperty="grcname"/>
        </html:select><font color="red">*</font>
      </logic:notEqual>
    </td>
    <td class="wordtd">����ά��վ:</td>
    <td>
      <logic:equal parameter="isSelectAdd" value="Y">
        ${maintStationName}
        <html:hidden name="maintContractBean" property="maintStation"/>
      </logic:equal>
      <logic:notEqual parameter="isSelectAdd" value="Y">
        <html:select name="maintContractBean" property="maintStation"  styleId="maintStation">
        	<html:option value="">��ѡ��</html:option>
          <html:options collection="maintStationList" property="storageid" labelProperty="storagename"/>
        </html:select><font color="red">*</font>
      </logic:notEqual>
    </td>
    <td class="wordtd">������:</td>
    <td>
    	 <html:text name="maintContractBean" property="attn" readonly="true" styleClass="default_input_noborder"/>
    </td>     
  </tr>
  <tr>
    <td class="wordtd">������ˮ��:</td>
    <td>
      ${maintContractBean.quoteBillNo}
      <html:hidden name="maintContractBean" property="quoteBillNo"/>
    </td>
    <td class="wordtd">����ǩ��ʽ:</td>
    <td>
    	<logic:equal name="maintContractBean" property="quoteSignWay" value="ZB">��ǩ</logic:equal>
    	<logic:equal name="maintContractBean" property="quoteSignWay" value="XB">��ǩ</logic:equal>
    	<logic:equal name="maintContractBean" property="quoteSignWay" value="BFXB">������ǩ</logic:equal>
		<html:hidden name="maintContractBean" property="quoteSignWay"/>
	</td>  
    <td class="wordtd">¼������:</td>
    <td>
    	<html:text name="maintContractBean" property="operDate" readonly="true" styleClass="default_input_noborder"/>
    </td>  
  </tr> 
</table>
<br>

<div id="caption_0" style="width: 100%;padding-top: 2;padding-bottom: 2;border-bottom: 0" class="tb">
  &nbsp;<input type="button" value=" + " onclick="addElevators('dynamictable_0')" class="default_input">
  <input type="button" value=" - " onclick="deleteRow('dynamictable_0')" class="default_input">           
  <b>&nbsp;��ͬ��ϸ</b>
</div>
<div id="wrap_0" style="overflow: scroll;">
  <table id="dynamictable_0" width="1600px" border="0" cellpadding="0" cellspacing="0" class="tb">
    <thead>
      <tr id="titleRow_0">
        <td class="wordtd_header" ><input type="checkbox" name="cbAll" onclick="checkTableAll('dynamictable_0',this)"/></td>
        <td class="wordtd_header" nowrap="nowrap">���</td>
        <td class="wordtd_header" nowrap="nowrap">ǩ��ʽ</td>
        <td class="wordtd_header" width="150">���ݱ��</td>
        <td class="wordtd_header" nowrap="nowrap">��������</td>
        <td class="wordtd_header">��</td>
        <td class="wordtd_header">վ</td>
        <td class="wordtd_header">��</td>
        <td class="wordtd_header" nowrap="nowrap">�����߶�</td>
        <td class="wordtd_header">����ͺ�</td>
        <td class="wordtd_header">�������</td>
        <td class="wordtd_header">���ۺ�ͬ��</td>
        <td class="wordtd_header">����λ����</td>
        <td class="wordtd_header">ʹ�õ�λ����<font color="red">*</font></td>
        <td class="wordtd_header">ά����ʼ����<font color="red">*</font></td>
        <td class="wordtd_header">ά����������<font color="red">*</font></td>
        <td class="wordtd_header" nowrap="nowrap">ʵ�ʽ�������</td>
        <td class="wordtd_header" nowrap="nowrap">��������<font color="red">*</font></td>
        <td class="wordtd_header" nowrap="nowrap">�Ƿ�̨����<font color="red">*</font></td>
        <td class="wordtd_header" nowrap="nowrap">�Ƿ�Ϊ������</td>
       <!-- <td class="wordtd_header freeRequire">�ʼ췢֤����</td>
        <td class="wordtd_header freeRequire">�ƽ��ͻ�����</td>
        <td class="wordtd_header freeRequire">ά��ȷ������</td>   -->   
      </tr>
    </thead>
    <tfoot>
      <tr height="15"><td colspan="20"></td></tr>
    </tfoot>
    <tbody>
      <tr id="sampleRow_0" style="display: none;">
        <td align="center"><input type="checkbox" onclick="cancelCheckAll('dynamictable_0','cbAll')"/></td>
        <td align="center"><input name="rownum" size="2" class="noborder_center"/></td>        
        <td align="center">
        	���Ᵽ<input type="hidden" name="signWay" value="XQ"/>
          <%-- <select name="signWay" id="signWay" onchange="changeSignWay(this)" >
            <logic:notEqual name="isSelectAdd" value="Y">
              <option value="">��ѡ��</option>
            </logic:notEqual>
            <logic:iterate id="s" name="signWayList" >
              <option value="${s.id.pullid}">${s.pullname}</option>
            </logic:iterate>
          </select> --%>
        </td>    
        <td align="center"><input type="text" name="elevatorNo" onclick="simpleOpenWindow('elevatorSaleAction',this.value);" readonly="readonly" class="link noborder_center"/></td>
        <td align="center"><input type="text" name="elevatorType" size="8" readonly="readonly" class="noborder_center"/></td>
        <td align="center"><input type="text" name="floor" size="3" readonly="readonly" class="noborder_center"/></td>
        <td align="center"><input type="text" name="stage" size="3" readonly="readonly" class="noborder_center"/></td>
        <td align="center"><input type="text" name="door" size="3" readonly="readonly" class="noborder_center"/></td>
        <td align="center"><input type="text" name="high" size="3" readonly="readonly" class="noborder_center"/></td>
        <td align="center"><input type="text" name="elevatorParam" readonly="readonly" class="noborder_center"/></td>
        <td align="center"><input type="text" name="annualInspectionDate" id="annualInspectionDate" size="12" class="Wdate" onfocus="WdatePicker({readOnly:true})"/></td>
        <td align="center"><input type="text" name="salesContractNo" readonly="readonly" class="noborder_center"/></td>
        <td align="center"><input type="text" name="projectName" class="noborder_center" size="30" readonly="readonly"/></td>        
        <td align="center"><input type="text" name="projectAddress"  size="30" onpropertychange="setOhterVal(this);"/></td>
        <td align="center"><input type="text" name="mainSdate" size="12" class="Wdate" onfocus="pickStartDay('mainEdate');setcontractSdate();" /></td>
        <td align="center"><input type="text" name="mainEdate" size="12" class="Wdate" onfocus="pickEndDay('mainSdate');setcontractEdate();" /></td>
        <td align="center"></td>
        <td align="center">
        <select name="elevatorNature" property="elevatorNature" >
           <logic:iterate id="s" name="elevatorNatureList" >
              <option value="${s.id.pullid}">${s.pullname}</option>
            </logic:iterate>
        </select>
        </td>
        <td align="center">
        <select name="r4" property="r4" >
             <option value="��">��</option>
             <option value="��">��</option>
        </select>
        </td>
        <td align="center">
        <select name="r5" property="r5" >
        	<option value="��">��</option>
            <option value="��">��</option>
        </select>
        </td>
        <!-- <td align="center"><input type="text" name="shippedDate" size="12" class="Wdate" onfocus="WdatePicker({isShowClear:true,readOnly:true})"/></td>
        <td align="center"><input type="text" name="issueDate" size="12" class="Wdate" onfocus="WdatePicker({isShowClear:true,readOnly:true})"/></td>
        <td align="center"><input type="text" name="tranCustDate" size="12" class="Wdate" onfocus="WdatePicker({isShowClear:true,readOnly:true})"/></td>
        <td align="center"><input type="text" name="mainConfirmDate" size="12" class="Wdate" onfocus="WdatePicker({isShowClear:true,readOnly:true})"/></td> -->
      </tr>
      <logic:present name="maintContractDetailList">
        <logic:iterate id="e" name="maintContractDetailList" indexId="i">
          <tr>
            <td align="center"><input type="checkbox" onclick="cancelCheckAll(this)"/></td>
            <td align="center"><input name="rownum" value="${i+1}" size="2" class="noborder_center"/></td>
            <td align="center">
            	${e.r1}<input type="hidden" name="signWay" value="${e.signWay}"/>
              <%-- <html:select name="e" property="signWay" onchange="changeSignWay(this)" >
                <html:options collection="signWayList" property="id.pullid" labelProperty="pullname"/>
              </html:select> --%>
            </td> 
            <td align="center">
              <input type="text" name="elevatorNo" value="${e.elevatorNo}" readonly="readonly" onclick="simpleOpenWindow('elevatorSaleAction',this.value);" class="link noborder_center"/>
            </td>
            <td align="center"><input type="text" name="elevatorType" value="${e.elevatorType}" readonly="readonly" size="8" class="noborder_center"/></td>
            <td align="center"><input type="text" name="floor" size="3" value="${e.floor}" readonly="readonly" class="noborder_center"/></td>
            <td align="center"><input type="text" name="stage" size="3" value="${e.stage}" readonly="readonly" class="noborder_center"/></td>
            <td align="center"><input type="text" name="door" size="3" value="${e.door}" readonly="readonly" class="noborder_center"/></td>
            <td align="center"><input type="text" name="high" size="3" value="${e.high}" readonly="readonly" class="noborder_center"/></td>
            <td align="center"><input type="text" name="elevatorParam" value="${e.elevatorParam}" readonly="readonly" class="noborder_center"/></td>
            <td align="center"><input type="text" name="annualInspectionDate" value="${e.annualInspectionDate}" size="12" class="Wdate" onfocus="WdatePicker({isShowClear:true,readOnly:true})"/></td>
            <td align="center"><input type="text" name="salesContractNo" value="${e.salesContractNo}" readonly="readonly" class="noborder_center"/></td>
            <td align="center"><input type="text" name="projectName" value="${e.projectName}" class="noborder_center" size="30" readonly="readonly"/></td>        
            <td align="center"><input type="text" name="projectAddress" value="${e.maintAddress}"  size="30" onpropertychange="setOhterVal(this);" /></td>        
            <td align="center"><input type="text" name="mainSdate" value="${e.mainSdate}" size="12" class="Wdate" onfocus="pickStartDay('mainEdate');setcontractSdate();"/></td>
            <td align="center"><input type="text" name="mainEdate" value="${e.mainEdate}" size="12" class="Wdate" onfocus="pickEndDay('mainSdate');setcontractEdate();"/></td>
            <td align="center"></td>
            <td align="center">
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
            <%-- <td align="center"><input type="text" name="shippedDate" value="${e.shippedDate}" size="12" class="Wdate" onfocus="WdatePicker({isShowClear:true,readOnly:true})"/></td>
            <td align="center"><input type="text" name="issueDate" value="${e.issueDate}" size="12" class="Wdate" onfocus="WdatePicker({isShowClear:true,readOnly:true})"/></td>
            <td align="center"><input type="text" name="tranCustDate" value="${e.tranCustDate}" size="12" class="Wdate" onfocus="WdatePicker({isShowClear:true,readOnly:true})"/></td>
            <td align="center"><input type="text" name="mainConfirmDate" value="${e.mainConfirmDate}" size="12" class="Wdate" onfocus="WdatePicker({isShowClear:true,readOnly:true})"/></td> --%>
          </tr>
        </logic:iterate>
      </logic:present>
    </tbody>    
  </table>
</div>
<br>

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
  <tr>
    <td nowrap="nowrap" class="wordtd">��ͬ�ܶ�:</td>
    <td width="20%">
      <html:text name="maintContractBean" property="contractTotal" readonly="true" styleClass="default_input_noborder" styleId="contractTotal" onchange="checkthisvalue(this);"/><!-- <font color="red">*</font> -->         
    </td>          
    <td nowrap="nowrap" class="wordtd">��������:</td>
    <td width="20%">
      <html:text name="maintContractBean" property="otherFee" readonly="true" styleClass="default_input_noborder" styleId="otherFee" onchange="checkthisvalue(this);"/>        
    </td>     
    <td nowrap="nowrap" class="wordtd">&nbsp;</td>
    <td width="20%">&nbsp;</td>   
  </tr>

  <tr>
    <td class="wordtd">���ʽ:</td>
    <td colspan="5">
    	<html:hidden name="maintContractBean" property="paymentMethod" />
      	<bean:write name="maintContractBean" property="r4"/>
    </td> 
  </tr>
  <tr>
    <td class="wordtd">���ʽ��ע:</td>
    <td colspan="5">
    	<html:hidden name="maintContractBean" property="paymentMethodRem" />
      	<bean:write name="maintContractBean" property="paymentMethodRem"/>
    </td> 
  </tr>
  <tr>
    <td class="wordtd">��ͬ�������������:</td>
    <td colspan="5">  
    	<html:hidden name="maintContractBean" property="contractTerms"/>
      	<bean:write name="maintContractBean" property="r5"/>      
    </td>                  
  </tr> 
  <tr>
    <td class="wordtd">��ͬ�������������ע:</td>
    <td colspan="5">
    	<html:hidden name="maintContractBean" property="contractContentRem" />
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

<script type="text/javascript"> 
	$("document").ready(function() {
		initPage();  
	})
	
	function initPage(){	
		setDynamicTable("dynamictable_0","sampleRow_0");// ���ö�̬��ɾ�б��
		setScrollTable("wrap_0","dynamictable_0",10);// ���ù������
		
		// ���ñ����ɾ��ʱ�������¼�
		setOnTableChange("dynamictable_0",function(){
			var rownums = document.getElementsByName("rownum"); 
			for(var i=0;i<rownums.length;i++){			
				rownums[i].value = i+1;
			}
		});	  
				 
		$("#contractSdate,#contractEdate").bind("propertychange",function(){setContractPeriod();}); // �����ͬ��Ч��  
		setTopRowDateInputsPropertychange(dynamictable_0); //��̬����һ�����ڿؼ����propertychange�¼�    
		selectAddElements($("#isSelectAdd").val()); // ��Ϊѡ���½�ʱִ�еķ���

		isFree($("#mainMode").val());//�жϱ�����ʽ�Ƿ���ѣ����ʱ�������ڡ��ʼ췢֤���ڡ��ƽ��ͻ����ڡ�ά��ȷ�����ڱ���
	}
</script>
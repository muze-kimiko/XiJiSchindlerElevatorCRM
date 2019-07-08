<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<script language="javascript" defer="defer" src="<html:rewrite forward='DatePickerJS'/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/openwindow.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/checkinput.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/dynamictable.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/math.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/jquery-1.9.1.min.js"/>"></script>

<html:hidden property="isreturn"/>
<html:hidden property="id"/>
<html:hidden name="maintContractQuoteBean" property="billNo"/>
<html:hidden property="maintContractQuoteDetails" styleId="maintContractQuoteDetails"/>

<div class="title-bar">
  &nbsp;<b>����Ϣ</b>
</div>
<table id="table1" width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
  <tr>
    <td nowrap="nowrap" class="wordtd"><bean:message key="maintContractQuote.attn"/>:</td>
    <td width="30%">
      <bean:write name="attnName"/>
      <html:hidden name="maintContractQuoteBean" property="attn"/>
    </td>
    <td nowrap="nowrap" class="wordtd"><bean:message key="maintContractQuote.applyDate"/>:</td>
    <td width="20%">
      <bean:write name="maintContractQuoteBean" property="applyDate"/>
      <html:hidden name="maintContractQuoteBean" property="applyDate"/>
    </td>
    <td class="wordtd"><bean:message key="maintContractQuote.maintDivision"/>:</td>
    <td width="20%">
      <bean:write name="maintDivisionName"/>
      <html:hidden name="maintContractQuoteBean" property="maintDivision"/>
    </td>           
  </tr>   
  <tr>
    <td class="wordtd">�׷���λ����:</td>
    <td>
      <%-- >html:hidden name="maintContractQuoteBean" property="companyId"/--%>
      <input type="text" name="companyName" id="companyName" value="${maintContractQuoteBean.companyName}" size="35" styleClass="default_input"/><font color="red">*</font>
      <%-- input type="button" value=".." onclick="openWindowAndReturnValue('searchCustomerAction','')"/><font color="red">*</font>&nbsp; --%>    
    </td>
    <td class="wordtd">�׷���ϵ��:</td>
    <td><input type="text" name="contacts" id="contacts" value="${maintContractQuoteBean.contacts}" class="default_input"/></td>
    <td class="wordtd">�׷���ϵ�绰:</td>
    <td><input type="text" name="contactPhone" id="contactPhone" value="${maintContractQuoteBean.contactPhone}" class="default_input"/></td>          
  </tr>
  <tr>
    <td class="wordtd"><bean:message key="maintContractQuote.maintStation"/>:</td>
    <td>
     <logic:equal name="maintContractQuoteBean" property="r1" value="A03">
     	${maintContractQuoteBean.r2}
     	<html:hidden name="maintContractQuoteBean" property="maintStation" styleId="maintStation"/>
     </logic:equal>
     <logic:notEqual name="maintContractQuoteBean" property="r1" value="A03">
	      <html:select name="maintContractQuoteBean" property="maintStation" styleId="maintStation">
	      	<html:option value="">��ѡ��</html:option>
	        <html:options collection="maintStationList" property="storageid" labelProperty="storagename"/><font color="red">*</font>
	      </html:select><font color="red">*</font>
      </logic:notEqual>
      
    </td>
    <!-- <td nowrap="nowrap" class="wordtd">�ͻ�����:</td>
    <td><html:text name="maintContractQuoteBean" property="customerArea" styleId="customerArea" styleClass="default_input"/></td>   -->
   	<td nowrap="nowrap" class="wordtd">����ǩ��ʽ:</td>
    <td>
    	<logic:equal name="maintContractQuoteBean" property="quoteSignWay" value="ZB">��ǩ</logic:equal>
    	<logic:equal name="maintContractQuoteBean" property="quoteSignWay" value="XB">��ǩ</logic:equal>
    	<logic:equal name="maintContractQuoteBean" property="quoteSignWay" value="BFXB">������ǩ</logic:equal>
		<html:hidden name="maintContractQuoteBean" property="quoteSignWay"/>
	</td>
	<td nowrap="nowrap" class="wordtd">&nbsp;</td>
	<td>&nbsp;</td>
	<%-- 
	<td nowrap="nowrap" class="wordtd">��ͬ����(��):</td>
	<td ><html:text name="maintContractQuoteBean" property="contractYears" styleId="contractYears" onchange="checkthisvalue(this);" size="10"/><font color="red">*</font></td>
  --%>
  </tr>         
</table>
<br>

<div id="caption_0" style="width: 100%;padding-top: 2;padding-bottom: 2;border-bottom: 0" class="tb">
  &nbsp;<input type="button" value=" + " onclick="addElevators('dynamictable_0')" class="default_input">
  <input type="button" value=" - " onclick="deleteRow('dynamictable_0');sumValuesByName('standardQuote','standardQuoteTotal');jsr8();" class="default_input">           
  <b>&nbsp;������Ϣ</b>
  <%-- input type="button" value="�����׼����" onclick="jsQuote()" class="default_input"--%>  
  <b><font color="red">��ע�⣺����=��ִͬ���ڼ䣬Ҫ֧���������ܶ��</font></b>
</div>
<div id="wrap_0" style="overflow-x:scroll">
<table id="dynamictable_0" class="dynamictable tb" width="1200" border="0" cellpadding="0" cellspacing="0">
  <thead>
    <tr id="titleRow_0">
      <td class="wordtd_header"><input type="checkbox" id="cbAll" onclick="checkTableAll('dynamictable_0',this)"/></td>
      <td class="wordtd_header" nowrap="nowrap">ǩ��ʽ</td>
      <td class="wordtd_header">���ݱ��</td>
      <td class="wordtd_header" nowrap="nowrap">��������</td>
      <td class="wordtd_header">��</td>
      <td class="wordtd_header">վ</td>
      <td class="wordtd_header">��</td>
      <td class="wordtd_header" nowrap="nowrap">�����߶�</td>
      <td class="wordtd_header" nowrap="nowrap">����</td>
      <td class="wordtd_header" nowrap="nowrap">�ٶ�</td>
      <td class="wordtd_header">����ͺ�</td>
      <td class="wordtd_header">���ۺ�ͬ��</td>
      <td class="wordtd_header">��Ŀ����</td>
      <td class="wordtd_header">��Ŀ��ַ</td>
      <td class="wordtd_header" nowrap="nowrap">��������(��)<font color="red">*</font></td>
      <td class="wordtd_header" nowrap="nowrap">��ͬ��Ч��(��)<font color="red">*</font></td>
      <td class="wordtd_header" nowrap="nowrap">����(Ԫ)</td>
      <%--td class="wordtd_header" nowrap="nowrap">��׼����(Ԫ)</td--%>
      <td class="wordtd_header" nowrap="nowrap">���ձ���(Ԫ)</td>
      <%--td class="wordtd_header" nowrap="nowrap">��׼���ۼ�������</td--%>
    </tr>
  </thead>
  <tfoot>
    <tr height="23"><td colspan="19"></td></tr>
  </tfoot>
  <tbody>
    <tr id="sampleRow_0" style="display:none">
      <td align="center"><input type="checkbox" onclick="cancelCheckAll('dynamictable_0','cbAll')"/></td>
      <td align="center">
      	<input type="hidden" name="signWay"/>
      	<input type="text" name="signWayName" size="10" readonly="readonly" class="noborder_center"/>
      </td>
      <td align="center"><input type="text" name="elevatorNo" size="14" readonly="readonly" class="noborder_center"/></td>
      <td align="center"><input type="text" name="elevatorType" size="10" readonly="readonly" class="noborder_center"/></td>
      <td align="center"><input type="text" name="floor" size="3" readonly="readonly" class="noborder_center"/></td>
      <td align="center"><input type="text" name="stage" size="3" readonly="readonly" class="noborder_center"/></td>
      <td align="center"><input type="text" name="door" size="3" readonly="readonly" class="noborder_center"/></td>
      <td align="center"><input type="text" name="high" size="3" readonly="readonly" class="noborder_center"/></td>
      <td align="center"><input type="text" name="weight" size="10" readonly="readonly" class="noborder_center"/></td>
      <td align="center"><input type="text" name="speed" size="10" readonly="readonly" class="noborder_center"/></td>
      <td align="center"><input type="text" name="elevatorParam" size="20" readonly="readonly" class="noborder_center"/></td>
      <td align="center"><input type="text" name="salesContractNo" size="12" readonly="readonly" class="noborder_center"/></td>
      <td align="center"><input type="text" name="projectName" size="30" readonly="readonly" class="noborder_center"/></td>
      <td align="center"><input type="text" name="projectAddress" size="38" readonly="readonly" class="noborder_center"/></td>
      <td align="center"><input type="text" name="elevatorAge" size="8" onpropertychange="checkthisvalue(this);setOhterVal(this);"/></td>
      <td align="center"><input type="text" name="contractPeriod" onpropertychange="checkthisvalue(this);setOhterVal(this);" size="8"/></td>
      <td align="center"><input type="text" name="jyMoney" size="8" onpropertychange="checkthisvalue(this);setOhterVal(this);"/></td>
      <%--td align="center"><input type="text" name="standardQuote" size="8" readonly="readonly" class="noborder_center"/></td--%>
      <td align="center">
      		<input type="text" name="finallyQuote" onpropertychange="checkthisvalue(this);setOhterVal(this);jsr8();" size="8"/>
      		<input type="hidden" name="standardQuote" />
      		<input type="hidden" name="standardQuoteDis"/>
      </td>
      <%--td align="center"><textarea name="standardQuoteDis" rows="3" cols="100" readonly="readonly" class="noborder_center">&nbsp;</textarea></td--%>
    </tr>
    <logic:present name="maintContractQuoteDetailList">
      <logic:iterate id="element" name="maintContractQuoteDetailList" >
        <tr>
          <td align="center"><input type="checkbox" onclick="cancelCheckAll(this)"/></td>
          <td align="center">
          	${element.r4}
          	<input type="hidden" name="signWay" value="${element.signWay}"/>
          </td>
          <td align="center"><input type="text" name="elevatorNo" size="14" value="${element.elevatorNo}" readonly="readonly" class="noborder_center"/></td>
          <td align="center"><input type="text" name="elevatorType" size="10" value="${element.elevatorType}" readonly="readonly" class="noborder_center"/></td>
          <td align="center"><input type="text" name="floor" size="3" value="${element.floor}" readonly="readonly" class="noborder_center"/></td>
          <td align="center"><input type="text" name="stage" size="3" value="${element.stage}" readonly="readonly" class="noborder_center"/></td>
          <td align="center"><input type="text" name="door" size="3" value="${element.door}" readonly="readonly" class="noborder_center"/></td>
          <td align="center"><input type="text" name="high" size="3" value="${element.high}" readonly="readonly" class="noborder_center"/></td>
     	  <td align="center"><input type="text" name="weight" size="10" value="${element.weight}" readonly="readonly" class="noborder_center"/></td>
      	  <td align="center"><input type="text" name="speed" size="10" value="${element.speed}" readonly="readonly" class="noborder_center"/></td>
          <td align="center"><input type="text" name="elevatorParam" size="20" value="${element.elevatorParam}"  readonly="readonly" class="noborder_center"/></td>
      	  <td align="center"><input type="text" name="salesContractNo" size="12" value="${element.salesContractNo}" readonly="readonly" class="noborder_center"/></td>
          <td align="center"><input type="text" name="projectName" size="30" value="${element.projectName}" readonly="readonly" class="noborder_center"/></td>
          <td align="center"><input type="text" name="projectAddress" size="38" value="${element.projectAddress}" readonly="readonly" class="noborder_center"/></td>          
          <td align="center"><input type="text" name="elevatorAge" value="${element.elevatorAge}" onkeypress="f_check_number2();" size="8" onpropertychange="setOhterVal(this);"/></td>
      	  <td align="center"><input type="text" name="contractPeriod" value="${element.contractPeriod}" onpropertychange="checkthisvalue(this);setOhterVal(this);" size="8"/></td>
      	  <td align="center"><input type="text" name="jyMoney" value="${element.jyMoney}" size="8" onpropertychange="checkthisvalue(this);setOhterVal(this);"/></td>
      	  <%--td align="center"><input type="text" name="standardQuote" value="${element.standardQuote}"  readonly="readonly" class="noborder_center" size="8"/></td--%>
          <td align="center">
          	<input type="text" name="finallyQuote" value="${element.finallyQuote}" onpropertychange="checkthisvalue(this);jsr8();" size="8"/>
          	<input type="hidden" name="standardQuote" value="${element.standardQuote}"/>
          	<input type="hidden" name="standardQuoteDis" value="${element.standardQuoteDis}"/>
          </td>
          <%--td align="center"><textarea name="standardQuoteDis" rows="3" cols="100" readonly="readonly" class="noborder_center">${element.standardQuoteDis}</textarea></td--%>
    </tr>
      </logic:iterate>
    </logic:present>
  </tbody>    
</table>
</div>
<br>

<table id="table2" width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
         
  <tr>
    <td class="wordtd">
    	<bean:message key="maintContractQuote.paymentMethodApply"/>:
    </td>
    <td width="28%">
		<html:select name="maintContractQuoteBean" property="paymentMethodApply" styleId="paymentMethodApply" onchange="isShowHidden(this)" >
			<html:option value="">��ѡ��</html:option>
		 	<html:options collection="PaymentMethodList" property="id.pullid" labelProperty="pullname"/>
		</html:select>
		<font color="red">*</font>
    </td>
    <td class="wordtd">˰��ҵ��Ѻϼƣ�Ԫ��:</td>
    <td width="20%" colspan="3">
    <html:text name="maintContractQuoteBean" property="businessCosts" size="15" styleId="businessCosts" onchange="checkthisvalue(this);jssdqtotal(this.value);"/>
    <font color="red">(������˰ǰ���˴��뻻�����д=˰ǰ����0.7)</font>
    </td>
    </tr>
    <tr style="display:none;" id="trok">
    <td class="wordtd">
    	���ʽ���뱸ע:
    </td>
    <td colspan="5">
    	<html:textarea name="maintContractQuoteBean" styleId="paymentMethodRem" property="paymentMethodRem" rows="2" cols="80" styleClass="default_textarea"/><font color="red">*</font>
    </td>
    </tr>
    <tr>
    <td class="wordtd">
    	��ͬ�������������:
    </td>
    <td colspan="5">
		<table id="searchCompany" style="border: 0;margin: 0;" class="tb">
       		<tr>
             <%int specialno=1; %>
       		<logic:present name="ContractContentList">
				  <logic:iterate id="element" name="ContractContentList">
						<td nowrap="nowrap"  style="border: none;" width="5%">
						&nbsp;
                         <input type="checkbox" name="contractContentApply" value="${element.id.pullid}" onclick="isShowHidden2();">${element.pullname }
                         <input type="hidden" name="contractContentApplyName" value="${element.pullname }"/>
                         </td>
					
						<% if(specialno%5==0){ %></tr><tr><%} %>
						<%specialno++; %>
				</logic:iterate>
				</tr>
			</logic:present>
       	</table>
    </td>                               
  </tr>
   <tr style="display:none;" id="trok2">
    <td class="wordtd">
    	��ͬ�������������ע:
    </td>
    <td colspan="5">
    	<html:textarea name="maintContractQuoteBean" styleId="contractContentRem" property="contractContentRem" rows="2" cols="80" styleClass="default_textarea"/><font color="red">*</font>
    </td>
    </tr>
  <tr>
    <td class="wordtd">���ձ��ۺϼƣ�Ԫ��:</td>
    <td width="28%">
      <html:text name="maintContractQuoteBean" property="finallyQuoteTotal" styleId="finallyQuoteTotal" readonly="true" styleClass="default_input_noborder"/>
    </td>
    <td class="wordtd"><%--��׼���ۺϼƣ�Ԫ��:--%>&nbsp;&nbsp;</td>
    <td width="20%">
    	&nbsp;&nbsp;
    	<html:hidden name="maintContractQuoteBean" property="standardQuoteTotal" styleId="standardQuoteTotal"/>
      <%--html:text name="maintContractQuoteBean" property="standardQuoteTotal" styleId="standardQuoteTotal" readonly="true" styleClass="default_input_noborder"/--%>
    </td>
    <td class="wordtd"><%-- �ۿ��ʣ�%��:--%>&nbsp;&nbsp;</td>
    <td width="20%">
    	&nbsp;&nbsp;
    	<html:hidden name="maintContractQuoteBean" property="discountRate" styleId="discountRate"/>
      <%-- html:text name="maintContractQuoteBean" property="discountRate" styleId="discountRate"  readonly="true" styleClass="default_input_noborder"/--%>
    </td>
  </tr>
</table>
<br>

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
  <tr>
    <td class="wordtd"><bean:message key="maintContractQuote.priceFluctuaApply"/>:</td>
    <td ><html:textarea name="maintContractQuoteBean" property="priceFluctuaApply" rows="3" cols="100" styleClass="default_textarea"/></td>
  </tr>      
  <tr>
    <td class="wordtd"><bean:message key="maintContractQuote.businessCostsApply"/>:</td>
    <td><html:textarea name="maintContractQuoteBean" property="businessCostsApply" rows="3" cols="100" styleClass="default_textarea"/></td>
  </tr>      
  <tr>
    <td class="wordtd">�������˵��:</td>
    <td><html:textarea name="maintContractQuoteBean" property="specialApplication" rows="3" cols="100" styleClass="default_textarea"/></td>
  </tr> 
</table>

<!-- ���ϴ��ĸ��� -->
<%@ include file="UpLoadedFile.jsp" %>
<!-- �ϴ��ĸ��� -->
<%@ include file="UpLoadFile.jsp" %>

<script type="text/javascript">
	window.onload = function(){	 	
		setDynamicTable("dynamictable_0","sampleRow_0");
		setScrollTable("wrap_0","dynamictable_0",10);
		
		for(var n = 0; n < 3; n++){
			$(".col_"+n).each(function(i,obj){			
				var width = $(".col_"+n).get(0).offsetWidth;
				$(obj).hide();	
				$(obj).width(width);
				$(obj).show();
			})
		}
	}
	
	
	function initapply(){
		//��ʼ���Ƿ���ʾ���ʽ��ע
		var paymentMethodApply=document.getElementById("paymentMethodApply");
		isShowHidden(paymentMethodApply);
		//��ʼ����ѡ��ͬ������������
		var ccastr='<bean:write name="maintContractQuoteBean" property="contractContentApply"/>';
		var ccarr=document.getElementsByName("contractContentApply");
	    for(var i=0;i<ccarr.length;i++){
	    	if(ccastr.indexOf(ccarr[i].value)>=0){
	    		ccarr[i].checked=true;
	    	}
	    }
		//��ʼ���Ƿ���ʾ��ͬ�������������ע
		isShowHidden2();
	}
	initapply();

</script>




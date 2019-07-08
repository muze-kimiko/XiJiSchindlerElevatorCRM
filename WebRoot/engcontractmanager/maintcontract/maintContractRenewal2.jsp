<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>

<script language="javascript" src="<html:rewrite page="/common/javascript/jquery-1.9.1.min.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/openwindow.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/checkinput.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/dynamictable.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/math.js"/>"></script>

<link rel="stylesheet" type="text/css" href="<html:rewrite forward='formCSS'/>">
<br>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <title>XJSCRM</title>
</head>
<body>
  <html:errors/>
 <html:form action="/maintContractAction.do?method=toRenewalRecord2" enctype="multipart/form-data">
    <html:hidden property="isreturn"/>
<html:hidden property="id"/>
<html:hidden name="maintContractQuoteBean" property="billNo"/>
<html:hidden property="maintContractDetails" styleId="maintContractDetails"/>
<html:hidden name="maintContractQuoteBean" property="historyBillNo" styleId="historyBillNo"/>
<html:hidden name="maintContractQuoteBean" property="histContractNo" styleId="histContractNo"/>
<html:hidden name="maintContractQuoteBean" property="histContractStatus" styleId="histContractStatus"/>
<html:hidden name="maintContractQuoteBean" property="xqType" styleId="xqType"/>

<div class="title-bar">
  &nbsp;<b>����Ϣ</b>
</div>
<table id="table1" width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
  <tr>
    <td nowrap="nowrap" class="wordtd_a"><bean:message key="maintContractQuote.attn"/>:</td>
    <td nowrap="nowrap" class="col_1">
      <bean:write name="attnName"/>
      <html:hidden name="maintContractQuoteBean" property="attn"/>
    </td>
    <td nowrap="nowrap" class="wordtd_a"><bean:message key="maintContractQuote.applyDate"/>:</td>
    <td nowrap="nowrap" class="col_2">
      <bean:write name="maintContractQuoteBean" property="applyDate"/>
      <html:hidden name="maintContractQuoteBean" property="applyDate"/>
    </td>
    <td nowrap="nowrap" class="wordtd_a"><bean:message key="maintContractQuote.maintDivision"/>:</td>
    <td nowrap="nowrap" class="col_3">
      <bean:write name="maintDivisionName"/>
      <html:hidden name="maintContractQuoteBean" property="maintDivision"/>
    </td>           
  </tr>   
  <tr>
    <td nowrap="nowrap" class="wordtd_a">�׷���λ����:</td>
    <td nowrap="nowrap" nowrap="nowrap">
      <html:text name="maintContractQuoteBean" property="companyName" styleId="companyName" size="35"/><font color="red">*</font>
     </td>
    <td nowrap="nowrap" class="wordtd_a">�׷���ϵ��:</td>
    <td>
    <html:text name="maintContractQuoteBean" property="contacts"/>
    </td>
    <td nowrap="nowrap" class="wordtd_a">�׷���ϵ�绰:</td>
    <td>
    <html:text name="maintContractQuoteBean" property="contactPhone"/>
    </td>          
  </tr>
  <tr>
    <td nowrap="nowrap" class="wordtd_a"><bean:message key="maintContractQuote.maintStation"/>:</td>
    <td nowrap="nowrap">
      ${maintStationName}
      <html:hidden name="maintContractQuoteBean" property="maintStation"/>
      <%-- <html:select name="maintContractQuoteBean" property="maintStation">
        <html:options collection="maintStationList" property="storageid" labelProperty="storagename"/><font color="red">*</font>
      </html:select> --%>
    </td>
    <%-- <td nowrap="nowrap" class="wordtd_a">�ͻ�����:</td>
    <td nowrap="nowrap"><html:text name="maintContractQuoteBean" property="customerArea" styleId="customerArea" styleClass="default_input"/></td>   --%>
   	<td nowrap="nowrap" class="wordtd">����ǩ��ʽ:</td>
    <td>
    	<logic:equal name="maintContractQuoteBean" property="quoteSignWay" value="ZB">��ǩ</logic:equal>
    	<logic:equal name="maintContractQuoteBean" property="quoteSignWay" value="XB">��ǩ</logic:equal>
    	<logic:equal name="maintContractQuoteBean" property="quoteSignWay" value="BFXB">������ǩ</logic:equal>
		<html:hidden name="maintContractQuoteBean" property="quoteSignWay"/>
	</td> 
    <td nowrap="nowrap" class="wordtd_a">&nbsp;</td>
    <td nowrap="nowrap">&nbsp;</td> 
  </tr>         
</table>
<br>

<div id="caption_0" style="width: 100%;padding-top: 2;padding-bottom: 2;border-bottom: 0" class="tb">
   	<logic:equal name="maintContractQuoteBean" property="xqType" value="ALL">
  		&nbsp;<input type="button" value="ѡ��������ͬ" onclick="addMaintContracts('dynamictable_0')" class="default_input">
  	</logic:equal>
  <input type="button" value=" + " onclick="addElevators('dynamictable_0')" class="default_input">
  <input type="button" value=" - " onclick="deleteRow('dynamictable_0');sumValuesByName('standardQuote','standardQuoteTotal');jsr8();" class="default_input">           
  <b>&nbsp;������Ϣ</b>
  <%-- input type="button" value="�����׼����" onclick="jsQuote()" class="default_input"--%>  
  <b><font color="red">��ע�⣺����=��ִͬ���ڼ䣬Ҫ֧���������ܶ��</font></b> 
</div>
<div id="wrap_0" style="overflow-x:scroll">
<table id="dynamictable_0" class="dynamictable tb" width="1500" border="0" cellpadding="0" cellspacing="0">
  <thead>
    <tr id="titleRow_0">
      <td class="wordtd_header"><input type="checkbox" id="cbAll" onclick="checkTableAll('dynamictable_0',this)"/></td>
      <td class="wordtd_header" nowrap="nowrap">ǩ��ʽ</td>
      <td class="wordtd_header" nowrap="nowrap">���ݱ��</td>
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
      <%-- td class="wordtd_header" nowrap="nowrap">��׼����(Ԫ)</td--%>
      <td class="wordtd_header" nowrap="nowrap">���ձ���(Ԫ)</td>
      <%-- td class="wordtd_header" nowrap="nowrap">��׼���ۼ�������</td--%>
    </tr>
  </thead>
  <tfoot>
    <tr height="23"><td colspan="15"></td></tr>
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
          <td align="center"><input type="text" name="floor" size="3" value="${element.floor}" size="3" readonly="readonly" class="noborder_center"/></td>
          <td align="center"><input type="text" name="stage" size="3" value="${element.stage}" size="3" readonly="readonly" class="noborder_center"/></td>
          <td align="center"><input type="text" name="door" size="3" value="${element.door}" size="3" readonly="readonly" class="noborder_center"/></td>
          <td align="center"><input type="text" name="high" size="3" value="${element.high}" size="3" readonly="readonly" class="noborder_center"/></td>
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
          		<input type="text" name="finallyQuote" value="${element.finallyQuote}" onpropertychange="checkthisvalue(this);setOhterVal(this);jsr8();" size="8"/>
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
    <td class="wordtd">ҵ��Ѻϼƣ�Ԫ��:</td>
    <td  width="20%" colspan="3"><html:text name="maintContractQuoteBean" property="businessCosts" styleId="businessCosts" onchange="checkthisvalue(this);jssdqtotal(this.value);"/>
    &nbsp;<font color="red">(������˰ǰ���˴��뻻�����д=˰ǰ����0.7)</font>
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
    <td class="wordtd_a" nowrap="nowrap">���ձ��ۺϼƣ�Ԫ��:</td>
    <td width="28%">
      <html:text name="maintContractQuoteBean" property="finallyQuoteTotal" styleId="finallyQuoteTotal" readonly="true" styleClass="default_input_noborder"/>
    </td>
    <td class="wordtd_a" nowrap="nowrap"><%--��׼���ۺϼƣ�Ԫ��:--%>&nbsp;&nbsp;</td>
    <td width="20%">
    	&nbsp;&nbsp;
    	<html:hidden name="maintContractQuoteBean" property="standardQuoteTotal" styleId="standardQuoteTotal"/>
      <%--html:text name="maintContractQuoteBean" property="standardQuoteTotal" styleId="standardQuoteTotal" readonly="true" styleClass="default_input_noborder"/--%>
    </td>
    <td class="wordtd_a" nowrap="nowrap"><%--�ۿ��ʣ�%��:--%>&nbsp;&nbsp;</td>
    <td width="20%">
    	&nbsp;&nbsp;
    	<html:hidden name="maintContractQuoteBean" property="discountRate" styleId="discountRate"/>
      <%--html:text name="maintContractQuoteBean" property="discountRate" styleId="discountRate"  readonly="true" styleClass="default_input_noborder"/--%>
    </td>
  </tr>
</table>
<br>

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
  <tr>
    <td class="wordtd_a" width="100"><bean:message key="maintContractQuote.priceFluctuaApply"/>:</td>
    <td><html:textarea name="maintContractQuoteBean" property="priceFluctuaApply" rows="3" cols="100" styleClass="default_textarea"/></td>
  </tr>      
  <tr>
    <td class="wordtd_a"><bean:message key="maintContractQuote.businessCostsApply"/>:</td>
    <td><html:textarea name="maintContractQuoteBean" property="businessCostsApply" rows="3" cols="100" styleClass="default_textarea"/></td>
  </tr>    
  <tr>
    <td class="wordtd">�������˵��:</td>
    <td><html:textarea name="maintContractQuoteBean" property="specialApplication" rows="3" cols="100" styleClass="default_textarea"/></td>
  </tr> 
</table>

<!-- �ϴ��ĸ��� -->
<%@ include file="/engcontractmanager/maintcontractquote/UpLoadFile.jsp" %> 

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
  </html:form>
</body>




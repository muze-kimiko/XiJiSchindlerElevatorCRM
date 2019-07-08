<%@ page contentType="text/html;charset=GBK" %>
<logic:present name="maintContractQuoteBean">
<script language="javascript" src="<html:rewrite page="/common/javascript/dynamictable.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/jquery-1.9.1.min.js"/>"></script>

<html:hidden name="maintContractQuoteBean" property="billNo"/>
  <div class="title-bar">
    &nbsp;<b>����Ϣ</b>
  </div>
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
    <tr>
      <td nowrap="nowrap" class="wordtd"><bean:message key="maintContractQuote.attn"/>:</td>
      <td width="20%">
        <bean:write name="attnName"/>
      </td>
      <td nowrap="nowrap" class="wordtd"><bean:message key="maintContractQuote.applyDate"/>:</td>
      <td width="20%">
        <bean:write name="maintContractQuoteBean" property="applyDate"/>
      </td>
      <td class="wordtd"><bean:message key="maintContractQuote.maintDivision"/>:</td>
      <td width="20%">
        <bean:write name="maintDivisionName"/>
      </td>          
    </tr>   
    <tr>
      <td class="wordtd">�׷���λ����:</td>
      <td>${maintContractQuoteBean.companyName}</td>
      <td class="wordtd">�׷���ϵ��:</td>
      <td>${maintContractQuoteBean.contacts}</td>
      <td class="wordtd">�׷���ϵ�绰:</td>
      <td>${maintContractQuoteBean.contactPhone}</td>            
    </tr>  
    <tr>
      <td class="wordtd"><bean:message key="maintContractQuote.maintStation"/>:</td>
      <td>
         <bean:write name="maintStationName"/>
      </td>  
     <%--  <td nowrap="nowrap" class="wordtd">�ͻ�����:</td>
      <td>
      	${maintContractQuoteBean.customerArea }
      </td>  --%>
      <td nowrap="nowrap" class="wordtd">����ǩ��ʽ:</td>
	    <td>
	    	<logic:equal name="maintContractQuoteBean" property="quoteSignWay" value="ZB">��ǩ</logic:equal>
	    	<logic:equal name="maintContractQuoteBean" property="quoteSignWay" value="XB">��ǩ</logic:equal>
	    	<logic:equal name="maintContractQuoteBean" property="quoteSignWay" value="BFXB">������ǩ</logic:equal>
		</td>  
      <td nowrap="nowrap" class="wordtd">&nbsp;</td>
      <td>&nbsp;</td> 
    </tr>       
  </table>
  <br>
  
  <div id="caption" style="width: 100%;padding-top: 2;padding-bottom: 2;border-bottom: 0" class="tb">        
    <b>&nbsp;������Ϣ</b>
    <b><font color="red">��ע�⣺����=��ִͬ���ڼ䣬Ҫ֧���������ܶ��</font></b>
  </div>
  <div id="wrap" style="overflow-x:scroll">
  <table id="dynamictable" width="1500" border="0" cellpadding="0" cellspacing="0" class="tb">
    <thead>
      <tr id="tb_head">
      	<td class="wordtd_header" nowrap="nowrap">ǩ��ʽ</td>
        <td class="wordtd_header" nowrap="nowrap">���ݱ��</td>
        <td class="wordtd_header" nowrap="nowrap">��������</td>
        <td class="wordtd_header">��</td>
        <td class="wordtd_header">վ</td>
        <td class="wordtd_header">��</td>
        <td class="wordtd_header" nowrap="nowrap">�����߶�</td>
      <td class="wordtd_header" nowrap="nowrap">����</td>
      <td class="wordtd_header" nowrap="nowrap">�ٶ�</td>
      	<td class="wordtd_header" nowrap="nowrap">����ͺ�</td>
        <td class="wordtd_header" nowrap="nowrap">���ۺ�ͬ��</td>
        <td class="wordtd_header" nowrap="nowrap">��Ŀ����</td>
        <td class="wordtd_header" nowrap="nowrap">��Ŀ��ַ</td>
      <td class="wordtd_header" nowrap="nowrap">��������(��)</td>
      <td class="wordtd_header" nowrap="nowrap">��ͬ��Ч��(��)</td>
      <td class="wordtd_header" nowrap="nowrap">����(Ԫ)</td>
        <td class="wordtd_header" nowrap="nowrap">��׼����(Ԫ)</td>
        <td class="wordtd_header" nowrap="nowrap">���ձ���(Ԫ)</td>
      <%-- td class="wordtd_header" nowrap="nowrap">��׼���ۼ�������</td--%>
      </tr>
    </thead>
    <tfoot>
      <tr height="18"><td colspan="14"></td></tr>
    </tfoot>
    <tbody>
      <logic:present name="maintContractQuoteDetailList">
        <logic:iterate id="element" name="maintContractQuoteDetailList" >
          <tr>
          	<td align="center" nowrap="nowrap">${element.r4}</td>
            <td align="center" nowrap="nowrap">${element.elevatorNo}</td>
            <td align="center" nowrap="nowrap">${element.elevatorType}</td>
            <td align="center">${element.floor}</td>
            <td align="center">${element.stage}</td>
            <td align="center">${element.door}</td>
            <td align="center">${element.high}</td>
            <td align="center">${element.weight}</td>
            <td align="center">${element.speed}</td>
            <td align="center">${element.elevatorParam}</td>
            <td align="center">${element.salesContractNo}</td>
            <td align="center">${element.projectName}</td>
            <td align="center">${element.projectAddress}</td>
            <td align="center">${element.elevatorAge}</td>
            <td align="center">${element.contractPeriod}</td>
            <td align="center">${element.jyMoney}</td>
            <td align="center">${element.standardQuote}</td>
            <td align="center">${element.finallyQuote}</td>
            <%--td align="center">${element.standardQuoteDis}</td--%>
          </tr>
        </logic:iterate>
      </logic:present>
    </tbody>    
  </table>   
  </div> 
  <br>
  
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
      <tr>
    <td class="wordtd">
    	<bean:message key="maintContractQuote.paymentMethodApply"/>:
    </td>
    <td width="20%">
        <bean:write name="maintContractQuoteBean" property="r4"/>
        <input type="hidden" name="paymentMethodApply" id="paymentMethodApply" value="<bean:write name="maintContractQuoteBean" property="paymentMethodApply"/>"/>
    </td>
    <td class="wordtd">˰��ҵ��Ѻϼƣ�Ԫ��:</td>
    <td width="20%" colspan="3"><bean:write name="maintContractQuoteBean" property="businessCosts"/>
    &nbsp;<font color="red">(������˰ǰ���˴��뻻�����д=˰ǰ����0.7)</font>
	</td>
    </tr>
    <logic:notEqual name="maintContractQuoteBean" property="paymentMethodRem" value="">
    	<tr>
	    <td class="wordtd">
	    	���ʽ���뱸ע:
	    </td>
	    <td colspan="5">
	    	<bean:write name="maintContractQuoteBean" property="paymentMethodRem"/>
	    </td>
	    </tr>
    </logic:notEqual>
    <tr>
    <td class="wordtd">
    	��ͬ�������������:
    </td>
    <td colspan="5">
    	<bean:write name="maintContractQuoteBean" property="contractContentApply"/>
    </td>
    </tr>
    <logic:notEqual name="maintContractQuoteBean" property="contractContentRem" value="">
	    <tr>
	    <td class="wordtd">
	    	��ͬ�������������ע:
	    </td>
	    <td colspan="5">
	    	<bean:write  name="maintContractQuoteBean" property="contractContentRem"/>
	    </td>
	    </tr>
     </logic:notEqual>
    <tr>
      <td class="wordtd">���ձ��ۺϼƣ�Ԫ��:</td>
      <td width="20%">
        <bean:write name="maintContractQuoteBean" property="finallyQuoteTotal"/>
      </td>
      <td class="wordtd">��׼���ۺϼƣ�Ԫ��:</td>
      <td width="20%">
        <bean:write name="maintContractQuoteBean" property="standardQuoteTotal"/>
      </td>
      <td class="wordtd">�ۿ��ʣ�%��:</td>
      <td width="20%"><bean:write name="maintContractQuoteBean" property="discountRate"/></td>
    </tr>
  </table>
  <br>
  
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
    <tr>
      <td class="wordtd"><bean:message key="maintContractQuote.priceFluctuaApply"/>:</td>
      <td><bean:write name="maintContractQuoteBean" property="priceFluctuaApply"/></td>
    </tr>      
    <tr>
      <td class="wordtd"><bean:message key="maintContractQuote.businessCostsApply"/>:</td>
      <td><bean:write name="maintContractQuoteBean" property="businessCostsApply"/></td>
    </tr>
    <tr>
      <td class="wordtd">�������˵��:</td>
      <td><bean:write name="maintContractQuoteBean" property="specialApplication"/></td>      
    </tr>

  </table> 
  
  <!-- ���ϴ��ĸ��� -->
<%@ include file="UpLoadFileDisplay.jsp" %>
  
<%@ include file="/workflow/processApproveMessage.jsp" %>
  
  <script type="text/javascript">
	window.onload = function(){	 	
		setScrollTable("wrap","dynamictable",10);
		
		for(var n = 0; n < 3; n++){
			$(".col_"+n).each(function(i,obj){			
				var width = $(".col_"+n).get(0).offsetWidth;
				$(obj).hide();	
				$(obj).width(width);
				$(obj).show();
			})
		}
	}
	
  </script>
</logic:present>
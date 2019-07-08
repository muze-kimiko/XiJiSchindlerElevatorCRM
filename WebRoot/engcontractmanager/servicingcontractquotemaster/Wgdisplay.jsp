<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<script language="javascript" defer="defer" src="<html:rewrite forward='DatePickerJS'/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/openwindow.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/dynamictable.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/checkinput.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/math.js"/>"></script>
<script language="javascript" src="<html:rewrite forward="jq.js"/>"></script>
    <html:hidden property="isreturn"/>
    <html:hidden property="id"/>
    <html:hidden name="ServicingContractQuoteMaster" property="billNo"/>

    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
      <tr>
        <td height="23" colspan="6">&nbsp;<b>����Ϣ</td>
      </tr>
      
      <tr>
        <td nowrap="nowrap" class="wordtd"><bean:message key="maintContractQuote.attn"/>:</td>
        <td width="20%">
          <bean:write name="ServicingContractQuoteMaster" property="attn"/>
        </td>
        <td nowrap="nowrap" class="wordtd"><bean:message key="maintContractQuote.applyDate"/>:</td>
        <td width="20%">
          <bean:write name="ServicingContractQuoteMaster" property="applyDate"/>
       </td>
        <td class="wordtd"><bean:message key="maintContractQuote.maintDivision"/>:</td>
        <td width="20%">
          <bean:write name="ServicingContractQuoteMaster" property="maintDivision"/>
        </td>             
      </tr>    
      <tr>
        <td class="wordtd">�׷���λ����:</td>
        <td>
        ${ServicingContractQuoteMaster.companyId} 
          </td>
        <td class="wordtd">�׷���ϵ��:</td>
        <td>${contacts }</td>
        <td class="wordtd">�׷���ϵ�绰:</td>
        <td >${contactPhone}</td>           
      </tr> 
      
      <tr>
      <td nowrap="nowrap" class="wordtd">ҵ�����:</td>
      <td>
     ${ServicingContractQuoteMaster.busType }
      </td>
      <td nowrap="nowrap" class="wordtd">����ά��վ:</td>
      <td>   
         <bean:write name="maintStationName"/>    
      </td>
      </tr>
      <tr>
    <td class="wordtd">�����ͻ�����Ҫ��:</td>
    <td colspan="5">${ServicingContractQuoteMaster.otherCustomer }</td>
      </tr>             
    </table>
    <br>
    
    <div id="scrollBox" style="overflow:scroll; overflow-y:hidden">
      <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb" id="table_22">
        <thead>
          <tr height="23">
            <td colspan="4">            
              <b>&nbsp;������Ϣ</b>
            </td>
          </tr>
          <tr id="tb_head" class="tb_head">
           <td class="wordtd_header" width="7%">ά���ݱ��<font color="red">*</font></td>
           <td class="wordtd_header" width="10%">���ۺ�ͬ��<font color="red">*</font></td>
            <td class="wordtd_header" width="23%">��Ŀ����<font color="red">*</font></td>
            <td class="wordtd_header" width="50%">ά������<font color="red">*</font></td>
          </tr>
        </thead>
        <tfoot>
          <tr height="23"><td colspan="4"></td></tr>
        </tfoot>
        <tbody>
          <logic:present name="scqdList">
            <logic:iterate id="element" name="scqdList" >
              <tr>
                <td align="center">${element.elevatorNo}</td>
                <td align="center">${element.salesContractNo}</td>
                <td align="center">${element.projectName}</td>
                <td align="center">${element.contents}</textarea></td>
                </tr>
            </logic:iterate>
          </logic:present>
        </tbody>    
      </table>
    </div>
  
    <br>
    <table id="uploadtab_1" width="100%">
  <tr>
  <td>
    <!-- ���ϴ��ĸ��� -->
    <%@ include file="UpLoadedFile.jsp" %>
  </td>
  </tr>
  </table>
    <br>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb" id="table_1">
    <thead>
    <tr height="23">
      <td colspan="8"><b>&nbsp;������ϸ</b></td>
    </tr>
    <tr class="wordtd_header" id="tb_head">
    <td width="5%">��� </td>
    <td width="35%">��������<font color="red">*</font></td>
    <td width="15%">���<font color="red">*</font></td>
    <td width="5%">����<font color="red">*</font></td>
    <td width="5%">��λ<font color="red">*</font></td>
    <td width="10%">����(Ԫ)<font color="red">*</font></td>
    <td width="10%">�۸�(Ԫ)<font color="red">*</font></td> 
    <td width="10%">���ռ۸�(Ԫ)<font color="red">*</font></td>   
    </tr>
    </thead>
    <tfoot>
        <tr height="23"><td colspan="8"></td></tr>
     </tfoot>
    <tbody>
    <% int i=1; %>
    <logic:present name="scqmdList">
   <logic:iterate name="scqmdList" id="element">
    <tr id="tr1" >
    <td align="center"><%= i %></td>
    <td align="center">${element.materialName}</td>
    <td align="center">${element.materialsStandard}</td>
    <td align="center">${element.quantity}</td>   
    <td align="center">${element.unit}</td>
    <td align="center">${element.unitPrice}</td>
    <td align="center">${element.price}</td>
    <td align="center">${element.finalPrice}</td>
    <%i++; %>
    </tr>
    </logic:iterate>
    </logic:present>
    </tbody>
    </table >
    <br>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
    <thead>
    <tr height="23" >
    <td colspan="4" >
    <b>&nbsp;����������ϸ</b>
    </td>
    </tr>
    <tr height="23" class="wordtd_header" id="tb_head">
    <td align="center">���</td>
    <td align="center">��������</td>
    <td align="center">�۸�(Ԫ)</td>
    <td align="center">���ռ۸�(Ԫ)</td>
    </tr>
    </thead>
    <% int j=1;%>
    <logic:present name="scqodList">
   <logic:iterate name="scqodList" id="element">
   <tr>
   <td align="center" width="10%"><%=j %></td>
   <td align="center" width="70%">${element.feeName }</td>
   <td align="center" width="10%">${element.price }</td>
   <td align="center" width="10%">${element.finalPrice }</td>  
   </tr>
   <%j++; %>
   </logic:iterate>
    </logic:present>
    </table>
    <br>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
      <tr>
        <td class="wordtd">��׼���ۺϼ�(Ԫ):</td>
        <td width="20%">
          ${ ServicingContractQuoteMaster.standardQuoteTotal}
        </td>
        <td class="wordtd">���ձ��ۺϼ�(Ԫ):</td>
        <td width="20%">
           ${ ServicingContractQuoteMaster.finallyQuoteTotal}
        </td>
        <td class="wordtd">ҵ���(Ԫ):</td>
        <td width="20%"> ${ ServicingContractQuoteMaster.businessCosts}</td>
      </tr>
    </table>
    <br>
    
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
      <tr>
        <td class="wordtd"><bean:message key="maintContractQuote.priceFluctuaApply"/>:</td>
        <td>${ ServicingContractQuoteMaster.priceFluctuaApply}</td>
      </tr>      
      <tr>
        <td class="wordtd"><bean:message key="maintContractQuote.businessCostsApply"/>:</td>
        <td>${ ServicingContractQuoteMaster.businessCostsApply}</td>
      </tr>        
      <tr>
        <td class="wordtd"><bean:message key="maintContractQuote.paymentMethodApply"/>:</td>
        <td>${ ServicingContractQuoteMaster.paymentMethodApply}</td>      
      </tr>
      <%--
      <tr>
        <td class="wordtd"><bean:message key="maintContractQuote.specialApplication"/>:</td>
        <td>${ ServicingContractQuoteMaster.specialApplication}</td>      
      </tr>
      --%>
      <tr>
        <td class="wordtd"><bean:message key="maintContractQuote.contractContentApply"/>:</td>
        <td>${ ServicingContractQuoteMaster.contractContentApply}</td>                                         
      </tr>
    </table>
  <script type="text/javascript">
    $("document").ready(function(){
      setScrollTable("scrollBox","table_22",10);
    }); 
  </script>
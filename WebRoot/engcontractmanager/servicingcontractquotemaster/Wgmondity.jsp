<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<script language="javascript" defer="defer" src="<html:rewrite forward='DatePickerJS'/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/dynamictable.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/openwindow.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/checkinput.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/math.js"/>"></script>
<script language="javascript" src="<html:rewrite forward="jq.js"/>"></script>
    <html:hidden property="isreturn"/>
    <html:hidden property="id"/>
    <html:hidden name="ServicingContractQuoteMaster" property="billNo" />

    <table width="100%" style="table-layout:fixed;" border="0" cellpadding="0" cellspacing="0" class="tb" >
      <tr>
        <td height="23" colspan="6">&nbsp;<b>����Ϣ</td>
      </tr>
      <tr>
        <td class="wordtd" width="10%"><bean:message key="maintContractQuote.attn"/>:</td>
        <td width="23%">
          <bean:write name="ServicingContractQuoteMaster" property="attn"/>
          <html:hidden name="ServicingContractQuoteMaster" property="attn"/>
        </td>
        <td class="wordtd" width="10%"><bean:message key="maintContractQuote.applyDate"/>:</td>
        <td width="23%">
          <bean:write name="ServicingContractQuoteMaster" property="applyDate"/>
          <html:hidden name="ServicingContractQuoteMaster" property="applyDate"/>
          <!--<html:text name="ServicingContractQuoteMaster" property="applyDate" styleClass="Wdate" onfocus="WdatePicker({isShowClear:true,readOnly:true})"/>-->
        </td>
        <td class="wordtd" width="10%"><bean:message key="maintContractQuote.maintDivision"/>:</td>
        <td width="23%">
          <bean:write name="ServicingContractQuoteMaster" property="maintDivision"/>
          <html:hidden name="ServicingContractQuoteMaster" property="maintDivision"/>
        </td>             
      </tr>    
      <tr>
        <td class="wordtd">�׷���λ����:</td>
        <td nowrap>
          <html:hidden name="ServicingContractQuoteMaster" property="companyId" />
          <input type="text" name="companyName" id="companyName" value="${companyName}" readonly="true" size="20" styleClass="Wdate"/>
          <input type="button" value=".." onclick="openWindowAndReturnValue('searchCustomerAction','')"/><font color="red">*</font>
        </td>
        <td class="wordtd">�׷���ϵ��:</td>
        <td><input type="text" name="companyContacts" id="contacts" value="${contacts}" readonly="true" class="default_input_noborder"/></td>
        <td class="wordtd">�׷���ϵ�绰:</td>
        <td><input type="text" name="companyPhone" id="contactPhone" value="${contactPhone}" readonly="true" class="default_input_noborder"/></td>           
      </tr> 
      <tr>
      <td class="wordtd">ҵ�����:</td>
      <td>
      <select name="busType" id="busType">
      <option value="W">ά��</option>
      <option value="G">����</option>
      </select>
      </td>
       <td class="wordtd">����ά��վ:</td>
        <td>
      ${maintStationName}
      <html:select name="ServicingContractQuoteMaster" property="maintStation">
       <html:options collection="maintStationList" property="storageid" labelProperty="storagename"/><font color="red">*</font>
      </html:select>
    </td>
      
      </tr>
      <tr>
      <td class="wordtd">�����ͻ�����Ҫ��:</td>
      <td colspan="5"><textarea name="otherCustomer" rows="2" cols="100"></textarea></td> 
      </tr>         
    </table>
    <br>
    <div >&nbsp;<input type="button" value=" + " onclick="addElevators('table_22')">
            <input type="button" value=" - " onclick="deleteRow('table_22')"><b>������Ϣ</b></div>
    <div id="scrollBox" style="overflow:scroll; overflow-y:hidden">   
      <table width="97%" border="0" cellpadding="0" cellspacing="0" class="tb" id="table_22">
        <thead>
          <tr id="tb_head" class="tb_head">
            <td class="wordtd_header" width="7%"><input type="checkbox" id="checkAll" onclick="checkTableAll('table_22',this)"/></td>
            <td class="wordtd_header" width="10%">ά���ݱ��<font color="red">*</font></td>
            <td class="wordtd_header" width="10%">���ۺ�ͬ��<font color="red">*</font></td>
            <td class="wordtd_header" width="23%">��Ŀ����<font color="red">*</font></td>
            <td class="wordtd_header" width="47%">ά������<font color="red">*</font></td>
          </tr>
        </thead>
        <tfoot>
          <tr height="23"><td colspan="5"></td></tr>
        </tfoot>
        <tbody>
          <tr id="modelrow_0">
           <td align="center"><input type="checkbox" onclick="cancelCheckAll('table_22','checkAll')"/></td>
           <td align="center"><input type="text" name="elevatorNo" id="elevatorNo" readonly="readonly" class="noborder_center"/></td>
           <td align="center"><input type="text" name="salesContractNo" id="salesContractNo" readonly="readonly" class="noborder_center"/></td>
           <td align="center"><input type="text" name="projectName" id="projectName" readonly="readonly" class="noborder_center"/></td>        
           <td align="center"><textarea name="contents" id="contents" rows="2" cols="100"></textarea></td>
          </tr>
          <logic:present name="scqdList">
            <logic:iterate id="element" name="scqdList" >
              <tr>
                <td align="center"><input type="checkbox" onclick="cancelCheckAll('table_22','checkAll')"/></td>
                <td align="center"><input type="text" name="elevatorNo" id="elevatorNo" value="${element.elevatorNo}" readonly="readonly" class="noborder_center"/></td>
                <td align="center"><input type="text" name="salesContractNo" id="salesContractNo" value="${element.salesContractNo}" readonly="readonly" class="noborder_center"/></td>
                <td align="center"><input type="text" name="projectName" id="projectName" value="${element.projectName}" readonly="readonly" class="noborder_center"/></td>
                <td align="center"><textarea name="contents" id="contents" rows="2" cols="100" >${element.contents}</textarea></td>
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
    <!-- �ϴ��ĸ��� -->
    <%@ include file="UpLoadFileDisplay.jsp" %>
  </td>
  </tr>
  <tr>
  <td>
    <!-- �ϴ��ĸ��� -->
    <%@ include file="UpLoadFile.jsp" %>
  </td>
  </tr>
  </table>
    <br>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb" id="table_1">
    <thead>
    <tr height="23">
    <td colspan="9">&nbsp;<input type="button" value=" + " onclick="addOneRow('table_1');fuz();" />
    <input type="button" value=" - " name="reduce" id="reduce" onclick="deleteRow('table_1');fuz();"/>
    <b>������ϸ</b></td></tr>
    <tr class="wordtd_header" id="tb_head">
    <td width="5%"><input type="checkbox" onclick="checkTableAll('table_1',this)"/></td>
    <td width="5%">��� </td>
    <td width="35%">��������<font color="red">*</font></td>
    <td width="15%">���<font color="red">*</font></td>
    <td width="5%">����<font color="red">*</font></td>
    <td width="5%">��λ<font color="red">*</font></td>
    <td width="10%">���ۣ�Ԫ��<font color="red">*</font></td>
    <td width="10%">�۸�Ԫ��<font color="red">*</font></td> 
    <td width="10%">���ռ۸�Ԫ��<font color="red">*</font></td>   
    </tr>
    </thead>
    <tfoot>
        <tr height="23"><td colspan="9"></td></tr>
     </tfoot>
    <tbody>
    <tr id="tr1" >
    <td align="center"><input type="checkbox" style="text-align:center" size="1"/></td>
    <td align="center"><input type="text" style="border: 0;text-align:center" id="xuhao" value="" width="5%" size="5"/></td>
    <td align="center"><input type="text" name="materialName" id="materialName" size="55"/></td>
    <td align="center"><input type="text"  name="materialsStandard" id="materialsStandard"/></td>
    <td align="center"><input type="text"  name="quantity" id="quantity" size="5" onblur="heji();"onpropertychange="checkthisvalue(this);"/></td>   
    <td align="center"><input type="text"  name="unit" id="unit" size="5"/></td>
    <td align="center"><input type="text"  name="unitPrice" id="unitPrice" size="15" onblur="heji();"onpropertychange="checkthisvalue(this);"/></td>
    <td align="center"><input type="text"  size="15" name="price2" id="price2" onpropertychange="checkthisvalue(this);sumValuesByName2(this.name,'price','standardQuoteTotal');"/></td>
    <td align="center"><input type="text"  size="15" name="finalPrice2" id="finalPrice2" onpropertychange="checkthisvalue(this);sumValuesByName2(this.name,'finalPrice','finallyQuoteTotal');"/></td>
    </tr>
    <logic:present name="scqmdList">
    
    <logic:iterate name="scqmdList" id="ele">
    <% int i=1;%>
     <tr>
    <td align="center"><input type="checkbox" style="text-align:center" size="1"/></td>
    <td align="center"><input type="text" style="border: 0;text-align:center" id="xuhao" value="" width="5%" size="5" value="<%=i %>"/></td>
    <td align="center"><input type="text" name="materialName" id="materialName" size="55" value="${ele.materialName }"/></td>
    <td align="center"><input type="text"  name="materialsStandard" id="materialsStandard" value="${ele.materialsStandard }"/></td>
    <td align="center"><input type="text"  name="quantity" id="quantity" size="5" onblur="heji();" value="${ele.quantity }" onpropertychange="checkthisvalue(this);"/></td>   
    <td align="center"><input type="text"  name="unit" id="unit" size="5" value="${ele.unit }"/></td>
    <td align="center"><input type="text"  name="unitPrice" id="unitPrice" size="15" onblur="heji();" value="${ele.unitPrice }" onpropertychange="checkthisvalue(this);"/></td>
    <td align="center"><input type="text"  size="15" name="price2" id="price2" onpropertychange="checkthisvalue(this);sumValuesByName(this.name,'standardQuoteTotal');" value="${ele.price }"/></td>
    <td align="center"><input type="text"  size="15" name="finalPrice2" id="finalPrice2" onpropertychange="checkthisvalue(this);sumValuesByName(this.name,'finallyQuoteTotal');" value="${ele.finalPrice }"/></td>
    </tr>
    <% i++;%>
    </logic:iterate>
    </logic:present>
    </tbody>
    </table >
    <br>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
    <thead>
    <tr height="23" >
    <td colspan="4" >
    <b>����������ϸ</b>
    </td>
    </tr>
    <tr height="23" class="wordtd_header" id="tb_head">
    <td align="center">���</td>
    <td align="center">��������</td>
    <td align="center">�۸�(Ԫ)</td>
    <td align="center">���ռ۸�(Ԫ)</td>
    </tr>
    </thead>
    <logic:present name="scqodList">
   <logic:iterate name="scqodList" id="element">
   <tr>
   <td align="center" width="10%">${element.r9 }</td>
   <td align="center" width="70%">${element.r4 }<input type="hidden" name="pullid" id="pullid" value="${element.feeName }"/></td>
   <td align="center" width="10%"><input type="text"  value="${element.price }" name="price" id="price" style="text-align:right" value="0"onpropertychange="checkthisvalue(this);sumValuesByName2('price','price2','standardQuoteTotal');"/></td>
   <td align="center" width="10%"><input type="text"  value="${element.finalPrice }" name="finalPrice" id="finalPrice" style="text-align:right" value="0" onpropertychange="checkthisvalue(this);sumValuesByName2('finalPrice','finalPrice2','finallyQuoteTotal');"/></td>  
   </tr>
   </logic:iterate>
    </logic:present>
    </table>
    <br>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
      <tr>
        <td class="wordtd">��׼���ۺϼƣ�Ԫ��:</td>
        <td width="20%">
          <html:text name="ServicingContractQuoteMaster" property="standardQuoteTotal" styleId="standardQuoteTotal" readonly="true" styleClass="default_input_noborder"/>
        </td>
        <td class="wordtd">���ձ��ۺϼƣ�Ԫ��:</td>
        <td width="20%">
          <html:text name="ServicingContractQuoteMaster" property="finallyQuoteTotal" styleId="finallyQuoteTotal" readonly="true" styleClass="default_input_noborder"/>
        </td>
        <td class="wordtd">ҵ���(Ԫ):</td>
        <td><html:text name="ServicingContractQuoteMaster" property="businessCosts" onchange="checkthisvalue(this);" /></td>
      </tr>
    </table>
    <br>
    
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
      <tr>
        <td class="wordtd"><bean:message key="maintContractQuote.priceFluctuaApply"/>:</td>
        <td><html:textarea name="ServicingContractQuoteMaster" property="priceFluctuaApply" rows="3" cols="100" styleClass="default_textarea"/></td>
      </tr>      
      <tr>
        <td class="wordtd"><bean:message key="maintContractQuote.businessCostsApply"/>:</td>
        <td><html:textarea name="ServicingContractQuoteMaster" property="businessCostsApply" rows="3" cols="100" styleClass="default_textarea"/></td>
      </tr>        
      <tr>
        <td class="wordtd"><bean:message key="maintContractQuote.paymentMethodApply"/>:</td>
        <td><html:textarea name="ServicingContractQuoteMaster" property="paymentMethodApply" rows="3" cols="100" styleClass="default_textarea"/></td>      
      </tr>
      <%--
      <tr>
        <td class="wordtd"><bean:message key="maintContractQuote.specialApplication"/>:</td>
        <td><html:textarea name="ServicingContractQuoteMaster" property="specialApplication" rows="3" cols="100" styleClass="default_textarea"/></td>      
      </tr>
      --%>
      <tr>
        <td class="wordtd"><bean:message key="maintContractQuote.contractContentApply"/>:</td>
        <td><html:textarea name="ServicingContractQuoteMaster" property="contractContentApply" rows="3" cols="100" styleClass="default_textarea"/></td>                                         
      </tr>
    </table>
  <script type="text/javascript">
    function heji(){
      var quantitys = document.getElementsByName("quantity");
      var unitPrices = document.getElementsByName("unitPrice");
      var price2s = document.getElementsByName("price2");    
      for(var i=0;i<quantitys.length;i++){
        price2s[i].value = unitPrices[i].value*10000000*Number(quantitys[i].value)/10000000;
      }

    }
    window.onload=function(){
      setDynamicTable("table_22","modelrow_0");
      setDynamicTable("table_1","tr1");
      setScrollTable("scrollBox","table_22",10);
    
      var busType='${ServicingContractQuoteMaster.busType}';
       $("#busType").val(busType);      
    }
    function checkthisvalue(obj){  
    	var objv=obj.value.substring(0,obj.value.length-1);
    	if(isNaN(obj.value)){
    		obj.value=objv;
    	}
    }
     
  </script>
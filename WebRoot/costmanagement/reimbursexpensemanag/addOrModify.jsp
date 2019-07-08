<%@page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<script language="javascript" defer="defer" src="<html:rewrite forward='DatePickerJS'/>"></script>

<html:hidden property="isreturn"/>
<html:hidden property="id"/>
<html:hidden name="reimbursExpenseManagBean" property="billno"/>
<html:hidden property="prds"/>
<html:hidden property="msrs"/>
<html:hidden property="mdrs"/>
<html:hidden property="nrms"/>
<a href="" id="maintContractNoOpen" target="_blank"></a>

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
   <tr>
   	<td class="wordtd" width="20%">����ά���ֲ���</td>
   	<td width="30%">

   		
   		<logic:notPresent name="ismchang" >
   		<input name="maintDivisionName" id="comName" value="${maintDivisionName }">
   		<html:hidden name="reimbursExpenseManagBean" property="maintDivision" styleId="comId" />
   		<input type="button" value=".." onclick="openWindowAndReturnValue3('searchReimbursPeopleationAction','toSearchRecord','','')" class="default_input"/>
        </logic:notPresent>
        <logic:present name="ismchang">
        ${maintDivisionName }
        <input type="hidden" name="maintDivisionName" id="comName" value="${maintDivisionName }">
        <html:hidden name="reimbursExpenseManagBean" property="maintDivision" styleId="comId" />
        </logic:present>

   	</td>
   	<td class="wordtd" width="20%">�������ţ�</td>
   	<td width="30%">
	    <font id="storageName">${storageName }</font>
	    <html:hidden name="reimbursExpenseManagBean" property="maintStation" styleId="storageId"/>
   	</td>
   </tr>
   <tr>
   	<td class="wordtd">�����ˣ�</td>
   	<td id="td1">
		 <font id="userName">${reimbursPeopleName }</font>
		 <html:hidden name="reimbursExpenseManagBean" property="reimbursPeople" styleId="userId"/>
   	</td>
   	<td class="wordtd">����ʱ�䣺</td>
   	<td><html:text name="reimbursExpenseManagBean" property="reimbursDate" size="12" styleClass="Wdate" onfocus="WdatePicker({isShowClear:true,readOnly:true})" /></td>
   </tr>
</table>
<br>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td class="wordtd" width="20%">�����ܶԪ����</td>
		<td width="75%"><html:text name="reimbursExpenseManagBean" property="totalAmount" styleId="totalAmount" readonly="true" styleClass="default_input_noborder" /></td>
	</tr>
</table>
<br>

<table width="100%" border="0" cellpadding="0" cellspacing="0"  class="tb">
	<tr>
		<td class="wordtd" width="20%">��ͬ������Ԫ����</td>
		<td width="75%"><html:text name="reimbursExpenseManagBean" property="projectMoney" styleId="projectMoney" readonly="true" styleClass="default_input_noborder" /></td>
	</tr>
</table>

<div id="caption_0" style="width: 100%;padding-top: 2;padding-bottom: 2;border-bottom: 0;border-top: 0" class="tb">
  &nbsp;<input type="button" value=" + " onclick="addElevators('prb')" class="default_input">
  <input type="button" value=" - " onclick="deleteRow('prb');sumValuesByName('prb','money','projectMoney');sumAmount();" class="default_input">           
  &nbsp;&nbsp;&nbsp;��������Ԫ��<input type="text" id="avgMoney" title="����������Ԫ��!" onkeypress="f_check_number2()"  onpropertychange="checkthisvalue(this);">
  &nbsp;<input type="button" value="ƽ������" onclick="avgMoney1()" class="default_input">
  <font color="red">ע���빴ѡ��Ҫƽ��������ĺ�ͬ�ţ�</font>
</div>
<table id="prb" width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<thead>
		<tr class="wordtd_header">
			<td width="5%"><input type="checkbox" name="cbAll" onclick="checkTableAll('prb',this)"/></td>
			<td width="10%">��ͬ��</td>
			<td width="10%">��ͬ����</td>
			<td width="10%">ά���ֲ�</td>
			<td width="15%">ά��վ</td>
			<td width="4%" nowrap="nowrap">̨��</td>
			<td width="8%" nowrap="nowrap">��Ԫ��<font color="red">*</font></td>
			<td width="10%">��������<font color="red">*</font></td>
			<td>��ע</td>
		</tr>
	</thead>
	<tfoot>
      <tr height="15"><td colspan="9">&nbsp;</td></tr>
    </tfoot>
	<tbody>
		<tr id="prbT_0" name="prbT_0" style="display: none;">
			<td align="center"><input type="checkbox" onclick="cancelCheckAll('prb','cbAll')"/></td>
			<td align="center">
				<input type="hidden" name="billno" id="billno" />
				<input type="text" name="maintContractNo" id="maintContractNo" onclick="simpleOpenWindowKK(this);" readonly="readonly" class="link noborder_center"/>
			</td>
			<td align="center"><input type="hidden" name="busType" id="busType" onpropertychange="bustype(this)" class="noborder" />
				<script type="text/javascript">
				function bustype(object){
					var bus=object.value;
					if(bus=="B"){
						object.parentElement.appendChild(document.createTextNode("����"));
					}
					if(bus=="W"){
						object.parentElement.appendChild(document.createTextNode("ά��"));
					}
					if(bus=="G"){
						object.parentElement.appendChild(document.createTextNode("����"));
					}
				}
					
				</script>
			</td>
			
			<td align="center">
			<input type="text" width="100%" name="comNameprb"  id="comNameprb" readonly="readonly"  class="noborder_center"/>
			<font id="comNameprbText"></font>
			<input type="hidden" name="maintDivisionBx" id="maintDivision"/>
			</td>
			<td align="center">
			<input type="text" width="100%" name="storageNameprb"  id="storageNameprb" readonly="readonly"  class="noborder_center"/>
		    <font id="storageNameprbText"></font>
			<input type="hidden" name="maintStationBx" id="maintStation"/>
			</td>
			<td align="center"><input type="text" name="num" id="num" readonly="readonly" class="noborder" size="3" /></td>
			<td align="center"><input type="text" name="money" id="money" class="default_input" size="9"  onpropertychange="checkthisvalue(this);sumValuesByName('prb',this.name,'projectMoney');sumAmount();" /></td>
			<td align="center">
        		<select name="reimburType" id="reimburType" >
		              <option value="">��ѡ��</option>
		            <logic:iterate id="s" name="prTypeList" >
		              <option value="${s.id.pullid}">${s.pullname}</option>
		            </logic:iterate>
          		</select>
			</td>
			<td><textarea rows="" cols="40"name="rem" id="rem" class="default_input" ></textarea></td>
		</tr>
		<logic:present name="projectList">
          <logic:iterate id="element1" name="projectList" >
          	<tr id="prbT_1" name="prbT_1">
	         	<td align="center"><input type="checkbox" onclick="cancelCheckAll('prb','cbAll')"/></td>
				<td align="center">
					<input type="hidden" name="billno" id="billno" value="${element1.billno}" />
					<input type="text" name="maintContractNo" id="maintContractNo" onclick="simpleOpenWindowKK(this);"value="${element1.maintContractNo}" readonly="readonly" class="link noborder_center"/>
			
				</td>
				<td align="center">
					<logic:match name="element1" property="busType" value="B">����</logic:match>
					<logic:match name="element1" property="busType" value="W">ά��</logic:match>
					<logic:match name="element1" property="busType" value="G">����</logic:match>
					<input type="hidden" name="busType" id="busType" value="${element1.busType}" />
				</td>
				<td align="center">${element1.comName}<input type="hidden" name="maintDivisionBx" id="maintDivision"value="${element1.maintDivisionBx}" /></td>
				<td align="center">${element1.storageName}<input type="hidden" name="maintStationBx" id="maintStation"value="${element1.maintStationBx}" /></td>
				<td align="center"><input type="text" name="num" id="num" readonly="readonly" class="noborder" size="3" value="${element1.num}" /></td>
				<td align="center"><input type="text" name="money" id="money" class="default_input" size="9" onpropertychange="checkthisvalue(this);sumValuesByName('prb',this.name,'projectMoney');sumAmount();" value="${element1.money}" /></td>
				<td align="center">
					<html:select name="element1" property="reimburType">
	        			<html:option value="">ȫ��</html:option>
			  			<html:options collection="prTypeList" property="id.pullid" labelProperty="pullname"/>
	        		</html:select>
				</td>
				<td><textarea rows="" cols="40" name="rem" id="rem" class="default_input" >${element1.rem}</textarea></td>
	         
	        </tr>
          </logic:iterate>
        </logic:present>
	</tbody>
</table>
<br/>

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td class="wordtd" width="20%">վ�ڱ�����Ԫ����</td>
		<td width="75%"><html:text name="reimbursExpenseManagBean" property="stationMoney" styleId="stationMoney" readonly="true" styleClass="default_input_noborder" /></td>
	</tr>
</table>

<div id="caption_0" style="width: 100%;padding-top: 2;padding-bottom: 2;border-bottom: 0;border-top: 0" class="tb">
  &nbsp;<input type="button" value=" + " onclick="addRow('msr')" class="default_input">
  <input type="button" value=" - " onclick="deleteRow('msr');sumValuesByName('msr','money','stationMoney');sumAmount();" class="default_input">           
</div>

<table id="msr" width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<thead>
		<tr class="wordtd_header">
			<td width="5%"><input type="checkbox" name="cbAll" onclick="checkTableAll('msr',this)"/></td>
			<td width="10%">��������<font color="red">*</font></td>
			<td width="15%">ά���ֲ�</td>
			<td width="15%">ά��վ</td>
			<td width="10%">��Ԫ��<font color="red">*</font></td>
			<td>��ע</td>
		</tr>
	</thead>
	<tfoot>
      <tr height="15"><td colspan="6">&nbsp;</td></tr>
    </tfoot>
   
	<tbody>
		<tr id="msrT_0" name="msrT_0" style="display: none;">
			<td align="center"><input type="checkbox" onclick="cancelCheckAll('msr','cbAll')"/></td>
			<td align="center">
        		<select name="reimburType" id="reimburType" >
		              <option value="">��ѡ��</option>
		            <logic:iterate id="s" name="mrTypeList" >
		              <option value="${s.id.pullid}">${s.pullname}</option>
		            </logic:iterate>
          		</select>
			</td>
			<td align="center">
   	        <font id="comNameMsr">${maintDivisionName }</font>
   		    <input type="hidden" name="maintDivisionBx" id="maintDivisionBx">
   		    <input type="button" name="openWindow" value=".." onclick="" class="default_input"/>
         	</td>
         	<td align="center">
	        <font id="storageNameMsr">${storageName }</font>
	        <input type="hidden" name="maintStationBx" id="maintStationBx">
   	        </td>
			<td align="center"><input type="text" name="money" class="default_input" onpropertychange="checkthisvalue(this);sumValuesByName('msr',this.name,'stationMoney');sumAmount();" /></td>
			<td><textarea rows="" cols="80" name="rem" class="default_input"></textarea>
			</td>
			
		</tr>
		<% int i=1; %>
		<logic:present name="stationList">
          <logic:iterate id="element2" name="stationList" >
          	<tr id="msrT_1" name="msrT_1">
	         <td align="center"><input type="checkbox" onclick="cancelCheckAll('msr','cbAll')"/></td>
	         <td align="center">
				<html:select name="element2" property="reimburType">
	        			<html:option value="">ȫ��</html:option>
			  			<html:options collection="mrTypeList" property="id.pullid" labelProperty="pullname"/>
        		</html:select>
			</td>
			<td align="center">
   	        <font id="comNameMsrmsr<%=i%>">${element2.r1 }</font>
   		    <input type="hidden" name="maintDivisionBx" id="maintDivisionBxmsr<%=i%>" value="${element2.maintDivisionBx }">
   		    <input type="button" name="openWindow" value=".." onclick="openWindowmaintDivisionBx(<%=i%>,'msr')" class="default_input"/>
         	</td>
         	<td align="center">
	        <font id="storageNameMsrmsr<%=i%>">${element2.r2 }</font>
	        <input type="hidden" name="maintStationBx" id="maintStationBxmsr<%=i%>" value="${element2.maintStationBx }">
   	        </td>
			<td align="center"><input type="text" value="${element2.money}" name="money" class="default_input" onpropertychange="checkthisvalue(this);sumValuesByName('msr',this.name,'stationMoney');sumAmount();" /></td>
			<td><textarea rows="" cols="80" name="rem" class="default_input" >${element2.rem}</textarea></td>
	        </tr>
	        <%i++; %>
          </logic:iterate>
        </logic:present>
	</tbody>
</table>
<br/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td class="wordtd" width="20%">�ֲ�������Ԫ����</td>
		<td width="75%"><html:text name="reimbursExpenseManagBean" property="divsionMoney" styleId="divsionMoney" readonly="true" styleClass="default_input_noborder" /></td>
	</tr>
</table>

<div id="caption_0" style="width: 100%;padding-top: 2;padding-bottom: 2;border-bottom: 0;border-top: 0" class="tb">
  &nbsp;<input type="button" value=" + " onclick="addRow('mdr')" class="default_input">
  <input type="button" value=" - " onclick="deleteRow('mdr');sumValuesByName('mdr','money','divsionMoney');sumAmount();" class="default_input">           
</div>

<table id="mdr" width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<thead>
		<tr class="wordtd_header">
			<td width="5%"><input type="checkbox" name="cbAll" onclick="checkTableAll('mdr',this)"/></td>
			<td width="10%">��������<font color="red">*</font></td>
			<td width="15%">ά���ֲ�</td>
			<td width="10%">��Ԫ��<font color="red">*</font></td>
			<td>��ע</td>
		</tr>
	</thead>
	<tfoot>
      <tr height="15"><td colspan="5">&nbsp;</td></tr>
    </tfoot>
   
	<tbody>
		<tr id="mdrT_0" name="mdrT_0" style="display: none;">
			<td align="center"><input type="checkbox" onclick="cancelCheckAll('mdr','cbAll')"/></td>
			<td align="center">
        		<select name="reimburType" id="reimburType" >
		              <option value="">��ѡ��</option>
		            <logic:iterate id="s" name="mrTypeList" >
		              <option value="${s.id.pullid}">${s.pullname}</option>
		            </logic:iterate>
          		</select>
			</td>
			<td align="center">
   		      <font id="comNameMsr">${maintDivisionName }</font>
   		    <input type="hidden" name="maintDivisionBx" id="maintDivisionBx">
   		    <input type="button" name="openWindow" value=".." onclick="" class="default_input"/>
         	</td>
			<td align="center"><input type="text" name="money" class="default_input" onpropertychange="checkthisvalue(this);sumValuesByName('mdr',this.name,'divsionMoney');sumAmount();" /></td>
			<td><textarea rows="" cols="80" name="rem" class="default_input"></textarea>
			</td>
			
		</tr>
		<% int y=1; %>
		<logic:present name="divisionList">
          <logic:iterate id="element3" name="divisionList" >
          	<tr id="msrT_1" name="msrT_1">
	         <td align="center"><input type="checkbox" onclick="cancelCheckAll('msr','cbAll')"/></td>
	         <td align="center">
				<html:select name="element3" property="reimburType">
	        			<html:option value="">ȫ��</html:option>
			  			<html:options collection="mrTypeList" property="id.pullid" labelProperty="pullname"/>
        		</html:select>
			</td>
			<td align="center">
   	        <font id="comNameMsrmdr<%=y%>">${element3.r1 }</font>
   		    <input type="hidden" name="maintDivisionBx" id="maintDivisionBxmdr<%=y%>" value="${element3.maintDivisionBx }">
   		    <input type="button" name="openWindow" value=".." onclick="openWindowmaintDivisionBx(<%=y%>,'mdr')" class="default_input"/>
         	</td>
			<td align="center"><input type="text" value="${element3.money}" name="money" class="default_input" onpropertychange="checkthisvalue(this);sumValuesByName('mdr',this.name,'divsionMoney');sumAmount();" /></td>
			<td><textarea rows="" cols="80" name="rem" class="default_input" >${element3.rem}</textarea></td>
	        </tr>
	        <%y++; %>
          </logic:iterate>
        </logic:present>
	</tbody>
</table>
<br/>

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td class="wordtd" width="20%">��ά���ɱ�������Ԫ����</td>
		<td width="75%"><html:text name="reimbursExpenseManagBean" property="noReimMoney" styleId="noReimMoney" readonly="true" styleClass="default_input_noborder" /></td>
	</tr>
</table>

<div id="caption_0" style="width: 100%;padding-top: 2;padding-bottom: 2;border-bottom: 0;border-top: 0" class="tb">
  &nbsp;<input type="button" value=" + " onclick="addRow('nrm')" class="default_input">
  <input type="button" value=" - " onclick="deleteRow('nrm');sumValuesByName('nrm','money','noReimMoney');sumAmount();" class="default_input">           
</div>

<table id="nrm" width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<thead>
		<tr class="wordtd_header">
			<td width="5%"><input type="checkbox" name="cbAll" onclick="checkTableAll('nrm',this)"/></td>
			<td width="10%">��������<font color="red">*</font></td>
			<td width="15%">��������</td>
			<td width="10%">��Ԫ��<font color="red">*</font></td>
			<td>��ע</td>
		</tr>
	</thead>
	<tfoot>
      <tr height="15"><td colspan="5">&nbsp;</td></tr>
    </tfoot>
   
	<tbody>
		<tr id="nrmT_0" name="nrmT_0" style="display: none;">
			<td align="center"><input type="checkbox" onclick="cancelCheckAll('nrm','cbAll')"/></td>
			<td align="center">
        		<select name="reimburType" id="reimburType" >
		              <option value="">��ѡ��</option>
		            <logic:iterate id="s" name="mrTypeList" >
		              <option value="${s.id.pullid}">${s.pullname}</option>
		            </logic:iterate>
          		</select>
			</td>
			<td align="center">
   		      <font id="comNameMsr">${maintDivisionName }</font>
   		    <input type="hidden" name="belongsDepart" id="maintDivisionBx">
   		    <input type="button" name="openWindow" value=".." onclick="" class="default_input"/>
         	</td>
			<td align="center"><input type="text" name="money" class="default_input" onpropertychange="checkthisvalue(this);sumValuesByName('nrm',this.name,'noReimMoney');sumAmount();" /></td>
			<td><textarea rows="" cols="80" name="rem" class="default_input"></textarea>
			</td>
		</tr>
		<% int x=1; %>
		<logic:present name="noReimList">
          <logic:iterate id="element4" name="noReimList" >
          	<tr id="msrT_1" name="msrT_1">
	         <td align="center"><input type="checkbox" onclick="cancelCheckAll('msr','cbAll')"/></td>
	         <td align="center">
				<html:select name="element4" property="reimburType">
	        			<html:option value="">ȫ��</html:option>
			  			<html:options collection="mrTypeList" property="id.pullid" labelProperty="pullname"/>
        		</html:select>
			</td>
			<td align="center">
   	        <font id="comNameMsrnrm<%=x%>">${element4.r1 }</font>
   		    <input type="hidden" name="belongsDepart" id="maintDivisionBxnrm<%=x%>" value="${element4.belongsDepart }">
   		    <input type="button" name="openWindow" value=".." onclick="openWindowmaintDivisionBx(<%=x%>,'nrm')" class="default_input"/>
         	</td>
			<td align="center"><input type="text" value="${element4.money}" name="money" class="default_input" onpropertychange="checkthisvalue(this);sumValuesByName('nrm',this.name,'noReimMoney');sumAmount();" /></td>
			<td><textarea rows="" cols="80" name="rem" class="default_input" >${element4.rem}</textarea></td>
	        </tr>
	        <%x++; %>
          </logic:iterate>
        </logic:present>
	</tbody>
</table>
<br/>

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb">
	<tr>
		<td class="wordtd" width="20%">¼���ˣ�</td>
		<td width="30%">
			<bean:write name="operName"/>
			<html:hidden name="reimbursExpenseManagBean" property="operId" />
		</td>
		<td class="wordtd" width="20%">¼�����ڣ�</td>
		<td width="30%"><html:text name="reimbursExpenseManagBean" property="operDate" readonly="true" styleClass="default_input_noborder" /></td>
	</tr>
</table>
<script type="text/javascript">
window.onload = function(){
	setDynamicTable("prb","prbT_0");// ���ö�̬��ɾ�б��
	setDynamicTable("msr","msrT_0");// ���ö�̬��ɾ�б��
	setDynamicTable("mdr","mdrT_0");// ���ö�̬��ɾ�б��
	setDynamicTable("nrm","nrmT_0");// ���ö�̬��ɾ�б��
}

</script>
<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/html-table.tld" prefix="table"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" type="text/css"
	href="<html:rewrite forward='formCSS'/>">
	<script language="javascript" defer="defer" src="<html:rewrite forward='DatePickerJS'/>"></script>
	<script language="javascript" src="<html:rewrite page="/common/javascript/dynamictable.js"/>"></script>
<html:errors />
<br>
<html:form action="/maintenanceWorkPlanTurnAction.do">
<input type="hidden" name="method" id="method"/>

	<table>
		<tr >
		    <td>
		          ά����ͬ��:
			</td>
		   <td>
             <html:text property="maintContractNo" styleId="maintContractNo" styleClass="default_input" size="20"></html:text>
			</td>
		    <td>
		        &nbsp;&nbsp;���ݱ��:
			</td>
		   <td>
             <html:text property="elevatorNo" styleId="elevatorNo" styleClass="default_input" size="20"></html:text>
			</td>
		     
		     <td>
		         &nbsp;&nbsp; ��Ŀ����:
			</td>
		   <td>
             <html:text property="projectName" styleId="projectName" styleClass="default_input" size="30"></html:text>
			</td>
			<td>
		          &nbsp;&nbsp;����ά��վ:
			</td>
		   <td>
			  <html:select property="mainStation" styleId="mainStation">
			     <html:options collection="mainStationList" property="storageid" labelProperty="storagename" />
			  </html:select>
			</td>
			</tr>
			<tr>
			<td>
		          ���ۺ�ͬ��:
			</td>
		   <td>
			  <html:text property="salesContractNo" styleId="salesContractNo" styleClass="default_input"></html:text>
			</td>
			<td>
		          &nbsp;&nbsp;ԭά����:
			</td>
		   <td>
			  <html:text property="maintPersonnel" styleId="maintPersonnel" styleClass="default_input"></html:text>
			</td>
					<td>
				         &nbsp;&nbsp;�´�����:
					</td>
				   <td>
				   		<html:text property="sdate1" styleId="sdate1" styleClass="Wdate" size="12" onfocus="WdatePicker({isShowClear:true})"></html:text>
						- 
						<html:text property="edate1" styleId="edate1" styleClass="Wdate" size="12" onfocus="WdatePicker({isShowClear:true})"></html:text>
						<font color="red">*</font>
					</td>
			
		</tr>
	</table>
<br>	
 <div  id="divid" style="overflow-y:auto;width:100%; height:340px;">
	<table class=tb width="100%" id="dynamictable_0" >  
	 <thead>
	<tr>
	<td class=wordtd_header style="text-align: center;"><div align="center"><input type="checkbox" name="selAll" onclick="checkTableAll('dynamictable_0',this);changeisBox();" /></div></td>
	<td nowrap="nowrap" class=wordtd_header style="text-align: center;" >&nbsp;���&nbsp;</td>		
	<td nowrap="nowrap" class=wordtd_header>ά����ͬ��</td>
	<td nowrap="nowrap" class=wordtd_header>���ۺ�ͬ��</td>
	<td nowrap="nowrap" class=wordtd_header >��Ŀ����</td>
	<td nowrap="nowrap" class=wordtd_header>���ݱ��</td>
	<td nowrap="nowrap" class=wordtd_header>ά����ʼʱ��</td>
	<td nowrap="nowrap" class=wordtd_header>ά���ƻ���ʼʱ��</td>
	<td nowrap="nowrap" class=wordtd_header>ά������ʱ��</td>
	<td nowrap="nowrap" class=wordtd_header>�´�����</td>
	<td nowrap="nowrap" class=wordtd_header>ά��վ</td>
	<td nowrap="nowrap" class=wordtd_header>ԭά����</td>
	<td nowrap="nowrap" class=wordtd_header>��ҵת������</td>
	<td nowrap="nowrap" class=wordtd_header>����ά����</td>		
	</tr>
	</thead>
	<logic:present name="maintenanceTurnListSize">
	<logic:equal name="maintenanceTurnListSize" value="0">
			<TR class=noItems ><TD colSpan=13>û��¼��ʾ! </TD></TR>
	</logic:equal>
	<logic:notEqual name="maintenanceTurnListSize"  value="0">
				<logic:present name="maintenanceTurnList">
	<logic:iterate id="element" name="maintenanceTurnList" indexId="i">
	
	
	<tr align="center" >
	<td nowrap="nowrap" ><input type="checkbox" name="checkboxMcds" onclick="changeisBox();cancelCheckAll('dynamictable_0','selAll');">
	</td>
	<td>${i+1 }</td>     
	<td nowrap="nowrap">
	<bean:write name="element"  property="maintContractNo"/>
	<input type="hidden" name="isBox" value="N">
	<input type="hidden" name="rowid" value="${element.rowid}" >
	<input type="hidden" name="elevator" value="'${element.elevatorNo}'">
	</td>
	<td nowrap="nowrap"><bean:write name="element" property="salesContractNo" /></td>
	<td nowrap="nowrap"><bean:write name="element" property="projectName" /></td>
	<td nowrap="nowrap">
	<a onclick="simpleOpenWindow('elevatorSaleAction','${element.elevatorNo}');" class="link">${element.elevatorNo}</a>	
	</td>
	<td nowrap="nowrap"><bean:write name="element" property="mainSdate" /></td>
	<td nowrap="nowrap"><bean:write name="element" property="shippedDate" /></td>
	<td nowrap="nowrap"><bean:write name="element" property="mainEdate" /> </td>
	<td nowrap="nowrap"><bean:write name="element" property="taskSubDate" /></td>
	<td nowrap="nowrap"><bean:write name="element" property="assignedMainStation" /></td>
	<td nowrap="nowrap"><bean:write name="element" property="maintPersonnel" /></td>
	<td nowrap="nowrap">
		<html:text property="zpdate" styleId="zpdate" value="${element.zpdate }" styleClass="Wdate" size="12"  onfocus="WdatePicker({isShowClear:true})"/>
		<input type="hidden" name="hzpdate" value="${element.zpdate}">
	</td>
	<td nowrap="nowrap">
		<select style="width: 90px;" name="zpoperid"  onmouseover="getmaintPersonnel(this,'${element.mainStation}');"  >
		     <option value="">��ѡ��</option>
        </select>		
	  <input type="hidden" name="mainStationId" value="${element.mainStation}">
	</td>
	</tr>	
	</logic:iterate>
	
	
	
	</logic:present>

	</logic:notEqual>


    </logic:present>
	

	</table>
	</div>
</html:form>
<script type="text/javascript">
$("document").ready(function() {
	  setScrollTable("divid","dynamictable_0",16);
});
</script>

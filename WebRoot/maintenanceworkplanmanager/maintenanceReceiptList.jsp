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
<html:form action="/maintenanceReceiptAction.do">
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
			<td>&nbsp;&nbsp;ά���ֲ�:</td>
	    	<td>
	    		<html:select property="maintDivision" styleId="maintDivision" onchange="Evenmore(this,'mainStation')">
			    	<html:options collection="maintDivisionList" property="grcid" labelProperty="grcname"/>
	    		</html:select>
	    	</td>
			</tr>
			<tr>
			 <td>      
        ���ۺ�ͬ��
        :
      </td>
      <td>
        <html:text property="salesContractNo" styleClass="default_input" />
      </td>
			
			
			
					<td>
		          &nbsp;&nbsp;�Ƿ�ǩ��:
			</td>
		   <td>
			  <html:select property="isAssignedSign" styleId="isAssignedSign">
			     <%-- html:option value="ALL"><bean:message key="pageword.all" /></html:option --%>    
			     <html:option value="Y">��ǩ��</html:option>
				 <html:option value="N">δǩ��</html:option>
				 <html:option value="R">���˻�</html:option>>

			  </html:select>
			</td>
			<td>
		         &nbsp;&nbsp;�´�����:
			</td>
		   <td >
		   		<html:text property="sdate1" styleId="sdate1" styleClass="Wdate" size="12" onfocus="WdatePicker({isShowClear:true})"></html:text>
				- 
				<html:text property="edate1" styleId="edate1" styleClass="Wdate" size="12" onfocus="WdatePicker({isShowClear:true})"></html:text>
			<font color="red">*</font>
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
	</table>
<br>	 
	 <table  class=tb  width="100%" id="dynamictable_0" >  
	<tr>
	<td class=wordtd_header style="width: 130px;text-align: center;"><div align="center"><input type="checkbox" name="selAll" onclick="checkTableAll('dynamictable_0',this);changeisBox();" /></div></td>
	<td nowrap="nowrap" class=wordtd_header style="text-align: center;" >&nbsp;���&nbsp;</td>		
	<td nowrap="nowrap" class=wordtd_header style="width: 130px;">ά����ͬ��</td>
	<td nowrap="nowrap" class=wordtd_header style="width: 100px;">���ۺ�ͬ��</td>
	<td nowrap="nowrap" class=wordtd_header style="width: 100px;">��Ŀ����</td>
	<td nowrap="nowrap" class=wordtd_header style="width: 100px;">���ݱ��</td>
	<td nowrap="nowrap" class=wordtd_header style="width: 100px;">ά����ʼʱ��</td>
	<td nowrap="nowrap" class=wordtd_header style="width: 100px;">ά������ʱ��</td>
	<td nowrap="nowrap" class=wordtd_header style="width: 90px;">�´�����</td>
	<td nowrap="nowrap" class=wordtd_header style="width: 90px;">ά��վ</td>
	<td nowrap="nowrap" class=wordtd_header style="width: 65px;">�Ƿ�ǩ��</td>
	<td nowrap="nowrap" class=wordtd_header style="width: 90px;">ǩ������</td>
	<td nowrap="nowrap" class=wordtd_header style="width: 65px;">ǩ����</td>	
	<td nowrap="nowrap" class=wordtd_header style="width: 90px;">����ά����</td>	
	<td nowrap="nowrap" class=wordtd_header style="width: 120px;">�˻�ԭ��</td>	
	<td nowrap="nowrap" class=wordtd_header style="width: 120px;">��ע</td>	
	</tr>
	
	<logic:present name="maintenanceReceiptListSize">
	<logic:equal name="maintenanceReceiptListSize" value="0">
			<TR class=noItems ><TD colSpan=15>û��¼��ʾ! </TD></TR>
	</logic:equal>
	<logic:notEqual name="maintenanceReceiptListSize"  value="0">
				<logic:present name="maintenanceReceiptList">
	<logic:iterate id="element" name="maintenanceReceiptList" indexId="i">
	
	
	<tr style="display:none;" class="inputtd" align="center" height="20">
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
	<td nowrap="nowrap"><bean:write name="element" property="contractSDate" /></td>
	<td nowrap="nowrap"><bean:write name="element" property="contractEDate" /> </td>
	<td nowrap="nowrap"><bean:write name="element" property="taskSubDate" /></td>
	<td nowrap="nowrap"><bean:write name="element" property="assignedMainStation" /></td>
	<td nowrap="nowrap">
	<logic:empty name="element" property="inAssignedSignFlag">
	  <select name="assignedSignFlag" id="assignedSignFlag" onchange="assignedSignFlagChange(this.value,${i+1 })">
			     <option value="">��ѡ��</option>
			     <option value="Y">ǩ��</option>
				 <option value="R">�˻�</option>
			  </select>
	</logic:empty>
	<logic:notEmpty name="element" property="inAssignedSignFlag"> 
	 ${element.inAssignedSignFlag =='R'?'���˻�':'��ǩ��' }	 
	 <input type="hidden" name="assignedSignFlag" value='<bean:write name="element" property="inAssignedSignFlag"/>'>
	</logic:notEmpty>
	<input type="hidden" name="inAssignedSignFlag" value='<bean:write name="element" property="inAssignedSignFlag"/>'>
	</td>
	<td nowrap="nowrap"><bean:write name="element" property="assignedSignDate" /></td>
	<td nowrap="nowrap"><bean:write name="element" property="assignedSign" /></td>
	<td nowrap="nowrap">
          <logic:notEmpty name="element" property="inAssignedSignFlag">	
	      <logic:match name="element" property="inAssignedSignFlag" value="Y">
	      &nbsp;<a href="<html:rewrite page='/maintenanceReceiptAction.do'/>?method=toMaintenanceWorkPlanDisplayRecord&isOpen=Y&id=${element.mwpmBillno}" target="_blnk"><bean:write name="element" property="inMaintPersonnel"/></a>&nbsp;         
    	</logic:match>	      
	      <input type="hidden" name="maintPersonnel" value='<bean:write name="element" property="inMaintPersonnel"/>'>	
	   </logic:notEmpty>
	   <logic:empty name="element" property="inAssignedSignFlag">
	  
	   		<logic:empty name="element" property="inMaintPersonnel">
	   		<%-- onchange="maintPersonnelChange(this.value,${i+1 })"  --%>
			<select style="width: 90px;" name="maintPersonnel" >
			     <option value="">��ѡ��</option>
			     <logic:iterate id="ele2" name="element" property="Loginuserlist">
			     	<option value="${ele2.userid }">${ele2.username }</option>
			     </logic:iterate>
			</select>
			</logic:empty>
	   </logic:empty>
	   <input type="hidden" name="hismaintpersonnel" value="${element.hismaintpersonnel}">
	  <input type="hidden" name="mainStationId" value="${element.mainStation}">
	</td>
	<td nowrap="nowrap">
	
	<logic:empty name="element" property="inAssignedSignFlag">
	<html:text style="width: 120px;" name="element" property="returnReason"></html:text>	
	</logic:empty>
	
	<logic:notEmpty name="element" property="inAssignedSignFlag">
	<logic:match name="element" property="inAssignedSignFlag" value="Y">
	<html:hidden name="element" property="returnReason"/>
	</logic:match>
	<logic:match name="element" property="inAssignedSignFlag" value="R">
	<bean:write name="element" property="returnReason"/>
	</logic:match>		
	</logic:notEmpty>	
	
	
	
	</td>
	<td>
	<html:text style="width: 120px;" name="element" property="assignedRem"></html:text>
	</td>
	</tr>	
	</logic:iterate>
	</logic:present>

	</logic:notEqual>


    </logic:present>
	

	</table>
	
</html:form>

<script>
	var rows = $(".inputtd");
	var i = 0;
	var showRow = setInterval(function(){
		if(i < rows.length){
			for(var j=i;j<rows.length && j<i+20;j++){
				rows[j].style.display="block";	
			}			
			i+=20;
		}else{
			clearInterval(showRow);
		}
	}, 1);
	
	//�����ʼ��Ϊ��һ�η���ά����
	function setMaintPersonnel(){
		var hismp=document.getElementsByName("hismaintpersonnel");
		var maintPersonnel=document.getElementsByName("maintPersonnel");
		for(var i=0;i<hismp.length;i++){
			var hmpval=hismp[i].value;
			if(hmpval!=""){
				var mpsel=maintPersonnel[i];
				//ѭ�������������ֵ
				for(var j=0;j<mpsel.length;j++){
		              if(hmpval==mpsel.options[j].value){  
		            	  mpsel.options[j].selected=true;
		            	  break;
		              }  
		          }
			}
		}
	}
	setMaintPersonnel();
	
	//var maintDivision=document.getElementById("maintDivision");
	//Evenmore(maintDivision,'mainStation');
	
</script>



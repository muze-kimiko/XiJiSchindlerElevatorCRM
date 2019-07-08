<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ page import="com.gzunicorn.common.util.SysConfig" %>
<script language="javascript" defer="defer" src="<html:rewrite forward='DatePickerJS'/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/jquery-1.9.1.min.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/openwindow.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/checkinput.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/dynamictable.js"/>"></script>
<script language="javascript">

//����ToolBar
function CreateToolBar(){
  SetToolBarHandle(true);
  SetToolBarHeight(20);

  AddToolBarItemEx("ReturnBtn","../../common/images/toolbar/back.gif","","",'<bean:message key="toolbar.return"/>',"65","0","returnMethod()");
  <logic:notPresent name="display">  
  <%-- �Ƿ��п�д��Ȩ��--%>
  <logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="nreturningmaintain" value="Y"> 
    AddToolBarItemEx("SaveBtn","../../common/images/toolbar/save.gif","","",'<bean:message key="toolbar.save"/>',"65","1","saveMethod()");
    AddToolBarItemEx("SaveReturnBtn","../../common/images/toolbar/save_back.gif","","",'<bean:message key="toolbar.returnsave"/>',"80","1","saveReturnMethod()");
  </logic:equal>
  </logic:notPresent>
  window.document.getElementById("toolbar").innerHTML=GenToolBar("TabToolBar_Manage","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");
}
//����
function returnMethod(){
  window.location = '<html:rewrite page="/lostElevatorMaintainSearchAction.do"/>?method=toSearchRecord';
}

//����
function saveMethod(){
	var cid=document.getElementById("companyId").value;
	var rod=document.getElementById("reOrder").value;
if(cid!=null&&cid!=" "){
		
		if(rod!=null&&rod!=" "){
			var maintContractNo=document.getElementsByName("maintContractNo");
			if(maintContractNo.length>0){
			document.lostElevatorMaintainForm.contracts.value=rowsToJSONArray("dynamictable_0","contracts");
			document.lostElevatorMaintainForm.isreturn.value = "N";
		    document.lostElevatorMaintainForm.submit();
		    }else{
		    	alert("����Ӷ��ݿͻ���ͬ��ϸ��");
		    }
		}else{
		alert("�ط�˳����Ϊ��");
		}
	}else{
			alert("��ͬ��ϵ�˲���Ϊ��");
	}

	
  
}

//���淵��
function saveReturnMethod(){
 
	var cid=document.getElementById("companyId").value;
	var rod=document.getElementById("reOrder").value;
	if(cid!=null&&cid!=""){
		
		if(rod!=null&&rod!=""){
		var maintContractNo=document.getElementsByName("maintContractNo");
			if(maintContractNo.length>0){
				document.lostElevatorMaintainForm.contracts.value=rowsToJSONArray("dynamictable_0","contracts");
				document.lostElevatorMaintainForm.isreturn.value = "Y";
			    document.lostElevatorMaintainForm.submit();
		   	}else{
		   		alert("����Ӷ��ݿͻ���ͬ��ϸ��");
		    }
		}else{
			alert("�ط�˳����Ϊ��");
		}
	}else{
		alert("��ͬ��ϵ�˲���Ϊ��");
	}
 
}

//�ж��Ƿ�Ϊ����
function onlyNumber(c){
	  if(!/^\d+$/.test(c.value)){
		   var tile="<bean:message key='markingitems.check.error.cgprice.required'/>";
		  alert(tile);
		  c.value=0;
	  }
}

//ֻ����������
function f_check_number2(){
	  	if((event.keyCode>=48 && event.keyCode<=57)){
	  	}else{
			event.keyCode=0;
	   	}
	 }

//��Ӻ�ͬ��ϸ
function addElevators(tableId){

  var paramstring = "contactPhone=";
  var contactPhone=document.getElementById("contactPhone");
  
  var cpstr=encodeURI(encodeURI(contactPhone.value));//URL����,��ֹ��������
  
  paramstring+=cpstr+"&wbBillnos="
  var wbBillnos = document.getElementsByName("wb_billno");
  for(i=0;i<wbBillnos.length;i++){
    paramstring += i<wbBillnos.length-1 ? "|"+wbBillnos[i].value+"|," : "|"+wbBillnos[i].value+"|"    
  }
  //alert(companyId.value);
  if(contactPhone.value!=null && contactPhone.value!=""){
	  var returnarray = openWindowWithParams("SearchLostElevatorReportAction","toSearchRecord",paramstring);//�����򲢷���ֵ
	  if(returnarray!=null && returnarray.length>0){          
	    addRows(tableId,returnarray.length);//������
	    toSetInputValue(returnarray,"lostElevatorMaintainForm");//��ҳ���Ӧ�����ֵ
	  }
  }else{
  	alert("����ѡ���ͬ��ϵ�ˣ�");
  }
  
 //setTopRowDateInputsPropertychange(document.getElementById(tableId));
}

</script>

  <tr>
    <td width="100%">
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height="22" class="table_outline3" valign="bottom" width="100%">
            <div id="toolbar" align="center">
            <script language="javascript">
              
                CreateToolBar();
                //SetToolBarAttribute();
              //-->
            </script>
            </div>
            </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
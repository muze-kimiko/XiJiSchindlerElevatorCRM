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

//ȥ���ո�
String.prototype.trim = function(){return this.replace(/(^\s*)|(\s*$)/g,"");}

//����
function returnMethod(){
  window.location = '<html:rewrite page="/returningMaintainSearchAction.do"/>?method=toSearchRecord';
}

//����
function saveMethod(){
	var contacts=document.getElementById("contacts").value;
	if(contacts.trim()==""){
		alert("��ͬ��ϵ�� ����Ϊ�գ�");
		return;
	}
	var contactPhone=document.getElementById("contactPhone").value;
	if(contactPhone.trim()==""){
		alert("��ͬ��ϵ�˵绰  ����Ϊ�գ�");
		return;
	}
	
	var rod=document.getElementById("reOrder").value;
	if(rod!=null&&rod!=""){
		//alert(rowsToJSONArray("dynamictable_0","contracts"));
		var maintContractNo=document.getElementsByName("maintContractNo");
		if(maintContractNo.length>0){
			document.returningMaintainForm.contracts.value=rowsToJSONArray("dynamictable_0","contracts");
			document.returningMaintainForm.isreturn.value = "N";
		    document.returningMaintainForm.submit();
	    }else{
	    	alert("����ӻطÿͻ�ά����ϸ��");
	    }
	}else{
		alert("<bean:message key='returningmaintain.check.error.reOrder.required'/>");
	}
}

//���淵��
function saveReturnMethod(){
	
	var contacts=document.getElementById("contacts").value;
	if(contacts.trim()==""){
		alert("��ͬ��ϵ�� ����Ϊ�գ�");
		document.getElementById("contacts").focus();
		return;
	}
	var contactPhone=document.getElementById("contactPhone").value;
	if(contactPhone.trim()==""){
		alert("��ͬ��ϵ�˵绰  ����Ϊ�գ�");
		document.getElementById("contactPhone").focus();
		return;
	}
 
	var rod=document.getElementById("reOrder").value;
	if(rod!=null&&rod!=""){
	var maintContractNo=document.getElementsByName("maintContractNo");
		if(maintContractNo.length>0){
			document.returningMaintainForm.contracts.value=rowsToJSONArray("dynamictable_0","contracts");
			document.returningMaintainForm.isreturn.value = "Y";
		    document.returningMaintainForm.submit();
	   	}else{
	    	alert("����ӻطÿͻ�ά����ϸ��");
	    }
	}else{
		alert("<bean:message key='returningmaintain.check.error.reOrder.required'/>");
		document.getElementById("reOrder").focus();
		return;
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

  var paramstring = "wtBillnos=";
  var wtBillnos = document.getElementsByName("wtBillno");
  for(i=0;i<wtBillnos.length;i++){
    paramstring += i<wtBillnos.length-1 ? "|"+wtBillnos[i].value+"|," : "|"+wtBillnos[i].value+"|"    
  }
  var paramstring1 = "wbBillnos1=";
  var wbBillnos = document.getElementsByName("wbBillno1");
  for(i=0;i<wbBillnos.length;i++){
    paramstring1 += i<wbBillnos.length-1 ? "|"+wbBillnos[i].value+"|," : "|"+wbBillnos[i].value+"|"    
  }
  //alert(companyId.value);
  	var projectName = window.location.pathname.replace(window.location.pathname.replace(/\s*\/+.*\/+/g,''),'');
	var url="query/Search.jsp?path="+encodeURIComponent(projectName+"searchReturningMaintainDetailAction.do?method=toSearchRecord&"+paramstring+"&"+paramstring1);
	
	  var returnarray = window.showModalDialog(url,window,'dialogWidth:970px;dialogHeight:500px;dialogLeft:200px;dialogTop:150px;center:yes;help:yes;resizable:yes;status:yes');//�����򲢷���ֵ
	  if(returnarray!=null && returnarray.length>0){          
	    addRows(tableId,returnarray.length);//������
	    toSetInputValue(returnarray,"returningMaintainForm");//��ҳ���Ӧ�����ֵ
	  }
 //setTopRowDateInputsPropertychange(document.getElementById(tableId));
}

function changeCustomer(){
	var table=document.getElementById("dynamictable_0");
	var l=table.rows.length;
	//alert(table.rows.length);
	if(l>1){
		for(var i=l-1;i>0;i--){
			table.deleteRow(i);
		}
	}
}
function contractdisplay(obj){
	var rowIdx = $(obj).parent().parent()[0].rowIndex-1;
	var r4 = document.getElementsByName("r4")[rowIdx].value;
	var val = document.getElementsByName("maintContractNo")[rowIdx].value;
	if(r4=="ί��"){
		var projectName = window.location.pathname.replace(window.location.pathname.replace(/\s*\/+.*\/+/g,''),'');
		var url=projectName+"returningMaintainDetailAction.do?method=toDisplayRecord&isOpen=Y&id="+val;
		window.open(url,'_blank');
	}else{
		var wbBillno = document.getElementsByName("wbBillno")[rowIdx].value;
		var paramstring = "typejsp=Yes";
		var paramstring1 = "id="+wbBillno;
		var paramstring2 = "HFflag=Y";
		var projectName = window.location.pathname.replace(window.location.pathname.replace(/\s*\/+.*\/+/g,''),'');
		var url=projectName+"maintContractAction.do?method=toDisplayRecord&isOpen=Y&"+paramstring+"&"+paramstring1+"&"+paramstring2;
		//var url="query/Search.jsp?path="+encodeURIComponent(projectName+"maintContractAction.do?method=toDisplayRecord&"+paramstring+"&"+paramstring1+"&"+paramstring2);
		window.open(url,'_blank');//������
		  
	}
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
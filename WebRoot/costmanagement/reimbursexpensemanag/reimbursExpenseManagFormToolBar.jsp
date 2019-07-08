<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ page import="com.gzunicorn.common.util.SysConfig" %>

<script language="javascript" src="<html:rewrite page="/common/javascript/openwindow.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/checkinput.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/dynamictable.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/jquery-1.9.1.min.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/math.js"/>"></script>
<script language="javascript" charset="GBK">

//����ToolBar
function CreateToolBar(){
  SetToolBarHandle(true);
  SetToolBarHeight(20);

  AddToolBarItemEx("ReturnBtn","../../common/images/toolbar/back.gif","","",'<bean:message key="toolbar.return"/>',"65","0","returnMethod()");
  <logic:notPresent name="display">  
  <%-- �Ƿ��п�д��Ȩ��--%>
  <logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="nreimbursexpensemanag" value="Y"> 
    AddToolBarItemEx("SaveBtn","../../common/images/toolbar/save.gif","","",'<bean:message key="toolbar.save"/>',"65","1","saveMethod()");
    AddToolBarItemEx("SaveReturnBtn","../../common/images/toolbar/save_back.gif","","",'<bean:message key="toolbar.returnsave"/>',"80","1","saveReturnMethod()");
  </logic:equal>
  </logic:notPresent>
  window.document.getElementById("toolbar").innerHTML=GenToolBar("TabToolBar_Manage","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");
}
//����
function returnMethod(){
  window.location = '<html:rewrite page="/reimbursExpenseManagSearchAction.do"/>?method=toSearchRecord';
}

//����
function saveMethod(){
	
	if(document.getElementById("userId").value==""){
		alert("��ѡ������!");return;
	}
	
  if(verify()){
	  document.reimbursExpenseManagForm.prds.value=rowsToJSONArray("prb","prds");
	  document.reimbursExpenseManagForm.msrs.value=rowsToJSONArray("msr","msrs");
	  document.reimbursExpenseManagForm.mdrs.value=rowsToJSONArray("mdr","mdrs");
	  document.reimbursExpenseManagForm.nrms.value=rowsToJSONArray("nrm","nrms");
	  document.reimbursExpenseManagForm.isreturn.value="N";
	  
	  document.getElementById("ReturnBtn").disabled = true;
	  document.getElementById("SaveBtn").disabled = true;
	  document.getElementById("SaveReturnBtn").disabled = true;
	  
	  
	  document.reimbursExpenseManagForm.submit();
  }
  
}

//���淵��
function saveReturnMethod(){
	if(document.getElementById("userId").value==""){
		alert("��ѡ������!");return;
	}
  inputTextTrim();  
  if(verify()){
	  document.reimbursExpenseManagForm.prds.value=rowsToJSONArray("prb","prds");
	  document.reimbursExpenseManagForm.msrs.value=rowsToJSONArray("msr","msrs");
	  document.reimbursExpenseManagForm.mdrs.value=rowsToJSONArray("mdr","mdrs");
	  document.reimbursExpenseManagForm.nrms.value=rowsToJSONArray("nrm","nrms");
	  document.reimbursExpenseManagForm.isreturn.value="Y";
	  
	  document.getElementById("ReturnBtn").disabled = true;
	  document.getElementById("SaveBtn").disabled = true;
	  document.getElementById("SaveReturnBtn").disabled = true;
	  
      document.reimbursExpenseManagForm.submit();
    } 
}

//��ӱ���������Ŀ
function addElevators(tableId,flag){
	
	if(document.getElementById("userId").value==""){
		alert("��ѡ������!");return;
	}

  var paramstring = "billnostr=";
  /**
  if(document.getElementsByName("billno")!=null && document.getElementsByName("billno").length>1){
	  var billnos=document.getElementsByName("billno");
	  var billnostr="";
	  for(var i=1;i<billnos.length;i++){
		  if(i==billnos.length-1){
			  billnostr+=billnos[i].value;
		  }else{
		  	  billnostr+=billnos[i].value+",";
		  }
	  }
	  paramstring+=billnostr;
  }
  */
  var reimbursPeople=document.getElementById("userId").value;
  paramstring+="&reimbursPeople="+reimbursPeople;
  var returnarray = openWindowWithParams("searchProjectReimbursementAction","toSearchRecord",paramstring);//�����򲢷���ֵ
  if(returnarray!=null && returnarray.length>0){          
    addRows(tableId,returnarray.length);//������
    toSetInputValue(returnarray,"reimbursExpenseManagForm");//��ҳ���Ӧ�����ֵ
  }    

}

function sumValuesByName(tableId,name,outputId){
	 var objs = document.getElementById(tableId);
	 var inputs=objs.getElementsByTagName("input");
	 var sum = 0;
	 var value = 0;
	 for(var i=0;i<inputs.length;i++){
		 if(inputs[i].name==name){
			 value = parseFloat(inputs[i].value);
			 sum =  isNaN(value) ? sum + 0 : accAdd(sum, value);
		 }
	 }
	 document.getElementById(outputId).value = sum;
}

//�����ܶ�
function sumAmount(){
	var pMoney=document.getElementById("projectMoney").value;
	if(pMoney==null || pMoney==""){
		pMoney=0;
	}
	var sMoney=document.getElementById("stationMoney").value;
	if(sMoney==null || sMoney==""){
		sMoney=0;
	}
	
	var dMoney=document.getElementById("divsionMoney").value;
	if(dMoney==null || dMoney==""){
		dMoney=0;
	}
	
	 var nMoney=document.getElementById("noReimMoney").value;
	if(nMoney==null || nMoney==""){
		nMoney=0;
	} 
	
	document.getElementById("totalAmount").value=accAdd(accAdd(parseFloat(dMoney),parseFloat(nMoney)),accAdd(parseFloat(pMoney), parseFloat(sMoney)));
}

/*
���table��*�ű�ǵ��ı�������ֵ�Ƿ�Ϊ�ղ���ʾ�����������¸�ʽ
����: <table id="XX">
   <tr id="XX"><td>����*</td><tr>
     <tr><td><input type="text"/></td><tr> 
     <table>
            ����ֵʱ����   ����������Ϊ�ա�
*/
function checkRowInput(tableId,titleRowId,name){
  var tableObject = document.getElementById(tableId);
  var rows = tableObject.rows; 
  var titleRowIndex = 0;
  var msg ="";

  for(var i=0;i<rows.length;i++){
    var cells = tableObject.rows[i].cells;
    var inputs = rows[i].getElementsByTagName("input");
    var selects = rows[i].getElementsByTagName("select");
    if(rows[i].id == titleRowId){
      titleRowIndex = i; 
    }
    
    for(var j=0;j<inputs.length;j++){
      var cellIndex = getFirstSpecificParentNode("td",inputs[j]).cellIndex;
      
      if(rows[titleRowIndex].cells[cellIndex]!=null&&rows[titleRowIndex].cells[cellIndex].innerHTML.indexOf("*")>=0){
        if(inputs[j].type=="text" && inputs[j].value==""){
          var title = rows[titleRowIndex].cells[cellIndex].innerHTML;            
          msg +=name+ "��"+(i-titleRowIndex)+"�� "+title.replace(/\s*<+.*>+\s*/g,"")+"����Ϊ��\n";    
        }        
      }
      
    }  
    
    for(var j=0;j<selects.length;j++){
        var cellIndex = getFirstSpecificParentNode("td",selects[j]).cellIndex;        
        if(rows[titleRowIndex].cells[cellIndex]!=null&&rows[titleRowIndex].cells[cellIndex].innerHTML.indexOf("*")>=0){
          if(selects[j].value==""){
            var title = rows[titleRowIndex].cells[cellIndex].innerHTML;            
            msg +=name+ "��"+(i-titleRowIndex)+"�� ��ѡ�� "+title.replace(/\s*<+.*>+\s*/g,"")+"\n";    
          }        
        }
        
      }
    
  }
        
  if(msg != ""){
    alert(msg);
    return false;
  } 
  return true;
}

function isNanSelects(tableId,titleRowId,name){
	var tableObject = document.getElementById(tableId);
	var rows=tableObject.rows;
	var msg ="";
	var maintContractNos=tableObject.getElementsByTagName("maintContractNo");
    var selects = tableObject.getElementsByTagName("select");
    var len=selects.length;
    for(var j=0;j<len;j++){
    	for(var i=0;i<len;i++){
    		if(i!=j){
    			if(selects[i].value==selects[j].value){
    				msg+=name+"��"+(j+1)+"�б��������ظ���������ѡ�������ͣ�\n";
    				break;
    			}
    		}
    	}
    }
	if(msg!=""){
		alert(msg);
		return false;
	}
	return true;
}

function isNaNContract(tableId,titleRowId,name){
	var tableObject = document.getElementById(tableId);
	var rows=tableObject.rows;
	var msg ="";
	var maintContractNos=document.getElementsByName("maintContractNo");
    var selects = tableObject.getElementsByTagName("select");
    var leng=maintContractNos.length;
    for(var j=0;j<leng;j++){
    	for(var i=0;i<leng;i++){
    		if(i!=j){
    			if(maintContractNos[j].value==maintContractNos[i].value){
    				if(selects[j].value==selects[i].value){
    					msg+=name+"��"+(j+1)+"�б��������ظ���������ѡ�������ͣ�\n";
    					break;
    				}
    			}
    		}
    	}
    }
    if(msg!=""){
		alert(msg);
		return false;
	}
	return true;
}

function isNanTable(){
	var prows=document.getElementById("prb").rows;
	var mrows=document.getElementById("msr").rows;
	var drows=document.getElementById("mdr").rows;
	var nrows=document.getElementById("nrm").rows;
	var msg="";
	if(prows.length<=2&&mrows.length<=2&&drows.length<=2&&nrows.length<=2){
		msg+="���������һ������ã�\n"
	}
	
	if(msg!=""){
		alert(msg);
		return false;
	}
	return true;
}

function verify(){
	if(isNanTable() && checkColumnInput(reimbursExpenseManagForm) && checkRowInput("prb","prbT_0","��Ŀ����") 
			&& checkRowInput("msr","msrT_0","վ�ڱ���") /* && isNanSelects("msr","msrT_0","վ�ڱ���") */
			&& checkRowInput("mdr","mdrT_0","�ֲ�����") /* && isNanSelects("mdr","mdrT_0","�ֲ�����") */
			&& checkRowInput("nrm","nrmT_0","��ά������") /* && isNanSelects("nrm","nrmT_0","��ά������") */){
		     var belongsDeparts=document.getElementsByName("belongsDepart");
		     if(belongsDeparts!=null){
		    	  var alertsrt="";
		    	 for(var i=0;i<belongsDeparts.length;i++){
		    		var belongsDepart = belongsDeparts[i].value;
		    		 if(belongsDepart=="00"||belongsDepart=="01"||belongsDepart=="02"
		    			 ||belongsDepart=="03"||belongsDepart=="04"){
		    			 alertsrt +="��ά������ ��"+(i+1)+"�� �������� ����Ϊά������ \n"; 
		    		 }
		    	 }
		        
		    	 if(alertsrt!=""){
		    		alert(alertsrt);
		    		return false;
		    	}
		    	 
		     }
		return true;
	}
	return false;
}

function checkPact(obj){
	var rowTr=obj.parentElement.parentElement;
	var inputs=rowTr.getElementsByTagName("input");
	var bustype="";
	var billno="";
	var actionName="";
	for(var i=0;i<inputs.length;i++){
		if(inputs[i].name=="busType"){
			bustype=inputs[i].value;
		}
		if(inputs[i].name=="billno"){
			billno=inputs[i].value;
		}
	}
	if(bustype=="B"){
		actionName="maintContractAction";
	}else{
		actionName="wgchangeContractAction";
	}
	
	simpleOpenWindow(actionName,billno,"typejsp=Yes");
}

function displayCheck(billno,bustype){
	var actionName="";
	if(bustype=="B"){
		actionName="maintContractAction";
	}else{
		actionName="wgchangeContractAction";
	}
	simpleOpenWindow(actionName,billno,"typejsp=Yes");
}



function openWindowAndReturnValue3(actionName,method,paramstring,flag){	 
	  var returnvalue = openWindowWithParams(actionName,method,paramstring);
      if(returnvalue!=null){
    	$("#prb tbody tr").remove();
    	$("#msr tbody tr").remove();
      }
	  toSetInputValue2(returnvalue,flag); 
	}
function avgMoney1(){
	var avgMoney= document.getElementById("avgMoney"); 
	if(avgMoney.value==""){
		alert("�����������!");
		return;
	}
	avgMoney=parseInt(avgMoney.value.replace(/(^\s*)|(\s*$)/g, ""));
	var tableObj = document.getElementById("prb"); 
	var inputs = tableObj.getElementsByTagName("input");
	var checkboxs = new Array(); //table������checkbox
	var nums = new Array();
	var moneys = new Array();
	for(var i=0;i<inputs.length;i++){
		if(inputs[i].type=="checkbox"){
			checkboxs.push(inputs[i]);          
		}
	}
  
	for(var i=0;i<inputs.length;i++){
		if(inputs[i].name=="num"){
			nums.push(inputs[i]);          
		}
	}
	for(var i=0;i<inputs.length;i++){
		if(inputs[i].name=="money"){
			moneys.push(inputs[i]);          
		}
	}
	
  	checkboxs[0].checked = false;//ȫѡcheckboxȡ��ѡ�� 
  	var sum=0
  	for(var i=1;i<checkboxs.length;i++){  
		if(checkboxs[i].checked == true){
			sum=sum+parseInt(nums[i-1].value.replace(/(^\s*)|(\s*$)/g, ""));
		}        
	}
  	var avgno=avgMoney/sum;//ƽ�����
  	for(var i=1;i<checkboxs.length;i++){  
		if(checkboxs[i].checked == true){
			var numNo=parseInt(nums[i-1].value.replace(/(^\s*)|(\s*$)/g, ""))
			moneys[i-1].value=(numNo*avgno).toFixed(2);
		}        
	}
  	
  	
}
	
//����һ��
function addRow(tableId) {
	if(document.getElementById("userId").value==""){
		alert("��ѡ������!");return;
	}
	var tableObj = document.getElementById(tableId);
	var index = tableObj.getAttribute("index");
	/* alert(sampleRows[index].innerHTML); */
	var newrow=sampleRows[index].cloneNode(true)
	tableObj.getElementsByTagName("tbody")[0].appendChild(newrow);
	cancelCheckAll(tableId);  
	setFlag(tableId,"add_"+tableObj.rows.length);//������Ӷ���
	
	var maintDivisionName = document.getElementsByName("maintDivisionName")[0].value;
	var comId = document.getElementById("comId").value;
	var storageName = document.getElementById("storageName").innerHTML;
	var storageId = document.getElementById("storageId").value;
	var i=tableObj.rows.length-1;	
	
	$("#"+tableId+" tr:eq("+i+") [name='openWindow']").click(function(){
		openWindowmaintDivisionBx(i,tableId);
	});
	$("#"+tableId+" tr:eq("+i+") #comNameMsr").html(maintDivisionName)
	$("#"+tableId+" tr:eq("+i+") #maintDivisionBx").val(comId);
	$("#"+tableId+" tr:eq("+i+") #comNameMsr").attr("id","comNameMsr"+tableId+i);
	$("#"+tableId+" tr:eq("+i+") #maintDivisionBx").attr("id","maintDivisionBx"+tableId+i);
	
	
	if(tableId=="msr"){
	    $("#msr tr:eq("+i+") #storageNameMsr").html(storageName);
	    $("#msr tr:eq("+i+") #maintStationBx").val(storageId);
		$("#msr tr:eq("+i+") #storageNameMsr").attr("id","storageNameMsr"+tableId+i);
		$("#msr tr:eq("+i+") #maintStationBx").attr("id","maintStationBx"+tableId+i);
	}
}

function openWindowmaintDivisionBx(i,str){
	if(str=="msr"){
		var returnvalue = openWindowWithParams('searchMaintDivisionBXAction','toSearchRecord','');
	}else{
		var returnvalue = openWindowWithParams('searchCompanyAction','toSearchRecord2',"srt="+str);
	}
    toSetInput(returnvalue,str+i); 
}






function toSetInput(arr,flag){
	  if(arr && arr.length == 1){  
	    flag=flag?flag:'';
	    for(i=0;i<arr.length;i++){		       
	      for(j=0;j<arr[i].length;j++){     
	        var object = arr[i][j].split("=");
	        var id = object[0];
	        var value = object[1];
	        if(document.getElementById(id+flag)){
	          var element = document.getElementById(id+flag);
	          if(element.value != null){
	          	element.value = value;
	          } else if(element.innerHTML != null){
	            element.innerHTML = value;
	          }
	          
	        }
	      }		      
	    } 		    
	  }  
	}

function simpleOpenWindowKK(obj){
	var mnos=document.getElementsByName("maintContractNo");
	var n=0;
	for(var i=0;i<mnos.length;i++){
		if(mnos[i]==obj){
			n=i;
		}
	}
	
	var billno=document.getElementsByName("billno")[n+1].value;
	var busType=document.getElementsByName("busType")[n].value;
	//alert(mnos.length+"=="+n+" ; "+billno+";"+busType);
	
	var maintContractNoOpen=document.getElementById("maintContractNoOpen");
	if(busType=="B"){
		maintContractNoOpen.href="<html:rewrite page='/maintContractAction.do'/>?method=toDisplayRecord&typejsp=Yes&id="+billno;
	}else{
		maintContractNoOpen.href="<html:rewrite page='/wgchangeContractAction.do'/>?method=toDisplayRecord&typejsp=Yes&id="+billno;
	}
	maintContractNoOpen.click();
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
            </script>
            </div>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
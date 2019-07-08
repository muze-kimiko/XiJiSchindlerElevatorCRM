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
<script language="javascript" src="<html:rewrite page="/common/javascript/math.js"/>"></script>

<script language="javascript">

//����ToolBar
function CreateToolBar(){
  SetToolBarHandle(true);
  SetToolBarHeight(20);
  
	AddToolBarItemEx("colseBtn","../../common/images/toolbar/close.gif","","",'�� ��',"65","0","closeMethod()");
	//AddToolBarItemEx("colseBtn","../../common/images/toolbar/close.gif","","",'�� ��',"65","0","window.close()");

  window.document.getElementById("toolbar").innerHTML=GenToolBar("TabToolBar_Manage","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");

}
//�ر�
function closeMethod(){
	window.top.close() 
}

//��Ӻ�ͬ��ϸ
function addElevators(tableId){

  var paramstring = "state=WB&elevatorNos=";    
  var elevatorNos = document.getElementsByName("elevatorNo");
  for(i=0;i<elevatorNos.length;i++){
    paramstring += i<elevatorNos.length-1 ? "|"+elevatorNos[i].value+"|," : "|"+elevatorNos[i].value+"|"    
  }
  var returnarray = openWindowWithParams("searchElevatorSaleAction","toSearchRecord2",paramstring);//�����򲢷���ֵ
  if(returnarray!=null && returnarray.length>0){          
    addRows(tableId,returnarray.length);//������
    toSetInputValue(returnarray,"maintContractForm");//��ҳ���Ӧ�����ֵ
  }
  
  setTopRowDateInputsPropertychange(document.getElementById(tableId));
  
  setotherval();
}
//����ʹ�õ�λ����=����λ����
function setotherval(){
	var projectName=document.getElementsByName("projectName");
	var projectAddress=document.getElementsByName("projectAddress");
	for(var i=0;i<projectName.length;i++){
		projectAddress[i].value=projectName[i].value
	}
}

//�����ͬ��Ч��
function setContractPeriod(){
  var sd = maintContractForm.contractSdate.value.split("-");
  var ed = maintContractForm.contractEdate.value.split("-");
  if(sd != "" && ed != ""){  
    var years = Number(ed[0])-Number(sd[0]);
    var months = years>0 ? 12*years - (Number(sd[1]) - Number(ed[1])) : Number(ed[1]) - Number(sd[1]);
    months = Number(ed[2])-Number(sd[2])>0 ? months+1 : months;  
    maintContractForm.contractPeriod.value = months;     
  }
}

function checkInput(){
  inputTextTrim();// ҳ�������ı������ȥ��ǰ��ո�
  
  var boo = false;
  
  var maintStation=document.getElementById("maintStation").value;
  if(maintStation==""){
	  alert("��ѡ�� ����ά��վ��");
  }else{
	  boo=true;
  }
  if(boo == true){
	  var elevatorNos = document.getElementsByName("elevatorNo"); 
	  if(elevatorNos !=null && elevatorNos.length>0){
	    boo = checkColumnInput(maintContractForm) && checkRowInput("dynamictable_0","titleRow_0");
	  }else{
	    alert("����� ��ͬ��ϸ��");
	    boo = false;
	  }
  }
  return boo;
}


function pickStartDay(endDateId){
  var endDate = document.getElementById(endDateId).value;
  endDate = endDate == null || endDate == ""��? "2099-12-31" : endDate   
  var d = endDate.split('-');
  WdatePicker({isShowClear:false,maxDate:d[0]+'-'+d[1]+'-'+(Number(d[2])-1),readOnly:true});
}


function pickEndDay(startDateId){
	var date = new Date();
	var startDate = document.getElementById(startDateId).value;
	var todayDate = date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
	var d = startDate < todayDate ? todayDate.split('-') : startDate.split('-');
	WdatePicker({isShowClear:false,minDate:d[0]+'-'+d[1]+'-'+(Number(d[2])+1),readOnly:true});
}


function setDatesByName(name,newDate){
  if(newDate && newDate!=""){
    var dates = document.getElementsByName(name);
          
    for ( var i = 0; i < dates.length; i++) { 
      if(dates[i].value == ""){
        dates[i].value = newDate;
      }  
    }  
  }
}

//��ϸ���У������ڿؼ���һ��ѡ������ʱ���Զ�����ͬһ��Ϊ�յ�����
function setTopRowDateInputsPropertychange(table){
  
  var tbody = table.getElementsByTagName("tbody")[0]; 
  var rows = tbody.childNodes;
  
  for(var i=0;i<rows.length;i++){
    if(!rows[i].id){
      var inputs = rows[i].getElementsByTagName("input");
      for(var j=0;j<inputs.length;j++){
       
        if(inputs[j].className.indexOf("Wdate")>=0){
           inputs[j].onpropertychange = function(){setDatesByName(this.name,this.value);};
        }
      }
      break;
    }
  }

}

//�жϱ�����ʽ�Ƿ���ѣ����ʱ�������ڡ��ʼ췢֤���ڡ��ƽ��ͻ����ڡ�ά��ȷ�����ڱ���
function isFree(mode){
	if(mode=="FREE"){
		$(".freeRequire").each(function(){
			$(this).append('<span style="color:red;">*</span>');
		})
		$("#contractTotal")[0].value=0;
		$("#otherFee")[0].value=0;
	} else {
		$(".freeRequire").each(function(){		
			$(this).children("span").remove();
		})
		$("#contractTotal")[0].value='${maintContractBean.contractTotal}';
		$("#otherFee")[0].value='${maintContractBean.otherFee}';
	}
}

//ѡ���½�
function selectAddElements(isSelectAdd){
    if(isSelectAdd == "Y"){
        $("#caption_0").html("<b>&nbsp;��ͬ��ϸ</b>");     
        $("#btGetSc").hide();
        $("#salesContractNo,#salesContractName,#deliveryAddress,#contractTotal,#otherFee").parent().html(function(){return this.innerHTML.replace("*","")})
        $("#salesContractNo,#salesContractName,#deliveryAddress,#contractTotal,#otherFee").attr("class","default_input_noborder");
        $("#salesContractName,#deliveryAddress,#contractTotal,#otherFee").attr("readonly","readonly");
      }
}

//
function changeSignWay(obj){
	var name=obj.name;
	var signWay=document.getElementsByName(name);
	if(obj==signWay[0]){
		for(var i=0;i<signWay.length;i++){
			signWay[i].value=signWay[0].value;
		}
	}
}

//AJAX��̬��ʾά��վ
var req;
function EvenmoreAdd(obj,listname){	
	var comid=obj.value;
	 var selectfreeid = document.getElementById(listname);
	if(comid!="" && comid!="%"){
		
		 if(window.XMLHttpRequest) {
			 req = new XMLHttpRequest();
		 }else if(window.ActiveXObject) {
			 req = new ActiveXObject("Microsoft.XMLHTTP");
		 }  //����response
		 
		 var url='<html:rewrite page="/maintContractAction.do"/>?method=toStorageIDList&comid='+comid;//��ת·��
		 req.open("post",url,true);//post �첽
		 req.onreadystatechange=function getnextlist(){
			
				if(req.readyState==4 && req.status==200){
				 var xmlDOM=req.responseXML;
				 var rows=xmlDOM.getElementsByTagName('rows');
				 if(rows!=null){
					    selectfreeid.options.length=0;
					    selectfreeid.add(new Option("ȫ��","%"));	

				 		for(var i=0;i<rows.length;i++){
							var colNodes = rows[i].childNodes;
							if(colNodes != null){
								var colLen = colNodes.length;
								for(var j=0;j<colLen;j++){
									var freeid = colNodes[j].getAttribute("name");
									var freename = colNodes[j].getAttribute("value");
									selectfreeid.add(new Option(freeid,freename));
					            }
				             }
				 		}
				 	}
				
				}
		 };//�ص�����
		 req.send(null);//������
	}else{		
		selectfreeid.options.length=0;
    	selectfreeid.add(new Option("ȫ��","%"));
	}
}

//��ѡ����ǵ�һ�е�����ʱ�������������ڵ�ֵ����Ϊ��һ�е�����ֵ
function setDateChanged(obj){
	var datearr=document.getElementsByName(obj.name);
	var dnum=0;
	for(var j=0;j<datearr.length;j++){
		if(datearr[j]==obj){
			dnum=j;
			break;
		}
	}
	//�ж��Ƿ��ǵ�һ������
	if(dnum==0){
		var datevalue=datearr[dnum].value;
		for(var i=0;i<datearr.length;i++){
			if(datearr[i].value.trim()==""){
				datearr[i].value=datevalue;
			}
		}
	}
}

//���ú�ͬ��ʼ����
function setcontractSdate(){
	var mainSdate = document.getElementsByName('mainSdate');
	var contractSdateval = mainSdate[0].value;
	for(var i=1;i<mainSdate.length;i++){
		var date1 = mainSdate[i].value;
		if(date1<contractSdateval){
			contractSdateval = date1;
		}
	}
	document.getElementById('contractSdate').value=contractSdateval;
}

//���ú�ͬ��������
function setcontractEdate(){
	var mainEdate = document.getElementsByName('mainEdate');
	var contractEdateval = mainEdate[0].value;
	for(var i=1;i<mainEdate.length;i++){
		var date1 = mainEdate[i].value;
		if(date1>contractEdateval){
			contractEdateval = date1;
		}
	}
	document.getElementById('contractEdate').value=contractEdateval;
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
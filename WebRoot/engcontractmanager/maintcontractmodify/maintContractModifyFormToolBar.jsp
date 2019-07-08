<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ page import="com.gzunicorn.common.util.SysConfig" %>

<script language="javascript" src="<html:rewrite page="/common/javascript/jquery-1.9.1.min.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/openwindow.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/dynamictable.js"/>"></script>

<script language="javascript">

//����ToolBar
function CreateToolBar(){
  SetToolBarHandle(true);
  SetToolBarHeight(20);

	  AddToolBarItemEx("ReturnBtn","../../common/images/toolbar/back.gif","","",'<bean:message key="toolbar.return"/>',"65","0","returnMethod()");
	  
	  <%-- �Ƿ��п�д��Ȩ��--%>
	  <logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="nmaintContractModify" value="Y"> 
	      AddToolBarItemEx("AuditBtn","../../common/images/toolbar/save.gif","","","�� ��","65","1","saveMethod()");
	  </logic:equal>

  window.document.getElementById("toolbar").innerHTML=GenToolBar("TabToolBar_Manage","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");
}

//ȥ���ո�
String.prototype.trim = function(){return this.replace(/(^\s*)|(\s*$)/g,"");}

//�ر�
function closeMethod(){
  window.close();
}
//����
function returnMethod(){
	window.location = '<html:rewrite page="/maintContractModifySearchAction.do"/>?method=toSearchRecord';
}

//����
function saveMethod(){
	var maintDivision=document.getElementById("maintDivision").value;
	if(maintDivision.trim()=="" || maintDivision.trim()=="%"){
		alert("��ѡ�� ����ά���ֲ���");
		return;
	}
	
	var maintStation=document.getElementById("maintStation").value;
	if(maintStation.trim()=="" || maintStation.trim()=="%"){
		alert("��ѡ�� ����ά��վ��");
		return;
	}
	
	var contractSdate=document.getElementById("contractSdate").value;
	if(contractSdate.trim()==""){
		alert("��ͬ��ʼ����  ����Ϊ�գ�");
		return;
	}
	
	var contractEdate=document.getElementById("contractEdate").value;
	if(contractEdate.trim()==""){
		alert("��ͬ��������  ����Ϊ�գ�");
		return;
	}
	
	//var assignedMainStation=document.getElementsByName("assignedMainStation");
	var elevatorNo=document.getElementsByName("elevatorNo");
	var mainEdate=document.getElementsByName("mainEdate");
	var iserrstr="";
	for(var i=0;i<mainEdate.length;i++){
		//if(assignedMainStation[i].value.trim()==""){
		//	iserrstr="�� "+(i+1)+"�� �´�ά��վ ����Ϊ�գ�";
		//	break;
		//}
		if(elevatorNo[i].value.trim()==""){
			iserrstr="�� "+(i+1)+"�� ���ݱ�� ����Ϊ�գ�";
			break;
		}

		if(mainEdate[i].value.trim()==""){
			iserrstr="�� "+(i+1)+"�� ά���������� ����Ϊ�գ�";
			break;
		}
	}
	if(iserrstr!=""){
		alert(iserrstr);
		return;
	}
	
	var contractTotal=document.getElementById("contractTotal").value;
	if(contractTotal.trim()==""){
		alert("��ͬ�ܶ� ����Ϊ�գ�");
		return;
	}
	document.maintContractModifyForm.submit();
}

//���������Ƿ���������
function checkthisvalue(obj){
  if(isNaN(obj.value)){
    alert("����������!");
    obj.value="0";
    obj.focus();
    return false;
  }
}

function pickStartDay(endDateId){
  var endDate = document.getElementById(endDateId).value;
  endDate = endDate == null || endDate == ""��? "2099-12-31" : endDate    
  var d = endDate.split('-');
  WdatePicker({isShowClear:false,maxDate:d[0]+'-'+d[1]+'-'+(Number(d[2])-1),readOnly:true});
}
function pickEndDay(startDateId){
	//var date = new Date();
	var startDate = document.getElementById(startDateId).value;
	//var todayDate = date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
	//var d = startDate < todayDate ? todayDate.split('-') : startDate.split('-');
	var d=startDate.split('-');
	WdatePicker({isShowClear:false,minDate:d[0]+'-'+d[1]+'-'+(Number(d[2])+1),readOnly:true});
}
//�����ͬ��Ч��
function setContractPeriod(){
  var sd = maintContractModifyForm.contractSdate.value.split("-");
  var ed = maintContractModifyForm.contractEdate.value.split("-");
  if(sd != "" && ed != ""){  
    var years = Number(ed[0])-Number(sd[0]);
    var months = years>0 ? 12*years - (Number(sd[1]) - Number(ed[1])) : Number(ed[1]) - Number(sd[1]);
    months = Number(ed[2])-Number(sd[2])>0 ? months+1 : months;  
    maintContractModifyForm.contractPeriod.value = months;     
  }
}

function setAssignedMainStation(objval){
	 var amst=document.getElementsByName("assignedMainStation");//�´�ά��վ
	 for(var i=0;i<amst.length;i++){
		 amst[i].value=objval;
	 }
	
}

//AJAX��̬��ʾά��վ
var req;
function EvenmoreUP(obj,listname){	
	var comid=obj.value;
	 var selectfreeid = document.getElementById(listname);
	if(comid!="" && comid!="%"){
		
		 if(window.XMLHttpRequest) {
			 req = new XMLHttpRequest();
		 }else if(window.ActiveXObject) {
			 req = new ActiveXObject("Microsoft.XMLHTTP");
		 }  //����response
		 
		 var url='<html:rewrite page="/maintContractModifyAction.do"/>?method=toStorageIDList&comid='+comid;//��ת·��
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
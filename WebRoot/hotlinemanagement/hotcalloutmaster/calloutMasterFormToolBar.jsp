<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ page import="com.gzunicorn.common.util.SysConfig" %>
<script language="javascript">

//����ToolBar
function CreateToolBar(){
  SetToolBarHandle(true);
  SetToolBarHeight(20);

  <logic:present name="isopenshow">
  	AddToolBarItemEx("closeBtn","../../common/images/toolbar/close.gif","","",'�� ��',"65","0","closeMethod()");
  </logic:present>
  <logic:notPresent name="isopenshow">
  	AddToolBarItemEx("ReturnBtn","../../common/images/toolbar/back.gif","","",'<bean:message key="toolbar.return"/>',"65","0","returnMethod()");
  </logic:notPresent>
  
  <logic:equal name="typejsp" value="add">
 AddToolBarItemEx("SaveBtn","../../common/images/toolbar/save.gif","","",'<bean:message key="toolbar.save"/>',"65","1","saveMethod('add','N')");
 AddToolBarItemEx("SaveReturnBtn","../../common/images/toolbar/save_back.gif","","","�����ύ������","120","1","saveMethod('add','Y')");
 </logic:equal>
 <logic:equal name="typejsp" value="mondity">
 AddToolBarItemEx("SaveBtn","../../common/images/toolbar/save.gif","","",'<bean:message key="toolbar.save"/>',"65","1","saveMethod('mondity','N')");
 AddToolBarItemEx("SaveReturnBtn","../../common/images/toolbar/save_back.gif","","","�����ύ������","120","1","saveMethod('mondity','Y')");
 </logic:equal>
 <logic:equal name="typejsp" value="sh">
 AddToolBarItemEx("ReturnBtn","../../common/images/toolbar/save.gif","","","���ͨ��","80","1","Auditing('isSendSms','Y')");
 AddToolBarItemEx("ReturnBtn","../../common/images/toolbar/save.gif","","","����ά�޵�","100","1","RAuditing('isSendSms','R')");
 </logic:equal>
 <logic:equal name="typejsp" value="ps">
 AddToolBarItemEx("ReturnBtn","../../common/images/toolbar/save.gif","","","����","80","1","saveps('N')");
 AddToolBarItemEx("ReturnBtn","../../common/images/toolbar/save.gif","","","���沢����","100","1","saveps('Y')");
 </logic:equal>
  window.document.getElementById("toolbar").innerHTML=GenToolBar("TabToolBar_Manage","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");
}

function delselect(){
	document.getElementById("hftId").value="";
	document.getElementById("hftDesc").value="";
}
//�ر�
function closeMethod(){
  window.close();
}

//����
function returnMethod(){
  window.location = '<html:rewrite page="/hotphoneSearchAction.do"/>?method=toSearchRecord';
}

//����
function saveMethod(typejsp,isreturn){	
	if(checkColumnInput(CalloutMasterjxForm)){
		//var storageid=document.getElementById("storageId").value;
		var assignObject=document.getElementById("assignObject").value;
		if(assignObject==''){
			alert("��ѡ�� �ɹ�����");
		}else{
			document.CalloutMasterjxForm.typejsp.value = typejsp;
			document.CalloutMasterjxForm.isreturn.value = isreturn;
			document.CalloutMasterjxForm.submit();
		}
	}
}
//���ؼ��޵�
function RAuditing(value,isreturn){
	var bhAuditRem=document.getElementById("bhAuditRem").value;
	if(bhAuditRem==""){
		alert("�������, ����Ϊ�գ�");
		return;
	}
	
	var calloutMasterNo=document.getElementById("calloutMasterNo").value;
	if(confirm("ȷ�ϲ��� "+calloutMasterNo+" ���޵���")){
		document.CalloutMasterjxForm.action='<html:rewrite page="/hotphoneAction.do"/>?typejsp=shsave&id='+calloutMasterNo+'&value=N&isreturn='+isreturn;
		document.CalloutMasterjxForm.submit();
		document.CalloutMasterjxForm.action='/hotphoneAction.do';
	}
}
function Auditing(value,isreturn){
	var value1;
	var value2=document.getElementsByName(value);
	var calloutMasterNo=document.getElementById("calloutMasterNo").value;
	var auditRem=document.getElementById("auditRem").value;
	for(var i=0;i<value2.length;i++){
		if(value2[i].checked==true){
			value1=value2[i].value;
		}
	}
	 //window.location = '<html:rewrite page="/hotphoneAction.do"/>?typejsp=shsave&id='+calloutMasterNo+'&value='+value1+'&isreturn='+isreturn+'&auditRem='+auditRem;
	 document.CalloutMasterjxForm.action='<html:rewrite page="/hotphoneAction.do"/>?typejsp=shsave&id='+calloutMasterNo+'&value='+value1+'&isreturn='+isreturn;
	 document.CalloutMasterjxForm.submit();
	 document.CalloutMasterjxForm.action='/hotphoneAction.do';
}

function saveps(isreturn){
	document.CalloutMasterjxForm.isreturn.value=isreturn;
	document.CalloutMasterjxForm.typejsp.value='pssave';
	document.CalloutMasterjxForm.submit();
}


//AJAX��̬��ʾά��վ
var phonearr=new Array();
var req;
function Evenmore(storageid){

	 var selectfreeid = document.getElementById("assignObject");

	if(storageid!="" && storageid!="%"){
		
		 if(window.XMLHttpRequest) {
			 req = new XMLHttpRequest();
		 }else if(window.ActiveXObject) {
			 req = new ActiveXObject("Microsoft.XMLHTTP");
		 }  //����response
		 
		 var url='<html:rewrite page="/hotphoneAction.do"/>?method=toStorageIDList&storageid='+storageid;//��ת·��
		 req.open("post",url,true);//post �첽
		 req.onreadystatechange=function getnextlist(){
			
				if(req.readyState==4 && req.status==200){
				 var xmlDOM=req.responseXML;
				 var rows=xmlDOM.getElementsByTagName('rows');
				 if(rows!=null){
					    selectfreeid.options.length=0;
					    selectfreeid.add(new Option("��ѡ��",""));	

				 		for(var i=0;i<rows.length;i++){
							var colNodes = rows[i].childNodes;
							if(colNodes != null){
								var colLen = colNodes.length;
							 	phonearr=new Array(colLen);
							 	
								for(var j=0;j<colLen;j++){
									var freename= colNodes[j].getAttribute("name");
									var freeid= colNodes[j].getAttribute("value");
									var freephone= colNodes[j].getAttribute("phone");
									selectfreeid.add(new Option(freename,freeid));
									
									var objs=new Object;
									objs.id=freeid;
									objs.phone=freephone;
									phonearr[j]=objs;
									
					            }
				             }
				 		}
				 		
					 	//����������ֵ
					 	var maintpersonnel=document.getElementById("maintPersonnel").value;
					    var aobj=document.getElementById("assignObject");
					    for(var a=0;a<aobj.length;a++){
					    	if(aobj[a].value==maintpersonnel){
					    		aobj[a].selected=true;
					    		break;
					    	}
					    }
					    //alert(phonearr[0].id+","+phonearr[1].phone+"; "+phonearr.length);
					    
				 	}
				
				}
		 };//�ص�����
		 req.send(null);//������
	}else{		
		selectfreeid.options.length=0;
		selectfreeid.add(new Option("��ѡ��",""));
	}
}

//���������仯�����ĵ绰
function changeuser(obj){
	var phone=document.getElementById("phone");
	if(obj.value==""){
		phone.value="";
	}else{
		for(var i=0;i<phonearr.length;i++){
			var objs=phonearr[i];
			if(objs.id==obj.value){
				phone.value=objs.phone;
			}
		}
	}
}

//��ʾά��վ������
function showstorageid(val){
	var sel=0;
	
	var selectfreeid = document.getElementById("assignstorageId");
	selectfreeid.options.length=0;
	//if(val==''){
    	selectfreeid.add(new Option("��ѡ��",""));	
	//}
    <logic:iterate id="element" name="storageidList">
	    var sid='<bean:write name="element" property="storageid"/>';
	    var sname='<bean:write name="element" property="storagename"/>';
	    
	    //if(val==''){
	    //	selectfreeid.add(new Option(sname,sid));
	    //}else if(val==sid){
	    //	selectfreeid.add(new Option(sname,sid));
	    //	if(sid!='10098'){
	    //		selectfreeid.add(new Option("����վ","10098"));
	    //	}
	    //}
	    
	    selectfreeid.add(new Option(sname,sid));

	    sel++;
	    if(val==sid){
	    	selectfreeid[sel].selected=true;//ѡ��������
	    }
	    
    </logic:iterate>
    
    
    
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
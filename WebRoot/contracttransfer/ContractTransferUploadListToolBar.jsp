 <%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="com.gzunicorn.common.util.SysConfig" %>
<script language="javascript" src="<html:rewrite page="/common/javascript/jquery-1.9.1.min.js"/>"></script>

<script language="javascript">
//����ToolBar
function CreateToolBar(){
  SetToolBarHandle(true);
  SetToolBarHeight(20);
  
  AddToolBarItemEx("SearchBtn","../../common/images/toolbar/search.gif","","",'<bean:message key="toolbar.search"/>',"65","0","searchMethod()");
  AddToolBarItemEx("ViewBtn","../../common/images/toolbar/view.gif","","",'<bean:message key="toolbar.read"/>',"65","1","viewMethod()");
  
  <%-- �Ƿ��п�д��Ȩ��--%>
  <logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="nContractTransferUpload" value="Y">
 	AddToolBarItemEx("ReferBtn","../../common/images/toolbar/add.gif","","",'�ϴ�',"65","1","uploadMethod()");
  	AddToolBarItemEx("ReferBtn","../../common/images/toolbar/digital_confirm.gif","","",'�����ϴ�',"80","1","uploadMethod2()");
  	AddToolBarItemEx("ReferBtn","../../common/images/toolbar/edit.gif","","",'��������',"80","1","feedbackMethod()");
    AddToolBarItemEx("OutBtn","../../common/images/toolbar/disk_default.gif","","",'��������',"80","1","outMethod()");
    <logic:equal name="transferflag" value="0">
    AddToolBarItemEx("ReferBtn","../../common/images/toolbar/edit.gif","","",'����ת��',"80","1","transferMethod()");
    </logic:equal>
  </logic:equal>
  
  window.document.getElementById("toolbar").innerHTML=GenToolBar("TabToolBar_Manage","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");
}

//��ѯ
function searchMethod(){
	serveTableForm.target = "_self";
	document.serveTableForm.submit();
}

//����
function outMethod(){
	var msg = saveMethod("toPrepareOutRecord");
	if(msg!=""){
		alert(msg);
	}
}

//�ϴ�
function uploadMethod(){
	var index = getIndex();	
	toDoMethod(index,"toPrepareAddRecord","","��ѡ����Ҫ�ϴ��ļ�¼��");
}

//�����ϴ�
function uploadMethod2(){
	var msg = saveMethod("toPrepareAddRecord");
	if(msg!=""){
		alert(msg);
	}
}

//����
function feedbackMethod(){
	var msg = saveMethod("toPrepareFeedBackRecord");
	if(msg!=""){
		alert(msg);
	}
}

//����ת��
function transferMethod(){
	var msg = saveMethod("toPrepareTransferRecord");
	if(msg!=""){
		alert(msg);
	}
}

function saveMethod(method){
	var ids = serveTableForm.ids;
	var transfeSubmitType = document.getElementsByName("transfeSubmitType");
	var fileType = document.getElementsByName("fileType");
	var fFlag = document.getElementsByName("fFlag");
	var billnostr = "";
	var msg = "";
	var isflag = "Y";
	var l = ids.length;
	if(l){
		for(var i=0;i<ids.length;i++){
			if(ids[i].checked == true){
				msg = "1";
				billnostr += ids[i].value+",";
				if(method=="toPrepareOutRecord"||method=="toPrepareTransferRecord"){
					if(transfeSubmitType[i].value=="Y"||transfeSubmitType[i].value=="R"){
						msg = ids[i].value+"�����ύ�򲵻ؼ�¼�����ܽ��в���"
						return msg;
					}
				}else{
					if(transfeSubmitType[i].value=="Y"){
						msg = ids[i].value+"�����ύ��¼�����ܽ��в���"
						return msg;
					}
					
					for(var j=i+1;j<ids.length;j++){
						if(ids[j].checked == true){
							if(fileType[i].value != fileType[j].value){
								msg = ids[j].value+"�������Ͳ�һ�£����ܽ��в���";
								return msg;
							}
						}
					}
				}
				
				if(method=="toPrepareAddRecord"){
					isflag = "N";
					if(fFlag[i].value != "N"){
						msg = ids[i].value+"���ϴ����������ܽ��������ϴ�����";
						return msg;
					}
				}
				
			}
		}
		if(msg == "1"){
			billnostr = billnostr.substring(0,billnostr.length-1);
			window.location = '<html:rewrite page="/ContractTransferUploadAction.do"/>?id='+billnostr+'&isflag='+isflag+'&method='+method;
			msg = "";
		}else{
			alert("��ѡ����Ҫ�����ļ�¼��");
		}
	}else{
		if(method=="toPrepareOutRecord"){
			if(transfeSubmitType[0].value=="Y"||transfeSubmitType[0].value=="R"){
				msg = ids.value+"�����ύ�򲵻ؼ�¼�����ܽ��в��ز���"
			}
		}else{
			if(transfeSubmitType[0].value=="Y"){
				msg = ids.value+"�����ύ��¼�����ܽ��в���"
			}
		}
		if(msg == ""){
			billnostr = ids.value;
			window.location = '<html:rewrite page="/ContractTransferUploadAction.do"/>?id='+billnostr+'&isflag='+isflag+'&method='+method;
		}
	}
	
	return msg;
	
}

//�鿴
function viewMethod(){
	var index = getIndex();	
	toDoMethod(index,"toDisplayRecord","","��ѡ����Ҫ�鿴�ļ�¼��");
}


//��ת����
function toDoMethod(index,method,params,alertMsg){
	if(params==null){
		params='';
	}
	if(index >= 0){
		var domsg = "";
		
		if(method=="toPrepareOutRecord"){
			domsg = outcheck(index);
		}else if(method=="toDisplayRecord"){
			domsg = "";
		}else{
			domsg = feedbackcheck(index);
		}
		
		if(domsg==""){
			window.location = '<html:rewrite page="/ContractTransferUploadAction.do"/>?id='+getVal('ids',index)+'&method='+method+params;	
		}else{
			alert(domsg);
		}
	}else if(alertMsg && alertMsg != ""){
		alert(alertMsg);
	}
}

//��ȡѡ�м�¼�±�
function getIndex(){
	if(serveTableForm.ids){
		var ids = serveTableForm.ids;
		if(ids.length == null){
			return 0;
		}
		for(var i=0;i<ids.length;i++){
			if(ids[i].checked == true){
				return i;
			}
		}		
	}
	return -1;	
}

//����name��ѡ���±��ȡԪ�ص�ֵ
function getVal(name,index){
	var obj = eval("serveTableForm."+name);
	if(obj && obj.length){
		obj = obj[index];
	}
	return obj ? obj.value : null;
}

//���ؼ���
function outcheck(index){
	var transfeSubmitType = document.getElementsByName("transfeSubmitType");
	if(transfeSubmitType[index].value=="Y"||transfeSubmitType[index].value=="R"){
		return "��ѡ��δ�ύ�ļ�¼���в��أ�";
	}else{
		return "";
	}
}

//��������
function feedbackcheck(index){
	var transfeSubmitType = document.getElementsByName("transfeSubmitType");
	
	if(transfeSubmitType[index].value=="Y"){
		return "�ü�¼���ύ�����ܽ��в�����";
	}else{
		return "";
	}
}



//AJAX��̬��ʾά��վ
var req;
function Evenmore(obj,listname){	
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
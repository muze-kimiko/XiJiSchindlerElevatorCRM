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

	  <logic:present name="workisdisplay">  
		AddToolBarItemEx("colseBtn","../../common/images/toolbar/close.gif","","",'�� ��',"65","0","closeMethod()");
		//AddToolBarItemEx("colseBtn","../../common/images/toolbar/close.gif","","",'�� ��',"65","0","window.close()");
	</logic:present>
	<logic:notPresent name="workisdisplay">  
		  AddToolBarItemEx("ReturnBtn","../../common/images/toolbar/back.gif","","",'<bean:message key="toolbar.return"/>',"65","0","returnMethod()");
		
		  <logic:notPresent name="display">  
		  <%-- �Ƿ��п�д��Ȩ��--%>
		  <logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="nmaintcontractdelay" value="Y"> 
		    AddToolBarItemEx("SaveBtn","../../common/images/toolbar/save.gif","","",'<bean:message key="toolbar.save"/>',"65","1","saveMethod()");   
		    AddToolBarItemEx("SaveReturnBtn","../../common/images/toolbar/save_back.gif","","",'<bean:message key="tollbar.saveandrefer"/>',"120","1","saveReturnMethod()");
		  
		    <logic:present name="isclosework">  
		  		AddToolBarItemEx("DelBtn","../../common/images/toolbar/delete.gif","","",'<bean:message key="toolbar.delete"/>',"65","1","deleteMethod()"); 
		  	</logic:present>  
		  </logic:equal>
		  </logic:notPresent>
	</logic:notPresent>

  window.document.getElementById("toolbar").innerHTML=GenToolBar("TabToolBar_Manage","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");

}

//Ajax ɾ��
var req;
function deleteMethod(){

	if(confirm("ȷ��ɾ���ü�¼���ݣ�")){

		 if(window.XMLHttpRequest) {
			 req = new XMLHttpRequest();
		 }else if(window.ActiveXObject) {
			 req = new ActiveXObject("Microsoft.XMLHTTP");
		 }  //����response
		 
		 var deljnlno=document.getElementById("deljnlno").value;
		 var url='<html:rewrite page="/maintContractDelayAction.do"/>?method=toAjaxDeleteRecord&jnlnostr='+deljnlno;//��ת·��
		 req.open("post",url,false);//true �첽;false ͬ��
		 req.onreadystatechange=function getnextlist(){
			
		 	if(req.readyState==4 && req.status==200){
				 var xmlDOM=req.responseXML;
				 var rows=xmlDOM.getElementsByTagName("rows");
				 if(rows!=null){
				 	//������
						var colNodes = rows[0].childNodes;
						//������
						if(colNodes != null){
							var freeid = colNodes[0].getAttribute("name");
							var freename = colNodes[0].getAttribute("value");
							if(freename=="Y"){
								alert("ɾ�����ݳɹ���\n���ȷ����ť���ر�ҳ�档");
								closeMethod();
							}else{
								alert("ɾ������ʧ�ܣ�");
							}
			            }
				 		
				 }
			}
		 };//�ص�����
		 req.send(null);//������
	}
}

//�ر�
function closeMethod(){
  window.close();
}
//����
function returnMethod(){
  window.location = '<html:rewrite page="/maintContractDelaySearchAction.do"/>?method=toSearchRecord';
}

//ȥ���ո�
String.prototype.trim = function(){return this.replace(/(^\s*)|(\s*$)/g,"");}

//����
function saveMethod(){

  if(checkEmpty("scrollTable","titleRow")){
	  var rem=document.getElementById("rem").value;
	  if(rem.trim()==""){
		  alert("��ע ����Ϊ�գ�");
	  }else{
		  document.maintContractDelayForm.isreturn.value = "N";
		  document.maintContractDelayForm.submit();
	  }
   
  }
  
}

//���淵��
function saveReturnMethod(){
  if(checkEmpty("scrollTable","titleRow")){
	  var rem=document.getElementById("rem").value;
	  if(rem.trim()==""){
		  alert("��ע ����Ϊ�գ�");
	  }else{
		  document.maintContractDelayForm.isreturn.value = "Y";
		  document.maintContractDelayForm.submit();
	  }
    } 
}

function pickEndDay(mainedate){
    /**
	var date = new Date();
	var startDate = sdate;
	var todayDate = date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
	var d = startDate < todayDate ? todayDate.split('-') : startDate.split('-');
	WdatePicker({isShowClear:false,minDate:d[0]+'-'+d[1]+'-'+(Number(d[2])+1),readOnly:true});
	*/
	//Ĭ�� �ӱ���������=ά���������� �� 3����
	var md=mainedate.split('-');
	var d = new Date(md[0]+"/"+md[1]+"/"+md[2]);
	//��һ���£�ͬ�����Լ�һ�죺getDate()+1����һ�꣺getYear()+1
	d.setMonth(d.getMonth() + 1 + 3);
	var edate=d.getFullYear()+"-"+d.getMonth()+"-"+d.getDate();
	var dt = edate.split('-');

 	WdatePicker({minDate:dt[0]+'-'+dt[1]+'-'+(Number(dt[2])+1),readOnly:true});
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
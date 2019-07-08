<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ page import="com.gzunicorn.common.util.SysConfig" %>

<script language="javascript" defer="defer" src="<html:rewrite forward='DatePickerJS'/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/checkinput.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/dynamictable.js"/>"></script>

<script language="javascript">

//����ToolBar
function CreateToolBar(){
  SetToolBarHandle(true);
  SetToolBarHeight(20);

  <logic:equal name="typejsp" value="Yes">
    AddToolBarItemEx("SaveSubmitBtn","../../common/images/toolbar/close.gif","","",'�ر�',"60","0","window.close()");
  </logic:equal>
  <logic:notPresent name="typejsp">
    AddToolBarItemEx("ReturnBtn","../../common/images/toolbar/back.gif","","",'<bean:message key="toolbar.return"/>',"65","0","returnMethod()");
  </logic:notPresent>
  <logic:notPresent name="display">  
	  <%-- �Ƿ��п�д��Ȩ��--%>
	  <logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="ncontractparagraphmanage" value="Y"> 
	    AddToolBarItemEx("SaveBtn","../../common/images/toolbar/save.gif","","",'<bean:message key="toolbar.save"/>',"65","1","saveMethod()");   
	    <logic:notPresent name="Backfill">
	      AddToolBarItemEx("SaveReturnBtn","../../common/images/toolbar/save_back.gif","","",'<bean:message key="tollbar.saveandrefer"/>',"120","1","saveReturnMethod()");
	    </logic:notPresent>
	  </logic:equal>
  </logic:notPresent>
  
  window.document.getElementById("toolbar").innerHTML=GenToolBar("TabToolBar_Manage","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");

}
//����
function returnMethod(){
  window.location = '<html:rewrite page="/contractParagraphManageSearchAction.do"/>?method=toSearchRecord';
}

//����
function saveMethod(){
  if(checkColumnInput(contractParagraphManageForm)){
	  if(document.getElementById("paragraphMoney")!=null && !isNaN(document.getElementById("paragraphMoney").value)){
		  if(parseFloat(document.getElementById("paragraphMoney").value)<=${contractBean.arrearsMoney}){
		  	var zero=0;
		  	if(zero!=${contractBean.arrearsMoney}){
			  document.contractParagraphManageForm.isreturn.value = "N";
			  document.contractParagraphManageForm.submitType.value = "N";
			  document.contractParagraphManageForm.submit();
			}else{
		    	alert("�ü�¼�Ѿ�û��Ƿ���");
		    }
		  }else{
			  alert("������ܴ���Ƿ���");
		  }
	  }else{
		  alert("������ֻ���������֣�");
	  }
    
  }
  
}

//���淵��
function saveReturnMethod(){
  inputTextTrim();  
  if(checkColumnInput(contractParagraphManageForm)){
	  if(document.getElementById("paragraphMoney")!=null && !isNaN(document.getElementById("paragraphMoney").value)){
		  if(parseFloat(document.getElementById("paragraphMoney").value)<=${contractBean.arrearsMoney}){
		  	var zero=0;
		  	if(zero!=${contractBean.arrearsMoney}){
			  document.contractParagraphManageForm.isreturn.value = "Y";
		      document.contractParagraphManageForm.submitType.value = "Y";
		      document.contractParagraphManageForm.submit();
		    }else{
		    	alert("�ü�¼�Ѿ�û��Ƿ���");
		    }
		  }else{
			  alert("������ܴ���Ƿ���");
		  }
	  }else{
		  alert("������ֻ���������֣�");
	  }
      
    } 
}

//������Ƿ�Ϊ����,���������븺�źͿ���������
function f_check_number3(){
 	if((event.keyCode>=48 && event.keyCode<=57) || event.keyCode==46 ){
 	}else{
		event.keyCode=0;
  	}
}

function judgePreMoney(object,receivables){
	var objv=object.value.substring(0,object.value.length-1);
	if(!isNaN(object.value)){
		if(parseFloat(object.value)>receivables){
			alert("������ܴ���Ƿ���");
			object.value=0;
		}
	}else{
		alert("������ֻ���������֣�");
		object.value=0;
	}
}

function lessToday(object){
	var date = new Date();
	var yyyy = date.getFullYear();
	var mm = date.getMonth()+1;
	mm = mm > 10 ? mm : "0"+mm;
	var dd = date.getDate() < 10 ? "0"+date.getDate() : date.getDate();
	var todayDate = "" + yyyy + mm + dd;
	var invoiceDate=object.value.replace(/-/g,"");
	if(Number(todayDate)<Number(invoiceDate)){
		alert("��Ʊ���ڲ��ܴ��ڵ�ǰ���ڣ�");
		object.value=yyyy+"-"+mm+"-"+dd;
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
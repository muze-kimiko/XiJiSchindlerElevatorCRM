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

  AddToolBarItemEx("ReturnBtn","../../common/images/toolbar/back.gif","","",'<bean:message key="toolbar.return"/>',"65","0","returnMethod()"); 
 <logic:notPresent name="display">  
  <%-- �Ƿ��п�д��Ȩ��--%>
  <logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="naccessoriesrequisitionmaintenance" value="Y"> 
    AddToolBarItemEx("SaveReturnBtn","../../common/images/toolbar/save_back.gif","","",'<bean:message key="toolbar.returnsave"/>',"80","1","saveReturnMethod()");
  </logic:equal>
 </logic:notPresent>
  window.document.getElementById("toolbar").innerHTML=GenToolBar("TabToolBar_Manage","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");
}
//����
function returnMethod(){
  window.location = '<html:rewrite page="/accessoriesRequisitionMaintenanceSearchAction.do"/>?method=toSearchRecord';
}

//ȥ���ո�
String.prototype.trim = function(){return this.replace(/(^\s*)|(\s*$)/g,"");}

//���淵��
function saveReturnMethod(){
	var msir=document.getElementsByName("isAgree");
	if(msir[0].value!=null && msir[0].value!=""){
		
		var isagree=msir[0].value;

		var picauditRem=document.getElementById("picauditRem").value;
		if(isagree=="N" && picauditRem.trim()==""){
			alert("ά�������������� ����Ϊ�գ�");
			return;
		}else if(isagree=="Y"){
			/*
			var isCharges=document.getElementById("isCharges").value;
			if(isCharges==""){
				alert("��ѡ�� �����ж��Ƿ��շѣ�");
				return;
			}
			var isCharges=document.getElementById("isCharges").value;
			if(isCharges==""){
				alert("��ѡ�� �Ƿ��շѣ�");
				return;
			}
			*/
			var instock=document.getElementById("instock").value;
			if(instock==""){
				alert("��ѡ�� �������Ƿ��п�棡");
				return;
			}
			/**
			var money1=document.getElementById("money1").value;
			if(isCharges=="Y" && money1.trim()==""){
				alert("�����ж���� ����Ϊ�գ�");
				return;
			}
			
			if(money1.trim()!="" && isNaN(money1.trim())){
				alert("�����ж���� ֻ���������֣�");
				return;
			}
			*/
		}
		
	    document.accessoriesRequisitionMForm.isreturn.value = "Y";
	    document.accessoriesRequisitionMForm.submit();
	}else{
   		alert("�Ƿ�ͬ�ⲻ��Ϊ��");
   }
}





//����
function downloadFile(id){
	var uri = '<html:rewrite page="/accessoriesRequisitionMaintenanceAction.do"/>?method=toDownLoadFiles';
	var name1=encodeURI(id);
	name1=encodeURI(name1);
		uri +='&filename='+ name1;
		uri +='&folder=AccessoriesRequisition.file.upload.folder';
	    
		window.location = uri;
	//window.open(url);
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
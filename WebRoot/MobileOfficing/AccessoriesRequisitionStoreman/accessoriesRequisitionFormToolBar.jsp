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
  <logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="naccessoriesrequisitionstoreman" value="Y"> 
    AddToolBarItemEx("SaveReturnBtn","../../common/images/toolbar/save_back.gif","","",'<bean:message key="toolbar.returnsave"/>',"80","1","saveReturnMethod()");
  </logic:equal>
  </logic:notPresent>
  window.document.getElementById("toolbar").innerHTML=GenToolBar("TabToolBar_Manage","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");
}
//����
function returnMethod(){
  window.location = '<html:rewrite page="/accessoriesRequisitionStoremanSearchAction.do"/>?method=toSearchRecord';
}

//ȥ���ո�
String.prototype.trim = function(){return this.replace(/(^\s*)|(\s*$)/g,"");}

//���淵��
function saveReturnMethod(){
	
	var msir=document.getElementsByName("wmIsAgree");
	if(msir[0].value!=null&&msir[0].value!=""){
		
		var isagree=msir[0].value;

		var wmRem=document.getElementById("wmRem").value;
		if(isagree=="N" && wmRem.trim()==""){
			alert("��������Ա������ ����Ϊ�գ�");
			return;
		}else if(isagree=="Y"){
			var newNo=document.getElementById("newNo").value;
			if(newNo.trim()==""){
				alert("�¼���� �¼�����/�ͺţ�");
				return;
			}
			
			var wmPayment=document.getElementById("wmPayment").value;
			if(wmPayment==""){
				alert("��ѡ�� ��ȡ��ʽ��");
				return;
			}
			/*
			var isCharges=document.getElementById("isCharges").value;
			if(isCharges==""){
				alert("��ѡ�� �����ж��Ƿ��շѣ�");
				return;
			}
			*/
			var instock=document.getElementById("instock").value;
			if(instock==""){
				alert("��ѡ�� �������Ƿ��п�棡");
				return;
			}
			/*
			var money2=document.getElementById("money2").value;
			if(isCharges=="Y" && money2.trim()==""){
				alert("�շѽ�� ����Ϊ�գ�");
				return;
			}
			if(money2.trim()!="" && isNaN(money2.trim())){
				alert("�շѽ�� ֻ���������֣�");
				return;
			}
			*/
		}
		
	    document.accessoriesRequisitionSForm.isreturn.value = "Y";
	    document.accessoriesRequisitionSForm.submit();
	}else{
   		alert("�Ƿ�ͬ�� ����Ϊ��");
   }
}

//����
function downloadFile(id){
	var uri = '<html:rewrite page="/accessoriesRequisitionStoremanAction.do"/>?method=toDownLoadFiles';
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
<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<link rel="stylesheet" type="text/css" href="<html:rewrite forward='ExtCSS'/>" />
<script type="text/javascript" src="<html:rewrite forward='ExtBase'/>"></script>
<script type="text/javascript" src="<html:rewrite forward='ExtAll'/>"></script>

<%--link rel="stylesheet" type="text/css" href="<html:rewrite forward='queryCSS'/>">
<link rel="stylesheet" type="text/css" href="<html:rewrite forward='publicCSS'/>">
<link rel="stylesheet" type="text/css" href="<html:rewrite forward='toolbarCSS'/>">
<link rel="stylesheet" type="text/css" href="<html:rewrite forward='stylessCSS'/>"--%>
<style type="text/css">
<!--
.STYLE5 {color: #FF0000}
.STYLE6 {color: #0000FF}
.STYLE7 {color: #0099FF}
-->
</style>
</head>
<script>
function show(thisObj,type){
	var nid=getPrefix(thisObj.id,"_Task");
	var nobj=document.getElementById(nid);
	if(nobj!=null){
		if(nobj.style.display=="none"){
			nobj.style.display="";
		}else{
			nobj.style.display="none";
		}
	}
}
function getPrefix(arg,postfix){
	var aa=arg.substring(0,arg.lastIndexOf("_"))+postfix;
	//alert(aa);
	return aa;
}
var panals=new Array();
function gotoPost(did,po){
		alert(panals.length);
		alert(panals[did].collapsed);
		if(panals[did].collapsed){
			alert("ddddd");
			panals[did].expand(true);
		}
		//Ext.expand.show();
		document.getElementById("hida").href=po;
		document.getElementById("hida").click();
	
}
</script>

<body>
<span class="STYLE5">Ҫ���������������ݿ����Զ��仯</span>
<a href="" id="hida"></a>
<a href="#agp">agp</a><br><a href="#info1">info1</a><br><a href="#info2">info2</a><br>

<a href="javascript:gotoPost(0,'#agp')">info3333</a>
<a href="javascript:gotoPost(1,'#info1')">info4444</a>
<a href="javascript:gotoPost(2,'#info2')">info5555</a>
<div id="AgentGoodsPlan"></div>
<div id="InfoIssue"></div>
<div id="InfoIssue2"></div>
<div id="hidContext" >
<div align="center" >
<div id="M_Flow" style="width:98%;" >
		<div id="M_Flow_Title" class="STYLE7" onclick="show(this,0)"  style=" width:100%; background-color:#D9E3E6" class="x-tab">��������</div>
		<div id="M_Flow_Task" style="display:auto">
			<div id="M_Flow_AgentGoodsPlan">
				<table><tr><td colspan="2">
 				<div id="M_Flow_AgentGoodsPlan_Title" onClick="show(this,1)"  style=" width:100%; background-color:#E6F2FF" class="x-tab">
						<table><tr><td>���̣�������Ҫ���ƻ�</td>
									<td>    </td>
									<td><div id="F_a">������10</div></td>
									<td bgcolor="#FF0000" ><div id="F_a">��������5</div></td>
									<td bgcolor="#0000FF" ><div id="F_a">��������5</div></td>
									</tr></table>
				</div>
				</td></tr>
				<tr><td width="10px">&nbsp;</td>
				<td>
				<div id="M_Flow_AgentGoodsPlan_Task">
 				<div id="M_Flow_AgentGoodsPlan_Private">
   					<div id="M_Flow_AgentGoodsPlan_Private_Title" onClick="show(this,2)">�������ͣ���������(<span class="STYLE5">5</span>)</div>
   					<div id="M_Flow_AgentGoodsPlan_Private_Task" style="display:auto">
						<table width="100%" border="1">
						  <tr>
							<td width="35%"><div align="center">��������</div></td>
							<td width="30%"><div align="center">����ʱ��</div></td>
							<td width="35%"><div align="center">�١�����</div></td>
						  </tr>
						  <tr>
							<td nowrap>��������Ա���</td>
							<td nowrap>2008-10-11 10:10:10 </td>
							<td nowrap><div><table><tr>
						  <td>&nbsp;<a href="#">��������</a>&nbsp;</td>
						  <td><a href="#">�鿴����</a>&nbsp;</td>
						  </tr></table></div></td>
						  </tr>
						  <tr>
							<td nowrap>BBBBBBBB</td>
							<td nowrap>&nbsp;</td>
							<td nowrap><div>
							  <table>
								<tr>
								  <td>&nbsp;<a href="#">��������</a>&nbsp;</td>
								  <td><a href="#">�鿴����</a>&nbsp;</td>
								</tr>
							  </table>
							  </div></td>
						  </tr>
						  <tr>
							<td nowrap>CCCCCCCCC</td>
							<td nowrap>&nbsp;</td>
							<td nowrap><table>
							  <tr>
								<td>&nbsp;<a href="#">��������</a>&nbsp;</td>
								<td><a href="#">�鿴����</a>&nbsp;</td>
							  </tr>
							</table>							</td>
						  </tr>
						  <tr>
							<td nowrap>DDDDDDDDD</td>
							<td nowrap>&nbsp;</td>
							<td nowrap><table>
							  <tr>
								<td>&nbsp;<a href="#">��������</a>&nbsp;</td>
								<td><a href="#">�鿴����</a>&nbsp;</td>
							  </tr>
							</table>							</td>
						  </tr>
						</table>
					</div>
				</div>
				<!--div style="height:3px">&nbsp;</div-->
				<div id="M_Flow_AgentGoodsPlan_Public">
  					<div id="M_Flow_AgentGoodsPlan_Public_Title"  onclick="show(this,2)"  style=" width:100%; background-color:#E6F2FF">�������ͣ���������(��������)(<span class="STYLE6">5</span>)</div>
  					<div id="M_Flow_AgentGoodsPlan_Public_Task" style="display:auto">
						<table width="100%" border="1">
						  <tr>
							<td>��������</td>
							<td>����ʱ��</td>
							<td>�١�����</td>
						  </tr>
						  <tr>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td><table>
							  <tr>
								<td>&nbsp;<a href="#">��������</a>&nbsp;</td>
								<td>&nbsp;<a href="#">�鿴����</a>&nbsp;</td>
							  </tr>
							</table>
							</td>
						  </tr>
						  <tr>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td><table>
							  <tr>
								<td>&nbsp;<a href="#">��������</a>&nbsp;</td>
								<td>&nbsp;<a href="#">�鿴����</a>&nbsp;</td>
							  </tr>
							</table></td>
						  </tr>
						  <tr>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td><table>
							  <tr>
								<td>&nbsp;<a href="#">��������</a>&nbsp;</td>
								<td>&nbsp;<a href="#">�鿴����</a>&nbsp;</td>
							  </tr>
							</table></td>
						  </tr>
						  <tr>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td><table>
							  <tr>
								<td>&nbsp;<a href="#">��������</a>&nbsp;</td>
								<td>&nbsp;<a href="#">�鿴����</a>&nbsp;</td>
							  </tr>
							</table></td>
						  </tr>
						</table>
					</div>
				  </div>
				</div>
				<div style="height:3px">&nbsp;</div>
			</div>
			</td></td></table>
			<div id="M_Flow_IssueInfo" >
			<table><tr><td colspan="2">
 				<div id="M_Flow_IssueInfo_Title" onClick="show(this,1)"  style=" width:100%; background-color:#E6F2FF">
						<table><tr><td>���̣���Ϣ����</td>
									<td>    </td>
									<td><div id="F_a">������10</div></td>
									<td bgcolor="#FF0000" ><div id="F_a">��������5</div></td>
									<td bgcolor="#0000FF" ><div id="F_a">��������5</div></td>
									</tr></table>
				</div>
				</td></tr>
				<tr><td width="10px">&nbsp;</td>
				<td>
				<div id="M_Flow_IssueInfo_Task">
 				<div id="M_Flow_IssueInfo_Private">
   					<div id="M_Flow_IssueInfo_Private_Title" onClick="show(this,2)">�������ͣ���������(<span class="STYLE5">5</span>)</div>
   					<div id="M_Flow_IssueInfo_Private_Task" style="display:auto">
						<table width="100%" border="1">
						  <tr>
							<td>��������</td>
							<td>����ʱ��</td>
							<td>�١�����</td>
						  </tr>
						  <tr>
							<td>AAAAAAAA</td>
							<td>&nbsp;</td>
							<td><div><table><tr>
						  <td>&nbsp;<a href="#">��������</a>&nbsp;</td>
						  <td>&nbsp;<a href="#">�鿴����</a>&nbsp;</td>
						  </tr></table></div></td>
						  </tr>
						  <tr>
							<td>BBBBBBBB</td>
							<td>&nbsp;</td>
							<td><div>
							  <table>
								<tr>
								  <td>&nbsp;<a href="#">��������</a>&nbsp;</td>
								  <td>&nbsp;<a href="#">�鿴����</a>&nbsp;</td>
								</tr>
							  </table>
							  </div></td>
						  </tr>
						  <tr>
							<td>CCCCCCCCC</td>
							<td>&nbsp;</td>
							<td><table>
							  <tr>
								<td>&nbsp;<a href="#">��������</a>&nbsp;</td>
								<td>&nbsp;<a href="#">�鿴����</a>&nbsp;</td>
							  </tr>
							</table>
							</td>
						  </tr>
						  <tr>
							<td>DDDDDDDDD</td>
							<td>&nbsp;</td>
							<td><table>
							  <tr>
								<td>&nbsp;<a href="#">��������</a>&nbsp;</td>
								<td>&nbsp;<a href="#">�鿴����</a>&nbsp;</td>
							  </tr>
							</table>
							</td>
						  </tr>
						</table>
					</div>
				</div>
				<!--div style="height:3px">&nbsp;</div-->
				<div id="M_Flow_IssueInfo_Public">
				
  					<div id="M_Flow_IssueInfo_Public_Title"  onclick="show(this,2)"  style=" width:100%; background-color:#E6F2FF">�������ͣ���������(��������)(<span class="STYLE6">5</span>)</div>
  					<div id="M_Flow_IssueInfo_Public_Task" style="display:auto">
						<table width="100%" border="1">
						  <tr>
							<td>��������</td>
							<td>����ʱ��</td>
							<td>�١�����</td>
						  </tr>
						  <tr>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td><table>
							  <tr>
								<td>&nbsp;<a href="#">��������</a>&nbsp;</td>
								<td>&nbsp;<a href="#">�鿴����</a>&nbsp;</td>
							  </tr>
							</table>
							</td>
						  </tr>
						  <tr>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td><table>
							  <tr>
								<td>&nbsp;<a href="#">��������</a>&nbsp;</td>
								<td>&nbsp;<a href="#">�鿴����</a>&nbsp;</td>
							  </tr>
							</table></td>
						  </tr>
						  <tr>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td><table>
							  <tr>
								<td>&nbsp;<a href="#">��������</a>&nbsp;</td>
								<td>&nbsp;<a href="#">�鿴����</a>&nbsp;</td>
							  </tr>
							</table></td>
						  </tr>
						  <tr>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td><table>
							  <tr>
								<td>&nbsp;<a href="#">��������</a>&nbsp;</td>
								<td>&nbsp;<a href="#">�鿴����</a>&nbsp;</td>
							  </tr>
							</table></td>
						  </tr>
						</table>
					</div>
				  </div>
				</div>
				<div style="height:3px">&nbsp;</div>
			</div>
			</td></td></table>
		</div>
		
		
</div>
</div>
</div>
<input type="button" value="test" onClick="tt()">
<script>
function tt(){
	document.getElementById("F_a11").innerHTML="300";
}
</script>

<script>
var ext=Ext.onReady(function(){
/*    var p = new Ext.Panel({
        title: '<table><tr><td><a name=\"agp\">���̣�������Ҫ���ƻ�</a></td><td>    </td><td><div id=\"F_a11\">������10</div></td><td bgcolor=\"#FF0000\" ><div id=\"F_a12\">��������5</div></td><td bgcolor=\"#0000FF\" ><div id=\"F_a13\">��������5</div></td></tr></table>',
        collapsible:true,
        renderTo: 'AgentGoodsPlan',
        html: document.getElementById('M_Flow_AgentGoodsPlan').innerHTML
    });
	panals[panals.length]=p;
	*/
/*	  var p2 = new Ext.Panel({
        title: '<table><tr><td><a name=\"info1\">���̣���Ϣ����</a></td><td>    </td><td><div id=\"F_a21\">������10</div></td><td bgcolor=\"#FF0000\" ><div id=\"F_a22\">��������5</div></td><td bgcolor=\"#0000FF\" ><div id=\"F_a23\">��������5</div></td></tr></table>',
        collapsible:true,
		collapsed:true,
        renderTo: 'InfoIssue',
        html: document.getElementById('M_Flow_IssueInfo').innerHTML
    });
	panals[panals.length]=p2;
*/	
	/*var p30 = new Ext.Panel({
        title: 'public',
        collapsible:true,
        renderTo: 'M_Flow_IssueInfo_Private',
        html: document.getElementById('M_Flow_IssueInfo_Private').innerHTML
    });
*/
	
/*	
	  var p3 = new Ext.Panel({
        title: '<table><tr><td><a name=\"info2\">���̣���Ϣ����2</a></td><td>    </td><td><div id=\"F_a21\">������10</div></td><td bgcolor=\"#FF0000\" ><div id=\"F_a22\">��������5</div></td><td bgcolor=\"#0000FF\" ><div id=\"F_a23\">��������5</div></td></tr></table>',
        collapsible:true,
        renderTo: 'InfoIssue2',
        html: document.getElementById('M_Flow_IssueInfo').innerHTML
    });
	panals[panals.length]=p3;
	*/
});
</script>
</body>
</html>

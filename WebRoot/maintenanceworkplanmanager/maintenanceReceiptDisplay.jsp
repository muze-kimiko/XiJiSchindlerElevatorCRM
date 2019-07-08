
<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>

<link rel="stylesheet" type="text/css" href="<html:rewrite forward='formCSS'/>">
<script language="javascript" src="<html:rewrite forward='pageJS'/>"></script>
<link href="/XJSCRM/common/css/bb.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="<html:rewrite page="/common/javascript/jquery-1.9.1.min.js"/>"></script>
<script type="text/javascript" src="<html:rewrite page="/common/javascript/highcharts/highcharts.js"/>"></script>

<br>
<html:errors/>

<logic:present name="mwpBean">
 <table width="100%" height="24" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr class="td_2">
      <td width="2" background="/images/line.gif"></td>
      <td width="100" class="tab-on" id="navcell" onclick="switchCell('0')">��ҵ�ƻ�</td>
      <td width="3" background="/images/line_2.gif"><img src="/images/line_1.gif" width="2" height="25"></td>
        
      <td width="100" class="tab-off" id="navcell" onclick="switchCell('1')">��ҵ����ͼ</td>  
      <td width="800" background="/images/line_2.gif"><img src="/images/line_1.gif" width="2" height="25"></td>
 </tr>
 </table>
 
 <table id="contents" width="100%" border="0" align="center" cellpadding="0" cellspacing="0">  
    <tr id="tb">
      <td width="3" background="/images/line_3.gif" bgcolor="#FFFFFF" style="background-repeat:repeat-y;">&nbsp;</td>
      <td bgcolor="#ffffff" style="text-align: center;">
      <br>
       <table width="800px" border="0" cellpadding="0" cellspacing="0" class="tb">
       <tr>
    <td width="20%" class="wordtd">��Ŀ����:</td>
    <td width="30%" class="inputtd" ><bean:write name="mwpBean" scope="request" property="projectName"/></td>
    <td width="20%" class="wordtd">ά���ֲ�:</td>
    <td width="30%" class="inputtd"><bean:write name="mwpBean" scope="request" property="maintDivision"/></td>
  </tr>
					
  <tr>
    <td class="wordtd">ά��վ:</td>
    <td class="inputtd"><bean:write name="mwpBean" scope="request" property="assignedMainStation"/></td>
    <td class="wordtd">ά����:</td>
    <td class="inputtd"><bean:write name="mwpBean" scope="request" property="maintPersonnelName"/>
    	<html:hidden name="mwpBean"  property="maintPersonnel" styleId="maintPersonnel"/> 
    	<html:hidden name="mwpBean"  property="maintPersonnelName" styleId="maintPersonnelName"/> 
  </tr>

					
  <tr>
     <td class="wordtd">������ʼ����:</td>
    <td class="inputtd"><bean:write name="mwpBean" scope="request" property="mainSdate"/></td>
    <td class="wordtd">������������:</td>
    <td class="inputtd"><bean:write name="mwpBean" scope="request" property="mainEdate"/></td>
  </tr>
  
  <tr>
     <td class="wordtd">ά����ͬ��:</td>
    <td class="inputtd"><bean:write name="mwpBean" scope="request" property="maintContractNo"/></td>
    <td class="wordtd">���ۺ�ͬ��:</td>
    <td class="inputtd"><bean:write name="mwpBean" scope="request" property="salesContractNo"/></td>
  </tr>
  <tr>
     <td class="wordtd">���ݱ��:</td>
    <td class="inputtd"><bean:write name="mwpBean" scope="request" property="elevatorNo"/></td>
    <td class="wordtd">��������:</td>
    <td class="inputtd">${mwpBean.elevatorType=='T'?'ֱ��':'����'}</td>
  </tr>
  <tr>
     <td class="wordtd">��/վ/��:</td>
    <td class="inputtd"><bean:write name="mwpBean" scope="request" property="floor"/>/<bean:write name="mwpBean" scope="request" property="stage"/>/<bean:write name="mwpBean" scope="request" property="door"/></td>
    <td class="wordtd">�����߶�:</td>
    <td class="inputtd"><bean:write name="mwpBean" scope="request" property="high"/></td>
  </tr>
  <tr>
     <td class="wordtd">�������:</td>
    <td class="inputtd"><bean:write name="mwpBean" scope="request" property="annualInspectionDate"/></td>
    <td class="wordtd">����ͺ�:</td>
    <td class="inputtd"><bean:write name="mwpBean" scope="request" property="elevatorParam"/></td>
  </tr>
  <tr>
     <td class="wordtd">�����߼�:</td>
    <td class="inputtd">
		<logic:equal name="mwpBean" property="maintLogic" value="1">�߼�һ</logic:equal>
		<logic:equal name="mwpBean" property="maintLogic" value="2">�߼���</logic:equal>
		<logic:equal name="mwpBean" property="maintLogic" value="3">�߼���</logic:equal>
		<logic:equal name="mwpBean" property="maintLogic" value="4">�߼���</logic:equal>
		<logic:equal name="mwpBean" property="maintLogic" value="5">�߼���</logic:equal>
	</td>
    <td class="wordtd">&nbsp;</td>
    <td class="inputtd">&nbsp;</td>
  </tr>
  </table>
  <br/>
  <logic:present name="mwpList">

  <table id="plans" width="800px" border="0" cellpadding="0" cellspacing="0" class="tb">
  <tr>
  <td class="wordtd" style="width: 200px;text-align: center;">����ʱ��</td>
  <td class="wordtd" style="width: 100px;text-align: center;">����</td>
  <td class="wordtd" style="width: 200px;text-align: center;">��������</td>
  <td class="wordtd" style="width: 200px;text-align: center;">׼����ʱ��(����)</td>
  <td class="wordtd" style="width: 300px;text-align: center;">��ҵʱ���</td>
  </tr>
  <logic:iterate id="ele" name="mwpList">
  <tr style="text-align: center;" class='<bean:write name="ele" property="style"/>'>
  <td><bean:write name="ele" property="maintDate"/><html:hidden name="ele" property="maintDate"/> </td>
  <td><bean:write name="ele" property="week"/><html:hidden name="ele" property="week"/></td>
  <td><bean:write name="ele" property="maintType"/> <html:hidden name="ele" property="maintType"/></td>
  <td><bean:write name="ele" property="maintDateTime"/><input type="hidden" name="sumMaintDateTime" value='<bean:write name="ele" property="sumMaintDateTime"/>'></td>
  <td><bean:write name="ele" property="maintSETime"/></td>
  </tr>
  </logic:iterate>
  </table>
  </logic:present>
   </td>
   </tr> 
   
	<!-- ����ͼ -->
    <tr id="tb" style="display:none;" >
      <td width="3" background="/images/line_3.gif" bgcolor="#FFFFFF" style="background-repeat:repeat-y;">&nbsp;</td>
      <td bgcolor="#ffffff">
        <div id="container" style="min-width: 1000px; height: 550px; margin: 0 auto"></div>
      </td>
    </tr>   
  </table>

  
  <script type="text/javascript">
  
  $("document").ready(function () {
  
  	var titleText=document.getElementById("maintPersonnelName").value;// ά��������
  	var maintPersonnel=document.getElementById("maintPersonnel").value;//ά�������
	
	var weeks = document.getElementsByName("week");//����
	var maintTypes = document.getElementsByName("maintType");//��������
  	var maintDates = document.getElementsByName("maintDate");//��������
  	var maintDateTimes = document.getElementsByName("maintDateTime");//׼����ʱ��
	var sumMaintDateTimes = document.getElementsByName("sumMaintDateTime");//�����ܱ���ʱ��

	var categories= [];
	for(var i=0;i<maintDates.length;i++){
		categories[i] = maintDates[i].value;
 		//if(i>0 && maintDates[i-1].value.substring(0,4) == maintDates[i].value.substring(0,4)){
		//	categories[i] = maintDates[i].value.substring(5);
		//}
 		categories[i] = maintDates[i].value.substring(5);
		categories[i] = categories[i].replace(/-/g,".");
	}
	
	var data = [];
	var overTime = 360;// ����50����ʱ�����ӱ�ɺ�ɫ
	for(var i=0;i<sumMaintDateTimes.length;i++){
		var y = Number(sumMaintDateTimes[i].value);
		/****** ����50����ʱ�����ӱ�ɺ�ɫ ******/
		var color = y > overTime ? '#F45B5B' : '#7CB5EC'; 
		data[i] = {color:color,y:y};		
	}
	
	adjustContainer();	//ͼ��С����Ӧ

	//������ʼ����
	var sdate='<bean:write name="mwpBean" scope="request" property="mainSdate"/>';
	sdate= sdate.replace(/-/g,".");
	//������������
	var edate='<bean:write name="mwpBean" scope="request" property="mainEdate"/>';
	edate= edate.replace(/-/g,".");
	
	//����ͼ����
    $("#container").highcharts({
        chart: {
            type: 'column'//����ͼ
        },
        title: {//����
            text: titleText+'('+sdate+'-'+edate+')'
        },
        subtitle: {//�ӱ���
            text: ''
        },
		credits: {
         	enabled:false
      	},
        xAxis: {//X��
            categories: categories, //���ǩ����          
            crosshair: true,
            labels: {//���ǩ
            	//staggerLines: 2, //���ǩ��������ʾ
            	style: {
            		color: "#444444"
            	}
            }
        },
        yAxis: {//Y��
            min: 0,
            title: {
                text: '��λ������'
            }
        },
        tooltip: {//������ʾ��
         	//shared: true,
         	useHTML: true,
			formatter: function () {
				
				var index = this.point.index;				
				var objs = eval(getDetailsJson(maintPersonnel, maintDates[index].value));				
				var format = '<b><font color='+this.color+'>' + maintDates[index].value + ' ����'+weeks[index].value+'</font></b>:<br/>';

				format += "<table style='color: #444444; text-align: center;'><tr>"+
					"<td nowrap='nowrap'><b>���ݱ��</b></td>"+
					"<td nowrap='nowrap'><b>��׼����ʱ��</b></td>"+
					"<td nowrap='nowrap'><b>ά����ͬ��</b></td>"+
					"<td nowrap='nowrap'><b>���ۺ�ͬ��</b></td>"+
					"<td nowrap='nowrap'><b>��Ŀ����</b></td>"+
					"<td nowrap='nowrap'><b>��������</b></td>"+
					"</tr>";
				
				for(var i=0; i<objs.length; i++){
					format +="<tr>"+
						"<td nowrap='nowrap'>"+objs[i].elevatorNo+"</td>"+
						"<td nowrap='nowrap'>"+objs[i].maintDateTime+"</td>"+
						"<td nowrap='nowrap'>"+objs[i].maintContractNo+"</td>"+
						"<td nowrap='nowrap'>"+objs[i].salesContractNo+"</td>"+
						"<td nowrap='nowrap'>"+objs[i].projectName+"</td>"+
						"<td nowrap='nowrap'>"+getMaintTypeName(objs[i].maintType)+"</td>"+
						"</tr>";
				}
				format += "</table>"

				return format;
            }
		},       
        plotOptions: {//��ͼ��������
            column: {
                pointPadding: 0.2,
                borderWidth: 0,
				colorByPoint: true,
				dataLabels: {
            		enabled: true
        		}
            }
			
        },
        series: [{//Y������
            name: '��ҵ����ͼ����ɫ���ӱ�ʾ�����ܱ���ʱ�����<font color=#F45B5B>'+overTime+'</font>���ӣ�',
            data: data,
			events: {
            	legendItemClick: function(event) { 
					return false;
            	}
          	}
        }],
        legend: {
        	//enabled: false
        }                               
    });
    
  })
  
	function getDetailsJson (maintPersonnel, maintDates) {
		
		var pathName = window.document.location.pathname;
		var projectName = pathName.substring(0, pathName.substr(1).indexOf('/')+1);
		var url = projectName+"/maintenanceReceiptAction.do?method=getMaintPersonnelOneDateDetail" + 
				"&maintPersonnel="+maintPersonnel+"&maintDate="+maintDates

		var obj = $.ajax({
				url: url,
				async:false				
			});
			
		return obj.responseText;	  
	}
	
	function getMaintTypeName(maintType){
		switch(maintType){
			case "halfMonth": return "���±���";
			case "quarter": return "���ȱ���";
			case "halfYear": return "���걣��";
			case "yearDegree": return "��ȱ���";
		}
	}
  
  function adjustContainer() { 
		$("#container").hide();
		var width = $("#contents").outerWidth();
		width = width >= ($("#plans tr").length/30) * 1000 ? width : ($("#plans tr").length/30) * 1000;
		$("#container").width(width);	
		$("#container").show();
		if(window.onresize == null){
			window.onresize = function () {	
				adjustContainer();
			} 
		}		
  }	
</script>
</logic:present>


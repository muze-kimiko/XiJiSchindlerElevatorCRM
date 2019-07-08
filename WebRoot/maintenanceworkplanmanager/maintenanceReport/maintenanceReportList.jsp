<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/html-table.tld" prefix="table"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" type="text/css"
	href="<html:rewrite forward='formCSS'/>">
	<script language="javascript" defer="defer" src="<html:rewrite forward='DatePickerJS'/>"></script>
	<script language="javascript" src="<html:rewrite page="/common/javascript/dynamictable.js"/>"></script>
  <link rel="stylesheet" type="text/css" href="<html:rewrite forward='formCSS'/>">
  <script language="javascript" src="<html:rewrite forward='pageJS'/>"></script>
  <link href="/XJSCRM/common/css/bb.css" rel="stylesheet" type="text/css">
  <script type="text/javascript" src="<html:rewrite page="/common/javascript/jquery-1.9.1.min.js"/>"></script>
  <script type="text/javascript" src="<html:rewrite page="/common/javascript/highcharts/highcharts.js"/>"></script>


<br>
<html:form action="/maintenanceReportAction.do?method=toSearchResults">
<input type="hidden" name="maintDivision" id="maintDivision" value="${rmap.maintDivision }"/>
<input type="hidden" name="mainStation" id="mainStation" value="${rmap.mainStation }"/>
<input type="hidden" name="maintPersonnel" id="maintPersonnel" value="${rmap.maintPersonnel }"/>
<input type="hidden" name="sdate1" id="sdate1" value="${rmap.sdate1 }"/>
<input type="hidden" name="edate1" id="edate1" value="${rmap.edate1 }"/>
<html:hidden property="genReport" styleId="genReport" />
	
	
	<table width="100%" height="24" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr class="td_2">
      <td width="2" background="/images/line.gif"></td>
      <td width="100" class="tab-on" id="navcell" onclick="switchCell('0')">����ʱ��</td>
      <td width="3" background="/images/line_2.gif"><img src="/images/line_1.gif" width="2" height="25"></td>
        
      <td width="100" class="tab-off" id="navcell" onclick="switchCell('1')">��������ͼ</td>  
      <td width="800" background="/images/line_2.gif"><img src="/images/line_1.gif" width="2" height="25"></td>
 </tr>
 </table>
	
	<table id="contents" width="100%" border="0" align="center" cellpadding="0" cellspacing="0">  
    <tr id="tb">
      <td width="3" background="/images/line_3.gif" bgcolor="#FFFFFF" style="background-repeat:repeat-y;">&nbsp;</td>
      <td bgcolor="#ffffff" style="text-align: center;">
      <br>
	<%int i=1; %>
	<table class=tb  width="100%" border=0  >
	<tr class=wordtd_header>
	<td nowrap="nowrap"  style="text-align: center;">���</td>	
	<td nowrap="nowrap"  style="text-align: center;">ά����</td>
	<td nowrap="nowrap"  style="text-align: center;">ά���ֲ�</td>
	<td nowrap="nowrap"  style="text-align: center;">ά��վ</td>
	<td nowrap="nowrap"  style="text-align: center;">��Ŀ����</td>
	<td nowrap="nowrap"  style="text-align: center;">ά����ͬ��</td>
	<td nowrap="nowrap"  style="text-align: center;">���ۺ�ͬ��</td>
	<td nowrap="nowrap"  style="text-align: center;">���ݱ��</td>
	<td nowrap="nowrap"  style="text-align: center;">��������</td>
	<td nowrap="nowrap"  style="text-align: center;">����ͺ�</td>
	<td nowrap="nowrap"  style="text-align: center;">����ʱ��</td>
	<td nowrap="nowrap"  style="text-align: center;">����</td>
	<td nowrap="nowrap"  style="text-align: center;">��������</td>
	<td nowrap="nowrap"  style="text-align: center;">׼����ʱ��(����)</td>
	<td nowrap="nowrap"  style="text-align: center;">��ҵʱ���</td>
	</tr>
	<logic:present name="maintenanceReportList">
	  <logic:iterate id="ele" name="maintenanceReportList">
	  <tr style="display:none;" class="inputtd" align="center" height="20">
	  <td><%=i++ %></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="username"/><html:hidden name="ele" property="username"/>
	  <html:hidden name="ele" property="userid"/></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="comname"/></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="storagename"/></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="projectName"/></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="maintContractNo"/></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="salesContractNo"/></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="elevatorNo"/></td>
	  <td nowrap="nowrap"  style="text-align: center;">${ele.elevatorType=='T'?'ֱ��':'����'}</td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="elevatorParam"/></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="maintDate"/><html:hidden name="ele" property="maintDate"/></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="week"/><html:hidden name="ele" property="week"/></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="maintType"/><html:hidden name="ele" property="maintType"/></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="maintDateTime"/><html:hidden name="ele" property="maintDateTime"/></td>
	  <td nowrap="nowrap"  style="text-align: center;"><bean:write name="ele" property="maintStartTime"/><bean:write name="ele" property="maintEndTime"/></td>
	  </tr>
	  </logic:iterate>
	  	</logic:present>
	  	<logic:notPresent name="maintenanceReportList">
	  <tr class="noItems" align="center" height="20"><td colspan="15" >û�м�¼��ʾ��</td></tr>
	  </logic:notPresent>
	  	</table>
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
	<script>
	var rows = $(".inputtd");
	var i = 0;
	var showRow = setInterval(function(){
		if(i < rows.length){
			for(var j=i;j<rows.length && j<i+20;j++){
				rows[j].style.display="block";	
			}			
			i+=20;
		}else{
			clearInterval(showRow);
		}
	}, 1);

	
  $("document").ready(function () {
  
	  <logic:present name="maintenanceReportList"> 
	  drawChart();
	  </logic:present>
  })
  
  function drawChart() {
  
  	var titleText=document.getElementsByName("username")[0].value;// ά��������
  	var maintPersonnel=document.getElementsByName("userid")[0].value;//ά�������
	
	var weeks = document.getElementsByName("week");//����
	var maintTypes = document.getElementsByName("maintType");//��������
  	var maintDates = document.getElementsByName("maintDate");//��������
  	var maintDateTimes = document.getElementsByName("maintDateTime");//׼����ʱ��
	var sumMaintDateTimes=[]; 
  	
  	var maintDates2=[];
  	var weeks2=[];
    for(var i=0;i<maintDateTimes.length;i++)
    {
    	if(i>0&&maintDates[i].value==maintDates[i-1].value)
    	{
    		sumMaintDateTimes[sumMaintDateTimes.length-1] +=  Number(maintDateTimes[i].value);
    	}else
    	{
    		sumMaintDateTimes[sumMaintDateTimes.length] = Number(maintDateTimes[i].value);
    		maintDates2.push(maintDates[i].value);
    		weeks2.push(weeks[i].value)
    	}
    }
   
	var categories= [];
	for(var i=0;i<maintDates2.length;i++){
		categories[i] = maintDates2[i];
 		if(i>0 && maintDates2[i-1].substring(0,4) == maintDates2[i].substring(0,4)){
			categories[i] = maintDates2[i].substring(5);
		}
		categories[i] = categories[i].replace(/-/g,".");
	}
	
	var data = [];
	var overTime = 360;// ����360����ʱ�����ӱ�ɺ�ɫ
	for(var i=0;i<sumMaintDateTimes.length;i++){
		var y = Number(sumMaintDateTimes[i]);
		/****** ����360����ʱ�����ӱ�ɺ�ɫ ******/
		var color = y > overTime ? '#F45B5B' : '#7CB5EC'; 
		data[i] = {color:color,y:y};		
	}
	
	adjustContainer(categories.length);	//ͼ��С����Ӧ

	//����ͼ����
    $("#container").highcharts({
        chart: {
            type: 'column'//����ͼ
        },
        title: {//����
            text: titleText 
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
				var objs = eval(getDetailsJson(maintPersonnel, maintDates2[index]));				
				var format = '<b><font color='+this.color+'>' + maintDates2[index] + ' ����'+weeks2[index]+'</font></b>:<br/>';

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
    
  }
  
  function getMaintTypeName(maintType){
		switch(maintType){
			case "halfMonth": return "���±���";
			case "quarter": return "���ȱ���";
			case "halfYear": return "���걣��";
			case "yearDegree": return "��ȱ���";
		}
	}
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

  function adjustContainer(rowLen) { 
		$("#container").hide();
		var width = $("#contents").outerWidth();
		width = width >= (rowLen/30) * 1000 ? width : (rowLen/30) * 1000;
		$("#container").width(width);	
		$("#container").show();
		if(window.onresize == null){
			window.onresize = function () {	
				adjustContainer();
			} 
		}		
  }	
</script>
		
</html:form>

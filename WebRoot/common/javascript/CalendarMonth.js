//----------------------------------------------------------------------------
//  ����һ������ Javascript ҳ��ű��ؼ���������΢��� IE ��5.0���ϣ������
//  �����ú����� setday(this,[object])��setday(this)��[object]�ǿؼ�����Ŀؼ��������������ӣ�
//  һ��<input name=txt><input type=button value=setday onclick="setday(this,document.all.txt)">
//  ����<input onfocus="setday(this)">
//  ����������������ǣ�1000 - 9999��
//  ��ESC���رոÿؼ�
//  ������µ���ʾ�ط����ʱ��ֱ�������µ�������
//  �ؼ���������һ�㼴�ɹرոÿؼ�
/*
Ver	2.0
�޸����ݣ�
1.*ȫ���޸�ʹ��iframe��Ϊ���������壬���ٱ�select��flash�ȿؼ���ס��
2.��������ֲ��iframe���ƶ������ؼ������⡣

Ver	1.5
�޸����ݣ�
1.ѡ�е�������ʾΪ����ȥ����ʽ
2.�޸��˹رղ�ķ�����ʹ��ʧȥ�����ʱ���ܹ��ر�������
3.�޸İ�������ʹ��Tab�л������ʱ����Թرտؼ�
4.*�����Զ��������Ƿ�����϶�

Ver 1.4
�޸����ݣ�
1.����ѡ����/�·��������Esc��������/�·ݲ���ʾ������
2.����ʹ��������ѡ���·���ɵ����ڴ����ַ���ת��Ϊ���ֵ����⣩
3.*�����ʽ�ĸĽ���ʹ�ÿؼ��ӳ�СѼ�������������죬�ӻҹ������˸߹�Ĺ���
4.�ٴ�������/�·ݵĵ���ռ䣬�����������λ����������

ע��*�ű�ʾ�ȽϹؼ��ĸĶ�

˵����
1.�ܵ�iframe�����ƣ�����϶����������ڣ���������ֹͣ�ƶ���
*/

//==================================================== �����趨���� =======================================================
var bMoveable=true;		//���������Ƿ�����϶�

//==================================================== WEB ҳ����ʾ���� =====================================================
var strFrame;		//����������HTML����
document.writeln('<iframe id=meizzDateLayer frameborder=0 style="position:absolute;width:145;z-index:9998;display:none;"></iframe>');
strFrame='<link rel="stylesheet" type="text/css" href="/XJSCRM/common/css/SelectMonth.css">';
strFrame+='<scr' + 'ipt language=javascript>';
strFrame+='var datelayerx,datelayery;	/*��������ؼ������λ��*/';
strFrame+='var bDrag;	/*����Ƿ�ʼ�϶�*/';
strFrame+='function document.onmousemove()	/*������ƶ��¼��У������ʼ�϶����������ƶ�����*/';
strFrame+='{if(bDrag && window.event.button==1)';
strFrame+='	{var DateLayer=parent.document.all.meizzDateLayer.style;';
strFrame+='		DateLayer.posLeft += window.event.clientX-datelayerx;/*����ÿ���ƶ��Ժ����λ�ö��ָ�Ϊ��ʼ��λ�ã����д����div�в�ͬ*/';
strFrame+='		DateLayer.posTop += window.event.clientY-datelayery;}}';
strFrame+='function DragStart()		/*��ʼ�����϶�*/';
strFrame+='{var DateLayer=parent.document.all.meizzDateLayer.style;';
strFrame+='	datelayerx=window.event.clientX;';
strFrame+='	datelayery=window.event.clientY;';
strFrame+='	bDrag=true;}';
strFrame+='function DragEnd(){		/*���������϶�*/';
strFrame+='	bDrag=false;}';
strFrame+='</scr' + 'ipt>';
strFrame+='<div style="z-index:9999;position: absolute; left:0; top:0;" onselectstart="return false">';
strFrame+='<span id=tmpSelectYearLayer style="z-index: 9999;position: absolute;top: 3; left: 19;display: none;"></span>';
strFrame+='<span id=tmpSelectMonthLayer style="z-index: 9999;position: absolute;top: 3; left: 78;display: none"></span>';
strFrame+='<table border=1 cellspacing=0 cellpadding=0 class="outtable" bordercolor=#80a6f6>';
strFrame+='<tr id=trSelectYM><td bgcolor=#ffffff>';
strFrame+='<table border=0 cellspacing=1 cellpadding=0 class="yearandmonthtable">';
strFrame+='<tr align=center>';
strFrame+='<td align=center class="nexttd" onclick="parent.meizzPrevM()" title="��ǰ�� 1 ��">&lt;</td>';
strFrame+='<td align=center class="yeartd" onclick="parent.tmpSelectYearInnerHTML(this.innerText.substring(0,4))" title="�������ѡ�����"><span id="meizzYearHead"></span></td>';
strFrame+='<td align=center class="monthtd" onclick="parent.tmpSelectMonthInnerHTML(this.innerText.length==3?this.innerText.substring(0,1):this.innerText.substring(0,2))" title="�������ѡ���·�"><span id="meizzMonthHead"></span></td>';
strFrame+='<td align=center class="nexttd" onclick="parent.meizzNextM()" title="��� 1 ��">&gt;</td>';
strFrame+='</tr>';
strFrame+='</table>';
strFrame+='</td></tr>';
strFrame+='<tr id=trWeekTitle><td>';
strFrame+='<table border=1 cellspacing=0 cellpadding=0 ' + (bMoveable? 'onmousedown="DragStart()" onmouseup="DragEnd()"':'') + ' class="weektable" bordercolorlight=#80A6F6 bordercolordark=#ffffca style="cursor:' + (bMoveable ? 'move':'default') + '">';
strFrame+='<tr align=center valign=bottom>';
strFrame+='<td class="weektd">��</td>';
strFrame+='<td class="weektd">һ</td>';
strFrame+='<td class="weektd">��</td>';
strFrame+='<td class="weektd">��</td>';
strFrame+='<td class="weektd">��</td>';
strFrame+='<td class="weektd">��</td>';
strFrame+='<td class="weektd">��</td>';
strFrame+='</tr>';
strFrame+='</table>';
strFrame+='</td></tr>';
strFrame+='<tr id=trMonthDate><td>';
strFrame+='<table border=1 cellspacing=2 bordercolordark=#ffffff class="31table">';
var n=0; for (j=0;j<5;j++){ strFrame+= ' <tr align=center>'; for (i=0;i<7;i++){
strFrame+='<td id=meizzDay'+n+' class=31td onclick=parent.meizzDayClick(this.innerText,0)></td>';n++;}
strFrame+='</tr>';}
strFrame+='<tr align=center>';
for (i=35;i<37;i++)strFrame+='<td id=meizzDay'+i+' class=31td onclick="parent.meizzDayClick(this.innerText,0)"></td>';
strFrame+='<td colspan=5 align=right><span onclick=parent.sureLayer() class="closestyle"><u>ȷ��</u></span><span style="width:6px;"></span><span onclick=parent.resetLayer() class="closestyle"><u>����</u></span><span style="width:6px;"></span><span onclick=parent.closeLayer() class="closestyle"><u>ȡ��</u></span></td></tr>';
strFrame+='</table></td></tr><tr id=trTime><td>';
strFrame+='<table border=0 cellspacing=1 cellpadding=0 class="timetable">';
strFrame+='<tr><td align=center><span id=spanHour class="timetd"></span><span id=spanMinute class="timetd"></span><span id=spanSecond class="timetd"></span></td></tr>';
strFrame+='</table>';
strFrame+='</td></tr>';
strFrame+='</table>';
strFrame+='</div>';

window.frames.meizzDateLayer.document.writeln(strFrame);
window.frames.meizzDateLayer.document.close();		//���ie������������������

//==================================================== WEB ҳ����ʾ���� ======================================================
var outObject;
var outButton;		//����İ�ť
var hiddenTimeVal="";
var onFlag="";
var odatelayer=window.frames.meizzDateLayer.document.all;		//�����������
function setmonth(tt,obj,showDateTime) //��������
{
        if (arguments.length >  3){alert("�Բ��𣡴��뱾�ؼ��Ĳ���̫�࣡");return;}
        if (arguments.length == 0){alert("�Բ�����û�д��ر��ؼ��κβ�����");return;}
        var dads  = document.all.meizzDateLayer.style;
        var th = tt;
        var ttop  = tt.offsetTop;     //TT�ؼ��Ķ�λ���
        var thei  = tt.clientHeight;  //TT�ؼ�����ĸ�
        var twid  = tt.clientWidth;   //TT�ؼ�����Ŀ�
        var tleft = tt.offsetLeft;    //TT�ؼ��Ķ�λ���
        var ttyp  = tt.type;          //TT�ؼ�������
        while (tt = tt.offsetParent){ttop+=tt.offsetTop; tleft+=tt.offsetLeft;}
        var oWidth = dads.width;
        oWidth = oWidth.replace("px","");
        var tWidth = parseInt(oWidth)+parseInt(tleft);
        var bodyWidth = document.body.clientWidth;
        if (tWidth > bodyWidth)
        {
          var temp = parseInt(bodyWidth) - oWidth;
          if (temp < 0)
            dads.left = 0;
          else
            dads.left = temp;
        }
        else
          dads.left = tleft;

//##
	if ( arguments.length < 3 )
	{
	        outObject = (arguments.length == 1) ? th : obj;
	        outButton = (arguments.length == 1) ? null : th;	//�趨�ⲿ����İ�ť
	}else{
        	outObject = ( arguments[1] == null ) ? th : obj;
	        outButton = ( arguments[1] == null ) ? null : th;	//�趨�ⲿ����İ�ť
        }

        //���ݵ�ǰ������������ʾ����������
        var rr = outObject.value;
        var r = rr.split(" ");

        if(r!=null){
          if(r.length==1){    //û��ʱ��������
            var r1 = rr.split("-");
            if(r1.length==3){
              hiddenTimeVal = rr;
              meizzSetDay(r1[0],r1[1]-1+1);
            }else{
              hiddenTimeVal = "";
              meizzSetDay(new Date().getFullYear(), new Date().getMonth()+1);
            }
          }else{
            if(r.length==2){   //��ʱ��������
              var r1 = r[0].split("-");
              if(r1.length==3){
                hiddenTimeVal = rr;
                meizzSetDay(r1[0],r1[1]-1+1);
              }else{
                hiddenTimeVal = "";
                meizzSetDay(new Date().getFullYear(), new Date().getMonth()+1);
              }
            }else{
              hiddenTimeVal="";
              meizzSetDay(new Date().getFullYear(), new Date().getMonth()+1);
            }
          }
        }else{
          hiddenTimeVal = "";
          meizzSetDay(new Date().getFullYear(), new Date().getMonth()+1);
        }
        
//##        outObject.showTime = "1";   //��������
//##	���ϲ���ʾ�����Ĺ���
	if ( ( outObject.showDate != null && outObject.showDate == "0" )
	  || ( showDateTime != null && showDateTime == 'showDate=0' ) )
	{
	        dads.height = "28px";
		odatelayer.trSelectYM.style.display = "none";
		odatelayer.trWeekTitle.style.display = "none";
		odatelayer.trMonthDate.style.display = "none";
          	odatelayer.trTime.style.display = ""; //��ʾʱ�����ѡ���
	}else{
		odatelayer.trSelectYM.style.display = "";
		odatelayer.trWeekTitle.style.display = "";
		odatelayer.trMonthDate.style.display = "";
	
	        if ( ( outObject.showTime != null && outObject.showTime == "1" )
	          || ( showDateTime != null && showDateTime == 'showTime=1' ) )
	        {
	          dads.height = "215px";
	          odatelayer.trTime.style.display = ""; //������ʱ�����ѡ���
	        }else{
	          dads.height = "190px";
	          odatelayer.trTime.style.display = "none"; //����ʱ�����ѡ���
	        }
	}
        var oHeight = dads.height;
        oHeight = oHeight.replace("px","");
        var divScrollTop = 0;
/*##        try{
          divScrollTop = GenForm.scrollTop;    //��������
        }catch(e){}
*/
        var tHeight = parseInt(oHeight)+parseInt(ttop)+parseInt(thei)-parseInt(divScrollTop);
        var bodyHeight = document.body.clientHeight;
        if (tHeight > bodyHeight)
        {
          var temp = parseInt(ttop)-parseInt(oHeight)-parseInt(divScrollTop);
          if (temp < 0)
            dads.top = 0;
          else
            dads.top = temp;
        }
        else
          dads.top  = (ttyp=="image")? ttop+thei-divScrollTop : ttop+thei-divScrollTop+8;

        if(r.length==2){
          var tmp=r[1].split(":");
          if(tmp.length==3)
            tmpSelectHourInnerHTML(tmp[0],tmp[1],tmp[2]); //ʱ���������
          else
            tmpSelectHourInnerHTML("0","0","0"); //ʱ���������
        }else{
          tmpSelectHourInnerHTML("0","0","0"); //ʱ���������
        }

//##	����ǲ���ʾ�����������������ʾʱ��Ĺ���
	if ( outObject != null && odatelayer.trSelectYM.style.display == "none" )
	{
          var tmp=rr.split(":");
          if(tmp.length==3)
            tmpSelectHourInnerHTML(tmp[0],tmp[1],tmp[2]); //ʱ���������
          else
            tmpSelectHourInnerHTML("0","0","0"); //ʱ���������
	}

        dads.display = '';

        event.returnValue=false;
}

var MonHead = new Array(12);    		   //����������ÿ���µ��������
    MonHead[0] = 31; MonHead[1] = 28; MonHead[2] = 31; MonHead[3] = 30; MonHead[4]  = 31; MonHead[5]  = 30;
    MonHead[6] = 31; MonHead[7] = 31; MonHead[8] = 30; MonHead[9] = 31; MonHead[10] = 30; MonHead[11] = 31;

var meizzTheYear=new Date().getFullYear(); //������ı����ĳ�ʼֵ
var meizzTheMonth=new Date().getMonth()+1; //�����µı����ĳ�ʼֵ
var meizzWDay=new Array(37);               //����д���ڵ�����

function document.onclick() //������ʱ�رոÿؼ�	//ie6�����������������л����㴦�����
{
  with(window.event)
  { if (srcElement.getAttribute("Author")==null && srcElement != outObject && srcElement != outButton)
    closeLayer();
  }
}

function document.onkeyup()		//��Esc���رգ��л�����ر�
  {
    if (window.event.keyCode==27){
                if(outObject)outObject.blur();
                closeLayer();
        }
        else if(document.activeElement)
                if(document.activeElement.getAttribute("Author")==null && document.activeElement != outObject && document.activeElement != outButton)
                {
                        closeLayer();
                }
  }

function meizzWriteHead(yy,mm)  //�� head ��д�뵱ǰ��������
  {
        odatelayer.meizzYearHead.innerText  = yy + " ��";
    odatelayer.meizzMonthHead.innerText = mm + " ��";
  }

function tmpSelectYearInnerHTML(strYear) //��ݵ�������
{
  if (strYear.match(/\D/)!=null){alert("�����������������֣�");return;}
  var m = (strYear) ? strYear : new Date().getFullYear();
  if (m < 1000 || m > 9999) {alert("���ֵ���� 1000 �� 9999 ֮�䣡");return;}
  var n = m - 10;
  if (n < 1000) n = 1000;
  if (n + 26 > 9999) n = 9974;
  var s = "<select name=tmpSelectYear style='font-size: 12px' "
     s += "onblur='document.all.tmpSelectYearLayer.style.display=\"none\"' "
     s += "onchange='document.all.tmpSelectYearLayer.style.display=\"none\";"
     s += "parent.meizzTheYear = this.value; parent.meizzSetDay(parent.meizzTheYear,parent.meizzTheMonth)'>\r\n";
  var selectInnerHTML = s;
  for (var i = n; i < n + 26; i++)
  {
    if (i == m)
       {selectInnerHTML += "<option Author=wayx value='" + i + "' selected>" + i + "��" + "</option>\r\n";}
    else {selectInnerHTML += "<option Author=wayx value='" + i + "'>" + i + "��" + "</option>\r\n";}
  }
  selectInnerHTML += "</select>";
  odatelayer.tmpSelectYearLayer.style.display="";
  odatelayer.tmpSelectYearLayer.innerHTML = selectInnerHTML;
  odatelayer.tmpSelectYear.focus();
}

function tmpSelectMonthInnerHTML(strMonth) //�·ݵ�������
{
  if (strMonth.match(/\D/)!=null){alert("�·���������������֣�");return;}
  var m = (strMonth) ? strMonth : new Date().getMonth() + 1;
  var s = "<select name=tmpSelectMonth style='font-size: 12px' "
     s += "onblur='document.all.tmpSelectMonthLayer.style.display=\"none\"' "
     s += "onchange='document.all.tmpSelectMonthLayer.style.display=\"none\";"
     s += "parent.meizzTheMonth = this.value; parent.meizzSetDay(parent.meizzTheYear,parent.meizzTheMonth)'>\r\n";
  var selectInnerHTML = s;
  for (var i = 1; i < 13; i++)
  {
    if (i == m)
       {selectInnerHTML += "<option Author=wayx value='"+i+"' selected>"+i+"��"+"</option>\r\n";}
    else {selectInnerHTML += "<option Author=wayx value='"+i+"'>"+i+"��"+"</option>\r\n";}
  }
  selectInnerHTML += "</select>";
  odatelayer.tmpSelectMonthLayer.style.display="";
  odatelayer.tmpSelectMonthLayer.innerHTML = selectInnerHTML;
  odatelayer.tmpSelectMonth.focus();
}

function tmpSelectHourInnerHTML(strHour,strMinute,strSecond) //ʱ�����������
{
  if (strHour.match(/\D/)!=null){strHour="0";}//alert("ʱ��������������֣�");return;}
  if (strMinute.match(/\D/)!=null){strHour="0";}//alert("����������������֣�");return;}
  if (strSecond.match(/\D/)!=null){strHour="0";}//alert("����������������֣�");return;}

  var m = (strHour) ? strHour : new Date().getHour();
  var s = "<select id=selHour name=tmpSelectHour style='width:37px;height:20px;font-size:12px' "
     s += "class='select' onchange='parent.changeHMS();'>\r\n";
  var selectInnerHTML = s;
  for (var i = 0; i < 24; i++)
  {
    if (i == m)
       {selectInnerHTML += "<option value='"+i+"' selected>"+i+""+"</option>\r\n";}
    else {selectInnerHTML += "<option value='"+i+"'>"+i+""+"</option>\r\n";}
  }
  selectInnerHTML += "</select>��";
  odatelayer.spanHour.innerHTML = selectInnerHTML;

  m = (strMinute) ? strMinute : new Date().getMinute();
  s = "<select id=selMinute name=tmpSelectMinute style='width:37px;height:20px;font-size:12px' "
     s += "class='select' onchange='parent.changeHMS();'>\r\n";
  selectInnerHTML = s;
  for (var i = 0; i < 60; i++)
  {
    if (i == m)
       {selectInnerHTML += "<option value='"+i+"' selected>"+i+""+"</option>\r\n";}
    else {selectInnerHTML += "<option value='"+i+"'>"+i+""+"</option>\r\n";}
  }
  selectInnerHTML += "</select>��";
  odatelayer.spanMinute.innerHTML = selectInnerHTML;

  m = (strSecond) ? strSecond : new Date().getSecond();
  s = "<select id=selSecond name=tmpSelectSecond style='width:37px;height:20px;font-size:12px' "
     s += "class='select' onchange='parent.changeHMS();'>\r\n";
  selectInnerHTML = s;
  for (var i = 0; i < 60; i++)
  {
    if (i == m)
       {selectInnerHTML += "<option value='"+i+"' selected>"+i+""+"</option>\r\n";}
    else {selectInnerHTML += "<option value='"+i+"'>"+i+""+"</option>\r\n";}
  }
  selectInnerHTML += "</select>";
  odatelayer.spanSecond.innerHTML = selectInnerHTML;
}

function closeLayer()               //�����Ĺر�
{
//##�������ʾ��������ر�������ʱ��Ҳ��ֵ����ȥ
  if ( outObject != null && odatelayer.trSelectYM.style.display == "none" )
  {
	  if (outObject)
	  {
		if ( hiddenTimeVal == null || hiddenTimeVal == "" )
			hiddenTimeVal = "00:00:00";

	    	outObject.value=hiddenTimeVal;
	  }
	  else
	    alert("����Ҫ����Ŀؼ����󲢲����ڣ�");
  }

  document.all.meizzDateLayer.style.display="none";
}

function IsPinYear(year)            //�ж��Ƿ���ƽ��
{
  if (0==year%4&&((year%100!=0)||(year%400==0))) return true;else return false;
}

function GetMonthCount(year,month)  //�������Ϊ29��
{
  var c=MonHead[month-1];if((month==2)&&IsPinYear(year)) c++;return c;
}
function GetDOW(day,month,year)     //��ĳ������ڼ�
{
  var dt=new Date(year,month-1,day).getDay()/7; return dt;
}

function meizzPrevY()  //��ǰ�� Year
{
  if(meizzTheYear > 999 && meizzTheYear <10000){meizzTheYear--;}
  else{alert("��ݳ�����Χ��1000-9999����");}
  meizzSetDay(meizzTheYear,meizzTheMonth);
}
function meizzNextY()  //���� Year
{
  if(meizzTheYear > 999 && meizzTheYear <10000){meizzTheYear++;}
  else{alert("��ݳ�����Χ��1000-9999����");}
  meizzSetDay(meizzTheYear,meizzTheMonth);
}
function meizzToday()  //Today Button
{
  var today;
  meizzTheYear = new Date().getFullYear();
  meizzTheMonth = new Date().getMonth()+1;
  today=new Date().getDate();
  if(outObject){
    outObject.value=meizzTheYear + "-" + meizzTheMonth + "-" + today;
  }
  closeLayer();
}
function meizzPrevM()  //��ǰ���·�
{
  if(meizzTheMonth>1){meizzTheMonth--}else{meizzTheYear--;meizzTheMonth=12;}
  meizzSetDay(meizzTheYear,meizzTheMonth);
}
function meizzNextM()  //�����·�
{
  if(meizzTheMonth==12){meizzTheYear++;meizzTheMonth=1}else{meizzTheMonth++}
  meizzSetDay(meizzTheYear,meizzTheMonth);
}

function meizzSetDay(yy,mm)   //��Ҫ��д����**********
{
  var curDate="";
  if(hiddenTimeVal!=""){
    var r=hiddenTimeVal.split(" ");
    if(r.length==1)
      curDate=hiddenTimeVal;
    if(r.length==2)
      curDate=r[0];
    var rr=curDate.split("-");
    curDate=new Date(rr[0],rr[1]-1,rr[2]);
  }

  onFlag="";
  meizzWriteHead(yy,mm);     //д�����е��ꡢ��
  //���õ�ǰ���µĹ�������Ϊ����ֵ
  meizzTheYear=yy;
  meizzTheMonth=mm;

  for (var i = 0; i < 37; i++){meizzWDay[i]=""};  //����ʾ�������ȫ�����
  var day1 = 1,day2=1,firstday = new Date(yy,mm-1,1).getDay();  //ĳ�µ�һ������ڼ�
  for (i=0;i<firstday;i++)meizzWDay[i]=GetMonthCount(mm==1?yy-1:yy,mm==1?12:mm-1)-firstday+i+1	//�ϸ��µ������
  for (i = firstday; day1 < GetMonthCount(yy,mm)+1; i++){meizzWDay[i]=day1;day1++;}
  for (i=firstday+GetMonthCount(yy,mm);i<37;i++){meizzWDay[i]=day2;day2++}
  for (i = 0; i < 37; i++)
  { var da = eval("odatelayer.meizzDay"+i)     //��д�µ�һ���µ�������������
    if (meizzWDay[i]!="")
      {
                //��ʼ���߿�
                da.borderColorLight="#80A6F6";
                da.borderColorDark="#FFFFFF";
                if(i<firstday)		//�ϸ��µĲ���
                {
                        da.innerHTML="<b><font color=gray>" + meizzWDay[i] + "</font></b>";
                        da.title=(mm==1?12:mm-1) +"��" + meizzWDay[i] + "��";
                        da.onclick=Function("meizzDayClick(this.innerText,-1)");
                        if(!curDate)
                                da.style.backgroundColor = ((mm==1?yy-1:yy) == new Date().getFullYear() &&
                                        (mm==1?12:mm-1) == new Date().getMonth()+1 && meizzWDay[i] == new Date().getDate()) ?
                                         "#FCFC73":"#F1ECEC";
                        else
                        {
                                da.style.backgroundColor =((mm==1?yy-1:yy)==curDate.getFullYear() && (mm==1?12:mm-1)== curDate.getMonth() + 1 &&
                                meizzWDay[i]==curDate.getDate())? "#7AFDFD" :
                                (((mm==1?yy-1:yy) == new Date().getFullYear() && (mm==1?12:mm-1) == new Date().getMonth()+1 &&
                                meizzWDay[i] == new Date().getDate()) ? "#FCFC73":"#F1ECEC");
                                //��ѡ�е�������ʾΪ����ȥ
                                if((mm==1?yy-1:yy)==curDate.getFullYear() && (mm==1?12:mm-1)== curDate.getMonth() + 1 &&
                                meizzWDay[i]==curDate.getDate())
                                {
                                        da.borderColorLight="#FFFFFF";
                                        da.borderColorDark="#80A6F6";
                                        onFlag=da.id;
                                }
                        }
                }
                else if (i>=firstday+GetMonthCount(yy,mm))		//�¸��µĲ���
                {
                        da.innerHTML="<b><font color=gray>" + meizzWDay[i] + "</font></b>";
                        da.title=(mm==12?1:mm+1) +"��" + meizzWDay[i] + "��";
                        da.onclick=Function("meizzDayClick(this.innerText,1)");
                        if(!curDate)
                                da.style.backgroundColor = ((mm==12?yy+1:yy) == new Date().getFullYear() &&
                                        (mm==12?1:mm+1) == new Date().getMonth()+1 && meizzWDay[i] == new Date().getDate()) ?
                                         "#FCFC73":"#F1ECEC";
                        else
                        {
                                da.style.backgroundColor =((mm==12?yy+1:yy)==curDate.getFullYear() && (mm==12?1:mm+1)== curDate.getMonth() + 1 &&
                                meizzWDay[i]==curDate.getDate())? "#7AFDFD" :
                                (((mm==12?yy+1:yy) == new Date().getFullYear() && (mm==12?1:mm+1) == new Date().getMonth()+1 &&
                                meizzWDay[i] == new Date().getDate()) ? "#FCFC73":"#F1ECEC");
                                //��ѡ�е�������ʾΪ����ȥ
                                if((mm==12?yy+1:yy)==curDate.getFullYear() && (mm==12?1:mm+1)== curDate.getMonth() + 1 &&
                                meizzWDay[i]==curDate.getDate())
                                {
                                        da.borderColorLight="#FFFFFF";
                                        da.borderColorDark="#80A6F6";
                                        onFlag=da.id;
                                }
                        }
                }
                else		//���µĲ���
                {
                        da.innerHTML="<b>" + meizzWDay[i] + "</b>";
                        da.title=mm +"��" + meizzWDay[i] + "��";
                        da.onclick=Function("meizzDayClick(this.innerText,0)");		//��td����onclick�¼��Ĵ���
                        //����ǵ�ǰѡ������ڣ�����ʾ����ɫ�ı���������ǵ�ǰ���ڣ�����ʾ����ɫ����
                        if(!curDate)
                                da.style.backgroundColor = (yy == new Date().getFullYear() && mm == new Date().getMonth()+1 && meizzWDay[i] == new Date().getDate())?
                                        "#FCFC73":"#F1ECEC";
                        else
                        {
                                da.style.backgroundColor =(yy==curDate.getFullYear() && mm== curDate.getMonth() + 1 && meizzWDay[i]==curDate.getDate())?
                                        "#7AFDFD":((yy == new Date().getFullYear() && mm == new Date().getMonth()+1 && meizzWDay[i] == new Date().getDate())?
                                        "#FCFC73":"#F1ECEC");
                                //��ѡ�е�������ʾΪ����ȥ
                                if(yy==curDate.getFullYear() && mm== curDate.getMonth() + 1 && meizzWDay[i]==curDate.getDate())
                                {
                                        da.borderColorLight="#FFFFFF";
                                        da.borderColorDark="#80A6F6";
                                        onFlag=da.id;
                                }
                        }
                }
        da.style.cursor="hand"
      }
    else{da.innerHTML="";da.style.backgroundColor="";da.style.cursor="default"}
  }
}

function meizzDayClick(n,ex)  //�����ʾ��ѡȡ���ڣ������뺯��*************
{
  if(onFlag!=""){
    var oldObj=meizzDateLayer.document.all.item(onFlag);
    oldObj.borderColorLight="#80A6F6";
    oldObj.borderColorDark="#FFFFFF";
    oldObj.style.backgroundColor="#F1ECEC";
    onFlag="";
  }
  var newObj=meizzDateLayer.event.srcElement;
  if(newObj.tagName.toLowerCase()=="font")
    newObj=newObj.parentElement;
  if(newObj.tagName.toLowerCase()=="b")
    newObj=newObj.parentElement;
  newObj.borderColorLight="#FFFFFF";
  newObj.borderColorDark="#80A6F6";
  newObj.style.backgroundColor="#7AFDFD";
  onFlag=newObj.id;


  var hour,min,sec;
  var yy=meizzTheYear;
  var mm = parseInt(meizzTheMonth)+ex;	//ex��ʾƫ����������ѡ���ϸ��·ݺ��¸��·ݵ�����
  //�ж��·ݣ������ж�Ӧ�Ĵ���
  if(mm<1){
          yy--;
          mm=12+mm;
  }
  else if(mm>12){
          yy++;
          mm=mm-12;
  }
  if (mm < 10){mm = "0" + mm;}
  if (!n) {//outObject.value="";
    return;}
  if ( n < 10){n = "0" + n;}
  hiddenTimeVal= yy + "-" + mm ; //ע�����������������ĳ�����Ҫ�ĸ�ʽ
  //hiddenTimeVal= yy + mm  + n ; //ע�������ʽΪ:yyyymmdd                                                                                    

  if ( odatelayer.trTime.style.display == "" ) //�����ʱ��ѡ���ģ����Ҳ����ʱ����
  {
    hour = odatelayer.selHour.value;
    if ( hour < 10 ) hour = "0" + hour;
    min = odatelayer.selMinute.value;
    if ( min < 10 ) min = "0" + min;
    sec = odatelayer.selSecond.value;
    if ( sec < 10 ) sec = "0" + sec;
    hiddenTimeVal += " " + hour + ":" + min + ":" + sec;
  }
  sureLayer();
}

function changeHMS(){
  var r=hiddenTimeVal.split(" ");

//##������ǲ���ʾ�����ģ���Ҫ����ѡ������
  if ( odatelayer.trSelectYM.style.display == "" )
  {
	  if(r[0]==""){
	    alert("����ѡ�����ڣ�");
	    return;
	  }  
  }

  hour = odatelayer.selHour.value;
  if ( hour < 10 ) hour = "0" + hour;
  min = odatelayer.selMinute.value;
  if ( min < 10 ) min = "0" + min;
  sec = odatelayer.selSecond.value;
  if ( sec < 10 ) sec = "0" + sec;
//##������ǲ���ʾ�����ģ������ֵ�������ڲ���
  if ( odatelayer.trSelectYM.style.display == "" )
  {
    hiddenTimeVal = r[0]+" "+hour+":"+min+":"+sec;
  }else{
    hiddenTimeVal = hour+":"+min+":"+sec;
  }

}

function sureLayer(){
  if (outObject)
    outObject.value=hiddenTimeVal;
  else
    alert("����Ҫ����Ŀؼ����󲢲����ڣ�");
  closeLayer();
}

function resetLayer(){
  if (outObject)
    outObject.value="";
  else
    alert("����Ҫ����Ŀؼ����󲢲����ڣ�");
  closeLayer();
}

function add_zero(temp)
{
  if(temp<10) return "0"+temp;
  else return temp;
}

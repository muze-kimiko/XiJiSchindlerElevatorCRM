var menu1Html="";             //�ݴ�menu1��html����
var menu2Html="";             //�ݴ�menu2��html����
var menu1OnItemName="";       //����menu1�б�ѡ�еİ�ť����
var menu2OnItemName="";       //����menu2�б�ѡ�еİ�ť����
var ButtonBarHtml="";         //�ݴ�Buttonbar��html����
var menu2DisplayBranch="";    //���ж�������˵���menu2DisplayBranch���浱ǰ������ʾ��menu2������

/************************/
/*���ܣ���menu1���һ����ť*/
/*������*/
/*       aName����ť������*/
/*  aNormalImg����ť������״̬����ʾ��ͼƬ*/
/*  aSelectImg����ť�ڱ�ѡ��״̬����ʾ��ͼƬ*/
/*    aOverImg����ť���������ȥ״̬����ʾ��ͼƬ*/
/*        aDes����ť�ϵ�����*/
/*     aAttach����ť�����Ķ�������onclick��onmouseover��onmouseout��*/
/*       aLeft����ť�������ǰһԪ�ص�����*/
/*      aWidth����ť�Ŀ����أ�*/
/*     aHeight����ť�ĸߣ����أ�*/
/************************/
function menu1_addItem(aName,aNormalImg,aSelectImg,aOverImg,aDes,aAttach,aLeft,aWidth,aHeight){
  var temp="";

  temp = "<span style=\"position:relative;width:"+aLeft+"px;\"></span><span id=\""+aName+"_out\" name=\""+aName+"_out\" widthValue=\""+aWidth+"\" heightValue=\""+aHeight+"\" class=\"menu1_normalItem_out\" style=\"width:"+aWidth+"px;height:"+aHeight+"px;\"><span id=\""+aName+"_middle\" name=\""+aName+"_middle\" widthValue=\""+aWidth+"\" heightValue=\""+aHeight+"\" class=\"menu1_normalItem_middle\" style=\"width:"+aWidth+"px;height:"+aHeight+"px;\"><span id=\""+aName+"_in\" name=\""+aName+"_in\" widthValue=\""+aWidth+"\" heightValue=\""+aHeight+"\" class=\"menu1_normalItem_in\" style=\"width:"+aWidth+"px;height:"+aHeight+"px;\""+aAttach+"><img id=\""+aName+"_img\" name=\""+aName+"_img\" normalImg=\""+aNormalImg+"\" selectImg=\""+aSelectImg+"\" overImg=\""+aOverImg+"\" src=\""+aNormalImg+"\" border=\"0\"><br>"+aDes+"</span></span></span>";
  menu1Html = menu1Html+temp;
}

/************************/
/*���ܣ�����menu1*/
/*������*/
/*aDivName��ָ��menu1����λ�õ�div����*/
/************************/
function createMenu1(aDivName){
  menu1Html = "<table style=\"table-layout:fixed;\" width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><tr class=\"menu1_tr\"><td class=\"menu1_leftTd\">"+menu1Html+"</td></tr></table>";
  document.all.item(aDivName).innerHTML = menu1Html;
  menu1Html="";
}

/************************/
/*���ܣ���menu1���õ�ǰѡ�а�ť*/
/*������*/
/*aName����ť������*/
/************************/
function setMenu1OnItem(aName){
  if(aName == menu1OnItemName)
    return;
  if(menu1OnItemName!=""){
    var iWidth1=document.all.item(menu1OnItemName+"_out").widthValue;
    var iHeight1=document.all.item(menu1OnItemName+"_out").heightValue;
    document.all.item(menu1OnItemName+"_out").className = "menu1_normalItem_out";
    document.all.item(menu1OnItemName+"_out").style.width = iWidth1+"px";
    document.all.item(menu1OnItemName+"_out").style.height = iHeight1+"px";
    document.all.item(menu1OnItemName+"_middle").className = "menu1_normalItem_middle";
    document.all.item(menu1OnItemName+"_middle").style.width = iWidth1+"px";
    document.all.item(menu1OnItemName+"_middle").style.height = iHeight1+"px";
    document.all.item(menu1OnItemName+"_in").className = "menu1_normalItem_in";
    document.all.item(menu1OnItemName+"_in").style.width = iWidth1+"px";
    document.all.item(menu1OnItemName+"_in").style.height = iHeight1+"px";
    document.all.item(menu1OnItemName+"_img").src = document.all.item(menu1OnItemName+"_img").normalImg;
  }
  if(aName!=""){
    var iWidth2=document.all.item(aName+"_out").widthValue;
    var iHeight2=document.all.item(aName+"_out").heightValue;
    document.all.item(aName+"_out").className = "menu1_selectItem_out";
    document.all.item(aName+"_out").style.width = (parseInt(iWidth2)+parseInt(5))+"px";
    document.all.item(aName+"_out").style.height = (parseInt(iHeight2)+parseInt(4))+"px";
    document.all.item(aName+"_middle").className = "menu1_selectItem_middle";
    document.all.item(aName+"_middle").style.width = (parseInt(iWidth2)+parseInt(4))+"px";
    document.all.item(aName+"_middle").style.height = (parseInt(iHeight2)+parseInt(2))+"px";
    document.all.item(aName+"_in").className = "menu1_selectItem_in";
    document.all.item(aName+"_in").style.width = iWidth2+"px";
    document.all.item(aName+"_in").style.height = iHeight2+"px";
    if(document.all.item(aName+"_img").selectImg != "")
      document.all.item(aName+"_img").src = document.all.item(aName+"_img").selectImg;
  }
  menu1OnItemName=aName;
}

/************************/
/*���ܣ���menu2���һ����ť*/
/*������*/
/*       aName����ť������*/
/*  aNormalImg����ť������״̬����ʾ��ͼƬ*/
/*  aSelectImg����ť�ڱ�ѡ��״̬����ʾ��ͼƬ*/
/*    aOverImg����ť���������ȥ״̬����ʾ��ͼƬ*/
/*        aDes����ť�ϵ�����*/
/*     aAttach����ť�����Ķ�������onclick��onmouseover��onmouseout��*/
/*       aLeft����ť�������ǰһԪ�ص�����*/
/*      aWidth����ť�Ŀ����أ�*/
/*     aHeight����ť�ĸߣ����أ�*/
/************************/
function menu2_addItem(aName,aNormalImg,aSelectImg,aOverImg,aDes,aAttach,aLeft,aWidth,aHeight){
  var temp="";

  temp = "<span style=\"position:relative;width:"+aLeft+"px;\"></span><span id=\""+aName+"_out\" name=\""+aName+"_out\" widthValue=\""+aWidth+"\" heightValue=\""+aHeight+"\" class=\"menu2_normalItem_out\" style=\"width:"+aWidth+"px;height:"+aHeight+"px;\""+aAttach+"><img id=\""+aName+"_img\" name=\""+aName+"_img\" normalImg=\""+aNormalImg+"\" selectImg=\""+aSelectImg+"\" overImg=\""+aOverImg+"\" src=\""+aNormalImg+"\" border=\"0\" align=\"absmiddle\"><span class=\"menu2_word\">"+aDes+"</span></span>";
  menu2Html = menu2Html+temp;
}

/************************/
/*���ܣ�����menu2*/
/*������*/
/*      aDivName��ָ��menu2����λ�õ�div����*/
/*    aTableName�����ж�������˵�ʱ��ָ�����������˵�������*/
/*aDefaultOnItem��ָ�������˵�Ĭ�ϵ�ѡ�а�ť*/
/************************/
function createMenu2(aDivName,aTableName,aDefaultOnItem){
  menu2Html = "<table id=\""+aTableName+"\" name=\""+aTableName+"\" defaultOnItem=\""+aDefaultOnItem+"\" width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"display:none;\"><tr class=\"menu2_tr\"><td class=\"menu2_Td\">"+menu2Html+"</td></tr></table>";
  document.all.item(aDivName).innerHTML += menu2Html;
  menu2Html="";
}

/************************/
/*���ܣ����ж�������˵�ʱ����ÿ�������˵����ø���Ĭ�ϵ�ѡ�а�ť*/
/*������*/
/*aName�������˵�������*/
/************************/
function setMenu2DefaultOnItem(aName){
  var itemName=document.all.item(aName).defaultOnItem;
  setMenu2OnItem(itemName);
}

/************************/
/*���ܣ���menu2���õ�ǰѡ�а�ť*/
/*������*/
/*aName����ť������*/
/************************/
function setMenu2OnItem(aName){
  if(aName == menu2OnItemName)
    return;
  if(menu2OnItemName!=""){
    var iWidth1=document.all.item(menu2OnItemName+"_out").widthValue;
    document.all.item(menu2OnItemName+"_out").className = "menu2_normalItem_out";
    document.all.item(menu2OnItemName+"_out").style.width = iWidth1+"px";
    document.all.item(menu2OnItemName+"_img").src = document.all.item(menu2OnItemName+"_img").normalImg;
  }
  if(aName!=""){
    var iWidth2=document.all.item(aName+"_out").widthValue;
    document.all.item(aName+"_out").className = "menu2_selectItem_out";
    document.all.item(aName+"_out").style.width = (parseInt(iWidth2)-parseInt(30))+"px";
    if(document.all.item(aName+"_img").selectImg != "")
      document.all.item(aName+"_img").src = document.all.item(aName+"_img").selectImg;
  }
  menu2OnItemName=aName;
}

/************************/
/*���ܣ����ж�������˵�ʱ�����õ�ǰ��ʾ�Ǹ������˵�*/
/*������*/
/*aName�������˵�������*/
/************************/
function displayMenu2Branch(aName){
  if(aName==menu2DisplayBranch)
    return;
  if(menu2DisplayBranch!="")
    document.all.item(menu2DisplayBranch).style.display = "none";
  if(aName!=""){
    document.all.item(aName).style.display = "block";
    setMenu2DefaultOnItem(aName);
  }
  menu2DisplayBranch=aName;
}

/************************/
/*���ܣ���Buttonbar���һ����ť*/
/*������*/
/*          aName����ť������*/
/*     aNormalImg����ť������״̬����ʾ��ͼƬ*/
/*  aMousedownImg����ť����걻����ȥһ˲����ʾ��ͼƬ*/
/*       aOverImg����ť���������ȥ״̬����ʾ��ͼƬ*/
/*   aNormalClass����ť������״̬�����õ�classname��ȱʡΪbutton_normal*/
/*aMousedownClass����ť����갴��ȥ��״̬�����õ�classname��ȱʡΪbutton_mousedown*/
/*     aOverClass����ť��������ȥ��״̬�����õ�classname��ȱʡΪbutton_over*/
/*           aDes����ť�ϵ�����*/
/*        aAttach����ť�����Ķ�������onclick��onmouseover��onmouseout��*/
/*          aLeft����ť�������ǰһԪ�ص�����*/
/*         aWidth����ť�Ŀ����أ�*/
/*        aHeight����ť�ĸߣ����أ�*/
/************************/
function ButtonBar_addItem(aName,aNormalImg,aMousedownImg,aOverImg,aNormalClass,aMousedownClass,aOverClass,aDes,aAttach,aLeft,aWidth,aHeight){
  var temp="";
  var normalClass="button_normal",mousedownClass="button_mousedown",overClass="button_over";

  if(aNormalClass!=""&&aNormalClass!=null)
    normalClass=aNormalClass;
  if(aMousedownClass!=""&&aMousedownClass!=null)
    mousedownClass=aMousedownClass;
  if(aOverClass!=""&&aOverClass!=null)
    overClass=aOverClass;
  temp = "<span style=\"position:relative;width:"+aLeft+"px;\"></span><span id=\""+aName+"\" name=\""+aName+"\" normalClass=\""+normalClass+"\" mousedownClass=\""+mousedownClass+"\" overClass=\""+overClass+"\" class=\""+normalClass+"\" style=\"width:"+aWidth+"px;height:"+aHeight+"px;\""+aAttach+"><img id=\""+aName+"_img\" name=\""+aName+"_img\" normalImg=\""+aNormalImg+"\" keydownImg=\""+aMousedownImg+"\" overImg=\""+aOverImg+"\" src=\""+aNormalImg+"\" border=\"0\"><span class=\"button_word\">"+aDes+"</span></span>";
  ButtonBarHtml = ButtonBarHtml+temp;
}

/************************/
/*���ܣ�����Buttonbar*/
/*������*/
/*aName��ָ��Buttonbar����λ�õ�����*/
/************************/
function createButtonBar(aName){
  ButtonBarHtml = "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><tr><td>"+ButtonBarHtml+"</td></tr></table>";
  document.all.item(aName).innerHTML = ButtonBarHtml;
  ButtonBarHtml="";
}

/************************/
/*���ܣ�����Ƶ�ButtonBar��ť�ϵ�Ч��*/
/*������*/
/*aName��ָ����ť������*/
/************************/
function buttonItemOver(aName){
  document.all.item(aName).className = document.all.item(aName).overClass;
}

/************************/
/*���ܣ������ButtonBar��ťʱ��Ч��*/
/*������*/
/*aName��ָ����ť������*/
/************************/
function buttonItemMouseDown(aName){
  document.all.item(aName).className = document.all.item(aName).mousedownClass;
}

/************************/
/*���ܣ�����ɿ����ButtonBar��ť����ʱ��Ч��*/
/*������*/
/*aName��ָ����ť������*/
/************************/
function buttonItemMouseUp(aName){
  document.all.item(aName).className = document.all.item(aName).overClass;
}

/************************/
/*���ܣ�����뿪ButtonBar��ť��Ч��*/
/*������*/
/*aName��ָ����ť������*/
/************************/
function buttonItemOut(aName){
  document.all.item(aName).className = document.all.item(aName).normalClass;
}

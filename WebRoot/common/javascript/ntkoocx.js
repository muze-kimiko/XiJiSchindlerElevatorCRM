var TANGER_OCX_bDocOpen = false;
var TANGER_OCX_filename;
var TANGER_OCX_actionURL; //For auto generate form fiields
var TANGER_OCX_OBJ; //The Control

//����ΪV1.7��������ʾ��

//�ӱ�������ͼƬ���ĵ�ָ��λ��
function AddPictureFromLocal()
{
	if(TANGER_OCX_bDocOpen)
	{	
    TANGER_OCX_OBJ.AddPicFromLocal(
	"", //·��
	true,//�Ƿ���ʾѡ���ļ�
	true,//�Ƿ񸡶�ͼƬ
	100,//����Ǹ���ͼƬ���������ߵ�Left ��λ��
	100); //����Ǹ���ͼƬ������ڵ�ǰ����Top
	};	
}

//��URL����ͼƬ���ĵ�ָ��λ��
function AddPictureFromURL(URL)
{
	if(TANGER_OCX_bDocOpen)
	{
    TANGER_OCX_OBJ.AddPicFromURL(
	URL,//URL ע�⣻URL���뷵��Word֧�ֵ�ͼƬ���͡�
	true,//�Ƿ񸡶�ͼƬ
	150,//����Ǹ���ͼƬ���������ߵ�Left ��λ��
	150);//����Ǹ���ͼƬ������ڵ�ǰ����Top
	};
}

//�ӱ�������ӡ���ĵ�ָ��λ��
function AddSignFromLocal()
{

   if(TANGER_OCX_bDocOpen)
   {
      TANGER_OCX_OBJ.AddSignFromLocal(
	"�����û�",//��ǰ��½�û�
	"",//ȱʡ�ļ�
	true,//��ʾѡ��
	0,//left
	0)  //top
   }
}

//��URL����ӡ���ĵ�ָ��λ��
function AddSignFromURL(URL)
{
   if(TANGER_OCX_bDocOpen)
   {
      TANGER_OCX_OBJ.AddSignFromURL(
	"�����û�",//��ǰ��½�û�
	URL,//URL
	50,//left
	50)  //top
   }
}

//��ʼ��дǩ��
function DoHandSign()
{
   if(TANGER_OCX_bDocOpen)
   {	
	TANGER_OCX_OBJ.DoHandSign(
	"�����û�",//��ǰ��½�û� ����
	0,//����0��ʵ�� 0��4 //��ѡ����
	0x000000ff, //��ɫ 0x00RRGGBB//��ѡ����
	2,//�ʿ�//��ѡ����
	100,//left//��ѡ����
	50); //top//��ѡ����
	}
}
//��ʼ�ֹ���ͼ���������ֹ���ʾ
function DoHandDraw()
{
	if(TANGER_OCX_bDocOpen)
	{	
	TANGER_OCX_OBJ.DoHandDraw(
	0,//����0��ʵ�� 0��4 //��ѡ����
	0x00ff0000,//��ɫ 0x00RRGGBB//��ѡ����
	3,//�ʿ�//��ѡ����
	200,//left//��ѡ����
	50);//top//��ѡ����
	}
}
//���ǩ�����
function DoCheckSign()
{
	if(TANGER_OCX_bDocOpen)
	{		
	var ret = TANGER_OCX_OBJ.DoCheckSign
	(
	/*��ѡ���� IsSilent ȱʡΪFAlSE����ʾ������֤�Ի���,����ֻ�Ƿ�����֤���������ֵ*/
	);//����ֵ����֤����ַ���
	//alert(ret);
	}	
}
//�˺�����������һ���Զ�����ļ�ͷ��
function TANGER_OCX_AddDocHeader( strHeader )
{
	var i,cNum = 30;
	var lineStr = "";
	try
	{
		for(i=0;i<cNum;i++) lineStr += "_";  //�����»���
		with(TANGER_OCX_OBJ.ActiveDocument.Application)
		{
			Selection.HomeKey(6,0); // go home
			Selection.TypeText(strHeader);
			Selection.TypeParagraph(); 	//����
			Selection.TypeText(lineStr);  //�����»���
			// Selection.InsertSymbol(95,"",true); //�����»���
			Selection.TypeText("��");
			Selection.TypeText(lineStr);  //�����»���
			Selection.TypeParagraph();
			//Selection.MoveUp(5, 2, 1); //�������У��Ұ�סShift�����൱��ѡ������
			Selection.HomeKey(6,1);  //ѡ���ļ�ͷ�������ı�
			Selection.ParagraphFormat.Alignment = 1; //���ж���
			with(Selection.Font)
			{
				NameFarEast = "����";
				Name = "����";
				Size = 12;
				Bold = false;
				Italic = false;
				Underline = 0;
				UnderlineColor = 0;
				StrikeThrough = false;
				DoubleStrikeThrough = false;
				Outline = false;
				Emboss = false;
				Shadow = false;
				Hidden = false;
				SmallCaps = false;
				AllCaps = false;
				Color = 255;
				Engrave = false;
				Superscript = false;
				Subscript = false;
				Spacing = 0;
				Scaling = 100;
				Position = 0;
				Kerning = 0;
				Animation = 0;
				DisableCharacterSpaceGrid = false;
				EmphasisMark = 0;
			}
			Selection.MoveDown(5, 3, 0); //����3��
		}
	}
	catch(err){
		//alert("����" + err.number + ":" + err.description);
	}
	finally{
	}
}
function strtrim(value)
{
	return value.replace(/^\s+/,'').replace(/\s+$/,'');
}

function TANGER_OCX_doFormOnSubmit()
{
	var form = document.forms[0];
  	if (form.onsubmit)
	{
    	var retVal = form.onsubmit();
     	if (typeof retVal == "boolean" && retVal == false)
       	return false;
	}
	return true;
}

//������ֹ��ʾ�޶��������͹��߲˵��������޶���
function TANGER_OCX_EnableReviewBar(boolvalue)
{
	TANGER_OCX_OBJ.ActiveDocument.CommandBars("Reviewing").Enabled = boolvalue;
	TANGER_OCX_OBJ.ActiveDocument.CommandBars("Track Changes").Enabled = boolvalue;
	TANGER_OCX_OBJ.IsShowToolMenu = boolvalue;	//�رջ�򿪹��߲˵�
}

//�򿪻��߹ر��޶�ģʽ
function TANGER_OCX_SetReviewMode(boolvalue)
{
	TANGER_OCX_OBJ.ActiveDocument.TrackRevisions = boolvalue;
}

//������˳��ۼ�����״̬�������������������
function TANGER_OCX_SetMarkModify(boolvalue)
{
	TANGER_OCX_SetReviewMode(boolvalue);
	TANGER_OCX_EnableReviewBar(!boolvalue);
}

//��ʾ/����ʾ�޶�����
function TANGER_OCX_ShowRevisions(boolvalue)
{
	TANGER_OCX_OBJ.ActiveDocument.ShowRevisions = boolvalue;
}

//��ӡ/����ӡ�޶�����
function TANGER_OCX_PrintRevisions(boolvalue)
{
	TANGER_OCX_OBJ.ActiveDocument.PrintRevisions = boolvalue;
}

function TANGER_OCX_SaveToServer()
{
	if(!TANGER_OCX_bDocOpen)
	{
		alert("û�д򿪵��ĵ���");
		return;
	}
	
	TANGER_OCX_filename = prompt("�������Ϊ��","���ĵ�.doc");
	if ( (!TANGER_OCX_filename))
	{
		TANGER_OCX_filename ="";
		return;
	}
	else if (strtrim(TANGER_OCX_filename)=="")
	{
		alert("�����������ļ�����");
		return;
	}
	//alert(TANGER_OCX_filename);
	TANGER_OCX_SaveDoc();
}


//����ҳ�沼��
function TANGER_OCX_ChgLayout()
{
 	try
	{
		TANGER_OCX_OBJ.showdialog(5); //����ҳ�沼��
	}
	catch(err){
		alert("����" + err.number + ":" + err.description);
	}
	finally{
	}
}

//��ӡ�ĵ�
function TANGER_OCX_PrintDoc()
{
	try
	{
		TANGER_OCX_OBJ.printout(true);
	}
	catch(err){
		alert("����" + err.number + ":" + err.description);
	}
	finally{
	}
}

function TANGER_OCX_SaveEditToServer()
{
	if(!TANGER_OCX_bDocOpen)
	{
		alert("û�д򿪵��ĵ���");
		return;
	}
	
	TANGER_OCX_filename = document.all.item("filename").value;
	if ( (!TANGER_OCX_filename))
	{
		TANGER_OCX_filename ="";
		return;
	}
	else if (strtrim(TANGER_OCX_filename)=="")
	{
		alert("�����������ļ�����");
		return;
	}
	//alert(TANGER_OCX_filename);
	TANGER_OCX_SaveDoc();
}

//������ֹ�ļ���>�½��˵�
function TANGER_OCX_EnableFileNewMenu(boolvalue)
{
	TANGER_OCX_OBJ.EnableFileCommand(0) = boolvalue;
}
//������ֹ�ļ���>�򿪲˵�
function TANGER_OCX_EnableFileOpenMenu(boolvalue)
{
	TANGER_OCX_OBJ.EnableFileCommand(1) = boolvalue;
}
//������ֹ�ļ���>�رղ˵�
function TANGER_OCX_EnableFileCloseMenu(boolvalue)
{
	TANGER_OCX_OBJ.EnableFileCommand(2) = boolvalue;
}
//������ֹ�ļ���>����˵�
function TANGER_OCX_EnableFileSaveMenu(boolvalue)
{
	TANGER_OCX_OBJ.EnableFileCommand(3) = boolvalue;
}
//������ֹ�ļ���>���Ϊ�˵�
function TANGER_OCX_EnableFileSaveAsMenu(boolvalue)
{
	TANGER_OCX_OBJ.EnableFileCommand(4) = boolvalue;
}
//������ֹ�ļ���>��ӡ�˵�
function TANGER_OCX_EnableFilePrintMenu(boolvalue)
{
	TANGER_OCX_OBJ.EnableFileCommand(5) = boolvalue;
}
//������ֹ�ļ���>��ӡԤ���˵�
function TANGER_OCX_EnableFilePrintPreviewMenu(boolvalue)
{
	TANGER_OCX_OBJ.EnableFileCommand(6) = boolvalue;
}

function TANGER_OCX_OpenDoc(docid)
{

	TANGER_OCX_OBJ = document.all.item("TANGER_OCX");
	if(docid != "")
	{	
		TANGER_OCX_OBJ.OpenFromURL("controlword.do?method=GetFileMethod&idput=" + docid);
	}
	else
	{
		TANGER_OCX_OBJ.CreateNew("Word.Document");
	}
}

function TANGER_OCX_OnDocumentOpened(str, obj)
{
	TANGER_OCX_bDocOpen = true;		
}

function TANGER_OCX_OnDocumentClosed()
{
   TANGER_OCX_bDocOpen = false;
}

function TANGER_OCX_SaveDoc()
{
	var newwin,newdoc;

	if(!TANGER_OCX_bDocOpen)
	{
		alert("û�д򿪵��ĵ���");
		return;
	}

	try
	{
	 	if(!TANGER_OCX_doFormOnSubmit())return; //������ڣ���ִ�б���onsubmit������
	 	//���ÿؼ���SaveToURL����
		var retHTML = TANGER_OCX_OBJ.SaveToURL
		(
			document.forms[0].action,  //�˴�Ϊuploadedit.asp
			"EDITFILE",	//�ļ�����������,����ѡ,��������<input type=file name=..>��name�����ظ�����
			"", //��ѡ�������Զ������ݣ�ֵ�ԣ���&�ָ����磺myname=tanger&hisname=tom,һ��Ϊ��
			document.forms[0].filename.value, //�ļ���,�˴��ӱ������ȡ��Ҳ���Զ���
			"quoteBookForm" //�ؼ��������ύ���ܿ�������ͬʱ�ύѡ���ı�����������.�˴���ʹ��id�������
		); //�˺������ȡ�ӷ������Ϸ��ص���Ϣ�����浽����ֵ�С�
		//��һ���´�����ʾ��������
		newwin = window.open("","_blank","left=200,top=200,width=400,height=300,status=0,toolbar=0,menubar=0,location=0,scrollbars=1,resizable=1",false);
		newdoc = newwin.document;
		newdoc.open();
		newdoc.write("<html><head><title>���ص�����</title></head><body><center><hr>")
		//newdoc.write(retHTML+"<hr>");
		newdoc.write("<input type=button VALUE='�رմ���' onclick='window.close()'>");
		newdoc.write('</center></body></html>');
		newdoc.close();
		if(window.opener) //��������ڴ��ڣ�ˢ�²��رյ�ǰ����
		{
			window.opener.quoteBookForm.wordflag.value="Y";
			//window.opener.location.reload();
			
		}
		if(0 == TANGER_OCX_OBJ.StatusCode)window.close();
	}
	catch(err){
		alert("���ܱ��浽URL��" + err.number + ":" + err.description);
	}
	finally{
	}
}
function TANGER_OCX_SaveAsHTML()
{
	var newwin,newdoc;

	if(!TANGER_OCX_bDocOpen)
	{
		alert("û�д򿪵��ĵ���");
		return;
	}
	try
	{
		//���ÿؼ���PublishAsHTMLToURL����
		var retHTML = TANGER_OCX_OBJ.PublishAsHTMLToURL
			(
				"uploadhtmls.jsp",
				"HTMLFILES", //�ļ�����������,����ѡ,��������ļ����Դ����ϴ�
				"",
				document.forms[0].htmlfile.value
				//�˴�ʡ���˵�5��������HTML FORM����������id.����,�����ύ������
				//ֻ�ύ���е�html�ļ���ص��ļ�
			);
		newwin = window.open("","_blank","left=200,top=200,width=400,height=300,status=0,toolbar=0,menubar=0,location=0,scrollbars=1,resizable=1",false);
		newdoc = newwin.document;
		newdoc.open();
		newdoc.write("<center><hr>"+retHTML+"<hr><input type=button VALUE='�رմ���' onclick='window.close()'></center>");
		newdoc.close();	
		newwin.focus();
		if(window.opener) //��������ڴ��ڣ�ˢ�²��رյ�ǰ����
		{
			window.opener.location.reload();
		}
	}
	catch(err){
		alert("���ܱ��浽URL��" + err.number + ":" + err.description);
	}
	finally{
	}
}
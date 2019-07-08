<%@ page contentType="text/css; charset=GBK" %>

body{
  background-color:white;
  font-size:9pt;
  font-family: ����;
  margin:0px 0px 0px 0px;  /*top right bottom left*/
  padding:0px 0px 0px 0px;
  scrollbar-face-color: #DEE3E7;
  scrollbar-highlight-color: #FFFFFF;
  scrollbar-shadow-color: #DEE3E7;
  scrollbar-3dlight-color: #D1D7DC;
  scrollbar-arrow-color:  #006699;
  scrollbar-track-color: #EFEFEF;
  scrollbar-darkshadow-color: #98AAB1;
}

input{
  height:18px;
}
select{
	font-size:9pt;
}
fieldset{
	padding-top:1px;
	padding-bottom:1px;
}

td{
  font-size:9pt;
  font-family: ����;
}
a:link {
	font-size: 12px;
	color: 0000ff;
	text-decoration: underline;
}
a:visited {
	font-size:12px;
	color: #0505BE;
	text-decoration: none;
}
a:hover {
	color:#1E72C6;
	text-decoration: underline;
}
a.active{
	color:#FEFE34;
	text-decoration:none;
}
.subBody{
  margin:3px 5px 0px 5px;
}

/*��Ϣ����ϵͳ*/

/**tree body**/

.tree_body{
  background-color:white;
  font-size:9pt;
  font-family: ����;
  margin:0px 0px 0px 0px;  /*top right bottom left*/
  padding-top:10px;
  border-right:1px #666666 solid;
  scrollbar-face-color: #DEE3E7;
  scrollbar-highlight-color: #FFFFFF;
  scrollbar-shadow-color: #DEE3E7;
  scrollbar-3dlight-color: #D1D7DC;
  scrollbar-arrow-color:  #006699;
  scrollbar-track-color: #EFEFEF;
  scrollbar-darkshadow-color: #98AAB1;
}


.Default_menuFrame{  /*��ӦMenuAndButtonbar.css���menu1_tr��menu2_tr*/
  width:100%;
  height:82px;
}
.Default_menu1Frame{  /*��ӦMenuAndButtonbar.css���menu1_tr*/
  width:100%;
  height:52px;
}
.Default_menu2Frame{  /*��ӦMenuAndButtonbar.css���menu2_tr*/
  width:100%;
  height:30px;
}
.Default_searchFrame{  /*��ӦsearchTable*/
  width:100%;
  height:78px;
}
.Default_listFrame{   /*��ӦQueryList.css���listUtil*/
  width:100%;
  height:355px;
}
.Default_contentTr{
  background-color:#e6f1c6;
}
.Default_contentTd{
  padding:6px;
  border-right:1px #ffffff solid;
  border-left:1px #ffffff solid;
}
.Default_frameTr{
  background-color:#eeeeee;
  height:9px;
}
.Default_frame_topLeftTd{
  width:14px;
  background-image:url('/WebRoot/common/images/frame_corner_topleft.gif');
  background-repeat:no-repeat;
  background-position:left;
}
.Default_frame_topMiddleTd{
  width:97%;
  background-image:url('/WebRoot/common/images/frame_topbg.gif');
  background-repeat:repeat-x;
}
.Default_frame_topRightTd{
  width:14px;
  background-image:url('/WebRoot/common/images/frame_corner_topright.gif');
  background-repeat:no-repeat;
  background-position:right;
}
.Default_frame_bottomLeftTd{
  width:14px;
  background-image:url('/WebRoot/common/images/frame_corner_bottomleft.gif');
  background-repeat:no-repeat;
  background-position:left;
}
.Default_frame_bottomMiddleTd{
  width:97%;
  background-image:url('/WebRoot/common/images/frame_bottombg.gif');
  background-repeat:repeat-x;
}
.Default_frame_bottomRightTd{
  width:14px;
  background-image:url('/WebRoot/common/images/frame_corner_bottomright.gif');
  background-repeat:no-repeat;
  background-position:right;
}
.Default_frame_outBorder{
  width:100%;Default_contentTd
  border-right:1px #d4d4d4 solid;
  border-left:1px #d4d4d4 solid;
  background-color:#ffffff;
}

.searchUtil{
  /*height:78px;*/
  border-bottom:1px #d4d4cc solid;
  background-image:url('/WebRoot/common/images/search_bg.gif');
  background-repeat:no-repeat;
  background-position:left;
}
.searchTable{
  border-bottom:1px #d4d4cc solid;
  background-image:url('/WebRoot/common/images/search_bg.gif');
  background-repeat:no-repeat;
  background-position:left;
  background-color:#f4f2f2;
}
.searchUtil_input{
  height:18px;
  border:1px #859bb5 solid;
}
.searchUtil_select{
  height:18px;
}
.searchUtil_btnOut{
  position:relative;
  top:0px;
  width:50px;
  border:1px #939EB2 solid;
}
.searchUtil_btnIn{
  width:48px;
  height:18px;
  padding-top:3px;
  border-right:1px #dcdcd9 solid;
  border-bottom:1px #dcdcd9 solid;
  text-align:center;
  background-color:white;
  background-image:url('/WebRoot/common/images/Btn_united_bg.gif');
  cursor:hand;
}

.btn_common{
	width:55px;
	height:20px;
	padding-top:4px;
	padding-left: 0px;
	border:1px #939EB2 solid;
	text-align:center;
	background-image:url('/WebRoot/common/images/Btn_united_bg.gif');
	cursor:hand;
	font-size: 9pt;
}
/**��������**/
.pop_body{
  	background-color:#C2CEEB;
  	font-size:9pt;
  	font-family: ����;
 		margin:8px 8px 8px 8px;
  	padding:0px 0px 0px 0px;
  	scrollbar-face-color: #DEE3E7;
  	scrollbar-highlight-color: #FFFFFF;
  	scrollbar-shadow-color: #DEE3E7;
  	scrollbar-3dlight-color: #D1D7DC;
  	scrollbar-arrow-color:  #006699;
  	scrollbar-track-color: #EFEFEF;
  	scrollbar-darkshadow-color: #98AAB1;
}
.pop_btn
{
	background-color:#C2CEEB;
	border:1px #939EB2 solid;
	width:55px;
	height:20px;
	padding-top:4px;
	padding-left: 0px;
	text-align:center;
	cursor:hand;
	font-size: 9pt;
}
.pop_edit
{
	border-top: 1px solid #ffffff;
	border-left: 1px solid #fffffff;
	border-right: 2px ridge #95A8D7;
	border-bottom: 2px ridge #95A8D7;
	background-color:#E0E7F8;
	padding-top:6px;
	padding-left:8px;
	padding-bottom:6px;
	padding-right:8px;
	vertical-align:top;
}
.pop_submit_td
{
	height:30px;
	text-align:right;
}
.pop_input
{
	border-right:1px #253E7A solid;
 	border-left:1px #253E7A solid;
	border-top:1px #253E7A solid;
 	border-bottom:1px #253E7A solid;
	background-color:#ffffff;
	font-size:9pt;
	height:18px;
}
.pop_textarea
{
	border-right:1px #253E7A solid;
 	border-left:1px #253E7A solid;
	border-top:1px #253E7A solid;
 	border-bottom:1px #253E7A solid;
	background-color:#ffffff;
	font-size:9pt;
}
.pop_file{
	height:18px;
	width:55px;
	text-align:center;
	padding-top:1px;
	border:1px solid #253E7A;
	background-color:#D4D0C8;
}
.pop_select
{
	font-size:9pt;
}
.must_input
{
	font-size:9pt;
	color:red;
}

/**�Ի���**/
/*������Ϣ��ʾ�򱳾�*/
.dialog_body
{
	background-color:D3E0FF;
	font-size:9pt;
	font-family: ����;
	margin:0px 0px 0px 0px;  /*top right bottom left*/
	padding:0px 0px 0px 0px;
	scrollbar-face-color: #DEE3E7;
	scrollbar-highlight-color: #FFFFFF;
	scrollbar-shadow-color: #DEE3E7;
	scrollbar-3dlight-color: #D1D7DC;
	scrollbar-arrow-color:  #006699;
	scrollbar-track-color: #EFEFEF;
	scrollbar-darkshadow-color: #98AAB1;
}
.dialog_info_bg
{
	background-image:url('/WebRoot/common/images/dialog/win_info_bg.jpg');
	background-repeat:no-repeat;
	background-position:left top;
	vertical-align:top;
}
.dialog_info_td
{
	padding-left:15px;
	padding-top:20px;
}
.dialog_pic_td
{
	padding-left:20px;
	padding-top:20px;
	width:30%;
}
.dialog_btn_td
{
	height:35px;
}

/*��ʼRecordRightҳ�������CSS����*/
.right_select
{
  cursor:hand;
  font-size:10.8pt;
  color:red;
}
.RecordRight_FolderTreeDiv
{
  position:relative;
  width:240px;
  height:230px;
  top:0px;
  left:0px;
  border-top:2px solid #333366;
  border-bottom:1px solid #eeeeee;
  border-left:2px solid #333366;
  border-right:1px solid  #eeeeee;
  background-color: #FFFFFF;
  overflow:auto;
}

/*����RecordRightҳ�������CSS����*/

/*����MFolder_iFrmҳ��FolderTree��CSS*/
.MFolder_iFrm_FolderTreeDiv
{
  width:265px;
  height:220px;
  background-color:white;
  border-top:2px solid #333366;
  border-bottom:2px solid #eeeeee;
  border-left:2px solid #333366;
  border-right:2px solid  #eeeeee;
  overflow:auto;
}
/**��Ϣ��¼�༭����**/
.record_body
{
  	background-color:#E0E7F8;
  	font-size:9pt;
  	font-family: ����;
  	padding:0px 0px 0px 0px;
  	scrollbar-face-color: #DEE3E7;
  	scrollbar-highlight-color: #FFFFFF;
  	scrollbar-shadow-color: #DEE3E7;
  	scrollbar-3dlight-color: #D1D7DC;
  	scrollbar-arrow-color:  #006699;
  	scrollbar-track-color: #EFEFEF;
  	scrollbar-darkshadow-color: #98AAB1;
}

td{
  font-size:9pt;
  font-family: ����;
}
.subBody{
  margin:3px 5px 0px 5px;
}
.Default_menuFrame{  /*��ӦMenuAndButtonbar.css���menu1_tr��menu2_tr*/
  width:100%;
  height:82px;
}
.Default_menu1Frame{  /*��ӦMenuAndButtonbar.css���menu1_tr*/
  width:100%;
  height:52px;
}
.Default_menu2Frame{  /*��ӦMenuAndButtonbar.css���menu2_tr*/
  width:100%;
  height:30px;
}
.Default_searchFrame{  /*��ӦsearchTable*/
  width:100%;
  height:78px;
}
.Default_listFrame{   /*��ӦQueryList.css���listUtil*/
  width:100%;
  height:355px;
}
.Default_contentTr{
  background-color:#ffffff;
}
.Default_contentTd{
  padding:6px;
  border-right:1px #ffffff solid;
  border-left:1px #ffffff solid;
}
.Default_frameTr{
  height:9px;
}
.Default_frame_topLeftTd{
  width:14px;
  background-image:url('/WebRoot/common/images/frame_corner_topleft.gif');
  background-repeat:no-repeat;
  background-position:left;
}
.Default_frame_topMiddleTd{
  width:97%;
  background-image:url('/WebRoot/common/images/frame_topbg.gif');
  background-repeat:repeat-x;
}
.Default_frame_topRightTd{
  width:14px;
  background-image:url('/WebRoot/common/images/frame_corner_topright.gif');
  background-repeat:no-repeat;
  background-position:right;
}
.Default_frame_bottomLeftTd{
  width:14px;
  background-image:url('/WebRoot/common/images/frame_corner_bottomleft.gif');
  background-repeat:no-repeat;
  background-position:left;
}
.Default_frame_bottomMiddleTd{
  width:97%;
  background-image:url('/WebRoot/common/images/frame_bottombg.gif');
  background-repeat:repeat-x;
}
.Default_frame_bottomRightTd{
  width:14px;
  background-image:url('/WebRoot/common/images/frame_corner_bottomright.gif');
  background-repeat:no-repeat;
  background-position:right;
}
.Default_frame_outBorder{
  width:100%;
  border-right:1px #d4d4d4 solid;
  border-left:1px #d4d4d4 solid;
	background-color:#ffffff;
}
.Default_frame_inBorder{
  width:100%;
  padding:0px 5px 5px 5px;
  border-right:2px #e4e2dc solid;
  border-left:2px #e4e2dc solid;
  background-color:#ffffff;
}

.Default_frame_inBorder{
  width:100%;
  padding:0px 5px 5px 5px;
  border-right:2px #e4e2dc solid;
  border-left:2px #e4e2dc solid;
  background-color:#f4f2f2;
}

.searchUtil{
  /*height:78px;*/
  border-bottom:1px #d4d4cc solid;
  background-image:url('/WebRoot/common/images/search_bg.gif');
  background-repeat:no-repeat;
  background-position:left;
}
.searchTable{
  height:78px;
  border-bottom:1px #d4d4cc solid;
  background-image:url('/WebRoot/common/images/search_bg.gif');
  background-repeat:no-repeat;
  background-position:left;
  background-color:#f4f2f2;
}
.searchUtil_input{
  width:120px;
  height:22px;
  border:1px #859bb5 solid;
}
.searchUtil_select{
  width:120px;
  height:22px;
}
.searchUtil_btnOut{
  position:relative;
  top:0px;
  width:50px;
  border:1px #939EB2 solid;
}
.searchUtil_btnIn{
  width:48px;
  height:18px;
  padding-top:3px;
  border-right:1px #dcdcd9 solid;
  border-bottom:1px #dcdcd9 solid;
  text-align:center;
  background-color:white;
  background-image:url('/WebRoot/common/images/Btn_united_bg.gif');
  cursor:hand;
}
/*
.searchUtil_btnOut{
  position:relative;
  top:-1px;
  width:50px;
  height:18px;
  border:1px #949794 solid;
}
.searchUtil_btnIn{
  width:48px;
  height:16px;
  padding-top:3px;
  border-right:1px #dcdcd9 solid;
  border-bottom:1px #dcdcd9 solid;
  text-align:center;
  background-color:white;
  cursor:hand;
}
/**�Ի���**/
/*������Ϣ��ʾ�򱳾�*/
.dialog_body
{
	background-color:D3E0FF;
	font-size:9pt;
	font-family: ����;
	margin:0px 0px 0px 0px;  /*top right bottom left*/
	padding:0px 0px 0px 0px;
	scrollbar-face-color: #DEE3E7;
	scrollbar-highlight-color: #FFFFFF;
	scrollbar-shadow-color: #DEE3E7;
	scrollbar-3dlight-color: #D1D7DC;
	scrollbar-arrow-color:  #006699;
	scrollbar-track-color: #EFEFEF;
	scrollbar-darkshadow-color: #98AAB1;
}
.dialog_info_bg
{
	background-image:url('/WebRoot/common/images/dialog/win_info_bg.jpg');
	background-repeat:no-repeat;
	background-position:left top;
	vertical-align:top;
}
.dialog_info_td
{
	padding-left:15px;
	padding-top:20px;
}
.dialog_pic_td
{
	padding-left:20px;
	padding-top:20px;
	width:30%;
}
.dialog_btn_td
{
	height:35px;
}
*/
/**
* showpage.jspҳ�� ���ڼ���td����ʾ���
*/
.showpage_td{
	padding-left:4px;
	padding-right:4px;
}
/**
* ���������û� ģ��
*/
.top_navigation{
	border:#d4d4d4 1px solid;
	background-color:white;
}
/**
* EditFile.jsp
*/
.EditFile_bg{
  /*height:78px;*/
  /*border-bottom:1px #d4d4cc solid;*/
  background-image:url('/WebRoot/common/images/content_block_bg.gif');
  background-repeat:no-repeat;
  background-position:left top;
  border-right:1px #646c94 solid;
  border-bottom:1px #646c94 solid;
  border-left:1px #646c94 solid;
  border-top:1px #646c94 solid;
	background-color:#ffffff;
}





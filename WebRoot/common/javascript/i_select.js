//��a_Fromsel�����һ��Ԫ�ص�a_Tosel
function doselect(a_Fromsel,a_Tosel){
	var l_item;
	var i;
	var l_Fromsel;
	var l_Tosel;
	l_Fromsel = document.all.item(a_Fromsel);
	l_Tosel = document.all.item(a_Tosel);

	for(i=0;i<l_Fromsel.options.length;i++)
	{
 		if (l_Fromsel.options(i).selected)
		{
			if(!isChosen(l_Tosel,l_Fromsel.options(i).value)){
				l_item=document.createElement("OPTION");
				l_item.value=l_Fromsel.options(i).value;
				l_item.text=l_Fromsel.options(i).text;
				l_Tosel.add(l_item);
			}
		}
	}

	DelSelected(a_Fromsel);
}

//ɾ��a_sSelectName��ѡ�е�һ��Ԫ��
function DelSelected(a_sSelectName){
	var l_Fromsel;
	l_Fromsel = document.all.item(a_sSelectName);
	for(i=l_Fromsel.options.length-1;i>=0;i--)
	{
		if(l_Fromsel.options(i).selected)
			l_Fromsel.remove(i);
	}
}

//ɾ��a_sSelectName�е�����Ԫ��
function DelAll(a_sSelectName){
	var l_Fromsel;
	l_Fromsel = document.all.item(a_sSelectName);
	for(i=l_Fromsel.options.length-1;i>=0;i--)
	{
		l_Fromsel.remove(i);
	}
}

//��a_Fromsel������Ԫ����ӵ�a_Tosel
function SelectAll(a_Fromsel,a_Tosel){
	var l_item;
	var i;
	var l_Fromsel;
	var l_Tosel;

	l_Fromsel = document.all.item(a_Fromsel);
	l_Tosel = document.all.item(a_Tosel);

	for(i=0;i<l_Fromsel.options.length;i++)
	{
		if(!isChosen(l_Tosel,l_Fromsel.options(i).text)){
			l_item=document.createElement("OPTION");
			l_item.value=l_Fromsel.options(i).value;
			l_item.text=l_Fromsel.options(i).text;
			l_Tosel.add(l_item);
		}
	}
	DelAll(a_Fromsel);
}

//�ж�a_oSelect���Ƿ��Ѵ���a_sCode
function isChosen(a_oSelect,a_sCode)
{
	var i;
	for(i=0; i<a_oSelect.options.length; i++)
	{
		if(a_oSelect.options(i).value == a_sCode)
		{
			return(true);
		}
	}
	return(false);
}

//�ж�a_oSelect���Ƿ��Ѵ���Text a_sText
function isChosenText(a_oSelect,a_sText){
	var i;
	for(i=0; i<a_oSelect.options.length; i++)
	{
		if(a_oSelect.options(i).text == a_sText)
		{
			return(true);
		}
	}
	return(false);
}


//��a_sSelectName���һ��Ԫ��
function SetSelectOption(a_sSelectName,a_sOptionValue,a_sOptionText){
	var l_oSelect;
	var l_oItem;
	l_oSelect = document.all.item(a_sSelectName);

	if(! isChosen(l_oSelect,a_sOptionValue)){
		l_oItem=document.createElement("OPTION");
		l_oItem.value=a_sOptionValue;
		l_oItem.text=a_sOptionText;
		l_oSelect.add(l_oItem);
	}
}

//ɾ��һԪ��
function DelSelectOption(a_sValue,a_sSelectName){
	var l_oItem;
	var l_oSelect;
	var i;

	l_oSelect = document.all.item(a_sSelectName);

	for(i=0;i<l_oSelect.options.length;i++){
		if(l_oSelect.options(i).value == a_sValue){
			l_oSelect.remove(i);
		}
	}
}

//��a_sSelectName��Ԫ�������ַ�������a_sIDString��
function BuildGroupStr(a_sSelectName,a_sIDString){
	var i;
	var l_str_group = document.all.item(a_sSelectName);
	var l_groupstring = document.all.item(a_sIDString);
	l_groupstring.value = "";
	for(i=0;i<l_str_group.options.length;i++){
		l_groupstring.value += l_str_group.options(i).value;
		if (i != l_str_group.options.length -1)
			l_groupstring.value += ", ";
	}
}

//��a_sSelectName��ѡ���Ԫ�����ϻ�������һλ,a_iMoveTypeΪ0�����ƣ�1���ϣ�
function MoveSelectOption(a_sSelectName,a_iMoveType){
	var i;
	var l_oSelect = document.all.item(a_sSelectName);
	var l_value;
	var l_text;

	if(a_iMoveType == 0){
		for(i=0;i<l_oSelect.options.length;i++){
			if(l_oSelect.options(i).selected && i < l_oSelect.options.length - 1){
				l_value = l_oSelect.options(i).value;
				l_text = l_oSelect.options(i).text;

				l_oSelect.options(i).value = l_oSelect.options(i + 1).value;
				l_oSelect.options(i).text = l_oSelect.options(i + 1).text;

				l_oSelect.options(i + 1).value = l_value;
				l_oSelect.options(i + 1).text = l_text;

				l_oSelect.options(i).selected = false;
				l_oSelect.options(i + 1).selected = true;

				return;
			}
		}
	}else{
		for(i=l_oSelect.options.length - 1;i>=0;i--){
			if(l_oSelect.options(i).selected && i > 0){
				l_value = l_oSelect.options(i).value;
				l_text = l_oSelect.options(i).text;

				l_oSelect.options(i).value = l_oSelect.options(i - 1).value;
				l_oSelect.options(i).text = l_oSelect.options(i - 1).text;

				l_oSelect.options(i - 1).value = l_value;
				l_oSelect.options(i - 1).text = l_text;

				l_oSelect.options(i).selected = false;
				l_oSelect.options(i - 1).selected = true;

				return;
			}
		}
	}
}

//ȡ��a_sSelectNameѡ�����value��text
function GetSelected(a_sSelectName){
	var l_Fromsel;
	var l_array = Array(2);
	l_Fromsel = document.all.item(a_sSelectName);
	for(i=l_Fromsel.options.length-1;i>=0;i--)
	{
		if(l_Fromsel.options(i).selected){
			l_array[0] = l_Fromsel.options(i).value;
			l_array[1] = l_Fromsel.options(i).text;
		}
	}
	return l_array;
}

//��a_sSelectName���һ��Ԫ��
function SetOptionValue(a_sSelectName,a_sOptionCode,a_sOptionValue,a_sOptionText){
	var l_oSelect;
	var l_oItem;
	l_oSelect = document.all.item(a_sSelectName);

	var i;
	for(i=0; i<l_oSelect.options.length; i++)
	{
		if(l_oSelect.options(i).value == a_sOptionCode)
		{
			l_oSelect.options(i).value = a_sOptionValue;
			l_oSelect.options(i).text = a_sOptionText;
		}
	}
}

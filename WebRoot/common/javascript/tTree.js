//--��ǿ������----------------------------------------------------
function tArray() //������ǿ������
{
	this.nodes = new Array();
	this.count = 0;
	this.Find = tArrayFindNode;
	this.Add = tArrayAddNode;
	this.AddA = tArrayAddNodeA;
	this.Remove = tArrayRemoveNode;
	this.Update = tArrayUpdateNode;
	this.Clone = tArrayCloneNode;
	this.insertBefore = tArrayInsertBefore;
	this.toString = tArraytoString;
}

function tArraytoString()
{
	var l_node,l_node2,l_return = '';
	for ( l_node in this.nodes )
	{
		l_return += "\n==tArraytoString==this.nodes['" + l_node + "']=";
		l_node2 = this.nodes[l_node];
		if ( l_node2.toString != null )
		{
			l_return += l_node2.toString();
		}else{
			l_return += "null";
		}
	}
	return l_return;
}

function tArrayFindNode(a_NodeName) //���ݴ�����һ�����
{
	if ( this.nodes == null )
		return null;
	return this.nodes[a_NodeName];
}

/*
function tArrayAddNodeA(a_NodeName,a_NodeValue)
���ܣ�������һ������Ԫ��
���룺
	a_NodeName��Ԫ�ش���
	a_NodeValue��Ԫ��ֵ
�����
	ʧ�ܣ�
		null������ͬ�����
	�ɹ�����������Ԫ�ص�����
*/
function tArrayAddNodeA(a_NodeName,a_NodeValue)
{
	var l_node = this.Find(a_NodeName);
	if (l_node != null) //�Ѿ�����ͬ�����
		return null;
	this.nodes[a_NodeName] = a_NodeValue;
	this.count++;
	return this.count;
}

/*
function tArrayAddNode(a_NodeName,a_NodeValue)
���ܣ�����һ������Ԫ�ص���ǿ�汾
	�������ֻ��һ������������Ϊ�������һ�����飬�Ѹ������Ԫ�س������뱾������
���룺
	a_NodeName��Ԫ�ش���
	a_NodeValue��Ԫ��ֵ
�����
	ʧ�ܣ�
		null������ͬ�����
		-2������ֻ��һ��������������Array��tArray����
		-3��ȡ����������ָ�����
		-4�����������������1��2
	�ɹ�����������Ԫ�ص�����
*/
function tArrayAddNode(a_NodeName,a_NodeValue)
{
	var l_node, l_nodes;

	switch(arguments.length)
	{
	case 1: //ֻ��һ������
		if ( arguments[0].constructor != tArray && arguments[0].constructor != Array )
			return -2;
		l_nodes = (arguments[0].constructor == tArray) ? arguments[0].nodes : l_nodes = arguments[0];
		if ( l_nodes == null )
			return -3;
		for ( l_node in l_nodes )
		{
			this.AddA(l_node,l_nodes[l_node]);
		}
		break;
	case 2: //����������
		return this.AddA(a_NodeName,a_NodeValue);
		break;
	default:
		return -4;
	}

	return this.count;
}

function tArrayInsertBefore(a_NodeName1,a_NodeName2,a_node)
{
	var l_node, l_array, o, o2;
	o = this.Find(a_NodeName1);
	o2 = this.Find(a_NodeName2);
	if ( o == null || o2 != null || a_NodeName2 == null || a_node == null)
		return null;
	l_array= new Array();
	for ( l_node in this.nodes )
	{
		if ( l_node == a_NodeName1 )
		{
			l_array[a_NodeName2] = a_node;
			this.count++;
		}
		l_array[l_node] = this.nodes[l_node];
	}
	this.nodes = l_array;
	return this.count;
}

function tArrayCloneNode()
{
	var l_node, l_array;
	l_array= new Array();
	for ( l_node in this.nodes )
	{
		l_array[l_node] = this.nodes[l_node];
	}
	return l_array;
}

function tArrayRemoveNode(a_NodeName)
{
	var l_node = this.Find(a_NodeName),l_node1;
	var l_array;

	l_array= new Array();
	if (l_node == null) //�Ҳ���Ҫɾ���Ľ��
		return null;
	//----------------hupw modify-----------
	for ( l_node1 in this.nodes )
	{
		if (l_node1!=a_NodeName)
		{l_array[l_node1] = this.nodes[l_node1];}
	}
	this.nodes = l_array;
	//--------------------------------------
	//this.nodes[a_NodeName] = null;
	this.count--;
	return this.count;
}

function tArrayUpdateNode(a_NodeName,a_NodeValue)
{
	var l_node = this.Find(a_NodeName);
	if (l_node == null) //�Ҳ���Ҫ���ĵĽ��
		return false;
	this.nodes[a_NodeName] = a_NodeValue;
	return true;
}


//--�������-------------------------------------------------------------

function tNodeBrotherCount()
{
	var l_node,l_ary,i=0;
	if ( this.paraNode == null )
	{
		l_ary = this.tree.nodes.nodes;
		for ( l_node in l_ary )
		{
			if ( l_ary[l_node].paraNode != null )
				continue;
			i++;
		}
		return i;
	}else{
		return this.paraNode.children.count;
	}
}

function tNodeIndex()
{
	var l_node,l_ary,i=0,s='';
	if ( this.paraNode == null )
	{
		l_ary = this.tree.nodes.nodes;
		for ( l_node in l_ary )
		{
			if ( l_ary[l_node].paraNode != null )
				continue;
			if ( l_node == this.nodeName )
			{
				return i;
			}
			i++;
		}
		return -1;
	}else{
		l_ary = this.paraNode.children.nodes;
		for ( l_node in l_ary )
		{
			s += l_node + ':';
			if ( l_node == this.nodeName )
			{
				return i;
//				s+= i;
//				return s;
			}
			i++;
		}
		return -1;
	}
}

function tNodeLayer()
{
	if ( this.paraNode == null )
		return 0;
	else
		return ( this.paraNode.Layer() + 1 );
}

function tNodeisLeaf()
{
	return ( this.children.count == 0 );
}

function tNodetoString()
{
	var l_return = '';
	l_return += "\n==tNodetoString==";
	l_return += "\nnodeName=" + this.nodeName;
	l_return += "\nparaName=" + this.paraName;
	l_return += "\nLayer=" + this.Layer();
	l_return += "\nIndex=" + this.Index();
	l_return += "\nBrotherCount=" + this.BrotherCount();
	l_return += "\nchildren.count=" + this.children.count;
	return l_return;
}

function tNodeUpdate(a_ParaName,a_ParaNode,a_tAry)
{
	this.paraName = a_ParaName;
	this.paraNode = a_ParaNode;
	this.array = a_tAry;
}


//--����-------------------------------------------------------------

function tTreetoString()
{
	return this.nodes.toString();
}

function tTreeCount()
{
	return this.nodes.count;
}

function tFindNode(a_NodeName) //���ݴ�����һ�����
{
	if ( this.nodes == null )
		return null;
	return this.nodes.Find(a_NodeName);
}

function tAddNode(a_NodeName,a_ParaName,a_tAry)
{
	var l_node = null,l_node2;
/*
����ж��߼���tArray���Add���������Ѿ�����
	l_node = this.Find(a_NodeName);
	if ( l_node != null ) //�Ѿ�����ͬ�����
		return null;
*/
	if ( a_ParaName != null )
	{
		l_node = this.Find(a_ParaName);
		if ( l_node == null && (a_ParaName == "" || a_ParaName=="null")) //�Ҳ��������
			a_ParaName = null; //�Ҳ������ڵ㣬�ѽڵ��ɸ��ڵ�
    else if(l_node == null) //���ڵ�ֵ��Ϊ�ջ�null
			return -1;
	}
	l_node2 = new tNode(a_NodeName,a_ParaName,l_node,a_tAry,this);

	//�ڸ����Ķ��Ӽ�������ӱ����
	if ( l_node != null && l_node.children.Add(a_NodeName,l_node2) == null )
		return -2;

	return this.nodes.Add(a_NodeName,l_node2);
}

function tRemoveNode(a_NodeName,a_ifRemoveChildren)
{
	var l_node,l_node2;

	l_node = this.nodes.Find(a_NodeName); //l_node��tNode��

	if ( a_ifRemoveChildren == null )
	{
		//ȥ�������Ķ��Ӽ����е����нڵ�
		for ( l_node2 in l_node.children.nodes ) //l_node.children��tArray��
		{
			this.Remove(l_node2);
		}
	}

	//�ڸ����Ķ��Ӽ�����ȥ�������
	if ( l_node.paraNode != null ) //l_node.paraNode��tNode��
		l_node.paraNode.children.Remove(a_NodeName);

	return this.nodes.Remove(a_NodeName);
}

function tUpdateNode(a_NodeName,a_ParaName,a_tAry)
{
	var l_node = null,l_node2;

	l_node2 = this.Find(a_NodeName); //l_node2��tNode��
	if ( l_node2 == null ) //�Ҳ������
		return -1;

	if ( a_ParaName != null)
	{
		l_node = this.Find(a_ParaName); //l_node��tNode��
		if ( l_node == null ) //�Ҳ��������
			return -2;

		if ( l_node2.children.Find(a_ParaName) != null ) //l_node2.children��tArray��
			return -3; //���ܹ����Լ����ӽ�����ó�Ϊ�����
	}

	if ( l_node2.paraName != a_ParaName ) //����㷢���ı���
	{
		//��ԭ���ĸ����Ķ��Ӽ�����ȥ�������
		if ( l_node2.paraNode.children.Remove(a_ParaName) == null )
			return -4;

		//���µĸ����Ķ��Ӽ�������ӱ����
		if ( l_node.children.Add(a_NodeName,l_node2) == null )
			return -5;
	}

	l_node2.Update(a_ParaName,l_node,a_tAry);

	return this.nodes.Update(a_NodeName,l_node2);
}

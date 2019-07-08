package com.gzunicorn.common.logic;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

public class MakeUpXML {
	private String root = "root";

	private Hashtable ht = new Hashtable();

	private String version = "1.0";

	private String encoding = "GBK";

	private String header = "<?xml version='" + version + "' " + "encoding='"
			+ encoding + "'?>";

	public MakeUpXML(String root) {
		this.root = root;
	}

	// ����ÿһ�е����ݡ�
	public void setCol(String rowid, String colname, String colvalue) {
		List list;
		// �ж��Ƿ���ڴ��У�������ڵĻ�������д����Ҳ׷�ӵ���list��ȥ��
		if (ht.containsKey(rowid)) {
			// �����У���ŵ������á�
			list = (List) ht.get(rowid);
			// ������µĶ�����ӵ�list������ȥ��
			// �������list��������ݣ�ʵ�������������ͬһ�����󣬹��Ѿ�����������������ݡ�
			//�����Ļ�������һ��list������˺ܶ��������ֵ�ˡ�
			list.add(new ColBean(colname, colvalue));
		} else {
			// ����һ���µ�list��Ȼ���list�����ݷŵ�Hashtable��ȥ���ˡ�
			list = new ArrayList();
			// ���ص���һ������
			list.add(new ColBean(colname, colvalue));
			ht.put(rowid, list);
		}
	}

	public String getXml(String sort) {// ����
		StringBuffer sb = new StringBuffer();
		List list;
		String key;
		Enumeration ele = ht.keys();
		sb.append(this.header);
		sb.append("<").append(root).append(">");
		int row = 0;
		while (ele.hasMoreElements()) {
			key = ele.nextElement().toString();
			list = (List) ht.get(key);
			if (list != null && list.size() > 0) {
				int len = list.size();
				sb.append("<rows id='").append(row++).append("' name='")
						.append(key).append("'>");
				for (int i = 0; i < len; i++) {
					ColBean cb = (ColBean) list.get(i);
					sb.append("<cols id='").append(i).append("' name='")
							.append(cb.colname).append("'>");
					sb.append(cb.colvale);
					sb.append("</cols>");
				}
				sb.append("</rows>");
			}
		}
		sb.append("</").append(root).append(">");
		return sb.toString();
	}

	public String getXml() {// ������
		StringBuffer sb = new StringBuffer();
		List list;
		String key;
		Enumeration ele = ht.keys();
		sb.append(this.header);
		sb.append("<").append(root).append(">");
		int row = 0;
		while (ele.hasMoreElements()) {
			key = ele.nextElement().toString();
			list = (List) ht.get(key);
			if (list != null && list.size() > 0) {
				int len = list.size();
				sb.append("<rows id='").append(row++).append("' name='")
						.append(key).append("'>");
				for (int i = 0; i < len; i++) {
					ColBean cb = (ColBean) list.get(i);
					sb.append("<cols id='").append(i).append("' name='")
							.append(cb.colname).append("'>");
					sb.append(cb.colvale);
					sb.append("</cols>");
				}
				sb.append("</rows>");
			}
		}
		sb.append("</").append(root).append(">");
		return sb.toString();
	}

	// �ڲ���
	public class ColBean {
		// ������
		private String colname;

		// ��ֵ
		private String colvale;

		public ColBean(String cn, String cv) {
			this.colname = cn;
			this.colvale = cv;
		}

		public String getColname() {
			return colname;
		}

		public void setColname(String colname) {
			this.colname = colname;
		}

		public String getColvale() {
			return colvale;
		}

		public void setColvale(String colvale) {
			this.colvale = colvale;
		}

	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
		this.header = "<?xml version='" + this.version + "' " + "encoding='"
				+ encoding + "'?>";
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
		this.header = "<?xml version='" + version + "' " + "encoding='"
				+ this.encoding + "'?>";
	}

}

package com.gzunicorn.common.pdfprint;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Font;

/**
 * ��д HeaderFooter �� onEndPage ����
 * @author Lijun
 * 2016-08-23
 */
public class HeaderFooter extends PdfPageEventHelper{
	/**
	 * ҳ����ʾ������
	 */
	private  String footerContent;
	/**
	 * ҳ����ʾ�������С
	 */
	private  Font fontDetail;
	
	/**
	 * ��д HeaderFooter �� onEndPage ����
	 * @param footerContent  ҳ����ʾ������
	 * @param fontDetail ҳ����ʾ�������С
	 */
	public HeaderFooter(String footerContent,Font fontDetail){
		this.footerContent=footerContent;
		this.fontDetail=fontDetail;
    }
	
    public void onEndPage (PdfWriter writer, Document document) {

        //д��ҳü
    	/**
        ColumnText.showTextAligned(writer.getDirectContent(), 
        		Element.ALIGN_CENTER, 
        		new Phrase(footerContent, fontDetail), 
        		document.left(), document.top() + 20, 0);
        */
    	
    	//д��ҳ��
        ColumnText.showTextAligned(writer.getDirectContent(), 
        		Element.ALIGN_CENTER, 
        		new Phrase(footerContent, fontDetail), 
        		(document.rightMargin()+document.right()+document.leftMargin()-document.left())/2.0F, 
        		document.bottom()-20, 0);
        
    }
    
}





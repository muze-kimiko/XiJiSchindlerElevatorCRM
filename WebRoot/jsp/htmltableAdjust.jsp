<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>

<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-tiles.tld" prefix="tiles" %>

<tiles:insert page="/jsp/template.jsp" flush="true">

   <br>
   <br>
   <br>
   <br>
  <tiles:put name="body" direct="true">
    <div align="center">
    
    <br/>
    
    <p></p>
    
    <table border="1" align="center" cellpadding="2"
				cellspacing="1" bgcolor="#ffffff">
      <tr>
        <logic:iterate id="element" name="com.zubarev.htmltable.Table.columns.adjust">
          <td>
            <logic:equal name="element" property="visible" value="false">
              <i>
            </logic:equal>

            <html:link page="/AdjustTableSave.do?action=Left" paramId="id" paramName="element" paramProperty="id">
              <html:img page="/images/adjust_left.jpg" border="0"/>
            </html:link>
            <bean:write name="element" property="name"/>
            <html:link page="/AdjustTableSave.do?action=Close" paramId="id" paramName="element" paramProperty="id">
              <html:img page="/images/adjust_close.jpg" border="0"/>
            </html:link>
            <html:link page="/AdjustTableSave.do?action=Right" paramId="id" paramName="element" paramProperty="id">
              <html:img page="/images/adjust_right.jpg" border="0"/>
            </html:link>

            <logic:equal name="element" property="visible" value="false">
              </i>
            </logic:equal>
          </td>
        </logic:iterate>
      </tr>
    </table>
    
   <p></p>
   
   <table border="0">
     <tr>
       <td><html:img page="/images/adjust_left.jpg" border="0"/></td>
       <td>�����ƶ�</td>
     </tr>
     <tr>
       <td><html:img page="/images/adjust_right.jpg" border="0"/></td>
       <td>�����ƶ�</td>
     </tr>
     <tr>
       <td><html:img page="/images/adjust_close.jpg" border="0"/></td>
       <td>����ʾ���У������Ӧ��Ϊб��Ļ�����˵������ʾ���У�</td>
     </tr>
   </table>
   
   <p></p>
   
   <html:link page="/AdjustTableSave.do?action=Done">
     ȷ��
   </html:link>
    
   </div>
    
  </tiles:put>
  
</tiles:insert>


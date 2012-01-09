<%@ page contentType="text/html;charset=ISO-8859-1" language="java"
         pageEncoding="ISO-8859-1"
%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes-dynattr.tld"
%><%@taglib prefix="mde" uri="/manydesigns-elements"
%><%@ taglib tagdir="/WEB-INF/tags" prefix="portofino" %>
<stripes:layout-render name="/skins/${skin}/portlet.jsp">
    <jsp:useBean id="actionBean" scope="request" type="com.manydesigns.portofino.actions.chart.ChartAction"/>
    <stripes:layout-component name="portletTitle">
        <c:out value="${actionBean.chartPage.title}"/>
    </stripes:layout-component>
    <stripes:layout-component name="portletBody">
        <mde:write name="actionBean" property="jfreeChartInstance"/>
    </stripes:layout-component>
    <stripes:layout-component name="portletFooter">
        <portofino:buttons list="chart-buttons" cssClass="portletButton" />
    </stripes:layout-component>
</stripes:layout-render>
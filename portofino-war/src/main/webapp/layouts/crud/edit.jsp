<%@ page contentType="text/html;charset=ISO-8859-1" language="java"
         pageEncoding="ISO-8859-1"
%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"
%><%@taglib prefix="mde" uri="/manydesigns-elements"
%><stripes:layout-render name="/layouts/portlet-page-definition.jsp">
    <stripes:layout-component name="portletPageHeader">
        <stripes:submit name="update" value="Update"/>
        <stripes:submit name="cancel" value="Cancel"/>
        <div class="breadcrumbs">
            <div class="inner">
                <mde:write name="breadcrumbs"/>
            </div>
        </div>
    </stripes:layout-component>
    <stripes:layout-component name="portletPageBody">
        <div class="portletWrapper first">
            <stripes:layout-render name="/skins/${skin}/portlet.jsp">
                <jsp:useBean id="actionBean" scope="request" type="com.manydesigns.portofino.actions.CrudAction"/>
                <stripes:layout-component name="portletTitle">
                    <c:out value="${actionBean.crud.editTitle}"/>
                </stripes:layout-component>
                <stripes:layout-component name="portletBody">
                    <c:if test="${actionBean.requiredFieldsPresent}">
                        Fields marked with a "*" are required.
                    </c:if>
                    <mde:write name="actionBean" property="form"/>
                    <input type="hidden" name="pk" value="<c:out value="${actionBean.pk}"/>"/>
                    <c:if test="${not empty actionBean.searchString}">
                        <input type="hidden" name="searchString" value="<c:out value="${actionBean.searchString}"/>"/>
                    </c:if>
                    <input type="hidden" name="cancelReturnUrl" value="<c:out value="${actionBean.cancelReturnUrl}"/>"/>
                </stripes:layout-component>
            </stripes:layout-render>
        </div>
    </stripes:layout-component>
    <stripes:layout-component name="portletPageFooter">
        <stripes:submit name="update" value="Update"/>
        <stripes:submit name="cancel" value="Cancel"/>
    </stripes:layout-component>
</stripes:layout-render>
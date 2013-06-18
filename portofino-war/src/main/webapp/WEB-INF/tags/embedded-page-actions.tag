<%@ tag import="org.slf4j.LoggerFactory" %>
<%@ tag import="com.manydesigns.portofino.pageactions.AbstractPageAction" %>
<%@ attribute name="list" required="true" %>
<%@ attribute name="cssClass" required="false" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes-dynattr.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="actionBean" scope="request"
             type="com.manydesigns.portofino.pageactions.AbstractPageAction"/>
<div class="${cssClass} portletContainer">
    <input type="hidden" name="portletWrapperName_${list}" value="portletWrapper_${list}" />
    <c:forEach var="portletInstance" items="${ actionBean.embeddedPageActions[list] }">
        <div id="portletWrapper_<c:out value='${portletInstance.id}' />">
            <% try {%>
                <jsp:include page="${portletInstance.jsp}" flush="false" />
            <%} catch (Throwable t) {
                LoggerFactory.getLogger(actionBean.getClass()).error("Error in included page", t);
            %>
                <div class="alert alert-error">
                    <button data-dismiss="alert" class="close" type="button">&times;</button>
                    <ul class="errorMessages">
                        <li>
                            <fmt:message key="portlet.view.error">
                                <fmt:param value="${portletInstance.jsp}" />
                            </fmt:message>
                        </li>
                    </ul>
                </div>
            <%}%>
        </div>
    </c:forEach>
</div>
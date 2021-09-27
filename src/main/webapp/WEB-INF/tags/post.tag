<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ tag pageEncoding="UTF-8"%>
<%@ attribute name="name" required="true"%>
<%@ attribute name="params" required="true"%>
<%@ attribute name="isDisabled" required="true"%>
<%@ attribute name="outline" required="true"%>

<form action="${homepage}/do" method="post">
  <c:forTokens var="p" items="${params}" delims="&">
    <c:set var="values" value="${fn:split(p, '=')}" />
    <input name="${values[0]}" value="${values[1]}" type="hidden">
  </c:forTokens>
  <button type="submit"
    class="btn btn-outline-${outline} ${isDisabled  ? 'disabled' : ''}"
    tabindex="${isDisabled  ? -1 : 0}">
    <fmt:message key="${name}" />
  </button>
</form>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="${locale}" class="h-100">
<head>
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<link rel="stylesheet" href="css/index.css">
<title>
  <fmt:message key="app.name" />
</title>
</head>
<body class="lead h-100 bg-dark text-light">
  <%@ include file="/WEB-INF/jspf/navbar.jspf"%>
  <h4 class="text-center p-3">
    <fmt:message key="home.info" />
  </h4>
  <%@ include file="/WEB-INF/jspf/order.jspf"%>
</body>
</html>
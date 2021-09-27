<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="${locale}" class="h-100">
<head>
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<link rel="stylesheet" href="css/index.css">
<title>
  <fmt:message key="room.list" />
</title>
</head>
<body class="lead h-100 bg-dark text-light mb-auto d-flex flex-column">
  <%@ include file="/WEB-INF/jspf/navbar.jspf"%>
  <%@ include file="/WEB-INF/jspf/list/room.jspf"%>
</body>
</html>
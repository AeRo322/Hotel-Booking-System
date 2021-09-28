<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="${locale}" class="h-100">
<head>
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<link rel="stylesheet" type="text/css" href="../css/index.css">
<title>
  <fmt:message key="navbar.profile" />
</title>
</head>
<body class="lead h-100 bg-dark text-white">
  <%@ include file="/WEB-INF/jspf/navbar.jspf"%>
  <%@ include file="/WEB-INF/jspf/personalInfo.jspf"%>
</body>
</html>
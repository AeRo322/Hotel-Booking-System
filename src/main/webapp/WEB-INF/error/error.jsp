<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="${locale}" class="h-100">
<head>
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<link rel="stylesheet" href="css/index.css">
<title>
  <fmt:message key="error.title" />
</title>
</head>
<body class="h-100 bg-dark text-white d-flex align-items-center">
  <div class="text-center">
    <h1 class="lead p-3">
      <fmt:message key="error.sorry" />
    </h1>
    <p class="lead px-5">
      <fmt:message key="error.help" />
    </p>
  </div>
</body>
</html>
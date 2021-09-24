<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="${locale}" class="h-100">
<head>
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<link rel="stylesheet" href="../css/login.css">
<title>
  <fmt:message key="login.sign_in" />
</title>
</head>
<body class="d-flex text-center h-100">
  <main class="form-signin w-100 m-auto">
    <form action="../do" method="post">
    
      <h1 class="h3 mb-3 fw-normal text-light">
        <fmt:message key="login.sign_in" />
      </h1>

      <input name="action" value="login" type="hidden">

      <div class="form-floating">
        <input name="email" type="email" class="form-control" id="email"
          required maxlength="255">
        <label for="email">
          <fmt:message key="login.email" />
        </label>
      </div>

      <div class="form-floating">
        <input name="password" type="password" class="form-control"
          id="password" required pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}">
        <label for="password">
          <fmt:message key="login.password" />
        </label>
      </div>

      <button class="w-100 btn btn-lg btn-primary" type="submit">
        <fmt:message key="navbar.login" />
      </button>

      <div class="text-light">
        <br>
        <fmt:message key="login.new_user?" />
        <br>
        <a href="register.jsp">
          <fmt:message key="login.register_now" />
        </a>
      </div>
      
    </form>
  </main>
</body>
</html>
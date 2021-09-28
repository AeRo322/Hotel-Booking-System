<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="${locale}" class="h-100">
<head>
<%@ include file="/WEB-INF/jspf/head.jspf"%>
<link rel="stylesheet" href="../css/login.css">
<title>
  <fmt:message key="register.registration" />
</title>
</head>
<body class="d-flex text-center h-100">
  <main class="form-signin w-100 m-auto">
    <form action="${homepage}/do" method="post">

      <input name="action" value="register" type="hidden">

      <h1 class="h3 mb-3 fw-normal text-light">
        <fmt:message key="register.registration" />
      </h1>

      <div class="form-floating">
        <input name="first_name" class="form-control" id="first_name" required
          maxlength="45">
        <label for="first_name">
          <fmt:message key="user.first_name" />
        </label>
      </div>

      <div class="form-floating">
        <input name="last_name" class="form-control" id="last_name" required
          maxlength="45">
        <label for="last_name">
          <fmt:message key="user.last_name" />
        </label>
      </div>

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
        <fmt:message key="register.register" />
      </button>

      <div class="text-light">
        <br>
        <fmt:message key="register.already_user?" />
        <br>
        <a href="register.jsp">
          <fmt:message key="register.login" />
        </a>
      </div>

    </form>
  </main>
</body>
</html>
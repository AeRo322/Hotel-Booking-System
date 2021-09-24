<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
<link rel="stylesheet" href="../style.css">
<meta charset="UTF-8">
<title>Sign in</title>
</head>
<body>

  <form action="${applicationScope.homepage}/controller" method="post">
    <input name="command" value="login" type="hidden">
    <input name="username" value="manager" pattern=".{1,16}">
    <br>
    <input name="password" value="Manager1"
      pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,32}" type="password">
    <br>
    <input type="submit" value="Log in">
  </form>

  <div>
    Already have an account?
    <a href="auth/registration">Sign in</a>
  </div>

</body>
</html>
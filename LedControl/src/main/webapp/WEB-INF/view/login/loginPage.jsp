<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
</head>
<body class="hold-transition login-page">
<!--       <form class="form-signin">
        <h2 class="form-signin-heading">조명 관리 사이트</h2>
        <label for="adminId" class="sr-only">관리자ID</label>
        <input type="text" id="adminId" class="form-control" placeholder="관리자ID" required autofocus>
        <label for="password" class="sr-only">Password</label>
        <input type="password" id="password" class="form-control" placeholder="Password" required>
        <div class="checkbox">
          <label>
            <input type="checkbox" value="remember-me"> Remember me
          </label>
        </div>
        <button class="btn btn-lg btn-primary btn-block" id="btnLogin" onclick="return false;">Sign in</button>
      </form> -->
	<div class="login-box">
		<div class="login-logo">
			<b>조명관리</b>
  		</div>
		<div class="login-box-body">
			<p class="login-box-msg">로그인 후 이용할 수 있습니다.</p>

			<form>
				<div class="form-group has-feedback">
					<input type="text" id="adminId" class="form-control" placeholder="관리자ID">
					<span class="glyphicon glyphicon-edit form-control-feedback"></span>
				</div>
				<div class="form-group has-feedback">
					<input type="password" id="password" class="form-control" placeholder="Password">
					<span class="glyphicon glyphicon-lock form-control-feedback"></span>
				</div>
				<div class="row">
					<div class="col-xs-8">
					</div>
	        		<div class="col-xs-4">
	          			<button type="submit" class="btn btn-primary btn-block btn-flat" id="btnLogin" onclick="return false;">Sign In</button>
	        		</div>
	      		</div>
	    	</form>
  		</div>
	</div>
<content tag="end">
	<script type="text/javascript">
  		$(function () {
			$('input').iCheck({
				checkboxClass: 'icheckbox_square-blue',
				radioClass: 'iradio_square-blue',
				increaseArea: '20%' // optional
			});
		});

		$(document).ready(function () {
			$('#btnLogin').click(function () {
				var param = {
						adminId : $('#adminId').val()
						, password : $('#password').val()
				};
	
				$.post('/login/login', param, function (response) {
					if (response.commonResult.success == true) {
						document.location.href = '/led/mode/list';
					} else {
						alert(response.commonResult.message);
					}
				});
			})
		});
	</script>
</content>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- Required meta tags always come first -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta http-equiv="x-ua-compatible" content="ie=edge">

<!-- bootstrap -->
<link href="/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<!-- Font Awesome -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
<!-- Ionicons -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
<link href="/css/AdminLTE.min.css" rel="stylesheet">
<link rel="stylesheet" href="/css/skins/_all-skins.min.css">
<!-- iCheck -->
<link rel="stylesheet" href="/plugins/iCheck/square/blue.css">
<!-- <link href="/css/default.css" rel="stylesheet"> -->

<title><sitemesh:write property='title' /></title>
<sitemesh:write property='head' />

</head>
<body class='<sitemesh:write property="body.class"/>'>
	<!-- 공통 네비게이션 바 -->
	<c:if test="${isLogin}">
	<header class="main-header">
    	<!-- Logo -->
    	<a href="../../index2.html" class="logo">
      		<!-- mini logo for sidebar mini 50x50 pixels -->
      		<span class="logo-mini"><b>조명</b></span>
      		<!-- logo for regular state and mobile devices -->
      		<span class="logo-lg"><b>조명</b>관리</span>
    	</a>
	    <!-- Header Navbar: style can be found in header.less -->
	    <nav class="navbar navbar-static-top">
			<!-- Sidebar toggle button-->
			<a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
				<span class="sr-only">Toggle navigation</span>
	        	<span class="icon-bar"></span>
	        	<span class="icon-bar"></span>
	        	<span class="icon-bar"></span>
			</a>

			<div class="navbar-custom-menu">
				<ul class="nav navbar-nav">
	          		<!-- Messages: style can be found in dropdown.less-->
	          		<li>
	          			<a href="javascript:logout();">
	          				<i class="glyphicon glyphicon-log-out"></i><span> 로그아웃</span>
	          			</a>
	          		</li>
				</ul>
			</div>
	    </nav>
    </header>

  	<!-- Left side column. contains the sidebar -->
  	<aside class="main-sidebar">
	    <!-- sidebar: style can be found in sidebar.less -->
	    <section class="sidebar">
	    	<ul class="sidebar-menu">
	    		<li class="header">MAIN MENU</li>
	    	<c:forEach items="${menuList}" var="menu">
	    		<li>
	    			<a href="${menu.menuUrl}">
	    				<i class="fa fa-lightbulb-o"></i><span>${menu.menuName}</span>
	    			</a>
	    		</li>
	    	</c:forEach>
	    	</ul>
	    </section>
  	</aside>

	</c:if>

	<c:if test="${isLogin}">
	<div class="content-wrapper">
		<sitemesh:write property="body" />
	</div>
	</c:if>
	<c:if test="${!isLogin}">
		<sitemesh:write property="body" />
	</c:if>
<script src="/js/jquery/jquery.min.js"></script>
<script src="/bootstrap/js/bootstrap.min.js"></script>
<script src="/js/common.js"></script>
<script src="/plugins/iCheck/icheck.min.js"></script>
<!-- SlimScroll -->
<script src="/plugins/slimScroll/jquery.slimscroll.min.js"></script>
<!-- FastClick -->
<script src="/plugins/fastclick/fastclick.js"></script>
<!-- AdminLTE App -->
<script src="/js/app.min.js"></script>
<sitemesh:write property='page.end'/>
</body>
</html>
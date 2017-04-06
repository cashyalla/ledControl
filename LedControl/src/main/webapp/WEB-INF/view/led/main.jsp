<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body class="hold-transition skin-blue fixed sidebar-mini">
	<title>Dash board</title>
	<!-- Bootstrap time Picker -->
	<link rel="stylesheet" href="/plugins/timepicker/bootstrap-timepicker.min.css">
</head>
<body>
	<section class="content-header">
		<h1>${current.menuName}</h1>
	</section>

	<section class="content">
		<div class="row">
			<div class="col-md-12">
				<div class="box box-success">
					<div class="box-header with-border">
						<div class="box-title">현재 설정값</div>
					</div>
					<div class="box-body">
						${currentLedMode.modeName}
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<div class="box box-info">
					<div class="box-header with-border">
						<div class="box-title">디밍 그룹 별 밝기</div>
					</div>
					<div class="box-body">
						<c:forEach items="${currentBrightness}" var="brightness">
						<p><code>${brightness.dimGroup.dimName}</code>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong>${brightness.value}%</strong></p>
						<div class="progress active">
							<div class="progress-bar progress-bar-primary progress-bar-striped" role="progressbar" aria-valuenow="${brightness.value}" aria-valuemin="0" aria-valuemax="100" style="width: ${brightness.value}%; background-color: ${brightness.dimGroup.color};">
								<span class="sr-only">${brightness.dimGroup.dimName}</span>
							</div>
						</div>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
	</section>

	<content tag="end">
	</content>
</body>
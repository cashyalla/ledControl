<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>타이머 관리</title>
	<!-- Bootstrap time Picker -->
	<link rel="stylesheet" href="/plugins/timepicker/bootstrap-timepicker.min.css">
</head>
<body class="hold-transition skin-blue fixed sidebar-mini">

	<section class="content-header">
		<h1>타이머 관리</h1>
	</section>

	<section class="content">
		<div class="row">
			<div class="col-lg-12">
				<div class="box box-info">
					<div class="box-header with-border">
						<div class="box-title">타이머 차트</div>
					</div>
					<div class="box-body">
						<div class="chart">
							<canvas id="timerChart" style="height: 300px;"></canvas>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<div class="box box-default">
					<div class="box-header with-border">
						<div class="pull-right">
							<button type="button" class="btn btn-primary btn-sm btn-flat btn-add" onclick="addTimerSchedule();">타이머 추가</button>
						</div>
					</div>
					<div class="box-body">
						<table class="table table-striped">
							<thead>
								<tr class="table-info">
									<th class="text-center">설정시간</th>
									<c:forEach items="${dimGroupList}" var="dimGroup">
										<th class="text-center">${dimGroup.dimName}</th>
									</c:forEach>
								</tr>
							</thead>
							<tbody id="timerScheduleBody">
								<c:if test="${empty timerScheduleList}">
									<tr id="rowEmpty">
										<td colspan="${fn:length(dimGroupList) + 1}" class="text-center">설정된 타이머가 없습니다.</td>
									</tr>
								</c:if>
								<c:forEach items="${timerScheduleList}" var="timerSchedule" varStatus="status">
									<tr name="timerScheduleData">
										<td class="text-center">
											<div class="bootstrap-timepicker">
												<div class="input-group">
													<input type="text" name="scheduleTime" class="form-control timepicker timepicker${status.index}"/>
													<div class="input-group-addon">
														<i class="fa fa-clock-o"></i>
													</div>
												</div>
											</div>
										</td>
										<c:forEach items="${timerSchedule.timerScheduleDetailList}" var="detail">
										<td class="text-center" name="dimGroup">
											<input type="text" hidden name="dimId" value="${detail.dimGroup.dimId}"/>
											<input type="number" name="setValue" value="${detail.setValue}" min="0" max="100"/>
										</td>
										</c:forEach>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<div class="box-footer">
						<div class="pull-right">
							<button type="button" class="btn btn-sm btn-flat btn-warning btn-save" id="btnSave">저장</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>

	<content tag="end">
		<!-- bootstrap time picker -->
		<script src="/plugins/timepicker/bootstrap-timepicker.min.js"></script>
		<!-- ChartJS 1.0.1 -->
		<script src="/plugins/chartjs/Chart.min.js"></script>
		<script type="text/javascript">

			$(document).ready(function () {

				// 차트 초기화
				var timerChartCanvas = $('#timerChart').get(0).getContext('2d');
				var timerChart = new Chart(timerChartCanvas);

				var timerChartData = {
					labels: [
					<c:forEach begin="0" end="24" var="i">
						<c:if test="${i < 10}">
							"0${i}:00",
						</c:if>
						<c:if test="${i >= 10}">
							"${i}:00",
						</c:if>
					</c:forEach>
					]
					, datasets: [
						<c:forEach var="entry" items="${timerChartMap}">
						{
							label: "${entry.value[0].name}",
							fillColor: "${entry.value[0].color}",
							strokeColor: "${entry.value[0].color}",
							pointColor: "${entry.value[0].color}",
							pointStrokeColor: "#c1c7d1",
							pointHighlightFill: "#fff",
							pointHighlightStroke: "rgba(220,220,220,1)",
							data: [
							<c:forEach items="${entry.value}" var="chartData" varStatus="status">
								${chartData.value},
							</c:forEach>
								${entry.value[0].value}
							]
						},
						</c:forEach>
					]
				}

				var timerChartOptions = {
					//Boolean - If we should show the scale at all
					showScale: true,
					//Boolean - Whether grid lines are shown across the chart
					scaleShowGridLines: false,
					//String - Colour of the grid lines
					scaleGridLineColor: "rgba(0,0,0,.05)",
					//Number - Width of the grid lines
					scaleGridLineWidth: 1,
					//Boolean - Whether to show horizontal lines (except X axis)
					scaleShowHorizontalLines: true,
					//Boolean - Whether to show vertical lines (except Y axis)
					scaleShowVerticalLines: true,
					//Boolean - Whether the line is curved between points
					bezierCurve: true,
					//Number - Tension of the bezier curve between points
					bezierCurveTension: 0.3,
					//Boolean - Whether to show a dot for each point
					pointDot: false,
					//Number - Radius of each point dot in pixels
					pointDotRadius: 4,
					//Number - Pixel width of point dot stroke
					pointDotStrokeWidth: 1,
					//Number - amount extra to add to the radius to cater for hit detection outside the drawn point
					pointHitDetectionRadius: 20,
					//Boolean - Whether to show a stroke for datasets
					datasetStroke: true,
					//Number - Pixel width of dataset stroke
					datasetStrokeWidth: 2,
					//Boolean - Whether to fill the dataset with a color
					datasetFill: false,
					//Boolean - whether to maintain the starting aspect ratio or not when responsive, if set to false, will take up entire container
					maintainAspectRatio: true,
					responsive: true
				};

				timerChart.Line(timerChartData, timerChartOptions)

				// Time Picker 초기화
				<c:forEach items="${timerScheduleList}" var="timerSchedule" varStatus="status">
				$('.timepicker${status.index}').timepicker({
					showInputs: false
					, maxHours: 24
					, showSeconds: false
					, minuteStep: 1
					, showMeridian: false
					, defaultTime: ${timerSchedule.hour} + ':' + ${timerSchedule.minute}
				});
				</c:forEach>

				$('#btnSave').click(function () {
					updateTimerSchedule();
				})
			});

			function initTimepicker() {
			    $(".timepicker").timepicker({
					showInputs: false
					, maxHours: 24
					, showSeconds: false
					, minuteStep: 1
					, showMeridian: false
    			});
			}

			function addTimerSchedule() {
				$('#rowEmpty').hide();

				var html = '<tr name="timerScheduleData">';
				html += '	<td class="text-center">';
				html += '		<div class="bootstrap-timepicker">';
				html += '			<div class="input-group">';
				html += '				<input type="text" name="scheduleTime" class="form-control timepicker"/>';
				html += '				<div class="input-group-addon">';
				html += '					<i class="fa fa-clock-o"></i>';
				html += '				</div>';
				html += '			</div>';
				html +=	'		</div>';
				html += '	</td>';
				<c:forEach items="${dimGroupList}" var="dimGroup">
				html += '	<td class="text-center" name="dimGroup">';
				html += '		<input type="text" hidden name="dimId" value="${dimGroup.dimId}"/>';
				html += '		<input type="number" name="setValue" value="0" min="0" max="100"/>';
				html += '	</td>';
				</c:forEach>
				html += '</tr>';

				$('#timerScheduleBody').append(html);

				initTimepicker();
			}

			function updateTimerSchedule() {

				var params = {}

				$('tr[name="timerScheduleData"]').each(function (i) {

					var timepicker = $(this).find('input[name="scheduleTime"]');
					var timeSplit = timepicker.val().split(':');

					params['timerScheduleList[' + i + '].hour'] = timeSplit[0];
					params['timerScheduleList[' + i + '].minute'] = timeSplit[1];
					$(this).find('td[name="dimGroup"]').each(function (j) {
						params['timerScheduleList[' + i + '].timerScheduleDetailList[' + j + '].dimGroup.dimId'] = $(this).find('input[name="dimId"]').val();
						params['timerScheduleList[' + i + '].timerScheduleDetailList[' + j + '].setValue'] = $(this).find('input[name="setValue"]').val();
					});
				});

				$.post('update', params, function (response) {
					if (response.commonResult.success == true) {
						alert('타이머 스케쥴이 저장되었습니다.');
						pageRefresh();
					} else {
						alert(response.commonResult.message);
					}
				});
			}
		</script>
	</content>
</body>
</html>
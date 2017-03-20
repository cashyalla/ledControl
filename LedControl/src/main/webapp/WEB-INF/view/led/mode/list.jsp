<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>${current.menuName}</title>
	<link rel="stylesheet" href="/plugins/ionslider/ion.rangeSlider.css">
	<!-- ion slider Nice -->
	<link rel="stylesheet" href="/plugins/ionslider/ion.rangeSlider.skinNice.css">
	<!-- bootstrap slider -->
	<link rel="stylesheet" href="/plugins/bootstrap-slider/slider.css">
</head>
<body class="hold-transition skin-blue fixed sidebar-mini">

	<section class="content-header">
		<h1>${current.menuName}</h1>
	</section>

	<section class="content">
		<div class="row">
			<div class="col-lg-12">
				<div class="pull-right">
					<button type="button" class="btn btn-sm btn-flat btn-primary btn-add" onclick="registerModal();">신규 등록</button>
				</div>
			</div>
		</div>
		<br/>
		<div class="row">
		<c:forEach items="${ledModeList}" var="ledMode">
			<div class="col-md-6">
				<div class="box box-info">
					<div class="box-header with-border">
						<h3>${ledMode.modeName}<c:if test="${ledMode.modeId eq currentLedMode.modeId}">(사용중)</c:if></h3>
					</div>
					<div class="box-body">
						<div style="height: 70px;">
							${ledMode.description}
						</div>
						<div class="pull-right">
						<c:if test="${ledMode.modeId ne config.modeSchedule and ledMode.modeId ne config.modeManual and ledMode.modeId ne config.modeCycle}">
							<button type="button" class="btn btn-warning btn-sm btn-flat btn-update" onclick="updateModal('${ledMode.modeId}');">수정</button>
							<button type="button" class="btn btn-danger btn-sm btn-flat btn-delete" onclick="deleteMode('${ledMode.modeId}');">삭제</button>
						</c:if>
						<c:if test="${ledMode.modeId ne currentLedMode.modeId}">
							<button type="button" class="btn btn-primary btn-sm btn-flat btn-ok" onclick="setMode('${ledMode.modeId}');">적용</button>
						</c:if>
						</div>
					</div>
				</div>
			</div>
		</c:forEach>
		</div>
	</section>

	<div class="modal fade" id="registerModeModal"  tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static" data-keyboard="false">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">신규 조명Mode 등록</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form id="modeForm">
						<input type="text" hidden name="modeId"/>
						<div class="form-group">
							<span class="input-group-addon" id="mode_name_addon">Mode이름</span>
							<input type="text" class="form-control" name="modeName" placeholder="Mode이름" aria-describedby="mode_name_addon"/>
						</div>
						<div class="form-group">
							<span class="input-group-addon" id="description_addon">Mode설명</span>
							<input type="text" class="form-control" name="description" placeholder="Mode설명" aria-describedby="description_addon"/>
						</div>

						<c:forEach items="${dimGroupList}" var="dimGroup" varStatus="status">
						<br/>
						<div class="row margin">
							<input type="hidden" name="ledModeSettingsList[${status.index}].dimGroup.dimId" value="${dimGroup.dimId}"/>
							<div class="col-xs-3">
								<span>${dimGroup.dimName}</span>
							</div>
							<div class="col-xs-6">
								<input id="dimSlider${status.index}" data-slider-id="slider${status.index}" class="set_value_slider slider form-control" type="text" data-slider-tooltip="hide" data-slider-min="0" data-slider-max="100" data-slider-step="1" data-slider-value="0" data-slider-orientation="horizontal" data-display-id="dimValue${status.index}"/>
							</div>
							<div class="col-xs-3">
								<input type="number" id="dimValue${status.index}" name="ledModeSettingsList[${status.index}].setValue" min="0" max="100" value="0"/>
							</div>
						</div>
						</c:forEach>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-success btn-sm btn-flat btn-cancel" data-dismiss="modal" aria-hidden="true">취소</button>
					<button type="button" class="btn btn-info btn-sm btn-flat btn-save" id="btnSave">저장</button>
					<button type="button" class="btn btn-warning btn-sm btn-flat btn-update" id="btnUpdate">수정</button>
				</div>
			</div>
		</div>
	</div>
	<content tag="end">
		<!-- Ion Slider -->
		<script src="/plugins/ionslider/ion.rangeSlider.min.js"></script>
		<!-- Bootstrap slider -->
		<script src="/plugins/bootstrap-slider/bootstrap-slider.js"></script>
		<script type="text/javascript">

			$(document).ready(function () {
				<c:forEach items="${dimGroupList}" var="dimGroup" varStatus="status">
				var slider = $('#dimSlider${status.index}').slider();

				$('#dimSlider${status.index}').change(function () {
					$('#' + $(this).attr('data-display-id')).val($(this).val());
					$(this).slider('setValue', $(this).val() * 1);
				});

				$('#dimValue${status.index}').on('keyup', function () {
					onSetValueChange($(this), 'dimSlider${status.index}');
				});

				$('#dimValue${status.index}').on('change', function () {
					onSetValueChange($(this), 'dimSlider${status.index}');
				});
				</c:forEach>

				$('#btnSave').click(function () {
					registerMode();
				});

				$('#btnUpdate').click(function () {
					updateMode();
				});
			});

			function onSetValueChange(obj, sliderId) {
				var val = obj.val() * 1;

				console.log('val type : ' + (typeof val));

				if (typeof val === 'number') {
					$('#' + sliderId).slider('setValue', val);
				}
			}

			function resetModeForm() {
				$('#modeForm')[0].reset();
				$('.set_value_slider').each(function () {
					$(this).slider('setValue', 0);
				})
			}

			function registerModal() {
				$('#btnSave').show();
				$('#btnUpdate').hide();
				resetModeForm();
				$('#registerModeModal').modal('show');
			}

			function registerMode() {
				var params = $('#modeForm').serializeObject();

				$.post('insert', params, function (response) {
					if (response.commonResult.success == true) {
						pageRefresh();
					} else {
						alert(response.commonResult.message);
					}
				});
			}

			function updateModal(modeId) {
				var param = {modeId: modeId}

				$('#modeForm')[0].reset();
				resetModeForm();

				$.post('get', param, function (response) {
					if (response.commonResult.success == true) {
						var ledMode = response.ledMode;

						$('#modeForm input[name="modeId"]').val(ledMode.modeId);
						$('#modeForm input[name="modeName"]').val(ledMode.modeName);
						$('#modeForm input[name="description"]').val(ledMode.description);

						for (var i = 0; i < ledMode.ledModeSettingsList.length; i++) {
							var setValue = ledMode.ledModeSettingsList[i].setValue;
							$('#dimSlider' + i).slider('setValue', setValue);
							$('#modeForm input[name="ledModeSettingsList[' + i + '].setValue"]').val(setValue);
						}

						$('#btnSave').hide();
						$('#btnUpdate').show();
						$('#registerModeModal').modal('show');
					} else {
						alert(response.commonResult.message);
					}
				});
			}

			function updateMode() {

		        var isConfirm = confirm('변경사항을 저장하시겠습니까?');
		        if (isConfirm == false) {
		        	return;
		        }

				var params = $('#modeForm').serializeObject();

				$.post('update', params, function (response) {
					if (response.commonResult.success == true) {
						alert('변경사항이 적용되었습니다.');
						pageRefresh();
					} else {
						alert(response.commonResult.message);
					}
				});
			}

			function deleteMode(modeId) {
		        var isConfirm = confirm('선택한 항목을 삭제하시겠습니까?');
		        if (isConfirm == false) {
		        	return;
		        }

		        var params = {modeId: modeId}

		        $.post('delete', params, function (response) {
		        	if (response.commonResult.success == true) {
		        		alert('삭제되었습니다.');
		        		pageRefresh();
		        	} else {
		        		alert(response.commonResult.message);
		        	}
		        });
			}

			function setMode(modeId) {
				var isConfirm = confirm('선택한 조명 모드로 설정하시겠습니까?');
				if (isConfirm == false) {
					return;
				}

				var params = {modeId: modeId}

				$.post('set', params, function (response) {
					if (response.commonResult.success == true) {
						alert('선택하신 조명 모드로 설정되었습니다.');
						pageRefresh();
					} else {
						alert(response.commonResult.message);
					}
				})
			}
		</script>
	</content>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>${current.menuName}</title>
<script type="text/javascript">
	$(document).ready(function () {
		$('#btnNew').click(function () {
			$('#registerDimGroupTitle').text('신규 디밍 그룹 등록');
			$('#dimGroupForm')[0].reset();
			$('#btnSave').show();
			$('#btnUpdate').hide();
			$('#registerDimGroupModal').modal('show');
		});

		$('#btnSave').click(function () {
			var params = $('#dimGroupForm').serializeObject();

			$.post('insert', params, function (response) {
				if (response.commonResult.success == true) {
					pageRefresh();
				} else {
					alert(response.commonResult.message);
				}
			});
		});

		$('#btnUpdate').click(function () {
			update();
		});

		$('#btnSaveDetail').click(function () {
			registerDimDetail();
		});

		$('#btnUpdateDetail').click(function () {

		})
	});

	function updateModal(dimId) {

		var param = {dimId: dimId}

		$.post('get', param, function (response) {
			if (response.commonResult.success == true) {
				var dimGroup = response.dimGroup;

				$('#registerDimGroupTitle').text('디밍 그룹 정보 수정');
				$('#dimGroupForm')[0].reset();
				$('#dimGroupForm input[name=dimId]').val(dimGroup.dimId);
				$('#dimGroupForm input[name=dimName]').val(dimGroup.dimName);
				$('#dimGroupForm input[name=description]').val(dimGroup.description);
				$('#dimGroupForm select[name=useYn]').val(dimGroup.useYn);

				$('#btnSave').hide();
				$('#btnUpdate').show();
				$('#registerDimGroupModal').modal('show');
			} else {
				alert(response.commonResult.message);
			}
		});
	}

	function update() {

        var isConfirm = confirm('변경사항을 저장하시겠습니까?');
        if (isConfirm == false) {
        	return;
        }

        var params = $('#dimGroupForm').serializeObject();

        $.post('update', params, function (response) {
        	if (response.commonResult.success == true) {
        		alert('변경사항이 적용되었습니다.');
        		pageRefresh();
        	} else {
        		alert(response.commonResult.message);
        	}
        });
	}

	function removeDimGroup(dimId) {

        var isConfirm = confirm('선택한 항목을 삭제하시겠습니까?\nDIM그룹을 삭제하시면 하위 핀 그룹도 함께 삭제됩니다.');
        if (isConfirm == false) {
        	return;
        }

        var params = {dimId: dimId};

        $.post('delete', params, function (response) {
        	if (response.commonResult.success == true) {
        		alert('삭제되었습니다.');
        		pageRefresh();
        	} else {
        		alert(response.commonResult.message);
        	}
        });
	}

	function registerDimDetailModal(dimId) {

		$('#dimDetailForm')[0].reset();
		$('#btnSaveDetail').show();
		$('#btnUpdateDetail').hide();
		$('#dimDetailForm input[name="dimGroup.dimId"]').val(dimId);

		// 핀 정보 조회
		$.post('/led/gpio/listAction', function (response) {
			var pinList = response.pinList;

			var optionHtml = '';

			for (var i = 0; i < pinList.length; i++) {
				var pin = pinList[i];
				optionHtml += '<option value="' + pin.seq + '">' + pin.pinNumber + ' : ' + pin.description + '</option>';
			}

			$('#dimDetailForm select[name="gpioPinInfo.seq"]').html(optionHtml);
			$('#registerDimDetailModal').modal('show');
		});

	}

	function registerDimDetail() {
		var params = $('#dimDetailForm').serializeObject();

		$.post('insertDetail', params, function (response) {
			if (response.commonResult.success == true) {
				pageRefresh();
			} else {
				alert(response.commonResult.message);
			}
		});
	}

	function updateDimDetailModal(seq) {		
		var param = {seq: seq};

		// 디밍 정보 상세 조회
		$.post('getDetail', param, function (response) {
			var dimDetail = response.dimDetail;
			var orgPinInfo = dimDetail.gpioPinInfo;

			$('#dimDetailForm')[0].reset();
			$('#btnSaveDetail').hide();
			$('#btnUpdateDetail').show();
			$('#dimDetailForm input[name="dimGroup.dimId"]').val(dimId);

			// 핀 정보 조회
			$.post('/led/gpio/listAction', function (response) {
				var pinList = response.pinList;

				var optionHtml = '<option value="' + orgPinInfo.seq + '">' + orgPinInfo.pinNumber + ' : ' + orgPinInfo.description + '</option>';

				for (var i = 0; i < pinList.length; i++) {
					var pin = pinList[i];
					optionHtml += '<option value="' + pin.seq + '">' + pin.pinNumber + ' : ' + pin.description + '</option>';
				}

				$('#dimDetailForm select[name="gpioPinInfo.seq"]').html(optionHtml);
				$('#registerDimDetailModal').modal('show');
			});
		});
	}

	function updateDimDetail() {

        var isConfirm = confirm('변경사항을 저장하시겠습니까?');
        if (isConfirm == false) {
        	return;
        }

		var params = $('#dimDetailForm').serializeObject();

		$.post('updateDetail', params, function (response) {
			if (response.commonResult.success == true) {
        		alert('변경사항이 적용되었습니다.');
        		pageRefresh();
			} else {
				alert(response.commonResult.message);
			}
		})
	}

	function removeDimDetail(seq) {

        var isConfirm = confirm('디밍그룹의 핀 정보를 삭제하시겠습니까?');
        if (isConfirm == false) {
        	return;
        }

		var param = {seq: seq};

		$.post('deleteDetail', param, function (response) {
			if (response.commonResult.success == true) {
        		alert('삭제되었습니다.');
        		pageRefresh();
			} else {
				alert(response.commonResult.message);
			}
		});
	}
</script>
</head>
<body>
	<div class="row">
		<h4 class="menu-title">${current.menuName}</h4>
	</div>
	
	<div class="row">
		<div class="col-md-12">
			<div class="text-right">
				<button type="button" id="btnNew" class="btn btn-primary btn-sm">신규 등록</button>
			</div>
		</div>
	</div>
	
	<div class="row">
		<div class="col-md-12">
			<form id="modifyForm">
				<table class="table table-striped">
					<thead>
						<tr class="table-info">
							<td class="td-col-2">DIM ID</td>
							<td class="td-col-2">DIM 이름</td>
							<td class="td-col-7">DIM 설명</td>
							<td class="td-col-1">사용여부</td>
							<td class="td-col-1">수정</td>
							<td class="td-col-1">핀 추가</td>
							<td class="td-col-1">삭제</td>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${dimGroupList}" var="dimGroup">
						<tr>
							<td>
								<input type="hidden" name="dimId" value="${dimGroup.dimId}"/>
								${dimGroup.dimId}
							</td>
							<td>${dimGroup.dimName}</td>
							<td>${dimGroup.description}</td>
							<td>${dimGroup.useYn}</td>
							<td><button type="button" class="btn btn-sm btn-success" onclick="updateModal('${dimGroup.dimId}');">수정</button></td>
							<td><button type="button" class="btn btn-sm btn-warning" onclick="registerDimDetailModal('${dimGroup.dimId}');">핀 추가</button></td>
							<td><button type="button" class="btn btn-sm btn-danger" onclick="removeDimGroup('${dimGroup.dimId}');">삭제</button></td>
						</tr>
						<c:forEach items="${dimGroup.dimDetails}" var="dimDetail">
							<tr>
								<td colspan="2">
									<input type="hidden" name="seq" value="${dimDetail.seq}"/>
									┗ 핀 번호 : ${dimDetail.gpioPinInfo.pinNumber}
								</td>
								<td colspan="3">
									핀 설명 : ${dimDetail.gpioPinInfo.description}
								</td>
								<td><button type="button" class="btn btn-sm btn-warning" onclick="updateDimDetailModal('${dimDetail.seq}');">수정</button></td>
								<td><button type="button" class="btn btn-sm btn-danger" onclick="removeDimDetail('${dimDetail.seq}');">삭제</button></td>
							</tr>
						</c:forEach>
					</c:forEach>
					</tbody>
				</table>
			</form>
		</div>
	</div>

	<div class="modal fade" id="registerDimGroupModal"  tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static" data-keyboard="false">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title"><label id="registerDimGroupTitle">신규 디밍 그룹 등록</label></h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form id="dimGroupForm">
						<input type="hidden" name="dimId"/>
						<div class="input-group">
							<span class="input-group-addon" id="dim_name_addon">디밍 그룹 이름</span>
							<input type="text" class="form-control" name="dimName" placeholder="디밍 그룹 이름" aria-describedby="dim_name_addon"/>
						</div>
						<div class="input-group">
							<span class="input-group-addon" id="description_addon">디밍 그룹 설명</span>
							<input type="text" class="form-control" name="description" placeholder="디밍 그룹 설명" aria-describedby="description_addon"/>
						</div>
						<div class="input-group">
							<span class="input-group-addon" id="use_yn_addon">사용여부</span>
							<select class="form-control" name="useYn" aria-describedby="use_yn_addon">
								<option value="Y">Y</option>
								<option value="N">N</option>
							</select>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-success btn-sm" data-dismiss="modal" aria-hidden="true">취소</button>
					<button type="button" class="btn btn-primary btn-sm" id="btnSave">저장</button>
					<button type="button" class="btn btn-info btn-sm" id="btnUpdate">수정</button>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="registerDimDetailModal"  tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static" data-keyboard="false">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title"><label id="registerDimDetailTitle"></label></h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form id="dimDetailForm">
						<input type="hidden" name="dimId"/>
						<div class="input-group">
							<span class="input-group-addon" id="dim_name_addon">디밍 그룹 ID</span>
							<input type="text" class="form-control" name="dimGroup.dimId" aria-describedby="dim_name_addon" readonly="true" />
						</div>
						<div class="input-group">
							<span class="input-group-addon" id="pin_number_addon">핀 번호</span>
							<select name="gpioPinInfo.seq" class="form-control" aria-describedby="pin_number_addon">
							</select>
						</div>
						<div class="input-group">
							<span class="input-group-addon" id="use_yn_addon">사용여부</span>
							<select class="form-control" name="useYn" aria-describedby="use_yn_addon">
								<option value="Y">Y</option>
								<option value="N">N</option>
							</select>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-success btn-sm" data-dismiss="modal" aria-hidden="true">취소</button>
					<button type="button" class="btn btn-primary btn-sm" id="btnSaveDetail">저장</button>
					<button type="button" class="btn btn-info btn-sm" id="btnUpdateDetail">수정</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
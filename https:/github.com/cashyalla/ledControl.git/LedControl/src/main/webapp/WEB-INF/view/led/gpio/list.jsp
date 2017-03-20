<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>${current.menuName}</title>
<style>
/*#ex1Slider .slider-selection {
	background: #BABABA;
}*/
</style>
<script type="text/javascript">

	$(document).ready(function () {

		// With JQuery
		// $('#ex1').slider();
		// $('#ex1').on('slide', function(slide) {
		// 	console.log('val : ' + slide.value);
		// })

		$('#btnNew').click(function () {
			$('#gpioForm')[0].reset();
			$('#btnSave').show();
			$('#btnUpdate').hide();
			$('#registerGpioPinModal').modal('show');
		});

		$('#btnSave').click(function () {
			var params = $('#gpioForm').serializeObject();

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
	});

	function updateModal(seq) {
		var param = {
			seq: seq
		};

		$.post('get', param, function (response) {
			if (response.commonResult.success == true) {
				var gpioPinInfo = response.gpioPinInfo;

				$('#gpioForm')[0].reset();
				$('#gpioForm input[name=seq]').val(gpioPinInfo.seq);
				$('#gpioForm input[name=pinNumber]').val(gpioPinInfo.pinNumber);
				$('#gpioForm input[name=description]').val(gpioPinInfo.description);

				$('#btnSave').hide();
				$('#btnUpdate').show();
				$('#registerGpioPinModal').modal('show');
			} else {
				alert(response.commonResult.message);
			}
		})
	}

	function update() {

        var isConfirm = confirm('변경사항을 저장하시겠습니까?');
        if (isConfirm == false) {
        	return;
        }

		var params = $('#gpioForm').serializeObject();

        $.post('update', params, function (response) {
        	if (response.commonResult.success == true) {
        		alert('변경사항이 적용되었습니다.');
        		pageRefresh();
        	} else {
        		alert(response.commonResult.message);
        	}
        });
	}

	function removeItem(seq) {

        var isConfirm = confirm('선택한 항목을 삭제하시겠습니까?');
        if (isConfirm == false) {
        	return;
        }

        var params = {seq: seq}

        $.post('delete', params, function (response) {
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

<!-- 	<div class="row">
		<div class="col-md-12 well">
			<input id="ex1" data-slider-id='ex1Slider' type="text" data-slider-min="0" data-slider-max="20" data-slider-step="1" data-slider-value="14"/>
		</div>
	</div> -->
	
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
							<td class="td-col-3">핀 번호</td>
							<td class="td-col-8">핀 설명</td>
							<td class="td-col-2">수정</td>
							<td class="td-col-2">삭제</td>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${pinList}" var="pin">
						<tr>
							<td>
								<input type="hidden" name="seq" value="${pin.seq}"/>
								${pin.pinNumber}
							</td>
							<td>${pin.description}</td>
							<td>
								<button type="button" class="btn btn-success btn-sm" onclick="updateModal(${pin.seq});">수정</button>
							</td>
							<td>
								<button type="button" class="btn btn-danger btn-sm" onclick="removeItem(${pin.seq});">삭제</button>
							</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</form>
		</div>
	</div>

	<div class="modal fade" id="registerGpioPinModal"  tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static" data-keyboard="false">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">신규 Gpio 핀 등록</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form id="gpioForm">
						<input type="hidden" name="seq"/>
						<div class="input-group">
							<span class="input-group-addon" id="pin_number_addon">핀 번호</span>
							<input type="number" class="form-control" name="pinNumber" placeholder="핀 번호" aria-describedby="pin_number_addon"/>
						</div>
						<div class="input-group">
							<span class="input-group-addon" id="description_addon">핀 설명</span>
							<input type="text" class="form-control" name="description" placeholder="핀 설명" aria-describedby="description_addon"/>
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
</body>
</html>
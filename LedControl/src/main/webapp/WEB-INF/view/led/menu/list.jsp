<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>메뉴 관리</title>
<script type="text/javascript">
	$(document).ready(function () {
		$('#btnNew').click(function () {
			$('#registerMenuModal').modal('show');
		});

		$('#btnSave').click(function () {
			var params = $('#menuForm').serializeObject();

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

		$('#btnDelete').click(function () {
			remove();
		});

		$('#modifyForm input[type=text], #modifyForm select').change(function () {
			$(this).parents('tr').find('input[type=checkbox]').attr('checked', true);
		});
	});

	function getCheckedItems() {
        var params = {};

        $('#modifyForm input:checked').each(function(i) {
        	params['menus[' + i + '].menuId'] = $(this).parents('tr').find('input[name=menuId]').val();
        	params['menus[' + i + '].menuName'] = $(this).parents('tr').find('input[name=menuName]').val();
        	params['menus[' + i + '].menuUrl'] = $(this).parents('tr').find('input[name=menuUrl]').val();
        	params['menus[' + i + '].sortNo'] = $(this).parents('tr').find('input[name=sortNo]').val();
        	params['menus[' + i + '].useYn'] = $(this).parents('tr').find('select[name=useYn]').val();
        });

        return params;
	}

	function update() {

        if ($('#modifyForm input:checked').length <= 0) {
            alert('수정할 항목이 없습니다.');
            return;
        }

        var isConfirm = confirm('선택한 항목을 수정하시겠습니까?');
        if (isConfirm == false) {
        	return;
        }

        var params = getCheckedItems();

        $.post('update', params, function (response) {
        	if (response.commonResult.success == true) {
        		alert('변경사항이 적용되었습니다.');
        		pageRefresh();
        	} else {
        		alert(response.commonResult.message);
        	}
        });
	}

	function remove() {

        if ($('#modifyForm input:checked').length <= 0) {
            alert('삭제할 항목이 없습니다.');
            return;
        }

        var isConfirm = confirm('선택한 항목을 삭제하시겠습니까?');
        if (isConfirm == false) {
        	return;
        }

        var params = getCheckedItems();

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
	
	<div class="row">
		<div class="col-md-12">
			<div class="text-right">
				<button type="button" id="btnNew" class="btn btn-primary">신규 등록</button>
			</div>
		</div>
	</div>
	
	<div class="row">
		<div class="col-md-12">
			<form id="modifyForm">
				<table class="table table-striped">
					<thead>
						<tr class="table-info">
							<td class="td-col-1">선택</td>
							<td class="td-col-2">메뉴ID</td>
							<td class="td-col-4">메뉴이름</td>
							<td class="td-col-5">메뉴URL</td>
							<td class="td-col-1">정렬순서</td>
							<td class="td-col-2">사용여부</td>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${menuList}" var="menu">
						<tr>
							<td><input type="checkbox" name="updateCheck" class="custom-checkbox form-control"/></td>
							<td>
								<input type="hidden" name="menuId" value="${menu.menuId}"/>
								${menu.menuId}
							</td>
							<td>
								<input type="text" class="form-control" name="menuName" value="${menu.menuName}"/>
							</td>
							<td>
								<input type="text" class="form-control" name="menuUrl" value="${menu.menuUrl}"/>
							</td>
							<td>
								<input type="number" class="form-control" name="sortNo" value="${menu.sortNo}"/>
							</td>
							<td>
								<select name="useYn" class="form-control-sm" <c:if test='${menu.useYn eq "N"}'>stype="color: red;"</c:if>>
									<option value="Y" <c:if test='${menu.useYn eq "Y"}'>selected="selected"</c:if>>Y</option>
									<option value="N" <c:if test='${menu.useYn eq "N"}'>selected="selected"</c:if>>N</option>
								</select>
							</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</form>
		</div>
	</div>

	<div class="row">
		<div class="col-md-12">
			<div class="text-right">
				<button type="button" class="btn btn-success" id="btnUpdate">수정</button>
				<button type="button" class="btn btn-danger" id="btnDelete">삭제</button>
			</div>
		</div>
	</div>

	<div class="modal fade" id="registerMenuModal"  tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static" data-keyboard="false">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">신규 메뉴 등록</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form id="menuForm">
						<div class="input-group">
							<span class="input-group-addon" id="menu_id_addon">메뉴ID</span>
							<input type="text" class="form-control" name="menuId" placeholder="메뉴ID" aria-describedby="menu_id_addon"/>
						</div>
						<div class="input-group">
							<span class="input-group-addon" id="menu_name_addon">메뉴이름</span>
							<input type="text" class="form-control" name="menuName" placeholder="메뉴이름" aria-describedby="menu_name_addon"/>
						</div>
						<div class="input-group">
							<span class="input-group-addon" id="menu_url_addon">메뉴URL</span>
							<input type="text" class="form-control" name="menuUrl" placeholder="메뉴URL" aria-describedby="menu_url_addon"/>
						</div>
						<div class="input-group">
							<span class="input-group-addon" id="order_addon">정렬순서</span>
							<input type="number" class="form-control" name="sortNo" placeholder="정렬순서" aria-describedby="order_addon"/>
						</div>
						<div class="input-group">
							<span class="input-group-addon" id="use_yn_addon">사용여부</span>
							<select class="form-control" name="useYn">
								<option value="Y">Y</option>
								<option value="N">N</option>
							</select>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-success btn-sm" data-dismiss="modal" aria-hidden="true">취소</button>
					<button type="button" class="btn btn-primary btn-sm" id="btnSave">저장</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
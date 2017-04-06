<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>메뉴 관리</title>
<link rel="stylesheet" href="/plugins/select2/select2.min.css">
<link rel="stylesheet" href="/plugins/iCheck/all.css">
</head>
<body class="hold-transition skin-blue fixed sidebar-mini">
	<section class="content-header">
		<h1>${current.menuName}</h1>
	</section>

	<section class="content">
		<div class="row">
			<div class="col-md-12">
				<div class="box">
					<div class="box-header with-border">
						<div class="pull-right">
							<button type="button" id="btnNew" class="btn btn-sm btn-flat btn-primary btn-add">신규 등록</button>
						</div>
					</div>
					<div class="box-body">
						<form id="modifyForm">
							<table class="table table-striped">
								<thead>
									<tr class="table-info">
										<th>선택</th>
										<th>메뉴ID</th>
										<th>메뉴이름</th>
										<th>메뉴URL</th>
										<th>정렬순서</th>
										<th>사용여부</th>
										<th>아이콘</th>
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
											<select name="useYn" class="form-control-sm select2" <c:if test='${menu.useYn eq "N"}'>stype="color: red;"</c:if>>
												<option value="Y" <c:if test='${menu.useYn eq "Y"}'>selected="selected"</c:if>>Y</option>
												<option value="N" <c:if test='${menu.useYn eq "N"}'>selected="selected"</c:if>>N</option>
											</select>
										</td>
										<td>
											<input type="text" class="form-control" name="icon" value="${menu.icon}"/>
										</td>
									</tr>
								</c:forEach>
								</tbody>
							</table>
						</form>
					</div>
					<div class="box-footer">
						<div class="pull-right">
							<button type="button" class="btn btn-success btn-sm btn-flat btn-update" id="btnUpdate">수정</button>
							<button type="button" class="btn btn-danger btn-sm btn-flat btn-delete" id="btnDelete">삭제</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>

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
					<form id="menuForm" class="form-horizontal">
						<div class="form-group">
							<label for="menuId" class="col-sm-2 control-label">메뉴ID</label>

							<div class="col-sm-10">
								<input type="text" class="form-control" id="menuId" name="menuId" placeholder="메뉴ID"/>
							</div>
						</div>
						<div class="form-group">
							<label for="menuName" class="col-sm-2 control-label">메뉴이름</label>

							<div class="col-sm-10">
								<input type="text" class="form-control" id="menuName" name="menuName" placeholder="메뉴이름"/>
							</div>
						</div>
						<div class="form-group">
							<label for="menuUrl" class="col-sm-2 control-label">메뉴URL</label>

							<div class="col-sm-10">
								<input type="text" class="form-control" id="menuUrl" name="menuUrl" placeholder="메뉴URL"/>
							</div>
						</div>
						<div class="form-group">
							<label for="sortNo" class="col-sm-2 control-label">정렬순서</label>

							<div class="col-sm-10">
								<input type="number" class="form-control" id="sortNo" name="sortNo" placeholder="정렬순서"/>
							</div>
						</div>
						<div class="form-group">
							<label for="useYn" class="col-sm-2 control-label">사용여부</label>

							<div class="col-sm-10">
								<select class="form-control select2" id="useYn" name="useYn">
									<option value="Y">Y</option>
									<option value="N">N</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label for="icon" class="col-sm-2 control-label">아이콘</label>

							<div class="col-sm-10">
								<input type="text" class="form-control" id="icon" name="icon" placeholder="아이콘"/>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-success btn-sm btn-flat btn-cancel" data-dismiss="modal" aria-hidden="true">취소</button>
					<button type="button" class="btn btn-primary btn-sm btn-flat btn-save" id="btnSave">저장</button>
				</div>
			</div>
		</div>
	</div>
	<content tag="end">
		<!-- Select2 -->
		<script src="/plugins/select2/select2.full.min.js"></script>
		<!-- iCheck 1.0.1 -->
		<script src="/plugins/iCheck/icheck.min.js"></script>
		<script type="text/javascript">
			$(document).ready(function () {

				$(".select2").select2();

				$('input[type="checkbox"]').iCheck({
					checkboxClass: 'icheckbox_minimal-blue'
				});

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

				$('#modifyForm input, #modifyForm select').change(function () {
					$(this).parents('tr').find('input[type=checkbox]').iCheck('check');
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
		        	params['menus[' + i + '].icon'] = $(this).parents('tr').find('input[name=icon]').val();
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
	</content>
</body>
</html>
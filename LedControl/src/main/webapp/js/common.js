'use strict';
// very important
jQuery.ajaxSettings.traditional = true;

$('button.btn-add').each(function () {
	
	console.log('btn-add');
	
	var text = $(this).text();
	
	var html = '<i class="glyphicon glyphicon-plus"></i><span>  ' + text + '</span>';
	$(this).html(html);
});

$('button.btn-save').each(function () {
	var text = $(this).text();
	
	var html = '<i class="glyphicon glyphicon-floppy-disk"></i><span>  ' + text + '</span>';
	$(this).html(html);
});

$('button.btn-update').each(function () {
	var text = $(this).text();
	
	var html = '<i class="glyphicon glyphicon-pencil"></i><span>  ' + text + '</span>';
	$(this).html(html);
});

$('button.btn-delete').each(function () {
	var text = $(this).text();
	
	var html = '<i class="glyphicon glyphicon-remove"></i><span>  ' + text + '</span>';
	$(this).html(html);
});

$('button.btn-ok').each(function () {
	var text = $(this).text();
	
	var html = '<i class="glyphicon glyphicon-ok"></i><span>  ' + text + '</span>';
	$(this).html(html);
});

$('button.btn-cancel').each(function () {
	var text = $(this).text();
	
	var html = '<i class="glyphicon glyphicon-log-out"></i><span>  ' + text + '</span>';
	$(this).html(html);
});

$.fn.serializeObject = function () {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function () {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

function pageRefresh() {
    //history.go(0);
    window.location.reload();
}

function goPage(url) {
    document.location.href = url;
}

function logout() {
    goPage('/login/logout');
}
'use strict';
// very important
jQuery.ajaxSettings.traditional = true;

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
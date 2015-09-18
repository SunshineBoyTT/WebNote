// 注销
$('.logout').click(function () {
    if (!(typeof saveArticle === 'undefined')) {
        saveArticle();
    }
    $.ajax({
        url    : '/user/logins',
        type   : 'DELETE',
        success: function () {
            document.cookie = "userCode=;expires=-1;path=/" // 删除 cookie
            setTimeout(function () {
                window.location.href = '/';
            }, 200);
        }
    });
});



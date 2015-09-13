$('form').submit(function () {
    var query = $(this).serialize();
    query     = query.replace(/\+/g, " ");
    query     = query.replace(/=/g, '":"').replace(/&/g, '","');
    query     = '{"' + query + '"}';
    //query     = JSON.parse(query);
    console.log(query);

    $.ajax({
        type       : 'POST',
        url        : '/api/logins',
        data       : query,
        contentType: "application/json; charset=utf-8",
        success    : function (data) {
            if (data == 'OK') {
                $('.alert')
                    .fadeOut('fast', function () {
                        $('.alert').text('登陆成功！正在跳转...')
                            .removeClass('alert-info')
                            .removeClass('alert-danger')
                            .addClass('alert-success');
                    }).fadeIn(function () {
                        setTimeout(function () {
                            window.location = '/';
                        }, 1000);
                    });
            } else {
                $('.alert')
                    .fadeOut('fast', function () {
                        $('.alert').text('登陆失败！请输入正确的用户名密码...')
                            .removeClass('alert-info')
                            .addClass('alert-danger');
                    }).fadeIn();
            }
        }
    });

    return false;
});
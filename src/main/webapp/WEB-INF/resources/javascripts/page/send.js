var io = io();
var t  = new Date().getTime();

$('form').submit(function () {
    var query = $(this).serialize();
    query     = query.replace(/\+/g, " ");
    query     = query + '&t=' + t;
    io.emit('workflow', query);
    return false;
});

// 这个地方还要不要？要！时间戳处理方式
io.on(t, function (msg) {
    if (msg == 1) {
        $('form input').val('');
        $('form textarea').val('');
        alert('添加成功!');
    }
});


$(function () {
    $('#change_time').datetimepicker({
        format: 'YYYY-MM-DD HH:mm'
    });
    $('.preview').modal('hide');
});


// 批量处理方式
$('#many button').click(function () {
    var changes_str = $('#changes').val();
    var html        = str2changesobjhtml(changes_str);
    $('.preview tbody').html(html);
    $('.preview').modal('show');
    console.log(html);
});

$('.btn-send').click(function () {
    var changes_str = $('#changes').val();
    io.emit('workflows', changes_str);
    alert('批量添加成功！');
    $('.preview').modal('hide');
    $('#changes').val('');
});

var str2changesobjhtml = function (input_str) {
    var str     = "";
    var changes = input_str.split('\n');
    console.log(changes);
    for (var change_str in changes) {
        str += '<tr>';
        var change = changes[change_str].split(',');
        console.log(change);
        for (var i in change) {
            console.log(change[i]);
            str += "<td>" + change[i] + "</td>"
        }
        str += '</tr>';
    }
    return str;
};
var io = io();

io.on('workflow', function (msg) {
    var workflow = JSON.parse(msg);
    var html     = '<div class="row workflow" data-change-time="' + workflow.change_time + '" style="display: none">' +
        '<div class="change-time"> ' + workflow.change_time_short + ' <span class="label label-info">New</span></div>' +
        '<div class="project-name"> ' + workflow.project_name + '</div>' +
        '<div class="type"> ' + workflow.type + '</div>' +
        '<div class="change_info"> ' + workflow.change_info + '</div>' +
        '<div class="influence"> ' + workflow.influence + '</div>' +
        '<div class="leading_official"> ' + workflow.leading_official + '</div>' +
        '<div class="sender"> ' + workflow.sender + '</div>' +
        '</div>';
    $('.my_workflows').prepend(html);
    setWidth();
    $(".my_workflows>.workflow:first").slideDown("slow");
    if ($('.my_workflows>.workflow').size() > 10) {
        $(".my_workflows>.workflow:last").remove();
    }
});

io.on('index', function (msg) {
    if (msg == 'refresh') {
        location.reload();
    }
});

// 5 minutes execute refresh
setTimeout(function () {
    location.reload();
}, 1000 * 60 * 5);

// set width
var setWidth = function () {
    var total_width   = $(window).width();
    var content_width = (total_width - 560) / 2;
    $('.change_info').width(content_width);
    $('.influence').width(content_width);
};


window.onload = function () {
    setWidth();
};
//当浏览器窗口大小改变时，设置显示内容的高度
window.onresize = function () {
    setWidth();
};

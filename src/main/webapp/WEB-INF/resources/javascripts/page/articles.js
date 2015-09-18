$('body').timeago();

//增加/初始化笔记
$('.initArticle').click(function () {
    $.post(
        '/article',
        function (data, status) {
            //alert("Data: " + data + "\nStatus: " + status);
            window.location.href = '/article/' + data;
        });
});
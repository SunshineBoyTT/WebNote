$('body').timeago();

var initArticles = function() {
    var contentNodes = $('.articles .content');
    $.each(contentNodes, function (i, contentNode) {
        //this;      //this指向当前元素
        //i;         //i表示Array当前下标
        //contentNode;     //value表示Array当前元素
        var src = contentNode.html();
        var target = marked(src);
        contentNode.html(target);
    });
};

$(initArticles());

//增加/初始化笔记
$('.initArticle').click(function () {
    $.post(
        '/article',
        function (data, status) {
            //alert("Data: " + data + "\nStatus: " + status);
            window.location.href = '/article/' + data;
        });
});
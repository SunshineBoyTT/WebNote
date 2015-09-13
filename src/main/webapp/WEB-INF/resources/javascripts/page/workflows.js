var io = io();


$('.show-again>a.btn').click(function () {
    var change_time = $('.row.workflow:last').data('change-time');
    $.getJSON('/api/workflows/before/' + change_time, function (result) {
        if (result.length == 0) {
            alert('已经没有变更了！')
        } else {
            var html          = "";
            $.each(result, function (i, workflow) {
                var workflowNode = '<div class="row workflow" data-change-time="' + workflow.change_time + '" data-workflow-id="' + workflow.id + '">' +
                    '<div class="col-xs-2"><p>' + workflow.change_time_short + '</p></div>' +
                    '<div class="col-xs-1"><p>' + workflow.project_name + '</p></div>' +
                    '<div class="col-xs-1"><p>' + workflow.type + '</p></div>' +
                    '<div class="col-xs-3"><p>' + workflow.change_info + '</p></div>' +
                    '<div class="col-xs-2"><p>' + workflow.influence + '</p></div>' +
                    '<div class="col-xs-1"><p>' + workflow.leading_official + '</p></div>' +
                    '<div class="col-xs-1"><p>' + workflow.sender + '</p></div>' +
                    '<div class="col-xs-1"><p><a href="#delete" class="delete"><span class="glyphicon glyphicon-remove"></span></a></p></div>' +
                    '</div>';
                html += workflowNode;
            });
            //console.log(html);
            $('.container-fluid.workflows').append(html);
            var height        = $('body').height();
            var window_height = $(window).height();
            //window.scrollTo(0, height);
            $("html,body").animate({scrollTop: height - window_height}, 1000);
        }
    });
});


$('.workflows').on('click', '.delete', function () {
    var $workflow   = $(this).parents('.workflow');
    var workflow_id = $workflow.data('workflow-id');
    $.post('/api/workflow/delete/' + workflow_id, function (data) {
        io.emit('index', 'refresh');
        if (data == 1) {
            $workflow.slideUp("slow", function () {
                alert("删除成功!");
                $workflow.remove();
            });
        }
    });
});
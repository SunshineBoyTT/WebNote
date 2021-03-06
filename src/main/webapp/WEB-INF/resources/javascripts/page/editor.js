var myEditor;

// TODO Editor 初始化代码还有很大的提升空间
$(function () {
    // Editor 初始化
    myEditor = editormd("my-editormd", {
        path              : '/resources/javascripts/editor_lib/',
        width             : "100%",
        height            : 740,
        toolbarIcons      : function () {
            // Or return editormd.toolbarModes[name]; // full, simple, mini
            // Using "||" set icons align right.
            return ["undo", "redo", "|", "bold", "hr", "moreTag", "|", "info", "||", "watch", "fullscreen", "preview"]
        },
        toolbarIconsClass : {
            testIcon: "fa-gears"  // 指定一个FontAawsome的图标类
        },
        toolbarIconsClass : {
            moreTag: "fa fa-magic"  // 如果没有图标，则可以这样直接插入内容，可以是字符串或HTML标签
        },
        // 用于增加自定义工具栏的功能，可以直接插入HTML标签，不使用默认的元素创建图标
        toolbarCustomIcons: {
            file  : '<input type="file" accept=".md" />',
            faicon: '<i class="fa fa - star" onclick="alert("faicon");"></i>'
        },
        // 自定义工具栏按钮的事件处理
        toolbarHandlers   : {
            /**
             * @param {Object}      cm         CodeMirror对象
             * @param {Object}      icon       图标按钮jQuery元素对象
             * @param {Object}      cursor     CodeMirror的光标对象，可获取光标所在行和位置
             * @param {String}      selection  编辑器选中的文本
             */
            testIcon: function (cm, icon, cursor, selection) {
                //var cursor    = cm.getCursor();     //获取当前光标对象，同cursor参数
                //var selection = cm.getSelection();  //获取当前选中的文本，同selection参数
                // 替换选中文本，如果没有选中文本，则直接插入
                cm.replaceSelection("[" + selection + ":testIcon]");
                // 如果当前没有选中的文本，将光标移到要输入的位置
                if (selection === "") {
                    cm.setCursor(cursor.line, cursor.ch + 1);
                }
                // this == 当前editormd实例
                console.log("testIcon =>", this, cm, icon, cursor, selection);
            }
            ,
            moreTag : function (cm, icon, cursor, selection) {
                cm.replaceSelection('\n<!--more-->\n\n');
                console.log("testIcon2 =>", this, icon.html());
            }
        }
        ,
        lang              : {
            toolbar: {
                file    : "上传文件",
                testIcon: "自定义按钮testIcon",  // 自定义按钮的提示文本，即title属性
                moreTag : "更多标签...",
                undo    : "撤销 (Ctrl+Z)"
            }
        }
        ,
        onload            : function () {
            this.fullscreen();
        },
        onchange          : function () {
            saveArticle();
        }

    });


    // Editor 增加粘贴事件
    var myEditorMd = document.querySelector('#my-editormd');
    if (myEditorMd) {
        myEditorMd.addEventListener('paste', function (e) {
            // chrome
            if (e.clipboardData && e.clipboardData.items[0].type.indexOf('image') > -1) {
                var that   = this,
                    reader = new FileReader();
                var file   = e.clipboardData.items[0].getAsFile();

                reader.onload = function (e) {

                    var data = this.result;
                    console.log(data);
                    showArticleInfo("图片上传中...");
                    $.post('/picture', {body: data}, function (text, status) {
                        // TODO 最好是像SeagmentFault那样,在编辑框中处理.还有另一种方式是飘出悬浮框提示框
                        setTimeout(function () {
                            $('.img-new').append('<img src="/images/' + text + '.png"/>');
                            myEditor.insertValue("![请输入图片标题...](http://7d9owd.com1.z0.glb.clouddn.com/images/" + text + ".png)\n");
                            myEditor.focus();
                            showArticleInfoThenHide("图片上传成功!");
                        }, 200);
                    });

                };
                reader.readAsDataURL(file);
            }
        }, false);
    }

    // Tags初始化
    $("#largeTags").tags({
        tagSize    : "lg",
        suggestions: ["alpha", "bravo", "charlie", "delta", "echo", "foxtrot", "golf", "hotel", "india"],
        tagData    : ["juliett", "kilo"]
    });
});

// 屏蔽Ctrl+S, And 保存
document.onkeydown = function (event) {
    console.log(window.event.keyCode);
    //if ((window.event.keyCode == 17) && (window.event.keyCode == 83)) {
    //    event.returnValue = false;
    //    saveArticle();
    //    return false;
    //}

    if (window.event.ctrlKey == 1) {
        if (window.event.keyCode == 83) {
            event.returnValue = false;
            saveArticle();
            return false;
        }
    }

};


// 保存笔记
var saveArticle = function () {
    showArticleInfo("保存中...");
    var articleCode = $('.title').data('code');
    var title       = $('.title').val();
    var category    = $('.category').val();
    var content     = myEditor.getMarkdown();
    var tags        = $('.tag.label.btn-info.lg');
    var tagListStr  = "";
    tags.each(function () {
        tagListStr += "," + $(this).select('span').text().trim();
    });
    tagListStr      = tagListStr.substr(1);
    $.post(
        '/article/' + articleCode,
        {
            title     : title,
            category  : category,
            content   : content,
            tagListStr: tagListStr
        },
        function (data, status) {
            //alert("Data: " + data + "\nStatus: " + status);
            showArticleInfoThenHide("于 " + new Date().toLocaleTimeString() + " 保存成功!");
        }
    );
};

// p.articleInfo 提示消息
var showArticleInfo         = function (msg) {
    var $infoNode = $('.articleInfo');
    $infoNode.text(msg);
    $infoNode.show();
};
var showArticleInfoThenHide = function (msg) {
    var $infoNode = $('.articleInfo');
    $infoNode.stop(true, true);
    showArticleInfo(msg);
    $infoNode.fadeOut(5000);

};


// 点击触发保存笔记
$('.btn-save').click(function () {
    saveArticle();
});


// 修改关闭窗口事件
window.onbeforeunload = function () {
    saveArticle();
};

// 自动保存笔记
setInterval(function () {
    console.log("自动保存");
    saveArticle();
}, 2 * 60 * 1000);

// 修改触发保存笔记
$(function () {
    $('.title').bind('input propertychange', function () {
        saveArticle();
    });
    $('textarea').bind('input propertychange', function () {
        saveArticle();
    });
});

// 初始化tooltip
$(function () {
    $('[data-toggle="tooltip"]').tooltip()
})




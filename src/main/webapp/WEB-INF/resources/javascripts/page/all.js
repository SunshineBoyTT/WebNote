var myEditor;

$(function () {
    myEditor = editormd("my-editormd", {
        path              : '/resources/javascripts/editor_lib/',
        width             : "100%",
        height            : 740,
        toolbarIcons      : function () {
            // Or return editormd.toolbarModes[name]; // full, simple, mini
            // Using "||" set icons align right.
            return ["undo", "redo", "|", "bold", "hr", "|", "info", "||", "watch", "fullscreen", "preview"]
        },
        toolbarIconsClass : {
            testIcon: "fa-gears"  // 指定一个FontAawsome的图标类
        },
        toolbarIconTexts  : {
            testIcon2: "测试按钮"  // 如果没有图标，则可以这样直接插入内容，可以是字符串或HTML标签
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
            testIcon : function (cm, icon, cursor, selection) {
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
            testIcon2: function (cm, icon, cursor, selection) {
                cm.replaceSelection("[" + selection + ":testIcon2](" + icon.html() + ")");
                console.log("testIcon2 =>", this, icon.html());
            }
        }
        ,
        lang              : {
            toolbar: {
                file     : "上传文件",
                testIcon : "自定义按钮testIcon",  // 自定义按钮的提示文本，即title属性
                testIcon2: "自定义按钮testIcon2",
                undo     : "撤销 (Ctrl+Z)"
            }
        }
        ,
        onload            : function () {
            $("[type=\"file\"]").bind("change", function () {
                alert($(this).val());
                testEditor.cm.replaceSelection($(this).val());
                console.log($(this).val(), testEditor);
            });
        }
    })
    ;
});

var myEditorMd = document.querySelector('#my-editormd');
if (myEditorMd) {
    myEditorMd.addEventListener('paste', function (e) {
        // chrome
        if (e.clipboardData && e.clipboardData.items[0].type.indexOf('image') > -1) {
            var that   = this,
                reader = new FileReader();
            file       = e.clipboardData.items[0].getAsFile();

            reader.onload = function (e) {

                var data = this.result;
                console.log(data);
                $.post('/picture', {body: data}, function (text, status) {
                    //alert(text);
                    setTimeout(function () {
                        $('.img-new').append('<img src="/images/' + text + '.png"/>');
                        myEditor.insertValue("![请输入图片标题...](http://7d9owd.com1.z0.glb.clouddn.com/images/" + text + ".png)\n");
                        myEditor.focus();
                    }, 200);
                });

            };
            reader.readAsDataURL(file);
        }
    }, false);
}


$(function () {
    $("#largeTags").tags({
        tagSize    : "lg",
        suggestions: ["alpha", "bravo", "charlie", "delta", "echo", "foxtrot", "golf", "hotel", "india"],
        tagData    : ["juliett", "kilo"]
    });
});

// 保存笔记
$('.btn-save').click(function () {
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
            alert("保存成功!");
        });
    //return false;
});


//代码飘落

var c = document.getElementById("codeFlow");
if (c) {
    var ctx = c.getContext("2d");

//全屏
//c.height = window.innerHeight;
    c.height = 400;
    c.width  = window.innerWidth;

//文字
    var txts = "0123456789qwertyuiop[]';lkjhgfdsazxcvbnm,./-=!@#$%^&*()_+}|\{POIUYTREWQASDFGHJKL:\"?><MNBVCXZ`~";
//转为数组
    txts = txts.split("");

    var font_size = 16;
    var columns   = c.width / font_size;
//用于计算输出文字时坐标，所以长度即为列数
    var drops = [];
//初始值
    for (var x = 0; x < columns; x++)
        drops[x] = 1;

//输出文字
    function draw() {
        //让背景逐渐由透明到不透明
        ctx.fillStyle = "rgba(0, 0, 0, 0.05)";
        ctx.fillRect(0, 0, c.width, c.height);

        ctx.fillStyle = "#0F0"; //文字颜色
        ctx.font = font_size + "px arial";
        //逐行输出文字
        for (var i = 0; i < drops.length; i++) {
            //随机取要输出的文字
            var text = txts[Math.floor(Math.random() * txts.length)];
            //输出文字，注意坐标的计算
            ctx.fillText(text, i * font_size, drops[i] * font_size);

            //如果绘满一屏或随机数大于0.95（此数可自行调整，效果会不同）
            if (drops[i] * font_size > c.height || Math.random() > 0.95)
                drops[i] = 0;

            //用于Y轴坐标增加
            drops[i]++;
        }
    }

    setInterval(draw, 33);
}


// 登录流程
$('form').submit(function () {
    var query = $(this).serialize();
    //query     = query.replace(/\+/g, " ");
    //query     = query.replace(/=/g, '":"').replace(/&/g, '","');
    //query     = '{"' + query + '"}';
    //query     = JSON.parse(query);
    console.log(query);

    $.ajax({
        type   : 'POST',
        url    : '/user/logins',
        data   : query,
        //contentType: "application/json; charset=utf-8",
        success: function (data) {
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


//增加/初始化笔记
$('.initArticle').click(function () {
    $.post(
        '/article',
        function (data, status) {
            //alert("Data: " + data + "\nStatus: " + status);
            window.location.href = '/article/' + data;
        });
});


// 注销
$('.logout').click(function () {
    $.ajax({
        url    : '/user/logins',
        type   : 'DELETE',
        success: function () {
            $.cookie('userCode', '', {expires: -1}); // 删除 cookie
            setTimeout(function () {
                window.location.href = '/';
            }, 100);
        }
    });
});



<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <title>首页 - 极简图床</title>
    <meta name="description" content="支持粘贴上传、拖放上传，一键复制 markdown 链接的免费图床，简单好用，无需注册"/>
    <meta name="keywords" content="免费图床 图床网站 永久图床 不限空间 图片空间"/>
    <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="copyright" content="tuku.io"/>
    <link rel="stylesheet" href="http://yotuku.cn/assets/css/vendor.css?rv=15062323.9712654@0.1.3"/>
    <link rel="stylesheet" href="http://yotuku.cn/assets/css/style.css?rv=15062323.9712654@0.1.3"/>
    <link rel="chrome-webstore-item" href="https://chrome.google.com/webstore/detail/heebflcbemenefckkgfnnklbhdbdkagg">
</head>
<body>
<!-- Main section {-->
<div id="main" class="container"></div>
<!--} Main section -->
<div id="copy_message" class="alert book_message">复制成功</div>

<div class="modal fade" id="dlgSetting" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <form class="form-horizontal" role="form" method="post" id="qiniu_setting_form">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">设置七牛云存储账号</h4>
                </div>
                <div class="modal-body">
                    <!-- <div class="form-group"> -->
                    <div class="col-sm-12 alert alert-info">
                        <p>设置为自己的七牛账号， 可免费使用 10G 存储空间，自己的文件自己做主，<a
                                href="https://portal.qiniu.com/signup?code=3lilcw8gorcya" target="_blank">点此注册</a></p>
                    </div>
                    <!-- </div> -->
                    <div class="form-group">
                        <label for="bucket" class="col-sm-2 control-label">空间名称</label>

                        <div class="col-sm-9 col-md-9">
                            <input type="text" class="form-control" id="bucket" placeholder="空间名称" required="required"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="ak" class="col-sm-2 control-label">AK <span id="test1"
                                                                                class="glyphicon glyphicon-question-sign size-10"
                                                                                data-toggle="tooltip"
                                                                                title="在七牛后台 “账号设置 - 密钥” 下查看 AK 和 SK 的值"
                                                                                aria-hidden="true"></span></label>

                        <div class="col-sm-9 col-md-9">
                            <input type="text" class="form-control" id="ak" placeholder="Access Key"
                                   required="required"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="sk" class="col-sm-2 control-label">SK <span id="test1"
                                                                                class="glyphicon glyphicon-question-sign size-10"
                                                                                data-toggle="tooltip"
                                                                                title="在七牛后台 “账号设置 - 密钥” 下查看 AK 和 SK 的值"
                                                                                aria-hidden="true"></span></label>

                        <div class="col-sm-9 col-md-9">
                            <input type="text" class="form-control" id="sk" placeholder="Secret Key"
                                   required="required"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="sk" class="col-sm-2 control-label">域名 <span id="test1"
                                                                                class="glyphicon glyphicon-question-sign size-10"
                                                                                data-toggle="tooltip"
                                                                                title="在七牛后台指定空间下 “空间设置 - 域名设置” 下查看空间绑定的域名"
                                                                                aria-hidden="true"></span></label>

                        <div class="col-sm-9 col-md-9">
                            <div class="input-group">
                                <span class="input-group-addon">http://</span>
                                <input type="text" class="form-control" id="domain" placeholder="空间默认域名"
                                       required="required"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="sk" class="col-sm-2 control-label"></label>

                        <div class="col-sm-10">
                            <div class="alert alert-danger" id="verify_qiniu_message">校验失败，请核对七牛账号信息</div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button class="btn btn-default reset-btn pull-left" data-dismiss="modal">恢复默认设置</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <input type="submit" class="btn btn-primary save-btn" id="save_qiniu_setting" value="保存"/>
                </div>

            </div>
        </form>
    </div>
</div>

<script id="tplNavbar" type="text/x-handlebars-template">
    <nav id="Navbar" class="navbar navbar-default">
        <div class="container-fluid">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                        data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" title="Goto TUKU home [15062323.9712654@0.1.3]" href="/">yotuku.cn</a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav primary">
                    <li class="active" data-page="home"><a href="#!/">首页 <span class="sr-only">(current)</span></a></li>
                    <li data-page="uploads"><a href="#!/uploads">我上传的</a></li>
                    <li><a href="javascript:chrome.webstore.install()">Chrome 插件</a></li>
                </ul>

                <ul class="nav navbar-nav navbar-right">
                    <li><a class="setting-link" href="#">设置</a></li>
                </ul>
                <p class="navbar-text navbar-right">当前空间: <span id="current_bucket">公共空间</span></p>
            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container-fluid -->
    </nav>
</script>
<script id="tplHomePage" type="text/x-handlebars-template">
    <div class="tuku-dnd-area" id="tuku-dnd-area">
        <div class="pics text-center">
            <input type="file" id="files" name="files[]" multiple style="display:none"/>

            <h2 id="help_message" onclick="$('#files').click()">粘贴、拖动或点此选择图片上传</h2>
            <span id="settings_help">文件默认会上传到公共空间，<a target="_blank"
                                                     href="https://portal.qiniu.com/signup?code=3lilcw8gorcya">注册七牛云存储</a>并在右上角设置为自己的账号，<br/>可免费使用七牛 10G 存储空间，自己的文件自己做主</span>
        </div>
    </div>
</script>

<script id="tplUploadsPage" type="text/x-handlebars-template">
    <div class="row hidden">
        <div class="col-md-12 text-center">
            <div class="tuku-dnd-area">
                <h2><i class="glyphicon glyphicon-plus"></i> 拖动文件到这里</h2>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12 action-bar">
            <button class="btn btn-default delete-all">删除全部</button>
        </div>
    </div>
    <div class="row no-data">
        <div class="col-md-12">
            <div class="well">
                <h2>还没有上传过任何图片，去首页上传你的第一张吧</h2>
            </div>
        </div>
    </div>
    <div class="row tuku-thumbnails">
    </div>
</script>

<script id="tplImageListItem" type="text/x-handlebars-template">
    <div class="thumbnail">
        <a href="{{imageUrl}}" alt="Image You Uploaded" target="_blank">
            <div class="img-wrapper" style="overflow:hidden; height: 220px;">
                <img src="{{thumbUrl}}" style="width: 100%">

                <div class="more-actions">
                    <button class="btn btn-danger remove-btn">删除</button>
                </div>
            </div>
        </a>

        <div class="actions">
            <div class="input-group">
                <input class="form-control input-sm" value="{{imageUrl}}"/>

                <div class="input-group-btn">
                    <button class="btn btn-default btn-sm btn-block copy-btn"
                            role="button"
                            data-clipboard-text="{{imageUrl}}">复制
                    </button>
                </div>
            </div>
        </div>
    </div>
</script>

<script src="http://yotuku.cn/assets/js/vendor.js?rv=15062323.9712654@0.1.3"></script>
<script src="http://yotuku.cn/assets/libs/ZeroClipboard/ZeroClipboard.min.js"></script>
<script src="//cdn.staticfile.org/handlebars.js/2.0.0-beta.1/handlebars.min.js"></script>

<script src="/resources/my.js"></script>
<!-- CNZZ -->
<script>
    var _czc = _czc || [];
    _czc.push(["_setAccount", "1254721230"]);
    _czc.push(["_setAutoPageview", false]);
</script>
<script type="text/javascript">var cnzz_protocol = (("https:" == document.location.protocol) ? " https://" : " http://");
document.write(unescape("%3Cspan id='cnzz_stat_icon_1254721230'%3E%3C/span%3E%3Cscript src='" + cnzz_protocol + "s11.cnzz.com/stat.php%3Fid%3D1254721230%26show%3Dpic' type='text/javascript'%3E%3C/script%3E"));</script>
<!-- GA -->
<script>
    (function (i, s, o, g, r, a, m) {
        i['GoogleAnalyticsObject'] = r;
        i[r] = i[r] || function () {
                    (i[r].q = i[r].q || []).push(arguments)
                }, i[r].l = 1 * new Date();
        a = s.createElement(o),
                m = s.getElementsByTagName(o)[0];
        a.async = 1;
        a.src   = g;
        m.parentNode.insertBefore(a, m)
    })(window, document, 'script', '//www.google-analytics.com/analytics.js', 'ga');

    ga('create', 'UA-6880001-28', 'auto');
</script>
<script>
    (function () {
        function __trackPageView(options) {
            options = options || {
                        'page' : '/',
                        'title': '首页'
                    };
            ga('send', 'pageview', options);
            _czc.push(["_trackPageview", options.page]);
        }

        function __trackEvent(category, action, label, value, nodeid) {
            _czc.push(["_trackEvent", category, action, label, value, nodeid]);
        }

        if (window.TUKU) {
            TUKU.eventHub.on('track:pv', _.debounce(__trackPageView, 200));
            TUKU.eventHub.on('track:event', __trackEvent);
        }
    })();
</script>
</body>
</html>
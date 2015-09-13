function bindOnProgress(a) {
    return function (b) {
        if (b.lengthComputable) {
            var c = b.loaded / b.total * 100 | 0, d = $("#" + a + " .progress .progress-bar");
            d.html(100 == c ? "正在转存到云存储..." : c + "%"), d.css("width", c + "%")
        }
    }
}
function xhr_provider(a) {
    var b = jQuery.ajaxSettings.xhr();
    return b.upload && b.upload.addEventListener("progress", bindOnProgress(a), !1), b
}
function upload(a, b, c) {
    $("body").css("border", "none");
    var d = "id_" + Math.floor(1e5 * Math.random());
    $(".pic").length > 0 ? $(".pic:first").before("<div class='pic' id='" + d + "'></div>") : $(".pics").append("<div class='pic' id='" + d + "'></div>"), $("#" + d).append(a);
    var e = ($(a).width(), '<div class="progress"><div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width:0%;">0%</div></div>');
    $("#" + d).append(e), $.ajax({
        type          : "POST", url: "upload?name=" + encodeURIComponent(c), data: b, xhr: function () {
            return xhr_provider(d)
        }, contentType: "text/plain"
    }).done(function (a) {
        if ("success" == a.status) {
            var b = a.url, e = '<form class="form-inline" role="form"><div class="form-group"><label class="sr-only" for="exampleInputEmail2">地址</label><input type="text" class="form-control" value="' + b + '" style="width:284px"></div> <button type="button" id="copy_button_' + d + '" class="btn btn-default copy-button" data-clipboard-text="' + a.url + '">复制</button> <button type="button" id="copy_markdown_button_' + d + '" class="btn btn-default copy-button" data-clipboard-text="![](' + a.url + ')">markdown</button> <a type="button" class="btn btn-default" href="' + b + '" target="_blank">打开</a></form>';
            $(".progress:last").remove(), $("#" + d).append(e);
            var f = new ZeroClipboard($("#copy_button_" + d));
            f.on("ready", function () {
                f.on("aftercopy", function () {
                    TUKU.utils.noty({text: "已复制到剪贴板", type: "success"})
                })
            });
            var g = new ZeroClipboard($("#copy_markdown_button_" + d));
            g.on("ready", function () {
                g.on("aftercopy", function () {
                    TUKU.utils.noty({text: "已复制到剪贴板", type: "success"})
                })
            }), window.TUKU && TUKU.eventHub && (TUKU.eventHub.trigger("uploadDone", {
                imageUrl : a.url,
                imageName: c
            }), TUKU.eventHub.trigger("track:event", "upload", "image"))
        }
    })
}
+function (a) {
    var b = a.TUKU || {};
    b.eventHub = {}, _.extend(b.eventHub, Backbone.Events);
    {
        var c = Backbone.$, d = function () {
            return c ? function (a) {
                var b = c(a.container);
                "function" == typeof a.containerMethod ? a.containerMethod.call(a, a.el) : b[a.containerMethod](a.el)
            } : function () {
                throw Error("Not impl yet")
            }
        }();
        b.View = Backbone.View.extend({
            autoRender     : !0,
            autoAttach     : !1,
            containerMethod: "append",
            viewOptions    : ["autoRender", "autoAttach", "container", "containerMethod", "listen"],
            initialize     : function (a) {
                var b = this;
                if (Backbone.View.prototype.initialize.apply(this, arguments), _.extend(this, _.pick(a, this.viewOptions)), this.model && this.listenTo(this.model, "dispose", this.dispose), this.collection && this.listenTo(this.collection, "dispose", function (a) {
                        a && a !== b.collection || b.dispose()
                    }), this.autoRender && this.render(), this.autoAttach) {
                    if (!this.container)throw Error("Container require if `autoAttach` enabled.");
                    d(this), this.trigger("addedToDOM")
                }
            },
            afterRender    : function () {
            },
            getTemplateData: function () {
                return this.model ? this.model.attributes : {}
            },
            getTemplateFunc: function () {
                return Handlebars.compile
            },
            render         : function () {
                var a = this.getTemplateFunc()(this.template), b = this.getTemplateData();
                return this.$el.html(a(b)), this.afterRender(), this
            },
            disposed       : !1,
            dispose        : function () {
                if (!this.disposed) {
                    this.off(), this.remove();
                    var a = this, b = ["el", "$el", "options", "model", "collection", "subviews", "subviewsByName", "_callbacks"];
                    _.each(b, function (b) {
                        delete a[b]
                    }), this.disposed = !0
                }
            }
        }), b.CollectionView = b.View.extend({
            itemView        : Backbone.View,
            listSelector    : ".list",
            fallbackSelector: ".no-data",
            render          : function () {
                if (!this.collection)return !1;
                var a = this, b = this.getTemplateFunc()(this.template), d = this.getTemplateData() || {};
                this.$el.empty(), this.$el.append(b(d));
                var e = c(this.listSelector, this.el);
                e.empty();
                var f = this.collection.models || [];
                _.each(f, function (b) {
                    new a.itemView({model: b, container: e, containerMethod: "append", autoAttach: !0})
                }), this.trigger("afterRender");
                var g = f.length > 0;
                return c(this.fallbackSelector, this.el).toggle(!g), e.toggle(g), this
            }
        })
    }
    a.TUKU = b
}(this), +function (a) {
    var b = a.TUKU || {}, c = b.utils = {};
    Handlebars.registerHelper("list", function (a, b) {
        for (var c = "<ul>", d = 0, e = a.length; e > d; d++)c = c + "<li>" + b.fn(a[d]) + "</li>";
        return c + "</ul>"
    }), c.storage = "undefined" != typeof localStorage ? {
        get   : function (a) {
            return localStorage.getItem(a)
        }, set: function (a, b) {
            return localStorage.setItem(a, b)
        }
    } : {
        get   : function () {
        }, set: function () {
        }
    }, c.uploadImage = function () {
    }, c.noty = function (a) {
        a = _.extend({timeout: 2e3, theme: "bootstrapTheme", layout: "top"}, a), noty(a)
    };
    var d = function () {
    };
    _.extend(d.prototype, {
        size        : function () {
            var a = b.utils, c = a.storage.get("localImage.size");
            return parseInt(c || 0)
        }, put      : function (a) {
            var c = b.utils, d = this.size(), e = d;
            c.storage.set("localImage." + e + ".url", a.imageUrl), c.storage.set("localImage." + e + ".name", a.imageName), c.storage.set("localImage.size", e + 1)
        }, remove   : function (a, b) {
            c.storage.set("localImage." + a + ".deleted", 1), b && b(null, {})
        }, removeAll: function () {
            for (var a = this.size(), b = 0; a > b; b++)localStorage.removeItem("localImage." + b + ".name"), localStorage.removeItem("localImage." + b + ".url"), localStorage.removeItem("localImage." + b + ".deleted");
            c.storage.set("localImage.size", 0)
        }, get      : function (a) {
            var c = b.utils;
            return {
                id      : a,
                imageUrl: c.storage.get("localImage." + a + ".url"),
                name    : c.storage.get("localImage." + a + ".name"),
                deleted : c.storage.get("localImage." + a + ".deleted")
            }
        }, getAll   : function () {
            var a = this.size(), b = [], c = this;
            if (!a)return b;
            for (var d = 0; a > d; d++)b[b.length] = c.get(d);
            return b = _.filter(b, function (a) {
                return !a.deleted
            })
        }
    }), c.ImageStorageEngine = d, a.TUKU = b
}(this), +function (a) {
    var b = a.TUKU;
    if (!b)throw Error("TUKU was required!");
    var c = Backbone.Model.extend({
        sync: function (a) {
            var c = new b.utils.ImageStorageEngine;
            "delete" === a && (c.remove(this.id), this.trigger("dispose", this))
        }
    }), d = Backbone.Collection.extend({model: c}), e = b.View.extend({
        tagName           : "div",
        template          : $("#tplNavbar").html(),
        container         : "body",
        containerMethod   : "prepend",
        events            : {"click .setting-link": "onSettingLinkClick"},
        initialize        : function () {
            b.View.prototype.initialize.apply(this, arguments), this.listenTo(b.eventHub, "route", this.onRouteChange), this.listenTo(b.eventHub, "settings:update", this._updateUI), this.listenTo(b.eventHub, "settings:reset", this.resetSettings)
        },
        onRouteChange     : function (a) {
            this.$el.find("[data-page=" + a + "]").siblings().removeClass("active").end().addClass("active")
        },
        onSettingLinkClick: function (a) {
            return a.preventDefault(), $("#bucket").val($.cookie("bucket")), $("#ak").val($.cookie("ak")), $("#sk").val($.cookie("sk")), $("#domain").val($.cookie("domain")), $("#dlgSetting").modal("show"), ga("send", "event", "设置", "点开设置", "点开设置"), b.eventHub.trigger("track:event", "设置", "点开设置", "点开设置"), !1
        },
        afterRender       : function () {
            var a = this;
            setTimeout(function () {
                a._updateUI()
            }, 100), b.eventHub.trigger("headerReady")
        },
        resetSettings     : function () {
            $.cookie("bucket", ""), $.cookie("ak", ""), $.cookie("sk", ""), $.cookie("domain", ""), this._updateUI()
        },
        _updateUI         : function () {
            var a = $.cookie("bucket");
            $("#current_bucket").text(a ? a : "公共空间")
        }
    }), f = b.View.extend({
        tagName           : "div", template: $("#tplHomePage").html(), initialize: function () {
            b.View.prototype.initialize.apply(this, arguments), this.storageEngine = new b.utils.ImageStorageEngine, this.listenTo(b.eventHub, "uploadDone", this.onImageUploaded)
        }, onImageUploaded: function (a) {
            this.storageEngine.put(a)
        }, remove         : function () {
            this.stopListening(b.eventHub)
        }
    }), g = b.View.extend({
        tagName      : "div",
        template     : $("#tplImageListItem").html(),
        className    : "col-md-3 col-sm-4",
        events       : {"click .more-actions .remove-btn": "removeItem"},
        initialize   : function () {
            b.View.prototype.initialize.apply(this, arguments)
        },
        onCopyClicked: function () {
        },
        addedToDOM   : function () {
            console.info("added TO DOM item")
        },
        afterRender  : function () {
            var a = new ZeroClipboard(this.$el.find("[data-clipboard-text]")[0]);
            a.on("ready", function () {
                a.on("aftercopy", function () {
                    b.utils.noty({text: "已复制到剪贴板", type: "success"}), b.eventHub.trigger("track:event", "上传", "复制")
                })
            })
        },
        removeItem   : function (a) {
            return a.preventDefault(), this.model && (this.model.destroy(), b.utils.noty({
                text: "删除成功",
                type: "success"
            })), !1
        }
    }), h = b.CollectionView.extend({
        tagName        : "div",
        template       : $("#tplUploadsPage").html(),
        itemView       : g,
        containerMethod: "html",
        listSelector   : ".tuku-thumbnails",
        events         : {"click .delete-all": "removeAll"},
        removeAll      : function () {
            if (confirm("确定要删除全部么?")) {
                var a = new b.utils.ImageStorageEngine;
                a.removeAll(), this.collection.reset([]), this.render()
            }
        }
    }), i = Backbone.Router.extend({
        currentView: null,
        routes     : {"!/": "home", "!/uploads": "uploads"},
        pagesMap   : {
            home   : {page: "/", title: "首页"},
            uploads: {page: "/uploads", title: "我上传的"}
        },
        initialized: !1,
        initialize : function () {
            var a = this;
            this.on("route", function (a) {
                b.eventHub.trigger("route", a), b.eventHub.trigger("track:pv", this.pagesMap[a])
            }), new e({autoAttach: !0}), a.home(), b.eventHub.trigger("track:pv", a.pagesMap.home), a.initialized = !0
        },
        home       : function () {
            this.currentView && this.currentView.remove(), this.currentView = new f({
                container      : "#main",
                containerMethod: "html",
                autoAttach     : !0
            }), $("title").text("首页 - 极简图床")
        },
        uploads    : function () {
            var a = ($("#main"), new b.utils.ImageStorageEngine);
            this.currentView && this.currentView.remove();
            var c = a.getAll().reverse();
            c && (c = _.map(c, function (a) {
                return a.thumbUrl = a.imageUrl && /\.(jpg|jpeg|png|gif|bmp)$/.test(a.imageUrl) ? a.imageUrl : "/assets/images/file.jpg", a
            }));
            var e = new d(c);
            this.currentView = new h({
                container      : "#main",
                containerMethod: "html",
                autoAttach     : !0,
                collection     : e
            }), $("title").text("我上传的 - 极简图床")
        }
    });
    a.TUKU = b, $(function () {
        var a = new i;
        window.__app = a, Backbone.history.start(), $("body").on("click", ".pic .form-control:text,.thumbnail .form-control:text", function () {
            $(this).focus().select()
        })
    })
}(window);
var BASE_URL = location.protocol + "//" + location.host;
document.body.onpaste = function (a) {
    for (var b = a.clipboardData.items, c = 0; c < b.length; ++c) {
        var d = a.clipboardData.items[c];
        if ("file" == b[c].kind && "image/png" == b[c].type) {
            $("#help_message").css("margin-top", "0px"), $("#settings_help").hide();
            var e = new FileReader;
            e.onloadend = function () {
                var a = this.result.substr(this.result.indexOf(",") + 1), b = document.createElement("img");
                b.src = "data:image/jpeg;base64," + a, upload(b, a, "")
            }, e.readAsDataURL(d.getAsFile()), ga("send", "event", "上传", "粘贴", "成功", b.length);
            break
        }
        $("#help_message").html("请粘贴图片"), ga("send", "event", "上传", "粘贴", "非图片", b.length)
    }
}, +function () {
    function a(a) {
        switch (a) {
            case"image/jpeg":
            case"image/png":
            case"image/gif":
            case"image/bmp":
            case"image/jpg":
                return !0;
            default:
                return !1
        }
    }

    function b(b) {
        if (b.dataTransfer) {
            b.preventDefault(), $("#help_message").css("margin-top", "0px"), $("#settings_help").hide();
            for (var c = b.dataTransfer.files, d = 0; d < c.length; d++) {
                var e = c[d], f = e.type ? e.type : "n/a", g = e.size, h = e.name, i = new FileReader, j = a(f);
                j ? g > 2097152 ? (alert("请选择小于 2M 的图片！"), $("body").css("border", "none")) : (i.onload = function () {
                    var a = this.result.substr(this.result.indexOf(",") + 1), b = document.createElement("img");
                    b.src = "data:image/jpeg;base64," + a, upload(b, a, "")
                }, i.readAsDataURL(e)) : g > 10485760 ? (alert("请选择小于 10M 的文件！"), $("body").css("border", "none")) : (i.onload = function () {
                    var a = this.result.substr(this.result.indexOf(",") + 1), b = document.createElement("img");
                    b.src = "/assets/images/file.jpg", upload(b, a, h)
                }, i.readAsDataURL(e))
            }
            ga("send", "event", "上传", "拖放", "all", c.length)
        }
    }

    function c(a) {
        this.isDragging = !1, this.exitTimer = null, this.options = _.defaults(a, {
            target: "body", onDrop: function () {
            }
        }), this.init()
    }

    return window.File && window.FileReader && window.FileList && window.Blob ? (c.prototype.init = function () {
        var a = function (a) {
            return a.preventDefault(), !1
        }, b  = this, c = this.options, d = $(c.target), e = _.throttle(_.bind(b.dndStart, b), 100);
        d.on("dragenter", a).on("drop", _.bind(b.dndRelease, b)).on("mouseleave", function () {
            b.isDragging && b.dndRelease()
        }), d[0].addEventListener("dragleave", function (a) {
            a.preventDefault(), b.isDragging || b.dndRelease()
        }, !1), d[0].addEventListener("dragover", function (a) {
            a.preventDefault(), e()
        }, !1), d[0].addEventListener("drop", this.options.onDrop, !1)
    }, c.prototype.clearTimer = function () {
        this.exitTimer && clearTimeout(this.exitTimer)
    }, c.prototype.dndStart = function () {
        this.isDragging || (this.clearTimer(), $(".tuku-dnd-area").toggleClass("dragover", !0), $("#help_message").text("松开鼠标即开始上传"), this.isDragging = !0)
    }, c.prototype.dndRelease = function () {
        var a = this;
        this.exitTimer = setTimeout(function () {
            $(".tuku-dnd-area").toggleClass("dragover", !1), $("#help_message").text("粘贴、拖动或点此选择图片上传"), a.isDragging = !1
        }, 200)
    }, void new c({target: document.body, onDrop: b})) : void console.warn("你的浏览器不支持拖放文件上传，请使用现代浏览器。")
}(this), $(function () {
    function a(a) {
        var b = a.target.files;
        if (b.length > 10)return void alert("一次最多上传 10 张图片");
        for (var c, d = 0; c = b[d]; d++)if (c.type.match("image.*")) {
            var e = c.size;
            if (e > 2097152)alert("请选择小于 2M 的图片！"); else {
                var f = new FileReader;
                f.onload = function () {
                    return function (a) {
                        var b = this.result.substr(this.result.indexOf(",") + 1), c = document.createElement("img");
                        c.src = a.target.result, $("#help_message").css("margin-top", "0px"), $("#settings_help").hide(), upload(c, b, "")
                    }
                }(c), f.readAsDataURL(c)
            }
        }
        ga("send", "event", "上传", "选择文件", "all", b.length)
    }

    $(document.body).on("change", "#files", a)
}), +function () {
    var a = TUKU.eventHub;
    TUKU.eventHub.on("headerReady", function () {
        $('[data-toggle="tooltip"]').tooltip(), $("#qiniu_setting_form").bind("submit", function () {
            $("#save_qiniu_setting").addClass("disabled"), $("#save_qiniu_setting").html("校验中");
            var b = $("#bucket").val(), c = $("#ak").val(), d = $("#sk").val(), e = $("#domain").val();
            return $.cookie("bucket", b, {expires: 365}), $.cookie("ak", c, {expires: 365}), $.cookie("sk", d, {expires: 365}), $.cookie("domain", e, {expires: 365}), $.ajax({
                type: "POST",
                url: "upload?test=y"
            }).done(function (c) {
                "success" == c.status ? ($("#dlgSetting").modal("hide"), $("#current_bucket").html(b), $("#verify_qiniu_message").slideUp(), $("#save_qiniu_setting").removeClass("disabled"), $("#save_qiniu_setting").html("保存"), a.trigger("settings:update"), ga("send", "event", "设置", "保存设置", "成功")) : ($("#verify_qiniu_message").slideDown(), $.cookie("bucket", ""), $.cookie("ak", ""), $.cookie("sk", ""), $.cookie("domain", ""), $("#save_qiniu_setting").removeClass("disabled"), ga("send", "event", "设置", "保存设置", "失败"))
            }), !1
        }), $("#dlgSetting").on("click", ".reset-btn", function () {
            a.trigger("settings:reset"), ga("send", "event", "设置", "恢复默认", "恢复默认")
        })
    })
}(this);
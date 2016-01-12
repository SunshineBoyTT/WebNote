# 关于云笔记

---

# 0. 起因

一直想找一款知识管理工具，但总不Match，从.txt开始，陆续使用、尝试了OneNote,WizNote,Typecho,WordPress,EverNote等，后来迷恋上了[Markdown](http://www.appinn.com/markdown/basic.html)，又重新审视了许多工具，但总是不Match.

现在基本上使用Instapaper（Mac->iPad）做稍后读，筛选资料知识点，平时基本上用EverNote（全平台）记录，分享使用[Roc`s随想录](http://coderroc.com)博客，该博客使用的是hexo，支持Markdown格式。

但是，没有好的Markdown编辑器。

 - 要么支持图片上传，不支持快捷键；
 - 要么支持快捷键，不支持图片粘贴上传；
 - 倒是[SegmentFault](http://segmentfault.com/)全支持，但是发表博文老是失败，图片存储怕禁止外链。

于是，还不如自己动手做一个。

<!--more-->

Markdown编辑器：[Editor.md](https://pandao.github.io/editor.md/)
图片上传图床：选择七牛云存储

# 1. 进化路线

![第一阶段基本流程](http://7d9owd.com1.z0.glb.clouddn.com/images/281e4952-c385-44f3-9c3b-f640b02a2497.png)


逐渐支持账户系统，可以注册，下载生成的Markdown文件，浏览自己创建的文件列表。

二期权限、QQ第三方登录



# 2. 源代码

https://github.com/cshijiel/picture

现在代码还比较原始，待完善...





Let`s Go！

---


![WebNote2.0](http://7d9owd.com1.z0.glb.clouddn.com/images/2ab5959c-504c-433c-b0f8-d3e75371ddc3.png)

地址：[http://webnote.coding.io](http://webnote.coding.io)

回顾这两天写这个Markdown系统，走了很多之前踩过的坑，现在总结下。

<!--more-->

## Cookie

对于一个**有用户**的系统来说，与登录相关的Cookie操作必不可少，包括，

- **服务端**Cookie的读取和设置
- **浏览器端（Javascript）**对Cookie的读取和设置

安全系数较高的系统应该使用KV映射用户主键，不能直接存放用户主键，且映射关系有有效期。

## SpringMVC

经常使用SpringMVC作为MVC框架，应该熟悉其常用的读取参数的方式。

例如：

1. `@RequestMapping`可以使用在方法上，类上，有name、value(path)、method等。

		@RequestMapping("/article")
		@Controller
		public class ArticleController {
			//...
		}

2. `@ResponseBody`可以直接在response响应流中写入方法的返回值，使用fastjson等库可以返回任意类型的JSON表示。
在返回值中还可以返回`"forward"`、`"redirect"`等表示，可以覆盖`@ResponseBody`。

		return "redirect:/";

3. `@PathVariable`指定一个参数，`@ModelAttribute`自动构造对象。

		@RequestMapping(value = "/{articleCode}", method = RequestMethod.POST)
		@ResponseBody
		public String updateArticle(@PathVariable("articleCode") String articleCode, @ModelAttribute Article article) {
			article.setCode(articleCode);
			articleDao.updateArticle(article);
			return "OK";
		}

4. 使用`response`下载文件

        try (OutputStream out = response.getOutputStream()) {
            out.write(builder.toString().getBytes());//向客户端输出，实际是把数据存放在response中，然后web服务器再去response中读取
        } catch (Exception e) {
            e.printStackTrace();
        }

## 其他

1. 注销
尝试在服务端删除Cookie但是不可行，最终使用JS在浏览器进行Cookie删除(重置)。

		// 注销
		$('.logout').click(function () {
			$.ajax({
				url    : '/user/logins',
				type   : 'DELETE',
				success: function () {
					document.cookie = "userCode=;expires=-1;path=/" // 删除 cookie
					setTimeout(function () {
						window.location.href = '/';
					}, 200);
				}
			});
		});
~~使用`document.cookie = ""`应该也可以，待测试。~~
经测试，不可以。原因是该方法是设置(覆盖)，应该符合Cookie的描述。

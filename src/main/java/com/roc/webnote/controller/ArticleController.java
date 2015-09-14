package com.roc.webnote.controller;

import com.roc.webnote.entity.Article;
import com.roc.webnote.repository.mapper.ArticleMapper;
import com.roc.webnote.repository.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by yp-tc-m-2795 on 15/9/13.
 */
@RequestMapping("/article")
@Controller
public class ArticleController {
    private Logger logger = LoggerFactory.getLogger(ArticleController.class);
    @Autowired
    private UserMapper    userDao;
    @Autowired
    private ArticleMapper articleDao;

    /**
     * TODO 添加Article,跳转至URL:/article/uuid
     *
     * @param userCode
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public String createArticle(@CookieValue(value = "userCode", required = false) String userCode) {
        if (StringUtils.isEmpty(userCode)) {
            return "redirect:/";
        } else {
            UUID uuid = UUID.randomUUID();
            Article article = new Article();

            article.setCode(uuid.toString());
            article.setUserCode(userCode);

            articleDao.initArticle(article);
            return uuid.toString();//JavaScript control the direct
        }
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String articleList(@CookieValue(value = "userCode", required = false) String userCode, Model model) {
        if (StringUtils.isEmpty(userCode)) {
            return "redirect:/";
        } else {
            model.addAttribute("articles", articleDao.getArticles(userCode));
            return "articles";
        }
    }

    @RequestMapping(value = "/{articleCode}", method = RequestMethod.GET)
    public String editArticle(@PathVariable("articleCode") String articleCode, Model model) {
        model.addAttribute("article", articleDao.getArticle(articleCode));
        return "article";
    }

    @RequestMapping(value = "/{articleCode}", method = RequestMethod.POST)
    @ResponseBody
    public String updateArticle(@PathVariable("articleCode") String articleCode, @ModelAttribute Article article) {
        article.setCode(articleCode);
        articleDao.updateArticle(article);
        return "OK";
    }

    /**
     * 下载后hexo直接可用
     *
     * @param articleCode
     * @return
     */
    @RequestMapping(value = "/download/{articleCode}", method = RequestMethod.GET)
    public void downloadArticle(@PathVariable("articleCode") String articleCode, HttpServletResponse response) {
        Article       article    = articleDao.getArticle(articleCode);
        String        categories = "categories: 知识记录";
        String        createTime = "date: " + getFormatDateTime(article.getCreateTime(), "yyyy-MM-dd HH:mm:ss");
        StringBuilder builder    = new StringBuilder();
        builder.append("title: " + article.getTitle() + "\n");
        builder.append(createTime + "\n");
        builder.append(categories + "\n");
        builder.append("---\n\n");
        builder.append(article.getContent());
        response.setHeader("content-disposition", "attachment;filename=" + article.getTitle() + ".md");
        try (OutputStream out = response.getOutputStream()) {
            out.write(builder.toString().getBytes());//向客户端输出，实际是把数据存放在response中，然后web服务器再去response中读取
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getFormatDateTime(Long timestamp, String formatter) {
        Date             date   = new Date(timestamp);
        SimpleDateFormat format = new SimpleDateFormat(formatter);
        return format.format(date);
    }


}

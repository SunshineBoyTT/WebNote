package com.roc.webnote.controller;

import com.roc.webnote.entity.Article;
import com.roc.webnote.repository.mapper.ArticleMapper;
import com.roc.webnote.repository.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Created by yp-tc-m-2795 on 15/9/13.
 */
@RequestMapping("/article")
public class ArticleController {
    private Logger logger = LoggerFactory.getLogger(ArticleController.class);
    @Autowired
    private UserMapper    userDao;
    @Autowired
    private ArticleMapper articleDao;

    /**
     * TODO 添加Article,跳转至URL:/article/uuid
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String createArticle(@CookieValue("userUuid") String userUuid) {
        UUID    uuid     = UUID.randomUUID();
        Article article  = new Article();
        String  userCode = userDao.getUserByUuid(userUuid).getCode();

        article.setCode(uuid.toString());
        article.setUserCode(userCode);

        articleDao.initArticle(article);


        return "redirect:/article/" + uuid;
    }

    @RequestMapping(value = "/{articleCode}", method = RequestMethod.GET)
    public String editArticle(@PathVariable("articleCode") String articleCode, Model model) {
        model.addAttribute("article", articleDao.getArticles(articleCode));
        return "article";
    }

    @RequestMapping(value = "/{articleCode}", method = RequestMethod.POST)
    @ResponseBody
    public String updateArticle(@PathVariable("articleCode") String articleCode, @ModelAttribute Article article) {
        article.setCode(articleCode);
        articleDao.updateArticle(article);
        return "OK";
    }


}

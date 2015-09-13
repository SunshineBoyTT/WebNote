package com.roc.webnote.repository.impl;

import com.roc.webnote.entity.Article;
import org.junit.Test;

import java.util.UUID;

/**
 * Created by yp-tc-m-2795 on 15/9/13.
 */
public class ArticleDaoTest {

    @Test
    public void testInit() {
        ArticleDao articleDao = new ArticleDao();
        Article    article    = new Article();
        article.setCode(UUID.randomUUID().toString());
        articleDao.initArticle(article);
    }

    @Test
    public void testUpdate() {
        ArticleDao articleDao = new ArticleDao();
        Article    article    = new Article();
        article.setCode("3e3cba28-65fc-4616-9564-185125986b3e");
        article.setTitle("-->LDjkf");
        articleDao.updateArticle(article);
    }

}
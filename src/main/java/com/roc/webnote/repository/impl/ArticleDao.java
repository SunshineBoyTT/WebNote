package com.roc.webnote.repository.impl;

import com.roc.webnote.entity.Article;
import com.roc.webnote.repository.BaseDao;
import com.roc.webnote.repository.mapper.ArticleMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yp-tc-m-2795 on 15/9/13.
 */
@Repository
public class ArticleDao extends BaseDao implements ArticleMapper {
    @Override
    public List<Article> getArticles(String userCode) {
        try (SqlSession session = sessionFactory.openSession(true)) {
            ArticleMapper articleMapper = session.getMapper(ArticleMapper.class);
            return articleMapper.getArticles(userCode);
        }
    }

    @Override
    public Article getArticle(String articleCode) {
        try (SqlSession session = sessionFactory.openSession(true)) {
            ArticleMapper articleMapper = session.getMapper(ArticleMapper.class);
            return articleMapper.getArticle(articleCode);
        }
    }


    @Override
    public void initArticle(Article article) {
        try (SqlSession session = sessionFactory.openSession(true)) {
            ArticleMapper articleMapper = session.getMapper(ArticleMapper.class);
            articleMapper.initArticle(article);
        }
    }

    @Override
    public void updateArticle(Article article) {
        try (SqlSession session = sessionFactory.openSession(true)) {
            ArticleMapper articleMapper = session.getMapper(ArticleMapper.class);
            articleMapper.updateArticle(article);
        }
    }
}

package com.roc.webnote.repository.mapper;

import com.roc.webnote.entity.Article;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by yp-tc-m-2795 on 15/9/13.
 */
public interface ArticleMapper {
    @Select("SELECT * FROM article WHERE userCode = #{userCode} ORDER BY createTime DESC")
    List<Article> getArticles(String userCode);

    @Select("SELECT * FROM article WHERE code = #{articleCode}")
    Article getArticle(String articleCode);

    @Insert("INSERT INTO article (code,title,userCode,createTime) values (#{code}, #{title}, #{userCode}, #{createTime})")
    void initArticle(Article article);

    @Update("UPDATE article SET title = #{title}, category = #{category}, content = #{content} WHERE code = #{code}")
    void updateArticle(Article article);

}

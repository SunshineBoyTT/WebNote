package com.roc.webnote.entity;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yp-tc-m-2795 on 15/9/9.
 */
public class Article {
    private String       code;
    private String       userCode;
    private String       title;
    private String       category;
    private List<String> tags;
    private String       content;
    private Long         createTime;
    private String       tagListStr;

    public Article() {
        this.title = "新的Markdown笔记...";
//        this.category = "";
//        this.content = "";
        this.createTime = System.currentTimeMillis();
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getTagListStr() {
        return tagListStr;
    }

    public void setTagListStr(String tagListStr) {
        this.tagListStr = tagListStr;
        // TODO 同时设置TagList
        if (!StringUtils.isEmpty(tagListStr)) {
            tags = new ArrayList<>();
            for (String s : tagListStr.split(",")) {
                tags.add(s);
            }
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }
}

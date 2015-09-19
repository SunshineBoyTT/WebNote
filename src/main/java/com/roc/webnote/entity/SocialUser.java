package com.roc.webnote.entity;

/**
 * Created by yp-tc-m-2795 on 15/9/19.
 */
public class SocialUser {
    private Integer id;
    private String  type;
    private String  openID;
    private String  userCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getOpenID() {

        return openID;
    }

    public void setOpenID(String openID) {
        this.openID = openID;
    }

    public String getType() {

        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

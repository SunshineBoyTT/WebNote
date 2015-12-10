package com.roc.webnote.entity;

import org.springframework.util.StringUtils;

/**
 * Created by yp-tc-m-2795 on 15/9/11.
 */
public class User {
    private String code;
    private String userName;
    private String password;
    private String nickname;
    private String avatar;

    public String getAvatar() {
        return StringUtils.isEmpty(avatar) ? "http://img-storage.qiniudn.com/15-9-19/74106195.jpg" : avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        if (StringUtils.isEmpty(nickname)) {
            return userName;
        }
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}

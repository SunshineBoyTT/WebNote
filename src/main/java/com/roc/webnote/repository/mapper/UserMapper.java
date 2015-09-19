package com.roc.webnote.repository.mapper;

import com.roc.webnote.entity.SocialUser;
import com.roc.webnote.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

/**
 * Created by yp-tc-m-2795 on 15/9/13.
 */
public interface UserMapper {

    @Select("SELECT * FROM user WHERE userName = #{userName} AND password = #{password}")
    User getUser(User user);

    @Select("SELECT * FROM user WHERE code = #{userCode}")
    User getUserByCode(String userCode);

    @Select("SELECT * FROM user Where `code` IN (SELECT userCode FROM social WHERE type = #{type} AND openID = #{openID})")
    User getUserBySocial(SocialUser socialUser);

    @Insert("INSERT INTO user (code, userName, password, nickname, avatar) values (#{code}, #{userName}, #{password}, #{nickname}, #{avatar})")
    void insertUser(User user);
}

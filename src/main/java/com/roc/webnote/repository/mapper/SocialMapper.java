package com.roc.webnote.repository.mapper;

import com.roc.webnote.entity.SocialUser;
import org.apache.ibatis.annotations.Insert;

/**
 * Created by yp-tc-m-2795 on 15/9/19.
 */
public interface SocialMapper {
    @Insert("INSERT INTO social (type, openID, userCode) values (#{type}, #{openID}, #{userCode})")
    void insertSocialUser(SocialUser socialUser);
}

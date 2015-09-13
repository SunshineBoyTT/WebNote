package com.roc.webnote.repository.mapper;

import com.roc.webnote.entity.User;
import org.apache.ibatis.annotations.Select;

/**
 * Created by yp-tc-m-2795 on 15/9/13.
 */
public interface UserMapper {

    @Select("SELECT * FROM user WHERE userName = #{userName} AND password = #{password}")
    User getUser(User user);

    @Select("SELECT * FROM user WHERE code = #{userUuid}")
    User getUserByUuid(String userUuid);
}

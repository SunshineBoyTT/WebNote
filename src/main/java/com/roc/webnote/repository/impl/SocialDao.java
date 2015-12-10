package com.roc.webnote.repository.impl;

import com.roc.webnote.entity.SocialUser;
import com.roc.webnote.repository.BaseDao;
import com.roc.webnote.repository.mapper.SocialMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

/**
 * Created by yp-tc-m-2795 on 15/9/19.
 */
@Repository
public class SocialDao extends BaseDao implements SocialMapper {
    @Override
    public void insertSocialUser(SocialUser socialUser) {
        try (SqlSession session = sessionFactory.openSession(true)) {
            SocialMapper socialMapper = session.getMapper(SocialMapper.class);
            socialMapper.insertSocialUser(socialUser);
        }
    }
}

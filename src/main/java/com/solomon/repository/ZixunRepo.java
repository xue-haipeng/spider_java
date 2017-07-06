package com.solomon.repository;

import com.solomon.domain.Article;
import com.solomon.domain.ZixunEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Created by xuehaipeng on 2017/6/13.
 */
@NoRepositoryBean
public interface ZixunRepo<T extends ZixunEntity> extends JpaRepository<T, Long> {

}

package com.solomon.service.impl;

import com.solomon.repository.elasticsearch.FormDataRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xuehaipeng on 2017/7/6.
 */
@Service
public class EsServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(EsServiceImpl.class);

    @Autowired
    FormDataRepo formDataRepo;


}

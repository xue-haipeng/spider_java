package com.solomon;

import com.solomon.domain.Article;
import com.solomon.repository.ArticleRepo;
import com.solomon.service.ArticleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoTests {

	@Autowired
	MongoRepository mongoRepository;

	@Autowired
	ArticleRepo articleRepo;

	@Test
	public void articleTest() {

		System.out.println(mongoRepository.findAll().get(1));



	}


}

package com.solomon;

import com.solomon.domain.Keyword;
import com.solomon.repository.KeywordRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpiderApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Autowired
	KeywordRepo keywordRepo;

	Sort sort = new Sort(Sort.Direction.ASC, "id");
	Pageable pageable = new PageRequest(10, 10, sort);

	@Test
	public void dsTest() {
		Page<Keyword> page = keywordRepo.findAll(pageable);
		List<Keyword> list = page.getContent();
		list.forEach(keyword -> System.out.println(keyword));
	}

	@Test
	public void test1() {
		File file = null;
		try {
			file = new ClassPathResource("static/data/duplicate.txt").getFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (file != null) {
			System.out.println(file.getPath());
		}
	}

}

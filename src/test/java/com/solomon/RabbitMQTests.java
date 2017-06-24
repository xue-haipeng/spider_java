package com.solomon;

import com.solomon.config.RabbitMqConfig;
import com.solomon.domain.Keyword;
import com.solomon.repository.KeywordRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
public class RabbitMQTests {

	@Autowired
	RabbitTemplate rabbitTemplate;

	@Test
	public void test1() {
		rabbitTemplate.convertAndSend(RabbitMqConfig.queueName, "Hellow from RabbitMQ!");
	}



}

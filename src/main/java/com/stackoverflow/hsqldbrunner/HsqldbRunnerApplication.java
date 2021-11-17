package com.stackoverflow.hsqldbrunner;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class HsqldbRunnerApplication {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public static void main(String[] args) {
		SpringApplication.run(HsqldbRunnerApplication.class, args);
	}

	@GetMapping("/query")
	public List<Map<String, Object>> query(@RequestParam String sql) {
		return jdbcTemplate.queryForList(sql);
	}

	@RequestMapping("/execute")
	public void execute(@RequestParam String sql) {
		jdbcTemplate.execute(sql);
	}
}

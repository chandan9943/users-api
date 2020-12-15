package com.sdet.auto.users;

import com.sdet.auto.users.controller.UserController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsersApplicationTests {

	@Autowired
	private UserController userController;

	@Test
	public void app_tc0001_contextLoads() {
		assertNotNull(userController);
	}

	@Test
	public void app_tc0002_applicationContextTest() {
		UsersApplication.main(new String[] {});
	}
}
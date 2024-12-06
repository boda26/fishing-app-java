package com.example.fishinggame;

import com.example.fishinggame.mapper.UserMapper;
import com.example.fishinggame.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class FishingGameApplicationTests {

	@Autowired
	private UserMapper userMapper;

	@Test
	void testGetAllUsers() {
		System.out.println("test get all users");
		List<User> users = userMapper.getAllUsers();
		for (User user : users) {
			System.out.println(user);
		}
	}

	@Test
	void testGetUserById() {
		System.out.println("test get user by id");
		User user = userMapper.getUserById(3);
		System.out.println(user);
	}

//	@Test
//	void testAddUser() {
//		try {
//			System.out.println("test add user");
//			int randomNumber = (int) (Math.random() * 1000) + 1;
//			User user = new User();
//			String username = "user" + randomNumber;
//			user.setUsername(username);
//			user.setPassword("123456");
//			user.setEmail(username + "@gmail.com");
//			int phone1 = (int)(Math.random() * 100) + 1;
//			int phone2 = (int)(Math.random() * 100) + 1;
//			int phone3 = (int)(Math.random() * 1000) + 1;
//			String phone = phone1 + "-" + phone2 + "-" + phone3;
//			user.setPhone(phone);
//			userMapper.addUser(user);
//			System.out.println(user);
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}

//	@Test
//	void testDeleteUser() {
//		try {
//			System.out.println("test delete user");
//			int id = 8;
//			int user = userMapper.deleteUser(id);
//			System.out.println("Successfully deleted " + user + " user");
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}

	@Test
	void testUpdateUser() {
		try {
			User updatedUser = new User();
			updatedUser.setUserId(1);
			updatedUser.setUsername("Jack");
			updatedUser.setPassword("12345678");
			updatedUser.setEmail("jack@gmail.com");
			updatedUser.setPhone("111-333-9999");
			userMapper.updateUser(updatedUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

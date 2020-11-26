package com.sdet.auto.users;

import com.sdet.auto.users.model.User;
import com.sdet.auto.users.repository.UserRepository;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@DataJpaTest
public class UserRepositoryTests {

    @Autowired
    UserRepository userRepository;

    @Before
    public void setUp() {
        User user1 = new User(null, "username1", "firstname1", "lastname1",
                "email1", "role1");
        User user2 = new User(null, "username2", "firstname2", "lastname2",
                "email2", "role2");

        this.userRepository.save(user1);
        this.userRepository.save(user2);
    }

    @Test
    public void user_repository_tc0001_testFetchData() {
        int user1 = 0;
        int user2 = 1;
        List<User> saved_users = userRepository.findAll();

        assertThat("user list", saved_users.size(), greaterThanOrEqualTo(2));

        //assert user1
        assertEquals("1", saved_users.get(user1).getId().toString());
        assertEquals("username1", saved_users.get(user1).getUsername());
        assertEquals("firstname1", saved_users.get(user1).getFirstname());
        assertEquals("lastname1", saved_users.get(user1).getLastname());
        assertEquals("email1", saved_users.get(user1).getEmail());

        //assert user2
        assertEquals("2", saved_users.get(user2).getId().toString());
        assertEquals("username2", saved_users.get(user2).getUsername());
        assertEquals("firstname2", saved_users.get(user2).getFirstname());
        assertEquals("lastname2", saved_users.get(user2).getLastname());
        assertEquals("email2", saved_users.get(user2).getEmail());
    }
}
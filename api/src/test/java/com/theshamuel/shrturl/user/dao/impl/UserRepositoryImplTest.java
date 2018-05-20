package com.theshamuel.shrturl.user.dao.impl;

import com.theshamuel.shrturl.commons.base.dao.impl.BaseRepositoryImplTest;
import com.theshamuel.shrturl.user.entity.User;
import com.theshamuel.shrturl.user.entity.UserBuilder;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * The User repository tests. {@link UserRepositoryImpl}
 *
 * @author Alex Gladkikh
 */
public class UserRepositoryImplTest extends BaseRepositoryImplTest{

    private UserRepositoryImpl userRepository = new UserRepositoryImpl();

    /**
     * Test find by login.
     */
    @Test
    public void testFindByLogin() {
        User expected = new UserBuilder().login("admin").password("123").salt("salt").author("admin").build();
        createTestRecords();
        User actual = userRepository.findByLogin("admin");
        expected.setId(actual.getId());
        assertThat(actual,is(equalTo(expected)));
    }


    @Override
    public void createTestRecords() {
        initCollection("users");
        User expected = new UserBuilder().login("admin").password("123").salt("salt").author("admin").build();
        template.save(expected);
    }

    @Override
    public void setMongo() {
        userRepository.setMongo(template);
    }

}

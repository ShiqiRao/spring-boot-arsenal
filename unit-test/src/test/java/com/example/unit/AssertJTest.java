package com.example.unit;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AssertJTest {

    @Test
    public void fluentTest() {
        Integer[] arr = new Integer[]{1, 2, 3};

        assertThat(1)
                .isEqualTo(1)
                .isLessThan(2)
                .isIn(arr);

        assertThat(arr)
                .hasSize(3)
                .contains(2, 3)
                .doesNotContain(4);

        assertThat("assert that")
                .startsWith("assert")
                .endsWith("that");
    }

    @Test
    public void compareObjects() {
        User user1 = new User()
                .setUsername("anonymous")
                .setFirstName("zhang")
                .setLastName("san");
        User user2 = new User()
                .setUsername("anonymous")
                .setFirstName("zhang")
                .setLastName("san");
        User user3 = new User()
                .setUsername("anonymous")
                .setFirstName("li")
                .setLastName("si");
        Visitor visitor = new Visitor()
                .setUsername("anonymous");
        assertThat(user1)
                //各字段均需要匹配
                .isEqualToComparingFieldByField(user2)
                //忽略给出的字段
                .isEqualToIgnoringGivenFields(user3, "firstName", "lastName")
                //匹配给出的字段
                .isEqualToComparingOnlyGivenFields(visitor, "username");
    }

}

package com.example.unit;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.unit.IsHelloWorldMatcher.isHelloWorld;
import static com.example.unit.NeverSuccess.neverSuccess;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.fail;

public class JUnit4AssertTest {

    @Test
    public void test1() {
        fail();
    }

    @Test
    public void assertThatTest() {
        // 断言1等于1
        assertThat(1, equalTo(1));
        // 50是否大于30并且小于60 allOf将两匹配器组合起来
        assertThat("错误信息", 50, allOf(greaterThan(30), lessThan(60)));
        // 判断字符串是否以.txt结尾
        assertThat("错误信息", "abc.txt", endsWith(".txt"));
    }

    @Test
    public void showMatchers() {
        //equalTo 断言输入项逻辑上等于匹配项
        assertThat(1, equalTo(1));

        //hasToString 断言输入项调用.toString方法后与匹配项相等
        assertThat(1, hasToString("1"));

        //is 语法糖 单独使用等同于equalTo
        assertThat(1, is(1));

        //结合其他匹配器使用则相当于直接调用其他匹配器，仅用作提高可读性
        assertThat(1, is(hasToString("1")));

        //closeTo 输入项是否接近于匹配项，例如1.03 处于1.06+/- 0.3，即1.03到1.09这一范围内
        assertThat(new BigDecimal("1.03"), closeTo(new BigDecimal("1.06"), new BigDecimal("0.03")));

        //sameInstance 判断输入项与匹配项是否为同一实例
        assertThat(BigDecimal.ONE, sameInstance(BigDecimal.ONE));

        //greaterThan 断言输入项大于匹配项，需要都是Comparable类型的对象。
        //类似的还有greaterThanOrEqualTo, lessThan, lessThanOrEqualTo
        assertThat(new BigDecimal("1.03"), greaterThan(BigDecimal.ONE));

        //hasProperty判断javaBean是否包含匹配项声明的属性名
        assertThat(new User(), hasProperty("username"));

        //array 将数组的内容拿出来依次作比较
        assertThat(new Integer[]{1, 2, 3}, array(is(1), is(2), is(3)));

        //hasItemInArray 断言数组内包含某元素
        assertThat(new Integer[]{1, 2, 3}, hasItemInArray(2));

        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        //hasItem 断言集合中包含item
        assertThat(list, hasItem(1));

        Map<Integer, String> map = new HashMap<>();
        map.put(1, "1");
        map.put(2, "2");
        //hasEntry 判断map中是否包含entry
        assertThat(map, hasEntry(1, "1"));

        //equalToIgnoringCase 在忽略大小写的情况下断言两字符串相等
        assertThat("Process finished with exit code 0", equalToIgnoringCase("process finished with exit code 0"));

        //equalToCompressingWhiteSpace 在忽略空格的情况下断言两字符串相等
        assertThat("Process finished with exit code 0", equalToCompressingWhiteSpace("Process   finished   with exit code 0"));

        //containsString 判断输入项内包含,类似的还有endsWith, startsWith
        assertThat("Process finished with exit code 0", containsString("0"));

        //allOf 组合匹配器 如果所有匹配器都匹配才匹配 类似于&&
        assertThat(1, allOf(greaterThan(0), lessThan(2)));

        //any 组合匹配器 如果任意匹配器匹配就匹配 类似于||
        assertThat(1, anyOf(greaterThan(0), lessThan(2)));

        //not 组合匹配器 非 类似！
        assertThat(1, not(greaterThan(2)));
    }

    @Test
    public void helloWorldCustomTest() {
        assertThat("Hey Java World", isHelloWorld());
    }

    @Test
    public void neverSuccessTest() {
        assertThat(null, neverSuccess());
    }

}

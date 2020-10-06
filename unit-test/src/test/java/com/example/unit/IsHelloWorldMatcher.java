package com.example.unit;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class IsHelloWorldMatcher extends BaseMatcher<String> {

    public static <T> Matcher<String> isHelloWorld() {
        //用以返回一个匹配器示例
        return new IsHelloWorldMatcher();
    }

    @Override
    public boolean matches(Object actual) {
        //匹配业务逻辑
        if (actual == null) {
            return false;
        }
        String s = (String) actual;
        return s.startsWith("Hello") && s.endsWith("World");
    }

    @Override
    public void describeTo(Description description) {
        //对于输入项的描述,在断言失败后将会被打印到console中
        description.appendText("a string that start with \"Hello\" and end with \"World\"");
    }
}

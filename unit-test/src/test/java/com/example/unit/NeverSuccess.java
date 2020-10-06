package com.example.unit;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class NeverSuccess extends TypeSafeMatcher<String> {

    public static <T> Matcher<String> neverSuccess() {
        return new NeverSuccess();
    }

    @Override
    protected boolean matchesSafely(String item) {
        return item == null;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("never success……");
    }
}

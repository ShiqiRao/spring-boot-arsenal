package com.example.unit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(
        {QuickBeforeAfterTest.class,
                FibonacciTest.class}
)
public class TestSuite {
    //类的内容为空
    //该类仅作为以上注解的容器（持有者）而存在
}

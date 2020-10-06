package com.example.unit;

import org.junit.jupiter.api.Test;

public class SimpleAssert {

    @Test
    void assertTrue_ThenAssertFalseWithInfo() {
        //断言1结果为 true，则继续往下执行
        assert 1 == 1;
        System.out.println("第一个断言顺利通过");
        //断言2结果为 false，抛异常程序终止
        assert 1 > 1 : "停下，这里是错误信息";
        System.out.println("第二个断言顺利通过");
    }

}

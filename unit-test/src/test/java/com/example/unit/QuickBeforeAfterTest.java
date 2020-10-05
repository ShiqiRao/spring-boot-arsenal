package com.example.unit;


import org.junit.*;
import org.junit.rules.ExpectedException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.fail;


public class QuickBeforeAfterTest {

    @BeforeClass
    public static void beforeClass() {
        //在整个类所有测试方法前执行的静态方法（仅执行一次）
        System.out.println("Before Class");
    }

    @Before
    public void setup() {
        //在所有测试方法之前执行（执行数次）
        System.out.println("Before Test");
    }

    @Test
    public void test1() {
        //测试方法1
        System.out.println("test1 executed");
    }

    @Test
    public void test2() {
        //测试方法2
        System.out.println("test2 executed");
    }

    @After
    public void teardown() {
        //在所有测试方法之后执行（执行数次）
        System.out.println("After test");
    }

    @AfterClass
    public static void afterClass() {
        //在整个类所有测试方法后执行的静态方法（仅执行一次）
        System.out.println("After Class");
    }

    @Test(timeout = 10)
    public void testFailWithTimeout() throws InterruptedException {
        Thread.sleep(100);
    }

    @Test(expected = FileNotFoundException.class)
    public void testReadFile() throws IOException {
        //文件名为一个不存在的文件
        FileReader reader = new FileReader("non-existent.txt");
        reader.read();
        reader.close();
    }

    @Test
    public void testReadFile2() {
        try {
            FileReader reader = new FileReader("test.txt");
            reader.read();
            reader.close();
            fail("Expected an IOException to be thrown");
        } catch (IOException e) {
            //并不推荐该方法，e.getMessage()的内容不能保证是固定的
            assertThat(e.getMessage(), is("test.txt (系统找不到指定的文件。)"));
        }
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testReadFile3() throws IOException {
        thrown.expect(IOException.class);
        //同样不推荐该方法，原因同上。仅供参考
        thrown.expectMessage(startsWith("test.txt (系统找不到指定的文件。)"));
        FileReader reader = new FileReader("test.txt");
        reader.read();
        reader.close();
    }

    @Ignore("结果非常显而易见")
    @Test
    public void testSame() {
        assertThat(1, is(1));
    }
}

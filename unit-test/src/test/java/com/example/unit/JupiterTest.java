package com.example.unit;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;

public class JupiterTest {

    @Test
    public void testReadFile() throws IOException {
        Assertions.assertThrows(Exception.class, () -> {
            //文件名为一个不存在的文件
            FileReader reader = new FileReader("non-existent.txt");
            reader.read();
            reader.close();
        });
    }

    @Test
    public void testFailWithTimeout() throws InterruptedException {
        Assertions.assertTimeout(Duration.ofMillis(10), () -> Thread.sleep(100));
    }


}

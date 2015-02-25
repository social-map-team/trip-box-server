package com.socialmap;

import com.socialmap.server.App;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

/**
 * Created by yy on 3/1/15.
 */
public class TestClasspathResources {
    @Test
    public void a() {
        Class c= TestClasspathResources.class;

        InputStream is = TestClasspathResources.class.getResourceAsStream("/hello.txt");
        StringWriter writer = new StringWriter();
        try {
            IOUtils.copy(is, writer, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String theString = writer.toString();
        System.out.println(theString);
    }
}

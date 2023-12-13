package com.jnu.student.data;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ShopDownLoaderTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void download() {
        ShopDownLoader shopdownloader = new ShopDownLoader();
        String fileContent = shopdownloader.download("http://file.nidama.net/class/mobile_develop/data/bookstore2023.json");

        Assert.assertTrue(fileContent.contains("\"name\": \"明珠商\","));
        Assert.assertTrue(fileContent.contains("\"latitude\": \"22.251953\", "));
        Assert.assertTrue(fileContent.contains("\"longitude\": \"113.526421\","));
        Assert.assertTrue(fileContent.contains("\"memo\": \"珠海二城广场\""));
    }

    @Test
    public void parseJsonObjects() {
    }
}
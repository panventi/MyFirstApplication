package com.jnu.student.data;

import androidx.test.platform.app.InstrumentationRegistry;

import com.jnu.student.R;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class DataBankTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getFileName() {
    }

    @Test
    public void saveAndloadBookItems() {
        ArrayList<Book> bookItems = new ArrayList<>();
        bookItems.add(new Book("软件项目管理案例教程（第4版）", R.drawable.book_2));
        bookItems.add(new Book("创新工程实践", R.drawable.book_no_name));

        DataBank dataBank = new DataBank();
        dataBank.saveBookItems(InstrumentationRegistry.getInstrumentation().getTargetContext(), bookItems);

        ArrayList<Book> bookItemLoaded = dataBank.LoadBookItems(InstrumentationRegistry.getInstrumentation().getTargetContext());
        Assert.assertEquals(2, bookItems.size());
        Assert.assertEquals(bookItems.size(), bookItemLoaded.size());
        for (int i = 0; i < bookItems.size(); ++i) {
            Assert.assertNotSame(bookItems.get(i), bookItemLoaded.get(i));
            Assert.assertEquals(bookItems.get(i).getTitle(), bookItemLoaded.get(i).getTitle());
            Assert.assertEquals(bookItems.get(i).getCoverResourceId(), bookItemLoaded.get(i).getCoverResourceId());
        }
    }
}
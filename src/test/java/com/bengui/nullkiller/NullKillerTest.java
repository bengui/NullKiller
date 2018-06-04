package com.bengui.nullkiller;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.bengui.nullkiller.NullKiller.*;
import static junit.framework.TestCase.*;

/**
 * @author benjamin.massello.
 */
public class NullKillerTest {

    @Test
    public void test_IfNotNull_notNull() {
        String string = "Hello";

        ifNotNull(string, TestCase::assertNotNull)
                .ifNull(() -> assertNull(string));
    }

    @Test
    public void test_IfNotNull_null() {
        String stringNull = getNull();

        ifNull(stringNull, () -> assertNull(stringNull))
                .ifNotNull(TestCase::assertNotNull);
    }

    @Test
    public void test_ifFirstNotNull_notNull() {
        List<String> stringList = new ArrayList<>();

        stringList.add("Hello");
        ifFirstNotNull(stringList, TestCase::assertNotNull)
                .ifNull(() -> assertTrue(stringList == getNull() || stringList.isEmpty()));
    }

    @Test
    public void test_ifFirstNotNull_empty() {
        List<String> stringList = new ArrayList<>();

        ifFirstNotNull(stringList, TestCase::assertNotNull)
                .ifNull(() -> assertTrue(stringList == getNull() || stringList.isEmpty()));
    }

    @Test
    public void test_ifFirstNotNull_null() {
        List<String> stringList = getNull();

        ifFirstNotNull(stringList, TestCase::assertNotNull)
                .ifNull(() -> assertTrue(stringList == getNull() || stringList.isEmpty()));
    }

    @Test
    public void test_or_null() {
        String string = getNull();
        String word = or(string, "defaultValue");
        assertEquals(word, "defaultValue");
    }

    @Test
    public void test_or_notNull() {
        String string = "Hello";
        String word = or(string, "defaultValue");
        assertEquals(word, "Hello");
    }

    @Test
    public void test_isNotNullNorEmpty_null() {
        assertFalse(isNotNullNorEmpty(getNull()));
    }

    @Test
    public void test_isNotNullNorEmpty_empty() {
        assertFalse(isNotNullNorEmpty(new ArrayList<>()));
    }

    @Test
    public void test_isNotNullNorEmpty_notEmpty() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("Hello");

        assertTrue(isNotNullNorEmpty(strings));
    }
}

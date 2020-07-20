package com.example.sweater;

import junit.framework.Assert;
import org.junit.Test;

public class SimpleTest {
    @Test
    public void test() {
        int x = 2;
        int y = 3;

        Assert.assertEquals(5, x + y);
        Assert.assertEquals(6, x * y);
    }

    @Test(expected = ArithmeticException.class)
    private void error() {
        int i = 0;
        int l = 4/i;
    }
}

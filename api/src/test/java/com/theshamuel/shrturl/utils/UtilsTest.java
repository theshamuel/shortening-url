package com.theshamuel.shrturl.utils;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class UtilsTest {
    @Test
    public void testGenerateSortUrlSeq(){
        assertThat(Utils.generateShortUrlSeq(10).length(),is(10));
        assertThat(Utils.generateShortUrlSeq(5).length(),is(5));
        assertThat(Utils.generateShortUrlSeq(3).length(),is(3));
    }

    @Test
    public void testGetPunycodeForRfDomain(){
        assertThat(Utils.getPunycodeForRfDomain("https://домены.рф/"),is("https://xn--d1acufc5f.xn--p1ai/"));
        assertThat(Utils.getPunycodeForRfDomain("http://www.бизнесфото.рф/simple/blog"),is("http://www.xn--90aifd0ahawks.xn--p1ai/simple/blog"));
    }
}

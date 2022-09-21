package org.sbitnev.part1.oneone;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class FooBar2Test {

    @Test
    void fooBarSolution() {
        int number = 15;
        List<String> result = FooBar2.fooBarSolution(number);
        assertThat(result.get(0)).isEqualTo("1");
        assertThat(result.get(2)).isEqualTo("Foo");
        assertThat(result.get(4)).isEqualTo("Bar");
        assertThat(result.get(number - 1)).isEqualTo("FooBar");
    }
}
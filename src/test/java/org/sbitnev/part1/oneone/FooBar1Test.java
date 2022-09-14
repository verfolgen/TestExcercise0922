package org.sbitnev.part1.oneone;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class FooBar1Test {

    @Test
    void fooBarSolutionFooBar() {
        assertThat(FooBar1.fooBarSolution(15)).isEqualTo("FooBar");
    }

    @Test
    void fooBarSolutionBar() {
        assertThat(FooBar1.fooBarSolution(5)).isEqualTo("Bar");
    }

    @Test
    void fooBarSolutionFoo() {
        assertThat(FooBar1.fooBarSolution(3)).isEqualTo("Foo");
    }

    @Test
    void fooBarSolutionNumber() {
        assertThat(FooBar1.fooBarSolution(16)).isEqualTo("16");
    }
}
package org.sbitnev.part1.oneone;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//way with IntStream
public class FooBar2 {
    public static void main(String[] args) {
        System.out.print("Enter the number: ");
        Scanner scanner = new Scanner(System.in);
        int number = scanner.nextInt();

        for (String fooBarResult : fooBarSolution(number)) {
            System.out.println(fooBarResult);
        }
        scanner.close();
    }

    public static List<String> fooBarSolution(int number) {
        List<String> result = IntStream.rangeClosed(1, number)
                .mapToObj(i -> i%3==0 ? (i%5==0?"FooBar":"Foo") : i%5==0 ? "Bar" : i + "")
                .collect(Collectors.toList());
        return  result;
    }
}

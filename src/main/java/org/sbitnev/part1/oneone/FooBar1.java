package org.sbitnev.part1.oneone;

import java.util.Scanner;

//simple way with if-else
public class FooBar1 {
    public static void main(String[] args) {
        System.out.print("Enter the number: ");
        Scanner scanner = new Scanner(System.in);
        int number = scanner.nextInt();
        for(int i = 1; i < number + 1; i++) {
            System.out.println(fooBarSolution(i));
        }
        scanner.close();
    }

    public static String fooBarSolution(int sourceNumber) {
        if (sourceNumber % 3 == 0 && sourceNumber % 5 == 0) {
            return "FooBar";
        } else if (sourceNumber % 5 == 0) {
            return "Bar";
        } else if (sourceNumber % 3 == 0) {
            return "Foo";
        }
        return Integer.toString(sourceNumber);
    }
}

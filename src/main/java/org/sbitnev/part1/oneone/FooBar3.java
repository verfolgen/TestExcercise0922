package org.sbitnev.part1.oneone;

import java.util.Scanner;

public class FooBar3 {
    public static void main(String[] args) {
        System.out.print("Enter the number: ");
        Scanner scanner = new Scanner(System.in);

        int number = scanner.nextInt();
            String s = "";
            int c3 = 0, c5 = 0;
            for (int i = 1; i <= number; i++) {
                c3++;
                c5++;
                if (c3 == 3) {
                    s += "Foo";
                    c3 = 0;
                }
                if (c5 == 5) {
                    s += "Bar";
                    c5 = 0;
                }
                if (s.length() == 0)
                    System.out.println(i);
                else
                    System.out.println(s);
                s = "";
        }
        scanner.close();
    }
}

package org.sbitnev.part1.onetwo;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Multithreading {
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(2);
        File file = new File("C://", "out.txt");
        UtilMultithreading utilMultithreading = new UtilMultithreading();

        utilMultithreading.createFile(file);
        utilMultithreading.writeZero(file);


        System.out.print("Enter the number multiple of 2 and more than 0: ");
        Scanner scanner = new Scanner(System.in);

        int number = scanner.nextInt();

        Thread thread1 = new Thread(()-> {
            System.out.println("Old number of Thread-1: " + utilMultithreading.readNumber(file));
            utilMultithreading.writeNumber(file);
            System.out.println("New number of Thread-1: " + utilMultithreading.readNumber(file));
        });

        Thread thread2 = new Thread(() -> {
            System.out.println("Old number of Thread-2: " + utilMultithreading.readNumber(file));
            utilMultithreading.writeNumber(file);
            System.out.println("New number of Thread-2: " + utilMultithreading.readNumber(file));
        });

        synchronized (file) {
            for (int i = 0; i < number; i++) {
                service.execute(thread1);
                service.execute(thread2);
            }
        }
        scanner.close();
    }


}

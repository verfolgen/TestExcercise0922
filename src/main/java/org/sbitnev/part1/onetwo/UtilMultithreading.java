package org.sbitnev.part1.onetwo;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class UtilMultithreading {
    public AtomicInteger atomicInteger = new AtomicInteger(0);

    public void createFile(File file) {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void writeZero(File file) {
        try(FileWriter writer = new FileWriter("C://out.txt")) {
            writer.write(String.valueOf(atomicInteger.get()));
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void writeNumber(File file) {
        try(FileWriter writer = new FileWriter("C://out.txt")) {
            writer.write(String.valueOf(atomicInteger.getAndIncrement()));
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized int readNumber(File file) {
        int result = atomicInteger.get();
        try(FileReader fileReader = new FileReader(file); Scanner scanner = new Scanner(fileReader)) {
            while(scanner.hasNextLine()) {
                result = Integer.parseInt(scanner.nextLine());
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}

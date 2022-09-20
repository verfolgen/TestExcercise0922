package org.sbitnev.part2.v1.csv.helper;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.*;
import org.sbitnev.part2.v1.user.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.channels.ByteChannel;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class CSVHelper {
    public static String TYPE = "text/csv";
    static String [] HEADERs = {"Id", "FIrstname", "Lastname", "Email"};


    public static boolean hasCSVFormat(MultipartFile file) {
        log.info("Check that is CSV file");
        if (!TYPE.equals(file.getContentType())) {
            log.info("Isn't a CSV file");
            return false;
        }
        log.info("this is a CSV file");
        return true;
    }

    public static List<User> csvToUsers(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {
            log.info("Start parse csv to users");
            CSVParser csvParser = new CSVParser(fileReader,
                    CSVFormat.DEFAULT
                            .builder()
                            .setHeader(HEADERs)
                            .setSkipHeaderRecord(true)
                            .setIgnoreHeaderCase(true)
                            .setTrim(true)
                            .build());
            List<User> users = new ArrayList<>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                User user = new User(
                        Long.parseLong(csvRecord.get("Id")),
                        LocalDate.now(),
                        csvRecord.get("Firstname"),
                        csvRecord.get("Lastname"),
                        csvRecord.get("Email")
                );

                users.add(user);
            }
            return users;
        } catch (IOException e) {
            log.error("Fail to parse CSV file");
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }


    public static ByteArrayInputStream usersToCSV(List<User> users) {
        final CSVFormat format = CSVFormat
                .DEFAULT
                .builder()
                .setQuoteMode(QuoteMode.MINIMAL)
                .build();
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format)) {
            for(User user : users) {
                List<String> data = Arrays.asList(
                        String.valueOf(user.getId()),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail()
                );
                csvPrinter.printRecord(data);
            }
            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
        }
    }

}

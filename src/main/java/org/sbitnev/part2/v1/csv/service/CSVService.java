package org.sbitnev.part2.v1.csv.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sbitnev.part2.v1.csv.helper.CSVHelper;
import org.sbitnev.part2.v1.user.entity.User;
import org.sbitnev.part2.v1.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class CSVService {
    private final UserRepository userRepository;

    @Transactional
    public boolean saveUserFromCSV(MultipartFile file) {
        if (CSVHelper.hasCSVFormat(file)) {
            try {
                log.info("Start save user in database");
                List<User> users = CSVHelper.csvToUsers(file.getInputStream());
                for (User user : users) {
                    if (!userRepository.existsUserByEmail(user.getEmail())) {
                        userRepository.save(user);
                    }
                }

            } catch (IOException e) {
                log.error("Fail to store csv data in database");
                throw new RuntimeException("fail to store csv data: " + e.getMessage());
            }
        }
        return true;
    }

    @Transactional
    public ByteArrayInputStream load() {
        List<User> tutorials = userRepository.findAll();
        ByteArrayInputStream in = CSVHelper.usersToCSV(tutorials);
        return in;
    }
}

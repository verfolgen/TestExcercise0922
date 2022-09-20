package org.sbitnev.part2.v1.user.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sbitnev.part2.v1.user.dto.UserDTO;
import org.sbitnev.part2.v1.user.entity.User;
import org.sbitnev.part2.v1.user.mapper.UserMapper;
import org.sbitnev.part2.v1.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public List<UserDTO> findAllUsers() {
        log.info("Start find all users");
        List<User> users = userRepository.findAll();
        return users
                .stream()
                .map(UserMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void saveUser (UserDTO userDTO) {
        Optional<User> optionalUserEmail = userRepository.findUserByEmail(userDTO.getEmail());
        Optional<User> optionalUserId = userRepository.findUserById(userDTO.getId());
        log.info("Attempt save user DTO {}", userDTO);
        if(userDTO == null) {
            log.error("User DTO isn't request {}", userDTO);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User DTO isn't in request");
        }
        if (optionalUserEmail.isPresent()) {
            log.error("User with email {} is exists", userDTO.getEmail());
            throw new ResponseStatusException
                    (HttpStatus.BAD_REQUEST, "User with email "+ userDTO.getEmail() + " is exists");
        }
        if (optionalUserId.isPresent()) {
            log.error("User with id {} is exists", userDTO.getId());
            throw new ResponseStatusException
                    (HttpStatus.BAD_REQUEST, "User with id "+ userDTO.getId() + " is exists");
        }
        User userEntity = UserMapper.INSTANCE.toEntity(userDTO);
        userEntity.setCreated(LocalDate.now());
        log.info("Successfully saved user with id {}", userEntity.getId());
        userRepository.save(userEntity);
    }

    @Transactional
    public void deleteUserById (Long userId) {
        log.info("Attempt delete user by id {}", userId);
        if(!userRepository.existsById(userId)) {
            log.error("User with id {} don't exist", userId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with " + userId + " don't exist");
        }
        log.info("Successfully delete user {}", userId);
        userRepository.deleteById(userId);
    }

    @Transactional
    public void updateUser(Long id, String firstName, String lastName, String email) {
        User user = userRepository.findUserById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "User with id " + id + " don't"));
        if(firstName != null && firstName.length() > 0 && !Objects.equals(user.getFirstName(), firstName)) {
            log.info("Successfully update firstName of user with id {}", id);
            user.setFirstName(firstName);
        }
        if(lastName != null && lastName.length() > 0 && !Objects.equals(user.getLastName(), lastName)) {
            log.info("Successfully update lastName of user with id {}", id);
            user.setLastName(lastName);
        }
        if (email != null && email.length() > 0 && !Objects.equals(user.getEmail(), email)) {
            Optional<User> optionalUserEmail = userRepository.findUserByEmail(email);
            if (optionalUserEmail.isPresent()) {
                log.error("User with email {} is exists", email);
                throw new ResponseStatusException
                        (HttpStatus.BAD_REQUEST, "User with email "+ email + " is exists");
            }
            user.setEmail(email);
        }
    }
}

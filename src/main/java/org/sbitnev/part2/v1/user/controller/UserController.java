package org.sbitnev.part2.v1.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.sbitnev.part2.v1.user.dto.UserDTO;
import org.sbitnev.part2.v1.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
@Tag(name = "Users", description = "All methods for working with users of the system")
public class UserController {

    private final UserService userService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get a list of all users")
    public ResponseEntity<List<UserDTO>> findAllUsers() {
        List<UserDTO> drugRecords = userService.findAllUsers();
        return ResponseEntity.ok().body(drugRecords);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create new user ")
    public void saveUser (@Parameter(description = "DTO user") @RequestBody UserDTO userDTO) {
        userService.saveUser(userDTO);
    }

    @DeleteMapping( "/{userId}")
    @Operation(summary = "Delete user by id")
    public void deleteUser(@Parameter(description = "User id to delete") @PathVariable("userId") Long userId) {
        userService.deleteUserById(userId);
    }

    @PutMapping("/{userId}")
    @Operation(summary = "Update user by id")
    public void updateUser(@Parameter(description = "User id to update") @PathVariable("userId") Long userId,
                           @Parameter(description = "First name id to update") @RequestParam String firstName,
                           @Parameter(description = "Last name id to update") @RequestParam String lastName,
                           @Parameter(description = "Email id to update") @RequestParam String email) {
        userService.updateUser(userId, firstName, lastName, email);
    }
}

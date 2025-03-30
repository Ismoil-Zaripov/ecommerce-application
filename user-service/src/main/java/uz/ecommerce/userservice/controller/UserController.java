package uz.ecommerce.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.ecommerce.commons.model.request.UserRequest;
import uz.ecommerce.commons.model.response.UserResponse;
import uz.ecommerce.userservice.service.UserService;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @GetMapping
    public String info() {
        return "USER-SERVICE";
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserResponse> getByUsername(@PathVariable String username){
        return ok(userService.getByUsername(username));
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest request){
        return new ResponseEntity<>(
                userService.createUser(request),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable int userId){
        return ok(userService.getUserById(userId));
    }

    @GetMapping("/list")
    public ResponseEntity<Page<UserResponse>> getUsersList(@RequestParam int page,
                                                           @RequestParam int size) {
        return ok(userService.getUsersList(page,size));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable int userId,
                                                   @RequestParam UserRequest userRequest) {
        return ok(userService.updateUser(userId,userRequest));
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(OK)
    public void deleteUser(@PathVariable int userId){
        userService.deleteUser(userId);
    }
}
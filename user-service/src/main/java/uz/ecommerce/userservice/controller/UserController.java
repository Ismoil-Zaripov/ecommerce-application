package uz.ecommerce.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.ecommerce.commons.model.request.UserRequest;
import uz.ecommerce.commons.model.response.UserResponse;
import uz.ecommerce.userservice.service.UserService;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest request){
        return new ResponseEntity<>(userService.createUser(request), CREATED);
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserResponse> getByUsername(@PathVariable String username){
        return ok(userService.getByUsername(username));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable int userId){
        return ok(userService.getUserById(userId));
    }

    @GetMapping
    public ResponseEntity<Page<UserResponse>> getUsersList(
            @RequestParam int page,
            @RequestParam int size
    ){
        return ok(userService.getUsersList(page,size));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable int userId,
            @RequestParam UserRequest userRequest
    ){
        return ok(userService.updateUser(userId,userRequest));
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(OK)
    public void deleteUser(@PathVariable int userId){
        userService.deleteUser(userId);
    }
}

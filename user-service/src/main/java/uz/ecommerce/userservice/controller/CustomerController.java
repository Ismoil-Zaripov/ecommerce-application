package uz.ecommerce.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.ecommerce.commons.model.request.UserRequest;
import uz.ecommerce.commons.model.response.UserResponse;
import uz.ecommerce.userservice.service.UserService;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    @Autowired
    private UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<UserResponse> signUp(
            @RequestBody UserRequest request
    ) {
        return new ResponseEntity<>(userService.signUp(request), CREATED);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<UserResponse> getCustomerById(@PathVariable int customerId) {
        return ok(userService.getCustomerById(customerId));
    }

    @GetMapping
    public ResponseEntity<Page<UserResponse>> getCustomersList(
            @RequestParam int page,
            @RequestParam int size
    ) {
        return ok(userService.getCustomersList(page, size));
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<UserResponse> updateCustomer(
            @PathVariable int customerId,
            @RequestBody UserRequest customerRequest
    ) {
        return ok(userService.updateUser(customerId,customerRequest));
    }
}

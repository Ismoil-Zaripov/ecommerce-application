package uz.ecommerce.authenticationservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.ecommerce.authenticationservice.service.authentication.AuthenticationService;
import uz.ecommerce.commons.model.request.AuthenticationRequest;
import uz.ecommerce.commons.model.request.RegisterRequest;
import uz.ecommerce.commons.model.response.AuthenticationResponse;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/authentication")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/sign-up")
    public void signUp(
            @RequestBody RegisterRequest request
    ) {
        authenticationService.signUp(request);
    }

    @PostMapping
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ok(authenticationService.authenticate(request));
    }
}

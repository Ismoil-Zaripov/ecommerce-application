package uz.ecommerce.authenticationservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.ecommerce.authenticationservice.service.authentication.AuthenticationService;
import uz.ecommerce.authenticationservice.service.jwt.JwtService;
import uz.ecommerce.commons.model.request.AuthenticationRequest;
import uz.ecommerce.commons.model.request.RegisterRequest;
import uz.ecommerce.commons.model.response.AuthenticationResponse;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/authentication")
public class AuthenticationController {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/sign-up")
    @ResponseStatus(OK)
    public void signUp(
            @RequestBody RegisterRequest request
    ) {
        authenticationService.signUp(request);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ok(authenticationService.authenticate(request));
    }

    @GetMapping("/validate-token")
    @ResponseStatus(OK)
    public Boolean validateToken(@RequestParam String token){
        return jwtService.validateToken(token);
    }
}

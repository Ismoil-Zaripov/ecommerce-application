package uz.ecommerce.authenticationservice.service.authentication;

import uz.ecommerce.commons.model.request.AuthenticationRequest;
import uz.ecommerce.commons.model.request.RegisterRequest;
import uz.ecommerce.commons.model.response.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);

    void signUp(RegisterRequest request);
}

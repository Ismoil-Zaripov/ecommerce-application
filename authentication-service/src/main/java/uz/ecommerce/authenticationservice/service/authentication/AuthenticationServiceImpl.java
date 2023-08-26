package uz.ecommerce.authenticationservice.service.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.ecommerce.authenticationservice.client.UserClient;
import uz.ecommerce.authenticationservice.service.jwt.JwtService;
import uz.ecommerce.commons.exception.APIException;
import uz.ecommerce.commons.model.request.AuthenticationRequest;
import uz.ecommerce.commons.model.request.RegisterRequest;
import uz.ecommerce.commons.model.request.UserRequest;
import uz.ecommerce.commons.model.response.AuthenticationResponse;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final UserClient userClient;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        userClient.getByUsername(authenticationRequest.getUsername());

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()
                    )
            );

        }
        catch (BadCredentialsException e) {
            throw new APIException(
                    "#### Bad credentials! ####\n%s".formatted(e.getMessage()),
                    HttpStatus.BAD_REQUEST.value()
            );
        }

        UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        String token = jwtService
                .generateToken(userDetails);

        return new AuthenticationResponse(token);
    }

    @Override
    public void signUp(RegisterRequest request) {
        UserRequest userRequest = UserRequest.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .username(request.getUsername())
                .password(request.getPassword())
                .build();

        userClient.createUser(userRequest);
    }
}

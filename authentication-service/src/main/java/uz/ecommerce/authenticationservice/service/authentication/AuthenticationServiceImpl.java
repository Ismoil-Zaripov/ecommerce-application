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
import uz.ecommerce.authenticationservice.entity.UserDetailsImpl;
import uz.ecommerce.authenticationservice.repository.UserDetailsRepository;
import uz.ecommerce.authenticationservice.service.jwt.JwtService;
import uz.ecommerce.commons.constant.Role;
import uz.ecommerce.commons.exception.APIException;
import uz.ecommerce.commons.model.request.AuthenticationRequest;
import uz.ecommerce.commons.model.request.RegisterRequest;
import uz.ecommerce.commons.model.response.AuthenticationResponse;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsRepository userDetailsRepository;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        UserDetails userDetails = userDetailsRepository
                .findByUsername(authenticationRequest.getUsername())
                .orElseThrow();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                            userDetails.getUsername(),
                            userDetails.getPassword()
                    )
            );

        }
        catch (BadCredentialsException e) {
            throw new APIException(
                    "#### Bad credentials! ####\n%s".formatted(e.getMessage()),
                    HttpStatus.BAD_REQUEST.value()
            );
        }

        return new AuthenticationResponse(jwtService.generateToken(userDetails));
    }

    @Override
    public void signUp(RegisterRequest request) {
        boolean userIsExists = userDetailsRepository
                .findByUsername(request.getUsername())
                .isPresent();

        if (userIsExists) throw new APIException(
                "User already exists",
                HttpStatus.BAD_REQUEST.value()
        );

        UserDetailsImpl userDetails = UserDetailsImpl.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.CUSTOMER.name())
                .build();

        userDetailsRepository.save(userDetails);
    }
}

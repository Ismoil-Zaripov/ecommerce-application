package uz.ecommerce.authenticationservice.service.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import uz.ecommerce.authenticationservice.service.jwt.JwtService;
import uz.ecommerce.commons.exception.APIException;
import uz.ecommerce.commons.model.request.AuthenticationRequest;
import uz.ecommerce.commons.model.response.AuthenticationResponse;

@Service
public class AuthenticationServiceImpl implements AuthenticationService{
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
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

        return new AuthenticationResponse(jwtService.generateToken(userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername())));
    }
}

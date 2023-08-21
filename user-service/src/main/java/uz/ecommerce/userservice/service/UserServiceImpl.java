package uz.ecommerce.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import uz.ecommerce.commons.constant.Role;
import uz.ecommerce.commons.exception.APIException;
import uz.ecommerce.commons.model.request.AuthenticationRequest;
import uz.ecommerce.commons.model.request.UserRequest;
import uz.ecommerce.commons.model.response.UserResponse;
import uz.ecommerce.userservice.client.AuthenticationClient;
import uz.ecommerce.userservice.entity.User;
import uz.ecommerce.userservice.mapper.UserMapper;
import uz.ecommerce.userservice.repository.UserRepository;

import static uz.ecommerce.userservice.mapper.UserMapper.mapToResponse;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private AuthenticationClient authenticationClient;
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserResponse signUp(UserRequest request) {

        boolean userIsExists = userRepository
                .findByUsername(request.getUsername())
                .isPresent();

        if (userIsExists) throw new APIException(
                "User already exists",
                HttpStatus.BAD_REQUEST.value()
        );

        User user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .username(request.getUsername())
                .password(new BCryptPasswordEncoder().encode(request.getPassword()))  // encode
                .role(Role.CUSTOMER)
                .build();

        userRepository.save(user);

        authenticationClient.authenticate(new AuthenticationRequest(
                user.getUsername(),
                user.getPassword()
        ));

        return mapToResponse(user);
    }

    @Override
    public UserResponse getByUsername(String username) {
        return userRepository
                .findByUsername(username)
                .map(UserMapper::mapToResponse)
                .orElseThrow(() -> new APIException(
                        "User not found", 404
                ));
    }

    @Override
    public Page<UserResponse> getUsersList(int page, int size) {
        return userRepository
                .findAll(PageRequest.of(page, size))
                .map(UserMapper::mapToResponse);
    }

    @Override
    public UserResponse updateUser(int userId, UserRequest userRequest) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new APIException(
                        "User not found", 404
                ));

        user.setFirstname(userRequest.getFirstname());
        user.setLastname(userRequest.getLastname());
        user.setUsername(userRequest.getUsername());
        user.setPassword(
                new BCryptPasswordEncoder()
                .encode(userRequest.getPassword())
        );
        user.setRole(userRequest.getRole());
        user.setImageUrl(userRequest.getImageUrl());

        userRepository.save(user);

        return mapToResponse(user);
    }
}

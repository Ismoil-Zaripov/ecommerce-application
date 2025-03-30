package uz.ecommerce.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import uz.ecommerce.commons.exception.APIException;
import uz.ecommerce.commons.model.request.UserRequest;
import uz.ecommerce.commons.model.response.UserResponse;
import uz.ecommerce.userservice.entity.User;
import uz.ecommerce.userservice.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserResponse getByUsername(String username) {
        return userRepository
                .findByUsername(username)
                .map(this::mapToResponse)
                .orElseThrow(() -> new APIException(
                        "User not found", 404
                ));
    }

    @Override
    public UserResponse createUser(UserRequest request) {
        boolean userIsExists = userRepository.findByUsername(request.getUsername()).isPresent();

        System.out.println("userIsExists = " + userIsExists);
        if (userIsExists) throw new APIException(
                "User already exists",
                HttpStatus.BAD_REQUEST.value()
        );

        User user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .username(request.getUsername())
                .password(request.getPassword())
                .role(request.getRole())
                .imageUrl(request.getImageUrl())
                .build();

        userRepository.save(user);
        return mapToResponse(user);
    }

    @Override
    public UserResponse getUserById(int userId) {
        User user = getUser(userId);
        return mapToResponse(user);
    }

    @Override
    public Page<UserResponse> getUsersList(int page, int size) {
        return userRepository
                .findAll(PageRequest.of(page, size))
                .map(this::mapToResponse);
    }

    @Override
    public UserResponse updateUser(int userId, UserRequest userRequest) {
        User user = getUser(userId);

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

    @Override
    public void deleteUser(int userId) {
        User user = getUser(userId);
        userRepository.delete(user);
    }

    private UserResponse mapToResponse(User user) {
        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(user, userResponse);
        return userResponse;
    }

    private User getUser(int userId){
        return userRepository
                .findById(userId)
                .orElseThrow(() -> new APIException(
                        "User not found", 404
                ));
    }
}
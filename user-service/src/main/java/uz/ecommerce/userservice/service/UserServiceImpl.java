package uz.ecommerce.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.ecommerce.commons.constant.Role;
import uz.ecommerce.commons.exception.APIException;
import uz.ecommerce.commons.model.request.UserRequest;
import uz.ecommerce.commons.model.response.UserResponse;
import uz.ecommerce.userservice.entity.User;
import uz.ecommerce.userservice.mapper.UserMapper;
import uz.ecommerce.userservice.repository.UserRepository;

import static uz.ecommerce.userservice.mapper.UserMapper.mapToResponse;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserResponse signUp(UserRequest request) {
        boolean customerAlreadyExists = userRepository
                .findByUsername(request.getUsername())
                .isPresent();

        if (customerAlreadyExists) throw new APIException(
                "Customer already exists",
                HttpStatus.BAD_REQUEST.value()
        );

        User customer = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .username(request.getUsername())
                .password(request.getPassword())
                .imageUrl(request.getImageUrl())
                .role(Role.CUSTOMER)
                .build();

        userRepository.save(customer);

        // authenticate user

        return mapToResponse(customer);
    }

    @Override
    public UserResponse getCustomerById(int id) {
        return userRepository
                .findByIdAndRole(id, Role.CUSTOMER)
                .map(UserMapper::mapToResponse)
                .orElseThrow(() -> new APIException(
                        "Customer not found", 404
                ));
    }

    @Override
    public Page<UserResponse> getCustomersList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC);
        return userRepository
                .findAllByRole(Role.CUSTOMER, pageable)
                .map(UserMapper::mapToResponse);
    }

    @Override
    public UserResponse updateUser(int customerId, UserRequest customerRequest) {
        return null;
    }
}

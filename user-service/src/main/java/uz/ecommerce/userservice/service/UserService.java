package uz.ecommerce.userservice.service;


import org.springframework.data.domain.Page;
import uz.ecommerce.commons.model.request.UserRequest;
import uz.ecommerce.commons.model.response.UserResponse;

public interface UserService {
    UserResponse signUp(UserRequest request);
    UserResponse getByUsername(String username);
    UserResponse getUserById(int userId);
    Page<UserResponse> getUsersList(int page, int size);
    UserResponse updateUser(int userId, UserRequest userRequest);
    void deleteUser(int userId);

}

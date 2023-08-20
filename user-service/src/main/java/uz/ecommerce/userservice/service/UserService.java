package uz.ecommerce.userservice.service;


import org.springframework.data.domain.Page;
import uz.ecommerce.commons.model.request.UserRequest;
import uz.ecommerce.commons.model.response.UserResponse;

public interface UserService {
    UserResponse signUp(UserRequest request);
    UserResponse getCustomerById(int id);
    Page<UserResponse> getCustomersList(int page, int size);
    UserResponse updateUser(int customerId, UserRequest customerRequest);
}

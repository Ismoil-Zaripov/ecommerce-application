package uz.ecommerce.userservice.mapper;

import org.springframework.beans.BeanUtils;
import uz.ecommerce.commons.model.response.UserResponse;
import uz.ecommerce.userservice.entity.User;

public class UserMapper {
    public static UserResponse mapToResponse(User user) {
        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(user, userResponse);
        return userResponse;
    }
}

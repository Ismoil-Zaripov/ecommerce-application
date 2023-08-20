package uz.ecommerce.commons.model.request;

import lombok.Data;
import uz.ecommerce.commons.constant.Role;

@Data
public class UserRequest {
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private Role role;
    private String imageUrl;
}

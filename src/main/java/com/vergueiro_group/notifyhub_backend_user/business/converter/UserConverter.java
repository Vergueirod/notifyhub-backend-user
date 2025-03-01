package com.vergueiro_group.notifyhub_backend_user.business.converter;

import com.vergueiro_group.notifyhub_backend_user.business.dto.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    // Converter de DTO para entity
    public User paraUser(UserDTO){
        return User.builder();


    }
}

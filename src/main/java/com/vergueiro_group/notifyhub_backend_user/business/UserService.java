package com.vergueiro_group.notifyhub_backend_user.business;

import com.vergueiro_group.notifyhub_backend_user.business.converter.UserConverter;
import com.vergueiro_group.notifyhub_backend_user.business.dto.UserDTO;
import com.vergueiro_group.notifyhub_backend_user.infrastructure.entity.User;
import com.vergueiro_group.notifyhub_backend_user.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;

    public UserDTO saveUser(UserDTO userDTO){
        User user = userConverter.paraUser(userDTO);
        return userConverter.paraUserDTO(
                userRepository.save(user)
        );
    }

}

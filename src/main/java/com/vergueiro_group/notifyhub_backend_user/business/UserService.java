package com.vergueiro_group.notifyhub_backend_user.business;

import com.vergueiro_group.notifyhub_backend_user.business.converter.UserConverter;
import com.vergueiro_group.notifyhub_backend_user.business.dto.UserDTO;
import com.vergueiro_group.notifyhub_backend_user.infrastructure.entity.User;
import com.vergueiro_group.notifyhub_backend_user.infrastructure.exceptions.ConflictException;
import com.vergueiro_group.notifyhub_backend_user.infrastructure.exceptions.ResourceNotFoundException;
import com.vergueiro_group.notifyhub_backend_user.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final PasswordEncoder passwordEncoder;

    public UserDTO saveUser(UserDTO userDTO) {
        emailExiste(userDTO.getEmail());

        User user = userConverter.paraUser(userDTO);

        // Definir o usuário para cada endereço
        if (user.getEnderecos() != null) {
            user.getEnderecos().forEach(endereco -> endereco.setUser(user));
        }

        // Definir o usuário para cada telefone
        if (user.getTelefones() != null) {
            user.getTelefones().forEach(telefone -> telefone.setUser(user));
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userConverter.paraUserDTO(userRepository.save(user));
    }

    public UserDTO searchUserForEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não encontrado: " + email)
        );
        return userConverter.paraUserDTO(user);
    }

    public void deleteUserForEmail(String email) {
        userRepository.deleteByEmail(email);
    }

    public void emailExiste(String email) {
        if (verificaEmailExistente(email)) {
            throw new ConflictException("Email já cadastrado: " + email);
        }
    }

    public boolean verificaEmailExistente(String email) {
        return userRepository.existsByEmail(email);
    }
}

//public class UserService {
//
//    private final UserRepository userRepository;
//    private final UserConverter userConverter;
//    private final PasswordEncoder passwordEncoder;
//
//    public UserDTO saveUser(UserDTO userDTO) {
//        emailExiste(userDTO.getEmail());
//        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
//        User user = userConverter.paraUser(userDTO);
//        return userConverter.paraUserDTO(
//                userRepository.save(user)
//        );
//    }
//    public void emailExiste(String email) {
//        if (verificaEmailExistente(email)) {
//            throw new ConflictException("Email já cadastrado: " + email);
//        }
//    }
//
//    public boolean verificaEmailExistente(String email) {return userRepository.existsByEmail(email);}
//}



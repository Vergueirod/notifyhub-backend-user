package com.vergueiro_group.notifyhub_backend_user.business;

import com.vergueiro_group.notifyhub_backend_user.business.converter.UserConverter;
import com.vergueiro_group.notifyhub_backend_user.business.dto.EnderecoDTO;
import com.vergueiro_group.notifyhub_backend_user.business.dto.TelefoneDTO;
import com.vergueiro_group.notifyhub_backend_user.business.dto.UserDTO;
import com.vergueiro_group.notifyhub_backend_user.infrastructure.entity.Endereco;
import com.vergueiro_group.notifyhub_backend_user.infrastructure.entity.Telefone;
import com.vergueiro_group.notifyhub_backend_user.infrastructure.entity.User;
import com.vergueiro_group.notifyhub_backend_user.infrastructure.exceptions.ConflictException;
import com.vergueiro_group.notifyhub_backend_user.infrastructure.exceptions.ResourceNotFoundException;
import com.vergueiro_group.notifyhub_backend_user.infrastructure.repository.EnderecoRepository;
import com.vergueiro_group.notifyhub_backend_user.infrastructure.repository.TelefoneRepository;
import com.vergueiro_group.notifyhub_backend_user.infrastructure.repository.UserRepository;
import com.vergueiro_group.notifyhub_backend_user.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telephoneRepository;

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

    public UserDTO updateDataUser(String token, UserDTO dto){
        String email = jwtUtil.extractEmailToken(token.substring(7));

        dto.setPassword(dto.getPassword() != null ? passwordEncoder.encode(dto.getPassword()) : null);

        User userEntity = userRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Email não localizado"));

        User user = userConverter.updateUser(dto, userEntity);

        return userConverter.paraUserDTO(userRepository.save(user));
    }
    public EnderecoDTO updateAddress(UUID idEndereco, EnderecoDTO enderecoDTO) {
        // Busca o endereço no banco
        Endereco entity = enderecoRepository.findById(idEndereco)
                .orElseThrow(() -> new ResourceNotFoundException("Id não encontrado " + idEndereco));

        // **Garantir que o usuário não seja perdido na atualização**
        User user = entity.getUser(); // Pegamos o usuário original

        // Atualiza os dados do endereço
        Endereco endereco = userConverter.updateAddress(enderecoDTO, entity);

        // **Reassocia o usuário ao endereço atualizado**
        endereco.setUser(user);

        // Salva e retorna o DTO atualizado
        return userConverter.paraEnderecoDTO(enderecoRepository.save(endereco));
    }


    public TelefoneDTO updateTelephone(UUID idTelefone, TelefoneDTO telefoneDTO){

        Telefone entity = telephoneRepository.findById(idTelefone).orElseThrow(() ->
                new ResourceNotFoundException("Id não encontrado " + idTelefone));
        Telefone telefone = userConverter.updateTelephone(telefoneDTO,entity);

        return userConverter.paraTelefoneDTO(telephoneRepository.save(telefone));
    }

    public EnderecoDTO addAddress(String token, EnderecoDTO dto) {
        String email = jwtUtil.extractEmailToken(token.substring(7));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Email não localizado " + email));

        Endereco endereco = userConverter.paraEnderecoEntity(dto, user);

        Endereco enderecoEntity = enderecoRepository.save(endereco);

        return userConverter.paraEnderecoDTO(enderecoEntity);
    }

    public TelefoneDTO addPhone(String token, TelefoneDTO dto){
        String email = jwtUtil.extractEmailToken(token.substring(7));
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Email não localizado " + email));

        Telefone telefone = userConverter.paraTelefoneEntity(dto, user.getId());
        Telefone telefoneEntity = telephoneRepository.save(telefone);
        return userConverter.paraTelefoneDTO(telefoneEntity);
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



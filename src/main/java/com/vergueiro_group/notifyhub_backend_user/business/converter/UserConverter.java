package com.vergueiro_group.notifyhub_backend_user.business.converter;

import com.vergueiro_group.notifyhub_backend_user.business.dto.EnderecoDTO;
import com.vergueiro_group.notifyhub_backend_user.business.dto.TelefoneDTO;
import com.vergueiro_group.notifyhub_backend_user.business.dto.UserDTO;
import com.vergueiro_group.notifyhub_backend_user.infrastructure.entity.Endereco;
import com.vergueiro_group.notifyhub_backend_user.infrastructure.entity.Telefone;
import com.vergueiro_group.notifyhub_backend_user.infrastructure.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserConverter {

    // Converter de DTO para entity
    public User paraUser(UserDTO userDTO){
        return User.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .enderecos(paraListaEndereco(userDTO.getEnderecos()))
                .telefones(paraListaTelefone(userDTO.getTelefones()))
                .build();
    }
    public List<Endereco> paraListaEndereco(List<EnderecoDTO> enderecoDTOS){
        //Java Stream
        return enderecoDTOS.stream().map(this::paraEndereco).toList();

        // Com for
//        List<Endereco> enderecos = new ArrayList<>();
//        for(EnderecoDTO enderecoDTO: enderecoDTOS){
//            enderecos.add(paraEndereco(enderecoDTO))
//        }
//        return enderecos;
    }

    public Endereco paraEndereco(EnderecoDTO enderecoDTO){
        return Endereco.builder()
                .rua(enderecoDTO.getRua())
                .numero(enderecoDTO.getNumero())
                .cidade(enderecoDTO.getCidade())
                .complemento(enderecoDTO.getComplemento())
                .cep(enderecoDTO.getCep())
                .estado(enderecoDTO.getEstado())
                .build();
    }

    public List<Telefone> paraListaTelefone(List<TelefoneDTO> telefoneDTOS){
        //Java Stream
        return telefoneDTOS.stream().map(this::paraTelefone).toList();
    }

    public Telefone paraTelefone(TelefoneDTO telefoneDTO){
        return Telefone.builder()
                .number(telefoneDTO.getNumber())
                .ddd(telefoneDTO.getDdd())
                .build();
    }

    // Converter de DTO para entity
    public UserDTO paraUserDTO(User userDTO){
        return UserDTO.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .enderecos(paraListaEnderecoDTO(userDTO.getEnderecos()))
                .telefones(paraListaTelefoneDTO(userDTO.getTelefones()))
                .build();
    }
    public List<EnderecoDTO> paraListaEnderecoDTO(List<Endereco> enderecoDTOS){
        //Java Stream
        return enderecoDTOS.stream().map(this::paraEnderecoDTO).toList();

    }

    public EnderecoDTO paraEnderecoDTO(Endereco enderecoDTO){
        return EnderecoDTO.builder()
                .rua(enderecoDTO.getRua())
                .numero(enderecoDTO.getNumero())
                .cidade(enderecoDTO.getCidade())
                .complemento(enderecoDTO.getComplemento())
                .cep(enderecoDTO.getCep())
                .estado(enderecoDTO.getEstado())
                .build();
    }

    public List<TelefoneDTO> paraListaTelefoneDTO(List<Telefone> telefoneDTOS){
        //Java Stream
        return telefoneDTOS.stream().map(this::paraTelefoneDTO).toList();
    }

    public TelefoneDTO paraTelefoneDTO(Telefone telefoneDTO){
        return TelefoneDTO.builder()
                .number(telefoneDTO.getNumber())
                .ddd(telefoneDTO.getDdd())
                .build();
    }

    public User updateUser(UserDTO userDTO, User entity){
        return User.builder()
                .name(userDTO.getName() != null ? userDTO.getName() : entity.getName())
                .id(entity.getId())
                .password(userDTO.getPassword() != null ? userDTO.getPassword() : entity.getPassword())
                .email(userDTO.getEmail() != null ? userDTO.getEmail() : entity.getEmail())
                .enderecos(entity.getEnderecos())
                .telefones(entity.getTelefones())
                .build();
    }

}

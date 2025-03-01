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

    public EnderecoDTO paraEnderecoDTO(Endereco endereco){
        return EnderecoDTO.builder()
                .id(endereco.getId())
                .rua(endereco.getRua())
                .numero(endereco.getNumero())
                .cidade(endereco.getCidade())
                .complemento(endereco.getComplemento())
                .cep(endereco.getCep())
                .estado(endereco.getEstado())
                .build();
    }

    public List<TelefoneDTO> paraListaTelefoneDTO(List<Telefone> telefoneDTOS){
        //Java Stream
        return telefoneDTOS.stream().map(this::paraTelefoneDTO).toList();
    }

    public TelefoneDTO paraTelefoneDTO(Telefone telefone){
        return TelefoneDTO.builder()
                .id(telefone.getId())
                .number(telefone.getNumber())
                .ddd(telefone.getDdd())
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

    public Endereco updateAddress(EnderecoDTO dto, Endereco entity){
        return Endereco.builder()
                .id(entity.getId())
                .rua(dto.getRua() != null ? dto.getRua() : entity.getRua())
                .numero(dto.getNumero() != null ? dto.getNumero() : entity.getNumero())
                .cidade(dto.getCidade() != null ? dto.getCidade() : entity.getCidade())
                .cep(dto.getCep() != null ? dto.getCep() : entity.getCep())
                .complemento(dto.getComplemento() != null ? dto.getComplemento() : entity.getComplemento())
                .estado(dto.getEstado() != null ? dto.getEstado() : entity.getEstado())
                .build();
    }

    public Telefone updateTelephone(TelefoneDTO dto, Telefone entity){
        return Telefone.builder()
                .id(entity.getId())
                .number(dto.getNumber() != null ? dto.getNumber() : entity.getNumber())
                .ddd(dto.getDdd() != null ? dto.getDdd() : entity.getDdd())
                .build();
    }
}

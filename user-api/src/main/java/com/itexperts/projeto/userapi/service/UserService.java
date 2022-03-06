package com.itexperts.projeto.userapi.service;

import com.itexperts.projeto.userapi.dto.UserLastNameRequestDTO;
import com.itexperts.projeto.userapi.dto.UserRequestDTO;
import com.itexperts.projeto.userapi.dto.UserResponseDTO;
import com.itexperts.projeto.userapi.model.User;
import com.itexperts.projeto.userapi.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    //V1 = convertendo Entity to DTO e vice versa via getter/setter
    //V2 = convertendo Entity to DTO e vice versa via construtor
    //V3 = convertendo Entity to DTO e vice versa via modelmapper

    @Autowired
    private UserRepository userRepository;

    //para uso do modelmapper é necessário importar dependência dele no pom.xml
    //instanciando model mapper sem classe de configuração
    private static final ModelMapper modelMapper = new ModelMapper();

    //injetando dependência do model mapper via classe de configuração
    //@Autowired
    //private ModelMapper modelMapper;

    //CREATE SEM DTO
    public User createSemDTO(User user) {

        //no cenario de estar trabalhando sem DTO daria pra retornar direto o metodo repository.save
        User u = userRepository.save(user);
        return u;
    }

    //CREATE COM DTO usando model mapper com métodos externalizados ao final da classe
    @Transactional
    public UserResponseDTO createComDTOV1(UserRequestDTO userRequestDTO) {

        User userEntity = modelMapperConvertRequestDTOToEntity(userRequestDTO);

        userEntity = userRepository.save(userEntity);

        UserResponseDTO userResponseDTO = modelMapperConvertEntityToResponseDTO(userEntity);

        return userResponseDTO;
    }

    //CREATE COM DTO usando construtor
    @Transactional
    public UserResponseDTO createComDTOV2(UserRequestDTO userRequestDTO) {

        User userEntity = new User(userRequestDTO);

        userEntity = userRepository.save(userEntity);

        UserResponseDTO userResponseDTO = new UserResponseDTO(userEntity);

        return userResponseDTO;
    }

    //CREATE COM DTO usando getter e setter com métodos externalizados ao final da classe
    @Transactional
    public UserResponseDTO createComDTOV3(UserRequestDTO userRequestDTO) {

        User userEntity = new User();
        convertDTOToEntity(userRequestDTO, userEntity);

        userEntity = userRepository.save(userEntity);

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        convertEntityToDTO(userEntity, userResponseDTO);

        return userResponseDTO;
    }

    //UPDATE TOTAL com retorno sem DTO
    @Transactional
    public User updateWithReturn(Long id, User user) {

        Optional<User> userReturned = userRepository.findById(id);
        userReturned.orElseThrow(() -> new RuntimeException("User not found"));

        userReturned.get().setName(user.getName());
        userReturned.get().setLastName(user.getLastName());

        User u = userRepository.save(userReturned.get());

        return u;
    }

    //UPDATE TOTAL com retorno DTO usando getter e setter com métodos externalizados ao final da classe
    @Transactional
    public UserResponseDTO updateWithReturnDTOV1(Long id, UserRequestDTO userRequestDTO) {

        User user = new User();
        convertDTOToEntity(userRequestDTO, user);

        Optional<User> userReturned = userRepository.findById(id);
        userReturned.orElseThrow(() -> new RuntimeException("User not found"));

        userReturned.get().setName(user.getName());
        userReturned.get().setLastName(user.getLastName());

        User userEntity = userRepository.save(userReturned.get());

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        convertEntityToDTO(userEntity, userResponseDTO);

        return userResponseDTO;
    }

    //UPDATE TOTAL com retorno DTO usando construtor
    @Transactional
    public UserResponseDTO updateWithReturnDTOV2(Long id, UserRequestDTO userRequestDTO) {

        User user = new User(userRequestDTO);

        Optional<User> userReturned = userRepository.findById(id);
        userReturned.orElseThrow(() -> new RuntimeException("User not found"));

        userReturned.get().setName(user.getName());
        userReturned.get().setLastName(user.getLastName());

        User userEntity = userRepository.save(userReturned.get());

        UserResponseDTO userResponseDTO = new UserResponseDTO(userEntity);

        return userResponseDTO;
    }

    //UPDATE TOTAL com retorno DTO usando model mapper
    @Transactional
    public UserResponseDTO updateWithReturnDTOV3(Long id, UserRequestDTO userRequestDTO) {

        User user = modelMapperConvertRequestDTOToEntity(userRequestDTO);

        Optional<User> userReturned = userRepository.findById(id);
        userReturned.orElseThrow(() -> new RuntimeException("User not found"));

        userReturned.get().setName(user.getName());
        userReturned.get().setLastName(user.getLastName());

        User userEntity = userRepository.save(userReturned.get());

        UserResponseDTO userResponseDTO = modelMapperConvertEntityToResponseDTO(userEntity);

        return userResponseDTO;
    }

    //UPDATE TOTAL sem retorno sem DTO
    @Transactional
    public void updateWithoutReturn(Long id, User user) {

        Optional<User> userReturned = userRepository.findById(id);
        userReturned.orElseThrow(() -> new RuntimeException("User not found"));

        userReturned.get().setName(user.getName());
        userReturned.get().setLastName(user.getLastName());

        userRepository.save(userReturned.get());
    }

    //UPDATE TOTAL sem retorno então não é necessária a conversão do retorno, só do request
    //UPDATE TOTAL sem retorno com DTO usando getter e setter com métodos externalizados ao final da classe
    @Transactional
    public void updateWithoutReturnDTOV1(Long id, UserRequestDTO userRequestDTO) {

        Optional<User> userReturned = userRepository.findById(id);
        userReturned.orElseThrow(() -> new RuntimeException("User not found"));

        convertDTOToEntity(userRequestDTO, userReturned.get());

        userRepository.save(userReturned.get());
    }

    //UPDATE TOTAL sem retorno com DTO usando construtor
    @Transactional
    public void updateWithoutReturnDTOV2(Long id, UserRequestDTO userRequestDTO) {

        Optional<User> userReturned = userRepository.findById(id);
        userReturned.orElseThrow(() -> new RuntimeException("User not found"));

        userReturned.get().setName(userRequestDTO.getName());
        userReturned.get().setLastName(userRequestDTO.getLastName());

        userRepository.save(userReturned.get());
    }

    //UPDATE TOTAL sem retorno com DTO usando modelmapper - FAIL
    @Transactional
    public void updateWithoutReturnDTOV3(Long id, UserRequestDTO userRequestDTO) {

        Optional<User> userReturned = userRepository.findById(id);
        userReturned.orElseThrow(() -> new RuntimeException("User not found"));

        userReturned.get().setName(userRequestDTO.getName());
        userReturned.get().setLastName(userRequestDTO.getLastName());

        userRepository.save(userReturned.get());
    }

    //UPDATE PARCIAL sem DTO
    @Transactional
    public User updateOnlyUserLastNameWithoutDTO(Long id, String lastName) {

        Optional<User> userReturned = userRepository.findById(id);
        userReturned.orElseThrow(() -> new RuntimeException("User not found"));

        userReturned.get().setLastName(lastName);

        User u = userRepository.save(userReturned.get());

        return u;
    }

    //UPDATE PARCIAL com DTO usando getter e setter com métodos externalizados ao final da classe
    @Transactional
    public UserResponseDTO updateOnlyUserLastNameWithDTOV1(Long id, UserLastNameRequestDTO userLastNameRequestDTO) {

        Optional<User> userReturned = userRepository.findById(id);
        userReturned.orElseThrow(() -> new RuntimeException("User not found"));

        convertUserLastNameDTOToEntity(userLastNameRequestDTO, userReturned.get());

        User userEntity = userRepository.save(userReturned.get());

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        convertEntityToDTO(userEntity, userResponseDTO);

        return userResponseDTO;
    }

    //UPDATE PARCIAL com DTO "usando construtor" - na vdd getter e setter na mao
    @Transactional
    public UserResponseDTO updateOnlyUserLastNameWithDTOV2(Long id, UserLastNameRequestDTO userLastNameRequestDTO) {

        Optional<User> userReturned = userRepository.findById(id);
        userReturned.orElseThrow(() -> new RuntimeException("User not found"));

        //como se faria essa parte abaixo via construtor?

        userReturned.get().setLastName(userLastNameRequestDTO.getLastName());

        User userEntity = userRepository.save(userReturned.get());

        UserResponseDTO userResponseDTO = new UserResponseDTO(userEntity);

        return userResponseDTO;
    }

    //UPDATE PARCIAL com DTO usando modelmapper - deu ruim com model mapper
//    @Transactional
//    public UserResponseDTO updateOnlyUserLastNameWithDTOV3(Long id, UserLastNameRequestDTO userLastNameRequestDTO) {
//
//        Optional<User> userReturned = userRepository.findById(id);
//        userReturned.orElseThrow(() -> new RuntimeException("User not found"));
//
//        modelMapperConvertRequestLastNameDTOToEntity(userLastNameRequestDTO);
//
//        User userEntity = userRepository.save(userReturned.get());
//
//        UserResponseDTO userResponseDTO = modelMapperConvertEntityToResponseDTO(userEntity);
//
//        return userResponseDTO;
//    }

    //GET BY ID sem DTO
    @Transactional(readOnly = true)
    public User getById(Long id) {

        Optional<User> userReturned = userRepository.findById(id);
        userReturned.orElseThrow(() -> new RuntimeException("User not found"));

        return userReturned.get();
    }

    //GET BY ID com DTO usando getter e setter com métodos externalizados ao final da classe
    @Transactional(readOnly = true)
    public UserResponseDTO getByIdWithDTOV1(Long id) {

        Optional<User> userReturned = userRepository.findById(id);
        userReturned.orElseThrow(() -> new RuntimeException("User not found"));

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        convertEntityToDTO(userReturned.get(), userResponseDTO);

        return userResponseDTO;
    }

    //GET BY ID com DTO usando construtor
    @Transactional(readOnly = true)
    public UserResponseDTO getByIdWithDTOV2(Long id) {

        Optional<User> userReturned = userRepository.findById(id);
        userReturned.orElseThrow(() -> new RuntimeException("User not found"));

        UserResponseDTO userResponseDTO = new UserResponseDTO(userReturned.get());

        return userResponseDTO;
    }

    //GET BY ID com DTO usando model mapper
    @Transactional(readOnly = true)
    public UserResponseDTO getByIdWithDTOV3(Long id) {

        Optional<User> userReturned = userRepository.findById(id);
        userReturned.orElseThrow(() -> new RuntimeException("User not found"));

        UserResponseDTO userResponseDTO = modelMapperConvertEntityToResponseDTO(userReturned.get());

        return userResponseDTO;
    }

    //GEL ALL LIST sem DTO
    @Transactional(readOnly = true)
    public List<User> getAllWithoutPageable() {

        List<User> users = userRepository.findAll();
        return users;
    }

    //GEL ALL LIST com DTO usando getter e setter com métodos externalizados ao final da classe
    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllWithoutPageableWithDTOV1() {

        List<User> users = userRepository.findAll();

        List<UserResponseDTO> usersDTO = new ArrayList<>();
        UserResponseDTO userResponseDTO = new UserResponseDTO();

        for (User user : users) {
            convertEntityToDTO(user, userResponseDTO);
            usersDTO.add(userResponseDTO);
        }

        return usersDTO;
    }

    //GEL ALL LIST com DTO usando construtor
    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllWithoutPageableWithDTOV2() {

        List<User> users = userRepository.findAll();

        List<UserResponseDTO> usersDTO = new ArrayList<>();

        for (User user : users) {
            UserResponseDTO userResponseDTO = new UserResponseDTO(user);
            usersDTO.add(userResponseDTO);
        }

        return usersDTO;
    }

    //GEL ALL LIST com DTO usando model mapper
    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllWithoutPageableWithDTOV3() {

        List<User> users = userRepository.findAll();

        List<UserResponseDTO> usersDTO = new ArrayList<>();
        UserResponseDTO userResponseDTO;
        for (User user : users) {
            userResponseDTO = modelMapperConvertEntityToResponseDTO(user);
            usersDTO.add(userResponseDTO);
        }

        return usersDTO;
    }

    //GEL ALL PAGEABLE sem DTO
    @Transactional(readOnly = true)
    public Page<User> getAllWithPageable(Pageable pageable) {

        Page<User> users = userRepository.findAll(pageable);

        return users;
    }

    //GET ALL PAGEABLE com DTO usando getter e setter nao deu =(
    //GEL ALL PAGEABLE com DTO usando construtor
    @Transactional(readOnly = true)
    public Page<UserResponseDTO> getAllWithPageableWithDTOV2(Pageable pageable) {

        Page<UserResponseDTO> users = userRepository.findAll(pageable).map(UserResponseDTO::new);
        return users;
    }

    //GEL ALL PAGEABLE com DTO usando model mapper
    @Transactional(readOnly = true)
    public Page<UserResponseDTO> getAllWithPageableWithDTOV3(Pageable pageable) {

        Page<UserResponseDTO> users = userRepository.findAll(pageable).map(UserService::modelMapperConvertEntityToResponseDTO);
        return users;
    }

    //Como não retorna nada e o parâmetro não é um RequestDTO, não é necessária a conversão
    @Transactional
    public void deleteById(Long id) {

        Optional<User> userReturned = userRepository.findById(id);
        userReturned.orElseThrow(() -> new RuntimeException("User not found"));

        userRepository.deleteById(userReturned.get().getId());
    }


    //Converters Model Mapper com retorno
    //private pq só essa classe que usa
    private static User modelMapperConvertRequestDTOToEntity(UserRequestDTO userRequestDTO) {
        User user = modelMapper.map(userRequestDTO, User.class);
        return user;
    }

    private static void modelMapperConvertRequestLastNameDTOToEntity(UserLastNameRequestDTO userLastNameRequestDTO) {
        User user = modelMapper.map(userLastNameRequestDTO, User.class);
    }

    private static UserResponseDTO modelMapperConvertEntityToResponseDTO(User user) {
        UserResponseDTO userResponseDTO = modelMapper.map(user, UserResponseDTO.class);
        return userResponseDTO;
    }

    //Converts Getter e Setter
    private void convertUserLastNameDTOToEntity(UserLastNameRequestDTO dto, User user) {
        user.setLastName(dto.getLastName());
    }

    private void convertDTOToEntity(UserRequestDTO dto, User user) {
        user.setName(dto.getName());
        user.setLastName(dto.getLastName());
    }

    private void convertEntityToDTO(User user, UserResponseDTO dto) {
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setLastName(user.getLastName());
    }

}

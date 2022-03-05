package com.itexperts.projeto.userapi.service;

import com.itexperts.projeto.userapi.model.User;
import com.itexperts.projeto.userapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User create(User user) {

        //no cenario de estar trabalhando sem DTO daria pra retornar direto o metodo repository.save
        User u = userRepository.save(user);
        return u;
    }

    @Transactional
    public User updateWithReturn(Long id, User user) {

        Optional<User> userReturned = userRepository.findById(id);
        userReturned.orElseThrow(() -> new RuntimeException("User not found"));

        userReturned.get().setName(user.getName());
        userReturned.get().setLastName(user.getLastName());

        User u = userRepository.save(userReturned.get());

        return u;
    }

    @Transactional
    public void updateWithoutReturn(Long id, User user) {

        Optional<User> userReturned = userRepository.findById(id);
        userReturned.orElseThrow(() -> new RuntimeException("User not found"));

        userReturned.get().setName(user.getName());
        userReturned.get().setLastName(user.getLastName());

        userRepository.save(userReturned.get());
    }

    //Teste sem DTO
    @Transactional
    public User updateOnlyUserLastName(Long id, String lastName) {

        Optional<User> userReturned = userRepository.findById(id);
        userReturned.orElseThrow(() -> new RuntimeException("User not found"));

        userReturned.get().setLastName(lastName);

        User u = userRepository.save(userReturned.get());

        return u;
    }

    @Transactional(readOnly = true)
    public User getById(Long id) {

        Optional<User> userReturned = userRepository.findById(id);
        userReturned.orElseThrow(() -> new RuntimeException("User not found"));

        return userReturned.get();
    }

    @Transactional(readOnly = true)
    public List<User> getAllWithoutPageable() {

        List<User> users = userRepository.findAll();
        return users;
    }

    @Transactional(readOnly = true)
    public Page<User> getAllWithPageable(Pageable pageable) {

        Page<User> users = userRepository.findAll(pageable);
        return users;
    }

    public void deleteById(Long id) {

        Optional<User> userReturned = userRepository.findById(id);
        userReturned.orElseThrow(() -> new RuntimeException("User not found"));

        userRepository.deleteById(userReturned.get().getId());
    }

}

package com.example.project_medical.Service;

import com.example.project_medical.Model.User;
import com.example.project_medical.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    public User salvarUsuario(User user){
        return (User) userRepository.save(user);
    }
    public List listarUsuarios(){
        return userRepository.findAll();
    }
    public void DeleteUser(String cpf){
        userRepository.deleteById(cpf);
    }
    public void UpdateUser(User newUser, String cpfOldUser){
        User oldUser = (User) userRepository.findById(cpfOldUser).get();
        oldUser.setName(newUser.getName());
        oldUser.setCpf(newUser.getCpf());
        userRepository.save(oldUser);
    }

}

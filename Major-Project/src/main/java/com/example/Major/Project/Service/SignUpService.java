package com.example.Major.Project.Service;

import com.example.Major.Project.DTO.UserDTO;
import com.example.Major.Project.Models.User;
import com.example.Major.Project.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignUpService {
    @Autowired
    UserRepository userRepository;

    public User saveUser(UserDTO userDTO){
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setRole(userDTO.getRole());
        return userRepository.save(user);
    }


    public UserDTO validate(UserDTO userDTO){
        User user = userRepository.findByUsername(userDTO.getUsername());
        System.out.println("password"+user.getPassword());
        System.out.println(user.getUserId());
        if(user!=null && user.getPassword().equals(userDTO.getPassword())){
            System.out.println("password after"+user.getPassword());
            userDTO.setUserId(user.getUserId());
            return userDTO;
        }
        else{
            return null;
        }
    }
}

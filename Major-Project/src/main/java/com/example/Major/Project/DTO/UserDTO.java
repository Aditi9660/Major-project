package com.example.Major.Project.DTO;

import com.example.Major.Project.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long UserId;
    private String username;
    private String password;
    private String Email;
    private Role role;

    public UserDTO(Long userId, String username) {
    }

    public UserDTO(Long userId, String username, String password) {
    }

    public UserDTO(Long userId) {
    }
}

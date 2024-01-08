package com.example.studentforum.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserDTO {
    private String username;
    private String password;
    private String fullname;
    private String email;
    private String gender;
    private Date birthday;
    private String phone;
    private String address;

}

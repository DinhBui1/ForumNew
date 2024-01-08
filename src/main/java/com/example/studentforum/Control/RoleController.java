package com.example.studentforum.Control;

import com.example.studentforum.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class RoleController {
    @Autowired
    private RoleService roleService;
}

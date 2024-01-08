package com.example.studentforum.Control;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.example.studentforum.Model.Icon;
import com.example.studentforum.Service.IconService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class IconController {
    @Autowired
    private IconService iconService;

    @QueryMapping
    public List<Icon> iconList(){
        return iconService.getallIcon();
    }


    @MutationMapping
    public Icon createIcon(@Argument  String iconname,@Argument String iconimage) {
        return iconService.createIcon(iconname, iconimage);
    }

}

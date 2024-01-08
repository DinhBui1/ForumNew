package com.example.studentforum.Service;

import com.example.studentforum.Model.Icon;
import com.example.studentforum.Repository.IconRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IconService {
    @Autowired
    private IconRepository iconRepository;

    public List<Icon> getallIcon(){
        return iconRepository.findAll();
    }

    public Icon createIcon(String iconname,String iconimage){
        Icon icon =new Icon();
        icon.setIconname(iconname);
        icon.setIconimage(iconimage);
        iconRepository.save(icon);
        return  icon;
    }
}

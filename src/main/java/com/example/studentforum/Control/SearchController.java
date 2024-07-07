package com.example.studentforum.Control;

import com.example.studentforum.Service.SearchService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;

@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping("/infosearch")
    public ResponseEntity<List<String>> search(@RequestParam("userId") String userId) {
        List<String> listInfoStrings = searchService.getInfoSearch(userId);
        return ResponseEntity.ok(listInfoStrings);
    }

    @DeleteMapping("/infosearch")
    public ResponseEntity<Object> deleteSearch(@RequestParam("userId") String userId, @RequestParam("keyword") String keyword) {
        searchService.deleteInfoSearch(userId, keyword);
        return ResponseEntity.noContent().build();
    }
}

package com.example.studentforum.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    @Autowired
    @Qualifier("redisTemplates")
    private RedisTemplate<String, String> template;

    public List<String> getInfoSearch(String userId) {
        List<String> values = template.opsForList().range(userId, 0, 6);
        return values;
    }

    public void deleteInfoSearch(String userId, String keyword) {
        template.opsForList().remove(userId, 1, keyword);
//        List<String> values = new ArrayList<>();
//        values.add(keyword);
//        template.opsForList().rightPushAll(userId, values);
    }
}

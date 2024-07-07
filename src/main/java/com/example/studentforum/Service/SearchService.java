package com.example.studentforum.Service;

import com.example.studentforum.Config.RedisManager;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.Collections;
import java.util.List;

@Service
public class SearchService {

    public List<String> getInfoSearch(String userId) {
        Jedis jedis = RedisManager.getConnection();
        List<String> listValues = jedis.lrange(userId, 0, -1);
        RedisManager.closeConnection();
        return listValues;
    }

    public void deleteInfoSearch(String userId, String keyword) {
        Jedis jedis = RedisManager.getConnection();
        List<String> listValues = jedis.lrange(userId, 0, -1);
        if (listValues.contains(keyword)) {
            jedis.lrem(userId, 0, keyword);
        }
        Collections.reverse(listValues);
        RedisManager.closeConnection();
    }
}

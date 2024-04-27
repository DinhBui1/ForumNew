package com.example.studentforum.Service;

import com.example.studentforum.Config.RedisManager;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.List;

@Service
public class SearchService {

    public List<String> getInfoSearch(String userId) {
        Jedis jedis = RedisManager.getConnection();
        List<String> listValues = jedis.lrange(userId, 0, -1);
        return listValues;
    }
}

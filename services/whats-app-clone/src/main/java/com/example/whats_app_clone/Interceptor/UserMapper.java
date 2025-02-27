package com.example.whats_app_clone.Interceptor;

import com.example.whats_app_clone.user.User;
import com.example.whats_app_clone.user.UserResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class UserMapper {

    public User fromTokenAttributes(Map<String, Object> claims) {
        User user = new User();
        if(claims.containsKey("sub")){
            user.setId(claims.get("sub").toString());
        }
        if(claims.containsKey("given_name")){
            user.setName(claims.get("given_name").toString());
        }
        else if(claims.containsKey("nickname")){
            user.setName(claims.get("nickname").toString());
        }
        else if(claims.containsKey("email")){
            user.setName(claims.get("email").toString());
        }

        user.setLastSeen(LocalDateTime.now());

        return null;

    }

    public UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .lastSeen(user.getLastSeen())
                .email(user.getEmail())
                .isOnline(user.isUserOnline())
                .build();
    }
}

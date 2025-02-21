package com.example.whats_app_clone.Interceptor;

import com.example.whats_app_clone.user.User;
import com.example.whats_app_clone.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserSynchronizer {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public void synchronizeWithIdp(Jwt token) {
        log.info("Synchronizing user with idp");
        getUserEmail(token).ifPresent(userEmail -> {
            log.info("Synchronizing user having email {}", userEmail);
            Optional<User> optUser = userRepository.findByEmail(userEmail);
            if (optUser.isPresent()) {
                 User user = userMapper.fromTokenAttributes(token.getClaims());
            }
        });
    }

    private Optional<String> getUserEmail(Jwt token) {
        Map<String, Object> claims = token.getClaims();
        if(claims.containsKey("email")){
            return Optional.of((String) claims.get("email"));
        }
        return Optional.ofNullable(token.getClaim("email"));

    }
}

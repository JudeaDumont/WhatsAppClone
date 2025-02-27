package com.example.whats_app_clone.user;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private String id;
    private String name;
    private String email;
    private LocalDateTime lastSeen;
    private boolean isOnline;
}

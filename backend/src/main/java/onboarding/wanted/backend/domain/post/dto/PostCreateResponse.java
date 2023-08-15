package onboarding.wanted.backend.domain.post.dto;

import lombok.*;
import onboarding.wanted.backend.domain.post.entity.Post;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostCreateResponse {
    private String title;
    private String content;
    private String username;
    private LocalDateTime createdAt;

    public static PostCreateResponse of(Post post) {
        return PostCreateResponse.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .username(post.getUser().getUsername())
                .createdAt(post.getCreatedAt())
                .build();
    }

}

package onboarding.wanted.backend.domain.post.dto;

import lombok.*;
import onboarding.wanted.backend.domain.post.entity.Post;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostGetResponse {

    private String title;
    private String content;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static PostGetResponse of(Post post) {

        return PostGetResponse.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .username(post.getUser().getUsername())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }

}

package onboarding.wanted.backend.domain.post.dto;

import lombok.*;
import onboarding.wanted.backend.domain.post.entity.Post;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class PostUpdateResponse {

    private String title;
    private String content;
    private String username;
    private LocalDateTime updatedAt;

    public static PostUpdateResponse of(Post post) {

        return PostUpdateResponse.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .username(post.getUser().getUsername())
                .updatedAt(post.getUpdatedAt())
                .build();
    }

}

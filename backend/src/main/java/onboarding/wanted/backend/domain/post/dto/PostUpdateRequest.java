package onboarding.wanted.backend.domain.post.dto;

import lombok.*;

import javax.validation.constraints.Size;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostUpdateRequest {

    @Size(max = 100, message = "게시글 제목은 100자까지 작성 가능합니다.")
    private String title;

    private String content;

}

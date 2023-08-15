package onboarding.wanted.backend.domain.post.service;

import lombok.RequiredArgsConstructor;
import onboarding.wanted.backend.domain.post.dto.PostCreateRequest;
import onboarding.wanted.backend.domain.post.dto.PostCreateResponse;
import onboarding.wanted.backend.domain.post.entity.Post;
import onboarding.wanted.backend.domain.post.repository.PostRepository;
import onboarding.wanted.backend.domain.user.entity.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public PostCreateResponse createPost(PostCreateRequest createReqDto) {

        //USER정보 가져오기
        Post post = createReqDto.toEntity(User.builder().build()); // TODO: 임시처리해둠

        return PostCreateResponse.of(postRepository.save(post));
    }
}

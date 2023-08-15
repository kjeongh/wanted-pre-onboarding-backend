package onboarding.wanted.backend.domain.post.service;

import lombok.RequiredArgsConstructor;
import onboarding.wanted.backend.domain.auth.service.AuthService;
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
    private final AuthService authService;

    public PostCreateResponse createPost(PostCreateRequest createReqDto) {

        User loginUser = authService.getLoginUser();
        Post post = createReqDto.toEntity(loginUser);

        return PostCreateResponse.of(postRepository.save(post));
    }

}

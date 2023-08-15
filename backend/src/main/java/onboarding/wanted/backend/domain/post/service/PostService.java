package onboarding.wanted.backend.domain.post.service;

import lombok.RequiredArgsConstructor;
import onboarding.wanted.backend.domain.auth.service.AuthService;
import onboarding.wanted.backend.domain.post.dto.PostCreateRequest;
import onboarding.wanted.backend.domain.post.dto.PostCreateResponse;
import onboarding.wanted.backend.domain.post.dto.PostUpdateRequest;
import onboarding.wanted.backend.domain.post.dto.PostUpdateResponse;
import onboarding.wanted.backend.domain.post.entity.Post;
import onboarding.wanted.backend.domain.post.repository.PostRepository;
import onboarding.wanted.backend.domain.user.entity.User;
import onboarding.wanted.backend.global.error.ErrorCode;
import onboarding.wanted.backend.global.error.exception.BusinessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final AuthService authService;

    @Transactional
    public PostCreateResponse createPost(PostCreateRequest createReqDto) {

        User loginUser = authService.getLoginUser();
        Post post = createReqDto.toEntity(loginUser);

        return PostCreateResponse.of(postRepository.save(post));
    }

    @Transactional
    public PostUpdateResponse updatePost(Long postId, PostUpdateRequest updateReqDto) {

        User loginUser = authService.getLoginUser();

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(ErrorCode.POST_NOT_FOUND));

        checkPostUpdateAuth(loginUser.getId(), post.getUser().getId());

        post.updatePost(updateReqDto.getTitle(), updateReqDto.getContent());
        return PostUpdateResponse.of(postRepository.save(post));

    }

    private void checkPostUpdateAuth(Long userId, Long authorId) {
        if (!userId.equals(authorId)) {
            throw new BusinessException(ErrorCode.POST_UPDATE_FORBIDDEN);
        }
    }

}

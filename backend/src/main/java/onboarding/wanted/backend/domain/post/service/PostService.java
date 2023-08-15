package onboarding.wanted.backend.domain.post.service;

import lombok.RequiredArgsConstructor;
import onboarding.wanted.backend.domain.auth.service.AuthService;
import onboarding.wanted.backend.domain.post.dto.*;
import onboarding.wanted.backend.domain.post.entity.Post;
import onboarding.wanted.backend.domain.post.repository.PostRepository;
import onboarding.wanted.backend.domain.user.entity.User;
import onboarding.wanted.backend.global.error.ErrorCode;
import onboarding.wanted.backend.global.error.exception.BusinessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

        validatePostAccessAuth(loginUser.getId(), post.getUser().getId());
        post.updatePost(updateReqDto.getTitle(), updateReqDto.getContent());

        return PostUpdateResponse.of(postRepository.save(post));
    }

    @Transactional
    public void deletePost(Long postId) {

        User loginUser = authService.getLoginUser();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(ErrorCode.POST_NOT_FOUND));

        validatePostAccessAuth(loginUser.getId(), post.getUser().getId());

        postRepository.deleteById(postId);
    }

    @Transactional(readOnly = true)
    public PostGetResponse getPost(Long postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(ErrorCode.POST_NOT_FOUND));

        return PostGetResponse.of(post);
    }

    @Transactional(readOnly = true)
    public PaginatedPostsResponse getPostList(Pageable pageable) {

        Page<Post> posts = postRepository.findAll(pageable);

        if (posts.isEmpty()) {
            throw new BusinessException(ErrorCode.POST_NOT_FOUND);
        }

        List<PostGetResponse> postList = posts.stream().map(PostGetResponse::of).collect(Collectors.toList());

        return PaginatedPostsResponse.builder()
                .posts(postList)
                .totalPages(posts.getTotalPages())
                .totalElements(posts.getNumberOfElements())
                .total(posts.getTotalElements())
                .build();
    }

    private void validatePostAccessAuth(Long userId, Long authorId) {
        if (!userId.equals(authorId)) {
            throw new BusinessException(ErrorCode.POST_FORBIDDEN);
        }
    }

}

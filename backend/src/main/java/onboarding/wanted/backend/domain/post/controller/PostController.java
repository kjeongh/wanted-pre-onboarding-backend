package onboarding.wanted.backend.domain.post.controller;

import lombok.RequiredArgsConstructor;
import onboarding.wanted.backend.domain.post.dto.*;
import onboarding.wanted.backend.domain.post.repository.PostRepository;
import onboarding.wanted.backend.domain.post.service.PostService;
import onboarding.wanted.backend.global.response.ApiResponse;
import onboarding.wanted.backend.global.response.CustomResponse;
import onboarding.wanted.backend.global.response.ResultCode;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostRepository postRepository;

    // 게시글 생성
    @PostMapping
    public ResponseEntity<CustomResponse> createPost(@RequestBody PostCreateRequest createReqDto) {

        PostCreateResponse createResDto = postService.createPost(createReqDto);

        return ApiResponse.of(ResultCode.POST_CREATE_SUCCESS, createResDto);
    }

    // 게시글 수정
    @PutMapping("/{postId}")
    public ResponseEntity<CustomResponse> updatePost(
            @PathVariable Long postId,
            @RequestBody PostUpdateRequest updateReqDto) {

        PostUpdateResponse updateResDto = postService.updatePost(postId, updateReqDto);

        return ApiResponse.of(ResultCode.POST_UPDATE_SUCCESS, updateResDto);
    }

    // 게시글 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<CustomResponse> deletePost(
            @PathVariable Long postId) {

        postService.deletePost(postId);

        return ApiResponse.of(ResultCode.POST_DELETE_SUCCESS);
    }

    // 게시글 단일 조회
    @GetMapping("/{postId}")
    public ResponseEntity<CustomResponse> getPost(
            @PathVariable Long postId) {

        PostGetResponse getResDto = postService.getPost(postId);

        return ApiResponse.of(ResultCode.POST_GET_SUCCESS, getResDto);
    }

    // 게시글 목록 조회 (페이지네이션)
    @GetMapping
    public ResponseEntity<CustomResponse> getAllPosts(Pageable pageable) {

        PaginatedPostsResponse getListResDto = postService.getPostList(pageable);

        return ApiResponse.of(ResultCode.POST_LIST_GET_SUCCESS, getListResDto);
    }

}

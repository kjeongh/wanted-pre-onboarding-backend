package onboarding.wanted.backend.domain.post.controller;

import lombok.RequiredArgsConstructor;
import onboarding.wanted.backend.domain.post.dto.PostCreateRequest;
import onboarding.wanted.backend.domain.post.dto.PostCreateResponse;
import onboarding.wanted.backend.domain.post.repository.PostRepository;
import onboarding.wanted.backend.domain.post.service.PostService;
import onboarding.wanted.backend.global.response.ResultCode;
import onboarding.wanted.backend.global.response.ResultResponse;
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
    public ResponseEntity<ResultResponse> createPost(@RequestBody PostCreateRequest createReqDto) {

        PostCreateResponse createResDto = postService.createPost(createReqDto);

        return ResponseEntity.ok(ResultResponse.of(ResultCode.POST_CREATE_SUCCESS,createResDto));
    }

    @PutMapping
    public ResponseEntity<ResultResponse> updatePost(@RequestBody PostCreateRequest createReqDto) {

        

        return ResponseEntity.ok(ResultResponse.of(ResultCode.POST_UPDATE_SUCCESS, updateResDto));
    }

}

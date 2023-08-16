package onboarding.wanted.backend.domain.post.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaginatedPostsResponse {

    List<PostGetResponse> posts;
    private long total;
    private int totalPages;
    private int totalElements;

}

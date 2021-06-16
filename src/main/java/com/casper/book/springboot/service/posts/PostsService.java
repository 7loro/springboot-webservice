package com.casper.book.springboot.service.posts;

import com.casper.book.springboot.domain.posts.Posts;
import com.casper.book.springboot.domain.posts.PostsRepository;
import com.casper.book.springboot.web.dto.PostsResponseDto;
import com.casper.book.springboot.web.dto.PostsSaveRequestDto;
import com.casper.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id)
        );
        posts.update(requestDto.getTitle(), requestDto.getContent());
        // 신기하게 repository 에 query 날리는 것이 없음. JPA 의 영속성 컨텍스트 때문.
        return id;
    }

    @Transactional
    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id)
        );
        return new PostsResponseDto(entity);
    }
}

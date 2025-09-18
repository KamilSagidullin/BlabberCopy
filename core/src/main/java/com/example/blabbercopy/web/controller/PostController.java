package com.example.blabbercopy.web.controller;

import com.example.blabbercopy.entity.Post;
import com.example.blabbercopy.repository.specification.PostFilter;
import com.example.blabbercopy.security.AppUserDetails;
import com.example.blabbercopy.service.PostService;
import com.example.blabbercopy.web.dto.CreatePostRequest;
import com.example.blabbercopy.web.dto.PageResponse;
import com.example.blabbercopy.web.dto.PostDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    @GetMapping
    public ResponseEntity<PageResponse<PostDto>> getAllPosts(@RequestParam Integer pageSize,@RequestParam Integer pageNumber){
        return constructFromPage(postService.findAll(PageRequest.of(pageNumber,pageSize)));
    }
    @GetMapping("/filter")
    public ResponseEntity<PageResponse<PostDto>> filterPosts(PostFilter postFilter){
        return constructFromPage(postService.filter(postFilter,PageRequest.of(postFilter.getPageNumber(),postFilter.getPageSize())));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<PostDto> createPost(@RequestBody CreatePostRequest createPostRequest, @AuthenticationPrincipal UserDetails userDetails){
        var authorId = ((AppUserDetails) userDetails).getId();
        Post post = new Post(createPostRequest.getText(), createPostRequest.getTag());
        postService.createPost(post,authorId);
        return ResponseEntity.ok(new PostDto(post.getId(), post.getText(), post.getTag(), userDetails.getUsername()));

    }
    @DeleteMapping("/{postId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deletePost(@PathVariable long postId, @AuthenticationPrincipal UserDetails userDetails){
        var authorId = ((AppUserDetails) userDetails).getId();
        postService.deleteById(postId,authorId);
        return ResponseEntity.noContent().build();
    }
    private ResponseEntity<PageResponse<PostDto>> constructFromPage(Page<Post> page){
        var content = page.getContent().stream().map(post ->
                new PostDto(post.getId(),post.getText(),post.getTag(),post.getAuthor().getUsername())).toList();
        return ResponseEntity.ok(new PageResponse<>(content,page.getTotalPages()));
    }
}

package com.example.blabbercopy.service;

import com.example.blabbercopy.entity.Post;
import com.example.blabbercopy.entity.User;
import com.example.blabbercopy.event.CreatePostApplicationEvent;
import com.example.blabbercopy.exception.BlabberException;
import com.example.blabbercopy.repository.PostRepository;
import com.example.blabbercopy.repository.specification.PostSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.blabbercopy.repository.specification.PostFilter;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;
    private final ApplicationEventPublisher publisher;

    @Cacheable(value = "posts",key = "new org.springframework.cache.interceptor.SimpleKey(#pageable.pageNumber, #pageable.pageSize)")
    public Page<Post> findAll(Pageable pageable){
        log.info("find all by pageable {}", pageable);
        return postRepository.findAll(pageable);
    }

     public Page<Post> filter(PostFilter postfilter,Pageable pageable){
        log.info("filter posts by data {}", postfilter);
        return postRepository.findAll(PostSpecification.withFilter(postfilter),pageable);
     }
     @CacheEvict(value = "posts")
     @Transactional
     public Post createPost(Post post, int authorId){
        log.info("Create new Post");
        User user = userService.findById(authorId);
        post.setAuthor(user);
        Post createdPost = postRepository.save(post);
        publisher.publishEvent(new CreatePostApplicationEvent(this,createdPost.getId(),authorId,user.getUsername()));
        return createdPost;

     }
     @CacheEvict(value = "posts",allEntries = true)
     @Transactional
    public void deleteById(Long postId, int userId){
        log.info("Deleting post {}", postId);
        if (!postRepository.existsByAuthorIdAndId(userId, postId))
            throw new BlabberException("Exception while deleting post" + postId + ". There is no post");
        postRepository.deleteById(postId);
     }


}

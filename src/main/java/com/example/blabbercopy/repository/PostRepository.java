package com.example.blabbercopy.repository;

import com.example.blabbercopy.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {
    boolean existsByAuthorIdAndId(Long authorId, Long postId);
}

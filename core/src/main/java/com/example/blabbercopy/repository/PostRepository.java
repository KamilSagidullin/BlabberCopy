package com.example.blabbercopy.repository;

import com.example.blabbercopy.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PostRepository extends JpaRepository<Post,Integer>, JpaSpecificationExecutor<Post> {
    boolean existsByAuthorIdAndId(int author_id, Long id);

    void deleteById(Long id);
}

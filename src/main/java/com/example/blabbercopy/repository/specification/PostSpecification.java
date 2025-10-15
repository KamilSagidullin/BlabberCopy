package com.example.blabbercopy.repository.specification;
import com.example.blabbercopy.entity.Post;
import jakarta.persistence.criteria.Path;
import org.springframework.data.jpa.domain.Specification;

public interface PostSpecification {

    static Specification<Post> withFilter(PostFilter filer) {
        return Specification.where(isEquals(filer.getAuthorId(), "author", "id")
                .and(contains("tag", filer.getTag()))
                .and(contains("text", filer.getText())));
    }

    private static <T> Specification<Post> isEquals(T object, String... fieldPath) {
        return ((root, query, criteriaBuilder) -> {
            if (object == null) {
                return criteriaBuilder.conjunction();
            }

            Path<?> rootByPath = root;
            for (String field : fieldPath) {
                rootByPath = rootByPath.get(field);
            }

            return criteriaBuilder.equal(rootByPath, object);
        });
    }

    private static Specification<Post> contains(String fieldName, String keyword) {
        return ((root, query, criteriaBuilder) -> {
            if (keyword == null || keyword.isBlank()) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.like(root.get(fieldName), "%" + keyword + "%");
        });
    }

}

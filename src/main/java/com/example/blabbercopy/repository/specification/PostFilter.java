package com.example.blabbercopy.repository.specification;

import lombok.Data;

@Data
public class PostFilter {
    private String tag;
    private int authorId;
    private String text;
    private int pageSize = 10;
    private int pageNumber = 0;
}

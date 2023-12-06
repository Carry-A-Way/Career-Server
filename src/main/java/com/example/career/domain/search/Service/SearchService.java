package com.example.career.domain.search.Service;

import com.example.career.domain.community.Dto.response.ArticleDto;
import com.example.career.domain.community.Entity.Article;
import com.example.career.domain.search.Dto.CommunitySearchRespDto;

import java.util.List;

public interface SearchService {
    public List<ArticleDto> getArticlesByKeyWord(Long userId, String keyWord, int page, int size);
}

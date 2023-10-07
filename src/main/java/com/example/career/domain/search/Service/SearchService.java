package com.example.career.domain.search.Service;

import com.example.career.domain.community.Entity.Article;
import com.example.career.domain.search.Dto.CommunitySearchRespDto;

import java.util.List;

public interface SearchService {
    public List<CommunitySearchRespDto> getArticlesByKeyWord(String keyWord, int page, int size);
}

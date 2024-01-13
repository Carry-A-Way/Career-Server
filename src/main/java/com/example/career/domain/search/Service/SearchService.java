package com.example.career.domain.search.Service;

import com.example.career.domain.community.Dto.Brief.UserBrief;
import com.example.career.domain.community.Dto.Brief.UserBriefWithRate;
import com.example.career.domain.community.Dto.response.ArticleDto;
import com.example.career.domain.community.Entity.Article;
import com.example.career.domain.search.Dto.CommunitySearchRespDto;
import com.example.career.domain.user.Entity.TutorDetail;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SearchService {
    public List<ArticleDto> getArticlesByKeyWord(Long userId, String keyWord, int page, int size);
    public List<UserBriefWithRate> getUserByTags(String keyWord, String type, int page, int size);
}

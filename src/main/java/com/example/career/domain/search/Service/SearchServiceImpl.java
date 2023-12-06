package com.example.career.domain.search.Service;

import com.example.career.domain.community.Dto.response.ArticleDto;
import com.example.career.domain.community.Entity.Article;
import com.example.career.domain.community.Entity.Comment;
import com.example.career.domain.community.Entity.Recomment;
import com.example.career.domain.community.Repository.ArticleRepository;
import com.example.career.domain.community.Repository.CommentRepository;
import com.example.career.domain.community.Repository.RecommentRepository;
import com.example.career.domain.community.Service.ArticleService;
import com.example.career.domain.search.Dto.CommunitySearchRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService{
    private final ArticleRepository articleRepository;
    private final ArticleService articleService;
    @Override
    public List<ArticleDto> getArticlesByKeyWord(Long userId, String keyWord, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Article> articles = articleRepository.findAllBySearchKeyWord(keyWord, pageable);

        return articleService.convertToArticleDtoList(articles,userId);
    }
}

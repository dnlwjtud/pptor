package com.team2.pptor.service;

import com.team2.pptor.domain.Article;
import com.team2.pptor.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleService {

    public final ArticleRepository articleRepository;

    @Transactional
    public void write(Article article) {

        articleRepository.save(article);

    }
}

package com.team2.pptor.service;

import com.team2.pptor.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

    public final ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {

        this.articleRepository = articleRepository;

    }


}

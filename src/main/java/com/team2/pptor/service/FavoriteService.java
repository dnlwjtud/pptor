package com.team2.pptor.service;

import com.team2.pptor.domain.Article.Article;
import com.team2.pptor.domain.Favorite.Favorite;
import com.team2.pptor.domain.Member.Member;
import com.team2.pptor.repository.ArticleRepository;
import com.team2.pptor.repository.FavoriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final ArticleRepository articleRepository;

    @Transactional
    public boolean save(Member member, Long articleId){
        Article article = articleRepository.findById(articleId).orElseThrow();

        if( isFavoriteExists(member, article) ){
            favoriteRepository.save(new Favorite(member, article));
        }

        return false;
    }

    @Transactional
    private boolean isFavoriteExists(Member member, Article article) {
        return favoriteRepository.findByMemberAndArticle(member, article).isEmpty();
    }

    @Transactional
    public Long count(){
        return favoriteRepository.count();
    }

    @Transactional
    public List<Favorite> findAll(){
        return favoriteRepository.findAll();
    }

    @Transactional
    public List<Favorite> findByMember(Member member){
        return favoriteRepository.findByMember(member);
    }

}

package com.team2.pptor.repository;

import com.team2.pptor.domain.Article.Article;
import com.team2.pptor.domain.Favorite.Favorite;
import com.team2.pptor.domain.Member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    Optional <Favorite> findByMemberAndArticle(Member member, Article article);

    List<Favorite> findByMember(Member member);
}

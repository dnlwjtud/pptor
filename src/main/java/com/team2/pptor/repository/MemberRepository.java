package com.team2.pptor.repository;

import com.team2.pptor.domain.Article.Article;
import com.team2.pptor.domain.Member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByLoginId(String loginId);

    @Modifying
    @Query(value="update Member m set m.loginPw = :#{#member.loginPw} " +
            ", m.nickname = :#{#member.nickname}" +
            ", m.email = :#{#member.email}" +
            ", m.authLevel = :#{#member.authLevel} WHERE m.id = :#{#member.id}", nativeQuery=false)
    void modify(@Param("member") Member member);

    Page<Member> findByLoginIdContaining(Pageable pageable, String SearchKeyword);

    Page<Member> findByNicknameContaining(Pageable pageable, String SearchKeyword);

    Page<Member> findByEmailContaining(Pageable pageable, String SearchKeyword);

    Page<Member> findById(Long id, Pageable pageable);

    //List<Article> findArticlesByLoginId(String loginId);

}

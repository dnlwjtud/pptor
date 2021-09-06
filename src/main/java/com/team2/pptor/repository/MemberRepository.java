package com.team2.pptor.repository;

import com.team2.pptor.domain.Member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByLoginId(String loginId);

    @Modifying
    @Query(value="update Member m set m.loginPw = :#{#member.loginPw} " +
            ", m.nickname = :#{#member.nickname}" +
            ", m.email = :#{#member.email}" +
            ", m.authLevel = :#{#member.authLevel} WHERE m.id = :#{#member.id}", nativeQuery=false)
    void modify(@Param("member") Member member);

}

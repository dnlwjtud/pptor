package com.team2.pptor.repository;

import com.team2.pptor.domain.Member.Member;
import com.team2.pptor.domain.follow.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    // 팔로우 받은 사람 리스트로 리턴
    @Query("select f from Follow f where f.toMember = :toMember")
    List<Follow> findFollowsByToMember(@Param("toMember") Member toMember);

    public Follow findFollowByToMemberLoginId(String toMemberLoginId);

}

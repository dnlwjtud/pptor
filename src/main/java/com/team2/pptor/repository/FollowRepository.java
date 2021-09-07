package com.team2.pptor.repository;

import com.team2.pptor.domain.follow.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    public Follow findFollowByToMemberLoginId(String toMemberLoginId);

}

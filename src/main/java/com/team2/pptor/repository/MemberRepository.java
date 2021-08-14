package com.team2.pptor.repository;

import com.team2.pptor.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository {

    @PersistenceContext
    EntityManager em;

    public int join(Member member){
        em.persist(member);
        return member.getId();
    }

    public int delete(Member member){
        em.remove(member);
        return member.getId();
    }

    // update 고민중
//    public int update(Member member){
//
//    }

    public Member findById(int id){
        return em.find(Member.class, id);
    }
}

package com.team2.pptor.repository;

import com.team2.pptor.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class MemberRepository {

    @PersistenceContext
    EntityManager em;

    // 회원가입
    @Transactional
    public int join(Member member){
        em.persist(member);
        return member.getId();
    }

    // 회원탈퇴
    @Transactional
    public int delete(Member member){
        em.remove(member);
        return member.getId();
    }

//    public int modify(Member member){
//
//    }

    // 회원번호로 회원찾기
    @Transactional
    public Member findById(int id){
        return em.find(Member.class, id);
    }

    // 회원 전체 리스트
    @Transactional
    public List findAll(){
        return em.createQuery("SELECT m FROM Member m")
                .getResultList();
    }
}

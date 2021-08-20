package com.team2.pptor.repository;

import com.team2.pptor.domain.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class BoardRepository {

    private final EntityManager em;

    /*
    게시판 생성 메소드
     */
    public void save(Board board){
        em.persist(board);
    }

}

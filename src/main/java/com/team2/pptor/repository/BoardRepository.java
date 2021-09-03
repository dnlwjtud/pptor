package com.team2.pptor.repository;

import com.team2.pptor.domain.Board.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

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

    /*
    게시판 삭제
     */
    public int deleteById(int id){
        Board board = findById(id);
        em.remove(board);
        return board.getId();
    }

    /*
    게시판 번호로 게시판 찾기
     */
    public Board findById(int id){
        return em.find(Board.class, id);
    }

    /*
    게시판 리스트
     */
    public List<Board> findAll() {
        return em.createQuery("SELECT b FROM Board b", Board.class)
                .getResultList();
    }
}

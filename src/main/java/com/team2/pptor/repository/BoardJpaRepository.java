package com.team2.pptor.repository;

import com.team2.pptor.domain.Board.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardJpaRepository {

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
    public Long deleteById(int id){
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
        return em.createQuery("select b from Board b", Board.class)
                .getResultList();
    }

    // 게시판 수 찾기
    public Long count(){
        return em.createQuery("select count(b) from Board b", Long.class)
                .getSingleResult();
    }

    // 게시판 이름으로 찾기
    public Board findBoardByName(String name) {

        return em.createQuery("select b from Board b where b.name = :name", Board.class)
                .setParameter("name",name)
                .getSingleResult();

    }

    /*
    게시판 이름으로 삭제
     */
    public void deleteByName(String name) {
        Board board = findBoardByName(name);
        em.remove(board);
    }
}

package com.team2.pptor.repository;

import com.team2.pptor.domain.Board.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository <Board, Long> {

    void deleteByName(String name);

    Board findBoardByName(String name);

}

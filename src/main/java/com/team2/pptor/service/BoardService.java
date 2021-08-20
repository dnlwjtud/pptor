package com.team2.pptor.service;

import com.team2.pptor.domain.Board;
import com.team2.pptor.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;

    public void makeTestData() {

        Board board1 = Board.createBoard(
                "기본 템플릿"
        );

        Board board2 = Board.createBoard(
                "사용자 템플릿"
        );

        boardRepository.save(board1);
        boardRepository.save(board2);

    }

}

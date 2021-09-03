package com.team2.pptor.service;

import com.team2.pptor.domain.Board.Board;
import com.team2.pptor.domain.Board.BoardModifyForm;
import com.team2.pptor.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    /*
    게시판 생성
     */
    public void save(Board board) {
        boardRepository.save(board);
    }

    /*
    게시판 삭제
     */
    public void delete(int id) {
        boardRepository.deleteById(id);
    }

    public List<Board> list() {
        return boardRepository.findAll();
    }

    /*
    게시판 조회
     */
    public Board findById(int id){
        try {
            return boardRepository.findById(id);
        } catch (Exception e ) {
            throw new IllegalStateException("존재하지 않는 게시판입니다.");
        }
    }

    /*
    게시물 수정
     */
    public void modify(BoardModifyForm boardModifyForm) {

        Board board = boardRepository.findById(boardModifyForm.getId());

        boardModifyForm.getName();

    }

}

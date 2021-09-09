package com.team2.pptor.service;

import com.team2.pptor.domain.Board.Board;
import com.team2.pptor.domain.Board.BoardModifyForm;
import com.team2.pptor.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

    //private final BoardJpaRepository boardRepository;
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
    게시판 번호로 삭제
     */
    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    /*
    게시판 이름으로 삭제
    */
    public void deleteByName(String name) {
        boardRepository.deleteByName(name);
    }

    public List<Board> list() {
        return boardRepository.findAll();
    }

    /*
    게시판 조회
     */
    @Transactional(readOnly = true)
    public Board findById(Long id){

        Optional<Board> boardOptional = boardRepository.findById(id);

        return boardOptional.orElseThrow(() -> new NoSuchElementException("해당 게시판은 존재하지 않습니다."));

        /*
        try {
            return boardRepository.findById(id).get();
        } catch (Exception e ) {
            throw new IllegalStateException("존재하지 않는 게시판입니다.");
        }
        */

    }

    /*
    게시물 수정
     */
    public void modify(BoardModifyForm boardModifyForm) {

        Optional<Board> boardOptional = boardRepository.findById(boardModifyForm.getId());

        boardOptional.ifPresent(
                board -> board.modifyBoard(
                        boardModifyForm.getName()
                )
        );

        if(boardOptional.isEmpty()){
            throw new IllegalStateException("해당 게시판이 존재하지 않습니다.");
        }

    }

    /*
    게시판 수를 카운트하기
    */
    @Transactional(readOnly = true)
    public Long count() {
        return boardRepository.count();
    }

    /*
    게시판 이름으로 조회하기
     */
    @Transactional(readOnly = true)
    public Board findBoardByName(String name) {

        try {
            return boardRepository.findBoardByName(name);
        } catch (Exception e) {
            throw new IllegalStateException("해당 게시판은 존재하지 않습니다.");
        }
    }
}

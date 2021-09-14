package com.team2.pptor.controller;

import com.team2.pptor.domain.Board.Board;
import com.team2.pptor.domain.Board.BoardModifyForm;
import com.team2.pptor.domain.Board.BoardSaveForm;
import com.team2.pptor.domain.Member.Member;
import com.team2.pptor.service.BoardService;
import com.team2.pptor.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    private final BoardService boardService;
    private final MemberService memberService;
    
    /* 
    게시판 생성
     */
    @PostMapping("/adm/boards/add")
    public String doAdd(@Validated @ModelAttribute BoardSaveForm boardSaveForm, BindingResult bindingResult, Principal principal){

        if ( bindingResult.hasErrors() ) {
            log.info("ERRORS={}",bindingResult);
            return "redirect:/";
        }

        Member member = memberService.findByLoginId(principal.getName());

        Board board = Board.createBoard(
                boardSaveForm.getName()
        );

        boardService.save(board);

        return "redirect:/adm/manage/boards";

    }

    /*
   게시판 삭제
   */
    @DeleteMapping("/adm/boards/{name}")
    public String doDelete(@PathVariable("name") String name){

        boardService.deleteByName(name);

        return "adm/board/admList";
    }

    /*
    게시판 수정 페이지 이동
     */
//    @GetMapping("/adm/boards/{name}")
//    public String showModify(@PathVariable("name") String name, Model model){
//
//        Board board = boardService.findBoardByName(name);
//
//        BoardModifyForm boardModifyForm = new BoardModifyForm();
//
//        boardModifyForm.setName(board.getName());
//
//        model.addAttribute("boardModifyForm", boardModifyForm);
//
//        return "/adm/boards/{name}";
//
//    }
    
    /*
    게시판 수정
     */
    @PutMapping("/adm/boards/modify")
    public String doModify(@Validated @ModelAttribute BoardModifyForm boardModifyForm, BindingResult bindingResult, Principal principal){

        //Board originBoard = boardService.findBoardByName(boardModifyForm.getOriginBoardName());

        if ( bindingResult.hasErrors() ) {
            log.info("ERRORS={}",bindingResult);
            return "redirect:/";
        }

        boardService.modify(boardModifyForm);

        /*
        System.out.println("바꿀 이름 넘어왔나 : " + boardModifyForm.getName());
        System.out.println("원래 게시판 이름 넘어왔나 : " + boardModifyForm.getOriginBoardName());

         */

        return "redirect:/adm/manage/boards";
    }

    /*
    회원이 보는 게시판 리스트
     */
    @GetMapping("/boards/list")
    public String showList(Model model){
        List<Board> boards = boardService.list();

        model.addAttribute("boards", boards);
        return "adm/board/list";
    }

    /*
    관리자가 보는 게시판 리스트
     */
    @GetMapping("/adm/manage/boards")
    public String showAdmList(Model model){
        List<Board> boards = boardService.list();

        model.addAttribute("boards", boards);
        model.addAttribute("count", boardService.count());
        return "adm/board/admList";
    }
}

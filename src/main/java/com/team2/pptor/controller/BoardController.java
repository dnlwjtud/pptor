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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
    @GetMapping("usr/board/add")
    public String showAdd(Model model){

        model.addAttribute("boardSaveForm", new BoardSaveForm());

        return "usr/board/add";
    }

    @PostMapping("usr/board/add")
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

        return "redirect:/";

    }

    /*
   게시판 삭제
   */
    @GetMapping("usr/board/delete/{id}") // {id}가 @PathVariable과 연결됨
    public String doDelete(@PathVariable("id") int id){

        boardService.delete(id);

        return "redirect:/usr/board/list";
    }

    /*
    게시판 수정
     */
    @GetMapping("usr/board/modify/{id}")
    public String showModify(@PathVariable("id") int id, Model model){

        Board board = boardService.findById(id);

        BoardModifyForm boardModifyForm = new BoardModifyForm();

        boardModifyForm.setName(board.getName());

        model.addAttribute("boardModifyForm", boardModifyForm);

        return "redirect:/";

    }

    @PostMapping("usr/board/modify")
    public String doModify(@Validated @ModelAttribute BoardModifyForm boardModifyForm, BindingResult bindingResult, Principal principal){

        Board findBoard = boardService.findById(boardModifyForm.getId());

        if ( bindingResult.hasErrors() ) {
            log.info("ERRORS={}",bindingResult);
            return "usr/board/list";
        }

        boardService.modify(boardModifyForm);


        return "redirect:/";
    }

    /*
    게시판 리스트
     */
    @GetMapping("usr/board/list")
    public String showList(Model model){
        List<Board> boards = boardService.list();

        model.addAttribute("boards", boards);
        return "usr/board/list";
    }
}

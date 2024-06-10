package com.example.jpafinal.service;


import com.example.jpafinal.Repository.BoardRepository;
import com.example.jpafinal.Repository.CategoryRepository;
import com.example.jpafinal.Repository.LocationRepository;
import com.example.jpafinal.Repository.MemberRepository;
import com.example.jpafinal.dto.BoardDto;
import com.example.jpafinal.entity.Board;
import com.example.jpafinal.entity.Category;
import com.example.jpafinal.entity.Location;
import com.example.jpafinal.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.swing.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class BoardService {
    private final LocationRepository locationRepository;
    private final BoardRepository boardRepository;
    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;

    private Board createBoardFromDto(BoardDto boardDto){
        // 회원 존재 확인
        Member member = memberRepository.findByEmail(boardDto.getEmail()).orElseThrow(
                ()->new RuntimeException("해당 회원이 존재하지 않습니다."));
        //카테고리 존재 확인
        Category category = categoryRepository.findById(boardDto.getBoardId()).orElseThrow(
                ()->new RuntimeException("해당 카테고리가 존재하지 않습니다."));
        return Board.builder().title(boardDto.getTitle())
                .content(boardDto.getContent())
                .imgPath(boardDto.getImg())
                .member(member)
                .category(category)
                .build();
    }
    private Location createLocationFromDto(BoardDto boardDto,Board board){
        return Location.builder().board(board)
                .address(boardDto.getAddress())
                .latitude(boardDto.getLatitude())
                .longitude(boardDto.getLongitude())
                .build();
    }
    // 게시글 등록
    public boolean saveBoard(BoardDto boardDto){
        try {
            Board board = createBoardFromDto(boardDto);
            Board saveBoard = boardRepository.save(board);
            Location location = createLocationFromDto(boardDto, saveBoard);
            locationRepository.save(location);
            return true;
        }catch (Exception e){
            log.error("Error occurred during saveBoard: {}", e.getMessage(),e);
            return false;
        }
    }
    // 게시글 엔티티를 DTO로 변환
    private BoardDto convertEntityToDto(Board board){
        BoardDto boardDto = new BoardDto();
        boardDto.setBoardId(board.getBoardId());
        boardDto.setTitle(board.getTitle());
        boardDto.setCategoryId(board.getCategory().getCategoryId());
        boardDto.setContent(board.getContent());
        boardDto.setImg(board.getImgPath());
        boardDto.setEmail(board.getMember().getEmail());
        boardDto.setRegDate(board.getRegDate());

        Location location = board.getLocation();
        if(location != null){
            boardDto.setAddress(location.getAddress());
            boardDto.setLatitude(location.getLatitude());
            boardDto.setLongitude(location.getLongitude());
        }
        return boardDto;
    }
    //게시글 전체 조회
    public List<BoardDto> getBoardList(){
        List<Board> boards = boardRepository.findAll();
        List<BoardDto> boardDtos = new ArrayList<>();
        for(Board board : boards){
            boardDtos.add(convertEntityToDto(board));
        }
        return boardDtos;
    }
    //게시글 페이징
    public List<BoardDto> getBoardList(int page,int size){
        Pageable pageable = PageRequest.of(page,size);
        List<Board> boards = boardRepository.findAll(pageable).getContent();
        List<BoardDto> boardDtos = new ArrayList<>();
        for(Board board : boards){
            boardDtos.add(convertEntityToDto(board));
        }
        return boardDtos;
    }
    //게시글 상세 조회(id로 조회)
    public BoardDto getBoardDetail(Long id){
        Board board = boardRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("해당 게시글이 존재하지 않습니다."));
        return convertEntityToDto(board);
    }
    //게시글 수정
    public boolean modifyBoard(Long id,BoardDto boardDto){
        try{
            // 게시글 존재 확인
            Board board = boardRepository.findById(id).orElseThrow(
                    ()-> new RuntimeException("해당 게시글이 존재하지 않습니다."));
            // 회원 존재 확인
            Member member = memberRepository.findByEmail(boardDto.getEmail()).orElseThrow(
                    ()-> new RuntimeException("해당 회원이 존재하지 않습니다."));
            // 카테고리 존재 확인
            Category category = categoryRepository.findById(boardDto.getCategoryId()).orElseThrow(
                    () -> new RuntimeException("해당 카테고리가 존재하지 않습니다."));
            boardRepository.save(Board.builder().title(boardDto.getTitle())
                    .category(category)
                    .member(member)
                    .content(boardDto.getContent())
                    .imgPath(boardDto.getImg())
                    .build());
            return true;
        } catch (Exception e){
            log.info("Error occured during modifyBoard: {}",e.getMessage(),e);
            return false;
        }
    }
    // 게시글 삭제
    public boolean deleteBoard(Long id){
        try{
            Board board = boardRepository.findById(id).orElseThrow(
                    ()-> new RuntimeException("해당 게시물이 존재하지 않습니다."));
            boardRepository.delete(board);
            return true;
        }catch(Exception e){
            log.info("Error occured during deleteBoard: {}",e.getMessage(),e);
            return false;
        }
    }
    // 게시글 검색
    public List<BoardDto> searchBoard(String keyword){
        List<Board> boards = boardRepository.findByTitleContaining(keyword);
        List<BoardDto> BoardDtos = new ArrayList<>();
        for(Board board : boards){
            BoardDtos.add(convertEntityToDto(board));
        }
        return BoardDtos;
    }
}

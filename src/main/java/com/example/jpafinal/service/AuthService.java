package com.example.jpafinal.service;


import com.example.jpafinal.Repository.MemberRepository;
import com.example.jpafinal.dto.MemberDto;
import com.example.jpafinal.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthService {
    private final MemberRepository memberRepository;

    //회원 가입 여부 확인
    public boolean isMember(String email){
        return memberRepository.existsByEmail(email);
    }
    // 회원 Dto -> Entity
    private Member convertDtoToEntity(MemberDto memberDto){
        return Member.builder().email(memberDto.getEmail())
                .pwd(memberDto.getPwd())
                .image(memberDto.getImage())
                .name(memberDto.getName())
                .build();
    }
    //회원 가입
    public boolean signup(MemberDto memberDto)
    {
        try{
            Member member = convertDtoToEntity(memberDto);
            memberRepository.save(member);
            return true;
        }catch(Exception e){
            log.error("Error ocurred during Signup {}",e.getMessage(), e);
            return false;
        }
    }
    //로그인
    public boolean login(String email,String pwd){
        Optional<Member> member = memberRepository.findByEmailAndPwd(email,pwd);
        log.info("member: {}",member);
        return member.isPresent();
    }
}

package com.example.jpafinal.Repository;

import com.example.jpafinal.dto.MemberDto;
import com.example.jpafinal.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;


@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
@Slf4j
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;
    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("회원 가입 여부 테스트")
    //회원 가입 여부 확인
    public void isMember(){
        boolean isEmailExist = memberRepository.existsByEmail("can3487@naver.com");
        if(isEmailExist){
            log.info("아이디가 존재합니다.");
        }
        else{
            log.info("가입이 가능합니다.");
        }
    }
    // 회원 Dto -> Entity
    private Member convertDtoToEntity(MemberDto memberDto){
        return Member.builder().email(memberDto.getEmail())
                .pwd(memberDto.getPwd())
                .image(memberDto.getImage())
                .name(memberDto.getName())
                .build();
    }

    public MemberDto createMemberEntity(){
        MemberDto dto = new MemberDto();
        dto.setEmail("can3487@naver.com");
        dto.setPwd("1234567");
        dto.setImage("url1");
        dto.setName("이경섭");
        return dto;
    }

    @Test
    @DisplayName("회원가입 테스트")
    //회원 가입
    public void signup()
    {
        try{
            MemberDto memberDto = this.createMemberEntity();
            Member member = convertDtoToEntity(memberDto);
            memberRepository.saveAndFlush(member);
            em.clear();
        }catch(Exception e){
            log.error("Error ocurred during Signup {}",e.getMessage(), e);
        }
    }
    private MemberDto loginExist(){
        MemberDto dto = new MemberDto();
        dto.setEmail("can3487@naver.com");
        dto.setPwd("1234567");
        return dto;
    }

    @Test
    @DisplayName("로그인 테스트")
    //로그인
    public void login() {
        this.signup();
        MemberDto login_access = loginExist();
        Optional<Member> optionalMember = memberRepository.findByEmailAndPwd(login_access.getEmail(), login_access.getPwd());
        if (optionalMember.isPresent()) {
            Member user = optionalMember.get();
            log.info("로그인 성공! member: {}", user);
        } else {
            throw new NoSuchElementException("사용자를 찾을 수 없습니다.");
        }
    }
}
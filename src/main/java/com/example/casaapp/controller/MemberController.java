package com.example.casaapp.controller;


import com.example.casaapp.dto.RequestMemberDTO;
import com.example.casaapp.dto.ResponseMemberDTO;
import com.example.casaapp.service.MemberService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/member/join")
    public ResponseMemberDTO joinMember(@RequestBody @Valid RequestMemberDTO requestMemberDTO){
        return memberService.joinMember(requestMemberDTO);
    }

    @GetMapping("/member/login")
    public ResponseMemberDTO loignMember(@RequestParam String memberid){
        log.info("멤버아이디: {}", memberid);
        return memberService.loginMember(memberid);
    }

    @GetMapping("/member/viewOneMember")
    public ResponseMemberDTO viewOneMember(@RequestParam String loginid){
        log.info("멤버아이디: {}", loginid);
        return memberService.loginMember(loginid);
    }


    @PostMapping("/member/update")
    public ResponseMemberDTO updateMember(@RequestBody @Valid RequestMemberDTO requestMemberDTO){
        String memberid = requestMemberDTO.getMemberid();
        return memberService.updateMember(memberid, requestMemberDTO);
    }

}

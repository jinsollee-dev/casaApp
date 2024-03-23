package com.example.casaapp.service;

import com.example.casaapp.domain.Member;
import com.example.casaapp.dto.RequestMemberDTO;
import com.example.casaapp.dto.ResponseMemberDTO;
import com.example.casaapp.repository.MemeberRepository;
import com.example.casaapp.type.Role;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.beans.Transient;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemeberRepository memeberRepository;

    @Override
    public ResponseMemberDTO joinMember(RequestMemberDTO memberDTO) {
        Member member = Member.builder()
                .memberid(memberDTO.getMemberid())
                .password(memberDTO.getPassword())
                .username(memberDTO.getUsername())
                .email(memberDTO.getEmail())
                .phone(memberDTO.getPhone())
                .address(memberDTO.getAddress())
                .birthday(memberDTO.getBirthday())
                .Role(Role.user)
                .build();
        Member member1 = memeberRepository.save(member);
        ResponseMemberDTO responseMemberDTO = ResponseMemberDTO.fromEntity(member1);
        return responseMemberDTO;


    }

    @Override
    public ResponseMemberDTO loginMember(String memberid) {
        Member member1 = memeberRepository.findByMemberid(memberid);
        log.info("아이디 찾기 : {}", member1);
        if (member1 != null) {
            ResponseMemberDTO responseMemberDTO = ResponseMemberDTO.fromEntity(member1);
            return responseMemberDTO;
        } else {
            return null;

        }
    }

    @Transactional
    @Override
    public ResponseMemberDTO updateMember(String memberid, RequestMemberDTO requestMemberDTO) {
        Member member = memeberRepository.findByMemberid(memberid);
        member.setPassword(requestMemberDTO.getPassword());
        member.setUsername(requestMemberDTO.getUsername());
        member.setPhone(requestMemberDTO.getPhone());
        member.setPhone(requestMemberDTO.getAddress());
        member.setBirthday(requestMemberDTO.getBirthday());

//        memeberRepository.save(member);
        return ResponseMemberDTO.fromEntity(member);
    }


}
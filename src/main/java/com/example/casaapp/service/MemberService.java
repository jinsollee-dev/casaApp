package com.example.casaapp.service;

import com.example.casaapp.dto.RequestMemberDTO;
import com.example.casaapp.dto.ResponseMemberDTO;
import jakarta.validation.Valid;

public interface MemberService {

    ResponseMemberDTO joinMember(RequestMemberDTO requestMemberDTO);


    ResponseMemberDTO loginMember(String memberid);

    ResponseMemberDTO updateMember(String memberid, @Valid RequestMemberDTO requestMemberDTO);
}

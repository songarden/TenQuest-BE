package com.kns.tenquest.service;

import com.kns.tenquest.entity.Member;
import com.kns.tenquest.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MemberService {
    @Autowired
    MemberRepository memberRepository;
    public List<Member> getAllMembers(){
        // Temporarily implemented. Just for test.
        return memberRepository.findAll();
    }

    public void insertMember(UUID memberId, String userId, String userInfo, String userName, String userEmail){
        Member member = Member.builder()
                .memberId(memberId)
                .userId(userId)
                .userInfo(userInfo)
                .userName(userName)
                .userEmail(userEmail)
                .build();
        memberRepository.save(member);
    }
}
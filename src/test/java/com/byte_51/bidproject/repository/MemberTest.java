package com.byte_51.bidproject.repository;

import com.byte_51.bidproject.entity.Member;
import com.byte_51.bidproject.entity.MemberRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class MemberTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void insertDummies2() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Member clubMember = Member.builder()
                    .email("user" + i + "@zerock.org")
                    .name("사용자" + i)
                    .fromSocial(false)
                    .password(passwordEncoder.encode("1111"))
                    .build();

            clubMember.addMemberRole(MemberRole.USER);

            if (i > 90) {
                clubMember.addMemberRole(MemberRole.ADMIN);
            }

            memberRepository.save(clubMember);
        });
    }

    @Test
    public void testRead(){
        Optional<Member> result = memberRepository.findByEmail("user95@zerock.org",false);
        Member clubMember = result.get();
        System.out.println(clubMember);
    }
}

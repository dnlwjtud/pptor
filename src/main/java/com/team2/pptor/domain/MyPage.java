package com.team2.pptor.domain;

import com.sun.istack.NotNull;
import com.team2.pptor.domain.Member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class MyPage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "my_page_id") @NotNull
    private int id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "artc_dir")
    private String dir;

}

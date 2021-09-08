package com.team2.pptor.domain.follow;

import com.team2.pptor.domain.Member.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private Long id;

    // 좋아요를 하는 유저
    @JoinColumn(name = "from_member_id")
    @ManyToOne
    private Member fromMember;

    // 좋아요 받는 유저
    @JoinColumn(name = "to_member_id")
    @ManyToOne
    private Member toMember;

    public static Follow createFollow(Member fromMember, Member toMember) {

        Follow follow = new Follow();

        follow.fromMember = fromMember;
        follow.toMember = toMember;

        return follow;

    }

    @Override
    public String toString() {
        return "Follow{" +
                "id=" + id +
                ", fromMember=" + fromMember.toString() +
                ", toMember=" + toMember.toString() +
                '}';
    }
}

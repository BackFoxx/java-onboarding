package onboarding.problem7.service;

import onboarding.problem7.repository.FriendRepository;
import onboarding.problem7.vo.Member;
import onboarding.problem7.vo.Relation;

import java.util.List;
import java.util.stream.Collectors;

public class RecommendService {
    public static final int RELATION_SCORE = 10;
    public static final int VISITOR_SCORE = 1;

    private final FriendRepository friendRepository;

    public RecommendService(FriendRepository friendRepository) {
        this.friendRepository = friendRepository;
    }

    public List<String> recommendFriends(List<List<String>> members, List<String> visitors) {
        List<Relation> relations = members.stream()
                .map(Relation::of)
                .collect(Collectors.toList());

        saveAllMembers(relations, visitors);
        analyzeRelations(relations);
        analyzeVisitors(visitors);

        return friendRepository.findAllNameSortByScoreNot0DescNameAsc();
    }

    private void saveAllMembers(List<Relation> relations, List<String> visitors) {
        relations.forEach(relation -> saveMembersByRelation(relation));
        visitors.forEach(visitor -> friendRepository.save(Member.of(visitor)));
    }

    private void saveMembersByRelation(Relation relation) {
        relation.forEach(name -> {
            if (relation.contains(friendRepository.getUser())) {
                friendRepository.save(Member.ofAlreadyFriend(name));
            } else {
                friendRepository.save(Member.of(name));
            }
        });
    }

    private void analyzeRelations(List<Relation> relations) {
        relations.forEach(relation -> analyzeRelation(relation));
    }

    private void analyzeRelation(Relation relation) {
        if (friendRepository.isMemberAlreadyFriend(Member.of(relation.getMemberName()))
                || friendRepository.isMemberAlreadyFriend(Member.of(relation.getAnotherMemberName()))
        ) {
            relation.forEach(name -> friendRepository.findByName(name)
                    .ifPresent(friend -> friend.addScore(RELATION_SCORE)));
        }
    }

    private void analyzeVisitors(List<String> visitors) {
        visitors.forEach(visitor -> friendRepository.findByName(visitor)
                .ifPresent(member -> member.addScore(VISITOR_SCORE))
        );
    }
}

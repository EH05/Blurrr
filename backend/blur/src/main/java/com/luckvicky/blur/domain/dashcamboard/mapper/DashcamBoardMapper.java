package com.luckvicky.blur.domain.dashcamboard.mapper;

import com.luckvicky.blur.domain.dashcamboard.model.dto.DashcamBoardDto;
import com.luckvicky.blur.domain.dashcamboard.model.dto.DashcamBoardListDto;
import com.luckvicky.blur.domain.dashcamboard.model.dto.DashcamMentionDto;
import com.luckvicky.blur.domain.dashcamboard.model.entity.Dashcam;
import com.luckvicky.blur.domain.dashcamboard.model.entity.DashcamMention;
import com.luckvicky.blur.domain.dashcamboard.model.entity.Option;
import com.luckvicky.blur.domain.dashcamboard.model.entity.Video;
import com.luckvicky.blur.domain.dashcamboard.repository.DashcamMentionRepository;
import com.luckvicky.blur.domain.member.model.MemberDto;
import com.luckvicky.blur.domain.member.model.SimpleMemberDto;
import com.luckvicky.blur.domain.member.model.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.logging.SimpleFormatter;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DashcamBoardMapper {

    private final DashcamMentionRepository dashcamMentionRepository;

    public DashcamBoardListDto toDashcamBoardListDto(Dashcam dashcam) {
        List<String> videoUrls = dashcam.getVideos().stream()
                .map(Video::getVideoUrl)
                .collect(Collectors.toList());

        List<DashcamMentionDto> mentionedLeagues = dashcamMentionRepository.findByDashcam(dashcam).stream()
                .map(DashcamMentionDto::of)
                .collect(Collectors.toList());

        SimpleMemberDto memberDto = toSimpleMemberDto(dashcam.getMember());

        return DashcamBoardListDto.builder()
                .id(dashcam.getId())
                .title(dashcam.getTitle())
                .member(memberDto)
                .viewCount(dashcam.getViewCount())
                .commentCount(dashcam.getCommentCount())
                .likeCount(dashcam.getLikeCount())
                .createdAt(dashcam.getCreatedAt())
                .videoUrl(videoUrls)
                .mentionedLeagues(mentionedLeagues)
                .build();
    }

    public List<DashcamBoardListDto> toDashcamBoardListDtos(List<Dashcam> dashcams) {
        return dashcams.stream()
                .map(this::toDashcamBoardListDto)
                .collect(Collectors.toList());
    }

    public DashcamBoardDto toDashcamBoardDto(Dashcam dashcam) {
        List<String> videoUrls = dashcam.getVideos().stream()
                .map(Video::getVideoUrl)
                .collect(Collectors.toList());

        List<Option> sortedOptions = dashcam.getOptions().stream()
                .sorted(Comparator.comparingInt(Option::getNum))
                .collect(Collectors.toList());

        List<DashcamMentionDto> mentionedLeagues = dashcamMentionRepository.findByDashcam(dashcam).stream()
                .map(DashcamMentionDto::of)
                .collect(Collectors.toList());

        SimpleMemberDto memberDto = toSimpleMemberDto(dashcam.getMember());

        return DashcamBoardDto.builder()
                .id(dashcam.getId())
                .title(dashcam.getTitle())
                .member(memberDto)
                .viewCount(dashcam.getViewCount())
                .commentCount(dashcam.getCommentCount())
                .likeCount(dashcam.getLikeCount())
                .createdAt(dashcam.getCreatedAt())
                .videoUrl(videoUrls)
                .content(dashcam.getContent())
                .options(sortedOptions)
                .mentionedLeagues(mentionedLeagues)
                .build();
    }

    private MemberDto toMemberDto(Member member) {
        return MemberDto.of(member);
    }


    private SimpleMemberDto toSimpleMemberDto(Member member){
        return SimpleMemberDto.of(member);
    }
}
package com.luckvicky.blur.domain.dashcamboard.model.dto.response;

import com.luckvicky.blur.domain.dashcamboard.model.dto.DashcamBoardListDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@Schema(name = "블랙박스 게시글 목록 응답")
public class DashcamBoardListResponse {
    private List<DashcamBoardListDto> boards;

    public static DashcamBoardListResponse of(List<DashcamBoardListDto> boards){
        return DashcamBoardListResponse.builder()
                .boards(boards)
                .build();
    }
}

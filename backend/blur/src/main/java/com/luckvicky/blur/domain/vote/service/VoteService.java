package com.luckvicky.blur.domain.vote.service;

import com.luckvicky.blur.domain.vote.model.dto.VoteResultDto;

import java.util.UUID;

public interface VoteService {
    boolean createVote(UUID memberId, UUID boardID, UUID optionId);
    VoteResultDto getVoteResult(UUID boardID, UUID optionID);
}

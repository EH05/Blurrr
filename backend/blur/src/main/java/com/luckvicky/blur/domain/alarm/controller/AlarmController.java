package com.luckvicky.blur.domain.alarm.controller;

import static com.luckvicky.blur.global.constant.Number.ALARM_PAGE_SIZE;

import com.luckvicky.blur.domain.alarm.model.dto.AlarmDto;
import com.luckvicky.blur.domain.alarm.model.entity.AlarmType;
import com.luckvicky.blur.domain.alarm.service.AlarmFacade;
import com.luckvicky.blur.domain.alarm.service.AlarmService;
import com.luckvicky.blur.global.enums.filter.SortingCriteria;
import com.luckvicky.blur.global.jwt.model.ContextMember;
import com.luckvicky.blur.global.security.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Tag(name = "알림 API")
@RequestMapping("/v1/alarm")
@RestController
public class AlarmController {

    private final AlarmFacade alarmService;

    public AlarmController(AlarmFacade alarmService) {
        this.alarmService = alarmService;
    }

//    @GetMapping("/test")
//    public Boolean test() {
//        alarmService.sendAlarm(UUID.fromString("11ef49a0-9d0d-d86b-b2aa-87fcf861b88d"), AlarmType.ADD_COMMENT, "게시글 입니다");
//        return true;
//    }

    @GetMapping(value = "/subscribe/{memberId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(
//            @AuthUser ContextMember member
            @PathVariable(name = "memberId") UUID memberId
    ) {
        return alarmService.subscribe(memberId);
    }

    @Operation(summary = "알림 리스트 조회")
    @GetMapping("/list")
    public ResponseEntity<List<AlarmDto>> findAlarms(
            @AuthUser ContextMember member,
            @RequestParam(required = false, defaultValue = "0", value = "page") int pageNo
    ) {
        return ResponseEntity.ok(alarmService.findAlarms(member.getId(), pageNo));
    }

    @Operation(summary = "알림 읽음 처리")
    @PutMapping("/read/{id}")
    public ResponseEntity<Boolean> modifyReadStatus(
            @AuthUser ContextMember member,
            @PathVariable(name = "id") UUID alarmId) {
        return ResponseEntity.ok(alarmService.modifyReadStatus(member.getId(),alarmId));
    }
}

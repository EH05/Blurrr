package com.luckvicky.blur.domain.alarm.repository;

import com.luckvicky.blur.domain.alarm.exception.NotExistAlarmException;
import com.luckvicky.blur.domain.alarm.model.entity.Alarm;
import com.luckvicky.blur.domain.member.model.entity.Member;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, UUID> {
    default Alarm getOrThrow(UUID id) {
        return findById(id).orElseThrow(NotExistAlarmException::new);
    }

    List<Alarm> findAllByMember(Member member, Pageable pageable);
}

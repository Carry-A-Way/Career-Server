package com.example.career.domain.meeting.repository;

import com.example.career.domain.meeting.entity.ZoomMeetingObjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZoomMeetingRepository extends JpaRepository<ZoomMeetingObjectEntity, Long> {
}

package com.example.career.domain.meeting.dto;

import com.example.career.domain.meeting.entity.ZoomMeetingObjectEntity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZoomMeetingObjectDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    private String uuid;

    private String assistant_id;

    private String host_email;

    private String registration_url;

    private String topic;

    private Integer type;

    private String start_time;

    private Integer duration;

    private String schedule_for;

    private String timezone;

    private String created_at;

    private String password;

    private String agenda;

    private String start_url;

    private String join_url;

    private String h323_password;

    private Integer pmi;

    private ZoomMeetingRecurrenceDTO recurrence;

    private ZoomMeetingSettingsDTO settings;

    public ZoomMeetingObjectEntity toEntity() {
        return ZoomMeetingObjectEntity.builder()
                .id(id)
                .agenda(agenda)
                .assistant_id(assistant_id)
                .created_at(created_at)
                .duration(duration)
                .h323_password(h323_password)
                .host_email(host_email)
                .join_url(join_url)
                .password(password)
                .registration_url(registration_url)
                .schedule_for(schedule_for)
                .start_time(start_time)
                .start_url(start_url)
                .timezone(timezone)
                .topic(topic)
                .type(type)
                .build();
    }

}

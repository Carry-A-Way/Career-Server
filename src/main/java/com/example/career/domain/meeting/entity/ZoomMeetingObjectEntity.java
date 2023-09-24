package com.example.career.domain.meeting.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "meetings")
public class ZoomMeetingObjectEntity implements Serializable {

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
    @Column(columnDefinition = "MEDIUMTEXT")
    private String start_url;
    @Column(columnDefinition = "MEDIUMTEXT")
    private String join_url;

    private String h323_password;



}

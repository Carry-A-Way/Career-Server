package com.example.career.domain.consult.Entity;

import com.example.career.domain.consult.Dto.*;
import com.example.career.domain.major.Entity.Major;
import com.example.career.domain.user.Entity.StudentDetail;
import com.example.career.domain.user.Entity.TutorDetail;
import com.example.career.domain.user.Entity.User;
import com.example.career.global.time.KoreaTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "Consult")
public class Consult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @OneToOne
//    @JoinColumn(name = "review_id", referencedColumnName = "id")
    private Long reviewId;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String contentsUrl;

    private Long meetingId;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String zoomLink;

    @Column(nullable = false)
    private int status = 0;
    @Column(columnDefinition = "TEXT")
    private String reason;

    @ManyToOne
    @JoinColumn(name = "tutorId", referencedColumnName = "id")
    private User mentor;

    @ManyToOne
    @JoinColumn(name = "studentId", referencedColumnName = "id")
    private User mentee;


    @Column(columnDefinition = "TEXT")
    private String questions;

    @Column(columnDefinition = "TINYTEXT")
    private String flow;

//    @ManyToOne
//    @JoinColumn(name = "major_id", referencedColumnName = "id")
    private String major;


    private LocalDateTime studentEnter;

    private LocalDateTime studentLeft;

    private LocalDateTime tutorEnter;

    private LocalDateTime tutorLeft;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist // 데이터 생성이 이루어질때 사전 작업
    public void prePersist() {
        this.createdAt = KoreaTime.now();
        this.updatedAt = this.createdAt;
    }
    public ConsultEachRespDto toConsultEachRespDto(){
        return ConsultEachRespDto.builder()
                .id(id)
                .reviewId(reviewId)
                .contentsUrl(contentsUrl)
                .zoomLink(zoomLink)
                .reason(reason)
                .status(status)
                .mentor(mentor)
                .mentee(mentee)
                .major(major)
                .flow(flow)
                .questions(questions)
                .studentEnter(studentEnter)
                .studentLeft(studentLeft)
                .tutorEnter(tutorEnter)
                .tutorLeft(tutorLeft)
                .startTime(startTime)
                .endTime(endTime)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
    public LastUpcomingConsult toLastUpcomingConsult(){
        return LastUpcomingConsult.builder()
                .consultId(id)
                .mentor(mentor.toConsultMentorRespDto())
                .student(mentee.toConsultMenteeRespDto())
                .contentsUrl(contentsUrl)
                .zoomLink(zoomLink)
                .reason(reason)
                .status(status)
                .major(major)
                .flow(flow)
                .questions(questions)
                .studentEnter(studentEnter)
                .studentLeft(studentLeft)
                .tutorEnter(tutorEnter)
                .tutorLeft(tutorLeft)
                .startTime(startTime)
                .endTime(endTime)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
    public UpcomingConsults toUpcomingConsult(){
        return UpcomingConsults.builder()
                .consultId(id)
                .mentor(mentor.toConsultMentorRespDto())
                .student(mentee.toConsultMenteeRespDto())
                .contentsUrl(contentsUrl)
                .zoomLink(zoomLink)
                .reason(reason)
                .status(status)
                .major(major)
                .flow(flow)
                .questions(questions)
                .studentEnter(studentEnter)
                .studentLeft(studentLeft)
                .tutorEnter(tutorEnter)
                .tutorLeft(tutorLeft)
                .startTime(startTime)
                .endTime(endTime)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
    public PreviousConsult toPreviousConsult(){
        return PreviousConsult.builder()
                .consultId(id)
                .mentor(mentor.toConsultMentorRespDto())
                .student(mentee.toConsultMenteeRespDto())
                .contentsUrl(contentsUrl)
                .zoomLink(zoomLink)
                .reason(reason)
                .status(status)
                .major(major)
                .flow(flow)
                .questions(questions)
                .studentEnter(studentEnter)
                .studentLeft(studentLeft)
                .tutorEnter(tutorEnter)
                .tutorLeft(tutorLeft)
                .startTime(startTime)
                .endTime(endTime)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
    public CanceledConsultByMentor toCanceledConsultByMentor(){
        return CanceledConsultByMentor.builder()
                .consultId(id)
                .mentor(mentor.toConsultMentorRespDto())
                .student(mentee.toConsultMenteeRespDto())
                .contentsUrl(contentsUrl)
                .zoomLink(zoomLink)
                .reason(reason)
                .status(status)
                .major(major)
                .flow(flow)
                .questions(questions)
                .studentEnter(studentEnter)
                .studentLeft(studentLeft)
                .tutorEnter(tutorEnter)
                .tutorLeft(tutorLeft)
                .startTime(startTime)
                .endTime(endTime)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
    public CanceledConsultByMentee toCanceledConsultByMentee(){
        return CanceledConsultByMentee.builder()
                .consultId(id)
                .mentor(mentor.toConsultMentorRespDto())
                .student(mentee.toConsultMenteeRespDto())
                .contentsUrl(contentsUrl)
                .zoomLink(zoomLink)
                .reason(reason)
                .status(status)
                .major(major)
                .flow(flow)
                .questions(questions)
                .studentEnter(studentEnter)
                .studentLeft(studentLeft)
                .tutorEnter(tutorEnter)
                .tutorLeft(tutorLeft)
                .startTime(startTime)
                .endTime(endTime)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }

    public BriefConsultRespDto toBriefDto() {
        return BriefConsultRespDto.builder()
                .id(id)
                .zoomLink(zoomLink)
                .status(status)
                .questions(questions)
                .flow(flow)
                .major(major)
                .startTime(startTime)
                .endTime(endTime)
                .build();
    }

}

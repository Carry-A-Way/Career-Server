package com.example.career.domain.user.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "Query")
public class Query
//        implements UserDetails
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private Long consultId;

    @Column(columnDefinition = "TEXT")
    String flow;

    @Column(columnDefinition = "TEXT")
    String query1;

    @Column(columnDefinition = "TEXT")
    String query2;

    @Column(columnDefinition = "TEXT")
    String etc;
}

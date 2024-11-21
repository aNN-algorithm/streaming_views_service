package com.example.streaming.common.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "daily_views_content")
public class DailyViewsContentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content_post_id", nullable = false)
    private Long contentPostId;

    @Column(name = "date", nullable = false)
    private LocalDate date;
}

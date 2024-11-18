package com.example.streaming.contentPost.repository;

import com.example.streaming.common.entity.ContentPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentPostJpaRepository extends JpaRepository<ContentPostEntity, Long> {

}

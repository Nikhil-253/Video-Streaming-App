package com.example.iitdvideohub.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoInfoRepository extends JpaRepository<VideoInfo, Long> {
}

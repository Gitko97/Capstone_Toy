package com.test.toy_springboot.photo.repository;

import com.test.toy_springboot.photo.domain.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
}

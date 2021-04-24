package com.leggett.media.video;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface VideoRepository extends PagingAndSortingRepository<Video, Long>{
    
    Video findById(long id);

    Page<Video> findAll(Pageable imagePage);

}

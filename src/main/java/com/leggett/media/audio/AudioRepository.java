package com.leggett.media.audio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AudioRepository   extends PagingAndSortingRepository<Audio, Long>{

    Audio findById(long id);

    Page<Audio> findAll(Pageable imagePage);

}

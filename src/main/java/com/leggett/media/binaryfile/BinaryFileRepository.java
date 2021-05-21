package com.leggett.media.binaryfile;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BinaryFileRepository extends PagingAndSortingRepository<BinaryFile, Long>{
    
    BinaryFile findById(long id);

    Page<BinaryFile> findAll(Pageable filePage);

    Page<BinaryFile> findAllBydtype(Pageable filePage, String dtype);

}

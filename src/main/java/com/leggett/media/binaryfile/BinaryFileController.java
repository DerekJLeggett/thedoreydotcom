package com.leggett.media.binaryfile;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BinaryFileController {
    @Autowired
    private BinaryFileRepository binaryFileRepository;

    @GetMapping(path = "/files")
    public @ResponseBody Iterable<BinaryFile> getFiles(int page, int size, String type) {
        //, Sort.by(Sort.Direction.DESC, "dateTaken")
        Pageable filePage = PageRequest.of(page, size);
        Page<BinaryFile> binaryFilesPage = binaryFileRepository.findAllBydtype(filePage, type);
        List<BinaryFile> binaryFilesList = binaryFilesPage.getContent();
        for(BinaryFile binaryFile: binaryFilesList){
            String fileName = binaryFile.getFileName();
            binaryFile.setFileName(fileName.substring(fileName.lastIndexOf("/") + 1));
        }
        return binaryFilesList;
    }

    @GetMapping(value="getPhoto", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getPhotoById(Long id){
        return getFileById(id);
    }

    @GetMapping(value="getPDF", produces=MediaType.APPLICATION_PDF_VALUE)
    public @ResponseBody byte[] getPDFById(Long id){
        return getFileById(id);
    }

    @GetMapping(value="getVideo", produces="video/mp4")
    public @ResponseBody byte[] getVideoById(Long id){
        return getFileById(id);
    }

    @GetMapping(value="getAudio", produces="audio/mp4")
    public @ResponseBody byte[] getAudioById(Long id){
        return getFileById(id);
    }

    private byte[] getFileById(Long id) {
        Optional<BinaryFile> binaryFile = binaryFileRepository.findById(id);
        File file = new File(binaryFile.get().getFileName());
        int length = (int) (file.length());
        FileInputStream fin;
        byte[] bytecodes = null;
        DataInputStream in = null;
        try {
            fin = new FileInputStream(file);
            in = new DataInputStream(fin);
            bytecodes = new byte[length];
            in.readFully(bytecodes);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{

        }
        return bytecodes;
    }
}

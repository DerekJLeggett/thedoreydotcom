package com.leggett.media.video;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VideoController {
    @Autowired
    private VideoRepository videoRepository;

    @GetMapping(path="/video")
    public @ResponseBody Iterable<Video> getVideos() {
        return videoRepository.findAll();
    }

    @GetMapping(
        value = "/getVideo",
        produces = "video/mp4"
    )
    public @ResponseBody byte[] getVideoById(Long id) throws IOException {
        Optional<Video> video = videoRepository.findById(id);
        File file = new File(video.get().getFileName());
        int length = (int)(file.length());
        FileInputStream fin = new FileInputStream(file);
        DataInputStream in = new DataInputStream(fin);
        byte[] bytecodes = new byte[length];
        in.readFully(bytecodes);
        in.close();
        return bytecodes;
    }
}

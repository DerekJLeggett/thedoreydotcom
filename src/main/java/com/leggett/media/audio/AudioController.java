package com.leggett.media.audio;

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
public class AudioController {
    @Autowired
    private AudioRepository audioRepository;

    @GetMapping(path="/audio")
    public @ResponseBody Iterable<Audio> getSongs() {
        return audioRepository.findAll();
    }

    @GetMapping(
        value = "/getAudio",
        produces = "audio/mpeg"
    )
    public @ResponseBody byte[] getAudioById(Long id) throws IOException {
        Optional<Audio> audio = audioRepository.findById(id);
        File file = new File(audio.get().getFileName());
        int length = (int)(file.length());
        FileInputStream fin = new FileInputStream(file);
        DataInputStream in = new DataInputStream(fin);
        byte[] bytecodes = new byte[length];
        in.readFully(bytecodes);
        in.close();
        return bytecodes;
    }
}

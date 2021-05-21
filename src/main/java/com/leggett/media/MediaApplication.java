package com.leggett.media;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.leggett.media.binaryfile.Audio;
import com.leggett.media.binaryfile.BinaryFileRepository;
import com.leggett.media.binaryfile.PDF;
import com.leggett.media.binaryfile.Photo;
import com.leggett.media.binaryfile.Video;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MediaApplication {
	private static final Logger logger = LoggerFactory.getLogger(MediaApplication.class);
	public static String FILE_ROOT = "";
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
	BinaryFileRepository binaryFileRepository;

	public static void main(String[] args) throws IOException {
		FILE_ROOT = args[0];
		SpringApplication.run(MediaApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(BinaryFileRepository binaryFileRepository) {
		return (args) -> {
			File thumbnailDirectory = new File(FILE_ROOT + "/thumbnails");
			if(!thumbnailDirectory.exists()){
				thumbnailDirectory.mkdir();
			};
			Set<String> files = getFiles(FILE_ROOT);
			for (String file : files) {
				if (file.toLowerCase().endsWith(".jpg") || file.toLowerCase().endsWith(".jpeg")) {
					// BufferedImage image = ImageIO.read(new File(file));
					// image = resizeImage(image, 1080);
					// ImageIO.write(image, "jpg", thumbnailDirectory);
					Date dateTaken = null;
					binaryFileRepository.save(new Photo(file, dateTaken));
				} else if (file.toLowerCase().endsWith(".pdf")) {
					binaryFileRepository.save(new PDF(file));
				} else if (file.toLowerCase().endsWith(".mov") || file.toLowerCase().endsWith(".mp4")) {
					binaryFileRepository.save(new Video(file));
				} else if (file.toLowerCase().endsWith(".m4a")) {
					binaryFileRepository.save(new Audio(file));
				}
			}
		};
	};

	/**
	 * Retrieve all files recursivley from the specified directory
	 * 
	 * @param dir
	 * @return
	 * @throws IOException
	 */
	public static Set<String> getFiles(String dir) throws IOException {
		Set<String> fileList = new HashSet<>();
		Files.walkFileTree(Paths.get(dir), new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				if (!Files.isDirectory(file)) {
					fileList.add(file.toAbsolutePath().toString());
				}
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFileFailed(Path file, IOException e) throws IOException {
				System.err.printf("Visiting failed for %s\n", file);

				return FileVisitResult.SKIP_SUBTREE;
			}

			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
				System.out.printf("About to visit directory %s\n", dir);

				return FileVisitResult.CONTINUE;
			}
		});
		return fileList;
	}

	/**
	 * Resize an image to the specified width maintaining aspect ratio
	 * 
	 * @param originalImage
	 * @param targetWidth
	 * @return BufferedImage
	 * @throws Exception
	 */
	BufferedImage resizeImage(BufferedImage originalImage, int targetWidth) {
		BufferedImage image = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB);
		try {
			image = Scalr.resize(originalImage, targetWidth);

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return image;
	}
}

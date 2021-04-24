package com.leggett.media;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.leggett.media.audio.AudioRepository;
import com.leggett.media.photo.Photo;
import com.leggett.media.photo.PhotoRepository;
import com.leggett.media.video.VideoRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.tiff.TiffField;
import org.apache.commons.imaging.formats.tiff.constants.ExifTagConstants;

@SpringBootApplication
public class MediaApplication {
	private static final Logger log = LoggerFactory.getLogger(MediaApplication.class);
	public static String PHOTO_ROOT = "./thumbnails";
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
	PhotoRepository photoRepository;
	AudioRepository audioRepository;
	VideoRepository videoRepository;

	public static void main(String[] args) throws IOException {
		SpringApplication.run(MediaApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(PhotoRepository photoRepository, AudioRepository audioRepository,
			VideoRepository videoRepository) {
		return (args) -> {
			Set<String> photos = getFiles(PHOTO_ROOT);
			for (String photo : photos) {
				if (photo.toLowerCase().endsWith(".jpg")) {
					Date dateTaken = null;
					try {
						// Get the original date taken
						File file = new File(photo);
						final ImageMetadata metadata = Imaging.getMetadata(file);
						if (metadata instanceof JpegImageMetadata) {
							final JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
							final TiffField field = jpegMetadata
									.findEXIFValueWithExactMatch(ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL);
							dateTaken = formatter.parse(field.getValueDescription().replace("'", ""));
						}
						photoRepository.save(new Photo(photo, dateTaken));
					} catch (Exception e) {
						log.error(e.getMessage());
					}
				}
			}

			// // Generate Thumbnails for all images
			// for (Photo photo : photoRepository.findAll()) {
			// 	Path path = Paths.get(photo.getFileName());
			// 	String fullImageFileName = path.getFileName().toString();
			// 	File thumbnailPath = new File(thumbnailFolder + "/" + fullImageFileName);
			// 	if (!thumbnailPath.exists() && !photo.getFileName().toLowerCase().contains("thumbnails")) {
			// 		try {
			// 			BufferedImage image = ImageIO.read(new File(photo.getFileName()));
			// 			image = resizeImage(image, 720);
			// 			ImageIO.write(image, "jpg", thumbnailPath);
			// 		} catch (IOException e) {
			// 			log.error(e.getMessage());
			// 		}
			// 		log.info(photo.toString());
			// 	}
			// }
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

	// /**
	//  * Resize an image to the specified width maintaining aspect ratio
	//  * 
	//  * @param originalImage
	//  * @param targetWidth
	//  * @return BufferedImage
	//  * @throws Exception
	//  */
	// BufferedImage resizeImage(BufferedImage originalImage, int targetWidth) {
	// 	BufferedImage image = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB);
	// 	try {
	// 		image = Scalr.resize(originalImage, targetWidth);

	// 	} catch (Exception e) {
	// 		log.error(e.getMessage());
	// 	}
	// 	return image;
	// }

}

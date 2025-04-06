package com.intern.backendettaba.controllers;

import com.intern.backendettaba.entities.Image;
import com.intern.backendettaba.entities.User;
import com.intern.backendettaba.services.ImageService;
import com.intern.backendettaba.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    private final UserService userService;

    @GetMapping("/image")
    public ResponseEntity<List<Image>> getImageAll() {
        List<Image> images =  imageService.findAll();
        return new ResponseEntity<>(images, HttpStatus.OK);
    }

    @PostMapping("/image/{userId}")
    public ResponseEntity<Image> uploadImage(@PathVariable(name = "userId") Long userId,
                                             @RequestParam("imageFile") MultipartFile file) throws IOException {

        System.out.println("Original Image Byte Size - " + file.getBytes().length);
        //User user=userService.getUserById(userId).getBody();
        Image img = new Image(null,file.getOriginalFilename(), file.getContentType(),
                compressBytes(file.getBytes()));
        Image im = imageService.addImage(img);
        return new ResponseEntity<>(im,HttpStatus.OK);
    }

    @DeleteMapping("/image/{id}")
    public ResponseEntity<?> deleteImage(@PathVariable(name = "id") Long id){
        imageService.deleteImageById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = { "/image/{id}" })
    public Image getImage(@PathVariable("id") Long id) {

        final Optional<Image> retrievedImage = imageService.getById(id);
        Image img = new Image() ;
        if (retrievedImage.isPresent()) {
            img = new Image(null,retrievedImage.get().getName(), retrievedImage.get().getType(),
                    decompressBytes(retrievedImage.get().getPicByte()));
        }
        return img;
    }

    /*@GetMapping(path = { "user/{id}/image" })
    public Image getImageByUserId(@PathVariable("id") Long id) {

        final Optional<Image> retrievedImage = imageService.getByUserId(id);
        Image img = new Image() ;
        if (retrievedImage.isPresent()) {
            img = new Image(null,retrievedImage.get().getName(), retrievedImage.get().getType(),
                    decompressBytes(retrievedImage.get().getPicByte()));
        }
        return img;
    }*/

    // compress the image bytes before storing it in the database
    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

        return outputStream.toByteArray();
    }

    // uncompress the image bytes before returning it to the angular application
    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException | DataFormatException ioe) {
            ioe.printStackTrace();
        }
        return outputStream.toByteArray();
    }
}

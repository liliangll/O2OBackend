package cn.com.efuture.o2o.backend.api.image;

import cn.com.efuture.o2o.backend.mybatis.entity.ImageFile;
import cn.com.efuture.o2o.backend.mybatis.mapper.ImageMapper;
import cn.com.efuture.o2o.backend.system.JsonResponse;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/image")
public class ImageController {
    protected org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ImageMapper imageMapper;

    private final ImageProcessor imageProcessor;

    public ImageController(ImageMapper imageMapper, ImageProcessor imageProcessor) {
        this.imageMapper = imageMapper;
        this.imageProcessor = imageProcessor;
    }

    @Value("${FILE_URL}")
    private String URL;

    @PostMapping("/upload")
    @SuppressWarnings("unchecked")
    public JsonResponse<List<ImageFile>> saveImage(MultipartHttpServletRequest request) {
        try {
            List<MultipartFile> multipartFiles = request.getFiles("file");
            if (multipartFiles.size() == 0) {
                throw new FileNotFoundException("file not exists");
            }
            MultipartFile image = multipartFiles.get(0);

            String itemCode = request.getParameter("itemCode");

            byte[] byteNew640 = imageProcessor.reSize(image.getBytes(), ".jpg", 640, 640);
            byte[] byteNew450 = imageProcessor.reSize(image.getBytes(), ".jpg", 640, 450);

            ImageFile f640 = new ImageFile(itemCode, "640X640", byteNew640);
            ImageFile f450 = new ImageFile(itemCode, "640X450", byteNew450);

            this.imageMapper.save(f640);
            this.imageMapper.save(f450);
            f640.setImage(null);
            f450.setImage(null);

            String url = URL + "/image/read/";

            f640.setUrl(url + f640.getId());
            f450.setUrl(url + f450.getId());

            List<ImageFile> list = new ArrayList<>();
            list.add(f640);
            list.add(f450);

            return JsonResponse.ok(list);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<?> readImage(@PathVariable String id) {
        try {
            ImageFile img = this.imageMapper.findOne(id);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_JPEG).body(img.getImage());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(JsonResponse.notOk(JsonResponse.ERR, e.getMessage()));
        }
    }
    
    @PostMapping("/rtbImageUpload")
    @SuppressWarnings("unchecked")
    public JsonResponse<List<ImageFile>> rtbImageUpload(MultipartHttpServletRequest request) {
        try {
            List<MultipartFile> multipartFiles = request.getFiles("file");
            if (multipartFiles.size() == 0) {
                throw new FileNotFoundException("file not exists");
            }
            MultipartFile image = multipartFiles.get(0);
            byte[] byteImage = image.getBytes();
            ImageFile imageFile = new ImageFile(null, null, byteImage);
            this.imageMapper.save(imageFile);
            imageFile.setImage(null);
            String url = URL + "/image/read/";
            imageFile.setUrl(url + imageFile.getId());
            List<ImageFile> list = new ArrayList<>();
            list.add(imageFile);
            return JsonResponse.ok(list);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return JsonResponse.notOk(JsonResponse.ERR, e.getMessage());
        }
    }

}

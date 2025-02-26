package kr.or.ddit.commons.controller;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ImageController {

	@GetMapping("/images/{folderName}/{imageId}")
	@ResponseBody
	public ResponseEntity<Resource> getImage(
			  @PathVariable String imageId
			, @PathVariable String folderName
	) {
	    try {
	    	String url = String.format("\\\\192.168.146.56\\Users\\PC_07\\Desktop\\stackUp\\images\\%s", folderName);
	        Path filePath = Paths.get(url, imageId);
	        Resource resource = new FileSystemResource(filePath);
	        
	        // 파일 존재 여부 확인
	        if (!resource.exists()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	        }
	        
	        return ResponseEntity.ok()
	                .contentType(MediaType.IMAGE_JPEG) // 이미지 타입 설정
	                .body(resource);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
	}

}

package kr.or.ddit.commons.controller;



import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.file.service.BaseService;
import kr.or.ddit.file.service.BaseService;
import kr.or.ddit.vo.File_DetailVO;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/{folderName}/{boardNo}/file")
@Slf4j
public class DownloadAndDeleteAtchController {

	@Autowired
	private ApplicationContext context;
	
	private BaseService getService(String folderName) {
        String beanName;
        switch (folderName) {
            case "board":
                beanName = "boardService";
                break;
            case "help":
                beanName = "helpService";
                break;
            case "freelancer":
                beanName = "freelancerService";
                break;
            case "MemberImage":
            	beanName = "resumeService";
            	break;
            case "resource":
            	beanName = "adminResourceService";
            	break;
            case "adminBoard":
            	beanName = "adminBoardService";
            	break;
            case "adminNotice":
            	beanName = "adminHelpService";
            	break;
            case "adminTest":
            	beanName = "adminTestService";
            	break;
            case "apply":
            	beanName = "applyService";
            	break;
            case "compTest":
            	beanName = "compTestService";
            	break;
            default:
                throw new IllegalArgumentException("Invalid folderName: " + folderName);
        }

        return (BaseService) context.getBean(beanName);
    }
	

	@GetMapping("{atchFileNo}/{fileSn}")
	public ResponseEntity<Resource> download(File_DetailVO target, @PathVariable String folderName) throws IOException {
		log.info(target.toString());
		BaseService service = getService(folderName);
		File_DetailVO atch = service.download((int) target.getAtchFileNo(), target.getFileSn());

		Resource savedFile = atch.getSavedFile();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentLength(atch.getFileSize());
		ContentDisposition disposition = ContentDisposition.attachment()
				.filename(atch.getOrignlFileNm(), Charset.forName("UTF-8")).build();
		headers.setContentDisposition(disposition);
		return ResponseEntity.ok().headers(headers).body(savedFile);
	}

	@DeleteMapping("{atchFileNo}/{fileSn}")
	@ResponseBody
	public Map<String, Object> deleteAttatch(@PathVariable int atchFileNo, @PathVariable int fileSn, @PathVariable String folderName) {
		BaseService service = getService(folderName);
		service.removeFile(atchFileNo, fileSn);
		return Collections.singletonMap("success", true);
	}

}

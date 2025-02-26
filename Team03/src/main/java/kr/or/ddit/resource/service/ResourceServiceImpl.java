package kr.or.ddit.resource.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import kr.or.ddit.commons.exception.PKNotFoundException;
import kr.or.ddit.file.service.FileService;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.paging.SimpleCondition;
import kr.or.ddit.resource.dao.ResourceMapper;
import kr.or.ddit.vo.FileVO;
import kr.or.ddit.vo.File_DetailVO;
import kr.or.ddit.vo.ResourceVO;
import lombok.RequiredArgsConstructor;

@Service("resourceService")
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {
	
	private final ResourceMapper mapper;
	private final FileService fileService;
	
	@Value("#{dirInfo.saveDirResource}")
	private Resource saveFolderRes;
	private File saveFolder;
	
	@PostConstruct
	public void init() throws IOException {
		this.saveFolder = saveFolderRes.getFile();
		if(!this.saveFolder.exists()) {
			this.saveFolder.mkdirs();
		}
	}
	
	@Override
	public ResourceVO readResource(int resourceNo) {
		ResourceVO resource = mapper.selectResource(resourceNo);
		if(resource == null)
			throw new PKNotFoundException(String.format("%s  자료가 없음", resourceNo));
		return resource;
	}

	@Override
	public List<ResourceVO> readResourceList(PaginationInfo paging) {
		if(paging!=null) {
			int totalRecord = mapper.selectTotalRecord(paging);
			paging.setTotalRecord(totalRecord);
		}
		return mapper.selectResourceList(paging);
	}
	
	private Integer mergeSavedDetailsAndNewDetails(FileVO savedAtchFile, FileVO newAtchFile) {
		FileVO mergeAtchFile = new FileVO();
		List<File_DetailVO> fileDetails = Stream.concat(
				Optional.ofNullable(savedAtchFile)
				.filter(saf->! CollectionUtils.isEmpty(saf.getFileDetails()))
					.map(saf->saf.getFileDetails().stream())
					.orElse(Stream.empty())
			, Optional.ofNullable(newAtchFile)
					.filter(naf->! CollectionUtils.isEmpty(naf.getFileDetails()))
					.map(naf->naf.getFileDetails().stream())
					.orElse(Stream.empty())
		).collect(Collectors.toList());	
		
			mergeAtchFile.setFileDetails(fileDetails);
		
		if( ! mergeAtchFile.getFileDetails().isEmpty() ) {
			fileService.createFile(mergeAtchFile, saveFolder);
		}
		
		if (savedAtchFile != null && savedAtchFile.getFileDetails() != null) {
			// 기존 첨부파일 그룹은 비활성화
			fileService.disableFile(savedAtchFile.getAtchFileNo());
		}
		
		return mergeAtchFile.getAtchFileNo();
	}
	
	
	@Override
	public File_DetailVO download(int atchFileNo, int fileSn) {
		return Optional.ofNullable(fileService.readFileDetail(atchFileNo, fileSn, saveFolder))
				.filter(fd -> fd.getSavedFile().exists())
				.orElseThrow(() -> new RuntimeException(String.format("[%d, %d]해당 파일이 없음.", atchFileNo, fileSn)));
	}

	@Override
	public int getTotalCount(PaginationInfo paging) {
		return mapper.getTotalCount(paging);
	}
	
}

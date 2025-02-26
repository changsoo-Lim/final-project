package kr.or.ddit.prod.product.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.file.service.BaseService;
import kr.or.ddit.file.service.FileService;
import kr.or.ddit.prod.product.dao.ProdMapper;
import kr.or.ddit.vo.FileVO;
import kr.or.ddit.vo.File_DetailVO;
import kr.or.ddit.vo.ProductVO;
import lombok.RequiredArgsConstructor;

@Service("ProdService")
@RequiredArgsConstructor
public class ProdServiceImpl implements ProdService,BaseService {
	
	@Inject
	ProdMapper mapper;
	
	private final FileService fileService;
	
	@Value("#{dirInfo.saveDirProdImage}")
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
	public List<ProductVO> readProdList() {
		return mapper.selectProdList();
	}

	@Override
	public ProductVO readProd(String productCd) {
		return mapper.selectProd(productCd);
	}

	@Override
	public ServiceResult createtProd(ProductVO productVO) {
		Integer atchFileNo = Optional.ofNullable(productVO.getFile())
				.filter(af->! CollectionUtils.isEmpty(af.getFileDetails()))
				.map(af->{
					fileService.createFile(af, saveFolder);
					return af.getAtchFileNo();
				}).orElse(null);
		productVO.setAtchFileNo(atchFileNo);
		if(mapper.insertProd(productVO) > 0) {
			return ServiceResult.OK;
		}else {
			return ServiceResult.FAIL;
		}
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
	public ServiceResult modifiyProd(ProductVO productVO) {
		ProductVO saved = readProd(productVO.getProductCd());
		
		Integer atchFileNo = Optional.ofNullable(productVO.getFile())
									 .filter(af -> af.getFileDetails() != null)
									 .map(af ->mergeSavedDetailsAndNewDetails(saved.getFile(), af))
									 .orElse(null);
		
		productVO.setAtchFileNo(atchFileNo);
		
		if(mapper.updateProd(productVO) > 0) {
			return ServiceResult.OK;
		}else {
			return ServiceResult.FAIL;
		}
	}

	@Override
	public ServiceResult removeProd(String productCd) {
		if(mapper.deleteProd(productCd) > 0) {
			return ServiceResult.OK;
		}else {
			return ServiceResult.FAIL;
		}
	}
	
	@Override
	public File_DetailVO download(int atchFileNo, int fileSn) {
		return Optional.ofNullable(fileService.readFileDetail(atchFileNo, fileSn, saveFolder))
				.filter(fd -> fd.getSavedFile().exists())
				.orElseThrow(() -> new RuntimeException(String.format("[%d, %d]해당 파일이 없음.", atchFileNo, fileSn)));
	}
	@Override
	public void removeFile(int atchFileNo, int fileSn) {
		fileService.removeFileDetail(atchFileNo, fileSn, saveFolder);
	}
}


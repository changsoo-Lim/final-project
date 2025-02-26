package kr.or.ddit.freelancer.freelancer.service;

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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.file.service.BaseService;
import kr.or.ddit.file.service.FileService;
import kr.or.ddit.freelancer.freelancer.dao.FreelancerMapper;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.vo.CodeVO;
import kr.or.ddit.vo.FileVO;
import kr.or.ddit.vo.File_DetailVO;
import kr.or.ddit.vo.FreelancerVO;
import kr.or.ddit.vo.FreeskillsVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("freelancerService")
@RequiredArgsConstructor
public class FreelancerServiceImpl implements FreelancerService, BaseService{
	
	private final FreelancerMapper mapper;
	private final FileService fileService;
// DirectoryInfo 설정 및 properties 파일 빈 등록(context-common.xml)
	@Value("#{dirInfo.saveDirFreelancer}")
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
	public FreelancerVO readFreelancer(String memId) {
		FreelancerVO freelancer = mapper.selectFreelancer(memId);
		return freelancer;
	}

	@Override
	public List<FreelancerVO> readFreelancerList(PaginationInfo<FreelancerVO> paging) {
		paging.setTotalRecord(mapper.selectTotalRecord(paging));
		List<FreelancerVO> freelancerList = mapper.selectFreelancerList(paging);
		return freelancerList;
	}

	@Override
	@Transactional
	public ServiceResult createFreelancer(final FreelancerVO freelancer) {
		ServiceResult result = null;
		Integer atchFileNo = Optional.ofNullable(freelancer.getAtchFile())
				.filter(af->! CollectionUtils.isEmpty(af.getFileDetails()))
				.map(af -> {
					fileService.createFile(af, saveFolder);
					return af.getAtchFileNo();
				}).orElse(null);
		freelancer.setAtchFileNo(atchFileNo);
		
		// 회원 프리랜서 상태 수정 후 프리랜서 정보 등록
		if(mapper.updateMemberToFreelancer(freelancer.getMemId()) > 0
			&& mapper.insertFreelancer(freelancer) > 0
		) {
			String memId = freelancer.getMemId();
			List<FreeskillsVO> freeskills = freelancer.getFreeskills();
			if(freeskills != null && freeskills.size() > 0) {
				for(FreeskillsVO freeskill : freeskills) {
					freeskill.setMemId(memId);
					
					mapper.insertFreeskills(freeskill);
				}
			}
			result = ServiceResult.OK;
		}else {
			result = ServiceResult.FAIL;
		}
		return result;
	}

	@Override
	@Transactional
	public ServiceResult modifyFreelancer(FreelancerVO freelancer) {
		ServiceResult result = null;
		FreelancerVO saved = mapper.selectFreelancer(freelancer.getMemId());
		Integer newAtchFileNo = Optional.ofNullable(freelancer.getAtchFile())
				.filter(af -> af.getFileDetails() != null)
				.map(af ->mergeSavedDetailsAndNewDetails(saved.getAtchFile(), af))
				.orElse(null);

		freelancer.setAtchFileNo(newAtchFileNo);
		
		String memId = freelancer.getMemId();
		if(mapper.updateFreelancer(freelancer) > 0) {
			List<FreeskillsVO> freeskills = freelancer.getFreeskills();
			if(freeskills != null && freeskills.size() > 0) {
				mapper.deleteFreeskills(memId);
				for(FreeskillsVO freeskill : freeskills) {
					freeskill.setMemId(memId);
					mapper.insertFreeskills(freeskill);
				}
			}
			result = ServiceResult.OK;
		}else {
			result = ServiceResult.FAIL;
		}
		return result;
	}

	@Override
	public ServiceResult modifyMemberToFreelancer(String memId) {
		ServiceResult result = null;
		if(mapper.updateMemberToFreelancer(memId) > 0) {
			result = ServiceResult.OK;
		}else {
			result = ServiceResult.FAIL;
		}
		return result;
	}

	@Override
	public ServiceResult removeFreelancer(String memId) {
		ServiceResult result = null;
		if(mapper.deleteFreelancer(memId) > 0) {
			result = ServiceResult.OK;
		}else {
			result = ServiceResult.FAIL;
		}
		return result;
	
	}
	
	/**
	 * 기존의 첨부파일 그룹이 있는 경우, 신규 파일과 기존 파일 그룹을 병합해 저장함.
	 * 
	 * @param atchFileId
	 */
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
	public List<CodeVO> selectCodeList() {
		List<CodeVO> codeList = mapper.selectCodeList();
		return codeList;
	}

	@Override
	public void removeFile(int atchFileNo, int fileSn) {
		fileService.removeFileDetail(atchFileNo, fileSn, saveFolder);
	}
	
	@Override
	public File_DetailVO download(int atchFileNo, int fileSn) {
		return Optional.ofNullable(fileService.readFileDetail(atchFileNo, fileSn, saveFolder))
						.filter(fd -> fd.getSavedFile().exists())
						.orElseThrow(() -> new RuntimeException(String.format("[%d, %d]해당 파일이 없음.", atchFileNo, fileSn)));
	}
	
}

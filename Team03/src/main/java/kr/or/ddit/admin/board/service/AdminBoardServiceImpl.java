package kr.or.ddit.admin.board.service;

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

import kr.or.ddit.admin.board.dao.AdminBoardMapper;
import kr.or.ddit.admin.board.exception.AdminBoardException;
import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.file.service.BaseService;
import kr.or.ddit.file.service.FileService;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.paging.SimpleCondition;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.FileVO;
import kr.or.ddit.vo.File_DetailVO;
import lombok.RequiredArgsConstructor;

@Service("adminBoardService")
@RequiredArgsConstructor
public class AdminBoardServiceImpl implements AdminBoardService, BaseService {

	private final AdminBoardMapper mapper;
	private final FileService fileService;
	
	@Value("#{dirInfo.saveDirAdminBoard}")
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
	public ServiceResult createAdminBoard(final BoardVO board) {
		Integer atchFileNo = Optional.ofNullable(board.getFile())
				.filter(af->! CollectionUtils.isEmpty(af.getFileDetails()))
				.map(af->{
					fileService.createFile(af, saveFolder);
					return af.getAtchFileNo();
				}).orElse(null);
		board.setAtchFileNo(atchFileNo);
		
		if( mapper.insertAdminBoard(board) > 0) {
			return ServiceResult.OK; 
		}else {
			return	ServiceResult.FAIL;
		}
	}

	@Override
	public BoardVO readAdminBoard(int boardNo) {
		BoardVO board = mapper.selectAdminBoard(boardNo);
		if(board == null)
			throw new AdminBoardException(String.format("%d 번 글이 없음", boardNo));
		return board;
	}

	@Override
	public List<BoardVO> readAdminBoardList(PaginationInfo paging) {
		if(paging!=null) {
			int totalRecord = mapper.selectTotalRecord(paging);
			paging.setTotalRecord(totalRecord);
		}
		return mapper.selectAdminBoardList(paging);
		
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
	public ServiceResult modifyAdminBoard(BoardVO board) {
		Integer atchFileNo = Optional.ofNullable(board.getFile())
				 .filter(af -> af.getFileDetails() != null)
				 .map(af ->mergeSavedDetailsAndNewDetails(board.getFile(), af))
				 .orElse(null);

			board.setAtchFileNo(atchFileNo);
		
		if( mapper.updateAdminBoard(board) > 0) {
					
					return ServiceResult.OK; 
				}else {
					return	ServiceResult.FAIL;
				}

	}

	@Override
	public ServiceResult removeAdminBoard(int boardNo) {
		if( mapper.deleteAdminBoard(boardNo) > 0) {
					
					return ServiceResult.OK; 
				}else {
					return	ServiceResult.FAIL;
				}
			}

	

	@Override
	public File_DetailVO download(int atchFileNo, int fileSn) {
		return Optional.ofNullable(fileService.readFileDetail(atchFileNo, fileSn, saveFolder))
				.filter(fd -> fd.getSavedFile().exists())
				.orElseThrow(() -> new AdminBoardException(String.format("[%d, %d]해당 파일이 없음.", atchFileNo, fileSn)));
	}

	@Override
	public void removeFile(int atchFileNo, int fileSn) {
		fileService.removeFileDetail(atchFileNo, fileSn, saveFolder);

	}
	
	@Override
	public ServiceResult removeAdminBoards(List<Integer> boardNo) {
		int count = mapper.deleteAdminBoards(boardNo);
		return count > 0 ? ServiceResult.OK : ServiceResult.FAIL;
	}
	
	public int getAdminTotalCount(PaginationInfo paging) {
		return mapper.getAdminTotalCount(paging);
	}
}

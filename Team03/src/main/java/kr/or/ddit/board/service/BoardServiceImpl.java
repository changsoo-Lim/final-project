package kr.or.ddit.board.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import kr.or.ddit.board.dao.BoardMapper;
import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.commons.exception.PKNotFoundException;
import kr.or.ddit.file.service.BaseService;
import kr.or.ddit.file.service.FileService;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.paging.SimpleCondition;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.FileVO;
import kr.or.ddit.vo.File_DetailVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("boardService")
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService, BaseService {
	
	private final BoardMapper dao;
	private final FileService fileService;
	
	@Value("#{dirInfo.saveDirBoard}")
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
	public ServiceResult createBoard(BoardVO board) {
		
		Integer atchFileNo = Optional.ofNullable(board.getFile())
				.filter(af->! CollectionUtils.isEmpty(af.getFileDetails()))
				.map(af->{
					fileService.createFile(af, saveFolder);
					return af.getAtchFileNo();
				}).orElse(null);
		board.setAtchFileNo(atchFileNo);
		
		if( dao.insertBoard(board) > 0) {
			return ServiceResult.OK; 
		}else {
			return	ServiceResult.FAIL;
		}
	}

	@Override
	public BoardVO readBoard(int boardNo) {
		BoardVO board = dao.selectBoard(boardNo);
		if(board == null)
			throw new PKNotFoundException(String.format("%s  글이 없음", boardNo));
		return board;
	}

	@Override
	public List<BoardVO> readBoardList(PaginationInfo paging) {
		if(paging!=null) {
			int totalRecord = dao.selectTotalRecord(paging);
			paging.setTotalRecord(totalRecord);
		}
		return dao.selectBoardList(paging);
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
	public ServiceResult modifyBoard(BoardVO board) {
		BoardVO saved = readBoard(board.getBoardNo());
		
		Integer atchFileNo = Optional.ofNullable(board.getFile())
									 .filter(af -> af.getFileDetails() != null)
									 .map(af ->mergeSavedDetailsAndNewDetails(saved.getFile(), af))
									 .orElse(null);
		
			board.setAtchFileNo(atchFileNo);
		
		if( dao.updateBoard(board) > 0) {
			
			return ServiceResult.OK; 
		}else {
			return	ServiceResult.FAIL;
		}
	}

	@Override
	public ServiceResult removeBoard(int boardNo) {
		if( dao.deleteBoard(boardNo) > 0) {
			
			return ServiceResult.OK; 
		}else {
			return	ServiceResult.FAIL;
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

	@Override
	public int getTotalCount(PaginationInfo paging) {
		return dao.getTotalCount(paging);
	}
	
	
}

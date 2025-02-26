package kr.or.ddit.notice.service;

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

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.commons.exception.PKNotFoundException;
import kr.or.ddit.file.service.BaseService;
import kr.or.ddit.file.service.FileService;
import kr.or.ddit.notice.dao.HelpMapper;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.vo.FileVO;
import kr.or.ddit.vo.File_DetailVO;
import kr.or.ddit.vo.NoticeVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service("helpService")
@RequiredArgsConstructor
public class HelpServiceImpl implements HelpService, BaseService {

	private final HelpMapper dao;
	private final FileService fileService;
	
	@Value("#{dirInfo.saveDirHelp}")
	private Resource saveFolderRes;
	private File saveFolder;

	@PostConstruct
	public void init() throws IOException {
		this.saveFolder = saveFolderRes.getFile();
		if (!this.saveFolder.exists()) {
			this.saveFolder.mkdirs();
		}
	}

	@Override
	public List<NoticeVO> readNoticeList(PaginationInfo paging) {
		if (paging != null) {
			int totalRecord = dao.selectTotalRecord(paging);
			paging.setTotalRecord(totalRecord);
		}
		return dao.selectNoticeList(paging);
	}

	@Override
	public NoticeVO readNotice(int noticeNo) throws PKNotFoundException {
		return Optional.ofNullable(dao.selectNotice(noticeNo))
				.orElseThrow(() -> new PKNotFoundException(String.format("%s 공지글 없음.", noticeNo)));
	}

	@Override
    public List<NoticeVO> readFaqList(String type, PaginationInfo paging) {
        if (paging != null) {
            int totalRecord = dao.selectTotalFaqRecord(type, paging);
            paging.setTotalRecord(totalRecord);
        }
        return dao.selectFaqListByType(type, paging);
    }
	
	@Override
    public ServiceResult createInquiry(NoticeVO noticeType) {
		Integer atchFileNo = Optional.ofNullable(noticeType.getFile())
				.filter(af -> !CollectionUtils.isEmpty(af.getFileDetails())).map(af -> {
					fileService.createFile(af, saveFolder);
					return af.getAtchFileNo();
				}).orElse(null);
		noticeType.setAtchFileNo(atchFileNo);

		return dao.insertInquiry(noticeType) > 0 ? ServiceResult.OK : ServiceResult.FAIL;
    }

	@Override
	public List<NoticeVO> readInquiryList(String userId, PaginationInfo paging) {
	    // 사용자 ID를 기준으로 총 레코드 수를 조회
	    int totalRecord = dao.selectTotalInquiry(userId, paging);
	    paging.setTotalRecord(totalRecord);

	    // 사용자 ID를 기준으로 문의 내역을 조회
	    return dao.selectInquiryList(userId, paging);
	}
	/** 파일 다운로드 */
	@Override
	public File_DetailVO download(int atchFileNo, int fileSn) {
		return Optional.ofNullable(fileService.readFileDetail(atchFileNo, fileSn, saveFolder))
				.filter(fd -> fd.getSavedFile().exists())
				.orElseThrow(() -> new RuntimeException(String.format("[%d, %d]해당 파일이 없음.", atchFileNo, fileSn)));
	}

	/** 파일 한건 삭제 */
	@Override
	public void removeFile(int atchFileId, int fileSn) {
		fileService.removeFileDetail(atchFileId, fileSn, saveFolder);
	}
	
    @Override
    public NoticeVO readInquiry(int noticeNo) throws PKNotFoundException {
    	return Optional.ofNullable(dao.selectInquiry(noticeNo))
                .orElseThrow(() -> new PKNotFoundException(String.format("문의글 번호 %s를 찾을 수 없습니다.", noticeNo)));
    }


}

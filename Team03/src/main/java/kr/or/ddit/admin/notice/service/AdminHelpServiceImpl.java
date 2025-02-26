package kr.or.ddit.admin.notice.service;

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

import kr.or.ddit.admin.notice.dao.AdminHelpMapper;
import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.file.service.BaseService;
import kr.or.ddit.file.service.FileService;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.paging.SimpleCondition;
import kr.or.ddit.vo.FileVO;
import kr.or.ddit.vo.File_DetailVO;
import kr.or.ddit.vo.NoticeVO;
import lombok.RequiredArgsConstructor;

@Service("adminHelpService")
@RequiredArgsConstructor
public class AdminHelpServiceImpl implements AdminHelpService, BaseService {

	private final AdminHelpMapper mapper;
	private final FileService fileService;

	@Value("#{dirInfo.saveDirAdminNotice}")
	private Resource saveFolderRes;
	private File saveFolder;

	@PostConstruct
	public void init() throws IOException {
		this.saveFolder = saveFolderRes.getFile();
		if (!this.saveFolder.exists()) {
			this.saveFolder.mkdirs();
		}
	}

	/** 공지사항 등록 */
	@Override
	public ServiceResult createNotice(NoticeVO notice) {
		Integer atchFileNo = Optional.ofNullable(notice.getFile())
				.filter(af -> !CollectionUtils.isEmpty(af.getFileDetails())).map(af -> {
					fileService.createFile(af, saveFolder);
					return af.getAtchFileNo();
				}).orElse(null);

		notice.setAtchFileNo(atchFileNo);

		return mapper.insertNotice(notice) > 0 ? ServiceResult.OK : ServiceResult.FAIL;
	}

	/** 공지사항 단건 조회 */
	@Override
	public NoticeVO readNotice(int noticeNo) {
		return Optional.ofNullable(mapper.selectNotice(noticeNo))
				.orElseThrow(() -> new RuntimeException(String.format("공지사항 %s가 존재하지 않습니다.", noticeNo)));
	}

	/** 공지사항 목록 조회 (페이징) */
	@Override
	public List<NoticeVO> readNoticeList(PaginationInfo paging) {
		int totalRecord = mapper.selectTotalRecord(paging);
		paging.setTotalRecord(totalRecord);
		return mapper.selectNoticeList(paging);
	}

	/** 공지사항 수정 */
	@Override
	public ServiceResult modifyNotice(NoticeVO notice) {
		// 1. 기존 파일 정보 조회
	    NoticeVO existingNotice = mapper.selectNotice(notice.getNoticeNo());

	    // 2. 새로운 파일 처리
	    Integer atchFileNo = Optional.ofNullable(notice.getFile())
				 .filter(af -> af.getFileDetails() != null)
				 .map(af ->mergeSavedDetailsAndNewDetails(existingNotice.getFile(), af))
				 .orElse(null);

	    // 3. 새로운 파일 번호 설정
	    notice.setAtchFileNo(atchFileNo);
	    
	    // 4. 공지사항 업데이트
	    int rows = mapper.updateNotice(notice);
	    return rows > 0 ? ServiceResult.OK : ServiceResult.FAIL;
	}

	/** 공지사항 삭제 */
	@Override
	public ServiceResult removeNotice(int noticeNo) {
		return mapper.deleteNotice(noticeNo) > 0 ? ServiceResult.OK : ServiceResult.FAIL;
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

	/** 기존 파일과 새 파일 병합 */
	private Integer mergeSavedDetailsAndNewDetails(FileVO savedFile, FileVO newFile) {
		FileVO mergedFile = new FileVO();
		List<File_DetailVO> mergedDetails = Stream.concat(
				Optional.ofNullable(savedFile).filter(s -> !CollectionUtils.isEmpty(s.getFileDetails()))
						.map(s -> s.getFileDetails().stream()).orElse(Stream.empty()),
				Optional.ofNullable(newFile).filter(n -> !CollectionUtils.isEmpty(n.getFileDetails()))
						.map(n -> n.getFileDetails().stream()).orElse(Stream.empty()))
				.collect(Collectors.toList());

		mergedFile.setFileDetails(mergedDetails);

		if (!mergedFile.getFileDetails().isEmpty()) {
			fileService.createFile(mergedFile, saveFolder);
		}

		if (savedFile != null && savedFile.getFileDetails() != null) {
			fileService.disableFile(savedFile.getAtchFileNo());
		}

		return mergedFile.getAtchFileNo();
	}

	@Override
	public ServiceResult toggleNoticePinned(int noticeNo) {
		NoticeVO notice = mapper.selectNotice(noticeNo);
	    if (notice != null) {
	        // 고정 여부 토글 (Y ↔ N)
	        String newPinnedStatus = "Y".equals(notice.getNoticePinned()) ? "N" : "Y";
	        int rows = mapper.updateNoticePinned(noticeNo, newPinnedStatus);
	        return rows > 0 ? ServiceResult.OK : ServiceResult.FAIL;
	    }
	    return ServiceResult.FAIL;
	}

	@Override
	public ServiceResult removeNotices(List<Integer> noticeNo) {
		int count = mapper.deleteNotices(noticeNo);
	    return count > 0 ? ServiceResult.OK : ServiceResult.FAIL;
	}

	@Override
	public List<NoticeVO> readFaqList(PaginationInfo paging) {
		int totalRecord = mapper.selectFaqTotalCount(paging);
        paging.setTotalRecord(totalRecord);
        return mapper.selectFaqList(paging);
	}

	@Override
	public NoticeVO readFaq(int noticeNo) {
		return mapper.selectFaq(noticeNo);
	}

	@Override
	public ServiceResult createFaq(NoticeVO faq) {
		return mapper.insertFaq(faq) > 0 ? ServiceResult.OK : ServiceResult.FAIL;
	}

	@Override
    public ServiceResult modifyFaq(NoticeVO faq) {
		int count = mapper.updateFaq(faq);
	    return count > 0 ? ServiceResult.OK : ServiceResult.FAIL;
    }

    @Override
    public ServiceResult removeFaq(int noticeNo) {
        return mapper.deleteFaq(noticeNo) > 0 ? ServiceResult.OK : ServiceResult.FAIL;
    }

	@Override
	public List<NoticeVO> readInquiryList(PaginationInfo paging) {
		int totalRecord = mapper.selectInquiryTotalCount(paging);
        paging.setTotalRecord(totalRecord);
        return mapper.selectInquiryList(paging);
	}

	@Override
	public NoticeVO readInquiry(int noticeNo) {
		return mapper.selectInquiry(noticeNo);
	}

	@Override
	public ServiceResult modifyInquiry(NoticeVO inquiry) {
		int count = mapper.updateInquiry(inquiry);
	    return count > 0 ? ServiceResult.OK : ServiceResult.FAIL;
	}

	@Override
	public ServiceResult removeInquiry(int noticeNo) {
		return mapper.deleteInquiry(noticeNo) > 0 ? ServiceResult.OK : ServiceResult.FAIL;
	}

	@Override
	public List<NoticeVO> readAllPendingInquiries() {
	    return mapper.selectAllPendingInquiries();
	}

	@Override
	public int getTotalCount(SimpleCondition simpleCondition) {
		return mapper.getTotalCount(simpleCondition);
	}

	@Override
	public int getFaqTotalCount(SimpleCondition simpleCondition) {
		return mapper.getFaqTotalCount(simpleCondition);
	}

	@Override
	public int getInquiryTotalCount(SimpleCondition simpleCondition) {
		return mapper.getInquiryTotalCount(simpleCondition);
	}

}

package kr.or.ddit.position.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.notification.service.NotificationService;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.position.dao.PositionMapper;
import kr.or.ddit.vo.EmployVO;
import kr.or.ddit.vo.NotificationVO;
import kr.or.ddit.vo.PositionVO;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PositionServiceImpl implements PositionService {

	@Inject
	private PositionMapper mapper;
	
	@Inject
	NotificationService notificationService;
	
	@Override
	public ServiceResult insertPosition(PositionVO position) {
		if(mapper.insertPosition(position)>0) {
			System.out.println(position);
			EmployVO employ = mapper.selectEmployNo(position.getFiledNo());
			System.out.println(employ);
			
			NotificationVO notification = new NotificationVO();
			notification.setMemId(position.getMemId()); // 알림을 받을 사용자 ID (게시글 작성자)
	        notification.setNotificType("POSITION"); // 알림 유형
	        notification.setNotificUrl("/employ/detail/" + employ.getEmployNo()); // 알림 URL
	        notification.setNotificCont("[포지션 제안] 포지션 제안이 도착했습니다."); // 알림 내용으로 포지션 제안 설정
	        
	        notificationService.notifyAndSave(notification);
			return ServiceResult.OK;
		}else {
			
			return ServiceResult.FAIL; 
		}
	}

	@Override
	public ServiceResult checkPosition(PositionVO position) {
		int cnt = mapper.checkPosition(position);
		return cnt > 0 ? ServiceResult.OK : ServiceResult.FAIL;
	}

	@Override
	public List<PositionVO> selectMemberPositionList(String memId, PaginationInfo<PositionVO> paging) {
		List<PositionVO> positionList = mapper.selectMemberPositionList(memId, paging);
		if (positionList.isEmpty()) {  
			paging.setTotalRecord(0);
		    return positionList;
		}
		paging.setTotalRecord(positionList.get(0).getTotalCnt());
		return positionList;
	}

	@Override
	public EmployVO readEmployNo(int filedNo) {
		return mapper.selectEmployNo(filedNo);
	}
	
	@Override
	public ServiceResult selectPoistionCheck(PositionVO position) {
		int cnt = mapper.selectPoistionCheck(position);
		return cnt > 0 ? ServiceResult.OK : ServiceResult.FAIL;
	}

	@Override
	public List<PositionVO> selectPositionList(String username, PaginationInfo<PositionVO> pagination) {
		int totalRecord = 0;
		if (pagination != null) {
			totalRecord = mapper.selectTotalRecorde(username);
			pagination.setTotalRecord(totalRecord);
		}
		
		List<PositionVO> interList = mapper.selectPositionList(username, pagination);
		if(!interList.isEmpty()) {
		interList.get(0).setTotal(totalRecord);
		}
		return interList;
	}
}

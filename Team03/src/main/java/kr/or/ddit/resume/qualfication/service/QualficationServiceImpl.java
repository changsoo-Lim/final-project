package kr.or.ddit.resume.qualfication.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.resume.qualfication.dao.QualficationMapper;
import kr.or.ddit.vo.QualificationVO;

@Service
public class QualficationServiceImpl implements QualficationService {

	@Inject
	QualficationMapper mapper;
	
	@Override
	public ServiceResult createQualfication(QualificationVO qualificationVO) {
		if(mapper.insertQualfication(qualificationVO)>0) {
			return ServiceResult.OK;
		}else {
			return ServiceResult.FAIL;
		}
	}

	@Override
	public QualificationVO readQualificationList(String memId) {
		return mapper.selectQualificationList(memId);
	}

	@Override
	public ServiceResult modifiyQualfication(QualificationVO qualificationVO) {
		if(mapper.updateQualfication(qualificationVO)>0) {
			return ServiceResult.OK;
		}else {
			return ServiceResult.FAIL;
		}
	}

}

package kr.or.ddit.resume.resume.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import kr.or.ddit.board.dao.BoardMapper;
import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.file.service.BaseService;
import kr.or.ddit.file.service.FileService;
import kr.or.ddit.resume.activity.dao.ActivityMapper;
import kr.or.ddit.resume.award.dao.AwardMapper;
import kr.or.ddit.resume.career.dao.CareerMapper;
import kr.or.ddit.resume.compu.dao.CompuMapper;
import kr.or.ddit.resume.cret.dao.CretMapper;
import kr.or.ddit.resume.edu.dao.EduMapper;
import kr.or.ddit.resume.experience.dao.ExperienceMapper;
import kr.or.ddit.resume.highschool.dao.HighSchoolMapper;
import kr.or.ddit.resume.lang_test.dao.Lang_TestMapper;
import kr.or.ddit.resume.language.dao.LanguageMapper;
import kr.or.ddit.resume.portfolio.dao.PortfolioMapper;
import kr.or.ddit.resume.pref.dao.PrefMapper;
import kr.or.ddit.resume.qualfication.dao.QualficationMapper;
import kr.or.ddit.resume.resume.ResumeDTO;
import kr.or.ddit.resume.resume.dao.ResumeMapper;
import kr.or.ddit.resume.uni.dao.UniMapper;
import kr.or.ddit.resume.work_cond.dao.Work_CondMapper;
import kr.or.ddit.vo.File_DetailVO;
import kr.or.ddit.vo.MemberVO;
import lombok.RequiredArgsConstructor;

@Service("ResumeService")
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService,BaseService {

	@Inject
	private ResumeMapper mapper;

	@Inject // 검정고시
	private QualficationMapper quaMapper;

	@Inject // 고등하교
	private HighSchoolMapper highMapper;

	@Inject // 대학교
	private UniMapper uniMapper;

	@Inject
	private CareerMapper careerMapper;

	@Inject
	private CretMapper certMapper;

	@Inject
	private CompuMapper compMapper;

	@Inject
	private PrefMapper prefMapper;

	@Inject
	private AwardMapper awardMapper;

	@Inject
	private EduMapper eduMapper;

	@Inject
	private LanguageMapper langMapper;

	@Inject
	private Lang_TestMapper langTestMapper;

	@Inject
	private ExperienceMapper expMapper;

	@Inject
	private ActivityMapper actMapper;

	@Inject
	private PortfolioMapper portfolioMapper;

	@Inject
	private Work_CondMapper workCondMapper;
	
	
	private final FileService fileService;
	
	@Value("#{dirInfo.saveDirMemberImage}")
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
	@Transactional
	public ServiceResult insertAndUpdateResume(String memId,ResumeDTO resume) {

		List<ServiceResult> resultList = new ArrayList<>();

		// ifPresent - 값이 존재할 때만 특정 작업을 수행

		// QualificationVO 처리
		Optional.ofNullable(resume.getQualification()).ifPresent(qualificationList -> {
			qualificationList.forEach(qualification -> {
			qualification.setMemId(memId);
			ServiceResult result = qualification.getQualificationNo() == null
					? quaMapper.insertQualfication(qualification) > 0 ? ServiceResult.OK : ServiceResult.FAIL
					: quaMapper.updateQualfication(qualification) > 0 ? ServiceResult.OK : ServiceResult.FAIL;
			resultList.add(result);
			});
		});

		// HighSchoolVO 처리
		Optional.ofNullable(resume.getHighSchool()).ifPresent(highSchoolList -> {
			highSchoolList.forEach(highSchool -> {
			highSchool.setMemId(memId);
			ServiceResult result = highSchool.getHighschoolNo() == null
					? highMapper.insertHighschool(highSchool) > 0 ? ServiceResult.OK : ServiceResult.FAIL
					: highMapper.updateHighSchool(highSchool) > 0 ? ServiceResult.OK : ServiceResult.FAIL;
			resultList.add(result);
			});
		});

		// List<UniVO> 처리
		Optional.ofNullable(resume.getUni()).ifPresent(uniList -> {
			uniList.forEach(uni -> {
				uni.setMemId(memId);
				ServiceResult result = uni.getUniNo() == null
						? uniMapper.insertUni(uni) > 0 ? ServiceResult.OK : ServiceResult.FAIL
						: uniMapper.updateUni(uni) > 0 ? ServiceResult.OK : ServiceResult.FAIL;
				resultList.add(result);
			});
		});

		// List<CareerVO> 처리
		Optional.ofNullable(resume.getCareer()).ifPresent(careerList -> {
			careerList.forEach(career -> {
				career.setMemId(memId);
				ServiceResult result = career.getCareerNo() == null
						? careerMapper.insertCareer(career) > 0 ? ServiceResult.OK : ServiceResult.FAIL
						: careerMapper.updateCareer(career) > 0 ? ServiceResult.OK : ServiceResult.FAIL;
				resultList.add(result);
			});
		});

		// List<CertrVO> 처리
		Optional.ofNullable(resume.getCert()).ifPresent(certList -> {
			certList.forEach(cert -> {
				cert.setMemId(memId);
				ServiceResult result = cert.getCertNo() == null
						? certMapper.insertCert(cert) > 0 ? ServiceResult.OK : ServiceResult.FAIL
						: certMapper.updateCert(cert) > 0 ? ServiceResult.OK : ServiceResult.FAIL;
				resultList.add(result);
			});
		});

		// List<CompVO> 처리
		Optional.ofNullable(resume.getComp()).ifPresent(compList -> {
			compList.forEach(comp -> {
				comp.setMemId(memId);
				ServiceResult result = comp.getCompSkillNo() == null
						? compMapper.insertComp(comp) > 0 ? ServiceResult.OK : ServiceResult.FAIL
						: compMapper.updateComp(comp) > 0 ? ServiceResult.OK : ServiceResult.FAIL;
				resultList.add(result);
			});
		});

		// List<PrefVO> 처리
		Optional.ofNullable(resume.getPref()).ifPresent(prefList -> {
			prefList.forEach(pref -> {
				pref.setMemId(memId);
				ServiceResult result = pref.getPrefNo() == null
						? prefMapper.insertPref(pref) > 0 ? ServiceResult.OK : ServiceResult.FAIL
						: prefMapper.updatePref(pref) > 0 ? ServiceResult.OK : ServiceResult.FAIL;
				resultList.add(result);
			});
		});

		// List<AwardVO> 처리
		Optional.ofNullable(resume.getAward()).ifPresent(awardList -> {
			awardList.forEach(award -> {
				award.setMemId(memId);
				ServiceResult result = award.getAwardNo() == null
						? awardMapper.insertAward(award) > 0 ? ServiceResult.OK : ServiceResult.FAIL
						: awardMapper.updateAward(award) > 0 ? ServiceResult.OK : ServiceResult.FAIL;
				resultList.add(result);
			});
		});

		// List<EduVO> 처리
		Optional.ofNullable(resume.getEdu()).ifPresent(eduList -> {
			eduList.forEach(edu -> {
				edu.setMemId(memId);
				ServiceResult result = edu.getEduNo() == null
						? eduMapper.insertEdu(edu) > 0 ? ServiceResult.OK : ServiceResult.FAIL
						: eduMapper.updateEdu(edu) > 0 ? ServiceResult.OK : ServiceResult.FAIL;
				resultList.add(result);
			});
		});

		// List<langVO> 처리
		Optional.ofNullable(resume.getLanguage()).ifPresent(langList -> {
			langList.forEach(lang -> {
				lang.setMemId(memId);
				ServiceResult result = lang.getLangNo() == null
						? langMapper.insertLanguage(lang) > 0 ? ServiceResult.OK : ServiceResult.FAIL
						: langMapper.updateLanguage(lang) > 0 ? ServiceResult.OK : ServiceResult.FAIL;
				resultList.add(result);
			});
		});

		// List<Lang_TestVO> 처리
		Optional.ofNullable(resume.getLangTest()).ifPresent(langTestList -> {
			langTestList.forEach(langTest -> {
				langTest.setMemId(memId);
				ServiceResult result = langTest.getLangTestNo() == null
						? langTestMapper.insertLangTest(langTest) > 0 ? ServiceResult.OK : ServiceResult.FAIL
						: langTestMapper.updateLangTest(langTest) > 0 ? ServiceResult.OK : ServiceResult.FAIL;
				resultList.add(result);
			});
		});

		// List<ExperienceVO> 처리
		Optional.ofNullable(resume.getExperience()).ifPresent(expList -> {
			expList.forEach(exp -> {
				exp.setMemId(memId);
				ServiceResult result = exp.getExpNo() == null
						? expMapper.insertExperience(exp) > 0 ? ServiceResult.OK : ServiceResult.FAIL
						: expMapper.updateExperience(exp) > 0 ? ServiceResult.OK : ServiceResult.FAIL;
				resultList.add(result);
			});
		});

		// List<ActivityVO> 처리
		Optional.ofNullable(resume.getActivity()).ifPresent(actList -> {
			actList.forEach(act -> {
				act.setMemId(memId);
				ServiceResult result = act.getActivityNo() == null
						? actMapper.insertActivity(act) > 0 ? ServiceResult.OK : ServiceResult.FAIL
						: actMapper.updateActivity(act) > 0 ? ServiceResult.OK : ServiceResult.FAIL;
				resultList.add(result);
			});
		});

		// PortfolioVO 처리
		Optional.ofNullable(resume.getPortfolio()).ifPresent(portfolioList -> {
			portfolioList.forEach(portfolio -> {
				portfolio.setMemId(memId);
				ServiceResult result = portfolio.getPortNo() == null
						? portfolioMapper.insertPortfolio(portfolio) > 0 ? ServiceResult.OK : ServiceResult.FAIL
						: portfolioMapper.updatePortfolio(portfolio) > 0 ? ServiceResult.OK : ServiceResult.FAIL;
				resultList.add(result);
			});
		});

		// Work_Cond 처리
		Optional.ofNullable(resume.getWorkCond()).ifPresent(wordCondList -> {
			wordCondList.forEach(wordCond -> {
			wordCond.setMemId(memId);
			ServiceResult result;

			if (wordCond.getWorkCondNo() == null) {
				if (workCondMapper.insertWorkCond(wordCond) > 0) {
					result = ServiceResult.OK;

					Optional.ofNullable(resume.getWorkCity()).ifPresent(workCityList -> {
						workCityList.forEach(workCity -> {
							workCity.setWorkCondNo(wordCond.getWorkCondNo());

							ServiceResult cityResult = workCity.getWorkCityNo() == null
									? (workCondMapper.insertWorkCity(workCity) > 0 ? ServiceResult.OK
											: ServiceResult.FAIL)
									: (workCondMapper.updateWorkCity(workCity) > 0 ? ServiceResult.OK
											: ServiceResult.FAIL);

							resultList.add(cityResult); // WorkCity 결과 추가
						});
					});
				} else {
					result = ServiceResult.FAIL;
				}
			} else {
				if (workCondMapper.updateWorkCond(wordCond) > 0) {
					result = ServiceResult.OK;

					Optional.ofNullable(resume.getWorkCity()).ifPresent(workCityList -> {
						workCityList.forEach(workCity -> {
							workCity.setWorkCondNo(wordCond.getWorkCondNo());

							ServiceResult cityResult = workCity.getWorkCityNo() == null
									? (workCondMapper.insertWorkCity(workCity) > 0 ? ServiceResult.OK
											: ServiceResult.FAIL)
									: (workCondMapper.updateWorkCity(workCity) > 0 ? ServiceResult.OK
											: ServiceResult.FAIL);

							resultList.add(cityResult); // WorkCity 결과 추가
						});
					});
				} else {
					result = ServiceResult.FAIL;
				}
			}

			resultList.add(result); // WorkCond 결과 추가
			});
		});

		return resultList.contains(ServiceResult.FAIL) ? ServiceResult.FAIL : ServiceResult.OK;
	}

	@Override
	public ServiceResult updateMemberImage(MemberVO member) {
		Integer atchFileNo = Optional.ofNullable(member.getFile())
				.filter(af->! CollectionUtils.isEmpty(af.getFileDetails()))
				.map(af->{
					fileService.createFile(af, saveFolder);
					return af.getAtchFileNo();
				}).orElse(null);
		member.setAtchFileNo(atchFileNo);
		if(mapper.updateMemberImage(member) > 0) {
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
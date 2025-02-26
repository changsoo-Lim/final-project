package kr.or.ddit.apply.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.ibatis.annotations.Delete;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.apply.service.ApplyService;
import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.javamail.service.EmailService;
import kr.or.ddit.vchat.service.VchatService;
import kr.or.ddit.vo.ApplyVO;
import kr.or.ddit.vo.CandidateVO;
import kr.or.ddit.vo.EmployVO;
import kr.or.ddit.vo.FieldVO;
import kr.or.ddit.vo.InterviewVO;
import kr.or.ddit.vo.ProcedureVO;
import kr.or.ddit.vo.TestVO;
import kr.or.ddit.vo.VchatVO;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.response.MultipleDetailMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;

@Slf4j
@Controller
@RequestMapping("/company/apply")
public class ApplyController {

	final DefaultMessageService messageService;
	
    @Inject
    private ApplyService service;
    
    @Inject
    private VchatService vchatService;
    
    @Inject
    private EmailService emailService;
    
    @ModelAttribute("vchat")
	public VchatVO vchat() {
		return new VchatVO();
	}
    
    /**
     * 로그인된 회사의 채용공고 목록 조회
     * 
     * @param model          뷰에 전달할 데이터를 담는 객체
     * @param authentication 인증 정보를 포함하는 객체
     * @return 채용공고 목록 화면
     */
    @GetMapping("/list")
    public String getEmploy(
		Model model
		, Authentication authentication
	) {
    	String compId = ((UserDetails) authentication.getPrincipal()).getUsername();
        List<EmployVO> employList = service.getEmployList(compId);
        List<EmployVO> employAndFieldList = vchatService.employAndFieldList(compId);
        
        model.addAttribute("employList", employList);
		model.addAttribute("list", employAndFieldList);
        return "company/apply/applyList";
    }
    
    @GetMapping("/employ")
    @ResponseBody
    public ResponseEntity<List<EmployVO>> getEmployList(Authentication authentication) {
        String compId = ((UserDetails) authentication.getPrincipal()).getUsername();
        List<EmployVO> employList = service.getEmployList(compId);
        return ResponseEntity.ok(employList); // JSON 응답
    }
    
    /**
     * 특정 채용공고의 모집 분야 목록 조회
     * 
     * @param employNo 채용공고 번호
     * @return 모집 분야 목록
     */
    @GetMapping("/fields")
    @ResponseBody
    public ResponseEntity<List<FieldVO>> getFieldList(@RequestParam("employNo") Integer employNo) {
        if (employNo == null) {
            return ResponseEntity.badRequest().build();
        }
        List<FieldVO> fieldList = service.getFieldListByEmployNo(employNo);
        return ResponseEntity.ok(fieldList);
    }

    /**
     * 특정 모집 분야의 지원 상태 목록 조회
     * 
     * @param filedNo 모집 분야 번호
     * @return 지원 상태 목록
     */
    @GetMapping("/statusList")
    @ResponseBody
    public ResponseEntity<List<ProcedureVO>> getStatusList(@RequestParam Integer filedNo) {
        if (filedNo == null) {
            return ResponseEntity.badRequest().build();
        }
        List<ProcedureVO> statusList = service.getAppStatusList(filedNo);
        return ResponseEntity.ok(statusList);
    }
    
    /**
     * 특정 모집 분야 및 상태에 따른 지원자 목록 조회
     * 
     * @param status  지원 상태 코드
     * @param filedNo 모집 분야 번호
     * @return 지원자 목록
     */
    @GetMapping
    @ResponseBody
    public ResponseEntity<?> getApplicants(
        @RequestParam(required = false) String status,
        @RequestParam(required = false) Integer filedNo
    ) {
        List<ApplyVO> applicants = service.getApplicantsGroupedByField(status, filedNo);

        return ResponseEntity.ok(applicants);
    }
    
	/*
	 * @PostMapping("/close")
	 * 
	 * @ResponseBody public ResponseEntity<String> closeEmploy(@RequestBody
	 * Map<String, Integer> requestData) { Integer employNo =
	 * requestData.get("employNo");
	 * 
	 * if (employNo == null) { return
	 * ResponseEntity.badRequest().body("채용공고 번호가 없습니다."); }
	 * 
	 * try { service.closeEmployWithApplicantUpdates(employNo); return
	 * ResponseEntity.ok("채용공고와 관련된 지원자 처리가 완료되었습니다."); } catch (Exception e) {
	 * e.printStackTrace(); return
	 * ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("마감 처리 중 오류 발생"
	 * ); } }
	 */

    
    /**
     * 지원자의 상태 업데이트
     * 
     * @param requestData 요청 데이터 (applyNo, newStatus, appPassYn)
     * @return 성공 시 상태 코드 200
     */
    @PutMapping("/updateStatus")
    public ResponseEntity<Void> updateApplicantStatus(@RequestBody Map<String, String> requestData) {
        String applyNo = requestData.get("applyNo");
        String newStatus = requestData.get("newStatus");
        String appPassYn = requestData.get("appPassYn");
        
        service.updateApplicantStatus(applyNo, newStatus, appPassYn);
        return ResponseEntity.ok().build();
    }

    /**
     * 다수 지원자 상태 일괄 업데이트
     * 
     * @param updates 업데이트 정보 리스트
     * @return 성공 시 상태 코드 200
     */
    @PostMapping("/updateStatusBatch")
    @ResponseBody
    public ResponseEntity<Void> updateStatusBatch(@RequestBody List<Map<String, String>> updates) {
        for (Map<String, String> update : updates) {
            String applyNo = update.get("applyNo");
            String newStatus = update.get("newStatus");
            String appPassYn = update.get("appPassYn");
            
            service.updateApplicantStatus(applyNo, newStatus, appPassYn);
        }
        return ResponseEntity.ok().build();
    }
    
    /**
     * 다수 지원자 불합격 처리
     * 
     * @param applicants 지원자 정보 리스트
     * @return 처리 결과 메시지
     */
    @PostMapping("/rejectApplicantsBatch")
    public ResponseEntity<String> rejectApplicantsBatch(@RequestBody List<Map<String, String>> applicants) {
        // 이미 불합격 상태인 지원자를 필터링
        List<String> invalidApplicants = service.getAlreadyRejectedApplicants(applicants);
        
        if (!invalidApplicants.isEmpty()) {
            String errorMsg = "이미 불합격 상태인 지원자가 포함되어 있습니다: " + String.join(", ", invalidApplicants);
            return ResponseEntity.badRequest().body(errorMsg);
        }

        boolean isUpdated = service.rejectApplicantsBatch(applicants);
        return isUpdated ? ResponseEntity.ok("불합격 처리가 완료되었습니다.") 
                         : ResponseEntity.status(500).body("불합격 처리 실패");
    }
    
    @GetMapping("/testDetail")
    @ResponseBody
    public ResponseEntity<Object> getTestDetail(@RequestParam Integer applyNo) {
        ApplyVO applyTestDetail = service.selectTestDetail(applyNo);
        
        // 데이터가 없는 경우에도 200 OK 반환, UI에서 처리
        if (applyTestDetail == null) {
            // 빈 ApplyVO 객체를 반환하거나 다른 메시지 구조로 반환
            return ResponseEntity.ok(new ApplyVO());
        }
        
        return ResponseEntity.ok(applyTestDetail);
    }
    
    @GetMapping("/interviewDetail")
    @ResponseBody
    public ResponseEntity<Object> getInterviewDetail(@RequestParam Integer applyNo) {
        List<ApplyVO> applyInterviewDetail = service.selectInterviewDetailList(applyNo);

        // 빈 리스트 반환
        if (applyInterviewDetail == null || applyInterviewDetail.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        return ResponseEntity.ok(applyInterviewDetail);
    }
    
    /**
     * 특정 지원자의 상세 정보 조회
     * 
     * @param applyNo 지원자 번호
     * @return 지원자 상세 정보
     */
    @GetMapping("/detail")
    @ResponseBody
    public ResponseEntity<?> getApplicantDetail(@RequestParam Integer applyNo) {
        ApplyVO applicantDetail = service.getApplicantDetail(applyNo);
        
        if (applicantDetail == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("지원자를 찾을 수 없습니다.");
        }

        return ResponseEntity.ok(applicantDetail);
    }

    /**
     * 특정 지원자에게 이메일 발송
     * 
     * @param applyNo  지원자 번호
     * @param emailData 이메일 제목과 본문
     * @return 이메일 발송 결과
     */
	@PostMapping("/sendMail/{applyNo}")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> sendMailToApplicant(
		@PathVariable Integer applyNo,
		@RequestBody Map<String, String> emailData
	) {
		Map<String, Object> resultMap = new HashMap<>();
		String subject = emailData.getOrDefault("subject", "지원 상태 안내");
		String body = emailData.getOrDefault("body", "지원 상태가 업데이트되었습니다.");

		try {
			ApplyVO applicantDetail = service.getApplicantDetail(applyNo);
			if (applicantDetail == null) {
				resultMap.put("message", "지원자를 찾을 수 없습니다.");
				return ResponseEntity.badRequest().body(resultMap);
			}

			String email = applicantDetail.getMember().getMemEmail();
			if (email == null || email.isEmpty()) {
				resultMap.put("message", "지원자의 이메일 정보가 없습니다.");
				return ResponseEntity.badRequest().body(resultMap);
			}

			emailService.sendEmail(email, subject, body);

			resultMap.put("message", "이메일이 성공적으로 전송되었습니다.");
			resultMap.put("email", email);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("message", "이메일 전송 중 오류가 발생했습니다: " + e.getMessage());
			return ResponseEntity.status(500).body(resultMap);
		}

		return ResponseEntity.ok(resultMap);
	}

	/**
     * 다수 지원자에게 이메일 일괄 발송
     * 
     * @param emailData 이메일 데이터 (applyNos, 제목, 본문)
     * @return 이메일 발송 결과
     */
	@PostMapping("/sendMailBatch")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> sendMailToApplicants(@RequestBody Map<String, Object> emailData) {
		
		List<Integer> applyNos = ((List<?>) emailData.get("applyNos")).stream()
	        .map(no -> Integer.parseInt(no.toString()))
	        .collect(Collectors.toList());

	    String subject = (String) emailData.getOrDefault("subject", "지원 상태 안내");
	    String body = (String) emailData.getOrDefault("body", "지원 상태가 업데이트되었습니다.");

	    Map<String, Object> resultMap = new HashMap<>();
	    try {
	        Map<String, Object> filterResult = service.filterApplicantsForEmail(applyNos);
	        List<ApplyVO> toSend = (List<ApplyVO>) filterResult.get("toSend");
	        List<ApplyVO> blocked = (List<ApplyVO>) filterResult.get("blocked");

	        List<String> successEmails = new ArrayList<>();
	        List<String> failedEmails = new ArrayList<>();
	        for (ApplyVO applicant : toSend) {
	            try {
	                emailService.sendEmail(applicant.getMember().getMemEmail(), subject, body);
	                successEmails.add(applicant.getMember().getMemEmail());
	            } catch (Exception e) {
	                failedEmails.add(applicant.getMember().getMemEmail());
	            }
	        }

	        resultMap.put("message", "메일 발송 작업이 완료되었습니다.");
	        resultMap.put("sentCount", successEmails.size());
	        resultMap.put("failedCount", failedEmails.size());
	        resultMap.put("blockedCount", blocked.size());
	        resultMap.put("successEmails", successEmails);
	        resultMap.put("failedEmails", failedEmails);
	        resultMap.put("blockedApplicants", blocked.stream()
	            .map(a -> a.getMember().getMemNm())
	            .collect(Collectors.toList()));
	        return ResponseEntity.ok(resultMap);
	    } catch (Exception e) {
	        e.printStackTrace();
	        resultMap.put("message", "메일 발송 중 오류 발생: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
	    }
	}

    public ApplyController() {
        // 반드시 계정 내 등록된 유효한 API 키, API Secret Key를 입력해주셔야 합니다!
        this.messageService = NurigoApp.INSTANCE.initialize("NCSLEWX1TNR41FS7", "삭제", "https://api.coolsms.co.kr");
    }

    /**
     * 다수 지원자에게 SMS 일괄 발송
     * 
     * @param smsData SMS 데이터 (수신자 번호 리스트, 메시지 내용)
     * @return SMS 발송 결과
     */
    @PostMapping("/sendSmsBatch")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> sendSmsToApplicants(
        @RequestBody Map<String, Object> smsData
    ) {
        List<String> phoneNumbers = ((List<?>) smsData.get("phoneNumbers"))
            .stream()
            .map(String::valueOf)
            .collect(Collectors.toList());
        
        String messageContent = (String) smsData.get("message");

        Map<String, Object> resultMap = new HashMap<>();
        try {
            // CoolSMS 단체 발송 메시지 리스트 생성
            ArrayList<Message> messages = new ArrayList<>();
            for (String phoneNumber : phoneNumbers) {
                Message message = new Message();
                message.setFrom("01044477734"); // 발신번호
                message.setTo(phoneNumber);    // 수신번호
                message.setText(messageContent); // 메시지 내용
                messages.add(message);
            }

            // CoolSMS API를 사용해 단체 발송
            MultipleDetailMessageSentResponse response = messageService.send(messages, false, true);

            resultMap.put("message", "문자가 성공적으로 전송되었습니다.");
            resultMap.put("response", response);
            return ResponseEntity.ok(resultMap);
        } catch (NurigoMessageNotReceivedException exception) {
            resultMap.put("message", "일부 문자가 전송되지 않았습니다.");
            resultMap.put("failedList", exception.getFailedMessageList());
            return ResponseEntity.status(500).body(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("message", "문자 전송 중 오류: " + e.getMessage());
            return ResponseEntity.status(500).body(resultMap);
        }
    }
    
    @PostMapping("/interview")
    @ResponseBody
    public ResponseEntity<?> createInterview(@RequestBody List<InterviewVO> interviews) {
        try {
            if (interviews == null || interviews.isEmpty()) {
                return ResponseEntity.badRequest().body("면접 일정 데이터가 없습니다.");
            }

            for (InterviewVO interview : interviews) {
                log.info("Received interview: {}", interview);

                if (interview.getApplyNo() == null || interview.getIntvDt() == null) {
                    return ResponseEntity.badRequest().body("지원자 번호 또는 면접 날짜가 누락되었습니다.");
                }

                service.insertInterview(interview);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "면접 일정이 성공적으로 저장되었습니다.");
            return ResponseEntity.ok(response);

        } catch (DataAccessException dae) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("면접 일정 저장 중 데이터베이스 오류가 발생했습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("면접 일정 저장 중 예상치 못한 오류가 발생했습니다.");
        }
    }
    
    /**
     * 관리자 및 회사 시험 데이터 가져오기
     */
    @GetMapping("/tests")
    @ResponseBody
    public ResponseEntity<List<TestVO>> getTestList(
            @RequestParam String testType,
            @RequestParam(required = false) String testOwner,
            Authentication authentication) {

        String compId = ((UserDetails) authentication.getPrincipal()).getUsername();

        List<TestVO> testList;
        if ("admin".equals(testOwner)) {
            testList = service.getAdminTests(testType);
        } else {
            testList = service.getCompanyTests(testType, compId);
        }

        return ResponseEntity.ok(testList);
    }

    /**
     * 지원자 시험 데이터 삽입
     */
    @PostMapping("/applyTest")
    @ResponseBody
    public ResponseEntity<String> applyTestToCandidates(@RequestBody List<CandidateVO> candidateVO) {
        try {
        	service.insertCandidate(candidateVO);
            return ResponseEntity.ok("시험이 성공적으로 적용되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("시험 적용 중 오류가 발생했습니다.");
        }
    }
    
    @PutMapping("/updateOpenYn")
    @ResponseBody
    public ResponseEntity<String> updateAppOpenYn(@RequestBody Map<String, String> requestData) {
        String applyNo = requestData.get("applyNo");
        String appOpenYn = requestData.get("appOpenYn");

        try {
            service.updateAppOpenYn(applyNo, appOpenYn);
            return ResponseEntity.ok("이력서 열람 여부가 업데이트되었습니다.");
        } catch (Exception e) {
            log.error("이력서 열람 여부 업데이트 중 오류 발생: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("업데이트 실패");
        }
    }
    /**
     * 지원자 등록
     */
    @PostMapping("/applyInsert")
    public ResponseEntity<Map<String, String>> applyInsert(@RequestBody ApplyVO applyVo
															, BindingResult errors
															, @AuthenticationPrincipal UserDetails user
	) {
    	Map<String, String> response = new HashMap<>();
    	boolean isMember = false;
		if(user != null) {
			isMember = user.getAuthorities().stream()
					.anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE02"));
		}
		if(isMember) {
			String memId = user.getUsername();
			if (!errors.hasErrors()) {
				applyVo.setMemId(memId);
				ServiceResult result = service.createApply(applyVo);
				switch (result) {
				case OK:
					response.put("status", "success");
		            response.put("message", "지원 완료되었습니다.");
					break;
				case PKDUPLICATED:
					response.put("status", "fail");
		            response.put("message", "이미 지원한 모집분야입니다.");
					break;
				
				default:
					response.put("status", "error");
		            response.put("message", "지원실패 하였습니다. 잠시 후 다시 시도해주세요.");
					break;
				}
			}else {
				response.put("status", "error");
				response.put("message", "서버 오류. 잠시 후 다시 시도해주세요.");
			}
		}
		return ResponseEntity.ok(response);
    }
    // 지원 취소
    @DeleteMapping("/cancel")
    public ResponseEntity<Map<String, String>> removeApply(@RequestBody ApplyVO applyVo
			, BindingResult errors
			, @AuthenticationPrincipal UserDetails user
    ) {
    	Map<String, String> response = new HashMap<>();
    	boolean isMember = false;
		if(user != null) {
			isMember = user.getAuthorities().stream()
					.anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE02"));
		}
		if(isMember) {
			String memId = user.getUsername();
			if (!errors.hasErrors()) {
				applyVo.setMemId(memId);
				if(service.removeApply(applyVo) > 0) {
					response.put("status", "success");
					response.put("message", "지원취소 완료되었습니다.");
				}else {
					response.put("status", "fail");
					response.put("message", "지원취소 실패하였습니다. 잠시 후 다시 시도해주세요.");
				}
			}else {
				response.put("status", "error");
				response.put("message", "서버 오류. 잠시 후 다시 시도해주세요.");
			}
		}
    	return ResponseEntity.ok(response);
    }
}

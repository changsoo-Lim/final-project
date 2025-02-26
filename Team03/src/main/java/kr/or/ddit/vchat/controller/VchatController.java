package kr.or.ddit.vchat.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.vchat.service.VchatService;
import kr.or.ddit.vo.EmployVO;
import kr.or.ddit.vo.VchatVO;

@Controller
@RequestMapping("/vchat")
public class VchatController {

	@Inject
	private VchatService service;
	
	@ModelAttribute("vchat")
	public VchatVO vchat() {
		return new VchatVO();
	}
	
	@GetMapping("interview")
	public String vchatListMember(Authentication authentication,Model model) {
		if(authentication==null) {
			return "redirect:/login";
		}
		String memId = ((UserDetails) authentication.getPrincipal()).getUsername();
		
		List<VchatVO> vchatList = service.readVchatMemList(memId);
		List<EmployVO> employAndFieldList = service.employAndFieldMemList(memId);
		
		model.addAttribute("vchatList", vchatList);
		model.addAttribute("list", employAndFieldList);
		
		return "member/vchat/vchatMemList";
	}
	
	@GetMapping
	public String vchatListComp(Authentication authentication,Model model) {
		if(authentication==null) {
			return "redirect:/login";
		}
		String compId = ((UserDetails) authentication.getPrincipal()).getUsername();
		
		List<VchatVO> vchatList = service.readVchatList(compId);
		List<EmployVO> employAndFieldList = service.employAndFieldList(compId);
		model.addAttribute("vchatList", vchatList);
		model.addAttribute("list", employAndFieldList);
		return "company/vchat/vchatList";
	}
	
	@PostMapping
	@ResponseBody
	public Map<String, Object> vchatform(@RequestBody VchatVO vchat) {
	    Map<String, Object> response = new HashMap<>();

	    if (vchat == null) {
	        response.put("status", "error");
	        response.put("message", "요청 데이터가 비어 있습니다.");
	        return response;
	    }

	    ServiceResult result = service.createVacht(vchat); // 방 생성 서비스 호출

	    switch (result) {
	        case OK:
	            response.put("status", "success");
	            response.put("message", "화상 채팅 방이 성공적으로 생성되었습니다.");
	            break;

	        default:
	            response.put("status", "error");
	            response.put("message", "화상 채팅 방 생성에 실패했습니다. 다시 시도해주세요.");
	            break;
	    }

	    return response; // JSON 응답 반환
	}


	@PostMapping("/room") // 화상채팅 방 생성
	public ResponseEntity<?> proxyRoomRequest(@RequestBody Map<String, Object> requestBody) {
	    // 외부 API URL
	    String apiUrl = "https://openapi.gooroomee.com/api/v1/room";
	    
	    // RestTemplate 생성 및 FormHttpMessageConverter 추가
	    RestTemplate restTemplate = new RestTemplate();
	    restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
	    
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
	    headers.set("X-GRM-AuthToken", "12056163501988613cf51b7b51cdd8140bb172761d02211a8b");

	    // MultiValueMap으로 요청 본문 구성
	    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
	    body.add("roomTitle", (String) requestBody.get("roomTitle"));
	    body.add("roomUrlId", (String) requestBody.get("roomUrlId"));
	    body.add("callType", (String) requestBody.get("callType"));
	    body.add("liveMode", requestBody.get("liveMode").toString());
	    body.add("maxJoinCount", requestBody.get("maxJoinCount").toString());
	    body.add("liveMaxJoinCount", requestBody.get("liveMaxJoinCount").toString());
	    body.add("passwd", (String) requestBody.get("passwd"));
	    body.add("layoutType", (String) requestBody.get("layoutType"));
	    body.add("sfuIncludeAll", requestBody.get("sfuIncludeAll").toString());

	    HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);

	    // 외부 API 요청
	    ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, entity, String.class);

	    return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
	}
	
	@PostMapping("/url") // 방 입장
	public ResponseEntity<?> proxyToGooroomee(@RequestBody Map<String, Object> requestBody) {
	    // 외부 API URL
	    String apiUrl = "https://openapi.gooroomee.com/api/v1/room/user/otp/url";

	    // RestTemplate 생성 및 FormHttpMessageConverter 추가
	    RestTemplate restTemplate = new RestTemplate();
	    restTemplate.getMessageConverters().add(new FormHttpMessageConverter());

	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
	    headers.set("X-GRM-AuthToken", "12056163501988613cf51b7b51cdd8140bb172761d02211a8b");

	    // MultiValueMap으로 요청 본문 구성
	    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
	    body.add("roleId", (String) requestBody.get("roleId"));
	    body.add("apiUserId", (String) requestBody.get("apiUserId"));
	    body.add("ignorePasswd", requestBody.get("ignorePasswd").toString());
	    body.add("roomId", (String) requestBody.get("roomId"));
	    body.add("username", (String) requestBody.get("username"));

	    HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);


        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, entity, String.class);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
	
	}
	
	@DeleteMapping("{vchatNo}")
	@ResponseBody
	public String del(@PathVariable("vchatNo") int vchatNo,RedirectAttributes redirectAttributes) {
		
		String lvn = null;
		ServiceResult result = service.removeVchat(vchatNo);
		switch (result) {
			case OK:
				lvn = "redirect:/vchat";
				break;
				
			default:
				redirectAttributes.addFlashAttribute("message", "삭제 실패 !");
				lvn = "redirect:/vchat";
				break;
		}
		return lvn;
	}
	
}

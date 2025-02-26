package kr.or.ddit.member.recommend.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.ddit.member.recommend.service.RecommendService;


@RestController
public class RecommendController {
	
	@Autowired
    private RecommendService service;

    @GetMapping("/recommend")
    public Map<String, Object> getRecommendations(
    		@AuthenticationPrincipal UserDetails user
    ) {
        return service.getRecommendations(user.getUsername());
    }
}

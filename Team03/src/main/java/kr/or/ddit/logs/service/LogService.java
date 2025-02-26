package kr.or.ddit.logs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.logs.dao.LogMapper;
import kr.or.ddit.vo.Log;

@Service
public class LogService {
	
	@Autowired
    private LogMapper logMapper;
	 	
	 public List<Log> getLogList() {
	        return logMapper.selectLogList();
    }
	// 로그인 기록을 로그에 추가
	    public void logLoginAction(String username) {
	        // 로그인 로그를 저장하는 DB 작업
	    	logMapper.insertLoginLog(username);
	    }
}
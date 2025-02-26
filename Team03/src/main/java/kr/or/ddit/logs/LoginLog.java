package kr.or.ddit.logs;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class LoginLog {
	
	private int logNo; // 로그 번호
    private String userId; // 사용자 ID
    private String action; // 수행된 작업
    private Timestamp actionTimestamp; // 작업 시간
}

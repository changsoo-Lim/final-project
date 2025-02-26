package kr.or.ddit.vo;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class Log {
	
	private int logNo;
    private String userId;
    private String action;
    private Timestamp actionTimestamp;
}

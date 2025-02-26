package kr.or.ddit.test.candidate.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class CandidateDTO implements Serializable   {
    private Integer rnum;           // 역순 번호
    private Integer candidateNo;    // 응시자 번호
    private Integer applyNo;		// 공고 지원자 번호
    private String candidateSdt;	// 시험시작일자
    private String candidateEdt;	// 시험마감일자
    private Integer candidateScore;	// 채점점수
    private String candidateYn;     // 응시 여부
    private String candidateCdt;    // 응시 일시
    
    private Integer testNo;         // 시험 번호
    private String compId;          // 기업 회원 ID
    private String testCd;          // 시험 코드
    private String testNm;			// 시험명
    private String testTime;		// 시험시간
    
    private Integer employNo;       // 채용 공고 번호
    private String employTitle;     // 공고 제목
    
    private Integer filedNo;        // 모집 분야 번호
    private String filedNm;         // 모집 분야명
    
    private String compNm;          // 기업명
    
}
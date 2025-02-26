package kr.or.ddit.intro.intro;

import java.util.List;

import kr.or.ddit.vo.Intro_DetailVO;
import lombok.Data;

@Data
public class IntroDTO {
	private String introTitle;
	private List<Intro_DetailVO> introDetails;
}

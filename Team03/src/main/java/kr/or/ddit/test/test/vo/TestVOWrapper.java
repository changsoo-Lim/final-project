package kr.or.ddit.test.test.vo;

import java.util.List;

import kr.or.ddit.vo.TestVO;
import kr.or.ddit.vo.Test_QuestnVO;
import lombok.Data;

@Data
public class TestVOWrapper {
	private TestVO test;
	private List<Test_QuestnVO> testQuestnList;
	
}

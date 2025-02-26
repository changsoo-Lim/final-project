package kr.or.ddit.employ.employ.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.employ.employ.dao.EmployMapper;
import kr.or.ddit.paging.PaginationInfo;
import kr.or.ddit.vo.CodeVO;
import kr.or.ddit.vo.EmployVO;
import kr.or.ddit.vo.FieldVO;
import kr.or.ddit.vo.FilterVO;
import kr.or.ddit.vo.ProcedureVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmployServiceImpl implements EmployService{
	
	@Inject
	private EmployMapper employMapper;
	
	@Override
	public EmployVO readEmploy(int employNo, String memId) {
		EmployVO emp = employMapper.selectEmploy(employNo, memId);
		String inputStartDate = emp.getEmploySd();
		String inputEndDate = emp.getEmployEd();
		// 입력된 날짜 문자열을 Date 객체로 변환
	    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
	    Date sDate = null;
	    Date eDate = null;
		try {
			sDate = inputFormat.parse(inputStartDate);
			eDate = inputFormat.parse(inputEndDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

	    // 요일 정보를 얻기 위해 Calendar 사용
	    Calendar sCalendar = Calendar.getInstance();
	    Calendar eCalendar = Calendar.getInstance();
	    sCalendar.setTime(sDate);
	    eCalendar.setTime(eDate);
	    String[] daysOfWeek = {"일", "월", "화", "수", "목", "금", "토"};
	    String sDayOfWeek = daysOfWeek[sCalendar.get(Calendar.DAY_OF_WEEK) - 1]; // 1~7 값을 반환하므로 -1
	    String eDayOfWeek = daysOfWeek[eCalendar.get(Calendar.DAY_OF_WEEK) - 1]; // 1~7 값을 반환하므로 -1

	    // 출력 형식 지정
	    String sFormattedDate = inputStartDate + " (" + sDayOfWeek + ") 00시00분";
	    String eFormattedDate = inputEndDate + " (" + eDayOfWeek + ") 23시59분";
	    
	    emp.setFormatStartDate(sFormattedDate);
	    emp.setFormatEndDate(eFormattedDate);
		return emp;
	}
	
	@Override
	public List<EmployVO> readEmployList(PaginationInfo<EmployVO> paging, String companyId, String memId) {
		List<EmployVO> empList = employMapper.selectEmployList(paging, companyId, memId);
		if (empList.isEmpty()) {
		    paging.setTotalRecord(0);
		    return empList;
		}
		paging.setTotalRecord(empList.get(0).getTotalCnt());
		
		for (EmployVO employVO : empList) {
			employVO.setApplyCnt(employMapper.selelctEmpApplyCnt(employVO.getEmployNo()));
		}
		
		
		return empList;
	}

	@Override
	@Transactional
	public int createEmploy(EmployVO employ) {
		// 1. 채용공고 등록
		// keyProperty="employNo"
        int rowcnt = employMapper.insertEmploy(employ);
        if(rowcnt == 0) {
            throw new RuntimeException("채용공고 등록 실패");
        }
        // 생성된 채용공고 PK
        int newEmployNo = employ.getEmployNo(); 
        
        // 2. 모집분야 목록이 있으면 순회
        List<FieldVO> fieldList = employ.getFieldList();
        if(fieldList != null && !fieldList.isEmpty()) {
            for(FieldVO field : fieldList) {
                // 채용공고번호 세팅
            	field.setEmployNo(newEmployNo);
                // keyProperty="filedNo"
                int fieldCnt = employMapper.insertField(field);
                if(fieldCnt == 0) {
                    throw new RuntimeException("모집분야 등록 실패");
                    
                }
                Integer newFileNo = field.getFiledNo();
                // 3. 절차 (Procedure)
                List<ProcedureVO> procedureList = field.getProcedure();
                if(procedureList != null && !procedureList.isEmpty()) {
                    int turn= 1;
                    for(ProcedureVO proc : procedureList) {
                        proc.setFiledNo(newFileNo);
                        proc.setProcedureTurn(turn++);
                        int pCnt = employMapper.insertProcedure(proc);
                        if(pCnt == 0) {
                            throw new RuntimeException("절차 등록 실패");
                        }
                    }
                }

                // 4. 필수조건 (Filter)
                List<FilterVO> filterList = field.getFilterList();
                if(filterList != null && !filterList.isEmpty()) {
                    for(FilterVO filter : filterList) {
                        filter.setFiledNo(newFileNo);
                        int fCnt = employMapper.insertFilter(filter);
                        if(fCnt == 0) {
                            throw new RuntimeException("필수조건 등록 실패");
                        }
                    }
                }
            }
        }
        return rowcnt;
	}

	@Override
	@Transactional
	public int modifyEmploy(EmployVO employ) {
		// 1) 채용공고 UPDATE
        int rowcnt = employMapper.updateEmploy(employ);
        if(rowcnt == 0) {
            throw new RuntimeException("채용공고 수정 실패");
        }
        Integer employNo = employ.getEmployNo();

        // 2) 기존 모집분야, 절차, 필수조건 모두 삭제 (ON DELETE CASCADE or 직접)
        //   a) FIELD 전체 DELETE
        employMapper.deleteField(employNo);
        //   b) -> procedure/filter는 DB에 filedNo 기반이므로,
        //     만약 ON DELETE CASCADE가 없다면, 
        //     deleteProcedure, deleteFilter도 호출해야 할 수도 있음

        // 3) 새롭게 fieldList re-insert
        List<FieldVO> fieldList = employ.getFieldList();
        if(fieldList != null && !fieldList.isEmpty()) {
            for(FieldVO field : fieldList) {
                field.setEmployNo(employNo);
                field.setFiledJobs(field.getFiledJobs().replace("/\n/g", "<br>"));
                field.setFiledPreference(field.getFiledPreference().replace("/\n/g", "<br>"));
                int fieldCnt = employMapper.insertField(field); // new filedNo
                if(fieldCnt==0) throw new RuntimeException("모집분야 재등록 실패");
                Integer newFileNo = field.getFiledNo();

                // 절차
                List<ProcedureVO> procedureList = field.getProcedure();
                if(procedureList!=null && !procedureList.isEmpty()) {
                    int turn=1;
                    for(ProcedureVO proc: procedureList) {
                        proc.setFiledNo(newFileNo);
                        proc.setProcedureTurn(turn++);
                        employMapper.insertProcedure(proc);
                    }
                }

                // 필수조건
                List<FilterVO> filterList = field.getFilterList();
                if(filterList!=null && !filterList.isEmpty()) {
                    for(FilterVO f: filterList) {
                        f.setFiledNo(newFileNo);
                        employMapper.insertFilter(f);
                    }
                }
            }
        }
        return rowcnt;
	}

	@Override
	public int removeEmploy(int employNo) {
		return employMapper.deleteEmploy(employNo);
	}

	@Override
	public List<CodeVO> readFilterTitleList() {
		return employMapper.selectFilterTitleList();
	}

	@Override
	public List<CodeVO> readFilterContList() {
		return employMapper.selectFilterContList();
	}

}

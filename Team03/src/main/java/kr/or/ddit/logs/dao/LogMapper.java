package kr.or.ddit.logs.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.vo.Log;

@Mapper
public interface LogMapper {
	List<Log> selectLogList();

	// 로그인 로그 기록
    @Insert("INSERT INTO log (user_id, action, action_timestamp) VALUES (#{userId}, 'LOGIN', SYSDATE)")
    void insertLoginLog(String userId);
    
}
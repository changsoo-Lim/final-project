package kr.or.ddit.notification.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.vo.NotificationVO;
import kr.or.ddit.vo.StatusVO;

@Mapper
public interface NotificationMapper {

    /**
     * 알림 내역 등록
     * @param notificationVO
     * @return
     */
    public int insertNotification(NotificationVO notificationVO);

    /**
     * 알림 수신 여부 조회
     * @param memId
     * @param type
     * @return
     */
    public String getNotificationStatus(@Param("memId") String memId, @Param("type") String type);
    
    public List<StatusVO> selectStatusList(String memId);
}


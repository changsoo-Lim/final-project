package kr.or.ddit.security.resource.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import kr.or.ddit.security.resource.vo.SecuredResourceVO;

@Mapper
public interface SecuredResourceMapper {
	public List<SecuredResourceVO> selectResourceList();
}

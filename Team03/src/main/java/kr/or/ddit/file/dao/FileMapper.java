package kr.or.ddit.file.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.vo.FileVO;
import kr.or.ddit.vo.File_DetailVO;

@Mapper
public interface FileMapper {
	/**
	 * 첨부파일 저장을 위한 메소드, 파라미터 맵을 형성하기 위해 @Param 사용함.
	 */
	public int insertFile(FileVO atchFile);

	/**
	 * 첨부파일 한건 조회를 위한 메소드
	 */
	public File_DetailVO selectFileDetail(@Param("atchFileNo") int atchFileNo, @Param("fileSn") int fileSn);

	/**
	 * 사용중인 첨부파일 그룹 전체 조회
	 */
	public FileVO selectFileEnable(@Param("atchFileNo") int atchFileNo);

	/**
	 * 사용 여부에 따른 파일 그룹 조회
	 * 
	 * @param atchFileNo
	 * @param enable
	 * @return
	 */
	public FileVO selectFile(@Param("atchFileNo") int atchFileNo, @Param("enable") boolean enable);

	/**
	 * downcount 증가
	 */
	public int incrementDowncount(@Param("atchFileNo") int atchFileNo, @Param("fileSn") int fileSn);

	/**
	 * 첨부파일 한건 삭제
	 */
	public int deleteFileDetail(@Param("atchFileNo") int atchFileNo, @Param("fileSn") int fileSn);

	/**
	 * 특정글의 첨부파일 그룹 전체 사용중지 처리
	 */
	public int disableFile(@Param("atchFileNo") int atchFileNo);

	/**
	 * 첨부파일 전체 그룹 내의 모든 파일 삭제
	 * 
	 * @param atchFileNo
	 * @return
	 */
	public int deleteDisabledFileDetails(@Param("atchFileNo") int atchFileNo);

	/**
	 * 첨부 파일 그룹 데이터 삭제
	 * 
	 * @param atchFileNo
	 * @return
	 */
	public int deleteDisabledFile(@Param("atchFileNo") int atchFileNo);

}

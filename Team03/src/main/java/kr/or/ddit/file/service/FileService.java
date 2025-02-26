package kr.or.ddit.file.service;

import java.io.File;

import kr.or.ddit.vo.FileVO;
import kr.or.ddit.vo.File_DetailVO;

public interface FileService {
	/**
	 * 첨부파일 그룹 저장(메타데이터와 2진 데이터 분리 저장)
	 * 
	 * @param atchFile
	 * @param saveFolder 2진 데이터 저장 디렉토리
	 * @throws Exception
	 */
	public void createFile(FileVO atchFile, File saveFolder);

	/**
	 * 사용중인 첨부파일 그룹 조회
	 * 
	 * @param atchFileNo
	 * @param saveFolder 2진 데이터 저장 디렉토리
	 * @return
	 */
	public default FileVO readFileEnable(int atchFileNo, File saveFolder) {
		return readFile(atchFileNo, true, saveFolder);
	}

	/**
	 * 사용 여부에 따른 첨부파일 그룹 조회
	 * 
	 * @param atchFileNo
	 * @param enable
	 * @param saveFolder
	 * @return
	 */
	public FileVO readFile(int atchFileNo, boolean enable, File saveFolder);

	/**
	 * 첨부파일 한건 조회
	 * 
	 * @param atchFileNo
	 * @param fileSn
	 * @param saveFolder 2진 데이터 저장 디렉토리
	 * @return
	 */
	public File_DetailVO readFileDetail(int atchFileNo, int fileSn, File saveFolder);

	/**
	 * 파일 한건 삭제 (메타데이터와 2진 데이터 분리 삭제)
	 * 
	 * @param atchFileNo
	 * @param fileSn
	 * @param saveFolder 2진 데이터 저장 디렉토리
	 */
	public void removeFileDetail(int atchFileNo, int fileSn, File saveFolder);

	/**
	 * 파일 그룹 비활성화
	 * 
	 * @param atchFileNo
	 */
	public void disableFile(int atchFileNo);

	/**
	 * 사용하지 않는 파일 그룹 제거
	 * 
	 * @param atchFileNo
	 */
	public void removeDiabledFile(int atchFileNo);
}

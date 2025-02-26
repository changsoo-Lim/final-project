package kr.or.ddit.file.service;

import kr.or.ddit.vo.File_DetailVO;

public interface BaseService {
    File_DetailVO download(int atchFileNo, int fileSn);
    
	void removeFile(int atchFileNo, int fileSn);
}

package kr.or.ddit.file.service;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.function.Failable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import kr.or.ddit.file.dao.FileMapper;
import kr.or.ddit.vo.FileVO;
import kr.or.ddit.vo.File_DetailVO;

@Service
public class FileServiceImpl implements FileService {

	@Autowired
	private FileMapper mapper;

	@Override
	public void createFile(FileVO atchFile, File saveFolder) {
		Optional.of(atchFile)
				.map(FileVO::getFileDetails)
				.ifPresent(fds -> 
					fds.forEach(
						Failable.asConsumer(fd -> fd.uploadFileSaveTo(saveFolder))
					)
				);
		mapper.insertFile(atchFile);
	}

	/**
	 * 
	 * 파일 메타데이터와 2진 데이터 결합
	 * 
	 * @param fileDetail
	 * @param saveFolder
	 */
	private void mergeMetadAndBinaryData(File_DetailVO fileDetail, File saveFolder) {
		FileSystemResource savedFile = new FileSystemResource(new File(saveFolder, fileDetail.getStreFileNm()));
		fileDetail.setSavedFile(savedFile);
	}

	@Override
	public FileVO readFile(int atchFileNo, boolean enable, File saveFolder) {
		FileVO atchFile = mapper.selectFile(atchFileNo, enable);
		Optional.ofNullable(atchFile)
				.map(FileVO::getFileDetails)
				.ifPresent(fds -> 
					fds.forEach(fd -> mergeMetadAndBinaryData(fd, saveFolder))
				);
		return atchFile;
	}

	@Override
	public File_DetailVO readFileDetail(int atchFileNo, int fileSn, File saveFolder) {
		File_DetailVO fileDetail = mapper.selectFileDetail(atchFileNo, fileSn);
		if (fileDetail != null) {
			mergeMetadAndBinaryData(fileDetail, saveFolder);
			mapper.incrementDowncount(atchFileNo, fileSn);
		}
		return fileDetail;
	}

	/**
	 * 파일 한건의 메타데이터와 2진 데이터 삭제
	 * 
	 * @param fileDetail
	 * @param saveFolder
	 * @throws IOException
	 */
	private void deleteFileDetail(File_DetailVO fileDetail, File saveFolder) throws IOException {
		mergeMetadAndBinaryData(fileDetail, saveFolder);
		FileUtils.deleteQuietly(fileDetail.getSavedFile().getFile());
		mapper.deleteFileDetail(fileDetail.getAtchFileNo(), fileDetail.getFileSn());
	}

	@Override
	public void removeFileDetail(int atchFileNo, int fileSn, File saveFolder) {
		File_DetailVO target = mapper.selectFileDetail(atchFileNo, fileSn);
		Optional.ofNullable(target)
				.ifPresent(
					Failable.asConsumer(fd -> 
						deleteFileDetail(fd, saveFolder)
					)
				);
	}

	@Override
	public void disableFile(int atchFileNo) {
		mapper.disableFile(atchFileNo);
	}

	@Override
	public void removeDiabledFile(int atchFileNo) {
		mapper.deleteDisabledFileDetails(atchFileNo);
		mapper.deleteDisabledFile(atchFileNo);
	}

}

package kr.or.ddit.prod.buy.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.file.service.FileService;
import kr.or.ddit.prod.buy.dao.BuyMapper;
import kr.or.ddit.vo.BuyVO;
import kr.or.ddit.vo.EmployVO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BuyServiceImpl implements BuyService{
	
	@Inject
	BuyMapper mapper;
	
	private final FileService fileService;
	
	@Value("#{dirInfo.saveDirProdImage}")
	private Resource saveFolderRes;
	private File saveFolder;
	
	@PostConstruct
	public void init() throws IOException {
		this.saveFolder = saveFolderRes.getFile();
		if(!this.saveFolder.exists()) {
			this.saveFolder.mkdirs();
		}
	}
	
	@Override
	public List<BuyVO> readBuyList(String compId) {
		return mapper.selectBuyList(compId);
	}

	@Override
	public BuyVO readBuy(int buyNo) {
		return mapper.selectBuy(buyNo);
	}

	@Override
	public ServiceResult createBuy(BuyVO buyVO) {
		Integer atchFileNo = Optional.ofNullable(buyVO.getFile())
				.filter(af->! CollectionUtils.isEmpty(af.getFileDetails()))
				.map(af->{
					fileService.createFile(af, saveFolder);
					return af.getAtchFileNo();
				}).orElse(null);
		buyVO.setAtchFileNo(atchFileNo);
		
		if(mapper.insertBuy(buyVO) > 0) {
			return ServiceResult.OK;
		}else {
			return ServiceResult.FAIL;
		}
	}

	@Override
	public List<EmployVO> empList(String compId) {
		return mapper.empList(compId);
	}

}

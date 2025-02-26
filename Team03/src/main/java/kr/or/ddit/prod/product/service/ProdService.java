package kr.or.ddit.prod.product.service;

import java.util.List;

import kr.or.ddit.commons.enumpkg.ServiceResult;
import kr.or.ddit.vo.ProductVO;

public interface ProdService {
	
	public List<ProductVO> readProdList();
	
	public ProductVO readProd(String productCd);
	
	public ServiceResult createtProd(ProductVO productVO);
	
	public ServiceResult modifiyProd(ProductVO productVO);
	
	public ServiceResult removeProd(String productCd);
}

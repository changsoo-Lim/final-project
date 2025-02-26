package kr.or.ddit.prod.product.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.vo.ProductVO;

@Mapper
public interface ProdMapper {
	
	public List<ProductVO> selectProdList();
	
	public ProductVO selectProd(String productCd);
	
	public int insertProd(ProductVO productVO);
	
	public int updateProd(ProductVO productVO);
	
	public int deleteProd(String productCd);
}

package kr.or.ddit.paging.renderer;

import kr.or.ddit.paging.PaginationInfo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultPaginationRenderer implements PaginationRenderer {

   @Override
   public String renderPagination(PaginationInfo paging, String fnName) {
	   
      int startPage = paging.getStartPage();
      int endPage = paging.getEndPage();
      int totalPage = paging.getTotalPage();
      int blockSize = paging.getBlockSize();
      int curruntPage = paging.getCurrentPage();
      
      endPage = endPage > totalPage ? totalPage : endPage;
      
      StringBuffer html = new StringBuffer();
      html.append("<nav aria-label=\"Page navigation example\">\r\n"
            + "  <ul class=\"pagination justify-content-center\">");
      
      String pattern = "<li class='page-item'><a href='javascript:void(0);' class='page-link' onclick='paging(%d);'>%s</a></li>";
      String patternActive = "<li class='page-item active'><a href='javascript:void(0);' class='page-link'>%s</a></li>";
      if(startPage > blockSize) {
         html.append(
               String.format(pattern, startPage-blockSize, "이전")
         );
      }
      for(int page = startPage; page <= endPage; page++) {
         if(page == curruntPage) {
            html.append(
                  String.format(patternActive, page)
                  );
         }else {
            html.append(
                  String.format(pattern, page, page)
                  );
            
         }
      }
      
      if(endPage < totalPage) {
         html.append(
               String.format(pattern, endPage+1, "다음")
         );
      }
      html.append("</ul>\r\n"
            + "</nav>");
      return html.toString();
   }

}

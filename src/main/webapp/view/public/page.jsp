<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<form id="pagerForm" action="<%=request.getContextPath()%>${param.url}" method="post">
	<input type="hidden" name="pageNum" value="${pm.currentPage}" /> 
	<input type="hidden" name="numPerPage" value="${pm.numPerPage}" /> 
	<input type="hidden" name="orderField" value="${param['orderField']}" />
	<input type="hidden" name="orderDirection" value="${param['orderDirection']}" />

</form> 
<div class="panelBar" >
	<div class="pages">
	 <div style=" float: left;">
		<span>显示</span> <select class="combox" name="numPerPage"
			onchange="navTabPageBreak({numPerPage:this.value})">
			<option ${pm.numPerPage == 20 ? 'selected' : ''} value="20">20</option>
			<option ${pm.numPerPage == 50 ? 'selected' : ''} value="50">50</option>
			<option ${pm.numPerPage == 100 ? 'selected' : ''} value="100">100</option>
			<option ${pm.numPerPage == 200 ? 'selected' : ''} value="200">200</option>
		</select> <span>条，共${pm.dataCount}条</span>
		</div>
	</div>
	<div class="pagination" targetType="navTab" 
		totalCount="${pm.dataCount}" pageNumShown="5"
		numPerPage="${pm.numPerPage}" currentPage="${pm.currentPage}"></div>
</div>
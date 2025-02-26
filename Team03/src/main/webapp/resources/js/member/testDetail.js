/**
 * 
 */
function openTestWindow(target) {
    // URL 설정 및 창 열기
    const url = target.dataset.test;
    window.open(url, "TestWindow", "width=1300,height=711,top=100,left=310,resizable=yes,location=no,scrollbars=no");
}

$(()=>{
	
});	
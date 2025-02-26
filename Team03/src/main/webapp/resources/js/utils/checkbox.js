/**
 * 
 */

$(document).ready(function() {
	$(".cbx_chkAll").click(function() {
		if($(".cbx_chkAll").is(":checked")) $(".chk").prop("checked", true);
		else $(".chk").prop("checked", false);
	});

	$(".chk").click(function() {
		var total = $(".chk").length;
		var checked = $(".chk:checked").length;

		if(total != checked) $(".cbx_chkAll").prop("checked", false);
		else $(".cbx_chkAll").prop("checked", true); 
	});
});
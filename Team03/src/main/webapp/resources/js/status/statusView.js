function toggleCheckbox(element) {
	
    const label = document.querySelector(`label[for="${element.id}"]`);
    const state = element.checked ? 'Y' : 'N';

    if (element.checked) {
        label.innerHTML = '🔔 ON';
    } else {
        label.innerHTML = '🔕 OFF';
    }
	const baseUrl = document.body.dataset.url;
    $.ajax({
        url: `${baseUrl}/status`,
        method: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify({
            type: element.id,
            state: state
        }),
        success: function(response) {
            console.log('업데이트 성공:', response);
        },
        error: function(error) {
            console.error('업데이트 실패:', error);
        }
    });
}
window.addEventListener('DOMContentLoaded', function() {
    const checkboxes = document.querySelectorAll('input[type="checkbox"]');
    checkboxes.forEach(function(checkbox) {
        toggleCheckbox(checkbox);
    });
});

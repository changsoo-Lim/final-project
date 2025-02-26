/**
 * 
 */

document.addEventListener("DOMContentLoaded", function () {
    // 데이터를 서버에서 Fetch API로 가져오기
    /*fetch('/admin/resource/data')
        .then(response => response.json()) // JSON 응답을 파싱
        .then(data => {
            const labels = data.map(item => item.resourceTitle); // 리소스 제목
            const downloadCounts = data.map(item => item.fileDwncnt); // 다운로드 수

            // 차트 생성
            const ctx = document.getElementById('downloadChart').getContext('2d');
            new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: labels,
                    datasets: [{
                        label: '다운로드 수',
                        data: downloadCounts,
                        backgroundColor: 'rgba(75, 192, 192, 0.2)',
                        borderColor: 'rgba(75, 192, 192, 1)',
                        borderWidth: 1
                    }]
                },
                options: {
                    responsive: true,
                    plugins: {
                        legend: {
                            display: true,
                            position: 'top',
                        },
                        tooltip: {
                            enabled: true,
                        }
                    },
                    scales: {
                        y: {
                            beginAtZero: true // y축 시작을 0으로 설정
                        }
                    }
                }
            });
        })
        .catch(error => console.error('데이터 가져오기 실패:', error));*/
});
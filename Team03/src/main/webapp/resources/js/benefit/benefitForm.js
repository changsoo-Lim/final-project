document.addEventListener("DOMContentLoaded", () => {
    const addFamButton = document.getElementById("addFamButton");
    const sendFamsButton = document.getElementById("sendFamsButton");
    const famList = document.getElementById("famList");
    const cjFamTemplate = document.querySelector(".cjFam");

    // 목록 추가
    addFamButton.addEventListener("click", () => {
        const famOne = cjFamTemplate.cloneNode(true); // 템플릿 복제
        famOne.style.display = "block"; // 복제된 템플릿 표시
        famOne.classList.add("addedFam"); // 추가된 요소에 클래스 부여
        famOne.querySelector(".revFam").addEventListener("click", () => {
            famOne.remove(); // 삭제 버튼 클릭 시 해당 요소 제거
        });
        famList.appendChild(famOne); // famList에 추가
    });

    // 데이터 전송
    sendFamsButton.addEventListener("click", () => {
        const fams = famList.querySelectorAll(".addedFam"); // 추가된 항목만 선택
        const famsArr = Array.from(fams).map(fam => {
            return {
                compId: fam.querySelector(".compId").value,
                cmpbftTile: fam.querySelector(".cmpbftTile").value,
                cmpbftCont: fam.querySelector(".cmpbftCont").value
            };
        });

        console.log("체크:", famsArr);

        // Fetch API로 데이터 전송
        fetch("../benefit/insert", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(famsArr),
        }).then(response => {
            if (response.ok) {
                console.log("Data sent successfully!");
            } else {
                console.error("Error sending data:", response.statusText);
            }
        }).catch(error => {
            console.error("Fetch error:", error);
        });
    });
});

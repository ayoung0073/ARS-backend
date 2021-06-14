let index = {
    init: function () {
        $("#btn-register").on("click", () => {
            this.register();
        });
        $("#btn-review-register").on("click", () => {
            this.reviewRegister();
        });
    },

    register: function () {
        let tagList = [];
        let tagArr = document.getElementsByClassName("btn-tag");
        let step = $("#step").val();
        console.log(document.getElementsByClassName("tui-editor-contents")[0]);
        for (let i = 0; i < tagArr.length; i++) {
            tagList.push(tagArr[i].value);
        }

        let data = {
            title: $("#title").val(),
            // content: document.getElementsByClassName("tui-editor-contents")[0].innerHTML,
            content: document.getElementsByClassName("tui-editor-contents")[0].innerHTML,
            link: $("#link").val(),
            step: step,
            notificationDate: date_setting(step),
            tagList: tagList
        }

        console.log(data);
        console.log(sessionStorage.getItem("access_token"));

        $.ajax({
            type: "POST",
            url: "/api/problems",
            headers: {"Authorization": sessionStorage.getItem("access_token"), "Content-type": "application/json"},
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function (result) {
            console.log(result);
            alert("글쓰기가 완료되었습니다.");
            location.href = "/";
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    reviewRegister: function () {
        let step = $("#step").val();
        let data = {
            content: document.getElementsByClassName("tui-editor-contents")[0].innerHTML,
            step: step,
            notificationDate: date_setting(step),
        }

        console.log(data);
        console.log(sessionStorage.getItem("access_token"));

        let problemId = document.getElementById("problem-id").value;
        console.log(problemId)

        $.ajax({
            type: "POST",
            url: "/api/problems/" + problemId + "/reviews",
            headers: {"Authorization": sessionStorage.getItem("access_token"), "Content-type": "application/json"},
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function (result) {
            console.log(result);
            alert("글쓰기가 완료되었습니다.");
            location.href = "/problems/" + problemId + "?index=1";
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    }

function date_setting(step) {
    console.log(step);
    let today = new Date();
    let date = new Date();

    switch (step) {
        case "1":
            date.setMonth(today.getMonth() + 3); // 3달 후
            break;
        case "2":
            date.setMonth(today.getMonth() + 2); // 2달 후
            break;
        case "3":
            date.setMonth(today.getMonth() + 1); // 1달 후
            break;
        case "4":
            date.setDate(today.getDate() + 14); // 2주 후
            break;
        case "5":
            date.setDate(today.getDate() + 7); // 1주 후
            break;
    }

    let year = date.getFullYear();
    let month = date.getMonth() + 1;
    let day = date.getDate();

    month = month >= 10 ? month : '0' + month; // 날짜 포맷 맞추기
    day = day >= 10 ? day : '0' + day;

    console.log(year + "-" + month + "-" + day);
    return year + "-" + month + "-" + day;
}


index.init()
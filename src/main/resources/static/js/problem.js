let index = {
    init: function () {
        // $("#btn-register").on("click", () => {
        //     this.register();
        // });
        // $("#btn-review-register").on("click", () => {
        //     this.registerReview();
        // });
    },

    register: function () {
        let tagList = [];
        let tagArr = document.getElementsByClassName("btn-tag");
        let content = document.getElementsByClassName("codemirror-lines")[0].innerText;
        console.log(content);

        let step = $("#step").val();
        for (let i = 0; i < tagArr.length; i++) {
            tagList.push(tagArr[i].value);
        }

        let data = {
            title: $("#title").val(),
            content: content,
            link: $("#link").val(),
            step: step,
            notificationDate: date_setting($("#notificationDate").val()),
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

    registerReview: function () {
        let content = document.getElementsByClassName("codemirror-lines")[0].innerText;
        console.log(content);
        let data = {
            content: content,
            notificationDate: date_setting($("#notificationDate").val()),
        }

        let problemId = document.getElementById("problem-id").value;

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

    updateProblem: function (problemId) {
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
            content: document.getElementById("editor").innerText,
            link: $("#link").val(),
            notificationDate: date_setting(step),
            tagList: tagList
        }

        $.ajax({
            type: "PUT",
            url: "/api/problems/" + problemId,
            headers: {"Authorization": sessionStorage.getItem("access_token"), "Content-type": "application/json"},
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function (result) {
            console.log(result);
            alert("글 수정이 완료되었습니다.");
            location.href = "/problems/" + problemId + "?index=1";
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    updateStep: function (problemId, step) {
        let data = {
            step: step
        }

        $.ajax({
            type: "PUT",
            url: "/api/problems/" + problemId + "/step",
            headers: {"Authorization": sessionStorage.getItem("access_token"), "Content-type": "application/json"},
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function (result) {
            console.log(result);
        }).fail(function () {
            alert("수정 권한 없습니다.");
            location.reload();
        });
    },

    deleteProblem: function (problemId) {
        $.ajax({
            type: "DELETE",
            url: "/api/problems/" + problemId,
            headers: {"Authorization": sessionStorage.getItem("access_token"), "Content-type": "application/json"},
            dataType: "json"
        }).done(function (result) {
            console.log(result);
            alert("글 삭제 완료하였습니다.");
            location.href = "/problems/" + problemId + "?index=1";
        }).fail(function (error) {
            alert("삭제 권한이 없습니다.");
        });
    },

     updateReview: function (problemId, reviewId) {
         let tagList = [];
         let content = document.getElementsByClassName("codemirror-lines")[0].innerText;
         let tagArr = document.getElementsByClassName("btn-tag");
         let step = $("#step").val();
         let notificationDate = date_setting(step);
         console.log(step)
         if (isNaN(step)) { // 숫자가 아닌 경우, 즉 날짜인 경우
             console.log(step);
             step = $("#review-step").val();
             console.log(step);
         }
         for (let i = 0; i < tagArr.length; i++) {
             tagList.push(tagArr[i].value);
         }

         let data = {
             title: $("#title").val(),
             content: content,
             link: $("#link").val(),
             notificationDate: notificationDate,
             tagList: tagList,
             step: step
         }

            $.ajax({
                type: "PUT",
                url: "/api/problems/" + problemId + "/reviews/" + reviewId,
                headers: {"Authorization": sessionStorage.getItem("access_token"), "Content-type": "application/json"},
                data: JSON.stringify(data),
                contentType: "application/json; charset=utf-8",
                dataType: "json"
            }).done(function (result) {
                console.log(result);
                alert("글 수정이 완료되었습니다.");
                location.href = "/problems/" + problemId + "?index=1";
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });
        },

        deleteReview: function (reviewId) {
            $.ajax({
                type: "DELETE",
                url: "/api/reviews/" + reviewId,
                headers: {"Authorization": sessionStorage.getItem("access_token"), "Content-type": "application/json"},
                dataType: "json"
            }).done(function (result) {
                console.log(result);
                alert("리뷰 삭제 완료하였습니다.");
                location.href = "/";
            }).fail(function (error) {
                alert("삭제 권한이 없습니다.");
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
        default:
            date.setMonth(today.getMonth() + 3); // 3달 후
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
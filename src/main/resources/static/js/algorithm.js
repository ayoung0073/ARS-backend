let index = {
    init: function () {
        $("#btn-register").on("click", () => {
            this.register();
        });
    },

    register: function () {

        let tagList = [];
        let tagArr = document.getElementsByClassName("btn-tag");
        for (let i = 0; i < tagArr.length; i++) {
            tagList.push(tagArr[i].value);
        }

        let data = {
            title: $("#title").val(),
            content: $("#markdown").val(),
            link: $("#link").val(),
            step: $("#step").val(),
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



}

function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);
    return results == null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}


index.init()
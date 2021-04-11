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
            review: $("#markdown").text(),
            link: $("#link").val(),
            tagList: tagList
        }

        console.log(data);

        $.ajax({
            type: "POST",
            url: "/api/problem",
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
    }

}

index.init()
let index = {
    init: function () {
        $("#btn-register").on("click", () => {
            this.register();
        });
    },

    register: function () {

        let tag = [];
        let tagArr = document.getElementsByClassName("btn-tag");
        for (let i = 0; i < tagArr.length; i++) {
            tag.push(tagArr[i].value);
        }

        let data = {
            title: $("#title").val(),
            content: $("#content").val(),
            link: $("#link").val(),
            tag: tag
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
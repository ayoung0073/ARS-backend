let nickname = sessionStorage.getItem("nickname");
if (nickname == null) {
        document.write(`
            <a class="nav-link" href="/user/login">login</a>
        `);
} else {
    document.write("&nbsp;&nbsp;" + nickname + "님 환영합니다!");
}
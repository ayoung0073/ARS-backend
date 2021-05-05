// let index = {
//     init: function () {
//         $("#btn-kakao").on("click", () => {
//             this.kakaoLogin();
//         });
//         $("#btn-google").on("click", () => {
//             this.googleLogin();
//         });
//     },
//
//
//     kakaoLogin: function () {
//         console.log("카카오 버튼 클릭");
//         $.ajax({
//             type: "GET",
//             url: "https://kauth.kakao.com/oauth/authorize?client_id=5354f1c1ec547c553355a726f5d4b288&redirect_uri=http://localhost:8080/user/kakao/callback&response_type=code"
//         }).done(function (result) {
//             console.log(result);
//             if (result.status === 500) {
//                 alert("회원가입에 실패하였습니다.")
//             } else {
//                 alert("회원가입이 완료되었습니다.");
//                 location.href = "/";
//             }
//         }).fail(function (error) {
//             alert(JSON.stringify(error));
//         });
//
//     },
//
// }
//     index.init();

// =============================================================
// 로그인 화면
//
// 대응 주제
//   토큰 발급
//
// 변경 지점
//   [토큰 발급]  handleLogin 함수
// =============================================================

// 로그인 처리.
//
// [토큰 발급] 주제에서 서버에 로그인 주소가 추가됨.
// 그 이전에는 호출해도 404 로 응답하므로 화면만 존재하는 상태.
async function handleLogin() {
    const email = document.getElementById('email').value.trim();
    const password = document.getElementById('password').value;

    if (!email) {
        showMessage('이메일을 입력');
        return;
    }
    if (!password) {
        showMessage('비밀번호를 입력');
        return;
    }

    try {
        const result = await login({ email, password });

        // 응답에 토큰이 포함되므로 함께 보관.
        // 이후 요청의 머리에 담아 신원을 증명.
        sessionStorage.setItem('accessToken', result.accessToken);
        sessionStorage.setItem('nickname', result.nickname);
        sessionStorage.setItem('memberId', result.memberId);

        location.href = '/index.html';
    } catch (e) {
        if (e.status === 401 || e.status === 400) {
            showMessage('이메일 또는 비밀번호가 올바르지 않음');
            return;
        }
        if (e.status === 404) {
            showMessage('로그인 주소가 아직 준비되지 않음');
            return;
        }
        showMessage('로그인하지 못함');
    }
}

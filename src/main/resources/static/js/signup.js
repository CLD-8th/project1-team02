// =============================================================
// 회원 가입 화면
//
// 대응 주제
//   화면 연동
// =============================================================

async function handleSignup() {
    const email = document.getElementById('email').value.trim();
    const nickname = document.getElementById('nickname').value.trim();
    const password = document.getElementById('password').value;
    const passwordConfirm = document.getElementById('password-confirm').value;

    if (!email) {
        showMessage('이메일을 입력');
        return;
    }
    if (!nickname) {
        showMessage('닉네임을 입력');
        return;
    }
    if (!password) {
        showMessage('비밀번호를 입력');
        return;
    }

    // 비밀번호 확인은 화면에서만 비교.
    // 서버로 전달하지 않으며 요청에는 비밀번호 하나만 담김.
    if (password !== passwordConfirm) {
        showMessage('비밀번호가 일치하지 않음');
        return;
    }

    try {
        await signup({ email, password, nickname });
        alert('가입 완료. 로그인 화면으로 이동');
        location.href = '/login.html';
    } catch (e) {
        if (e.status === 400) {
            showMessage('이미 가입된 이메일이거나 값이 올바르지 않음');
            return;
        }
        handleError(e, '가입하지 못함');
    }
}

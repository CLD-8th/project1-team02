// =============================================================
// 공통 동작
//
// 머리와 꼬리는 각 화면의 HTML 에 직접 배치.
// 여기서는 로그인 상태에 따라 달라지는 부분과 안내 표시를 담당.
//
// 대응 주제
//   화면 연동 · 토큰 발급 · 토큰 저장과 차단
// =============================================================

// 머리 오른쪽 표시.
//
// [토큰 발급] 주제 이전에는 보관된 값이 없으므로 항상 로그인과 가입이 표시.
// 해당 주제에서 로그인이 동작하면 별명과 로그아웃으로 전환.
function renderHeaderUser() {
    const area = document.getElementById('header-user');
    if (!area) {
        return;
    }
    const nickname = getNickname();

    area.innerHTML = nickname
        ? `<a href="/mypage.html" class="nickname">${escapeHtml(nickname)}</a>
           <button class="btn-text" onclick="handleLogout()">로그아웃</button>`
        : `<a href="/login.html"><button class="btn-text">로그인</button></a>
           <a href="/signup.html"><button class="btn-primary">가입</button></a>`;
}

// 로그인 상태 확인.
//
// [토큰 발급] 주제에서 로그인 성공 시 이 값이 채워짐.
// 그 이전에는 항상 빈 값이므로 로그인하지 않은 상태로 처리.
function getNickname() {
    return sessionStorage.getItem('nickname');
}

function getMemberId() {
    const id = sessionStorage.getItem('memberId');
    return id ? Number(id) : null;
}

function isLoggedIn() {
    return getNickname() !== null;
}

// 로그아웃.
//
// [토큰 저장과 차단] 주제 이전에는 보관된 값만 제거.
// 해당 주제에서 서버에 알려 토큰을 차단 목록에 등록하도록 변경.
function handleLogout() {
    // 서버에 알려 갱신 토큰을 제거하고 접근 토큰을 차단.
    //
    // 실패해도 화면의 값은 제거.
    // 서버에 알리지 못해도 이 기기에서는 사용할 수 없어야 함.
    logout().catch(() => {});

    clearSession();
    location.href = '/index.html';
}

function clearSession() {
    sessionStorage.removeItem('accessToken');
    sessionStorage.removeItem('nickname');
    sessionStorage.removeItem('memberId');
}

// 안내 표시.
//
// 실패 사유를 화면에 표시. 응답 코드로 구분한 뒤 문구를 전달.
function showMessage(text, type = 'error') {
    const area = document.getElementById('message');
    if (!area) {
        return;
    }
    area.textContent = text;
    area.className = `message show ${type}`;
}

function clearMessage() {
    const area = document.getElementById('message');
    if (!area) {
        return;
    }
    area.className = 'message';
}

// 실패 응답 처리.
//
// 인증이 필요한 상태이면 로그인 화면으로 이동.
// 그 외에는 전달받은 문구를 표시.
function handleError(error, defaultText) {
    if (error.status === 401) {
        clearSession();
        location.href = '/login.html';
        return;
    }
    if (error.status === 403) {
        showMessage('권한이 없음');
        return;
    }
    showMessage(defaultText);
}

document.addEventListener('DOMContentLoaded', renderHeaderUser);

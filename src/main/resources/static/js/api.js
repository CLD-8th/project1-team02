// =============================================================
// 서버 호출 담당
//
// 화면마다 호출 코드를 두지 않고 여기에 모음.
// 공통 처리가 한곳에 있으므로 주소나 머리 값이 바뀌어도 화면이 변하지 않음.
//
// 대응 주제
//   화면 연동 · 토큰 발급 · 토큰 저장과 차단
// =============================================================

// 서버 주소.
// 빈 값이면 현재 주소를 그대로 사용하므로 화면과 서버가 같은 출처가 됨.
// 화면을 별도 서버로 띄우면 여기에 절대 주소를 지정하며 그 경우 다른 출처가 됨.
const BASE_URL = '';

class ApiError extends Error {
    constructor(status, body) {
        super('요청 실패');
        this.status = status;
        this.body = body;
    }
}

async function request(path, options = {}) {
    const headers = { 'Content-Type': 'application/json' };

    // 로그인으로 받은 토큰을 모든 요청의 머리에 부착.
    // 서버가 이 값의 서명을 확인해 요청자를 판단.
    const token = sessionStorage.getItem('accessToken');
    if (token) {
        headers['Authorization'] = 'Bearer ' + token;
    }

    const response = await fetch(BASE_URL + path, {
        method: options.method || 'GET',
        headers: headers,
        body: options.body ? JSON.stringify(options.body) : undefined
    });

    const text = await response.text();
    const data = text ? JSON.parse(text) : null;

    if (!response.ok) {
        throw new ApiError(response.status, data);
    }
    return data;
}

// =============================================================
// 게시글
// =============================================================

// 목록 조회.
//
// [페이징과 정렬] 주제 이전에는 params 에 keyword 만 전달.
// 해당 주제에서 page · size · sort 를 함께 전달.
function getNotices(params) {
    const query = params ? '?' + new URLSearchParams(params).toString() : '';
    return request('/notices' + query);
}

function getPost(id) {
    return request('/notices' + id);
}

function createPost(body) {
    return request('/notices', { method: 'POST', body });
}

// =============================================================
// 회원
// =============================================================

// 가입.
// [화면 연동] 주제에서 서버에 해당 주소가 추가됨.
function signup(body) {
    return request('/members', { method: 'POST', body });
}

// 회원 조회.
// [화면 연동] 주제에서 서버에 해당 주소가 추가됨.
function getMember(id) {
    return request(`/members/${id}`);
}

// 회원 정보 수정.
//
// [엔티티와 매핑] 주제에서 서버에 해당 주소가 추가됨.
// 그 이전에는 호출해도 404 로 응답.
function updateMember(id, body) {
    return request(`/members/${id}`, { method: 'PUT', body });
}

// 회원 탈퇴.
//
// [인가와 권한 확인] 주제에서 서버에 해당 주소가 추가됨.
// 본인 확인이 가능한 시점에 도입.
function deleteMember(id) {
    return request(`/members/${id}`, { method: 'DELETE' });
}

// 로그인.
//
// [저장소 규약] 주제에서 서버에 해당 주소가 추가됨.
// 이메일로 회원을 찾아 비밀번호를 대조.
//
// [토큰 발급] 주제부터는 응답에 토큰이 함께 포함됨.
function login(body) {
    return request('/auth/login', { method: 'POST', body });
}

// 로그아웃.
// [토큰 저장과 차단] 주제에서 서버에 해당 주소가 추가됨.
function logout() {
    return request('/auth/logout', { method: 'POST' });
}

// =============================================================
// 공통 도구
// =============================================================

function escapeHtml(text) {
    if (text === null || text === undefined) {
        return '';
    }
    return String(text)
        .replaceAll('&', '&amp;')
        .replaceAll('<', '&lt;')
        .replaceAll('>', '&gt;');
}

function formatDate(value) {
    if (!value) {
        return '-';
    }
    return value.substring(0, 10);
}

function formatDateTime(value) {
    if (!value) {
        return '-';
    }
    return value.substring(0, 16).replace('T', ' ');
}

function getQueryParam(name) {
    return new URLSearchParams(location.search).get(name);
}

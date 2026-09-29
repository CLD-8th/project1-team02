// =============================================================
// 목록 화면
//
// 대응 주제
//   화면 연동 · 엔티티와 매핑 · 연관관계 매핑
//   요청 응답 형태 분리 · 페이징과 정렬
//
// 변경 지점
//   [페이징과 정렬]  load 함수 · render 함수
// =============================================================

// [페이징과 정렬] 주제에서 사용. 그 이전에는 값이 바뀌지 않음.
let currentPage = 0;
const PAGE_SIZE = 10;

async function load() {
    clearMessage();
    const department = document.getElementById('department').value.trim();

    // 조회 조건 구성.
    const params = {};
    if (department) {
        params.department = department;
    }

    try {
        const posts = await getNotices(Object.keys(params).length ? params : null);
        render(posts);
    } catch (e) {
        handleError(e, '공지 목록을 불러오지 못함');
    }
}

function render(posts) {
    const list = document.getElementById('list');

    const items = posts;
    document.getElementById('count').textContent = items.length;

    if (items.length === 0) {
        list.innerHTML = '<tr><td colspan="4" class="empty">등록된 공지가 없음</td></tr>';
        return;
    }

    list.innerHTML = items.map(post => `
        <tr>
            <td class="col-id">${post.id}</td>
            <td class="title">
                <a href="/post.html?id=${post.id}">${escapeHtml(post.title)}</a>
                ${commentMark(post)}
            </td>
            <td class="col-writer">${escapeHtml(writerOf(post))}</td>
            <td class="col-department">${escapeHtml(post.department)}</td>
            <td class="col-date">${formatDate(post.createdAt)}</td>
        </tr>`).join('');
}

// 작성자 표시.
//
// [요청 응답 형태 분리] 주제 이전에는 저장 형태가 그대로 전달되므로
// 작성자가 객체이며 그 안의 별명을 꺼내야 함.
// 해당 주제 이후에는 별명만 담긴 항목이 전달.
function writerOf(post) {
    if (post.writerNickname) {
        return post.writerNickname;
    }
    if (post.writer && post.writer.nickname) {
        return post.writer.nickname;
    }
    return '-';
}

// 댓글 수 표시.
//
// [연관관계 매핑] 주제에서 서버가 이 값을 전달하기 시작.
// 그 이전에는 값이 없으므로 표시가 부재.
function commentMark(post) {
    if (!post.commentCount) {
        return '';
    }
    return `<span class="reply">[${post.commentCount}]</span>`;
}

// -------------------------------------------------------------
// [페이징과 정렬] 주제에서 사용.
// 위 render 함수의 적용하면 이 함수가 호출됨.
// -------------------------------------------------------------
function renderPagination(totalPages, current) {
    const area = document.getElementById('pagination');
    if (!totalPages || totalPages <= 1) {
        area.innerHTML = '';
        return;
    }

    const buttons = [];
    for (let i = 0; i < totalPages; i++) {
        buttons.push(
            `<button class="${i === current ? 'current' : ''}" onclick="moveTo(${i})">${i + 1}</button>`);
    }
    area.innerHTML = buttons.join('');
}

function moveTo(page) {
    currentPage = page;
    load();
}

function search() {
    currentPage = 0;
    load();
}

document.addEventListener('DOMContentLoaded', () => {
    document.getElementById('department').addEventListener('keydown', e => {
        if (e.key === 'Enter') {
            search();
        }
    });
    load();
});

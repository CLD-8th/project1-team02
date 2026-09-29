const formId = getQueryParam('id');

function clearErrors() {
    document.querySelectorAll('.field-error').forEach(el => {
        el.textContent = '';
        el.classList.add('hidden');
    });
    const saveError = document.getElementById('save-error');
    if (saveError) {
        saveError.textContent = '';
        saveError.classList.add('hidden');
    }
}

function showFieldError(fieldId, message) {
    const errorEl = document.getElementById(`${fieldId}-error`);
    if (errorEl) {
        errorEl.textContent = message;
        errorEl.classList.remove('hidden');
    }
}

function showGlobalError(message) {
    const saveError = document.getElementById('save-error');
    if (saveError) {
        saveError.textContent = message;
        saveError.classList.remove('hidden');
    }
}

// 수정일 때 기존 데이터 불러오기
async function initForm() {
    if (formId) {
        const titleEl = document.getElementById('page-title');
        if (titleEl) titleEl.textContent = '공지사항 수정';
        document.title = '공지사항 수정';

        try {
            const data = await request(`/notices/${formId}`);
            document.getElementById('title').value = data.title || '';
            document.getElementById('content').value = data.content || '';
            if (data.department) {
                document.getElementById('department').value = data.department;
            }
        } catch (error) {
            showGlobalError('데이터를 불러오지 못했습니다.');
        }
    }
}

// 저장 및 등록 처리
async function saveForm() {
    clearErrors();

    const title = document.getElementById('title').value.trim();
    const content = document.getElementById('content').value.trim();
    const department = document.getElementById('department').value;

    let hasError = false;
    if (!title) {
        showFieldError('title', '제목을 입력하세요.');
        hasError = true;
    }
    if (!content) {
        showFieldError('content', '내용을 입력하세요.');
        hasError = true;
    }
    if (hasError) return;

    const payload = {
        title: title,
        content: content,
        department: department
    };

    const currentMemberId = sessionStorage.getItem('memberId') || 1;

    try {
        let result;
        if (formId) {
            result = await request(`/notices/${formId}`, { method: 'PUT', body: payload });
        } else {
            result = await request(`/notices?memberId=${currentMemberId}`, {
                method: 'POST',
                body: payload
            });
        }

        const targetId = result && result.id ? result.id : formId;
        if (targetId) {
            location.href = `/detail.html?id=${targetId}`;
        } else {
            location.href = '/index.html';
        }
    } catch (error) {
        if (error.body && error.body.message) {
            showGlobalError(error.body.message);
        } else {
            showGlobalError('저장 중 오류가 발생했습니다.');
        }
    }
}

document.addEventListener('DOMContentLoaded', () => {
    document.getElementById('save').addEventListener('click', saveForm);
    document.getElementById('cancel').addEventListener('click', () => history.back());
    initForm();
});
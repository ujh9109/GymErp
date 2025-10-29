--------------------------------------------------------
-- 1. EMPLOYEE
--------------------------------------------------------
INSERT INTO EMPLOYEE VALUES (1, '김구라', '남', '서울시 강남구', DATE '1990-03-12', '010-1111-2222', 'dhkim@test.com', SYSDATE, NULL, NULL, '트레이너1', 'TRAINER');
INSERT INTO EMPLOYEE VALUES (2, '박종복', '남', '서울시 마포구', DATE '1992-07-08', '010-3333-4444', 'pjb@test.com', SYSDATE, NULL, NULL, '트레이너2', 'TRAINER');
INSERT INTO EMPLOYEE VALUES (3, '서종훈', '남', '서울시 송파구', DATE '1994-05-20', '010-5555-6666', 'sjh@test.com', SYSDATE, NULL, NULL, '매니저', 'ADMIN');

--------------------------------------------------------
-- 2. MEMBER
--------------------------------------------------------
INSERT INTO MEMBER VALUES (1, '윤성완', '남', DATE '1997-07-02', '010-2222-3333', 'ysw@test.com', '서울 강남구', SYSDATE, SYSDATE, NULL, '단골 회원');
INSERT INTO MEMBER VALUES (2, '이서연', '여', DATE '1998-11-15', '010-7777-8888', 'lsy@test.com', '서울 마포구', SYSDATE, SYSDATE, NULL, '신규 회원');
INSERT INTO MEMBER VALUES (3, '김태현', '남', DATE '1995-09-09', '010-9999-0000', 'kth@test.com', '서울 송파구', SYSDATE, SYSDATE, NULL, NULL);

--------------------------------------------------------
-- 3. VOUCHER_LOG
--------------------------------------------------------
INSERT INTO VOUCHER_LOG VALUES (1, 1, '윤성완', DATE '2025-01-01', DATE '2025-03-01');
INSERT INTO VOUCHER_LOG VALUES (2, 2, '이서연', DATE '2025-02-01', DATE '2025-04-01');
INSERT INTO VOUCHER_LOG VALUES (3, 3, '김태현', DATE '2025-03-01', DATE '2025-05-01');

--------------------------------------------------------
-- 4. PT_LOG
--------------------------------------------------------
INSERT INTO PT_LOG VALUES (1, 1, 1, '김구라', '윤성완', '완료', 10, 500000, 500000, SYSDATE);
INSERT INTO PT_LOG VALUES (2, 2, 2, '박종복', '이서연', '진행중', 5, 250000, 125000, SYSDATE);
INSERT INTO PT_LOG VALUES (3, 3, 3, '서종훈', '김태현', '예약', 8, 400000, 0, SYSDATE);

--------------------------------------------------------
-- 5. SCHEDULE
--------------------------------------------------------
INSERT INTO SCHEDULE VALUES (1, 1, SYSTIMESTAMP, SYSTIMESTAMP, 'PT 10회 세션', '#FFAA00', 'PT', 1);
INSERT INTO SCHEDULE VALUES (2, 2, SYSTIMESTAMP, SYSTIMESTAMP, '회의', '#00AAFF', 'ETC', 2);
INSERT INTO SCHEDULE VALUES (3, 3, SYSTIMESTAMP, SYSTIMESTAMP, '근무', '#00FFAA', 'WORK', 3);

--------------------------------------------------------
-- 6. ATTENDANCE
--------------------------------------------------------
INSERT INTO ATTENDANCE VALUES (1, 1, SYSDATE, SYSTIMESTAMP, SYSTIMESTAMP, 8, '출근');
INSERT INTO ATTENDANCE VALUES (2, 2, SYSDATE, SYSTIMESTAMP, SYSTIMESTAMP, 7, '출근');
INSERT INTO ATTENDANCE VALUES (3, 3, SYSDATE, SYSTIMESTAMP, SYSTIMESTAMP, 9, '출근');

--------------------------------------------------------
-- 7. VACATION
--------------------------------------------------------
INSERT INTO VACATION VALUES (1, 1, DATE '2025-02-01', DATE '2025-02-05', '연차', '승인', 10, 7, 3);
INSERT INTO VACATION VALUES (2, 2, DATE '2025-03-10', DATE '2025-03-12', '개인사유', '대기', 10, 9, 1);
INSERT INTO VACATION VALUES (3, 3, DATE '2025-04-15', DATE '2025-04-20', '휴식', '승인', 15, 14, 1);

--------------------------------------------------------
-- 8. ETC
--------------------------------------------------------
INSERT INTO ETC VALUES (1, 1, SYSTIMESTAMP, DATE '2025-01-15', DATE '2025-01-16', SYSTIMESTAMP, '회의 준비', '회의');
INSERT INTO ETC VALUES (2, 2, SYSTIMESTAMP, DATE '2025-02-10', DATE '2025-02-10', SYSTIMESTAMP, '고객상담', '상담');
INSERT INTO ETC VALUES (3, 3, SYSTIMESTAMP, DATE '2025-03-01', DATE '2025-03-01', SYSTIMESTAMP, '운영회의', '기타');

--------------------------------------------------------
-- 9. EMPSCHEDULE
--------------------------------------------------------
INSERT INTO EMPSCHEDULE VALUES (1, 1, 1, SYSTIMESTAMP, SYSTIMESTAMP, 'PT 수업', '#FFB6C1', 'PT', 1);
INSERT INTO EMPSCHEDULE VALUES (2, 2, 2, SYSTIMESTAMP, SYSTIMESTAMP, '상담', '#FFD700', 'ETC', 2);
INSERT INTO EMPSCHEDULE VALUES (3, 3, 3, SYSTIMESTAMP, SYSTIMESTAMP, '회의', '#00FFFF', 'WORK', 3);

--------------------------------------------------------
-- 10. REGISTRATION
--------------------------------------------------------
INSERT INTO REGISTRATION VALUES (1, 1, 1, 1, SYSTIMESTAMP, DATE '2025-01-01', DATE '2025-01-30', DATE '2025-01-05', SYSTIMESTAMP, 'PT 10회 등록');
INSERT INTO REGISTRATION VALUES (2, 2, 2, 2, SYSTIMESTAMP, DATE '2025-02-01', DATE '2025-02-28', DATE '2025-02-03', SYSTIMESTAMP, '상담 등록');
INSERT INTO REGISTRATION VALUES (3, 3, 3, 3, SYSTIMESTAMP, DATE '2025-03-01', DATE '2025-03-31', DATE '2025-03-02', SYSTIMESTAMP, '근무 등록');

--------------------------------------------------------
-- 11. CODEA
--------------------------------------------------------
INSERT INTO CODEA VALUES ('SERVICE', '서비스');
INSERT INTO CODEA VALUES ('PRODUCT', '실물상품');
INSERT INTO CODEA VALUES ('CATEGORY', '카테고리');

--------------------------------------------------------
-- 12. CODEB
--------------------------------------------------------
INSERT INTO CODEB VALUES ('PT_BASIC', 'SERVICE', 'PT 10회권');
INSERT INTO CODEB VALUES ('SUPPLEMENT', 'PRODUCT', '보충제');
INSERT INTO CODEB VALUES ('EQUIP', 'PRODUCT', '운동기구');

--------------------------------------------------------
-- 13. SERVICE
--------------------------------------------------------
INSERT INTO SERVICE VALUES (1, 1, 'PT_BASIC', 'PT 10회권', 500000, 1, SYSDATE, NULL, 10);
INSERT INTO SERVICE VALUES (2, 2, 'PT_BASIC', 'PT 20회권', 900000, 1, SYSDATE, NULL, 20);
INSERT INTO SERVICE VALUES (3, 3, 'PT_BASIC', 'PT 30회권', 1300000, 1, SYSDATE, NULL, 30);

--------------------------------------------------------
-- 14. PRODUCT
--------------------------------------------------------
INSERT INTO PRODUCT VALUES (1, 'SUPPLEMENT', '단백질 보충제', 30000, 1, SYSDATE, NULL);
INSERT INTO PRODUCT VALUES (2, 'EQUIP', '요가매트', 25000, 1, SYSDATE, NULL);
INSERT INTO PRODUCT VALUES (3, 'EQUIP', '아령세트', 40000, 1, SYSDATE, NULL);

--------------------------------------------------------
-- 15. STOCKADJUSTMENT
--------------------------------------------------------
INSERT INTO STOCKADJUSTMENT VALUES (1, 1, 'SUPPLEMENT', SYSDATE, 5, '테스트 출고');
INSERT INTO STOCKADJUSTMENT VALUES (2, 2, 'EQUIP', SYSDATE, 2, '재고 수정');
INSERT INTO STOCKADJUSTMENT VALUES (3, 3, 'EQUIP', SYSDATE, 1, '테스트 출고');

--------------------------------------------------------
-- 16. PURCHASE
--------------------------------------------------------
INSERT INTO PURCHASE VALUES (1, 1, 'SUPPLEMENT', SYSDATE, 100, '초기입고');
INSERT INTO PURCHASE VALUES (2, 2, 'EQUIP', SYSDATE, 50, '추가입고');
INSERT INTO PURCHASE VALUES (3, 3, 'EQUIP', SYSDATE, 30, '테스트입고');

--------------------------------------------------------
-- 17. SALES_ITEM
--------------------------------------------------------
INSERT INTO SALES_ITEM VALUES (1, 1, 1, '단백질 보충제', 2, 30000, 60000, 'PRODUCT', 'ACTIVE', SYSDATE, NULL);
INSERT INTO SALES_ITEM VALUES (2, 2, 2, '요가매트', 1, 25000, 25000, 'PRODUCT', 'ACTIVE', SYSDATE, NULL);
INSERT INTO SALES_ITEM VALUES (3, 3, 3, '아령세트', 3, 40000, 120000, 'PRODUCT', 'ACTIVE', SYSDATE, NULL);

--------------------------------------------------------
-- 18. SALES_SERVICE
--------------------------------------------------------
INSERT INTO SALES_SERVICE VALUES (1, 1, 1, 1, 'PT 10회권', 10, 10, 0, 500000, 500000, 50000, 'SERVICE', 'ACTIVE', SYSDATE, NULL);
INSERT INTO SALES_SERVICE VALUES (2, 2, 2, 2, 'PT 20회권', 20, 20, 0, 900000, 900000, 45000, 'SERVICE', 'ACTIVE', SYSDATE, NULL);
INSERT INTO SALES_SERVICE VALUES (3, 3, 3, 3, 'PT 30회권', 30, 30, 0, 1300000, 1300000, 43333, 'SERVICE', 'ACTIVE', SYSDATE, NULL);

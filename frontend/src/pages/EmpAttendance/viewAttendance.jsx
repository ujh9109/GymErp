// src/pages/EmpAttendance/admin.jsx
import axios from "axios";
import { useEffect, useMemo, useState } from "react";
import { Alert, Button, Form, InputGroup, Table } from "react-bootstrap";
import { useSearchParams } from "react-router-dom";

// 전체조회 프로토타입
// const fetchAll = () => {
//   setLoading(true);
//   setError("");
//   fetch(`/api/v1/attendance`)
//     .then(async (res) => {
//       if (!res.ok) throw new Error((await res.text()) || "전체 목록 조회 실패");
//       return res.json();
//     })
//     .then((data) => setRows(Array.isArray(data) ? data : []))
//     .catch((e) => setError(e.message || "에러"))
//     .finally(() => setLoading(false));
// };

// 사번 검색 프로토타입
// const fetchByEmp = (n) => {
//   if (!n) return fetchAll();
//   setLoading(true);
//   setError("");
//   const qs = new URLSearchParams();
//   qs.set("empNum", n);
//   fetch(`/api/v1/attendance?${qs.toString()}`)
//     .then(async (res) => {
//       if (!res.ok) throw new Error((await res.text()) || "직원별 목록 조회 실패");
//       return res.json();
//     })
//     .then((data) => setRows(Array.isArray(data) ? data : []))
//     .catch((e) => setError(e.message || "에러"))
//     .finally(() => setLoading(false));
// };

function EmpAttendanceView() {
  const [params, setParams] = useSearchParams();

  const [empNum, setEmpNum] = useState(params.get("empNum") || "");
  const [rows, setRows] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const fmtTime = (s) => (s ? s.replace("T", " ").slice(0, 19) : "");
  const fmtDur = (sec) => {
    if (sec == null) return "";
    const h = Math.floor(sec / 3600);
    const m = Math.floor((sec % 3600) / 60);
    return `${h}h ${m}m`;
  };

  // 전체 조회
  const fetchAll = async () => {
    setLoading(true);
    setError("");
    fetch(`/api/v1/attendance`)
    try {
      const { data } = await axios.get('/api/v1/attendance');
      setRows(Array.isArray(data) ? data : []);
    } catch (err) {
      setError(err.response?.data?.message || err.message || "전체목록 조회 실패");
    } finally {
      setLoading(false)
    }


  }
  // 사번 검색
  const fetchByEmp = async (empNum) => {
    if (!empNum) return fetchAll();
    setLoading(true);
    setError("");
    try {
      const res = await axios.get('/api/v1/attendance', { params: { empNum } });
      setRows(Array.isArray(res.data) ? res.data : []);
    } catch (err) {
      setError(err.response?.data?.message || err.message || "직원별 목록 조회 실패");
      setRows([]);
    } finally {
      setLoading(false);
    }
  }

  // ✅ 쿼리스트링과 상태 동기화 + 라우트 변경에만 반응해서 조회
  useEffect(() => {
    const p = params.get("empNum") || "";
    setEmpNum(p); // 입력창과 동기화
    if (p) fetchByEmp(p);
    else fetchAll();
  }, [params]); // ← URL 변경 시에만 재조회

  // ✅ 검색: URL만 갱신(이펙트가 알아서 조회함)
  const handleSearch = () => {
    const next = new URLSearchParams(params);
    if (empNum) next.set("empNum", empNum);
    else next.delete("empNum");
    setParams(next, { replace: true });
    // fetchByEmp(empNum);  ← 호출 X (이중 호출 방지)
  };

  // ✅ 초기화: 입력/URL 초기화(이펙트가 전체조회)
  const handleReset = () => {
    setEmpNum("");
    const next = new URLSearchParams(params);
    next.delete("empNum");
    setParams(next, { replace: true });
    // fetchAll(); ← 호출 X (이중 호출 방지)
  };

  const openCount = useMemo(
    () =>
      rows.filter(
        (r) => (r.checkOut == null || r.checkOut === "") && (r.checkIn || r.startedAt)
      ).length,
    [rows]
  );

  return (
    <>
      {error && (
        <Alert variant="danger" onClose={() => setError("")} dismissible className="mt-3">
          {error}
        </Alert>
      )}

      <div className="d-flex align-items-center mt-3 mb-2">
        <h1 className="me-3 mb-0">직원 출퇴근(조회)</h1>
        <span className="badge text-bg-secondary">전체 {rows.length}건</span>
        <span className="badge text-bg-warning ms-2">미퇴근 {openCount}명</span>
      </div>

      {/* 검색 영역 */}
      <InputGroup className="my-3" style={{ maxWidth: 420 }}>
        <Form.Control
          placeholder="사번으로 검색"
          value={empNum}
          onChange={(e) => setEmpNum(e.target.value.replace(/\D/g, ""))}
        />
        <Button variant="primary" onClick={handleSearch} disabled={loading}>
          검색
        </Button>
        <Button variant="outline-secondary" onClick={handleReset} disabled={loading}>
          전체
        </Button>
      </InputGroup>

      {/* 목록 테이블 */}
      <Table bordered hover size="sm" className="align-middle">
        <thead>
          <tr>
            <th style={{ width: 90 }}>attNum</th>
            <th style={{ width: 90 }}>empNum</th>
            <th style={{ width: 120 }}>일자</th>
            <th>출근</th>
            <th>퇴근</th>
            <th style={{ width: 120 }}>근무시간</th>
            <th style={{ width: 110 }}>상태</th>
            <th style={{ width: 110 }}>액션</th>
          </tr>
        </thead>
        <tbody>
          {rows.length === 0 && (
            <tr>
              <td colSpan={8} className="text-center text-muted py-4">데이터 없음</td>
            </tr>
          )}
          {rows.map((r) => {
            const attNum = r.attNum ?? r.id;
            const attDate = (r.attDate || r.startedAt || r.checkIn || "").slice(0, 10);
            const started = r.checkIn ?? r.startedAt ?? "";
            const ended = r.checkOut ?? r.endedAt ?? "";
            const duration = r.workHours ?? r.duration ?? r.durationS ?? r.durationSec;
            const status = ended ? (r.attState ?? "DONE") : (r.attState ?? "WORKING");

            const focusThisEmp = () => {
              const v = String(r.empNum);
              setEmpNum(v); // 입력창 동기화
              const next = new URLSearchParams(params);
              next.set("empNum", v);
              setParams(next, { replace: true }); // ← useEffect가 알아서 조회
            };

            return (
              <tr key={attNum}>
                <td>{attNum}</td>
                <td>
                  <Button
                    variant="link"
                    className="p-0"
                    onClick={focusThisEmp}
                    disabled={loading}
                  >
                    {r.empNum}
                  </Button>
                </td>
                <td>{attDate}</td>
                <td>{fmtTime(started)}</td>
                <td>{ended ? fmtTime(ended) : <span className="text-danger">미퇴근</span>}</td>
                <td>{duration != null ? fmtDur(duration) : ended ? "-" : ""}</td>
                <td>
                  <span
                    className={`badge ${status === "WORKING"
                      ? "text-bg-success"
                      : status === "DONE"
                        ? "text-bg-secondary"
                        : "text-bg-info"
                      }`}
                  >
                    {status}
                  </span>
                </td>
                <td>
                  <Button
                    size="sm"
                    variant="outline-secondary"
                    onClick={focusThisEmp}
                    disabled={loading}
                  >
                    이 직원만
                  </Button>
                </td>
              </tr>
            );
          })}
        </tbody>
      </Table>
    </>
  );
} export default EmpAttendanceView;
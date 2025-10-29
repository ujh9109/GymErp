package com.example.gymerp.controller;

import org.springframework.web.bind.annotation.*;
import com.example.gymerp.dto.PtLogDto;
import com.example.gymerp.dto.VoucherLogDto;
import com.example.gymerp.service.LogService;
import lombok.RequiredArgsConstructor;
import java.util.Map;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class LogController {

    private final LogService logService;

    /* ================================
       [회원권 관련]
    ================================ */

    // 회원권 유효성 확인
    @GetMapping("/vouchers/check")
    public Map<String, Object> checkVoucher(@RequestParam Long memNum) {
        boolean valid = logService.isVoucherValid(memNum);
        return Map.of("memNum", memNum, "isValid", valid);
    }

    // 회원권 등록 또는 연장
    @PostMapping("/vouchers")
    public Map<String, Object> saveOrUpdateVoucher(@RequestBody VoucherLogDto dto) {
        logService.saveOrUpdateVoucher(dto);
        return Map.of("message", "회원권 로그가 등록 또는 갱신되었습니다.");
    }

    // 회원권 상세 조회
    @GetMapping("/vouchers/{memNum}")
    public Map<String, Object> getVoucherDetail(@PathVariable Long memNum) {
        VoucherLogDto voucher = logService.getVoucherByMember(memNum);
        return Map.of("voucher", voucher);
    }

    // 회원권 직접 수정 (관리자용)
    @PutMapping("/vouchers/{voucherId}")
    public Map<String, Object> updateVoucherLogManual(@PathVariable Long voucherId, @RequestBody VoucherLogDto dto) {
        dto.setVoucherId(voucherId);
        logService.updateVoucherLogManual(dto);
        return Map.of("message", "회원권 로그 수정이 완료되었습니다.");
    }


    /* ================================
       [PT 관련]
    ================================ */

    // PT 충전 로그 등록
    @PostMapping("/pt/charge")
    public Map<String, Object> insertPtChargeLog(@RequestBody PtLogDto dto) {
        logService.addPtChargeLog(dto);
        return Map.of("message", "PT 충전 로그가 등록되었습니다.");
    }

    // PT 소비 로그 등록
    @PostMapping("/pt/consume")
    public Map<String, Object> insertPtConsumeLog(@RequestBody PtLogDto dto) {
        logService.addPtConsumeLog(dto);
        return Map.of("message", "PT 소비 로그가 등록되었습니다.");
    }

    // PT 트레이너 변경 로그 등록 (자동 계산)
    @PostMapping("/pt/change")
    public Map<String, Object> insertPtChangeLog(@RequestBody PtLogDto dto) {
        logService.addPtChangeLog(dto);
        return Map.of("message", "PT 트레이너 변경 로그가 등록되었습니다.");
    }

    // PT 로그 직접 수정 (관리자용)
    @PutMapping("/pt/{usageId}")
    public Map<String, Object> updatePtLogManual(@PathVariable Long usageId, @RequestBody PtLogDto dto) {
        dto.setUsageId(usageId);
        logService.updatePtLogManual(dto);
        return Map.of("message", "PT 로그 수정이 완료되었습니다.");
    }

    // 남은 PT 횟수 상세 조회
    @GetMapping("/pt/{memNum}")
    public Map<String, Object> getPtLogDetail(@PathVariable Long memNum) {
        int remaining = logService.getRemainingPtCount(memNum);
        return Map.of("memNum", memNum, "remainingCount", remaining);
    }
}

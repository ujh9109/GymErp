package com.example.gymerp.controller;

import org.springframework.web.bind.annotation.*;
import com.example.gymerp.dto.PtLogDto;
import com.example.gymerp.dto.VoucherLogDto;
import com.example.gymerp.service.LogService;
import lombok.RequiredArgsConstructor;
import java.util.Map;

@RestController
@RequestMapping("/api/v1") 
@RequiredArgsConstructor
public class LogController {

    private final LogService logService;

    /* ================================
       [회원권 관련]
    ================================ */

    @GetMapping("/log/voucher/check")
    public Map<String, Object> checkVoucher(@RequestParam Long memNum) {
        boolean valid = logService.isVoucherValid(memNum);
        return Map.of("memNum", memNum, "isValid", valid);
    }

    @PostMapping("/log/voucher")
    public Map<String, Object> saveOrUpdateVoucher(@RequestBody VoucherLogDto dto) {
        logService.saveOrUpdateVoucher(dto);
        return Map.of("message", "회원권 로그가 등록 또는 갱신되었습니다.");
    }

    @GetMapping("/log/voucher/{memNum}")
    public Map<String, Object> getVoucherDetail(@PathVariable Long memNum) {
        VoucherLogDto voucher = logService.getVoucherByMember(memNum);
        return Map.of("voucher", voucher);
    }

    @PutMapping("/log/voucher/{voucherId}")
    public Map<String, Object> updateVoucherLogManual(@PathVariable Long voucherId, @RequestBody VoucherLogDto dto) {
        dto.setVoucherId(voucherId);
        logService.updateVoucherLogManual(dto);
        return Map.of("message", "회원권 로그 수정이 완료되었습니다.");
    }

    /* ================================
       [PT 관련]
    ================================ */

    @PostMapping("/log/pt/charge")
    public Map<String, Object> insertPtChargeLog(@RequestBody PtLogDto dto) {
        logService.addPtChargeLog(dto);
        return Map.of("message", "PT 충전 로그가 등록되었습니다.");
    }

    @PostMapping("/log/pt/consume")
    public Map<String, Object> insertPtConsumeLog(@RequestBody PtLogDto dto) {
        logService.addPtConsumeLog(dto);
        return Map.of("message", "PT 소비 로그가 등록되었습니다.");
    }

    @PostMapping("/log/pt/change")
    public Map<String, Object> insertPtChangeLog(@RequestBody PtLogDto dto) {
        logService.addPtChangeLog(dto);
        return Map.of("message", "PT 트레이너 변경 로그가 등록되었습니다.");
    }

    @PutMapping("/log/pt/{usageId}")
    public Map<String, Object> updatePtLogManual(@PathVariable Long usageId, @RequestBody PtLogDto dto) {
        dto.setUsageId(usageId);
        logService.updatePtLogManual(dto);
        return Map.of("message", "PT 로그 수정이 완료되었습니다.");
    }

    @GetMapping("/log/pt/{memNum}")
    public Map<String, Object> getRemainingPtCount(@PathVariable Long memNum) {
        int remaining = logService.getRemainingPtCount(memNum);
        return Map.of("memNum", memNum, "remainingCount", remaining);
    }
}

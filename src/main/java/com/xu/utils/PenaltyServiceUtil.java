package com.xu.utils;

import org.springframework.stereotype.Component;

@Component
public class PenaltyServiceUtil {
    // 基础每天罚金金额
    private static final double BASE_PENALTY_PER_DAY = 1.0;

    /**
     * 根据逾期天数计算罚金。
     *
     * @param overdueDays 逾期天数
     * @return 罚金总额
     */
    public double calculatePenalty(int overdueDays) {
        if (overdueDays <= 0) {
            return 0.0;
        }
        // 罚金递增因子，例如每天的罚金是前一天的1.05倍
        double incrementFactor = 1.2;
        double penalty = BASE_PENALTY_PER_DAY;
        for (int i = 1; i < overdueDays; i++) {
            penalty *= incrementFactor;
        }
        return penalty;
    }
}

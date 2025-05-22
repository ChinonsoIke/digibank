package proj.dtos;

import jakarta.validation.constraints.Positive;

public class WithdrawDto {
    public Long accountId;
    public Long userId;
    @Positive
    public double amount;
}

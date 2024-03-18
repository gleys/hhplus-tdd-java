package io.hhplus.tdd.point.domain;

public record PointHistory(
    Long id,
    Long userId,
    TransactionType type,
    Long amount,
    Long timeMillis
) {

}
package io.eventtracking.service;

public interface TransactionHandler<R,T> {

  void handleTransaction(R request, T param);

}

package com.vh.simpleBankProject.repository;

import com.vh.simpleBankProject.model.TransferHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferHistoryRepository extends JpaRepository<TransferHistory, Long> {

}

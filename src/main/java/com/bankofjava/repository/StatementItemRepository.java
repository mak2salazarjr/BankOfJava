package com.bankofjava.repository;

import com.bankofjava.domain.StatementItem;
import org.springframework.data.repository.CrudRepository;

public interface StatementItemRepository extends CrudRepository<StatementItem, Long> {

}

package com.org.CoronaReport;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagingRepository extends JpaRepository<CovidData, Long>{

}

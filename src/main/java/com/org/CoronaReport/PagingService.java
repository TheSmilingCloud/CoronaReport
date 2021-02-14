package com.org.CoronaReport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PagingService {

	
	@Autowired
	private PagingRepository pagingRepository;
	
	
	public Page<CovidData> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
			Sort.by(sortField).descending();
		
		
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return this.pagingRepository.findAll(pageable);
	}
}

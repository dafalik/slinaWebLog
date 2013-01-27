/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package slina.mb.domain.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import slina.mb.db.repositories.SlinaDbEventRepository;
import slina.mb.db.repositories.SlinaDbLogDetailsRepository;
import slina.mb.db.repositories.SlinaDbLogFileRepository;

/**
 * @author AnilAmarakoon
 *
 *@Service
 */
@Repository
@Transactional(readOnly = true)
public class SlinaRepositoryServiceImpl  implements SlinaRepositoryService {

	@Autowired
	private SlinaDbLogFileRepository slinaDbLogFileRepository;
	
	@Autowired
	private SlinaDbEventRepository slinaDbEventRepository;

	@Autowired
	private SlinaDbLogDetailsRepository slinaDbEventDetailRepository;
	
	/**
	 * @see slina.mb.domain.db.SlinaRepositoryService#findAll()
	 */
	@Override
	public List<DbLogFileImpl> findAll() {
	    return this.slinaDbLogFileRepository.findAll();
	  }
	 	
	public List<DbLogEventImpl> getLogEvents() {
		return slinaDbEventRepository.findAll();
	}
	

	  /**
	 * @see slina.mb.domain.db.SlinaRepositoryService#save(slina.mb.domain.db.DbLogFileImpl)
	 */
	  @Override
	  @Transactional
	  public DbLogFileImpl save(DbLogFileImpl t) {
		
		return this.slinaDbLogFileRepository.save(t);
		
	  }
	 
	  /**
	 * @see slina.mb.domain.db.SlinaRepositoryService#delete(java.lang.Integer)
	 */
	@Override
	@Transactional
	public void delete(Long  id) {
		this.slinaDbLogFileRepository.delete(id);
	  }
	 

	  /**
	 * @see slina.mb.domain.db.SlinaRepositoryService#findOne(java.lang.Integer)
	 */
	@Override
	public DbLogFileImpl findOne(Long id) {  
		  
		  return this.slinaDbLogFileRepository.findOne(id);
	  }


	  
}

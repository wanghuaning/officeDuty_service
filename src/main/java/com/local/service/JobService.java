package com.local.service;

import com.local.entity.elsys.ElSysJob;
import org.nutz.dao.QueryResult;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

public interface JobService {

    ElSysJob findById(Long id);

    void create(ElSysJob job);

    void update(ElSysJob job);

    void delete(Long id);

    QueryResult selectAllByParam(String Name, int enabled, int pageSize, int pageNumber);
}
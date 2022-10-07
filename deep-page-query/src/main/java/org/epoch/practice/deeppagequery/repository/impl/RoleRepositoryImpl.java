package org.epoch.practice.deeppagequery.repository.impl;


import org.epoch.mybatis.repository.BaseMybatisRepository;
import org.epoch.practice.deeppagequery.domain.repository.RoleRepository;
import org.epoch.practice.deeppagequery.repository.entity.Role;
import org.epoch.practice.deeppagequery.repository.impl.mapper.RoleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Marshal
 * @date 2019/3/31
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleRepositoryImpl extends BaseMybatisRepository<RoleMapper, Role, String> implements RoleRepository {
}

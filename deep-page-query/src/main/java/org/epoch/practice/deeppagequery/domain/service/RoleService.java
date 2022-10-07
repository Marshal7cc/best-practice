package org.epoch.practice.deeppagequery.domain.service;

import java.util.List;
import java.util.concurrent.*;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.epoch.core.constant.Digital;
import org.epoch.core.constant.StringPool;
import org.epoch.core.util.GenericTypeConverter;
import org.epoch.core.util.TTLMap;
import org.epoch.data.domain.Page;
import org.epoch.data.service.impl.BaseServiceImpl;
import org.epoch.practice.deeppagequery.domain.entity.RoleDO;
import org.epoch.practice.deeppagequery.domain.repository.RoleRepository;
import org.epoch.practice.deeppagequery.repository.entity.Role;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author Marshal
 * @since 2022/7/25
 */
@Slf4j
@Service
public class RoleService extends BaseServiceImpl<RoleRepository, RoleDO, Role, String> {

    private final TTLMap<String, RoleDO> roleCache = new TTLMap<>();
    private final TTLMap<String, Future<Page<RoleDO>>> paceCache = new TTLMap<>();

    private final ExecutorService threadPool = Executors.newFixedThreadPool(10);

    @SneakyThrows
    @Override
    public <Q> Page<RoleDO> findAll(Pageable pageable, Q query) {
        Page<RoleDO> result = null;

        // Get page from pageCache.
        String currentCacheKey = query.toString() + StringPool.COLON + pageable.toString();
        boolean alreadyInCache = paceCache.containsKey(currentCacheKey);
        if (alreadyInCache) {
            log.info("hit page cache, cache key is {}", currentCacheKey);
            Future<Page<RoleDO>> future = paceCache.get(currentCacheKey);
            Page<RoleDO> page = future.get();

            // Resolve modified data.
            List<RoleDO> content = page.getContent();
            for (RoleDO role : content) {
                if (roleCache.containsKey(role.getId())) {
                    role.setDescription(roleCache.get(role.getId()).getDescription());
                }
            }
            result = page;
        }

        // Get page from database.
        StopWatch stopWatch = StopWatch.createStarted();
        if (!alreadyInCache) {
            result = super.findAll(pageable, query);
        }
        long totalTimeMillis = stopWatch.getTime();

        if (totalTimeMillis > Digital.TEN || alreadyInCache) {
            log.info("page speed is lower, start previous find task...");
            for (int i = 1; i <= Digital.FIVE; i++) {
                Pageable newPageable = PageRequest.of(pageable.getPageNumber() + i, pageable.getPageSize());
                String cacheKey = query.toString() + StringPool.COLON + newPageable.toString();
                if (paceCache.containsKey(cacheKey)) {
                    continue;
                }

                Future<Page<RoleDO>> future = threadPool.submit(new PageCacheQuery(query, newPageable, this.repository));

                paceCache.put(cacheKey, future, 120L, TimeUnit.SECONDS);
            }

        }

        return result;
    }

    @Override
    public RoleDO save(RoleDO domain) {
        RoleDO savedDomain = super.save(domain);
        roleCache.put(domain.getId(), domain, 1L, TimeUnit.DAYS);
        return savedDomain;
    }

    static class PageCacheQuery implements Callable<Page<RoleDO>> {
        private final Object condition;
        private final Pageable pageable;
        private final RoleRepository repository;

        public PageCacheQuery(Object condition, Pageable pageable, RoleRepository repository) {
            this.condition = condition;
            this.pageable = pageable;
            this.repository = repository;
        }

        @Override
        public Page<RoleDO> call() throws Exception {
            Page<Role> tPage = this.repository.findAll(pageable, condition);
            return new Page<>(tPage.getPageInfo(), GenericTypeConverter.parseArray(tPage.getContent(), RoleDO.class));
        }
    }
}

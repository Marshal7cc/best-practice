package org.epoch.practice.deeppagequery.facade.controller;


import org.epoch.practice.deeppagequery.domain.entity.RoleDO;
import org.epoch.practice.deeppagequery.domain.service.RoleService;
import org.epoch.practice.deeppagequery.facade.query.RoleQuery;
import org.epoch.practice.deeppagequery.facade.vo.RoleVO;
import org.epoch.web.facade.controller.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Marshal
 * @since 2021/8/26
 */
@RestController
@RequestMapping("/api/v1/roles")
public class RoleController extends BaseController<RoleService, RoleVO, RoleQuery, RoleDO, String> {

}

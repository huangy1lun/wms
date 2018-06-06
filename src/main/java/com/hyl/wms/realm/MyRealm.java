package com.hyl.wms.realm;

import com.hyl.wms.domain.Employee;
import com.hyl.wms.mapper.EmployeeMapper;
import com.hyl.wms.service.IPermissionService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Set;

public class MyRealm extends AuthorizingRealm {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private IPermissionService permissionService;
    /**
     * 认证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取用户登录时填的用户名
        String principal = (String) token.getPrincipal();
        //查询数据库
        //假数据
        Employee employee = employeeMapper.checkLoginByUsername(principal);
        //判断用户名是否存在
        if(employee == null) {
            return null;
        }
        //用户存在,封装一个认证信息对象(身份信息,凭证信息,当前的realm名字)
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(employee, employee.getPassword(), "MyRealm");
        return info;
    }

    /**
     * 授权
     * @param token
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection token) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获取登录用户的id
        Employee  principal = (Employee) token.getPrimaryPrincipal();
        if (principal.isAdmin()) {
            info.addRole("ADMIN");
            info.addStringPermission("*:*");
        } else {
            //添加角色集合
            info.addRoles(Arrays.asList("role1"));
            //添加权限集合
            Set<String> permissions = permissionService.queryPermissionByEmployeeId(principal.getId());
            info.addStringPermissions(permissions);
        }
        return info;
    }


}

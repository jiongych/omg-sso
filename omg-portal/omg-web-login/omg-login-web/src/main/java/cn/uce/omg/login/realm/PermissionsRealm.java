package cn.uce.omg.login.realm;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import cn.uce.omg.portal.biz.IUserBiz;
import cn.uce.web.common.util.WebUtil;



/** 
 * 用户授权信息域
 * @author raowenbin
 *
 */
public class PermissionsRealm extends AuthorizingRealm {
	private Log log = LogFactory.getLog(PermissionsRealm.class);
	@Resource
	private IUserBiz fspUserBiz;
	
	 /** 
     * 为当前登录的Subject授予角色和权限(当有权限校验的标签才会进)
     */  
    @Override  
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals){
    	if (principals == null) {
            return null;
        }
        //为当前用户设置角色和权限  
        SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
        try {
    		List<String> roles = fspUserBiz.findRoleByUserName(WebUtil.getCurrentUser().getEmpCode());
    		if (roles == null || roles.isEmpty()) {
        		throw new UnauthorizedException();
        	}
    		List<String> permissions = fspUserBiz.findUserAllPermissionCode(roles);
    		if (permissions == null || permissions.isEmpty()) {
        		throw new UnauthorizedException();
        	}
    		simpleAuthorInfo.addRoles(roles);
	    	simpleAuthorInfo.addStringPermissions(permissions);
	    	return simpleAuthorInfo;
        } catch (Exception e) {
        	log.error(e);
        }
        throw new UnauthorizedException();
    }  
	
	/**
	 * 验证当前登录的Subject (调login方法就会进)
     * 
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) {
		UsernamePasswordToken userToken = (UsernamePasswordToken) authcToken;  
        //没有返回登录用户名对应的SimpleAuthenticationInfo对象时,就会在LoginController中抛出UnknownAccountException异常 
		return new SimpleAuthenticationInfo(userToken.getPrincipal(), userToken.getCredentials(), this.getName());
	}
	
	/** 
     * 清除当前用户的授权信息 
     */  
    public void clearCache(){  
    	 PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
    	 clearCachedAuthorizationInfo(principals); 
    }  
    
    private Cache<Object, AuthorizationInfo> getAuthorizationCacheLazy() {
    	Cache<Object, AuthorizationInfo> authorizationCache = getAuthorizationCache();
        if (authorizationCache == null) {

            if (log.isDebugEnabled()) {
                log.debug("No authorizationCache instance set.  Checking for a cacheManager...");
            }

            CacheManager cacheManager = getCacheManager();

            if (cacheManager != null) {
                String cacheName = getAuthorizationCacheName();
                if (log.isDebugEnabled()) {
                    log.debug("CacheManager [" + cacheManager + "] has been configured.  Building " +
                            "authorization cache named [" + cacheName + "]");
                }
                authorizationCache = cacheManager.getCache(cacheName);
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("No cache or cacheManager properties have been set.  Authorization cache cannot " +
                            "be obtained.");
                }
            }
        }

        return authorizationCache;
    }
    
    private Cache<Object, AuthorizationInfo> getAvailableAuthorizationCache() {
        Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
        if (cache == null && isAuthorizationCachingEnabled()) {
            cache = getAuthorizationCacheLazy();
        }
        return cache;
    }
    
    protected AuthorizationInfo getAuthorizationInfo(PrincipalCollection principals) {
    	
        if (principals == null) {
            return null;
        }
        Object key = principals.getPrimaryPrincipal();
        AuthorizationInfo info = null;

        if (log.isTraceEnabled()) {
            log.trace("Retrieving AuthorizationInfo for principals [" + principals + "]");
        }

        Cache<Object, AuthorizationInfo> cache = getAvailableAuthorizationCache();
        if (cache != null) {
            if (log.isTraceEnabled()) {
                log.trace("Attempting to retrieve the AuthorizationInfo from cache.");
            }
            //Object key = getAuthorizationCacheKey(principals);
            
            info = cache.get(key);
            if (log.isTraceEnabled()) {
                if (info == null) {
                    log.trace("No AuthorizationInfo found in cache for principals [" + principals + "]");
                } else {
                    log.trace("AuthorizationInfo found in cache for principals [" + principals + "]");
                }
            }
        }


        if (info == null) {
            // Call template method if the info was not found in a cache
            info = doGetAuthorizationInfo(principals);
            // If the info is not null and the cache has been created, then cache the authorization info.
            if (info != null && cache != null) {
                if (log.isTraceEnabled()) {
                    log.trace("Caching authorization info for principals: [" + principals + "].");
                }
                //Object key = getAuthorizationCacheKey(principals);
                cache.put(key, info);
            }
        }

        return info;
    }
	
}

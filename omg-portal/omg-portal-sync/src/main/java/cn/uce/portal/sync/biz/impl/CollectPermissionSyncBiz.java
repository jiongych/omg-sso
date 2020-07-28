package cn.uce.portal.sync.biz.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.uce.mq.rocket.exp.base.MsgBodyListenerConcurrently;
import cn.uce.portal.sync.entity.CollectPermission;
import cn.uce.portal.sync.entity.Permission;
import cn.uce.portal.sync.service.CollectPermissionService;
import cn.uce.portal.sync.service.PermissionService;

import com.alibaba.fastjson.JSONObject;

/**
 * 新乾坤首页搜集菜单点击信息数据保存
 *<pre>
 * PortalCollectPermissionSyncBiz
 *<pre>
 * @author 014221
 * @email jiyongchao@uce.cn
 * @data 2019年4月10日下午2:09:43
 */
@Service("collectPermissionSyncBiz")
public class CollectPermissionSyncBiz extends MsgBodyListenerConcurrently<Object> {

	@Resource
	private CollectPermissionService collectPermissionService;
	@Resource
	private PermissionService permissionService;
	/**
	 * 
	 * 方法的描述：
	 * <pre>
	 * 接收新乾坤菜单点击数据
	 * </pre>
	 * @param body
	 * @author jiyongchao
	 * @email jiyongchao@uce.cn
	 * @date 2019年4月15日下午5:30:02
	 */
	@Override
	public void processMessageBody(Object body) {
		if (null != body) {
			String message = body.toString().replaceAll("@type", "null");
			CollectPermission portalCollectPermission = JSONObject.parseObject(message, CollectPermission.class);
			if (null != portalCollectPermission) {
				if(null != portalCollectPermission.getPermissionId()) {
					//根据菜单ID查询数据
					Permission portalPermission = permissionService.findById(portalCollectPermission.getPermissionId());
					if (null != portalPermission) {
						portalCollectPermission.setPermissionName(portalPermission.getPermissionName());
						portalCollectPermission.setSystemCode(portalPermission.getSystemCode());
						portalCollectPermission.setPermissionCode(portalPermission.getPermissionCode());
					}
				} else {
					Permission portalPermission =  permissionService.findByParam(portalCollectPermission);
					if (null != portalPermission) {
						portalCollectPermission.setPermissionId(portalPermission.getId());
						portalCollectPermission.setPermissionName(portalPermission.getPermissionName());
						portalCollectPermission.setSystemCode(portalPermission.getSystemCode());
						portalCollectPermission.setPermissionCode(portalPermission.getPermissionCode());
					}
				}
				String url = portalCollectPermission.getUrl();
				if (null != url && url.length() > 255) {
					url = url.substring(0, 255);
				}
			}
			
			collectPermissionService.saveInfo(portalCollectPermission);
		}
	}

}

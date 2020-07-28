/** 
 * @项目名称: FSP
 * @文件名称: RemoveLoginFailCacheJob  implements InitializingBean 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.processor;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;

import cn.uce.omg.gateway.constant.OmgProductConstants;
import cn.uce.omg.gateway.constant.OmgTrackConstants.PushSubscribe;
import cn.uce.omg.sso.biz.IUserBiz;
import cn.uce.omg.sso.cache.HashRedisWithFieldExpireCache;
import cn.uce.omg.sso.service.PushService;
import cn.uce.omg.sso.vo.LoginFailVo;
import cn.uce.omg.vo.UserVo;


/**
 * 删除用户登录失败缓存信息job
 * @author huangting
 * @date 2017年6月8日 下午7:42:59
 */
public class RemoveLoginFailCacheJob  implements InitializingBean {
	private final static Log log = LogFactory.getLog(RemoveLoginFailCacheJob.class);
	
	/**
	 * 用户登录失败缓存
	 */
	private HashRedisWithFieldExpireCache<LoginFailVo> loginFailCache;
	/**
	 * UserBiz
	 */
	private IUserBiz userBiz;
	
	/**
	 * 推送Service
	 */
	private PushService pushService;
	
	/**
	 * job执行入口
	 * @throws InterruptedException
	 * @author huangting
	 * @date 2017年6月14日 下午4:06:47
	 */
	public void execute() throws InterruptedException {
		//modify by zhangRb 20180814 日志降级
		log.warn("用户登录失败缓存 Job开始");
		try {
			//声明登录失败对象
			LoginFailVo loginFail = null;
			//获取当前时间
			Calendar calendar = Calendar.getInstance();
			//获取所有用户登录失败缓存信息
			Map<String , LoginFailVo> loginFailMap = loginFailCache.entries();
			for (String key : loginFailMap.keySet()) {
				//根据key获取登录失败缓存信息
				loginFail = loginFailMap.get(key);
				//信息为空跳过
				if (loginFail == null || loginFail.getUserExpireTime() == null ) {
					continue;
				}
				//操作时间转换为data
				calendar.setTimeInMillis(loginFail.getUserExpireTime());
				//该job是每天23:59:59执行，清除当天用户登录失败信息的缓存
				if (ObjectUtils.compare(calendar.getTime(), new Date()) < 0) {
					//清除今天零点前的登录失败信息
					loginFailCache.delete(key);
					//更新数据库对应的锁定状态
					boolean result = userBiz.updateUserLockState(key, false);
					//modify by zhangRb 20180526 查询用户为空导致异常出现的BUG修复
					if (result) {
						//推送更新后的用户到其他业务平台
						UserVo userVo = userBiz.findUserByEmpCode(key);
						pushService.pushToAllPartner(userVo.getEmpId(), OmgProductConstants.PARTNER_PRODUCT_UPDATE_USER, PushSubscribe.USER);
					}
				}
			}
		} catch (Exception e) {
			log.error("用户登录失败缓存 Job失败" , e);
		}
	}
	/**
	 * 所有的属性被初始化后调用
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		// sonar检查需要方法体有代码或者注释
	}
	/**
	 * @param loginFailCache the loginFailCache to set
	 */
	public void setLoginFailCache(
			HashRedisWithFieldExpireCache<LoginFailVo> loginFailCache) {
		this.loginFailCache = loginFailCache;
	}
	
	/**
	 * @param userBiz the userBiz to set
	 */
	public void setUserBiz(IUserBiz userBiz){
		this.userBiz = userBiz;
	}
	/**
	 * @param pushService the pushService to set
	 */
	public void setPushService(PushService pushService) {
		this.pushService = pushService;
	}
}

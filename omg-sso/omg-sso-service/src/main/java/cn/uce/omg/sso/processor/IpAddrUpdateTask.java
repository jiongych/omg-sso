/** 
 * @项目名称: FSP
 * @文件名称: IpAddrUpdateTask extends Thread 
 * @version 2.0
 * @Copyright: 2016~2017 www.uce.cn Inc. All rights reserved.
* 注意：本内容仅限于优速物流有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */ 
package cn.uce.omg.sso.processor;

import java.io.File;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.redis.serializer.RedisSerializer;

import cn.uce.omg.sso.biz.impl.IpinfoBiz;
import cn.uce.omg.sso.cache.HashRedisWithFieldExpireCache;
import cn.uce.omg.sso.constant.AuthConstants;
import cn.uce.omg.sso.util.StringUtil;
import cn.uce.omg.sso.util.ip.IPSeeker;
import cn.uce.omg.sso.util.redis.BoundHashRedisSupport;
import cn.uce.omg.sso.vo.IPEntry;
import cn.uce.omg.sso.vo.IpInfoVo;

/**
 * ip地址导入task，隔一段时间进行导入一次ip地址，
 * 时间间隔不确定，当检测到redis数据为空时，进行导入操作
 * @author tanchong
 *
 */
public class IpAddrUpdateTask extends Thread {

	private Log log = LogFactory.getLog(this.getClass());
	/** 
	 * ip地址是否已经导入过
	 */
	private BoundHashRedisSupport<Boolean> ipAddrFlagCache;
	/** 
	 * IP地址缓存
	 */
	private HashRedisWithFieldExpireCache<IpInfoVo> ipAddrCache;
	
	/**
	 * ip信息biz
	 */
	private IpinfoBiz ipInfoBiz;
	/**
	 * key
	 */
	private String key = "sso-ipAddr:hash";
	/** 
	 * 文件路径
	 */
	private String filePath;

	/**
	 * 线程执行入口
	 * @author huangting
	 * @date 2017年6月14日 下午4:06:47
	 */
	@Override
	public void run() {
		try {
			// 进行检测是否已经更新过了
			Boolean isFlag = ipAddrFlagCache.get(AuthConstants.IS_UPDATE_IP);
			if (isFlag != null && isFlag) {
				return;
			}
			//判断文件路径是否为空
			if (StringUtil.isNotEmpty(filePath)) {
				return;
			}
			// 删除redis的所有数据
			RedisSerializer<?> key = ipAddrCache.getRedisTemplate().getHashKeySerializer();
//			ipAddrCache.getRedisTemplate().delete(key);
			// 判断文件路径是否正确
			File file = new File(filePath);
			//文件不存在返回
			if (!file.exists()) {
				return;
			}
			// 获取纯真的数据，并解析出来，存入到数据中
			IPSeeker ipSeeker = IPSeeker.getInstance(filePath);
			// 获取所有ip信息
			List<IPEntry> ipEntryList = ipSeeker.getIPEntriesDebug(null);
			for (IPEntry entry : ipEntryList) {
				if (entry != null) {
					try {
						
					} catch (Exception e) {
						log.error("IpAddrUpdateTask.run.IP信息导入错误:" + entry.getBeginIp() + "-" + entry.getEndIp(), e);
					}
				}
			}
		} catch (Exception e) {
			log.error("IpAddrUpdateTask.run.导入ip地址信息失败", e);
		}
	}
	
	/**
	 * @param ipAddrFlagCache the ipAddrFlagCache to set
	 */
	public void setIpAddrFlagCache(BoundHashRedisSupport<Boolean> ipAddrFlagCache) {
		this.ipAddrFlagCache = ipAddrFlagCache;
	}

	/**
	 * @param ipAddrCache the ipAddrCache to set
	 */
	public void setIpAddrCache(HashRedisWithFieldExpireCache<IpInfoVo> ipAddrCache) {
		this.ipAddrCache = ipAddrCache;
	}

	/**
	 * @param ipInfoBiz the ipInfoBiz to set
	 */
	public void setIpInfoBiz(IpinfoBiz ipInfoBiz) {
		this.ipInfoBiz = ipInfoBiz;
	}

	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}

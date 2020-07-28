package cn.uce.omg.sso.cache;

import org.springframework.beans.factory.annotation.Autowired;

import cn.uce.base.exception.BusinessException;
import cn.uce.core.cache.CacheSupport;
import cn.uce.omg.sso.biz.IUserBiz;
import cn.uce.omg.vo.UserVo;
import cn.uce.utils.StringUtil;

public class UserCache extends CacheSupport<UserVo>{
	
	@Autowired
	private IUserBiz userBiz;
	
	@Override
	public UserVo doGet(String empCode) throws BusinessException {
		if (StringUtil.isBlank(empCode)) {
			return null;
		}
		return userBiz.findUserByEmpCode(empCode);
	}

	@Override
	public String getCacheId() {
		return "omg-" + getClass().getSimpleName();
	}

	/**
	 * @param userBiz the userBiz to set
	 */
	public void setUserBiz(IUserBiz userBiz) {
		this.userBiz = userBiz;
	}
}

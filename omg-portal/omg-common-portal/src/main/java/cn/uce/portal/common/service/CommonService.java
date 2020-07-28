package cn.uce.portal.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.uce.portal.common.base.CurrentUser;
import cn.uce.portal.common.dao.ICommonDao;

/**
 * @Description: (CommonService) 
 * @author XJ
 * @date 2018年4月8日 下午2:04:29
 */
@Service("commonService")
public class CommonService {
	
	@Autowired
	private ICommonDao commonDao;
	
	/**
	 * @Description: (findPermissionCodeByUser) 
	 * @param empCode
	 * @return
	 * @author XJ
	 * @date 2018年4月8日 下午2:04:03
	 */
	public List<String> findPermissionCodeByUser(String empCode){
		return commonDao.findPermissionCodeByUser(empCode);
	}
	
	/**
	 * @Description: (findCurrentUser) 
	 * @param empCode
	 * @return
	 * @author XJ
	 * @date 2018年4月10日 下午5:03:50
	 */
	public CurrentUser findCurrentUser(String empCode){
		return commonDao.findCurrentUser(empCode);
	}

}

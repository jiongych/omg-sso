package cn.uce.omg.portal.biz;

import java.util.Map;

import cn.uce.base.page.Page;
import cn.uce.base.page.Pagination;
import cn.uce.omg.portal.vo.PortalBrowserInfoVo;

public interface IBrowserInfoBiz {

	Map<String, Object> loadBrowserInfo();

	Map<String, Object> loadLoginPepoleInfo();

	Map<String, Long> loadResolutionRatio();

	Map<String, Object> loadLoginRankingInfo(String value);

	Pagination<PortalBrowserInfoVo> findAllBrowserInfo(
			PortalBrowserInfoVo browserInfoVo, Page page);

}

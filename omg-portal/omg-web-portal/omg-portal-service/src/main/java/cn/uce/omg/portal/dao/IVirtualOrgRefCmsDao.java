package cn.uce.omg.portal.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.uce.omg.portal.vo.VirtualOrgRefCmsVo;


@Repository("virtualOrgRefCmsDao")
public interface IVirtualOrgRefCmsDao {
	
	List<VirtualOrgRefCmsVo> findVirtualOrgRefCmsRef();

}

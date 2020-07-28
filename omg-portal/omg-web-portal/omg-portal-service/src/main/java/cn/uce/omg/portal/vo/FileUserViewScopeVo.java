package cn.uce.omg.portal.vo;

import java.io.Serializable;

//受众群体
public class FileUserViewScopeVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//受众id
	private Long scopeId;
	//群体名称
	private String scopeName;
	//范围code
	private String scopeCode;

	public Long getScopeId() {
		return scopeId;
	}

	public void setScopeId(Long scopeId) {
		this.scopeId = scopeId;
	}

	public String getScopeName() {
		return scopeName;
	}

	public void setScopeName(String scopeName) {
		this.scopeName = scopeName;
	}

	public String getScopeCode() {
		return scopeCode;
	}

	public void setScopeCode(String scopeCode) {
		this.scopeCode = scopeCode;
	}
	
	
}

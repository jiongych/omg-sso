package cn.uce.omg.main.sso.converter;

import cn.uce.omg.main.converter.ResponseXmlConverter;
import cn.uce.omg.sso.vo.ReturnResultVo;

/**
 * 描述:
 * 返回结果Vo
 *
 * @outhor BaoJingyu
 * @create 2018-01-20 18:49
 * @empNo 015966
 * @email baojingyu@uce.cn
 */
public class ReturnResultVoConverter extends ResponseXmlConverter {

    public ReturnResultVoConverter() {
        this("xml");
    }
    /**
     *
     * @param contentType
     */
    public ReturnResultVoConverter(String contentType) {
        super(contentType);
        changeField();
    }

    /**
     * 数据转换
     *
     * @author huangting
     * @date 2017年6月9日 下午12:09:59
     */
    public void changeField() {
        this.aliasClass("returnResult", ReturnResultVo.class);
    }
}
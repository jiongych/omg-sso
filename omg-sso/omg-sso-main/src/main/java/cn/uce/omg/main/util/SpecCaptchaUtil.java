package cn.uce.omg.main.util;

import org.apache.commons.codec.binary.Base64OutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/**
 * @author wangshangbing
 * @FileName: SpecCaptchaUtil.java
 * @Description: png格式验证码
 * @date 2017/8/30 11:18
 */

public class SpecCaptchaUtil extends CaptchaUtil {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public SpecCaptchaUtil() {
    }

    public SpecCaptchaUtil(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public SpecCaptchaUtil(int width, int height, int len) {
        this(width, height);
        this.len = len;
    }

    /*public SpecCaptchaUtil(int width, int height, int len, Font font) {
        this(width, height, len);
        //this.font = font;
    }*/

    /**
     * 生成验证码
     *
     * @throws IOException IO异常
     */
    @Override
    public void out(OutputStream out) {
        graphicsImage(alphas(), out);
    }
    /**
     * 生成验证码转换成base64
     *
     * @throws IOException IO异常
     */
    @Override
    public String outToString() {
        try {
        	 ByteArrayOutputStream os = new ByteArrayOutputStream(); 
        	 OutputStream b64 = new Base64OutputStream(os);
             graphicsImage(alphas(), b64);
			 String result = os.toString("UTF-8");
			 String param = "data:image/png;base64,"+result;//拼接固定的接收格式
			 return param;
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(),e);
		}
        return null;
    }
    

    /**
     * 画随机码图
     *
     * @param strs 文本
     * @param out  输出流
     */
    private boolean graphicsImage(char[] strs, OutputStream out) {
        boolean ok = false;
        try {
            graphicsImageToStream(strs, out);
            out.flush();
            ok = true;
        } catch (IOException e) {
            ok = false;
            logger.error(e.getMessage(),e);
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                logger.error(e.getMessage(),e);
            }
        }
        return ok;
    }
    /**
     * 
      * <p>Description : 生成图片， 并把图片放入工作流中</p>
      * @author : crj
      * @date : 2017年10月31日上午9:13:48
     */
	private void graphicsImageToStream(char[] strs, OutputStream out)
			throws IOException {
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) bi.getGraphics();
		AlphaComposite ac3;
		Color color;
		int len = strs.length;
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		// 随机画干扰的蛋蛋
		for (int i = 0; i < 25; i++) {
		    color = color(150, 250);
		    g.setColor(color);
		    g.drawOval(num(width), num(height), 10 + num(10), 10 + num(10));// 画蛋蛋，有蛋的生活才精彩
		    color = null;
		}
		
		// 添加噪点
		float yawpRate = 0.02f;// 噪声率
		int area = (int) (yawpRate * width * height);
		for (int i = 0; i < area; i++) {
		    int x = num(width);
		    int y = num(height);
		    
		    bi.setRGB(x, y, num(255));
		}
		Font font = getFont(28);
		g.setFont(font);
		int h = height - ((height - font.getSize()) >> 1),
		        w = width / len,
		        size = w - font.getSize() + 1;
		/* 画字符串 */
		for (int i = 0; i < len; i++) {
		    ac3 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f);// 指定透明度
		    g.setComposite(ac3);
		    color = new Color(20 + num(110), 20 + num(110), 20 + num(110));// 对每个字符都用随机颜色
		    g.setColor(color);
		    g.drawString(strs[i] + "", (width - (len - i) * w) + size, h - 4);
		    color = null;
		    ac3 = null;
		}
		ImageIO.write(bi, "png", out);
	}
}
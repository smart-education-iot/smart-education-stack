package com.education.common.config.conf;

import java.awt.Font;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.education.common.utils.CaptchaService;
import com.octo.captcha.component.image.backgroundgenerator.UniColorBackgroundGenerator;
import com.octo.captcha.component.image.color.SingleColorGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.DecoratedRandomTextPaster;
import com.octo.captcha.component.image.textpaster.textdecorator.BaffleTextDecorator;
import com.octo.captcha.component.image.textpaster.textdecorator.TextDecorator;
import com.octo.captcha.component.image.wordtoimage.ComposedWordToImage;
import com.octo.captcha.component.word.wordgenerator.RandomWordGenerator;
import com.octo.captcha.engine.GenericCaptchaEngine;
import com.octo.captcha.image.gimpy.GimpyFactory;

/**
 * 验证码配置
 * @author jamesli
 * @version 1.0
 * @date 2016-05-24
 * <dependency>
 *   	<groupId>com.octo.captcha</groupId>
 *   	<artifactId>jcaptcha</artifactId>
 *  	<version>1.0</version>
 * </dependency>
 */
@Configuration
public class CaptchaConfig {
	

	/**
	 * ImageCaptchaService
	 * @return
	 */
	@Bean
	public CaptchaService captchaService(){
		return new CaptchaService(captchaEngine(),180,100000,0);
	}
	
	/**
	 * GenericCaptchaEngine
	 * @return
	 */
	public GenericCaptchaEngine captchaEngine(){
		return new GenericCaptchaEngine(new com.octo.captcha.CaptchaFactory[]{captchaFactory()});
	}

	/**
	 * GimpyFactory
	 * @return
	 */
  	public GimpyFactory captchaFactory(){
  		return new GimpyFactory(randomWordGenerator(),randomImageGenerator());
  	}
	
	/**
	 * RandomWordGenerator
	 * @return
	 */
	public RandomWordGenerator randomWordGenerator(){
		return new RandomWordGenerator("0123456789abcdefghijklmnopqrstuvwxyz");
	}
	
	/**
	 * ComposedWordToImage
	 * @return
	 */
	public ComposedWordToImage randomImageGenerator(){
		return new ComposedWordToImage(fontGenRandom(),backGenUni(),decoratedPaster());
	}
	/**
	 * UniColorBackgroundGenerator
	 * @return
	 */
	public UniColorBackgroundGenerator backGenUni(){
		return new UniColorBackgroundGenerator(80,32);
	}

    /**
     * 1. 最小字体
     * 2. 最大字体
     * 3. 字体列表
     * @return
     */
	public RandomFontGenerator fontGenRandom(){
		Font font = new java.awt.Font("Arial",0,20);
		return new RandomFontGenerator(20,20, new java.awt.Font[]{font});
	}

	/**
	 * SingleColorGenerator
	 * @return
	 */
	public SingleColorGenerator colorGenerator(){
		return new SingleColorGenerator(colorDimGrey());
	}
	
	/**
	 * BaffleTextDecorator
	 * @return
	 */
	public BaffleTextDecorator baffleDecorator(){
		return new BaffleTextDecorator(1,colorWrite());
	}
	
	/**
	 * DecoratedRandomTextPaster
	 * @return
	 */
	public DecoratedRandomTextPaster decoratedPaster(){
		/**
		 * 1. 最大字符长度
		 * 2. 最小字符长度
		 * 3. 文本颜色
		 * 4. 文本混淆baffleDecorator
		 */		
		return new DecoratedRandomTextPaster(4,4,colorGen(),new TextDecorator[]{baffleDecorator()});
	}
	/**
	 * java.awt.Color
	 * @return
	 */
	public java.awt.Color colorDimGrey(){
		return new java.awt.Color(105, 105, 105);
	}
	/**
	 * java.awt.Color
	 * @return
	 */
	public java.awt.Color colorWrite(){
		return new java.awt.Color(255, 255, 255);
	}	
	/**
	 * SingleColorGenerator
	 * @return
	 */
	public SingleColorGenerator colorGen(){
		return new SingleColorGenerator(colorDimGrey());
	}
}

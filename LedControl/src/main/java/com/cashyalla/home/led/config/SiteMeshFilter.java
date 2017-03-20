package com.cashyalla.home.led.config;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;
import org.sitemesh.content.tagrules.html.Sm2TagRuleBundle;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SiteMeshFilter extends ConfigurableSiteMeshFilter {

	@Override
	protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
		builder.addTagRuleBundle(new Sm2TagRuleBundle());
		builder.addDecoratorPath("*", "/WEB-INF/decorator/default-decorator.jsp");
	}
	
}

package com.cashyalla.home.led.naming;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class CamelCaseNamingStrategy implements PhysicalNamingStrategy {
	
	private String camelToUnderscore(String text) {
		String regex = "([a-z0-9])([A-Z])";
		String replacement = "$1_$2";
		String underScoreText = text.replaceAll(regex, replacement);
		
		return underScoreText;
	}
	
	@Override
	public Identifier toPhysicalCatalogName(Identifier name, JdbcEnvironment jdbcEnvironment) {
		return name;
	}

	@Override
	public Identifier toPhysicalSchemaName(Identifier name, JdbcEnvironment jdbcEnvironment) {
		return name;
	}

	@Override
	public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment jdbcEnvironment) {
		String tableName = name.getText();
		
		if (tableName.startsWith("TB_") == true) {
			return name;
		}
		
		tableName = "TB_" + camelToUnderscore(tableName);
		Identifier calemCaseTableName = new Identifier(tableName.toUpperCase(), false);
		
		return calemCaseTableName;
	}

	@Override
	public Identifier toPhysicalSequenceName(Identifier name, JdbcEnvironment jdbcEnvironment) {
		return name;
	}

	@Override
	public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment jdbcEnvironment) {
		String text = camelToUnderscore(name.getText());
		boolean isQuated = name.isQuoted();
		
		Identifier camelCase = new Identifier(text.toUpperCase(), isQuated);
		
		return camelCase;
	}

}

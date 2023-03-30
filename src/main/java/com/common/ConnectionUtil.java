package com.common;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionUtil {
	
	private static final String RESOURCE = "java:/comp/env";
	private static final String RESOURCE_NAME = "jdbc/oracle";
	
	public static  DataSource getDataSource() {
		DataSource dataSource = null;
		try {
			Context ctx = new InitialContext();
			Context envCtx = (Context) ctx.lookup(RESOURCE);
			dataSource = (DataSource) envCtx.lookup(RESOURCE_NAME);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return dataSource;
	}
	
}

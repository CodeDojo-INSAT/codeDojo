package com.zs.codeDojo.properties;

public class Properties {
    public final static String sqlScriptsBase = "../models/SQLScripts/";

    public static final String procedureScript = sqlScriptsBase + "procedure.sql";
    public static final String eventScript = sqlScriptsBase + "event.sql";

    static ClassLoader classLoader = new Properties().getClass().getClassLoader();
    public static final String logPath = classLoader.getResource("com/zs/codeDojo/logs/error.log").getFile();

    public static final String db_username = "arjun";
    public static final String db_password = "password@1";
    public static final String db_host = "localhost";
    public static final String db_port = "3306";
    public static final String db_name = "servlet";
}

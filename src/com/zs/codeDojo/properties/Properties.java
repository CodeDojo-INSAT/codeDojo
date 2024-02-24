package com.zs.codeDojo.properties;

public class Properties {
    public final static String sqlScriptsBase = "/opt/apache/webapps/codeDojo/src/com/zs/codeDojo/models/SQLScripts/";

    public static final String procedureScript = sqlScriptsBase + "procedure.sql";
    public static final String eventScript = sqlScriptsBase + "event.sql";

    public static final String logPath = "/opt/apache/webapps/codeDojo/src/com/zs/codeDojo/logs/error.log";


    public static final String db_username = "arjun";
    public static final String db_password = "password@1";
    public static final String db_host = "localhost";
    public static final String db_port = "3306";
    public static final String db_name = "servlet";
}

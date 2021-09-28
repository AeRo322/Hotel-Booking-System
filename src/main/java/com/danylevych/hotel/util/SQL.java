package com.danylevych.hotel.util;

public final class SQL {

    private SQL() {

    }

    public static String formatSql(Object[] objects) {
	StringBuilder stringBuilder = new StringBuilder();
	for (Object object : objects) {
	    stringBuilder.append(object).append(',');
	}
	stringBuilder.deleteCharAt(stringBuilder.length() - 1);
	return stringBuilder.toString();
    }

    public static String generateSqlFind(Object column, int n,
            String... tables) {
	return generateSqlFind(column, "*", n, tables);
    }

    public static String generateSqlFind(Object column, String aggregate, int n,
            String... tables) {
	final String sql = "SELECT %s"
	                   + " FROM %s"
	                   + " WHERE %s IN (%s)";

	StringBuilder placeholders = new StringBuilder();
	for (int i = 0; i < n; i++) {
	    placeholders.append('?').append(',');
	}
	placeholders.deleteCharAt(placeholders.length() - 1);

	StringBuilder fromClause = new StringBuilder();
	for (int i = 0; i < tables.length; i++) {
	    fromClause.append('`').append(tables[i]).append('`').append(',');
	}
	fromClause.deleteCharAt(fromClause.length() - 1);

	return String.format(sql, aggregate, fromClause, column, placeholders);
    }

    public static String generateSqlUpdate(Object[] columns, int n,
            String table) {
	final String sql = "UPDATE `%s`"
	                   + " SET %s"
	                   + " WHERE %s";

	final String whereClause = String.format("%s=?", columns[n]);
	StringBuilder s = new StringBuilder();
	for (int i = 0; i < n; i++) {
	    s.append(columns[i]).append("=?,");
	}
	s.deleteCharAt(s.length() - 1);

	return String.format(sql, table, s, whereClause);
    }

    public static String generateSqlInsert(Object[] columns, int n,
            String table) {
	final String sql = "INSERT INTO `%s`"
	                   + " (%s)"
	                   + " VALUES(%s)";

	StringBuilder columnNames = new StringBuilder();
	StringBuilder placeholders = new StringBuilder();
	for (int i = 0; i < n; i++) {
	    columnNames.append(columns[i]).append(',');
	    placeholders.append('?').append(',');
	}
	columnNames.deleteCharAt(columnNames.length() - 1);
	placeholders.deleteCharAt(placeholders.length() - 1);

	return String.format(sql, table, columnNames, placeholders);
    }
}

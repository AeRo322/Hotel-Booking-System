package com.danylevych.hotel.dao;

import static com.danylevych.hotel.util.DbUtils.close;
import static com.danylevych.hotel.util.DbUtils.rollback;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.danylevych.hotel.entity.Entity;

public abstract class JdbcDao<T extends Entity> {

    protected final DaoFactory daoFactory;
    private final String table;

    protected JdbcDao(DaoFactory daoFactory, String table) {
	this.daoFactory = daoFactory;
	this.table = '`' + table + '`';
    }

    protected Connection getConnection() throws SQLException {
	return daoFactory.getConnection();
    }

    protected abstract T mapEntity(ResultSet resultSet);

    public abstract List<T> list(int limit, int offset, String orderBy,
            boolean isAscending) throws DaoException;

    protected List<T> list(String sql, Object... values) throws DaoException {
	List<T> list = new ArrayList<>();

	try (Connection c = getConnection();
	     PreparedStatement s = prepareStatement(c, sql, values);
	     ResultSet resultSet = s.executeQuery()) {

	    while (resultSet.next()) {
		T item = mapEntity(resultSet);
		list.add(item);
	    }

	} catch (Exception e) {
	    throw new DaoException(e);
	}

	return list;
    }

    protected void update(String sql, Object... values) throws DaoException {
	try (Connection c = getConnection();
	     PreparedStatement s = prepareStatement(c, sql, values)) {
	    final int rowsAffected = s.executeUpdate();
	    if (rowsAffected <= 0) {
		throw new DaoException("Couldn't update, no rows affected");
	    }
	} catch (SQLException e) {
	    throw new DaoException(e);
	}
    }

    protected T find(String sql, Object... values) throws DaoException {
	try (Connection c = getConnection();
	     PreparedStatement s = prepareStatement(c, sql, values);
	     ResultSet resultSet = s.executeQuery()) {

	    if (!resultSet.next()) {
		return null;
	    }

	    return mapEntity(resultSet);
	} catch (Exception e) {
	    throw new DaoException(e);
	}
    }

    protected long create(Connection c, String sql, Object... values)
            throws DaoException {
	return create(c, false, sql, values);
    }

    protected long create(Connection c, T t) {
	try {
	    return create(c, t, false);
	} catch (DaoException e) {
	    throw new IllegalStateException(e);
	}
    }

    private String formatInsertSql(T t) {
	String sql = "INSERT INTO %s"
	             + " (%s)"
	             + " VALUES(%s)";
	return sql;

	// String.format(sql, table, t.getInsertColumns(),
	// t.getInsertColumns());
    }

    protected long create(Connection c, T t, boolean shouldReturnGeneratedKeys)
            throws DaoException {
	final String sql = formatInsertSql(t);

	try (PreparedStatement s = prepareStatement(c, sql,
	        shouldReturnGeneratedKeys, t.extract())) {
	    final int rowsAffected = s.executeUpdate();
	    if (rowsAffected <= 0) {
		throw new DaoException("Couldn't insert, no rows affected");
	    }

	    return shouldReturnGeneratedKeys ? getGeneratedKey(s) : 0;
	} catch (SQLException e) {
	    throw new DaoException(e);
	}
    }

    protected long create(Connection c, boolean shouldReturnGeneratedKeys,
            String sql, Object... values) throws DaoException {
	try (PreparedStatement s =
	        prepareStatement(c, sql, shouldReturnGeneratedKeys, values)) {
	    final int rowsAffected = s.executeUpdate();
	    if (rowsAffected <= 0) {
		throw new DaoException("Couldn't insert, no rows affected");
	    }

	    return shouldReturnGeneratedKeys ? getGeneratedKey(s) : 0;
	} catch (SQLException e) {
	    throw new DaoException(e);
	}
    }

    public int count() throws DaoException {
	String sql = "SELECT COUNT(*)"
	             + " FROM %s";

	sql = String.format(sql, table);

	try (Connection c = getConnection();
	     PreparedStatement s = prepareStatement(c, sql);
	     ResultSet resultSet = s.executeQuery()) {

	    if (!resultSet.next()) {
		return 0;
	    }

	    return resultSet.getInt(1);
	} catch (SQLException e) {
	    throw new DaoException(e);
	}
    }

    private long getGeneratedKey(Statement statement) throws DaoException {
	try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
	    if (!generatedKeys.next()) {
		throw new DaoException("Result set is empty");
	    }

	    final long generatedKey = generatedKeys.getLong(1);
	    if (generatedKey == 0) {
		throw new DaoException("Generated key equals to 0");
	    }

	    return generatedKey;
	} catch (SQLException e) {
	    throw new DaoException(e);
	}
    }

    protected void executeAsTransaction(Consumer<Connection> function)
            throws DaoException {
	Connection connection = null;

	try {
	    connection = daoFactory.getTransactionConnection();
	    function.accept(connection);
	    connection.commit();
	} catch (Exception e) {
	    rollback(connection);
	    throw new DaoException(e);
	} finally {
	    close(connection);
	}
    }

    private PreparedStatement prepareStatement(Connection connection,
            String sql, Object... values) throws SQLException {
	return prepareStatement(connection, sql, false, values);
    }

    private PreparedStatement prepareStatement(Connection connection,
            String sql, boolean shouldReturnGeneratedKeys, Object... values)
            throws SQLException {
	PreparedStatement statement = connection.prepareStatement(sql,
	        shouldReturnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS
	                                  : Statement.NO_GENERATED_KEYS);
	try {
	    setValues(statement, values);
	} catch (Exception e) {
	    statement.close();
	    throw e;
	}
	return statement;
    }

    private void setValues(PreparedStatement statement, Object[] values)
            throws SQLException {
	int k = 1;
	for (Object value : values) {
	    statement.setObject(k++, value);
	}
    }

}

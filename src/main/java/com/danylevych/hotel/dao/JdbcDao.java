package com.danylevych.hotel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

import com.danylevych.hotel.entity.User;
import com.danylevych.hotel.util.Loggers;
import com.danylevych.hotel.util.SQL;

public abstract class JdbcDao<T> {

    protected final DaoFactory daoFactory;
    private final Object[] columns;
    private final String table;
    private final int n;

    protected JdbcDao(DaoFactory daoFactory, String table, Object[] columns,
                      int n) {
	this.daoFactory = daoFactory;
	this.columns = columns;
	this.table = table;
	this.n = n;
    }

    protected Connection getConnection() throws SQLException {
	return daoFactory.getConnection();
    }

    protected abstract T mapEntity(ResultSet resultSet);

    protected abstract String generateSqlFind(int n);

    protected abstract Object getWhereValue(T t);

    protected abstract Object[] getValues(T t);

    private Object[] getUpdateValues(T t) {
	Stream<Object> values = Stream.of(getValues(t));
	Stream<Object> whereValue = Stream.of(getWhereValue(t));
	return Stream.concat(values, whereValue).toArray();
    }

    private String generateSqlUpdate() {
	return SQL.generateSqlUpdate(columns, n, table);
    }

    private String generateSqlInsert() {
	return SQL.generateSqlInsert(columns, n, table);
    }

    public abstract List<T> list(int limit, int offset, String orderBy,
            boolean isAscending, User user);

    protected List<T> list(String sql, Object... values) {
	List<T> list = new ArrayList<>();
	try (Connection c = getConnection();
	     PreparedStatement s = prepareStatement(c, sql, values);
	     ResultSet resultSet = s.executeQuery()) {

	    while (resultSet.next()) {
		T item = mapEntity(resultSet);
		list.add(item);
	    }

	} catch (Exception e) {
	    throw new IllegalStateException(e);
	}
	return list;
    }

    public List<T> find(Object... ids) {
	return list(generateSqlFind(ids.length), ids);
    }

    public T find(long id) {
	List<T> list = list(generateSqlFind(1), id);
	return list.isEmpty() ? null : list.get(0);
    }

    protected T find(String sql, Object... values) {
	List<T> list = list(sql, values);
	return list.isEmpty() ? null : list.get(0);
    }

    public int count() {
	String sql = "SELECT COUNT(*)"
	             + " FROM `%s`";

	sql = String.format(sql, table);

	try (Connection c = getConnection();
	     PreparedStatement s = prepareStatement(c, sql);
	     ResultSet resultSet = s.executeQuery()) {

	    if (!resultSet.next()) {
		return 0;
	    }

	    return resultSet.getInt(1);
	} catch (SQLException e) {
	    throw new IllegalStateException(e);
	}
    }

    public long create(Connection c, boolean shouldReturnGeneratedKeys, T t) {
	return create(c, shouldReturnGeneratedKeys, generateSqlInsert(),
	        getValues(t));
    }

    public void create(T t) {
	create(generateSqlInsert(), getValues(t));
    }

    public long create(Connection c, T t) {
	return create(c, false, generateSqlInsert(), getValues(t));
    }

    protected long create(Connection c, String sql, Object... values) {
	return create(c, false, sql, values);
    }

    protected int create(String sql, Object... values) {
	return update(sql, values);
    }

    protected long create(Connection c, boolean shouldReturnGeneratedKeys,
            String sql, Object... values) {
	List<Long> keys = update(c, shouldReturnGeneratedKeys, sql, values);
	return keys == null ? 0 : keys.get(0);
    }

    public void update(Connection c, T t) {
	update(c, false, generateSqlUpdate(), getUpdateValues(t));
    }

    public void update(T t) {
	update(generateSqlUpdate(), getUpdateValues(t));
    }

    protected void update(Connection c, String sql, Object... values) {
	update(c, false, sql, values);
    }

    protected int update(String sql, Object... values) {
	try (Connection c = getConnection();
	     PreparedStatement s = prepareStatement(c, sql, values)) {
	    return s.executeUpdate();
	} catch (SQLException e) {
	    throw new IllegalStateException(e);
	}
    }

    protected List<Long> update(Connection c, boolean shouldReturnGeneratedKeys,
            String sql, Object... values) {
	try (PreparedStatement s =
	        prepareStatement(c, sql, shouldReturnGeneratedKeys, values)) {
	    final int rowsAffected = s.executeUpdate();
	    if (rowsAffected <= 0) {
		throw new IllegalStateException("No rows affected");
	    }

	    return shouldReturnGeneratedKeys ? getGeneratedKeys(s) : null;
	} catch (SQLException e) {
	    throw new IllegalStateException(e);
	}
    }

    private List<Long> getGeneratedKeys(Statement statement) {
	List<Long> keys = new ArrayList<>();
	try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
	    while (generatedKeys.next()) {
		final long generatedKey = generatedKeys.getLong(1);
		if (generatedKey == 0) {
		    throw new IllegalStateException("Generated key is 0");
		}
		keys.add(generatedKey);
	    }
	    return keys;
	} catch (SQLException e) {
	    throw new IllegalStateException(e);
	}
    }

    protected void transaction(Consumer<Connection> function) {
	Connection connection = null;
	try {
	    connection = daoFactory.getConnection();
	    connection.setAutoCommit(false);
	    function.accept(connection);
	    connection.commit();
	} catch (Exception e) {
	    rollback(connection);
	    throw new IllegalStateException(e);
	} finally {
	    close(connection);
	}
    }

    protected PreparedStatement prepareStatement(Connection connection,
            String sql, Object... values) {
	return prepareStatement(connection, sql, false, values);
    }

    private PreparedStatement prepareStatement(Connection connection,
            String sql, boolean shouldReturnGeneratedKeys, Object... values) {
	PreparedStatement statement = null;
	try {
	    statement = connection.prepareStatement(sql,
	            shouldReturnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS
	                                      : Statement.NO_GENERATED_KEYS);
	    setValues(statement, values);
	} catch (Exception e) {
	    close(statement);
	    throw new IllegalStateException(e);
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

    protected void rollback(Connection connection) {
	if (connection != null) {
	    try {
		connection.rollback();
	    } catch (SQLException e) {
		Loggers.log(e);
	    }
	}
    }

    protected void close(AutoCloseable autoCloseable) {
	if (autoCloseable != null) {
	    try {
		autoCloseable.close();
	    } catch (Exception e) {
		Loggers.log(e);
	    }
	}
    }

}

package com.danylevych.hotel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.danylevych.hotel.util.Loggers;

public abstract class JdbcDao<T> {

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
            boolean isAscending, Object... values);

    public List<Integer> getRoomNumbers(Long orderDetailsId) {
	final String sql = "SELECT room_number"
	                   + " FROM order_details_has_room"
	                   + " WHERE order_details_id = ?";

	List<Integer> list = new ArrayList<>();

	try (Connection c = getConnection();
	     PreparedStatement s = prepareStatement(c, sql, orderDetailsId);
	     ResultSet resultSet = s.executeQuery()) {

	    while (resultSet.next()) {
		final int roomNumber = resultSet.getInt("room_number");

		list.add(roomNumber);
	    }

	} catch (Exception e) {
	    throw new IllegalStateException(e);
	}

	return list;
    }

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

    public T find(String sql, Object... values) {
	try (Connection c = getConnection();
	     PreparedStatement s = prepareStatement(c, sql, values);
	     ResultSet resultSet = s.executeQuery()) {

	    if (!resultSet.next()) {
		return null;
	    }

	    return mapEntity(resultSet);
	} catch (Exception e) {
	    throw new IllegalStateException(e);
	}
    }

    protected void update(Connection c, String sql, Object... values) {
	update(c, false, sql, values);
    }

    protected void create(String sql, Object... values) {
	update(sql, values);
    }

    protected long create(Connection c, String sql, Object... values) {
	return create(c, false, sql, values);
    }

    protected long create(Connection c, boolean shouldReturnGeneratedKeys,
            String sql, Object... values) {

	List<Long> keys = update(c, shouldReturnGeneratedKeys, sql, values);
	if (keys == null) {
	    return 0;
	}
	return keys.get(0);
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

    public int count() {
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
	    throw new IllegalStateException(e);
	}
    }

    private List<Long> getGeneratedKeys(Statement statement) {
	List<Long> keys = new ArrayList<>();

	try (ResultSet generatedKeys = statement.getGeneratedKeys()) {

	    while (generatedKeys.next()) {
		final long generatedKey = generatedKeys.getLong(1);
		if (generatedKey == 0) {
		    throw new IllegalStateException(
		            "Generated key equals to 0");
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

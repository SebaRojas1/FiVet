/*
 * Copyright (c) 2022 Sebastián Rojas Rodriguez sebastian.rojas04@alumnos.ucn.cl
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package cl.ucn.disc.pdis.fivet.orm;

import com.google.common.collect.Lists;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * ORMLiteDAO class.
 *
 * @author Sebastian Rojas
 * @param <T> the base entity
 */
@Slf4j
public final class ORMLiteDAO<T extends BaseEntity> implements DAO<T> {

    /**
     * The real DAO (connection to ORMLite DAO).
     */
    private final Dao<T, Integer> dao;

    private static ConnectionSource cs;

    /**
     * The constructor of ORMLiteDAO.
     *
     * @param cs the connection to the database
     * @param clazz the type of T
     */
    @SneakyThrows(SQLException.class)
    public ORMLiteDAO(@NonNull final ConnectionSource cs, @NonNull final Class<T> clazz) {
        this.dao = DaoManager.createDao(cs, clazz);
    }

    /**
     * The builder of the connection source.
     *
     * @param s to use
     * @return the cs
     */
    @SneakyThrows
    public static ConnectionSource buildConnectionSource(String s) {
        cs = new JdbcConnectionSource(s);
        return cs;
    }

    /**
     * Get optinal, T.
     *
     * @param id to search
     * @return a T
     */
    @SneakyThrows(SQLException.class)
    @Override
    public Optional<T> get(Integer id) {
        // Exec the SQL
        T t = this.dao.queryForId(id);
        if (t == null) {
            return Optional.empty();
        }
        if (t.getDeletedAt() != null) {
            return Optional.empty();
        }
        return Optional.of(t);
    }

    /**
     * Get optional, T.
     *
     * @param attrib the name of attribute
     * @param value the value
     * @return a T
     */
    @SneakyThrows
    @Override
    public Optional<T> get(String attrib, Object value) {
        List<T> list = this.dao.queryForEq(attrib, value);

        for (T t : list) {
            if (t.getDeletedAt() == null) {
                return Optional.of(t);
            }
        }
        return Optional.empty();
    }

    /**
     * Get all the Ts.
     *
     * @return the List of T
     */
    @SneakyThrows(SQLException.class)
    @Override
    public List<T> getAll() {
        List<T> list = Lists.newArrayList();

        for (T t : this.dao.queryForAll()) {
            if (t.getDeletedAt() == null) {
                list.add(t);
            }
        }
        return list;
    }

    /**
     * Save a T.
     *
     * @param t to save
     */
    @SneakyThrows(SQLException.class)
    @Override
    public void save(T t) {
        int created = this.dao.create(t);

        if (created != 1) {
            throw new SQLException("Rows created != 1");
        }

    }

    /**
     * Delete a T adding the delete date.
     *
     * @param t to delete
     */
    @SneakyThrows(SQLException.class)
    @Override
    public void delete(T t) {

        t.deletedAt = ZonedDateTime.now();

        if (this.dao.update(t) != 1) {
            throw new SQLException("Rows updated != 1");
        }
    }


    /**
     * Delete a T with id adding the delete date.
     *
     * @param id of the t to delete
     */
    @SneakyThrows(SQLException.class)
    @Override
    public void delete(Integer id) {
        Optional<T> t = this.get(id);
        if (t.isEmpty()) {
            log.warn("Entity t {} not found", id);
            return;
        }
        t.get().deletedAt = ZonedDateTime.now();

        if (this.dao.update(t.get()) != 1) {
            throw new SQLException("Rows updated != 1");
        }
    }

    /**
     * Drop and create a new table.
     */
    @Override
    @SneakyThrows
    public void dropAndCreateTable() {
        TableUtils.dropTable(cs, dao.getDataClass(), true);
        TableUtils.createTable(cs, dao.getDataClass());
    }

}

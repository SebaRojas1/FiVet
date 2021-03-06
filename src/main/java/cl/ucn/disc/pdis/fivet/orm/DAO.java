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

import java.util.List;
import java.util.Optional;

/**
 * The dao.
 *
 * @author Sebastian Rojas
 * @param <T> the base entity
 */
public interface DAO<T extends BaseEntity> {

    /**
     * Get optional, T.
     *
     * @param id to search
     * @return a T
     */
    Optional<T> get(Integer id);

    /**
     * Get optional, T.
     *
     * @param attrib attribute in the table
     * @param value value to get a row
     * @return a T
     */
    Optional<T> get(String attrib, Object value);

    /**
     * Get all the Ts.
     *
     * @return all entities
     */
    List<T> getAll();

    /**
     * Save a T.
     *
     * @param t to save
     */
    void save(T t);

    /**
     * Delete a T.
     *
     * @param t to delete
     */
    void delete(T t);

    /**
     * Delete a T with id.
     *
     * @param id to find and delete
     */
    void delete(Integer id);

    /**
     * Drop and create a new table.
     */
    void dropAndCreateTable();
}

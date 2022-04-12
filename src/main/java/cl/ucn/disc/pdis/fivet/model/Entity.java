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

package cl.ucn.disc.pdis.fivet.model;

import java.time.LocalDateTime;

/**
 * The model of the entity objects
 *
 * @author Sebastiàn Rojas
 */
public abstract class Entity {

    /**
     * The id of the entity
     */
    protected Integer id;

    /**
     * date and time of the entity deleted
     */
    protected LocalDateTime deletedAt;

    /**
     * date and time of the entity created
     */
    protected LocalDateTime createdAt;

    /**
     * Void constructor required
     */
    Entity() {

    }

    /**
     * The constructor of entity
     * @param id The id of the entity
     * @param deletedAt datetime of the deleted entity
     * @param createdAt datetime of the created entity
     */
    public Entity(Integer id, LocalDateTime deletedAt, LocalDateTime createdAt) {

        this.id = id;
        this.deletedAt = deletedAt;
        this.createdAt = createdAt;

    }

}

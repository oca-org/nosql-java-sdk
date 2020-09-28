/*-
 * Copyright (c) 2011, 2020 Oracle and/or its affiliates.  All rights reserved.
 *
 * Licensed under the Universal Permissive License v 1.0 as shown at
 *  https://oss.oracle.com/licenses/upl/
 */

package oracle.nosql.driver.ops;

import oracle.nosql.driver.Consistency;
import oracle.nosql.driver.NoSQLHandle;

/**
 * Cloud service only.
 * <p>
 * A TableLimits instance is used during table creation to specify the
 * throughput and capacity to be consumed by the table. It is also used
 * in an operation to change the limits of an existing table.
 * {@link NoSQLHandle#tableRequest} and {@link TableRequest} are used to
 * perform these operations. These values are enforced by the system and
 * used for billing purposes.
 * <p>
 * Throughput limits are defined in terms of read units and write units.  A
 * read unit represents 1 eventually consistent read per second for data up to
 * 1 KB in size. A read that is absolutely consistent is double that, consuming
 * 2 read units for a read of up to 1 KB in size. This means that if an
 * application is to use {@link Consistency#ABSOLUTE} it may need to specify
 * additional read units when creating a table. A write unit represents 1 write
 * per second of data up to 1 KB in size.
 * <p>
 * In addition to throughput table capacity must be specified to indicate
 * the maximum amount of storage, in gigabytes, allowed for the table.
 * <p>
 * All 3 values must be used whenever using this object. There are no defaults
 * and no mechanism to indicate "no change."
 *
 * @see NoSQLHandle#tableRequest
 */
public class TableLimits {

    private int readUnits;
    private int writeUnits;
    private int storageGB;

    /**
     * Constructs a TableLimits instance.
     *
     * @param readUnits the desired throughput of read operation in terms of
     * read units. A read unit represents 1 eventually consistent read
     * per second for data up to 1 KB in size. A read that is absolutely
     * consistent is double that, consuming 2 read units for a read of up to
     * 1 KB in size. See {@link Consistency}.
     *
     * @param writeUnits the desired throughput of write operation in terms of
     * write units. A write unit represents 1 write per second of data up to
     * 1 KB in size.
     *
     * @param storageGB the maximum storage to be consumed by the table, in
     * gigabytes
     */
    public TableLimits(int readUnits,
                       int writeUnits,
                       int storageGB) {
        this.readUnits = readUnits;
        this.writeUnits = writeUnits;
        this.storageGB = storageGB;
    }

    /**
     * Returns the read throughput in terms of read units
     * @return read units
     */
    public int getReadUnits() {
        return readUnits;
    }

    /**
     * Returns the write throughput in terms of write units
     * @return write units
     */
    public int getWriteUnits() {
        return writeUnits;
    }

    /**
     * Returns the storage capacity in gigabytes
     * @return provisioned storage size in GB
     */
    public int getStorageGB() {
        return storageGB;
    }

    /**
     * Sets the read throughput in terms of read units.
     *
     * @param readUnits the throughput to use, in read units
     *
     * @return this
     */
    public TableLimits setReadUnits(int readUnits) {
        this.readUnits = readUnits;
        return this;
    }

    /**
     * Sets the write throughput in terms of write units.
     *
     * @param writeUnits the throughput to use, in write units
     *
     * @return this
     */
    public TableLimits setWriteUnits(int writeUnits) {
        this.writeUnits = writeUnits;
        return this;
    }


    /**
     * Sets the storage capacity in gigabytes.
     *
     * @param storageGB the capacity to use, in gigabytes
     *
     * @return this
     */
    public TableLimits setStorageGB(int storageGB) {
        this.storageGB = storageGB;
        return this;
    }

    @Override
    public String toString() {
        return "[" + readUnits + ", " + writeUnits + ", " + storageGB + "]";
    }

    /**
     * @hidden
     */
    void validate() {
        if (readUnits <= 0 || writeUnits <= 0 || storageGB <= 0) {
            throw new IllegalArgumentException(
                "TableLimits values must be non-negative");
        }
    }
}

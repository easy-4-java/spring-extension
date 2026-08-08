package org.springframework.extension.jdbc;

import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for {@link DataSourceRoutingKeyHolder}.
 */
public class DataSourceRoutingKeyHolderTest {

    @After
    public void cleanup() {
        DataSourceRoutingKeyHolder.clearDataSourceKey();
        DataSourceRoutingKeyHolder.dataSourceKeys.clear();
    }

    @Test
    public void shouldHaveDefaultMasterDataSource() {
        assertEquals("defaultDataSource", DataSourceRoutingKeyHolder.MASTER_DATASOURCE);
    }

    @Test
    public void shouldGetDefaultDataSourceKey() {
        String key = DataSourceRoutingKeyHolder.getDataSourceKey();
        assertEquals(DataSourceRoutingKeyHolder.MASTER_DATASOURCE, key);
    }

    @Test
    public void shouldSetDataSourceKey() {
        DataSourceRoutingKeyHolder.setDataSourceKey("slave1");
        assertEquals("slave1", DataSourceRoutingKeyHolder.getDataSourceKey());
    }

    @Test
    public void shouldClearDataSourceKey() {
        DataSourceRoutingKeyHolder.setDataSourceKey("slave1");
        DataSourceRoutingKeyHolder.clearDataSourceKey();
        assertEquals(DataSourceRoutingKeyHolder.MASTER_DATASOURCE, DataSourceRoutingKeyHolder.getDataSourceKey());
    }

    @Test
    public void shouldContainDataSourceKey() {
        DataSourceRoutingKeyHolder.dataSourceKeys.add("slave1");
        assertTrue(DataSourceRoutingKeyHolder.containDataSourceKey("slave1"));
        assertFalse(DataSourceRoutingKeyHolder.containDataSourceKey("slave2"));
    }

    @Test
    public void shouldUseSlaveDataSource() {
        DataSourceRoutingKeyHolder.dataSourceKeys.add("slave1");
        DataSourceRoutingKeyHolder.useSlaveDataSource();
        String key = DataSourceRoutingKeyHolder.getDataSourceKey();
        assertEquals("slave1", key);
    }

    @Test
    public void shouldFallbackToMasterWhenNoSlaves() {
        DataSourceRoutingKeyHolder.useSlaveDataSource();
        assertEquals(DataSourceRoutingKeyHolder.MASTER_DATASOURCE, DataSourceRoutingKeyHolder.getDataSourceKey());
    }

    @Test
    public void shouldHaveDataSourceKeysList() {
        assertNotNull(DataSourceRoutingKeyHolder.dataSourceKeys);
    }
}

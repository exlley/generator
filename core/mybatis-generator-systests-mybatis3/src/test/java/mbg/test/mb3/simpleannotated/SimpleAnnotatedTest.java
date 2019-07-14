/**
 *    Copyright 2006-2019 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package mbg.test.mb3.simpleannotated;

import static mbg.test.common.util.TestUtilities.datesAreEqual;
import static mbg.test.common.util.TestUtilities.timesAreEqual;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import mbg.test.common.util.TestUtilities;
import mbg.test.mb3.generated.simpleannotated.mapper.AwfulTableMapper;
import mbg.test.mb3.generated.simpleannotated.mapper.FieldsBlobsMapper;
import mbg.test.mb3.generated.simpleannotated.mapper.FieldsOnlyMapper;
import mbg.test.mb3.generated.simpleannotated.mapper.PKBlobsMapper;
import mbg.test.mb3.generated.simpleannotated.mapper.PKFieldsMapper;
import mbg.test.mb3.generated.simpleannotated.mapper.PKFieldsBlobsMapper;
import mbg.test.mb3.generated.simpleannotated.mapper.PKOnlyMapper;
import mbg.test.mb3.generated.simpleannotated.model.AwfulTable;
import mbg.test.mb3.generated.simpleannotated.model.FieldsBlobs;
import mbg.test.mb3.generated.simpleannotated.model.FieldsOnly;
import mbg.test.mb3.generated.simpleannotated.model.PKBlobs;
import mbg.test.mb3.generated.simpleannotated.model.PKFields;
import mbg.test.mb3.generated.simpleannotated.model.PKFieldsBlobs;
import mbg.test.mb3.generated.simpleannotated.model.PKOnly;

public class SimpleAnnotatedTest extends AbstractSimpleAnnotatedTest {

    @Test
    public void testAwfulTable() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            AwfulTableMapper mapper = sqlSession.getMapper(AwfulTableMapper.class);
            
            AwfulTable record = new AwfulTable();
            record.setFirstFirstName("Fred");
            int rows = mapper.insert(record);
            assertEquals(1, rows);
            assertNotNull(record.getCustomerId());
            
            record = new AwfulTable();
            record.setFirstFirstName("Barney");
            rows = mapper.insert(record);
            assertEquals(1, rows);
            assertNotNull(record.getCustomerId());
            
            List<AwfulTable> records = mapper.selectAll();
            assertEquals(2, records.size());
            
            AwfulTable returnedRecord = mapper.selectByPrimaryKey(record.getCustomerId());
            assertNotNull(returnedRecord);
            assertEquals(record.getFirstFirstName(), returnedRecord.getFirstFirstName());
            
            record.setFirstFirstName("Betty");
            rows = mapper.updateByPrimaryKey(record);
            assertEquals(1, rows);
            
            returnedRecord = mapper.selectByPrimaryKey(record.getCustomerId());
            assertNotNull(returnedRecord);
            assertEquals(record.getFirstFirstName(), returnedRecord.getFirstFirstName());
            
            rows = mapper.deleteByPrimaryKey(record.getCustomerId());
            assertEquals(1, rows);
            
            records = mapper.selectAll();
            assertEquals(1, records.size());
            
        } finally {
            sqlSession.close();
        }
    }
    

    @Test
    public void testPKFieldsInsert() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            PKFieldsMapper mapper = sqlSession.getMapper(PKFieldsMapper.class);
            PKFields record = new PKFields();
            record.setDatefield(new Date());
            record.setDecimal100field(10L);
            record.setDecimal155field(new BigDecimal("15.12345"));
            record.setDecimal30field((short) 3);
            record.setDecimal60field(6);
            record.setFirstname("Jeff");
            record.setId1(1);
            record.setId2(2);
            record.setLastname("Butler");
            record.setTimefield(new Date());
            record.setTimestampfield(new Date());

            mapper.insert(record);

            PKFields returnedRecord = mapper.selectByPrimaryKey(2, 1);
            assertNotNull(returnedRecord);

            assertTrue(datesAreEqual(record.getDatefield(), returnedRecord
                    .getDatefield()));
            assertEquals(record.getDecimal100field(), returnedRecord
                    .getDecimal100field());
            assertEquals(record.getDecimal155field(), returnedRecord
                    .getDecimal155field());
            assertEquals(record.getDecimal30field(), returnedRecord
                    .getDecimal30field());
            assertEquals(record.getDecimal60field(), returnedRecord
                    .getDecimal60field());
            assertEquals(record.getFirstname(), returnedRecord.getFirstname());
            assertEquals(record.getId1(), returnedRecord.getId1());
            assertEquals(record.getId2(), returnedRecord.getId2());
            assertEquals(record.getLastname(), returnedRecord.getLastname());
            assertTrue(timesAreEqual(record.getTimefield(), returnedRecord
                    .getTimefield()));
            assertEquals(record.getTimestampfield(), returnedRecord
                    .getTimestampfield());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKFieldsUpdateByPrimaryKey() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            PKFieldsMapper mapper = sqlSession.getMapper(PKFieldsMapper.class);
            PKFields record = new PKFields();
            record.setFirstname("Jeff");
            record.setLastname("Smith");
            record.setId1(1);
            record.setId2(2);

            mapper.insert(record);

            record.setFirstname("Scott");
            record.setLastname("Jones");

            int rows = mapper.updateByPrimaryKey(record);
            assertEquals(1, rows);

            PKFields record2 = mapper.selectByPrimaryKey(2, 1);
            assertNotNull(record2);

            assertEquals(record.getFirstname(), record2.getFirstname());
            assertEquals(record.getLastname(), record2.getLastname());
            assertEquals(record.getId1(), record2.getId1());
            assertEquals(record.getId2(), record2.getId2());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKfieldsDeleteByPrimaryKey() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            PKFieldsMapper mapper = sqlSession.getMapper(PKFieldsMapper.class);
            PKFields record = new PKFields();
            record.setFirstname("Jeff");
            record.setLastname("Smith");
            record.setId1(1);
            record.setId2(2);

            mapper.insert(record);

            int rows = mapper.deleteByPrimaryKey(2, 1);
            assertEquals(1, rows);

            List<PKFields> answer = mapper.selectAll();
            assertEquals(0, answer.size());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKFieldsSelectByPrimaryKey() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            PKFieldsMapper mapper = sqlSession.getMapper(PKFieldsMapper.class);
            PKFields record = new PKFields();
            record.setFirstname("Jeff");
            record.setLastname("Smith");
            record.setId1(1);
            record.setId2(2);
            mapper.insert(record);

            record = new PKFields();
            record.setFirstname("Bob");
            record.setLastname("Jones");
            record.setId1(3);
            record.setId2(4);
            mapper.insert(record);

            PKFields newRecord = mapper.selectByPrimaryKey(4, 3);

            assertNotNull(newRecord);
            assertEquals(record.getFirstname(), newRecord.getFirstname());
            assertEquals(record.getLastname(), newRecord.getLastname());
            assertEquals(record.getId1(), newRecord.getId1());
            assertEquals(record.getId2(), newRecord.getId2());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKFieldsSelectAll() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            PKFieldsMapper mapper = sqlSession.getMapper(PKFieldsMapper.class);
            PKFields record = new PKFields();
            record.setFirstname("Fred");
            record.setLastname("Flintstone");
            record.setId1(1);
            record.setId2(1);
            mapper.insert(record);

            record = new PKFields();
            record.setFirstname("Wilma");
            record.setLastname("Flintstone");
            record.setId1(1);
            record.setId2(2);
            mapper.insert(record);

            record = new PKFields();
            record.setFirstname("Pebbles");
            record.setLastname("Flintstone");
            record.setId1(1);
            record.setId2(3);
            mapper.insert(record);

            record = new PKFields();
            record.setFirstname("Barney");
            record.setLastname("Rubble");
            record.setId1(2);
            record.setId2(1);
            mapper.insert(record);

            record = new PKFields();
            record.setFirstname("Betty");
            record.setLastname("Rubble");
            record.setId1(2);
            record.setId2(2);
            mapper.insert(record);

            record = new PKFields();
            record.setFirstname("Bamm Bamm");
            record.setLastname("Rubble");
            record.setId1(2);
            record.setId2(3);
            mapper.insert(record);

            List<PKFields> answer = mapper.selectAll();
            assertEquals(6, answer.size());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testFieldsOnly() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            FieldsOnlyMapper mapper = sqlSession.getMapper(FieldsOnlyMapper.class);
            
            FieldsOnly record = new FieldsOnly();
            record.setDoublefield(1.23);
            record.setFloatfield(4.35);
            record.setIntegerfield(9);
            mapper.insert(record);
            
            record = new FieldsOnly();
            record.setDoublefield(11.2233);
            record.setFloatfield(44.3355);
            record.setIntegerfield(99);
            mapper.insert(record);
            
            List<FieldsOnly> records = mapper.selectAll();
            
            assertEquals(2, records.size());
            
        } finally {
            sqlSession.close();
        }
    }
    
    @Test
    public void testFieldsBlobs() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            FieldsBlobsMapper mapper = sqlSession.getMapper(FieldsBlobsMapper.class);
            
            FieldsBlobs record = new FieldsBlobs();
            record.setFirstname("Fred");
            record.setBlob1(TestUtilities.generateRandomBlob());
            mapper.insert(record);
            
            record = new FieldsBlobs();
            record.setFirstname("Barney");
            record.setBlob1(TestUtilities.generateRandomBlob());
            mapper.insert(record);
            
            List<FieldsBlobs> records = mapper.selectAll();
            
            assertEquals(2, records.size());
            
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKBlobs() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            PKBlobsMapper mapper = sqlSession.getMapper(PKBlobsMapper.class);
            
            PKBlobs record = new PKBlobs();
            record.setId(1);
            record.setBlob1(TestUtilities.generateRandomBlob());
            int rows = mapper.insert(record);
            assertEquals(1, rows);
            
            record = new PKBlobs();
            record.setId(2);
            record.setBlob1(TestUtilities.generateRandomBlob());
            rows = mapper.insert(record);
            assertEquals(1, rows);
            
            List<PKBlobs> records = mapper.selectAll();
            assertEquals(2, records.size());
            
            PKBlobs returnedRecord = mapper.selectByPrimaryKey(record.getId());
            assertNotNull(returnedRecord);
            assertTrue(TestUtilities.blobsAreEqual(record.getBlob1(), returnedRecord.getBlob1()));
            
            record.setBlob1(TestUtilities.generateRandomBlob());
            rows = mapper.updateByPrimaryKey(record);
            assertEquals(1, rows);
            
            returnedRecord = mapper.selectByPrimaryKey(record.getId());
            assertNotNull(returnedRecord);
            assertTrue(TestUtilities.blobsAreEqual(record.getBlob1(), returnedRecord.getBlob1()));
            
            rows = mapper.deleteByPrimaryKey(record.getId());
            assertEquals(1, rows);
            
            records = mapper.selectAll();
            assertEquals(1, records.size());
            
        } finally {
            sqlSession.close();
        }
    }
    
    @Test
    public void testPKFieldsBlobs() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            PKFieldsBlobsMapper mapper = sqlSession.getMapper(PKFieldsBlobsMapper.class);
            
            PKFieldsBlobs record = new PKFieldsBlobs();
            record.setId1(1);
            record.setId2(1);
            record.setBlob1(TestUtilities.generateRandomBlob());
            int rows = mapper.insert(record);
            assertEquals(1, rows);
            
            record = new PKFieldsBlobs();
            record.setId1(2);
            record.setId2(2);
            record.setBlob1(TestUtilities.generateRandomBlob());
            rows = mapper.insert(record);
            assertEquals(1, rows);
            
            List<PKFieldsBlobs> records = mapper.selectAll();
            assertEquals(2, records.size());
            
            PKFieldsBlobs returnedRecord = mapper.selectByPrimaryKey(record.getId1(), record.getId2());
            assertNotNull(returnedRecord);
            assertTrue(TestUtilities.blobsAreEqual(record.getBlob1(), returnedRecord.getBlob1()));
            
            record.setBlob1(TestUtilities.generateRandomBlob());
            rows = mapper.updateByPrimaryKey(record);
            assertEquals(1, rows);
            
            returnedRecord = mapper.selectByPrimaryKey(record.getId1(), record.getId2());
            assertNotNull(returnedRecord);
            assertTrue(TestUtilities.blobsAreEqual(record.getBlob1(), returnedRecord.getBlob1()));
            
            rows = mapper.deleteByPrimaryKey(record.getId1(), record.getId2());
            assertEquals(1, rows);
            
            records = mapper.selectAll();
            assertEquals(1, records.size());
            
        } finally {
            sqlSession.close();
        }
    }
    
    @Test
    public void testPKOnly() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            PKOnlyMapper mapper = sqlSession.getMapper(PKOnlyMapper.class);

            PKOnly record = new PKOnly();
            record.setId(1);
            record.setSeqNum(1);
            mapper.insert(record);
            
            record = new PKOnly();
            record.setId(1);
            record.setSeqNum(2);
            mapper.insert(record);
            
            record = new PKOnly();
            record.setId(2);
            record.setSeqNum(1);
            mapper.insert(record);
            
            record = new PKOnly();
            record.setId(2);
            record.setSeqNum(2);
            mapper.insert(record);
            
            List<PKOnly> records = mapper.selectAll();
            assertEquals(4, records.size());
            
            mapper.deleteByPrimaryKey(record.getId(), record.getSeqNum());
            
            records = mapper.selectAll();
            assertEquals(3, records.size());
        } finally {
            sqlSession.close();
        }
    }
}

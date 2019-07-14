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
package mbg.test.mb3.hierarchical.immutable;

import static mbg.test.common.util.TestUtilities.blobsAreEqual;
import static mbg.test.common.util.TestUtilities.generateRandomBlob;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import mbg.test.mb3.generated.hierarchical.immutable.mapper.FieldsBlobsMapper;
import mbg.test.mb3.generated.hierarchical.immutable.mapper.FieldsOnlyMapper;
import mbg.test.mb3.generated.hierarchical.immutable.mapper.PKBlobsMapper;
import mbg.test.mb3.generated.hierarchical.immutable.mapper.PKFieldsMapper;
import mbg.test.mb3.generated.hierarchical.immutable.mapper.PKFieldsBlobsMapper;
import mbg.test.mb3.generated.hierarchical.immutable.mapper.PKOnlyMapper;
import mbg.test.mb3.generated.hierarchical.immutable.model.FieldsBlobs;
import mbg.test.mb3.generated.hierarchical.immutable.model.FieldsBlobsExample;
import mbg.test.mb3.generated.hierarchical.immutable.model.FieldsBlobsWithBLOBs;
import mbg.test.mb3.generated.hierarchical.immutable.model.FieldsOnly;
import mbg.test.mb3.generated.hierarchical.immutable.model.FieldsOnlyExample;
import mbg.test.mb3.generated.hierarchical.immutable.model.PKBlobsExample;
import mbg.test.mb3.generated.hierarchical.immutable.model.PKBlobsKey;
import mbg.test.mb3.generated.hierarchical.immutable.model.PKBlobsWithBLOBs;
import mbg.test.mb3.generated.hierarchical.immutable.model.PKFields;
import mbg.test.mb3.generated.hierarchical.immutable.model.PKFieldsExample;
import mbg.test.mb3.generated.hierarchical.immutable.model.PKFieldsBlobs;
import mbg.test.mb3.generated.hierarchical.immutable.model.PKFieldsBlobsExample;
import mbg.test.mb3.generated.hierarchical.immutable.model.PKFieldsBlobsWithBLOBs;
import mbg.test.mb3.generated.hierarchical.immutable.model.PKOnlyExample;
import mbg.test.mb3.generated.hierarchical.immutable.model.PKOnlyKey;

/**
 * 
 * @author Jeff Butler
 * 
 */
public class UpdateByExampleTest extends AbstractHierarchicalImmutableTest {

    @Test
    public void testFieldsOnlyUpdateByExampleSelective() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            FieldsOnlyMapper mapper = sqlSession
                    .getMapper(FieldsOnlyMapper.class);
            FieldsOnly record = new FieldsOnly(5, 11.22, 33.44);
            mapper.insert(record);

            record = new FieldsOnly(8, 44.55, 66.77);
            mapper.insert(record);

            record = new FieldsOnly(9, 88.99, 100.111);
            mapper.insert(record);

            record = new FieldsOnly(null, 99d, null);
            FieldsOnlyExample example = new FieldsOnlyExample();
            example.createCriteria().andIntegerfieldGreaterThan(5);

            int rows = mapper.updateByExampleSelective(record, example);
            assertEquals(2, rows);

            example.clear();
            example.createCriteria().andIntegerfieldEqualTo(5);
            List<FieldsOnly> answer = mapper.selectByExample(example);
            assertEquals(1, answer.size());
            record = answer.get(0);
            assertEquals(record.getDoublefield(), 11.22, 0.001);
            assertEquals(record.getFloatfield(), 33.44, 0.001);
            assertEquals(record.getIntegerfield().intValue(), 5);

            example.clear();
            example.createCriteria().andIntegerfieldEqualTo(8);
            answer = mapper.selectByExample(example);
            assertEquals(1, answer.size());
            record = answer.get(0);
            assertEquals(record.getDoublefield(), 99d, 0.001);
            assertEquals(record.getFloatfield(), 66.77, 0.001);
            assertEquals(record.getIntegerfield().intValue(), 8);

            example.clear();
            example.createCriteria().andIntegerfieldEqualTo(9);
            answer = mapper.selectByExample(example);
            assertEquals(1, answer.size());
            record = answer.get(0);
            assertEquals(record.getDoublefield(), 99d, 0.001);
            assertEquals(record.getFloatfield(), 100.111, 0.001);
            assertEquals(record.getIntegerfield().intValue(), 9);
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testFieldsOnlyUpdateByExample() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            FieldsOnlyMapper mapper = sqlSession
                    .getMapper(FieldsOnlyMapper.class);
            FieldsOnly record = new FieldsOnly(5, 11.22, 33.44);
            mapper.insert(record);

            record = new FieldsOnly(8, 44.55, 66.77);
            mapper.insert(record);

            record = new FieldsOnly(9, 88.99, 100.111);
            mapper.insert(record);

            record = new FieldsOnly(22, null, null);
            FieldsOnlyExample example = new FieldsOnlyExample();
            example.createCriteria().andIntegerfieldEqualTo(5);

            int rows = mapper.updateByExample(record, example);
            assertEquals(1, rows);

            example.clear();
            example.createCriteria().andIntegerfieldEqualTo(22);
            List<FieldsOnly> answer = mapper.selectByExample(example);
            assertEquals(1, answer.size());
            record = answer.get(0);
            assertNull(record.getDoublefield());
            assertNull(record.getFloatfield());
            assertEquals(record.getIntegerfield().intValue(), 22);
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKOnlyUpdateByExampleSelective() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            PKOnlyMapper mapper = sqlSession.getMapper(PKOnlyMapper.class);
            PKOnlyKey key = new PKOnlyKey(1, 3);
            mapper.insert(key);

            key = new PKOnlyKey(5, 6);
            mapper.insert(key);

            key = new PKOnlyKey(7, 8);
            mapper.insert(key);

            PKOnlyExample example = new PKOnlyExample();
            example.createCriteria().andIdGreaterThan(4);
            key = new PKOnlyKey(null, 3);
            int rows = mapper.updateByExampleSelective(key, example);
            assertEquals(2, rows);

            example.clear();
            example.createCriteria().andIdEqualTo(5).andSeqNumEqualTo(3);

            long returnedRows = mapper.countByExample(example);
            assertEquals(1, returnedRows);

            example.clear();
            example.createCriteria().andIdEqualTo(7).andSeqNumEqualTo(3);

            returnedRows = mapper.countByExample(example);
            assertEquals(1, returnedRows);
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKOnlyUpdateByExample() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            PKOnlyMapper mapper = sqlSession.getMapper(PKOnlyMapper.class);
            PKOnlyKey key = new PKOnlyKey(1, 3);
            mapper.insert(key);

            key = new PKOnlyKey(5, 6);
            mapper.insert(key);

            key = new PKOnlyKey(7, 8);
            mapper.insert(key);

            PKOnlyExample example = new PKOnlyExample();
            example.createCriteria().andIdEqualTo(7);
            key = new PKOnlyKey(22, 3);
            int rows = mapper.updateByExample(key, example);
            assertEquals(1, rows);

            example.clear();
            example.createCriteria().andIdEqualTo(22).andSeqNumEqualTo(3);

            long returnedRows = mapper.countByExample(example);
            assertEquals(1, returnedRows);
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKFieldsUpdateByExampleSelective() {
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

            record = new PKFields();
            record.setFirstname("Fred");
            PKFieldsExample example = new PKFieldsExample();
            example.createCriteria().andLastnameLike("J%");
            int rows = mapper.updateByExampleSelective(record, example);
            assertEquals(1, rows);

            example.clear();
            example.createCriteria().andFirstnameEqualTo("Fred")
                    .andLastnameEqualTo("Jones").andId1EqualTo(3)
                    .andId2EqualTo(4);

            long returnedRows = mapper.countByExample(example);
            assertEquals(1, returnedRows);
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKFieldsUpdateByExample() {
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

            record = new PKFields();
            record.setFirstname("Fred");
            record.setId1(3);
            record.setId2(4);
            PKFieldsExample example = new PKFieldsExample();
            example.createCriteria().andId1EqualTo(3).andId2EqualTo(4);

            int rows = mapper.updateByExample(record, example);
            assertEquals(1, rows);

            example.clear();
            example.createCriteria().andFirstnameEqualTo("Fred")
                    .andLastnameIsNull().andId1EqualTo(3).andId2EqualTo(4);

            long returnedRows = mapper.countByExample(example);
            assertEquals(1, returnedRows);
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKBlobsUpdateByExampleSelective() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            PKBlobsMapper mapper = sqlSession.getMapper(PKBlobsMapper.class);
            PKBlobsWithBLOBs record = new PKBlobsWithBLOBs(3,
                    generateRandomBlob(), generateRandomBlob(), "Long String 1");
            mapper.insert(record);

            record = new PKBlobsWithBLOBs(6, generateRandomBlob(),
                    generateRandomBlob(), "Long String 2");
            mapper.insert(record);

            PKBlobsWithBLOBs newRecord = new PKBlobsWithBLOBs(null,
                    generateRandomBlob(), null, null);

            PKBlobsExample example = new PKBlobsExample();
            example.createCriteria().andIdGreaterThan(4);
            int rows = mapper.updateByExampleSelective(newRecord, example);
            assertEquals(1, rows);

            List<PKBlobsWithBLOBs> answer = mapper
                    .selectByExampleWithBLOBs(example);
            assertEquals(1, answer.size());

            PKBlobsWithBLOBs returnedRecord = answer.get(0);

            assertEquals(6, returnedRecord.getId().intValue());
            assertTrue(blobsAreEqual(newRecord.getBlob1(),
                    returnedRecord.getBlob1()));
            assertTrue(blobsAreEqual(record.getBlob2(),
                    returnedRecord.getBlob2()));
            assertEquals(record.getCharacterlob(), returnedRecord.getCharacterlob());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKBlobsUpdateByExampleWithoutBLOBs() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            PKBlobsMapper mapper = sqlSession.getMapper(PKBlobsMapper.class);
            PKBlobsWithBLOBs record = new PKBlobsWithBLOBs(3,
                    generateRandomBlob(), generateRandomBlob(), "Long String 1");
            mapper.insert(record);

            record = new PKBlobsWithBLOBs(6, generateRandomBlob(),
                    generateRandomBlob(), "Long String 2");
            mapper.insert(record);

            PKBlobsKey newRecord = new PKBlobsKey(8);

            PKBlobsExample example = new PKBlobsExample();
            example.createCriteria().andIdGreaterThan(4);
            int rows = mapper.updateByExample(newRecord, example);
            assertEquals(1, rows);

            List<PKBlobsWithBLOBs> answer = mapper
                    .selectByExampleWithBLOBs(example);
            assertEquals(1, answer.size());

            PKBlobsWithBLOBs returnedRecord = answer.get(0);

            assertEquals(8, returnedRecord.getId().intValue());
            assertTrue(blobsAreEqual(record.getBlob1(),
                    returnedRecord.getBlob1()));
            assertTrue(blobsAreEqual(record.getBlob2(),
                    returnedRecord.getBlob2()));
            assertEquals(record.getCharacterlob(), returnedRecord.getCharacterlob());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKBlobsUpdateByExampleWithBLOBs() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            PKBlobsMapper mapper = sqlSession.getMapper(PKBlobsMapper.class);
            PKBlobsWithBLOBs record = new PKBlobsWithBLOBs(3,
                    generateRandomBlob(), generateRandomBlob(), "Long String 1");
            mapper.insert(record);

            record = new PKBlobsWithBLOBs(6, generateRandomBlob(),
                    generateRandomBlob(), "Long String 2");
            mapper.insert(record);

            PKBlobsWithBLOBs newRecord = new PKBlobsWithBLOBs(8, null, null, null);

            PKBlobsExample example = new PKBlobsExample();
            example.createCriteria().andIdGreaterThan(4);
            int rows = mapper.updateByExampleWithBLOBs(newRecord, example);
            assertEquals(1, rows);

            List<PKBlobsWithBLOBs> answer = mapper
                    .selectByExampleWithBLOBs(example);
            assertEquals(1, answer.size());

            PKBlobsWithBLOBs returnedRecord = answer.get(0);

            assertEquals(8, returnedRecord.getId().intValue());
            assertNull(returnedRecord.getBlob1());
            assertNull(returnedRecord.getBlob2());
            assertNull(returnedRecord.getCharacterlob());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKFieldsBlobsUpdateByExampleSelective() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            PKFieldsBlobsMapper mapper = sqlSession
                    .getMapper(PKFieldsBlobsMapper.class);
            PKFieldsBlobsWithBLOBs record = new PKFieldsBlobsWithBLOBs(3, 4,
                    "Jeff", "Smith", generateRandomBlob());
            mapper.insert(record);

            record = new PKFieldsBlobsWithBLOBs(5, 6, "Scott", "Jones",
                    generateRandomBlob());
            mapper.insert(record);

            PKFieldsBlobsWithBLOBs newRecord = new PKFieldsBlobsWithBLOBs(null,
                    null, "Fred", null, null);
            PKFieldsBlobsExample example = new PKFieldsBlobsExample();
            example.createCriteria().andId1NotEqualTo(3);
            int rows = mapper.updateByExampleSelective(newRecord, example);
            assertEquals(1, rows);

            List<PKFieldsBlobsWithBLOBs> answer = mapper
                    .selectByExampleWithBLOBs(example);
            assertEquals(1, answer.size());

            PKFieldsBlobsWithBLOBs returnedRecord = answer.get(0);

            assertEquals(record.getId1(), returnedRecord.getId1());
            assertEquals(record.getId2(), returnedRecord.getId2());
            assertEquals(newRecord.getFirstname(),
                    returnedRecord.getFirstname());
            assertEquals(record.getLastname(), returnedRecord.getLastname());
            assertTrue(blobsAreEqual(record.getBlob1(),
                    returnedRecord.getBlob1()));

        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKFieldsBlobsUpdateByExampleWithoutBLOBs() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            PKFieldsBlobsMapper mapper = sqlSession
                    .getMapper(PKFieldsBlobsMapper.class);
            PKFieldsBlobsWithBLOBs record = new PKFieldsBlobsWithBLOBs(3, 4,
                    "Jeff", "Smith", generateRandomBlob());
            mapper.insert(record);

            record = new PKFieldsBlobsWithBLOBs(5, 6, "Scott", "Jones",
                    generateRandomBlob());
            mapper.insert(record);

            PKFieldsBlobs newRecord = new PKFieldsBlobs(5, 8, "Fred", null);
            PKFieldsBlobsExample example = new PKFieldsBlobsExample();
            example.createCriteria().andId1EqualTo(5);
            int rows = mapper.updateByExample(newRecord, example);
            assertEquals(1, rows);

            List<PKFieldsBlobsWithBLOBs> answer = mapper
                    .selectByExampleWithBLOBs(example);
            assertEquals(1, answer.size());

            PKFieldsBlobsWithBLOBs returnedRecord = answer.get(0);

            assertEquals(newRecord.getId1(), returnedRecord.getId1());
            assertEquals(newRecord.getId2(), returnedRecord.getId2());
            assertEquals(newRecord.getFirstname(),
                    returnedRecord.getFirstname());
            assertNull(returnedRecord.getLastname());
            assertTrue(blobsAreEqual(record.getBlob1(),
                    returnedRecord.getBlob1()));

        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKFieldsBlobsUpdateByExampleWithBLOBs() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            PKFieldsBlobsMapper mapper = sqlSession
                    .getMapper(PKFieldsBlobsMapper.class);
            PKFieldsBlobsWithBLOBs record = new PKFieldsBlobsWithBLOBs(3, 4,
                    "Jeff", "Smith", generateRandomBlob());
            mapper.insert(record);

            record = new PKFieldsBlobsWithBLOBs(5, 6, "Scott", "Jones",
                    generateRandomBlob());
            mapper.insert(record);

            PKFieldsBlobsWithBLOBs newRecord = new PKFieldsBlobsWithBLOBs(3, 8,
                    "Fred", null, null);
            PKFieldsBlobsExample example = new PKFieldsBlobsExample();
            example.createCriteria().andId1EqualTo(3);
            int rows = mapper.updateByExampleWithBLOBs(newRecord, example);
            assertEquals(1, rows);

            List<PKFieldsBlobsWithBLOBs> answer = mapper
                    .selectByExampleWithBLOBs(example);
            assertEquals(1, answer.size());

            PKFieldsBlobsWithBLOBs returnedRecord = answer.get(0);

            assertEquals(newRecord.getId1(), returnedRecord.getId1());
            assertEquals(newRecord.getId2(), returnedRecord.getId2());
            assertEquals(newRecord.getFirstname(),
                    returnedRecord.getFirstname());
            assertNull(returnedRecord.getLastname());
            assertNull(returnedRecord.getBlob1());

        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testFieldsBlobsUpdateByExampleSelective() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            FieldsBlobsMapper mapper = sqlSession
                    .getMapper(FieldsBlobsMapper.class);
            FieldsBlobsWithBLOBs record = new FieldsBlobsWithBLOBs("Jeff",
                    "Smith", generateRandomBlob(), generateRandomBlob(), null);
            mapper.insert(record);

            record = new FieldsBlobsWithBLOBs("Scott", "Jones",
                    generateRandomBlob(), generateRandomBlob(), null);
            mapper.insert(record);

            FieldsBlobsWithBLOBs newRecord = new FieldsBlobsWithBLOBs(null,
                    "Doe", null, null, null);
            FieldsBlobsExample example = new FieldsBlobsExample();
            example.createCriteria().andFirstnameLike("S%");
            int rows = mapper.updateByExampleSelective(newRecord, example);
            assertEquals(1, rows);

            List<FieldsBlobsWithBLOBs> answer = mapper
                    .selectByExampleWithBLOBs(example);
            assertEquals(1, answer.size());

            FieldsBlobsWithBLOBs returnedRecord = answer.get(0);

            assertEquals(record.getFirstname(), returnedRecord.getFirstname());
            assertEquals(newRecord.getLastname(), returnedRecord.getLastname());
            assertTrue(blobsAreEqual(record.getBlob1(),
                    returnedRecord.getBlob1()));
            assertTrue(blobsAreEqual(record.getBlob2(),
                    returnedRecord.getBlob2()));
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testFieldsBlobsUpdateByExampleWithoutBLOBs() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            FieldsBlobsMapper mapper = sqlSession
                    .getMapper(FieldsBlobsMapper.class);
            FieldsBlobsWithBLOBs record = new FieldsBlobsWithBLOBs("Jeff",
                    "Smith", generateRandomBlob(), generateRandomBlob(), null);
            mapper.insert(record);

            record = new FieldsBlobsWithBLOBs("Scott", "Jones",
                    generateRandomBlob(), generateRandomBlob(), null);
            mapper.insert(record);

            FieldsBlobs newRecord = new FieldsBlobs("Scott", "Doe");
            FieldsBlobsExample example = new FieldsBlobsExample();
            example.createCriteria().andFirstnameLike("S%");
            int rows = mapper.updateByExample(newRecord, example);
            assertEquals(1, rows);

            List<FieldsBlobsWithBLOBs> answer = mapper
                    .selectByExampleWithBLOBs(example);
            assertEquals(1, answer.size());

            FieldsBlobsWithBLOBs returnedRecord = answer.get(0);

            assertEquals(newRecord.getFirstname(),
                    returnedRecord.getFirstname());
            assertEquals(newRecord.getLastname(), returnedRecord.getLastname());
            assertTrue(blobsAreEqual(record.getBlob1(),
                    returnedRecord.getBlob1()));
            assertTrue(blobsAreEqual(record.getBlob2(),
                    returnedRecord.getBlob2()));
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testFieldsBlobsUpdateByExampleWithBLOBs() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            FieldsBlobsMapper mapper = sqlSession
                    .getMapper(FieldsBlobsMapper.class);
            FieldsBlobsWithBLOBs record = new FieldsBlobsWithBLOBs("Jeff",
                    "Smith", generateRandomBlob(), generateRandomBlob(), null);
            mapper.insert(record);

            record = new FieldsBlobsWithBLOBs("Scott", "Jones",
                    generateRandomBlob(), generateRandomBlob(), null);
            mapper.insert(record);

            FieldsBlobsWithBLOBs newRecord = new FieldsBlobsWithBLOBs("Scott",
                    "Doe", null, null, null);
            FieldsBlobsExample example = new FieldsBlobsExample();
            example.createCriteria().andFirstnameLike("S%");
            int rows = mapper.updateByExampleWithBLOBs(newRecord, example);
            assertEquals(1, rows);

            List<FieldsBlobsWithBLOBs> answer = mapper
                    .selectByExampleWithBLOBs(example);
            assertEquals(1, answer.size());

            FieldsBlobsWithBLOBs returnedRecord = answer.get(0);

            assertEquals(newRecord.getFirstname(),
                    returnedRecord.getFirstname());
            assertEquals(newRecord.getLastname(), returnedRecord.getLastname());
            assertNull(returnedRecord.getBlob1());
            assertNull(returnedRecord.getBlob2());
        } finally {
            sqlSession.close();
        }
    }
}

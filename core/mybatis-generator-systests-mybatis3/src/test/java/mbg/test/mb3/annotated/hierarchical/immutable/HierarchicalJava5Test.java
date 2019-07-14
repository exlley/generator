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
package mbg.test.mb3.annotated.hierarchical.immutable;

import static mbg.test.common.util.TestUtilities.blobsAreEqual;
import static mbg.test.common.util.TestUtilities.datesAreEqual;
import static mbg.test.common.util.TestUtilities.generateRandomBlob;
import static mbg.test.common.util.TestUtilities.timesAreEqual;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import mbg.test.mb3.generated.annotated.hierarchical.Immutable.Mapper.FieldsBlobsMapper;
import mbg.test.mb3.generated.annotated.hierarchical.Immutable.Mapper.FieldsOnlyMapper;
import mbg.test.mb3.generated.annotated.hierarchical.Immutable.Mapper.PKBlobsMapper;
import mbg.test.mb3.generated.annotated.hierarchical.Immutable.Mapper.PKFieldsMapper;
import mbg.test.mb3.generated.annotated.hierarchical.Immutable.Mapper.PKFieldsBlobsMapper;
import mbg.test.mb3.generated.annotated.hierarchical.Immutable.Mapper.PKOnlyMapper;
import mbg.test.mb3.generated.annotated.hierarchical.Immutable.Model.FieldsBlobs;
import mbg.test.mb3.generated.annotated.hierarchical.Immutable.Model.FieldsBlobsExample;
import mbg.test.mb3.generated.annotated.hierarchical.Immutable.Model.FieldsBlobsWithBLOBs;
import mbg.test.mb3.generated.annotated.hierarchical.Immutable.Model.FieldsOnly;
import mbg.test.mb3.generated.annotated.hierarchical.Immutable.Model.FieldsOnlyExample;
import mbg.test.mb3.generated.annotated.hierarchical.Immutable.Model.PKBlobsExample;
import mbg.test.mb3.generated.annotated.hierarchical.Immutable.Model.PKBlobsKey;
import mbg.test.mb3.generated.annotated.hierarchical.Immutable.Model.PKBlobsWithBLOBs;
import mbg.test.mb3.generated.annotated.hierarchical.Immutable.Model.PKFields;
import mbg.test.mb3.generated.annotated.hierarchical.Immutable.Model.PKFieldsExample;
import mbg.test.mb3.generated.annotated.hierarchical.Immutable.Model.PKFieldsKey;
import mbg.test.mb3.generated.annotated.hierarchical.Immutable.Model.PKFieldsBlobs;
import mbg.test.mb3.generated.annotated.hierarchical.Immutable.Model.PKFieldsBlobsExample;
import mbg.test.mb3.generated.annotated.hierarchical.Immutable.Model.PKFieldsBlobsKey;
import mbg.test.mb3.generated.annotated.hierarchical.Immutable.Model.PKFieldsBlobsWithBLOBs;
import mbg.test.mb3.generated.annotated.hierarchical.Immutable.Model.PKOnlyExample;
import mbg.test.mb3.generated.annotated.hierarchical.Immutable.Model.PKOnlyKey;

/**
 * @author Jeff Butler
 * 
 */
public class HierarchicalJava5Test extends AbstractAnnotatedHierarchicalImmutableTest {

    @Test
    public void testFieldsOnlyInsert() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            FieldsOnlyMapper mapper = sqlSession.getMapper(FieldsOnlyMapper.class);
            FieldsOnly record = new FieldsOnly(5, 11.22, 33.44);
            mapper.insert(record);

            FieldsOnlyExample example = new FieldsOnlyExample();
            example.createCriteria().andIntegerfieldEqualTo(5);

            List<FieldsOnly> answer = mapper.selectByExample(example);
            assertEquals(1, answer.size());

            FieldsOnly returnedRecord = answer.get(0);
            assertEquals(record.getIntegerfield(), returnedRecord
                    .getIntegerfield());
            assertEquals(record.getDoublefield(), returnedRecord
                    .getDoublefield());
            assertEquals(record.getFloatfield(), returnedRecord.getFloatfield());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testFieldsOnlySelectByExample() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            FieldsOnlyMapper mapper = sqlSession.getMapper(FieldsOnlyMapper.class);
            FieldsOnly record = new FieldsOnly(5, 11.22, 33.44);
            mapper.insert(record);

            record = new FieldsOnly(8, 44.55, 66.77);
            mapper.insert(record);

            record = new FieldsOnly(9, 88.99, 100.111);
            mapper.insert(record);

            FieldsOnlyExample example = new FieldsOnlyExample();
            example.createCriteria().andIntegerfieldGreaterThan(5);

            List<FieldsOnly> answer = mapper.selectByExample(example);
            assertEquals(2, answer.size());

            example = new FieldsOnlyExample();
            answer = mapper.selectByExample(example);
            assertEquals(3, answer.size());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testFieldsOnlySelectByExampleNoCriteria() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            FieldsOnlyMapper mapper = sqlSession.getMapper(FieldsOnlyMapper.class);
            FieldsOnly record = new FieldsOnly(5, 11.22, 33.44);
            mapper.insert(record);

            record = new FieldsOnly(8, 44.55, 66.77);
            mapper.insert(record);

            record = new FieldsOnly(9, 88.99, 100.111);
            mapper.insert(record);

            FieldsOnlyExample example = new FieldsOnlyExample();
            example.createCriteria();

            List<FieldsOnly> answer = mapper.selectByExample(example);
            assertEquals(3, answer.size());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testFieldsOnlyDeleteByExample() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            FieldsOnlyMapper mapper = sqlSession.getMapper(FieldsOnlyMapper.class);
            FieldsOnly record = new FieldsOnly(5, 11.22, 33.44);
            mapper.insert(record);

            record = new FieldsOnly(8, 44.55, 66.77);
            mapper.insert(record);

            record = new FieldsOnly(9, 88.99, 100.111);
            mapper.insert(record);

            FieldsOnlyExample example = new FieldsOnlyExample();
            example.createCriteria().andIntegerfieldGreaterThan(5);

            int rows = mapper.deleteByExample(example);
            assertEquals(2, rows);

            example = new FieldsOnlyExample();
            List<FieldsOnly> answer = mapper.selectByExample(example);
            assertEquals(1, answer.size());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testFieldsOnlyCountByExample() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            FieldsOnlyMapper mapper = sqlSession.getMapper(FieldsOnlyMapper.class);
            FieldsOnly record = new FieldsOnly(5, 11.22, 33.44);
            mapper.insert(record);

            record = new FieldsOnly(8, 44.55, 66.77);
            mapper.insert(record);

            record = new FieldsOnly(9, 88.99, 100.111);
            mapper.insert(record);

            FieldsOnlyExample example = new FieldsOnlyExample();
            example.createCriteria().andIntegerfieldGreaterThan(5);
            long rows = mapper.countByExample(example);
            assertEquals(2, rows);

            example.clear();
            rows = mapper.countByExample(example);
            assertEquals(3, rows);
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKOnlyInsert() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            PKOnlyMapper mapper = sqlSession.getMapper(PKOnlyMapper.class);
            PKOnlyKey key = new PKOnlyKey(1, 3);
            mapper.insert(key);

            PKOnlyExample example = new PKOnlyExample();
            List<PKOnlyKey> answer = mapper.selectByExample(example);
            assertEquals(1, answer.size());

            PKOnlyKey returnedRecord = answer.get(0);
            assertEquals(key.getId(), returnedRecord.getId());
            assertEquals(key.getSeqNum(), returnedRecord.getSeqNum());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKOnlyDeleteByPrimaryKey() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            PKOnlyMapper mapper = sqlSession.getMapper(PKOnlyMapper.class);
            PKOnlyKey key = new PKOnlyKey(1, 3);
            mapper.insert(key);

            key = new PKOnlyKey(5, 6);
            mapper.insert(key);

            PKOnlyExample example = new PKOnlyExample();
            List<PKOnlyKey> answer = mapper.selectByExample(example);
            assertEquals(2, answer.size());

            key = new PKOnlyKey(5, 6);
            int rows = mapper.deleteByPrimaryKey(key);
            assertEquals(1, rows);

            answer = mapper.selectByExample(example);
            assertEquals(1, answer.size());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKOnlyDeleteByExample() {
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
            int rows = mapper.deleteByExample(example);
            assertEquals(2, rows);

            example = new PKOnlyExample();
            List<PKOnlyKey> answer = mapper.selectByExample(example);
            assertEquals(1, answer.size());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKOnlySelectByExample() {
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
            List<PKOnlyKey> answer = mapper.selectByExample(example);
            assertEquals(2, answer.size());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKOnlySelectByExampleNoCriteria() {
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
            example.createCriteria();
            List<PKOnlyKey> answer = mapper.selectByExample(example);
            assertEquals(3, answer.size());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKOnlyCountByExample() {
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
            long rows = mapper.countByExample(example);
            assertEquals(2, rows);

            example.clear();
            rows  = mapper.countByExample(example);
            assertEquals(3, rows);
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

            PKFieldsKey key = new PKFieldsKey();
            key.setId1(1);
            key.setId2(2);

            PKFields returnedRecord = mapper.selectByPrimaryKey(key);
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

            PKFieldsKey key = new PKFieldsKey();
            key.setId1(1);
            key.setId2(2);

            PKFields record2 = mapper.selectByPrimaryKey(key);

            assertEquals(record.getFirstname(), record2.getFirstname());
            assertEquals(record.getLastname(), record2.getLastname());
            assertEquals(record.getId1(), record2.getId1());
            assertEquals(record.getId2(), record2.getId2());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKFieldsUpdateByPrimaryKeySelective() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            PKFieldsMapper mapper = sqlSession.getMapper(PKFieldsMapper.class);
            PKFields record = new PKFields();
            record.setFirstname("Jeff");
            record.setLastname("Smith");
            record.setDecimal60field(5);
            record.setId1(1);
            record.setId2(2);

            mapper.insert(record);

            PKFields newRecord = new PKFields();
            newRecord.setId1(1);
            newRecord.setId2(2);
            newRecord.setFirstname("Scott");
            newRecord.setDecimal60field(4);

            int rows = mapper.updateByPrimaryKeySelective(newRecord);
            assertEquals(1, rows);

            PKFieldsKey key = new PKFieldsKey();
            key.setId1(1);
            key.setId2(2);

            PKFields returnedRecord = mapper.selectByPrimaryKey(key);

            assertTrue(datesAreEqual(record.getDatefield(), returnedRecord
                    .getDatefield()));
            assertEquals(record.getDecimal100field(), returnedRecord
                    .getDecimal100field());
            assertEquals(record.getDecimal155field(), returnedRecord
                    .getDecimal155field());
            assertEquals(record.getDecimal30field(), returnedRecord
                    .getDecimal30field());
            assertEquals(newRecord.getDecimal60field(), returnedRecord
                    .getDecimal60field());
            assertEquals(newRecord.getFirstname(), returnedRecord
                    .getFirstname());
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

            PKFieldsKey key = new PKFieldsKey();
            key.setId1(1);
            key.setId2(2);

            int rows = mapper.deleteByPrimaryKey(key);
            assertEquals(1, rows);

            PKFieldsExample example = new PKFieldsExample();
            List<PKFields> answer = mapper.selectByExample(example);
            assertEquals(0, answer.size());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKFieldsDeleteByExample() {
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

            PKFieldsExample example = new PKFieldsExample();
            List<PKFields> answer = mapper.selectByExample(example);
            assertEquals(2, answer.size());

            example = new PKFieldsExample();
            example.createCriteria().andLastnameLike("J%");
            int rows = mapper.deleteByExample(example);
            assertEquals(1, rows);

            example = new PKFieldsExample();
            answer = mapper.selectByExample(example);
            assertEquals(1, answer.size());
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

            PKFieldsKey key = new PKFieldsKey();
            key.setId1(3);
            key.setId2(4);
            PKFields newRecord = mapper.selectByPrimaryKey(key);

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
    public void testPKFieldsSelectByExampleLike() {
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

            PKFieldsExample example = new PKFieldsExample();
            example.createCriteria().andFirstnameLike("B%");
            example.setOrderByClause("ID1, ID2");
            List<PKFields> answer = mapper.selectByExample(example);
            assertEquals(3, answer.size());
            PKFields returnedRecord = answer.get(0);
            assertEquals(2, returnedRecord.getId1().intValue());
            assertEquals(1, returnedRecord.getId2().intValue());
            returnedRecord = answer.get(1);
            assertEquals(2, returnedRecord.getId1().intValue());
            assertEquals(2, returnedRecord.getId2().intValue());
            returnedRecord = answer.get(2);
            assertEquals(2, returnedRecord.getId1().intValue());
            assertEquals(3, returnedRecord.getId2().intValue());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKFieldsSelectByExampleNotLike() {
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

            PKFieldsExample example = new PKFieldsExample();
            example.createCriteria().andFirstnameNotLike("B%");
            example.setOrderByClause("ID1, ID2");
            List<PKFields> answer = mapper.selectByExample(example);
            assertEquals(3, answer.size());
            PKFields returnedRecord = answer.get(0);
            assertEquals(1, returnedRecord.getId1().intValue());
            assertEquals(1, returnedRecord.getId2().intValue());
            returnedRecord = answer.get(1);
            assertEquals(1, returnedRecord.getId1().intValue());
            assertEquals(2, returnedRecord.getId2().intValue());
            returnedRecord = answer.get(2);
            assertEquals(1, returnedRecord.getId1().intValue());
            assertEquals(3, returnedRecord.getId2().intValue());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKFieldsSelectByExampleComplexLike() {
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

            PKFieldsExample example = new PKFieldsExample();
            example.createCriteria().andFirstnameLike("B%").andId2EqualTo(3);
            example.or(example.createCriteria().andFirstnameLike("Wi%"));

            example.setOrderByClause("ID1, ID2");
            List<PKFields> answer = mapper.selectByExample(example);
            assertEquals(2, answer.size());
            PKFields returnedRecord = answer.get(0);
            assertEquals(1, returnedRecord.getId1().intValue());
            assertEquals(2, returnedRecord.getId2().intValue());
            returnedRecord = answer.get(1);
            assertEquals(2, returnedRecord.getId1().intValue());
            assertEquals(3, returnedRecord.getId2().intValue());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKFieldsSelectByExampleIn() {
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

            List<Integer> ids = new ArrayList<>();
            ids.add(1);
            ids.add(3);

            PKFieldsExample example = new PKFieldsExample();
            example.createCriteria().andId2In(ids);

            example.setOrderByClause("ID1, ID2");
            List<PKFields> answer = mapper.selectByExample(example);
            assertEquals(4, answer.size());
            PKFields returnedRecord = answer.get(0);
            assertEquals(1, returnedRecord.getId1().intValue());
            assertEquals(1, returnedRecord.getId2().intValue());
            returnedRecord = answer.get(1);
            assertEquals(1, returnedRecord.getId1().intValue());
            assertEquals(3, returnedRecord.getId2().intValue());
            returnedRecord = answer.get(2);
            assertEquals(2, returnedRecord.getId1().intValue());
            assertEquals(1, returnedRecord.getId2().intValue());
            returnedRecord = answer.get(3);
            assertEquals(2, returnedRecord.getId1().intValue());
            assertEquals(3, returnedRecord.getId2().intValue());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKFieldsSelectByExampleBetween() {
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

            PKFieldsExample example = new PKFieldsExample();
            example.createCriteria().andId2Between(1, 3);

            example.setOrderByClause("ID1, ID2");
            List<PKFields> answer = mapper.selectByExample(example);
            assertEquals(6, answer.size());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKFieldsSelectByExampleNoCriteria() {
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

            PKFieldsExample example = new PKFieldsExample();
            example.createCriteria();

            example.setOrderByClause("ID1, ID2");
            List<PKFields> answer = mapper.selectByExample(example);
            assertEquals(6, answer.size());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKFieldsSelectByExampleEscapedFields() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            PKFieldsMapper mapper = sqlSession.getMapper(PKFieldsMapper.class);
            PKFields record = new PKFields();
            record.setFirstname("Fred");
            record.setLastname("Flintstone");
            record.setId1(1);
            record.setId2(1);
            record.setWierdField(11);
            mapper.insert(record);

            record = new PKFields();
            record.setFirstname("Wilma");
            record.setLastname("Flintstone");
            record.setId1(1);
            record.setId2(2);
            record.setWierdField(22);
            mapper.insert(record);

            record = new PKFields();
            record.setFirstname("Pebbles");
            record.setLastname("Flintstone");
            record.setId1(1);
            record.setId2(3);
            record.setWierdField(33);
            mapper.insert(record);

            record = new PKFields();
            record.setFirstname("Barney");
            record.setLastname("Rubble");
            record.setId1(2);
            record.setId2(1);
            record.setWierdField(44);
            mapper.insert(record);

            record = new PKFields();
            record.setFirstname("Betty");
            record.setLastname("Rubble");
            record.setId1(2);
            record.setId2(2);
            record.setWierdField(55);
            mapper.insert(record);

            record = new PKFields();
            record.setFirstname("Bamm Bamm");
            record.setLastname("Rubble");
            record.setId1(2);
            record.setId2(3);
            record.setWierdField(66);
            mapper.insert(record);

            List<Integer> values = new ArrayList<>();
            values.add(11);
            values.add(22);

            PKFieldsExample example = new PKFieldsExample();
            example.createCriteria().andWierdFieldLessThan(40).andWierdFieldIn(
                    values);

            example.setOrderByClause("ID1, ID2");
            List<PKFields> answer = mapper.selectByExample(example);
            assertEquals(2, answer.size());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKFieldsCountByExample() {
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

            PKFieldsExample example = new PKFieldsExample();
            example.createCriteria().andLastnameLike("J%");
            long rows = mapper.countByExample(example);
            assertEquals(1, rows);

            example.clear();
            rows = mapper.countByExample(example);
            assertEquals(2, rows);
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKBlobsInsert() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            PKBlobsMapper mapper = sqlSession.getMapper(PKBlobsMapper.class);
            PKBlobsWithBLOBs record = new PKBlobsWithBLOBs(3, generateRandomBlob(), generateRandomBlob(),
                    "Long String 1");
            mapper.insert(record);

            PKBlobsExample example = new PKBlobsExample();
            List<PKBlobsWithBLOBs> answer = mapper
                    .selectByExampleWithBLOBs(example);
            assertEquals(1, answer.size());

            PKBlobsWithBLOBs returnedRecord = answer.get(0);
            assertEquals(record.getId(), returnedRecord.getId());
            assertTrue(blobsAreEqual(record.getBlob1(), returnedRecord
                    .getBlob1()));
            assertTrue(blobsAreEqual(record.getBlob2(), returnedRecord
                    .getBlob2()));
            assertEquals(record.getCharacterlob(), returnedRecord.getCharacterlob());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKBlobsUpdateByPrimaryKeyWithBLOBs() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            PKBlobsMapper mapper = sqlSession.getMapper(PKBlobsMapper.class);
            PKBlobsWithBLOBs record = new PKBlobsWithBLOBs(3, generateRandomBlob(), generateRandomBlob(),
                    "Long String 1");
            mapper.insert(record);

            record = new PKBlobsWithBLOBs(3, generateRandomBlob(), generateRandomBlob(),
                    "Long String 2");
            int rows = mapper.updateByPrimaryKeyWithBLOBs(record);
            assertEquals(1, rows);

            PKBlobsKey key = new PKBlobsKey(3);

            PKBlobsWithBLOBs newRecord = mapper.selectByPrimaryKey(key);

            assertNotNull(newRecord);
            assertEquals(record.getId(), newRecord.getId());
            assertTrue(blobsAreEqual(record.getBlob1(), newRecord.getBlob1()));
            assertTrue(blobsAreEqual(record.getBlob2(), newRecord.getBlob2()));
            assertEquals(record.getCharacterlob(), newRecord.getCharacterlob());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKBlobsUpdateByPrimaryKeySelective() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            PKBlobsMapper mapper = sqlSession.getMapper(PKBlobsMapper.class);
            PKBlobsWithBLOBs record = new PKBlobsWithBLOBs(3, generateRandomBlob(), generateRandomBlob(),
                    "Long String 1");
            mapper.insert(record);

            PKBlobsWithBLOBs newRecord = new PKBlobsWithBLOBs(3, null, generateRandomBlob(),
                    "Long String 2");
            mapper.updateByPrimaryKeySelective(newRecord);

            PKBlobsKey key = new PKBlobsKey(3);

            PKBlobsWithBLOBs returnedRecord = mapper.selectByPrimaryKey(key);
            assertNotNull(returnedRecord);
            assertEquals(record.getId(), returnedRecord.getId());
            assertTrue(blobsAreEqual(record.getBlob1(), returnedRecord
                    .getBlob1()));
            assertTrue(blobsAreEqual(newRecord.getBlob2(), returnedRecord
                    .getBlob2()));
            assertEquals(newRecord.getCharacterlob(), returnedRecord.getCharacterlob());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKBlobsDeleteByPrimaryKey() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            PKBlobsMapper mapper = sqlSession.getMapper(PKBlobsMapper.class);
            PKBlobsWithBLOBs record = new PKBlobsWithBLOBs(3, generateRandomBlob(), generateRandomBlob(),
                    "Long String 1");
            mapper.insert(record);

            PKBlobsExample example = new PKBlobsExample();
            List<PKBlobsKey> answer = mapper.selectByExample(example);
            assertEquals(1, answer.size());

            PKBlobsKey key = new PKBlobsKey(3);
            int rows = mapper.deleteByPrimaryKey(key);
            assertEquals(1, rows);

            example = new PKBlobsExample();
            answer = mapper.selectByExample(example);
            assertEquals(0, answer.size());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKBlobsDeleteByExample() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            PKBlobsMapper mapper = sqlSession.getMapper(PKBlobsMapper.class);
            PKBlobsWithBLOBs record = new PKBlobsWithBLOBs(3, generateRandomBlob(), generateRandomBlob(),
                    "Long String 1");
            mapper.insert(record);

            record = new PKBlobsWithBLOBs(6, generateRandomBlob(), generateRandomBlob(),
                    "Long String 2");
            mapper.insert(record);

            PKBlobsExample example = new PKBlobsExample();
            List<PKBlobsKey> answer = mapper.selectByExample(example);
            assertEquals(2, answer.size());

            example = new PKBlobsExample();
            example.createCriteria().andIdLessThan(4);
            int rows = mapper.deleteByExample(example);
            assertEquals(1, rows);

            example = new PKBlobsExample();
            answer = mapper.selectByExample(example);
            assertEquals(1, answer.size());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKBlobsSelectByPrimaryKey() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            PKBlobsMapper mapper = sqlSession.getMapper(PKBlobsMapper.class);
            PKBlobsWithBLOBs record = new PKBlobsWithBLOBs(3, generateRandomBlob(), generateRandomBlob(),
                    "Long String 1");
            mapper.insert(record);

            record = new PKBlobsWithBLOBs(6, generateRandomBlob(), generateRandomBlob(),
                    "Long String 2");
            mapper.insert(record);

            PKBlobsKey key = new PKBlobsKey(6);
            PKBlobsWithBLOBs newRecord = mapper.selectByPrimaryKey(key);
            assertNotNull(newRecord);
            assertEquals(record.getId(), newRecord.getId());
            assertTrue(blobsAreEqual(record.getBlob1(), newRecord.getBlob1()));
            assertTrue(blobsAreEqual(record.getBlob2(), newRecord.getBlob2()));
            assertEquals(record.getCharacterlob(), newRecord.getCharacterlob());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKBlobsSelectByExampleWithoutBlobs() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            PKBlobsMapper mapper = sqlSession.getMapper(PKBlobsMapper.class);
            PKBlobsWithBLOBs record = new PKBlobsWithBLOBs(3, generateRandomBlob(), generateRandomBlob(),
                    "Long String 1");
            mapper.insert(record);

            record = new PKBlobsWithBLOBs(6, generateRandomBlob(), generateRandomBlob(),
                    "Long String 2");
            mapper.insert(record);

            PKBlobsExample example = new PKBlobsExample();
            example.createCriteria().andIdGreaterThan(4);
            List<PKBlobsKey> answer = mapper.selectByExample(example);

            assertEquals(1, answer.size());

            PKBlobsKey key = answer.get(0);
            assertFalse(key instanceof PKBlobsWithBLOBs);
            assertEquals(6, key.getId().intValue());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKBlobsSelectByExampleWithoutBlobsNoCriteria() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            PKBlobsMapper mapper = sqlSession.getMapper(PKBlobsMapper.class);
            PKBlobsWithBLOBs record = new PKBlobsWithBLOBs(3, generateRandomBlob(), generateRandomBlob(),
                    "Long String 1");
            mapper.insert(record);

            record = new PKBlobsWithBLOBs(6, generateRandomBlob(), generateRandomBlob(),
                    "Long String 2");
            mapper.insert(record);

            PKBlobsExample example = new PKBlobsExample();
            example.createCriteria();
            List<PKBlobsKey> answer = mapper.selectByExample(example);

            assertEquals(2, answer.size());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKBlobsSelectByExampleWithBlobs() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            PKBlobsMapper mapper = sqlSession.getMapper(PKBlobsMapper.class);
            PKBlobsWithBLOBs record = new PKBlobsWithBLOBs(3, generateRandomBlob(), generateRandomBlob(),
                    "Long String 1");
            mapper.insert(record);

            record = new PKBlobsWithBLOBs(6, generateRandomBlob(), generateRandomBlob(),
                    "Long String 2");
            mapper.insert(record);

            PKBlobsExample example = new PKBlobsExample();
            example.createCriteria().andIdGreaterThan(4);
            List<PKBlobsWithBLOBs> answer = mapper
                    .selectByExampleWithBLOBs(example);

            assertEquals(1, answer.size());

            PKBlobsWithBLOBs newRecord = answer.get(0);
            assertEquals(record.getId(), newRecord.getId());
            assertTrue(blobsAreEqual(record.getBlob1(), newRecord.getBlob1()));
            assertTrue(blobsAreEqual(record.getBlob2(), newRecord.getBlob2()));
            assertEquals(record.getCharacterlob(), newRecord.getCharacterlob());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKBlobsCountByExample() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            PKBlobsMapper mapper = sqlSession.getMapper(PKBlobsMapper.class);
            PKBlobsWithBLOBs record = new PKBlobsWithBLOBs(3, generateRandomBlob(), generateRandomBlob(),
                    "Long String 1");
            mapper.insert(record);

            record = new PKBlobsWithBLOBs(6, generateRandomBlob(), generateRandomBlob(),
                    "Long String 2");
            mapper.insert(record);

            PKBlobsExample example = new PKBlobsExample();
            example.createCriteria().andIdLessThan(4);
            long rows = mapper.countByExample(example);
            assertEquals(1, rows);

            example.clear();
            rows = mapper.countByExample(example);
            assertEquals(2, rows);
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKFieldsBlobsInsert() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            PKFieldsBlobsMapper mapper = sqlSession.getMapper(PKFieldsBlobsMapper.class);
            PKFieldsBlobsWithBLOBs record = new PKFieldsBlobsWithBLOBs(1, 2, ":Jeff", "Smith", generateRandomBlob());
            mapper.insert(record);

            PKFieldsBlobsExample example = new PKFieldsBlobsExample();
            List<PKFieldsBlobsWithBLOBs> answer = mapper
                    .selectByExampleWithBLOBs(example);
            assertEquals(1, answer.size());

            PKFieldsBlobsWithBLOBs returnedRecord = answer.get(0);
            assertEquals(record.getId1(), returnedRecord.getId1());
            assertEquals(record.getId2(), returnedRecord.getId2());
            assertEquals(record.getFirstname(), returnedRecord.getFirstname());
            assertEquals(record.getLastname(), returnedRecord.getLastname());
            assertTrue(blobsAreEqual(record.getBlob1(), returnedRecord
                    .getBlob1()));
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKFieldsBlobsUpdateByPrimaryKeyWithBLOBs() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            PKFieldsBlobsMapper mapper = sqlSession.getMapper(PKFieldsBlobsMapper.class);
            PKFieldsBlobsWithBLOBs record = new PKFieldsBlobsWithBLOBs(3, 4, "Jeff", "Smith", generateRandomBlob());
            mapper.insert(record);

            PKFieldsBlobsWithBLOBs updateRecord = new PKFieldsBlobsWithBLOBs(3, 4, "Scott", "Jones", generateRandomBlob());

            int rows = mapper.updateByPrimaryKeyWithBLOBs(updateRecord);
            assertEquals(1, rows);

            PKFieldsBlobsKey key = new PKFieldsBlobsKey(3, 4);
            PKFieldsBlobsWithBLOBs newRecord = mapper.selectByPrimaryKey(key);
            assertEquals(updateRecord.getFirstname(), newRecord.getFirstname());
            assertEquals(updateRecord.getLastname(), newRecord.getLastname());
            assertEquals(record.getId1(), newRecord.getId1());
            assertEquals(record.getId2(), newRecord.getId2());
            assertTrue(blobsAreEqual(updateRecord.getBlob1(), newRecord
                    .getBlob1()));
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKFieldsBlobsUpdateByPrimaryKeyWithoutBLOBs() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            PKFieldsBlobsMapper mapper = sqlSession.getMapper(PKFieldsBlobsMapper.class);
            PKFieldsBlobsWithBLOBs record = new PKFieldsBlobsWithBLOBs(3, 4, "Jeff", "Smith", generateRandomBlob());
            mapper.insert(record);

            PKFieldsBlobs updateRecord = new PKFieldsBlobs(3, 4, "Scott", "Jones");

            int rows = mapper.updateByPrimaryKey(updateRecord);
            assertEquals(1, rows);

            PKFieldsBlobsKey key = new PKFieldsBlobsKey(3, 4);
            PKFieldsBlobsWithBLOBs newRecord = mapper.selectByPrimaryKey(key);
            assertEquals(updateRecord.getFirstname(), newRecord.getFirstname());
            assertEquals(updateRecord.getLastname(), newRecord.getLastname());
            assertEquals(record.getId1(), newRecord.getId1());
            assertEquals(record.getId2(), newRecord.getId2());
            assertTrue(blobsAreEqual(record.getBlob1(), newRecord.getBlob1()));
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKFieldsBlobsUpdateByPrimaryKeySelective() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            PKFieldsBlobsMapper mapper = sqlSession.getMapper(PKFieldsBlobsMapper.class);
            PKFieldsBlobsWithBLOBs record = new PKFieldsBlobsWithBLOBs(3, 4, "Jeff", "Smith", generateRandomBlob());
            mapper.insert(record);

            PKFieldsBlobsWithBLOBs updateRecord = new PKFieldsBlobsWithBLOBs(3, 4, null, "Jones", null);

            int rows = mapper.updateByPrimaryKeySelective(updateRecord);
            assertEquals(1, rows);

            PKFieldsBlobsKey key = new PKFieldsBlobsKey(3, 4);
            PKFieldsBlobsWithBLOBs returnedRecord = mapper.selectByPrimaryKey(key);
            assertEquals(record.getFirstname(), returnedRecord.getFirstname());
            assertEquals(updateRecord.getLastname(), returnedRecord
                    .getLastname());
            assertEquals(record.getId1(), returnedRecord.getId1());
            assertEquals(record.getId2(), returnedRecord.getId2());
            assertTrue(blobsAreEqual(record.getBlob1(), returnedRecord
                    .getBlob1()));
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKFieldsBlobsDeleteByPrimaryKey() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            PKFieldsBlobsMapper mapper = sqlSession.getMapper(PKFieldsBlobsMapper.class);
            PKFieldsBlobsWithBLOBs record = new PKFieldsBlobsWithBLOBs(3, 4, "Jeff", "Smith", generateRandomBlob());
            mapper.insert(record);

            record = new PKFieldsBlobsWithBLOBs(5, 6, "Scott", "Jones", generateRandomBlob());
            mapper.insert(record);

            PKFieldsBlobsExample example = new PKFieldsBlobsExample();
            List<PKFieldsBlobs> answer = mapper
                    .selectByExample(example);
            assertEquals(2, answer.size());

            PKFieldsBlobsKey key = new PKFieldsBlobsKey(5, 6);
            int rows = mapper.deleteByPrimaryKey(key);
            assertEquals(1, rows);

            example = new PKFieldsBlobsExample();
            answer = mapper.selectByExample(example);
            assertEquals(1, answer.size());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKFieldsBlobsDeleteByExample() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            PKFieldsBlobsMapper mapper = sqlSession.getMapper(PKFieldsBlobsMapper.class);
            PKFieldsBlobsWithBLOBs record = new PKFieldsBlobsWithBLOBs(3, 4, "Jeff", "Smith", generateRandomBlob());
            mapper.insert(record);

            record = new PKFieldsBlobsWithBLOBs(5, 6, "Scott", "Jones", generateRandomBlob());
            mapper.insert(record);

            PKFieldsBlobsExample example = new PKFieldsBlobsExample();
            List<PKFieldsBlobs> answer = mapper
                    .selectByExample(example);
            assertEquals(2, answer.size());

            example = new PKFieldsBlobsExample();
            example.createCriteria().andId1NotEqualTo(3);
            int rows = mapper.deleteByExample(example);
            assertEquals(1, rows);

            example = new PKFieldsBlobsExample();
            answer = mapper.selectByExample(example);
            assertEquals(1, answer.size());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKFieldsBlobsSelectByPrimaryKey() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            PKFieldsBlobsMapper mapper = sqlSession.getMapper(PKFieldsBlobsMapper.class);
            PKFieldsBlobsWithBLOBs record = new PKFieldsBlobsWithBLOBs(3, 4, "Jeff", "Smith", generateRandomBlob());
            mapper.insert(record);

            record = new PKFieldsBlobsWithBLOBs(5, 6, "Scott", "Jones", generateRandomBlob());
            mapper.insert(record);

            PKFieldsBlobsExample example = new PKFieldsBlobsExample();
            List<PKFieldsBlobs> answer = mapper
                    .selectByExample(example);
            assertEquals(2, answer.size());

            PKFieldsBlobsKey key = new PKFieldsBlobsKey(5, 6);
            PKFieldsBlobsWithBLOBs newRecord = mapper.selectByPrimaryKey(key);
            assertEquals(record.getId1(), newRecord.getId1());
            assertEquals(record.getId2(), newRecord.getId2());
            assertEquals(record.getFirstname(), newRecord.getFirstname());
            assertEquals(record.getLastname(), newRecord.getLastname());
            assertTrue(blobsAreEqual(record.getBlob1(), newRecord.getBlob1()));
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKFieldsBlobsSelectByExampleWithoutBlobs() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            PKFieldsBlobsMapper mapper = sqlSession.getMapper(PKFieldsBlobsMapper.class);
            PKFieldsBlobsWithBLOBs record = new PKFieldsBlobsWithBLOBs(3, 4, "Jeff", "Smith", generateRandomBlob());
            mapper.insert(record);

            record = new PKFieldsBlobsWithBLOBs(5, 6, "Scott", "Jones", generateRandomBlob());
            mapper.insert(record);

            PKFieldsBlobsExample example = new PKFieldsBlobsExample();
            example.createCriteria().andId2EqualTo(6);
            List<PKFieldsBlobs> answer = mapper
                    .selectByExample(example);
            assertEquals(1, answer.size());

            PKFieldsBlobs newRecord = answer.get(0);
            assertFalse(newRecord instanceof PKFieldsBlobsWithBLOBs);
            assertEquals(record.getId1(), newRecord.getId1());
            assertEquals(record.getId2(), newRecord.getId2());
            assertEquals(record.getFirstname(), newRecord.getFirstname());
            assertEquals(record.getLastname(), newRecord.getLastname());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKFieldsBlobsSelectByExampleWithBlobs() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            PKFieldsBlobsMapper mapper = sqlSession.getMapper(PKFieldsBlobsMapper.class);
            PKFieldsBlobsWithBLOBs record = new PKFieldsBlobsWithBLOBs(3, 4, "Jeff", "Smith", generateRandomBlob());
            mapper.insert(record);

            record = new PKFieldsBlobsWithBLOBs(5, 6, "Scott", "Jones", generateRandomBlob());
            mapper.insert(record);

            PKFieldsBlobsExample example = new PKFieldsBlobsExample();
            example.createCriteria().andId2EqualTo(6);
            List<PKFieldsBlobsWithBLOBs> answer = mapper
                    .selectByExampleWithBLOBs(example);
            assertEquals(1, answer.size());

            PKFieldsBlobsWithBLOBs newRecord = answer.get(0);
            assertEquals(record.getId1(), newRecord.getId1());
            assertEquals(record.getId2(), newRecord.getId2());
            assertEquals(record.getFirstname(), newRecord.getFirstname());
            assertEquals(record.getLastname(), newRecord.getLastname());
            assertTrue(blobsAreEqual(record.getBlob1(), newRecord.getBlob1()));
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKFieldsBlobsSelectByExampleWithBlobsNoCriteria() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            PKFieldsBlobsMapper mapper = sqlSession.getMapper(PKFieldsBlobsMapper.class);
            PKFieldsBlobsWithBLOBs record = new PKFieldsBlobsWithBLOBs(3, 4, "Jeff", "Smith", generateRandomBlob());
            mapper.insert(record);

            record = new PKFieldsBlobsWithBLOBs(5, 6, "Scott", "Jones", generateRandomBlob());
            mapper.insert(record);

            PKFieldsBlobsExample example = new PKFieldsBlobsExample();
            example.createCriteria();
            List<PKFieldsBlobsWithBLOBs> answer = mapper.selectByExampleWithBLOBs(example);
            assertEquals(2, answer.size());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKFieldsBlobsCountByExample() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            PKFieldsBlobsMapper mapper = sqlSession.getMapper(PKFieldsBlobsMapper.class);
            PKFieldsBlobsWithBLOBs record = new PKFieldsBlobsWithBLOBs(3, 4, "Jeff", "Smith", generateRandomBlob());
            mapper.insert(record);

            record = new PKFieldsBlobsWithBLOBs(5, 6, "Scott", "Jones", generateRandomBlob());
            mapper.insert(record);

            PKFieldsBlobsExample example = new PKFieldsBlobsExample();
            example.createCriteria().andId1NotEqualTo(3);
            long rows = mapper.countByExample(example);
            assertEquals(1, rows);

            example.clear();
            rows = mapper.countByExample(example);
            assertEquals(2, rows);
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testFieldsBlobsInsert() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            FieldsBlobsMapper mapper = sqlSession.getMapper(FieldsBlobsMapper.class);
            FieldsBlobsWithBLOBs record = new FieldsBlobsWithBLOBs("Jeff", "Smith", generateRandomBlob(), generateRandomBlob(), null);
            mapper.insert(record);

            FieldsBlobsExample example = new FieldsBlobsExample();
            List<FieldsBlobsWithBLOBs> answer = mapper
                    .selectByExampleWithBLOBs(example);
            assertEquals(1, answer.size());

            FieldsBlobsWithBLOBs returnedRecord = answer.get(0);
            assertEquals(record.getFirstname(), returnedRecord.getFirstname());
            assertEquals(record.getLastname(), returnedRecord.getLastname());
            assertTrue(blobsAreEqual(record.getBlob1(), returnedRecord
                    .getBlob1()));
            assertTrue(blobsAreEqual(record.getBlob2(), returnedRecord
                    .getBlob2()));
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testFieldsBlobsDeleteByExample() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            FieldsBlobsMapper mapper = sqlSession.getMapper(FieldsBlobsMapper.class);
            FieldsBlobsWithBLOBs record = new FieldsBlobsWithBLOBs("Jeff", "Smith", generateRandomBlob(), generateRandomBlob(), null);
            mapper.insert(record);

            record = new FieldsBlobsWithBLOBs("Scott", "Jones", generateRandomBlob(), generateRandomBlob(), null);
            mapper.insert(record);

            FieldsBlobsExample example = new FieldsBlobsExample();
            List<FieldsBlobs> answer = mapper.selectByExample(example);
            assertEquals(2, answer.size());

            example = new FieldsBlobsExample();
            example.createCriteria().andFirstnameLike("S%");
            int rows = mapper.deleteByExample(example);
            assertEquals(1, rows);

            example = new FieldsBlobsExample();
            answer = mapper.selectByExample(example);
            assertEquals(1, answer.size());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testFieldsBlobsSelectByExampleWithoutBlobs() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            FieldsBlobsMapper mapper = sqlSession.getMapper(FieldsBlobsMapper.class);
            FieldsBlobsWithBLOBs record = new FieldsBlobsWithBLOBs("Jeff", "Smith", generateRandomBlob(), generateRandomBlob(), null);
            mapper.insert(record);

            record = new FieldsBlobsWithBLOBs("Scott", "Jones", generateRandomBlob(), generateRandomBlob(), null);
            mapper.insert(record);

            FieldsBlobsExample example = new FieldsBlobsExample();
            example.createCriteria().andFirstnameLike("S%");
            List<FieldsBlobs> answer = mapper.selectByExample(example);
            assertEquals(1, answer.size());

            FieldsBlobs newRecord = answer.get(0);
            assertFalse(newRecord instanceof FieldsBlobsWithBLOBs);
            assertEquals(record.getFirstname(), newRecord.getFirstname());
            assertEquals(record.getLastname(), newRecord.getLastname());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testFieldsBlobsSelectByExampleWithBlobs() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            FieldsBlobsMapper mapper = sqlSession.getMapper(FieldsBlobsMapper.class);
            FieldsBlobsWithBLOBs record = new FieldsBlobsWithBLOBs("Jeff", "Smith", generateRandomBlob(), generateRandomBlob(), null);
            mapper.insert(record);

            record = new FieldsBlobsWithBLOBs("Scott", "Jones", generateRandomBlob(), generateRandomBlob(), null);
            mapper.insert(record);

            FieldsBlobsExample example = new FieldsBlobsExample();
            example.createCriteria().andFirstnameLike("S%");
            List<FieldsBlobsWithBLOBs> answer = mapper
                    .selectByExampleWithBLOBs(example);
            assertEquals(1, answer.size());

            FieldsBlobsWithBLOBs newRecord = answer.get(0);
            assertEquals(record.getFirstname(), newRecord.getFirstname());
            assertEquals(record.getLastname(), newRecord.getLastname());
            assertTrue(blobsAreEqual(record.getBlob1(), newRecord.getBlob1()));
            assertTrue(blobsAreEqual(record.getBlob2(), newRecord.getBlob2()));
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testFieldsBlobsSelectByExampleWithBlobsNoCriteria() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            FieldsBlobsMapper mapper = sqlSession.getMapper(FieldsBlobsMapper.class);
            FieldsBlobsWithBLOBs record = new FieldsBlobsWithBLOBs("Jeff", "Smith", generateRandomBlob(), generateRandomBlob(), null);
            mapper.insert(record);

            record = new FieldsBlobsWithBLOBs("Scott", "Jones", generateRandomBlob(), generateRandomBlob(), null);
            mapper.insert(record);

            FieldsBlobsExample example = new FieldsBlobsExample();
            example.createCriteria();
            List<FieldsBlobsWithBLOBs> answer = mapper.selectByExampleWithBLOBs(example);
            assertEquals(2, answer.size());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testFieldsBlobsCountByExample() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            FieldsBlobsMapper mapper = sqlSession.getMapper(FieldsBlobsMapper.class);
            FieldsBlobsWithBLOBs record = new FieldsBlobsWithBLOBs("Jeff", "Smith", generateRandomBlob(), generateRandomBlob(), null);
            mapper.insert(record);

            record = new FieldsBlobsWithBLOBs("Scott", "Jones", generateRandomBlob(), generateRandomBlob(), null);
            mapper.insert(record);

            FieldsBlobsExample example = new FieldsBlobsExample();
            example.createCriteria().andFirstnameLike("S%");
            long rows = mapper.countByExample(example);
            assertEquals(1, rows);

            example.clear();
            rows = mapper.countByExample(example);
            assertEquals(2, rows);
        } finally {
            sqlSession.close();
        }
    }
}

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
package mbg.test.mb3.dsql;

import static mbg.test.common.util.TestUtilities.blobsAreEqual;
import static mbg.test.common.util.TestUtilities.generateRandomBlob;
import static mbg.test.mb3.generated.dsql.mapper.AwfulTableDynamicSqlSupport.awfulTable;
import static mbg.test.mb3.generated.dsql.mapper.FieldsBlobsDynamicSqlSupport.fieldsBlobs;
import static mbg.test.mb3.generated.dsql.mapper.FieldsOnlyDynamicSqlSupport.fieldsOnly;
import static mbg.test.mb3.generated.dsql.mapper.PKBlobsDynamicSqlSupport.PKBlobs;
import static mbg.test.mb3.generated.dsql.mapper.PKFieldsDynamicSqlSupport.PKFields;
import static mbg.test.mb3.generated.dsql.mapper.PKFieldsBlobsDynamicSqlSupport.PKFieldsBlobs;
import static mbg.test.mb3.generated.dsql.mapper.PKOnlyDynamicSqlSupport.PKOnly;
import static org.junit.jupiter.api.Assertions.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.mybatis.dynamic.sql.render.RenderingStrategy;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;

import mbg.test.mb3.generated.dsql.mapper.AwfulTableMapper;
import mbg.test.mb3.generated.dsql.mapper.FieldsBlobsMapper;
import mbg.test.mb3.generated.dsql.mapper.FieldsOnlyMapper;
import mbg.test.mb3.generated.dsql.mapper.PKBlobsMapper;
import mbg.test.mb3.generated.dsql.mapper.PKFieldsMapper;
import mbg.test.mb3.generated.dsql.mapper.PKFieldsBlobsMapper;
import mbg.test.mb3.generated.dsql.mapper.PKOnlyMapper;
import mbg.test.mb3.generated.dsql.mapper.mbgtest.IdMapper;
import mbg.test.mb3.generated.dsql.mapper.mbgtest.TranslationMapper;
import mbg.test.mb3.generated.dsql.model.AwfulTable;
import mbg.test.mb3.generated.dsql.model.FieldsBlobs;
import mbg.test.mb3.generated.dsql.model.FieldsOnly;

import mbg.test.mb3.generated.dsql.model.mbgtest.Id;
import mbg.test.mb3.generated.dsql.model.mbgtest.Translation;

/**
 * @author Jeff Butler
 * 
 */
public class DynamicSqlTest extends AbstractTest {

    @Test
    public void testFieldsOnlyInsert() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            FieldsOnlyMapper mapper = sqlSession.getMapper(FieldsOnlyMapper.class);
            FieldsOnly record = new FieldsOnly();
            record.setDoublefield(11.22);
            record.setFloatfield(33.44);
            record.setIntegerfield(5);
            mapper.insert(record);

            List<FieldsOnly> answer = mapper.selectByExample()
                    .where(fieldsOnly.integerfield, isEqualTo(5))
                    .build()
                    .execute();
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
            FieldsOnly record = new FieldsOnly();
            record.setDoublefield(11.22);
            record.setFloatfield(33.44);
            record.setIntegerfield(5);
            mapper.insert(record);

            record = new FieldsOnly();
            record.setDoublefield(44.55);
            record.setFloatfield(66.77);
            record.setIntegerfield(8);
            mapper.insert(record);

            record = new FieldsOnly();
            record.setDoublefield(88.99);
            record.setFloatfield(100.111);
            record.setIntegerfield(9);
            mapper.insert(record);

            List<FieldsOnly> answer = mapper.selectByExample()
                    .where(fieldsOnly.integerfield, isGreaterThan(5))
                    .build()
                    .execute();
            assertEquals(2, answer.size());

            answer = mapper.selectByExample()
                    .build()
                    .execute();
            assertEquals(3, answer.size());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testFieldsOnlySelectByExampleDistinct() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            FieldsOnlyMapper mapper = sqlSession.getMapper(FieldsOnlyMapper.class);
            FieldsOnly record = new FieldsOnly();
            record.setDoublefield(11.22);
            record.setFloatfield(33.44);
            record.setIntegerfield(5);
            mapper.insert(record);
            mapper.insert(record);
            mapper.insert(record);

            record = new FieldsOnly();
            record.setDoublefield(44.55);
            record.setFloatfield(66.77);
            record.setIntegerfield(8);
            mapper.insert(record);

            record = new FieldsOnly();
            record.setDoublefield(88.99);
            record.setFloatfield(100.111);
            record.setIntegerfield(9);
            mapper.insert(record);

            List<FieldsOnly> answer = mapper.selectDistinctByExample()
                    .where(fieldsOnly.integerfield, isEqualTo(5))
                    .build()
                    .execute();
            assertEquals(1, answer.size());

            answer = mapper.selectByExample()
                    .build()
                    .execute();
            assertEquals(5, answer.size());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testFieldsOnlySelectByExampleNoCriteria() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            FieldsOnlyMapper mapper = sqlSession.getMapper(FieldsOnlyMapper.class);
            FieldsOnly record = new FieldsOnly();
            record.setDoublefield(11.22);
            record.setFloatfield(33.44);
            record.setIntegerfield(5);
            mapper.insert(record);

            record = new FieldsOnly();
            record.setDoublefield(44.55);
            record.setFloatfield(66.77);
            record.setIntegerfield(8);
            mapper.insert(record);

            record = new FieldsOnly();
            record.setDoublefield(88.99);
            record.setFloatfield(100.111);
            record.setIntegerfield(9);
            mapper.insert(record);

            List<FieldsOnly> answer = mapper.selectByExample()
                    .build()
                    .execute();
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
            FieldsOnly record = new FieldsOnly();
            record.setDoublefield(11.22);
            record.setFloatfield(33.44);
            record.setIntegerfield(5);
            mapper.insert(record);

            record = new FieldsOnly();
            record.setDoublefield(44.55);
            record.setFloatfield(66.77);
            record.setIntegerfield(8);
            mapper.insert(record);

            record = new FieldsOnly();
            record.setDoublefield(88.99);
            record.setFloatfield(100.111);
            record.setIntegerfield(9);
            mapper.insert(record);

            int rows = mapper.deleteByExample()
                    .where(fieldsOnly.integerfield, isGreaterThan(5))
                    .build()
                    .execute();
            assertEquals(2, rows);

            List<FieldsOnly> answer = mapper.selectByExample()
                    .build()
                    .execute();
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
            FieldsOnly record = new FieldsOnly();
            record.setDoublefield(11.22);
            record.setFloatfield(33.44);
            record.setIntegerfield(5);
            mapper.insert(record);

            record = new FieldsOnly();
            record.setDoublefield(44.55);
            record.setFloatfield(66.77);
            record.setIntegerfield(8);
            mapper.insert(record);

            record = new FieldsOnly();
            record.setDoublefield(88.99);
            record.setFloatfield(100.111);
            record.setIntegerfield(9);
            mapper.insert(record);

            long rows = mapper.countByExample()
                    .where(fieldsOnly.integerfield, isGreaterThan(5))
                    .build()
                    .execute();
            assertEquals(2, rows);

            rows = mapper.countByExample()
                    .build()
                    .execute();
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
            mbg.test.mb3.generated.dsql.model.PKOnly key = new mbg.test.mb3.generated.dsql.model.PKOnly(1, 3);
            mapper.insert(key);
            List<mbg.test.mb3.generated.dsql.model.PKOnly> answer = mapper.selectByExample()
                    .build()
                    .execute();
            assertEquals(1, answer.size());

            mbg.test.mb3.generated.dsql.model.PKOnly returnedRecord = answer.get(0);
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
            mbg.test.mb3.generated.dsql.model.PKOnly key = new mbg.test.mb3.generated.dsql.model.PKOnly(1, 3);
            int rows = mapper.insert(key);

            key = new mbg.test.mb3.generated.dsql.model.PKOnly(5, 6);
            rows = mapper.insert(key);

            List<mbg.test.mb3.generated.dsql.model.PKOnly> answer = mapper.selectByExample()
                    .build()
                    .execute();
            assertEquals(2, answer.size());

            rows = mapper.deleteByPrimaryKey(5, 6);
            assertEquals(1, rows);

            answer = mapper.selectByExample()
                    .build()
                    .execute();
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
            mbg.test.mb3.generated.dsql.model.PKOnly key = new mbg.test.mb3.generated.dsql.model.PKOnly(1, 3);
            mapper.insert(key);

            key = new mbg.test.mb3.generated.dsql.model.PKOnly(5, 6);
            mapper.insert(key);

            key = new mbg.test.mb3.generated.dsql.model.PKOnly(7, 8);
            mapper.insert(key);

            int rows = mapper.deleteByExample()
                    .where(PKOnly.id, isGreaterThan(4))
                    .build()
                    .execute();
            assertEquals(2, rows);

            List<mbg.test.mb3.generated.dsql.model.PKOnly> answer = mapper.selectByExample()
                    .build()
                    .execute();
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
            mbg.test.mb3.generated.dsql.model.PKOnly key = new mbg.test.mb3.generated.dsql.model.PKOnly(1, 3);
            mapper.insert(key);

            key = new mbg.test.mb3.generated.dsql.model.PKOnly(5, 6);
            mapper.insert(key);

            key = new mbg.test.mb3.generated.dsql.model.PKOnly(7, 8);
            mapper.insert(key);

            List<mbg.test.mb3.generated.dsql.model.PKOnly> answer = mapper.selectByExample()
                    .where(PKOnly.id, isGreaterThan(4))
                    .orderBy(PKOnly.id)
                    .build()
                    .execute();
            assertEquals(2, answer.size());
            assertEquals(5, answer.get(0).getId().intValue());
            assertEquals(6, answer.get(0).getSeqNum().intValue());
            assertEquals(7, answer.get(1).getId().intValue());
            assertEquals(8, answer.get(1).getSeqNum().intValue());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKOnlySelectByExampleBackwards() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            PKOnlyMapper mapper = sqlSession.getMapper(PKOnlyMapper.class);
            mbg.test.mb3.generated.dsql.model.PKOnly key = new mbg.test.mb3.generated.dsql.model.PKOnly(1, 3);
            mapper.insert(key);

            key = new mbg.test.mb3.generated.dsql.model.PKOnly(5, 6);
            mapper.insert(key);

            key = new mbg.test.mb3.generated.dsql.model.PKOnly(7, 8);
            mapper.insert(key);

            SelectStatementProvider selectStatement = select(PKOnly.seqNum, PKOnly.id)
                    .from(PKOnly)
                    .where(PKOnly.id, isGreaterThan(4))
                    .orderBy(PKOnly.id)
                    .build()
                    .render(RenderingStrategy.MYBATIS3);
                    
            List<mbg.test.mb3.generated.dsql.model.PKOnly> answer = mapper.selectMany(selectStatement);
            assertEquals(2, answer.size());
            assertEquals(5, answer.get(0).getId().intValue());
            assertEquals(6, answer.get(0).getSeqNum().intValue());
            assertEquals(7, answer.get(1).getId().intValue());
            assertEquals(8, answer.get(1).getSeqNum().intValue());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKOnlySelectByExampleWithBackwardsResults() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            PKOnlyMapper mapper = sqlSession.getMapper(PKOnlyMapper.class);
            mbg.test.mb3.generated.dsql.model.PKOnly key = new mbg.test.mb3.generated.dsql.model.PKOnly(1, 3);
            mapper.insert(key);

            key = new mbg.test.mb3.generated.dsql.model.PKOnly(5, 6);
            mapper.insert(key);

            key = new mbg.test.mb3.generated.dsql.model.PKOnly(7, 8);
            mapper.insert(key);

            SelectStatementProvider selectStatement = select(PKOnly.id, PKOnly.seqNum)
                    .from(PKOnly)
                    .where(PKOnly.id, isGreaterThan(4))
                    .orderBy(PKOnly.id)
                    .build()
                    .render(RenderingStrategy.MYBATIS3);
            
            List<mbg.test.mb3.generated.dsql.model.PKOnly> answer = mapper.selectMany(selectStatement);
            assertEquals(2, answer.size());
            assertEquals(5, answer.get(0).getId().intValue());
            assertEquals(6, answer.get(0).getSeqNum().intValue());
            assertEquals(7, answer.get(1).getId().intValue());
            assertEquals(8, answer.get(1).getSeqNum().intValue());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKOnlySelectByExampleNoCriteria() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            PKOnlyMapper mapper = sqlSession.getMapper(PKOnlyMapper.class);
            mbg.test.mb3.generated.dsql.model.PKOnly key = new mbg.test.mb3.generated.dsql.model.PKOnly(1, 3);
            mapper.insert(key);

            key = new mbg.test.mb3.generated.dsql.model.PKOnly(5, 6);
            mapper.insert(key);

            key = new mbg.test.mb3.generated.dsql.model.PKOnly(7, 8);
            mapper.insert(key);

            List<mbg.test.mb3.generated.dsql.model.PKOnly> answer = mapper.selectByExample()
                    .build()
                    .execute();
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
            mbg.test.mb3.generated.dsql.model.PKOnly key = new mbg.test.mb3.generated.dsql.model.PKOnly(1, 3);
            mapper.insert(key);

            key = new mbg.test.mb3.generated.dsql.model.PKOnly(5, 6);
            mapper.insert(key);

            key = new mbg.test.mb3.generated.dsql.model.PKOnly(7, 8);
            mapper.insert(key);

            long rows = mapper.countByExample()
                    .where(PKOnly.id, isGreaterThan(4))
                    .build()
                    .execute();
            assertEquals(2, rows);

            rows = mapper.countByExample()
                    .build()
                    .execute();
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
            mbg.test.mb3.generated.dsql.model.PKFields record = new mbg.test.mb3.generated.dsql.model.PKFields();
            record.setDatefield(LocalDate.now());
            record.setDecimal100field(10L);
            record.setDecimal155field(new BigDecimal("15.12345"));
            record.setDecimal30field((short) 3);
            record.setDecimal60field(6);
            record.setFirstname("Jeff");
            record.setId1(1);
            record.setId2(2);
            record.setLastname("Butler");
            record.setTimefield(LocalTime.of(13, 2, 4));
            record.setTimestampfield(LocalDateTime.now());
            record.setStringboolean(true);

            mapper.insert(record);

            mbg.test.mb3.generated.dsql.model.PKFields returnedRecord = mapper.selectByPrimaryKey(2, 1);
            assertNotNull(returnedRecord);

            assertEquals(record.getDatefield(), returnedRecord
                    .getDatefield());
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
            assertEquals(record.getTimefield(), returnedRecord
                    .getTimefield());
            assertEquals(record.getTimestampfield(), returnedRecord
                    .getTimestampfield());
            assertEquals(record.isStringboolean(), returnedRecord.isStringboolean());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKFieldsUpdateByPrimaryKey() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            PKFieldsMapper mapper = sqlSession.getMapper(PKFieldsMapper.class);
            mbg.test.mb3.generated.dsql.model.PKFields record = new mbg.test.mb3.generated.dsql.model.PKFields();
            record.setFirstname("Jeff");
            record.setLastname("Smith");
            record.setId1(1);
            record.setId2(2);

            mapper.insert(record);

            record.setFirstname("Scott");
            record.setLastname("Jones");

            int rows = mapper.updateByPrimaryKey(record);
            assertEquals(1, rows);

            mbg.test.mb3.generated.dsql.model.PKFields record2 = mapper.selectByPrimaryKey(2, 1);

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
            mbg.test.mb3.generated.dsql.model.PKFields record = new mbg.test.mb3.generated.dsql.model.PKFields();
            record.setFirstname("Jeff");
            record.setLastname("Smith");
            record.setDecimal60field(5);
            record.setId1(1);
            record.setId2(2);

            mapper.insert(record);

            mbg.test.mb3.generated.dsql.model.PKFields newRecord = new mbg.test.mb3.generated.dsql.model.PKFields();
            newRecord.setId1(1);
            newRecord.setId2(2);
            newRecord.setFirstname("Scott");
            newRecord.setDecimal60field(4);

            int rows = mapper.updateByPrimaryKeySelective(newRecord);
            assertEquals(1, rows);

            mbg.test.mb3.generated.dsql.model.PKFields returnedRecord = mapper.selectByPrimaryKey(2, 1);

            assertEquals(record.getDatefield(), returnedRecord
                    .getDatefield());
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
            assertEquals(record.getTimefield(), returnedRecord
                    .getTimefield());
            assertEquals(record.getTimestampfield(), returnedRecord
                    .getTimestampfield());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKFieldsDeleteByPrimaryKey() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            PKFieldsMapper mapper = sqlSession.getMapper(PKFieldsMapper.class);
            mbg.test.mb3.generated.dsql.model.PKFields record = new mbg.test.mb3.generated.dsql.model.PKFields();
            record.setFirstname("Jeff");
            record.setLastname("Smith");
            record.setId1(1);
            record.setId2(2);

            mapper.insert(record);

            int rows = mapper.deleteByPrimaryKey(2, 1);
            assertEquals(1, rows);

            List<mbg.test.mb3.generated.dsql.model.PKFields> answer = mapper.selectByExample()
                    .build()
                    .execute();
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
            mbg.test.mb3.generated.dsql.model.PKFields record = new mbg.test.mb3.generated.dsql.model.PKFields();
            record.setFirstname("Jeff");
            record.setLastname("Smith");
            record.setId1(1);
            record.setId2(2);
            mapper.insert(record);

            record = new mbg.test.mb3.generated.dsql.model.PKFields();
            record.setFirstname("Bob");
            record.setLastname("Jones");
            record.setId1(3);
            record.setId2(4);

            mapper.insert(record);

            List<mbg.test.mb3.generated.dsql.model.PKFields> answer = mapper.selectByExample()
                    .build()
                    .execute();
            assertEquals(2, answer.size());

            int rows = mapper.deleteByExample()
                    .where(PKFields.lastname, isLike("J%"))
                    .build()
                    .execute();
            assertEquals(1, rows);

            answer = mapper.selectByExample()
                    .build()
                    .execute();
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
            mbg.test.mb3.generated.dsql.model.PKFields record = new mbg.test.mb3.generated.dsql.model.PKFields();
            record.setFirstname("Jeff");
            record.setLastname("Smith");
            record.setId1(1);
            record.setId2(2);
            mapper.insert(record);

            record = new mbg.test.mb3.generated.dsql.model.PKFields();
            record.setFirstname("Bob");
            record.setLastname("Jones");
            record.setId1(3);
            record.setId2(4);
            mapper.insert(record);

            mbg.test.mb3.generated.dsql.model.PKFields newRecord = mapper.selectByPrimaryKey(4, 3);

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
            mbg.test.mb3.generated.dsql.model.PKFields record = new mbg.test.mb3.generated.dsql.model.PKFields();
            record.setFirstname("Fred");
            record.setLastname("Flintstone");
            record.setId1(1);
            record.setId2(1);
            mapper.insert(record);

            record = new mbg.test.mb3.generated.dsql.model.PKFields();
            record.setFirstname("Wilma");
            record.setLastname("Flintstone");
            record.setId1(1);
            record.setId2(2);
            mapper.insert(record);

            record = new mbg.test.mb3.generated.dsql.model.PKFields();
            record.setFirstname("Pebbles");
            record.setLastname("Flintstone");
            record.setId1(1);
            record.setId2(3);
            mapper.insert(record);

            record = new mbg.test.mb3.generated.dsql.model.PKFields();
            record.setFirstname("Barney");
            record.setLastname("Rubble");
            record.setId1(2);
            record.setId2(1);
            mapper.insert(record);

            record = new mbg.test.mb3.generated.dsql.model.PKFields();
            record.setFirstname("Betty");
            record.setLastname("Rubble");
            record.setId1(2);
            record.setId2(2);
            mapper.insert(record);

            record = new mbg.test.mb3.generated.dsql.model.PKFields();
            record.setFirstname("Bamm Bamm");
            record.setLastname("Rubble");
            record.setId1(2);
            record.setId2(3);
            mapper.insert(record);

            List<mbg.test.mb3.generated.dsql.model.PKFields> answer = mapper.selectByExample()
                    .where(PKFields.firstname, isLike("B%"))
                    .orderBy(PKFields.id1, PKFields.id2)
                    .build()
                    .execute();
            assertEquals(3, answer.size());
            mbg.test.mb3.generated.dsql.model.PKFields returnedRecord = answer.get(0);
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
            mbg.test.mb3.generated.dsql.model.PKFields record = new mbg.test.mb3.generated.dsql.model.PKFields();
            record.setFirstname("Fred");
            record.setLastname("Flintstone");
            record.setId1(1);
            record.setId2(1);
            mapper.insert(record);

            record = new mbg.test.mb3.generated.dsql.model.PKFields();
            record.setFirstname("Wilma");
            record.setLastname("Flintstone");
            record.setId1(1);
            record.setId2(2);
            mapper.insert(record);

            record = new mbg.test.mb3.generated.dsql.model.PKFields();
            record.setFirstname("Pebbles");
            record.setLastname("Flintstone");
            record.setId1(1);
            record.setId2(3);
            mapper.insert(record);

            record = new mbg.test.mb3.generated.dsql.model.PKFields();
            record.setFirstname("Barney");
            record.setLastname("Rubble");
            record.setId1(2);
            record.setId2(1);
            mapper.insert(record);

            record = new mbg.test.mb3.generated.dsql.model.PKFields();
            record.setFirstname("Betty");
            record.setLastname("Rubble");
            record.setId1(2);
            record.setId2(2);
            mapper.insert(record);

            record = new mbg.test.mb3.generated.dsql.model.PKFields();
            record.setFirstname("Bamm Bamm");
            record.setLastname("Rubble");
            record.setId1(2);
            record.setId2(3);
            mapper.insert(record);

            List<mbg.test.mb3.generated.dsql.model.PKFields> answer = mapper.selectByExample()
                    .where(PKFields.firstname, isNotLike("B%"))
                    .orderBy(PKFields.id1, PKFields.id2)
                    .build()
                    .execute();
            assertEquals(3, answer.size());
            mbg.test.mb3.generated.dsql.model.PKFields returnedRecord = answer.get(0);
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
            mbg.test.mb3.generated.dsql.model.PKFields record = new mbg.test.mb3.generated.dsql.model.PKFields();
            record.setFirstname("Fred");
            record.setLastname("Flintstone");
            record.setId1(1);
            record.setId2(1);
            mapper.insert(record);

            record = new mbg.test.mb3.generated.dsql.model.PKFields();
            record.setFirstname("Wilma");
            record.setLastname("Flintstone");
            record.setId1(1);
            record.setId2(2);
            mapper.insert(record);

            record = new mbg.test.mb3.generated.dsql.model.PKFields();
            record.setFirstname("Pebbles");
            record.setLastname("Flintstone");
            record.setId1(1);
            record.setId2(3);
            mapper.insert(record);

            record = new mbg.test.mb3.generated.dsql.model.PKFields();
            record.setFirstname("Barney");
            record.setLastname("Rubble");
            record.setId1(2);
            record.setId2(1);
            mapper.insert(record);

            record = new mbg.test.mb3.generated.dsql.model.PKFields();
            record.setFirstname("Betty");
            record.setLastname("Rubble");
            record.setId1(2);
            record.setId2(2);
            mapper.insert(record);

            record = new mbg.test.mb3.generated.dsql.model.PKFields();
            record.setFirstname("Bamm Bamm");
            record.setLastname("Rubble");
            record.setId1(2);
            record.setId2(3);
            mapper.insert(record);

            List<mbg.test.mb3.generated.dsql.model.PKFields> answer = mapper.selectByExample()
                    .where(PKFields.firstname, isLike("B%"), and(PKFields.id2, isEqualTo(3)))
                    .or(PKFields.firstname, isLike("Wi%"))
                    .orderBy(PKFields.id1, PKFields.id2)
                    .build()
                    .execute();
                    
            assertEquals(2, answer.size());
            mbg.test.mb3.generated.dsql.model.PKFields returnedRecord = answer.get(0);
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
            mbg.test.mb3.generated.dsql.model.PKFields record = new mbg.test.mb3.generated.dsql.model.PKFields();
            record.setFirstname("Fred");
            record.setLastname("Flintstone");
            record.setId1(1);
            record.setId2(1);
            mapper.insert(record);

            record = new mbg.test.mb3.generated.dsql.model.PKFields();
            record.setFirstname("Wilma");
            record.setLastname("Flintstone");
            record.setId1(1);
            record.setId2(2);
            mapper.insert(record);

            record = new mbg.test.mb3.generated.dsql.model.PKFields();
            record.setFirstname("Pebbles");
            record.setLastname("Flintstone");
            record.setId1(1);
            record.setId2(3);
            mapper.insert(record);

            record = new mbg.test.mb3.generated.dsql.model.PKFields();
            record.setFirstname("Barney");
            record.setLastname("Rubble");
            record.setId1(2);
            record.setId2(1);
            mapper.insert(record);

            record = new mbg.test.mb3.generated.dsql.model.PKFields();
            record.setFirstname("Betty");
            record.setLastname("Rubble");
            record.setId1(2);
            record.setId2(2);
            mapper.insert(record);

            record = new mbg.test.mb3.generated.dsql.model.PKFields();
            record.setFirstname("Bamm Bamm");
            record.setLastname("Rubble");
            record.setId1(2);
            record.setId2(3);
            mapper.insert(record);

            List<mbg.test.mb3.generated.dsql.model.PKFields> answer = mapper.selectByExample()
                    .where(PKFields.id2, isIn(1, 3))
                    .orderBy(PKFields.id1, PKFields.id2)
                    .build()
                    .execute();
            assertEquals(4, answer.size());
            mbg.test.mb3.generated.dsql.model.PKFields returnedRecord = answer.get(0);
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
            mbg.test.mb3.generated.dsql.model.PKFields record = new mbg.test.mb3.generated.dsql.model.PKFields();
            record.setFirstname("Fred");
            record.setLastname("Flintstone");
            record.setId1(1);
            record.setId2(1);
            mapper.insert(record);

            record = new mbg.test.mb3.generated.dsql.model.PKFields();
            record.setFirstname("Wilma");
            record.setLastname("Flintstone");
            record.setId1(1);
            record.setId2(2);
            mapper.insert(record);

            record = new mbg.test.mb3.generated.dsql.model.PKFields();
            record.setFirstname("Pebbles");
            record.setLastname("Flintstone");
            record.setId1(1);
            record.setId2(3);
            mapper.insert(record);

            record = new mbg.test.mb3.generated.dsql.model.PKFields();
            record.setFirstname("Barney");
            record.setLastname("Rubble");
            record.setId1(2);
            record.setId2(1);
            mapper.insert(record);

            record = new mbg.test.mb3.generated.dsql.model.PKFields();
            record.setFirstname("Betty");
            record.setLastname("Rubble");
            record.setId1(2);
            record.setId2(2);
            mapper.insert(record);

            record = new mbg.test.mb3.generated.dsql.model.PKFields();
            record.setFirstname("Bamm Bamm");
            record.setLastname("Rubble");
            record.setId1(2);
            record.setId2(3);
            mapper.insert(record);

            List<mbg.test.mb3.generated.dsql.model.PKFields> answer = mapper.selectByExample()
                    .where(PKFields.id2, isBetween(1).and(3))
                    .orderBy(PKFields.id1, PKFields.id2)
                    .build()
                    .execute();
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
            mbg.test.mb3.generated.dsql.model.PKFields record = new mbg.test.mb3.generated.dsql.model.PKFields();
            record.setFirstname("Fred");
            record.setLastname("Flintstone");
            record.setId1(1);
            record.setId2(1);
            mapper.insert(record);

            record = new mbg.test.mb3.generated.dsql.model.PKFields();
            record.setFirstname("Wilma");
            record.setLastname("Flintstone");
            record.setId1(1);
            record.setId2(2);
            mapper.insert(record);

            record = new mbg.test.mb3.generated.dsql.model.PKFields();
            record.setFirstname("Pebbles");
            record.setLastname("Flintstone");
            record.setId1(1);
            record.setId2(3);
            mapper.insert(record);

            record = new mbg.test.mb3.generated.dsql.model.PKFields();
            record.setFirstname("Barney");
            record.setLastname("Rubble");
            record.setId1(2);
            record.setId2(1);
            mapper.insert(record);

            record = new mbg.test.mb3.generated.dsql.model.PKFields();
            record.setFirstname("Betty");
            record.setLastname("Rubble");
            record.setId1(2);
            record.setId2(2);
            mapper.insert(record);

            record = new mbg.test.mb3.generated.dsql.model.PKFields();
            record.setFirstname("Bamm Bamm");
            record.setLastname("Rubble");
            record.setId1(2);
            record.setId2(3);
            mapper.insert(record);

            List<mbg.test.mb3.generated.dsql.model.PKFields> answer = mapper.selectByExample()
                    .orderBy(PKFields.id1, PKFields.id2)
                    .build()
                    .execute();

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
            mbg.test.mb3.generated.dsql.model.PKFields record = new mbg.test.mb3.generated.dsql.model.PKFields();
            record.setFirstname("Fred");
            record.setLastname("Flintstone");
            record.setId1(1);
            record.setId2(1);
            record.setWierdField(11);
            mapper.insert(record);

            record = new mbg.test.mb3.generated.dsql.model.PKFields();
            record.setFirstname("Wilma");
            record.setLastname("Flintstone");
            record.setId1(1);
            record.setId2(2);
            record.setWierdField(22);
            mapper.insert(record);

            record = new mbg.test.mb3.generated.dsql.model.PKFields();
            record.setFirstname("Pebbles");
            record.setLastname("Flintstone");
            record.setId1(1);
            record.setId2(3);
            record.setWierdField(33);
            mapper.insert(record);

            record = new mbg.test.mb3.generated.dsql.model.PKFields();
            record.setFirstname("Barney");
            record.setLastname("Rubble");
            record.setId1(2);
            record.setId2(1);
            record.setWierdField(44);
            mapper.insert(record);

            record = new mbg.test.mb3.generated.dsql.model.PKFields();
            record.setFirstname("Betty");
            record.setLastname("Rubble");
            record.setId1(2);
            record.setId2(2);
            record.setWierdField(55);
            mapper.insert(record);

            record = new mbg.test.mb3.generated.dsql.model.PKFields();
            record.setFirstname("Bamm Bamm");
            record.setLastname("Rubble");
            record.setId1(2);
            record.setId2(3);
            record.setWierdField(66);
            mapper.insert(record);

            List<Integer> values = new ArrayList<>();
            values.add(11);
            values.add(22);

            List<mbg.test.mb3.generated.dsql.model.PKFields> answer = mapper.selectByExample()
                    .where(PKFields.wierdField, isLessThan(40))
                    .and(PKFields.wierdField, isIn(11, 22))
                    .orderBy(PKFields.id1, PKFields.id2)
                    .build()
                    .execute();
                            
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
            mbg.test.mb3.generated.dsql.model.PKFields record = new mbg.test.mb3.generated.dsql.model.PKFields();
            record.setFirstname("Jeff");
            record.setLastname("Smith");
            record.setId1(1);
            record.setId2(2);
            mapper.insert(record);

            record = new mbg.test.mb3.generated.dsql.model.PKFields();
            record.setFirstname("Bob");
            record.setLastname("Jones");
            record.setId1(3);
            record.setId2(4);
            mapper.insert(record);

            long rows = mapper.countByExample()
                    .where(PKFields.lastname, isLike("J%"))
                    .build()
                    .execute();
            
            assertEquals(1, rows);

            rows = mapper.countByExample()
                    .build()
                    .execute();
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
            mbg.test.mb3.generated.dsql.model.PKBlobs record = new mbg.test.mb3.generated.dsql.model.PKBlobs();
            record.setId(3);
            record.setBlob1(generateRandomBlob());
            record.setBlob2(generateRandomBlob());
            mapper.insert(record);

            List<mbg.test.mb3.generated.dsql.model.PKBlobs> answer = mapper.selectByExample()
                    .build()
                    .execute();
            
            assertEquals(1, answer.size());

            mbg.test.mb3.generated.dsql.model.PKBlobs returnedRecord = answer.get(0);
            assertEquals(record.getId(), returnedRecord.getId());
            assertTrue(blobsAreEqual(record.getBlob1(), returnedRecord
                    .getBlob1()));
            assertTrue(blobsAreEqual(record.getBlob2(), returnedRecord
                    .getBlob2()));
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKBlobsUpdateByPrimaryKeyWithBLOBs() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            PKBlobsMapper mapper = sqlSession.getMapper(PKBlobsMapper.class);
            mbg.test.mb3.generated.dsql.model.PKBlobs record = new mbg.test.mb3.generated.dsql.model.PKBlobs();
            record.setId(3);
            record.setBlob1(generateRandomBlob());
            record.setBlob2(generateRandomBlob());
            mapper.insert(record);

            record = new mbg.test.mb3.generated.dsql.model.PKBlobs();
            record.setId(3);
            record.setBlob1(generateRandomBlob());
            record.setBlob2(generateRandomBlob());
            int rows = mapper.updateByPrimaryKey(record);
            assertEquals(1, rows);

            mbg.test.mb3.generated.dsql.model.PKBlobs newRecord = mapper.selectByPrimaryKey(3);

            assertNotNull(newRecord);
            assertEquals(record.getId(), newRecord.getId());
            assertTrue(blobsAreEqual(record.getBlob1(), newRecord.getBlob1()));
            assertTrue(blobsAreEqual(record.getBlob2(), newRecord.getBlob2()));
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKBlobsUpdateByPrimaryKeySelective() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            PKBlobsMapper mapper = sqlSession.getMapper(PKBlobsMapper.class);
            mbg.test.mb3.generated.dsql.model.PKBlobs record = new mbg.test.mb3.generated.dsql.model.PKBlobs();
            record.setId(3);
            record.setBlob1(generateRandomBlob());
            record.setBlob2(generateRandomBlob());
            mapper.insert(record);

            mbg.test.mb3.generated.dsql.model.PKBlobs newRecord = new mbg.test.mb3.generated.dsql.model.PKBlobs();
            newRecord.setId(3);
            newRecord.setBlob2(generateRandomBlob());
            mapper.updateByPrimaryKeySelective(newRecord);

            mbg.test.mb3.generated.dsql.model.PKBlobs returnedRecord = mapper.selectByPrimaryKey(3);
            assertNotNull(returnedRecord);
            assertEquals(record.getId(), returnedRecord.getId());
            assertTrue(blobsAreEqual(record.getBlob1(), returnedRecord
                    .getBlob1()));
            assertTrue(blobsAreEqual(newRecord.getBlob2(), returnedRecord
                    .getBlob2()));
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKBlobsDeleteByPrimaryKey() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            PKBlobsMapper mapper = sqlSession.getMapper(PKBlobsMapper.class);
            mbg.test.mb3.generated.dsql.model.PKBlobs record = new mbg.test.mb3.generated.dsql.model.PKBlobs();
            record.setId(3);
            record.setBlob1(generateRandomBlob());
            record.setBlob2(generateRandomBlob());
            mapper.insert(record);

            List<mbg.test.mb3.generated.dsql.model.PKBlobs> answer = mapper.selectByExample()
                    .build()
                    .execute();
            assertEquals(1, answer.size());

            int rows = mapper.deleteByPrimaryKey(3);
            assertEquals(1, rows);

            answer = mapper.selectByExample()
                    .build()
                    .execute();
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
            mbg.test.mb3.generated.dsql.model.PKBlobs record = new mbg.test.mb3.generated.dsql.model.PKBlobs();
            record.setId(3);
            record.setBlob1(generateRandomBlob());
            record.setBlob2(generateRandomBlob());
            mapper.insert(record);

            record = new mbg.test.mb3.generated.dsql.model.PKBlobs();
            record.setId(6);
            record.setBlob1(generateRandomBlob());
            record.setBlob2(generateRandomBlob());
            mapper.insert(record);

            List<mbg.test.mb3.generated.dsql.model.PKBlobs> answer = mapper.selectByExample()
                    .build()
                    .execute();
            assertEquals(2, answer.size());

            int rows = mapper.deleteByExample()
                    .where(PKBlobs.id, isLessThan(4))
                    .build()
                    .execute();
            assertEquals(1, rows);

            answer = mapper.selectByExample()
                    .build()
                    .execute();
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
            mbg.test.mb3.generated.dsql.model.PKBlobs record = new mbg.test.mb3.generated.dsql.model.PKBlobs();
            record.setId(3);
            record.setBlob1(generateRandomBlob());
            record.setBlob2(generateRandomBlob());
            mapper.insert(record);

            record = new mbg.test.mb3.generated.dsql.model.PKBlobs();
            record.setId(6);
            record.setBlob1(generateRandomBlob());
            record.setBlob2(generateRandomBlob());
            mapper.insert(record);

            mbg.test.mb3.generated.dsql.model.PKBlobs newRecord = mapper.selectByPrimaryKey(6);
            assertNotNull(newRecord);
            assertEquals(record.getId(), newRecord.getId());
            assertTrue(blobsAreEqual(record.getBlob1(), newRecord.getBlob1()));
            assertTrue(blobsAreEqual(record.getBlob2(), newRecord.getBlob2()));
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKBlobsSelectByExampleWithBlobs() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            PKBlobsMapper mapper = sqlSession.getMapper(PKBlobsMapper.class);
            mbg.test.mb3.generated.dsql.model.PKBlobs record = new mbg.test.mb3.generated.dsql.model.PKBlobs();
            record.setId(3);
            record.setBlob1(generateRandomBlob());
            record.setBlob2(generateRandomBlob());
            mapper.insert(record);

            record = new mbg.test.mb3.generated.dsql.model.PKBlobs();
            record.setId(6);
            record.setBlob1(generateRandomBlob());
            record.setBlob2(generateRandomBlob());
            mapper.insert(record);

            List<mbg.test.mb3.generated.dsql.model.PKBlobs> answer = mapper.selectByExample()
                    .where(PKBlobs.id, isGreaterThan(4))
                    .build()
                    .execute();

            assertEquals(1, answer.size());

            mbg.test.mb3.generated.dsql.model.PKBlobs newRecord = answer.get(0);
            assertEquals(record.getId(), newRecord.getId());
            assertTrue(blobsAreEqual(record.getBlob1(), newRecord.getBlob1()));
            assertTrue(blobsAreEqual(record.getBlob2(), newRecord.getBlob2()));
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testPKBlobsCountByExample() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            PKBlobsMapper mapper = sqlSession.getMapper(PKBlobsMapper.class);
            mbg.test.mb3.generated.dsql.model.PKBlobs record = new mbg.test.mb3.generated.dsql.model.PKBlobs();
            record.setId(3);
            record.setBlob1(generateRandomBlob());
            record.setBlob2(generateRandomBlob());
            mapper.insert(record);

            record = new mbg.test.mb3.generated.dsql.model.PKBlobs();
            record.setId(6);
            record.setBlob1(generateRandomBlob());
            record.setBlob2(generateRandomBlob());
            mapper.insert(record);

            long rows = mapper.countByExample()
                    .where(PKBlobs.id, isLessThan(4))
                    .build()
                    .execute();
            assertEquals(1, rows);

            rows = mapper.countByExample()
                    .build()
                    .execute();
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
            mbg.test.mb3.generated.dsql.model.PKFieldsBlobs record = new mbg.test.mb3.generated.dsql.model.PKFieldsBlobs();
            record.setId1(3);
            record.setId2(4);
            record.setFirstname("Jeff");
            record.setLastname("Smith");
            record.setBlob1(generateRandomBlob());
            mapper.insert(record);

            List<mbg.test.mb3.generated.dsql.model.PKFieldsBlobs> answer = mapper.selectByExample()
                    .build()
                    .execute();
            assertEquals(1, answer.size());

            mbg.test.mb3.generated.dsql.model.PKFieldsBlobs returnedRecord = answer.get(0);
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
            mbg.test.mb3.generated.dsql.model.PKFieldsBlobs record = new mbg.test.mb3.generated.dsql.model.PKFieldsBlobs();
            record.setId1(3);
            record.setId2(4);
            record.setFirstname("Jeff");
            record.setLastname("Smith");
            record.setBlob1(generateRandomBlob());
            mapper.insert(record);

            mbg.test.mb3.generated.dsql.model.PKFieldsBlobs updateRecord = new mbg.test.mb3.generated.dsql.model.PKFieldsBlobs();
            updateRecord.setId1(3);
            updateRecord.setId2(4);
            updateRecord.setFirstname("Scott");
            updateRecord.setLastname("Jones");
            updateRecord.setBlob1(generateRandomBlob());

            int rows = mapper.updateByPrimaryKey(updateRecord);
            assertEquals(1, rows);

            mbg.test.mb3.generated.dsql.model.PKFieldsBlobs newRecord = mapper.selectByPrimaryKey(3, 4);
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
    public void testPKFieldsBlobsUpdateByPrimaryKeySelective() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            PKFieldsBlobsMapper mapper = sqlSession.getMapper(PKFieldsBlobsMapper.class);
            mbg.test.mb3.generated.dsql.model.PKFieldsBlobs record = new mbg.test.mb3.generated.dsql.model.PKFieldsBlobs();
            record.setId1(3);
            record.setId2(4);
            record.setFirstname("Jeff");
            record.setLastname("Smith");
            record.setBlob1(generateRandomBlob());
            mapper.insert(record);

            mbg.test.mb3.generated.dsql.model.PKFieldsBlobs updateRecord = new mbg.test.mb3.generated.dsql.model.PKFieldsBlobs();
            updateRecord.setId1(3);
            updateRecord.setId2(4);
            updateRecord.setLastname("Jones");

            int rows = mapper.updateByPrimaryKeySelective(updateRecord);
            assertEquals(1, rows);

            mbg.test.mb3.generated.dsql.model.PKFieldsBlobs returnedRecord = mapper.selectByPrimaryKey(3, 4);
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
            mbg.test.mb3.generated.dsql.model.PKFieldsBlobs record = new mbg.test.mb3.generated.dsql.model.PKFieldsBlobs();
            record.setId1(3);
            record.setId2(4);
            record.setFirstname("Jeff");
            record.setLastname("Smith");
            record.setBlob1(generateRandomBlob());
            mapper.insert(record);

            record = new mbg.test.mb3.generated.dsql.model.PKFieldsBlobs();
            record.setId1(5);
            record.setId2(6);
            record.setFirstname("Scott");
            record.setLastname("Jones");
            record.setBlob1(generateRandomBlob());
            mapper.insert(record);

            List<mbg.test.mb3.generated.dsql.model.PKFieldsBlobs> answer = mapper.selectByExample()
                    .build()
                    .execute();
            assertEquals(2, answer.size());

            int rows = mapper.deleteByPrimaryKey(5, 6);
            assertEquals(1, rows);

            answer = mapper.selectByExample()
                    .build()
                    .execute();
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
            mbg.test.mb3.generated.dsql.model.PKFieldsBlobs record = new mbg.test.mb3.generated.dsql.model.PKFieldsBlobs();
            record.setId1(3);
            record.setId2(4);
            record.setFirstname("Jeff");
            record.setLastname("Smith");
            record.setBlob1(generateRandomBlob());
            mapper.insert(record);

            record = new mbg.test.mb3.generated.dsql.model.PKFieldsBlobs();
            record.setId1(5);
            record.setId2(6);
            record.setFirstname("Scott");
            record.setLastname("Jones");
            record.setBlob1(generateRandomBlob());
            mapper.insert(record);

            List<mbg.test.mb3.generated.dsql.model.PKFieldsBlobs> answer = mapper.selectByExample()
                    .build()
                    .execute();
            assertEquals(2, answer.size());

            int rows = mapper.deleteByExample()
                    .where(PKFieldsBlobs.id1, isNotEqualTo(3))
                    .build()
                    .execute();
            assertEquals(1, rows);

            answer = mapper.selectByExample()
                    .build()
                    .execute();
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
            mbg.test.mb3.generated.dsql.model.PKFieldsBlobs record = new mbg.test.mb3.generated.dsql.model.PKFieldsBlobs();
            record.setId1(3);
            record.setId2(4);
            record.setFirstname("Jeff");
            record.setLastname("Smith");
            record.setBlob1(generateRandomBlob());
            mapper.insert(record);

            record = new mbg.test.mb3.generated.dsql.model.PKFieldsBlobs();
            record.setId1(5);
            record.setId2(6);
            record.setFirstname("Scott");
            record.setLastname("Jones");
            record.setBlob1(generateRandomBlob());
            mapper.insert(record);

            List<mbg.test.mb3.generated.dsql.model.PKFieldsBlobs> answer = mapper.selectByExample()
                    .build()
                    .execute();
            assertEquals(2, answer.size());

            mbg.test.mb3.generated.dsql.model.PKFieldsBlobs newRecord = mapper.selectByPrimaryKey(5, 6);
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
    public void testPKFieldsBlobsSelectByExampleWithBlobs() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            PKFieldsBlobsMapper mapper = sqlSession.getMapper(PKFieldsBlobsMapper.class);
            mbg.test.mb3.generated.dsql.model.PKFieldsBlobs record = new mbg.test.mb3.generated.dsql.model.PKFieldsBlobs();
            record.setId1(3);
            record.setId2(4);
            record.setFirstname("Jeff");
            record.setLastname("Smith");
            record.setBlob1(generateRandomBlob());
            mapper.insert(record);

            record = new mbg.test.mb3.generated.dsql.model.PKFieldsBlobs();
            record.setId1(5);
            record.setId2(6);
            record.setFirstname("Scott");
            record.setLastname("Jones");
            record.setBlob1(generateRandomBlob());
            mapper.insert(record);

            List<mbg.test.mb3.generated.dsql.model.PKFieldsBlobs> answer = mapper.selectByExample()
                    .where(PKFieldsBlobs.id2, isEqualTo(6))
                    .build()
                    .execute();
            assertEquals(1, answer.size());

            mbg.test.mb3.generated.dsql.model.PKFieldsBlobs newRecord = answer.get(0);
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
            mbg.test.mb3.generated.dsql.model.PKFieldsBlobs record = new mbg.test.mb3.generated.dsql.model.PKFieldsBlobs();
            record.setId1(3);
            record.setId2(4);
            record.setFirstname("Jeff");
            record.setLastname("Smith");
            record.setBlob1(generateRandomBlob());
            mapper.insert(record);

            record = new mbg.test.mb3.generated.dsql.model.PKFieldsBlobs();
            record.setId1(5);
            record.setId2(6);
            record.setFirstname("Scott");
            record.setLastname("Jones");
            record.setBlob1(generateRandomBlob());
            mapper.insert(record);

            List<mbg.test.mb3.generated.dsql.model.PKFieldsBlobs> answer = mapper.selectByExample()
                    .build()
                    .execute();
            assertEquals(2, answer.size());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testFieldsBlobsInsert() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            FieldsBlobsMapper mapper = sqlSession.getMapper(FieldsBlobsMapper.class);
            FieldsBlobs record = new FieldsBlobs();
            record.setFirstname("Jeff");
            record.setLastname("Smith");
            record.setBlob1(generateRandomBlob());
            record.setBlob2(generateRandomBlob());
            mapper.insert(record);

            List<FieldsBlobs> answer = mapper.selectByExample()
                    .build()
                    .execute();
            assertEquals(1, answer.size());

            FieldsBlobs returnedRecord = answer.get(0);
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
            FieldsBlobs record = new FieldsBlobs();
            record.setFirstname("Jeff");
            record.setLastname("Smith");
            record.setBlob1(generateRandomBlob());
            record.setBlob2(generateRandomBlob());
            mapper.insert(record);

            record = new FieldsBlobs();
            record.setFirstname("Scott");
            record.setLastname("Jones");
            record.setBlob1(generateRandomBlob());
            record.setBlob2(generateRandomBlob());
            mapper.insert(record);

            List<FieldsBlobs> answer = mapper.selectByExample()
                    .build()
                    .execute();
            assertEquals(2, answer.size());

            int rows = mapper.deleteByExample()
                    .where(fieldsBlobs.firstname, isLike("S%"))
                    .build()
                    .execute();
            assertEquals(1, rows);

            answer = mapper.selectByExample()
                    .build()
                    .execute();
            assertEquals(1, answer.size());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testFieldsBlobsSelectByExampleWithBlobs() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            FieldsBlobsMapper mapper = sqlSession.getMapper(FieldsBlobsMapper.class);
            FieldsBlobs record = new FieldsBlobs();
            record.setFirstname("Jeff");
            record.setLastname("Smith");
            record.setBlob1(generateRandomBlob());
            record.setBlob2(generateRandomBlob());
            mapper.insert(record);

            record = new FieldsBlobs();
            record.setFirstname("Scott");
            record.setLastname("Jones");
            record.setBlob1(generateRandomBlob());
            record.setBlob2(generateRandomBlob());
            mapper.insert(record);

            List<FieldsBlobs> answer = mapper.selectByExample()
                    .where(fieldsBlobs.firstname, isLike("S%"))
                    .build()
                    .execute();
            assertEquals(1, answer.size());

            FieldsBlobs newRecord = answer.get(0);
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
            FieldsBlobs record = new FieldsBlobs();
            record.setFirstname("Jeff");
            record.setLastname("Smith");
            record.setBlob1(generateRandomBlob());
            record.setBlob2(generateRandomBlob());
            mapper.insert(record);

            record = new FieldsBlobs();
            record.setFirstname("Scott");
            record.setLastname("Jones");
            record.setBlob1(generateRandomBlob());
            record.setBlob2(generateRandomBlob());
            mapper.insert(record);

            List<FieldsBlobs> answer = mapper.selectByExample()
                    .build()
                    .execute();
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
            mbg.test.mb3.generated.dsql.model.PKFieldsBlobs record = new mbg.test.mb3.generated.dsql.model.PKFieldsBlobs();
            record.setId1(3);
            record.setId2(4);
            record.setFirstname("Jeff");
            record.setLastname("Smith");
            record.setBlob1(generateRandomBlob());
            mapper.insert(record);

            record = new mbg.test.mb3.generated.dsql.model.PKFieldsBlobs();
            record.setId1(5);
            record.setId2(6);
            record.setFirstname("Scott");
            record.setLastname("Jones");
            record.setBlob1(generateRandomBlob());
            mapper.insert(record);

            long rows = mapper.countByExample()
                    .where(PKFieldsBlobs.id1, isNotEqualTo(3))
                    .build()
                    .execute();
            assertEquals(1, rows);

            rows = mapper.countByExample()
                    .build()
                    .execute();
            assertEquals(2, rows);
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testAwfulTableInsert() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            AwfulTableMapper mapper = sqlSession.getMapper(AwfulTableMapper.class);
            AwfulTable record = new AwfulTable();
            record.seteMail("fred@fred.com");
            record.setEmailaddress("alsofred@fred.com");
            record.setFirstFirstName("fred1");
            record.setFrom("from field");
            record.setId1(1);
            record.setId2(2);
            record.setId5(5);
            record.setId6(6);
            record.setId7(7);
            record.setSecondFirstName("fred2");
            record.setThirdFirstName("fred3");
            
            record.setActive(true);
            record.setActive1(Boolean.FALSE);
            record.setActive2(new byte[]{-128, 127});

            mapper.insert(record);
            Integer generatedCustomerId = record.getCustomerId();
            assertEquals(57, generatedCustomerId.intValue());

            AwfulTable returnedRecord = mapper
                    .selectByPrimaryKey(generatedCustomerId);

            assertEquals(generatedCustomerId, returnedRecord.getCustomerId());
            assertEquals(record.geteMail(), returnedRecord.geteMail());
            assertEquals(record.getEmailaddress(), returnedRecord
                    .getEmailaddress());
            assertEquals(record.getFirstFirstName(), returnedRecord
                    .getFirstFirstName());
            assertEquals(record.getFrom(), returnedRecord.getFrom());
            assertEquals(record.getId1(), returnedRecord.getId1());
            assertEquals(record.getId2(), returnedRecord.getId2());
            assertEquals(record.getId5(), returnedRecord.getId5());
            assertEquals(record.getId6(), returnedRecord.getId6());
            assertEquals(record.getId7(), returnedRecord.getId7());
            assertEquals(record.getSecondFirstName(), returnedRecord
                    .getSecondFirstName());
            assertEquals(record.getThirdFirstName(), returnedRecord
                    .getThirdFirstName());
            assertTrue(returnedRecord.isActive());
            assertFalse(returnedRecord.getActive1().booleanValue());
            assertEquals(3, returnedRecord.getActive2().length);
            assertEquals(-128, returnedRecord.getActive2()[0]);
            assertEquals(127, returnedRecord.getActive2()[1]);
            assertEquals(0, returnedRecord.getActive2()[2]);
            
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testAwfulTableInsertSelective() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            AwfulTableMapper mapper = sqlSession.getMapper(AwfulTableMapper.class);
            AwfulTable record = new AwfulTable();

            record.seteMail("fred@fred.com");
            record.setEmailaddress("alsofred@fred.com");
            record.setFrom("from field");
            record.setId1(1);
            record.setId2(2);
            record.setId5(5);
            record.setId6(6);
            record.setId7(7);
            record.setSecondFirstName("fred2");
            record.setThirdFirstName("fred3");

            mapper.insertSelective(record);
            Integer generatedCustomerId = record.getCustomerId();
            assertEquals(57, generatedCustomerId.intValue());

            AwfulTable returnedRecord = mapper
                    .selectByPrimaryKey(generatedCustomerId);

            assertEquals(generatedCustomerId, returnedRecord.getCustomerId());
            assertEquals(record.geteMail(), returnedRecord.geteMail());
            assertEquals(record.getEmailaddress(), returnedRecord
                    .getEmailaddress());
            assertEquals("Mabel", returnedRecord.getFirstFirstName());
            assertEquals(record.getFrom(), returnedRecord.getFrom());
            assertEquals(record.getId1(), returnedRecord.getId1());
            assertEquals(record.getId2(), returnedRecord.getId2());
            assertEquals(record.getId5(), returnedRecord.getId5());
            assertEquals(record.getId6(), returnedRecord.getId6());
            assertEquals(record.getId7(), returnedRecord.getId7());
            assertEquals(record.getSecondFirstName(), returnedRecord
                    .getSecondFirstName());
            assertEquals(record.getThirdFirstName(), returnedRecord
                    .getThirdFirstName());
        } finally {
            sqlSession.close();
        }
    }
    
    @Test
    public void testAwfulTableUpdateByPrimaryKey() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            AwfulTableMapper mapper = sqlSession.getMapper(AwfulTableMapper.class);
            AwfulTable record = new AwfulTable();
            record.seteMail("fred@fred.com");
            record.setEmailaddress("alsofred@fred.com");
            record.setFirstFirstName("fred1");
            record.setFrom("from field");
            record.setId1(1);
            record.setId2(2);
            record.setId5(5);
            record.setId6(6);
            record.setId7(7);
            record.setSecondFirstName("fred2");
            record.setThirdFirstName("fred3");

            mapper.insert(record);
            Integer generatedCustomerId = record.getCustomerId();

            record.setId1(11);
            record.setId2(22);

            int rows = mapper.updateByPrimaryKey(record);
            assertEquals(1, rows);

            AwfulTable returnedRecord = mapper.selectByPrimaryKey(generatedCustomerId);

            assertEquals(generatedCustomerId, returnedRecord.getCustomerId());
            assertEquals(record.geteMail(), returnedRecord.geteMail());
            assertEquals(record.getEmailaddress(), returnedRecord
                    .getEmailaddress());
            assertEquals(record.getFirstFirstName(), returnedRecord
                    .getFirstFirstName());
            assertEquals(record.getFrom(), returnedRecord.getFrom());
            assertEquals(record.getId1(), returnedRecord.getId1());
            assertEquals(record.getId2(), returnedRecord.getId2());
            assertEquals(record.getId5(), returnedRecord.getId5());
            assertEquals(record.getId6(), returnedRecord.getId6());
            assertEquals(record.getId7(), returnedRecord.getId7());
            assertEquals(record.getSecondFirstName(), returnedRecord
                    .getSecondFirstName());
            assertEquals(record.getThirdFirstName(), returnedRecord
                    .getThirdFirstName());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testAwfulTableUpdateByPrimaryKeySelective() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            AwfulTableMapper mapper = sqlSession.getMapper(AwfulTableMapper.class);
            AwfulTable record = new AwfulTable();
            record.seteMail("fred@fred.com");
            record.setEmailaddress("alsofred@fred.com");
            record.setFirstFirstName("fred1");
            record.setFrom("from field");
            record.setId1(1);
            record.setId2(2);
            record.setId5(5);
            record.setId6(6);
            record.setId7(7);
            record.setSecondFirstName("fred2");
            record.setThirdFirstName("fred3");

            mapper.insert(record);
            Integer generatedCustomerId = record.getCustomerId();

            AwfulTable newRecord = new AwfulTable();
            newRecord.setCustomerId(generatedCustomerId);
            newRecord.setId1(11);
            newRecord.setId2(22);

            int rows = mapper.updateByPrimaryKeySelective(newRecord);
            assertEquals(1, rows);

            AwfulTable returnedRecord = mapper.selectByPrimaryKey(generatedCustomerId);

            assertEquals(generatedCustomerId, returnedRecord.getCustomerId());
            assertEquals(record.geteMail(), returnedRecord.geteMail());
            assertEquals(record.getEmailaddress(), returnedRecord
                    .getEmailaddress());
            assertEquals(record.getFirstFirstName(), returnedRecord
                    .getFirstFirstName());
            assertEquals(record.getFrom(), returnedRecord.getFrom());
            assertEquals(newRecord.getId1(), returnedRecord.getId1());
            assertEquals(newRecord.getId2(), returnedRecord.getId2());
            assertEquals(record.getId5(), returnedRecord.getId5());
            assertEquals(record.getId6(), returnedRecord.getId6());
            assertEquals(record.getId7(), returnedRecord.getId7());
            assertEquals(record.getSecondFirstName(), returnedRecord
                    .getSecondFirstName());
            assertEquals(record.getThirdFirstName(), returnedRecord
                    .getThirdFirstName());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testAwfulTableDeleteByPrimaryKey() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            AwfulTableMapper mapper = sqlSession.getMapper(AwfulTableMapper.class);
            AwfulTable record = new AwfulTable();
            record.seteMail("fred@fred.com");
            record.setEmailaddress("alsofred@fred.com");
            record.setFirstFirstName("fred1");
            record.setFrom("from field");
            record.setId1(1);
            record.setId2(2);
            record.setId5(5);
            record.setId6(6);
            record.setId7(7);
            record.setSecondFirstName("fred2");
            record.setThirdFirstName("fred3");

            mapper.insert(record);
            Integer generatedCustomerId = record.getCustomerId();

            int rows = mapper.deleteByPrimaryKey(generatedCustomerId);
            assertEquals(1, rows);

            List<AwfulTable> answer = mapper.selectByExample()
                    .build()
                    .execute();
            assertEquals(0, answer.size());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testAwfulTableDeleteByExample() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            AwfulTableMapper mapper = sqlSession.getMapper(AwfulTableMapper.class);
            AwfulTable record = new AwfulTable();
            record.seteMail("fred@fred.com");
            record.setEmailaddress("alsofred@fred.com");
            record.setFirstFirstName("fred1");
            record.setFrom("from field");
            record.setId1(1);
            record.setId2(2);
            record.setId5(5);
            record.setId6(6);
            record.setId7(7);
            record.setSecondFirstName("fred2");
            record.setThirdFirstName("fred3");

            mapper.insert(record);

            record = new AwfulTable();
            record.seteMail("fred2@fred.com");
            record.setEmailaddress("alsofred2@fred.com");
            record.setFirstFirstName("fred11");
            record.setFrom("from from field");
            record.setId1(11);
            record.setId2(22);
            record.setId5(55);
            record.setId6(66);
            record.setId7(77);
            record.setSecondFirstName("fred22");
            record.setThirdFirstName("fred33");

            mapper.insert(record);

            List<AwfulTable> answer = mapper.selectByExample()
                    .build()
                    .execute();
            assertEquals(2, answer.size());

            int rows = mapper.deleteByExample()
                    .where(awfulTable.eMail, isLike("fred@%"))
                    .build()
                    .execute();
            assertEquals(1, rows);

            answer = mapper.selectByExample()
                    .build()
                    .execute();
            assertEquals(1, answer.size());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testAwfulTableSelectByPrimaryKey() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            AwfulTableMapper mapper = sqlSession.getMapper(AwfulTableMapper.class);
            AwfulTable record = new AwfulTable();
            record.seteMail("fred@fred.com");
            record.setEmailaddress("alsofred@fred.com");
            record.setFirstFirstName("fred1");
            record.setFrom("from field");
            record.setId1(1);
            record.setId2(2);
            record.setId5(5);
            record.setId6(6);
            record.setId7(7);
            record.setSecondFirstName("fred2");
            record.setThirdFirstName("fred3");

            mapper.insert(record);

            record = new AwfulTable();
            record.seteMail("fred2@fred.com");
            record.setEmailaddress("alsofred2@fred.com");
            record.setFirstFirstName("fred11");
            record.setFrom("from from field");
            record.setId1(11);
            record.setId2(22);
            record.setId5(55);
            record.setId6(66);
            record.setId7(77);
            record.setSecondFirstName("fred22");
            record.setThirdFirstName("fred33");

            mapper.insert(record);
            Integer generatedKey = record.getCustomerId();

            AwfulTable returnedRecord = mapper.selectByPrimaryKey(generatedKey);

            assertNotNull(returnedRecord);
            assertEquals(record.getCustomerId(), returnedRecord.getCustomerId());
            assertEquals(record.geteMail(), returnedRecord.geteMail());
            assertEquals(record.getEmailaddress(), returnedRecord
                    .getEmailaddress());
            assertEquals(record.getFirstFirstName(), returnedRecord
                    .getFirstFirstName());
            assertEquals(record.getFrom(), returnedRecord.getFrom());
            assertEquals(record.getId1(), returnedRecord.getId1());
            assertEquals(record.getId2(), returnedRecord.getId2());
            assertEquals(record.getId5(), returnedRecord.getId5());
            assertEquals(record.getId6(), returnedRecord.getId6());
            assertEquals(record.getId7(), returnedRecord.getId7());
            assertEquals(record.getSecondFirstName(), returnedRecord
                    .getSecondFirstName());
            assertEquals(record.getThirdFirstName(), returnedRecord
                    .getThirdFirstName());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testAwfulTableSelectByExampleLike() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            AwfulTableMapper mapper = sqlSession.getMapper(AwfulTableMapper.class);
            AwfulTable record = new AwfulTable();
            record.seteMail("fred@fred.com");
            record.setEmailaddress("alsofred@fred.com");
            record.setFirstFirstName("fred1");
            record.setFrom("from field");
            record.setId1(1);
            record.setId2(2);
            record.setId5(5);
            record.setId6(6);
            record.setId7(7);
            record.setSecondFirstName("fred2");
            record.setThirdFirstName("fred3");
            mapper.insert(record);

            record = new AwfulTable();
            record.seteMail("wilma@wilma.com");
            record.setEmailaddress("alsoWilma@wilma.com");
            record.setFirstFirstName("wilma1");
            record.setFrom("from field");
            record.setId1(11);
            record.setId2(22);
            record.setId5(55);
            record.setId6(66);
            record.setId7(77);
            record.setSecondFirstName("wilma2");
            record.setThirdFirstName("wilma3");
            mapper.insert(record);

            record = new AwfulTable();
            record.seteMail("pebbles@pebbles.com");
            record.setEmailaddress("alsoPebbles@pebbles.com");
            record.setFirstFirstName("pebbles1");
            record.setFrom("from field");
            record.setId1(111);
            record.setId2(222);
            record.setId5(555);
            record.setId6(666);
            record.setId7(777);
            record.setSecondFirstName("pebbles2");
            record.setThirdFirstName("pebbles3");
            mapper.insert(record);

            record = new AwfulTable();
            record.seteMail("barney@barney.com");
            record.setEmailaddress("alsoBarney@barney.com");
            record.setFirstFirstName("barney1");
            record.setFrom("from field");
            record.setId1(1111);
            record.setId2(2222);
            record.setId5(5555);
            record.setId6(6666);
            record.setId7(7777);
            record.setSecondFirstName("barney2");
            record.setThirdFirstName("barney3");
            mapper.insert(record);

            record = new AwfulTable();
            record.seteMail("betty@betty.com");
            record.setEmailaddress("alsoBetty@betty.com");
            record.setFirstFirstName("betty1");
            record.setFrom("from field");
            record.setId1(11111);
            record.setId2(22222);
            record.setId5(55555);
            record.setId6(66666);
            record.setId7(77777);
            record.setSecondFirstName("betty2");
            record.setThirdFirstName("betty3");
            mapper.insert(record);

            record = new AwfulTable();
            record.seteMail("bammbamm@bammbamm.com");
            record.setEmailaddress("alsoBammbamm@bammbamm.com");
            record.setFirstFirstName("bammbamm1");
            record.setFrom("from field");
            record.setId1(111111);
            record.setId2(222222);
            record.setId5(555555);
            record.setId6(666666);
            record.setId7(777777);
            record.setSecondFirstName("bammbamm2");
            record.setThirdFirstName("bammbamm3");
            mapper.insert(record);

            List<AwfulTable> answer = mapper.selectByExample()
                    .where(awfulTable.firstFirstName, isLike("b%"))
                    .orderBy(awfulTable.customerId)
                    .build()
                    .execute();
            assertEquals(3, answer.size());
            AwfulTable returnedRecord = answer.get(0);
            assertEquals(1111, returnedRecord.getId1().intValue());
            assertEquals(2222, returnedRecord.getId2().intValue());
            returnedRecord = answer.get(1);
            assertEquals(11111, returnedRecord.getId1().intValue());
            assertEquals(22222, returnedRecord.getId2().intValue());
            returnedRecord = answer.get(2);
            assertEquals(111111, returnedRecord.getId1().intValue());
            assertEquals(222222, returnedRecord.getId2().intValue());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testAwfulTableSelectByExampleNotLike() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            AwfulTableMapper mapper = sqlSession.getMapper(AwfulTableMapper.class);
            AwfulTable record = new AwfulTable();
            record.seteMail("fred@fred.com");
            record.setEmailaddress("alsofred@fred.com");
            record.setFirstFirstName("fred1");
            record.setFrom("from field");
            record.setId1(1);
            record.setId2(2);
            record.setId5(5);
            record.setId6(6);
            record.setId7(7);
            record.setSecondFirstName("fred2");
            record.setThirdFirstName("fred3");
            mapper.insert(record);

            record = new AwfulTable();
            record.seteMail("wilma@wilma.com");
            record.setEmailaddress("alsoWilma@wilma.com");
            record.setFirstFirstName("wilma1");
            record.setFrom("from field");
            record.setId1(11);
            record.setId2(22);
            record.setId5(55);
            record.setId6(66);
            record.setId7(77);
            record.setSecondFirstName("wilma2");
            record.setThirdFirstName("wilma3");
            mapper.insert(record);

            record = new AwfulTable();
            record.seteMail("pebbles@pebbles.com");
            record.setEmailaddress("alsoPebbles@pebbles.com");
            record.setFirstFirstName("pebbles1");
            record.setFrom("from field");
            record.setId1(111);
            record.setId2(222);
            record.setId5(555);
            record.setId6(666);
            record.setId7(777);
            record.setSecondFirstName("pebbles2");
            record.setThirdFirstName("pebbles3");
            mapper.insert(record);

            record = new AwfulTable();
            record.seteMail("barney@barney.com");
            record.setEmailaddress("alsoBarney@barney.com");
            record.setFirstFirstName("barney1");
            record.setFrom("from field");
            record.setId1(1111);
            record.setId2(2222);
            record.setId5(5555);
            record.setId6(6666);
            record.setId7(7777);
            record.setSecondFirstName("barney2");
            record.setThirdFirstName("barney3");
            mapper.insert(record);

            record = new AwfulTable();
            record.seteMail("betty@betty.com");
            record.setEmailaddress("alsoBetty@betty.com");
            record.setFirstFirstName("betty1");
            record.setFrom("from field");
            record.setId1(11111);
            record.setId2(22222);
            record.setId5(55555);
            record.setId6(66666);
            record.setId7(77777);
            record.setSecondFirstName("betty2");
            record.setThirdFirstName("betty3");
            mapper.insert(record);

            record = new AwfulTable();
            record.seteMail("bammbamm@bammbamm.com");
            record.setEmailaddress("alsoBammbamm@bammbamm.com");
            record.setFirstFirstName("bammbamm1");
            record.setFrom("from field");
            record.setId1(111111);
            record.setId2(222222);
            record.setId5(555555);
            record.setId6(666666);
            record.setId7(777777);
            record.setSecondFirstName("bammbamm2");
            record.setThirdFirstName("bammbamm3");
            mapper.insert(record);

            List<AwfulTable> answer = mapper.selectByExample()
                    .where(awfulTable.firstFirstName, isNotLike("b%"))
                    .orderBy(awfulTable.customerId)
                    .build()
                    .execute();
            assertEquals(3, answer.size());
            AwfulTable returnedRecord = answer.get(0);
            assertEquals(1, returnedRecord.getId1().intValue());
            assertEquals(2, returnedRecord.getId2().intValue());
            returnedRecord = answer.get(1);
            assertEquals(11, returnedRecord.getId1().intValue());
            assertEquals(22, returnedRecord.getId2().intValue());
            returnedRecord = answer.get(2);
            assertEquals(111, returnedRecord.getId1().intValue());
            assertEquals(222, returnedRecord.getId2().intValue());
        } finally {
            sqlSession.close();
        }
    }
    
    @Test
    public void testAwfulTableSelectByExampleComplexLike() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            AwfulTableMapper mapper = sqlSession.getMapper(AwfulTableMapper.class);
            AwfulTable record = new AwfulTable();
            record.seteMail("fred@fred.com");
            record.setEmailaddress("alsofred@fred.com");
            record.setFirstFirstName("fred1");
            record.setFrom("from field");
            record.setId1(1);
            record.setId2(2);
            record.setId5(5);
            record.setId6(6);
            record.setId7(7);
            record.setSecondFirstName("fred2");
            record.setThirdFirstName("fred3");
            mapper.insert(record);

            record = new AwfulTable();
            record.seteMail("wilma@wilma.com");
            record.setEmailaddress("alsoWilma@wilma.com");
            record.setFirstFirstName("wilma1");
            record.setFrom("from field");
            record.setId1(11);
            record.setId2(22);
            record.setId5(55);
            record.setId6(66);
            record.setId7(77);
            record.setSecondFirstName("wilma2");
            record.setThirdFirstName("wilma3");
            mapper.insert(record);

            record = new AwfulTable();
            record.seteMail("pebbles@pebbles.com");
            record.setEmailaddress("alsoPebbles@pebbles.com");
            record.setFirstFirstName("pebbles1");
            record.setFrom("from field");
            record.setId1(111);
            record.setId2(222);
            record.setId5(555);
            record.setId6(666);
            record.setId7(777);
            record.setSecondFirstName("pebbles2");
            record.setThirdFirstName("pebbles3");
            mapper.insert(record);

            record = new AwfulTable();
            record.seteMail("barney@barney.com");
            record.setEmailaddress("alsoBarney@barney.com");
            record.setFirstFirstName("barney1");
            record.setFrom("from field");
            record.setId1(1111);
            record.setId2(2222);
            record.setId5(5555);
            record.setId6(6666);
            record.setId7(7777);
            record.setSecondFirstName("barney2");
            record.setThirdFirstName("barney3");
            mapper.insert(record);

            record = new AwfulTable();
            record.seteMail("betty@betty.com");
            record.setEmailaddress("alsoBetty@betty.com");
            record.setFirstFirstName("betty1");
            record.setFrom("from field");
            record.setId1(11111);
            record.setId2(22222);
            record.setId5(55555);
            record.setId6(66666);
            record.setId7(77777);
            record.setSecondFirstName("betty2");
            record.setThirdFirstName("betty3");
            mapper.insert(record);

            record = new AwfulTable();
            record.seteMail("bammbamm@bammbamm.com");
            record.setEmailaddress("alsoBammbamm@bammbamm.com");
            record.setFirstFirstName("bammbamm1");
            record.setFrom("from field");
            record.setId1(111111);
            record.setId2(222222);
            record.setId5(555555);
            record.setId6(666666);
            record.setId7(777777);
            record.setSecondFirstName("bammbamm2");
            record.setThirdFirstName("bammbamm3");
            mapper.insert(record);

            List<AwfulTable> answer = mapper.selectByExample()
                    .where(awfulTable.firstFirstName, isLike("b%"), and(awfulTable.id2, isEqualTo(222222)))
                    .or(awfulTable.firstFirstName, isLike("wi%"))
                    .orderBy(awfulTable.customerId)
                    .build()
                    .execute();
                            
            assertEquals(2, answer.size());
            AwfulTable returnedRecord = answer.get(0);
            assertEquals(11, returnedRecord.getId1().intValue());
            assertEquals(22, returnedRecord.getId2().intValue());
            returnedRecord = answer.get(1);
            assertEquals(111111, returnedRecord.getId1().intValue());
            assertEquals(222222, returnedRecord.getId2().intValue());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testAwfulTableSelectByExampleIn() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            AwfulTableMapper mapper = sqlSession.getMapper(AwfulTableMapper.class);
            AwfulTable record = new AwfulTable();
            record.seteMail("fred@fred.com");
            record.setEmailaddress("alsofred@fred.com");
            record.setFirstFirstName("fred1");
            record.setFrom("from field");
            record.setId1(1);
            record.setId2(2);
            record.setId5(5);
            record.setId6(6);
            record.setId7(7);
            record.setSecondFirstName("fred2");
            record.setThirdFirstName("fred3");
            mapper.insert(record);

            record = new AwfulTable();
            record.seteMail("wilma@wilma.com");
            record.setEmailaddress("alsoWilma@wilma.com");
            record.setFirstFirstName("wilma1");
            record.setFrom("from field");
            record.setId1(11);
            record.setId2(22);
            record.setId5(55);
            record.setId6(66);
            record.setId7(77);
            record.setSecondFirstName("wilma2");
            record.setThirdFirstName("wilma3");
            mapper.insert(record);

            record = new AwfulTable();
            record.seteMail("pebbles@pebbles.com");
            record.setEmailaddress("alsoPebbles@pebbles.com");
            record.setFirstFirstName("pebbles1");
            record.setFrom("from field");
            record.setId1(111);
            record.setId2(222);
            record.setId5(555);
            record.setId6(666);
            record.setId7(777);
            record.setSecondFirstName("pebbles2");
            record.setThirdFirstName("pebbles3");
            mapper.insert(record);

            record = new AwfulTable();
            record.seteMail("barney@barney.com");
            record.setEmailaddress("alsoBarney@barney.com");
            record.setFirstFirstName("barney1");
            record.setFrom("from field");
            record.setId1(1111);
            record.setId2(2222);
            record.setId5(5555);
            record.setId6(6666);
            record.setId7(7777);
            record.setSecondFirstName("barney2");
            record.setThirdFirstName("barney3");
            mapper.insert(record);

            record = new AwfulTable();
            record.seteMail("betty@betty.com");
            record.setEmailaddress("alsoBetty@betty.com");
            record.setFirstFirstName("betty1");
            record.setFrom("from field");
            record.setId1(11111);
            record.setId2(22222);
            record.setId5(55555);
            record.setId6(66666);
            record.setId7(77777);
            record.setSecondFirstName("betty2");
            record.setThirdFirstName("betty3");
            mapper.insert(record);

            record = new AwfulTable();
            record.seteMail("bammbamm@bammbamm.com");
            record.setEmailaddress("alsoBammbamm@bammbamm.com");
            record.setFirstFirstName("bammbamm1");
            record.setFrom("from field");
            record.setId1(111111);
            record.setId2(222222);
            record.setId5(555555);
            record.setId6(666666);
            record.setId7(777777);
            record.setSecondFirstName("bammbamm2");
            record.setThirdFirstName("bammbamm3");
            mapper.insert(record);

            List<AwfulTable> answer = mapper.selectByExample()
                    .where(awfulTable.id1, isIn(1, 11))
                    .orderBy(awfulTable.customerId)
                    .build()
                    .execute();
            
            assertEquals(2, answer.size());
            AwfulTable returnedRecord = answer.get(0);
            assertEquals(1, returnedRecord.getId1().intValue());
            assertEquals(2, returnedRecord.getId2().intValue());
            returnedRecord = answer.get(1);
            assertEquals(11, returnedRecord.getId1().intValue());
            assertEquals(22, returnedRecord.getId2().intValue());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testAwfulTableSelectByExampleBetween() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            AwfulTableMapper mapper = sqlSession.getMapper(AwfulTableMapper.class);
            AwfulTable record = new AwfulTable();
            record.seteMail("fred@fred.com");
            record.setEmailaddress("alsofred@fred.com");
            record.setFirstFirstName("fred1");
            record.setFrom("from field");
            record.setId1(1);
            record.setId2(2);
            record.setId5(5);
            record.setId6(6);
            record.setId7(7);
            record.setSecondFirstName("fred2");
            record.setThirdFirstName("fred3");
            mapper.insert(record);

            record = new AwfulTable();
            record.seteMail("wilma@wilma.com");
            record.setEmailaddress("alsoWilma@wilma.com");
            record.setFirstFirstName("wilma1");
            record.setFrom("from field");
            record.setId1(11);
            record.setId2(22);
            record.setId5(55);
            record.setId6(66);
            record.setId7(77);
            record.setSecondFirstName("wilma2");
            record.setThirdFirstName("wilma3");
            mapper.insert(record);

            record = new AwfulTable();
            record.seteMail("pebbles@pebbles.com");
            record.setEmailaddress("alsoPebbles@pebbles.com");
            record.setFirstFirstName("pebbles1");
            record.setFrom("from field");
            record.setId1(111);
            record.setId2(222);
            record.setId5(555);
            record.setId6(666);
            record.setId7(777);
            record.setSecondFirstName("pebbles2");
            record.setThirdFirstName("pebbles3");
            mapper.insert(record);

            record = new AwfulTable();
            record.seteMail("barney@barney.com");
            record.setEmailaddress("alsoBarney@barney.com");
            record.setFirstFirstName("barney1");
            record.setFrom("from field");
            record.setId1(1111);
            record.setId2(2222);
            record.setId5(5555);
            record.setId6(6666);
            record.setId7(7777);
            record.setSecondFirstName("barney2");
            record.setThirdFirstName("barney3");
            mapper.insert(record);

            record = new AwfulTable();
            record.seteMail("betty@betty.com");
            record.setEmailaddress("alsoBetty@betty.com");
            record.setFirstFirstName("betty1");
            record.setFrom("from field");
            record.setId1(11111);
            record.setId2(22222);
            record.setId5(55555);
            record.setId6(66666);
            record.setId7(77777);
            record.setSecondFirstName("betty2");
            record.setThirdFirstName("betty3");
            mapper.insert(record);

            record = new AwfulTable();
            record.seteMail("bammbamm@bammbamm.com");
            record.setEmailaddress("alsoBammbamm@bammbamm.com");
            record.setFirstFirstName("bammbamm1");
            record.setFrom("from field");
            record.setId1(111111);
            record.setId2(222222);
            record.setId5(555555);
            record.setId6(666666);
            record.setId7(777777);
            record.setSecondFirstName("bammbamm2");
            record.setThirdFirstName("bammbamm3");
            mapper.insert(record);

            List<AwfulTable> answer = mapper.selectByExample()
                    .where(awfulTable.id1, isBetween(1).and(1000))
                    .build()
                    .execute();
            assertEquals(3, answer.size());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testAwfulTableSelectByExampleNoCriteria() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            AwfulTableMapper mapper = sqlSession.getMapper(AwfulTableMapper.class);
            AwfulTable record = new AwfulTable();
            record.seteMail("fred@fred.com");
            record.setEmailaddress("alsofred@fred.com");
            record.setFirstFirstName("fred1");
            record.setFrom("from field");
            record.setId1(1);
            record.setId2(2);
            record.setId5(5);
            record.setId6(6);
            record.setId7(7);
            record.setSecondFirstName("fred2");
            record.setThirdFirstName("fred3");
            mapper.insert(record);

            record = new AwfulTable();
            record.seteMail("wilma@wilma.com");
            record.setEmailaddress("alsoWilma@wilma.com");
            record.setFirstFirstName("wilma1");
            record.setFrom("from field");
            record.setId1(11);
            record.setId2(22);
            record.setId5(55);
            record.setId6(66);
            record.setId7(77);
            record.setSecondFirstName("wilma2");
            record.setThirdFirstName("wilma3");
            mapper.insert(record);

            record = new AwfulTable();
            record.seteMail("pebbles@pebbles.com");
            record.setEmailaddress("alsoPebbles@pebbles.com");
            record.setFirstFirstName("pebbles1");
            record.setFrom("from field");
            record.setId1(111);
            record.setId2(222);
            record.setId5(555);
            record.setId6(666);
            record.setId7(777);
            record.setSecondFirstName("pebbles2");
            record.setThirdFirstName("pebbles3");
            mapper.insert(record);

            record = new AwfulTable();
            record.seteMail("barney@barney.com");
            record.setEmailaddress("alsoBarney@barney.com");
            record.setFirstFirstName("barney1");
            record.setFrom("from field");
            record.setId1(1111);
            record.setId2(2222);
            record.setId5(5555);
            record.setId6(6666);
            record.setId7(7777);
            record.setSecondFirstName("barney2");
            record.setThirdFirstName("barney3");
            mapper.insert(record);

            record = new AwfulTable();
            record.seteMail("betty@betty.com");
            record.setEmailaddress("alsoBetty@betty.com");
            record.setFirstFirstName("betty1");
            record.setFrom("from field");
            record.setId1(11111);
            record.setId2(22222);
            record.setId5(55555);
            record.setId6(66666);
            record.setId7(77777);
            record.setSecondFirstName("betty2");
            record.setThirdFirstName("betty3");
            mapper.insert(record);

            record = new AwfulTable();
            record.seteMail("bammbamm@bammbamm.com");
            record.setEmailaddress("alsoBammbamm@bammbamm.com");
            record.setFirstFirstName("bammbamm1");
            record.setFrom("from field");
            record.setId1(111111);
            record.setId2(222222);
            record.setId5(555555);
            record.setId6(666666);
            record.setId7(777777);
            record.setSecondFirstName("bammbamm2");
            record.setThirdFirstName("bammbamm3");
            mapper.insert(record);

            List<AwfulTable> answer = mapper.selectByExample()
                    .orderBy(awfulTable.customerId.descending())
                    .build()
                    .execute();
            assertEquals(6, answer.size());
            AwfulTable returnedRecord = answer.get(0);
            assertEquals(111111, returnedRecord.getId1().intValue());
            returnedRecord = answer.get(1);
            assertEquals(11111, returnedRecord.getId1().intValue());
            returnedRecord = answer.get(2);
            assertEquals(1111, returnedRecord.getId1().intValue());
            returnedRecord = answer.get(3);
            assertEquals(111, returnedRecord.getId1().intValue());
            returnedRecord = answer.get(4);
            assertEquals(11, returnedRecord.getId1().intValue());
            returnedRecord = answer.get(5);
            assertEquals(1, returnedRecord.getId1().intValue());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testAwfulTableCountByExample() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            AwfulTableMapper mapper = sqlSession.getMapper(AwfulTableMapper.class);
            AwfulTable record = new AwfulTable();
            record.seteMail("fred@fred.com");
            record.setEmailaddress("alsofred@fred.com");
            record.setFirstFirstName("fred1");
            record.setFrom("from field");
            record.setId1(1);
            record.setId2(2);
            record.setId5(5);
            record.setId6(6);
            record.setId7(7);
            record.setSecondFirstName("fred2");
            record.setThirdFirstName("fred3");

            mapper.insert(record);

            record = new AwfulTable();
            record.seteMail("fred2@fred.com");
            record.setEmailaddress("alsofred2@fred.com");
            record.setFirstFirstName("fred11");
            record.setFrom("from from field");
            record.setId1(11);
            record.setId2(22);
            record.setId5(55);
            record.setId6(66);
            record.setId7(77);
            record.setSecondFirstName("fred22");
            record.setThirdFirstName("fred33");

            mapper.insert(record);

            long rows = mapper.countByExample()
                    .where(awfulTable.eMail, isLike("fred@%"))
                    .build()
                    .execute();
            assertEquals(1, rows);

            rows = mapper.countByExample()
                    .build()
                    .execute();
            assertEquals(2, rows);
        } finally {
            sqlSession.close();
        }
    }
    
    @Test
    public void testTranslationTable() {
        try(SqlSession sqlSession = sqlSessionFactory.openSession()) {
            TranslationMapper mapper = sqlSession.getMapper(TranslationMapper.class);
            
            Translation t = new Translation();
            t.setId(1);
            t.setTranslation("Spanish");
            mapper.insert(t);
            
            t = new Translation();
            t.setId(2);
            t.setTranslation("French");
            mapper.insert(t);
            
            Translation returnedRecord = mapper.selectByPrimaryKey(2);
            
            assertEquals(t.getId(), returnedRecord.getId());
            assertEquals(t.getTranslation(), returnedRecord.getTranslation());
            
            t.setTranslation("Italian");
            mapper.updateByPrimaryKey(t);
            
            returnedRecord = mapper.selectByPrimaryKey(2);
            
            assertEquals(t.getId(), returnedRecord.getId());
            assertEquals(t.getTranslation(), returnedRecord.getTranslation());
        }
    }
    
    @Test
    public void testIdTable() {
        try(SqlSession sqlSession = sqlSessionFactory.openSession()) {
            IdMapper mapper = sqlSession.getMapper(IdMapper.class);
            
            Id id = new Id();
            id.setId(1);
            id.setDescription("Spanish");
            mapper.insert(id);
            
            id = new Id();
            id.setId(2);
            id.setDescription("French");
            mapper.insert(id);
            
            Id returnedRecord = mapper.selectByPrimaryKey(2);
            
            assertEquals(id.getId(), returnedRecord.getId());
            assertEquals(id.getDescription(), returnedRecord.getDescription());
            
            id.setDescription("Italian");
            mapper.updateByPrimaryKey(id);
            
            returnedRecord = mapper.selectByPrimaryKey(2);
            
            assertEquals(id.getId(), returnedRecord.getId());
            assertEquals(id.getDescription(), returnedRecord.getDescription());
        }
    }
    
    @Test
    public void testEquals1() {
        mbg.test.mb3.generated.dsql.model.PKFields PKFields1 = new mbg.test.mb3.generated.dsql.model.PKFields();
        assertFalse(PKFields1.equals(null));
    }
    
    @Test
    public void testEquals2() {
        mbg.test.mb3.generated.dsql.model.PKFields PKFields1 = new mbg.test.mb3.generated.dsql.model.PKFields();
        mbg.test.mb3.generated.dsql.model.PKFields PKFields2 = new mbg.test.mb3.generated.dsql.model.PKFields();
        assertTrue(PKFields1.equals(PKFields2));
    }
    
    @Test
    public void testEquals3() {
        mbg.test.mb3.generated.dsql.model.PKFields PKFields1 = new mbg.test.mb3.generated.dsql.model.PKFields();
        PKFields1.setId1(2);

        mbg.test.mb3.generated.dsql.model.PKFields PKFields2 = new mbg.test.mb3.generated.dsql.model.PKFields();
        PKFields2.setId1(2);
        
        assertTrue(PKFields1.equals(PKFields2));
    }
    
    @Test
    public void testEquals4() {
        mbg.test.mb3.generated.dsql.model.PKFields PKFields1 = new mbg.test.mb3.generated.dsql.model.PKFields();
        PKFields1.setId1(2);

        mbg.test.mb3.generated.dsql.model.PKFields PKFields2 = new mbg.test.mb3.generated.dsql.model.PKFields();
        PKFields2.setId1(3);
        
        assertFalse(PKFields1.equals(PKFields2));
    }

    @Test
    public void testEquals5() {
        AwfulTable awfulTable1 = new AwfulTable();
        awfulTable1.setActive(false);
        awfulTable1.setCustomerId(3);
        awfulTable1.seteMail("fred@fred.com");
        awfulTable1.setEmailaddress("fred@fred.com");
        awfulTable1.setFirstFirstName("Fred");
        awfulTable1.setFrom("from");
        awfulTable1.setId1(22);
        awfulTable1.setId2(33);
        awfulTable1.setId5(55);
        awfulTable1.setId6(66);
        awfulTable1.setId7(77);
        awfulTable1.setLastName("Rubble");
        awfulTable1.setSecondFirstName("Bamm Bamm");
        awfulTable1.setThirdFirstName("Pebbles");

        AwfulTable awfulTable2 = new AwfulTable();
        awfulTable2.setActive(false);
        awfulTable2.setCustomerId(3);
        awfulTable2.seteMail("fred@fred.com");
        awfulTable2.setEmailaddress("fred@fred.com");
        awfulTable2.setFirstFirstName("Fred");
        awfulTable2.setFrom("from");
        awfulTable2.setId1(22);
        awfulTable2.setId2(33);
        awfulTable2.setId5(55);
        awfulTable2.setId6(66);
        awfulTable2.setId7(77);
        awfulTable2.setLastName("Rubble");
        awfulTable2.setSecondFirstName("Bamm Bamm");
        awfulTable2.setThirdFirstName("Pebbles");
        
        assertTrue(awfulTable1.equals(awfulTable2));
        
        awfulTable2.setActive(true);
        assertFalse(awfulTable1.equals(awfulTable2));
    }

    @Test
    public void testHashCode1() {
        mbg.test.mb3.generated.dsql.model.PKFields PKFields1 = new mbg.test.mb3.generated.dsql.model.PKFields();
        mbg.test.mb3.generated.dsql.model.PKFields PKFields2 = new mbg.test.mb3.generated.dsql.model.PKFields();
        assertTrue(PKFields1.hashCode() == PKFields2.hashCode());
    }
    
    @Test
    public void testHashCode2() {
        mbg.test.mb3.generated.dsql.model.PKFields PKFields1 = new mbg.test.mb3.generated.dsql.model.PKFields();
        PKFields1.setId1(2);

        mbg.test.mb3.generated.dsql.model.PKFields PKFields2 = new mbg.test.mb3.generated.dsql.model.PKFields();
        PKFields2.setId1(2);
        
        assertTrue(PKFields1.hashCode() == PKFields2.hashCode());
    }

    @Test
    public void testHashCode3() {
        AwfulTable awfulTable1 = new AwfulTable();
        awfulTable1.setActive(false);
        awfulTable1.setCustomerId(3);
        awfulTable1.seteMail("fred@fred.com");
        awfulTable1.setEmailaddress("fred@fred.com");
        awfulTable1.setFirstFirstName("Fred");
        awfulTable1.setFrom("from");
        awfulTable1.setId1(22);
        awfulTable1.setId2(33);
        awfulTable1.setId5(55);
        awfulTable1.setId6(66);
        awfulTable1.setId7(77);
        awfulTable1.setLastName("Rubble");
        awfulTable1.setSecondFirstName("Bamm Bamm");
        awfulTable1.setThirdFirstName("Pebbles");

        AwfulTable awfulTable2 = new AwfulTable();
        awfulTable2.setActive(false);
        awfulTable2.setCustomerId(3);
        awfulTable2.seteMail("fred@fred.com");
        awfulTable2.setEmailaddress("fred@fred.com");
        awfulTable2.setFirstFirstName("Fred");
        awfulTable2.setFrom("from");
        awfulTable2.setId1(22);
        awfulTable2.setId2(33);
        awfulTable2.setId5(55);
        awfulTable2.setId6(66);
        awfulTable2.setId7(77);
        awfulTable2.setLastName("Rubble");
        awfulTable2.setSecondFirstName("Bamm Bamm");
        awfulTable2.setThirdFirstName("Pebbles");
        
        assertTrue(awfulTable1.hashCode() == awfulTable2.hashCode());
    }
}

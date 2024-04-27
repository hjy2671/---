package me.zhengjie.base;

import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import com.baomidou.mybatisplus.extension.toolkit.ChainWrappers;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;

import java.util.Collection;

/**
* 公共抽象Mapper接口类
* @author fanglei
* @date 2021/07/28
*/
@SuppressWarnings("unchecked")
@Mapper
public interface CommonMapper<M,E> extends BaseMapper<E> {

    Log log = LogFactory.getLog(CommonMapper.class);
    int DEFAULT_BATCH_SIZE = 1000;

    default QueryChainWrapper<E> query() {
        return ChainWrappers.queryChain(this);
    }
    
    default LambdaQueryChainWrapper<E> lambdaQuery() {
        return ChainWrappers.lambdaQueryChain(this);
    }
    
    default UpdateChainWrapper<E> update() {
        return ChainWrappers.updateChain(this);
    }
    
    default LambdaUpdateChainWrapper<E> lambdaUpdate() {
        return ChainWrappers.lambdaUpdateChain(this);
    }

    default Class<M> currentMapperClass() {
        return (Class<M>) ReflectionKit.getSuperClassGenericType(this.getClass(), CommonMapper.class, 0);
    }

    default Class<E> currentModelClass() {
        return (Class<E>) ReflectionKit.getSuperClassGenericType(this.getClass(), CommonMapper.class, 1);
    }

    /**
     * 批量插入(包含限制条数)
     */
    default boolean insertBatch(Collection<E> entityList, int batchSize) {
        String sqlStatement = SqlHelper.getSqlStatement(this.currentMapperClass(), SqlMethod.INSERT_ONE);
        return SqlHelper.executeBatch(this.currentModelClass(), log, entityList, batchSize,
                (sqlSession, entity) -> sqlSession.insert(sqlStatement, entity));
    }

    default boolean insertBatch(Collection<E> entityList) {
        return this.insertBatch(entityList, DEFAULT_BATCH_SIZE);
    }

    default boolean updateBatch(Collection<E> entityList, int batchSize) {
        String sqlStatement = SqlHelper.getSqlStatement(this.currentMapperClass(), SqlMethod.UPDATE_BY_ID);
        return SqlHelper.executeBatch(this.currentModelClass(), log, entityList, batchSize,
                (sqlSession, entity) -> sqlSession.insert(sqlStatement, entity));
    }

    default boolean updateBatch(Collection<E> entityList) {
        return this.insertBatch(entityList, DEFAULT_BATCH_SIZE);
    }
}

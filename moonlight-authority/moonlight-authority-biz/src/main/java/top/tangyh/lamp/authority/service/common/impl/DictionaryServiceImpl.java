package top.tangyh.lamp.authority.service.common.impl;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.ImmutableMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.tangyh.basic.base.entity.SuperEntity;
import top.tangyh.basic.base.service.SuperServiceImpl;
import top.tangyh.basic.cache.model.CacheHashKey;
import top.tangyh.basic.cache.repository.CachePlusOps;
import top.tangyh.basic.database.mybatis.conditions.Wraps;
import top.tangyh.basic.database.mybatis.conditions.query.LbqWrapper;
import top.tangyh.basic.echo.properties.EchoProperties;
import top.tangyh.basic.exception.BizException;
import top.tangyh.basic.utils.ArgumentAssert;
import top.tangyh.basic.utils.BeanPlusUtil;
import top.tangyh.basic.utils.CollHelper;
import top.tangyh.lamp.authority.dao.common.DictionaryMapper;
import top.tangyh.lamp.authority.dto.common.DictionaryPageQuery;
import top.tangyh.lamp.authority.dto.common.DictionaryTypeSaveDTO;
import top.tangyh.lamp.authority.entity.common.Dictionary;
import top.tangyh.lamp.authority.service.common.DictionaryService;
import top.tangyh.lamp.common.cache.common.DictionaryTypeCacheKeyBuilder;
import top.tangyh.lamp.common.constant.DefValConstants;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

/**
 * <p>
 * 业务实现类
 * 字典类型
 * </p>
 *
 * @author zuihou
 * @date 2019-07-02
 */
@Slf4j
@Service

@RequiredArgsConstructor
public class DictionaryServiceImpl extends SuperServiceImpl<DictionaryMapper, Dictionary> implements DictionaryService {

    private final EchoProperties ips;
    private final CachePlusOps cachePlusOps;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Dictionary saveType(DictionaryTypeSaveDTO dictType) {
        long typeCount = count(Wraps.<Dictionary>lbQ().eq(Dictionary::getType, dictType.getType()));
        if (typeCount > 0) {
            throw BizException.validFail("字典类型[%s]已存在", dictType.getType());
        }
        Dictionary dict = BeanPlusUtil.toBean(dictType, Dictionary.class);
        dict.setName(DefValConstants.DICT_PLACEHOLDER);
        dict.setCode(DefValConstants.DICT_PLACEHOLDER);
        dict.setSortValue(DefValConstants.SORT_VALUE);
        dict.setReadonly(false);
        baseMapper.insert(dict);
        return dict;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteType(List<String> types) {
        LbqWrapper<Dictionary> typeWrapper = Wraps.<Dictionary>lbQ().in(Dictionary::getType, types);
        List<Dictionary> list = list(typeWrapper);

        List<Long> ids = list.stream().map(Dictionary::getId).collect(toList());
        boolean bool = removeByIds(ids);

        CacheHashKey[] typeKeys = list.stream().map(type -> new DictionaryTypeCacheKeyBuilder().hashKey(type.getType())).toArray(CacheHashKey[]::new);
        cachePlusOps.del(typeKeys);
        return bool;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateType(DictionaryTypeSaveDTO dictType) {
        LbqWrapper<Dictionary> wrapper = Wraps.<Dictionary>lbQ().eq(Dictionary::getType, dictType.getType());
        List<Dictionary> list = list(wrapper);
        if (list.isEmpty()) {
            return true;
        }
        list.forEach(item -> {
            item.setState(dictType.getState());
            item.setLabel(dictType.getLabel());
            item.setUpdateTime(null);
        });
        boolean bool = updateBatchById(list);

        CacheHashKey typeKey = new DictionaryTypeCacheKeyBuilder().hashKey(dictType.getType());
        cachePlusOps.del(typeKey);
        return bool;
    }

    @Override
    @Transactional(readOnly = true)
    public IPage<Dictionary> pageType(IPage<Dictionary> page, DictionaryPageQuery query) {
        return baseMapper.pageType(page, query);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(Dictionary model) {
        long count = count(Wraps.<Dictionary>lbQ().eq(Dictionary::getType, model.getType()).eq(Dictionary::getCode, model.getCode()));
        ArgumentAssert.isFalse(count > 0, StrUtil.format("字典[{}]已经存在，请勿重复创建", model.getCode()));

        Dictionary type = getOne(Wraps.<Dictionary>lbQ().eq(Dictionary::getType, model.getType()).eq(Dictionary::getCode, DefValConstants.DICT_PLACEHOLDER));
        boolean bool;
        if (type == null) {
            bool = super.save(model);
        } else {
            BeanPlusUtil.copyProperties(model, type, SuperEntity.FIELD_ID, SuperEntity.CREATE_TIME, SuperEntity.CREATED_BY);
            bool = super.updateById(type);
        }

        CacheHashKey typeKey = new DictionaryTypeCacheKeyBuilder().hashFieldKey(model.getCode(), model.getType());
        cachePlusOps.hSet(typeKey, model.getName());
        return bool;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateAllById(Dictionary model) {
        return update(model, super::updateAllById);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(Dictionary model) {
        return update(model, super::updateById);
    }

    private boolean update(Dictionary model, Function<Dictionary, Boolean> function) {
        long count = count(Wraps.<Dictionary>lbQ().eq(Dictionary::getType, model.getType())
                .eq(Dictionary::getCode, model.getCode()).ne(Dictionary::getId, model.getId()));
        ArgumentAssert.isFalse(count > 0, StrUtil.format("字典[{}]已经存在，请勿重复创建", model.getCode()));
        Dictionary old = getById(model.getId());
        ArgumentAssert.notNull(old, "字典不存在或已被删除！");
        boolean bool = function.apply(model);

        CacheHashKey typeKey = new DictionaryTypeCacheKeyBuilder().hashKey(model.getType());
        cachePlusOps.del(typeKey);
        return bool;
    }

    @Override
    public boolean removeById(Serializable id) {
        Dictionary model = getById(id);
        ArgumentAssert.notNull(model, "字典项不存在");
        boolean remove = super.removeById(id);

        CacheHashKey typeKey = new DictionaryTypeCacheKeyBuilder().hashFieldKey(model.getCode(), model.getType());
        cachePlusOps.hDel(typeKey);
        return remove;
    }


    @Override
    public boolean removeByIds(Collection<?> idList) {
        if (idList.isEmpty()) {
            return true;
        }
        List<Dictionary> list = listByIds((Collection<? extends Serializable>) idList);
        boolean remove = super.removeByIds(idList);
        CacheHashKey[] hashKeys = list.stream()
                .map(model -> new DictionaryTypeCacheKeyBuilder()
                        .hashKey(model.getType())).toArray(CacheHashKey[]::new);
        cachePlusOps.del(hashKeys);
        return remove;
    }


    @Override
    public Map<String, List<Dictionary>> listByTypes(String[] types) {
        if (ArrayUtil.isEmpty(types)) {
            return Collections.emptyMap();
        }
        LbqWrapper<Dictionary> query = Wraps.<Dictionary>lbQ()
                .in(Dictionary::getType, (Object[]) types)
                .eq(Dictionary::getState, true)
                .orderByAsc(Dictionary::getSortValue);
        List<Dictionary> list = super.list(query);

        //key 是类型
        return list.stream().collect(groupingBy(Dictionary::getType, LinkedHashMap::new, toList()));
    }

    @Override
    public Map<String, Map<Dictionary, String>> mapInverse(String[] types) {
        Map<String, List<Dictionary>> typeMap = listByTypes(types);

        //需要返回的map
        Map<String, Map<Dictionary, String>> typeCodeNameMap = new LinkedHashMap<>(CollHelper.initialCapacity(typeMap.size()));

        typeMap.forEach((key, items) -> {
            ImmutableMap<Dictionary, String> itemCodeMap = CollHelper.uniqueIndex(items, i -> i, Dictionary::getName);
            typeCodeNameMap.put(key, itemCodeMap);
        });
        return typeCodeNameMap;
    }

    @Override
    public Map<Serializable, Object> findByIds(Set<Serializable> types) {
        if (types.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<Serializable, Object> codeValueMap = new HashMap<>();
        types.forEach(type -> {
            Function<CacheHashKey, Map<String, String>> fun = (ck) -> {
                LbqWrapper<Dictionary> wrap = Wraps.<Dictionary>lbQ().eq(Dictionary::getType, type);
                List<Dictionary> list = list(wrap);
                return CollHelper.uniqueIndex(list, Dictionary::getCode, Dictionary::getName);
            };
            Map<String, String> map = cachePlusOps.hGetAll(new DictionaryTypeCacheKeyBuilder().hashKey(type), fun);
            map.forEach((value, txt) -> codeValueMap.put(StrUtil.join(ips.getDictSeparator(), type, value), txt));
        });
        return codeValueMap;
    }

}

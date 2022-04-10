package top.tangyh.lamp.authority.service.core.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.tangyh.basic.annotation.echo.EchoResult;
import top.tangyh.basic.base.request.PageParams;
import top.tangyh.basic.base.service.SuperCacheServiceImpl;
import top.tangyh.basic.cache.model.CacheKeyBuilder;
import top.tangyh.basic.database.mybatis.conditions.Wraps;
import top.tangyh.basic.database.mybatis.conditions.query.LbqWrapper;
import top.tangyh.basic.utils.ArgumentAssert;
import top.tangyh.basic.utils.CollHelper;
import top.tangyh.lamp.authority.dao.core.StationMapper;
import top.tangyh.lamp.authority.dto.core.StationPageQuery;
import top.tangyh.lamp.authority.entity.core.Station;
import top.tangyh.lamp.authority.service.core.StationService;
import top.tangyh.lamp.common.cache.core.StationCacheKeyBuilder;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 业务实现类
 * 岗位
 * </p>
 *
 * @author zuihou
 * @date 2019-07-22
 */
@Slf4j
@Service

public class StationServiceImpl extends SuperCacheServiceImpl<StationMapper, Station> implements StationService {
    @Override
    protected CacheKeyBuilder cacheKeyBuilder() {
        return new StationCacheKeyBuilder();
    }

    @Override
    public boolean check(Long id, String name) {
        LbqWrapper<Station> wrap = Wraps.<Station>lbQ()
                .eq(Station::getName, name).ne(Station::getId, id);
        return count(wrap) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(Station model) {
        ArgumentAssert.isFalse(check(null, model.getName()), StrUtil.format("岗位[{}]已经存在", model.getName()));
        return super.save(model);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(Station model) {
        ArgumentAssert.isFalse(check(model.getId(), model.getName()), StrUtil.format("岗位[{}]已经存在", model.getName()));
        return super.updateById(model);
    }

    @Override
    @EchoResult
    public IPage<Station> findStationPage(IPage<Station> page, PageParams<StationPageQuery> params) {
        StationPageQuery data = params.getModel();
        Station station = BeanUtil.toBean(data, Station.class);

        //Wraps.lbQ(station); 这种写法值 不能和  ${ew.customSqlSegment} 一起使用
        LbqWrapper<Station> wrapper = Wraps.lbq(null, params.getExtra(), Station.class);

        // ${ew.customSqlSegment} 语法一定要手动eq like 等
        wrapper.like(Station::getName, station.getName())
                .like(Station::getDescribe, station.getDescribe())
                .eq(Station::getOrgId, station.getOrgId())
                .eq(Station::getState, station.getState());
        return baseMapper.findStationPage(page, wrapper);
    }

    @Override
    public Map<Serializable, Object> findByIds(Set<Serializable> ids) {
        return CollHelper.uniqueIndex(findStation(ids), Station::getId, Station::getName);
    }

    private List<Station> findStation(Set<Serializable> ids) {
        // 强转， 防止数据库隐式转换，  若你的id 是string类型，请勿强转
        return findByIds(ids,
                missIds -> super.listByIds(missIds.stream().filter(Objects::nonNull).map(Convert::toLong).collect(Collectors.toList()))
        );
    }

}

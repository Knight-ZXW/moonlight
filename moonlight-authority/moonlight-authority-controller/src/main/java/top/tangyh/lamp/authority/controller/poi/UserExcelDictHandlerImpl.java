package top.tangyh.lamp.authority.controller.poi;

import cn.afterturn.easypoi.handler.inter.IExcelDictHandler;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import top.tangyh.basic.database.mybatis.conditions.Wraps;
import top.tangyh.lamp.authority.entity.common.Dictionary;
import top.tangyh.lamp.authority.service.common.DictionaryService;

/**
 * 用户导出字典处理器
 *
 * @author tangyh
 * @version v1.0
 * @date 2021/5/23 6:58 下午
 * @create [2021/5/23 6:58 下午 ] [tangyh] [初始创建]
 */
@Component
@RequiredArgsConstructor
public class UserExcelDictHandlerImpl implements IExcelDictHandler {
    public static final String DICT_NATION = "NATION";
    public static final String DICT_EDUCATION = "EDUCATION";
    public static final String DICT_POSITION_STATUS = "POSITION_STATUS";
    private final DictionaryService dictionaryService;

    @Override
    public String toName(String dict, Object obj, String name, Object value) {
        if (value == null) {
            return null;
        }

        if (StrUtil.equalsAny(dict, DICT_NATION, DICT_EDUCATION, DICT_POSITION_STATUS)) {
            Dictionary dictionary = dictionaryService.getOne(Wraps.<Dictionary>lbQ()
                    .eq(Dictionary::getType, dict)
                    .eq(Dictionary::getCode, String.valueOf(value)), false);
            return dictionary != null ? dictionary.getName() : String.valueOf(value);
        }
        return String.valueOf(value);
    }

    @Override
    public String toValue(String dict, Object obj, String name, Object value) {
        if (value == null) {
            return null;
        }
        if (StrUtil.equalsAny(dict, DICT_NATION, DICT_EDUCATION, DICT_POSITION_STATUS)) {
            Dictionary dictionary = dictionaryService.getOne(Wraps.<Dictionary>lbQ()
                    .eq(Dictionary::getType, dict)
                    .eq(Dictionary::getName, String.valueOf(value)), false);
            return dictionary != null ? dictionary.getCode() : String.valueOf(value);
        }
        return value.toString();
    }
}

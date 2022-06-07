package com.github.ccphamy.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ccphamy.modules.sys.entity.SysConfigEntity;
import com.github.ccphamy.modules.sys.service.SysConfigService;
import com.github.ccphamy.common.exception.RRException;
import com.github.ccphamy.common.utils.PageUtils;
import com.github.ccphamy.common.utils.Query;
import com.github.ccphamy.modules.sys.dao.SysConfigDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Map;

@Service("sysConfigService")
public class SysConfigServiceImpl extends ServiceImpl<SysConfigDao, SysConfigEntity> implements SysConfigService {

    private final static ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String paramKey = (String) params.get("paramKey");

        IPage<SysConfigEntity> page = this.page(
            new Query<SysConfigEntity>().getPage(params),
            new QueryWrapper<SysConfigEntity>()
                .like(StringUtils.isNotBlank(paramKey), "param_key", paramKey)
                .eq("status", 1)
        );

        return new PageUtils(page);
    }

    @Override
    public void saveConfig(SysConfigEntity config) {
        this.save(config);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysConfigEntity config) {
        this.updateById(config);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateValueByKey(String key, String value) {
        baseMapper.updateValueByKey(key, value);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(Long[] ids) {
        this.removeByIds(Arrays.asList(ids));
    }

    @Override
    public String getValue(String key) {
        SysConfigEntity config = baseMapper.queryByKey(key);

        return config == null ? null : config.getParamValue();
    }

    @Override
	public SysConfigEntity getSysConfig(String key) {
		return baseMapper.queryByKey(key);
	}

    @Override
    public <T> T getConfigObject(String key, Class<T> clazz) throws JsonProcessingException {
        String value = getValue(key);
        if (StringUtils.isNotBlank(value)) {
            return MAPPER.readValue(value, clazz);
        }

        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new RRException("获取参数失败");
        }
    }
}

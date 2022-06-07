package com.github.ccphamy.modules.oss.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.ccphamy.modules.oss.dao.SysOssDao;
import com.github.ccphamy.modules.oss.entity.SysOssEntity;
import com.github.ccphamy.modules.oss.service.SysOssService;
import com.github.ccphamy.common.utils.PageUtils;
import com.github.ccphamy.common.utils.Query;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("sysOssService")
public class SysOssServiceImpl extends ServiceImpl<SysOssDao, SysOssEntity> implements SysOssService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysOssEntity> page = this.page(
            new Query<SysOssEntity>().getPage(params)
        );

        return new PageUtils(page);
    }

}

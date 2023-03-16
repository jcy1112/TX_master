package com.springboot.service.impl;

import com.springboot.entity.Lun;
import com.springboot.mapper.LunMapper;
import com.springboot.service.LunService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 *  服务实现类
 *
 * @author 文涛
 * @since 2023-03-07
 */
@Service
public class LunServiceImpl extends ServiceImpl<LunMapper, Lun> implements LunService {

}

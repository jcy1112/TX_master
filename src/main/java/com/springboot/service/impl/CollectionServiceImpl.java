package com.springboot.service.impl;

import com.springboot.entity.Collection;
import com.springboot.mapper.CollectionMapper;
import com.springboot.service.CollectionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 文涛
 * @since 2023-03-21
 */
@Service
public class CollectionServiceImpl extends ServiceImpl<CollectionMapper, Collection> implements CollectionService {

}

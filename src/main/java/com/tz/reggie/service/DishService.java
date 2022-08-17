package com.tz.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tz.reggie.dto.DishDto;
import com.tz.reggie.entity.Dish;

public interface DishService extends IService<Dish> {

    //新增菜品,同时插入菜品对应的口味数据，操作两张表
    public void saveWithFlavor(DishDto dishDto);

    public DishDto getByIdWithFlavor(Long id);

    public void updateWithFlavor(DishDto dishDto);
}

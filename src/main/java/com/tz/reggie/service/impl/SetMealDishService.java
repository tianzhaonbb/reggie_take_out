package com.tz.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tz.reggie.entity.SetmealDish;
import com.tz.reggie.mapper.SetMealDishMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SetMealDishService extends ServiceImpl<SetMealDishMapper, SetmealDish> implements com.tz.reggie.service.SetMealDishService {
}

package com.tz.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tz.reggie.common.CustomException;
import com.tz.reggie.dto.SetmealDto;
import com.tz.reggie.entity.Setmeal;
import com.tz.reggie.entity.SetmealDish;
import com.tz.reggie.mapper.SetMealMapper;
import com.tz.reggie.service.SetMealDishService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SetMealService extends ServiceImpl<SetMealMapper, Setmeal> implements com.tz.reggie.service.SetMealService {

    @Resource
    private SetMealDishService setMealDishService;

    @Override
    @Transactional
    public void savaWithDish(SetmealDto setmealDto) {
        //保存套餐基本信息
        this.save(setmealDto);
        //保存套餐和菜品的基本信息
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map((item->{
            item.setSetmealId(setmealDto.getId());
            return item;
        })).collect(Collectors.toList());

        setMealDishService.saveBatch(setmealDishes);

    }

    @Override
    @Transactional
    public void removeWithDish(List<Long> ids) {
        //查询套餐状态
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId,ids);
        queryWrapper.eq(Setmeal::getStatus,1);

        int count = this.count(queryWrapper);
        if(count > 0){
            throw new CustomException("套餐正在售卖，不能删除");
        }

        this.removeByIds(ids);

        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(SetmealDish::getSetmealId,ids);

        setMealDishService.remove(lambdaQueryWrapper);
    }
}

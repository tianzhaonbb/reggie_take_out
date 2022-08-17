package com.tz.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tz.reggie.common.CustomException;
import com.tz.reggie.entity.Category;
import com.tz.reggie.entity.Dish;
import com.tz.reggie.entity.Employee;
import com.tz.reggie.entity.Setmeal;
import com.tz.reggie.mapper.CategoryMapper;
import com.tz.reggie.mapper.EmployeeMapper;
import com.tz.reggie.service.DishService;
import com.tz.reggie.service.EmployeeService;
import com.tz.reggie.service.SetMealService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CategoryService extends ServiceImpl<CategoryMapper, Category> implements com.tz.reggie.service.CategoryService {
    /**
     * 根据id进行删除
     * @param id
     */
    @Resource
    private DishService dishService;
    @Resource
    private SetMealService setMealService;

    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //添加查询条件
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        int count1 = dishService.count(dishLambdaQueryWrapper);

        //查询当前是否关联了菜品，抛出一个异常
        if(count1 > 0){
            throw new CustomException("当前分类下，关联了菜品不能删除");
        }

        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        //查询当前是否关联了套餐，抛出一个异常
        int count2 = setMealService.count(setmealLambdaQueryWrapper);
        if(count2 > 0){
            throw new CustomException("当前分类下，关联了套餐不能删除");
        }
        //正常删除分类

        super.removeById(id);

    }
}

package com.tz.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tz.reggie.common.R;
import com.tz.reggie.dto.SetmealDto;
import com.tz.reggie.entity.Category;
import com.tz.reggie.entity.Setmeal;
import com.tz.reggie.service.CategoryService;
import com.tz.reggie.service.SetMealDishService;
import com.tz.reggie.service.SetMealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 套餐管理
 */
@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetMealController {

    @Resource
    private SetMealDishService setMealDishService;

    @Resource
    private SetMealService setMealService;

    @Resource
    private CategoryService categoryService;


    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto){
        log.info("套餐信息:{}",setmealDto);

        setMealService.savaWithDish(setmealDto);

        return null;
    }

    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){

        Page<Setmeal> pageInfo = new Page<>();
        Page<SetmealDto> dtoPage = new Page<>();

        BeanUtils.copyProperties(pageInfo,dtoPage,"records");
        List<Setmeal> records = pageInfo.getRecords();

        List<SetmealDto> list = records.stream().map((item->{
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item,setmealDto);

            Long categoryId = item.getCategoryId();

            Category category = categoryService.getById(categoryId);

            if(category != null){
                String name1 = category.getName();
                setmealDto.setCategoryName(name1);
            }
            return setmealDto;
        })).collect(Collectors.toList());


        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.like(name != null,Setmeal::getName,name);

        queryWrapper.orderByDesc(Setmeal::getUpdateTime);

        setMealService.page(pageInfo,queryWrapper);

        dtoPage.setRecords(list);
        return R.success(dtoPage);

    }

    /**
     * 删除套餐
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids){

        setMealService.removeWithDish(ids);

        return null;
    }

}

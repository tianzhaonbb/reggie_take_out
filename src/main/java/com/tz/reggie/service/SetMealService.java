package com.tz.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tz.reggie.dto.SetmealDto;
import com.tz.reggie.entity.Setmeal;

import java.util.List;

public interface SetMealService extends IService<Setmeal> {

    public void savaWithDish(SetmealDto setmealDto);

    public void removeWithDish(List<Long> ids);

}

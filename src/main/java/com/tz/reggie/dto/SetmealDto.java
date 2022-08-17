package com.tz.reggie.dto;

import com.tz.reggie.entity.Setmeal;
import com.tz.reggie.entity.SetmealDish;
import lombok.Data;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}

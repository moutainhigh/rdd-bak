package com.cqut.czb.bn.service.impl.food;

import com.cqut.czb.bn.dao.mapper.FileMapperExtra;
import com.cqut.czb.bn.dao.mapper.food.DishSystemMapperExtra;
import com.cqut.czb.bn.entity.dto.ManageFood.Food;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.entity.File;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.food.ManageSystemFoodService;
import com.cqut.czb.bn.service.impl.AnnouncementServiceImpl;
import com.cqut.czb.bn.util.file.FileUploadUtil;
import com.cqut.czb.bn.util.string.StringUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Service
public class ManageSystemFoodServiceImpl implements ManageSystemFoodService{
    @Autowired
    DishSystemMapperExtra mapper;

    @Autowired
    FileMapperExtra fileMapperExtra;

    @Autowired
    AnnouncementServiceImpl announcementServiceImpl;

    @Override
    public JSONResult add(Food food, MultipartFile file, User user) {

        String address = "";
        try{
//            if (file!=null||!file.isEmpty()) {
//                address = FileUploadUtil.putObject(file.getOriginalFilename(), file.getInputStream());//返回图片储存路径
//            }
//            File file1 = announcementServiceImpl.setFile(file.getOriginalFilename(),address,user.getUserId(),new Date());
//            fileMapperExtra.insertSelective(file1);
//            food.setFileId(file1.getFileId());
            food.setFileId("134491335612411288");
            food.setDishId(StringUtil.createId());
            food.setCreateAt(new Date());
            if(mapper.insert(food) > 0) {
                return new JSONResult("新增美食菜品成功",200);
            } else {
                return new JSONResult("新增美食菜品失败", 500);
            }
        } catch(Exception e) {
            return new JSONResult("图片上传失败",500);
        }
    }

    @Override
    public JSONResult delete(Food food) {
        fileMapperExtra.deleteByPrimaryKey(food.getFileId());
        if(mapper.delete(food.getDishId()) > 0) {
            return new JSONResult("删除美食菜品成功",200);
        } else {
            return new JSONResult("删除美食菜品失败", 500);
        }
    }

    @Override
    public JSONResult change(Food food, MultipartFile file, User user) {
        try{
            if (file!=null||!file.isEmpty()) {
                File file1 = fileMapperExtra.selectByPrimaryKey(food.getFileId());
                file1.setSavePath(FileUploadUtil.putObject(file.getOriginalFilename(),file.getInputStream()));
                file1.setFileName(file.getOriginalFilename());
                file1.setUploader(user.getUserId());
                file1.setUpdateAt(new Date());
                fileMapperExtra.updateByPrimaryKeySelective(file1);
            }
            if(mapper.edit(food) > 0) {
                return new JSONResult("修改美食菜品类型成功",200);
            } else {
                return new JSONResult("修改美食菜品类型失败", 500);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONResult("修改图片失败", 500);
        }
    }

    @Override
    public JSONResult changeWithoutImage(Food food) {
        if(mapper.edit(food) > 0)
            return new JSONResult("修改美食菜品类型成功",200);
        else
            return new JSONResult("修改美食菜品型失败", 500);
    }

    @Override
    public JSONResult search(Food food, PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize());
        Page<Food> standardPageInfo = mapper.search(food);

        return new JSONResult("查询美食菜品类型成功", 200, new PageInfo<>(standardPageInfo));
    }
}

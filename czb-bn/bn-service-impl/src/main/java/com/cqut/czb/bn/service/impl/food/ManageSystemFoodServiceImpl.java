package com.cqut.czb.bn.service.impl.food;

import com.cqut.czb.bn.dao.mapper.FileMapperExtra;
import com.cqut.czb.bn.dao.mapper.food.DishSystemMapperExtra;
import com.cqut.czb.bn.entity.dto.ManageFood.Food;
import com.cqut.czb.bn.entity.dto.ManageFood.SetInfo;
import com.cqut.czb.bn.entity.dto.ManageFood.ShopInfo;
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

import java.util.*;

@Service
public class ManageSystemFoodServiceImpl implements ManageSystemFoodService{
    @Autowired
    DishSystemMapperExtra mapper;

    @Autowired
    FileMapperExtra fileMapperExtra;

    @Autowired
    AnnouncementServiceImpl announcementServiceImpl;

    /**
     * 新增菜品
     * @param food
     * @param file
     * @param user
     * @return
     */
    @Override
    public JSONResult add(Food food, MultipartFile file, User user) {

        String address = "";
        try{
            //菜品图片处理
            if (file!=null||!file.isEmpty()) {
                address = FileUploadUtil.putObject(file.getOriginalFilename(), file.getInputStream());//返回图片储存路径
            }
            File file1 = announcementServiceImpl.setFile(file.getOriginalFilename(),address,user.getUserId(),new Date());
            fileMapperExtra.insertSelective(file1);
            food.setFileId(file1.getFileId());

            //是否有商店id，是否商店id为""，这里是区分菜品管理页面是否为管理员模式
            String shopId = "";
            if(null == food.getShopId() || food.getShopId().equals("")) {
                shopId = mapper.getShopId(user.getUserId());
            } else {
                shopId = food.getShopId();
            }

            String time = food.getSupplyTime().replace(",", "");

            //将供应时间排序
            Integer[] times = new Integer[time.length()];
            for(int i = 0; i < time.length(); i++) {
                times[i] = (int) time.charAt(i) - 48;
            }
            Arrays.sort(times);
            String timeResult = "";
            for(int i = 0; i < times.length; i++) {
                timeResult = timeResult + times[i].toString() + ",";
            }
            timeResult = timeResult.substring(0, timeResult.length() - 1);

            //设置菜品相关信息
            food.setShopId(shopId);
            food.setSupplyTime(timeResult);
            food.setDishId(StringUtil.createId());
            food.setCreateAt(new Date());

            // 如果是套餐，则插入套餐关系
            if(food.getDishType() == 1) {
                setAddOrEdit(food);
            }

            // 插入菜品
            if(mapper.insert(food) > 0) {
                return new JSONResult("新增美食菜品成功",200);
            } else {
                return new JSONResult("新增美食菜品失败", 500);
            }
        } catch(Exception e) {
            return new JSONResult("图片上传失败",500);
        }
    }

    /**
     * 删除菜品
     * @param food
     * @return
     */
    @Override
    public JSONResult delete(Food food) {
        //先删除图片文件
        fileMapperExtra.deleteByPrimaryKey(food.getFileId());
        //再删除菜品
        if(mapper.delete(food.getDishId()) > 0) {
            return new JSONResult("删除美食菜品成功",200);
        } else {
            return new JSONResult("删除美食菜品失败", 500);
        }
    }

    /**
     * 有图编辑菜品
     * @param food
     * @param file
     * @param user
     * @return
     */
    @Override
    public JSONResult change(Food food, MultipartFile file, User user) {
        // 如果是套餐，则插入套餐关系
        if(food.getDishType() == 1) {
            setAddOrEdit(food);
        }

        //是否有商店id，是否商店id为""，这里是区分菜品管理页面是否为管理员模式
        String shopId = "";
        if(null == food.getShopId() || food.getShopId().equals("")) {
            shopId = mapper.getShopId(user.getUserId());
        } else {
            shopId = food.getShopId();
        }

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

    /**
     * 无图编辑菜品
     * @param food
     * @return
     */
    @Override
    public JSONResult changeWithoutImage(Food food) {
        // 如果是套餐，则插入套餐关系
        if(food.getDishType() == 1) {
            setAddOrEdit(food);
        }

        if(mapper.edit(food) > 0)
            return new JSONResult("修改美食菜品类型成功",200);
        else
            return new JSONResult("修改美食菜品型失败", 500);
    }

    /**
     * 查询与编辑获取
     * @param food
     * @param pageDTO
     * @param user
     * @return
     */
    @Override
    public JSONResult search(Food food, PageDTO pageDTO, User user) {
        //是否有商店id，是否商店id为""，这里是区分菜品管理页面是否为管理员模式
        if(food.getShopId().equals("") || null == food.getShopId()) {
            food.setShopId(mapper.getShopId(user.getUserId()));
        }

        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize());
        Page<Food> standardPageInfo = mapper.search(food);

        return new JSONResult("查询美食菜品类型成功", 200, new PageInfo<>(standardPageInfo));
    }

    /**
     * 获取套餐信息
     * @param food
     * @return
     */
    @Override
    public JSONResult getSetInfo(Food food) {
        return new JSONResult(mapper.getSetInfo(food.getDishId()));
    }

    /**
     * 获取美食商店
     * @param pageDTO
     * @return
     */
    @Override
    public JSONResult getShops(ShopInfo shopInfo,PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize());
        Page<ShopInfo> infos = mapper.getShops(shopInfo);
        return new JSONResult("获取美食商店成功", 200, new PageInfo(infos));
    }

    /**
     * 新增编辑时，是否修改了套餐
     * @param food
     * @return
     */
    private boolean setAddOrEdit(Food food) {
        List<SetInfo> setInfoList = mapper.getSetInfo(food.getDishId());

        String[] dishIds = food.getDishIds().substring(0, food.getDishIds().length() - 1).split(",");

        List<SetInfo> insertList = new ArrayList<>();
        for(int i = 0; i < dishIds.length; i++) {
            SetInfo set = new SetInfo();
            set.setSetMealId(food.getDishId());
            set.setSingleId(dishIds[i]);
            set.setRelationId(StringUtil.createId());
            insertList.add(set);
        }

        // 比较前端传来的dishid和数据库获取的套餐菜品id是否有删除的
        List<SetInfo> deleteList = new ArrayList<>();
        for(int i = 0; i < setInfoList.size(); i++) {
            SetInfo oneSet = setInfoList.get(i);
            boolean isExist = false;
           for(int j = 0; j < dishIds.length; j++) {
               if(dishIds[j].equals(oneSet.getSetMealId())) {
                   isExist = true;
               }
           }
           if(!isExist) { // 有删除的则加入删除list
               SetInfo set = new SetInfo();
               set.setRelationId(oneSet.getRelationId());
               deleteList.add(set);
           } else { // 不是删除的，则比较是否是已有的
               for(int j = 0; j < insertList.size(); j++) {
                   if(insertList.get(j).getSingleId().equals(oneSet.getSingleId())) {
                       insertList.remove(j);
                   }
               }
           }
        }

        //先删除前端取消的套餐
        if(deleteList.size() != 0) {
            mapper.deleteList(deleteList);
        }

        //再插入新增的套餐菜品
        if(insertList.size() != 0) {
            mapper.insertSets(insertList);
        }
        return true;
    }
}

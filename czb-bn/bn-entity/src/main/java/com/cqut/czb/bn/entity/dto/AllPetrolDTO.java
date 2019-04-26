package com.cqut.czb.bn.entity.dto;

import com.cqut.czb.bn.entity.entity.Petrol;
import java.util.List;
import java.util.Map;


/**
 * 创建人：陈德强
 * 创建时间：2019/4/24
 * 作用：存储所用的油卡
 */
public class AllPetrolDTO {

    private static Map petrolMap;

    public AllPetrolDTO() {
    }

    public AllPetrolDTO(List<Petrol> petrolList) {
        changePetrolMap(petrolList);
    }

    public static Map getPetrolMap() {
        return petrolMap;
    }

    public static void setPetrolMap(Map petrolMap) {
        AllPetrolDTO.petrolMap = petrolMap;
    }

    /**
     * 更改map中的油卡
     * @param petrols
     */
    public static void changePetrolMap(List<Petrol> petrols) {
//        System.out.println(petrols.size()+petrols.get(0).getPetrolId()+petrols.get(0));
        for( int i = 0 ; i < petrols.size() ; i++) {//内部不锁定，效率最高，但在多线程要考虑并发操作的问题。
            petrolMap.put(petrols.get(i).getPetrolId(),petrols.get(i));
        }
    }

    /**
     * 随机获取油卡
     */


    /**
     * 移除油卡
     */

    /**
     * 放回油卡
     */


}

package com.cqut.czb.bn.entity.dto;

import com.cqut.czb.bn.entity.dto.appBuyPetrol.PetrolInputDTO;
import com.cqut.czb.bn.entity.entity.Petrol;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 创建人：陈德强
 * 创建时间：2019/4/24
 * 作用：存储所用的油卡
 */
public class AllPetrolDTO {
    //用于储存从数据库中读取的所有油卡
    private static Map<String,Petrol> petrolMap=new ConcurrentHashMap<String,Petrol>();

    //用于第二次读取的油卡
    private static Map<String,Petrol> currentPetrol=new ConcurrentHashMap<String,Petrol>();

//    privates

    /**
     * 无参构造
     */
    public AllPetrolDTO() {}

    public AllPetrolDTO( Map<String,Petrol> petrolMap){
        AllPetrolDTO.petrolMap=petrolMap;
    }


    public static void setPetrolMap(Map<String, Petrol> petrolMap) {
        AllPetrolDTO.petrolMap = petrolMap;
    }

    public static Map<String, Petrol> getPetrolMap() {
        return petrolMap;
    }

    public static Map<String, Petrol> getCurrentPetrol() {
        return currentPetrol;
    }

    public static void setCurrentPetrol(Map<String, Petrol> currentPetrol) {
        AllPetrolDTO.currentPetrol = currentPetrol;
    }

    /**
     * 更改map中的油卡
     * @param petrols
     */
    public static void changePetrolMap(List<Petrol> petrols) {
        AllPetrolDTO.clearPetrolMap();
        for( int i = 0 ; i < petrols.size() ; i++) {//内部不锁定，效率最高，但在多线程要考虑并发操作的问题。
            AllPetrolDTO.petrolMap.put(petrols.get(i).getPetrolId(),petrols.get(i));
        }
    }

    /**
     * 移除所有的油卡
     */
    public static void clearPetrolMap(){
        Iterator<Map.Entry<String,Petrol>> it = AllPetrolDTO.petrolMap.entrySet().iterator();
         while(it.hasNext()){
//             Map.Entry<String,Petrol> entry = it.next();
         it.remove();//使用迭代器的remove()方法删除元素
         }
    }

    /**
     * 随机获取油卡(包含移除油卡)
     * @param petrolInputDTO
     * @return
     */
    public static Petrol randomPetrol(PetrolInputDTO petrolInputDTO) {
        int petrolKind = petrolInputDTO.getPetrolKind();
        double petrolPrice = petrolInputDTO.getPetrolPrice();
        String ownerId = petrolInputDTO.getOwnerId();
        boolean isHave=false;//是否有油卡标识
        Petrol petrol=new Petrol(); //当前遍历的油卡值
        Iterator<Map.Entry<String, Petrol>> it = petrolMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Petrol> entry = it.next();
            petrol = entry.getValue();//当前遍历的油卡值
            if (petrol.getPetrolKind() == petrolKind && petrol.getPetrolPrice() == petrolPrice) {
                isHave=true;
                petrol.setOwnerId(ownerId);
                //将其放入当前暂存的一个map中
                currentPetrol.put(petrol.getPetrolId(),petrol);
                it.remove();
                break;
            }
        }
        if (isHave==false)
            return null;
        else
            return petrol;
    }

    /**
     * 放回一张卡
     */
    public static void putBackPetrol(Petrol petrol){
        petrolMap.put(petrol.getPetrolId(),petrol);
    }
}

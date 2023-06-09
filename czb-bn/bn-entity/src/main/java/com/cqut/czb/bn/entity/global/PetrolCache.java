package com.cqut.czb.bn.entity.global;

import com.cqut.czb.bn.entity.dto.appBuyPetrol.PetrolInputDTO;
import com.cqut.czb.bn.entity.entity.Petrol;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PetrolCache {
    //用于储存从数据库中读取的所有油卡(键值存的是卡号)
    public static Map<String, Petrol> AllpetrolMap = new ConcurrentHashMap<String, Petrol>();

    //用于第二次读取的油卡
    public static Map<String, Petrol> currentPetrolMap = new ConcurrentHashMap<String, Petrol>();

    /**
     * 传入一个油卡list
     * 先清空数据向AllPetrolMap中加入油卡队列
     *
     * @param petrols
     */
    public static void changeAllPetrolMap(List<Petrol> petrols) {
        clearPetrolMap("AllpetrolMap");//将所有的油卡都清空，再存如AllPetrolMap
        for (int i = 0; i < petrols.size(); i++) {//内部不锁定，效率最高，但在多线程要考虑并发操作的问题。
            AllpetrolMap.put(petrols.get(i).getPetrolNum(), petrols.get(i));
        }
    }

    /**
     * 传入一个油卡list
     * 不清空数据向AllPetrolMap中加入油卡队列
     *
     * @param petrols
     */
    public static void addAllPetrolMap(List<Petrol> petrols) {
        for (int i = 0; i < petrols.size(); i++) {//内部不锁定，效率最高，但在多线程要考虑并发操作的问题。
            if (petrols.get(i) != null) {
                if (isContainPetorlMap(AllpetrolMap, petrols.get(i).getPetrolNum()) == false)
                    AllpetrolMap.put(petrols.get(i).getPetrolNum(), petrols.get(i));
            }
        }
    }

    /**
     * 传入一个油卡list
     * 不清空数据向currentPetrolMap中加入油卡队列
     *
     * @param petrols
     */
    public static void addcurrentPetrolMap(List<Petrol> petrols) {
        for (int i = 0; i < petrols.size(); i++) {//内部不锁定，效率最高，但在多线程要考虑并发操作的问题。
            if (petrols.get(i) != null) {
                if (isContainPetorlMap(currentPetrolMap, petrols.get(i).getPetrolNum()) == false)
                    currentPetrolMap.put(petrols.get(i).getPetrolNum(), petrols.get(i));
            }
        }
    }


    /**
     * 判断是否包含某张油卡（油卡号是建值）
     *
     * @param petrolMap
     * @param petrolNum
     * @return
     */
    public static boolean isContainPetorlMap(Map<String, Petrol> petrolMap, String petrolNum) {
        //用containsKey()方法来判断是否存在某个key值
        if (petrolMap.containsKey(petrolNum)) {
            System.out.println("卡号为： " + petrolNum + "存在");
            return true;
        }
        System.out.println("卡号为： " + petrolNum + "不存在");
        return false;

    }


    /**
     * 移除所有的油卡
     */
    public static void clearPetrolMap(String mapName) {
        if (mapName.trim().equals("AllpetrolMap")) {
            Iterator<Map.Entry<String, Petrol>> it = AllpetrolMap.entrySet().iterator();
            while (it.hasNext()) {
                it.remove();//使用迭代器的remove()方法删除元素
            }
        } else if (mapName.trim().equals("currentPetrolMap")) {
            Iterator<Map.Entry<String, Petrol>> it = currentPetrolMap.entrySet().iterator();
            while (it.hasNext()) {
                it.remove();//使用迭代器的remove()方法删除元素
            }
        }
    }

    /**
     * 移除某张油卡
     * 根据传来的名字进行操作
     */
    public static int clearPetrol(String mapName, String petrolNum) {
        int result = 0;
        if (mapName.trim().equals("AllpetrolMap")) {
            Iterator<Map.Entry<String, Petrol>> it = AllpetrolMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Petrol> entry = it.next();
                String key = entry.getKey();
                if (key .equals(petrolNum)) {
                    System.out.println("delete this: " + key + ";");
                    it.remove();
                    result++;
                }
            }

        } else if (mapName.trim().equals("currentPetrolMap")) {
            Iterator<Map.Entry<String, Petrol>> it = currentPetrolMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Petrol> entry = it.next();
                String key = entry.getKey();
                if (key.equals(petrolNum)) {
                    System.out.println("delete this: " + key);
                    it.remove();
                    result++;
                }
            }

        }
        return result;
    }

    /**
     * 随机获取油卡(包含移除油卡)
     *
     * @param petrolInputDTO
     * @return
     */
    public static Petrol randomPetrol(PetrolInputDTO petrolInputDTO) {
        int petrolKind = petrolInputDTO.getPetrolKind();
        double petrolPrice = petrolInputDTO.getPetrolPrice();
        String area = petrolInputDTO.getArea();
//        String area = "河北省";
        String ownerId = petrolInputDTO.getOwnerId();
        Integer isSpecial = petrolInputDTO.getIsSpecial();
        String remark=petrolInputDTO.getRemark();
        Petrol petrol = new Petrol(); //当前遍历的油卡值
        Map<String, Petrol> P = AllpetrolMap;
        Iterator<Map.Entry<String, Petrol>> it = AllpetrolMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Petrol> entry = it.next();
            petrol = entry.getValue();//当前遍历的油卡值
            boolean choice=false;
            if(remark==null||"".equals(remark)||"通用".equals(remark)){
                choice=petrol.getPetrolKind() == petrolKind && petrol.getPetrolPrice() == petrolPrice &&
                        petrol.getArea().equals(area) && petrol.getIsSpecialPetrol().equals(isSpecial) &&
                        petrol.getRemark()==null;
                if(choice==false){
                    choice=petrol.getPetrolKind() == petrolKind && petrol.getPetrolPrice() == petrolPrice &&
                            petrol.getArea().equals(area) && petrol.getIsSpecialPetrol().equals(isSpecial) &&
                        petrol.getRemark().equals("通用");
                }
            }else {
                choice=petrol.getPetrolKind() == petrolKind && petrol.getPetrolPrice() == petrolPrice &&
                        petrol.getArea().equals(area) && petrol.getIsSpecialPetrol().equals(isSpecial) &&
                        remark.equals(petrol.getRemark());
            }
            if (choice) {
                petrol.setOwnerId(ownerId);
                //当前时间加十分钟
                long currentTime = System.currentTimeMillis() + 120 * 1000;
                petrol.setEndTime(currentTime);
                //将其放入当前暂存的一个currentPetrolMap中(可以覆盖以往的)。
                currentPetrolMap.put(petrol.getPetrolNum(), petrol);
//                AllpetrolMap.remove(petrol.getPetrolNum());
                it.remove();
                System.out.println("randomPetrol" + AllpetrolMap.size() + ":" + currentPetrolMap.size());
                System.out.println("petrol_num"+petrol.getPetrolNum());
                return petrol;
            }
        }
        return null;
    }

    /**
     * 放回一张卡
     */
    public static void putBackPetrol(String mapName, Petrol petrol) {
        if (mapName.trim() .equals("AllpetrolMap")) {
            if (isContainPetorlMap(AllpetrolMap, petrol.getPetrolNum()) != false) {
                AllpetrolMap.put(petrol.getPetrolNum(), petrol);
                System.out.println("all" + AllpetrolMap.size());
                System.out.println("current" + currentPetrolMap.size());
            }
        } else if (mapName.trim().equals("currentPetrolMap")) {
            if (isContainPetorlMap(currentPetrolMap, petrol.getPetrolNum()) != false) {
                currentPetrolMap.put(petrol.getPetrolNum(), petrol);
                System.out.println("all" + AllpetrolMap.size());
                System.out.println("current" + currentPetrolMap.size());
            }
        }

    }

    public static boolean isContainsNotPay(String userId) {
        Iterator<Map.Entry<String, Petrol>> currentPetrol = PetrolCache.currentPetrolMap.entrySet().iterator();
        while (currentPetrol.hasNext()) {
            Map.Entry<String, Petrol> entry = currentPetrol.next();
            String key = entry.getKey();
            Petrol petrol = PetrolCache.currentPetrolMap.get(key);
            if (petrol.getOwnerId() == null || petrol.getOwnerId().equals(userId)) {
                System.out.println("放回前"+ PetrolCache.AllpetrolMap.size()+ ":"+PetrolCache.currentPetrolMap.size());
                currentPetrol.remove();        //移除油卡
                System.out.println("存在未完成订单");
                petrol.setOwnerId("");//将用户id置为空
                petrol.setEndTime(0);
                PetrolCache.AllpetrolMap.put(petrol.getPetrolNum(),petrol);
                System.out.println("放会后"+ PetrolCache.AllpetrolMap.size()+ ":"+PetrolCache.currentPetrolMap.size());
                return false;
            }
        }
        System.out.println("无未完成订单");
        return true;
    }

}

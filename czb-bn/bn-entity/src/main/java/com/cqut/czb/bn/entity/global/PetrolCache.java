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
     * 向AllPetrolMap中加入油卡队列(清空数据);
     * @param petrols
     */
    public static void changeAllPetrolMap(List<Petrol> petrols) {
        clearPetrolMap(AllpetrolMap);//将所有的油卡都清空，再存如AllPetrolMap
        for( int i = 0 ; i < petrols.size() ; i++) {//内部不锁定，效率最高，但在多线程要考虑并发操作的问题。
            AllpetrolMap.put(petrols.get(i).getPetrolNum(),petrols.get(i));
        }
    }

    /**
     * 传入一个油卡list
     * 不清空数据向AllPetrolMap中加入油卡队列
     * @param petrols
     */
    public static void addAllPetrolMap(List<Petrol> petrols) {
        for( int i = 0 ; i < petrols.size() ; i++) {//内部不锁定，效率最高，但在多线程要考虑并发操作的问题。
            if(petrols.get(i)!=null)
            {
                if(isContainPetorlMap(AllpetrolMap,petrols.get(i).getPetrolNum())==false)
                    AllpetrolMap.put(petrols.get(i).getPetrolNum(),petrols.get(i));
            }
        }
    }

    /**
     * 传入一个油卡list
     * 不清空数据向currentPetrolMap中加入油卡队列
     * @param petrols
     */
    public static void addcurrentPetrolMap(List<Petrol> petrols) {
        for( int i = 0 ; i < petrols.size() ; i++) {//内部不锁定，效率最高，但在多线程要考虑并发操作的问题。
            if(petrols.get(i)!=null)
            {
                if(isContainPetorlMap(currentPetrolMap,petrols.get(i).getPetrolNum())==false)
                    currentPetrolMap.put(petrols.get(i).getPetrolNum(),petrols.get(i));
            }
        }
    }


    /**
     * 判断是否包含某张油卡（油卡号是建值）
     * @param petrolMap
     * @param petrolNum
     * @return
     */
    public static boolean isContainPetorlMap(Map<String,Petrol> petrolMap, String petrolNum){
        //用containsKey()方法来判断是否存在某个key值
        if(petrolMap.containsKey(petrolNum)){
            System.out.println("卡号为： "+petrolNum+"存在");
            return true;
        }
        System.out.println("卡号为： "+petrolNum+"不存在");
        return false;
    }


    /**
     * 移除所有的油卡
     */
    public static void clearPetrolMap(Map<String,Petrol> petrolMap){
        Iterator<Map.Entry<String,Petrol>> it = petrolMap.entrySet().iterator();
        while(it.hasNext()){
            it.remove();//使用迭代器的remove()方法删除元素
        }
    }

    /**
     * 移除某张油卡
     */
    public static void clearPetrol(Map<String,Petrol> petrolMap,String petrolNum){
        Iterator<Map.Entry<String,Petrol>> it = petrolMap.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String, Petrol> entry=it.next();
            String key=entry.getKey();
            if(key==petrolNum){
                System.out.println("delete this: "+key+" = "+key);
                it.remove();        //OK
            }
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
        boolean isHave=false;//是否有油卡
        Petrol petrol=new Petrol(); //当前遍历的油卡值
        Iterator<Map.Entry<String, Petrol>> it = AllpetrolMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Petrol> entry = it.next();
            petrol = entry.getValue();//当前遍历的油卡值
            if (petrol.getPetrolKind() == petrolKind && petrol.getPetrolPrice() == petrolPrice) {
                isHave=true;
                petrol.setOwnerId(ownerId);
                //当前时间加十分钟
                long currentTime = System.currentTimeMillis() + 60 * 1000;
                petrol.setEndTime(currentTime);
                //将其放入当前暂存的一个currentPetrolMap中,同时判断是否已经存在相应的卡
                if(isContainPetorlMap(currentPetrolMap,petrol.getPetrolNum())==false)
                {
                    currentPetrolMap.put(petrol.getPetrolNum(),petrol);
                    it.remove();
                    break;
                }
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
    public static void putBackPetrol(Map<String,Petrol> petrolMap,Petrol petrol){
        if(isContainPetorlMap(petrolMap,petrol.getPetrolNum())!=false)
        {
            petrolMap.put(petrol.getPetrolNum(),petrol);
            System.out.println(AllpetrolMap.get(petrol.getPetrolNum()));
            System.out.println(currentPetrolMap.get(petrol.getPetrolNum()));
        }
    }

    public static boolean isContainsNotPay(String userId){
        Iterator<Map.Entry<String,Petrol>> currentPetrol = PetrolCache.currentPetrolMap.entrySet().iterator();
        while(currentPetrol.hasNext()){
            Map.Entry<String, Petrol> entry=currentPetrol.next();
            String key=entry.getKey();
            Petrol petrol=PetrolCache.currentPetrolMap.get(key);
            if(petrol.getOwnerId()==null||petrol.getOwnerId()==userId){
                System.out.println("存在未完成订单");
                return false;
            }
        }
        System.out.println("无未完成订单");
        return true;
    }

}

package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.DictMapperExtra;
import com.cqut.czb.bn.dao.mapper.weChatSmallProgram.WeChatCommodityMapperExtra;
import com.cqut.czb.bn.entity.dto.WeChatCommodity.WCPCommodityInputDTO;
import com.cqut.czb.bn.entity.dto.WeChatCommodity.WCPCommodityOutputDTO;
import com.cqut.czb.bn.entity.entity.Dict;
import com.cqut.czb.bn.service.WCPCommodityInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description
 * @auther nihao
 * @create 2019-11-25 15:02
 */
@Service
public class WCPCommodityInfoServiceIpml implements WCPCommodityInfoService {

    @Autowired
    WeChatCommodityMapperExtra weChatCommodityMapperExtra;

    @Autowired
    DictMapperExtra dictMapperExtra;

    @Override
    public List<WCPCommodityOutputDTO> getCommodity(WCPCommodityInputDTO wcpCommodityInputDTO) {
        List<WCPCommodityOutputDTO> list = weChatCommodityMapperExtra.selectAllCommodityByArea(wcpCommodityInputDTO);
//        if(wcpCommodityInputDTO.getLatitude() != null && wcpCommodityInputDTO.getLatitude() != "" && wcpCommodityInputDTO.getLongitude() != "" && wcpCommodityInputDTO.getLongitude() != null) {
//            Double longtitude = Double.valueOf(wcpCommodityInputDTO.getLongitude());
//            Double latitude = Double.valueOf(wcpCommodityInputDTO.getLatitude());
//            for (WCPCommodityOutputDTO wcpCommodityOutputDTO : list) {
//                Double wcpLongtitude = Double.valueOf(wcpCommodityOutputDTO.getLongitude());
//                Double wcpLatitude = Double.valueOf(wcpCommodityOutputDTO.getLatitude());
//                Double distance = DistanceMeter.InputDistance(latitude, longtitude, wcpLatitude, wcpLongtitude);
//                //对距离的格式化
//                if(distance < 1000){
//                    wcpCommodityOutputDTO.setDistance(String.valueOf(distance) + "m");
//                }else if(distance > 1000){
//                    distance = distance / 1000;
//                    wcpCommodityOutputDTO.setDistance(String.format("%.1f",distance) + "km");
//                }
//            }
//        }
        return list;
    }

    @Override
    public WCPCommodityOutputDTO getOneCommodityById(WCPCommodityInputDTO wcpCommodityInputDTO) {
        WCPCommodityOutputDTO wcpCommodityOutputDTO = weChatCommodityMapperExtra.selectCommodityById(wcpCommodityInputDTO.getCommodityId());
//        if(wcpCommodityInputDTO.getLatitude() != null && wcpCommodityInputDTO.getLongitude() != null){
//            Double longtitude = Double.valueOf(wcpCommodityInputDTO.getLongitude());
//            Double latitude = Double.valueOf(wcpCommodityInputDTO.getLatitude());
//            Double wcpLongtitude = Double.valueOf(wcpCommodityOutputDTO.getLongitude());
//            Double wcpLatitude = Double.valueOf(wcpCommodityOutputDTO.getLatitude());
//            Double distance = DistanceMeter.InputDistance(latitude, longtitude, wcpLatitude, wcpLongtitude);
//            //对距离的格式化
//            if(distance < 1000){
//                wcpCommodityOutputDTO.setDistance(String.valueOf(distance) + "m");
//            }else if(distance > 1000){
//                distance = distance / 1000;
//                wcpCommodityOutputDTO.setDistance(String.format("%.1f",distance) + "km");
//            }
//        }
        Dict dict = dictMapperExtra.selectDictByName("sp_fy1");
        if(dict != null){
            Double num = Double.valueOf(dict.getContent()) * wcpCommodityOutputDTO.getTotalFyMoney();
            BigDecimal bd = new BigDecimal(num);
            wcpCommodityOutputDTO.setFyMoney((int)bd.setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue());
        }
        return wcpCommodityOutputDTO;

    }

    public static void main(String[] args){
        Double num = 69.56345;
        BigDecimal bd = new BigDecimal(num);
        num = bd.setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
        System.out.println(num);
    }

    @Override
    public String getAreas() {
        return weChatCommodityMapperExtra.getAreas();
    }
}
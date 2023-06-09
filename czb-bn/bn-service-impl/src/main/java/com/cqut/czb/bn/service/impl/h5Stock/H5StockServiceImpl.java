package com.cqut.czb.bn.service.impl.h5Stock;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cqut.czb.bn.dao.mapper.h5Stock.H5StockMapper;
import com.cqut.czb.bn.dao.mapper.h5Stock.H5StockMapperExtra;
import com.cqut.czb.bn.entity.dto.H5CommodityDTO;
import com.cqut.czb.bn.entity.dto.H5StockDTO;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WeChatCommodityOrderDTO;
import com.cqut.czb.bn.entity.dto.directChargingSystem.DirectChargingOrderDto;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.h5Stock.H5StockService;
import com.cqut.czb.bn.service.impl.WeChatSmallProgram.weChatOrderDelivery;
import com.cqut.czb.bn.util.string.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author Liyan
 */
@Service
public class H5StockServiceImpl implements H5StockService {

    @Autowired
    private H5StockMapper mapper;

    @Autowired
    private H5StockMapperExtra h5StockMapperExtra;

    @Override
    public JSONResult<List<H5StockDTO>> h5StockList(H5StockDTO h5StockDTO) {
        PageHelper.startPage(h5StockDTO.getCurrentPage(), h5StockDTO.getPageSize(),true);
        List<H5StockDTO> withdrawList = new ArrayList<>();
        if (h5StockDTO.getCommodityId() != null && !h5StockDTO.getCommodityId().equals("")) {
            withdrawList = mapper.h5StockList(h5StockDTO);
        } else {
            withdrawList = mapper.h5StockOrderList(h5StockDTO);
        }
        PageInfo<H5StockDTO> pageInfo = new PageInfo<>(withdrawList);
        return new JSONResult("列表数据查询成功", 200, pageInfo);
    }

    @Override
    public JSONResult<List<H5CommodityDTO>> h5CommodityList(H5CommodityDTO commodityDTO) {
        PageHelper.startPage(commodityDTO.getCurrentPage(), commodityDTO.getPageSize(),true);
        List<H5CommodityDTO> withdrawList = mapper.h5CommodityList(commodityDTO);
        PageInfo<H5CommodityDTO> pageInfo = new PageInfo<>(withdrawList);
        return new JSONResult("列表数据查询成功", 200, pageInfo);
    }

    @Override
    public List<Double> h5CommodityStockPriceGroup(String commodityId) {
        return mapper.h5CommodityStockPriceGroup(commodityId);
    }

    @Override
    public int h5ImportData(MultipartFile file, Integer recordType) throws IOException {
        InputStream inputStream = file.getInputStream();
        List<H5StockDTO> deliveryList = null;
        Map<String,H5StockDTO> deliveryMap = new HashMap<>();
        deliveryList = H5StockDelivery.readExcel(file.getOriginalFilename(), inputStream);
        String[] contentStr;
        String[] split;
        HashMap<String, String> map = new HashMap<>();
        if(deliveryList != null){
            for (H5StockDTO p : deliveryList){
                p.setStockId(StringUtil.createId());
                p.setState(0);
                p.setRecordType(recordType);
                p.setCreateAt(new Date());
                String priceId = h5StockMapperExtra.getPriceId(p.getPrice(),p.getCommodityId());
                if (priceId == null) {
                    p.setPriceId(StringUtil.createId());
                }else {
                    p.setPriceId(priceId);
                }
                contentStr = p.getContent().split(",");
                for(int n = 0; n < contentStr.length; n++) {
                    split = contentStr[n].split(":",2);
                    map.put(split[0], split[1]);
                }
                p.setContent(JSONObject.toJSONString(map));
                deliveryMap.put(p.getStockId(),p);
            }
        }
        List<H5StockDTO> deliveryListNoRepeat = new ArrayList<>();
        for (H5StockDTO p : deliveryMap.values()){
            System.out.println("打印测试"+p);
            deliveryListNoRepeat.add(p);
        }
        int countForInsert = h5StockMapperExtra.importData(deliveryListNoRepeat);
        return countForInsert;
    }

    @Override
    public List<H5StockDTO> getCommodity() {
        return mapper.getCommodity();
    }

    @Override
    public JSONResult getTotalConsumption(H5StockDTO h5StockDTO) {
        Map<String, Double> map = new HashMap<>();
        map.put("totalConsumption",mapper.getTotalConsumption(h5StockDTO));
        h5StockDTO.setRecordType(1);
        map.put("moveTotalConsumption",mapper.getTotalConsumption(h5StockDTO));
        h5StockDTO.setRecordType(2);
        map.put("oilTotalConsumption",mapper.getTotalConsumption(h5StockDTO));
        return new JSONResult(map);
    }

    @Override
    public boolean dropCommodityById(String commodityId) {
        return h5StockMapperExtra.dropCommodityById(commodityId) != 0;
    }
}

package com.cqut.czb.bn.entity.global;

import com.cqut.czb.bn.entity.dto.role.ElTreeDTO;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 生成Vue插件ElTree所需数据
 * 作者：杨强
 * 时间：2018-04-14
 * */
public class GetElTree {
    /**
     * 转换为ElTree插件所需数据
     * @param res
     * @return
     */
    public static List<ElTreeDTO> getTree(List<ElTreeDTO> res) {
        List<ElTreeDTO> result = new LinkedList<>();
        for(int i = 0 ; i < res.size() ; i++){
            if(res.get(i).getParentId() == null){
                setChild(res, res.get(i));
                result.add(res.get(i));
            }
        }
        return result;
    }

    private static void setChild(List<ElTreeDTO> res, ElTreeDTO currentNode){
        List<ElTreeDTO> child = new ArrayList<>();
        for(int i = 0 ; i < res.size() ; i++){
            if(res.get(i).getParentId() != null && ((String)res.get(i).getParentId()).equals(currentNode.getId())){
                setChild(res, res.get(i));
                child.add(res.get(i));
            }
        }
        if(child.size() != 0){
            currentNode.setChildren(child);
        }
    }
}

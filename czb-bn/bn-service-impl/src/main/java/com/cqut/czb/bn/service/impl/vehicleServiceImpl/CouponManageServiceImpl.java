package com.cqut.czb.bn.service.impl.vehicleServiceImpl;

import com.cqut.czb.bn.dao.mapper.UserMapperExtra;
import com.cqut.czb.bn.dao.mapper.vehicleService.CouponStandardMapper;
import com.cqut.czb.bn.dao.mapper.vehicleService.CouponStandardMapperExtra;
import com.cqut.czb.bn.dao.mapper.vehicleService.ServerCouponMapper;
import com.cqut.czb.bn.dao.mapper.vehicleService.ServerCouponMapperExtra;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.user.UserDTO;
import com.cqut.czb.bn.entity.dto.user.UserInputDTO;
import com.cqut.czb.bn.entity.dto.vehicleService.IssueServerCouponDTO;
import com.cqut.czb.bn.entity.dto.vehicleService.ServerCouponDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.entity.vehicleService.CouponStandard;
import com.cqut.czb.bn.entity.entity.vehicleService.ServerCoupon;
import com.cqut.czb.bn.service.vehicleService.CouponManageService;
import com.cqut.czb.bn.util.string.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CouponManageServiceImpl implements CouponManageService {

    @Autowired
    ServerCouponMapperExtra serverCouponMapperExtra;
    @Autowired
    CouponStandardMapperExtra couponStandardMapperExtra;
    @Autowired
    UserMapperExtra userMapperExtra;

    @Override
    public List<ServerCouponDTO> getCouponList(ServerCouponDTO serverCouponDTO,User user) {
        serverCouponDTO.setOwnerId(user.getUserId());
        isExpire(serverCouponDTO);
        List<ServerCouponDTO> serverCouponDTOList = serverCouponMapperExtra.selectByPrimaryKey(serverCouponDTO);
        return serverCouponDTOList;
    }

    public List<ServerCouponDTO> getUseCouponList(ServerCouponDTO serverCouponDTO,User user) {
        serverCouponDTO.setOwnerId(user.getUserId());
        isExpire(serverCouponDTO);
        List<ServerCouponDTO> serverCouponDTOList = serverCouponMapperExtra.appSelectByGroup(serverCouponDTO);
        if (serverCouponDTOList!=null && serverCouponDTOList.size()!=0) {
            for (int i = 0; i < serverCouponDTOList.size(); i++) {
                serverCouponDTOList.get(i).setCouponId(serverCouponDTOList.get(i).getCouponId().split(",")[0]);
            }
        }
        return serverCouponDTOList;
    }

    //更新已过期的优惠券
    public Boolean isExpire(ServerCouponDTO serverCouponDTO){
//        if (serverCouponDTO.getOwnerId()==null || "".equals(serverCouponDTO.getOwnerId())){
//            return null;
//        }
        serverCouponDTO.setUpdateAt(new Date());
        return serverCouponMapperExtra.updateExpire(serverCouponDTO)>0;
    }

    @Override
    public PageInfo<CouponStandard> getAllCoupon(CouponStandard couponStandard,PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(),pageDTO.getPageSize());
        return new PageInfo<>(couponStandardMapperExtra.selectByPrimaryKey(couponStandard));
    }

    @Override
    public Boolean insertCouponStandard(CouponStandard couponStandard) {
        couponStandard.setCreateAt(new Date());
        couponStandard.setStandardId(StringUtil.createId());
        return couponStandardMapperExtra.insert(couponStandard)>0;
    }

    @Override
    public Boolean updateCouponStandard(CouponStandard couponStandard) {
        couponStandard.setUpdateAt(new Date());
        return couponStandardMapperExtra.updateByPrimaryKeySelective(couponStandard)>0;
    }

    @Override
    public Boolean deleteCouponStandard(CouponStandard couponStandard) {
        couponStandard.setUpdateAt(new Date());
        return couponStandardMapperExtra.updateByDelete(couponStandard)>0;
    }

    @Override
    public List<CouponStandard> getCouponType() {
        return couponStandardMapperExtra.selectCouponStandardType();
    }

    @Override
    public PageInfo<ServerCouponDTO> getCouponByUser(ServerCouponDTO serverCouponDTO,PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(),pageDTO.getPageSize());
        isExpire(serverCouponDTO);
        return new PageInfo<>(serverCouponMapperExtra.selectByPrimaryKey(serverCouponDTO));
    }

    //计算优惠券过期时间
    public Date compute(Integer continueDay) {
        Date destroyTime = null;
        try {
            DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
            Date day=new Date();
            String str=format.format(day);
            Date now=format.parse(str);
            long curMilliNow = now.getTime();
            int dayMis=1000*60*60*24;//一天的毫秒
            long resultMis=curMilliNow+(dayMis-1000);
            Date startTime = new Date(resultMis);
            destroyTime = DateUtils.addDays(startTime, continueDay);   //将过期时间变成一天的最后一毫秒
        }catch (Exception e){
            e.printStackTrace();
        }
        return destroyTime;
    }

    //给推荐人发三张优惠券
    public Boolean issueCouponToPartner(User user) {
        User partner = userMapperExtra.selectByPrimaryKey(user.getUserId());
        if (partner!=null&&partner.getSuperiorUser()!=null&&partner.getSuperiorUser()!=""){
            List<IssueServerCouponDTO> insert = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                IssueServerCouponDTO exp = new IssueServerCouponDTO();
                exp.setOwnerId(partner.getSuperiorUser());
                exp.setCouponId(StringUtil.createId());
                exp.setCouponStandard(String.valueOf(i+1));
                CouponStandard search = new CouponStandard();      //计算过期时间
                search.setStandardId(String.valueOf(i+1));
                List<CouponStandard> couponStandard = couponStandardMapperExtra.selectByPrimaryKey(search);
                if (couponStandard==null||couponStandard.size()==0||couponStandard.get(0).getContinueDays()==null){
                    return false;
                }else {
                    exp.setDestroyTime(compute(couponStandard.get(0).getContinueDays()));
                }
                exp.setStatus(0);
                insert.add(exp);
            }
            return serverCouponMapperExtra.insertByList(insert)>0;
        }
       return false;
    }

    @Override
    public Boolean issueCoupon(IssueServerCouponDTO issueServerCouponDTO) {
        if (issueServerCouponDTO==null||issueServerCouponDTO.getType()==null){
            return false;
        }
        CouponStandard search = new CouponStandard();      //计算过期时间
        search.setStandardId(issueServerCouponDTO.getStandardType());
        List<CouponStandard> couponStandard = couponStandardMapperExtra.selectByPrimaryKey(search);
        if (couponStandard==null||couponStandard.size()==0||couponStandard.get(0).getContinueDays()==null){
            return false;
        }else {
            issueServerCouponDTO.setDestroyTime(compute(couponStandard.get(0).getContinueDays()));
        }
        if (issueServerCouponDTO.getType()==0){   //如果是单个发放
            issueServerCouponDTO.setCouponId(StringUtil.createId());
            User user = userMapperExtra.findUserByAccount(issueServerCouponDTO.getUserAccount());
            if (user==null||user.getUserId()==null||"".equals(user.getUserId())){
                return false;
            }else {
                issueServerCouponDTO.setOwnerId(user.getUserId());
            }
            issueServerCouponDTO.setCouponStandard(issueServerCouponDTO.getStandardType());
            issueServerCouponDTO.setStatus(0);
            issueServerCouponDTO.setCreateAt(new Date());
            issueServerCouponDTO.setUpdateAt(new Date());
            return serverCouponMapperExtra.insert(issueServerCouponDTO)>0;
        } else if (issueServerCouponDTO.getType()==1){   //如果是批量发放
            UserInputDTO input = new UserInputDTO();
            input.setPartner(issueServerCouponDTO.getPartner());
            List<User> userDTOList = userMapperExtra.selectByPartner(input);
            if (userDTOList!=null&&userDTOList.size()!=0) {  //如果有发放目标
                List<IssueServerCouponDTO> insert = new ArrayList<>();
                for (int i = 0; i < userDTOList.size(); i++) {
                    IssueServerCouponDTO exp = new IssueServerCouponDTO();
                    exp.setOwnerId(userDTOList.get(i).getUserId());
                    exp.setCouponId(StringUtil.createId());
                    exp.setCouponStandard(issueServerCouponDTO.getStandardType());
                    exp.setDestroyTime(issueServerCouponDTO.getDestroyTime());
                    exp.setStatus(0);
                    insert.add(exp);
                }
                Boolean isInsert = serverCouponMapperExtra.insertByList(insert) > 0;
                return isInsert;
            }else {
                return null;
            }
        }else {
            return false;
        }
    }
}

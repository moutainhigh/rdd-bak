package com.cqut.czb.bn.entity.dto.appBuyPetrol;

import com.cqut.czb.bn.entity.entity.Petrol;

import java.util.Date;

public class PetrolInfo {

    private Petrol petrol;

    public Petrol getPetrol() {
        return petrol;
    }

    public void setPetrol(Petrol petrol) {
        this.petrol = petrol;
    }

    public static Petrol getNewPetrol(Petrol petrol){
        Petrol petrol1=new Petrol();
        petrol1.setPetrolPrice(petrol.getPetrolPrice());
        petrol1.setDiscount(petrol.getDiscount());
        petrol1.setRemark(petrol.getRemark());
        petrol1.setOwnerId(petrol.getPetrolId());
        petrol1.setOwnerId(petrol.getOwnerId());
        petrol1.setPetrolNum(petrol.getPetrolNum());
        petrol1.setArea(petrol.getArea());
        petrol1.setPetrolType(petrol.getPetrolType());
        petrol1.setPetrolDenomination(petrol.getPetrolDenomination());
        petrol1.setPetrolKind(petrol.getPetrolKind());
        petrol1.setPetrolPsw(petrol.getPetrolPsw());
        return petrol1;
    }
}

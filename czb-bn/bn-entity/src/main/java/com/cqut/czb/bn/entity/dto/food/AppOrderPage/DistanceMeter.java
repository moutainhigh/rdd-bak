package com.cqut.czb.bn.entity.dto.food.AppOrderPage;

import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GeodeticCurve;
import org.gavaghan.geodesy.GlobalCoordinates;

//测算距离
public class DistanceMeter {

    public static double GetDistanceMeter(GlobalCoordinates gpsFrom, GlobalCoordinates gpsTo, Ellipsoid ellipsoid)
    {
        //创建GeodeticCalculator，调用计算方法，传入坐标系、经纬度用于计算距离
        GeodeticCurve geoCurve = new GeodeticCalculator().calculateGeodeticCurve(ellipsoid, gpsFrom, gpsTo);

        return geoCurve.getEllipsoidalDistance();
    }

    public static double InputDistance(double lat1, double long1,double lat2, double long2)
    {

        GlobalCoordinates source = new GlobalCoordinates(lat1,long1);
        GlobalCoordinates target = new GlobalCoordinates(lat2,long2);

        double meter1 = GetDistanceMeter(source, target, Ellipsoid.Sphere);

        return meter1;
    }

}

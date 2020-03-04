package com.vn.babumart.models;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 13-January-2020
 * Time: 11:02
 * Version: 1.0
 */
public class FeeShipGHNObj {
    String ErrorMessage;
    String CalculatedFee;
    String ServiceFee;
    String CoDFee;
    String DiscountFee;
    String WeightDimension;

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }

    public String getCalculatedFee() {
        return CalculatedFee;
    }

    public void setCalculatedFee(String calculatedFee) {
        CalculatedFee = calculatedFee;
    }

    public String getServiceFee() {
        return ServiceFee;
    }

    public void setServiceFee(String serviceFee) {
        ServiceFee = serviceFee;
    }

    public String getCoDFee() {
        return CoDFee;
    }

    public void setCoDFee(String coDFee) {
        CoDFee = coDFee;
    }

    public String getDiscountFee() {
        return DiscountFee;
    }

    public void setDiscountFee(String discountFee) {
        DiscountFee = discountFee;
    }

    public String getWeightDimension() {
        return WeightDimension;
    }

    public void setWeightDimension(String weightDimension) {
        WeightDimension = weightDimension;
    }
}

package com.ekomuliyo.myunittesting;

public class MainViewModel {
    private final CuboiModel cuboiModel;

    MainViewModel(CuboiModel cuboiModel){
        this.cuboiModel = cuboiModel;
    }

    void save(double l, double w, double h){
        cuboiModel.save(l,w,h);
    }

    double getCircumreference(){
        return cuboiModel.getCircumference();
    }

    double getSurfaceArea(){
        return cuboiModel.getSurfaceArea();
    }

    double getVolume(){
        return cuboiModel.getVolume();
    }
}

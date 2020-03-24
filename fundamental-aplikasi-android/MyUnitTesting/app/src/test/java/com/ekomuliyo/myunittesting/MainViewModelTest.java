package com.ekomuliyo.myunittesting;

import android.util.Log;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MainViewModelTest {

    private MainViewModel mainViewModel;
    private CuboiModel cuboiModel;

    private final double dummyLength = 12.0;
    private final double dummyWidth = 7.0;
    private final double dummyHeight = 6.0;

    private final double dummyVolume = 504.0;
    private final double dummyCircumreference = 2016.0;
    private final double dummySurfaceArea = 396.0;

    @Before
    public void before(){
        cuboiModel = mock(CuboiModel.class);
        mainViewModel = new MainViewModel(cuboiModel);
    }

    @Test
    public void testVolume() {
        cuboiModel = new CuboiModel();
        mainViewModel = new MainViewModel(cuboiModel);
        mainViewModel.save(dummyWidth, dummyLength, dummyHeight);
        double volume = mainViewModel.getVolume();
        assertEquals(dummyVolume, volume, 0.0001);
    }

    @Test
    public void testCircumreference() {
        cuboiModel = new CuboiModel();
        mainViewModel = new MainViewModel(cuboiModel);
        mainViewModel.save(dummyWidth, dummyLength, dummyHeight);
        double circumreference = mainViewModel.getCircumreference();
        assertEquals(dummyCircumreference, circumreference, 0.0001);
    }

    @Test
    public void testSurfaceArea() {
        cuboiModel = new CuboiModel();
        mainViewModel = new MainViewModel(cuboiModel);
        mainViewModel.save(dummyWidth, dummyLength, dummyHeight);
        double surfacearea = mainViewModel.getSurfaceArea();
        assertEquals(dummySurfaceArea, surfacearea, 0.0001);
    }


    // melakukan pengujian menggunakan mock dari JUnit
    @Test
    public void testMockVolume(){
        when(mainViewModel.getVolume()).thenReturn(dummyVolume); // method getVolume() langsung mengembalikan nilai di parameter thenReturn(nilai kembalian nya)
        double volume = mainViewModel.getVolume(); // mengambil nilai nya
        verify(cuboiModel).getVolume(); // membuat object/fungsi baru dari aslinya, sehingga tidak mempengaruhi object/fungsi aslinya saat di testing karna nilai kembali default atau 0
        assertEquals(dummyVolume, volume, 0.0001);
    }

    @Test
    public void testMockCircumference(){
        when(mainViewModel.getCircumreference()).thenReturn(dummyCircumreference);
        double circumference = mainViewModel.getCircumreference();
        verify(cuboiModel).getCircumference();
        assertEquals(dummyCircumreference, circumference, 0.0001);
    }

    @Test
    public void testMockSurface(){
        when(mainViewModel.getSurfaceArea()).thenReturn(dummySurfaceArea);
        double surface = mainViewModel.getSurfaceArea();
        verify(cuboiModel).getSurfaceArea();
        assertEquals(dummySurfaceArea, surface, 0.0001);
    }

}
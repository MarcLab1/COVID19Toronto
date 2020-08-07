package com.covid19toronto.ui;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;

public class MyViewModel extends ViewModel {
    private MutableLiveData<String> FSA;
    private MutableLiveData <List<String>> FSAList;

    public MyViewModel() {
        FSA = new MutableLiveData<>();
        FSAList = new MutableLiveData<>();
    }
    public MutableLiveData<String> getFSA() {
        if (FSA == null) {
            FSA = new MutableLiveData<String>();
        }
        return FSA;
    }

    public MutableLiveData<List<String>> getFSAList() {
        if (FSAList == null) {
            FSAList = new MutableLiveData<List<String>>();
        }
        return FSAList;
    }

    public void setDefaultFSA()
    {
        FSA.setValue(FSAList.getValue().get(0).toString());
    }

}




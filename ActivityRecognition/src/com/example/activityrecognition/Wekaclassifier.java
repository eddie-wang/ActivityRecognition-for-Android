package com.example.activityrecognition;

import java.io.IOException;
import java.io.InputStream;

import android.util.Log;

import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.Instances;
import weka.core.converters.ArffLoader;


/**
 * @author Jia Yu
 * @date 2010-6-9
 */
public class Wekaclassifier {

    /**
     * @param args
     */
    private ArffLoader loader;
    private Instances dataSet;
    private  InputStream arffFile;
    private int sizeOfDataset;
    private Classifier classifier;
    private int sizeOfAttribute;
    private String resultClass;
    private double[] distributions;

    public Wekaclassifier(InputStream in) throws Exception {
    	this.arffFile = in; 
        loadTrainSet();
        loadClassifier();
        buildClassifierModel();
    }

    private void buildClassifierModel() throws Exception {
        this.classifier.buildClassifier(dataSet);
    }

    private void loadClassifier() {
        this.classifier = new J48();
    }

    private void loadTrainSet() throws IOException {
        loader = new ArffLoader();
        loader.setSource(this.arffFile);
        dataSet = loader.getDataSet();
        setSizeOfDataset(dataSet.numInstances());
        setSizeOfAttribute(dataSet.numAttributes());
        dataSet.setClassIndex(this.sizeOfAttribute - 1);
    }

    public void classifyInstance(weka.core.Instance instance) throws Exception {
        double tNum = this.classifier.classifyInstance(instance);
        setDistributions(this.classifier.distributionForInstance(instance));
        Attribute attr = dataSet.attribute(dataSet.classIndex());
        int classIndex = (int) tNum;
        setResultClass(attr.value(classIndex));
    }
    
    public  String getResult(float features[]) throws Exception {
    	Log.v("wang","dddd");
           weka.core.Instance ins = new weka.core.Instance(this.getSizeOfAttribute());
            ins.setDataset(this.getDataSet());
           
            for (int i = 0; i < ins.numAttributes() - 1; i++) 
              {  ins.setValue(i, features[i]);
                 Log.v("wang","features"+i+" :"+features[i]);
              }
                // System.out.println(ins.attribute(i).getLowerNumericBound());
            Log.v("wang","eeee");
            //ins.setValue(ins.numAttributes() - 1, "walk");
          
            this.classifyInstance(ins);
            Log.v("wang",getResultClass());
            return getResultClass();
        
    }

    public int getSizeOfAttribute() {
           return sizeOfAttribute;
    }

    public void setSizeOfAttribute(int sizeOfAttribute) {
        this.sizeOfAttribute = sizeOfAttribute;
    }

    public Instances getDataSet() {
        return dataSet;
    }

    public void setDataSet(Instances dataSet) {
        this.dataSet = dataSet;
    }

    public String getResultClass() {
        return resultClass;
    }

    public void setResultClass(String resultClass) {
        this.resultClass = resultClass;
    }

    public void setDistributions(double[] distributions) {
        this.distributions = distributions;
    }

    public double[] getDistributions() {
        return distributions;
    }

    public void setSizeOfDataset(int sizeOfDataset) {
        this.sizeOfDataset = sizeOfDataset;
    }

    public int getSizeOfDataset() {
        return sizeOfDataset;
    }
}

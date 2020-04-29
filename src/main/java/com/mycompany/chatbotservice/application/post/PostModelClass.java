package com.mycompany.chatbotservice.application.post;

import com.mycompany.chatbotservice.application.pojo.Question;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import weka.classifiers.meta.FilteredClassifier;
import weka.core.Instances;

@Path("/postModel")
public class PostModelClass {
    //Burada diğer algortimalarla karşılaştırabilmek için algoritmalardan dönen response indislerini tutcaz.
    double[] rsp = new double[3];

    String filePath;
    String categoryFilePath;
    String response;

    ClassLoader classLoader;

    @POST //İşlem tipi
    @Produces(MediaType.TEXT_PLAIN) //Döndürülen veri tipi
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/model")
    public String convertBasic(Question question) throws Exception {

        classLoader = new PostModelClass().getClass().getClassLoader();
        categoryFilePath = getCategoryFileName();
        filePath = getFileName();

        System.out.println("PostModelClass");

        response = findCategory(question);

        return response;
    }

    public String findClass(Question question, String categoryName) throws Exception {
        System.out.println("findClass");
        //POST ile gönderilen JSON verisini weka'daki algoritmalara yerleştirmek için arff'e çevirilir.
        try {
            System.out.println("file name --> " + getFileName());
            System.out.println("category name --> " +categoryName);

            FileWriter arff = new FileWriter(filePath);

            System.out.println(question.getQuestion());

            arff.append("@relation question\n");
            arff.append("@attribute question string\n");
            if (categoryName.equalsIgnoreCase("c1")) {
                arff.append(
                        "@attribute class { rsp1, rsp2, rsp19, rsp20, rsp24, rsp25, rsp26, rsp27, rsp28, rsp43, rsp44, rsp45, rsp46, rsp47, rsp52, rsp53, rsp54, rsp55, rsp56, rsp62, rsp63, rsp64, rsp65, rsp66, rsp68, rsp73, rsp75, rsp76, rsp77, rsp88, rsp89, rsp107, rsp119, rsp120 }\n"
                );
            }
            else if(categoryName.equalsIgnoreCase("c2")){
                arff.append(
                        "@attribute class {rsp3, rsp4, rsp5, rsp41}\n"
                );
            }
            else if(categoryName.equalsIgnoreCase("c3")){
                arff.append(
                        "@attribute class {rsp6, rsp7}\n");
            }
            else if(categoryName.equalsIgnoreCase("c4")){
                arff.append(
                        "@attribute class {rsp8, rsp9, rsp10, rsp11, rsp34, rsp35}\n"
                );
            }
            else if(categoryName.equalsIgnoreCase("c5")){
                arff.append(
                        "@attribute class {rsp12, rsp47, rsp81, rsp82, rsp87, rsp94, rsp113, rsp121}\n"
                );
            }
            else if(categoryName.equalsIgnoreCase("c6")){
                arff.append(
                        "@attribute class {rsp13, rsp14, rsp15, rsp16, rsp17, rsp18, rsp19, rsp20, rsp21, rsp42, rsp57, rsp103, rsp104, rsp105, rsp106, rsp107, rsp108, rsp109}\n"
                );
            }
            else if(categoryName.equalsIgnoreCase("c7")){
                arff.append(
                        "@attribute class { rsp22, rsp23, rsp26, rsp27, rsp28, rsp29, rsp30, rsp31, rsp32, rsp33, rsp34, rsp35, rsp36, rsp37, rsp38, rsp48, rsp49, rsp58, rsp59, rsp60, rsp61, rsp66, rsp67, rsp69, rsp70, rsp71, rsp72, rsp74, rsp78, rsp79, rsp80, rsp83, rsp85, rsp86, rsp90, rsp91, rsp101, rsp110, rsp111, rsp112, rsp113, rsp115, rsp116, rsp117, rsp118, rsp122, rsp123, rsp124, rsp125, rsp126, rsp129}\n"
                );
            }
            else if(categoryName.equalsIgnoreCase("c8")){
                arff.append(
                        "@attribute class {rsp39, rsp40, rsp84, rsp92, rsp93, rsp127, rsp128, rsp129, rsp130, rsp131, rsp132 }\n"
                );
            }
            else if(categoryName.equalsIgnoreCase("c9")){
                arff.append(
                        "@attribute class {rsp50, rsp51, rsp95, rsp96, rsp97, rsp98, rsp99, rsp100, rsp108}\n"
                );
            }
            else{
                arff.append(
                        "@attribute class { rsp1, rsp2, rsp3, rsp4, rsp5, rsp6, rsp7, rsp8, rsp9, rsp10, rsp11, rsp12, rsp13 ,rsp14, rsp15, rsp16, rsp17, rsp18, rsp19, rsp20, rsp21, rsp22, rsp23, rsp24, rsp25, rsp26, rsp27, rsp28 ,rsp29, rsp30, rsp31, rsp32, rsp33, rsp34, rsp35, rsp36, rsp37, rsp38, rsp39, rsp40, rsp41, rsp42, rsp43, rsp44, rsp45, rsp46, rsp47, rsp48, rsp49, rsp50, rsp51, rsp52, rsp53, rsp54, rsp55, rsp56, rsp57, rsp58, rsp59, rsp60, rsp61, rsp62, rsp63, rsp64, rsp65, rsp66, rsp67, rsp68, rsp69, rsp70, rsp71, rsp72, rsp73, rsp74, rsp75, rsp76, rsp77, rsp78, rsp79, rsp80, rsp81, rsp82, rsp83, rsp84, rsp85, rsp86, rsp87, rsp88, rsp89, rsp90, rsp91, rsp92, rsp93, rsp94, rsp95, rsp96, rsp97, rsp98, rsp99, rsp100 ,rsp101, rsp103, rsp104, rsp105, rsp106, rsp107, rsp108, rsp109, rsp110, rsp111, rsp112, rsp113, rsp115, rsp116, rsp117, rsp118}\n"
                );
            }
            arff.append("@data\n");
            arff.append("'" + question.getQuestion() + "'" + ",");
            arff.append("?");

            arff.flush();
            arff.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Model yüklendi
        //Model yüklendi
        System.out.println("modeller yükleniyor");

        String J48ModelPath;
        String BayesModelPath;
        String IbkModelPath;

        if(categoryName.equalsIgnoreCase("c1")){
            System.out.println("c1 Model yükle");
            J48ModelPath = "models/subModels/c1/J48/c1-j48.model";
            BayesModelPath = "models/subModels/c1/NaiveBayes/c1-bayes.model";
            IbkModelPath = "models/subModels/c1/IBK/c1-IBK.model";
        }
        else if(categoryName.equalsIgnoreCase("c2")){
            System.out.println("c2 Model yükle");
            J48ModelPath = "models/subModels/c2/J48/c2-j48.model";
            BayesModelPath = "models/subModels/c2/NaiveBayes/c2-bayes.model";
            IbkModelPath = "models/subModels/c2/IBK/c2-IBK.model";
        }
        else if(categoryName.equalsIgnoreCase("c3")){
            System.out.println("c3 Model yükle");
            J48ModelPath = "models/subModels/c3/J48/c3-j48.model";
            BayesModelPath = "models/subModels/c3/NaiveBayes/c3-bayes.model";
            IbkModelPath = "models/subModels/c3/IBK/c3-IBK.model";
        }
        else if(categoryName.equalsIgnoreCase("c4")){
            System.out.println("c4 Model yükle");
            J48ModelPath = "models/subModels/c4/J48/c4-j48.model";
            BayesModelPath = "models/subModels/c4/NavieBayes/c4-bayes.model";
            IbkModelPath = "models/subModels/c4/IBK/c4-IBK.model";
        }
        else if(categoryName.equalsIgnoreCase("c5")){
            System.out.println("c5 Model yükle");
            J48ModelPath = "models/subModels/c5/J48/c5-j48.model";
            BayesModelPath = "models/subModels/c5/NaiveBayes/c5-bayes.model";
            IbkModelPath = "models/subModels/c5/IBK/c5-IBK.model";
        }
        else if(categoryName.equalsIgnoreCase("c6")){
            System.out.println("c6 Model yükle");
            J48ModelPath = "models/subModels/c6/J48/c6-j48.model";
            BayesModelPath = "models/subModels/c6/NaiveBayes/c6-bayes.model";
            IbkModelPath = "models/subModels/c6/IBK/c6-IBK.model";
        }
        else if(categoryName.equalsIgnoreCase("c7")){
            System.out.println("c7 Model yükle");
            J48ModelPath = "models/subModels/c7/J48/c7-j48.model";
            BayesModelPath = "models/subModels/c7/NaiveBayes/c7-bayes.model";
            IbkModelPath = "models/subModels/c7/IBK/c7-IBK.model";
        }
        else if(categoryName.equalsIgnoreCase("c8")){
            System.out.println("c8 Model yükle");
            J48ModelPath = "models/subModels/c8/J48/c8-j48.model";
            BayesModelPath = "models/subModels/c8/NaiveBayes/c8-bayes.model";
            IbkModelPath = "models/subModels/c8/IBK/c8-IBK.model";
        }
        else if(categoryName.equalsIgnoreCase("c9")){
            System.out.println("c9 Model yükle");
            J48ModelPath = "models/subModels/c9/J48/c9-j48.model";
            BayesModelPath = "models/subModels/c9/NaiveBayes/c9-bayes.model";
            IbkModelPath = "models/subModels/c9/IBK/c9-IBK.model";
        }
        else{
            System.out.println("Category not found");
            J48ModelPath = "models/fcJ48.model";
            BayesModelPath = "models/fcBayes.model";
            IbkModelPath = "models/fcIbk.model";
        }

        //resource
        FilteredClassifier NBM = (FilteredClassifier) weka.core.SerializationHelper.read(classLoader.getResource(BayesModelPath).getFile());
        FilteredClassifier J48 = (FilteredClassifier) weka.core.SerializationHelper.read(classLoader.getResource(J48ModelPath).getFile());
        FilteredClassifier IBK = (FilteredClassifier) weka.core.SerializationHelper.read(classLoader.getResource(IbkModelPath).getFile());

        //JSON'dan arff'e dönüştürülen dosya okunuyor.
        BufferedReader breader = null;
        breader = new BufferedReader(new FileReader(filePath));
        Instances test = new Instances(breader);
        test.setClassIndex(test.numAttributes() - 1);

        Instances labeled = new Instances(test);
        // label instances

        //algoritmalardan dönen index değerleri
        if(!(categoryName.equalsIgnoreCase("c9"))){
            rsp[0] = NBM.classifyInstance(test.instance(0));       //response'un indisini döndürür.
            rsp[1] = J48.classifyInstance(test.instance(0));      //j48 algoritmasından dönen response'indisini tutar
            rsp[2] = IBK.classifyInstance(test.instance(0));     //IBK algoritmasından dönen response'indisini tutar

            System.out.println("clsNBM --> " + rsp[0]);
            System.out.println("clsJ48 --> " + rsp[1]);
            System.out.println("clsIBK --> " + rsp[2]);
        }
        else{
            rsp[0] = J48.classifyInstance(test.instance(0));       //response'un indisini döndürür.
            rsp[1] = NBM.classifyInstance(test.instance(0));      //j48 algoritmasından dönen response'indisini tutar
            rsp[2] = IBK.classifyInstance(test.instance(0));     //IBK algoritmasından dönen response'indisini tutar

            System.out.println("clsJ48 --> " + rsp[0]);
            System.out.println("clsNBM --> " + rsp[1]);
            System.out.println("clsIBK --> " + rsp[2]);
        }

        double[] responseCounter = rspCounter();            //Hangi response'dan fazla var onu sayacak olan counter fonksiyonu

        double result = bestResponse(responseCounter);     //En çok hangi response' çıkmış onun döndürecek olan fonksiyon

        labeled.instance(0).setClassValue(result);

        System.out.println("Result --> " + result);
        System.out.println("cevap -->" + labeled.instance(0));

        String[] cevaplar = labeled.instance(0).toString().split(",");
        System.out.println("cevap 1 ->" + cevaplar[0]);
        System.out.println("cevap 2 ->" + cevaplar[1]);

        question.setQuestion(labeled.instance(0).toString());

        breader.close();

        testArffDelete();

        return cevaplar[1];
    }

    public String findCategory(Question question) throws Exception {

        String className;
        System.out.println("findCategory");

        try {
            System.out.println("category file name --> " + getCategoryFileName());

            FileWriter arff = new FileWriter(categoryFilePath);

            System.out.println(question.getQuestion());

            arff.append("@relation question\n");
            arff.append("@attribute question string\n");
            arff.append(
                    "@attribute class {c1, c2, c3, c4, c5, c6, c7, c8, c9}\n");
            arff.append("@data\n");
            arff.append("'" + question.getQuestion() + "'" + ",");
            arff.append("?");

            arff.flush();
            arff.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Model yüklendi
        System.out.println("category modeller yükleniyor");

        String catogeriesNBModelPath = "models/decisionModels/categories-bayes.model";
        String catogeriesJ48ModelPath = "models/decisionModels/catogeries-j48.model";
        String catogeriesIBKModelPath = "models/decisionModels/catogeries-IBK.model";

        //resource
        FilteredClassifier NBM = (FilteredClassifier) weka.core.SerializationHelper.read(classLoader.getResource(catogeriesNBModelPath).getFile());
        FilteredClassifier J48 = (FilteredClassifier) weka.core.SerializationHelper.read(classLoader.getResource(catogeriesJ48ModelPath).getFile());
        FilteredClassifier IBK = (FilteredClassifier) weka.core.SerializationHelper.read(classLoader.getResource(catogeriesIBKModelPath).getFile());

        //JSON'dan arff'e dönüştürülen dosya okunuyor.
        BufferedReader breader = null;
        breader = new BufferedReader(new FileReader(categoryFilePath));
        Instances test = new Instances(breader);
        test.setClassIndex(test.numAttributes() - 1);

        Instances labeled = new Instances(test);
        // label instances
        rsp[0] = J48.classifyInstance(test.instance(0));       //J48 category response'un indisini döndürür.
        rsp[1] = NBM.classifyInstance(test.instance(0));      //NBM algoritmasından dönen category response'indisini tutar
        rsp[2] = IBK.classifyInstance(test.instance(0));     //IBK algoritmasından dönen  category response'indisini tutar

        //algoritmalardan dönen index değerleri
        System.out.println("category clsJ48 --> " + rsp[0]);
        System.out.println("category clsNBM --> " + rsp[1]);
        System.out.println("category clsIBK --> " + rsp[2]);

        double[] responseCounter = rspCounter();            //Hangi response'dan fazla var onu sayacak olan counter fonksiyonu

        double result = bestResponse(responseCounter);     //En çok hangi response' çıkmış onun döndürecek olan fonksiyon

        labeled.instance(0).setClassValue(result);

        System.out.println("Category Result --> " + result);
        System.out.println("Category Cevap -->" + labeled.instance(0));

        String[] cevaplar = labeled.instance(0).toString().split(",");
        System.out.println("cevap category 1 ->" + cevaplar[0]);
        System.out.println("cevap category 2 ->" + cevaplar[1]);

        breader.close();

        categoryArffDelete();

        className = findClass(question, cevaplar[1]);

        return  className;
    }

    //JSON'dan weka algoritmalarnda çalıştırmak için dönüştürdüğümüz test.arff adlı dosyayı silen fonk.
    public void testArffDelete() {
        File file = new File(filePath);
        if (file.delete()) {
            System.out.println(filePath + " deleted!");
        } else {
            System.out.println("Delete operation is failed.!!!");
        }
    }

    public void categoryArffDelete() {
        File file = new File(categoryFilePath);
        if (file.delete()) {
            System.out.println(categoryFilePath + " deleted!");
        } else {
            System.out.println("Delete operation is failed.!!!");
        }
    }

    //Modellerden dönen cevapları sayan fonksiyon
    public double[] rspCounter() {

        double[] responseCounter = new double[rsp.length];

        for (int i = 0; i < rsp.length; i++) {
            for (int j = i + 1; j < rsp.length; j++) {
                if (rsp[i] == rsp[j]) {
                    responseCounter[i]++;
                }
            }
        }
        return responseCounter;
    }

    /*
     *               findClass
     *index --> i = 0 --> en başarılı olan weka algoritması (NB, J48, IBK)
     * */
    public double bestResponse(double[] responseCounter) {
        //en fazla olan response'u tutan deger
        double bestRsp = responseCounter[0];
        int index = 0;
        boolean equals = true;                      //hepsi eşit olup olmadığı tutan deger

        for (int i = 0; i < responseCounter.length; i++) {
            if (responseCounter[i] != 0) {
                equals = false;
            }
            if (bestRsp < responseCounter[i]) {
                bestRsp = responseCounter[i];
                index = i;
            }
        }

        if (equals) {
            index = 0;
        }
        return rsp[index];
    }

    public String getFileName() {
        long timeStamp = System.currentTimeMillis();
        return timeStamp + ".arff";
    }

    public String getCategoryFileName() {
        long timeStamp = System.currentTimeMillis();
        return timeStamp + "category.arff";
    }
}

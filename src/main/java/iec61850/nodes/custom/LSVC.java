package iec61850.nodes.custom;

import iec61850.nodes.common.LN;
import iec61850.nodes.protection.protectionrelatedfunctions.objects.samples.SAV;
import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Getter @Setter
public class LSVC extends LN {

    private List<SAV> signals = new ArrayList<>();

    /** Считанный файл CFG */
    private List<String> cfgFileLines = new ArrayList<>();

    /** Считанный файл DAT */
    private List<String> datFileLines = new ArrayList<>();
    private List<Float> aBuffer = new ArrayList<>();
    private List<Float> bBuffer = new ArrayList<>();
    private Iterator<String> iterator;
    private int signalNumber;
    public LSVC() {
    }

    /** Загрузить Comtrade файл (.cfg) */
    public void readComtrade(String CSVPath){
        cfgFileLines = readFile(CSVPath + ".cfg");
        datFileLines = readFile(CSVPath + ".dat");

        iterator = datFileLines.iterator();

        /* Извлечение числа сигналов */
        int analogNumber = Integer.parseInt(cfgFileLines.get(1).split(",")[1].replace("A", ""));
        int discreteNumber = Integer.parseInt(cfgFileLines.get(1).split(",")[2].replace("D", ""));
        signalNumber = analogNumber + discreteNumber;

        if(signals.size() < signalNumber) {
            for (int i = 0; i < 100; i++) {
                signals.add(new SAV());
            }
        }



        /* Извлечение масштабирующих сигналов (для аналоговых сигналов) */
        for (int i=2; i<(2+analogNumber); i++){
            String line = cfgFileLines.get(i);
            String[] lSplit = line.split(",");
            aBuffer.add(Float.parseFloat(lSplit[5]));
            bBuffer.add(Float.parseFloat(lSplit[6]));
        }

        System.out.printf("Осциллограмма загружена, количество сигналов: %s, количество выборок: %s %n%n", signalNumber, datFileLines.size());
    }


    @Override
    public void process() {
        if(iterator.hasNext()){
            String[] split = iterator.next().split(",");


            for(int s=0; s < signalNumber ; s++){
                float value = Float.parseFloat(split[s + 2]);
                if(s < aBuffer.size()) value = value * aBuffer.get(s) + bBuffer.get(s);

                SAV sav = signals.get(s);
                sav.getInstMag().getF().setValue(value * 1000);

            }
        }
    }


    /** Загрузить содержимое файла */
    private static List<String> readFile(String path){
        List<String> fileEntry = new ArrayList<>();

        try {
            File file = new File(path);
            if(!file.exists()) System.err.println(path + " - Файл не найден, неправильно указан путь");

            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line = bufferedReader.readLine();
            while(line!=null){
                fileEntry.add(line);
                line = bufferedReader.readLine();
            }

            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) { e.printStackTrace(); }

        return fileEntry;
    }

    public boolean hasNext() {
        return iterator.hasNext();
    }

    public List<SAV> getSignals() {
        return signals;
    }
}

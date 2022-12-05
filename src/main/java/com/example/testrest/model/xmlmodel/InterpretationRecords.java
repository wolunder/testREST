package com.example.testrest.model.xmlmodel;

public class InterpretationRecords {
    //преобразовавние доли в дробь
    public static double calculationNumerator(String recordsNumDen, String factorDenumerator) {

        String[] str = recordsNumDen.split("/");
        int factor = factorDenumerator.length();

        int exponent = str[1].length() - factor;
        double result = Integer.parseInt(str[0]) / Math.pow(10.0, exponent);

        int factorLenght = factorDenumerator.length();
        factorLenght = factorLenght + 4;

        if(exponent > 4 && (result >0 && result < 1)) {
            result = result * Math.pow(10.0, (exponent - 4));
        }

       else if (factorLenght == str[1].length() && (result >0 && result < 1) ){
            result = result *10;
        }
        return result;
    }
}

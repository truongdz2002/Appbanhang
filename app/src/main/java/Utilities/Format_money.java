package Utilities;

import java.text.DecimalFormat;

public class Format_money {

    public Format_money() {
    }
    public String FormatMoney(int a)
    {
        String s;
        String pattern="###,###,###";
        DecimalFormat decimalFormat=new DecimalFormat(pattern);
        s=String.valueOf(decimalFormat.format(a));

        return s;
    }
}

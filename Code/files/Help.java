package g;

import java.io.Serializable;

public class Help implements Serializable {
    double obsgetx;
    double obsgety;
    String obstype;
    double ballx;
    double bally;
    int countscore;
    public Help(double obsgetx,double obsgety,String obstype,double ballx,double bally,int countscore){
        this.ballx=ballx;
        this.bally=bally;
        this.countscore=countscore;
        this.obstype=obstype;
        this.obsgetx=obsgetx;
        this.obsgety=obsgety;

    }
}

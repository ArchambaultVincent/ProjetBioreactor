package Optimisation;

public class State {
    private double action[]=new double[7];
    private double growthvalue;
    private double value;
    State(){
        this.growthvalue = 0;
        for(int i=0;i < 7 ; i++)
            action[i]=1/7;
        value=5;
    }
    public double[] getAction(){
        return this.action;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getGrowthvalue() {
        return growthvalue;
    }

    public void setGrowthvalue(double growthvalue) {
        this.growthvalue = growthvalue;
    }

    public void setAction(double action1,double action2,double action3,double action4,double action5,double action6,double action7){
        action[0]=action1;
        action[1]=action2;
        action[2]=action3;
        action[3]=action4;
        action[4]=action5;
        action[5]=action6;
        action[6]=action7;
    }
}

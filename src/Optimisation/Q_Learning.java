package Optimisation;

public class Q_Learning {
    private final int phMAX=10;
    private final int tempMAX=30;
    private final int doMAX=10;

    private double alpha = 0.1  ;
    private double gamma = 0.90 ;
    private State StateMax;
    private State LastState=null;
    private State AllState[][][] = new State[tempMAX][phMAX][doMAX];
    private double actualbiomass=-1;
    private int phindex;
    private int doindex;
    private int tempindex;
    private int action=-1;

    public Q_Learning(int phindex, int doindex, int tempindex){
        this.phindex=phindex;
        this.doindex=doindex;
        this.tempindex=tempindex;
        for(int index=0; index < tempMAX;index++){
            for(int index2=0; index2 < phMAX;index2++){
                for(int index3=0; index3 < doMAX;index3++){
                    AllState[index][index2][index3]=new State();
                }
            }
        }
    }

    public void reforcement(double biomass){
        System.out.println(tempindex+"|"+phindex+"|"+doindex);
        State state = AllState[tempindex][phindex][doindex];
        double value = state.getValue();
        double val=0;
        if(actualbiomass < 0){
            actualbiomass=biomass;
            val=0;
            StateMax=state;
        }
        else{
            double evolv=biomass-actualbiomass;
            actualbiomass=biomass;
            if (evolv > 0){
                val=5;
            }
            else{
                val=-20;
            }
            state.setGrowthvalue(evolv);
            if(state.getGrowthvalue() > StateMax.getGrowthvalue()){
                StateMax=state;
            }
            if(LastState==null){
                LastState=state;
            }
            else{
                if(LastState.getGrowthvalue() > state.getGrowthvalue())
                    val-=10;
                LastState=state;
            }
        }
        value=(1-alpha)*value+alpha*(val+StateMax.getValue()*gamma);
        state.setValue(value);
    }

    public double MaxValue(){
        double Maxvalue=0;
        if(phindex == phMAX-1){
            Maxvalue+=AllState[tempindex][phindex-1][doindex].getValue();
        }
        else if (phindex == 0){
            Maxvalue+=AllState[tempindex][phindex+1][doindex].getValue();
        }
        else{
            Maxvalue+=AllState[tempindex][phindex-1][doindex].getValue();
            Maxvalue+=AllState[tempindex][phindex+1][doindex].getValue();
        }
        if(tempindex == tempMAX-1){
            Maxvalue+=AllState[tempindex-1][phindex][doindex].getValue();
        }
        else if (tempindex == 0){
            Maxvalue+=AllState[tempindex+1][phindex][doindex].getValue();
        }
        else{
            Maxvalue+=AllState[tempindex+1][phindex][doindex].getValue();
            Maxvalue+=AllState[tempindex-1][phindex][doindex].getValue();
        }
        if(doindex == doMAX-1){
            Maxvalue+=AllState[tempindex][phindex][doindex-1].getValue();
        }
        else if (doindex == 0){
            Maxvalue+=AllState[tempindex][phindex][doindex+1].getValue();
        }
        else{
            Maxvalue+=AllState[tempindex][phindex][doindex+1].getValue();
            Maxvalue+=AllState[tempindex][phindex][doindex-1].getValue();
        }
        return Maxvalue;
    }
    public int MaxIndex(){
        int maxIndex=7;
        if(phindex == phMAX-1 || phindex == 0){
            maxIndex--;
        }
        if(tempindex == tempMAX-1 || tempindex == 0){
            maxIndex--;
        }
        if(doindex == doMAX-1 || doindex == 0 ){
            maxIndex--;

        }
        return maxIndex;
    }
    public void NewAction(){
        double Maxvalue=MaxValue();
        int maxIndex=MaxIndex();
        double action1,action2,action3,action4,action5,action6,action7=0.0;
        if(phindex == phMAX-1){
             action1= AllState[tempindex][phindex-1][doindex].getValue()/MaxValue();
             action2= 0;
        }
        else if (phindex == 0){
            action2= AllState[tempindex][phindex+1][doindex].getValue()/MaxValue();
            action1= 0;
        }
        else{
            action1= AllState[tempindex][phindex-1][doindex].getValue()/MaxValue();
            action2= AllState[tempindex][phindex+1][doindex].getValue()/MaxValue();
        }
        if(tempindex == tempMAX-1){
            action3= AllState[tempindex-1][phindex][doindex].getValue()/MaxValue();
            action4= 0;
        }
        else if (tempindex == 0){
            action3= 0;
            action4= AllState[tempindex+1][phindex][doindex].getValue()/MaxValue();
        }
        else{
            action3= AllState[tempindex-1][phindex][doindex].getValue()/MaxValue();
            action4= AllState[tempindex+1][phindex][doindex].getValue()/MaxValue();

        }
        if(doindex == doMAX-1){
            action5= AllState[tempindex][phindex][doindex-1].getValue()/MaxValue();
            action6= 0;
        }
        else if (doindex == 0){
            action5= 0;
            action6= AllState[tempindex][phindex][doindex+1].getValue()/MaxValue();
        }
        else{
            action5= AllState[tempindex][phindex][doindex-1].getValue()/MaxValue();
            action6= AllState[tempindex][phindex][doindex+1].getValue()/MaxValue();
        }
        AllState[tempindex][phindex][doindex].setAction(action1,action2,action3,action4, action5,action6,action7);
    }

    public int decision(){
       int index=0;
       double rand=Math.random();
       double action[]=AllState[tempindex][phindex][doindex].getAction();
       double proba=action[index];
       while(proba <= rand && index < 6) {
           index++;
           proba += action[index];
       }
        return index;
    }

    public int Action(){
        NewAction();
        action = decision();
        switch (action){
            case 0 :
                phindex--;
                break;
            case 1 :
                phindex++;
                break;
            case 2 :
                tempindex--;
                break;
            case 3 :
                tempindex++;
                break;
            case 4 :
                doindex--;
                break;
            case 5 :
                doindex++;
                break;
            default:
                break;
        }
        return action;
    }

   public Bio_Parameter Get_Parameter(){
        Bio_Parameter b= new Bio_Parameter(phindex,tempindex,doindex/10,0);
        return b;
    }

}

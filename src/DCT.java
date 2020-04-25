public class DCT {




    public static void main(String[] args) {
        int N=8;
        double f[][]=new double[N][N];
        double F[][]=new double[N][N];
        double fp[][]=new double[N][N];
        double FP[][]=new double[N][N];
        for(int i=0; i < N ; i++)
            f[0][i]=-2;
        for(int i2=0; i2 < N ; i2++)
            f[1][i2]=-2;
        for(int i3=0; i3 < N ; i3++)
            f[2][i3]=-2;
        for(int i4=0; i4 < N ; i4++)
            f[3][i4]=-2;
        for(int i5=0; i5 < N ; i5++)
            f[4][i5]=-2;
        for(int i6=0; i6 < N ; i6++)
            f[5][i6]=-2;
        for(int i7=0; i7 < N ; i7++)
            f[6][i7]=-2;
        for(int i8=0; i8 < N ; i8++)
            f[7][i8]=-2;
        double W[][]=new double[N][N];
        W[0][0]=8;
        W[0][1]=16;
        W[0][2]=16;
        W[0][3]=16;
        W[0][4]=16;
        W[0][5]=16;
        W[0][6]=16;
        W[0][7]=16;
        W[1][0]=16;
        W[1][1]=16;
        W[1][2]=16;
        W[1][3]=16;
        W[1][4]=16;
        W[1][5]=16;
        W[1][6]=16;
        W[1][7]=16;
        W[2][0]=16;
        W[2][1]=16;
        W[2][2]=16;
        W[2][3]=16;
        W[2][4]=16;
        W[2][5]=16;
        W[2][6]=16;
        W[2][7]=16;
        W[3][0]=16;
        W[3][1]=16;
        W[3][2]=16;
        W[3][3]=16;
        W[3][4]=16;
        W[3][5]=16;
        W[3][6]=16;
        W[3][7]=16;
        W[4][0]=16;
        W[4][1]=16;
        W[4][2]=16;
        W[4][3]=16;
        W[4][4]=16;
        W[4][5]=16;
        W[4][6]=16;
        W[4][7]=16;
        W[5][0]=16;
        W[5][1]=16;
        W[5][2]=16;
        W[5][3]=16;
        W[5][4]=16;
        W[5][5]=16;
        W[5][6]=16;
        W[5][7]=16;
        W[6][0]=16;
        W[6][1]=16;
        W[6][2]=16;
        W[6][3]=16;
        W[6][4]=16;
        W[6][5]=16;
        W[6][6]=16;
        W[6][7]=16;
        W[7][0]=16;
        W[7][1]=16;
        W[7][2]=16;
        W[7][3]=16;
        W[7][4]=16;
        W[7][5]=16;
        W[7][6]=16;
        W[7][7]=16;

        for(int u=0;u<N;u++){
            for(int v=0;v<N;v++){
                double cu=0,cv=0;
                if(u > 0){
                    cu=1;
                }
                else{
                    cu=1/Math.sqrt(2);
                }
                if(v > 0){
                    cv=1;
                }
                else{
                    cv=1/Math.sqrt(2);
                }
                double somme=0;
                for(int x=0;x<N;x++){
                    for(int y=0;y<N;y++){
                        double cos1=Math.cos(((2*x+1)*u*Math.PI)/(2*N));
                        double cos2=Math.cos(((2*y+1)*v*Math.PI)/(2*N));
                        somme+=f[x][y]*cos1*cos2;
                    }
                }
                double truc=0.25;
                F[u][v]=Math.round(truc*cv*cu*somme);
            }
        }
        System.out.println("affichage GRAND F");
        for(int index=0; index < N;index++){
            String line="";
            for(int index2=0; index2 < N;index2++){
               line+=(F[index][index2])+";";
            }
            System.out.println(line);
        }
        int QWANT=16;
        double QF[][]=new double[N][N];
        for(int index3=0;index3 < N;index3++){
            for(int index4=0;index4 < N;index4++){
                QF[index3][index4]=(16*F[index3][index4])/(QWANT*W[index3][index4]);
            }
        }
        System.out.println("affichage  QF");
        for(int index=0; index < N;index++){
            String line="";
            for(int index2=0; index2 < N;index2++){
                line+=(QF[index][index2])+";";
            }
            System.out.println(line);
        }

        System.out.println("Transformer INVERSE");
        for(int index3=0;index3 < N;index3++){
            for(int index4=0;index4 < N;index4++){
                FP[index3][index4]=(QF[index3][index4]*QWANT*W[index3][index4])/(16);
            }
        }
        for(int u=0;u<N;u++){
            for(int v=0;v<N;v++){
                double somme=0;
                for(int x=0;x<N;x++){
                    for(int y=0;y<N;y++){
                        double cu=0,cv=0;
                        if(x > 0){
                            cu=1;
                        }
                        else{
                            cu=1/Math.sqrt(2);
                        }
                        if(y > 0){
                            cv=1;
                        }
                        else{
                            cv=1/Math.sqrt(2);
                        }
                        double cos1=Math.cos(((2*u+1)*x*Math.PI)/(2*N));
                        double cos2=Math.cos(((2*v+1)*y*Math.PI)/(2*N));
                        somme+=FP[x][y]*cos1*cos2*cu*cv;
                    }
                }
                double truc=0.25;
                fp[u][v]=Math.round(truc*somme);
            }
        }

        System.out.println("affichage  G");
        for(int index=0; index < N;index++){
            String line="";
            for(int index2=0; index2 < N;index2++){
                line+=(FP[index2][index])+";";
            }
            System.out.println(line);
        }

        System.out.println("affichage  g");
        for(int index=0; index < N;index++){
            String line="";
            for(int index2=0; index2 < N;index2++){
                line+=(fp[index2][index])+";";
            }
            System.out.println(line);
        }


        double E[]=new double[16];
        E[0]=2;
        E[1]=32;
        E[2]=32;
        E[3]=24;
        E[4]=32;
        E[5]=24;
        E[6]=16;
        E[7]=8;
        E[8]=32;
        E[9]=16;
        E[10]=8;
        E[11]=4;
        E[12]=24;
        E[13]=8;
        E[14]=4;
        E[15]=2;

                double entropie=0;
                double max=0;
                for(int i=0; i < 16 ;i++){
                    max+=E[i];
                }
        for(int i=0; i < 16 ;i++){
            entropie=(E[i]/max)*(Math.log(E[i]/max)/Math.log(2));
        }
        entropie=entropie*(-1);
        System.out.println("Entropie ="+ entropie);
    }
}

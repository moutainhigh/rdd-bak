package com.cqut.czb.bn.util.method;

import java.util.Arrays;

public class GetIdentifyCode {
    public static String getIdentityCode(String id, String num){
        String identityCode = new String();
        String identity1 = id.substring(0,6);
        String identity2 = id.substring(12, 18);
        String identity3 = num.substring(1, 5);
        System.out.println(identity3);
        int[] index1 = new int[3];
        int[] index2 = new int[3];
        index1[2] = -1;
        index2[2] = -1;

        int[] index3 = new int[2];
        index3[1] = -1;

        //如果在随机排列身份证前6为和后6为的过程中，重复出现了相同的下标，则在这中间插入车牌号第2-6中的随机两位字符
        boolean ifInto = false;

        for(int i = 0; index1[2] == -1 && index2[2] == -1; i++ ){
            Integer a = (int) (Math.random()*6);
            Integer b = (int) (Math.random()*6) ;

            if(Arrays.toString(index1).contains(a.toString()) || Arrays.toString(index2).contains(b.toString())){
                System.out.println("c");
                if(!ifInto){
                    for(int j = 0; index3[1] == -1; j++ ){
                        Integer c = (int) (Math.random()*4);
                        if(Arrays.toString(index3).contains(c.toString())){
                            j--;
                            continue;
                        }
                        else{
                            index3[j] = c;
                            identityCode = identityCode + identity3.charAt(c) ;
                        }
                    }
                    ifInto = true;
                }
                i--;
                continue;
            }
            else{
                index1[i] = a;
                index2[i] = b;
                identityCode = identityCode + identity1.charAt(a) + identity2.charAt(b);
            }
        }

        // 没有在上面过程中打印c，则在末尾添加两位字符
        if(!ifInto){
            for(int i = 0; index3[1] == -1; i++ ){
                Integer c = (int) (Math.random()*4);
                if(Arrays.toString(index3).contains(c.toString())){
                    System.out.println("c");
                    i--;
                    continue;
                }
                else{
                    index3[i] = c;
                    identityCode = identityCode + identity3.charAt(c) ;
                }
            }
        }

        return identityCode;
    }
}

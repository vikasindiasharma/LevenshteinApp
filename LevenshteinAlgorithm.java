public class LevenshteinAlgorithm {

    public static void main(String[] args) {

        testlevenshtein("Haus","Maus",1);
        testlevenshtein("Haus","Mausi",2);
        testlevenshtein("Haus","Häuser",3);
        testlevenshtein("Kartoffelsalat","Runkelrüben",12);

        testlevenshtein("Haus","Maus",2,1);
        testlevenshtein("Haus","Mausi",2,2);
        testlevenshtein("Haus","Häuser",2,3);
        testlevenshtein("Kartoffelsalat","Runkelrüben",2,3);


    }

    static  void testlevenshtein (String token1,String token2 , int expectedDistance )
    {
        long nano_startTime = System.nanoTime();
        int actualResult = levenshtein(token1,token2);
        long nano_endTime = System.nanoTime();
        long duration=nano_endTime - nano_startTime;
        String result = actualResult == expectedDistance ? "Pass": "Failed";
        System.out.println(String.format("[%s => %s] Result [%s] ,Test execution nano time  [%s]",token1,token2,result,duration));
    }

    static  void testlevenshtein (String token1,String token2 , int maxDist, int expectedDistance )
    {
        long nano_startTime = System.nanoTime();
        int actualResult = levenshtein(token1,token2,maxDist);
        long nano_endTime = System.nanoTime();
        long duration=nano_endTime - nano_startTime;
        String result = actualResult == expectedDistance ? "Pass": "Failed";
        System.out.println(String.format("[%s => %s] Result [%s] ,Test execution nano time  [%s]",token1,token2,result,duration));

    }

    static int levenshtein(String token1, String token2)
    {
        return  levenshtein(token1,token2,Integer.MAX_VALUE);
    }
    static int levenshtein(String token1, String token2,int maxDist)
    {
        long nano_startTime = System.nanoTime();

        int result =0;
        if (token1 != null  && token2 != null && !token1.equals(token2) )
        {

        if (token1.length() == 0) {
            result=token2.length();
        }
        else if  (token2.length() == 0) {
            result= token1.length();
        }
        else {
            if ( maxDist != Integer.MAX_VALUE)
            {
                //an early exit if the distance exceeds a maximum
                //distance. Return maxDist + 1 in this case.
                maxDist++;
            }
            int[] vector1 = new int[token2.length() + 1];
            int[] vector2 = new int[token2.length() + 1];
            int[] tempArray;


            for (int index = 0; index < vector1.length; index++) vector1[index] = index;

            boolean earlyExit = false;
            for (int firstTokenIndex = 0; firstTokenIndex < token1.length(); firstTokenIndex++) {

                vector2[0] = firstTokenIndex + 1;

                int vector2Min = vector2[0];

                for (int secondTokenIndex = 0; secondTokenIndex < token2.length(); secondTokenIndex++) {
                    int cost = 1;
                    if (token1.charAt(firstTokenIndex) == token2.charAt(secondTokenIndex)) {
                        cost = 0;
                    }
                    vector2[secondTokenIndex + 1] = Math.min(
                            vector2[secondTokenIndex] + 1,              // Cost of insertion
                            Math.min(
                                    vector1[secondTokenIndex + 1] + 1,  // Cost of remove
                                    vector1[secondTokenIndex] + cost)); // Cost of substitution

                    vector2Min = Math.min(vector2Min, vector2[secondTokenIndex + 1]);
                }

                if (vector2Min >= maxDist ) {
                    result= maxDist;
                    earlyExit= true;
                    break;
                }

                tempArray = vector1;
                vector1 = vector2;
                vector2 = tempArray;

            }

            if (!earlyExit)
                result = vector1[token2.length()];
        }
        }

        long nano_endTime = System.nanoTime();
        long duration=nano_endTime - nano_startTime;
        System.out.print( String.format("levenshtein function execution nano time : [%s] , ", duration));
        return  result;
    }


}

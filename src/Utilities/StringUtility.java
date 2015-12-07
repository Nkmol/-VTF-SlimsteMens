package Utilities;

public final class StringUtility {
	
	private StringUtility() {
		
	}
	
	public static final String endLine = System.getProperty("line.separator");
	
	public static int CalculateLevenshteinDistance(String string1, String string2) {
	    
	    int length1 = string1.length() + 1;
	    int length2 = string2.length() + 1;  
	    // the array of distances
	    int[] cost = new int[length1];
	    int[] newCost = new int[length1];

	    // initial cost of skipping prefix in String s0
	    for (int i = 0; i < length1; i++)
	        cost[i] = i;

	    // dynamically computing the array of distances

	    // transformation cost for each letter in s1
	    for (int j = 1; j < length2; j++) {

	        // initial cost of skipping prefix in String s1
	        newCost[0] = j - 1;

	        // transformation cost for each letter in s0
	        for (int i = 1; i < length1; i++) {

	            // matching current letters in both strings
	            int match = (string1.charAt(i - 1) == string2.charAt(j - 1)) ? 0 : 1;

	            // computing cost for each transformation
	            int replaceCost = cost[i - 1] + match;
	            int insertCost = cost[i] + 1;
	            int deleteCost = newCost[i - 1] + 1;

	            // keep minimum cost
	            newCost[i] = Math.min(Math.min(insertCost, deleteCost),
	                    replaceCost);
	        }

	        // swap cost/newcost arrays
	        int[] swap = cost;
	        cost = newCost;
	        newCost = swap;
	    }

	    // the distance is the cost for transforming all letters in both strings
	    return cost[length1 - 1];
	}
	
	public static int CalculateMatchPercentage(String s0, String s1) {                       
		
		// Trim and remove duplicate spaces
	    s0 = s0.trim().replaceAll("\\s+", " ");
	    s1 = s1.trim().replaceAll("\\s+", " ");
	    return (int) (100 - (float) CalculateLevenshteinDistance(s0, s1) * 100 / (float) Math.max(s0.length(), s1.length()));
	}
}

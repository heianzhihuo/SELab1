import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Expression {
	polynomial root;
	
	private boolean isMatch(String pattern, String s) {
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(s);
		boolean flag = m.matches();
		return flag;
	}

	private boolean isFind(String pattern, String s) {
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(s);
		boolean flag = m.find();
		return flag;
	}

	public boolean isValid(String str) {
		//judge is a valid expression
		String partern1 = "[a-zA-Z0-9\\+\\-\\*\\^]{0,}";// is consist of valid
//		String partern1 = "[^a-z^A-Z^0-9^\\+^\\-^\\*^\\^]";
		String partern2 = "[\\+\\-\\*\\^]{2,}";// is there two or more operation
		String partern3 = "^[a-zA-Z0-9]";// start with char or number
		String partern4 = "[a-zA-Z0-9]$";// end with char or number
		String partern5 = "[0-9][a-zA-Z]";// number can not be followed by char
		String partern6 = "\\^[^0-9]";//^ must followed by number 
		boolean flag = isMatch(partern1, str) && !isFind(partern2, str) && isFind(partern3, str)
				&& isFind(partern4, str) && !isFind(partern5, str) && !isFind(partern6,str);
		/* System.out.println("1:"+isMatch(partern1,str));
		 System.out.println("2:"+isFind(partern2,str));
		 System.out.println("3:"+isFind(partern3,str));
		 System.out.println("4:"+isFind(partern4,str));
		 System.out.println("5:"+isFind(partern5,str));
		 System.out.println("6:"+isFind(partern6,str));*/
		return flag;
	}

	public String expression(String str){
		//qiu biao da shi yu chu li
		str = str.replaceAll("\\s*", "");
		if (!isValid(str)) {
			return "Error!";
		}
		root = new polynomial(str);
		String output = root.toString();
		return output;
	}
	
	public String simplify(String str){
		//ji suan yu chu li
		HashMap<String,Integer> vals=new HashMap<String,Integer>();
		str=str.replace("!simplify","");
		str=str.replaceAll("\\s*", "");
		String partern = "[a-zA-Z]+=\\-{0,1}[0-9]+";
		String[] vars=str.split(",");
		for(String s:vars){
			if(s.length()==0){
				return root.toString();
			}
			if(!s.matches(partern)){
				System.out.println(s);
				return "Wrong Parameter!!!";
			}
			String [] tmp=s.split("=");
			if(!root.varSet.contains(tmp[0]))
				return "Error, no variable!";
			else{
				vals.put(tmp[0],Integer.parseInt(tmp[1]));
			}
		}
		polynomial tmp = root.simplify(vals);
		return tmp.toString();
	}
	
	public String derivative(String str){
		//qiu dao yu chu li
		str=str.replace("!d/d","");
		str=str.replaceAll("\\s*", "");
		String parttern = "[a-zA-Z]+[0-9]*";
		if(!str.matches(parttern))
			return "Wrong Parameter!!!";
		polynomial tmp = root.derivative(str);
		return tmp.toString();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long startTime,endTime;
		float excTime;
		String str,output;
		Expression person = new Expression();
		Scanner s = new Scanner(System.in);
		while (true) {
			System.out.print('>');
			str = s.nextLine();
			if (!str.startsWith("!")){
				startTime = System.currentTimeMillis();
				output = person.expression(str);
				endTime = System.currentTimeMillis();
				excTime = (float)(endTime-startTime)/1000;
				System.out.println(output);
				System.out.println("StartTime:"+startTime+" EndTime:"+endTime+" ExcTime:"+excTime);
			}
			else if (str.startsWith("!simplify")){
				//System.out.println(person.simplify(str));
				startTime = System.currentTimeMillis();
				
				output = person.simplify(str);
				
				endTime = System.currentTimeMillis();
				excTime = (float)(endTime-startTime)/1000;
				System.out.println(output);
				System.out.println("StartTime:"+startTime+" EndTime:"+endTime+" ExcTime:"+excTime);
			}
			else if (str.startsWith("!d/d")){
				//System.out.println(person.derivative(str));
				startTime = System.currentTimeMillis();
				
				output = person.derivative(str);
				
				endTime = System.currentTimeMillis();
				excTime = (float)(endTime-startTime)/1000;
				System.out.println(output);
				System.out.println("StartTime:"+startTime+" EndTime:"+endTime+" ExcTime:"+excTime);
			}
			else if (str.equalsIgnoreCase("!exit")){
				System.out.println("Exit Scuess!");
				break;
			}
			else
				System.out.println("Command Error!");
		}
		s.close();
	}

}

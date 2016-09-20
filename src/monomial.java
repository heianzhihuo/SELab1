import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class monomial {
	public int coefficient = 1;
	HashMap<String, Integer> mon;
	
	public monomial(){
		mon = new HashMap<String, Integer>();
	}
	
	public monomial(String str) {
		// TODO Auto-generated constructor stub
		mon = new HashMap<String, Integer>();
		String[] mons = str.split("\\*");
		for (String s : mons) {
			if (isNumeric(s)) {
				coefficient *= Integer.parseInt(s);
				// System.out.println(coefficient);
			} else {
				String[] var = s.split("\\^");
				//int exp = var.length == 1 ? 1 : Integer.parseInt(var[1]);
				int exp = 1;
				for(int i=1;i<var.length;i++){
					exp *= Integer.parseInt(var[i]);
				}
				if (mon.get(var[0]) == null) {
					//添加新变量及指数
					mon.put(var[0], exp);
				} else {
					//存在的变量指数增加，同底数幂相乘，底数不变指数相加
					mon.put(var[0], mon.get(var[0]) + exp);
				}
			}
		}
	}
	
	public String toString() {
		//zhuan huan cheng String
		String str = new String();
		Iterator iter = mon.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			if (str.length() == 0) {
				str = str + entry.getKey().toString();
			} else {
				str = str + "*" + entry.getKey().toString();
			}
			if (entry.getValue().toString().compareTo("1") != 0) {
				str = str + "^" + entry.getValue().toString();
			}
		}
		if(str.length()==0){
			str = String.valueOf(this.coefficient);
		} else {
			if (coefficient == -1) {
				str = "-" + str;
			}
			else if (coefficient != 1){
				str = String.valueOf(this.coefficient) + "*" + str;
			}
		}
		return str;
	}
	
	public monomial simplify(HashMap<String,Integer>vals){
		//dan xiang shi qiu zhi
		monomial result = new monomial();
		Set<String> tmps = vals.keySet();
		result.coefficient = coefficient;
		for(String s:mon.keySet()){
			if(tmps.contains(s)){
				if(vals.get(s)==0){
					result.coefficient = 0;
					break;
				} else{
					result.coefficient *= Math.pow(vals.get(s),mon.get(s));
				}
			}else{
				result.mon.put(s,mon.get(s));
			}
		}
		return result;
	}
	
	public monomial derivative(String str){
		//dan xiang shi qiu dao
		monomial result = new monomial();
		result.coefficient = coefficient;
		result.mon.putAll(mon);
		if(mon.containsKey(str)){
			if(mon.get(str)==1){
				result.mon.remove(str);
			}else{
				result.coefficient *= mon.get(str);
				result.mon.put(str,(mon.get(str)-1));
			}
		} else
			result.coefficient = 0;
		return result;
	}
	
	public boolean isSimilarItem(monomial m) {
		//is similar item with m
		if (mon.size() != m.mon.size()) {
			return false;
		}
		for (String key : m.mon.keySet()) {
			if (mon.containsKey(key)) {
				int tmp1 = mon.get(key);
				int tmp2 = m.mon.get(key);
				if (tmp1 != tmp2) {
					return false;
				}
			} else {
				return false;
			}
		}
		return true;
	}
	
	private boolean isNumeric(String str) {
		//judge is number
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

}

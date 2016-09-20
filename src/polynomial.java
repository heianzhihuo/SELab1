import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class polynomial {
	
	HashSet<String> varSet=new HashSet<String>();
	private ArrayList<monomial> mons;
	
	public polynomial(){
		mons = new ArrayList<monomial>();
	}
	
	public polynomial(String str){
		mons = new ArrayList<monomial>();
		String[] mons = str.split("\\+");
		for (String s : mons) {
			String[] s2 = s.split("\\-");
			monomial tmp = new monomial(s2[0]);
			varSet.addAll(tmp.mon.keySet());
			this.mons.add(new monomial(s2[0]));
			for (int i = 1; i < s2.length; i++) {
				monomial temp = new monomial(s2[i]);
				temp.coefficient = -temp.coefficient;
				this.mons.add(temp);
				varSet.addAll(temp.mon.keySet());
			}
		}
		collect();
	}
	
	public polynomial simplify(HashMap<String,Integer>vals){
		//qiu zhi
		polynomial result = new polynomial();
		for(int i=0;i<mons.size();i++){
			monomial tmp = mons.get(i).simplify(vals);
			if(tmp.coefficient!=0){
				//System.out.println(tmp.coefficient);
				result.mons.add(tmp);
			}
		}
		result.collect();
		return result;
	}
	
	public polynomial derivative(String str){
		polynomial result = new polynomial();
		for(int i=0;i<mons.size();i++){
			monomial tmp = mons.get(i).derivative(str);
			result.mons.add(tmp);
		}
		result.collect();
		return result;
	}
	
	private void collect() {
		//he bing tong lei xiang
		int i = 0,j;
		while(i<mons.size()){
			//System.out.println("1+"+mons.size());
			j = i+1;
			monomial tmp1 = mons.get(i);
			while(j<mons.size()){
				//System.out.println("2+"+mons.size());
				monomial tmp2 = mons.get(j);
				if (tmp1.isSimilarItem(tmp2)) {
					tmp1.coefficient += tmp2.coefficient;
					mons.remove(j);
				} else
					j++;
			}
			if (tmp1.coefficient == 0) {
				mons.remove(i);
			}else
				i++;
		}
	}
	
	public String toString() {
		//make it to String
		if (mons.size() == 0) {
			return "0";
		}
		String str = mons.get(0).toString();
		for (int i = 1; i < mons.size(); i++) {
			if (mons.get(i).coefficient < 0) {
				str = str + mons.get(i).toString();
			} else {
				str = str + "+" + mons.get(i).toString();
			}
		}
		return str;
	}

}

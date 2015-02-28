package testmaster.android.resource;

import java.util.ArrayList;

public class DestroyDecorator {
	
	static private ArrayList<DestroyInterface> resourceList = new ArrayList<DestroyInterface>();
	static private DestroyDecorator This;
	static private boolean singletone = false;
	
	public DestroyDecorator() {
	}
	
	static public synchronized DestroyDecorator getDestroyDecorator() {
		if (!singletone) {
			This = new DestroyDecorator();
			singletone = true;
		}
		
		return This;
	}
	
	static public boolean isHaveDoing() {
		return singletone;
	}
	
	public static synchronized void addDecorate(DestroyInterface th) {		
		if (!singletone) {
			This = new DestroyDecorator();
			singletone = true;
		}
		
		This.resourceList.add(th);
		// TODO Auto-generated constructor stub
	}
	
	public static void deleteDecorate(DestroyInterface th) {
		for (int i = 0; i < resourceList.size(); i++) {
			if (resourceList.get(i) == th)
				resourceList.remove(i);
		}
	}
	
	public void destroy() {
		for (int i = 0; i < resourceList.size(); i++) {
			resourceList.get(i).decoratingDestroy();
		}
		resourceList.clear();
		singletone = false;
		This = null;
	}
}

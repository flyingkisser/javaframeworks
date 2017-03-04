package frameworks.utils;

import javax.script.Bindings;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import logic.config.GameConfig;
import logic.system.GameMgr;

public class JSEngine {
	private static JSEngine s_objSelf=null;
	
	private ScriptEngine m_jsEngine;
	private Bindings m_binding;
	private Log log=Log.getInstance();
	
	public static JSEngine getInstance(){
		if(s_objSelf==null)
			s_objSelf=new JSEngine();
		return s_objSelf;
	}
	
	public JSEngine()
	{
		m_jsEngine = new ScriptEngineManager().getEngineByName("javascript");
		m_binding = m_jsEngine.createBindings();
		m_jsEngine.setBindings(m_binding, ScriptContext.ENGINE_SCOPE);
	}
	
	private Object invoke(String jsFuncName, String jsFuncBody, Object...jsArgs){
		Object o = null;
		try {
			m_jsEngine.eval(jsFuncBody);
			o =((Invocable) m_jsEngine).invokeFunction(jsFuncName, jsArgs);
		} catch (NoSuchMethodException e) {
			log.error("invokeFunction %s failed NoSuchMethodException", jsFuncName);
			e.printStackTrace();
		} catch (ScriptException e) {
			log.error("invokeFunction %s failed ScriptException", jsFuncName);
			e.printStackTrace();
		}
	  catch (Exception e) {
		log.error("invokeFunction %s failed Exception unknown", jsFuncName);
		e.printStackTrace();
		StackTraceElement[] stackElements = e.getStackTrace();
        if (stackElements != null) {
            for (int i = 0; i < stackElements.length; i++) 
            	frameworks.utils.Log.getInstance().error(stackElements[i].toString());
        }
	}
		return o;
	}
	

	public boolean init(String jsRetVarName, String jsFuncName, String jsFuncBody,  Double... jsArgs)
	{
		Object o=invoke(jsFuncName, jsFuncBody, jsArgs);
		if(o==null)
			return false;
		//把函数返回对象绑定到js对象varName中
		m_binding.put(jsRetVarName, o);
		return true;
	}
	
	
	public double eval(String expression)
	{
		double result =0;
		try {
			result = (double)m_jsEngine.eval(expression);
		} catch (Exception e) {
			log.error("Calc %s failed Exception", expression);
			e.printStackTrace();
			StackTraceElement[] stackElements = e.getStackTrace();
	        if (stackElements != null) {
	            for (int i = 0; i < stackElements.length; i++) 
	            	frameworks.utils.Log.getInstance().error(stackElements[i].toString());
	        }
			System.exit(-1);
		}
		return result;
	}


}

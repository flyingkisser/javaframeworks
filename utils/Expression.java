package javaframeworks.utils;

import javax.script.Bindings;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;


public class Expression {

	public Expression() {
		m_se = new ScriptEngineManager().getEngineByName("javascript");
	}

	public Bindings GetBinding() {
		if (m_binding == null) {
			m_binding = m_se.createBindings();
			m_se.setBindings(m_binding, ScriptContext.ENGINE_SCOPE);
		}

		return m_binding;
	}

	public void Prepare(String varName, String funcName, String func,
			Double... args) {
		m_binding = GetBinding();
		Invocable in = (Invocable) m_se;

		Object o = null;
		try {
			m_se.eval(func);
			o = in.invokeFunction(funcName, args);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		m_binding.put(varName, o);
	}
	
	public void Prepare(String statement) {
		Invocable in = (Invocable) m_se;

		try {
			m_se.eval(statement);
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Object Calc(String expression) {
		if (m_binding == null)
			return (Object) null;

		Object result = null;
		try {
			result = m_se.eval(expression);
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	public Double getDouble(String expression) {
		Object r = Calc(expression);
		Double result = 0.0;
		if (r instanceof Double)
		{
			result = (Double)r;
		}else if (r instanceof Integer)
		{
			result = ((Integer)r).doubleValue();
		}

		return result;
	}

	public Float getFloat(String expression) {
		Float result = (Float) Calc(expression);

		return result;
	}

	public Integer getInteger(String expression) {
		Object r = Calc(expression);
		if (r instanceof Integer)
		{
			return (Integer)r;
		}else if (r instanceof Double)
		{
			return ((Double)r).intValue();
		}else
		{
			return 0;
		}
	}

	public Boolean getBoolean(String expression) {
		Boolean result = (Boolean) Calc(expression);

		return result;
	}

	public void setFloat(String name, Float value) {
		Bindings bd = GetBinding();

		bd.put(name, value);
	}

	public void setDouble(String name, Float value) {
		Bindings bd = GetBinding();

		bd.put(name, value);
	}
	
	public void setInteger(String name, int i) {
		Bindings bd = GetBinding();

		bd.put(name, i);
	}

	public void setBoolean(String name, Float value) {
		Bindings bd = GetBinding();

		bd.put(name, value);
	}
	
	
	ScriptEngine m_se;
	Bindings m_binding;
	
	public static void main(String[] args) {
		Expression e = new Expression();
		
		String func = "function Player(hp, attack){this.hp = hp;this.attack = attack; return this;}";
		e.Prepare("player", "Player", func, 1000.0, 500.0);
		
		double v0 = (double)e.Calc("player.hp");
		double v1 = (double)e.Calc("player.hp - player.attack");
		double v2 = (double)e.Calc("player.hp*player.attack");
		double v3 = (double)e.Calc("(player.hp*player.attack)/1000");
		
		System.out.println(v1);
		System.out.println(v2);
		System.out.println(v3);
		
		e.setInteger("level", 11);
		String val = "praytime=player.hp;interrupt=false;damagetype=0;ratio=1;damage=5*level;hit=0.2;crit=0.2;critdmg=0.5;";
		e.Prepare(val);
		
		int pray = e.getInteger("praytime");
		boolean inter = e.getBoolean("interrupt");
		int dt = e.getInteger("damagetype");
		int rat = e.getInteger("ratio");
		double dmg = e.getDouble("damage");
		double hit = e.getDouble("hit");
		double crit = e.getDouble("crit");
		double critdmg = e.getDouble("critdmg");
		
		
		System.out.println("-----");
		System.out.println(pray);
		System.out.println(inter);
		System.out.println(dt);
		System.out.println(rat);
		System.out.println(dmg);
		System.out.println(hit);
		System.out.println(crit);
		System.out.println(critdmg);
		//e.m_binding.put(varName, o);
	}
}

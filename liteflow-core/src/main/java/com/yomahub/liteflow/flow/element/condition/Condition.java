/**
 * <p>Title: liteflow</p>
 * <p>Description: 轻量级的组件式流程框架</p>
 * @author Bryan.Zhang
 * @email weenyc31@163.com
 * @Date 2020/4/1
 */
package com.yomahub.liteflow.flow.element.condition;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ObjectUtil;
import com.yomahub.liteflow.enums.ExecuteTypeEnum;
import com.yomahub.liteflow.flow.element.Executable;
import com.yomahub.liteflow.enums.ConditionTypeEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Condition的抽象类
 * @author Bryan.Zhang
 */
public abstract class Condition implements Executable{

	private String id;

	/**
	 * 可执行元素的集合
	 */
	private final Map<String, List<Executable>> executableGroup = new HashMap<>();

	private final String DEFAULT_GROUP = "DEFAULT";


	/**
	 * 当前所在的ChainName
	 * 如果对于子流程来说，那这个就是子流程所在的Chain
	 */
	private String currChainId;

	@Override
	public ExecuteTypeEnum getExecuteType() {
		return ExecuteTypeEnum.CONDITION;
	}

	@Override
	public String getExecuteId() {
		return this.id;
	}

	public List<Executable> getExecutableList() {
		return getExecutableList(DEFAULT_GROUP);
	}

	public List<Executable> getExecutableList(String groupKey) {
		List<Executable> executableList = this.executableGroup.get(groupKey);
		if (CollUtil.isEmpty(executableList)){
			executableList = new ArrayList<>();
		}
		return executableList;
	}

	public Executable getExecutableOne(String groupKey) {
		List<Executable> list = getExecutableList(groupKey);
		if (CollUtil.isEmpty(list)){
			return null;
		}else{
			return list.get(0);
		}
	}

	public void setExecutableList(List<Executable> executableList) {
		this.executableGroup.put(DEFAULT_GROUP, executableList);
	}

	public void addExecutable(Executable executable) {
		addExecutable(DEFAULT_GROUP, executable);
	}

	public void addExecutable(String groupKey, Executable executable) {
		if (ObjectUtil.isNull(executable)){
			return;
		}
		List<Executable> executableList = this.executableGroup.get(groupKey);
		if (CollUtil.isEmpty(executableList)){
			this.executableGroup.put(groupKey, ListUtil.toList(executable));
		}else{
			this.executableGroup.get(groupKey).add(executable);
		}
	}

	public abstract ConditionTypeEnum getConditionType();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 
	 * @deprecated 请使用 {@link #setCurrChainId(String)}
	 */
	@Deprecated
	public String getCurrChainName() {
		return currChainId;
	}

	public String getCurrChainId() {
		return currChainId;
	}
	
	@Override
	public void setCurrChainId(String currChainId) {
		this.currChainId = currChainId;
	}

	public Map<String, List<Executable>> getExecutableGroup() {
		return executableGroup;
	}
}

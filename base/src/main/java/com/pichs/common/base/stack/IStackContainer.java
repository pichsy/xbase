package com.pichs.common.base.stack;

import java.util.ArrayList;
import java.util.List;

/**
 * 栈的管理者接口
 */
public interface IStackContainer {

    /**
     * 集合 IStackChild  list
     */
    List<IStackChild> mStackList = new ArrayList<>();

    /**
     * 添加Activity， 建议添加到list的头部
     * 即：list.add(0,activity)
     *
     * @param activity IStackChild
     */
    void addActivity(IStackChild activity);

    /**
     * 删除Activity
     *
     * @param activity IStackChild
     */
    void removeActivity(IStackChild activity);

    /**
     * 获取当前栈队列
     * @return List<IStackChild>
     */
    List<IStackChild> getStackList();
}

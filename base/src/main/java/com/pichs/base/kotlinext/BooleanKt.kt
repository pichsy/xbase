package com.pichs.base.kotlinext

/*
 * @author: pichs
 */
/**
 * 是否为空
 *  使用时记住和 《  ?. 》 的冲突，
 *  使用[isNULL] 是必须是可空对象，
 *  -----> 不能使用--> xxx?.isNULL
 *  因为 《  ?. 》 的含义是 如果是空就不继续执行后面的代码了。
 *  必须这么用
 *  例如  ： var tmpStr:String? = null
 *  使用：  tmpStr.isNULL{ ... }
 */
inline fun <T : Any> T?.isNULL(block: () -> Unit): T? {
    if (this == null) {
        block()
    }
    return this
}

/**
 * 不为空
 *  使用时记住和 《  ?. 》 的冲突，
 *  使用[isNotNULL] 是必须是可空对象，
 *  ---> 不能使用--> xxx?.isNULL
 *  因为 《  ?. 》 的含义是 如果是空就不继续执行后面的代码了。
 *  必须这么用
 *  例如  ： var tmpStr:String? = null
 *  使用：  tmpStr.isNotNULL{ ... }
 */
inline fun <T : Any> T?.isNotNULL(block: (T) -> Unit): T? {
    if (this != null) {
        block(this)
    }
    return this
}

/**
 * 为true
 */
inline fun Boolean.isTRUE(block: () -> Unit): Boolean {
    if (this) {
        block()
    }
    return this
}

/**
 * 为false
 */
inline fun Boolean.isFALSE(block: () -> Unit): Boolean {
    if (!this) {
        block()
    }
    return this
}


/**
 * 为 true， null会过滤不走
 *  使用时记住和 《  ?. 》 的冲突，
 *  使用 [?.isFALSE] 是必须是可空对象，
 *  ---> 不能使用--> xxx?.isTRUE
 *  因为 《  ?. 》 的含义是 如果是空就不继续执行后面的代码了。
 *  必须这么用
 *  例如  ： var isYes:Boolean? = null
 *  使用：  isYes.isTRUE{ ... }
 *  即： 和该方法最近的那个 点 《 . 》 前面不能带 问号《 ? 》
 */
inline fun Boolean?.isTRUE(block: () -> Unit): Boolean? {
    if (this == true) {
        block()
    }
    return this
}

/**
 * 为 false， null会过滤不走
 *  使用时记住和 《  ?. 》 的冲突，
 *  使用 [?.isFALSE] 是必须是可空对象，
 *  ---> 不能使用--> xxx?.isFALSE
 *  因为 《  ?. 》 的含义是 如果是空就不继续执行后面的代码了。
 *  必须这么用
 *  例如  ： var isNo:Boolean? = null
 *  使用：  isNo.isFALSE{ ... }
 *  即： 和该方法最近的那个 点 《 . 》 前面不能带 问号《 ? 》
 */
inline fun Boolean?.isFALSE(block: () -> Unit): Boolean? {
    if (this == false) {
        block()
    }
    return this
}

/**
 * 是否是null或者false，执行 block()
 * @return Boolean 返回 self
 *  使用时记住和 《  ?. 》 的冲突，
 *  使用 [?.isNullOrFalse] 是必须是可空对象，
 *  ---->  不能使用--> xxx?.isNullOrFalse
 *  因为 《  ?. 》 的含义是 如果是空就不继续执行后面的代码了。
 *  必须这么用
 *  例如  ： var isNo:Boolean? = null
 *  使用：  isNo.isNullOrFalse{ ... }
 *  即： 和该方法最近的那个 点 《 . 》 前面不能带 问号《 ? 》
 */
inline fun Boolean?.isNullOrFalse(block: () -> Unit): Boolean? {
    if (this == null) {
        block()
    } else if (!this) {
        block()
    }
    return this
}

/**
 * 是否是null或者true，执行 block()
 * @return Boolean 返回 self
 *  使用时记住和 《  ?. 》 的冲突，
 *  使用 [?.isNullOrFalse] 是必须是可空对象，
 *  ---->  不能使用--> xxx?.isNullOrTrue
 *  因为 《  ?. 》 的含义是 如果是空就不继续执行后面的代码了。
 *  必须这么用
 *  例如  ： var isNo:Boolean? = null
 *  使用：  isNo.isNullOrTrue{ ... }
 *  即： 和该方法最近的那个 点 《 . 》 前面不能带 问号《 ? 》
 */
inline fun Boolean?.isNullOrTrue(block: () -> Unit): Boolean? {
    if (this == null) {
        block()
    } else if (this) {
        block()
    }
    return this
}

/**
 * 三元运算 单类型返回值。场景居多，
 * 解决kotlin不能用三元运算符 的《痛》
 * val isGood = true
 * val str = isGood.triple({"答对了"},{"答错了"})
 */
inline fun <T> Boolean.triple(trueBlock: (Boolean) -> T, falseBlock: (Boolean) -> T): T {
    return if (this) {
        trueBlock(this)
    } else {
        falseBlock(this)
    }
}

/**
 * 三元运算 多类型返回值。
 * val obj = isGood.triple({ 1 },{"答错了"})
 */
inline fun Boolean.tripleAny(trueBlock: (Boolean) -> Any, falseBlock: (Boolean) -> Any): Any {
    return if (this) {
        trueBlock(this)
    } else {
        falseBlock(this)
    }
}

/**
 * 三元运算
 *  使用时记住和 《  ?. 》 的冲突，
 *  使用 [?.isNullOrFalse] 是必须是可空对象，
 *  ---->  不能使用--> xxx?.tripleNullable
 *  因为 《  ?. 》 的含义是 如果是空就不继续执行后面的代码了。
 *  必须这么用
 *  例如  ： var isNo:Boolean? = null
 *  使用：  isNo.tripleNullable({...},{...})
 *  即： 和该方法最近的那个 点 《 . 》 前面不能带 问号《 ? 》
 */
inline fun <T> Boolean?.tripleNullable(
    trueBlock: (Boolean) -> T,
    falseOrNullBlock: (Boolean?) -> T
): T {
    return if (this == true) {
        trueBlock(this)
    } else {
        falseOrNullBlock(this)
    }
}